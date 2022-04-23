import controller.Controller;
import controller.Login;
import domain.Excursie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.ExcursieRepository;
import repository.RezervareRepository;
import repository.UtilizatorRepository;
import service.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainFXX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Properties props = new Properties();
        try{
            props.load(new FileReader("bd.config"));

        } catch (FileNotFoundException e) {
            System.out.println("Nu am gasit");
        } catch (IOException e) {
            System.out.println("Cannot find " + e );
        }
        ExcursieRepository excursieRepo = new ExcursieRepository(props);
        RezervareRepository rezervareRepository = new RezervareRepository(props);
        //       rezervareRepository.save(new Rezervare((long)1,(long)2,"Matei","012",13 ));
//        for(Excursie e : excursieRepo.findAll()){
//            System.out.println(e);
//        }
//        for(Excursie e : excursieRepo.findByNameHour("Bucuresti",1200,1400)){
//            System.out.println(e);
//        }
        UtilizatorRepository utilizatorRepository = new UtilizatorRepository(props);
//        System.out.println(utilizatorRepository.findByNamePwd("abc","def").getId());

        Service service = new Service(utilizatorRepository,excursieRepo,rezervareRepository);
        service.getAllExcursie().forEach(System.out::println);

//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("appl.fxml"));
//        AnchorPane root = null;
//        try {
//            root = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // loader.setController(new Controller());
//        Controller ctrl = loader.getController();
//        ctrl.setServices(service);
//
//        primaryStage.setScene(new Scene(root));
//        primaryStage.setTitle("Hi");
//        primaryStage.show();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // loader.setController(new Controller());
        Login ctrl = loader.getController();
        ctrl.setService(service);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Hi");
        primaryStage.show();

//        Label label = new Label("Hi");
//        Scene scene = new Scene(label);
//        primaryStage.setScene(scene);
//        primaryStage.show();

    }
}
