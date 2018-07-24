package maintananceViewer;

import Database.builders;
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
import javafx.stage.Stage;
import javafx.util.StringConverter;
import propertyViewer.PropertyManagementController;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class initateJobController implements Initializable {

    public connectionDB connectSQL;
    private Connection connected;
    public issues selectedissue;
    public ObservableList<builders> data = FXCollections.<builders>observableArrayList();
    public ObservableList<issues> issueData = FXCollections.<issues>observableArrayList();
    @FXML
    private JFXComboBox<builders> propertyList;
    @FXML
    private JFXDatePicker reportedDate;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXTextArea descriptionField;
    @FXML
    private JFXTreeTableView<issues> jobTable;
    private final JFXTreeTableColumn<issues, String> jobIDCol = new JFXTreeTableColumn("Job ID");
    private final JFXTreeTableColumn<issues, String> propertyCol = new JFXTreeTableColumn("Property");
    private final JFXTreeTableColumn<issues, String> jobDescCol = new JFXTreeTableColumn("Description");
    private final JFXTreeTableColumn<issues, String> statusCol = new JFXTreeTableColumn("Status");
    private final JFXTreeTableColumn<issues, String> reportedOnCol = new JFXTreeTableColumn("Reported On");
    private final JFXTreeTableColumn<issues, String> levelOfUrgencyCol = new JFXTreeTableColumn("Urgency");


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connectSQL = new Database.connectionDB();
        populateBuilders();

        reportedDate.setConverter(new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
    }

    public void populateBuilders() {

        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM builder ";
        try {
            java.sql.Statement st = connected.createStatement();
            ResultSet rs = st.executeQuery(Sqlite);
            while (rs.next()) {

                data.add(new builders(rs.getString("idBuilder"), rs.getString("FirstName"), rs.getString("LastName")));
            }
            propertyList.setItems(data);
            propertyList.setConverter(new StringConverter<builders>() {
                @Override
                public String toString(builders object) {
                    return object.firstName + " " + object.lastName;
                }

                @Override
                public builders fromString(String string) {
                    return null;
                }
            });
        } catch (SQLException ex) {
        }

    }

    public void listTents() {
        //populate tableview using data from database
        updateTable();

        jobIDCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("issueID"));

        propertyCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("Property"));

        jobDescCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("issue"));

        statusCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("status"));

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
        jobTable.getColumns().setAll(jobIDCol, propertyCol, jobDescCol, reportedOnCol, statusCol, levelOfUrgencyCol);
        jobTable.setRoot(root);
        jobTable.setShowRoot(false);

    }

    public void updateTable() {
        issueData.clear();
        properties prop;
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM intiatedAndCompleteJobs INNER JOIN builder ON intiatedAndCompleteJobs.BuilderAssigned=builder.idBuilder INNER JOIN issues ON issues.idIssues=intiatedAndCompleteJobs.issueID' WHERE builder.idBuilder=" + propertyList.getValue().getBuilderID() + "";
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

    @FXML
    public void addNewIssue() {
          Stage stage = (Stage) addBtn.getScene().getWindow();
        connected = connectSQL.returnConnection();
        String Sqlite = "INSERT INTO intiatedAndCompleteJobs(issueID,BuilderAssigned,JobStarted,Comments)VALUES (?,?,?,?)";

        try {
            PreparedStatement preparedStatement = (PreparedStatement) connected.prepareStatement(Sqlite);
            preparedStatement.setString(1, selectedissue.issueID);
            preparedStatement.setString(2, propertyList.getValue().getBuilderID());
            preparedStatement.setString(3, reportedDate.getValue().toString());
            preparedStatement.setString(4, descriptionField.getText());
            preparedStatement.executeUpdate();
            Sqlite = "UPDATE issues set status=? WHERE idIssues='" + selectedissue.issueID + "'";
            preparedStatement = (PreparedStatement) connected.prepareStatement(Sqlite);
            preparedStatement.setString(1, "Ongoing");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

stage.close();
    }

}
