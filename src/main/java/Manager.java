import java.util.*;
import java.lang.*;
import java.text.*;

public class Manager {
    View view;
    Map<Integer, String> studentTypes = new HashMap<Integer, String>();
    Map<Integer, String> univerities = new HashMap<Integer, String>();
    Map<Integer, String> studentsNames = new HashMap<Integer, String>();
    Map<Integer, Student> students = new HashMap<Integer, Student>();
    Map<Integer, Appointment> appointments = new HashMap<Integer, Appointment>();
    Map<Integer, String> appointmentStrings= new HashMap<Integer, String>();
    Map<Integer, String> patientStrings = new HashMap<Integer, String>();
    Map<Integer, Patient> patients = new HashMap<Integer, Patient>();
    Map<Integer, String> appointmentTypes = new HashMap<Integer, String>();



    Connection connection;

    public Manager (Connection connection){
        view = new View();
        this.connection = connection;
        List studentTypeList = this.connection.getAll("StudentType");
        List universitiesList = this.connection.getAll("University");
        List aList = this.connection.getAll("AppointmentType");
        this.getAllStudents();
        for (int i = 1; i <= aList.size(); i++) {
            AppointmentType appointmentType = (AppointmentType) aList.get(i);
            appointmentTypes.put(appointmentType.getId(), appointmentType.getName());
        }

        for (int i = 1; i <= studentTypeList.size(); i++) {
            StudentType studentType = (StudentType) studentTypeList.get(i);
            this.studentTypes.put(studentType.getId(), studentType.getName());
        }


        for (int i = 1; i <= universitiesList.size(); i++) {
            University university = (University) universitiesList.get(i);
            this.univerities.put(university.getId(), university.getName());
        }
        if (this.students.isEmpty()){
            int numberOfStudents = this.view.intPositiveInput("Ingrese el numero de estudiantes", "Ingrese un numero valido");
            this.addStudents(numberOfStudents);
        }
        this.getAppointment();
    }
    public void addPatient(){
        String firstName = this.view.input("Ingrese el primer nombre:");
        String lastName = this.view.input("Ingrese el apellido:");
        String email = "";
        boolean bool = true;
        while (bool){
            email = this.view.input("Ingrese el email:");
            List result = this.connection.simpleWhere("Patient", "email", email.toLowerCase());
            if(result.isEmpty()){
                bool = false;
            }else{
                view.print("Ese correo ya ha sido tomado.");
            }
        }

        Patient patient = new Patient(firstName, lastName, email.toLowerCase());
        patient.save();
//        this.connection.getSession().save(patient);
        this.getAllPatients();

    }

    public void addStudent(){
        String firstName = this.view.input("Ingrese el primer nombre:");
        String lastName = this.view.input("Ingrese el apellido:");
        int universityId = this.view.selectKey(univerities);
        String phone = this.view.input("Ingrese el numero de telefono:");
        int type_id = this.view.selectKey(studentTypes);
        Student student = new Student(firstName, lastName, phone, universityId, type_id);
        this.connection.getSession().save(student);
        this.getAllStudents();

    }

    public void addStudents(int number){
        for (int i = 0; i < number; i++) {
            String firstName = this.view.input("Ingrese el primer nombre:");
            String lastName = this.view.input("Ingrese el apellido:");
            int universityId = this.view.selectKey(univerities);
            String phone = this.view.input("Ingrese el numero de telefono:");
            int type_id = this.view.selectKey(studentTypes);
            Student student = new Student(firstName, lastName, phone, universityId, type_id);
            this.connection.getSession().save(student);
        }
        this.getAllStudents();

    }

    public void getAllStudents(){
        this.studentsNames.clear();
        this.students.clear();
        List students = this.connection.getAll("Student");
        for (int i = 1; i <= students.size(); i++) {
            Student student = (Student) students.get(i);

            this.studentsNames.put(student.getId(), student.getFullName());
            this.students.put(student.getId(), student);
        }
    }

    public void getAppointment(){
        this.appointments.clear();
        this.appointmentStrings.clear();
        List appointments = this.connection.getAll("Appointment");
        for (int i = 1; i <= appointments.size(); i++) {
            Appointment appointment = (Appointment) appointments.get(i);
            appointment.setData();
            this.appointmentStrings.put(appointment.getId(), appointment.getDescrpition());
            this.appointments.put(appointment.getId(), appointment);
        }
    }

    public void getAllPatients(){
        this.patients.clear();
        this.patientStrings.clear();
        List patients = this.connection.getAll("Patient");
        for (int i = 1; i <= patients.size(); i++) {
            Patient patient = (Patient) patients.get(i);
            this.patientStrings.put(patient.getId(), patient.getFirstName() + " " + patient.getLastName());
            this.patients.put(patient.getId(), patient);
        }
    }


    public int getStudentIndex(){
        return view.selectKey(studentsNames);
    }

    public int getAppointmentIndex(){
        return view.selectKey(appointmentStrings);
    }
    public int getPatientIndex(){
        return view.selectKey(patientStrings);
    }
    public int getUniversityIndex(){
        return view.selectKey(univerities);
    }
    public void filterStudents(){
        int studentTypeId = view.selectKey(studentTypes);
        List list = this.connection.simpleWhere("Student", "student_type_id", studentTypeId);
        ArrayList<Student> studentsFiltered = new ArrayList<Student>();
        list.forEach(student -> studentsFiltered.add((Student)student));
        this.printStudents(studentsFiltered);

    }
    public void printStudents(ArrayList<Student> students){
        for (int i = 0; i < students.size(); i++) {
            view.print((i+1) + ") " + students.get(i).getFullName());
        }
    }
    public void addAppointment(String type){
//        this.getAllStudents();
//        this.getAllStudents();
        if (this.students.isEmpty()){
            view.print("No hay estudiantes para hacer la cita");
        }else{
            String reason = this.view.input("Ingrese la razon de la cita:");
            int appointmentType = this.view.selectKey(this.appointmentTypes);
            view.print("Seleccione el estudiante al que le quiere asignar");
            int studentIndex = this.getStudentIndex();
            int patientIndex = 0;
            if (this.patients.isEmpty()){
                view.print("No tiene pacientes tiene que agregar uno.");
                this.addPatient();
                patientIndex = this.getPatientIndex();
            }else{
                String d =  view.input("Ingrese 'si' si desea agregar un nuevo paciente o asignar un paciente.\nSi no desea puede ingresar cualquier otra cosa");
                if (d.equalsIgnoreCase("si")){
                    this.addPatient();
                }
                view.print("Seleccione el paciente al que le quiere asignar");
                patientIndex = this.getPatientIndex();
            }

            String observations = this.view.input("Ingrese las observaciones de la cita:");
            Appointment appointment = new Appointment(observations, reason, patientIndex, studentIndex, appointmentType);
            this.connection.getSession().save(appointment);
            view.print("Cita tipo " + this.appointmentTypes.get(appointmentType) + " ingresada con exito");
            this.getAppointment();
        }
    }

    public void showAppointments(String who){
        int index = (who.equals("student")) ? this.getStudentIndex() : this.getPatientIndex();
        List list = this.connection.simpleWhere("Appointment", who+"_id", index);
        if (list.isEmpty()){
            view.print("No hay citas.");
        }else{
            list.forEach(appointment -> view.print(((Appointment)appointment).getDescrpition()));
        }
        
    }
}
