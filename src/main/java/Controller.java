import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.*;

public class Controller {
    View view;

    Connection myConnection;
    ArrayList <String> menu = new ArrayList<String>(Arrays.asList("Jornadas", "Estudiantes", "Pacientes", "Donaciones", "Salir"));
    ArrayList <String> appointmentsMenu = new ArrayList<String>(
            Arrays.asList("Solicitar jornada", "Ver jornadas de un estudiante", "Ver jornadas de un paciente", "Regresar"
            ));
    ArrayList <String> studentsMenu = new ArrayList<String>(
            Arrays.asList("Ver todos los estudiantes", "Agregar estudiante", "Ver estudiante por tipo", "Agregar estudiantes","Regresar"));
    ArrayList <String> patientsMenu = new ArrayList<String>(Arrays.asList("Ver todos los pacientes", "Agregar paciente","Regresar"));
    ArrayList <String> donationsMenu = new ArrayList<String>(Arrays.asList("Ver cuenta", "Donar","Regresar"));
    Manager manager;
    boolean appointmentBool= false;
    boolean studentsBool= false;
    boolean patientsBool= false;
    boolean donationBool= false;
    boolean running= true;
    public Controller() {


        this.view = new View();
        this.myConnection = new Connection();
        this.manager = new Manager(myConnection);
    }

    public void main(){
        while (this.running){
            String menu = this.printMenu();
            switch (menu){
                case "Jornadas":
                    this.appointmentMenu();
                    break;
                case "Estudiantes":
                    this.studentMenu();
                    break;
                case "Pacientes":
                    this.patientMenu();
                    break;
                case "Donaciones":
                    this.donationsMenu();
                    break;
                case "Salir":
                    this.running = false;
                    break;
            }
        }
        this.myConnection.closeSession();
    }
    public String printMenu(){
        return this.view.selectOptions(this.menu);
    }


    public String printPatientsMenu(){
        return this.view.selectOptions(this.patientsMenu);
    }
    public String printDonationsMenu(){
        return this.view.selectOptions(this.donationsMenu);
    }

    public String printStudentMenu(){
        return this.view.selectOptions(this.studentsMenu);
    }


    public String printAppointmentMenu(){
        return this.view.selectOptions(this.appointmentsMenu);
    }

    public void newAppointment(){
        this.manager.addAppointment();
    }

    public void showAppointments(String who){
        this.manager.showAppointments(who);
    }

    public void appointmentMenu(){
        this.appointmentBool = true;
        while(this.appointmentBool){
            String appointMenu = this.printAppointmentMenu();
            switch (appointMenu){
                case "Solicitar jornada":
                    this.newAppointment();
                    break;
                case "Ver jornadas de un estudiante":
                    this.showAppointments("student");
                    break;
                case "Ver jornadas de un paciente":
                    this.showAppointments("patient");
                    break;
                case "Regresar":
                    this.appointmentBool = false;
                    break;

            }
        }
    }
    public void studentMenu(){
        this.studentsBool = true;
        while(this.studentsBool){
            String menu = this.printStudentMenu();
            switch (menu){
                case "Ver todos los estudiantes":
                    this.manager.printAllStudents();
                    break;
                case "Ver estudiante por tipo":
                    this.manager.filterStudents();
                    break;
                case "Agregar estudiante":
                    this.manager.addStudent();
                    break;
                case "Agregar estudiantes":
                    int i = this.view.intPositiveInput("Ingrese el numero de estudiantes que desea agregar", "Ingrese un valor valido");
                    this.manager.addStudents(i);
                    break;
                case "Regresar":
                    this.studentsBool = false;
                    break;

            }
        }
    }
    public void patientMenu(){
        this.patientsBool = true;
        while(this.patientsBool){
            String menu = this.printPatientsMenu();

            switch (menu){
                case "Ver todos los pacientes":
                    this.manager.printAllPatients();
                    break;
                case "Agregar paciente":
                    this.manager.addPatient();
                    break;
                case "Regresar":
                    this.patientsBool = false;
                    break;

            }
        }
    }
    public void donationsMenu(){
        this.donationBool = true;
        while(this.donationBool){
            String menu = this.printDonationsMenu();

            switch (menu){
                case "Donar":
                    this.manager.donate();
                    break;
                case "Ver cuenta":
                    this.manager.showAccount();
                    break;
                case "Regresar":
                    this.donationBool = false;
                    break;

            }
        }
    }
}
