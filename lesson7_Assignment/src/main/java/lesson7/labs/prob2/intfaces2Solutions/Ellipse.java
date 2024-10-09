package lesson7.labs.prob2.intfaces2Solutions;

public class Ellipse implements ClosedCurve {
    private double semiAxis;
    private double elateral;

    public Ellipse(double semiAxis, double elateral) {
        this.semiAxis = semiAxis;
        this.elateral = elateral;
    }

    @Override
    public double computePerimeter() {
        return 4 * semiAxis * elateral;
    }
}

