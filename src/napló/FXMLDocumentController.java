/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package napló;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import static javax.management.Query.value;


/**
 *
 * @author Gyozo
 */
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
    
//</editor-fold>
    
    

//</editor-fold>
    
    
    private final ObservableList<LogEntry> LogData = 
            FXCollections.observableArrayList();
    
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
        
    }
    
    // ezzel váltunk vissza a main pane-re
    @FXML
    private void handleRegPaneCancelButton(ActionEvent event) {
        regPane.setVisible(false);
        StartPane.setVisible(true);
    }
    
    // ezzel váltunk a regisztráció pane-re
    @FXML
    private void handleLogEntryButton(ActionEvent event) {
        mainPane.setVisible(true);
        logPane.setVisible(false);
        Stage stage = (Stage) logEntryButton.getScene().getWindow();
        stage.setResizable(true);
        stage.setMaximized(true);
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
        stage.setResizable(false);
        stage.setMaximized(false);
    }
    // ezzekel váltjuk a háttér színét
    @FXML
    private void handleMainColorLightPinkMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #F9D5F2;");
        mainPane.setStyle("-fx-background-color : #F9D5F2;");
        
    }
    @FXML
    private void handleMainColorLightGreenMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #C1FDD9;");
        mainPane.setStyle("-fx-background-color : #C1FDD9;");
    }
    @FXML
    private void handleMainColorLightRedMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #FFB9BB;");
        mainPane.setStyle("-fx-background-color : #FFB9BB;");
    }
    @FXML
    private void handleMainColorPinkMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #F792E3;");
        mainPane.setStyle("-fx-background-color : #F792E3;");
    }
    @FXML
    private void handleMainColorGreenMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #55FA97;");
        mainPane.setStyle("-fx-background-color : #55FA97;");
    }
    @FXML
    private void handleMainColorRedMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #FA6367;");
        mainPane.setStyle("-fx-background-color : #FA6367;");
    }
    @FXML
    private void handleMainColorDefaultMenuButton(ActionEvent event) {
        mainPaneMenuBar.setStyle("-fx-background-color : #E2EEF0;");
        mainPane.setStyle("-fx-background-color : #E2EEF0;");
    }
    // ezzel aktiváljuk a segítséget
    @FXML
    private void handleMainHelpMenuButton(ActionEvent event) {
        logPane.setVisible(false);
        StartPane.setVisible(true);
    }
    
    public void setTableData(){
        /* A tábla megjelenítésnél használjuk */
        TableColumn TitleCol = new TableColumn("Cím"); // table column létrehozása
        TitleCol.setMinWidth(80); // sose legyen kissebb 100 pixelnél
        TitleCol.setCellFactory(TextFieldTableCell.forTableColumn()); // beálítjuk, hogy minden cellának text field legyen a tartalma
        TitleCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("title")); /*  setCellValueFactory(ezzel állítjuk be az értékét), 
        new PropertyValueFactory (megmondjuk h melyik pojoból szedje ki, és h mit szedjen ki) */
        
        TitleCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LogEntry, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<LogEntry, String> t) {
                ((LogEntry) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                ).setTitle(t.getNewValue());
            }
        });

        TableColumn DateCol = new TableColumn("Dátum");
        DateCol.setMinWidth(120);
        DateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        DateCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("date"));
  
        mainListView.getColumns().addAll(TitleCol,DateCol); // itt adjuk hozzá a tábla neveket
        mainListView.setItems(LogData); // itt adjuk hozzá az adatokat
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setTableData();
        
    }    
    
}
