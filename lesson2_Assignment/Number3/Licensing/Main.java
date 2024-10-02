package Licensing;

public class Main {

    public static void main(String[] args) {
        // Create driver
        Driver driver = new Driver("Lazarus Korir");

       
        driver.addLicense("ABC12345");

        
        System.out.println(driver);

       
        driver.removeLicense();

        // Print the driver's details after removing the license
        System.out.println(driver);
    }
}