/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Database.connectionDB;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import homeScreen.homeScreen;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class loginController implements Initializable {

    public connectionDB connectSQL;
    private Connection connected;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXTextField logUser;
    @FXML
    private JFXPasswordField logPassword;
    @FXML
    private AnchorPane backgroundPane;
    @FXML
    private AnchorPane frontPane;
   Alert alert;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
              alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        backgroundPane.setId("Backpane");
        frontPane.setId("Frontpane");
        connectSQL = new Database.connectionDB();
    }

    @FXML
    private void login(ActionEvent event) throws SQLException, IOException {
        if(logUser.getText().equals("")|| logPassword.getText().equals("")){
                             alert.setContentText("Login details not complete");
                            alert.showAndWait();
                            return;
        }
        connected = connectSQL.returnConnection();
        String newQuery = "SELECT * FROM users WHERE UserName = '" + logUser.getText() + "' AND Password = '" + logPassword.getText() + "'";
        Statement query = connected.createStatement();
        ResultSet result = query.executeQuery(newQuery);

        if (!result.next()) {
                                 alert.setContentText("Incorrect Login");
                            alert.showAndWait();
                            return;
        } else {
            System.out.println("Working");

            homeScreen tm = new homeScreen();
            Stage primaryStage = new Stage();
            tm.start(primaryStage);
        ((Node)(event.getSource())).getScene().getWindow().hide();
         
           
     
        }
    }

}
