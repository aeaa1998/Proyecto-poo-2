import javax.persistence.*;

@Entity
@Table(name="student_types")
public class StudentType {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    public StudentType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StudentType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
