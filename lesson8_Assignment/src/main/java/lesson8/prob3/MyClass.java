package lesson8.prob3;

import java.util.function.Predicate;

public class MyClass {
	int x;
	String y;

	public MyClass(int x, String y) {
		this.x = x;
		this.y = y;
	}

	// Testing method to check the equality
	public void myMethod(MyClass cl) {

		// Using  method reference
		Predicate<MyClass> isEqualMethodRef = this::equals;
		System.out.println(isEqualMethodRef.test(cl));  // Check if 'cl' is equal to 'this'
	}

	public void myMethodUsingLambda(MyClass cl) {
		// Using a lambda expression

		Predicate<MyClass> isEqualLambda = other -> this.equals(other);
		System.out.println(isEqualLambda.test(cl));  // Check if 'cl' is equal to 'this'
	}

	@Override
	public boolean equals(Object ob) {
		if (ob == null) return false;
		if (ob.getClass() != getClass()) return false;
		MyClass mc = (MyClass) ob;
		return mc.x == x && mc.y.equals(y);
	}

	public static void main(String[] args) {
		MyClass myclass = new MyClass(1, "A");
		MyClass myclass1 = new MyClass(1, "B");
		System.out.println("Using  method reference");
		myclass.myMethod(myclass);  // Output: true
		myclass.myMethod(myclass1); // Output: false

		System.out.println();

		System.out.println("Using a lambda expression");
		myclass.myMethodUsingLambda(myclass);  // Output: true
		myclass.myMethodUsingLambda(myclass1); // Output: false

	}
}
