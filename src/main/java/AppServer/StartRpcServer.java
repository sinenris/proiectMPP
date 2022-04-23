package AppServer;

import AppNetworking.utils.AbstractServer;
import AppNetworking.utils.AppRpcConcurrentServer;
import AppNetworking.utils.ServerException;
import repository.*;
import service.IService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {

       // Properties serverProps=new Properties();
        Properties serverProps = new Properties();
        try{
            serverProps.load(new FileReader("bd.config"));

        } catch (FileNotFoundException e) {
            System.out.println("Nu am gasit");
        } catch (IOException e) {
            System.out.println("Cannot find " + e );
        }
        IUtilizatorRepository userRepo = new UtilizatorRepository(serverProps);
        iExcursieRepository excursieRepo = new ExcursieRepository(serverProps);
        IRezervareRepository rezervareRepository = new RezervareRepository(serverProps);
        IService chatServerImpl=new ServiceImpl(userRepo, excursieRepo, rezervareRepository);
        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new AppRpcConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
