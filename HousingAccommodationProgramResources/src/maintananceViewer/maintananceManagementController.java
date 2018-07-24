package maintananceViewer;

import Database.connectionDB;
import Database.issues;
import Database.properties;
import com.jfoenix.controls.JFXButton;
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
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import propertyViewer.PropertyManagementController;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class maintananceManagementController implements Initializable {

    @FXML
    private JFXTreeTableView<issues> treeviewProp;
    public connectionDB connectSQL;
    private Connection connected;
    public ObservableList<issues> data = FXCollections.<issues>observableArrayList();
    properties selectedProp;
    private final JFXTreeTableColumn<issues, String> jobIDCol = new JFXTreeTableColumn("Job ID");
    private final JFXTreeTableColumn<issues, String> propertyCol = new JFXTreeTableColumn("Property");
    private final JFXTreeTableColumn<issues, String> jobDescCol = new JFXTreeTableColumn("Description");
    private final JFXTreeTableColumn<issues, String> statusCol = new JFXTreeTableColumn("Status");
    private final JFXTreeTableColumn<issues, String> dateReportedCol = new JFXTreeTableColumn("Reported On");
    private final JFXTreeTableColumn<issues, String> levelOfUrgencyCol = new JFXTreeTableColumn("Urgency");
    private final JFXTreeTableColumn<issues, String> builderCol = new JFXTreeTableColumn("Builder Assigned");
    private final JFXTreeTableColumn<issues, String> startedOnCol = new JFXTreeTableColumn("Works Started");
    private final JFXTreeTableColumn<issues, String> finishedOnCol = new JFXTreeTableColumn("Works Finished");
    private final JFXTreeTableColumn<issues, String> commentsCol = new JFXTreeTableColumn("Job Comments");
    @FXML
    private JFXButton openJobBtn;
    @FXML
    private JFXButton ongoingBtn;
    @FXML
    private JFXButton completedBtn;
    @FXML
    private JFXButton addIssueBtn;
    @FXML
    private JFXButton editIssueBtn;
    @FXML
    private JFXButton deleteIssueBtn;
    @FXML
    private JFXButton initiateBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        deleteIssueBtn.disableProperty().bind(Bindings.isEmpty(treeviewProp.getSelectionModel().getSelectedItems()));
        editIssueBtn.disableProperty().bind(Bindings.isEmpty(treeviewProp.getSelectionModel().getSelectedItems()));
        initiateBtn.disableProperty().bind(Bindings.isEmpty(treeviewProp.getSelectionModel().getSelectedItems()));

        connectSQL = new Database.connectionDB();
        listTents();

    }

    public void listTents() {
        //populate tableview using data from database
        updateTable();

        jobIDCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("issueID"));

        propertyCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("Property"));

        jobDescCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("issue"));

        statusCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("status"));

        dateReportedCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("dateReported"));
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
        builderCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("builder"));
        startedOnCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("jobStarted"));
        finishedOnCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("jobCompleted"));
        commentsCol.setCellValueFactory(new TreeItemPropertyValueFactory<issues, String>("comments"));
        final TreeItem<issues> root = new RecursiveTreeItem<issues>(data, RecursiveTreeObject::getChildren);
        treeviewProp.getColumns().setAll(jobIDCol, propertyCol, jobDescCol, dateReportedCol, statusCol, levelOfUrgencyCol);
        treeviewProp.setRoot(root);
        treeviewProp.setShowRoot(false);

    }

    public void updateTable() {
        data.clear();
        properties prop;
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM issues INNER JOIN property ON issues.propertyID=property.PropertyID WHERE Status='Open'";
        ResultSet rs;
        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);

            while (rs.next()) {
                prop = new properties(rs.getString("PropertyID"), rs.getString("DoorNo") + " " + rs.getString("FlatNo") + " " + rs.getString("FirstLine"), "", "");
                data.add(new issues(prop, rs.getString("propertyID"), rs.getString("idIssues"), rs.getString("Description"), rs.getString("Status"), rs.getString("levelOfUrgency"), rs.getString("Date"), rs.getString("reportedBy")));

            }

        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        treeviewProp.refresh();

    }

    @FXML
    private void addNewIssue(ActionEvent event) throws IOException {

        addingIssue addingIssue = new addingIssue();

        Stage primaryStage = new Stage();
        addingIssue.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            updateTable();

        });

    }

    @FXML
    private void aeditIssue(ActionEvent event) throws IOException {

        editIssue addingIssue = new editIssue(treeviewProp.getSelectionModel().getSelectedItem().getValue());

        Stage primaryStage = new Stage();
        addingIssue.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            updateTable();

        });

    }

    @FXML
    public void deleteIssue() {
        issues selectedIssue = treeviewProp.getSelectionModel().getSelectedItem().getValue();
        connected = connectSQL.returnConnection();
        String Sqlite = "DELETE FROM issues WHERE idIssues='" + selectedIssue.issueID + "'";
        try {
            java.sql.Statement st = connected.createStatement();
            st.executeUpdate(Sqlite);

        } catch (SQLException e) {

            e.printStackTrace();
        }
        updateTable();
    }

    @FXML
    private void manageBuilders(ActionEvent event) throws IOException {

        manageBuilders manageBuilders = new manageBuilders();

        Stage primaryStage = new Stage();
        manageBuilders.start(primaryStage);

    }

    @FXML
    private void initateJob(ActionEvent event) throws IOException {

        initateJob initateJob = new initateJob(treeviewProp.getSelectionModel().getSelectedItem().getValue());

        Stage primaryStage = new Stage();
        initateJob.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            updateTable();

        });

    }

    @FXML
    private void filterOnGoingJobs() {
        treeviewProp.getSelectionModel().clearSelection();
        data.clear();
        properties prop;
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM intiatedAndCompleteJobs INNER JOIN issues ON intiatedAndCompleteJobs.issueID=issues.idIssues INNER JOIN builder ON intiatedAndCompleteJobs.BuilderAssigned=builder.idBuilder INNER JOIN property ON issues.propertyID=property.PropertyID WHERE Status='Ongoing'";
        ResultSet rs;
        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);

            while (rs.next()) {
                prop = new properties(rs.getString("PropertyID"), rs.getString("DoorNo") + " " + rs.getString("FlatNo") + " " + rs.getString("FirstLine"), "", "");
                data.add(new issues(prop, rs.getString("propertyID"), rs.getString("idIssues"), rs.getString("builder.FirstName") + " " + rs.getString("builder.LastName"), rs.getString("JobStarted"), rs.getString("JobCompleted"), rs.getString("Description"), rs.getString("Comments"), rs.getString("reportedBy"), rs.getString("Date"), rs.getString("Status"), rs.getString("levelOfUrgency")));

            }
            treeviewProp.getColumns().setAll(jobIDCol, propertyCol, jobDescCol, dateReportedCol, statusCol, levelOfUrgencyCol, builderCol, startedOnCol, commentsCol);
            treeviewProp.refresh();
            openJobBtn.setDisable(false);
            ongoingBtn.setDisable(true);
            completedBtn.setDisable(false);
           deleteIssueBtn.disableProperty().unbind();
        editIssueBtn.disableProperty().unbind();
        initiateBtn.disableProperty().unbind();
            editIssueBtn.setDisable(true);
            initiateBtn.setDisable(true);
            deleteIssueBtn.setDisable(true);

        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void filterOpenJobs() {
        treeviewProp.getSelectionModel().clearSelection();

        treeviewProp.getColumns().setAll(jobIDCol, propertyCol, jobDescCol, dateReportedCol, statusCol, levelOfUrgencyCol);
        updateTable();
        openJobBtn.setDisable(true);
        ongoingBtn.setDisable(false);
        completedBtn.setDisable(false);
        editIssueBtn.setDisable(false);
       
        initiateBtn.setDisable(false);
        deleteIssueBtn.setDisable(false);
         deleteIssueBtn.disableProperty().bind(Bindings.isEmpty(treeviewProp.getSelectionModel().getSelectedItems()));
        editIssueBtn.disableProperty().bind(Bindings.isEmpty(treeviewProp.getSelectionModel().getSelectedItems()));
        initiateBtn.disableProperty().bind(Bindings.isEmpty(treeviewProp.getSelectionModel().getSelectedItems()));

    }

    @FXML
    private void filterCompleteJobs() {
                treeviewProp.getSelectionModel().clearSelection();

        data.clear();
        properties prop;
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM intiatedAndCompleteJobs INNER JOIN issues ON intiatedAndCompleteJobs.issueID=issues.idIssues INNER JOIN builder ON intiatedAndCompleteJobs.BuilderAssigned=builder.idBuilder INNER JOIN property ON issues.propertyID=property.PropertyID WHERE Status='Complete'";
        ResultSet rs;
        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);

            while (rs.next()) {
                prop = new properties(rs.getString("PropertyID"), rs.getString("DoorNo") + " " + rs.getString("FlatNo") + " " + rs.getString("FirstLine"), "", "");
                data.add(new issues(prop, rs.getString("propertyID"), rs.getString("idIssues"), rs.getString("builder.FirstName") + " " + rs.getString("builder.LastName"), rs.getString("JobStarted"), rs.getString("JobCompleted"), rs.getString("Description"), rs.getString("Comments"), rs.getString("reportedBy"), rs.getString("Date"), rs.getString("Status"), rs.getString("levelOfUrgency")));

            }
            treeviewProp.getColumns().setAll(jobIDCol, propertyCol, jobDescCol, dateReportedCol, statusCol, levelOfUrgencyCol, builderCol, startedOnCol, finishedOnCol, commentsCol);
            treeviewProp.refresh();
            openJobBtn.setDisable(false);
            ongoingBtn.setDisable(false);
            completedBtn.setDisable(true);
            
             deleteIssueBtn.disableProperty().unbind();
        editIssueBtn.disableProperty().unbind();
        initiateBtn.disableProperty().unbind();
            editIssueBtn.setDisable(true);
            initiateBtn.setDisable(true);
            deleteIssueBtn.setDisable(true);

        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
