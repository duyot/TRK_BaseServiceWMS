/**
 * @(#)GoodsHandoverSerialForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.GoodsHandoverSerial;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:41 PM
 */
@XmlRootElement(name = "GoodsHandoverSerial")
public class GoodsHandoverSerialDTO extends BaseFWDTO<GoodsHandoverSerial> {

    //Fields
    private String id;
    private String cellCode;
    private String goodsHandoverId;
    private String goodsHandoverIdName;
    private String quantity;
    private String barcode;
    private String fromSerial;
    private String toSerial;
    private String note;
    private static long changedTime = 0;

    //Constructor
    public GoodsHandoverSerialDTO() {
        setDefaultSortField("id");
    }

    public GoodsHandoverSerialDTO(String goodsHandoverId) {
        this.goodsHandoverId = goodsHandoverId;
    }

    public GoodsHandoverSerialDTO(String id, String cellCode, String goodsHandoverId, String quantity, String barcode, String fromSerial, String toSerial, String note) {
        this.id = id;
        this.cellCode = cellCode;
        this.goodsHandoverId = goodsHandoverId;
        this.quantity = quantity;
        this.barcode = barcode;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.note = note;
    }
    //Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public String getCellCode() {
        return cellCode;
    }

    public void setGoodsHandoverId(String goodsHandoverId) {
        this.goodsHandoverId = goodsHandoverId;
    }

    public String getGoodsHandoverId() {
        return goodsHandoverId;
    }

    public void setGoodsHandoverIdName(String goodsHandoverIdName) {
        this.goodsHandoverIdName = goodsHandoverIdName;
    }

    public String getGoodsHandoverIdName() {
        return goodsHandoverIdName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    @Override
    public GoodsHandoverSerial toModel() {
        GoodsHandoverSerial model = new GoodsHandoverSerial(
                !StringUtils.validString(id) ? null
                        : Long.valueOf(id),
                cellCode,
                !StringUtils.validString(goodsHandoverId) ? null
                        : Long.valueOf(goodsHandoverId),
                !StringUtils.validString(quantity) ? null
                        : Long.valueOf(quantity),
                barcode,
                fromSerial,
                toSerial,
                note);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id) ? null : Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getId().toString();
    }

    @Override
    public long getChangedTime() {
        return GoodsHandoverSerialDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        GoodsHandoverSerialDTO.changedTime = changedTime;
    }
}
