package napló;

import javafx.beans.property.SimpleStringProperty;

public class Users {
    private final SimpleStringProperty name;
    private final SimpleStringProperty password;
    private final SimpleStringProperty id;
    
    public Users() {
        this.name = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
    }

    public Users(String name, String password) {
        this.name = new SimpleStringProperty(name);
        this.password = new SimpleStringProperty(password);
        this.id = new SimpleStringProperty("");
    }

    public String getName() {
        return name.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getId() {
        return id.get();
    }
  
    public void setName(String newName) {
        name.set(newName);
    }

    public void setPassword(String newPassword) {
        password.set(newPassword);
    }
    public void setId(String fid) {
        id.set(fid);
    }
}
