/*
Martín España 19258
Javier Estuardo Hernandez 19202
Angel Cuellar 18382    
Augusto Alonso 181085
Arturo Armendáriz 18021
*/


import org.hibernate.Transaction;

public class Models {
    public void update(Connection connection){
        connection.openSession();
        Transaction tx = connection.getSession().beginTransaction();

        connection.getSession().update(this);
        tx.commit();

        connection.closeSession();

    };
    public void save(Connection connection){
        connection.openSession();
        Transaction tx = connection.getSession().beginTransaction();

        connection.getSession().save(this);
        tx.commit();
        connection.closeSession();
    };
}

