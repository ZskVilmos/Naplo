/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package napló;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FXMLDocumentController implements Initializable {
    
    //<editor-fold defaultstate="collapsed" desc="FXML">
    
    @FXML
    private AnchorPane AnchorPane;
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Start">
    @FXML
    private Pane StartPane;
    
    @FXML
    private Button StartLogButton;
    @FXML
    private Button StartRegButton;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Registration">
    @FXML
    private Pane regPane;
    
    @FXML
    private TextField regNameTF;
    @FXML
    private TextField regPasswordTF;
    @FXML
    private TextField regPasswordInspectionTF;
    @FXML
    private Button regRegButton;
    @FXML
    private Button regCancelButton;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Login">
    @FXML
    private Pane logPane;
    @FXML
    private TextField logNameTF;
    @FXML
    private TextField logPasswordTF;
    @FXML
    private Button logEntryButton;
    @FXML
    private Button logCancelButton;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="main">
    @FXML
    private BorderPane mainPane;
    
    @FXML
    private Button MainBackButton;
    
    @FXML
    private Button newLogAddButton;
    
    @FXML
    private MenuItem mainBack;
    @FXML
    private MenuItem mainColorLightPink;
    @FXML
    private MenuItem mainColorLightGreen;
    @FXML
    private MenuItem mainColorLightRed;
    @FXML
    private MenuItem mainColorPink;
    @FXML
    private MenuItem mainColorGreen;
    @FXML
    private MenuItem mainColorRed;
    @FXML
    private MenuItem mainColorDefault;
    
    @FXML
    private Menu mainHelp;
    
    @FXML
    private TableView mainListView;
    
    @FXML
    private MenuBar mainPaneMenuBar;
    
    @FXML
    private TextArea mainTextArea;
    
    @FXML
    private TextField NewLogAddTitleTextField;
    
    @FXML
    private SplitPane mainPaneBottom;
    
//</editor-fold>
    
    

//</editor-fold>
   
    DB db = new DB();
    int actuaID;
    
    private ObservableList<LogEntry> LogData = 
            FXCollections.observableArrayList();
    
    @FXML
    private void addLogEntry(ActionEvent event) {
        LogEntry newLogEntry = new LogEntry(NewLogAddTitleTextField.getText(), mainTextArea.getText(), actuaID);
        LogData.add(newLogEntry);
        db.addLogEntry(newLogEntry,actuaID);
        NewLogAddTitleTextField.clear();
        mainTextArea.clear();
    }
    
    
//<editor-fold defaultstate="collapsed" desc="Buttons">
    // ezzel váltunk a bejelentkezés pane-re
    @FXML
    private void handleStartLogButton(ActionEvent event) {
        logPane.setVisible(true);
        StartPane.setVisible(false);
        
    }
    
    // ezzel váltunk a regisztráció pane-re
    @FXML
    private void handleStartRegButton(ActionEvent event) {
        regPane.setVisible(true);
        StartPane.setVisible(false);
    }
    
    // ezzel regisztráljuk a felhasználót pane-re
    @FXML
    private void handleRegPaneRegButton(ActionEvent event) {
        Users newUser = new Users(regNameTF.getText(),regPasswordTF.getText());
        if(!regNameTF.getText().isEmpty() && !regPasswordTF.getText().isEmpty() && !regPasswordInspectionTF.getText().isEmpty()){
            if((regPasswordTF.getText() != regPasswordInspectionTF.getText()) && (!regPasswordTF.getText().isEmpty())){
                if(db.checkUser(regNameTF.getText())){
                    db.addUser(newUser);
                    System.out.println("létrejött!");
                    regPane.setVisible(false);
                    StartPane.setVisible(true);
                } else {
                    System.out.println("Ez a név már foglalt! másik felhasználó nevet kell megadnod");
                }
            } else {
                System.out.println("a jelszó, és az ellenörző jelszó nem ugyanaz!");
            }
        } else {
            System.out.println("minden mezőt ki kell tölteni!");
        }
    }
    
    // ezzel váltunk vissza a main pane-re
    @FXML
    private void handleRegPaneCancelButton(ActionEvent event) {
        regPane.setVisible(false);
        StartPane.setVisible(true);
    }
    
    // ezzel váltunk a main felületre
    @FXML
    private void handleLogEntryButton(ActionEvent event) { // ???????????????????????????????????????????????????
        Users actualUser = null;
        if(!logNameTF.getText().isEmpty() && !logPasswordTF.getText().isEmpty()){
            actualUser = db.entryUser(logNameTF.getText(), logPasswordTF.getText());
            if(actualUser != null){
                mainPane.setVisible(true);
                logPane.setVisible(false);
                actuaID = Integer.parseInt(actualUser.getId());
//                db.getAllLogEntry(actuaID); // ??????????
                Stage stage = (Stage) logEntryButton.getScene().getWindow();
                stage.setResizable(false);
                stage.setMaximized(true);
                setTableData();
                mainTextArea.clear();
//                LogData.addAll(db.getAllLogEntry(actuaID));
//                mainListView.setItems(LogData); // itt adjuk hozzá az adatokat
            } else {
                System.out.println("nincs ilyen felhasználó");
            }
        } else {
            System.out.println("nem adtál meg felhasználó nevet, vagy jelszót");
        }
        
    }
    
    // ezzel váltunk a regisztráció pane-re
    @FXML
    private void handleLogCancelButton(ActionEvent event) {
        logPane.setVisible(false);
        StartPane.setVisible(true);
    }
    // ezzel váltunk vissza a fő oldalra
    @FXML
    private void handleMainBackButton(ActionEvent event) {
        mainPane.setVisible(false);
        StartPane.setVisible(true);
        Stage stage = (Stage) logEntryButton.getScene().getWindow();
        actuaID = 0;
        
        
        stage.setWidth(350);
        stage.setHeight(300);
        stage.setResizable(false);
        stage.setMaximized(false);
    }
    //<editor-fold defaultstate="collapsed" desc="háttér színek">
// ezzekel váltjuk a háttér színét
    @FXML
    private void handleMainColorLightPinkMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #F9D5F2;");
        mainPane.setStyle("-fx-background-color : #F9D5F2;");
        mainPaneBottom.setStyle("-fx-background-color : #F9D5F2;");
        
    }
    @FXML
    private void handleMainColorLightGreenMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #C1FDD9;");
        mainPane.setStyle("-fx-background-color : #C1FDD9;");
        mainPaneBottom.setStyle("-fx-background-color : #C1FDD9;");
    }
    @FXML
    private void handleMainColorLightRedMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #FFB9BB;");
        mainPane.setStyle("-fx-background-color : #FFB9BB;");
        mainPaneBottom.setStyle("-fx-background-color : #FFB9BB;");
    }
    @FXML
    private void handleMainColorPinkMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #F792E3;");
        mainPane.setStyle("-fx-background-color : #F792E3;");
        mainPaneBottom.setStyle("-fx-background-color : #F792E3;");
    }
    @FXML
    private void handleMainColorGreenMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #55FA97;");
        mainPane.setStyle("-fx-background-color : #55FA97;");
        mainPaneBottom.setStyle("-fx-background-color : #55FA97;");
    }
    @FXML
    private void handleMainColorRedMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #FA6367;");
        mainPane.setStyle("-fx-background-color : #FA6367;");
        mainPaneBottom.setStyle("-fx-background-color : #FA6367;");
    }
    @FXML
    private void handleMainColorDefaultMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #E2EEF0;");
        mainPane.setStyle("-fx-background-color : #E2EEF0;");
        mainPaneBottom.setStyle("-fx-background-color : #E2EEF0;");
    }
