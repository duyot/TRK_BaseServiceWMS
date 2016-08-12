/**
 * @(#)InventoryResultForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.InventoryResult;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:26 PM
 */
@XmlRootElement(name = "InventoryResult")
public class InventoryResultDTO extends BaseFWDTO<InventoryResult> {

    //Fields

    private String inventoryResultId;
    private String inventoryActionId;
    private String inventoryActionIdName;
    private String goodsCode;
    private String goodsName;
    private String goodsId;
    private String unitId;
    private String unitName;
    private String cellId;
    private String cellCode;
    private String barcode;
    private String fromSerial;
    private String toSerial;
    private String amountInventory;
    private String amount;
    private String note;
    private String amountFalse;
    private String type;
    private static long changedTime = 0;

    //Constructor

    public InventoryResultDTO() {
        setDefaultSortField("inventoryResultId");
    }

    public InventoryResultDTO(String inventoryResultId, String inventoryActionId, String goodsCode, String goodsName, String goodsId, String unitId, String unitName, String cellId, String cellCode, String barcode, String fromSerial, String toSerial, String amountInventory, String amount, String note, String amountFalse, String type) {
        this.inventoryResultId = inventoryResultId;
        this.inventoryActionId = inventoryActionId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsId = goodsId;
        this.unitId = unitId;
        this.unitName = unitName;
        this.cellId = cellId;
        this.cellCode = cellCode;
        this.barcode = barcode;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.amountInventory = amountInventory;
        this.amount = amount;
        this.note = note;
        this.amountFalse = amountFalse;
        this.type = type;
    }
	//Getters and setters

    public void setInventoryResultId(String inventoryResultId) {
        this.inventoryResultId = inventoryResultId;
    }

    public String getInventoryResultId() {
        return inventoryResultId;
    }

    public void setInventoryActionId(String inventoryActionId) {
        this.inventoryActionId = inventoryActionId;
    }

    public String getInventoryActionId() {
        return inventoryActionId;
    }

    public void setInventoryActionIdName(String inventoryActionIdName) {
        this.inventoryActionIdName = inventoryActionIdName;
    }

    public String getInventoryActionIdName() {
        return inventoryActionIdName;
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

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public String getCellCode() {
        return cellCode;
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

    public void setAmountInventory(String amountInventory) {
        this.amountInventory = amountInventory;
    }

    public String getAmountInventory() {
        return amountInventory;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setAmountFalse(String amountFalse) {
        this.amountFalse = amountFalse;
    }

    public String getAmountFalse() {
        return amountFalse;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public InventoryResult toModel() {
        InventoryResult model = new InventoryResult(
                !StringUtils.validString(inventoryResultId) ? null
                        : Long.valueOf(inventoryResultId),
                !StringUtils.validString(inventoryActionId) ? null
                        : Long.valueOf(inventoryActionId),
                goodsCode,
                goodsName,
                !StringUtils.validString(goodsId) ? null
                        : Long.valueOf(goodsId),
                !StringUtils.validString(unitId) ? null
                        : Long.valueOf(unitId),
                unitName,
                !StringUtils.validString(cellId) ? null
                        : Long.valueOf(cellId),
                cellCode,
                barcode,
                fromSerial,
                toSerial,
                !StringUtils.validString(amountInventory) ? null
                        : Double.parseDouble(amountInventory),
                !StringUtils.validString(amount) ? null
                        : Double.parseDouble(amount),
                note,
                !StringUtils.validString(amountFalse) ? null
                        : Double.parseDouble(amountFalse),
                type);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(inventoryResultId) ? null : Long.valueOf(inventoryResultId);
    }

    @Override
    public String catchName() {
        return getInventoryResultId().toString();
    }

    @Override
    public long getChangedTime() {
        return InventoryResultDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        InventoryResultDTO.changedTime = changedTime;
    }
}
