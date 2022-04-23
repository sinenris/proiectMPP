package AppClient;

import AppNetworking.rpcprotocol.AppServicesRpcProxy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.IService;

import java.io.IOException;
import java.util.Properties;

public class StartRpcClientFX extends Application {

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClientFX.class.getResourceAsStream("/appclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("chat.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("chat.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IService server = new AppServicesRpcProxy(serverIP, serverPort);



        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("login.fxml")
        );
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ControllerLogin ctrl = loader.getController();
        ctrl.setService(server, root);


        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Hi");
        primaryStage.show();




    }


}
