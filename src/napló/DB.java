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
import javafx.beans.property.SimpleStringProperty;

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
                createStatement.execute("create table users(userID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(20), password varchar(20))");
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
    
    /** A user létrehozása, adni kell neki felhaszáló nevet és jelszót */
    public void addUser(Users user) {
        try {
            String sqlAdd = "insert into users (name, password) values(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlAdd);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            System.out.println("Valami baj van a felhasználó létrehozásakor");
            System.out.println(""+ex);
        }
    }
    
    public Boolean checkUser(String name) {
        String sqlCheck = "SELECT name FROM users";
        String names;
        int i = 0;
        try {
            ResultSet rs = createStatement.executeQuery(sqlCheck);
            
            while(rs.next()) {
                i++;
                System.out.println(i);
                names = rs.getString("name");
                    if(names.equals(name)){
                        System.out.println(rs.next());
                        return false;
                    }
                }
                System.out.println("he?");
                System.out.println(rs.next());
                return true; // ha igaz, akkor jó a felhasználó név, és fellehet tölteni a controllban a felhasználót (lehetett volna false is, de igazából mind1)
            
            
        } catch (SQLException ex) {
            System.out.println("Valami baj van a felhasználó lecsekkolásakor");
            System.out.println(""+ex);
        }
        return null;
    }
    
    /** A user beléptetése, bekell írni a felhaszáló nevet és a jelszavát */
    public Users entryUser(String name, String password) {
        Users newUser = null;
        String sqlAdd = "SELECT name, password, userID FROM users WHERE name ='" + name +"'";// ????????????????????????
        try {
            ResultSet rs = createStatement.executeQuery(sqlAdd);
            rs.next();
            if(name.equals(rs.getString("name")) && password.equals(rs.getString("password"))){
                newUser = new Users(rs.getString("name"),rs.getString("password"),rs.getString("userID"));
            } else {
                System.out.println("Nem jó a felhasználó név, vagy a jelszó");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a felhasználó beolvasásakor");
            System.out.println(""+ex);
        }
        return newUser;
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
    
    /** létrehozza az aktuális naplót */
    
    public void addLogEntry(LogEntry logEntry){

      try {
        String sql = "insert into logEntry (logTitle, logText, datum, userID) values (?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, logEntry.getTitle());
        preparedStatement.setString(2, logEntry.getText());
        preparedStatement.setString(3, logEntry.getDate());
        preparedStatement.setString(4, logEntry.getUserID());
        preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a naplób ejegyzés hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
    // ???????????????? \/
    /** visszaadja az összes napló bejegyzést, az aktuális user id-t hozzákell adni*/
    public ArrayList<LogEntry> getAllLogEntry(String actualUserID){ // nem tuti h String az jó
        String sql = "Select * from logEntry WHERE logEntry.userID =" + Integer.parseInt(actualUserID);
        ArrayList<LogEntry> LogEntryArray = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            LogEntryArray = new ArrayList<>();
            
            while(rs.next()) {
                LogEntry actualLogEntry = new LogEntry(rs.getString("title"),rs.getString("text"),rs.getString("date"),rs.getString("logID"),rs.getString("userID"));
                LogEntryArray.add(actualLogEntry);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a napló bejegyzések kiolvasásakor");
            System.out.println(""+ex);
        }
        return LogEntryArray;
    }
    
    public void showAllLogEntry(){
        String sql = "select* from logEntry";
        try {
            ResultSet rs = createStatement.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
