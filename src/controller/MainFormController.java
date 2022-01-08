package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import model.*;
import view.tm.DriverTM;
import view.tm.InParkingTM;
import view.tm.OnDeliveryTM;
import view.tm.VehicleTM;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.time.LocalDate.now;
import static javafx.scene.control.ButtonType.CLOSE;

public class MainFormController {
    public AnchorPane contextMainForm ;

    public JFXComboBox<String> cmbSelectVehicle;
    public JFXTextField txtVehicleType;
    public JFXComboBox<String> cmbDriver;
    public Text txtSlotNumber;
    public JFXButton btnMngLogin;
    public Button btnParkVehicle;
    public Button btnOnDeliveryShift;

    public Pane paneClock;
    public Text txtTime;
    public Text txtDate;
    public Text txtDay;

    /*public static void printTime(String timeString) {
        LiveClock clock = new LiveClock();
        clock.DigitalWatch();
        System.out.println(timeString);

        lblTime.setText(timeString);
        lblDate.setText(timeString);
    }*/

    public MainFormController(){
        txtSlotNumber = new Text();

        txtTime = new Text();
        txtDate = new Text();
        txtDay = new Text();
        currentDateTime();
    }

    //---------------------------------------------Live Clock--------------------------------------------------
    public void currentDateTime(){

        Thread thread = new Thread(){
            public void run(){
                while (true){

                    Calendar cal = new GregorianCalendar();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMMM-dd");
                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm:ss a");
                    DateFormat formatter = new SimpleDateFormat("EEEE", Locale.forLanguageTag("en"));

                    Date date = cal.getTime();

                    //txtDay.setText(getDayStringOld(dayOfMonth, "en"));
                    txtDay.setText(formatter.format(date));
                    txtDate.setText(simpleDateFormat.format(date));
                    txtTime.setText(simpleTimeFormat.format(date));

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    /*int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                    int day = cal.get(Calendar.DAY_OF_WEEK);

                    //int hour = cal.get(Calendar.HOUR); // 12 hour clock
                    int hour = cal.get(Calendar.HOUR_OF_DAY); // 24 hour clock
                    int am_pm = cal.get(Calendar.AM_PM);
                    int minute = cal.get(Calendar.MINUTE);
                    int second = cal.get(Calendar.SECOND);*/

                    /*System.out.println(simpleDateFormat.format(date));
                    System.out.println(simpleTimeFormat.format(date));

                    System.out.println(day+"/"+(month+1)+"/"+year);
                    System.out.println(hour+":"+(minute)+":"+second);*/
                }
            }
        };
        thread.start();
    }
    //--------------------------------------------------------------------------------------------------------

    public static ObservableList<String> vehicleNoList = FXCollections.observableArrayList();
    public static ObservableList<String> driverNameList = FXCollections.observableArrayList();

    public void initialize(){

        if (vehicleNoList.size() == 0) {
            for (VehicleTM v : vehicleList) {
                vehicleNoList.add(v.getVehicleNo());
            }
            cmbSelectVehicle.setItems(vehicleNoList);
        }else{
            //loadVehicleNo();
        }

        if (driverNameList.size() == 0) {
            for (DriverTM d : driverList) {
                driverNameList.add(d.getName());
            }
            cmbDriver.setItems(driverNameList);
        } else{
            //loadDriverNames();
        }
    }

    public void loadVehicleNo(VehicleTM vehicle){
        cmbSelectVehicle.getItems().clear();

        if(vehicleList.get(0).equals(vehicle)){ //overridden equals() in VehicleTM.java
            new Alert(Alert.AlertType.WARNING,"This Vehicle Already exists...", ButtonType.CLOSE).show();
        }else{
            vehicleList.add(vehicle);
            vehicleNoList.add(vehicle.getVehicleNo());
            new Alert(Alert.AlertType.CONFIRMATION,"Vehicle added successfully...", CLOSE).show();
            cmbSelectVehicle.setItems(vehicleNoList);
        }
    }

