package napló;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.util.Callback;


public class FXMLDocumentController implements Initializable {
    
    //<editor-fold defaultstate="collapsed" desc="FXML">
    
//    @FXML
//    private AnchorPane AnchorPane;
    
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
    private PasswordField regPasswordPF;
    @FXML
    private PasswordField regPasswordInspectionPF;
    @FXML
    private Button regRegButton;
    @FXML
    private Button regCancelButton;
    @FXML
    private CheckBox regCheckBox;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Login">
    @FXML
    private Pane logPane;
    @FXML
    private TextField logNameTF;
    @FXML
    private PasswordField logPasswordPF;
    @FXML
    private TextField logPasswordTF;
    @FXML
    private Button logEntryButton;
    @FXML
    private Button logCancelButton;
    @FXML
    private CheckBox logCheckBox;
    
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="main">
    @FXML
    private BorderPane mainPane;
    
    @FXML
    private Button MainBackButton;
    
    @FXML
    private Button newLogAddButton;
    
    @FXML
    private Button mainTextUpdateButton;
    
    @FXML
    private Button mainHelpButton;
    
    @FXML
    private TableView mainListView;
    
    @FXML
    private TextArea mainTextArea;
    
    @FXML
    private TextField NewLogAddTitleTextField;
    
    @FXML
    private SplitPane mainPaneBottom;
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="helpPane">
    @FXML
    private AnchorPane HelpPane;
    
