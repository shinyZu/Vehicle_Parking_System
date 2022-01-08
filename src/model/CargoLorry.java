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

public class CargoLorry extends VehicleClass implements Vehicle {

    /*private String vehicleNo;
    private String vehicleType;
    private int maxWeight;
    private int noOfPassengers;
    private String driverNIC;*/

    private int[] reservedSlotsLorry = {5,6,7,8,9,10,11};
    private static boolean[] occupiedSlots = {true,false,true,true,false,true,false};

    private int availableSlot;

    public CargoLorry() { }

    public CargoLorry(String driverName) {
        for (DriverTM dtm :driverList) {
            if(dtm.getName().equals(driverName)){
                this.driverNIC = dtm.getNic();
            }
        }
    }

    public CargoLorry(String vehicleNo, String vehicleType, int maxWeight, int noOfPassengers, String driverNIC) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.maxWeight = maxWeight;
        this.noOfPassengers = noOfPassengers;
        this.driverNIC = driverNIC;
    }

    public int getAvailableSlot() {
        return this.availableSlot;
    }

    public int[] getReservedSlotsVan() { return reservedSlotsLorry;}

    public static boolean[] getOccupiedSlots() { return occupiedSlots;}

    public void setAvailableSlot(int availableSlot) { this.availableSlot = availableSlot;}

    public void setReservedSlotsVan(int[] reservedSlotsVan) { this.reservedSlotsLorry = reservedSlotsVan;}

    public static void setOccupiedSlots(boolean[] occupiedSlots) { CargoLorry.occupiedSlots = occupiedSlots;}

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

        if ((occupiedSlots[0] && occupiedSlots[1] && occupiedSlots[2] && occupiedSlots[3] && occupiedSlots[4] && occupiedSlots[5] && occupiedSlots[6]) == true && availableSlot == 0 && checkVehicleAlreadyInPark(vehicleNo)) {
            new Alert(Alert.AlertType.WARNING, "This Cargo Lorry is Already Parked...\nAll Slots for Cargo Lorries have been occupied...", ButtonType.CLOSE).show();

        } else if ((occupiedSlots[0] && occupiedSlots[1] && occupiedSlots[2] && occupiedSlots[3] && occupiedSlots[4] && occupiedSlots[5] && occupiedSlots[6]) == true && availableSlot == 0) {
            new Alert(Alert.AlertType.WARNING, "No Slot Available...\nAll Slots for Cargo Lorries have been occupied...", ButtonType.CLOSE).show();

        } else {
            if (!checkVehicleAlreadyInPark(vehicleNo)) {
                for (int index = 0; index < reservedSlotsLorry.length; index++) {
                    if (!occupiedSlots[index]) {
                        availableSlot = this.reservedSlotsLorry[index];
                        occupiedSlots[index] = true;
                        tmParkedVehicles.add(new InParkingTM(vehicleNo, vehicleType, availableSlot, this.sdf.format(this.date)));
                        break;
                    }
                }
            } else if (checkVehicleAlreadyInPark(vehicleNo)) {
                new Alert(Alert.AlertType.WARNING, "This Cargo Lorry is Already Parked...", ButtonType.CLOSE).show();
            }
        }

        OnDeliveryTM odt2 = null;
        for (OnDeliveryTM odt : tmOnDeliveryVehicles) {
            if (odt.getVehicleNo().equals(vehicleNo)) {
                odt2 = odt;
            }
        }
        tmOnDeliveryVehicles.remove(odt2);

        if (!checkVehicleAlreadyInPark(vehicleNo)) {
            for (VehicleTM vtm : vehicleList) {
                if (vtm.getVehicleNo().equals(vehicleNo)) {
                    parkedVehicleListWithNIC.add(new CargoLorry(vehicleNo, vehicleType, vtm.getMaxWeight(), vtm.getNoOfPassengers(), this.driverNIC));
                    System.out.println("listWithNIC" + parkedVehicleListWithNIC.toString());
                }
            }
        }

    }

    @Override
    public void leavePark(String vehicleNo, String vehicleType) {
        setOnDeliveryVehicles(vehicleNo, vehicleType, reservedSlotsLorry, occupiedSlots, this.driverNIC, this.sdf, this.date);
    }
}
