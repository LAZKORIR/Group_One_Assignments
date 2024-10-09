package lesson7.labs.prob2.intfaces2Solutions;

public class EquilateralTriangle implements  Polygon {
    private double side;

    public EquilateralTriangle(double side) {
        this.side = side;
    }

    @Override
    public double[] getLengths() {
        return new double[] { side, side, side };
    }

    @Override
    public double computePerimeter() {
        return 3 * side; // or use default method from Polygon
    }
}

