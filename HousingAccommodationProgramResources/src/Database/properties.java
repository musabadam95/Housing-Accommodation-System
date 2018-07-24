/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class properties extends RecursiveTreeObject<properties> {
    
    public String PropertyID;
    public String FlatNo;
    public String DoorNo;
    public String FirstLine;
    public String PostCode;
    public String Town;
    public String Borough;
    public areaManager AreaManagerobj;
    public String AreaManager;
    public String ContractEnded;
    public String ContractStarted;
    public String Cost;
    public String DayOfMonth;
    public String LandlordID;
    public String propertyAddress;
    public String rentCost;
    public String DatePaid;
    
    public properties(String PropertyID, String FlatNo, String DoorNo, String FirstLine, String PostCode, String Town, String Borough, areaManager AreaManagerobj, String ContractEnded, String ContractStarted, String cost, String DayofMonth, String Landlord) {
        this.PropertyID = PropertyID;
        this.FlatNo = FlatNo;
        this.DoorNo = DoorNo;
        this.FirstLine = FirstLine;
        this.AreaManagerobj =AreaManagerobj;
        this.PostCode = PostCode;
        this.Town = Town;
        this.Borough = Borough;
        this.ContractEnded = ContractEnded;
        this.ContractStarted = ContractStarted;
        this.Cost = cost;
        this.DayOfMonth = DayofMonth;
        this.LandlordID = Landlord;
    }
    
    
        public properties(String PropertyID, String FlatNo, String DoorNo, String FirstLine, String PostCode, String Town, String Borough, String AreaManager, String ContractEnded, String ContractStarted, String cost, String DayofMonth, String Landlord) {
        this.PropertyID = PropertyID;
        this.FlatNo = FlatNo;
        this.DoorNo = DoorNo;
        this.FirstLine = FirstLine;
        this.AreaManager =AreaManager;
        this.PostCode = PostCode;
        this.Town = Town;
        this.Borough = Borough;
        this.ContractEnded = ContractEnded;
        this.ContractStarted = ContractStarted;
        this.Cost = cost;
        this.DayOfMonth = DayofMonth;
        this.LandlordID = Landlord;
    }
    
    
    public properties(String PropertyID,String propertyAddress, String rentCost, String DatePaid) {
         this.PropertyID = PropertyID;
        this.propertyAddress = propertyAddress;
        this.rentCost = rentCost;
        this.DatePaid = DatePaid;
        
    }
 

    public String getPropertyAddress() {
        return propertyAddress;
    }
    
    public String getRentCost() {
        return rentCost;
    }
    
    public String getDatePaid() {
        return DatePaid;
    }
    
    public void setPropertyAddress(String Address) {
        this.propertyAddress=Address;
    }

    public void setRentCost(String RentCost) {
        this.rentCost=RentCost;
    }

    public void setDatePaid(String DatePaid){
    
    this.DatePaid=DatePaid;
    }
    
    public String getCost() {
        return Cost;
    }
    
    public String getDayOfMonth() {
        return DayOfMonth;
    }
    
    public String getLandlordID() {
        return LandlordID;
    }
    
    public void setCost(String Cost) {
        this.Cost=Cost;
    }
    
    public void setDayOfMonth(String DayOfMonth) {
        this.DayOfMonth=DayOfMonth;
    }
    
    public void setLandlordID(String LandlordID) {
        this.LandlordID=LandlordID;
    }
    
    public String getAreaManager() {
        return AreaManagerobj.firstName+" "+AreaManagerobj.lastName;
    }
    
    public void setAreaManager(String AreaManager) {
        this.AreaManager=AreaManager;
    }
    
    public String getContractEnded() {
        return ContractEnded;
    }
    
    public void setContractEnded(String ContractEnded) {
        this.ContractEnded=ContractEnded;
    }
    
    public void setContractStarted(String ContractStarted) {
        this.ContractStarted=ContractStarted;
    }
    
    public String getContractStarted() {
        return ContractStarted;
    }
    
    public String getBorough() {
        return Borough;
    }
    
    public String getPropertyID() {
        return PropertyID;
    }
    
    public String getFlatNo() {
        return FlatNo;
    }
    
    public String getDoorNo() {
        return DoorNo;
    }
    
    public String getFirstLine() {
        return FirstLine;
    }
    
    public String getPostCode() {
        return PostCode;
    }
    
    public String getTown() {
        return Town;
    }
    
    public void setBorough(String Borough) {
        
        this.Borough=Borough;
    }
    
    public void setTown(String Town) {
        this.Town=Town;
    }
    
    public void setPropertyID(String PropertyID) {
        this.PropertyID=PropertyID;
    }
    
    public void setFlatNo(String FlatNo) {
        this.FlatNo=FlatNo;
    }
    
    public void setDoorNo(String DoorNo) {
        this.DoorNo=DoorNo;
    }
    
    public void setFirstLine(String FirstLine) {
        this.FirstLine=FirstLine;
    }
    
    public void setPostCode(String PostCode) {
        this.PostCode=PostCode;
    }
    
}
