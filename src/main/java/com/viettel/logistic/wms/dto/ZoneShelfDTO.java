/**
 * @(#)ZoneShelfForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.ZoneShelf;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 06-May-15 9:44 PM
 */
@XmlRootElement(name = "ZoneShelf")
public class ZoneShelfDTO extends BaseFWDTO<ZoneShelf> {

    //Fields

    private String id;
    private String stockId;
    private String zoneId;
    private String zoneIdName;
    private String code;
    private String name;
    private String amount;
    private String layerNum;
    private String cellNum;
    private String hightLayer;
    private String hight;
    private String wide;
    private String length;
    private String volume;
    private String volumeReal;
    private String isExportedCell;
    private static long changedTime = 0;

    //Constructor

    public ZoneShelfDTO() {
        setDefaultSortField("zoneId");
    }

    public ZoneShelfDTO(String id, String stockId, String zoneId, String code, String name, String amount, String layerNum, String cellNum, String hightLayer, String hight, String wide, String length, String volume, String volumeReal, String isExportedCell) {
        this.id = id;
        this.stockId = stockId;
        this.zoneId = zoneId;
        this.code = code;
        this.name = name;
        this.amount = amount;
        this.layerNum = layerNum;
        this.cellNum = cellNum;
        this.hightLayer = hightLayer;
        this.hight = hight;
        this.wide = wide;
        this.length = length;
        this.volume = volume;
        this.volumeReal = volumeReal;
        this.isExportedCell = isExportedCell;
    }
	//Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneIdName(String zoneIdName) {
        this.zoneIdName = zoneIdName;
    }

    public String getZoneIdName() {
        return zoneIdName;
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

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setLayerNum(String layerNum) {
        this.layerNum = layerNum;
    }

    public String getLayerNum() {
        return layerNum;
    }

    public void setCellNum(String cellNum) {
        this.cellNum = cellNum;
    }

    public String getCellNum() {
        return cellNum;
    }

    public void setHightLayer(String hightLayer) {
        this.hightLayer = hightLayer;
    }

    public String getHightLayer() {
        return hightLayer;
    }

    public void setHight(String hight) {
        this.hight = hight;
    }

    public String getHight() {
        return hight;
    }

    public void setWide(String wide) {
        this.wide = wide;
    }

    public String getWide() {
        return wide;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getLength() {
        return length;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolumeReal(String volumeReal) {
        this.volumeReal = volumeReal;
    }

    public String getVolumeReal() {
        return volumeReal;
    }

    public void setIsExportedCell(String isExportedCell) {
        this.isExportedCell = isExportedCell;
    }

    public String getIsExportedCell() {
        return isExportedCell;
    }

    @Override
    public ZoneShelf toModel() {
        ZoneShelf model = new ZoneShelf(
                !StringUtils.validString(id) ? null
                        : Long.valueOf(id),
                !StringUtils.validString(stockId) ? null
                        : Long.valueOf(stockId),
                !StringUtils.validString(zoneId) ? null
                        : Long.valueOf(zoneId),
                code,
                name,
                !StringUtils.validString(amount) ? null
                        : Long.valueOf(amount),
                !StringUtils.validString(layerNum) ? null
                        : Long.valueOf(layerNum),
                !StringUtils.validString(cellNum) ? null
                        : Long.valueOf(cellNum),
                !StringUtils.validString(hightLayer) ? null
                        : Long.valueOf(hightLayer),
                !StringUtils.validString(hight) ? null
                        : Double.parseDouble(hight),
                !StringUtils.validString(wide) ? null
                        : Double.parseDouble(wide),
                !StringUtils.validString(length) ? null
                        : Double.parseDouble(length),
                !StringUtils.validString(volume) ? null
                        : Double.parseDouble(volume),
                !StringUtils.validString(volumeReal) ? null
                        : Double.parseDouble(volumeReal),
                isExportedCell);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id) ? null : Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getZoneId().toString();
    }

    @Override
    public long getChangedTime() {
        return ZoneShelfDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        ZoneShelfDTO.changedTime = changedTime;
    }
}
