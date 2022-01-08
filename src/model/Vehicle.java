package model;

import controller.MainFormController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import view.tm.DriverTM;
import view.tm.InParkingTM;
import view.tm.OnDeliveryTM;

import java.text.SimpleDateFormat;
import java.util.Date;

import static controller.MainFormController.*;

public interface Vehicle {
    /*String vehicleNo = "";
    String vehicleType = "";
    int maxWeight = 0;
    int noOfPassengers = 0;
    String driverNIC = "";*/

    int getAvailableSlot();

    void park(String vehicleNo, String vehicleType);

    void leavePark(String vehicleNo, String vehicleType);

}
