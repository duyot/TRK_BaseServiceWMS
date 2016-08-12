/**
 * @(#)AppParamsForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logstic.wms.webservice.dto;

/**
 * @author Truongbx3
 * @version 1.0
 * @since 07-Apr-15 8:53 AM
 */
public class AppParamsDTO {

    //Fields
    private String parId;
    private String parType;
    private String parCode;
    private String parName;
    private String parOrder;
    private String description;
    private String status;

    //Constructor
    public AppParamsDTO() {
    }

    public AppParamsDTO(String status) {
        this.status = status;
    }

    public AppParamsDTO(String description, String parCode, String parName, String parOrder, String parType) {
        this.description = description;
        this.parCode = parCode;
        this.parName = parName;
        this.parOrder = parOrder;
        this.parType = parType;

    }

    public AppParamsDTO(String parId, String parType, String parCode, String parName, String parOrder, String description, String status) {
        this.parId = parId;
        this.parType = parType;
        this.parCode = parCode;
        this.parName = parName;
        this.parOrder = parOrder;
        this.description = description;
        this.status = status;
    }
    //Getters and setters

    public void setParId(String parId) {
        this.parId = parId;
    }

    public String getParId() {
        return parId;
    }

    public void setParType(String parType) {
        this.parType = parType;
    }

    public String getParType() {
        return parType;
    }

    public void setParCode(String parCode) {
        this.parCode = parCode;
    }

    public String getParCode() {
        return parCode;
    }

    public void setParName(String parName) {
        this.parName = parName;
    }

    public String getParName() {
        return parName;
    }

    public void setParOrder(String parOrder) {
        this.parOrder = parOrder;
    }

    public String getParOrder() {
        return parOrder;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
