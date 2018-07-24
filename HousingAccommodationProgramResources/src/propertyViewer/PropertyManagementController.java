/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertyViewer;

import Database.areaManager;
import Database.connectionDB;
import Database.properties;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class PropertyManagementController implements Initializable {

    @FXML
    private JFXTreeTableView<properties> treeviewProp;
        private final JFXTreeTableColumn<properties, String> FlatNocol = new JFXTreeTableColumn("Flat");
            private final JFXTreeTableColumn<properties, String> DoorNoIDcol = new JFXTreeTableColumn("Door No");
    private final JFXTreeTableColumn<properties, String> propertyIDcol = new JFXTreeTableColumn("PropertyID");
    private final JFXTreeTableColumn<properties, String> AddressCol = new JFXTreeTableColumn("Address");
    private final JFXTreeTableColumn<properties, String> Towncol = new JFXTreeTableColumn("Town");
    private final JFXTreeTableColumn<properties, String> AreaManagercol = new JFXTreeTableColumn("Area Manager");
    private final JFXTreeTableColumn<properties, String> ContractStarCol = new JFXTreeTableColumn("Contract Started");
    private final JFXTreeTableColumn<properties, String> ContractEndedCol = new JFXTreeTableColumn("Contract Ends");
    public connectionDB connectSQL;
    private Connection connected;
    public ObservableList<properties> data = FXCollections.<properties>observableArrayList();
    @FXML
    private JFXButton deletePropBtn;
    @FXML
    private JFXButton payRentbtn;
    @FXML
    private JFXButton returnBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        deletePropBtn.disableProperty().bind(Bindings.isEmpty(treeviewProp.getSelectionModel().getSelectedItems()));
        payRentbtn.disableProperty().bind(Bindings.isEmpty(treeviewProp.getSelectionModel().getSelectedItems()));
        returnBtn.disableProperty().bind(Bindings.isEmpty(treeviewProp.getSelectionModel().getSelectedItems()));

        connectSQL = new Database.connectionDB();
        listTents();
    }

    public void listTents() {
        //populate tableview using data from database
        updateTable();
FlatNocol.setCellValueFactory(new TreeItemPropertyValueFactory<properties, String>("FlatNo"));
DoorNoIDcol.setCellValueFactory(new TreeItemPropertyValueFactory<properties, String>("DoorNo"));
        propertyIDcol.setCellValueFactory(new TreeItemPropertyValueFactory<properties, String>("PropertyID"));

        AddressCol.setCellValueFactory(new TreeItemPropertyValueFactory<properties, String>("FirstLine"));

        Towncol.setCellValueFactory(new TreeItemPropertyValueFactory<properties, String>("Town"));

        AreaManagercol.setCellValueFactory(new TreeItemPropertyValueFactory<properties, String>("AreaManager"));

        ContractStarCol.setCellValueFactory(new TreeItemPropertyValueFactory<properties, String>("ContractStarted"));


        ContractEndedCol.setCellValueFactory(new TreeItemPropertyValueFactory<properties, String>("ContractEnded"));

        final TreeItem<properties> root = new RecursiveTreeItem<properties>(data, RecursiveTreeObject::getChildren);
        treeviewProp.getColumns().setAll(propertyIDcol,FlatNocol,DoorNoIDcol, AddressCol, Towncol, AreaManagercol, ContractStarCol, ContractEndedCol);
        treeviewProp.setRoot(root);
        treeviewProp.setShowRoot(false);

    }

    private void updateTable() {
        data.clear();
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM property inner join areamanager ON AreaManager=idAreaManager WHERE ContractEnded >= now()";
        ResultSet rs;

        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            while (rs.next()) {
areaManager areaManager=new areaManager(rs.getString("AreaManager"),rs.getString("FirstName"),rs.getString("LastName"));
                data.add(new properties(rs.getString(1), rs.getString("DoorNo"), rs.getString("FlatNo"), rs.getString("FirstLine"), rs.getString("PostCode"), rs.getString("Town"), rs.getString("DoorNo"), areaManager, rs.getString("ContractEnded"), rs.getString("ContractStarted"), rs.getString("Cost"), rs.getString("DayOfMonth"), rs.getString("Landlord")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

        treeviewProp.refresh();

    }

    @FXML
    public void addNewProperty() throws IOException {

        addingProperty addingProperty = new addingProperty();
        Stage primaryStage = new Stage();
        addingProperty.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            updateTable();

        });
    }

    @FXML
    public void manageAreaManagers() throws IOException {

        manageAreaManager manageAreaManager = new manageAreaManager();
        Stage primaryStage = new Stage();
        manageAreaManager.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            updateTable();

        });
    }

    @FXML
    private void deleteTennant(ActionEvent event) {
        try {
            TreeItem<properties> property = treeviewProp.getSelectionModel().getSelectedItem();
            String query = "DELETE FROM property WHERE PropertyID='" + property.getValue().PropertyID.toString() + "'";
            connected = connectSQL.returnConnection();
            java.sql.Statement st = connected.createStatement();
            st.executeUpdate(query);
            updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void returnProperty(ActionEvent event) throws ParseException {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String time = sdf.format(date);

            TreeItem<properties> property = treeviewProp.getSelectionModel().getSelectedItem();
            String query = "UPDATE property SET ContractEnded='" + time + "' WHERE PropertyID='" + property.getValue().PropertyID.toString() + "'";
            connected = connectSQL.returnConnection();
            java.sql.Statement st = connected.createStatement();
            st.executeUpdate(query);
            updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void payRent() throws IOException {
        rentPaying payNewRent = new rentPaying();
        payNewRent.prop = treeviewProp.getSelectionModel().getSelectedItem().getValue();
        Stage primaryStage = new Stage();
        payNewRent.start(primaryStage);
        primaryStage.setOnHidden(e -> {

        });
    }

}
