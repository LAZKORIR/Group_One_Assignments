package lesson7.labs.prob3;

import java.util.ArrayList;
import java.util.Random;

public class FoodDeliverySystem {
    public static void main(String[] args) {

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Sushi Palace", 5.0, 10.0));
        restaurants.add(new Restaurant("Pizza Corner", 3.5, 5.0));
        restaurants.add(new Restaurant("Burger World", 4.0, 7.5));

        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John Doe", 60));
        customers.add(new Customer("Jane Smith", 40));
        customers.add(new Customer("Bob Johnson", 80));

        System.out.println("Welcome to the Food Delivery System!");
        System.out.println();

        Random random = new Random();

        for (Restaurant restaurant : restaurants) {
            for (Customer customer : customers) {
                double orderAmount = 50 + (150 * random.nextDouble());
                double deliveryCharge = restaurant.calculateDeliveryCharge(restaurant.getLocationDistance());
                boolean isLoyal = customer.isLoyaltyCustomer(customer.getLoyaltyPoints());

                System.out.printf("Processing order for Customer: %s at Restaurant: %s%n",
                        customer.getCustomerName(), restaurant.getRestaurantName());
                System.out.printf("Customer Loyalty Status: %s%n", isLoyal ? "Loyal Customer" : "New Customer");


                double discount = restaurant.calculateDiscount(orderAmount);
                double totalAfterDiscount = orderAmount - discount + deliveryCharge;


                RestaurantInterface.printFormattedData(
                        restaurant.getRestaurantName(),
                        customer.getCustomerName(),
                        orderAmount,
                        deliveryCharge,
                        discount,
                        totalAfterDiscount
                );
            }
        }
    }
}

