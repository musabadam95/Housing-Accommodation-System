/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class finance extends RecursiveTreeObject<finance> {
    
    public String Property;
    public String January;
    public String February;
    public String March;
    public String April;
    public String May;
    public String June;
    public String July;
    public String August;
    public String Sepetember;
    public String October;
    public String November;
    public String December;
    public String expenseName;
    public String amount;
    public String quantity;
    public String totalCost;
    public String catergory;
    public String propertyID;
    public String datePaid;
    public String expenseID;
    public String cost;
    public finance(String propertyID, String Property, String January, String February, String March, String April, String May, String June, String July, String August, String September, String October, String November, String December) {
        this.Property = Property;
        this.January = January;
        this.February = February;
        this.March = March;
        this.April = April;
        this.May = May;
        this.June = June;
        this.July =July;
        this.August = August;
        this.Sepetember = September;
        this.October = October;
        this.November = November;
        this.December = December;
        this.propertyID = propertyID;
    }
    
    public finance(String expenseID, String expenseName, String amount, String quantity, String totalCost, String catergory, String propertyID, String datePaid) {
        this.expenseName = expenseName;
        this.amount = amount;
        this.quantity = quantity;
        this.totalCost =totalCost;
        this.catergory = catergory;
        this.propertyID =propertyID;
        this.datePaid = datePaid;
        this.expenseID =expenseID;
    }
    
    public finance(String type, String cost ){
    
    this.expenseName=type;
    this.cost=cost;
    }
    
    
     public String getCost() {
        return cost;
    }

    public void setCost(String Cost) {
        this.cost=Cost;
    }
    
    
    public String getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(String expenseID) {
        this.expenseID=expenseID;
    }

    public String getExpenseName() {
        return expenseName;
    }
    
    public void setExpenseName(String expenseName) {
        this.expenseName=expenseName;
    }
    
    public String getAmount() {
        return amount;
    }
    
    public void setAmount(String amount) {
        this.amount=amount;
    }
    
    public String getQuantity() {
        return quantity;
    }
    
    public void setQuantity(String quantity) {
        this.quantity=quantity;
    }
    
    public String getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(String totalCost) {
        this.totalCost=totalCost;
    }
    
    public String getCatergory() {
        return catergory;
    }
    
    public void setCatergory(String catergory) {
        this.catergory=catergory;
    }
    
    public String getPropertyID() {
        return propertyID;
    }
    
    public void setPropertyID(String propertyID) {
        this.propertyID=propertyID;
    }
    
    public String getDatePaid() {
        return datePaid;
    }
    
    public void setDatePaid(String datePaid) {
        this.datePaid=datePaid;
    }
    
    public String getJanuary() {
        return January;
    }
    
    public void setJanuary(String January) {
        this.January=January;
    }
    
    public String getFebruary() {
        return February;
    }
    
    public void setFebruary(String February) {
        this.February=February;
    }
    
    public String getMarch() {
        return March;
    }
    
    public void setMarch(String March) {
        this.March=March;
    }
    
    public String getApril() {
        return April;
    }
    
    public void setApril(String April) {
        this.April=April;
    }
    
    public String getMay() {
        return May;
    }
    
    public void setMay(String May) {
        this.May=May;
    }
    
    public String getJune() {
        return June;
    }
    
    public void setJune(String June) {
        this.June=June;
    }
    
    public String getJuly() {
        return July;
    }
    
    public void setJuly(String July) {
        this.July=July;
    }
    
    public String getAugust() {
        return August;
    }
    
    public void setAugust(String August) {
        this.August=August;
    }
    
    public String getSepetember() {
        return Sepetember;
    }
    
    public void setSepetember(String September) {
        this.Sepetember=September;
    }
    
    public String getOctober() {
        return October;
    }
    
    public void setOctober(String October) {
        this.October=October;
    }
    
    public String getNovember() {
        return November;
    }
    
    public void setNovember(String November) {
        this.November=November;
    }
    
    public String getDecember() {
        return December;
    }
    
    public void setDecember(String December) {
        this.December=December;
    }
    
    public String getProperty() {
        return Property;
    }
    
    public void setProperty(String Property) {
        this.Property=Property;
    }
    
}
