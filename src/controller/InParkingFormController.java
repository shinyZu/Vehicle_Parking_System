package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.InParkingTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static controller.MainFormController.tmParkedVehicles;

public class InParkingFormController {
    public AnchorPane contextInParking;

    public JFXComboBox cmbOptions;
    public JFXButton btnLogout;
    public JFXButton btnAddVehicle;
    public JFXButton btnAddDriver;

    public TableColumn colVehicleNo;
    public TableColumn colVehicleType;
    public TableColumn colParkingSlot;
    public TableColumn colParkedTime;

    public TableView<InParkingTM> tblInParking ;

    /*static { //--Vehicles already parked....
        tmParkedVehicles.add(new InParkingTM("KA-4563","Van",1,"12/07/2021 - 08:51"));
        tmParkedVehicles.add(new InParkingTM("58-3567","Van",3,"12/07/2021 - 08:51"));
        tmParkedVehicles.add(new InParkingTM("GF-4358","Van",13,"12/07/2021 - 08:51"));
        tmParkedVehicles.add(new InParkingTM("KB-3668","Cargo Lorry",5,"12/07/2021 - 08:51"));
        tmParkedVehicles.add(new InParkingTM("YQ-3536","Cargo Lorry",7,"12/07/2021 - 08:51"));
        tmParkedVehicles.add(new InParkingTM("CBB-3566","Cargo Lorry",8,"12/07/2021 - 08:51"));
        tmParkedVehicles.add(new InParkingTM("QH-3444","Cargo Lorry",10,"12/07/2021 - 08:51"));
    }*/

    public void initialize(){
        cmbOptions.getItems().addAll(
                "In Parking", "On Delivery"
        );

        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colParkingSlot.setCellValueFactory(new PropertyValueFactory<>("slotNo"));
        colParkedTime.setCellValueFactory(new PropertyValueFactory<>("parkedTime"));

        tblInParking.setItems(tmParkedVehicles);
    }
    public void listParkedVehiclesOnAction(ActionEvent actionEvent) throws IOException {
        if(cmbOptions.getValue().equals("On Delivery")){
            URL resource = getClass().getResource("../view/OnDeliveryForm.fxml");
            Parent load = FXMLLoader.load(resource);
            contextInParking.getChildren().add(load);
        }
    }

    public void logoutOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();
    }

    public void addVehicleOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AddVehicleForm.fxml"))));
        window.setTitle("Add Vehicle");
        window.centerOnScreen();
        window.show();
    }

    public void addDriverOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AddDriverForm.fxml"))));
        window.setTitle("Add Driver");
        window.centerOnScreen();
        window.show();
    }

}
