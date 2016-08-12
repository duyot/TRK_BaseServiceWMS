/**
 * @(#)CellsForm.java ,
 * Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */

package com.viettel.logistic.wms.dto;


import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.Cells;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;
/**
* @author ngocnd6
* @version 1.0
* @since 08-Apr-15 9:36 AM
*/
public class StockTransInforDTO {    
	//Fields
    private String stockTransId;
    private String stockTransDate;
    private String stockTransStatus;
    private String custId;
    private String ownerId;
    private String ownerType;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsIsSerial;
    private String goodsIsSerialStrip;
    private String goodsState;
    private String fromSerial;
    private String toSerial;
    private String quantity;
    private String barcode;
    private String bincode;
    private String cellCode;
    private String fromDate;
    private String toDate;

    public String getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(String stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getStockTransDate() {
        return stockTransDate;
    }

    public void setStockTransDate(String stockTransDate) {
        this.stockTransDate = stockTransDate;
    }

    public String getStockTransStatus() {
        return stockTransStatus;
    }

    public void setStockTransStatus(String stockTransStatus) {
        this.stockTransStatus = stockTransStatus;
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

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCellCode() {
        return cellCode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
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

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBincode() {
        return bincode;
    }

    public void setBincode(String bincode) {
        this.bincode = bincode;
    }

}
