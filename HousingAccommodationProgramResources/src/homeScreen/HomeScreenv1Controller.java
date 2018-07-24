package homeScreen;

import expenseViewer.PropertyExpense;
import financeViewer.financeManagement;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import maintananceViewer.maintananceManagement;
import propertyViewer.PropertyManagement;
import tennantViewer.TennantManagement;
import utilityViewer.utilityViewer;

/**
 * FXML Controller class
 *
 * @author muzab
 */
public class HomeScreenv1Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void enterTenantViewer(ActionEvent event) throws IOException {

        TennantManagement tm = new TennantManagement();
        Stage primaryStage = new Stage();
        tm.start(primaryStage);

    }

    @FXML
    public void enterropertyViewer(ActionEvent event) throws IOException {

        PropertyManagement tm = new PropertyManagement();
        Stage primaryStage = new Stage();
        tm.start(primaryStage);

    }

    @FXML
    public void enterExpenseViewer(ActionEvent event) throws IOException {

        PropertyExpense tm = new PropertyExpense();
        Stage primaryStage = new Stage();
        tm.start(primaryStage);

    }
    
    @FXML
    public void enterUtilityViewer(ActionEvent event) throws IOException {

        utilityViewer tm = new utilityViewer();
        Stage primaryStage = new Stage();
        tm.start(primaryStage);

    } 
        public void enterFinanceViewer(ActionEvent event) throws IOException {

        financeManagement tm = new financeManagement();
        Stage primaryStage = new Stage();
        tm.start(primaryStage);

    } 
        
                public void enterMaintananceViewer(ActionEvent event) throws IOException {

        maintananceManagement tm = new maintananceManagement();
        Stage primaryStage = new Stage();
        tm.start(primaryStage);

    } 
        
}
