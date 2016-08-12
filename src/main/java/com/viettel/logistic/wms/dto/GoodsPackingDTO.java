/**
 * @(#)GoodsPackingForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.GoodsPacking;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 5/20/2015 6:43 PM
 */
@XmlRootElement(name = "GoodsPacking")
public class GoodsPackingDTO extends BaseFWDTO<GoodsPacking> {

    //Fields

    private String id;
    private String code;
    private String packingNumber;
    private String unitType;
    private String packingSize;
    private String packingWeight;
    private String packingVolume;
    private String unitTypeName;
    private String goodsId;
    private String packingDefault;
    private String unitName;
    private String palletNumber;
    private static long changedTime = 0;

    //Constructor

    public GoodsPackingDTO() {
        setDefaultSortField("code");
    }

    public GoodsPackingDTO(String id, String code, String packingNumber, String unitType, String packingSize, String packingWeight, String packingVolume, String unitTypeName, String goodsId, String packingDefault, String unitName, String palletNumber) {
        this.id = id;
        this.code = code;
        this.packingNumber = packingNumber;
        this.unitType = unitType;
        this.packingSize = packingSize;
        this.packingWeight = packingWeight;
        this.packingVolume = packingVolume;
        this.unitTypeName = unitTypeName;
        this.goodsId = goodsId;
        this.packingDefault = packingDefault;
        this.unitName = unitName;
        this.palletNumber = palletNumber;
    }
	//Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setPackingNumber(String packingNumber) {
        this.packingNumber = packingNumber;
    }

    public String getPackingNumber() {
        return packingNumber;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setPackingSize(String packingSize) {
        this.packingSize = packingSize;
    }

    public String getPackingSize() {
        return packingSize;
    }

    public void setPackingWeight(String packingWeight) {
        this.packingWeight = packingWeight;
    }

    public String getPackingWeight() {
        return packingWeight;
    }

    public void setPackingVolume(String packingVolume) {
        this.packingVolume = packingVolume;
    }

    public String getPackingVolume() {
        return packingVolume;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }

    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setPackingDefault(String packingDefault) {
        this.packingDefault = packingDefault;
    }

    public String getPackingDefault() {
        return packingDefault;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setPalletNumber(String palletNumber) {
        this.palletNumber = palletNumber;
    }

    public String getPalletNumber() {
        return palletNumber;
    }

    @Override
    public GoodsPacking toModel() {
        GoodsPacking model = new GoodsPacking(
                !StringUtils.validString(id) ? null
                        : Long.valueOf(id),
                code,
                !StringUtils.validString(packingNumber) ? null
                        : Long.valueOf(packingNumber),
                unitType,
                packingSize,
                !StringUtils.validString(packingWeight) ? null
                        : Double.parseDouble(packingWeight),
                !StringUtils.validString(packingVolume) ? null
                        : Double.parseDouble(packingVolume),
                unitTypeName,
                !StringUtils.validString(goodsId) ? null
                        : Long.valueOf(goodsId),
                packingDefault,
                unitName,
                !StringUtils.validString(palletNumber) ? null
                        : Long.valueOf(palletNumber));
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id) ? null : Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getCode().toString();
    }

    @Override
    public long getChangedTime() {
        return GoodsPackingDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        GoodsPackingDTO.changedTime = changedTime;
    }
}
