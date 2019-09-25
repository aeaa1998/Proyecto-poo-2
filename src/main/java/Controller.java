import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.*;

public class Controller {
    View view;

    Connection myConnection;
    ArrayList <String> menu = new ArrayList<String>(Arrays.asList("Jornadas", "Estudiantes", "Pacientes", "Salir"));
    ArrayList <String> appointmentsMenu = new ArrayList<String>(
            Arrays.asList("Solicitar jornada", "Ver jornadas de un estudiante", "Ver jornadas de un paciente", "Regresar"
            ));
    ArrayList <String> studentsMenu = new ArrayList<String>(
            Arrays.asList("Ver todos los estudiantes", "Agregar estudiante", "Ver estudiante por tipo", "Agregar estudiantes","Regresar"));
    ArrayList <String> patientsMenu = new ArrayList<String>(Arrays.asList("Ver todos los pacientes", "Agregar paciente","Regresar"));
    Manager manager;
    boolean appointmentBool= false;
    boolean studentsBool= false;
    boolean patientsBool= false;
    boolean running= true;
    public Controller() {
        this.view = new View();
        this.myConnection = new Connection();
        this.manager = new Manager(myConnection);
    }

    public String printMenu(){
        return this.view.selectOptions(this.menu);
    }


    public String printPatientsMenu(){
        return this.view.selectOptions(this.patientsMenu);
    }
	
	public Menu(){
		
		boolean active = true;
		while(active){
			String  m = this.printMenu();
			switch(m)){
				case "Jornadas":
					String j = this.view.selectOptions(this.appointmentsMenu);
					boolean case1 = true;
					while(case1){
						switch(j){
							case "Solicitar jornada":
								//code
							break;
							case "Ver jornadas de un estudiante":
								//code
							break;
							case "Ver jornadas de un paciente":
								//code
							break;
							case "Regresar":
								case1 = false;
							break;
							default:
								view.print("Ingrese una opcion valida.");
						}
					}
				break;
				case "Estudiantes":
					//Code 
				break;
				case "Pacientes":
					//Code 
				break;
				case "Salir":
					view.print("Gracias por utilizar este prototipo.");
					active = false;
				break;
				default: 
					view.print("Ingrese una opcion valida.");
			}
		}
	}

    public String printStudentMenu(){
        return this.view.selectOptions(this.studentsMenu);
    }



}
