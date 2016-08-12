/**
 * @(#)StockGoodsTotalForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.logistic.wms.model.StockGoodsTotal;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 11:35 AM
 */
@XmlRootElement(name = "StockGoodsTotal")
public class StockGoodsTotalDTO extends BaseFWDTO<StockGoodsTotal> {

    //Fields

    private String id;
    private String custId;
    private String ownerId;
    private String ownerType;
    private String goodsId;
    private String goodsState;
    private String goodsCode;
    private String amount;
    private String amountIssue;
    private String changeDate;
    private static long changedTime = 0;

    //Constructor

    public StockGoodsTotalDTO() {
        setDefaultSortField("cusId");
    }

    public StockGoodsTotalDTO(String id, String custId, String ownerId, String ownerType, String goodsId, String goodsState, String amount, String amountIssue, String changeDate) {
        this.id = id;
        this.custId = custId;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.goodsId = goodsId;
        this.goodsState = goodsState;
        this.amount = amount;
        this.amountIssue = amountIssue;
        this.changeDate = changeDate;
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

    @Override
    public StockGoodsTotal toModel() {
        try {
            StockGoodsTotal model = new StockGoodsTotal(
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
                    !StringUtils.validString(amount) ? null
                            : Double.parseDouble(amount),
                    !StringUtils.validString(amountIssue) ? null
                            : Double.parseDouble(amountIssue),
                    !StringUtils.validString(changeDate) ? null
                            : DateTimeUtils.convertStringToDate(changeDate));
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
        return StockGoodsTotalDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        StockGoodsTotalDTO.changedTime = changedTime;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }
    
}
