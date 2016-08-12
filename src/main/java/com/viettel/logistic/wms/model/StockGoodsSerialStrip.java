/**
 * @(#)StockGoodsSerialStripBO.java 5/21/2015 1:35 PM, Copyright 2011 Viettel
 * Telecom. All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 5/21/2015 1:35 PM
 */
@Entity
@Table(name = "STOCK_GOODS_SERIAL_STRIP")
public class StockGoodsSerialStrip extends BaseFWModel {

    //Fields
    private Long id;
    private Long custId;
    private Long ownerId;
    private String ownerType;
    private Long goodsId;
    private String goodsState;
    private String status;
    private String fromSerial;
    private String toSerial;
    private Long quantity;
    private String saleType;
    private Double price;
    private String channelTypeId;
    private String barcode;
    private String changeUser;
    private Date changeDate;
    private Date importDate;
    private Date saleDate;
    private String bincode;
    private String cellCode;
    private String addInfor;
    //
    private Long partnerId;
    //duyot: new fields dong bo bccs
    private Long importStockTransId;
    private Long orderId;

    //Constructors
    public StockGoodsSerialStrip() {
        setColId("id");
        setColName("custId");
        setUniqueColumn(new String[]{"custId", "goodsId", "fromSerial", "toSerial"});
    }

    public StockGoodsSerialStrip(Long id) {
        this.id = id;
    }

    public StockGoodsSerialStrip(Long id, Long custId, Long ownerId, String ownerType,
            Long goodsId, String goodsState, String status, String fromSerial, String toSerial,
            Long quantity, String saleType, Double price, String channelTypeId, String barcode,
            String changeUser, Date changeDate, Date importDate, Date saleDate, String bincode,
            String cellCode, String addInfor, Long partnerId, Long importStockTransId,Long orderId
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

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "STOCK_GOODS_SERIAL_STRIP_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    @Column(name = "FROM_SERIAL", nullable = false)
    public String getFromSerial() {
        return this.fromSerial;
    }

    public void setFromSerial(final String fromSerial) {
        this.fromSerial = fromSerial;
    }

    @Column(name = "TO_SERIAL", nullable = false)
    public String getToSerial() {
        return this.toSerial;
    }

    public void setToSerial(final String toSerial) {
        this.toSerial = toSerial;
    }

    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
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

    @Column(name = "BINCODE")
    public String getBincode() {
        return this.bincode;
    }

    public void setBincode(final String bincode) {
        this.bincode = bincode;
    }

    @Column(name = "CELL_CODE")
    public String getCellCode() {
        return this.cellCode;
    }

    public void setCellCode(final String cellCode) {
        this.cellCode = cellCode;
    }

    @Column(name = "ADD_INFOR")
    public String getAddInfor() {
        return this.addInfor;
    }

    public void setAddInfor(final String addInfor) {
        this.addInfor = addInfor;
    }

    @Column(name = "PARTNER_ID")
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    @Column(name = "IMPORT_STOCK_TRANS_ID")
    public Long getImportStockTransId() {
        return importStockTransId;
    }

    public void setImportStockTransId(Long importStockTransId) {
        this.importStockTransId = importStockTransId;
    }

     @Column(name = "ORDER_ID")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public StockGoodsSerialStripDTO toDTO() {
        StockGoodsSerialStripDTO dto = new StockGoodsSerialStripDTO(
                id == null ? null : id.toString(), custId == null ? null : custId.toString(),
                ownerId == null ? null : ownerId.toString(), ownerType, goodsId == null ? null : goodsId.toString(),
                goodsState, status, fromSerial, toSerial, quantity == null ? null : quantity.toString(), saleType,
                price == null ? null : price.toString(), channelTypeId, barcode, changeUser,
                changeDate == null ? null : DateTimeUtils.convertDateToString(changeDate, ParamUtils.ddMMyyyyHHmmss),
                importDate == null ? null : DateTimeUtils.convertDateToString(importDate, ParamUtils.ddMMyyyyHHmmss),
                saleDate == null ? null : DateTimeUtils.convertDateToString(saleDate, ParamUtils.ddMMyyyyHHmmss),
                bincode, cellCode, addInfor, partnerId == null ? null : partnerId.toString(),
                importStockTransId == null ? null : importStockTransId.toString(),orderId == null ? null : orderId.toString()
        );
        return dto;
    }
}
