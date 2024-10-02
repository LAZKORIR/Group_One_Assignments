package Licensing;

public class DriverLicense {
    private String licenseNum;
    private Driver driver;  // Back reference to the driver

    // Constructor
    public DriverLicense(String licenseNum) {
        this.licenseNum = licenseNum;
        this.driver = null; // Initially no driver
    }

    // Method to get the license number
    public String getLicenseNum() {
        return licenseNum;
    }

    // Method to set the driver (Controlled by Driver class)
    void setDriver(Driver driver) {
        this.driver = driver;
    }

    // Method to get the associated driver
    public Driver getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return "DriverLicense{licenseNum='" + licenseNum + "'}";
    }
}
