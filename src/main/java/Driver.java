import org.hibernate.*;
import org.hibernate.cfg.*;

import java.util.List;
import java.util.logging.Level;

public class Driver {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
//			Student student = new Student("Augusto", "Alonso", "23322098", 1, 1);
			session.beginTransaction();
//			session.save(student);
//			session.getTransaction().commit();
			Query query = session.createQuery("from Student where id = :id");
			query.setParameter("id", 1);
			List list = query.list();
			Student s = (Student) list.get(0);
			System.out.println(s.getFirstName());
			System.out.println(s.getFirstName());
			System.out.println(s.getFirstName());
			System.out.println(s.getFirstName());
			System.out.println(s.getFirstName());
		}
		catch (Exception e) {
			e.printStackTrace();
			factory.close();
		}
	}

}
