package maintananceViewer;

import Database.connectionDB;
import Database.issues;
import Database.properties;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.StringConverter;
import propertyViewer.PropertyManagementController;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class editIssueController implements Initializable {
    public issues selectedIssue;
    public connectionDB connectSQL;
    private Connection connected;
    private final ObservableList<properties> data = FXCollections.<properties>observableArrayList();
    private final ObservableList<issues> issueData = FXCollections.<issues>observableArrayList();
    private final ObservableList<String> tennantData = FXCollections.<String>observableArrayList();
    private final ObservableList<String> priorityData = FXCollections.<String>observableArrayList();
    @FXML
    private JFXComboBox<properties> propertyList;
    @FXML
    private JFXComboBox<String> priorityLevel;
    @FXML
    private JFXComboBox<String> tennantPicker;
    @FXML
    private JFXDatePicker reportedDate;
    @FXML
    private JFXButton updateBtn;
    @FXML
    private JFXTextArea descriptionField;
    @FXML
    private JFXTreeTableView<issues> jobTable;
    private final JFXTreeTableColumn<issues, String> jobIDCol = new JFXTreeTableColumn("Job ID");
    private final JFXTreeTableColumn<issues, String> propertyCol = new JFXTreeTableColumn("Property");
    private final JFXTreeTableColumn<issues, String> jobDescCol = new JFXTreeTableColumn("Description");
    private final JFXTreeTableColumn<issues, String> statusCol = new JFXTreeTableColumn("Status");
    private final JFXTreeTableColumn<issues, String> startedCol = new JFXTreeTableColumn("Reported On");
    private final JFXTreeTableColumn<issues, String> levelOfUrgencyCol = new JFXTreeTableColumn("Urgency");

    /**
     * Initializes the controller class.
     */
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connectSQL = new Database.connectionDB();
        populateProperty();
        populatePriority();
        reportedDate.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }
            
            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
        
        getValueForEditing();
        populateTennant();
    }
    
    public void populateProperty() {
        
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM property ";
        try {
            java.sql.Statement st = connected.createStatement();
            ResultSet rs = st.executeQuery(Sqlite);
            while (rs.next()) {
                
                data.add(new properties(rs.getString("PropertyID"), rs.getString("DoorNo"), rs.getString("FlatNo"), rs.getString("FirstLine"), rs.getString("PostCode"), rs.getString("Town"), rs.getString("DoorNo"), rs.getString("AreaManager"), rs.getString("ContractEnded"), rs.getString("ContractStarted"), rs.getString("Cost"), rs.getString("DayOfMonth"), rs.getString("Landlord")));
            }
            propertyList.setItems(data);
            propertyList.setConverter(new StringConverter<properties>() {
                @Override
                public String toString(properties object) {
                    return object.getFirstLine();
                }
                
                @Override
                public properties fromString(String string) {
                    return null;
                }
            });
        } catch (SQLException ex) {
        }
        
    }
    @FXML
    public void populateTennant() {
        tennantData.clear();
        connected = connectSQL.returnConnection();
        try {
            String Sqlite = "SELECT * FROM tennant WHERE LivesAt='" + propertyList.getValue().getPropertyID() + "' ";
            
            java.sql.Statement st = connected.createStatement();
            ResultSet rs = st.executeQuery(Sqlite);
            while (rs.next()) {
                
                tennantData.add((rs.getString("FirstName") + " " + rs.getString("LastName")));
            }
            tennantPicker.setItems(tennantData);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
        listTents();
    }
    
    public void listTents() {
        //populate tableview using data from database
        updateTable();
        
        jobIDCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("issueID"));
        
        propertyCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("Property"));
        
        jobDescCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("issue"));
        
        statusCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("status"));

        startedCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("dateReported"));
        levelOfUrgencyCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("levelOfUrgency"));
        
        levelOfUrgencyCol.setCellFactory((TreeTableColumn<issues, String> param) -> {
            TreeTableCell cell = new TreeTableCell<issues, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<issues> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("");
                        ttr.setStyle("");
                        setStyle("");
                        
                    } else if (item.equals("Low")) {
                        
                        setStyle("-fx-background-color:yellow");
                    } else if (item.equals("Medium")) {
                        
                        setStyle("-fx-background-color:orange");
                    } else {
                        setStyle("-fx-background-color:red");
                    }
                }
            };
            return cell;
        });
        
        final TreeItem<issues> root = new RecursiveTreeItem<issues>(issueData, RecursiveTreeObject::getChildren);
        jobTable.getColumns().setAll(jobIDCol, propertyCol, jobDescCol, startedCol, statusCol, levelOfUrgencyCol);
        jobTable.setRoot(root);
        jobTable.setShowRoot(false);
        
    }
    
    public void updateTable() {
        issueData.clear();
        properties prop;
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM issues INNER JOIN property ON issues.propertyID=property.PropertyID WHERE property.PropertyID='" + propertyList.getValue().getPropertyID() + "'";
        ResultSet rs;
        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            
            while (rs.next()) {
                prop = new properties(rs.getString("PropertyID"), rs.getString("DoorNo") + " " + rs.getString("FlatNo") + " " + rs.getString("FirstLine"), "", "");
                issueData.add(new issues(prop, rs.getString("propertyID"), rs.getString("idIssues"), rs.getString("Description"), rs.getString("Status"), rs.getString("levelOfUrgency"), rs.getString("Date"), rs.getString("reportedBy")));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        
        jobTable.refresh();
        
    }
    
    public void populatePriority() {
        priorityData.setAll("High", "Medium", "Low");
        priorityLevel.setItems(priorityData);
    }   
    public void getValueForEditing() {
               int i=setSelectedValue(selectedIssue.propertyID);
        propertyList.getSelectionModel().select(i);
       
        priorityLevel.setValue(selectedIssue.levelOfUrgency);
        reportedDate.setValue(LocalDate.parse(selectedIssue.dateReported, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        tennantPicker.setValue(selectedIssue.reportedBy);
        descriptionField.setText(selectedIssue.issue);
    }
    @FXML
      public void editIssue(){
       connected = connectSQL.returnConnection();
        String Sqlite = "UPDATE issues SET Description=? ,reportedBy=? ,Date=? ,levelOfUrgency=?,propertyID=? WHERE idIssues='"+jobTable.getSelectionModel().getSelectedItem().getValue().issueID+"'";
        
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connected.prepareStatement(Sqlite);
            preparedStatement.setString(1, descriptionField.getText());
            preparedStatement.setString(2, tennantPicker.getValue());
            preparedStatement.setString(3, reportedDate.getValue().toString());
            preparedStatement.setString(4, priorityLevel.getValue());
            preparedStatement.setString(5, propertyList.getValue().getPropertyID());
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
 updateTable();
        descriptionField.clear();
        tennantPicker.setValue(null);
        reportedDate.setValue(null);
        priorityLevel.setValue(null);
        propertyList.setValue(null);
    }
    public int setSelectedValue(String propertyID)
    {
        for (int i =0;i<propertyList.getItems().size();i++) {
            if (data.get(i).getPropertyID().equals(propertyID)){
            return i;
            }
        }
        return 0;
    }
}
