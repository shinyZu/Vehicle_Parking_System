package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.DriverTM;
import view.tm.VehicleTM;

import java.io.IOException;
import java.util.regex.Pattern;

import static controller.MainFormController.driverList;
import static controller.MainFormController.vehicleList;
import static javafx.scene.control.ButtonType.CLOSE;

public class AddDriverFormController{
    public AnchorPane contextAddDriver;
    public JFXButton btnAddNewDriver;
    public JFXButton btnCancel;

    public TextField txtDriverName;
    public TextField txtNIC;
    public TextField txtLicenseNo;
    public TextField txtAddress;
    public TextField txtContact;

// --regEx--

    public void addNewDriverOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainForm.fxml"));
        Parent load = loader.load();
        MainFormController controller = loader.getController();

        if ( validateFields() != null){
            controller.loadDriverNames(validateFields());
        }

        /*DriverTM driver = new DriverTM(txtDriverName.getText(),txtNIC.getText(),txtLicenseNo.getText(),txtAddress.getText(),
                txtContact.getText());*/
    }

    // --regEx--

    public DriverTM validateFields(){
        //+[^A-Za-z\s]+    /(^@)|(@$)/    "/^([a-zA-Z]{2}[0-9]{6}|[0-9]{8})?$/"
        boolean isValidDriverName = Pattern.matches("[A-Z][a-z]*+[\\s]+[A-Z][a-z]*",txtDriverName.getText());
        boolean isValidNIC = Pattern.matches("([789][0-9]{9}[V]|[1-9][0-9]{11})",txtNIC.getText());
        boolean isValidDriverLicenseNo = Pattern.matches("[B][0-9]{7}",txtLicenseNo.getText());
        boolean isValidAddress = Pattern.matches("[A-Z][a-z]*",txtAddress.getText());
        boolean isValidContact = Pattern.matches("[0][7][0-9]{8}",txtContact.getText());

        DriverTM newDriver = null;
        if (isValidDriverName){
            if (isValidNIC){
                if (isValidDriverLicenseNo){
                    if (isValidAddress) {
                        if (isValidContact) {
                            newDriver = new DriverTM(txtDriverName.getText(), txtNIC.getText(), txtLicenseNo.getText(), txtAddress.getText(),
                                    txtContact.getText());
                            return newDriver;
                        }else {
                            new Alert(Alert.AlertType.WARNING,"Invalid Contact Number...Try Again...)", CLOSE).show();
                        }
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Invalid Address...Try Again...", CLOSE).show();
                    }
                }else {
                    new Alert(Alert.AlertType.WARNING,"Invalid Driver License Number...Try Again...\n(format - [B...])", CLOSE).show();
                }
            }else {
                new Alert(Alert.AlertType.WARNING,"Invalid NIC...Try Again...\n(format - [0000000000V / 000000000000] )", CLOSE).show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Invalid Driver Name...Try Again...\n(format-[First Last])", CLOSE).show();
        }
        return null;
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
