package labsolns.prob1Compo;

public class PersonWithJob {
    private Person person;
    private double salary;

    PersonWithJob(String name, double salary) {
        person = new Person(name); // Composition: PersonWithJob contains a Person object
        this.salary = salary;
    }

    public String getName() {
        return person.getName();
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof PersonWithJob)) return false;
        PersonWithJob other = (PersonWithJob) obj;
        return person.equals(other.person) && this.salary == other.salary;
    }
}

