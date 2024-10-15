package lesson9.prob7;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		List<Employee> list = Arrays.asList(new Employee("Joe", "Davis", 120000),
				          new Employee("John", "Sims", 110000),
				          new Employee("Joe", "Stevens", 200000),
		                  new Employee("Andrew", "Reardon", 80000),
		                  new Employee("Joe", "Cummings", 760000),
		                  new Employee("Steven", "Walters", 135000),
		                  new Employee("Thomas", "Blake", 111000),
		                  new Employee("Alice", "Richards", 101000),
		                  new Employee("Donald", "Trump", 100000));
		
		//your stream pipeline here
	String res =	list.stream()
				.filter(n ->n.getSalary() > 100000)
				.filter(emp -> {
					String lname = emp.getLastName();
					char fchar = lname.toUpperCase().charAt(0);
					return  fchar>= 'N' && fchar <= 'Z';
				})
				.map(e -> e.getFirstName() + " "+e.getLastName())
				.sorted()
				.collect(Collectors.joining(","));

		System.out.println(res);

		System.out.println();

		// Using the LambdaLibrary element
		String result = LambdaLibrary.FILTER_AND_COLLECT_EMPLOYEES.apply(list, 100000.0);

		// Print the result
		System.out.println(result); // Output: Alice Richards,Joe Stevens,John Sims,Steven Walters

	}

}
