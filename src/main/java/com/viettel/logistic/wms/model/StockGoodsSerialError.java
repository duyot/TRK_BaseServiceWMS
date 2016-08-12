/**
 * @(#)StockGoodsSerialErrorBO.java 29-Apr-15 8:41 AM, Copyright 2011 Viettel
 * Telecom. All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.StockGoodsSerialErrorDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 8:41 AM
 */
@Entity
@Table(name = "STOCK_GOODS_SERIAL_ERROR")
public class StockGoodsSerialError extends BaseFWModel {

    //Fields
    private Long id;
    private Long stockTransId;
    private Long custId;
    private Long ownerId;
    private String ownerType;
    private Long goodsId;
    private String goodsState;
    private String status;
    private String fromSerial;
    private String toSerial;
    private String saleType;
    private Double price;
    private String channelTypeId;
    private String barcode;
    private String changeUser;
    private Date changeDate;
    private Date importDate;
    private Date saleDate;
    private Long quantity;
    private String bincode;
    private String addInfor;
    private Date createDatetime;

    //Constructors
    public StockGoodsSerialError() {
        setColId("id");
        setColName("stockTransId");
        setUniqueColumn(new String[]{"id"});
    }

    public StockGoodsSerialError(Long id) {
        this.id = id;
    }

    public StockGoodsSerialError(Long id, Long stockTransId, Long custId, Long ownerId, String ownerType, Long goodsId, String goodsState, String status, String fromSerial, String toSerial, String saleType, Double price, String channelTypeId, String barcode, String changeUser, Date changeDate, Date importDate, Date saleDate, Long quantity, String bincode, String addInfor, Date createDatetime) {
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

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "STOCK_GOODS_SERIAL_ERROR_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "STOCK_TRANS_ID")
    public Long getStockTransId() {
        return this.stockTransId;
    }

    public void setStockTransId(final Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    @Column(name = "CUST_ID", nullable = false)
    public Long getCustId() {
        return this.custId;
    }

    public void setCustId(final Long custId) {
        this.custId = custId;
    }

    @Column(name = "OWNER_ID", nullable = false)
    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name = "OWNER_TYPE", nullable = false)
    public String getOwnerType() {
        return this.ownerType;
    }

    public void setOwnerType(final String ownerType) {
        this.ownerType = ownerType;
    }

    @Column(name = "GOODS_ID", nullable = false)
    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(final Long goodsId) {
        this.goodsId = goodsId;
    }

    @Column(name = "GOODS_STATE", nullable = false)
    public String getGoodsState() {
        return this.goodsState;
    }

    public void setGoodsState(final String goodsState) {
        this.goodsState = goodsState;
    }

    @Column(name = "STATUS", nullable = false)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
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

    @Column(name = "SALE_TYPE")
    public String getSaleType() {
        return this.saleType;
    }

    public void setSaleType(final String saleType) {
        this.saleType = saleType;
    }

    @Column(name = "PRICE")
    public Double getPrice() {
        return this.price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    @Column(name = "CHANNEL_TYPE_ID")
    public String getChannelTypeId() {
        return this.channelTypeId;
    }

    public void setChannelTypeId(final String channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    @Column(name = "BARCODE")
    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(final String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "CHANGE_USER")
    public String getChangeUser() {
        return this.changeUser;
    }

    public void setChangeUser(final String changeUser) {
        this.changeUser = changeUser;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CHANGE_DATE")
    public Date getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(final Date changeDate) {
        this.changeDate = changeDate;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "IMPORT_DATE")
    public Date getImportDate() {
        return this.importDate;
    }

    public void setImportDate(final Date importDate) {
        this.importDate = importDate;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "SALE_DATE")
    public Date getSaleDate() {
        return this.saleDate;
    }

    public void setSaleDate(final Date saleDate) {
        this.saleDate = saleDate;
    }

    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "BINCODE")
    public String getBincode() {
        return this.bincode;
    }

    public void setBincode(final String bincode) {
        this.bincode = bincode;
    }

    @Column(name = "ADD_INFOR")
    public String getAddInfor() {
        return this.addInfor;
    }

    public void setAddInfor(final String addInfor) {
        this.addInfor = addInfor;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATETIME")
    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(final Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Override
    public StockGoodsSerialErrorDTO toDTO() {
        StockGoodsSerialErrorDTO dto = new StockGoodsSerialErrorDTO(
                id == null ? null : id.toString(), stockTransId == null ? null : stockTransId.toString(), custId == null ? null : custId.toString(), ownerId == null ? null : ownerId.toString(), ownerType, goodsId == null ? null : goodsId.toString(), goodsState, status, fromSerial == null ? null : fromSerial.toString(), toSerial == null ? null : toSerial.toString(), saleType, price == null ? null : price.toString(), channelTypeId, barcode, changeUser, changeDate == null ? null : DateTimeUtils.convertDateToString(changeDate, ParamUtils.ddMMyyyy), importDate == null ? null : DateTimeUtils.convertDateToString(importDate, ParamUtils.ddMMyyyy), saleDate == null ? null : DateTimeUtils.convertDateToString(saleDate, ParamUtils.ddMMyyyy), quantity == null ? null : quantity.toString(), bincode, addInfor, createDatetime == null ? null : DateTimeUtils.convertDateToString(createDatetime, ParamUtils.ddMMyyyy)
        );
        return dto;
    }
}
