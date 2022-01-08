package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import view.tm.DriverTM;
import view.tm.InParkingTM;
import view.tm.OnDeliveryTM;

import java.text.SimpleDateFormat;
import java.util.Date;

import static controller.MainFormController.*;

public class VehicleClass{
    protected String vehicleNo;
    protected String vehicleType;
    protected int maxWeight;
    protected int noOfPassengers;
    protected String driverNIC;

    protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:mm a");
    protected Date date = new Date();


    //--verify whether the vehicle that is selected to park is already parked--
    public boolean checkVehicleAlreadyInPark(String vehicleNo){
        for (InParkingTM itm : tmParkedVehicles) {
            if (itm.getVehicleNo().equals(vehicleNo)) {
                return true;
            }
        }
        return false;
    }

    //--checks the status of the Vehicles and Drivers and updates the On Delivery table when a Vehicle is leaving the park--
    public void setOnDeliveryVehicles(String vehicleNo, String vehicleType, int[] reservedSlots, boolean[] occupiedSlots, String driverNIC, SimpleDateFormat sdf, Date date) {
        for (OnDeliveryTM otm : tmOnDeliveryVehicles) {
            if (otm.getVehicleNo().equals(vehicleNo)) {
                new Alert(Alert.AlertType.WARNING, "This Vehicle Is Already On Delivery...", ButtonType.CLOSE).show();
                return;
            }
        }

        for (DriverTM dtm : driverList) {
            if (dtm.getNic().equals(driverNIC)) {
                String driverName = dtm.getName();
                for ( OnDeliveryTM otm : tmOnDeliveryVehicles) {
                    if (otm.getDriverName().equals(driverName)){
                        new Alert(Alert.AlertType.WARNING, "This Driver Is Already On Delivery...", ButtonType.CLOSE).show();
                        return;
                    }
                }
            }
        }

        int slotToBeFree = 0;
        InParkingTM itm2 = null;
        for (InParkingTM itm : tmParkedVehicles) {
            if(itm.getVehicleNo().equals(vehicleNo)){
                itm2 = itm;
                for (int index = 0; index < reservedSlots.length; index++){
                    if(reservedSlots[index] == itm.getSlotNo()){
                        occupiedSlots[index] = false;
                        slotToBeFree = reservedSlots[index];
                    }
                }
            }
        }
        tmParkedVehicles.remove(itm2);

        for (DriverTM dtm : driverList) {
            if (dtm.getNic().equals(driverNIC)){
                tmOnDeliveryVehicles.add(new OnDeliveryTM(vehicleNo,vehicleType,dtm.getName(), sdf.format(date)));

            }
        }

        String slot = String.format("%02d", slotToBeFree);
        new Alert(Alert.AlertType.INFORMATION,"Slot "+slot+" is now free...\nHave A Safe Drive!!!", ButtonType.OK).show();

    }

    //--to verify whether its the same driver that brings back the vehicle--
    public boolean verifyDriverWithOnDeliveryVehicle(String vehicleNo, String driverNIC){
        for (OnDeliveryTM otm : tmOnDeliveryVehicles) {
            if (otm.getVehicleNo().equals(vehicleNo)){
                String driverWhoDroveTheVehicle = otm.getDriverName();
                for (DriverTM dtm : driverList) {
                    if (dtm.getName().equals(driverWhoDroveTheVehicle)){
                        if(dtm.getNic().equals(driverNIC)){ // if same driver
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
