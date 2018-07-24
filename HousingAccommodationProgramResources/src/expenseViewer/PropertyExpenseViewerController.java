/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expenseViewer;

import Database.Expense;
import Database.connectionDB;
import Database.properties;
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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;
import propertyViewer.PropertyManagementController;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class PropertyExpenseViewerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public connectionDB connectSQL;
    private Connection connected;
    public ObservableList<Expense> data = FXCollections.<Expense>observableArrayList();
    properties selectedProp;
    @FXML
    private JFXTreeTableView<Expense> treeviewExpense;
    private JFXTreeTableColumn<Expense, String> property = new JFXTreeTableColumn("Property");
    private JFXTreeTableColumn<Expense, String> january = new JFXTreeTableColumn("January");
    private JFXTreeTableColumn<Expense, String> february = new JFXTreeTableColumn("February");
    private JFXTreeTableColumn<Expense, String> march = new JFXTreeTableColumn("March");
    private JFXTreeTableColumn<Expense, String> april = new JFXTreeTableColumn("April");
    private JFXTreeTableColumn<Expense, String> may = new JFXTreeTableColumn("May");
    private JFXTreeTableColumn<Expense, String> june = new JFXTreeTableColumn("June");
    private JFXTreeTableColumn<Expense, String> july = new JFXTreeTableColumn("July");
    private JFXTreeTableColumn<Expense, String> august = new JFXTreeTableColumn("August");
    private JFXTreeTableColumn<Expense, String> september = new JFXTreeTableColumn("Sepetember");
    private JFXTreeTableColumn<Expense, String> october = new JFXTreeTableColumn("October");
    private JFXTreeTableColumn<Expense, String> november = new JFXTreeTableColumn("November");
    private JFXTreeTableColumn<Expense, String> december = new JFXTreeTableColumn("December");

    @FXML
    private JFXButton importCSV;
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
        property.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().Property;
            }
        });

        january.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().January;
            }
        });

        february.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().February;
            }
        });

        march.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().March;
            }
        });

        april.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().April;
            }
        });

        may.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().May;
            }
        });
        june.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().June;
            }
        });
        july.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().July;
            }
        });
        august.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().August;
            }
        });
        september.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().Sepetember;
            }
        });
        october.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().October;
            }
        });
        november.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().November;
            }
        });
        december.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Expense, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Expense, String> param) {
                return param.getValue().getValue().December;
            }
        });

        final TreeItem<Expense> root = new RecursiveTreeItem<Expense>(data, RecursiveTreeObject::getChildren);
        treeviewExpense.getColumns().setAll(property, january, february, march, april, may, june, july, august, september, october, november, december);
        treeviewExpense.setRoot(root);
        treeviewExpense.setShowRoot(false);

    }

    private void updateTable() {
        data.clear();
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT\n"
                + "  property.propertyID,FlatNo,DoorNo, FirstLine,\n"
                + "  sum(if(month(datePaid) = 1, totalCost, 0))  AS JANUARY,\n"
                + "  sum(if(month(datePaid) = 2, totalCost, 0))  AS FEBRUARY,\n"
                + "  sum(if(month(datePaid) = 3, totalCost, 0))  AS MARCH,\n"
                + "  sum(if(month(datePaid) = 4, totalCost, 0))  AS APRIL,\n"
                + "  sum(if(month(datePaid) = 5, totalCost, 0))  AS MAY,\n"
                + "  sum(if(month(datePaid) = 6, totalCost, 0))  AS JUNE,\n"
                + "  sum(if(month(datePaid) = 7, totalCost, 0))  AS JULY,\n"
                + "  sum(if(month(datePaid) = 8, totalCost, 0))  AS AUGUST,\n"
                + "  sum(if(month(datePaid) = 9, totalCost, 0))  AS SEPTEMBER,\n"
                + "  sum(if(month(datePaid) = 10, totalCost, 0)) AS OCTOBER,\n"
                + "  sum(if(month(datePaid) = 11, totalCost, 0)) AS NOVEMBER,\n"
                + "  sum(if(month(datePaid) = 12, totalCost, 0)) AS 'DECEMBER'\n"
                + "FROM expenses right JOIN property ON expenses.propertyID=property.PropertyID\n"
                + "GROUP BY propertyID;";

        ResultSet rs;

        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);

            while (rs.next()) {
                if (rs.getString("PropertyID").isEmpty()) {
                    data.add(new Expense("Other", rs.getString("FlatNo") + rs.getString("DoorNo") + rs.getString("FirstLine"), rs.getString("JANUARY"), rs.getString("FEBRUARY"), rs.getString("MARCH"), rs.getString("APRIL"), rs.getString("MAY"), rs.getString("JUNE"), rs.getString("JULY"), rs.getString("AUGUST"), rs.getString("SEPTEMBER"), rs.getString("OCTOBER"), rs.getString("NOVEMBER"), rs.getString("DECEMBER")));

                } else {
                    data.add(new Expense(rs.getString("PropertyID"),rs.getString("DoorNo")+ rs.getString("FlatNo")   +" "+ rs.getString("FirstLine"), rs.getString("JANUARY"), rs.getString("FEBRUARY"), rs.getString("MARCH"), rs.getString("APRIL"), rs.getString("MAY"), rs.getString("JUNE"), rs.getString("JULY"), rs.getString("AUGUST"), rs.getString("SEPTEMBER"), rs.getString("OCTOBER"), rs.getString("NOVEMBER"), rs.getString("DECEMBER")));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Sqlite = "SELECT\n"
                    + "  sum(if(month(datePaid) = 1, totalCost, 0))  AS JANUARY,\n"
                    + "  sum(if(month(datePaid) = 2, totalCost, 0))  AS FEBRUARY,\n"
                    + "  sum(if(month(datePaid) = 3, totalCost, 0))  AS MARCH,\n"
                    + "  sum(if(month(datePaid) = 4, totalCost, 0))  AS APRIL,\n"
                    + "  sum(if(month(datePaid) = 5, totalCost, 0))  AS MAY,\n"
                    + "  sum(if(month(datePaid) = 6, totalCost, 0))  AS JUNE,\n"
                    + "  sum(if(month(datePaid) = 7, totalCost, 0))  AS JULY,\n"
                    + "  sum(if(month(datePaid) = 8, totalCost, 0))  AS AUGUST,\n"
                    + "  sum(if(month(datePaid) = 9, totalCost, 0))  AS SEPTEMBER,\n"
                    + "  sum(if(month(datePaid) = 10, totalCost, 0)) AS OCTOBER,\n"
                    + "  sum(if(month(datePaid) = 11, totalCost, 0)) AS NOVEMBER,\n"
                    + "  sum(if(month(datePaid) = 12, totalCost, 0)) AS 'DECEMBER'\n"
                    + "FROM expenses WHERE propertyID is null;";

            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            while (rs.next()) {

                data.add(new Expense("Other", "Other" + "" + "Expenses", rs.getString("JANUARY"), rs.getString("FEBRUARY"), rs.getString("MARCH"), rs.getString("APRIL"), rs.getString("MAY"), rs.getString("JUNE"), rs.getString("JULY"), rs.getString("AUGUST"), rs.getString("SEPTEMBER"), rs.getString("OCTOBER"), rs.getString("NOVEMBER"), rs.getString("DECEMBER")));

            }
        } catch (SQLException ex) {

        }
        treeviewExpense.refresh();

    }

    @FXML
    public void managePropExpense() throws IOException, SQLException {
        manageExpense mExpense = new manageExpense();
        ResultSet rs;
        properties prop = new properties("other", "other", "other", "other", "other", "other", "other", "", "", "", "", "", "");
        String PropertyID = treeviewExpense.getSelectionModel().getSelectedItem().getValue().propertyID.getValue();

        if (PropertyID.equals("Other")) {
           prop = new properties("other", "other", "other", "other", "other", "other", "other", "", "", "", "", "", "");

        } else {
            String Sqlite = "SELECT * FROM property WHERE PropertyID='" + PropertyID + "'";
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);

            while (rs.next()) {

                prop = new properties(rs.getString(1), rs.getString("DoorNo"), rs.getString("FlatNo"), rs.getString("FirstLine"), rs.getString("PostCode"), rs.getString("Town"), rs.getString("DoorNo"), rs.getString("AreaManager"), rs.getString("ContractEnded"), rs.getString("ContractStarted"), rs.getString("Cost"), rs.getString("DayOfMonth"), rs.getString("Landlord"));

            }
        }

        mExpense.prop = prop;
        Stage primaryStage = new Stage();
        mExpense.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            treeviewExpense.refresh();
        });
        treeviewExpense.refresh();
    }

    @FXML
    public void importExpense() throws IOException {
        PropertyExpenseImporter exImport = new PropertyExpenseImporter();
        Stage primaryStage = new Stage();
        exImport.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            treeviewExpense.refresh();
        });
        treeviewExpense.refresh();

    }
}
