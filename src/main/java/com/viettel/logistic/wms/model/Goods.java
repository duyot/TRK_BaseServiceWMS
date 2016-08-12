/**
 * @(#)GoodsBO.java 5/19/2015 3:52 PM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.GoodsDTO;

/**
 * @author ThieuLQ1
 * @version 1.0
 * @since 5/19/2015 3:52 PM
 */
@Entity
@Table(name = "GOODS")
public class Goods extends BaseFWModel {

    //Fields
    private Long custId;
    private Long goodsId;
    private String code;
    private String name;
    private String status;
    private String goodsGroup;
    private Long parentGoodsId;
    private String unitType;
    private String isSerial;
    private String isSerialStrip;
    private Double originPrice;
    private String originSize;
    private Double weight;
    private Double volumeOrigin;
    private Double volumeReal;
    private String description;
    private String goodsType;

    //Constructors
    public Goods() {
        setColId("goodsId");
        setColName("code");
        setUniqueColumn(new String[]{"code", "custId"}); // edit 28/07/15 9h:42p ngocnd6
    }

    public Goods(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Goods(Long custId, Long goodsId, String code, String name, String status, String goodsGroup, Long parentGoodsId, String unitType, String isSerial, String isSerialStrip, Double originPrice, String originSize, Double weight, Double volumeOrigin, Double volumeReal, String description, String goodsType) {
        this.custId = custId;
        this.goodsId = goodsId;
        this.code = code;
        this.name = name;
        this.status = status;
        this.goodsGroup = goodsGroup;
        this.parentGoodsId = parentGoodsId;
        this.unitType = unitType;
        this.isSerial = isSerial;
        this.isSerialStrip = isSerialStrip;
        this.originPrice = originPrice;
        this.originSize = originSize;
        this.weight = weight;
        this.volumeOrigin = volumeOrigin;
        this.volumeReal = volumeReal;
        this.description = description;
        this.goodsType = goodsType;
    }

    //Getters and Setters
    @Column(name = "CUST_ID", nullable = false)
    public Long getCustId() {
        return this.custId;
    }

    public void setCustId(final Long custId) {
        this.custId = custId;
    }

    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "GOODS_SEQ")
            }
    )
    @Column(name = "GOODS_ID", unique = true, nullable = false)
    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(final Long goodsId) {
        this.goodsId = goodsId;
    }

    @Column(name = "CODE", nullable = false)
    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(name = "STATUS", nullable = false)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Column(name = "GOODS_GROUP")
    public String getGoodsGroup() {
        return this.goodsGroup;
    }

    public void setGoodsGroup(final String goodsGroup) {
        this.goodsGroup = goodsGroup;
    }

    @Column(name = "PARENT_GOODS_ID")
    public Long getParentGoodsId() {
        return this.parentGoodsId;
    }

    public void setParentGoodsId(final Long parentGoodsId) {
        this.parentGoodsId = parentGoodsId;
    }

    @Column(name = "UNIT_TYPE")
    public String getUnitType() {
        return this.unitType;
    }

    public void setUnitType(final String unitType) {
        this.unitType = unitType;
    }

    @Column(name = "IS_SERIAL", nullable = false)
    public String getIsSerial() {
        return this.isSerial;
    }

    public void setIsSerial(final String isSerial) {
        this.isSerial = isSerial;
    }

    @Column(name = "IS_SERIAL_STRIP", nullable = false)
    public String getIsSerialStrip() {
        return this.isSerialStrip;
    }

    public void setIsSerialStrip(final String isSerialStrip) {
        this.isSerialStrip = isSerialStrip;
    }

    @Column(name = "ORIGIN_PRICE")
    public Double getOriginPrice() {
        return this.originPrice;
    }

    public void setOriginPrice(final Double originPrice) {
        this.originPrice = originPrice;
    }

    @Column(name = "ORIGIN_SIZE")
    public String getOriginSize() {
        return this.originSize;
    }

    public void setOriginSize(final String originSize) {
        this.originSize = originSize;
    }

    @Column(name = "WEIGHT")
    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(final Double weight) {
        this.weight = weight;
    }

    @Column(name = "VOLUME_ORIGIN")
    public Double getVolumeOrigin() {
        return this.volumeOrigin;
    }

    public void setVolumeOrigin(final Double volumeOrigin) {
        this.volumeOrigin = volumeOrigin;
    }

    @Column(name = "VOLUME_REAL")
    public Double getVolumeReal() {
        return this.volumeReal;
    }

    public void setVolumeReal(final Double volumeReal) {
        this.volumeReal = volumeReal;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Column(name = "GOODS_TYPE")
    public String getGoodsType() {
        return this.goodsType;
    }

    public void setGoodsType(final String goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public GoodsDTO toDTO() {
        GoodsDTO dto = new GoodsDTO(
                custId == null ? null : custId.toString(), goodsId == null ? null : goodsId.toString(), code, name, status, goodsGroup, parentGoodsId == null ? null : parentGoodsId.toString(), unitType, isSerial, isSerialStrip, originPrice == null ? null : String.format("%.0f", originPrice), originSize, weight == null ? null : weight.toString(), volumeOrigin == null ? null : volumeOrigin.toString(), volumeReal == null ? null : volumeReal.toString(), description, goodsType
        );
        return dto;
    }
}
