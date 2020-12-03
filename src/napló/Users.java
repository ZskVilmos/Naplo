package napló;

import javafx.beans.property.SimpleStringProperty;

public class Users {
    private final SimpleStringProperty name;
    private final SimpleStringProperty password;
    private final int id;
    
    
    // alap konstruktor biztonsági okokból
    public Users() {
        this.name = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.id = 0;
    }

    
    // létrehozáskor
    public Users(String name, String password) {
        this.name = new SimpleStringProperty(name);
        this.password = new SimpleStringProperty(password);
        this.id =0;
    }
    
    // belépéskor
    public Users(String name, String password, int userID) {
        this.name = new SimpleStringProperty(name);
        this.password = new SimpleStringProperty(password);
        this.id = userID; // ?????????????????????????????????
    }

    public String getName() {
        return name.get();
    }

    public String getPassword() {
        return password.get();
    }

    public int getId() {
        return id;
    }
  
    public void setName(String newName) {
        name.set(newName);
    }

    public void setPassword(String newPassword) {
        password.set(newPassword);
    }
    
}
