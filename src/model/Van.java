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

public class Van extends VehicleClass implements Vehicle{

    private int availableSlot;
    
    private int[] reservedSlotsVan = {1,2,3,4,12,13};
    private static boolean[] occupiedSlots = {true,false,true,false,false,true};

    public Van() { }

    public Van(String driverName) {
        for (DriverTM dtm :driverList) {
            if(dtm.getName().equals(driverName)){
                this.driverNIC = dtm.getNic();
            }
        }
    }

    public Van(String vehicleNo, String vehicleType, int maxWeight, int noOfPassengers, String driverNIC) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.maxWeight = maxWeight;
        this.noOfPassengers = noOfPassengers;
        this.driverNIC = driverNIC;

    }

    public int getAvailableSlot() {
        return availableSlot;
    }

    public int[] getReservedSlotsVan() { return reservedSlotsVan;}

    public static boolean[] getOccupiedSlots() { return occupiedSlots;}

    public void setAvailableSlot(int availableSlot) { this.availableSlot = availableSlot;}

    public void setReservedSlotsVan(int[] reservedSlotsVan) { this.reservedSlotsVan = reservedSlotsVan;}

    public static void setOccupiedSlots(boolean[] occupiedSlots) { Van.occupiedSlots = occupiedSlots;}

    @Override
    public String toString() {
        return  vehicleNo + ", " +  vehicleType + ", " + maxWeight +", " + noOfPassengers + ", " + driverNIC;
    }

    @Override
    public void park(String vehicleNo, String vehicleType){

        //--to verify whether its the same driver that brings back the vehicle--
        //OnDeliveryTM otm2 = null;
        for (OnDeliveryTM otm : tmOnDeliveryVehicles) {
            if (otm.getVehicleNo().equals(vehicleNo)){
                //otm2 = otm;
                if (!verifyDriverWithOnDeliveryVehicle(vehicleNo,this.driverNIC)) {
                    new Alert(Alert.AlertType.WARNING, "Driver Mismatch...Try Again...", ButtonType.CLOSE).show();
                    return;
                }
            }
        }
        //tmOnDeliveryVehicles.remove(otm2);

        if ((occupiedSlots[0] && occupiedSlots[1] && occupiedSlots[2] && occupiedSlots[3] && occupiedSlots[4] && occupiedSlots[5]) == true && availableSlot == 0 && checkVehicleAlreadyInPark(vehicleNo)) {
            new Alert(Alert.AlertType.WARNING, "This Van is already Parked...\nAll Slots for Vans have been occupied...", ButtonType.CLOSE).show();

        } else if ((occupiedSlots[0] && occupiedSlots[1] && occupiedSlots[2] && occupiedSlots[3] && occupiedSlots[4] && occupiedSlots[5]) == true && availableSlot == 0) {
            new Alert(Alert.AlertType.WARNING, "No Slot Available...\nAll Slots for Vans have been occupied...", ButtonType.CLOSE).show();

        } else {
            if (!checkVehicleAlreadyInPark(vehicleNo)) {
                for (int index = 0; index < reservedSlotsVan.length; index++) {
                    if (!occupiedSlots[index]) {
                        availableSlot = reservedSlotsVan[index];
                        occupiedSlots[index] = true;
                        tmParkedVehicles.add(new InParkingTM(vehicleNo, vehicleType, availableSlot, sdf.format(date)));
                        break;
                    }
                }
            } else if (checkVehicleAlreadyInPark(vehicleNo)) {
                new Alert(Alert.AlertType.WARNING, "This Van is Already Parked...", ButtonType.CLOSE).show();
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
                    parkedVehicleListWithNIC.add(new Van(vehicleNo, vehicleType, vtm.getMaxWeight(), vtm.getNoOfPassengers(), driverNIC));
                    System.out.println("listWithNIC" + parkedVehicleListWithNIC.toString());
                }
            }
        }

    }

    @Override
    public void leavePark(String vehicleNo, String vehicleType) {
        setOnDeliveryVehicles(vehicleNo, vehicleType, this.reservedSlotsVan, occupiedSlots, this.driverNIC, this.sdf, this.date);
    }
}
