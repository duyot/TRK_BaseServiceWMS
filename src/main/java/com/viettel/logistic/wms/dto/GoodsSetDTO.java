/**
 * @(#)GoodsSetForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.GoodsSet;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:43 PM
 */
@XmlRootElement(name = "GoodsSet")
public class GoodsSetDTO extends BaseFWDTO<GoodsSet> {

    //Fields

    private String id;
    private String masterId;
    private String detailId;
    private String quantity;
    private String status;
    private static long changedTime = 0;

    //Constructor

    public GoodsSetDTO() {
        setDefaultSortField("masterId");
    }

    public GoodsSetDTO(String id, String masterId, String detailId, String quantity, String status) {
        this.id = id;
        this.masterId = masterId;
        this.detailId = detailId;
        this.quantity = quantity;
        this.status = status;
    }
	//Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public GoodsSet toModel() {
        GoodsSet model = new GoodsSet(
                !StringUtils.validString(id) ? null
                        : Long.valueOf(id),
                !StringUtils.validString(masterId) ? null
                        : Long.valueOf(masterId),
                !StringUtils.validString(detailId) ? null
                        : Long.valueOf(detailId),
                !StringUtils.validString(quantity) ? null
                        : Long.valueOf(quantity),
                status);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id) ? null : Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getMasterId().toString();
    }

    @Override
    public long getChangedTime() {
        return GoodsSetDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        GoodsSetDTO.changedTime = changedTime;
    }
}
