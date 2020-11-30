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
//    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
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
            }
            if(!rs2.next()){
//              A logEntry-ben tároljuk a naplóbejegyzés id-ját, címét, a szövegét, dátumát, és egy külső kulcsot
                createStatement.execute("create table logEntry(logID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),logTitle varchar(20), logText varchar(10000), datum varchar(20),userID int references users(userID))");
            }
            
            System.out.println("Az adattáblák létrejöttek");
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor");
            System.out.println(""+ex);
        }
    }
    /** visszaadja az összes napló bejegyzést, az aktuális user id-t hozzákell adni*/
    public ArrayList<LogEntry> getAllLogEntry(Users user){
        String sql = "Select * from logEntry WHERE users." + user.getId() + "= logEntry.userID";
        ArrayList<LogEntry> LogEntryArray = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            LogEntryArray = new ArrayList<>();
            
            while(rs.next()) {
                LogEntry actualLogEntry = new LogEntry(rs.getString("title"),rs.getString("text"),rs.getString("date"));
                LogEntryArray.add(actualLogEntry);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a napló bejegyzések kiolvasásakor");
            System.out.println(""+ex);
        }
        return LogEntryArray;
    }
    
    /** A user létrehozása, adni kell neki felhaszáló nevet és jelszót */
    public void addUser(Users user) {
        try {
            String sqlAdd = "insert into logEntry (name, password) values(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlAdd);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            System.out.println("Valami baj van a felhasználó létrehozásakor");
            System.out.println(""+ex);
        }
    }
    
    /** A user beléptetése, bekell írni a felhaszáló nevet és a jelszavát */
    public void entryUser(Users user) {
        
        try {
            
            String sqlAdd = "SELECT * FROM users";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlAdd);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getId());
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            System.out.println("Valami baj van a felhasználó létrehozásakor");
            System.out.println(""+ex);
        }
    }
    
    /** kitörli a userst, és az összes LogEntry-jét*/
//    public void DeleteUser(Users user) {
//        
//        try {
//            String sqlAdd = "DELETE FROM users WHERE users.userID ==" + user.getId() + "";
//            PreparedStatement preparedStatement = conn.prepareStatement(sqlAdd);
//            preparedStatement.setString(1, user.getName());
//            preparedStatement.setString(1, user.getPassword());
//            preparedStatement.execute();
//            //A users-ben tároljuk a felhaszáló nevét, kódját, és az id-t.
//        } catch (SQLException ex) {
//            System.out.println("Valami baj van a felhasználó Törlésekor");
//            System.out.println(""+ex);
//        }
//    }
    
    
}