    public void loadDriverNames(DriverTM driver){
        cmbDriver.getItems().clear();

        for (DriverTM dtm : driverList) {
            if(dtm.getNic().equalsIgnoreCase(driver.getNic())) {
                new Alert(Alert.AlertType.WARNING, "This Driver Already Exists...\nPlease Check the Driver NIC...", ButtonType.CLOSE).show();
            }else if (dtm.getDriverLicenseNo().equalsIgnoreCase(driver.getDriverLicenseNo())) {
                new Alert(Alert.AlertType.WARNING, "This Driver Already Exists...\nPlease Check the Driver License Number...", ButtonType.CLOSE).show();
            }else if (dtm.getContact() == driver.getContact()){
                new Alert(Alert.AlertType.WARNING, "This Driver Already Exists...\nPlease Check the Driver Contact Number...", ButtonType.CLOSE).show();
            }
        }

        if (!driverList.get(0).equals(driver)){
            driverList.add(driver);
            driverNameList.add(driver.getName());
            new Alert(Alert.AlertType.CONFIRMATION, "Driver added successfully...", CLOSE).show();
            cmbDriver.setItems(driverNameList);
        }
    }

    public static ObservableList<VehicleTM> vehicleList = FXCollections.observableArrayList();
    public static ObservableList<DriverTM> driverList = FXCollections.observableArrayList();

    public static ObservableList<InParkingTM> tmParkedVehicles = FXCollections.observableArrayList();
    public static ObservableList<OnDeliveryTM> tmOnDeliveryVehicles = FXCollections.observableArrayList();

    public static ObservableList<Vehicle> parkedVehicleListWithNIC = FXCollections.observableArrayList();


