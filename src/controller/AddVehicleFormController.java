package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.VehicleTM;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

import static controller.MainFormController.vehicleList;
import static controller.MainFormController.vehicleNoList;
import static javafx.scene.control.ButtonType.CLOSE;


public class AddVehicleFormController {
    public AnchorPane contextAddVehicle;
    public JFXButton btnAddVehicle;
    public JFXButton btnCancel;

    public TextField txtVehicleNo;
    public TextField txtMaxWeight;
    public TextField txtNoOfPassengers;
    public ComboBox<String> cmbVehicleType;

    public void initialize(){
        cmbVehicleType.getItems().addAll(
                "Bus","Cargo Lorry","Van"
        );

    }

    Alert alert = null;
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
    Optional<ButtonType> result = null;

// -- regEx--

    public void addNewVehicleOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainForm.fxml"));
        Parent load = loader.load();
        MainFormController controller = loader.getController();

        //boolean txtVehNoIsEmpty = Pattern.matches(null,txtVehicleNo.getText());

        if (txtVehicleNo.getText().equals("") || txtMaxWeight.getText().equals("") || txtNoOfPassengers.getText().equals("") || cmbVehicleType.getValue() == null){
            new Alert(Alert.AlertType.WARNING,"Please fill all details...",ButtonType.OK).show();
            return;
        }

        // ---if the manager tries to add a new bus to the system
        boolean vehicleTypeFoundAsBus = Pattern.matches("Bus",cmbVehicleType.getValue());

        if (vehicleTypeFoundAsBus){
            alert = new Alert(Alert.AlertType.CONFIRMATION,"No parking slot available for another Bus...\n" +
                    "Are you sure you want to add this Bus?",yes,no);

            result = alert.showAndWait();

            if (result.orElse(no) == yes){
                if ( validateFields() != null){
                    controller.loadVehicleNo(validateFields());
                }
            }

        }else { //---if the manager tries to add a new vehicle(Van or a Lorry) to the system
            int countVans = 0;
            int countLorries = 0;

            for (VehicleTM vtm : vehicleList) {
                if (vtm.getVehicleType().equalsIgnoreCase("Van")){
                    countVans++;
                }
            }
            for (VehicleTM vtm : vehicleList) {
                if (vtm.getVehicleType().equalsIgnoreCase("Cargo Lorry")){
                    countLorries++;
                }
            }

            boolean vehicleTypeFoundAsVan = Pattern.matches("Van",cmbVehicleType.getValue());
            boolean vehicleTypeFoundAsLorry = Pattern.matches("Cargo Lorry",cmbVehicleType.getValue());

            if (vehicleList.size() < 14 ) { //---if the no. of vehicles currently exist in the company is less than 14
                if ( vehicleTypeFoundAsVan && countVans < 6) { //-- if the total no. of "Vans" that the company currently have is less than "6"
                    if ( validateFields() != null){
                        controller.loadVehicleNo(validateFields());
                    }
                }else if ( vehicleTypeFoundAsVan && countVans >= 6){ //-- if the total no. of "Vans" that the company currently have exceeds "6"
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Reached maximum number of Vans.\nAre you sure you want to add this Van?", yes,no);
                    result = alert.showAndWait();

                    if (result.orElse(no) == yes){
                        if ( validateFields() != null){
                            controller.loadVehicleNo(validateFields());
                        }
                    }
                }else if (vehicleTypeFoundAsLorry && countLorries < 7){  //-- if the total no. of "Cargo Lorries" that the company currently have is less than "7"
                    if ( validateFields() != null){
                        controller.loadVehicleNo(validateFields());
                    }
                }else if (vehicleTypeFoundAsLorry && countLorries >= 7){ //-- if the total no. of "Cargo Lorries" that the company currently have exceeds "7"
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Reached maximum number of Cargo Lorries.\nAre you sure you want to add this Cargo Lorry?", yes,no);
                    result = alert.showAndWait();

                    if (result.orElse(no) == yes){
                        if ( validateFields() != null){
                            controller.loadVehicleNo(validateFields());
                        }
                    }
                }
            }else if (vehicleList.size() >= 14){ // if the manager tries to add a new vehicle when the total no.of vehicles(Bus,Van,Lorry) in the company equals to the maximum that it can keep(14)

                alert = new Alert(Alert.AlertType.CONFIRMATION, "Reached maximum number of Vehicles.\nAre you sure you want to add this Vehicle?", yes,no);

                result = alert.showAndWait();

                if (result.orElse(no) == yes){
                    if ( validateFields() != null){
                        controller.loadVehicleNo(validateFields());
                    }
                }
            }
        }
    }

    // -- regEx--

    public VehicleTM validateFields(){

        boolean isValidVehicleNumber = Pattern.matches("[A-Z|0-9]+[A-Z|0-9]+[-]+[0-9]{4}",txtVehicleNo.getText());
        boolean isValidWeightValue = Pattern.matches("[0-9]*",txtMaxWeight.getText());
        boolean isValidPassengerQuantity = Pattern.matches("[0-9]{0,3}",txtNoOfPassengers.getText());

        VehicleTM newVehicle = null;
        if (isValidVehicleNumber){
            if (isValidWeightValue){
                if (isValidPassengerQuantity){
                    newVehicle = new VehicleTM(txtVehicleNo.getText(), cmbVehicleType.getValue(),
                            txtMaxWeight.getText(), txtNoOfPassengers.getText());
                    return newVehicle;
                }else {
                    new Alert(Alert.AlertType.WARNING,"Invalid Passenger Quantity...Try Again...", CLOSE).show();
                }
            }else {
                new Alert(Alert.AlertType.WARNING,"Invalid Weight Value...Try Again...", CLOSE).show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Invalid Vehicle Number...Try Again...\n(format-[XX-0000])", CLOSE).show();
        }
        return null;
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
