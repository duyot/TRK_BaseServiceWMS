/**
 * @(#)MapStaffGoodsForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.logistic.wms.model.MapStaffGoods;
import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hongdq4
 * @version 1.0
 * @since 10/16/2015 10:12 AM
 */
@XmlRootElement(name = "MapStaffGoods")
public class MapStaffGoodsDTO extends BaseFWDTO<MapStaffGoods> {

    //Fields
    private String mapId;
    private String staffId;
    private String staffCode;
    private String staffName;
    private String staffType;
    private String staffTypeName;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String unitType;
    private String unitTypeName;
    private String goodsType;
    private String goodsTypeName;
    private String goodsGroupName;
    private String staffEmail;
    private String staffPhone;
    private static long changedTime = 0;

    //Constructor
    public MapStaffGoodsDTO() {
        setDefaultSortField("staffId");
    }

    public MapStaffGoodsDTO(String mapId, String staffId, String staffCode, String staffName, String staffType, String staffTypeName, String goodsId, String goodsCode, String goodsName, String unitType, String unitTypeName, String goodsType, String goodsTypeName, String goodsGroupName, String staffEmail, String staffPhone) {
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
    //Getters and setters

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getMapId() {
        return mapId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffTypeName(String staffTypeName) {
        this.staffTypeName = staffTypeName;
    }

    public String getStaffTypeName() {
        return staffTypeName;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }

    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public String getGoodsGroupName() {
        return goodsGroupName;
    }

    public void setGoodsGroupName(String goodsGroupName) {
        this.goodsGroupName = goodsGroupName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    @Override
    public MapStaffGoods toModel() {
        MapStaffGoods model = new MapStaffGoods(
                !StringUtils.validString(mapId) ? null
                        : Long.valueOf(mapId),
                !StringUtils.validString(staffId) ? null
                        : Long.valueOf(staffId),
                staffCode,
                staffName,
                staffType,
                staffTypeName,
                !StringUtils.validString(goodsId) ? null
                        : Long.valueOf(goodsId),
                goodsCode,
                goodsName,
                unitType,
                unitTypeName,
                goodsType,
                goodsTypeName,
                goodsGroupName,
                staffEmail,
                staffPhone);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(mapId) ? null : Long.valueOf(mapId);
    }

    @Override
    public String catchName() {
        return getStaffId().toString();
    }

    @Override
    public long getChangedTime() {
        return MapStaffGoodsDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        MapStaffGoodsDTO.changedTime = changedTime;
    }
}