    @FXML
    private Button MainHelpBackButton;
//</editor-fold>
    

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Osztályváltozók">
    DB db = new DB();
    int actualUserID;
    int actualLogEntryTextId;
    private ObservableList<LogEntry> LogData =
            FXCollections.observableArrayList();
//</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Gombok">
    
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
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Információ");
        Users newUser = new Users(regNameTF.getText(),regPasswordPF.getText());
        if((regNameTF.getText().length() > 0) || (regPasswordPF.getText().length() > 5) || (regPasswordInspectionPF.getText().length() > 5)){
            if((regPasswordPF.getText().length() > 5) && (regPasswordInspectionPF.getText().length() > 5)){
                if(regPasswordPF.getText().equals(regPasswordInspectionPF.getText())){
                    if((regNameTF.getText().length() > 0)){
                        if(db.checkUser(regNameTF.getText())){
                            db.addUser(newUser);
                            regPane.setVisible(false);
                            StartPane.setVisible(true);
                        } else {
                            regNameTF.clear();
                            regPasswordTF.clear();
                            regPasswordInspectionTF.clear();
                            regPasswordPF.clear();
                            regPasswordInspectionPF.clear();
                            alert.setHeaderText("Ez a név már foglalt! másik felhasználó nevet kell megadnod");
                            alert.showAndWait();
                        }
                    } else {
                        regNameTF.clear();
                        regPasswordTF.clear();
                        regPasswordInspectionTF.clear();
                        regPasswordPF.clear();
                        regPasswordInspectionPF.clear();
                        alert.setHeaderText("Nem adtál meg felhasználó nevet!");
                        alert.showAndWait();
                    }
                } else {
                    regNameTF.clear();
                    regPasswordTF.clear();
                    regPasswordInspectionTF.clear();
                    regPasswordPF.clear();
                    regPasswordInspectionPF.clear();
                    alert.setHeaderText("a jelszó, és az ellenörző jelszó nem ugyanaz!");
                    alert.showAndWait();
                }
            }else{
                  regNameTF.clear();
                  regPasswordTF.clear();
                  regPasswordInspectionTF.clear();
                  regPasswordPF.clear();
                regPasswordInspectionPF.clear();
                  alert.setHeaderText("A jelszónak minimum 6 karakterből kell álnia!");
                  alert.showAndWait();  
            }
        } else {
            regNameTF.clear();
            regPasswordTF.clear();
            regPasswordInspectionTF.clear();
            regPasswordPF.clear();
            regPasswordInspectionPF.clear();
            alert.setHeaderText("minden mezőt ki kell tölteni! A jelszónak minimum 6 karakterből kell álnia!");
            alert.showAndWait();
        }
    }
    
    // ezzel váltunk vissza a main pane-re
    @FXML
    private void handleRegPaneCancelButton(ActionEvent event) {
        regPane.setVisible(false);
        StartPane.setVisible(true);
    }
    
    // ezzel jelentkezünk be és váltunk a main felületre
    @FXML
    private void handleLogEntryButton(ActionEvent event) {
        Users actualUser = null;
        if(!logNameTF.getText().isEmpty() && !logPasswordPF.getText().isEmpty()){
            actualUser = db.entryUser(logNameTF.getText(), logPasswordPF.getText());
            if(actualUser != null){
                
                mainPane.setVisible(true);
                logPane.setVisible(false);
                actualUserID = Integer.parseInt(actualUser.getId());
                Stage stage = (Stage) logEntryButton.getScene().getWindow();
                stage.setResizable(true);
                stage.setMaximized(true);
                setTableData();
                mainTextArea.clear();
                

            } else {
                
                logNameTF.clear();
                logPasswordTF.clear();
                logPasswordPF.clear();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Rossz felhasználó név, vagy jelszó");
                alert.setTitle("Információ");
                alert.showAndWait();
            }
        } else {
            logNameTF.clear();
            logPasswordTF.clear();
            logPasswordPF.clear();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("nem adtál meg felhasználó nevet, vagy jelszót");
            alert.setTitle("Információ");
            alert.showAndWait();
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
        actualUserID = 0;
        
        
        stage.setWidth(350);
        stage.setHeight(300);
        stage.setResizable(false);
        stage.setMaximized(false);
    }
    
    // Ezzel adjuk hozzá a napló bejegyzést
    @FXML
    private void addLogEntry(ActionEvent event) {
        LogEntry newLogEntry = new LogEntry(NewLogAddTitleTextField.getText(), mainTextArea.getText(), actualUserID);
        LogData.add(newLogEntry);
        db.addLogEntry(newLogEntry,actualUserID);
        NewLogAddTitleTextField.clear();
        mainTextArea.clear();
    }
    
    // ezzel aktiváljuk a segítséget
    @FXML
    private void handleMainHelpButton(ActionEvent event) {
        HelpPane.setVisible(true);
        mainPane.setDisable(true);
    }
    // ezzel kapcsoljuk ki a segítséget
    @FXML
    private void handleMainHelpBackButton(ActionEvent event) {
        HelpPane.setVisible(false);
        mainPane.setDisable(false);
    }
    // ezzel adjuk hozzá az új naplóbejegyzést
    @FXML
    private void handleNewLogAddButton(ActionEvent event) {
        if(NewLogAddTitleTextField.getText().length() > 0){
            String newTitle = NewLogAddTitleTextField.getText();
            String newLog = mainTextArea.getText();
            LogEntry newLogEntry = new LogEntry(newTitle,newLog,actualUserID);
            db.addLogEntry(newLogEntry,actualUserID);
            setTableData();
            mainTextArea.clear();
            NewLogAddTitleTextField.clear();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Információ");
            alert.setHeaderText("Nem adtál meg címet, cím nélkül nem lehet elmenteni!");
            alert.showAndWait();
        }
        
    }
    
    @FXML
    private void handleMainTextUpdateButton(ActionEvent event) {  
        
        String Text = mainTextArea.getText();
        db.updateLogEntryText(Text,actualLogEntryTextId);
        NewLogAddTitleTextField.clear();
        setTableData();
        newLogAddButton.setDisable(false);
        mainTextUpdateButton.setDisable(true);
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Check Box">
    @FXML
    public void LogTogglevisiblePassword(ActionEvent event) {
        if (logCheckBox.isSelected()) {
            logPasswordTF.setText(logPasswordPF.getText());
            logPasswordTF.setVisible(true);
            logPasswordPF.setVisible(false);
        } else {
            logPasswordPF.setText(logPasswordTF.getText());
            logPasswordTF.setVisible(false);
            logPasswordPF.setVisible(true);
            
        }
    }
    
    public void RegTogglevisiblePassword(ActionEvent event) {
        if (regCheckBox.isSelected()) {
            regPasswordTF.setText(regPasswordPF.getText()); 
            regPasswordTF.setVisible(true);
            regPasswordPF.setVisible(false);
            
            regPasswordInspectionTF.setText(regPasswordInspectionPF.getText());
            regPasswordInspectionTF.setVisible(true);
            regPasswordInspectionPF.setVisible(false);
            
        } else {
            regPasswordPF.setText(regPasswordTF.getText());
            regPasswordTF.setVisible(false);
            regPasswordPF.setVisible(true);
            
            regPasswordInspectionPF.setText(regPasswordInspectionTF.getText());
            regPasswordInspectionTF.setVisible(false);
            regPasswordInspectionPF.setVisible(true);
        }
    }
//</editor-fold>
    
   //<editor-fold defaultstate="collapsed" desc="Tábla beállítása">
   
   public void setTableData(){
       TableColumn TitleCol = new TableColumn("Cím");
       TableColumn DateCol = new TableColumn("Dátum");
       TableColumn getCol = new TableColumn( "Megnyitás" );
       TableColumn removeCol = new TableColumn( "Törlés" );
       
       TitleCol.setMinWidth(150);
       DateCol.setMinWidth(100);
       getCol.setMinWidth(80);
       removeCol.setMinWidth(80);
       getCol.setStyle("-fx-alignment: CENTER");
       removeCol.setStyle("-fx-alignment: CENTER");
       
       LogData.clear();
       mainListView.getColumns().clear();
       mainListView.setItems(LogData);
       
       TitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
       TitleCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("title"));
       DateCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("date"));
       
       TitleCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LogEntry, String>>() {
           @Override
           public void handle(TableColumn.CellEditEvent<LogEntry, String> t) {
               LogEntry actualLogEntry = (LogEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
               actualLogEntry.setTitle(t.getNewValue());
               db.updateLogEntryTitle(actualLogEntry);
           }
           
           
           
       });
       

       
       Callback<TableColumn<LogEntry, String>, TableCell<LogEntry, String>> cellFactory =
               new Callback<TableColumn<LogEntry, String>, TableCell<LogEntry, String>>()
               {
                   @Override
                   public TableCell call( final TableColumn<LogEntry, String> param )
                   {
                       final TableCell<LogEntry, String> cell = new TableCell<LogEntry, String>()
                       {
                           final Button btn = new Button( "Megnyitás" );
                           
                           @Override
                           public void updateItem( String item, boolean empty )
                           {
                               super.updateItem( item, empty );
                               if ( empty )
                               {
                                   setGraphic( null );
                                   setText( null );
                               }
                               else
                               {
                                   btn.setOnAction( ( ActionEvent event ) ->
                                   {
                                       newLogAddButton.setDisable(true);
                                       mainTextUpdateButton.setDisable(false);
                                       LogEntry getLogEntry = getTableView().getItems().get( getIndex() );
                                       mainTextArea.setText(getLogEntry.getText());
                                       actualLogEntryTextId = Integer.parseInt(getLogEntry.getLogID());
                                       System.out.println(getLogEntry.getLogID());
                                       
                                        LogData.clear();
                                        mainListView.getColumns().clear();
                                        mainListView.setItems(LogData);
                                        
                                        mainListView.getColumns().addAll(TitleCol,DateCol,getCol,removeCol); // itt adjuk hozzá a tábla neveket

                                        LogData.addAll(db.getAllLogEntry(actualUserID));

                                        mainListView.setItems(LogData); // itt adjuk hozzá az adatokat
                                   } );
                                   setGraphic( btn );
                                   setText( null );
                               }
                           }
                       };
                       return cell;
                   }
               };
       
       getCol.setCellFactory( cellFactory );
       
       
       Callback<TableColumn<LogEntry, String>, TableCell<LogEntry, String>> cellFactory2 =
               new Callback<TableColumn<LogEntry, String>, TableCell<LogEntry, String>>()
               {
                   @Override
                   public TableCell call( final TableColumn<LogEntry, String> param )
                   {
                       final TableCell<LogEntry, String> cell = new TableCell<LogEntry, String>()
                       {
                           final Button btn = new Button( "Törlés" );
                           
                           @Override
                           public void updateItem( String item, boolean empty )
                           {
                               super.updateItem( item, empty );
                               if ( empty )
                               {
                                   setGraphic( null );
                                   setText( null );
                               }
                               else
                               {
                                   btn.setOnAction( ( ActionEvent event ) ->
                                   {
                                        
                                        LogEntry deleteLogEntry = getTableView().getItems().get( getIndex() );
                                        LogData.remove(deleteLogEntry);
                                        db.removeLogEntry(deleteLogEntry);
                                        
                                        LogData.clear();
                                        mainListView.getColumns().clear();
                                        mainListView.setItems(LogData);
                                        
                                        mainListView.getColumns().addAll(TitleCol,DateCol,getCol,removeCol); // itt adjuk hozzá a tábla neveket

                                        LogData.addAll(db.getAllLogEntry(actualUserID));

                                        mainListView.setItems(LogData); // itt adjuk hozzá az adatokat
                                        mainTextArea.setText("");
                                        newLogAddButton.setDisable(false);
                                        mainTextUpdateButton.setDisable(true);
                                   } );
                                   setGraphic( btn );
                                   setText( null );
                               }
                           }
                       };
                       return cell;
                   }
               };
       
       removeCol.setCellFactory( cellFactory2 );

    mainListView.getColumns().addAll(TitleCol,DateCol,getCol,removeCol); // itt adjuk hozzá a tábla neveket

    LogData.addAll(db.getAllLogEntry(actualUserID));

    mainListView.setItems(LogData); // itt adjuk hozzá az adatokat

   }
    

       
   
//</editor-fold>
    
   @Override
   public void initialize(URL url, ResourceBundle rb) {
       this.LogTogglevisiblePassword(null);
       this.RegTogglevisiblePassword(null);
   }

  
    
}
