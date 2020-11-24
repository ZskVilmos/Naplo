package napló;

import java.sql.Date;
import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;

public class LogEntry {
    private final SimpleStringProperty title;
    private final SimpleStringProperty text;

    public LogEntry() {
        this.title = new SimpleStringProperty("");
        this.text = new SimpleStringProperty("");
    }
    
    
    public LogEntry(SimpleStringProperty title, SimpleStringProperty text) {
        this.title = title;
        this.text = text;
    }

    public SimpleStringProperty getTitle() {
        return title;
    }

    public SimpleStringProperty getText() {
        return text;
    }
    
    public void setTitle(String newTitle) {
        title.set(newTitle);
    }
    
    public void setText(String newText) {
        text.set(newText);
    }
    
    public static String getDate(){
        String datum;
        
        Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            return datum = year + "." + month + "." + day + ". " + hour + ":" + minute;
    }
    
    
}
