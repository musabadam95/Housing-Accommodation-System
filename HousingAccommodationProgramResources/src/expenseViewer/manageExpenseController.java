/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expenseViewer;

import Database.Expense;
import propertyViewer.*;
import Database.connectionDB;
import Database.properties;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class manageExpenseController implements Initializable {

    public ObservableList<Expense> data = FXCollections.<Expense>observableArrayList();
    public ObservableList<String> catergoryList = FXCollections.<String>observableArrayList();
    public ObservableList<properties> propLists = FXCollections.<properties>observableArrayList();

    public properties selectedProp;
    public properties editingProp;
    @FXML
    public JFXTextField expenseNameTxt;
    @FXML
    public JFXTextField costTxt;
    @FXML
    public JFXTextField quantityTxt;
    @FXML
    public JFXTextField totalTxt;
    @FXML
    public JFXComboBox<String> categoryPicker;
    @FXML
    public JFXButton addBtn;
    @FXML
    public JFXButton editBtn;
    @FXML
    public JFXButton delBtn;
    @FXML
    public JFXDatePicker datePaidPicker;

    @FXML
    private JFXTreeTableView<Expense> expenseTable;
    private JFXTreeTableColumn<Expense, String> paidDateCol = new JFXTreeTableColumn("Rent Paid");
    private JFXTreeTableColumn<Expense, String> expenseName = new JFXTreeTableColumn("Expense Item");
    private JFXTreeTableColumn<Expense, String> CostCol = new JFXTreeTableColumn("Cost");
    private JFXTreeTableColumn<Expense, String> quantityCol = new JFXTreeTableColumn("Quantity");
    private JFXTreeTableColumn<Expense, String> totalCostCol = new JFXTreeTableColumn("Total Cost");
    private JFXTreeTableColumn<Expense, String> catergoryCol = new JFXTreeTableColumn("Catergory");
    public connectionDB connectSQL;
    private Connection connected;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @FXML
    private JFXButton updateBtn;
    @FXML
    public JFXComboBox<properties> propertyPicker;
Alert alert;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        // TODO
        editBtn.disableProperty().bind(Bindings.isEmpty(expenseTable.getSelectionModel().getSelectedItems()));
        delBtn.disableProperty().bind(Bindings.isEmpty(expenseTable.getSelectionModel().getSelectedItems()));
        datePaidPicker.setConverter(new StringConverter<LocalDate>() {

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
        connectSQL = new Database.connectionDB();
        try {
             listExpense();
            populateComboBox();
            populateproperty();
           
        } catch (SQLException ex) {
            Logger.getLogger(manageExpenseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(manageExpenseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (selectedProp == null) {
            selectedProp = new properties("", "", "", "other", "other", "other", "other", "other", "other", "other", "other", "", "");

        } else {
 int i = setSelectedValue(selectedProp.PropertyID);
        
        propertyPicker.getSelectionModel().select(i);
        }
        
        
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
        
          quantityTxt.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!quantityTxt.getText().matches("[0-9]")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    quantityTxt.setText("");
                }
            }
        });

        quantityTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                //when focus lost
                if (quantityTxt.getText().matches("[a-z]")) {
                    //when it not matches the pattern (1.0 - 6.0)
                    //set the textField empty
                    quantityTxt.setText("");
                }
            }

        });
        
        
        
        
    }

    public void populateComboBox() throws SQLException {
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM expensecatergory";
        ResultSet rs;
        java.sql.Statement st = connected.createStatement();
        rs = st.executeQuery(Sqlite);
        while (rs.next()) {
            catergoryList.add(rs.getString("catergoryname"));

        }
        categoryPicker.setItems(catergoryList);
    }

    public void populateproperty() {

        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM property ";
        try {
            java.sql.Statement st = connected.createStatement();
            ResultSet rs = st.executeQuery(Sqlite);
            while (rs.next()) {

                propLists.add(new properties(rs.getString(1), rs.getString("DoorNo"), rs.getString("FlatNo"), rs.getString("FirstLine"), rs.getString("PostCode"), rs.getString("Town"), rs.getString("DoorNo"), rs.getString("AreaManager"), rs.getString("ContractEnded"), rs.getString("ContractStarted"), rs.getString("Cost"), rs.getString("DayOfMonth"), rs.getString("Landlord")));
            }
            propLists.add(new properties("", "other", "other", "other", "", "", "", "", "", "", "", "", ""));
            propertyPicker.setItems(propLists);
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

    public void listExpense() throws ParseException {
        //populate tableview using data from database
        updateTable();
        paidDateCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().datePaid;
            }
        });

        expenseName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().expenseName;
            }
        });

        CostCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().amount;
            }
        });

        quantityCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().quantity;
            }
        });
        totalCostCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().totalCost;
            }
        });
        catergoryCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().catergory;
            }
        });
        final TreeItem<Expense> root = new RecursiveTreeItem<Expense>(data, RecursiveTreeObject::getChildren);
        expenseTable.getColumns().setAll(paidDateCol, expenseName, CostCol, quantityCol, totalCostCol, catergoryCol);
        expenseTable.setRoot(root);
        expenseTable.setShowRoot(false);

    }

    private void updateTable() throws ParseException {
        data.clear();
        connected = connectSQL.returnConnection();
        String Sqlite = "";
        if (selectedProp.FirstLine.equals("other")) {
            Sqlite = "SELECT * FROM expenses INNER JOIN expensecatergory ON expenses.catergory=expensecatergory.idexpenseCatergory WHERE expenses.propertyID IS NULL";

        } else {
            Sqlite = "SELECT * FROM expenses INNER JOIN property ON property.PropertyID=expenses.propertyID INNER JOIN expensecatergory ON expenses.catergory=expensecatergory.idexpenseCatergory WHERE expenses.propertyID=' " + selectedProp.getPropertyID() + "'";
        }
        try {
            java.sql.Statement st = connected.createStatement();
            ResultSet rs = st.executeQuery(Sqlite);
            while (rs.next()) {
                data.add(new Expense(rs.getString("idexpenses"), rs.getString("expenseName"), rs.getString("amount"), rs.getString("quantity"), rs.getString("totalCost"), rs.getString("catergoryname"), rs.getString("propertyID"), rs.getString("datePaid")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        expenseTable.refresh();
    }

    @FXML
    public void getValuesforEditing() {

        expenseNameTxt.setText(expenseTable.getSelectionModel().getSelectedItem().getValue().getExpenseName());
        costTxt.setText(expenseTable.getSelectionModel().getSelectedItem().getValue().getAmount());
        quantityTxt.setText(expenseTable.getSelectionModel().getSelectedItem().getValue().getQuantity());
        totalTxt.setText(expenseTable.getSelectionModel().getSelectedItem().getValue().getTotalCost());
        datePaidPicker.setValue(LocalDate.parse(expenseTable.getSelectionModel().getSelectedItem().getValue().getDatePaid(), dateTimeFormatter));
        categoryPicker.setValue(expenseTable.getSelectionModel().getSelectedItem().getValue().getCatergory());
        int i = 0;
        if (expenseTable.getSelectionModel().getSelectedItem().getValue().getPropertyID() == null) {
            i = setSelectedValue(String.valueOf(propertyPicker.getItems().size() - 1));
        } else {
            i = setSelectedValue(expenseTable.getSelectionModel().getSelectedItem().getValue().getPropertyID());
        }
        propertyPicker.getSelectionModel().select(i);
    }

    public int setSelectedValue(String propertyID) {

        for (int i = 0; i < propertyPicker.getItems().size(); i++) {

            if (propLists.get(i).getPropertyID().equals(propertyID)) {

                return i;
            }
        }
        return 0;
    }

    @FXML
    public void editItems() throws SQLException, ParseException {
        String Sqlite = "";

        if (propertyPicker.getValue().FirstLine.equals("other")) {
            System.out.println("ajisd");
            Sqlite = "UPDATE expenses SET expenseName='" + expenseNameTxt.getText() + "',amount='" + costTxt.getText() + "',quantity='" + quantityTxt.getText() + "',totalCost='" + totalTxt.getText() + "',catergory='" + checkCatergoryExist(categoryPicker.getValue()) + "',propertyID= NULL,datePaid='" + datePaidPicker.getValue().toString() + "' WHERE idexpenses='" + expenseTable.getSelectionModel().getSelectedItem().getValue().getExpenseID() + "'";

        } else {

            Sqlite = "UPDATE expenses SET expenseName='" + expenseNameTxt.getText() + "',amount='" + costTxt.getText() + "',quantity='" + quantityTxt.getText() + "',totalCost='" + totalTxt.getText() + "',catergory='" + checkCatergoryExist(categoryPicker.getValue()) + "',propertyID='" + propertyPicker.getValue().PropertyID + "',datePaid='" + datePaidPicker.getValue().toString() + "' WHERE idexpenses='" + expenseTable.getSelectionModel().getSelectedItem().getValue().getExpenseID() + "'";
        }
        java.sql.Statement stmt = connected.createStatement();
        stmt.executeUpdate(Sqlite);
        updateTable();
    }

    @FXML
    public void addNewPayment() throws SQLException, ParseException {
         if(expenseNameTxt.getText().isEmpty()||quantityTxt.getText().isEmpty()||costTxt.getText().isEmpty()||propertyPicker.equals(null)||datePaidPicker.equals(null)){
              alert.setContentText("Check if all details have been filled");
                            alert.showAndWait();
                            return;
            }
        
        String catID = checkCatergoryExist(categoryPicker.getValue());
        String Sqlite = "INSERT INTO expenses (expenseName,amount,quantity,totalCost,catergory,propertyID,datePaid) Values ('" + expenseNameTxt.getText() + "','" + costTxt.getText() + "','" + quantityTxt.getText() + "','" + totalTxt.getText() + "','" + catID + "','" + propertyPicker.getValue().PropertyID + "','" + datePaidPicker.getValue().toString() + "')";
        java.sql.Statement stmt = connected.createStatement();
        stmt.executeUpdate(Sqlite);
        updateTable();
    }

    private String checkCatergoryExist(String catergory) throws SQLException {
        String Sqlite = "SELECT * FROM expensecatergory WHERE catergoryname='" + catergory + "'";

        java.sql.Statement stmt = connected.createStatement();

        ResultSet rs = stmt.executeQuery(Sqlite);

        if (rs.next()) {
            return rs.getString("idexpenseCatergory");
        } else {
            Sqlite = "INSERT INTO expensecatergory (catergoryname) Values ('" + catergory + "')";

            stmt = connected.createStatement();
            stmt.executeUpdate(Sqlite);

            Sqlite = "SELECT * FROM expensecatergory WHERE catergoryname='" + catergory + "'";
            stmt = connected.createStatement();
            rs = stmt.executeQuery(Sqlite);
            rs.next();
            return rs.getString("idexpenseCatergory");

        }

    }

    @FXML
    public void generateTotal() {
        try {
            Double cost = Double.parseDouble(costTxt.getText());
            Double quantity = Double.parseDouble(quantityTxt.getText());
            totalTxt.setText(Double.toString(cost * quantity));
        } catch (Exception e) {

        }
    }

    @FXML
    public void deleteItem() throws SQLException, ParseException {

        String expenseID = expenseTable.getSelectionModel().getSelectedItem().getValue().getExpenseID();
        String Sqlite = "DELETE FROM expenses WHERE idexpenses='" + expenseID + "'";
        java.sql.Statement stmt = connected.createStatement();
        stmt.executeUpdate(Sqlite);
        updateTable();

    }
}
