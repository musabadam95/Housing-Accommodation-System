/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Expense extends RecursiveTreeObject<Expense> {
    
    public StringProperty Property;
    public StringProperty January;
    public StringProperty February;
    public StringProperty March;
    public StringProperty April;
    public StringProperty May;
    public StringProperty June;
    public StringProperty July;
    public StringProperty August;
    public StringProperty Sepetember;
    public StringProperty October;
    public StringProperty November;
    public StringProperty December;
    public StringProperty expenseName;
    public StringProperty amount;
    public StringProperty quantity;
    public StringProperty totalCost;
    public StringProperty catergory;
    public StringProperty propertyID;
    public StringProperty datePaid;
    public StringProperty expenseID;
    
    public Expense(String propertyID, String Property, String January, String February, String March, String April, String May, String June, String July, String August, String September, String October, String November, String December) {
        this.Property = new SimpleStringProperty(Property);
        this.January = new SimpleStringProperty(January);
        this.February = new SimpleStringProperty(February);
        this.March = new SimpleStringProperty(March);
        this.April = new SimpleStringProperty(April);
        this.May = new SimpleStringProperty(May);
        this.June = new SimpleStringProperty(June);
        this.July = new SimpleStringProperty(July);
        this.August = new SimpleStringProperty(August);
        this.Sepetember = new SimpleStringProperty(September);
        this.October = new SimpleStringProperty(October);
        this.November = new SimpleStringProperty(November);
        this.December = new SimpleStringProperty(December);
        this.propertyID = new SimpleStringProperty(propertyID);
    }
    
    public Expense(String expenseID, String expenseName, String amount, String quantity, String totalCost, String catergory, String propertyID, String datePaid) {
        this.expenseName = new SimpleStringProperty(expenseName);
        this.amount = new SimpleStringProperty(amount);
        this.quantity = new SimpleStringProperty(quantity);
        this.totalCost = new SimpleStringProperty(totalCost);
        this.catergory = new SimpleStringProperty(catergory);
        this.propertyID = new SimpleStringProperty(propertyID);
        this.datePaid = new SimpleStringProperty(datePaid);
        this.expenseID = new SimpleStringProperty(expenseID);
    }
    
    public String getExpenseID() {
        return expenseID.get();
    }

    public void setExpenseID(String expenseID) {
        this.expenseID.set(expenseID);
    }

    public String getExpenseName() {
        return expenseName.get();
    }
    
    public void setExpenseName(String expenseName) {
        this.expenseName.set(expenseName);
    }
    
    public String getAmount() {
        return amount.get();
    }
    
    public void setAmount(String amount) {
        this.amount.set(amount);
    }
    
    public String getQuantity() {
        return quantity.get();
    }
    
    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }
    
    public String getTotalCost() {
        return totalCost.get();
    }
    
    public void setTotalCost(String totalCost) {
        this.totalCost.set(totalCost);
    }
    
    public String getCatergory() {
        return catergory.get();
    }
    
    public void setCatergory(String catergory) {
        this.catergory.set(catergory);
    }
    
    public String getPropertyID() {
        return propertyID.get();
    }
    
    public void setPropertyID(String propertyID) {
        this.propertyID.set(propertyID);
    }
    
    public String getDatePaid() {
        return datePaid.get();
    }
    
    public void setDatePaid(String datePaid) {
        this.datePaid.set(datePaid);
    }
    
    public String getJanuary() {
        return January.get();
    }
    
    public void setJanuary(String January) {
        this.January.set(January);
    }
    
    public String getFebruary() {
        return February.get();
    }
    
    public void setFebruary(String February) {
        this.February.set(February);
    }
    
    public String getMarch() {
        return March.get();
    }
    
    public void setMarch(String March) {
        this.March.set(March);
    }
    
    public String getApril() {
        return April.get();
    }
    
    public void setApril(String April) {
        this.April.set(April);
    }
    
    public String getMay() {
        return May.get();
    }
    
    public void setMay(String May) {
        this.May.set(May);
    }
    
    public String getJune() {
        return June.get();
    }
    
    public void setJune(String June) {
        this.June.set(June);
    }
    
    public String getJuly() {
        return July.get();
    }
    
    public void setJuly(String July) {
        this.July.set(July);
    }
    
    public String getAugust() {
        return August.get();
    }
    
    public void setAugust(String August) {
        this.August.set(August);
    }
    
    public String getSepetember() {
        return Sepetember.get();
    }
    
    public void setSepetember(String September) {
        this.Sepetember.set(September);
    }
    
    public String getOctober() {
        return October.get();
    }
    
    public void setOctober(String October) {
        this.October.set(October);
    }
    
    public String getNovember() {
        return November.get();
    }
    
    public void setNovember(String November) {
        this.November.set(November);
    }
    
    public String getDecember() {
        return December.get();
    }
    
    public void setDecember(String December) {
        this.December.set(December);
    }
    
    public String getProperty() {
        return Property.get();
    }
    
    public void setProperty(String Property) {
        this.Property.set(Property);
    }
    
}
