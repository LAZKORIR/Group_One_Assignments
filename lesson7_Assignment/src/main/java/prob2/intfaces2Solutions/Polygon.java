package prob2.intfaces2Solutions;

public interface Polygon extends ClosedCurve {
    double[] getLengths();

    default double computePerimeter() {
        double[] sides = getLengths();
        double perimeter = 0;
        for(double side : sides) {
            perimeter += side;
        }
        return perimeter;
    }
}

