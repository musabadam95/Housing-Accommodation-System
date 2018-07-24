package maintananceViewer;

import Database.builders;
import Database.connectionDB;
import Database.properties;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import propertyViewer.PropertyManagementController;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class manageBuildersController implements Initializable {

    public connectionDB connectSQL;
    private Connection connected;
    public ObservableList<builders> data = FXCollections.<builders>observableArrayList();

    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXTreeTableView<builders> jobTable;

    private final JFXTreeTableColumn<builders, String> builderIDCol = new JFXTreeTableColumn("Builder ID");
    private final JFXTreeTableColumn<builders, String> firstNameCol = new JFXTreeTableColumn("First Name");
    private final JFXTreeTableColumn<builders, String> lastNameCol = new JFXTreeTableColumn("Last Name");
    @FXML
    private JFXTextField firstNameTxt;
    @FXML
    private JFXTextField lastNameTxt;
    @FXML
    private JFXButton delBtn;
    @FXML
    private JFXButton editBtn;
    @FXML
    private JFXButton updateBtn;
    private builders selectedBuilder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        delBtn.disableProperty().bind(Bindings.isEmpty(jobTable.getSelectionModel().getSelectedItems()));
        editBtn.disableProperty().bind(Bindings.isEmpty(jobTable.getSelectionModel().getSelectedItems()));
        updateBtn.disableProperty().bind(Bindings.isEmpty(jobTable.getSelectionModel().getSelectedItems()));
        connectSQL = new Database.connectionDB();
        listTents();
    }

    public void listTents() {
        //populate tableview using data from database
        updateTable();

        builderIDCol.setCellValueFactory(new TreeItemPropertyValueFactory<builders, String>("builderID"));

        firstNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<builders, String>("firstName"));

        lastNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<builders, String>("lastName"));

        final TreeItem<builders> root = new RecursiveTreeItem<builders>(data, RecursiveTreeObject::getChildren);
        jobTable.getColumns().setAll(builderIDCol, firstNameCol, lastNameCol);
        jobTable.setRoot(root);
        jobTable.setShowRoot(false);

    }

    public void updateTable() {
        data.clear();
        properties prop;
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM builder ";
        ResultSet rs;
        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);

            while (rs.next()) {
                data.add(new builders(rs.getString("idBuilder"), rs.getString("FirstName"), rs.getString("LastName")));

            }

        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        jobTable.refresh();

    }

    @FXML
    public void addBuilder() {
        connected = connectSQL.returnConnection();
        String Sqlite = "INSERT INTO builder(FirstName,LastName)VALUES (?,?)";

        try {
            PreparedStatement preparedStatement = (PreparedStatement) connected.prepareStatement(Sqlite);
            preparedStatement.setString(1, firstNameTxt.getText());
            preparedStatement.setString(2, lastNameTxt.getText());
            preparedStatement.executeUpdate();
            java.sql.Statement stmt = connected.createStatement();
            Sqlite = "Select * from builder where idBuilder=LAST_INSERT_ID()";
            ResultSet rs = stmt.executeQuery(Sqlite);
            String pK = "";
            while (rs.next()) {
                pK = rs.getString("idBuilder");
            }

            Sqlite = "INSERT INTO users (UserName,Password,Type,builderID) Values ('" + firstNameTxt.getText() + lastNameTxt.getText() + "','" + lastNameTxt.getText() + pK + "','" + "Builder" + "','" + pK + "')";
            stmt.executeUpdate(Sqlite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateTable();
        firstNameTxt.clear();
        lastNameTxt.clear();
    }

    public void deleteBuilder() {
        connected = connectSQL.returnConnection();
        String Sqlite = "DELETE FROM builder WHERE idBuilder='" + jobTable.getSelectionModel().getSelectedItem().getValue().getBuilderID() + "'";
        try {
            java.sql.Statement stmt = connected.createStatement();
            stmt.executeUpdate(Sqlite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateTable();

    }

    @FXML
    public void getValueForEditing() {
        selectedBuilder = jobTable.getSelectionModel().getSelectedItem().getValue();

        firstNameTxt.setText(jobTable.getSelectionModel().getSelectedItem().getValue().firstName);
        lastNameTxt.setText(jobTable.getSelectionModel().getSelectedItem().getValue().lastName);

    }

    @FXML
    public void editIssue() {
        connected = connectSQL.returnConnection();
        String Sqlite = "UPDATE builder SET FirstName=? ,LastName=? WHERE idBuilder='" + selectedBuilder.builderID + "'";

        try {
            PreparedStatement preparedStatement = (PreparedStatement) connected.prepareStatement(Sqlite);
            preparedStatement.setString(1, firstNameTxt.getText());
            preparedStatement.setString(2, lastNameTxt.getText());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateTable();
        firstNameTxt.clear();
        lastNameTxt.clear();
    }

}
