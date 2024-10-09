package lesson7.labs.prob3;

public interface RestaurantInterface {
    String getRestaurantName();
    double calculateDeliveryCharge(double distance);

    static void printFormattedData(String restaurantName, String customerName, double orderAmount,
                                   double deliveryCharge, double discount, double totalAfterDiscount) {
        System.out.printf("Restaurant: %s%n", restaurantName);
        System.out.printf("Customer: %s%n", customerName);
        System.out.printf("Order Amount: $%.2f%n", orderAmount);
        System.out.printf("Delivery Charge: $%.2f%n", deliveryCharge);
        System.out.printf("Discount Applied: $%.2f%n", discount);
        System.out.printf("Total after Discount: $%.2f%n", totalAfterDiscount);
        System.out.println();
    }

    default double calculateDiscount(double totalOrderAmount) {
        if (totalOrderAmount > 100) {
            return totalOrderAmount * 0.05;
        }
        return 0.0;
    }
}

