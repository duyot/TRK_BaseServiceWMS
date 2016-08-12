/**
 * @(#)PosProductForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.PosProduct;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:45 PM
 */
@XmlRootElement(name = "PosProduct")
public class PosProductDTO extends BaseFWDTO<PosProduct> {

    //Fields

    private String id;
    private String max;
    private String positionId;
    private String goodsId;
    private String custId;
    private static long changedTime = 0;

    //Constructor

    public PosProductDTO() {
        setDefaultSortField("positionId");
    }

    public PosProductDTO(String id, String max, String positionId, String goodsId, String custId) {
        this.id = id;
        this.max = max;
        this.positionId = positionId;
        this.goodsId = goodsId;
        this.custId = custId;
    }
	//Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMax() {
        return max;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
    }

    @Override
    public PosProduct toModel() {
        PosProduct model = new PosProduct(
                !StringUtils.validString(id) ? null
                        : Long.valueOf(id),
                !StringUtils.validString(max) ? null
                        : Long.valueOf(max),
                !StringUtils.validString(positionId) ? null
                        : Long.valueOf(positionId),
                !StringUtils.validString(goodsId) ? null
                        : Long.valueOf(goodsId),
                !StringUtils.validString(custId) ? null
                        : Long.valueOf(custId));
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id) ? null : Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getPositionId().toString();
    }

    @Override
    public long getChangedTime() {
        return PosProductDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        PosProductDTO.changedTime = changedTime;
    }
}
