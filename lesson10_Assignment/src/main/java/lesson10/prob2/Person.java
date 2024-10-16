package lesson10.prob2;

import java.util.Objects;

public class Person {
	String name;


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Person person = (Person) obj;
		return Objects.equals(name, person.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	public Person(String n) {
		name = n;
	}
	public String getName() {
		return name;
	}
}
