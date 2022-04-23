package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class Login {
    Service s;
    public Button btnLogare;
    public TextField txtPassword;
    public TextField txtUsername;

    public void setService(Service s){
        this.s = s;
    }

    public void handleLogare(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if(s.findByNamePw(username,password)!=null){
            FXMLLoader loaderN = new FXMLLoader();
            loaderN.setLocation(getClass().getResource("/appl.fxml"));
            AnchorPane rootN = null;
            try {
                rootN = loaderN.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage dialogStage = new Stage();
            dialogStage.setTitle(txtUsername.getText());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene sceneN= new Scene(rootN);
            dialogStage.setScene(sceneN);
            Controller c = loaderN.getController();
            c.setServices(this.s);
            dialogStage.show();
        }

    }
}
