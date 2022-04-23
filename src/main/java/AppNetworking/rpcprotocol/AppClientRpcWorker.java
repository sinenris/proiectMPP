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

public class AppClientRpcWorker implements Runnable, IObserver {

    private IService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public AppClientRpcWorker(IService server, Socket connection){
        this.server = server;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }
    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request) {
        Response response = null;
        if(request.type() ==RequestType.LOGIN){
            System.out.println("Login request .. "+ request.type());
            Utilizator u = (Utilizator)request.data();
            try{
                server.findByNamePw(u.getUsername(), u.getPassword(), this);
                return new Response.Builder().type(ResponseType.OK).build();
            }
            catch (ServiceException e){
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if(request.type() == RequestType.GET_ALL){
            try{
                Iterable<Excursie> excursieList = server.getAllExcursie();
                Iterable<ExcursieDTO> excursieDTOS = DTOUtils.getDTO(excursieList);

                return new Response.Builder().type(ResponseType.GET_ALL).data(excursieDTOS).build();
            }
            catch (ServiceException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if(request.type() == RequestType.GET_SOME){
            String u = (String)request.data();
            String[] words = u.split(" ");
            try{
                Iterable<Excursie> excursieList = server.getSomeExcursie(words[0],Integer.parseInt(words[1]),Integer.parseInt(words[2]));
                return new Response.Builder().type(ResponseType.GET_SOME).data(excursieList).build();
            }
            catch(ServiceException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if(request.type()==RequestType.EXECUTE_R){
            System.out.println("Sending EXECUTE_R request.");
            Rezervare r = (Rezervare)request.data();
            try {
                server.executaRezervare(r);
                System.out.println("Sending response EXEC Rezervare.");
                return new Response.Builder().type(ResponseType.EXECUTA_REZERVARE).data(r).build();
            } catch (ServiceException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }

        }
        if(request.type() ==RequestType.LOGOUT){
            System.out.println("Sending logout Request");
            Utilizator u = (Utilizator)request.data();
            try{
                server.logout(u, this);
                connected = false;
                return okResponse;
            } catch (ServiceException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        return response;
    }

    @Override
    public void executaRezervare(Iterable<Excursie> e) throws ServiceException {
        Iterable<Excursie> excursieList = e;
        Iterable<ExcursieDTO> excursieDTOS = DTOUtils.getDTO(excursieList);
        Response resp=new Response.Builder().type(ResponseType.EXECUTA_REZERVARE_N).data(excursieDTOS).build();
        try {
            sendResponse(resp);
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }
    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }
}
