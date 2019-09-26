/*
Martín España 19258
Javier Estuardo Hernandez 19202
Angel Cuellar 18382    
Augusto Alonso 181085
Arturo Armendáriz 18021
*/



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.lang.reflect.Type;
import java.util.*;
public class Connection {

    private SessionFactory classFactories;
    private Session session;


    public Connection() {
        classFactories = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Appointment.class)
                .addAnnotatedClass(Patient.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(University.class)
                .addAnnotatedClass(StudentType.class)
                .buildSessionFactory();
    }

    public List getById(String className, int id) {
        Query query;
        query = this.session.createQuery("from " + className + " where id = :id");

        query.setParameter("id", id);
        List list = query.list();
        return list;
    }

    public List getAll(String className) {
        Query query;
        query = this.session.createQuery("from " + className);
        return query.list();
    }
    public List simpleWhere(String className, String column, String value) {
        Query query;
        query = this.session.createQuery("from " + className + " where "+column+" = :"+column+"");
        query.setParameter(column, value);
        return query.list();
    }

    public List simpleWhere(String className, String column, int value) {
        Query query;
        query = this.session.createQuery("from " + className + " where "+column+" = :"+column+"");
        query.setParameter(column, value);

        return query.list();
    }

    public void setClassFactories(SessionFactory classFactories) {
        this.classFactories = classFactories;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public SessionFactory getClassFactories() {
        return classFactories;
    }

    public Session getSession() {
        return session;
    }
}
