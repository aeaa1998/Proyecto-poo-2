import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="appointment_medicines")
public class AppointmentMedicine extends Models {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="medicine_id")
    private int medicineId;
    @Column(name="appointment_id")
    private int appointmentId;

    public AppointmentMedicine() {
    }

    public AppointmentMedicine(int medicineId, int appointmentId) {
        this.medicineId = medicineId;
        this.appointmentId = appointmentId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
}
