/**
 * @(#)HistoryChangeGoodsForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.logistic.wms.model.HistoryChangeGoods;
import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author duyot
 * @version 1.0
 * @since 2/3/2016 11:09 AM
 */
@XmlRootElement(name = "HistoryChangeGoods")
public class HistoryChangeGoodsDTO extends BaseFWDTO<HistoryChangeGoods> {

    //Fields
    private String id;
    private String custId;
    private String custCode;
    private String custName;
    private String stockId;
    private String stockCode;
    private String stockName;
    private String orderCode;
    private String orderUserName;
    private String expectedDate;
    private String completeDate;
    private String notes;
    private String changeType;
    private String amount;
    private String oldGoodsId;
    private String oldGoodsCode;
    private String oldGoodsName;
    private String newGoodsId;
    private String newGoodsCode;
    private String newGoodsName;
    private String oldFromSerial;
    private String oldToSerial;
    private String newFromSerial;
    private String newToSerial;
    private String oldGoodsState;
    private String newGoodsState;
    private String description;
    private String cellCode;
    private String oldGoodsUnitType;
    private String newGoodsUnitType;

    private static long changedTime = 0;

    //Constructor
    public HistoryChangeGoodsDTO() {
        setDefaultSortField("custId");
    }

    public HistoryChangeGoodsDTO(String id, String custId, String custCode, String custName, String stockId, String stockCode,
            String stockName, String orderCode, String orderUserName, String expectedDate, String completeDate, String notes,
            String changeType, String amount, String oldGoodsId, String oldGoodsCode, String oldGoodsName, String newGoodsId,
            String newGoodsCode, String newGoodsName, String oldFromSerial, String oldToSerial, String newFromSerial,
            String newToSerial, String oldState, String newState, String description,
            String cellCode, String oldGoodsUnitType, String newGoodsUnitType) {
        this.id = id;
        this.custId = custId;
        this.custCode = custCode;
        this.custName = custName;
        this.stockId = stockId;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.orderCode = orderCode;
        this.orderUserName = orderUserName;
        this.expectedDate = expectedDate;
        this.completeDate = completeDate;
        this.notes = notes;
        this.changeType = changeType;
        this.amount = amount;
        this.oldGoodsId = oldGoodsId;
        this.oldGoodsCode = oldGoodsCode;
        this.oldGoodsName = oldGoodsName;
        this.newGoodsId = newGoodsId;
        this.newGoodsCode = newGoodsCode;
        this.newGoodsName = newGoodsName;
        this.oldFromSerial = oldFromSerial;
        this.oldToSerial = oldToSerial;
        this.newFromSerial = newFromSerial;
        this.newToSerial = newToSerial;
        this.oldGoodsState = oldState;
        this.newGoodsState = newState;
        this.description = description;
        this.cellCode = cellCode;
        this.oldGoodsUnitType = oldGoodsUnitType;
        this.newGoodsUnitType = newGoodsUnitType;
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

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
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

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setOldGoodsId(String oldGoodsId) {
        this.oldGoodsId = oldGoodsId;
    }

    public String getOldGoodsId() {
        return oldGoodsId;
    }

    public void setOldGoodsCode(String oldGoodsCode) {
        this.oldGoodsCode = oldGoodsCode;
    }

    public String getOldGoodsCode() {
        return oldGoodsCode;
    }

    public void setOldGoodsName(String oldGoodsName) {
        this.oldGoodsName = oldGoodsName;
    }

    public String getOldGoodsName() {
        return oldGoodsName;
    }

    public void setNewGoodsId(String newGoodsId) {
        this.newGoodsId = newGoodsId;
    }

    public String getNewGoodsId() {
        return newGoodsId;
    }

    public void setNewGoodsCode(String newGoodsCode) {
        this.newGoodsCode = newGoodsCode;
    }

    public String getNewGoodsCode() {
        return newGoodsCode;
    }

    public void setNewGoodsName(String newGoodsName) {
        this.newGoodsName = newGoodsName;
    }

    public String getNewGoodsName() {
        return newGoodsName;
    }

    public void setOldFromSerial(String oldFromSerial) {
        this.oldFromSerial = oldFromSerial;
    }

    public String getOldFromSerial() {
        return oldFromSerial;
    }

    public void setOldToSerial(String oldToSerial) {
        this.oldToSerial = oldToSerial;
    }

    public String getOldToSerial() {
        return oldToSerial;
    }

    public void setNewFromSerial(String newFromSerial) {
        this.newFromSerial = newFromSerial;
    }

    public String getNewFromSerial() {
        return newFromSerial;
    }

    public void setNewToSerial(String newToSerial) {
        this.newToSerial = newToSerial;
    }

    public String getNewToSerial() {
        return newToSerial;
    }

    public String getOldGoodsState() {
        return oldGoodsState;
    }

    public void setOldGoodsState(String oldGoodsState) {
        this.oldGoodsState = oldGoodsState;
    }

    public String getNewGoodsState() {
        return newGoodsState;
    }

    public void setNewGoodsState(String newGoodsState) {
        this.newGoodsState = newGoodsState;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public String getCellCode() {
        return cellCode;
    }

    public String getOldGoodsUnitType() {
        return oldGoodsUnitType;
    }

    public void setOldGoodsUnitType(String oldGoodsUnitType) {
        this.oldGoodsUnitType = oldGoodsUnitType;
    }

    public String getNewGoodsUnitType() {
        return newGoodsUnitType;
    }

    public void setNewGoodsUnitType(String newGoodsUnitType) {
        this.newGoodsUnitType = newGoodsUnitType;
    }

    @Override
    public HistoryChangeGoods toModel() {
        try {
            HistoryChangeGoods model = new HistoryChangeGoods(
                    !StringUtils.validString(id) ? null
                            : Long.valueOf(id),
                    !StringUtils.validString(custId) ? null
                            : Long.valueOf(custId),
                    custCode,
                    custName,
                    !StringUtils.validString(stockId) ? null
                            : Long.valueOf(stockId),
                    stockCode,
                    stockName,
                    orderCode,
                    orderUserName,
                    !StringUtils.validString(expectedDate) ? null
                            : DateTimeUtils.convertStringToDate(expectedDate),
                    !StringUtils.validString(completeDate) ? null
                            : DateTimeUtils.convertStringToDate(completeDate),
                    notes,
                    changeType,
                    !StringUtils.validString(amount) ? null
                            : Double.valueOf(amount),
                    !StringUtils.validString(oldGoodsId) ? null
                            : Long.valueOf(oldGoodsId),
                    oldGoodsCode,
                    oldGoodsName,
                    !StringUtils.validString(newGoodsId) ? null
                            : Long.valueOf(newGoodsId),
                    newGoodsCode,
                    newGoodsName,
                    oldFromSerial,
                    oldToSerial,
                    newFromSerial,
                    newToSerial,
                    oldGoodsState,
                    newGoodsState,
                    description,
                    cellCode, oldGoodsUnitType, newGoodsUnitType);
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
        return HistoryChangeGoodsDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        HistoryChangeGoodsDTO.changedTime = changedTime;
    }
}
