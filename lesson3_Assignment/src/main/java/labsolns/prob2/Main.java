package labsolns.prob2;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Apartment apt1 = new Apartment(1200);
        Apartment apt2 = new Apartment(1000);
        Apartment apt3 = new Apartment(1400);

        Building building1 = new Building( 500);
        building1.addApartments(apt1);
        building1.addApartments(apt2);
        Building building2 = new Building( 300);
        building2.addApartments(apt3);

        Landlord landlord = new Landlord("Alvin");
        landlord.addBuildings(building1);
        landlord.addBuildings(building2);

        // Calculate and display the total profit
        double totalProfit = landlord.calculateTotalProfit();
        System.out.println("Landlord's total monthly profit: $" + totalProfit);
    }
}

