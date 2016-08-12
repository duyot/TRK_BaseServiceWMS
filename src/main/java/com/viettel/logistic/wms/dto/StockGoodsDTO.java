/**
 * @(#)StockGoodsForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import com.viettel.logistic.wms.model.StockGoods;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 07-May-15 2:37 PM
 */
@XmlRootElement(name = "StockGoods")
public class StockGoodsDTO extends BaseFWDTO<StockGoods> {

    //Fields
    private String id;
    private String custId;
    private String ownerId;
    private String ownerType;
    private String goodsId;
    private String goodsState;
    private String barcode;
    private String amount;
    private String amountIssue;
    private String changeDate;
    private String importDate;
    private String bincode;
    private String addInfor;
    private String cellCode;
    private static long changedTime = 0;
    //
    private String partnerId;
    //
    private String status;
    private String orderId;
    private String oldStatus;
    private String importStockTransId;

    //Constructor
    public StockGoodsDTO() {
        setDefaultSortField("custId");
    }

    public StockGoodsDTO(String id, String custId, String ownerId, String ownerType,
            String goodsId, String goodsState, String barcode, String amount, String amountIssue,
            String changeDate, String importDate, String bincode, String addInfor, String cellCode, String partnerId,
            String status, String orderId, String importStockTransId
    ) {
        this.id = id;
        this.custId = custId;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.goodsId = goodsId;
        this.goodsState = goodsState;
        this.barcode = barcode;
        this.amount = amount;
        this.amountIssue = amountIssue;
        this.changeDate = changeDate;
        this.importDate = importDate;
        this.bincode = bincode;
        this.addInfor = addInfor;
        this.cellCode = cellCode;
        this.partnerId = partnerId;
        this.status = status;
        this.orderId = orderId;
        this.importStockTransId = importStockTransId;
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

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmountIssue(String amountIssue) {
        this.amountIssue = amountIssue;
    }

    public String getAmountIssue() {
        return amountIssue;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getImportStockTransId() {
        return importStockTransId;
    }

    public void setImportStockTransId(String importStockTransId) {
        this.importStockTransId = importStockTransId;
    }

    @Override
    public StockGoods toModel() {
        try {
            StockGoods model = new StockGoods(
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
                    barcode,
                    !StringUtils.validString(amount) ? null
                            : Double.parseDouble(amount),
                    !StringUtils.validString(amountIssue) ? null
                            : Double.parseDouble(amountIssue),
                    !StringUtils.validString(changeDate) ? null
                            : DateTimeUtils.convertStringToDate(changeDate),
                    !StringUtils.validString(importDate) ? null
                            : DateTimeUtils.convertStringToDate(importDate),
                    bincode,
                    addInfor,
                    cellCode,
                    !StringUtils.validString(partnerId) ? null
                            : Long.valueOf(partnerId),
                    status,
                    !StringUtils.validString(orderId) ? null
                            : Long.valueOf(orderId),
                    !StringUtils.validString(importStockTransId) ? null
                            : Long.valueOf(importStockTransId)
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
        return StockGoodsDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        StockGoodsDTO.changedTime = changedTime;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

}
