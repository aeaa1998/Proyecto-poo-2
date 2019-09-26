import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.lang.reflect.Type;
import java.util.*;
public class Connection {

    private SessionFactory classFactories;
    private Session session;


    public Connection() {
        this.classFactories = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Appointment.class)
                .addAnnotatedClass(AppointmentType.class)
                .addAnnotatedClass(Patient.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(University.class)
                .addAnnotatedClass(StudentType.class)
                .buildSessionFactory();

    }

    public List getById(String className, int id) {
        this.openSession();

        Transaction tx = this.session.beginTransaction();

        Query query;
        query = this.session.createQuery("from " + className + " where id = :id");

        query.setParameter("id", id);
        List list = query.list();
        tx.commit();
        this.session.close();

        return list;
    }

    public void openSession(){
        this.session = this.classFactories.openSession();

    }

    public List getAll(String className)
    {
        this.openSession();
        Transaction tx = session.beginTransaction();

        Query query;
        query = this.session.createQuery("from " + className);
        List l = query.list();
        tx.commit();

        this.session.close();
        return l;
    }
    public List simpleWhere(String className, String column, String value) {
        this.openSession();

        Transaction tx = session.beginTransaction();

        Query query;
        query = this.session.createQuery("from " + className + " where "+column+" = :"+column+"");
        query.setParameter(column, value);

        List l = query.list();
        tx.commit();

        session.close();
        return l;
    }

    public List simpleWhere(String className, String column, int value) {
        this.openSession();

        Transaction tx = session.beginTransaction();

        Query query;
        query = this.session.createQuery("from " + className + " where "+column+" = :"+column+"");
        query.setParameter(column, value);

        List l = query.list();
        tx.commit();

        this.session.close();
        return l;
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
    public void closeSession() {
        this.classFactories.close();

        this.classFactories = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Appointment.class)
                .addAnnotatedClass(AppointmentType.class)
                .addAnnotatedClass(Patient.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(University.class)
                .addAnnotatedClass(StudentType.class)
                .buildSessionFactory();
    }
}
