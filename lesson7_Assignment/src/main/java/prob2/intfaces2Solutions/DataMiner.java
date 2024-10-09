package prob2.intfaces2Solutions;

import java.util.ArrayList;
import java.util.List;

public class DataMiner {
    List<ClosedCurve> objects = new ArrayList<>();

    public static void main(String[] args) {
        DataMiner dm = new DataMiner();
        dm.objects.add(new Rectangle(5,7));
        dm.objects.add(new Circle(6));
        dm.objects.add(new EquilateralTriangle(4));
        dm.objects.add(new Ellipse(3, 1.2)); // semi axis and elateral
        System.out.println("Average Perimeter: " + dm.computeAveragePerimeter());
    }

    public double computeAveragePerimeter() {
        if(objects == null || objects.isEmpty()) return 0.0;
        double sum = 0.0;
        for(ClosedCurve obj : objects) {
            sum += obj.computePerimeter();
        }
        return sum / objects.size();
    }
}
