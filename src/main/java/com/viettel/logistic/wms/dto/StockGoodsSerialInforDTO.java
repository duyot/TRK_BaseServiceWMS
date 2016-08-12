/**
 * @(#)StockGoodsSerialStripForm.java , Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.utils.StringUtils;

public class StockGoodsSerialInforDTO {

    //Fields
    private String id;
    private String custId;
    private String ownerId;
    private String ownerType;
    private String cellCode;
    private String goodsId;
    private String goodsState;
    private String status;
    private String fromSerial;
    private String toSerial;
    private String saleType;
    private String price;
    private String channelTypeId;
    private String cellId;
    private String barcode;
    private String changeUser;
    private String changeDate;
    private String importDate;
    private String saleDate;
    private String quantity;
    private String bincode;
    private String addInfor;
    //
    private String goodsType;
    private String goodsGroup;
    private String goodsCode;
    private String goodsName;

    private String errorInfo;
    private String orderId;
    private String couponCode;
    //lay thong tin nhan vien
    private String staffId;
    private String staffCode;
    private String staffName;
    //duyOT
    private String receiveCode;
    private String receiveName;
    private String orderCode;
    //Constructor
    //thienng1 - 19/04/2016
    private String custIdInventory;
    private String ownerIdInventory;
    private String goodsUnitType;
    private String goodsUnitTypeName;
    private String amount;
    private String amountInventory;
    private String amountFalse;
    //thienng1 - 09/05/2016
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustIdInventory() {
        return custIdInventory;
    }

    public void setCustIdInventory(String custIdInventory) {
        this.custIdInventory = custIdInventory;
    }

    public String getOwnerIdInventory() {
        return ownerIdInventory;
    }

    public void setOwnerIdInventory(String ownerIdInventory) {
        this.ownerIdInventory = ownerIdInventory;
    }

    public String getGoodsUnitType() {
        return goodsUnitType;
    }

    public void setGoodsUnitType(String goodsUnitType) {
        this.goodsUnitType = goodsUnitType;
    }

    public String getGoodsUnitTypeName() {
        return goodsUnitTypeName;
    }

    public void setGoodsUnitTypeName(String goodsUnitTypeName) {
        this.goodsUnitTypeName = goodsUnitTypeName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmountInventory() {
        return amountInventory;
    }

    public void setAmountInventory(String amountInventory) {
        this.amountInventory = amountInventory;
    }

    public String getAmountFalse() {
        return amountFalse;
    }

    public void setAmountFalse(String amountFalse) {
        this.amountFalse = amountFalse;
    }

    //Constructor
    public StockGoodsSerialInforDTO(String custId, String ownerId, String ownerType,
            String cellCode, String goodsId, String goodsState, String status,
            String fromSerial, String barcode) {
        this.custId = custId;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.cellCode = cellCode;
        this.goodsId = goodsId;
        this.goodsState = goodsState;
        this.status = status;
        this.fromSerial = fromSerial;
        this.barcode = barcode;
    }

    public StockGoodsSerialInforDTO() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setChannelTypeId(String channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getChannelTypeId() {
        return channelTypeId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getCellId() {
        return cellId;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setChangeUser(String changeUser) {
        this.changeUser = changeUser;
    }

    public String getChangeUser() {
        return changeUser;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setBincode(String bincode) {
        this.bincode = bincode;
    }

    public String getBincode() {
        return bincode;
    }

    public void setAddInfor(String addInfor) {
        this.addInfor = addInfor;
    }

    public String getAddInfor() {
        return addInfor;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsGroup() {
        return goodsGroup;
    }

    public void setGoodsGroup(String goodsGroup) {
        this.goodsGroup = goodsGroup;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = StringUtils.HTMLEncode(goodsName);
    }

    public String getCellCode() {
        return cellCode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

}