    static {
        //-- Drivers that the Company have(includes both drivers on delivery shift and who are not)--

        driverList.add(new DriverTM("Sumith Kumara","7835348345V","B6474845","Panadura","0725637456")); // Parked
        driverList.add(new DriverTM("Amila Pathirana","8826253734V","B3354674","Galle","0717573583"));
        driverList.add(new DriverTM("Jithmal Perera","9283289272V","B3674589","Horana","0772452457"));
        driverList.add(new DriverTM("Sumith Dissanayaka", "9425245373V", "B8366399", "Kaluthara","0782686390"));
        driverList.add(new DriverTM("Chethiya Dilan", "9162353436V", "B6836836", "Panadura","0772436737"));
        driverList.add(new DriverTM("Dushantha Perera", "9255556343V", "B3334435", "Matara","0777245343"));
        driverList.add(new DriverTM("Charith Sudara", "9573536833V", "B6835836", "Baththaramulla","0771536662"));
        driverList.add(new DriverTM("Prashan Dineth", "9362426738V", "B2683536", "Wadduwa","0715353434")); // On Delivery
        driverList.add(new DriverTM("Dinesh Udara", "9026344373V", "B5343783", "Hettimulla","0713456878"));
        driverList.add(new DriverTM("Priyanga Perera", "9135343537V", "B3853753", "Matara","0723344562"));

        //--Vehicles that the company have(both in Parking and On Delivery)--

        vehicleList.add(new VehicleTM("KA-4563","Van","1000","7")); // In Park
        vehicleList.add(new VehicleTM("58-3567","Van","1500","4"));
        vehicleList.add(new VehicleTM("GF-4358","Van","800","4"));
        vehicleList.add(new VehicleTM("KB-3668","Cargo Lorry","2500","2"));
        vehicleList.add(new VehicleTM("YQ-3536","Cargo Lorry","2000","2"));
        vehicleList.add(new VehicleTM("CBB-3566","Cargo Lorry","2500","2"));
        vehicleList.add(new VehicleTM("QH-3444","Cargo Lorry","5000","4"));
        vehicleList.add(new VehicleTM("CCB-3568","Van","1800","8")); // On Delivery
        vehicleList.add(new VehicleTM("JJ-9878","Cargo Lorry","3000","2"));
        vehicleList.add(new VehicleTM("NA-3434","Bus","3500","60"));
        //vehicleList.add(new VehicleTM("LM-6679","Van","1500","4"));
        //vehicleList.add(new VehicleTM("QA-3369","Van","1800","6"));

        //--Vehicles that are parked and the Drivers who parked them--

        parkedVehicleListWithNIC.add(new Van("KA-4563","Van",1000,7,"7835348345V"));
        parkedVehicleListWithNIC.add(new CargoLorry("KB-3668","Cargo Lorry",2500,2,"8826253734V"));
        parkedVehicleListWithNIC.add(new CargoLorry("QH-3444","Cargo Lorry",5000,4,"9283289272V"));
        parkedVehicleListWithNIC.add(new Van("58-3567","Van",1500,4,"9425245373V"));
        parkedVehicleListWithNIC.add(new Van("GF-4358","Van",800,4,"9573536833V"));
        parkedVehicleListWithNIC.add(new CargoLorry("YQ-3536","Cargo Lorry",2000,2,"9362426738V"));
        parkedVehicleListWithNIC.add(new CargoLorry("CBB-3566","Cargo Lorry",2500,2,"9026344373V"));

        //--Vehicles that are On Delivery--

        tmOnDeliveryVehicles.add(new OnDeliveryTM("CCB-3568","Van","Chethiya Dilan","18/07/2020 - 02:51 PM"));
        tmOnDeliveryVehicles.add(new OnDeliveryTM("JJ-9878","Cargo Lorry","Dushantha Perera","18/07/2020 - 08:41 AM"));
        tmOnDeliveryVehicles.add(new OnDeliveryTM("NA-3434","Bus","Priyanga Perera","18/07/2020 - 04:30 PM"));

        //--Vehicles In Park--

        tmParkedVehicles.add(new InParkingTM("KA-4563","Van",1,"12/07/2021 - 08:15 AM"));
        tmParkedVehicles.add(new InParkingTM("58-3567","Van",3,"12/07/2021 - 07:26 PM"));
        tmParkedVehicles.add(new InParkingTM("GF-4358","Van",13,"12/07/2021 - 09:13 AM"));
        tmParkedVehicles.add(new InParkingTM("KB-3668","Cargo Lorry",5,"12/07/2021 - 06:21 PM"));
        tmParkedVehicles.add(new InParkingTM("YQ-3536","Cargo Lorry",7,"12/07/2021 - 11:09 AM"));
        tmParkedVehicles.add(new InParkingTM("CBB-3566","Cargo Lorry",8,"12/07/2021 - 12:43 PM"));
        tmParkedVehicles.add(new InParkingTM("QH-3444","Cargo Lorry",10,"12/07/2021 - 10:38 AM"));
    }

