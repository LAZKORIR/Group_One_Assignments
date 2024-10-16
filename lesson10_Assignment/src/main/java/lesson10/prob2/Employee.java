package lesson10.prob2;

import java.util.Objects;

public class Employee extends Person {
	int id;
	int salary;
	
	public Employee(int id, String n, int s) {
		super(n);
		this.id = id;
		this.salary = s;
		
	}
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<");
		sb.append("name: ");
		sb.append(getName());
		sb.append(" salary: ");
		sb.append("" + salary);
		sb.append(" year of birth: ");
		return sb.toString();
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Employee employee = (Employee) obj;
		return id == employee.id &&
				Objects.equals(name, employee.name) &&
				Double.compare(employee.salary, salary) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, salary);
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	
}

