package AppClient;

import domain.Utilizator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.IService;
import service.ServiceException;

import java.io.IOException;

public class ControllerLogin {
    Parent mainChatParent;
    IService s;
    public Button btnLogare;
    public TextField txtPassword;
    public TextField txtUsername;

    private ControllerMain cm;
    private Utilizator crtUser;

    public void setControllerMain(ControllerMain s){
        this.cm = s;
    }
    public void setService(IService s, Parent p){
        this.s = s; this.mainChatParent = p;
    }

    public void handleLogare(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

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
                ControllerMain c = loaderN.getController();
                c.setServices(this.s);
        try {
            Utilizator u = s.findByNamePw(username, password,c);
            if(u!=null){
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                c.setUtilizator(u);
                dialogStage.show();
            }
        } catch (ServiceException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            alert.show();
        }

    }
}
