package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFormController{
    public JFXButton btnLogin;
    public JFXButton btnCancel;
    public AnchorPane contextLoginForm;
    public TextField txtUserName;

    public ImageView imgShowPwd;
    public ImageView imgHidePwd;
    public PasswordField fieldPassword;
    public CheckBox chkHideShowPwd;
    public TextField txtPassword;


    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        //--Validation using RegEx--

        /*Pattern p = Pattern.compile("[A-Z][a-z]*");
        Matcher m = p.matcher(txtUserName.getText());
        boolean matchUserNameFound = m.matches();*/

        boolean userNameMatchFound = Pattern.matches("[A-Z][a-z]*", txtUserName.getText());
        boolean passwordIsEmpty = Pattern.matches("[a-zA-Z0-9]{0}", fieldPassword.getText());
        boolean passwordMatchFound = Pattern.matches("[0-9]{4}",fieldPassword.getText());
        boolean usersMatch = Pattern.matches("Admin|Manager",txtUserName.getText());

        if (userNameMatchFound && !passwordIsEmpty && usersMatch){
            if (passwordMatchFound){
                Stage window = (Stage) contextLoginForm.getScene().getWindow();
                window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/InParkingForm.fxml"))));
                window.setTitle("Parking System");
                window.centerOnScreen();
            }else{
                new Alert(Alert.AlertType.WARNING, "Invalid username or password", ButtonType.CLOSE).show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Invalid username or password", ButtonType.CLOSE).show();
        }
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void showPasswordOnAction(MouseEvent mouseEvent) {
        txtPassword.toFront();
        imgHidePwd.toFront();
        txtPassword.setText(fieldPassword.getText());
    }

    public void hidePasswordOnAction(MouseEvent mouseEvent) {
        fieldPassword.toFront();
        imgShowPwd.toFront();

    }

   /* public StackPane stackpane;
    public TextField txtSamplePwd;
    public PasswordField samplePwdField;
    public ImageView imgHP;*/


   /* public void showPasswordSample(MouseEvent mouseEvent) {
        samplePwdField.setPrefHeight(50);
        txtSamplePwd.setPrefHeight(50);
        samplePwdField.textProperty().bindBidirectional(txtSamplePwd.textProperty());

        imgHP.setFitHeight(32);
        imgHP.setFitWidth(32);

        StackPane.setMargin(imgHP, new Insets(0, 10, 0, 0));
        StackPane.setAlignment(imgHP, Pos.CENTER_RIGHT);

        imgHP.setOnMousePressed((event) -> {
            samplePwdField.toFront();
            imgHP.toFront();
        });

        imgHP.setOnMouseClicked((e) -> {
           // txtSamplePwd.toFront();
            txtSamplePwd.setText("aaa");
            imgHP.toFront();
        });
        imgHP.setOnMouseClicked((e) -> {
            samplePwdField.toFront();
            imgHP.toFront();
        });

    }*/
}
