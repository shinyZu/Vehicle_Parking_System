package view.tm;

public class InParkingTM {
    private String vehicleNo;
    private String vehicleType;
    private int slotNo;
    private String parkedTime;

    public InParkingTM() { }

    public InParkingTM(String vehicleNo, String vehicleType, int slotNo, String parkedTime) {
        this.setVehicleNo(vehicleNo);
        this.setVehicleType(vehicleType);
        this.setSlotNo(slotNo);
        this.setParkedTime(parkedTime);
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

    public int getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(int slotNo) {
        this.slotNo = slotNo;
    }

    public String getParkedTime() {
        return parkedTime;
    }

    public void setParkedTime(String parkedTime) {
        this.parkedTime = parkedTime;
    }

    @Override
    public String toString() {
        return  vehicleNo + ", " + vehicleType + ", " + slotNo + ", " + parkedTime;
    }
}
