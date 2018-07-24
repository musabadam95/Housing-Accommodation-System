package propertyViewer;

import Database.areaManager;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import propertyViewer.PropertyManagementController;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class manageAreaManagerController implements Initializable {

    public connectionDB connectSQL;
    private Connection connected;
    public ObservableList<areaManager> data = FXCollections.<areaManager>observableArrayList();

    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXTreeTableView<areaManager> areaManagerTable;

    private final JFXTreeTableColumn<areaManager, String> areaManagerIDCol = new JFXTreeTableColumn("Builder ID");
    private final JFXTreeTableColumn<areaManager, String> firstNameCol = new JFXTreeTableColumn("First Name");
    private final JFXTreeTableColumn<areaManager, String> lastNameCol = new JFXTreeTableColumn("Last Name");
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
    private areaManager selectedAreaManager;
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connectSQL = new Database.connectionDB();
        listTents();
    }

    public void listTents() {
        //populate tableview using data from database
        updateTable();

        areaManagerIDCol.setCellValueFactory(new TreeItemPropertyValueFactory<areaManager, String>("areaManagerID"));

        firstNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<areaManager, String>("firstName"));

        lastNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<areaManager, String>("lastName"));

        final TreeItem<areaManager> root = new RecursiveTreeItem<areaManager>(data, RecursiveTreeObject::getChildren);
        areaManagerTable.getColumns().setAll(areaManagerIDCol, firstNameCol, lastNameCol);
        areaManagerTable.setRoot(root);
        areaManagerTable.setShowRoot(false);

    }

    public void updateTable() {
        data.clear();
        properties prop;
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM areamanager ";
        ResultSet rs;
        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);

            while (rs.next()) {
                data.add(new areaManager(rs.getString("idAreaManager"), rs.getString("FirstName"), rs.getString("LastName")));

            }

        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        areaManagerTable.refresh();

    }

    @FXML
    public void addBuilder() {
        connected = connectSQL.returnConnection();
        String Sqlite = "INSERT INTO areamanager(FirstName,LastName)VALUES (?,?)";

        try {
            PreparedStatement preparedStatement = (PreparedStatement) connected.prepareStatement(Sqlite);
            preparedStatement.setString(1, firstNameTxt.getText());
            preparedStatement.setString(2, lastNameTxt.getText());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
updateTable();
    }

    public void deleteBuilder() {
        connected = connectSQL.returnConnection();
        String Sqlite = "DELETE FROM areamanager WHERE idAreaManager='" + areaManagerTable.getSelectionModel().getSelectedItem().getValue().getAreaManagerID() + "'";
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
        selectedAreaManager = areaManagerTable.getSelectionModel().getSelectedItem().getValue();

        firstNameTxt.setText(areaManagerTable.getSelectionModel().getSelectedItem().getValue().firstName);
        lastNameTxt.setText(areaManagerTable.getSelectionModel().getSelectedItem().getValue().lastName);

    }

    @FXML
    public void editIssue() {
        connected = connectSQL.returnConnection();
        String Sqlite = "UPDATE areamanager SET FirstName=? ,LastName=? WHERE idAreaManager='" + selectedAreaManager.getAreaManagerID() + "'";

        try {
            PreparedStatement preparedStatement = (PreparedStatement) connected.prepareStatement(Sqlite);
            preparedStatement.setString(1, firstNameTxt.getText());
            preparedStatement.setString(2, lastNameTxt.getText());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateTable();
    }

}
