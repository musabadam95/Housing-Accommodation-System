/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilityViewer;

import Database.connectionDB;
import Database.properties;
import Database.utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import propertyViewer.PropertyManagementController;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class utilityViewerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public connectionDB connectSQL;
    private Connection connected;
    public ObservableList<utility> data = FXCollections.<utility>observableArrayList();
    properties selectedProp;
    @FXML
    private JFXTreeTableView<utility> treeviewExpense;
    private final JFXTreeTableColumn<utility, String> property = new JFXTreeTableColumn("Property");
    private final JFXTreeTableColumn<utility, String> elecCompany = new JFXTreeTableColumn("Electric Supplier");
    private final JFXTreeTableColumn<utility, String> elecAccountNo = new JFXTreeTableColumn("AccountNo");
    private final JFXTreeTableColumn<utility, String> elecCost = new JFXTreeTableColumn("Cost P/m");
    private final JFXTreeTableColumn<utility, String> gasCompany = new JFXTreeTableColumn("Gas Supplier");
    private final JFXTreeTableColumn<utility, String> gasAccountNo = new JFXTreeTableColumn("AccountNo");
    private final JFXTreeTableColumn<utility, String> gasCost = new JFXTreeTableColumn("Cost P/m");
    private final JFXTreeTableColumn<utility, String> waterCompany = new JFXTreeTableColumn("Water Supplier");
    private final JFXTreeTableColumn<utility, String> waterAccountNo = new JFXTreeTableColumn("AccountNo");
    private final JFXTreeTableColumn<utility, String> waterCost = new JFXTreeTableColumn("Cost P/m");
    private final JFXTreeTableColumn<utility, String> cTaxCompany = new JFXTreeTableColumn("Assigned Council");
    private final JFXTreeTableColumn<utility, String> cTaxAccountNo = new JFXTreeTableColumn("AccountNo");
    private final JFXTreeTableColumn<utility, String> cTaxCost = new JFXTreeTableColumn("Cost P/m");

    @FXML
    private JFXButton ManagePropertyExpenseBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ManagePropertyExpenseBtn.disableProperty().bind(Bindings.isEmpty(treeviewExpense.getSelectionModel().getSelectedItems()));

        connectSQL = new Database.connectionDB();
        listTents();

    }

    public void listTents() {
        //populate tableview using data from database
        updateTable();

        property.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("address"));
        elecCompany.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("elecCompany"));
        elecAccountNo.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("elecAccountNo"));
        elecCost.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("elecCost"));
        gasCompany.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("gasCompany"));
        gasAccountNo.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("gasAccountNo"));
        gasCost.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("gasCost"));
        waterCompany.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("waterCompany"));
        waterAccountNo.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("waterAccountNo"));
        waterCost.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("waterCost"));
        cTaxCompany.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("cTaxCompany"));
        cTaxAccountNo.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("cTaxAccountNo"));
        cTaxCost.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("cTaxCost"));

        final TreeItem<utility> root = new RecursiveTreeItem<utility>(data, RecursiveTreeObject::getChildren);
        treeviewExpense.getColumns().setAll(property, elecCompany, elecAccountNo, elecCost, gasCompany, gasAccountNo, gasCost, waterCompany, waterAccountNo, waterCost, cTaxCompany, cTaxAccountNo, cTaxCost);
        treeviewExpense.setRoot(root);
        treeviewExpense.setShowRoot(false);

    }

    private void updateTable() {
        data.clear();
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM utilities INNER JOIN property ON utilities.PropertyID=property.PropertyID";

        ResultSet rs;

        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            properties prop;
            while (rs.next()) {
                prop = new properties(rs.getString("PropertyID"), rs.getString("DoorNo") + " " + rs.getString("FlatNo") + " " + rs.getString("FirstLine"), "", "");
                data.add(new utility(rs.getString("PropertyID"), prop, rs.getString("EnegComp"), rs.getString("EnegAcntNo"), rs.getString("EnegCost"), rs.getString("GasComp"), rs.getString("GasAcntNo"), rs.getString("GasCost"), rs.getString("WaterComp"), rs.getString("WaterAcntNo"), rs.getString("WaterCost"), rs.getString("CTaxBorough"), rs.getString("CTaxAccntNo"), rs.getString("CTaxCost")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

        treeviewExpense.refresh();

    }

    @FXML
    public void manageUtilitys() throws IOException, SQLException {
        manageUtility mExpense = new manageUtility();
        ResultSet rs;

        String PropertyID = treeviewExpense.getSelectionModel().getSelectedItem().getValue().propertyID;
        String Sqlite = "SELECT * FROM property WHERE PropertyID='" + PropertyID + "'";
        java.sql.Statement st = connected.createStatement();
        rs = st.executeQuery(Sqlite);
        properties prop = null;
        while (rs.next()) {

            prop = new properties(rs.getString(1), rs.getString("DoorNo"), rs.getString("FlatNo"), rs.getString("FirstLine"), rs.getString("PostCode"), rs.getString("Town"), rs.getString("DoorNo"), rs.getString("AreaManager"), rs.getString("ContractEnded"), rs.getString("ContractStarted"), rs.getString("Cost"), rs.getString("DayOfMonth"), rs.getString("Landlord"));

        }
        mExpense.prop = prop;
        Stage primaryStage = new Stage();
        mExpense.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            updateTable();
        });
        treeviewExpense.refresh();
    }

}
