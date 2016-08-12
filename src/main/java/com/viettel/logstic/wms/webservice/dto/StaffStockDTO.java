/**
 * @(#)StaffStockForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logstic.wms.webservice.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 25-Apr-15 10:59 AM
 */
@XmlRootElement(name = "StaffStock")
public class StaffStockDTO {

    //Fields

    private String id;
    private String staffId;
    private String stockId;
    private String status;

    //Constructor

    public StaffStockDTO() {
    }

    public StaffStockDTO(String id, String staffId, String stockId, String status) {
        this.id = id;
        this.staffId = staffId;
        this.stockId = stockId;
        this.status = status;
    }
	//Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