//</editor-fold>
    // ezzel aktiváljuk a segítséget
    // !!!!!!!!!!!! \/
    @FXML
    private void handleMainHelpMenuButton(ActionEvent event) { 
//        logPane.setVisible(false);
//        StartPane.setVisible(true);
    }
    // ezzel adjuk hozzá az új naplóbejegyzést
    @FXML
    private void handleNewLogAddButton(ActionEvent event) { // ???????????????????????????????????????????????
//        mainTextArea.setEditable(true);
        String newTitle = NewLogAddTitleTextField.getText();
        String newLog = mainTextArea.getText();
        LogEntry newLogEntry = new LogEntry(newTitle,newLog,actuaID);
        LogData.add(newLogEntry);
        db.addLogEntry(newLogEntry,actuaID);
    }
//</editor-fold>
    
    // ?????????????????? \/
    public void setTableData(){
        /* A tábla megjelenítésnél használjuk */
        TableColumn TitleCol = new TableColumn("Cím"); // table column létrehozása
        TableColumn DateCol = new TableColumn("Dátum");
        LogData.clear();
        mainListView.setItems(LogData);
        
        
        TitleCol.setMinWidth(250); // sose legyen kissebb 100 pixelnél
//        TitleCol.setCellFactory(TextFieldTableCell.forTableColumn()); // beálítjuk, hogy minden cellának text field legyen a tartalma
        TitleCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("title")); 
        /*  setCellValueFactory(ezzel állítjuk be az értékét), 
        new PropertyValueFactory (megmondjuk h melyik pojoból szedje ki, és h mit szedjen ki) */
        
        TitleCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LogEntry, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<LogEntry, String> t) {
                LogEntry actualLogEntry = (LogEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualLogEntry.setTitle(t.getNewValue());
                db.updateLogEntry(actualLogEntry, actuaID);
            }
        });
        

        DateCol = new TableColumn("Dátum");
        DateCol.setMinWidth(143);
        DateCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("date"));
  
        mainListView.getColumns().addAll(TitleCol,DateCol); // itt adjuk hozzá a tábla neveket
        
        LogData.addAll(db.getAllLogEntry(actuaID));
        
        mainListView.setItems(LogData); // itt adjuk hozzá az adatokat
        System.out.println(actuaID);
        
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        
    }    
    
}
