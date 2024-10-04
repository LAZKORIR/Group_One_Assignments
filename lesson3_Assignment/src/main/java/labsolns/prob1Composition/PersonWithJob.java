package labsolns.prob1Composition;

public class PersonWithJob {
    private Person person;
    private double salary;

    PersonWithJob(String name, double salary) {
        //Composition comes here
        person = new Person(name);//Instantiated the Person instead of inheriting it
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
        System.out.println("==============TEST IF PASSES HERE=============");
        PersonWithJob other = (PersonWithJob) obj;
        return person.equals(other.person) && this.salary == other.salary;
    }
}

