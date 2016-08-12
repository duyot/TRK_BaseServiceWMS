/**
 * @(#)GoodsHandoverForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.GoodsHandover;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:39 PM
 */
@XmlRootElement(name = "GoodsHandover")
public class GoodsHandoverDTO extends BaseFWDTO<GoodsHandover> {

    //Fields
    private String goodsHandoverId;
    private String goodsCode;
    private String goodsName;
    private String quantityProposed;
    private String quantityReal;
    private String quantityFluctuation;
    private String quantityRemain;
    private String unitType;
    private String status;
    private String requestId;
    private String note;
    private String type;
    private static long changedTime = 0;
    private List<GoodsHandoverSerialDTO> lstGoodsHandoverSerialDTOs = new ArrayList<>();

    //Constructor
    public GoodsHandoverDTO() {
        setDefaultSortField("goodsHandoverId");
    }

    public GoodsHandoverDTO(String requestId, String type) {
        this.requestId = requestId;
        this.type = type;
    }

    public GoodsHandoverDTO(String goodsHandoverId, String goodsCode, String goodsName, String quantityProposed, String quantityReal, String quantityFluctuation, String quantityRemain, String unitType, String status, String requestId, String note, String type) {
        this.goodsHandoverId = goodsHandoverId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.quantityProposed = quantityProposed;
        this.quantityReal = quantityReal;
        this.quantityFluctuation = quantityFluctuation;
        this.quantityRemain = quantityRemain;
        this.unitType = unitType;
        this.status = status;
        this.requestId = requestId;
        this.note = note;
        this.type = type;
    }

    //Getters and setters
    public void setGoodsHandoverId(String goodsHandoverId) {
        this.goodsHandoverId = goodsHandoverId;
    }

    public String getGoodsHandoverId() {
        return goodsHandoverId;
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

    public void setQuantityProposed(String quantityProposed) {
        this.quantityProposed = quantityProposed;
    }

    public String getQuantityProposed() {
        return quantityProposed;
    }

    public void setQuantityReal(String quantityReal) {
        this.quantityReal = quantityReal;
    }

    public String getQuantityReal() {
        return quantityReal;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getQuantityRemain() {
        return quantityRemain;
    }

    public void setQuantityRemain(String quantityRemain) {
        this.quantityRemain = quantityRemain;
    }

    @Override
    public GoodsHandover toModel() {
        GoodsHandover model = new GoodsHandover(
                !StringUtils.validString(goodsHandoverId) ? null
                        : Long.valueOf(goodsHandoverId),
                goodsCode,
                goodsName,
                !StringUtils.validString(quantityProposed) ? null
                        : Double.parseDouble(quantityProposed),
                !StringUtils.validString(quantityReal) ? null
                        : Double.parseDouble(quantityReal),
                !StringUtils.validString(quantityFluctuation) ? null
                        : Double.parseDouble(quantityFluctuation),
                !StringUtils.validString(quantityRemain) ? null
                        : Double.parseDouble(quantityRemain),
                unitType,
                status,
                !StringUtils.validString(requestId) ? null
                        : Long.valueOf(requestId),
                note,
                type);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(goodsHandoverId) ? null : Long.valueOf(goodsHandoverId);
    }

    @Override
    public String catchName() {
        return getGoodsHandoverId();
    }

    @Override
    public long getChangedTime() {
        return GoodsHandoverDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        GoodsHandoverDTO.changedTime = changedTime;
    }

    public List<GoodsHandoverSerialDTO> getLstGoodsHandoverSerialDTOs() {
        return lstGoodsHandoverSerialDTOs;
    }

    public void setLstGoodsHandoverSerialDTOs(List<GoodsHandoverSerialDTO> lstGoodsHandoverSerialDTOs) {
        this.lstGoodsHandoverSerialDTOs = lstGoodsHandoverSerialDTOs;
    }

    public String getQuantityFluctuation() {
        return quantityFluctuation;
    }

    public void setQuantityFluctuation(String quantityFluctuation) {
        this.quantityFluctuation = quantityFluctuation;
    }

}
