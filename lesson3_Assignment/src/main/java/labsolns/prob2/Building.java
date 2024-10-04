package labsolns.prob2;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private List<Apartment> apartments;
    private double maintenanceCost;

    public Building(double maintenanceCost) {
        this.apartments = new ArrayList<Apartment>();
        this.maintenanceCost = maintenanceCost;
    }

    public void addApartments(Apartment apartment){
        this.apartments.add(apartment);
    }
    public double calculateProfit() {
        double totalRent = 0;
        for (Apartment apt : apartments) {
            totalRent += apt.getRent();
        }
        return totalRent - maintenanceCost;
    }
}

