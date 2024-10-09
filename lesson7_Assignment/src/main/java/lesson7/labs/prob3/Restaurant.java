package lesson7.labs.prob3;

public class Restaurant implements RestaurantInterface {
    private String restaurantName;
    private double baseDeliveryCharge;
    private double locationDistance;

    public Restaurant(String restaurantName, double baseDeliveryCharge, double locationDistance) {
        this.restaurantName = restaurantName;
        this.baseDeliveryCharge = baseDeliveryCharge;
        this.locationDistance = locationDistance;
    }

    public double getLocationDistance() {
        return locationDistance;
    }

    @Override
    public String getRestaurantName() {
        return restaurantName;
    }

    @Override
    public double calculateDeliveryCharge(double distance) {
        return baseDeliveryCharge + (locationDistance * 0.5);
    }
}


