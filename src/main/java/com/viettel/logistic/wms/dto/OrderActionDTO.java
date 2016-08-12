/**
 * @(#)OrderActionForm.java ,
 * Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

//import com.viettel.logistics.cms.webservice.dto.BaseDTO;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 5:40 PM
 */
@XmlRootElement(name = "OrderAction")
public class OrderActionDTO {

    //Fields

    private String id;
    private String code;
    private String actionType;
    private String orderType;
    private String orderDate;
    private String finishOrderDate;
    private String content;
    private String orderStockId;
    private String status;
    private String requireVolume;
    private String requireWeight;
    private String orderIdList;
    private String custId;
    private String custCode;
    private String custName;
    private String staffCreatedId;
    private String staffCreatedName;
    private String contactName;
    private String statusName;
    private String orderTypeName;
    private String createDatetime;
    //
    private String orderStockName;
    //
    //duyot new fields
    private String partnerId;
    //
    private String orderCode;
    private String synTransCode;
    private String orderCommandCode;
    //
    private String sygnVoffice;

    //Constructor
    public OrderActionDTO() {

    }

    public OrderActionDTO(String id, String code, String actionType, String orderType, String orderDate, String finishOrderDate, String content, String orderStockId, String status, String requireVolume, String requireWeight, String orderIdList, String custId, String custCode, String custName, String staffCreatedId, String staffCreatedName, String contactName, String statusName, String createDatetime) {
        this.id = id;
        this.code = code;
        this.actionType = actionType;
        this.orderType = orderType;
        this.orderDate = orderDate;
        this.finishOrderDate = finishOrderDate;
        this.content = content;
        this.orderStockId = orderStockId;
        this.status = status;
        this.requireVolume = requireVolume;
        this.requireWeight = requireWeight;
        this.orderIdList = orderIdList;
        this.custId = custId;
        this.custCode = custCode;
        this.custName = custName;
        this.staffCreatedId = staffCreatedId;
        this.staffCreatedName = staffCreatedName;
        this.contactName = contactName;
        this.statusName = statusName;
        this.createDatetime = createDatetime;
    }

    public OrderActionDTO(String code, String finishOrderDate, String content, String status) {
        this.code = code;
        this.finishOrderDate = finishOrderDate;
        this.content = content;
        this.status = status;
    }

    public OrderActionDTO(String status) {
        this.status = status;
    }

	//Getters and setters
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setFinishOrderDate(String finishOrderDate) {
        this.finishOrderDate = finishOrderDate;
    }

    public String getFinishOrderDate() {
        return finishOrderDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setOrderStockId(String orderStockId) {
        this.orderStockId = orderStockId;
    }

    public String getOrderStockId() {
        return orderStockId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRequireVolume(String requireVolume) {
        this.requireVolume = requireVolume;
    }

    public String getRequireVolume() {
        return requireVolume;
    }

    public void setRequireWeight(String requireWeight) {
        this.requireWeight = requireWeight;
    }

    public String getRequireWeight() {
        return requireWeight;
    }

    public void setOrderIdList(String orderIdList) {
        this.orderIdList = orderIdList;
    }

    public String getOrderIdList() {
        return orderIdList;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

    public void setStaffCreatedId(String staffCreatedId) {
        this.staffCreatedId = staffCreatedId;
    }

    public String getStaffCreatedId() {
        return staffCreatedId;
    }

    public void setStaffCreatedName(String staffCreatedName) {
        this.staffCreatedName = staffCreatedName;
    }

    public String getStaffCreatedName() {
        return staffCreatedName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getOrderStockName() {
        return orderStockName;
    }

    public void setOrderStockName(String orderStockName) {
        this.orderStockName = orderStockName;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSynTransCode() {
        return synTransCode;
    }

    public void setSynTransCode(String synTransCode) {
        this.synTransCode = synTransCode;
    }

    public String getOrderCommandCode() {
        return orderCommandCode;
    }

    public void setOrderCommandCode(String orderCommandCode) {
        this.orderCommandCode = orderCommandCode;
    }

    public String getSygnVoffice() {
        return sygnVoffice;
    }

    public void setSygnVoffice(String sygnVoffice) {
        this.sygnVoffice = sygnVoffice;
    }
    
    
    

}
