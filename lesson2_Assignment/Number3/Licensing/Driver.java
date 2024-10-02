package Licensing;

public class Driver {
    private String name;
    private String licenseNumber;
    private DriverLicense driverLicense; 

    // Constructor
    public Driver(String name) {
        this.name = name;
        this.driverLicense = null; // Initially, no license is associated
    }

    // Method to get the driver's name
    public String getName() {
        return name;
    }

    // Method to get the driver's license
    public DriverLicense getLicense() {
        return driverLicense;
    }

    // Method to add a license to the driver (controls the association)
    public void addLicense(String licenseNum) {
        if (this.driverLicense == null) {
            this.driverLicense = new DriverLicense(licenseNum);
            this.driverLicense.setDriver(this);  // Set driver in the license
            this.licenseNumber = licenseNum;
        } else {
            System.out.println("Driver already has a license.");
        }
    }

    // Method to remove the driver's license
    public void removeLicense() {
        if (this.driverLicense != null) {
            this.driverLicense.setDriver(null); // Break association on license side
            this.driverLicense = null;
            this.licenseNumber = null;
        } else {
            System.out.println("Driver has no license to remove.");
        }
    }

    // toString for display
    @Override
    public String toString() {
        return "Driver{name='" + name + "', licenseNumber='" + licenseNumber + "'}";
    }
}
