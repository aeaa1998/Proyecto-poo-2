import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="record_types")
public class RecordType extends Models {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public RecordType() {
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
}
