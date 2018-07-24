/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertyViewer;

import Database.areaManager;
import Database.connectionDB;
import Database.landlord;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class addingPropertyController implements Initializable {

    public connectionDB connectSQL;
    private Connection connected;

    public ObservableList<landlord> landlordList = FXCollections.<landlord>observableArrayList();

    @FXML
    private JFXDatePicker ContractEndPicker;
    @FXML
    private JFXTextField FlatNoTxt;
    @FXML
    private JFXTextField DoorNoTxt;
    @FXML
    private JFXTextField FirstLineTxt;
    @FXML
    private JFXTextField TownTxt;
    @FXML
    private JFXTextField BoroughTxt;
    @FXML
    private JFXTextField RateTxt;
    @FXML
    private JFXDatePicker ContractPicker;
    @FXML
    private JFXComboBox<landlord> landlordPicker;
    @FXML
    private AnchorPane propertyList;

    @FXML
    private JFXTextField rentTxt;
    @FXML
    private JFXButton addpropBtn;
    @FXML
    private JFXTextField postCodeTxt;
    @FXML
    private JFXComboBox<areaManager> areaManagerPicker;
    public ObservableList<areaManager> areaManagerList = FXCollections.<areaManager>observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connectSQL = new Database.connectionDB();
        RateTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                //when focus lost
                if (RateTxt.getText().matches("[a-z]")) {
                    //when it not matches the pattern (1.0 - 6.0)
                    //set the textField empty
                    RateTxt.setText("");
                }
            }

        });
        RateTxt.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!RateTxt.getText().matches("[0-9]+(?:\\.[0-9]{0,2})?")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    RateTxt.setText("");
                }
            }
        });

        RateTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                //when focus lost
                if (RateTxt.getText().matches("[a-z]")) {
                    //when it not matches the pattern (1.0 - 6.0)
                    //set the textField empty
                    RateTxt.setText("");
                }
            }

        });
        rentTxt.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                if (!rentTxt.getText().matches("[0-9]+(?:\\.[0-9]{0,2})?")) {
                    // when it not matches the pattern (1.0 - 6.0)
                    // set the textField empty
                    rentTxt.setText("");
                }
            }
        });

        rentTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                //when focus lost
                if (rentTxt.getText().matches("[a-z]")) {
                    //when it not matches the pattern (1.0 - 6.0)
                    //set the textField empty
                    rentTxt.setText("");
                }
            }

        });
        landlord l = new landlord("1", "2", "3", "4");
        try {
            populateLandlords();
            populateAreaManager();
        } catch (SQLException ex) {
            Logger.getLogger(addingPropertyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ContractPicker.setConverter(new StringConverter<LocalDate>() {
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

        ContractEndPicker.setConverter(new StringConverter<LocalDate>() {
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
    }

    @FXML
    public void addNewProperty() {

        Stage stage = (Stage) addpropBtn.getScene().getWindow();
        connected = connectSQL.returnConnection();
        try {
            String FlatNo = FlatNoTxt.getText();
            String DoorNo = DoorNoTxt.getText();
            String FirstLine = FirstLineTxt.getText();
            String Town = TownTxt.getText();
            String Borough = BoroughTxt.getText();
            String ContractStarted = ContractPicker.getValue().toString();
            String RentDate=String.valueOf(ContractPicker.getValue().getDayOfMonth());
            String ContractEnded = ContractEndPicker.getValue().toString();
            String rate = RateTxt.getText();
            String rentCost = rentTxt.getText();
            String PostCode = postCodeTxt.getText();
            String areaManagerID=areaManagerPicker.getValue().getAreaManagerID();
            landlord nu = landlordPicker.getValue();
            java.sql.Statement stmt = connected.createStatement();
            String Sqlite = "INSERT INTO property (FlatNo,DoorNo,FirstLine,Town,Borough,ContractStarted,ContractEnded,Rate,Landlord,Cost,PostCode,AreaManager,DayOfMonth) Values ('" + FlatNo + "','" + DoorNo + "','" + FirstLine + "','" + Town + "','" + Borough + "','" + ContractStarted + "','" + ContractEnded + "','" + rate + "','" + nu.getLandlordID() + "','" + rentCost + "','" + PostCode + "','"+areaManagerID+"','"+RentDate+"')";
            stmt.executeUpdate(Sqlite);
            Sqlite = "SELECT PropertyID FROM property WHERE FlatNo='" + FlatNo + "' AND FirstLine='" + FirstLine + "' OR DoorNo='" + DoorNo + "' AND FirstLine='" + FirstLine + "'";
            stmt = connected.createStatement();
            ResultSet rs = stmt.executeQuery(Sqlite);
            rs.next();
            Sqlite = "INSERT INTO utilities \n"
                    + "(`PropertyID`,`CTaxBorough`,`CTaxAccntNo`,`CTaxCost`,`EnegComp`,`EnegAcntNo`,`EnegCost`,`GasComp`,`GasAcntNo`,`GasCost`,`WaterComp`,`WaterAcntNo`,`WaterCost`)\n"
                    + "VALUES('" + rs.getString("PropertyID") + "','awaiting','awaiting',0.0,'awaiting','awaiting',0.0,'awaiting','awaiting',0.0,'awaiting','awaiting',0.0);";
            stmt.executeUpdate(Sqlite);
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(addingPropertyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage.close();
    }

    @FXML
    public void addNewLandlord() throws IOException {

        addingLandlord addingLandlord = new addingLandlord();
        Stage primaryStage = new Stage();
        addingLandlord.start(primaryStage);
        primaryStage.setOnHidden(e -> {
            try {
                populateLandlords();
            } catch (SQLException ex) {
                Logger.getLogger(addingPropertyController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    public void populateLandlords() throws SQLException {
        landlordList.clear();
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM landlord";
        ResultSet rs;
        java.sql.Statement st = connected.createStatement();
        rs = st.executeQuery(Sqlite);
        while (rs.next()) {
            landlord l = new landlord(rs.getString("LandlordID"), rs.getString("landlordName"), rs.getString("bankAccountNo"), rs.getString("sortCode"));

            landlordList.add(l);

        }
        landlordPicker.setItems(landlordList);

        landlordPicker.setConverter(new StringConverter<landlord>() {
            @Override
            public String toString(landlord object) {
                return object.getLandlordName();
            }

            @Override
            public landlord fromString(String string) {
                return null;
            }
        });
    }

    public void populateAreaManager() throws SQLException {
        areaManagerList.clear();
        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM areamanager";
        ResultSet rs;
        java.sql.Statement st = connected.createStatement();
        rs = st.executeQuery(Sqlite);
        while (rs.next()) {
            areaManager l = new areaManager(rs.getString("idAreaManager"), rs.getString("FirstName"), rs.getString("FirstName"));

            areaManagerList.add(l);

        }
        areaManagerPicker.setItems(areaManagerList);

        areaManagerPicker.setConverter(new StringConverter<areaManager>() {
            @Override
            public String toString(areaManager object) {
                return object.getFirstName() + " " + object.getLastName();
            }

            @Override
            public areaManager fromString(String string) {
                return null;
            }
        });
    }

}
