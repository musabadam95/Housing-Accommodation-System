package financeViewer;

import Database.connectionDB;
import Database.finance;
import Database.properties;
import Database.tennants;
import com.jfoenix.controls.JFXComboBox;
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
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import propertyViewer.PropertyManagementController;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class financeManagementController implements Initializable {

    @FXML
    private JFXTreeTableView<finance> treeviewProp;
    public connectionDB connectSQL;
    private Connection connected;
    public ObservableList<finance> data = FXCollections.<finance>observableArrayList();
    properties selectedProp;
    private final JFXTreeTableColumn<finance, String> property = new JFXTreeTableColumn("Property");
    private final JFXTreeTableColumn<finance, String> january = new JFXTreeTableColumn("January");
    private final JFXTreeTableColumn<finance, String> february = new JFXTreeTableColumn("February");
    private final JFXTreeTableColumn<finance, String> march = new JFXTreeTableColumn("March");
    private final JFXTreeTableColumn<finance, String> april = new JFXTreeTableColumn("April");
    private final JFXTreeTableColumn<finance, String> may = new JFXTreeTableColumn("May");
    private JFXTreeTableColumn<finance, String> june = new JFXTreeTableColumn("June");
    private JFXTreeTableColumn<finance, String> july = new JFXTreeTableColumn("July");
    private JFXTreeTableColumn<finance, String> august = new JFXTreeTableColumn("August");
    private JFXTreeTableColumn<finance, String> september = new JFXTreeTableColumn("Sepetember");
    private JFXTreeTableColumn<finance, String> october = new JFXTreeTableColumn("October");
    private JFXTreeTableColumn<finance, String> november = new JFXTreeTableColumn("November");
    private JFXTreeTableColumn<finance, String> december = new JFXTreeTableColumn("December");
    @FXML
    private JFXComboBox<Integer> getYear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connectSQL = new Database.connectionDB();
        populateYear();
        listTents();

    }

    public void populateYear() {
        ObservableList<Integer> years = FXCollections.<Integer>observableArrayList();
        String Sqlite = "SELECT DISTINCT YEAR(LivingFrom)AS Year FROM tennant;";
        connected = connectSQL.returnConnection();
        try {
            java.sql.Statement st = connected.createStatement();
            ResultSet rs;
            rs = st.executeQuery(Sqlite);

            while (rs.next()) {

                years.add(rs.getInt("Year"));

            }
            getYear.setItems(years);
            Calendar todayDate = Calendar.getInstance();

            getYear.setValue(todayDate.get(Calendar.YEAR));
        } catch (SQLException e) {
        }

    }

    public void listTents() {
        //populate tableview using data from database
        updateTable();

        property.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("Property"));

        january.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("January"));

        january.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        february.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("February"));

        february.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        march.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("March"));

        march.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        april.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("April"));

        april.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        may.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("May"));

        may.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        june.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("June"));

        june.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        july.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("July"));

        july.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        august.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("August"));

        august.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        september.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("Sepetember"));

        september.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        october.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("October"));

        october.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        november.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("November"));

        november.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        december.setCellValueFactory(new TreeItemPropertyValueFactory<finance, String>("December"));

        december.setCellFactory((TreeTableColumn<finance, String> param) -> {
            TreeTableCell cell = new TreeTableCell<finance, String>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<finance> ttr = getTreeTableRow();
                    setText(item);
                    if (item == null || empty) {
                        setText("0.00");
                        ttr.setStyle("");
                        setStyle("");

                    } else if (Double.parseDouble(item) > 0) {

                        setStyle("-fx-background-color:green");
                    } else if (Double.parseDouble(item) < 0) {

                        setStyle("-fx-background-color:red");
                    } else {

                    }
                }
            };
            return cell;
        });

        final TreeItem<finance> root = new RecursiveTreeItem<finance>(data, RecursiveTreeObject::getChildren);
        treeviewProp.getColumns().setAll(property, january, february, march, april, may, june, july, august, september, october, november, december);
        treeviewProp.setRoot(root);
        treeviewProp.setShowRoot(false);

    }

    @FXML
    public void updateTable() {

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
                + "FROM expenses right JOIN property ON expenses.propertyID=property.PropertyID WHERE datePaid is NULL OR YEAR(datePaid)=" + getYear.getValue() + "\n"
                + "GROUP BY propertyID;";

        ResultSet rs;

        try {
            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);

            while (rs.next()) {
                if (rs.getString("PropertyID").isEmpty()) {
                    data.add(new finance("Other", rs.getString("FlatNo") + rs.getString("DoorNo") + rs.getString("FirstLine"), rs.getString("JANUARY"), rs.getString("FEBRUARY"), rs.getString("MARCH"), rs.getString("APRIL"), rs.getString("MAY"), rs.getString("JUNE"), rs.getString("JULY"), rs.getString("AUGUST"), rs.getString("SEPTEMBER"), rs.getString("OCTOBER"), rs.getString("NOVEMBER"), rs.getString("DECEMBER")));

                } else {
                    long[] dayspent = sumCalculateDays(rs.getString("PropertyID"));
                    dayspent[0] -= rs.getLong("JANUARY");

                    dayspent[1] -= rs.getLong("FEBRUARY");

                    dayspent[2] -= rs.getLong("MARCH");

                    dayspent[3] -= rs.getLong("APRIL");

                    dayspent[4] -= rs.getLong("MAY");

                    dayspent[5] -= rs.getLong("JUNE");

                    dayspent[6] -= rs.getLong("JULY");

                    dayspent[7] -= rs.getLong("AUGUST");

                    dayspent[8] -= rs.getLong("SEPTEMBER");

                    dayspent[9] -= rs.getLong("OCTOBER");
                    dayspent[10] -= rs.getLong("NOVEMBER");
                    dayspent[11] -= rs.getLong("DECEMBER");

                    finance object = new finance(rs.getString("PropertyID"), rs.getString("FlatNo") + rs.getString("DoorNo") + rs.getString("FirstLine"), String.valueOf(dayspent[0]), String.valueOf(dayspent[1]), String.valueOf(dayspent[2]), String.valueOf(dayspent[3]), String.valueOf(dayspent[4]), String.valueOf(dayspent[5]), String.valueOf(dayspent[6]), String.valueOf(dayspent[7]), String.valueOf(dayspent[8]), String.valueOf(dayspent[9]), String.valueOf(dayspent[10]), String.valueOf(dayspent[11]));

                    data.add(object);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class
                    .getName()).log(Level.SEVERE, null, ex);
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
                    + "FROM expenses WHERE propertyID is null AND year(datePaid)=" + getYear.getValue() + ";";

            java.sql.Statement st = connected.createStatement();
            rs = st.executeQuery(Sqlite);
            while (rs.next()) {

                data.add(new finance("Other", "Other" + "" + "Expenses", rs.getString("JANUARY"), rs.getString("FEBRUARY"), rs.getString("MARCH"), rs.getString("APRIL"), rs.getString("MAY"), rs.getString("JUNE"), rs.getString("JULY"), rs.getString("AUGUST"), rs.getString("SEPTEMBER"), rs.getString("OCTOBER"), rs.getString("NOVEMBER"), rs.getString("DECEMBER")));

            }
        } catch (SQLException ex) {

        }
        treeviewProp.refresh();

    }

    public long[] sumCalculateDays(String propertyID) {
        long[] propFinance = new long[12];
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
        int year = getYear.getValue();
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

    @FXML
    public void detailedFinance() throws IOException {
        String propID = treeviewProp.getSelectionModel().getSelectedItem().getValue().propertyID;
        properties selectedprop = null;
        if (propID.equals("Other")) {

        } else {
            connected = connectSQL.returnConnection();
            ResultSet rs;
            java.sql.Statement st;

            try {
                st = connected.createStatement();
                String Sqlite = "SELECT * FROM property where propertyID='" + propID + "'";
                rs = st.executeQuery(Sqlite);
                while (rs.next()) {
                    selectedprop = new properties(rs.getString(1), rs.getString("DoorNo"), rs.getString("FlatNo"), rs.getString("FirstLine"), rs.getString("PostCode"), rs.getString("Town"), rs.getString("DoorNo"), rs.getString("AreaManager"), rs.getString("ContractEnded"), rs.getString("ContractStarted"), rs.getString("Cost"), rs.getString("DayOfMonth"), rs.getString("Landlord"));

                }
            } catch (SQLException ex) {
                Logger.getLogger(financeManagementController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        detailedFinance detailedFinance = new detailedFinance();
        detailedFinance.setProperties(selectedprop);
        Stage primaryStage = new Stage();
        detailedFinance.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            updateTable();

        });

    }

}
