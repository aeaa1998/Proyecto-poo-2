/*
Martín España 19258
Javier Estuardo Hernandez 19202
Angel Cuellar 18382    
Augusto Alonso 181085
Arturo Armendáriz 18021
*/


public class Models {
    public void update(Connection connection){
        connection.getSession().update(this);
    };
    public void save(Connection connection){
        connection.getSession().update(this);
    };
}

