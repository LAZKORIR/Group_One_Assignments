package lesson9.prob7;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class LambdaLibrary {
    // Lambda to filter employees by salary and last name, then collect their full names as a string

    public static final BiFunction<List<Employee>, Double, String> FILTER_AND_COLLECT_EMPLOYEES =
            (list, salaryThreshold) -> list.stream()
                    .filter(emp -> emp.getSalary() > salaryThreshold)
                    .filter(emp -> {
                        String lname = emp.getLastName();
                        char fchar = lname.toUpperCase().charAt(0);
                        return fchar >= 'N' && fchar <= 'Z';
                    })
                    .map(emp -> emp.getFirstName() + " " + emp.getLastName())
                    .sorted()
                    .collect(Collectors.joining(","));
}

