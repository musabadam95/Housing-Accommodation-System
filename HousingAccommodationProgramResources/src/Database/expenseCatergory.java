/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class expenseCatergory extends RecursiveTreeObject<expenseCatergory> {

    public StringProperty catergoryID;
    public StringProperty catergoryName;


    public expenseCatergory(String catergoryID, String catergoryName) {
        this.catergoryID = new SimpleStringProperty(catergoryID);
        this.catergoryName = new SimpleStringProperty(catergoryName);
       }



    public String getCatergoryName() {
        return catergoryName.get();
    }

    public void setCatergoryName(String catergoryName) {
        this.catergoryName.set(catergoryName);
    }

    public String getCatergoryID() {
        return catergoryID.get();
    }

    public void setCatergoryID(String catergoryID) {
        this.catergoryID.set(catergoryID);
    }

 

}
