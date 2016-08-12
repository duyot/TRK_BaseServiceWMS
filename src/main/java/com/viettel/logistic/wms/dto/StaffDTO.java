/**
 * @(#)StaffForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 16-Apr-15 1:28 PM
 */
@XmlRootElement(name = "Staff")
public class StaffDTO {

    //Fields
    private String staffId;
    private String changedTime = "0";
    private String defaultSortField = "code";
    private String code;
    private String name;
    private String birthDate;
    private String email;
    private String telNumber;
    private String deptId;
    private String deptName;//map deptId 2 Name
    private String deptIdName;
    private String staffType;
    private String staffTypeName;//map type name
    private String status;
    private String statusName;//map status
    private String stockId;//kho muon gan cho nv
    private String staffStockId;
    //them 3 truong account
    private String vofficeAccount;
    private String ttnsAccount;
    private String otherAccount;

    public StaffDTO() {
    }

    public StaffDTO(String staffId, String code, String name, String birthDate, String email, String telNumber, String deptId, String staffType, String status, String changedTime, String defaultSortField ) {
        this.staffId = staffId;
        this.code = code;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.telNumber = telNumber;
        this.deptId = deptId;
        this.staffType = staffType;
        this.status = status;
        this.changedTime = changedTime;
        this.defaultSortField = defaultSortField;
    }
    //Getters and setters

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptIdName(String deptIdName) {
        this.deptIdName = deptIdName;
    }

    public String getDeptIdName() {
        return deptIdName;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getStaffTypeName() {
        return staffTypeName;
    }

    public void setStaffTypeName(String staffTypeName) {
        this.staffTypeName = staffTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStaffStockId() {
        return staffStockId;
    }

    public void setStaffStockId(String staffStockId) {
        this.staffStockId = staffStockId;
    }

    public String getVofficeAccount() {
        return vofficeAccount;
    }

    public void setVofficeAccount(String vofficeAccount) {
        this.vofficeAccount = vofficeAccount;
    }

    public String getTtnsAccount() {
        return ttnsAccount;
    }

    public void setTtnsAccount(String ttnsAccount) {
        this.ttnsAccount = ttnsAccount;
    }

    public String getOtherAccount() {
        return otherAccount;
    }

    public void setOtherAccount(String otherAccount) {
        this.otherAccount = otherAccount;
    }

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

}
