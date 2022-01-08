package view.tm;

import static controller.MainFormController.driverList;

public class DriverTM {
    private String name;
    private String nic;
    private String driverLicenseNo;
    private String address;
    private int contact;

    public DriverTM() {
    }

    public DriverTM(String name, String nic, String driverLicenseNo, String address, String contact) {
        this.setName(name);
        this.setNic(nic);
        this.setDriverLicenseNo(driverLicenseNo);
        this.setAddress(address);
        this.setContact(Integer.parseInt(contact));
    }

    @Override
    public String toString() {
        return  getName() /*+", "+ getNic() +", "+ getDriverLicenseNo() +", "+ getAddress() +", " + getContact()*/;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getDriverLicenseNo() {
        return driverLicenseNo;
    }

    public void setDriverLicenseNo(String driverLicenseNo) {
        this.driverLicenseNo = driverLicenseNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }
    @Override
    public boolean equals(Object obj) {
        DriverTM d = (DriverTM) obj;
        for (DriverTM dtm : driverList) {
            if(dtm.getNic().equalsIgnoreCase(d.getNic()) || dtm.getDriverLicenseNo().equalsIgnoreCase(d.getDriverLicenseNo()) || dtm.getContact() == d.getContact()){
                return true;
            }
        }
        return false;

    }
}
