/**
 * @(#)HistoryChangeGoodsBO.java 2/3/2016 11:09 AM, Copyright 2011 Viettel
 * Telecom. All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.logistic.wms.dto.HistoryChangeGoodsDTO;
import com.viettel.vfw5.base.model.BaseFWModel;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author duyot
 * @version 1.0
 * @since 2/3/2016 11:09 AM
 */
@Entity
@Table(name = "HISTORY_CHANGE_GOODS")
public class HistoryChangeGoods extends BaseFWModel {

    //Fields
    private Long id;
    private Long custId;
    private String custCode;
    private String custName;
    private Long stockId;
    private String stockCode;
    private String stockName;
    private String orderCode;
    private String orderUserName;
    private Date expectedDate;
    private Date completeDate;
    private String notes;
    private String changeType;
    private Double amount;
    private Long oldGoodsId;
    private String oldGoodsCode;
    private String oldGoodsName;
    private Long newGoodsId;
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

    //Constructors
    public HistoryChangeGoods() {
        setColId("id");
        setColName("custId");
        setUniqueColumn(new String[]{"custId"});
    }

    public HistoryChangeGoods(Long id) {
        this.id = id;
    }

    public HistoryChangeGoods(Long id, Long custId, String custCode, String custName, Long stockId, String stockCode,
            String stockName, String orderCode, String orderUserName, Date expectedDate, Date completeDate, String notes,
            String changeType, Double amount, Long oldGoodsId, String oldGoodsCode, String oldGoodsName,
            Long newGoodsId, String newGoodsCode, String newGoodsName, String oldFromSerial, String oldToSerial,
            String newFromSerial, String newToSerial, String oldGoodsState, String newGoodsState, String description,
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
        this.oldGoodsState = oldGoodsState;
        this.newGoodsState = newGoodsState;
        this.description = description;
        this.cellCode = cellCode;
        this.oldGoodsUnitType = oldGoodsUnitType;
        this.newGoodsUnitType = newGoodsUnitType;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "HISTORY_CHANGE_GOODS_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "CUST_ID")
    public Long getCustId() {
        return this.custId;
    }

    public void setCustId(final Long custId) {
        this.custId = custId;
    }

    @Column(name = "CUST_CODE")
    public String getCustCode() {
        return this.custCode;
    }

    public void setCustCode(final String custCode) {
        this.custCode = custCode;
    }

    @Column(name = "CUST_NAME")
    public String getCustName() {
        return this.custName;
    }

    public void setCustName(final String custName) {
        this.custName = custName;
    }

    @Column(name = "STOCK_ID")
    public Long getStockId() {
        return this.stockId;
    }

    public void setStockId(final Long stockId) {
        this.stockId = stockId;
    }

    @Column(name = "STOCK_CODE")
    public String getStockCode() {
        return this.stockCode;
    }

    public void setStockCode(final String stockCode) {
        this.stockCode = stockCode;
    }

    @Column(name = "STOCK_NAME")
    public String getStockName() {
        return this.stockName;
    }

    public void setStockName(final String stockName) {
        this.stockName = stockName;
    }

    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return this.orderCode;
    }

    public void setOrderCode(final String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "ORDER_USER_NAME")
    public String getOrderUserName() {
        return this.orderUserName;
    }

    public void setOrderUserName(final String orderUserName) {
        this.orderUserName = orderUserName;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "EXPECTED_DATE")
    public Date getExpectedDate() {
        return this.expectedDate;
    }

    public void setExpectedDate(final Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "COMPLETE_DATE")
    public Date getCompleteDate() {
        return this.completeDate;
    }

    public void setCompleteDate(final Date completeDate) {
        this.completeDate = completeDate;
    }

    @Column(name = "NOTES")
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    @Column(name = "CHANGE_TYPE")
    public String getChangeType() {
        return this.changeType;
    }

    public void setChangeType(final String changeType) {
        this.changeType = changeType;
    }

    @Column(name = "AMOUNT")
    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    @Column(name = "OLD_GOODS_ID")
    public Long getOldGoodsId() {
        return this.oldGoodsId;
    }

    public void setOldGoodsId(final Long oldGoodsId) {
        this.oldGoodsId = oldGoodsId;
    }

    @Column(name = "OLD_GOODS_CODE")
    public String getOldGoodsCode() {
        return this.oldGoodsCode;
    }

