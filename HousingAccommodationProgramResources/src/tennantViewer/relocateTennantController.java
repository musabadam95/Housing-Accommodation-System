package tennantViewer;

import Database.connectionDB;
import Database.properties;
import Database.tennants;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.util.StringConverter;
import propertyViewer.PropertyManagementController;
import propertyViewer.manageAreaManagerController;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class relocateTennantController implements Initializable {

    public connectionDB connectSQL;
    private Connection connected;
    public ObservableList<tennants> data = FXCollections.<tennants>observableArrayList();
    public ObservableList<properties> propList = FXCollections.<properties>observableArrayList();

    private final JFXTreeTableColumn<tennants, String> tennantIDcol = new JFXTreeTableColumn("TennantID");
    private final JFXTreeTableColumn<tennants, String> FirstNamecol = new JFXTreeTableColumn("FirstName");
    private final JFXTreeTableColumn<tennants, String> LastNamecol = new JFXTreeTableColumn("LastName");
    private final JFXTreeTableColumn<tennants, String> DOBCol = new JFXTreeTableColumn("DateOfBirth");
    private final JFXTreeTableColumn<tennants, String> AddressCol = new JFXTreeTableColumn("Address");
    private final JFXTreeTableColumn<tennants, String> LivingFromCol = new JFXTreeTableColumn("LivingFrom");
    @FXML
    private JFXTreeTableView<tennants> tennantTable;

    @FXML
    private JFXTextField firstNameTxt;
    @FXML
    private JFXComboBox<properties> propertyPicker;
    @FXML
    private JFXTextField lastNameTxt;

    @FXML
    private JFXButton editBtn;
    @FXML
    private JFXButton updateBtn;
    private tennants selectedtennant;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
        editBtn.disableProperty().bind(Bindings.isEmpty(tennantTable.getSelectionModel().getSelectedItems()));
        updateBtn.disableProperty().bind(Bindings.isEmpty(tennantTable.getSelectionModel().getSelectedItems()));
        connectSQL = new Database.connectionDB();
        listTents();
        populateproperty();
    }

    public void populateproperty() {

        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM property ";
        try {
            java.sql.Statement st = connected.createStatement();
            ResultSet rs = st.executeQuery(Sqlite);
            while (rs.next()) {

                propList.add(new properties(rs.getString(1), rs.getString("DoorNo"), rs.getString("FlatNo"), rs.getString("FirstLine"), rs.getString("PostCode"), rs.getString("Town"), rs.getString("DoorNo"), rs.getString("AreaManager"), rs.getString("ContractEnded"), rs.getString("ContractStarted"), rs.getString("Cost"), rs.getString("DayOfMonth"), rs.getString("Landlord")));
            }

            propertyPicker.setItems(propList);
            propertyPicker.setConverter(new StringConverter<properties>() {
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
            Logger.getLogger(manageAreaManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void listTents() {
        //populate tableview using data from database
        updateTable();
        FirstNamecol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("FirstName"));

        tennantIDcol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("tennantID"));

        LastNamecol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("LastName"));

        AddressCol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("Address"));
        DOBCol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("DateOfBirth"));

        LivingFromCol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("LivingFrom"));

        final TreeItem<tennants> root = new RecursiveTreeItem<tennants>(data, RecursiveTreeObject::getChildren);
        tennantTable.getColumns().setAll(tennantIDcol, FirstNamecol, LastNamecol, DOBCol, AddressCol, LivingFromCol);
        tennantTable.setRoot(root);
        tennantTable.setShowRoot(false);

    }

    private void updateTable() {
        data.clear();
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM tennant Right JOIN property ON tennant.LivesAt=property.PropertyID ";
        ResultSet rs;

        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            while (rs.next()) {

                data.add(new tennants(rs.getString(1), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("DateOfBirth"), rs.getString("LivesAt"), rs.getString("DoorNo") + " " + rs.getString("FirstLine"), rs.getString("LivingFrom")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tennantTable.refresh();

    }

    @FXML
    public void getValueForEditing() {
        selectedtennant = tennantTable.getSelectionModel().getSelectedItem().getValue();

        firstNameTxt.setText(tennantTable.getSelectionModel().getSelectedItem().getValue().FirstName);
        lastNameTxt.setText(tennantTable.getSelectionModel().getSelectedItem().getValue().LastName);

    }

    @FXML
    public void editIssue() {
        connected = connectSQL.returnConnection();
        String Sqlite = "UPDATE tennant SET LivesAt=? WHERE tennantID='" + selectedtennant.tennantID + "'";

        try {
            PreparedStatement preparedStatement = (PreparedStatement) connected.prepareStatement(Sqlite);
            preparedStatement.setString(1, propertyPicker.getValue().PropertyID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateTable();
        firstNameTxt.clear();
        lastNameTxt.clear();
    }

}
