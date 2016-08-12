/**
 * @(#)StockPlanForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.wms.dto;

import com.viettel.logstic.wms.webservice.dto.BaseDTO;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author QuyenDM
 * @version 1.0
 * @since 07-May-15 2:49 PM
 */
@XmlRootElement(name = "StockPlan")
public class StockPlanDTO extends BaseDTO {
    //Fields
    private String id;
    private String planDate;
    private String code;
    private String planType;
    private String status;
    private String orderActionIdList;
    private String expAreaIdList;
    private String expDescription;
    private String expStartDate;
    private String expEndDate;
    private String zoneEndDate;
    private String zoneDescription;
    private String zoneIdList;
    private static long changedTime = 0;

    //Constructor

    public StockPlanDTO() {

    }

    public StockPlanDTO(String id, String planDate, String code, String planType, String status, String orderActionIdList, String expAreaIdList, String expDescription, String expStartDate, String expEndDate, String zoneEndDate, String zoneDescription, String zoneIdList) {
        this.id = id;
        this.planDate = planDate;
        this.code = code;
        this.planType = planType;
        this.status = status;
        this.orderActionIdList = orderActionIdList;
        this.expAreaIdList = expAreaIdList;
        this.expDescription = expDescription;
        this.expStartDate = expStartDate;
        this.expEndDate = expEndDate;
        this.zoneEndDate = zoneEndDate;
        this.zoneDescription = zoneDescription;
        this.zoneIdList = zoneIdList;
    }
	//Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanType() {
        return planType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderActionIdList(String orderActionIdList) {
        this.orderActionIdList = orderActionIdList;
    }

    public String getOrderActionIdList() {
        return orderActionIdList;
    }

    public void setExpAreaIdList(String expAreaIdList) {
        this.expAreaIdList = expAreaIdList;
    }

    public String getExpAreaIdList() {
        return expAreaIdList;
    }

    public void setExpDescription(String expDescription) {
        this.expDescription = expDescription;
    }

    public String getExpDescription() {
        return expDescription;
    }

    public void setExpStartDate(String expStartDate) {
        this.expStartDate = expStartDate;
    }

    public String getExpStartDate() {
        return expStartDate;
    }

    public void setExpEndDate(String expEndDate) {
        this.expEndDate = expEndDate;
    }

    public String getExpEndDate() {
        return expEndDate;
    }

    public void setZoneEndDate(String zoneEndDate) {
        this.zoneEndDate = zoneEndDate;
    }

    public String getZoneEndDate() {
        return zoneEndDate;
    }

    public void setZoneDescription(String zoneDescription) {
        this.zoneDescription = zoneDescription;
    }

    public String getZoneDescription() {
        return zoneDescription;
    }

    public void setZoneIdList(String zoneIdList) {
        this.zoneIdList = zoneIdList;
    }

    public String getZoneIdList() {
        return zoneIdList;
    }

}
