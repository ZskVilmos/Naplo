package napló;

import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;

public class LogEntry {

    private final SimpleStringProperty title;
    private final SimpleStringProperty text;
    private final SimpleStringProperty date;
    private final SimpleStringProperty logID;
    private final SimpleStringProperty userID;

    /**
     * alap konstruktor
     */
    public LogEntry() {
        this.title = new SimpleStringProperty("");
        this.text = new SimpleStringProperty("");
        this.date = new SimpleStringProperty(makeDate());
        this.logID = new SimpleStringProperty();
        this.userID = new SimpleStringProperty();
    }

    /**
     * létrehozáskor használt konstruktor
     */
    public LogEntry(String title, String text, Integer userID) {

        this.title = new SimpleStringProperty(title);
        this.text = new SimpleStringProperty(text);
        this.date = new SimpleStringProperty(makeDate());
        this.logID = new SimpleStringProperty();
        this.userID = new SimpleStringProperty(String.valueOf(userID));
    }

    /**
     * már meglévő adatok behívásakor használt konstruktor ( mivel ilyenkor
     * "újat" hozunk létre)
     */
    public LogEntry(String logTitle, String logText, String logDate, Integer logID, Integer userID) {
        this.title = new SimpleStringProperty(logTitle);
        this.text = new SimpleStringProperty(logText);
        this.date = new SimpleStringProperty(logDate);
        this.logID = new SimpleStringProperty(String.valueOf(logID));
        this.userID = new SimpleStringProperty(String.valueOf(userID));
    }

    public String getTitle() {
        return title.get();
    }

    public String getText() {
        return text.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getLogID() {
        return logID.get();
    }

    public String getUserID() {
        return userID.get();
    }

    private String makeDate() {
        String newMinute = "";
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        if (minute < 10) {
            switch (minute) {
                case 0:
                    newMinute = "00";
                    break;
                case 1:
                    newMinute = "01";
                    break;
                case 2:
                    newMinute = "02";
                    break;
                case 3:
                    newMinute = "03";
                    break;
                case 4:
                    newMinute = "04";
                    break;
                case 5:
                    newMinute = "05";
                    break;
                case 6:
                    newMinute = "06";
                    break;
                case 7:
                    newMinute = "07";
                    break;
                case 8:
                    newMinute = "08";
                    break;
                case 9:
                    newMinute = "09";
                    break;

            }
        } else {
            newMinute = String.valueOf(minute);
        }

        return year + "." + month + "." + day + ". " + hour + ":" + newMinute;
    }

    public void setTitle(String newTitle) {
        title.set(newTitle);
    }

    public void setText(String newText) {
        text.set(newText);
    }

}
