/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class landlord extends RecursiveTreeObject<landlord> {

    public StringProperty LandlordID;
    public StringProperty LandlordName;
    public StringProperty AccountNo;
    public StringProperty SortCode;

    public landlord(String landlordID, String landlordName, String accountNo, String sortCode) {
        this.LandlordID = new SimpleStringProperty(landlordID);
        this.LandlordName = new SimpleStringProperty(landlordName);
        this.AccountNo = new SimpleStringProperty(accountNo);
        this.SortCode = new SimpleStringProperty(sortCode);

    }

    public String getLandlordID() {
        return LandlordID.get();
    }

    public void setLandlordID(String landlordid) {

        this.LandlordID.set(landlordid);
    }

    public String getLandlordName() {
        return LandlordName.get();
    }

    public void setLandlordName(String landlordname) {

        this.LandlordName.set(landlordname);
    }

    public String getAccountNo() {
        return AccountNo.get();
    }

    public void setAccountNo(String accountNo) {
        this.AccountNo.set(accountNo);
    }

    public String getSortCode() {
        return SortCode.get();
    }

    public void setSortCode(String sortcode) {
        this.SortCode.set(sortcode);
    }
}
