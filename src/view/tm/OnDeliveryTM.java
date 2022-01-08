package view.tm;

public class OnDeliveryTM {
    private String vehicleNo;
    private String vehicleType;
    private String driverName;
    private String leftTime;

    public OnDeliveryTM() { }

    public OnDeliveryTM(String vehicleNo, String vehicleType, String driverName, String leftTime) {
        this.setVehicleNo(vehicleNo);
        this.setVehicleType(vehicleType);
        this.setDriverName(driverName);
        this.setLeftTime(leftTime);
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

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
    }

    @Override
    public String toString() {
        return  vehicleNo + ", " + vehicleType + ", " + driverName + ", " + leftTime;
    }
}
