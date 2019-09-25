import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.*;

public class Controller {
    View view;

    Connection myConnection;
    ArrayList <String> menu = new ArrayList<String>(
            Arrays.asList("Solicitar jornada", "Contacto", "Emergencia", "Ver jornadas por voluntario", "Salir"
            ));
    public Controller() {

        myConnection = new Connection();
		view = new View();

    }
	
	public Menu(){
		boolean active = true;
		while(active){
			switch(view.input("Ingrese la opción que desee: \nSolicitar jornada \nContacto \nEmergencia \nVer jornadas por voluntario \nSalir")){
				case "Solicitar jornada":
					//Code
				break;
				case "Contacto":
					//Code 
				break;
				case "Emergencia":
					//Code 
				break;
				case "Ver jornadas por voluntario":
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
}
