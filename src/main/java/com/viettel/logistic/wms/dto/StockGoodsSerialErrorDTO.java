/**
 * @(#)StockGoodsSerialErrorForm.java , Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.logistic.wms.model.StockGoodsSerialError;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 8:41 AM
 */
@XmlRootElement(name = "StockGoodsSerialError")
public class StockGoodsSerialErrorDTO extends BaseFWDTO<StockGoodsSerialError> {

    //Fields

    private String id;
    private String stockTransId;
    private String custId;
    private String ownerId;
    private String ownerType;
    private String goodsId;
    private String goodsState;
    private String status;
    private String fromSerial;
    private String toSerial;
    private String saleType;
    private String price;
    private String channelTypeId;
    private String barcode;
    private String changeUser;
    private String changeDate;
    private String importDate;
    private String saleDate;
    private String quantity;
    private String bincode;
    private String addInfor;
    private String createDatetime;
    private static long changedTime = 0;

    //Constructor

    public StockGoodsSerialErrorDTO() {
        setDefaultSortField("stockTransId");
    }

    public StockGoodsSerialErrorDTO(String id, String stockTransId, String custId, String ownerId, String ownerType, String goodsId, String goodsState, String status, String fromSerial, String toSerial, String saleType, String price, String channelTypeId, String barcode, String changeUser, String changeDate, String importDate, String saleDate, String quantity, String bincode, String addInfor, String createDatetime) {
        this.id = id;
        this.stockTransId = stockTransId;
        this.custId = custId;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.goodsId = goodsId;
        this.goodsState = goodsState;
        this.status = status;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.saleType = saleType;
        this.price = price;
        this.channelTypeId = channelTypeId;
        this.barcode = barcode;
        this.changeUser = changeUser;
        this.changeDate = changeDate;
        this.importDate = importDate;
        this.saleDate = saleDate;
        this.quantity = quantity;
        this.bincode = bincode;
        this.addInfor = addInfor;
        this.createDatetime = createDatetime;
    }
	//Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStockTransId(String stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getStockTransId() {
        return stockTransId;
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

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    @Override
    public StockGoodsSerialError toModel() {
        try {
            StockGoodsSerialError model = new StockGoodsSerialError(
                    !StringUtils.validString(id) ? null
                            : Long.valueOf(id),
                    !StringUtils.validString(stockTransId) ? null
                            : Long.valueOf(stockTransId),
                    !StringUtils.validString(custId) ? null
                            : Long.valueOf(custId),
                    !StringUtils.validString(ownerId) ? null
                            : Long.valueOf(ownerId),
                    ownerType,
                    !StringUtils.validString(goodsId) ? null
                            : Long.valueOf(goodsId),
                    goodsState,
                    status,
                    fromSerial,
                    toSerial,
                    saleType,
                    !StringUtils.validString(price) ? null
                            : Double.parseDouble(price),
                    channelTypeId,
                    barcode,
                    changeUser,
                    !StringUtils.validString(changeDate) ? null
                            : DateTimeUtils.convertStringToDate(changeDate),
                    !StringUtils.validString(importDate) ? null
                            : DateTimeUtils.convertStringToDate(importDate),
                    !StringUtils.validString(saleDate) ? null
                            : DateTimeUtils.convertStringToDate(saleDate),
                    !StringUtils.validString(quantity) ? null
                            : Long.valueOf(quantity),
                    bincode,
                    addInfor,
                    !StringUtils.validString(createDatetime) ? null
                            : DateTimeUtils.convertStringToDate(createDatetime));
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
        return getStockTransId().toString();
    }

    @Override
    public long getChangedTime() {
        return StockGoodsSerialErrorDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        StockGoodsSerialErrorDTO.changedTime = changedTime;
    }
}
