/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintananceViewer;

import Database.issues;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author muzab
 */
public class initateJob {
    private issues selectedissues;
    public initateJob(issues selectedissues){
    this.selectedissues=selectedissues;
    }

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("initateJob.fxml"));
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> controllerClass) {
                if (controllerClass == initateJobController.class) {
                    initateJobController controller = new initateJobController();
                    controller.selectedissue = selectedissues;
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
