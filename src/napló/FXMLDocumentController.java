package napló;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.event.Event; 
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton; 
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private Button mainHelpButton;
    
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
        Users newUser = new Users(regNameTF.getText(),regPasswordTF.getText());
        if((regNameTF.getText().length() > 0) || (regPasswordTF.getText().length() > 5) || (regPasswordInspectionTF.getText().length() > 5)){
            if((regPasswordTF.getText().length() > 5) && (regPasswordInspectionTF.getText().length() > 5)){
                if(regPasswordTF.getText().equals(regPasswordInspectionTF.getText())){
                    if((regNameTF.getText().length() > 0)){
                        if(db.checkUser(regNameTF.getText())){
                            db.addUser(newUser);
                            regPane.setVisible(false);
                            StartPane.setVisible(true);
                        } else {
                            regNameTF.clear();
                            regPasswordTF.clear();
                            regPasswordInspectionTF.clear();
                            alert.setHeaderText("Ez a név már foglalt! másik felhasználó nevet kell megadnod");
                            alert.showAndWait();
                        }
                    } else {
                        regNameTF.clear();
                        regPasswordTF.clear();
                        regPasswordInspectionTF.clear();
                        alert.setHeaderText("Nem adtál meg felhasználó nevet!");
                        alert.showAndWait();
                    }
                } else {
                    regNameTF.clear();
                    regPasswordTF.clear();
                    regPasswordInspectionTF.clear();
                    alert.setHeaderText("a jelszó, és az ellenörző jelszó nem ugyanaz!");
                    alert.showAndWait();
                }
            }else{
                  regNameTF.clear();
                  regPasswordTF.clear();
                  regPasswordInspectionTF.clear();
                  alert.setHeaderText("A jelszónak minimum 6 karakterből kell álnia!");
                  alert.showAndWait();  
            }
        } else {
            regNameTF.clear();
            regPasswordTF.clear();
            regPasswordInspectionTF.clear();;
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
    
    // ezzel váltunk a main felületre
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
                stage.setResizable(false);
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
    private void handleMainHelpButton(ActionEvent event) {
        HelpPane.setVisible(true);
    }
    @FXML
    private void handleMainHelpBackButton(ActionEvent event) {
        HelpPane.setVisible(false);
    }
    // ezzel adjuk hozzá az új naplóbejegyzést
    @FXML
    private void handleNewLogAddButton(ActionEvent event) {
        String newTitle = NewLogAddTitleTextField.getText();
        String newLog = mainTextArea.getText();
        LogEntry newLogEntry = new LogEntry(newTitle,newLog,actualUserID);
        LogData.add(newLogEntry);
        db.addLogEntry(newLogEntry,actualUserID);
        mainTextArea.clear();
        NewLogAddTitleTextField.clear();
    }
    
    @FXML
    private void handleMainTextUpdateButton(ActionEvent event) {  

        String Text = mainTextArea.getText();
        db.updateLogEntryText(Text,actualUserID);
        NewLogAddTitleTextField.clear();
        setTableData();
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Check Box">
    @FXML
    public void togglevisiblePassword(ActionEvent event) {
        if (logCheckBox.isSelected()) {
            logPasswordTF.setText(logPasswordPF.getText());
            logPasswordTF.setVisible(true);
            logPasswordPF.setVisible(false);
            
        } else {
            logPasswordPF.setText(logPasswordTF.getText());
            logPasswordPF.setVisible(true);
            logPasswordTF.setVisible(false);
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
       
//       LogData.clear();
//       mainListView.getColumns().clear();
//       mainListView.setItems(LogData);
       
       TitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
       TitleCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("title"));
       
       TitleCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LogEntry, String>>() {
           @Override
           public void handle(TableColumn.CellEditEvent<LogEntry, String> t) {
               LogEntry actualLogEntry = (LogEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
               actualLogEntry.setTitle(t.getNewValue());
               db.updateLogEntryTitle(actualLogEntry);
           }
           
           
           
       });
       
       DateCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("date"));
       
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
                                       LogEntry getLogEntry = getTableView().getItems().get( getIndex() );
                                       mainTextArea.setText(getLogEntry.getText());
                                       actualLogEntryTextId = Integer.parseInt(getLogEntry.getLogID());
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
    
   public void setTableData2newLogAddButton(){
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
       
       TitleCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LogEntry, String>>() {
           @Override
           public void handle(TableColumn.CellEditEvent<LogEntry, String> t) {
               LogEntry actualLogEntry = (LogEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
               actualLogEntry.setTitle(t.getNewValue());
               db.updateLogEntryTitle(actualLogEntry);
           }
           
           
           
       });
       
       DateCol.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("date"));
       
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
                                       LogEntry getLogEntry = getTableView().getItems().get( getIndex() );
                                       mainTextArea.setText(getLogEntry.getText());
                                       actualLogEntryTextId = Integer.parseInt(getLogEntry.getLogID());
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
        this.togglevisiblePassword(null);
    }    
    
}
