package lesson8.prob2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;



public class ProductTest {

	enum SortMethod {BYPRICE, BYTITLE};

	private static Comparator<Product> priceComparator = (p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice());
	private static Comparator<Product> titleComparator = (p1, p2) -> p1.getTitle().compareTo(p2.getTitle());


	public static void main(String[] args) {



		List<Product> products = new ArrayList<>();
		products.add(new Product("Laptop", 1200.99, 1001));
		products.add(new Product("Smartphone", 799.49, 1002));
		products.add(new Product("Tablet", 499.99, 1003));
		products.add(new Product("Smartwatch", 199.99, 1004));
		products.add(new Product("Laptop", 1100.75, 1005));  // Same title, different price
		products.add(new Product("Smartphone", 799.49, 1002)); // Duplicate product
		products.add(new Product("Laptop", 1100.75, 1005));    // Duplicate product
		products.add(new Product("Headphones", 99.99, 1006));
		products.add(new Product("Laptop", 950.00, 1007));    // Same title, different model and price
		products.add(new Product("Smartwatch", 199.99, 1008)); // Same price, different model
		products.add(new Product("Headphones", 129.99, 1009)); // Same title, different price
		products.add(new Product("Tablet", 599.99, 1010));     // Same title, different price
		products.add(new Product("Smartphone", 899.99, 1011)); // Same title, different price and model
		products.add(new Product("Smartwatch", 250.00, 1012)); // Same title, different price
		products.add(new Product("Laptop", 999.99, 1013));     // Same title, different price and model
		products.add(new Product("Tablet", 499.99, 1014));     // Duplicate product
		products.add(new Product("Smartphone", 799.49, 1015)); // Duplicate price, different model
		products.add(new Product("Smartwatch", 150.00, 1016)); // Same title, different price and model
		products.add(new Product("Headphones", 89.99, 1017));  // Same title, different price
		products.add(new Product("Tablet", 399.99, 1018));

		//Sorting by price
		//Collections.sort(products, new PriceComparator());
		//products.sort(new PriceComparator());
		System.out.println("Sorted by Price:");
		System.out.println(products);

		//Sort by title
		//Collections.sort(products, new TitleComparator());
		//products.sort(new TitleComparator());
		System.out.println("\nSorted by Title:");
		System.out.println(products);

		// Task c: Sort by price and title using lambdas
		//
		mysort(products,  SortMethod.BYPRICE);
		System.out.println("\nSorted by Price (Lambda):");
		//products.sort(PriceComparator);
		System.out.println(products);

		mysort(products,  SortMethod.BYTITLE);
		System.out.println("\nSorted by Title (Lambda):");
		//products.sort(titleComparator);
		System.out.println(products);

		// Task d: If the title is the same, use the model as another attribute to sort
		//Comparator<Product> titleThenModelComparator = Comparator.comparing(Product::getTitle).thenComparing(Product::getModel);

		Comparator<Product> titleThenModelComparator = (p1, p2) -> {
			int titleComparison = p1.getTitle().compareTo(p2.getTitle());
			if (titleComparison != 0) {
				return titleComparison;
			}
			return Integer.compare(p1.getModel(), p2.getModel());
		};



		System.out.println("\nSorted by Title, then by Model:");
		Collections.sort(products, titleThenModelComparator);
		//products.sort(titleThenModelComparator);
		System.out.println(products);
	}


    public static void mysort(List<Product> products, SortMethod method) {
		// A closure is the implementation of interface embedded inside another class
		// as Local Inner class

		if (method == SortMethod.BYPRICE) {
			Collections.sort(products, priceComparator);

		} else {
			Collections.sort(products, titleComparator);
		}
	}


}



