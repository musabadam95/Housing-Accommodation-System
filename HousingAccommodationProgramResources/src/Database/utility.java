/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class utility extends RecursiveTreeObject<utility> {

    public String type;
    public String Company;
    public String Cost;
    public String AccountNo;
    public String elecCompany;
    public String elecAccountNo;
    public String elecCost;
    public String gasCompany;
    public String gasAccountNo;
    public String gasCost;
    public String waterCompany;
    public String waterAccountNo;
    public String waterCost;
    public String cTaxCompany;
    public String cTaxAccountNo;
    public String cTaxCost;
    public String address;
    public properties selectedProp;
    public String propertyID;

    public utility(String propertyID, properties prop, String elecCompany, String elecAccountNo, String elecCost, String gasCompany, String gasAccountNo, String gasCost, String waterCompany, String waterAccountNo, String waterCost, String cTaxCompany, String cTaxAccountNo, String cTaxCost) {
        this.selectedProp = prop;
        this.elecCompany = elecCompany;
        this.elecAccountNo = elecAccountNo;
        this.elecCost = elecCost;
        this.gasCompany = gasCompany;
        this.gasAccountNo = gasAccountNo;
        this.gasCost = gasCost;
        this.waterCompany = waterCompany;
        this.waterAccountNo = waterAccountNo;
        this.waterCost = waterCost;
        this.cTaxCompany = cTaxCompany;
        this.cTaxAccountNo = cTaxAccountNo;
        this.cTaxCost = cTaxCost;

        this.address = selectedProp.propertyAddress;
        this.propertyID = propertyID;
    }

    public utility(String type, String propertyID, properties prop, String Company, String AccountNo, String Cost) {
        this.selectedProp = prop;
        this.Company = Company;
        this.AccountNo = AccountNo;
        this.Cost = Cost;
        this.type = type;
        this.propertyID = propertyID;
        this.address = selectedProp.propertyAddress;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String AccountNo) {
        this.AccountNo = AccountNo;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String Cost) {
        this.Cost = Cost;
    }

    public String getElecCompany() {
        return elecCompany;
    }

    public void setElecCompany(String elecCompany) {
        this.elecCompany = elecCompany;
    }

    public String getElecAccountNo() {
        return elecAccountNo;
    }

    public void setElecAccountNo(String elecAccountNo) {
        this.elecAccountNo = elecAccountNo;
    }

    public String getElecCost() {
        return elecCost;
    }

    public void setElecCost(String elecCost) {
        this.elecCost = elecCost;
    }

    public String getGasCompany() {
        return gasCompany;
    }

    public void setGasCompany(String gasCompany) {
        this.gasCompany = gasCompany;
    }

    public String getGasAccountNo() {
        return gasAccountNo;
    }

    public void setGasAccountNo(String gasAccountNo) {
        this.gasAccountNo = gasAccountNo;
    }

    public String getGasCost() {
        return gasCost;
    }

    public void setGasCost(String gasCost) {
        this.gasCost = gasCost;
    }

    public String getWaterCompany() {
        return waterCompany;
    }

    public void setWaterCompany(String waterCompany) {
        this.waterCompany = waterCompany;
    }

    public String getWaterAccountNo() {
        return waterAccountNo;
    }

    public void setWaterAccountNo(String waterAccountNo) {
        this.waterAccountNo = waterAccountNo;
    }

    public String getWaterCost() {
        return waterCost;
    }

    public void setWaterCost(String waterCost) {
        this.waterCost = waterCost;
    }

    public String getCTaxCompany() {
        return cTaxCompany;
    }

    public void setCTaxCompany(String cTaxCompany) {
        this.cTaxCompany = cTaxCompany;
    }

    public String getCTaxAccountNo() {
        return cTaxAccountNo;
    }

    public void setCTaxAccountNo(String cTaxAccountNo) {
        this.cTaxAccountNo = cTaxAccountNo;
    }

    public String getCTaxCost() {
        return cTaxCost;
    }

    public void setCTaxCost(String cTaxCost) {
        this.cTaxCost = cTaxCost;
    }

}
