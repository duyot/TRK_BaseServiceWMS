/**
 * @(#)StockGoodsBO.java 07-May-15 2:37 PM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.StockGoodsDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 07-May-15 2:37 PM
 */
@Entity
@Table(name = "STOCK_GOODS")
public class StockGoods extends BaseFWModel {

    //Fields
    private Long id;
    private Long custId;
    private Long ownerId;
    private String ownerType;
    private Long goodsId;
    private String goodsState;
    private String barcode;
    private Double amount;
    private Double amountIssue;
    private Date changeDate;
    private Date importDate;
    private String bincode;
    private String addInfor;
    private String cellCode;
    
    //new filed duyot
    private Long partnerId;
    //new field for syn
    private String status;
    private Long orderId;
    private Long importStockTransId;
    //
    //Constructors
    public StockGoods() {
        setColId("id");
        setColName("custId");
        setUniqueColumn(new String[]{"custId", "ownerId", "ownerType", "goodsId", "goodsState", "barcode", "bincode", "cellCode"});
    }

    public StockGoods(Long id) {
        this.id = id;
    }
    
    public StockGoods(Long id, Long custId, Long ownerId, String ownerType, Long goodsId, String goodsState, 
            String barcode, Double amount, Double amountIssue, Date changeDate, Date importDate, String bincode, 
            String addInfor, String cellCode, Long partnerId,
            String status, Long orderId, Long importStockTransId
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
        //
        this.partnerId = partnerId;
        //
        this.status = status;
        this.orderId = orderId;
        this.importStockTransId = importStockTransId;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "STOCK_GOODS_SEQ")
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

    @Column(name = "BARCODE")
    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(final String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "AMOUNT", nullable = false)
    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    @Column(name = "AMOUNT_ISSUE", nullable = false)
    public Double getAmountIssue() {
        return this.amountIssue;
    }

    public void setAmountIssue(final Double amountIssue) {
        this.amountIssue = amountIssue;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CHANGE_DATE", nullable = false)
    public Date getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(final Date changeDate) {
        this.changeDate = changeDate;
    }

    @Column(name = "IMPORT_DATE", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getImportDate() {
        return this.importDate;
    }

    public void setImportDate(final Date importDate) {
        this.importDate = importDate;
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

    @Column(name = "CELL_CODE")
    public String getCellCode() {
        return this.cellCode;
    }

    public void setCellCode(final String cellCode) {
        this.cellCode = cellCode;
    }

    @Column(name = "PARTNER_ID")
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }
    
    
    //---------------------------
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name = "ORDER_ID")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

     @Column(name = "IMPORT_STOCK_TRANS_ID")
    public Long getImportStockTransId() {
        return importStockTransId;
    }

    public void setImportStockTransId(Long importStockTransId) {
        this.importStockTransId = importStockTransId;
    }
    

    @Override
    public StockGoodsDTO toDTO() {
        StockGoodsDTO dto = new StockGoodsDTO(
                id == null ? null : id.toString(), custId == null ? null : custId.toString(), 
                ownerId == null ? null : ownerId.toString(), ownerType, 
                goodsId == null ? null : goodsId.toString(), goodsState, barcode, 
                amount == null ? null : amount.toString(), amountIssue == null ? null : amountIssue.toString(), 
                changeDate == null ? null : DateTimeUtils.convertDateToString(changeDate, ParamUtils.ddMMyyyyHHmmss), 
                importDate == null ? null : DateTimeUtils.convertDateToString(importDate, ParamUtils.ddMMyyyyHHmmss), 
                bincode, addInfor, cellCode, partnerId == null ? null : partnerId.toString(),status,
                orderId == null ? null : orderId.toString(),importStockTransId == null ? null : importStockTransId.toString()
        );
        return dto;
    }
}
