import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="appointment_types")
public class AppointmentType {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    public AppointmentType() {
    }

    public AppointmentType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AppointmentType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
