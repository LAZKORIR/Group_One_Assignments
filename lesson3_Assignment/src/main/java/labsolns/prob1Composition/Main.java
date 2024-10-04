package labsolns.prob1Composition;

public class Main {

    public static void main(String[] args) {
        PersonWithJob p1 = new PersonWithJob("Joe", 30000);
        Person p2 = new Person("Joe");

        // Now p1 and p2 should not be equal because they are different types
        System.out.println("p1.equals(p2)? " + p1.equals(p2));  // false
        System.out.println("p2.equals(p1)? " + p2.equals(p1));  // false
    }

}
