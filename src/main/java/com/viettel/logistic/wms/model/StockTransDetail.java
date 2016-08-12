/**
 * @(#)StockTransDetailBO.java 23-Apr-15 10:52 AM, Copyright 2011 Viettel
 * Telecom. All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 23-Apr-15 10:52 AM
 */
@Entity
@Table(name = "STOCK_TRANS_DETAIL")
public class StockTransDetail extends BaseFWModel {

    //Fields
    private Long stockTransDetailId;
    private Long stockTransId;
    private Date stockTransDate;
    private Long goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private String goodsUnitType;
    private String goodsUnitTypeName;
    private String goodsIsSerial;
    private String goodsIsSerialStrip;
    private Double amountOrder;
    private Double amountReal;
    private String bincode;
    private String barcode;
    private String cellCode;
    private String notes;
    private Date createDatetime;
    private String addInfor;
    //
    private String tmpStockTransDetailId;

    //Constructors
    public StockTransDetail() {
        setColId("stockTransDetailId");
        setColName("stockTransDate");
        setUniqueColumn(new String[]{"stockTransDetailId"});
    }

    public StockTransDetail(Long stockTransDetailId) {
        this.stockTransDetailId = stockTransDetailId;
    }

    public StockTransDetail(Long stockTransDetailId, Long stockTransId, Date stockTransDate, Long goodsId, String goodsCode, String goodsName, String goodsState, String goodsUnitType, String goodsUnitTypeName, String goodsIsSerial, String goodsIsSerialStrip, Double amountOrder, Double amountReal, String bincode, String barcode, String cellCode, String notes, Date createDatetime, String addInfor) {
        this.stockTransDetailId = stockTransDetailId;
        this.stockTransId = stockTransId;
        this.stockTransDate = stockTransDate;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsState = goodsState;
        this.goodsUnitType = goodsUnitType;
        this.goodsUnitTypeName = goodsUnitTypeName;
        this.goodsIsSerial = goodsIsSerial;
        this.goodsIsSerialStrip = goodsIsSerialStrip;
        this.amountOrder = amountOrder;
        this.amountReal = amountReal;
        this.bincode = bincode;
        this.barcode = barcode;
        this.cellCode = cellCode;
        this.notes = notes;
        this.createDatetime = createDatetime;
        this.addInfor = addInfor;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "STOCK_TRANS_DETAIL_SEQ")
            }
    )
    @Column(name = "STOCK_TRANS_DETAIL_ID", unique = true, nullable = false)
    public Long getStockTransDetailId() {
        return this.stockTransDetailId;
    }

    public void setStockTransDetailId(final Long stockTransDetailId) {
        this.stockTransDetailId = stockTransDetailId;
    }

    @Column(name = "STOCK_TRANS_ID", columnDefinition = "StockTrans")
    public Long getStockTransId() {
        return this.stockTransId;
    }

    public void setStockTransId(final Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "STOCK_TRANS_DATE")
    public Date getStockTransDate() {
        return this.stockTransDate;
    }

    public void setStockTransDate(final Date stockTransDate) {
        this.stockTransDate = stockTransDate;
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

    @Column(name = "GOODS_STATE")
    public String getGoodsState() {
        return this.goodsState;
    }

    public void setGoodsState(final String goodsState) {
        this.goodsState = goodsState;
    }

    @Column(name = "GOODS_UNIT_TYPE")
    public String getGoodsUnitType() {
        return this.goodsUnitType;
    }

    public void setGoodsUnitType(final String goodsUnitType) {
        this.goodsUnitType = goodsUnitType;
    }

    @Column(name = "GOODS_UNIT_TYPE_NAME")
    public String getGoodsUnitTypeName() {
        return this.goodsUnitTypeName;
    }

    public void setGoodsUnitTypeName(final String goodsUnitTypeName) {
        this.goodsUnitTypeName = goodsUnitTypeName;
    }

    @Column(name = "GOODS_IS_SERIAL")
    public String getGoodsIsSerial() {
        return this.goodsIsSerial;
    }

    public void setGoodsIsSerial(final String goodsIsSerial) {
        this.goodsIsSerial = goodsIsSerial;
    }

    @Column(name = "GOODS_IS_SERIAL_STRIP")
    public String getGoodsIsSerialStrip() {
        return this.goodsIsSerialStrip;
    }

    public void setGoodsIsSerialStrip(final String goodsIsSerialStrip) {
        this.goodsIsSerialStrip = goodsIsSerialStrip;
    }

    @Column(name = "AMOUNT_ORDER")
    public Double getAmountOrder() {
        return this.amountOrder;
    }

    public void setAmountOrder(final Double amountOrder) {
        this.amountOrder = amountOrder;
    }

    @Column(name = "AMOUNT_REAL")
    public Double getAmountReal() {
        return this.amountReal;
    }

    public void setAmountReal(final Double amountReal) {
        this.amountReal = amountReal;
    }

    @Column(name = "BINCODE")
    public String getBincode() {
        return this.bincode;
    }

    public void setBincode(final String bincode) {
        this.bincode = bincode;
    }

    @Column(name = "BARCODE")
    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(final String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "CELL_CODE")
    public String getCellCode() {
        return this.cellCode;
    }

    public void setCellCode(final String cellCode) {
        this.cellCode = cellCode;
    }

    //

    @Column(name = "NOTES")
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATETIME")
    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(final Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Column(name = "ADD_INFOR")
    public String getAddInfor() {
        return this.addInfor;
    }

    public void setAddInfor(final String addInfor) {
        this.addInfor = addInfor;
    }

    @Override
    public StockTransDetailDTO toDTO() {
        StockTransDetailDTO dto = new StockTransDetailDTO(
                stockTransDetailId == null ? null : stockTransDetailId.toString(), stockTransId == null ? null : stockTransId.toString(), stockTransDate == null ? null : DateTimeUtils.convertDateToString(stockTransDate, ParamUtils.ddMMyyyy), goodsId == null ? null : goodsId.toString(), goodsCode, goodsName, goodsState, goodsUnitType, goodsUnitTypeName, goodsIsSerial, goodsIsSerialStrip, amountOrder == null ? null : amountOrder.toString(), amountReal == null ? null : amountReal.toString(), bincode, barcode, cellCode, notes, createDatetime == null ? null : DateTimeUtils.convertDateToString(createDatetime, ParamUtils.ddMMyyyy), addInfor
        );
        return dto;
    }

    public String getTmpStockTransDetailId() {
        return tmpStockTransDetailId;
    }

    public void setTmpStockTransDetailId(String tmpStockTransDetailId) {
        this.tmpStockTransDetailId = tmpStockTransDetailId;
    }
}
