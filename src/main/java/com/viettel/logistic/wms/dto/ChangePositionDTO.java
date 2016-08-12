/**
 * @(#)ChangePositionForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.ChangePosition;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author HungKV
 * @version 1.0
 * @since 6/9/2015 9:40 AM
 */
@XmlRootElement(name = "ChangePosition")
public class ChangePositionDTO extends BaseFWDTO<ChangePosition> {

    //Fields
    private String id;
    private String stockId;
    private String stockCode;
    private String stockName;
    private String customerId;
    private String customerCode;
    private String customerName;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String implementDate;
    private String implementerId;
    private String implementerName;
    private String cellCodeOld;
    private String cellCodeNew;
    private String barcode;
    private String fromSerial;
    private String toSerial;
    private String note;
    private String quantity;
    private static long changedTime = 0;
    //QuyenDM 15/06/2015 them cac truong phuc vu don dich kho
    private String goodsType;
    private String isSerial;
    private String isSerialStrip;
    private String ownerType;
    private String goodsState;
    private String zoneName;
    private String errorInfor;

    //Constructor

    public ChangePositionDTO() {
        setDefaultSortField("goodsCode");
    }

    public ChangePositionDTO(String id, String stockId, String stockCode, String stockName, String customerId, String customerCode, String customerName, String goodsId, String goodsCode, String goodsName, String implementDate, String implementerId, String implementerName, String cellCodeOld, String cellCodeNew, String barcode, String fromSerial, String toSerial, String note, String quanlity) {
        this.id = id;
        this.stockId = stockId;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.customerId = customerId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.implementDate = implementDate;
        this.implementerId = implementerId;
        this.implementerName = implementerName;
        this.cellCodeOld = cellCodeOld;
        this.cellCodeNew = cellCodeNew;
        this.barcode = barcode;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.note = note;
        this.quantity = quanlity;
    }
    //Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockName() {
        return stockName;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setImplementDate(String implementDate) {
        this.implementDate = implementDate;
    }

    public String getImplementDate() {
        return implementDate;
    }

    public void setImplementerId(String implementerId) {
        this.implementerId = implementerId;
    }

    public String getImplementerId() {
        return implementerId;
    }

    public void setImplementerName(String implementerName) {
        this.implementerName = implementerName;
    }

    public String getImplementerName() {
        return implementerName;
    }

    public void setCellCodeOld(String cellCodeOld) {
        this.cellCodeOld = cellCodeOld;
    }

    public String getCellCodeOld() {
        return cellCodeOld;
    }

    public void setCellCodeNew(String cellCodeNew) {
        this.cellCodeNew = cellCodeNew;
    }

    public String getCellCodeNew() {
        return cellCodeNew;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
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

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    @Override
    public ChangePosition toModel() {
        try {
            ChangePosition model = new ChangePosition(
                    !StringUtils.validString(id) ? null
                            : Long.valueOf(id),
                    !StringUtils.validString(stockId) ? null
                            : Long.valueOf(stockId),
                    stockCode,
                    stockName,
                    !StringUtils.validString(customerId) ? null
                            : Long.valueOf(customerId),
                    customerCode,
                    customerName,
                    !StringUtils.validString(goodsId) ? null
                            : Long.valueOf(goodsId),
                    goodsCode,
                    goodsName,
                    !StringUtils.validString(implementDate) ? null
                            : DateTimeUtils.convertStringToDateTime(implementDate),
                    implementerId,
                    implementerName,
                    cellCodeOld,
                    cellCodeNew,
                    barcode,
                    fromSerial,
                    toSerial,
                    note,
                    quantity);
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id) ? null : Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getGoodsCode().toString();
    }

    @Override
    public long getChangedTime() {
        return ChangePositionDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        ChangePositionDTO.changedTime = changedTime;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIsSerial() {
        return isSerial;
    }

    public void setIsSerial(String isSerial) {
        this.isSerial = isSerial;
    }

    public String getIsSerialStrip() {
        return isSerialStrip;
    }

    public void setIsSerialStrip(String isSerialStrip) {
        this.isSerialStrip = isSerialStrip;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getErrorInfor() {
        return errorInfor;
    }

    public void setErrorInfor(String errorInfor) {
        this.errorInfor = errorInfor;
    }
    
}
