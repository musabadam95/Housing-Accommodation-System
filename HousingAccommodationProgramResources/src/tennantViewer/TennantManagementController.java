/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tennantViewer;

import Database.connectionDB;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Database.tennants;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import propertyViewer.PropertyManagementController;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class TennantManagementController implements Initializable {

    @FXML
    private JFXTreeTableView<tennants> treeview;
    private final JFXTreeTableColumn<tennants, String> tennantIDcol = new JFXTreeTableColumn("TennantID");
    private final JFXTreeTableColumn<tennants, String> FirstNamecol = new JFXTreeTableColumn("FirstName");
    private final JFXTreeTableColumn<tennants, String> LastNamecol = new JFXTreeTableColumn("LastName");
    private final JFXTreeTableColumn<tennants, String> DOBCol = new JFXTreeTableColumn("DateOfBirth");
    private final JFXTreeTableColumn<tennants, String> AddressCol = new JFXTreeTableColumn("Address");
    private final JFXTreeTableColumn<tennants, String> LivingFromCol = new JFXTreeTableColumn("LivingFrom");
    public connectionDB connectSQL;
    private Connection connected;
    public ObservableList<tennants> data = FXCollections.<tennants>observableArrayList();
    @FXML
    private JFXButton importCSV;
    @FXML
    private JFXButton TennantLeaveBtn;
    @FXML
    private JFXButton delTennantBtn;
    @FXML
    private JFXButton addTennantBtn;
    @FXML
    private Label month1;
    @FXML
    private Label month2;
    @FXML
    private Label month3;
    @FXML
    private Label month4;
    @FXML
    private Label month5;
    @FXML
    private Label month6;
    @FXML
    private Label month7;
    @FXML
    private Label month8;
    @FXML
    private Label month9;
    @FXML
    private Label month10;
    @FXML
    private Label month11;
    @FXML
    private Label month12;
    @FXML
    private JFXComboBox<Integer> getYear;
    Alert alert;
    @FXML
    private JFXButton RelocateBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        delTennantBtn.disableProperty().bind(Bindings.isEmpty(treeview.getSelectionModel().getSelectedItems()));
        TennantLeaveBtn.disableProperty().bind(Bindings.isEmpty(treeview.getSelectionModel().getSelectedItems()));

        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
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
        FirstNamecol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("FirstName"));

        tennantIDcol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("tennantID"));

        LastNamecol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("LastName"));

        AddressCol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("Address"));
        DOBCol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("DateOfBirth"));

        LivingFromCol.setCellValueFactory(new TreeItemPropertyValueFactory<tennants, String>("LivingFrom"));

        final TreeItem<tennants> root = new RecursiveTreeItem<tennants>(data, RecursiveTreeObject::getChildren);
        treeview.getColumns().setAll(tennantIDcol, FirstNamecol, LastNamecol, DOBCol, AddressCol, LivingFromCol);
        treeview.setRoot(root);
        treeview.setShowRoot(false);

    }

    private void updateTable() {
        data.clear();
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM tennant left JOIN property ON tennant.LivesAt=property.PropertyID ";
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

        treeview.refresh();

    }

    @FXML
    public void clearSelection() {
        try {
            calculateDays();
        } catch (ParseException e) {

        }
    }

    @FXML
    public void addNewTennant() throws IOException {

        addingTennant addingTennant = new addingTennant();
        Stage primaryStage = new Stage();
        addingTennant.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            updateTable();

        });
    }

    @FXML
    public void relocateTennants() throws IOException {

        relocateTennant relocateTennant = new relocateTennant();
        Stage primaryStage = new Stage();
        relocateTennant.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            updateTable();

        });
    }

    @FXML
    public void removeTennant() {
        TreeItem<tennants> tennant = treeview.getSelectionModel().getSelectedItem();
        String firstName = tennant.getValue().getFirstName();
        String LastName = tennant.getValue().getLastName();
        String DOB = tennant.getValue().getDateOfBirth();
        String LivesAt = tennant.getValue().getLivesAtPK();
        String LivingFrom = tennant.getValue().getLivingFrom();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String query = "INSERT INTO oldtennants  (FirstName,LastName,DOB, LivesAt,LivingFrom,LeftOn) Values ('" + firstName + "','" + LastName + "','" + DOB + "','" + LivesAt + "','" + LivingFrom + "','" + dateFormat.format(date) + "' )";
        connected = connectSQL.returnConnection();

        try {
            java.sql.Statement st = connected.createStatement();
            st.executeUpdate(query);
            query = "DELETE FROM tennant WHERE tennantID='" + tennant.getValue().getTennantID() + "'";
            connected = connectSQL.returnConnection();
            st.executeUpdate(query);
            updateTable();
        } catch (SQLException ex) {

            alert.setContentText("Transfer Failed. No changes have been made");
            alert.showAndWait();

        }
    }

    @FXML
    public void importTennants() throws FileNotFoundException, IOException, ParseException {

        try {
            alert.setContentText("ENSURE THAT THE COLUMNS ARE IN THE FOLLOWING ORDER: \n 1. First Name 2. Last Name 3. DateOfBirth 4.Date Moved In 5.Property ID");
            alert.showAndWait();
            JFileChooser chooser = new JFileChooser();

            int choice = chooser.showOpenDialog(chooser);
            if (choice != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File chosenFile = chooser.getSelectedFile();
            String filePath = chosenFile.getAbsolutePath();
            CSVReader csvReader = new CSVReader(new FileReader(filePath));
            String[] row = null;

            java.sql.Statement stmt = connected.createStatement();
            while ((row = csvReader.readNext()) != null) {
                String Sqlite = "INSERT INTO tennant (FirstName,LastName,DateOfBirth,LivingFrom,LivesAt) Values ('" + row[0] + "','" + row[1] + "','" + row[2] + "','" + row[3] + "'," + row[4] + ")";
                stmt.executeUpdate(Sqlite);
            }
            updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void deleteTennant() {
        try {
            TreeItem<tennants> tennant = treeview.getSelectionModel().getSelectedItem();
            String query = "DELETE FROM tennant WHERE tennantID='" + tennant.getValue().getTennantID() + "'";
            connected = connectSQL.returnConnection();
            java.sql.Statement st = connected.createStatement();
            st.executeUpdate(query);
            updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(PropertyManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void calculateDays() throws ParseException {
        try{
        tennants tennant = treeview.getSelectionModel().getSelectedItem().getValue();
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

        //check if tennant moved in after the selected year therfore set all months to 0
        if (tennantMovedIn.get(Calendar.YEAR) > year) {
            for (int i = 0; i < dayspentList.length; i++) {
                dayspentList[i] = 0;

            }
            month1.setText(Long.toString(dayspentList[0]));
            month2.setText(Long.toString(dayspentList[1]));
            month3.setText(Long.toString(dayspentList[2]));
            month4.setText(Long.toString(dayspentList[3]));
            month5.setText(Long.toString(dayspentList[4]));
            month6.setText(Long.toString(dayspentList[5]));
            month7.setText(Long.toString(dayspentList[6]));
            month8.setText(Long.toString(dayspentList[7]));
            month9.setText(Long.toString(dayspentList[8]));
            month10.setText(Long.toString(dayspentList[9]));
            month11.setText(Long.toString(dayspentList[10]));
            month12.setText(Long.toString(dayspentList[11]));
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);

//check if tennant moved in this year or prev years , if this year then get actual month moved, else set month to january
        if (tennantMovedIn.get(Calendar.YEAR) < comparedDate.get(Calendar.YEAR)) {
            tennantMovedIn.set(cal.get(Calendar.YEAR), 0, 1);
        }

        //calculate spent days in each month and add to array
        for (int i = tennantMovedIn.get(Calendar.MONTH); i < 12; i++) {
//if i has gone above todays month then stop and set the rest as 0
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

                dayspentList[i] = diff;

            } else {
                cal.set(cal.get(Calendar.YEAR), i, 1);

                //last day of the month
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date lastDay = cal.getTime();
                long diff = TimeUnit.DAYS.convert(cal.getTimeInMillis() - tennantMovedIn.getTimeInMillis(), TimeUnit.MILLISECONDS) - days;
                days += diff;

                dayspentList[i] = diff;

            }

        }

        month1.setText(Long.toString(dayspentList[0]));
        month2.setText(Long.toString(dayspentList[1]));
        month3.setText(Long.toString(dayspentList[2]));
        month4.setText(Long.toString(dayspentList[3]));
        month5.setText(Long.toString(dayspentList[4]));
        month6.setText(Long.toString(dayspentList[5]));
        month7.setText(Long.toString(dayspentList[6]));
        month8.setText(Long.toString(dayspentList[7]));
        month9.setText(Long.toString(dayspentList[8]));
        month10.setText(Long.toString(dayspentList[9]));
        month11.setText(Long.toString(dayspentList[10]));
        month12.setText(Long.toString(dayspentList[11]));
    }catch( NullPointerException e){
    }
    }
}
