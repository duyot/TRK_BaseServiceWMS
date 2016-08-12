/**
 * @(#)GoodsHandoverBO.java 8/22/2015 2:39 PM, Copyright 2011 Viettel Telecom.
 * All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.GoodsHandoverDTO;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:39 PM
 */
@Entity
@Table(name = "GOODS_HANDOVER")
public class GoodsHandover extends BaseFWModel {

    //Fields
    private Long goodsHandoverId;
    private String goodsCode;
    private String goodsName;
    private Double quantityProposed;
    private Double quantityReal;
    private Double quantityFluctuation;
    private Double quantityRemain;
    private String unitType;
    private String status;
    private Long requestId;
    private String note;
    private String type;

    //Constructors
    public GoodsHandover() {
        setColId("goodsHandoverId");
        setColName("goodsHandoverId");
        setUniqueColumn(new String[]{"goodsHandoverId"});
    }

    public GoodsHandover(Long goodsHandoverId) {
        this.goodsHandoverId = goodsHandoverId;
    }

    public GoodsHandover(Long goodsHandoverId, String goodsCode, String goodsName, Double quantityProposed, Double quantityReal,
            Double quantityFluctuation, Double quantityRemain, String unitType, String status, Long requestId, String note, String type) {
        this.goodsHandoverId = goodsHandoverId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.quantityProposed = quantityProposed;
        this.quantityReal = quantityReal;
        this.quantityFluctuation = quantityFluctuation;
        this.quantityRemain = quantityRemain;
        this.unitType = unitType;
        this.status = status;
        this.requestId = requestId;
        this.note = note;
        this.type = type;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "GOODS_HANDOVER_SEQ")
            }
    )
    @Column(name = "GOODS_HANDOVER_ID", unique = true, nullable = false)
    public Long getGoodsHandoverId() {
        return this.goodsHandoverId;
    }

    public void setGoodsHandoverId(final Long goodsHandoverId) {
        this.goodsHandoverId = goodsHandoverId;
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

    @Column(name = "QUANTITY_PROPOSED")
    public Double getQuantityProposed() {
        return this.quantityProposed;
    }

    public void setQuantityProposed(final Double quantityProposed) {
        this.quantityProposed = quantityProposed;
    }

    @Column(name = "QUANTITY_REAL")
    public Double getQuantityReal() {
        return this.quantityReal;
    }

    public void setQuantityReal(final Double quantityReal) {
        this.quantityReal = quantityReal;
    }

    @Column(name = "QUANTITY_FLUCTUATION")
    public Double getQuantityFluctuation() {
        return this.quantityFluctuation;
    }

    public void setQuantityFluctuation(final Double quantityFluctuation) {
        this.quantityFluctuation = quantityFluctuation;
    }

    @Column(name = "QUANTITY_REMAIN")
    public Double getQuantityRemain() {
        return this.quantityRemain;
    }

    public void setQuantityRemain(final Double quantityRemain) {
        this.quantityRemain = quantityRemain;
    }

    @Column(name = "UNIT_TYPE")
    public String getUnitType() {
        return this.unitType;
    }

    public void setUnitType(final String unitType) {
        this.unitType = unitType;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Column(name = "REQUEST_ID")
    public Long getRequestId() {
        return this.requestId;
    }

    public void setRequestId(final Long requestId) {
        this.requestId = requestId;
    }

    @Column(name = "NOTE")
    public String getNote() {
        return this.note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Column(name = "TYPE")
    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public GoodsHandoverDTO toDTO() {
        GoodsHandoverDTO dto = new GoodsHandoverDTO(
                goodsHandoverId == null ? null : goodsHandoverId.toString(), goodsCode, goodsName,
                quantityProposed == null ? null : quantityProposed.toString(), quantityReal == null ? null : quantityReal.toString(),
                quantityFluctuation == null ? null : quantityFluctuation.toString(), quantityRemain == null ? null : quantityRemain.toString(),
                unitType, status, requestId == null ? null : requestId.toString(), note, type
        );
        return dto;
    }
}
