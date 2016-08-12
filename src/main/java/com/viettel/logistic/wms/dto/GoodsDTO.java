/**
 * @(#)GoodsForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.Goods;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ThieuLQ1
 * @version 1.0
 * @since 5/19/2015 3:52 PM
 */
@XmlRootElement(name = "Goods")
public class GoodsDTO extends BaseFWDTO<Goods> {

    //Fields
    private String custId;
    private String goodsId;
    private String code;
    private String name;
    private String status;
    private String goodsGroup;
    private String parentGoodsId;
    private String unitType;
    private String isSerial;
    private String isSerialStrip;
    private String originPrice;
    private String originSize;
    private String weight;
    private String volumeOrigin;
    private String volumeReal;
    private String description;
    private String goodsType;
    private String goodsPacking;
    private static long changedTime = 0;

    //Constructor
    public GoodsDTO() {
        setDefaultSortField("code");
    }
    //QuyenDM 01/12/2015 encode ki tu dac biet sang html
    public GoodsDTO(String custId, String goodsId, String code, String name, String status, String goodsGroup, String parentGoodsId, String unitType, String isSerial, String isSerialStrip, String originPrice, String originSize, String weight, String volumeOrigin, String volumeReal, String description, String goodsType) {
        this.custId = custId;
        this.goodsId = goodsId;
        this.code = code;
        this.name = StringUtils.HTMLEncode(name);
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
        this.description = StringUtils.HTMLEncode(description);
        this.goodsType = goodsType;
    }
    //Getters and setters

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = StringUtils.HTMLEncode(name);
    }

    public String getName() {
        return name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setGoodsGroup(String goodsGroup) {
        this.goodsGroup = goodsGroup;
    }

    public String getGoodsGroup() {
        return goodsGroup;
    }

    public void setParentGoodsId(String parentGoodsId) {
        this.parentGoodsId = parentGoodsId;
    }

    public String getParentGoodsId() {
        return parentGoodsId;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setIsSerial(String isSerial) {
        this.isSerial = isSerial;
    }

    public String getIsSerial() {
        return isSerial;
    }

    public void setIsSerialStrip(String isSerialStrip) {
        this.isSerialStrip = isSerialStrip;
    }

    public String getIsSerialStrip() {
        return isSerialStrip;
    }

    public void setOriginPrice(String originPrice) {
        this.originPrice = originPrice;
    }

    public String getOriginPrice() {
        return originPrice;
    }

    public void setOriginSize(String originSize) {
        this.originSize = originSize;
    }

    public String getOriginSize() {
        return originSize;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }

    public void setVolumeOrigin(String volumeOrigin) {
        this.volumeOrigin = volumeOrigin;
    }

    public String getVolumeOrigin() {
        return volumeOrigin;
    }

    public void setVolumeReal(String volumeReal) {
        this.volumeReal = volumeReal;
    }

    public String getVolumeReal() {
        return volumeReal;
    }

    public void setDescription(String description) {
        this.description = StringUtils.HTMLEncode(description);
    }

    public String getDescription() {
        return description;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    @Override
    public Goods toModel() {
        Goods model = new Goods(
                !StringUtils.validString(custId) ? null
                        : Long.valueOf(custId),
                !StringUtils.validString(goodsId) ? null
                        : Long.valueOf(goodsId),
                code,
                name,
                status,
                goodsGroup,
                !StringUtils.validString(parentGoodsId) ? null
                        : Long.valueOf(parentGoodsId),
                unitType,
                isSerial,
                isSerialStrip,
                !StringUtils.validString(originPrice) ? null
                        : Double.parseDouble(originPrice),
                originSize,
                !StringUtils.validString(weight) ? null
                        : Double.parseDouble(weight),
                !StringUtils.validString(volumeOrigin) ? null
                        : Double.parseDouble(volumeOrigin),
                !StringUtils.validString(volumeReal) ? null
                        : Double.parseDouble(volumeReal),
                description,
                goodsType);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(goodsId) ? null : Long.valueOf(goodsId);
    }

    @Override
    public String catchName() {
        return getCode().toString();
    }

    public String getGoodsPacking() {
        return goodsPacking;
    }

    public void setGoodsPacking(String goodsPacking) {
        this.goodsPacking = goodsPacking;
    }

    @Override
    public long getChangedTime() {
        return GoodsDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        GoodsDTO.changedTime = changedTime;
    }

}
