package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.OnDeliveryTM;

import java.io.IOException;
import java.net.URL;

import static controller.MainFormController.tmOnDeliveryVehicles;
import static controller.MainFormController.tmParkedVehicles;

public class OnDeliveryFormController {
    public AnchorPane contextOnDelivery;

    public JFXButton btnLogout;
    public JFXComboBox cmbOptions;
    public JFXButton btnAddVehicle;
    public JFXButton btnAddDriver;

    public TableView<OnDeliveryTM> tblOnDelivery;

    public TableColumn colVehicleNo;
    public TableColumn colVehicleType;
    public TableColumn colDriverName;
    public TableColumn colLeftTime;

    public void initialize(){
        cmbOptions.getItems().addAll(
                "In Parking", "On Delivery"
        );

        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colLeftTime.setCellValueFactory(new PropertyValueFactory<>("leftTime"));

        tblOnDelivery.setItems(tmOnDeliveryVehicles);
    }

    public void listOnDeliveryVehiclesOnAction(ActionEvent actionEvent) throws IOException {
        if(cmbOptions.getValue().equals("In Parking")){
            URL resource = getClass().getResource("../view/InParkingForm.fxml");
            Parent load = FXMLLoader.load(resource);
            contextOnDelivery.getChildren().add(load);
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
