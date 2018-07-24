/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class areaManager extends RecursiveTreeObject<areaManager> {

    public String areaManagerID;
    public String firstName;
    public String lastName;
  

    public areaManager(String areaManagerID, String firstName, String lastName) {
        this.areaManagerID =areaManagerID;
        this.firstName = firstName;
        this.lastName = lastName;
    

    }

    public String getAreaManagerID() {
        return areaManagerID;
    }

    public void setAreaManagerID(String areaManagerID) {

        this.areaManagerID=areaManagerID;
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
