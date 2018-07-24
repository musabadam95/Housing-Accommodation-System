/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financeViewer;

import Database.connectionDB;
import Database.finance;
import Database.properties;
import Database.tennants;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import propertyViewer.PropertyManagementController;

public class detailedFinanceController implements Initializable {

    public ObservableList<properties> data = FXCollections.<properties>observableArrayList();
    public ObservableList<finance> detailedData = FXCollections.<finance>observableArrayList();

    public ObservableList<PieChart.Data> pieData = FXCollections.<PieChart.Data>observableArrayList();

    properties selectedProp;

    public connectionDB connectSQL;
    private Connection connected;
    public Calendar lastPaid;
    public Calendar rentToPayDate;
    @FXML
    private JFXTreeTableView<finance> expenseTable;

    private JFXTreeTableColumn<finance, String> typeCol = new JFXTreeTableColumn("");
    private JFXTreeTableColumn<finance, String> costCol = new JFXTreeTableColumn("");
    @FXML
    private PieChart pie;
    @FXML
    private Label propertyName;
    @FXML
    private JFXComboBox<String> monthPicker;
    @FXML
    private JFXComboBox<String> yearPicker;
    public long[] propFinance = new long[12];

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        connectSQL = new Database.connectionDB();

        populateComboBoxes();
        //listRents();
        createInitialPieChart();
        listTents();
    }

    public void listTents() {
        //populate tableview using data from database

        updateTable();

        typeCol.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("expenseName"));

        costCol.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("cost"));

        final TreeItem<finance> root = new RecursiveTreeItem<finance>(detailedData, RecursiveTreeObject::getChildren);
        expenseTable.getColumns().setAll(typeCol, costCol);
        expenseTable.setRoot(root);
        expenseTable.setShowRoot(false);

    }

    public void populateComboBoxes() {
        ObservableList<String> months = FXCollections.<String>observableArrayList();
        months.addAll("All", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        monthPicker.setItems(months);
        ObservableList<String> years = FXCollections.<String>observableArrayList();

        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT DISTINCT YEAR(datePaid)as year FROM expenses";
        ResultSet rs;
        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            while (rs.next()) {
                years.add(rs.getString("year"));
            }

        } catch (SQLException e) {

        }

        monthPicker.setItems(months);

        yearPicker.setItems(years);
        Calendar comparedDate = Calendar.getInstance();

        yearPicker.setValue(String.valueOf(comparedDate.get(Calendar.YEAR)));
    }

    public void createInitialPieChart() {
        connected = connectSQL.returnConnection();
        ResultSet rs;
        String Sqlite = "";
        if (selectedProp == null) {
            Sqlite = "SELECT expensecatergory.catergoryname , sum(expenses.totalCost)as total FROM expenses INNER JOIN expensecatergory on expenses.catergory=expensecatergory.idexpenseCatergory where propertyID is NULL GROUP BY catergoryname";

        } else {
            Sqlite = "SELECT expensecatergory.catergoryname , sum(expenses.totalCost)as total FROM expenses INNER JOIN expensecatergory on expenses.catergory=expensecatergory.idexpenseCatergory where propertyID=" + selectedProp.getPropertyID() + " GROUP BY catergoryname";
        }
        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            while (rs.next()) {
                pieData.add(new PieChart.Data(rs.getString("catergoryname"), rs.getInt("total")));

            }
            pieData.forEach(data
                    -> data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), " £", data.pieValueProperty()
                            )
                    )
            );
            pie.setData(pieData);
        } catch (SQLException e) {

        }

    }

    @FXML
    public void updatePieChart() {
        updateTable();
        String Sqlite = "";
        if (selectedProp == null) {
            Sqlite = "SELECT expensecatergory.catergoryname , sum(expenses.totalCost)as total FROM expenses INNER JOIN expensecatergory on expenses.catergory=expensecatergory.idexpenseCatergory where propertyID is NULL AND MONTH(datePaid)=" + monthPicker.getValue() + " AND YEAR(datePaid)=" + yearPicker.getValue() + " GROUP BY catergoryname";
            if (monthPicker.getValue().equals("All") && yearPicker.getValue().equals("All")) {
                createInitialPieChart();
            } else if (monthPicker.getValue().equals("All") && !yearPicker.getValue().equals("All")) {
                Sqlite = "SELECT expensecatergory.catergoryname , sum(expenses.totalCost)as total FROM expenses INNER JOIN expensecatergory on expenses.catergory=expensecatergory.idexpenseCatergory where propertyID is NULL AND  YEAR(datePaid)=" + yearPicker.getValue() + " GROUP BY catergoryname";
            } else if (!monthPicker.getValue().equals("All") && yearPicker.getValue().equals("All")) {
                Sqlite = "SELECT expensecatergory.catergoryname , sum(expenses.totalCost)as total FROM expenses INNER JOIN expensecatergory on expenses.catergory=expensecatergory.idexpenseCatergory where propertyID is NULL AND MONTH(datePaid)=" + monthPicker.getValue() + " GROUP BY catergoryname";

            }
        } else {
            Sqlite = "SELECT expensecatergory.catergoryname , sum(expenses.totalCost)as total FROM expenses INNER JOIN expensecatergory on expenses.catergory=expensecatergory.idexpenseCatergory where propertyID=" + selectedProp.getPropertyID() + " AND MONTH(datePaid)=" + monthPicker.getValue() + " AND YEAR(datePaid)=" + yearPicker.getValue() + " GROUP BY catergoryname";
            if (monthPicker.getValue().equals("All") && yearPicker.getValue().equals("All")) {
                createInitialPieChart();
            } else if (monthPicker.getValue().equals("All") && !yearPicker.getValue().equals("All")) {
                Sqlite = "SELECT expensecatergory.catergoryname , sum(expenses.totalCost)as total FROM expenses INNER JOIN expensecatergory on expenses.catergory=expensecatergory.idexpenseCatergory where propertyID=" + selectedProp.getPropertyID() + " AND  YEAR(datePaid)=" + yearPicker.getValue() + " GROUP BY catergoryname";
            } else if (!monthPicker.getValue().equals("All") && yearPicker.getValue().equals("All")) {
                Sqlite = "SELECT expensecatergory.catergoryname , sum(expenses.totalCost)as total FROM expenses INNER JOIN expensecatergory on expenses.catergory=expensecatergory.idexpenseCatergory where propertyID=" + selectedProp.getPropertyID() + " AND MONTH(datePaid)=" + monthPicker.getValue() + " GROUP BY catergoryname";

            }
        }
        connected = connectSQL.returnConnection();
        ResultSet rs;

        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            pieData.clear();
            while (rs.next()) {
                pieData.add(new PieChart.Data(rs.getString("catergoryname"), rs.getInt("total")));

            }
            pieData.forEach(data
                    -> data.nameProperty().bind(
                            Bindings.concat(
                                    data.getName(), " £", data.pieValueProperty()
                            )
                    )
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateTable() {
        detailedData.clear();
        connected = connectSQL.returnConnection();
        ResultSet rs;
        String Sqlite = "";
        if (selectedProp == null) {
            Sqlite = "SELECT\n"
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
                    + "FROM expenses RIGHT JOIN property ON expenses.propertyID=property.PropertyID WHERE property.PropertyID is null AND YEAR(datePaid)=" + yearPicker.getValue() + "\n"
                    + "GROUP BY propertyID;";
        } else {
             Sqlite = "SELECT\n"
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
                    + "FROM expenses RIGHT JOIN property ON expenses.propertyID=property.PropertyID WHERE property.PropertyID=" + selectedProp.getPropertyID() + " AND YEAR(datePaid)=" + yearPicker.getValue() + "\n"
                    + "GROUP BY propertyID;";
        }
        
        
        if(selectedProp==null){
        
        
          if (monthPicker.getValue() == (null) || monthPicker.getValue().equals("All")) {//for all months
            Sqlite = "SELECT property.propertyID,FlatNo,DoorNo, FirstLine,SUM(totalCost)AS Total FROM expenses RIGHT JOIN property ON expenses.propertyID=property.PropertyID WHERE property.PropertyID IS NULL AND YEAR(datePaid)=" + yearPicker.getValue() + " GROUP BY propertyID;";
            try {
                java.sql.Statement st = connected.createStatement();
                rs = st.executeQuery(Sqlite);

                while (rs.next()) {

                    finance object = new finance("Expenses :", "£ " + rs.getString("Total"));

                    detailedData.add(object);
                    long total = 0;
                  
                }
            } catch (SQLException ex) {
                Logger.getLogger(PropertyManagementController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {//for specific months

            try {
                java.sql.Statement st = connected.createStatement();
                rs = st.executeQuery(Sqlite);

                while (rs.next()) {
                    finance object = new finance("Expenses :", "£ " + rs.getString(Integer.parseInt(monthPicker.getValue())));
                    detailedData.add(object);

                }
            } catch (SQLException ex) {
                Logger.getLogger(PropertyManagementController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        }
        else{
        if (monthPicker.getValue() == (null) || monthPicker.getValue().equals("All")) {//for all months
            Sqlite = "SELECT property.propertyID,FlatNo,DoorNo, FirstLine,SUM(totalCost)AS Total FROM expenses RIGHT JOIN property ON expenses.propertyID=property.PropertyID WHERE property.PropertyID=" + selectedProp.getPropertyID() + " AND YEAR(datePaid)=" + yearPicker.getValue() + " GROUP BY propertyID;";
            try {
                java.sql.Statement st = connected.createStatement();
                rs = st.executeQuery(Sqlite);

                while (rs.next()) {

                    long[] dayspent = sumCalculateDays(rs.getString("PropertyID"));

                    finance object = new finance("Expenses :", "£ " + rs.getString("Total"));

                    detailedData.add(object);
                    long total = 0;
                    for (int i = 0; i < dayspent.length; i++) {

                        total = total + dayspent[i];
                    }

                    finance object2 = new finance("Income :", "£ " + total);

                    detailedData.add(object2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PropertyManagementController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {//for specific months

            try {
                java.sql.Statement st = connected.createStatement();
                rs = st.executeQuery(Sqlite);

                while (rs.next()) {

                    long[] dayspent = sumCalculateDays(rs.getString("PropertyID"));

                    finance object = new finance("Expenses :", "£ " + rs.getString(Integer.parseInt(monthPicker.getValue())));

                    detailedData.add(object);
                    long sum = dayspent[Integer.parseInt(monthPicker.getValue())];

                    finance object2 = new finance("Income :", "£ " + sum);
                    detailedData.add(object2);

                }
            } catch (SQLException ex) {
                Logger.getLogger(PropertyManagementController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
        //   treeviewProp.refresh();
    }

    public long[] sumCalculateDays(String propertyID) {

        ResultSet rs;
        java.sql.Statement st;
        connected = connectSQL.returnConnection();

        String Sqlite = "SELECT * FROM tennant INNER JOIN property ON tennant.LivesAt=property.PropertyID where LivesAt='" + propertyID + "'";

        try {
            st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            long[] days;
            while (rs.next()) {

                try {
                    days = calculateDays(new tennants(rs.getString(1), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("DateOfBirth"), rs.getString("LivesAt"), rs.getString("DoorNo") + " " + rs.getString("FirstLine"), rs.getString("LivingFrom")), rs.getLong("rate"));
                    for (int i = 0; i < days.length; i++) {
                        propFinance[i] += days[i];

                    }

                } catch (ParseException ex) {
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return propFinance;
    }

    public long[] calculateDays(tennants tennant, long rate) throws ParseException {
        propFinance = new long[12];
        int year = Integer.parseInt(yearPicker.getValue());
        long[] dayspentList = new long[12];

        //initate date that is going to be used to compare tennants movedIn date
        Calendar comparedDate = Calendar.getInstance();
        comparedDate.set(Calendar.YEAR, year);
        //

        String expectedPattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);

        //create date representing today
        Calendar todayDate = Calendar.getInstance();
        Date todaydates = new Date();
        todayDate.setTime(todaydates);
        //check if comparedDate's Year is less then today's Year and if it is then set compatedDate to 31st December
        if (comparedDate.get(Calendar.YEAR) < todayDate.get(Calendar.YEAR)) {
            comparedDate.set(year, 11, 31);
        }

        //get date tenant moved in 
        String userInput = tennant.getLivingFrom();
        Date date = formatter.parse(userInput);
        Calendar tennantMovedIn = Calendar.getInstance();
        tennantMovedIn.setTime(date);
        long days = 0;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);

//check if tennant moved in this year or prev years , if this year then get actual month moved, else set month to january
        if (tennantMovedIn.get(Calendar.YEAR) < comparedDate.get(Calendar.YEAR)) {
            tennantMovedIn.set(cal.get(Calendar.YEAR), 0, 1);
        }

        //calculate spent days in each month and add to array
        for (int i = tennantMovedIn.get(Calendar.MONTH); i < 12; i++) {

            if (i > comparedDate.get(Calendar.MONTH)) {

                for (int remaining = i + 1; remaining < dayspentList.length; remaining++) {
                    dayspentList[remaining] = 0;

                }
                break;
            } else if (i == comparedDate.get(Calendar.MONTH)) {
                cal.set(cal.get(Calendar.YEAR), i, 1);
                //last day of the month 
                cal.set(Calendar.DAY_OF_MONTH, comparedDate.get(Calendar.DAY_OF_MONTH));
                Date lastDay = cal.getTime();

                long diff = TimeUnit.DAYS.convert(cal.getTimeInMillis() - tennantMovedIn.getTimeInMillis(), TimeUnit.MILLISECONDS) - days;
                days += diff;

                dayspentList[i] = diff * rate;

            } else {
                cal.set(cal.get(Calendar.YEAR), i, 1);

                //last day of the month
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date lastDay = cal.getTime();
                long diff = TimeUnit.DAYS.convert(cal.getTimeInMillis() - tennantMovedIn.getTimeInMillis(), TimeUnit.MILLISECONDS) - days;
                days += diff;

                dayspentList[i] = diff * rate;

            }

        }

        return dayspentList;
    }

}
