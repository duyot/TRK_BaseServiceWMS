/**
 * @(#)MapStaffGoodsBO.java 10/16/2015 10:12 AM, Copyright 2011 Viettel Telecom.
 * All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.logistic.wms.dto.MapStaffGoodsDTO;
import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author hongdq4
 * @version 1.0
 * @since 10/16/2015 10:12 AM
 */
@Entity
@Table(name = "MAP_STAFF_GOODS")
public class MapStaffGoods extends BaseFWModel {

    //Fields
    private Long mapId;
    private Long staffId;
    private String staffCode;
    private String staffName;
    private String staffType;
    private String staffTypeName;
    private Long goodsId;
    private String goodsCode;
    private String goodsName;
    private String unitType;
    private String unitTypeName;
    private String goodsType;
    private String goodsTypeName;
    private String goodsGroup;
    private String goodsGroupName;
    private String staffEmail;
    private String staffPhone;

    //Constructors
    public MapStaffGoods() {
        setColId("mapId");
        setColName("staffId");
        setUniqueColumn(new String[]{"staffId", "goodsId"});
    }

    public MapStaffGoods(Long mapId) {
        this.mapId = mapId;
    }

    public MapStaffGoods(Long mapId, Long staffId, String staffCode, String staffName, String staffType, String staffTypeName, Long goodsId, String goodsCode, String goodsName, String unitType, String unitTypeName, String goodsType, String goodsTypeName, String goodsGroupName, String staffEmail, String staffPhone) {
        this.mapId = mapId;
        this.staffId = staffId;
        this.staffCode = staffCode;
        this.staffName = staffName;
        this.staffType = staffType;
        this.staffTypeName = staffTypeName;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.unitType = unitType;
        this.unitTypeName = unitTypeName;
        this.goodsType = goodsType;
        this.goodsTypeName = goodsTypeName;
        this.goodsGroupName = goodsGroupName;
        this.staffEmail = staffEmail;
        this.staffPhone = staffPhone;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "MAP_STAFF_GOODS_SEQ")
            }
    )
    @Column(name = "MAP_ID", unique = true, nullable = false)
    public Long getMapId() {
        return this.mapId;
    }

    public void setMapId(final Long mapId) {
        this.mapId = mapId;
    }

    @Column(name = "STAFF_ID", nullable = false)
    public Long getStaffId() {
        return this.staffId;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }

    @Column(name = "STAFF_CODE", nullable = false)
    public String getStaffCode() {
        return this.staffCode;
    }

    public void setStaffCode(final String staffCode) {
        this.staffCode = staffCode;
    }

    @Column(name = "STAFF_NAME", nullable = false)
    public String getStaffName() {
        return this.staffName;
    }

    public void setStaffName(final String staffName) {
        this.staffName = staffName;
    }

    @Column(name = "STAFF_TYPE")
    public String getStaffType() {
        return this.staffType;
    }

    public void setStaffType(final String staffType) {
        this.staffType = staffType;
    }

    @Column(name = "STAFF_TYPE_NAME")
    public String getStaffTypeName() {
        return this.staffTypeName;
    }

    public void setStaffTypeName(final String staffTypeName) {
        this.staffTypeName = staffTypeName;
    }

    @Column(name = "GOODS_ID", nullable = false)
    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(final Long goodsId) {
        this.goodsId = goodsId;
    }

    @Column(name = "GOODS_CODE", nullable = false)
    public String getGoodsCode() {
        return this.goodsCode;
    }

    public void setGoodsCode(final String goodsCode) {
        this.goodsCode = goodsCode;
    }

    @Column(name = "GOODS_NAME", nullable = false)
    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(final String goodsName) {
        this.goodsName = goodsName;
    }

    @Column(name = "UNIT_TYPE")
    public String getUnitType() {
        return this.unitType;
    }

    public void setUnitType(final String unitType) {
        this.unitType = unitType;
    }

    @Column(name = "UNIT_TYPE_NAME")
    public String getUnitTypeName() {
        return this.unitTypeName;
    }

    public void setUnitTypeName(final String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }

    @Column(name = "GOODS_TYPE")
    public String getGoodsType() {
        return this.goodsType;
    }

    public void setGoodsType(final String goodsType) {
        this.goodsType = goodsType;
    }

    @Column(name = "GOODS_TYPE_NAME")
    public String getGoodsTypeName() {
        return this.goodsTypeName;
    }

    public void setGoodsTypeName(final String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    @Column(name = "GOODS_GROUP_NAME")
    public String getGoodsGroupName() {
        return goodsGroupName;
    }

    public void setGoodsGroupName(String goodsGroupName) {
        this.goodsGroupName = goodsGroupName;
    }

    @Column(name = "STAFF_EMAIL")
    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    @Column(name = "STAFF_PHONE")
    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    @Override
    public MapStaffGoodsDTO toDTO() {
        MapStaffGoodsDTO dto = new MapStaffGoodsDTO(
                mapId == null ? null : mapId.toString(), staffId == null ? null : staffId.toString(), staffCode, staffName, staffType, staffTypeName, goodsId == null ? null : goodsId.toString(), goodsCode, goodsName, unitType, unitTypeName, goodsType, goodsTypeName, goodsGroupName, staffEmail, staffPhone
        );
        return dto;
    }
}
