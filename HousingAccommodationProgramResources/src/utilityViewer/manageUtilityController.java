/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilityViewer;

import propertyViewer.*;
import Database.connectionDB;
import Database.properties;
import Database.utility;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.StringConverter;

public class manageUtilityController implements Initializable {

    public ObservableList<utility> data = FXCollections.<utility>observableArrayList();
    public ObservableList<properties> propertyList = FXCollections.<properties>observableArrayList();
    public ObservableList<String> utilityList = FXCollections.<String>observableArrayList();
    properties selectedProp;
    @FXML
    JFXTextField costTxt;
    JFXButton addBtn;
    @FXML
    JFXButton editBtn;
    @FXML
    JFXButton delBtn;

    @FXML
    private JFXTreeTableView<utility> expenseTable;
    private final JFXTreeTableColumn<utility, String> typeCol = new JFXTreeTableColumn("Rent Paid");
    private final JFXTreeTableColumn<utility, String> companyCol = new JFXTreeTableColumn("Expense Item");
    private final JFXTreeTableColumn<utility, String> CostCol = new JFXTreeTableColumn("Cost");
    private final JFXTreeTableColumn<utility, String> accountNoCol = new JFXTreeTableColumn("Quantity");

    public connectionDB connectSQL;
    private Connection connected;
    @FXML
    private JFXButton updateBtn;
    @FXML
    private JFXTextField utilityType;
    @FXML
    private JFXTextField companyTxt;
    @FXML
    private JFXTextField accntNoTxt;
Alert alert;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        editBtn.disableProperty().bind(Bindings.isEmpty(expenseTable.getSelectionModel().getSelectedItems()));
        delBtn.disableProperty().bind(Bindings.isEmpty(expenseTable.getSelectionModel().getSelectedItems()));
        connectSQL = new Database.connectionDB();
         costTxt.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!costTxt.getText().matches("[0-9]+(?:\\.[0-9]{0,2})?")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    costTxt.setText("");
                }
            }
        });

        costTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                //when focus lost
                if (costTxt.getText().matches("[a-z]")) {
                    //when it not matches the pattern (1.0 - 6.0)
                    //set the textField empty
                    costTxt.setText("");
                }
            }

        });
        try {
            listExpense();
        } catch (ParseException ex) {
            Logger.getLogger(manageUtilityController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

  

    public void listExpense() throws ParseException {
        //populate tableview using data from database
        updateTable();
        
         typeCol.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("type"));
          companyCol.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("Company"));
           CostCol.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("Cost"));
            accountNoCol.setCellValueFactory(new TreeItemPropertyValueFactory<utility, String>("AccountNo"));
          

        final TreeItem<utility> root = new RecursiveTreeItem<utility>(data, RecursiveTreeObject::getChildren);
        expenseTable.getColumns().setAll(typeCol, companyCol, CostCol, accountNoCol);
        expenseTable.setRoot(root);
        expenseTable.setShowRoot(false);

    }

    private void updateTable() throws ParseException {
        data.clear();
        connected = connectSQL.returnConnection();
        System.out.println(selectedProp.PropertyID.equals(null));
        String Sqlite = "SELECT * FROM utilities INNER JOIN property ON property.PropertyID=utilities.PropertyID WHERE utilities.PropertyID=' " + selectedProp.getPropertyID() + "'";
        ResultSet rs;
        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            while (rs.next()) {
                data.add(new utility("electric", selectedProp.getPropertyID(), selectedProp, rs.getString("EnegComp"), rs.getString("EnegAcntNo"), rs.getString("EnegCost")));
                data.add(new utility("gas", selectedProp.getPropertyID(), selectedProp, rs.getString("GasComp"), rs.getString("GasAcntNo"), rs.getString("GasCost")));
                data.add(new utility("water", selectedProp.getPropertyID(), selectedProp, rs.getString("WaterComp"), rs.getString("WaterAcntNo"), rs.getString("WaterCost")));
                data.add(new utility("Council Tax", selectedProp.getPropertyID(), selectedProp, rs.getString("CTaxBorough"), rs.getString("CTaxAccntNo"), rs.getString("CTaxCost")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

        expenseTable.refresh();
    }

    @FXML
    public void getValuesforEditing() {
        costTxt.setText(expenseTable.getSelectionModel().getSelectedItem().getValue().getCost());
        utilityType.setText(expenseTable.getSelectionModel().getSelectedItem().getValue().getType());
        companyTxt.setText(expenseTable.getSelectionModel().getSelectedItem().getValue().getCompany());
        accntNoTxt.setText(expenseTable.getSelectionModel().getSelectedItem().getValue().getAccountNo());
      
    }

    @FXML
    public void editItems() throws SQLException, ParseException {
           if(companyTxt.getText().isEmpty()||accntNoTxt.getText().isEmpty()||costTxt.getText().isEmpty()){
              alert.setContentText("Check if all details have been filled");
                            alert.showAndWait();
                            return;
            }
        String Sqlite = "";
        String SqliteB = "";
        java.sql.Statement stmt = connected.createStatement();

            switch (utilityType.getText()) {
                case "gas":
                    Sqlite = "UPDATE utilities SET GasComp='" + companyTxt.getText() + "',GasAcntNo='" + accntNoTxt.getText() + "',GasCost='" + costTxt.getText() + "'WHERE PropertyID='" + selectedProp.getPropertyID() + "'";
                    stmt.executeUpdate(Sqlite);
                    break;
                case "electric":
                    Sqlite = "UPDATE utilities SET EnegComp='" + companyTxt.getText() + "',EnegAcntNo='" + accntNoTxt.getText() + "',EnegCost='" + costTxt.getText() + "'WHERE PropertyID='" + selectedProp.getPropertyID() + "'";
                    stmt.executeUpdate(Sqlite);
                    break;
                case "water":
                    Sqlite = "UPDATE utilities SET WaterComp='" + companyTxt.getText() + "',WaterAcntNo='" + accntNoTxt.getText() + "',WaterCost='" + costTxt.getText() + "'WHERE PropertyID='" + selectedProp.getPropertyID() + "'";
                    stmt.executeUpdate(Sqlite);
                    break;
                case "Council Tax":
                    Sqlite = "UPDATE utilities SET CTaxBorough='" + companyTxt.getText() + "',CTaxAccntNo='" + accntNoTxt.getText() + "',CTaxCost='" + costTxt.getText() + "'WHERE PropertyID='" + selectedProp.getPropertyID() + "'";
                    stmt.executeUpdate(Sqlite);
                    break;
            }
        

        updateTable();
    }

    @FXML
    public void deleteItem() throws SQLException, ParseException {
        java.sql.Statement stmt = connected.createStatement();
        String Sqlite = "";
        switch (expenseTable.getSelectionModel().getSelectedItem().getValue().getType()) {
            case "gas":
                Sqlite = "UPDATE utilities SET GasComp='awaiting',GasAcntNo='awaiting',GasCost='0.0'WHERE PropertyID='" + expenseTable.getSelectionModel().getSelectedItem().getValue().selectedProp.getPropertyID() + "'";
                stmt.executeUpdate(Sqlite);
                break;
            case "electric":
                Sqlite = "UPDATE utilities SET EnegComp='awaiting',EnegAcntNo='awaiting',EnegCost='0.0'WHERE PropertyID='" + expenseTable.getSelectionModel().getSelectedItem().getValue().selectedProp.getPropertyID() + "'";
                stmt.executeUpdate(Sqlite);
                break;
            case "water":
                Sqlite = "UPDATE utilities SET WaterComp='awaiting',WaterAcntNo='awaiting',WaterCost='0.0'WHERE PropertyID='" + expenseTable.getSelectionModel().getSelectedItem().getValue().selectedProp.getPropertyID() + "'";
                stmt.executeUpdate(Sqlite);
                break;
            case "Council Tax":
                Sqlite = "UPDATE utilities SET CTaxBorough='awaiting',CTaxAccntNo='awaiting',CTaxCost='0.0'WHERE PropertyID='" + expenseTable.getSelectionModel().getSelectedItem().getValue().selectedProp.getPropertyID() + "'";
                stmt.executeUpdate(Sqlite);
                break;
        }

        updateTable();

    }

}
