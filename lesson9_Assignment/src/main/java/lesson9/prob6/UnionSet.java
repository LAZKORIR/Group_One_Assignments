package lesson9.prob6;

import java.util.*;

public class UnionSet {
    public static void main(String[] args) {

        List<Set<String>> sets = Arrays.asList(
                new HashSet<>(Arrays.asList("A", "B")),
                new HashSet<>(Collections.singleton("D")),
                new HashSet<>(Arrays.asList("1", "3", "5"))
        );

        Set<String> result = union(sets);
        System.out.println(result); // Output: [A, B, D, 1, 3, 5]

    }
    public static Set<String> union(List<Set<String>> sets) {
        return sets.stream()
                .reduce(new LinkedHashSet<>(), (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                });
    }
}
