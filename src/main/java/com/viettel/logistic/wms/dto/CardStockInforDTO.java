/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.dto;

/**
 *
 * @author thienng1
 */
public class CardStockInforDTO {

    private String stockTransId;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private String stockTransDate;
    private String stockTransType;
    private String stockTransCode;
    private String amountImportRecovered;
    private String deptId;
    private String transUserId;
    private String notes;
    private String amountRemain;
    private String amountImport;
    private String amountExport;
    //
    private String fromDate;
    private String toDate;
    private String goodsIsSerial;
    private String goodsIsSerialStrip;
    private String custId;
    private String ownerId;
    private String ownerType;

    public CardStockInforDTO() {
    }

    public String getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(String stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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
        this.goodsName = goodsName;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getStockTransDate() {
        return stockTransDate;
    }

    public void setStockTransDate(String stockTransDate) {
        this.stockTransDate = stockTransDate;
    }

    public String getStockTransType() {
        return stockTransType;
    }

    public void setStockTransType(String stockTransType) {
        this.stockTransType = stockTransType;
    }

    public String getStockTransCode() {
        return stockTransCode;
    }

    public void setStockTransCode(String stockTransCode) {
        this.stockTransCode = stockTransCode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAmountRemain() {
        return amountRemain;
    }

    public void setAmountRemain(String amountRemain) {
        this.amountRemain = amountRemain;
    }

    public String getAmountImport() {
        return amountImport;
    }

    public void setAmountImport(String amountImport) {
        this.amountImport = amountImport;
    }

    public String getAmountExport() {
        return amountExport;
    }

    public void setAmountExport(String amountExport) {
        this.amountExport = amountExport;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getGoodsIsSerial() {
        return goodsIsSerial;
    }

    public void setGoodsIsSerial(String goodsIsSerial) {
        this.goodsIsSerial = goodsIsSerial;
    }

    public String getGoodsIsSerialStrip() {
        return goodsIsSerialStrip;
    }

    public void setGoodsIsSerialStrip(String goodsIsSerialStrip) {
        this.goodsIsSerialStrip = goodsIsSerialStrip;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getAmountImportRecovered() {
        return amountImportRecovered;
    }

    public void setAmountImportRecovered(String amountImportRecovered) {
        this.amountImportRecovered = amountImportRecovered;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getTransUserId() {
        return transUserId;
    }

    public void setTransUserId(String transUserId) {
        this.transUserId = transUserId;
    }

}
