package view.tm;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import static controller.MainFormController.driverList;
import static controller.MainFormController.vehicleList;

public class VehicleTM {
    private String vehicleNo;
    private String vehicleType;
    private int maxWeight;
    private int noOfPassengers;

    public VehicleTM() { }

    public VehicleTM(String vehicleNo, String vehicleType, String maxWeight, String noOfPassengers) {
        this.setVehicleNo(vehicleNo);
        this.setVehicleType(vehicleType);
        this.setMaxWeight(Integer.parseInt(maxWeight));
        this.setNoOfPassengers(Integer.parseInt(noOfPassengers));
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(int noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }

    @Override
    public String toString() {
        return vehicleNo + ", " + vehicleType + ", " + maxWeight + ", " + noOfPassengers;
    }

    @Override
    public boolean equals(Object obj) {
        VehicleTM v = (VehicleTM) obj;
        for (VehicleTM vtm : vehicleList) {
            if(vtm.getVehicleNo().equalsIgnoreCase(v.getVehicleNo())){
                return true;
            }
        }
        return false;
    }
}
