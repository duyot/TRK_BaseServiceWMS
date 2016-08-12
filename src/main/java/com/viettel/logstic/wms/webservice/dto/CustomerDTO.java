/**
 * @(#)CustomerForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logstic.wms.webservice.dto;

public class CustomerDTO {

    //Fields
    private String custId;
    private String code;
    private String name;
    private String custType;
    private String bussTypeList;
    private String phoneNumber;
    private String fax;
    private String email;
    private String taxCode;
    private String accountNo;
    private String bankName;
    private String agency;
    private String address;
    private String provinceId;
    private String districtId;
    private String wardId;
    private String status;
    private String deployAddress;
    private String deployProvinceId;
    private String deployDistictId;
    private String deployWardId;
    //private static long changedTime = 0;

    //Constructor
    public CustomerDTO() {

    }

    public CustomerDTO(String custId, String code, String name, String custType, String bussTypeList, String phoneNumber, String fax, String email, String taxCode, String accountNo, String bankName, String agency, String address, String provinceId, String districtId, String wardId, String status, String deployAddress, String deployProvinceId, String deployDistictId, String deployWardId) {
        this.custId = custId;
        this.code = code;
        this.name = name;
        this.custType = custType;
        this.bussTypeList = bussTypeList;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
        this.email = email;
        this.taxCode = taxCode;
        this.accountNo = accountNo;
        this.bankName = bankName;
        this.agency = agency;
        this.address = address;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.status = status;
        this.deployAddress = deployAddress;
        this.deployProvinceId = deployProvinceId;
        this.deployDistictId = deployDistictId;
        this.deployWardId = deployWardId;
    }
    //Getters and setters

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
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

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getCustType() {
        return custType;
    }

    public void setBussTypeList(String bussTypeList) {
        this.bussTypeList = bussTypeList;
    }

    public String getBussTypeList() {
        return bussTypeList;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAgency() {
        return agency;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDeployAddress(String deployAddress) {
        this.deployAddress = deployAddress;
    }

    public String getDeployAddress() {
        return deployAddress;
    }

    public void setDeployProvinceId(String deployProvinceId) {
        this.deployProvinceId = deployProvinceId;
    }

    public String getDeployProvinceId() {
        return deployProvinceId;
    }

    public void setDeployDistictId(String deployDistictId) {
        this.deployDistictId = deployDistictId;
    }

    public String getDeployDistictId() {
        return deployDistictId;
    }

    public void setDeployWardId(String deployWardId) {
        this.deployWardId = deployWardId;
    }

    public String getDeployWardId() {
        return deployWardId;
    }

}
