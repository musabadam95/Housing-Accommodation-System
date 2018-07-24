/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class issues extends RecursiveTreeObject<issues> {

    public properties selectedProp;
    public String Property;
    public String propertyID;
    public String issueID;
    public String Builder;
    public String jobStarted;
    public String jobCompleted;
    public String issue;
    public String comments;
    public String reportedBy;
    public String dateReported;
    public String status;
    public String levelOfUrgency;

    public issues(properties prop, String propertyID, String issueID, String issue, String status, String levelOfUrgency, String dateReported, String reportedBy) {
        selectedProp = prop;
        Property = prop.getPropertyAddress();
        this.propertyID = propertyID;
        this.issueID = issueID;
        this.issue = issue;
        this.reportedBy = reportedBy;
        this.dateReported = dateReported;
        this.levelOfUrgency = levelOfUrgency;
        this.status = status;
    }

    public issues(properties prop,String propertyID, String issueID, String Builder, String jobStarted, String jobCompleted, String issue, String comments, String reportedBy, String dateReported, String status,String levelOfUrgency) {
          selectedProp = prop;
         Property = prop.getPropertyAddress();
          this.propertyID = propertyID;
        this.issueID = issueID;
        this.Builder = Builder;
        this.jobStarted = jobStarted;
        this.jobCompleted = jobCompleted;
        this.issue = issue;
        this.comments = comments;
        this.reportedBy = reportedBy;
        this.dateReported = dateReported;
        this.levelOfUrgency = levelOfUrgency;
        this.status=status;
    }

    public properties getSelectedProp() {

        return selectedProp;
    }

    public void setSelectedProp(properties prop) {
        selectedProp = prop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevelOfUrgency() {
        return levelOfUrgency;
    }

    public void setLevelOfUrgency(String levelOfUrgency) {
        this.levelOfUrgency = levelOfUrgency;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String Issue) {

        this.issue = Issue;
    }

    public String getJobCompleted() {
        return jobCompleted;
    }

    public void setJobCompleted(String jobCompleted) {
        this.jobCompleted = jobCompleted;
    }

    public String getJobStarted() {
        return jobStarted;
    }

    public void setJobStarted(String jobStarted) {
        this.jobStarted = jobStarted;
    }

    public String getBuilder() {
        return Builder;
    }

    public void setBuilder(String Builder) {
        this.Builder = Builder;
    }

    public String getIssueID() {
        return issueID;
    }

    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }

    public String getDateReported() {
        return dateReported;
    }

    public void setDateReported(String dateReported) {
        this.dateReported = dateReported;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getProperty() {
        return Property;
    }

    public void setProperty(String Property) {
        this.Property = Property;
    }

}
