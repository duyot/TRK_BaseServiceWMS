/**
 * @(#)StockForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.logistic.wms.model.Stock;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Truongbx3
 * @version 1.0
 * @since 06-Apr-15 11:42 PM
 */
@XmlRootElement(name = "Stock")
public class StockDTO extends BaseFWDTO<Stock> {

    //Fields

    private String stockId;
    private String code;
    private String name;
    private String address;
    private String type;
    private String provinceId;
    private String districtId;
    private String wardId;
    private String parentStockId;
    private String square;
    private String volume;
    private String effectiveDate;
    private String expiryDate;
    private String stockPath;
    private String status;
    private String deptId;
    private String usedVolume;
    private static long changedTime = 0;

    //Constructor

    public StockDTO() {
        setDefaultSortField("name");
    }

    public StockDTO(String stockId, String code, String name, String address, String type, String provinceId, String districtId, String wardId, String parentStockId, String square, String volume, String effectiveDate, String expiryDate, String stockPath, String status, String deptId, String usedVolume) {
        this.stockId = stockId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.type = type;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.parentStockId = parentStockId;
        this.square = square;
        this.volume = volume;
        this.effectiveDate = effectiveDate;
        this.expiryDate = expiryDate;
        this.stockPath = stockPath;
        this.status = status;
        this.deptId = deptId;
        this.usedVolume = usedVolume;
    }
	//Getters and setters

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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setParentStockId(String parentStockId) {
        this.parentStockId = parentStockId;
    }

    public String getParentStockId() {
        return parentStockId;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public String getSquare() {
        return square;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolume() {
        return volume;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setStockPath(String stockPath) {
        this.stockPath = stockPath;
    }

    public String getStockPath() {
        return stockPath;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setUsedVolume(String usedVolume) {
        this.usedVolume = usedVolume;
    }

    public String getUsedVolume() {
        return usedVolume;
    }

    @Override
    public Stock toModel() {
        try {
            Stock model = new Stock(
                    !StringUtils.validString(stockId) ? null
                            : Long.valueOf(stockId),
                    code,
                    name,
                    address,
                    type,
                    !StringUtils.validString(provinceId) ? null
                            : Long.valueOf(provinceId),
                    !StringUtils.validString(districtId) ? null
                            : Long.valueOf(districtId),
                    !StringUtils.validString(wardId) ? null
                            : Long.valueOf(wardId),
                    !StringUtils.validString(parentStockId) ? null
                            : Long.valueOf(parentStockId),
                    !StringUtils.validString(square) ? null
                            : Double.parseDouble(square),
                    !StringUtils.validString(volume) ? null
                            : Double.parseDouble(volume),
                    !StringUtils.validString(effectiveDate) ? null
                            : DateTimeUtils.convertStringToDate(effectiveDate),
                    !StringUtils.validString(expiryDate) ? null
                            : DateTimeUtils.convertStringToDate(expiryDate),
                    !StringUtils.validString(stockPath) ? null
                            : Long.valueOf(stockPath),
                    status,
                    !StringUtils.validString(deptId) ? null
                            : Long.valueOf(deptId),
                    !StringUtils.validString(usedVolume) ? null
                            : Double.parseDouble(usedVolume));
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(stockId) ? null : Long.valueOf(stockId);
    }

    @Override
    public String catchName() {
        return getName().toString();
    }

    @Override
    public long getChangedTime() {
        return StockDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        StockDTO.changedTime = changedTime;
    }
}
