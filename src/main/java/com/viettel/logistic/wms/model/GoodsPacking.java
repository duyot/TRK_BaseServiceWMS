/**
 * @(#)GoodsPackingBO.java 5/20/2015 6:43 PM, Copyright 2011 Viettel Telecom.
 * All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.GoodsPackingDTO;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 5/20/2015 6:43 PM
 */
@Entity
@Table(name = "GOODS_PACKING")
public class GoodsPacking extends BaseFWModel {

    //Fields
    private Long id;
    private String code;
    private Long packingNumber;
    private String unitType;
    private String packingSize;
    private Double packingWeight;
    private Double packingVolume;
    private String unitTypeName;
    private Long goodsId;
    private String packingDefault;
    private String unitName;
    private Long palletNumber;

    //Constructors
    public GoodsPacking() {
        setColId("id");
        setColName("code");
        setUniqueColumn(new String[]{"code"});
    }

    public GoodsPacking(Long id) {
        this.id = id;
    }

    public GoodsPacking(Long id, String code, Long packingNumber, String unitType, String packingSize, Double packingWeight, Double packingVolume, String unitTypeName, Long goodsId, String packingDefault, String unitName, Long palletNumber) {
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

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "GOODS_PACKING_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "CODE")
    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Column(name = "PACKING_NUMBER")
    public Long getPackingNumber() {
        return this.packingNumber;
    }

    public void setPackingNumber(final Long packingNumber) {
        this.packingNumber = packingNumber;
    }

    @Column(name = "UNIT_TYPE")
    public String getUnitType() {
        return this.unitType;
    }

    public void setUnitType(final String unitType) {
        this.unitType = unitType;
    }

    @Column(name = "PACKING_SIZE")
    public String getPackingSize() {
        return this.packingSize;
    }

    public void setPackingSize(final String packingSize) {
        this.packingSize = packingSize;
    }

    @Column(name = "PACKING_WEIGHT")
    public Double getPackingWeight() {
        return this.packingWeight;
    }

    public void setPackingWeight(final Double packingWeight) {
        this.packingWeight = packingWeight;
    }

    @Column(name = "PACKING_VOLUME")
    public Double getPackingVolume() {
        return this.packingVolume;
    }

    public void setPackingVolume(final Double packingVolume) {
        this.packingVolume = packingVolume;
    }

    @Column(name = "UNIT_TYPE_NAME")
    public String getUnitTypeName() {
        return this.unitTypeName;
    }

    public void setUnitTypeName(final String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }

    @Column(name = "GOODS_ID", nullable = false)
    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(final Long goodsId) {
        this.goodsId = goodsId;
    }

    @Column(name = "PACKING_DEFAULT")
    public String getPackingDefault() {
        return this.packingDefault;
    }

    public void setPackingDefault(final String packingDefault) {
        this.packingDefault = packingDefault;
    }

    @Column(name = "UNIT_NAME")
    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(final String unitName) {
        this.unitName = unitName;
    }

    @Column(name = "PALLET_NUMBER")
    public Long getPalletNumber() {
        return this.palletNumber;
    }

    public void setPalletNumber(final Long palletNumber) {
        this.palletNumber = palletNumber;
    }

    @Override
    public GoodsPackingDTO toDTO() {
        GoodsPackingDTO dto = new GoodsPackingDTO(
                id == null ? null : id.toString(), code, packingNumber == null ? null : packingNumber.toString(), unitType, packingSize, packingWeight == null ? null : packingWeight.toString(), packingVolume == null ? null : packingVolume.toString(), unitTypeName, goodsId == null ? null : goodsId.toString(), packingDefault, unitName, palletNumber == null ? null : palletNumber.toString()
        );
        return dto;
    }
}
