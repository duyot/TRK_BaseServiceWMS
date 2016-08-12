/**
 * @(#)MapStockForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author truongbx3
 * @version 1.0
 * @since 8/24/2015 10:16 AM
 */
@XmlRootElement(name = "MapStock")
public class MapStockDTO  {

    //Fields

    private String id;
    private String logStockId;
    private String logStockCode;
    private String stockId;
    private String stockCode;
    private String type;
    private String address;
    private String provinceId;
    private String districtId;
    private String wardId;

    //Constructor
    public MapStockDTO(){

    }
   

    public MapStockDTO(String id, String logStockId, String logStockCode, String stockId, String stockCode, String type, String address, String provinceId, String districtId, String wardId) {
        this.id = id;
        this.logStockId = logStockId;
        this.logStockCode = logStockCode;
        this.stockId = stockId;
        this.stockCode = stockCode;
        this.type = type;
        this.address = address;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
    }
	//Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLogStockId(String logStockId) {
        this.logStockId = logStockId;
    }

    public String getLogStockId() {
        return logStockId;
    }

    public void setLogStockCode(String logStockCode) {
        this.logStockCode = logStockCode;
    }

    public String getLogStockCode() {
        return logStockCode;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
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

}
