import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.sk.PrettyTable;


import java.util.*;
import java.lang.*;
import java.text.*;

public class Manager {
    View view;
    BankAccount bankAccount;
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
        this.bankAccount = new BankAccount(this.connection);
        List studentTypeList = this.connection.getAll("StudentType");
        List universitiesList = this.connection.getAll("University");
        List aList = this.connection.getAll("AppointmentType");
        this.getAllStudents();
        for (int i = 0; i < aList.size(); i++) {
            AppointmentType appointmentType = (AppointmentType) aList.get(i);
            appointmentTypes.put(appointmentType.getId(), appointmentType.getName());
        }

        for (int i = 0; i < studentTypeList.size(); i++) {
            StudentType studentType = (StudentType) studentTypeList.get(i);
            this.studentTypes.put(studentType.getId(), studentType.getName());
        }


        for (int i = 0; i < universitiesList.size(); i++) {
            University university = (University) universitiesList.get(i);
            this.univerities.put(university.getId(), university.getName());
        }
        if (this.students.isEmpty()){
            int numberOfStudents = this.view.intPositiveInput("Ingrese el numero de estudiantes", "Ingrese un numero valido");
            this.addStudents(numberOfStudents);
        }
        this.getAppointment();
        this.getAllPatients();
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
        Transaction tx = null;
        Patient patient = null;
        try {
            this.connection.openSession();

            tx = this.connection.getSession().beginTransaction();
            patient = new Patient(firstName, lastName, email.toLowerCase());
            this.connection.getSession().save(patient);

            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
//            e.printStackTrace();
        } finally {
            this.connection.closeSession();

        }
//        this.connection.getSession().save(patient);
        this.getAllPatients();

    }

    public void addStudent(){

        this.connection.openSession();
        Transaction tx = this.connection.getSession().beginTransaction();
        String firstName = this.view.input("Ingrese el primer nombre:");
        String lastName = this.view.input("Ingrese el apellido:");
        int universityId = this.view.selectKey(univerities);
        String phone = this.view.input("Ingrese el numero de telefono:");
        int type_id = this.view.selectKey(studentTypes);
        Student student = new Student(firstName, lastName, phone, universityId, type_id);
        this.connection.getSession().save(student);
        tx.commit();
        this.connection.closeSession();
        this.getAllStudents();

    }

    public void addStudents(int number){

        for (int i = 0; i < number; i++) {
            this.connection.openSession();
            Transaction tx = this.connection.getSession().beginTransaction();
            String firstName = this.view.input("Ingrese el primer nombre:");
            String lastName = this.view.input("Ingrese el apellido:");
            int universityId = this.view.selectKey(univerities);
            String phone = this.view.input("Ingrese el numero de telefono:");
            int type_id = this.view.selectKey(studentTypes);
            Student student = new Student(firstName, lastName, phone, universityId, type_id);
            this.connection.getSession().save(student);
            tx.commit();
            this.connection.closeSession();
        }
        this.getAllStudents();

    }

    public void getAllStudents(){
        this.studentsNames.clear();
        this.students.clear();
        List students = this.connection.getAll("Student");

        for (int i = 0; i < students.size(); i++) {
            Student student = (Student) students.get(i);

            this.studentsNames.put(student.getId(), student.getFullName());
            this.students.put(student.getId(), student);
        }
    }

    public void getAppointment(){
        this.appointments.clear();
        this.appointmentStrings.clear();
        List appointments = this.connection.getAll("Appointment");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = (Appointment) appointments.get(i);
            this.connection.openSession();

            appointment.setData(this.connection);
            this.connection.openSession();
            this.appointmentStrings.put(appointment.getId(), appointment.getDescrpition(this.connection));
            this.connection.closeSession();
            this.appointments.put(appointment.getId(), appointment);
        }
    }

    public void getAllPatients(){
        this.patients.clear();
        this.patientStrings.clear();
        List patients = this.connection.getAll("Patient");
        for (int i = 0; i < patients.size(); i++) {
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
        return view.selectKey(this.patientStrings);
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

        PrettyTable table = new PrettyTable("Numero de estudiante", "Primer nombre", "Apellido", "Numero", "Universidad");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            table.addRow(String.valueOf(i+1), s.getFirstName(), s.getLastName(), s.getPhone(), this.univerities.get(s.getUniversityId()));
        }
        view.print(table);

    }
    public void printAllStudents(){
        ArrayList<Student> all = new ArrayList<Student>();
        this.students.forEach((key, student) -> all.add(student));
        this.printStudents(all);
    }
    public void printAllPatients(){
        ArrayList<Patient> all = new ArrayList<Patient>();
        this.patients.forEach((key, patient) -> all.add(patient));
        PrettyTable table = new PrettyTable("Numero de paciente", "Primer nombre", "Apellido", "email");

        for (int i = 0; i < all.size(); i++) {
            Patient p = all.get(i);
            table.addRow(String.valueOf(i+1), p.getFirstName(), p.getLastName(), p.getEmail());
        }
        view.print(table);

    }
    public void addAppointment(){
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
                this.getAllPatients();
                patientIndex = this.getPatientIndex();
            }else{
                String d =  view.input("Ingrese 'si' si desea agregar un nuevo paciente o asignar un paciente.\nSi no desea puede ingresar cualquier otra cosa");
                if (d.equalsIgnoreCase("si")){
                    this.addPatient();
                }
                this.getAllPatients();
                view.print("Seleccione el paciente al que le quiere asignar");
                patientIndex = this.getPatientIndex();
            }

            String observations = this.view.input("Ingrese las observaciones de la cita:");
            Appointment appointment = new Appointment(observations, reason, patientIndex, studentIndex, appointmentType);

            this.connection.openSession();
            Transaction tx = this.connection.getSession().beginTransaction();
            this.connection.getSession().save(appointment);
            tx.commit();
            this.connection.getSession().close();
            view.print("Cita tipo " + this.appointmentTypes.get(appointmentType) + " ingresada con exito");
            this.getAppointment();
        }
    }

    public void showAppointments(String who){
        if ((who.equals("student") && this.students.isEmpty()) || (who.equals("patient") && this.patients.isEmpty())){
            String s = (who.equals("student")) ? "estudiantes" : "pacientes";
            this.view.print("No hay " +s);
        }else{
            int index = (who.equals("student")) ? this.getStudentIndex() : this.getPatientIndex();
            List list = this.connection.simpleWhere("Appointment", who+"_id", index);
            if (list.isEmpty()){
                view.print("No hay citas.");
            }else{
                this.connection.openSession();
                PrettyTable table = new PrettyTable("Tipo de cita", "Nombre de paciente", "Nombre de estudiante", "Razon", "Observaciones");

                list.forEach(appointment -> {
                    ((Appointment)appointment).getDescrpition(this.connection);
                    Appointment a = ((Appointment)appointment);
                    Patient p = this.patients.get(a.getPatientId());
                    Student s = this.students.get(a.getStudentId());
                    table.addRow(this.appointmentTypes.get(a.getAppointmentTypeId()), p.getFirstName() + " " + p.getLastName(), s.getFullName(), a.getReason(), a.getObervations());
                });
                this.view.print(table);
                this.connection.closeSession();
            }
        }


        
    }

    public void showAccount(){
        this.bankAccount.printBalance(this.connection);
    }
    public void donate(){
        BankRecord bankRecord = new BankRecord();
        bankRecord.setAmount(this.view.doubleInput("Ingrese la cantidad por donar:", "Ingrese una valor valido", 0.1));
        bankRecord.setRecordTypeId(1);
        bankRecord.save(this.connection);
        this.bankAccount.update(this.connection);
    }
}
