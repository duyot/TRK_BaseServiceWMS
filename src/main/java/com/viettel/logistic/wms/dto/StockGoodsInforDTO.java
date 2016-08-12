/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.utils.StringUtils;

/**
 *
 * @author chudv
 */
public class StockGoodsInforDTO {

    private String custId;
    private String ownerId;
    private String ownerType;
    private String ownerCode;
    private String ownerName;
    private String goodsType;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private String amount;
    private String amountIssue;
    private String unitType;
    //ThienNG1 02.06.2015
    private String zoneName;
    private String cellCode;
    private String staffId;
    private String staffCode;
    private String staffName;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
    //

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

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    //
    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
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
        this.goodsName = StringUtils.HTMLEncode(goodsName);
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmountIssue() {
        return amountIssue;
    }

    public void setAmountIssue(String amountIssue) {
        this.amountIssue = amountIssue;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getCellCode() {
        return cellCode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
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

}
