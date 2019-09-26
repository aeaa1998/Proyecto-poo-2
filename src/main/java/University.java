/*
Martín España 19258
Javier Estuardo Hernandez 19202
Angel Cuellar 18382    
Augusto Alonso 181085
Arturo Armendáriz 18021
*/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="universities")
public class University {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    public University(String name) {
        this.name = name;
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
