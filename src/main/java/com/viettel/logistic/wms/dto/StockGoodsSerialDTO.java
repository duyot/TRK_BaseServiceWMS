/**
 * @(#)StockGoodsSerialForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.logistic.wms.model.StockGoodsSerial;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 07-May-15 2:51 PM
 */
@XmlRootElement(name = "StockGoodsSerial")
public class StockGoodsSerialDTO extends BaseFWDTO<StockGoodsSerial> {

    //Fields
    private String id;
    private String custId;
    private String ownerId;
    private String ownerType;
    private String goodsId;
    private String goodsState;
    private String status;
    private String serial;
    private String saleType;
    private String changeUser;
    private String price;
    private String channelTypeId;
    private String barcode;
    private String changeDate;
    private String importDate;
    private String saleDate;
    private String bincode;
    private String addInfor;
    private String cellCode;
    private static long changedTime = 0;
    //
    private String partnerId;
    private String importStockTransId;
    private String orderId;
    private String oldStatus;

    public StockGoodsSerialDTO() {
        setDefaultSortField("serial");
    }

    public StockGoodsSerialDTO(String id, String custId, String ownerId, String ownerType,
            String goodsId, String goodsState, String status, String serial, String saleType,
            String changeUser, String price, String channelTypeId, String barcode, String changeDate,
            String importDate, String saleDate, String bincode, String addInfor, String cellCode,
            String partnerId, String importStockTransId, String orderId
    ) {
        this.id = id;
        this.custId = custId;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.goodsId = goodsId;
        this.goodsState = goodsState;
        this.status = status;
        this.serial = serial;
        this.saleType = saleType;
        this.changeUser = changeUser;
        this.price = price;
        this.channelTypeId = channelTypeId;
        this.barcode = barcode;
        this.changeDate = changeDate;
        this.importDate = importDate;
        this.saleDate = saleDate;
        this.bincode = bincode;
        this.addInfor = addInfor;
        this.cellCode = cellCode;
        //
        this.partnerId = partnerId;
        this.importStockTransId = importStockTransId;
        this.orderId = orderId;
        //
    }

    //QuyenDM 20160412 - Chuyen doi tu StockGoodsSerialDTO sang StockGoodsSerialInforDTO
    public StockGoodsSerialInforDTO convert2StockGoodsSerialInforDTO() {
        return new StockGoodsSerialInforDTO(custId, ownerId, ownerType, cellCode,
                goodsId, goodsState, status, serial, barcode);
    }
    //Getters and setters

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

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getSerial() {
        return serial;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setChangeUser(String changeUser) {
        this.changeUser = changeUser;
    }

    public String getChangeUser() {
        return changeUser;
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

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public String getCellCode() {
        return cellCode;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getImportStockTransId() {
        return importStockTransId;
    }

    public void setImportStockTransId(String importStockTransId) {
        this.importStockTransId = importStockTransId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    @Override
    public StockGoodsSerial toModel() {
        try {
            StockGoodsSerial model = new StockGoodsSerial(
                    !StringUtils.validString(id) ? null
                            : Long.valueOf(id),
                    !StringUtils.validString(custId) ? null
                            : Long.valueOf(custId),
                    !StringUtils.validString(ownerId) ? null
                            : Long.valueOf(ownerId),
                    ownerType,
                    !StringUtils.validString(goodsId) ? null
                            : Long.valueOf(goodsId),
                    goodsState,
                    status,
                    serial,
                    saleType,
                    changeUser,
                    !StringUtils.validString(price) ? null
                            : Double.parseDouble(price),
                    !StringUtils.validString(channelTypeId) ? null
                            : Long.valueOf(channelTypeId),
                    barcode,
                    !StringUtils.validString(changeDate) ? null
                            : DateTimeUtils.convertStringToDate(changeDate),
                    !StringUtils.validString(importDate) ? null
                            : DateTimeUtils.convertStringToDate(importDate),
                    !StringUtils.validString(saleDate) ? null
                            : DateTimeUtils.convertStringToDate(saleDate),
                    bincode,
                    addInfor,
                    cellCode,
                    !StringUtils.validString(partnerId) ? null
                            : Long.valueOf(partnerId),
                    !StringUtils.validString(importStockTransId) ? null
                            : Long.valueOf(importStockTransId),
                    !StringUtils.validString(orderId) ? null
                            : Long.valueOf(orderId)
            );
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
        return getSerial().toString();
    }

    @Override
    public long getChangedTime() {
        return StockGoodsSerialDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        StockGoodsSerialDTO.changedTime = changedTime;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }
    
}
