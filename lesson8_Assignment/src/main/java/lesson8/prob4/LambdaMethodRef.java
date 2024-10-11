package lesson8.prob4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiFunction;

public class LambdaMethodRef {
    public static void main(String[] args) {
        String fname = "Tom";
        String lname = "Bruce";
        // Imperative code
        String com = fname + " " +lname;
        System.out.println(com);
       
        /* Task 1 - Must provide a space between first and last name
           a) Convert the concatenation task of imperative code to lambda, 
           which takes two string inputs and return a string.
           b) Do the same using Method Reference
           c) Print the result on console by invoking the
              Lambda and Method Reference object
        */

        //Concatenating names using lambda
        System.out.println("Concatenating names using lambda");
        BiFunction<String,String,String> concatString = (s1,s2) -> s1+" "+s2;
        System.out.println(concatString.apply(fname,lname));

        //Concatenating names using method reference
        System.out.println("Concatenating names using method reference");
        BiFunction<String,String,String> concatString1 = LambdaMethodRef::concatenate;
        System.out.println(concatString1.apply(fname,lname));
       
        String[] names1 = {"Alexis", "Tim", "Kyleen", "Bruce", "tom"};
        // Imperative code - Using Arrays.sort with an anonymous Comparator to ignore case
        Arrays.sort(names1, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2); // Ignore case during comparison
            }
        });
        System.out.println("Sored list using imperative");
        for (String name : names1) {
            System.out.println(name);
        }

        /* Task-2
        a.  Use Arrays.sort() to sort the names
            by ignore case using lambda for the above imperative style of code.
        b.  Use Arrays.sort() to sort the names
            by ignore case using Method reference.
        c.  Print the sorted list on console
         */

        System.out.println("Sorted List using lambda");
        String[] names2 = {"Alexis", "Tim", "Kyleen", "Bruce", "tom"};

        //Using lambda expression (without method reference)
        Arrays.sort(names2,(s1,s2)-> s1.compareToIgnoreCase(s2));

        for (String name : names2) {
            System.out.println(name);
        }

        System.out.println("Sorted List using method reference");
        String[] names3 = {"Alexis", "Tim", "Kyleen", "Bruce", "tom"};

        //Using method reference
        Arrays.sort(names3,String::compareToIgnoreCase);

        for (String name : names3) {
            System.out.println(name);
        }

       

    }

    public static String concatenate(String s1, String s2){

        return s1+" " + s2;
    }

}
