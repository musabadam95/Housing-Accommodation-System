/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expenseViewer;

import Database.properties;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class manageExpense {

    public properties prop;

    public manageExpense() {
    }

    public void setProperties(properties prop) {
        this.prop = prop;

    }

    public void start(Stage primaryStage) throws IOException {
   
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manageExpense.fxml"));
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> controllerClass) {
                if (controllerClass == manageExpenseController.class) {
                    manageExpenseController controller = new manageExpenseController();
                                 
                   controller.selectedProp = prop;
                    return controller;
                    
                } else {
                    try {
                        return controllerClass.newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        Parent root = (Parent) fxmlLoader.load();
               Scene scene = new Scene(root, 998, 611);      
  
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