    public void setOldGoodsCode(final String oldGoodsCode) {
        this.oldGoodsCode = oldGoodsCode;
    }

    @Column(name = "OLD_GOODS_NAME")
    public String getOldGoodsName() {
        return this.oldGoodsName;
    }

    public void setOldGoodsName(final String oldGoodsName) {
        this.oldGoodsName = oldGoodsName;
    }

    @Column(name = "NEW_GOODS_ID")
    public Long getNewGoodsId() {
        return this.newGoodsId;
    }

    public void setNewGoodsId(final Long newGoodsId) {
        this.newGoodsId = newGoodsId;
    }

    @Column(name = "NEW_GOODS_CODE")
    public String getNewGoodsCode() {
        return this.newGoodsCode;
    }

    public void setNewGoodsCode(final String newGoodsCode) {
        this.newGoodsCode = newGoodsCode;
    }

    @Column(name = "NEW_GOODS_NAME")
    public String getNewGoodsName() {
        return this.newGoodsName;
    }

    public void setNewGoodsName(final String newGoodsName) {
        this.newGoodsName = newGoodsName;
    }

    @Column(name = "OLD_FROM_SERIAL")
    public String getOldFromSerial() {
        return this.oldFromSerial;
    }

    public void setOldFromSerial(final String oldFromSerial) {
        this.oldFromSerial = oldFromSerial;
    }

    @Column(name = "OLD_TO_SERIAL")
    public String getOldToSerial() {
        return this.oldToSerial;
    }

    public void setOldToSerial(final String oldToSerial) {
        this.oldToSerial = oldToSerial;
    }

    @Column(name = "NEW_FROM_SERIAL")
    public String getNewFromSerial() {
        return this.newFromSerial;
    }

    public void setNewFromSerial(final String newFromSerial) {
        this.newFromSerial = newFromSerial;
    }

    @Column(name = "NEW_TO_SERIAL")
    public String getNewToSerial() {
        return this.newToSerial;
    }

    public void setNewToSerial(final String newToSerial) {
        this.newToSerial = newToSerial;
    }

    @Column(name = "OLD_GOODS_STATE")
    public String getOldGoodsState() {
        return this.oldGoodsState;
    }

    public void setOldGoodsState(final String oldGoodsState) {
        this.oldGoodsState = oldGoodsState;
    }

    @Column(name = "NEW_GOODS_STATE")
    public String getNewGoodsState() {
        return this.newGoodsState;
    }

    public void setNewGoodsState(final String newGoodsState) {
        this.newGoodsState = newGoodsState;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Column(name = "CELL_CODE")
    public String getCellCode() {
        return this.cellCode;
    }

    public void setCellCode(final String cellCode) {
        this.cellCode = cellCode;
    }

    @Column(name = "OLD_GOODS_UNIT_TYPE")
    public String getOldGoodsUnitType() {
        return oldGoodsUnitType;
    }

    public void setOldGoodsUnitType(String oldGoodsUnitType) {
        this.oldGoodsUnitType = oldGoodsUnitType;
    }

    @Column(name = "NEW_GOODS_UNIT_TYPE")
    public String getNewGoodsUnitType() {
        return newGoodsUnitType;
    }

    public void setNewGoodsUnitType(String newGoodsUnitType) {
        this.newGoodsUnitType = newGoodsUnitType;
    }

    @Override
    public HistoryChangeGoodsDTO toDTO() {
        HistoryChangeGoodsDTO dto = new HistoryChangeGoodsDTO(
                id == null ? null : id.toString(), custId == null ? null : custId.toString(), custCode, custName, stockId == null ? null : stockId.toString(), stockCode, stockName, orderCode, orderUserName, expectedDate == null ? null : DateTimeUtils.convertDateToString(expectedDate, ParamUtils.ddMMyyyy), completeDate == null ? null : DateTimeUtils.convertDateToString(completeDate, ParamUtils.ddMMyyyy), notes, changeType, amount == null ? null : amount.toString(), oldGoodsId == null ? null : oldGoodsId.toString(), oldGoodsCode, oldGoodsName, newGoodsId == null ? null : newGoodsId.toString(), newGoodsCode, newGoodsName, oldFromSerial, oldToSerial, newFromSerial, newToSerial, oldGoodsState, newGoodsState, description, cellCode, oldGoodsUnitType, newGoodsUnitType
        );
        return dto;
    }
}
