package lesson7.labs.prob3;

public interface CustomerInterface {
    String getCustomerName();
    int getLoyaltyPoints();

    default boolean isLoyaltyCustomer(int loyaltyPoints) {
        return loyaltyPoints > 50;
    }
}


