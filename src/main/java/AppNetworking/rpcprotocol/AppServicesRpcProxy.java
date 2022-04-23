package AppNetworking.rpcprotocol;

import AppNetworking.dtoUtils.DTOUtils;
import AppNetworking.dtoUtils.ExcursieDTO;
import domain.Excursie;
import domain.Rezervare;
import domain.Utilizator;
import service.IObserver;
import service.IService;
import service.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AppServicesRpcProxy implements IService {

    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public AppServicesRpcProxy(String host, int port){
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }


    private void initializeConnection() {

        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private boolean isUpdate(Response response){
        return response.type()== ResponseType.EXECUTA_REZERVARE_N;
    }

    @Override
    public void logout(Utilizator u, IObserver obs) throws ServiceException {
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(u).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            throw new ServiceException( err);
        }
    }

    @Override
    public Iterable<Excursie> getAllExcursie() throws ServiceException {
        Iterable<Excursie> excursii = new ArrayList<>();
        Request req = new Request.Builder().type(RequestType.GET_ALL).data(null).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            throw new ServiceException(err);
        }
        Iterable<ExcursieDTO> exc = (Iterable<ExcursieDTO>)response.data();
        Iterable<Excursie> excR = DTOUtils.getFromDTO(exc);
        return excR;

    }

    @Override
    public Iterable<Excursie> getSomeExcursie(String obiectiv, int min, int max) throws ServiceException {
        Iterable<Excursie> excursii = new ArrayList<>();
        String trimite = obiectiv + " " + min + " " + max;
        Request req = new Request.Builder().type(RequestType.GET_SOME).data(trimite).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            throw new ServiceException(err);
        }
        Iterable<Excursie> exc = (Iterable<Excursie>)response.data();
        return exc;
    }

    @Override
    public void executaRezervare(Rezervare r) throws ServiceException {
        Request req = new Request.Builder().type(RequestType.EXECUTE_R).data(r).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type()==ResponseType.ERROR){
            throw new ServiceException("error");
        }
    }



    @Override
    public Utilizator findByNamePw(String nume, String pw, IObserver client) throws ServiceException {
        initializeConnection();
        Utilizator u = new Utilizator((long)1,nume,pw);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(u).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() ==ResponseType.OK){
            this.client = client;
            return u;
        }
        if(response.type() ==ResponseType.ERROR){
            String err = response.data().toString();
            closeConnection();
            throw new ServiceException(err);
        }
        return null;
    }

    private Response readResponse() throws ServiceException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendRequest(Request req) throws ServiceException{
        try {
            output.writeObject(req);
            output.flush();
        } catch (IOException e) {
            throw new ServiceException("Error sending object "+e);
        }
    }


    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }

    private void handleUpdate(Response response){

        if(response.type()==ResponseType.EXECUTA_REZERVARE_N){
            Iterable<ExcursieDTO> exc = (Iterable<ExcursieDTO>)response.data();
            Iterable<Excursie> excR = DTOUtils.getFromDTO(exc);
            try{
                client.executaRezervare(excR);
            }
            catch (ServiceException exce){
                exce.printStackTrace();
            }

        }
    }

}
