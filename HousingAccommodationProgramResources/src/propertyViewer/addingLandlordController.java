/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertyViewer;

import Database.connectionDB;
import Database.properties;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class addingLandlordController implements Initializable {

    public connectionDB connectSQL;
    private Connection connected;
    @FXML
    private AnchorPane propertyList;
    private JFXTextField FirstNameTxt;
    private JFXTextField LastNameTxt;
    private JFXDatePicker DOBPicker;
    private JFXComboBox<properties> propList;
    public ObservableList<properties> data = FXCollections.<properties>observableArrayList();
    private JFXDatePicker MovedInPicker;
    @FXML
    private JFXTextField landlordNameTxt;
    @FXML
    private JFXTextField sortCodeTxt;
    @FXML
    private JFXTextField accntNoTxt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connectSQL = new Database.connectionDB();

       
    }

    @FXML
    public void addNewProperty() {
        connected = connectSQL.returnConnection();

        try {
            String name = landlordNameTxt.getText();
            String sortCode = sortCodeTxt.getText();
            String accountNo = accntNoTxt.getText();
            java.sql.Statement stmt = connected.createStatement();
            String Sqlite = "INSERT INTO landlord (landlordName,bankAccountNo,sortCode) Values ('" + name + "','" + Integer.parseInt(sortCode) + "','" + Integer.parseInt(accountNo)+"')";
            stmt.executeUpdate(Sqlite);
      
            
        } catch (SQLException ex) {
            Logger.getLogger(addingPropertyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

}
