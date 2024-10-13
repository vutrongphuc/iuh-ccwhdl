
import java.util.ArrayList;
import java.util.List;

public class Management implements Functional {

    private List<Person> list;

    public Management() {
        this.list = new ArrayList<Person>();
    }

    public void addPerson(Person p) {
        this.list.add(p);
    }

    @Override
    public void printList() {
        Person p;
        Student s;
        for (int i = 0; i < this.list.size(); i++) {
            p = this.list.get(i);
            if (p instanceof Student) {
                s = (Student) p;
                System.out.println("Student: " + s.getFirstName() + " " + s.getLastName() + " " + (s.getGender() ? "Male" : "Female"));
            }
        }
    }

    @Override
    public void deletePerson(String id) {
        System.err.println("Deleting person with id: " + id);
    }

}
