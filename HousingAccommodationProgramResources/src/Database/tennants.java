/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


public class tennants extends RecursiveTreeObject<tennants> {

    public String tennantID;
    public String FirstName;
    public String LastName;
    public String DateOfBirth;
    public String LivesAtPK;
    public String Address;
    public String LivingFrom;

    public tennants(String tennantID, String FirstName, String LastName, String DateOfBirth, String LivesAtPK, String Address, String LivingFrom) {
        this.tennantID =tennantID;
        this.FirstName =FirstName;
        this.LastName =LastName;
        this.DateOfBirth =DateOfBirth;
        this.LivesAtPK =LivesAtPK;
        this.Address =Address;
        this.LivingFrom =LivingFrom;

    }
    public tennants(String tennantID, String FirstName, String LastName, String DateOfBirth, String LivesAtPK,String LivingFrom) {
        this.tennantID =tennantID;
        this.FirstName =FirstName;
        this.LastName =LastName;
        this.DateOfBirth =DateOfBirth;
        this.LivesAtPK =LivesAtPK;

        this.LivingFrom =LivingFrom;

    }

    public String getLivingFrom() {
return LivingFrom;
    }

    public String getTennantID() {
        return tennantID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public String getLivesAtPK() {
        return LivesAtPK;
    }

    public String getAddress() {
        return Address;
    }

    public void setLivingFrom(String LivingFrom){
        
        this.LivingFrom=LivingFrom;
    }
    
    public void setAddress(String Address) {
        this.Address=Address;
    }

    public void setTennantID(String TennantID) {
        this.tennantID=TennantID;
    }

    public void setFirstName(String FirstName) {
        this.FirstName=FirstName;
    }

    public void setLastName(String LastName) {
        this.LastName=LastName;
    }

    public void setDateOfBirth(String DateOfBirth) {
        this.DateOfBirth=DateOfBirth;
    }

    public void setLivesAtPK(String LivesAtPK) {
        this.LivesAtPK=LivesAtPK;
    }

}
