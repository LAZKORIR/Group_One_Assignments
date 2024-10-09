package lesson7.labs.prob2.intfaces2Solutions;


public class Rectangle implements Polygon {
	private double length, width;

	public Rectangle(double length, double width) {
		this.length = length;
		this.width = width;
	}

	@Override
	public double[] getLengths() {
		return new double[] { length, width, length, width };
	}

	@Override
	public double computePerimeter() {
		// Using the default method from Polygon to compute the perimeter
		return Polygon.super.computePerimeter();
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}
}
