package napló;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.Global;

public class Napló extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root, 350, 300);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setTitle("Napló");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        DB db = new DB();
        
        //dátum lekérdezése
        //System.out.println(LogEntry.getDate());
    }
    
}
