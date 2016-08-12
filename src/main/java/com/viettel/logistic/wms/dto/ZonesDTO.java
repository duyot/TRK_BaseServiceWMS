/**
 * @(#)ZonesForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.Zones;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 13-Apr-15 2:43 PM
 */
@XmlRootElement(name = "Zones")
public class ZonesDTO extends BaseFWDTO<Zones> {

    //Fields

    private String zoneId;
    private String stockId;
    private String code;
    private String name;
    private String description;
    private String type;
    private String status;
    private String realVolume;
    private String usedVolume;
    private String custIdList;
    private String goodsTypeIdList;
    private static long changedTime = 0;

    //Constructor

    public ZonesDTO() {
        setDefaultSortField("code");
    }

    public ZonesDTO(String zoneId, String stockId, String code, String name, String description, String type, String status, String realVolume, String usedVolume, String custIdList, String goodsTypeIdList) {
        this.zoneId = zoneId;
        this.stockId = stockId;
        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.status = status;
        this.realVolume = realVolume;
        this.usedVolume = usedVolume;
        this.custIdList = custIdList;
        this.goodsTypeIdList = goodsTypeIdList;
    }
	//Getters and setters

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRealVolume(String realVolume) {
        this.realVolume = realVolume;
    }

    public String getRealVolume() {
        return realVolume;
    }

    public void setUsedVolume(String usedVolume) {
        this.usedVolume = usedVolume;
    }

    public String getUsedVolume() {
        return usedVolume;
    }

    public void setCustIdList(String custIdList) {
        this.custIdList = custIdList;
    }

    public String getCustIdList() {
        return custIdList;
    }

    public void setGoodsTypeIdList(String goodsTypeIdList) {
        this.goodsTypeIdList = goodsTypeIdList;
    }

    public String getGoodsTypeIdList() {
        return goodsTypeIdList;
    }

    @Override
    public Zones toModel() {
        Zones model = new Zones(
                !StringUtils.validString(zoneId) ? null
                        : Long.valueOf(zoneId),
                !StringUtils.validString(stockId) ? null
                        : Long.valueOf(stockId),
                code,
                name,
                description,
                !StringUtils.validString(type) ? null
                        : Long.valueOf(type),
                status,
                !StringUtils.validString(realVolume) ? null
                        : Double.parseDouble(realVolume),
                !StringUtils.validString(usedVolume) ? null
                        : Double.parseDouble(usedVolume),
                custIdList,
                goodsTypeIdList);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(zoneId) ? null : Long.valueOf(zoneId);
    }

    @Override
    public String catchName() {
        return getCode().toString();
    }

    @Override
    public long getChangedTime() {
        return ZonesDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        ZonesDTO.changedTime = changedTime;
    }
}
