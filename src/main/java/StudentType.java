/*
Martín España 19258
Javier Estuardo Hernandez 19202
Angel Cuellar 18382    
Augusto Alonso 181085
Arturo Armendáriz 18021
*/


import javax.persistence.*;

@Entity
@Table(name="student_types")
public class StudentType extends Models{
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
