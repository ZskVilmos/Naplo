package napló;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:NoteDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    
    public DB(){
        
       //Megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);

            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println(""+ex);
        }
        
        
        if(conn != null) {
            try {
                createStatement = conn.createStatement();
                System.out.println("A teherautó létrejött");
            } catch (SQLException ex) {
                System.out.println("Valami baj van a createStatement(teherautó) létrehozásakor");
                System.out.println(""+ex);
            }
        }
        
        
        try {
            // megnézzük hogy üres e az adatbázis?
            dbmd = conn.getMetaData();
            System.out.println("A metaData létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData(adatbázis leírása) létrehozásakor");
            System.out.println(""+ex);
        }
        
        try {
            ResultSet rs1 = dbmd.getTables(null,"APP","USERS",null);
            ResultSet rs2 = dbmd.getTables(null,"APP","LOGENTRY",null);
            
            if(!rs1.next()){
//              A users-ben tároljuk a felhaszáló nevét, kódját, és az id-t.
                createStatement.execute("create table users(userID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(10), password varchar(20))");
                System.out.println("mu");
            }
            if(!rs2.next()){
//              A logEntry-ben tároljuk a naplóbejegyzés id-ját, címét, a szövegét, dátumát, és egy külső kulcsot
                createStatement.execute("create table logEntry(logID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),logTitle varchar(20), logText varchar(10000), datum date,userID int references users(userID))");
                System.out.println("haha");
            }
            
            System.out.println("Az adattáblák létrejöttek");
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor");
            System.out.println(""+ex);
        }
    }
    
    public ArrayList<Users> getAllUsers(){
        String sql = "Select * from contacts";
        ArrayList<Users> usersArray = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            usersArray = new ArrayList<>();
            
            while(rs.next()) {
                Users actualUser = new Users(rs.getString("name"),rs.getString("password"));
                usersArray.add(actualUser);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a felhasználók kiolvasásakor");
            System.out.println(""+ex);
        }
        return usersArray;
    }
    
    public void addUser(Users user) {
        try {
            String sqlAdd = "insert into logEntry (name, password) values(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlAdd);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.execute();
            //A users-ben tároljuk a felhaszáló nevét, kódját, és az id-t.
        } catch (SQLException ex) {
            System.out.println("Valami baj van a felhasználó létrehozásakor");
            System.out.println(""+ex);
        }
    }
    
    public void entryUser(Users user) {
        
        
        
        try {
            String sqlAdd = "insert into logEntry (name, password) values(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlAdd);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.execute();
            //A users-ben tároljuk a felhaszáló nevét, kódját, és az id-t.
        } catch (SQLException ex) {
            System.out.println("Valami baj van a felhasználó létrehozásakor");
            System.out.println(""+ex);
        }
    }
    
    
    
}
