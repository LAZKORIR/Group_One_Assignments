package labsolns.prob1Composition;

public class Person {
    private String name;

    Person(String n) {
        name = n;
    }


    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object aPerson) {
        if(aPerson == null) return false;
        if(!(aPerson instanceof Person)) return false;
        System.out.println("==============TEST IF PASSES HERE=============");
        Person p = (Person)aPerson;
        return this.name.equals(p.name);
    }
}

