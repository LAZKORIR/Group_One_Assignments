package labsolns.prob2;

import java.util.ArrayList;
import java.util.List;

public class Landlord {
    private List<Building> buildings;

    String name;

    public Landlord(String name) {
        this.buildings = new ArrayList<Building>();
        this.name=name;
    }

    public void addBuildings(Building building){
        this.buildings.add(building);
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public String getName() {
        return name;
    }

    public double calculateTotalProfit() {
        double totalProfit = 0;
        for (Building building : buildings) {
            totalProfit += building.calculateProfit();
        }
        return totalProfit;
    }
}

