/**
 * @(#)CellsForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.Cells;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@XmlRootElement(name = "Cells")
public class CellsDTO extends BaseFWDTO<Cells> {

    //Fields

    private String cellId;
    private String stockId;
    private String zoneId;
    private String zoneShelfId;
    private String zoneShelfIdName;
    private String code;
    private String name;
    private String hight;
    private String wide;
    private String length;
    private String volume;
    private String volumeReal;
    private String status;
    private String zone;
    private static long changedTime = 0;

    //Constructor

    public CellsDTO() {
        setDefaultSortField("cellId");
    }

    public CellsDTO(String cellId, String stockId, String zoneId, String zoneShelfId, String code, String name, String hight, String wide, String length, String volume, String volumeReal, String status) {
        this.cellId = cellId;
        this.stockId = stockId;
        this.zoneId = zoneId;
        this.zoneShelfId = zoneShelfId;
        this.code = code;
        this.name = name;
        this.hight = hight;
        this.wide = wide;
        this.length = length;
        this.volume = volume;
        this.volumeReal = volumeReal;
        this.status = status;
    }
	//Getters and setters

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getCellId() {
        return cellId;
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

    public void setZoneShelfId(String zoneShelfId) {
        this.zoneShelfId = zoneShelfId;
    }

    public String getZoneShelfId() {
        return zoneShelfId;
    }

    public void setZoneShelfIdName(String zoneShelfIdName) {
        this.zoneShelfIdName = zoneShelfIdName;
    }

    public String getZoneShelfIdName() {
        return zoneShelfIdName;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public Cells toModel() {
        Cells model = new Cells(
                !StringUtils.validString(cellId) ? null
                        : Long.valueOf(cellId),
                !StringUtils.validString(stockId) ? null
                        : Long.valueOf(stockId),
                !StringUtils.validString(zoneId) ? null
                        : Long.valueOf(zoneId),
                !StringUtils.validString(zoneShelfId) ? null
                        : Long.valueOf(zoneShelfId),
                code,
                name,
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
                status);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(cellId) ? null : Long.valueOf(cellId);
    }

    @Override
    public String catchName() {
        return getCellId().toString();
    }

    @Override
    public long getChangedTime() {
        return CellsDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        CellsDTO.changedTime = changedTime;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
    
}
