/**
 * @(#)ChangePositionBO.java 6/9/2015 9:40 AM, Copyright 2011 Viettel Telecom.
 * All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author HungKV
 * @version 1.0
 * @since 6/9/2015 9:40 AM
 */
@Entity
@Table(name = "CHANGE_POSITION")
public class ChangePosition extends BaseFWModel {

    //Fields
    private Long id;
    private Long stockId;
    private String stockCode;
    private String stockName;
    private Long customerId;
    private String customerCode;
    private String customerName;
    private Long goodsId;
    private String goodsCode;
    private String goodsName;
    private Date implementDate;
    private String implementerId;
    private String implementerName;
    private String cellCodeOld;
    private String cellCodeNew;
    private String barcode;
    private String fromSerial;
    private String toSerial;
    private String note;
    private String quantity;

    //Constructors
    public ChangePosition() {
        setColId("id");
        setColName("goodsCode");
        setUniqueColumn(new String[]{"id"});
    }

    public ChangePosition(Long id) {
        this.id = id;
    }

    public ChangePosition(Long id, Long stockId, String stockCode, String stockName, Long customerId, String customerCode, String customerName, Long goodsId, String goodsCode, String goodsName, Date implementDate, String implementerId, String implementerName, String cellCodeOld, String cellCodeNew, String barcode, String fromSerial, String toSerial, String note, String quanlity) {
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

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "CHANGE_POSITION_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    @Column(name = "CUSTOMER_ID")
    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(final Long customerId) {
        this.customerId = customerId;
    }

    @Column(name = "CUSTOMER_CODE")
    public String getCustomerCode() {
        return this.customerCode;
    }

    public void setCustomerCode(final String customerCode) {
        this.customerCode = customerCode;
    }

    @Column(name = "CUSTOMER_NAME")
    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    @Column(name = "GOODS_ID")
    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(final Long goodsId) {
        this.goodsId = goodsId;
    }

    @Column(name = "GOODS_CODE")
    public String getGoodsCode() {
        return this.goodsCode;
    }

    public void setGoodsCode(final String goodsCode) {
        this.goodsCode = goodsCode;
    }

    @Column(name = "GOODS_NAME")
    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(final String goodsName) {
        this.goodsName = goodsName;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "IMPLEMENT_DATE")
    public Date getImplementDate() {
        return this.implementDate;
    }

    public void setImplementDate(final Date implementDate) {
        this.implementDate = implementDate;
    }

    @Column(name = "IMPLEMENTER_ID")
    public String getImplementerId() {
        return this.implementerId;
    }

    public void setImplementerId(final String implementerId) {
        this.implementerId = implementerId;
    }

    @Column(name = "IMPLEMENTER_NAME")
    public String getImplementerName() {
        return this.implementerName;
    }

    public void setImplementerName(final String implementerName) {
        this.implementerName = implementerName;
    }

    @Column(name = "CELL_CODE_OLD")
    public String getCellCodeOld() {
        return this.cellCodeOld;
    }

    public void setCellCodeOld(final String cellCodeOld) {
        this.cellCodeOld = cellCodeOld;
    }

    @Column(name = "CELL_CODE_NEW")
    public String getCellCodeNew() {
        return this.cellCodeNew;
    }

    public void setCellCodeNew(final String cellCodeNew) {
        this.cellCodeNew = cellCodeNew;
    }

    @Column(name = "BARCODE")
    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(final String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "FROM_SERIAL")
    public String getFromSerial() {
        return this.fromSerial;
    }

    public void setFromSerial(final String fromSerial) {
        this.fromSerial = fromSerial;
    }

    @Column(name = "TO_SERIAL")
    public String getToSerial() {
        return this.toSerial;
    }

    public void setToSerial(final String toSerial) {
        this.toSerial = toSerial;
    }

    @Column(name = "NOTE")
    public String getNote() {
        return this.note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Column(name = "QUANTITY")
    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final String quantity) {
        this.quantity = quantity;
    }

    @Override
    public ChangePositionDTO toDTO() {
        ChangePositionDTO dto = new ChangePositionDTO(
                id == null ? null : id.toString(), stockId == null ? null : stockId.toString(), stockCode, stockName, customerId == null ? null : customerId.toString(), customerCode, customerName, goodsId == null ? null : goodsId.toString(), goodsCode, goodsName, implementDate == null ? null : DateTimeUtils.convertDateToString(implementDate, ParamUtils.ddMMyyyyHHmmss), implementerId, implementerName == null ? null : implementerName.toString(), cellCodeOld, cellCodeNew, barcode, fromSerial, toSerial, note, quantity
        );
        return dto;
    }
}
