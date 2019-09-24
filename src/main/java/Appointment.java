import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="appointments")
public class Appointment implements Models {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="observations")
    private String obervations;


    @Column(name="reason")
    private String reason;


    @Column(name="patients_id")
    private int patientId;

    @Column(name="student_id")
    private int studentId;

    @Column(name="appointment_type_id")
    private int appointmentTypeId;


    private Patient patient;
    private Student student;

    public Appointment(String obervations, String reason, int patientId, int studentId, int appointmentTypeId) {
        this.obervations = obervations;
        this.reason = reason;
        this.patientId = patientId;
        this.studentId = studentId;
        this.appointmentTypeId = appointmentTypeId;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObervations() {
        return obervations;
    }

    public void setObervations(String obervations) {
        this.obervations = obervations;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getAppointmentTypeId() {
        return appointmentTypeId;
    }

    public void setAppointmentTypeId(int appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }

    public String getReason() {
        return reason;
    }

    public String getDescrpition(){
        return this.reason + " Estudiante " + this.student.getFullName() + " - " + this.patient.getFirstName() + this.patient.getLastName();
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", obervations='" + obervations + '\'' +
                ", patientId=" + patientId +
                ", studentId='" + studentId + '\'' +
                ", appointmentTypeId=" + appointmentTypeId +
                '}';
    }

    public void setData(){
        Connection connection = new Connection();

        List list = connection.getById("Student", this.studentId);
        this.student = (Student) list.get(0);

        list = connection.getById("Patient", this.patientId);
        this.patient = (Patient) list.get(0);

    }
    @Override
    public void update() {
        Connection connection = new Connection();
        connection.getSession().update(this);
    }

    @Override
    public void save() {
        Connection connection = new Connection();
        connection.getSession().save(this);
    }
}


