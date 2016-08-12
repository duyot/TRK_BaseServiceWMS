/**
 * @(#)OrderStorageZoneForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.logstic.wms.webservice.dto.BaseDTO;

public class OrderStorageZoneDTO extends BaseDTO {

    //Fields
    private String id;
    private String zoneId;
    private String orderDate;
    private String orderVolume;
    private static long changedTime = 0;

    //Constructor
    public OrderStorageZoneDTO() {

    }

    public OrderStorageZoneDTO(String zoneId, String orderDate, String orderVolume) {
        this.zoneId = zoneId;
        this.orderDate = orderDate;
        this.orderVolume = orderVolume;
    }
    

    public OrderStorageZoneDTO(String id, String zoneId, String orderDate, String orderVolume) {
        this.id = id;
        this.zoneId = zoneId;
        this.orderDate = orderDate;
        this.orderVolume = orderVolume;
    }
    //Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderVolume(String orderVolume) {
        this.orderVolume = orderVolume;
    }

    public String getOrderVolume() {
        return orderVolume;
    }

}
