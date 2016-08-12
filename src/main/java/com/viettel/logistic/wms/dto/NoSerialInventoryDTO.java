/**
 * @(#)NoSerialInventoryForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.logistic.wms.model.NoSerialInventory;
import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 5/10/2016 4:47 PM
 */
@XmlRootElement(name = "NoSerialInventory")
public class NoSerialInventoryDTO extends BaseFWDTO<NoSerialInventory> {

    //Fields
    private String id;
    private String custId;
    private String custCode;
    private String custName;
    private String stockId;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private String quantity;
    private String cellCode;
    private String barcode;
    private String createDate;
    private String note;
    private String status;
    private String staffId;
    private String staffCode;
    private String staffName;
    private String inventoryCode;
    private String goodsUnitTypeName;
    private static long changedTime = 0;

    //Constructor
    public NoSerialInventoryDTO() {
        setDefaultSortField("id");
    }

    public NoSerialInventoryDTO(String id, String custId, String custCode, String custName, String stockId, String goodsId, String goodsCode, String goodsName, String goodsState, String quantity, String cellCode, String barcode, String createDate, String note, String status, String staffId, String staffCode, String staffName, String inventoryCode, String goodsUnitTypeName) {
        this.id = id;
        this.custId = custId;
        this.custCode = custCode;
        this.custName = custName;
        this.stockId = stockId;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsState = goodsState;
        this.quantity = quantity;
        this.cellCode = cellCode;
        this.barcode = barcode;
        this.createDate = createDate;
        this.note = note;
        this.status = status;
        this.staffId = staffId;
        this.staffCode = staffCode;
        this.staffName = staffName;
        this.inventoryCode = inventoryCode;
        this.goodsUnitTypeName = goodsUnitTypeName;
    }
    //Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
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

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
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

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    @Override
    public NoSerialInventory toModel() {
        try {
            NoSerialInventory model = new NoSerialInventory(
                    !StringUtils.validString(id) ? null
                            : Long.valueOf(id),
                    !StringUtils.validString(custId) ? null
                            : Long.valueOf(custId),
                    custCode,
                    custName,
                    !StringUtils.validString(stockId) ? null
                            : Long.valueOf(stockId),
                    !StringUtils.validString(goodsId) ? null
                            : Long.valueOf(goodsId),
                    goodsCode,
                    goodsName,
                    goodsState,
                    !StringUtils.validString(quantity) ? null
                            : Double.parseDouble(quantity),
                    cellCode,
                    barcode,
                    !StringUtils.validString(createDate) ? null
                            : DateTimeUtils.convertStringToDate(createDate),
                    note,
                    !StringUtils.validString(status) ? null
                            : Long.valueOf(status),
                    !StringUtils.validString(staffId) ? null
                            : Long.valueOf(staffId),
                    staffCode,
                    staffName,
                    inventoryCode,
                    goodsUnitTypeName);
            return model;
        } catch (Exception e) {
            return null;
        }
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
        return NoSerialInventoryDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        NoSerialInventoryDTO.changedTime = changedTime;
    }

    public String getGoodsUnitTypeName() {
        return goodsUnitTypeName;
    }

    public void setGoodsUnitTypeName(String goodsUnitTypeName) {
        this.goodsUnitTypeName = goodsUnitTypeName;
    }

}
