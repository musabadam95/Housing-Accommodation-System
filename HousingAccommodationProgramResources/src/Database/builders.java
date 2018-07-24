/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class builders extends RecursiveTreeObject<builders> {

    public String builderID;
    public String firstName;
    public String lastName;
  

    public builders(String builderID, String firstName, String lastName) {
        this.builderID =builderID;
        this.firstName = firstName;
        this.lastName = lastName;
    

    }

    public String getBuilderID() {
        return builderID;
    }

    public void setBuilderID(String builderID) {

        this.builderID=builderID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName=firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

  
}
