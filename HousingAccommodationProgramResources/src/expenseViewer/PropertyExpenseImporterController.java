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
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.swing.JFileChooser;

public class PropertyExpenseImporterController implements Initializable {

    public ObservableList<Expense> data = FXCollections.<Expense>observableArrayList();
    public ObservableList<String> catergoryList = FXCollections.<String>observableArrayList();
 public ObservableList<properties> propLists = FXCollections.<properties>observableArrayList();
    properties selectedProp;
    @FXML
    JFXTextField expenseNameTxt;
    @FXML
    JFXTextField costTxt;
    @FXML
    JFXTextField quantityTxt;
    @FXML
    JFXTextField totalTxt;
    @FXML
    JFXComboBox<String> categoryPicker;
    JFXButton addBtn;
    @FXML
    JFXButton editBtn;
    JFXButton delBtn;
    @FXML
    JFXDatePicker datePaidPicker;
    public String csvFormat = "";
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
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @FXML
    private JFXButton updateBtn;
    @FXML
    private StackPane stack;
    @FXML
    private JFXButton editBtn1;
    @FXML
    private JFXButton delBtn1;
    @FXML
    private JFXDialogLayout dialogLayout;
    @FXML
    private JFXDialog dialog;
    @FXML
    private JFXComboBox<properties> propertyPicker;
    Alert alert;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        // TODO
        editBtn.disableProperty().bind(Bindings.isEmpty(expenseTable.getSelectionModel().getSelectedItems()));

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
        datePaidPicker.setConverter(new StringConverter<LocalDate>() {
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
        try {
            populateComboBox();
            populateproperty();
            listExpense();
        } catch (SQLException ex) {
            Logger.getLogger(manageExpenseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(manageExpenseController.class.getName()).log(Level.SEVERE, null, ex);
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
        updateTable();

    }

    private void updateTable() throws ParseException {
        data.clear();
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM expenses WHERE propertyID is null";
        ResultSet rs;
        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            while (rs.next()) {
                data.add(new Expense(rs.getString("idexpenses"), rs.getString("expenseName"), rs.getString("amount"), rs.getString("quantity"), rs.getString("totalCost"), "", rs.getString("propertyID"), rs.getString("datePaid")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    @FXML
    public void editItems() throws SQLException, ParseException {

        String Sqlite = "UPDATE expenses SET expenseName='" + expenseNameTxt.getText() + "',amount='" + costTxt.getText() + "',quantity='" + quantityTxt.getText() + "',totalCost='" + totalTxt.getText() + "',catergory='" + checkCatergoryExist(categoryPicker.getValue()) + "',propertyID='" + propertyPicker.getValue().PropertyID + "',datePaid='" + datePaidPicker.getValue().toString() + "' WHERE idexpenses='" + expenseTable.getSelectionModel().getSelectedItem().getValue().getExpenseID() + "'";
        java.sql.Statement stmt = connected.createStatement();
        stmt.executeUpdate(Sqlite);
        updateTable();
    }

    
    private String checkCatergoryExist(String catergory) throws SQLException {
        catergory = catergory.toLowerCase();
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
    public void importTennants() throws FileNotFoundException, IOException, ParseException {
stack.toFront();

        JFileChooser chooser = new JFileChooser();
        JFXComboBox<String> jfxCombo = new JFXComboBox<>();
        jfxCombo.applyCss();
        jfxCombo.getItems().add("HSBC");
        jfxCombo.getItems().add("Llyods TSB");
        JFXButton selBtn = new JFXButton("Select");
        selBtn.setStyle("-fx-background-color: green");
        selBtn.setStyle(" -fx-text-fill: white;");
        selBtn.setButtonType(JFXButton.ButtonType.RAISED);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setActions(jfxCombo, selBtn);
          dialogLayout.setStyle("-fx-background-color: #0f3b68");
        JFXDialog dialog = new JFXDialog(this.stack, dialogLayout, JFXDialog.DialogTransition.CENTER);
     
      
        selBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent __) {

                csvFormat = jfxCombo.getValue();

                dialog.close();
                try {
                    int choice = chooser.showOpenDialog(chooser);
                    if (choice != JFileChooser.APPROVE_OPTION) {
                        return;
                    }

                    File chosenFile = chooser.getSelectedFile();
                    String filePath = chosenFile.getAbsolutePath();
                    CSVReader csvReader = new CSVReader(new FileReader(filePath));
                    String[] row = null;
                    String Sqlite = "";
                    java.sql.Statement stmt = connected.createStatement();
                    Double cost = 0.0;
                    row = csvReader.readNext();

                    switch (csvFormat) {
                        case "HSBC":
                            alert.setContentText("ENSURE THAT THE COLUMNS ARE IN THE FOLLOWING ORDER: \n 1. Date Paid 2. Description 3. Cost");
                            alert.showAndWait();
                            while ((row = csvReader.readNext()) != null) {
                                try {
                                    cost = -1 * Double.parseDouble(row[2]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    break;
                                }
                                
                                String[] result = checkExists(row[1]);//check if expense exists already
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");    
                                Date date = formatter.parse(row[0]);

                                if (result[0].equals("")) {
                                    Sqlite = "INSERT INTO expenses (expenseName,amount,quantity,totalCost,datePaid) Values ('" + row[1] + "','" + cost + "','1','" + cost + "','" + row[0] + "')";
                                } else {
                                    Sqlite = "INSERT INTO expenses (expenseName,amount,propertyID,catergory,quantity,totalCost,datePaid) Values ('" + row[1] + "','" + cost + "'," + result[0] + "," + result[1] + ",'1','" + cost + "','" + row[0] + "')";
                                }
                                stmt.executeUpdate(Sqlite);
                            }

                        case "Llyods TSB":
                            while ((row = csvReader.readNext()) != null) {
                                try {
                                    cost = -1 * Double.parseDouble(row[2]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    break;
                                }
                                String[] result = checkExists(row[1]);//check if expense exists already
                                System.out.println(result[0]);
                                if (result[0].equals("")) {
                                    Sqlite = "INSERT INTO expenses (expenseName,amount,quantity,totalCost,datePaid) Values ('" + row[1] + "','" + cost + "','1','" + cost + "','" + row[0] + "')";
                                } else {
                                    Sqlite = "INSERT INTO expenses (expenseName,amount,propertyID,catergory,quantity,totalCost,datePaid) Values ('" + row[1] + "','" + cost + "'," + result[0] + "," + result[1] + ",'1','" + cost + "','" + row[0] + "')";
                                }
                                stmt.executeUpdate(Sqlite);
                            }
                    }
stack.toBack();
                    updateTable();
                  
                } catch (SQLException ex) {
                    Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
            
                } catch (FileNotFoundException ex) {
                    System.out.println("File Not Found");
                    
                } catch (IOException ex) {
                    Logger.getLogger(PropertyExpenseImporterController.class.getName()).log(Level.SEVERE, null, ex);
                   
                } catch (ParseException ex) {
                     alert.setContentText("Column Mismatch");
                            alert.showAndWait();
                         
                   return;
                }
            }
        });

        dialog.show();
        dialog.setOnDialogClosed(e -> {
            stack.toBack();
        });

    }

    public String[] checkExists(String expenseName) throws SQLException {
        String[] result = new String[2];
        java.sql.Statement stmt = connected.createStatement();
        System.out.println(expenseName);
        String query = "SELECT utilities.PropertyID FROM utilities WHERE '" + expenseName + "' LIKE concat('%',utilities.EnegAcntNo,'%') OR '" + expenseName + "' LIKE CONCAT('%',utilities.GasAcntNo,'%')OR '" + expenseName + "' LIKE CONCAT('%',utilities.WaterAcntNo,'%') OR '" + expenseName + "' LIKE CONCAT('%',utilities.CTaxAccntNo,'%')";
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        if (rs.next()) {
            result[0] = rs.getString("PropertyID");
            result[1] = checkCatergoryExist("utility");
        } else {
            rs = stmt.getResultSet();
            result[0] = "";
            result[1] = "";
        }
        return result;
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
