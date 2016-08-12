/**
 * @(#)StockGoodsTotalBO.java 29-Apr-15 11:35 AM, Copyright 2011 Viettel
 * Telecom. All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.StockGoodsTotalDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 11:35 AM
 */
@Entity
@Table(name = "STOCK_GOODS_TOTAL")
public class StockGoodsTotal extends BaseFWModel {

    //Fields
    private Long id;
    private Long custId;
    private Long ownerId;
    private String ownerType;
    private Long goodsId;
    private String goodsState;
    private Double amount;
    private Double amountIssue;
    private Date changeDate;

    //Constructors
    public StockGoodsTotal() {
        setColId("id");
        setColName("cusId");
        setUniqueColumn(new String[]{"custId", "ownerId", "ownerType", "goodsId", "goodsState"});
    }

    public StockGoodsTotal(Long id) {
        this.id = id;
    }

    public StockGoodsTotal(Long id, Long custId, Long ownerId, String ownerType, Long goodsId, String goodsState, Double amount, Double amountIssue, Date changeDate) {
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

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "STOCK_GOODS_TOTAL_SEQ")
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

    @Override
    public StockGoodsTotalDTO toDTO() {
        StockGoodsTotalDTO dto = new StockGoodsTotalDTO(
                id == null ? null : id.toString(), custId == null ? null : custId.toString(), ownerId == null ? null : ownerId.toString(), ownerType, goodsId == null ? null : goodsId.toString(), goodsState, amount == null ? null : amount.toString(), amountIssue == null ? null : String.format("%.0f", amountIssue), changeDate == null ? null : DateTimeUtils.convertDateToString(changeDate, ParamUtils.ddMMyyyy)
        );
        return dto;
    }
}