    public void goToLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage window = new Stage();
        window.setScene(scene);
        window.setTitle("Login");
        window.centerOnScreen();
        window.show();
    }

    public void searchVehicleTypeOnAction(ActionEvent actionEvent) {
        btnOnDeliveryShift.setDisable(false);
        btnParkVehicle.setDisable(false);

        for (VehicleTM v : vehicleList) { //when vehicle number is selected it automatically sets the vehicle type
            if(cmbSelectVehicle.getValue().equals(v.getVehicleNo())){
                txtVehicleType.setText(v.getVehicleType());
            }
        }
        for (OnDeliveryTM otm : tmOnDeliveryVehicles) { //when on delivery vehicle is selected it automatically selects the driver
            if (cmbSelectVehicle.getValue().equals(otm.getVehicleNo())){
                cmbDriver.setValue(otm.getDriverName());
            }
        }
        for (OnDeliveryTM otm : tmOnDeliveryVehicles) { // disables the On Delivery Shift button when a vehicle comes to park
            if (otm.getVehicleNo().equals(cmbSelectVehicle.getValue())){
                btnOnDeliveryShift.setDisable(true);
            }
        }
        for (InParkingTM itm : tmParkedVehicles) { // disables the Park Vehicle button when a vehicle leaves the park
            if (itm.getVehicleNo().equals(cmbSelectVehicle.getValue())){
                btnParkVehicle.setDisable(true);
            }
        }
    }

    public void searchDrivingVehicleOnAction(ActionEvent actionEvent) {
        if (cmbDriver.getSelectionModel().getSelectedItem() != null) {
            for (OnDeliveryTM otm : tmOnDeliveryVehicles) { //when driver on delivery is selected it automatically selects the vehicle number and the vehicle type
                if (cmbDriver.getValue().equalsIgnoreCase(otm.getDriverName())) {
                    cmbSelectVehicle.setValue(otm.getVehicleNo());
                    txtVehicleType.setText(otm.getVehicleType());
                }
            }
        }else{
           // return;
        }
    }

    public void clearInputs(){
        cmbSelectVehicle.setValue("");
        cmbDriver.setValue(null);
        txtVehicleType.setText("");
    }

    // ---------------------------------Polymorphism/Dynamic Method Dispatch/Inheritance/Abstraction---------------------------

    public void parkVehicleOnAction(ActionEvent actionEvent) {
        String slot = null;
        Vehicle vehicle;

        if (txtVehicleType.getText().equalsIgnoreCase("Van")){
            vehicle = new Van(cmbDriver.getValue());
            vehicle.park(cmbSelectVehicle.getValue(),txtVehicleType.getText());

            slot = String.format("%02d", vehicle.getAvailableSlot()); // declared as an interface in order to feed knowledge to vehicle reference about getAvailableSlot()
            setSlotNumber(slot);
            btnOnDeliveryShift.setDisable(false);
            clearInputs();

        }else if (txtVehicleType.getText().equalsIgnoreCase("Bus")){
            vehicle = new Bus(cmbDriver.getValue());
            vehicle.park(cmbSelectVehicle.getValue(),txtVehicleType.getText());

            slot = String.format("%02d", vehicle.getAvailableSlot()); // Filling with zeroes --> format - 00
            setSlotNumber(slot);
            btnOnDeliveryShift.setDisable(false);
            clearInputs();

        }else if (txtVehicleType.getText().equalsIgnoreCase("Cargo Lorry")){
            vehicle = new CargoLorry(cmbDriver.getValue());
            vehicle.park(cmbSelectVehicle.getValue(),txtVehicleType.getText());

            slot = String.format("%02d", vehicle.getAvailableSlot());
            setSlotNumber(slot);
            btnOnDeliveryShift.setDisable(false);
            clearInputs();
        }

    }

    public void onDeliveryShiftOnAction(ActionEvent actionEvent) {
        Vehicle vehicle;

        if (cmbDriver.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a Driver...", ButtonType.OK).show();
            return;

        }else {
            if (txtVehicleType.getText().equalsIgnoreCase("Van")) {
                    vehicle = new Van(cmbDriver.getValue());
                    vehicle.leavePark(cmbSelectVehicle.getValue(), txtVehicleType.getText());
                    btnParkVehicle.setDisable(false);
                    clearInputs();

            } else if (txtVehicleType.getText().equalsIgnoreCase("Bus")) {
                vehicle = new Bus(cmbDriver.getValue());
                vehicle.leavePark(cmbSelectVehicle.getValue(), txtVehicleType.getText());

                btnParkVehicle.setDisable(false);
                clearInputs();

            } else if (txtVehicleType.getText().equalsIgnoreCase("Cargo Lorry")) {
                vehicle = new CargoLorry(cmbDriver.getValue());
                vehicle.leavePark(cmbSelectVehicle.getValue(), txtVehicleType.getText());

                btnParkVehicle.setDisable(false);
                clearInputs();
            }
        }
    }

    public void setSlotNumber(String slot) {
        //String newSlot = "";
        //------------------------Animation--to blink--
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(txtSlotNumber.getText().length() != 0){
                    txtSlotNumber.setText("");
                }else{
                    txtSlotNumber.setText(slot);
                }
            }
        };

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), eventHandler));
        //animation.setCycleCount(Timeline.INDEFINITE);
        animation.setCycleCount(06);
        animation.play();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> txtSlotNumber.setText("00")));
        timeline.play();

    }

}


