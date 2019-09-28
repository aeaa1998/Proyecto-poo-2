import org.hibernate.Query;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="students")
public class Student implements Models {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "uniresity_id")
    private int universityId;

    @Column(name = "student_type_id")
    private int studentTypeId;

    @Transient
    private StudentType studentType;

    public Student() {
    }

    public Student(String firstName, String lastName, String phone, int universityId, int studentTypeId) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.universityId = universityId;
        this.studentTypeId = studentTypeId;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    public int getStudentTypeId() {
        return studentTypeId;
    }

    public void setStudentTypeId(int studentTypeId) {
        this.studentTypeId = studentTypeId;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", universityId=" + universityId +
                ", studentTypeId=" + studentTypeId +
                '}';
    }

    public void setStudentType() {
        Connection connection = new Connection();
        List list = connection.getById("StudentType", this.studentTypeId);
        this.studentType = (StudentType) list.get(0);
    }

    @Override
    public void update(Connection connection) {
        connection.getSession().update(this);
    }

    @Override
    public void save(Connection connection) {
        connection.getSession().save(this);
    }
}
