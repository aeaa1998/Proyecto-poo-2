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

    }
}
