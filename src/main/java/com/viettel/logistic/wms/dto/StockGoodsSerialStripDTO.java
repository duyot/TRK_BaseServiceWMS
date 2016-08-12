/**
 * @(#)StockGoodsSerialStripForm.java , Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.logistic.wms.model.StockGoodsSerialStrip;
import com.viettel.vfw5.base.utils.StringUtils;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 5/21/2015 1:35 PM
 */
@XmlRootElement(name = "StockGoodsSerialStrip")
public class StockGoodsSerialStripDTO extends BaseFWDTO<StockGoodsSerialStrip> {

    //Fields
    private String id;
    private String custId;
    private String ownerId;
    private String ownerType;
    private String goodsId;
    private String goodsState;
    private String status;
    private String fromSerial;
    private String toSerial;
    private String quantity;
    private String saleType;
    private String price;
    private String channelTypeId;
    private String barcode;
    private String changeUser;
    private String changeDate;
    private String importDate;
    private String saleDate;
    private String bincode;
    private String cellCode;
    private String addInfor;
    private static long changedTime = 0;
    //ThienNG1 18/06/2015
    private String goodsType;
    private String goodsGroup;
    private String goodsCode;
    private String goodsName;
    private String custName;
    private String oldStatus;
    private String partnerId;
    private String staffId;
    //
    private String importStockTransId;
    private String orderId;

    //end
    //Constructor
    public StockGoodsSerialStripDTO() {
        setDefaultSortField("custId");
    }

    public StockGoodsSerialStripDTO(String id, String custId, String ownerId, String ownerType,
            String goodsId, String goodsState, String status, String fromSerial, String toSerial,
            String quantity, String saleType, String price, String channelTypeId, String barcode, String changeUser,
            String changeDate, String importDate, String saleDate, String bincode, String cellCode, String addInfor, String partnerId,
            String importStockTransId, String orderId
    ) {
        this.id = id;
        this.custId = custId;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.goodsId = goodsId;
        this.goodsState = goodsState;
        this.status = status;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.quantity = quantity;
        this.saleType = saleType;
        this.price = price;
        this.channelTypeId = channelTypeId;
        this.barcode = barcode;
        this.changeUser = changeUser;
        this.changeDate = changeDate;
        this.importDate = importDate;
        this.saleDate = saleDate;
        this.bincode = bincode;
        this.cellCode = cellCode;
        this.addInfor = addInfor;
        //
        this.partnerId = partnerId;
        //
        this.importStockTransId = importStockTransId;
        this.orderId = orderId;
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

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
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

    public void setBincode(String bincode) {
        this.bincode = bincode;
    }

    public String getBincode() {
        return bincode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public String getCellCode() {
        return cellCode;
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
        this.goodsName = goodsName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
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
    public StockGoodsSerialStrip toModel() {
        try {
            StockGoodsSerialStrip model = new StockGoodsSerialStrip(
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
                    fromSerial,
                    toSerial,
                    !StringUtils.validString(quantity) ? null
                            : Long.valueOf(quantity),
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
                    bincode,
                    cellCode,
                    addInfor,
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
        return getCustId().toString();
    }

    @Override
    public long getChangedTime() {
        return StockGoodsSerialStripDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        StockGoodsSerialStripDTO.changedTime = changedTime;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

}
