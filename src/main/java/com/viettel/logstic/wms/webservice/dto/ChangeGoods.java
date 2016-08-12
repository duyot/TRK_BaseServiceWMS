/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logstic.wms.webservice.dto;

import com.viettel.logistic.wms.dto.*;

/**
 *
 * @author Thienng1
 */
public class ChangeGoods {

    private String custId;
    private String custCode;
    private String custName;
    private String stockId;
    private String stockCode;
    private String changeType;
    private String oldGoodsId;
    private String oldGoodsCode; //ma hang moi
    private String oldGoodsName; //ma hang cu
    private String newGoodsId;
    private String newGoodsCode;//ma hang moi
    private String newGoodsName;//ten hang moi
    private String oldState; // trang thai cu
    private String newState; // trang thai moi
    private String Amount;//so luong
    private String oldFromSerial;//serial tu
    private String oldToSerial;//serial den
    private String newFromSerial;//serial den
    private String newToSerial;//serial den
    private String cellCode;
    private String barcode;
    private String desciption;
    private String inforError;

    public String getInforError() {
        return inforError;
    }

    public void setInforError(String inforError) {
        this.inforError = inforError;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public ChangeGoods() {
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getOldGoodsId() {
        return oldGoodsId;
    }

    public void setOldGoodsId(String oldGoodsId) {
        this.oldGoodsId = oldGoodsId;
    }

    public String getOldGoodsCode() {
        return oldGoodsCode;
    }

    public void setOldGoodsCode(String oldGoodsCode) {
        this.oldGoodsCode = oldGoodsCode;
    }

    public String getOldGoodsName() {
        return oldGoodsName;
    }

    public void setOldGoodsName(String oldGoodsName) {
        this.oldGoodsName = oldGoodsName;
    }

    public String getNewGoodsCode() {
        return newGoodsCode;
    }

    public void setNewGoodsCode(String newGoodsCode) {
        this.newGoodsCode = newGoodsCode;
    }

    public String getNewGoodsName() {
        return newGoodsName;
    }

    public void setNewGoodsName(String newGoodsName) {
        this.newGoodsName = newGoodsName;
    }

    public String getOldState() {
        return oldState;
    }

    public void setOldState(String oldState) {
        this.oldState = oldState;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getCellCode() {
        return cellCode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getNewGoodsId() {
        return newGoodsId;
    }

    public void setNewGoodsId(String newGoodsId) {
        this.newGoodsId = newGoodsId;
    }

    public String getOldFromSerial() {
        return oldFromSerial;
    }

    public void setOldFromSerial(String oldFromSerial) {
        this.oldFromSerial = oldFromSerial;
    }

    public String getOldToSerial() {
        return oldToSerial;
    }

    public void setOldToSerial(String oldToSerial) {
        this.oldToSerial = oldToSerial;
    }

    public String getNewFromSerial() {
        return newFromSerial;
    }

    public void setNewFromSerial(String newFromSerial) {
        this.newFromSerial = newFromSerial;
    }

    public String getNewToSerial() {
        return newToSerial;
    }

    public void setNewToSerial(String newToSerial) {
        this.newToSerial = newToSerial;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    @Override
    public String toString() {
        return "ChangeGoods{" + "changeType=" + changeType + ", oldGoodsCode=" + oldGoodsCode + ", oldGoodsName=" + oldGoodsName + ", newGoodsCode=" + newGoodsCode + ", newGoodsName=" + newGoodsName + ", oldState=" + oldState + ", newState=" + newState + ", Amount=" + Amount + ", oldFromSerial=" + oldFromSerial + ", oldToSerial=" + oldToSerial + ", newFromSerial=" + newFromSerial + ", newToSerial=" + newToSerial + ", desciption=" + desciption + ", inforError=" + inforError + '}';
    }

}
