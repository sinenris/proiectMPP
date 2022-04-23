package controller;


import domain.Excursie;
import domain.Rezervare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.Service;
import utils.ExcursieEvent;
import utils.Observer;

public class Controller implements Observer<ExcursieEvent> {
    Service s;

    public TableView<Excursie> tvExcursii;
    public TableView<Excursie> tvExcursii2;
    public TableColumn<Excursie, Long> tcId;
    public TableColumn<Excursie, String> tcOT;
    public TableColumn<Excursie, String> tcFT;
    public TableColumn<Excursie, Integer> tcOP;
    public TableColumn<Excursie, Double> tcP;
    public TableColumn<Excursie, Long> tcNLD;
    public TableColumn<Excursie, Long> tcIdC;
    public TableColumn<Excursie, String> tcOTC;
    public TableColumn<Excursie, String> tcFTC;
    public TableColumn<Excursie, Integer> tcOTP;
    public TableColumn<Excursie, Double> tcPC;
    public TableColumn<Excursie, Long> tcNLDC;
    public Label lbExcursii1;
    public Label lbExcursii2;
    public TextField txtLimInf;
    public TextField txtTuristic;
    public TextField txtLimSup;
    public Button btnCauta;
    public Button btnRezerva;
    public TextField txtRezerva;
    public TextField txtTelefon;
    public TextField txtBilete;
    public Button btnLogout;

    ObservableList<Excursie> getExcursie(){
        ObservableList<Excursie> excursii =  FXCollections.observableArrayList();
        s.getAllExcursie().forEach(excursii::add);
        return excursii;
    }
    ObservableList<Excursie> getExcursiiC(String nume, int min, int max){
        ObservableList<Excursie> excursii2 =  FXCollections.observableArrayList();
        s.getSomeExcursie(nume,min,max).forEach(excursii2::add);
        return excursii2;
    }


    public void init(){
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcOT.setCellValueFactory(new PropertyValueFactory<>("obiectivTuristic"));
        tcFT.setCellValueFactory(new PropertyValueFactory<>("numeFirmaTransport"));
        tcOP.setCellValueFactory(new PropertyValueFactory<>("oraPlecarii"));
        tcP.setCellValueFactory(new PropertyValueFactory<>("pret"));
        tcNLD.setCellValueFactory(new PropertyValueFactory<>("locuriDisponibile"));
        tvExcursii.setItems(getExcursie());

        tcIdC.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcOTC.setCellValueFactory(new PropertyValueFactory<>("obiectivTuristic"));
        tcFTC.setCellValueFactory(new PropertyValueFactory<>("numeFirmaTransport"));
        tcOTP.setCellValueFactory(new PropertyValueFactory<>("oraPlecarii"));
        tcPC.setCellValueFactory(new PropertyValueFactory<>("pret"));
        tcNLDC.setCellValueFactory(new PropertyValueFactory<>("locuriDisponibile"));
        //tvExcursii2.setItems(getExcursiiC("Bucuresti",800,1700));

        tvExcursii.setRowFactory(tv -> new TableRow<Excursie>(){
            @Override
            protected void updateItem(Excursie item, boolean empty){
                super.updateItem(item,empty);
                if(empty || item==null){
                    setStyle("");
                }
                else if(item.getLocuriDisponibile()<=0){
                    //setStyle("-fx-text-fill: red");
                    this.setStyle("-fx-text-background-color: red");
                }
                else {
                    setStyle("");
                }

            }
        });

        tvExcursii2.setRowFactory(tv -> new TableRow<Excursie>(){
            @Override
            protected void updateItem(Excursie item, boolean empty){
                super.updateItem(item,empty);
                if(empty || item==null){
                    setStyle("");
                }
                else if(item.getLocuriDisponibile()<=0){
                    //setStyle("-fx-text-fill: red");
                    this.setStyle("-fx-text-background-color: red");
                }
                else {
                    setStyle("");
                }

            }
        });

    }

    public void setServices(Service s){
        this.s = s;
        s.addObserver(this);
        init();

    }
    public void handleCauta(ActionEvent actionEvent) {

        try{
            String nume = txtTuristic.getText();
            int min = Integer.parseInt(txtLimInf.getText());
            int sup = Integer.parseInt(txtLimSup.getText());
            tvExcursii2.setItems(getExcursiiC(nume,min,sup));
        }
        catch(Exception e){


        }
    }

    public void handleRezerva(ActionEvent actionEvent) {
        try{
            Long id = tvExcursii.getSelectionModel().getSelectedItem().getId();
            String nume = txtRezerva.getText();
            String telefon = txtTelefon.getText();
            int nrLocuriRezervate = Integer.parseInt(txtBilete.getText());
            Rezervare r = new Rezervare((long)1,id,nume,telefon,nrLocuriRezervate);
            s.executaRezervare(r);
            init();
        }
        catch(Exception e){

        }
    }

    @Override
    public void update(ExcursieEvent excursieEvent) {
        init();
    }

    public void handleLogOut(ActionEvent actionEvent) {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();
    }
}
