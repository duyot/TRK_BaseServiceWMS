/**
 * @(#)InventoryResultBO.java 6/9/2015 11:26 PM, Copyright 2011 Viettel Telecom.
 * All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.InventoryResultDTO;

/**
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:26 PM
 */
@Entity
@Table(name = "INVENTORY_RESULT")
public class InventoryResult extends BaseFWModel {

    //Fields
    private Long inventoryResultId;
    private Long inventoryActionId;
    private String goodsCode;
    private String goodsName;
    private Long goodsId;
    private Long unitId;
    private String unitName;
    private Long cellId;
    private String cellCode;
    private String barcode;
    private String fromSerial;
    private String toSerial;
    private Double amountInventory;
    private Double amount;
    private String note;
    private Double amountFalse;
    private String type;

    //Constructors
    public InventoryResult() {
        setColId("inventoryResultId");
        setColName("inventoryResultId");
        setUniqueColumn(new String[]{"inventoryResultId"});
    }

    public InventoryResult(Long inventoryResultId) {
        this.inventoryResultId = inventoryResultId;
    }

    public InventoryResult(Long inventoryResultId, Long inventoryActionId, String goodsCode, String goodsName, Long goodsId, Long unitId, String unitName, Long cellId, String cellCode, String barcode, String fromSerial, String toSerial, Double amountInventory, Double amount, String note, Double amountFalse, String type) {
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

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "INVENTORY_RESULT_SEQ")
            }
    )
    @Column(name = "INVENTORY_RESULT_ID", unique = true, nullable = false)
    public Long getInventoryResultId() {
        return this.inventoryResultId;
    }

    public void setInventoryResultId(final Long inventoryResultId) {
        this.inventoryResultId = inventoryResultId;
    }

    @Column(name = "INVENTORY_ACTION_ID", nullable = false, columnDefinition = "InventoryAction")
    public Long getInventoryActionId() {
        return this.inventoryActionId;
    }

    public void setInventoryActionId(final Long inventoryActionId) {
        this.inventoryActionId = inventoryActionId;
    }

    @Column(name = "GOODS_CODE")
    public String getGoodsCode() {
        return this.goodsCode;
    }

    public void setGoodsCode(final String goodsCode) {
        this.goodsCode = goodsCode;
    }

    @Column(name = "GOODS_NAME")
    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(final String goodsName) {
        this.goodsName = goodsName;
    }

    @Column(name = "GOODS_ID")
    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(final Long goodsId) {
        this.goodsId = goodsId;
    }

    @Column(name = "UNIT_ID")
    public Long getUnitId() {
        return this.unitId;
    }

    public void setUnitId(final Long unitId) {
        this.unitId = unitId;
    }

    @Column(name = "UNIT_NAME")
    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(final String unitName) {
        this.unitName = unitName;
    }

    @Column(name = "CELL_ID")
    public Long getCellId() {
        return this.cellId;
    }

    public void setCellId(final Long cellId) {
        this.cellId = cellId;
    }

    @Column(name = "CELL_CODE")
    public String getCellCode() {
        return this.cellCode;
    }

    public void setCellCode(final String cellCode) {
        this.cellCode = cellCode;
    }

    @Column(name = "BARCODE")
    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(final String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "FROM_SERIAL")
    public String getFromSerial() {
        return this.fromSerial;
    }

    public void setFromSerial(final String fromSerial) {
        this.fromSerial = fromSerial;
    }

    @Column(name = "TO_SERIAL")
    public String getToSerial() {
        return this.toSerial;
    }

    public void setToSerial(final String toSerial) {
        this.toSerial = toSerial;
    }

    @Column(name = "AMOUNT_INVENTORY")
    public Double getAmountInventory() {
        return this.amountInventory;
    }

    public void setAmountInventory(final Double amountInventory) {
        this.amountInventory = amountInventory;
    }

    @Column(name = "AMOUNT")
    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    @Column(name = "NOTE")
    public String getNote() {
        return this.note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Column(name = "AMOUNT_FALSE")
    public Double getAmountFalse() {
        return this.amountFalse;
    }

    public void setAmountFalse(final Double amountFalse) {
        this.amountFalse = amountFalse;
    }

    @Column(name = "TYPE")
    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public InventoryResultDTO toDTO() {
        InventoryResultDTO dto = new InventoryResultDTO(
                inventoryResultId == null ? null : inventoryResultId.toString(), inventoryActionId == null ? null : inventoryActionId.toString(), goodsCode, goodsName, goodsId == null ? null : goodsId.toString(), unitId == null ? null : unitId.toString(), unitName, cellId == null ? null : cellId.toString(), cellCode == null ? null : cellCode.toString(), barcode, fromSerial, toSerial, amountInventory == null ? null : amountInventory.toString(), amount == null ? null : amount.toString(), note, amountFalse == null ? null : amountFalse.toString(), type
        );
        return dto;
    }
}
