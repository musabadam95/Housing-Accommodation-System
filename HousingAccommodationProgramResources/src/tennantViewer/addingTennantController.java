/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tennantViewer;

import Database.connectionDB;
import Database.properties;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class addingTennantController implements Initializable {

    public connectionDB connectSQL;
    private Connection connected;
    @FXML
    private AnchorPane propertyList;
    @FXML
    private JFXTextField FirstNameTxt;
    @FXML
    private JFXTextField LastNameTxt;
    @FXML
    private JFXDatePicker DOBPicker;
    @FXML
    private JFXComboBox<properties> propList;
    public ObservableList<properties> data = FXCollections.<properties>observableArrayList();
    @FXML
    private JFXDatePicker MovedInPicker;
Alert alert;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        connectSQL = new Database.connectionDB();
        populateList();
        FirstNameTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                //when focus lost
                if (FirstNameTxt.getText().matches("[0-9]")) {

                    //when it not matches the pattern (1.0 - 6.0)
                    //set the textField empty
                    FirstNameTxt.setText("");
                }
            }

        });

        LastNameTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                //when focus lost
                if (LastNameTxt.getText().matches("[0-9]")) {
                    //when it not matches the pattern (1.0 - 6.0)
                    //set the textField empty
                    LastNameTxt.setText("");
                }
            }

        });

        DOBPicker.setConverter(new StringConverter<LocalDate>() {
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

        MovedInPicker.setConverter(new StringConverter<LocalDate>() {
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

    public void populateList() {

        connected = connectSQL.returnConnection();
        String Sqlite = "SELECT * FROM property ";
        try {
            java.sql.Statement st = connected.createStatement();
            ResultSet rs = st.executeQuery(Sqlite);
            while (rs.next()) {

                data.add(new properties(rs.getString(1), rs.getString("DoorNo"), rs.getString("FlatNo"), rs.getString("FirstLine"), rs.getString("PostCode"), rs.getString("Town"), rs.getString("DoorNo"), rs.getString("AreaManager"), rs.getString("ContractEnded"), rs.getString("ContractStarted"), rs.getString("Cost"), rs.getString("DayOfMonth"), rs.getString("Landlord")));
            }
            propList.setItems(data);
            propList.setConverter(new StringConverter<properties>() {
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
            Logger.getLogger(TennantManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void addNewTennant() {
        try {
              Stage stage = (Stage) MovedInPicker.getScene().getWindow();
                  if(FirstNameTxt.getText().isEmpty()||LastNameTxt.getText().isEmpty()||DOBPicker.equals(null)||propList.equals(null)||MovedInPicker.equals(null)){
              alert.setContentText("Check if all details have been filled");
                            alert.showAndWait();
                            return;
            }
            String FirstName = FirstNameTxt.getText();
            String LastName = LastNameTxt.getText();
          
            String DOB = DOBPicker.getValue().toString();
            properties selProp = propList.getValue();
            String movedIn = MovedInPicker.getValue().toString();

        
            java.sql.Statement stmt = connected.createStatement();
            String Sqlite = "INSERT INTO tennant (FirstName,LastName,DateOfBirth,LivesAt,LivingFrom) Values ('" + FirstName + "','" + LastName + "','" + DOB + "','" + selProp.getPropertyID().toString() + "','" + movedIn + "')";
            stmt.executeUpdate(Sqlite);

            Sqlite = "Select * from tennant where tennantID=LAST_INSERT_ID()";
            ResultSet rs = stmt.executeQuery(Sqlite);
            String pK="";
            while (rs.next()) {
                pK = rs.getString("tennantID");
            }

         Sqlite = "INSERT INTO users (UserName,Password,Type,tennantID) Values ('" + FirstName+LastName +"','" + LastName+DOB.replace("-", "") + "','" + "Tennant" + "','" + pK + "')";
            stmt.executeUpdate(Sqlite);
                    stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(addingTennantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
