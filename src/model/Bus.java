package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import view.tm.DriverTM;
import view.tm.InParkingTM;
import view.tm.OnDeliveryTM;
import view.tm.VehicleTM;

import java.text.SimpleDateFormat;
import java.util.Date;

import static controller.MainFormController.*;
import static controller.MainFormController.parkedVehicleListWithNIC;

public class Bus extends VehicleClass implements Vehicle{
    /*private String vehicleNo;
    private String vehicleType;
    private int maxWeight;
    private int noOfPassengers;
    private String driverNIC;*/

    private int[] reservedSlotsBus = {14};
    private static boolean[] occupiedSlots = {false};

    private int availableSlot;

    public Bus() { }

    public Bus(String driverName) {
        for (DriverTM dtm :driverList) {
            if(dtm.getName().equals(driverName)){
                this.driverNIC = dtm.getNic();
            }
        }
    }

    public Bus(String vehicleNo, String vehicleType, int maxWeight, int noOfPassengers, String driverNIC) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.maxWeight = maxWeight;
        this.noOfPassengers = noOfPassengers;
        this.driverNIC = driverNIC;
    }

    public int getAvailableSlot() {
        return this.availableSlot;
    }

    public int[] getReservedSlotsVan() { return reservedSlotsBus;}

    public static boolean[] getOccupiedSlots() { return occupiedSlots;}

    public void setAvailableSlot(int availableSlot) { this.availableSlot = availableSlot;}

    public void setReservedSlotsVan(int[] reservedSlotsVan) { this.reservedSlotsBus = reservedSlotsVan;}

    public static void setOccupiedSlots(boolean[] occupiedSlots) { Bus.occupiedSlots = occupiedSlots;}

    @Override
    public String toString() {
        return  vehicleNo + ", " +  vehicleType + ", " + maxWeight +", " + noOfPassengers + ", " + driverNIC;
    }

    @Override
    public void park(String vehicleNo, String vehicleType) {

        for (OnDeliveryTM otm : tmOnDeliveryVehicles) {
            if (otm.getVehicleNo().equals(vehicleNo)){
                if (!verifyDriverWithOnDeliveryVehicle(vehicleNo,this.driverNIC)) {
                    new Alert(Alert.AlertType.WARNING, "Driver Mismatch...Try Again...", ButtonType.CLOSE).show();
                    return;
                }
            }
        }

        if (occupiedSlots[0] == true && availableSlot == 0 && checkVehicleAlreadyInPark(vehicleNo)) {
            new Alert(Alert.AlertType.WARNING, "This Bus is already Parked...\nAll Slots for Buses have been occupied...", ButtonType.CLOSE).show();

        } else if (occupiedSlots[0] == true && availableSlot == 0) {
            new Alert(Alert.AlertType.WARNING, "No Slot Available...\nAll Slots for Buses have been occupied...", ButtonType.CLOSE).show();

        } else {
            if (!checkVehicleAlreadyInPark(vehicleNo)) {
                for (int index = 0; index < reservedSlotsBus.length; index++) {
                    if (!occupiedSlots[index]) {
                        availableSlot = this.reservedSlotsBus[index];
                        occupiedSlots[index] = true;
                        tmParkedVehicles.add(new InParkingTM(vehicleNo, vehicleType, getAvailableSlot(), this.sdf.format(date)));
                        break;
                    }
                }
            } else if (checkVehicleAlreadyInPark(vehicleNo)) {
                new Alert(Alert.AlertType.WARNING, "This Bus is Already Parked...", ButtonType.CLOSE).show();
            }
        }

        OnDeliveryTM odt2 = null;
        for (OnDeliveryTM odt : tmOnDeliveryVehicles) {
            if (odt.getVehicleNo().equals(vehicleNo)) {
                odt2 = odt;
            }
        }
        tmOnDeliveryVehicles.remove(odt2);

    }

    @Override
    public void leavePark(String vehicleNo, String vehicleType) {
        setOnDeliveryVehicles(vehicleNo, vehicleType, reservedSlotsBus, occupiedSlots, this.driverNIC, this.sdf, this.date);
    }
}
