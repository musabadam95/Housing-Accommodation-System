/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertyViewer;

import Database.connectionDB;
import Database.properties;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.StringConverter;

public class RentPayingv2Controller implements Initializable {

    public ObservableList<properties> data = FXCollections.<properties>observableArrayList();
    properties selectedProp;
    @FXML
    private JFXTextField rentDueTxt;
    @FXML
    private JFXTextField deductionTxt;
    @FXML
    private JFXTextField totalPayTxt;
    @FXML
    private JFXDatePicker datePayedPicker;
    @FXML
    private JFXButton payBtn;
    @FXML
    private JFXTreeTableView<properties> rentTable;
    private JFXTreeTableColumn<properties, String> rentPaidDateCol = new JFXTreeTableColumn("Rent Paid");
    private JFXTreeTableColumn<properties, String> AddressCol = new JFXTreeTableColumn("Address");
    private JFXTreeTableColumn<properties, String> CostCol = new JFXTreeTableColumn("Cost");
    public connectionDB connectSQL;
    private Connection connected;
    public Calendar lastPaid;
    public Calendar rentToPayDate;
    @FXML
    private Label alertRent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

            datePayedPicker.setConverter(new StringConverter<LocalDate>() {
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
            connectSQL = new Database.connectionDB();
            listRents();
        } catch (ParseException ex) {
            Logger.getLogger(RentPayingv2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
          deductionTxt.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!deductionTxt.getText().matches("[0-9]+(?:\\.[0-9]{0,2})?")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    deductionTxt.setText("");
                }
            }
        });

        deductionTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                //when focus lost
                if (deductionTxt.getText().matches("[a-z]")) {
                    //when it not matches the pattern (1.0 - 6.0)
                    //set the textField empty
                    deductionTxt.setText("");
                }
            }

        });
        
       
        
    }

    public void listRents() throws ParseException {
        //populate tableview using data from database
        updateTable();

        rentPaidDateCol.setCellValueFactory(new TreeItemPropertyValueFactory<properties, String>("DatePaid"));
        AddressCol.setCellValueFactory(new TreeItemPropertyValueFactory<properties, String>("propertyAddress"));
        CostCol.setCellValueFactory(new TreeItemPropertyValueFactory<properties, String>("rentCost"));

        final TreeItem<properties> root = new RecursiveTreeItem<properties>(data, RecursiveTreeObject::getChildren);
        rentTable.getColumns().setAll(rentPaidDateCol, AddressCol, CostCol);
        rentTable.setRoot(root);
        rentTable.setShowRoot(false);

    }

    private void updateTable() throws ParseException {
        data.clear();
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM property INNER JOIN paidrent ON property.PropertyID=paidrent.PropertyID WHERE property.PropertyID='" + selectedProp.getPropertyID() + "' ORDER BY DatePaid ASC";
        ResultSet rs;
        lastPaid = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            while (rs.next()) {
                data.add(new properties(rs.getString("PropertyID"), rs.getString("FlatNo") + rs.getString("DoorNo") + " " + rs.getString("FirstLine"), rs.getString("AmountPaid"), rs.getString("DatePaid")));
                date = formatter.parse(rs.getString("DatePaid"));
                lastPaid.setTime(date);
            }
            rentDueTxt.setText(selectedProp.getCost());
            totalPayTxt.setText(selectedProp.getCost());
            try {

                Calendar today = Calendar.getInstance();
                rentToPayDate = Calendar.getInstance();
                today.setTime(new Date());
                String day = selectedProp.DayOfMonth;
                if (Integer.parseInt(selectedProp.DayOfMonth) == 1) {
                    day = "0" + day;
                }
                Date rentDate = formatter.parse(today.get(Calendar.YEAR) + "-" + today.get(Calendar.MONTH) + "-" + day);
                rentToPayDate.setTime(rentDate);
                System.out.println(rentToPayDate.getTime() + " " + today.getTime());
                if (lastPaid.get(Calendar.MONTH) < today.get(Calendar.MONTH)) {
                    alertRent.setText("RENT DUE");

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

        rentTable.refresh();

    }

    @FXML
    public void changeRentDue() {
        try {
            Double totalCost = Double.parseDouble(selectedProp.getCost()) - Double.parseDouble(deductionTxt.getText());
            totalPayTxt.setText(totalCost.toString());
        } catch (NumberFormatException e) {
            totalPayTxt.setText(selectedProp.getCost());
        }
    }

    @FXML
    private void addNewPayment() throws SQLException, ParseException {

        java.sql.Statement stmt = connected.createStatement();
        String Sqlite = "INSERT INTO paidrent (PropertyID,DatePaid,AmountPaid) Values ('" + selectedProp.getPropertyID() + "','" + datePayedPicker.getValue().toString() + "','" + totalPayTxt.getText() + "')";
        stmt.executeUpdate(Sqlite);
        updateTable();
    }
}
