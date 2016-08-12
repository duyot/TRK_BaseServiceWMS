/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logstic.wms.webservice.dto;



/**
 *
 * @author TruongBx3
 */
public class BaseDTO {

    public String changedTime = "0";
    public String defaultSortField;
    public String userNameLogging;

    public String getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(String changedTime) {
        this.changedTime = changedTime;
    }

    public String getDefaultSortField() {
        return defaultSortField;
    }

    public void setDefaultSortField(String defaultSortField) {
        this.defaultSortField = defaultSortField;
    }

    public String getUserNameLogging() {

        return userNameLogging;
    }

    public void setUserNameLogging(String userNameLogging) {
        this.userNameLogging = userNameLogging;
    }

}
