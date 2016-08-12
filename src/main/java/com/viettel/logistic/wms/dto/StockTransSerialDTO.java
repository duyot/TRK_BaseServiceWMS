/**
 * @(#)StockTransSerialForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import com.viettel.logistic.wms.model.StockTransSerial;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 10:38 PM
 */
@XmlRootElement(name = "StockTransSerial")
public class StockTransSerialDTO extends BaseFWDTO<StockTransSerial> {

    //Fields
    private String stockTransSerialId;
    private String stockTransId;
    private String stockTransDetailId;
    private String stockTransDate;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private String goodsUnitType;
    private String goodsUnitTypeName;
    private String fromSerial;
    private String toSerial;
    private String amountOrder;
    private String amountReal;
    private String bincode;
    private String barcode;
    private String cellCode;
    private String notes;
    private String createDatetime;
    private String addInfor;
    private static long changedTime = 0;
    //thienng1
    private String changeType;
    private String tmpChangeStockGoodsFromSerial;
    private String tmpChangeStockGoodsToSerial;
    //
    private String tmpStockTransDetailId;
  
    //
    private String fromDateSearch;
    private String toDateSearch;
    //
    private String quantity;
    

    public StockTransSerialDTO() {
        setDefaultSortField("stockTransDate");
    }

    public StockTransSerialDTO(String stockTransSerialId, String stockTransId, String stockTransDetailId,
            String stockTransDate, String goodsId, String goodsCode, String goodsName, String goodsState,
            String goodsUnitType, String goodsUnitTypeName, String fromSerial, String toSerial, String amountOrder,
            String amountReal, String bincode, String barcode, String cellCode, String notes, String createDatetime, String addInfor
           
    ) {
        this.stockTransSerialId = stockTransSerialId;
        this.stockTransId = stockTransId;
        this.stockTransDetailId = stockTransDetailId;
        this.stockTransDate = stockTransDate;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsState = goodsState;
        this.goodsUnitType = goodsUnitType;
        this.goodsUnitTypeName = goodsUnitTypeName;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.amountOrder = amountOrder;
        this.amountReal = amountReal;
        this.bincode = bincode;
        this.barcode = barcode;
        this.cellCode = cellCode;
        this.notes = notes;
        this.createDatetime = createDatetime;
        this.addInfor = addInfor;
        
    }

    //duyot: xoa bo nhung turong k can thiet de lai from-to serial
    public void format() {
        this.stockTransSerialId = null;
        this.stockTransId = null;
        this.stockTransDetailId = null;
        this.stockTransDate = null;
        this.goodsId = null;
        this.goodsCode = null;
        this.goodsName = null;
        this.goodsState = null;
        this.goodsUnitType = null;
        this.goodsUnitTypeName = null;
        this.amountOrder = null;
        this.amountReal = null;
        this.bincode = null;
        this.barcode = null;
        this.cellCode = null;
        this.notes = null;
        this.createDatetime = null;
        this.addInfor = null;
        this.setDefaultSortField(null);
        this.setTmpStockTransDetailId(null);
       
    }

    //Getters and setters
    public void setStockTransSerialId(String stockTransSerialId) {
        this.stockTransSerialId = stockTransSerialId;
    }

    public String getStockTransSerialId() {
        return stockTransSerialId;
    }

    public void setStockTransId(String stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getStockTransId() {
        return stockTransId;
    }

    public void setStockTransDetailId(String stockTransDetailId) {
        this.stockTransDetailId = stockTransDetailId;
    }

    public String getStockTransDetailId() {
        return stockTransDetailId;
    }

    public void setStockTransDate(String stockTransDate) {
        this.stockTransDate = stockTransDate;
    }

    public String getStockTransDate() {
        return stockTransDate;
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

    public void setGoodsUnitType(String goodsUnitType) {
        this.goodsUnitType = goodsUnitType;
    }

    public String getGoodsUnitType() {
        return goodsUnitType;
    }

    public void setGoodsUnitTypeName(String goodsUnitTypeName) {
        this.goodsUnitTypeName = goodsUnitTypeName;
    }

    public String getGoodsUnitTypeName() {
        return goodsUnitTypeName;
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

    public void setAmountOrder(String amountOrder) {
        this.amountOrder = amountOrder;
    }

    public String getAmountOrder() {
        return amountOrder;
    }

    public void setAmountReal(String amountReal) {
        this.amountReal = amountReal;
    }

    public String getAmountReal() {
        return amountReal;
    }

    public void setBincode(String bincode) {
        this.bincode = bincode;
    }

    public String getBincode() {
        return bincode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getCellCode() {
        return cellCode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setAddInfor(String addInfor) {
        this.addInfor = addInfor;
    }

    public String getAddInfor() {
        return addInfor;
    }

    

    @Override
    public StockTransSerial toModel() {
        try {
            StockTransSerial model = new StockTransSerial(
                    !StringUtils.validString(stockTransSerialId) ? null
                            : Long.valueOf(stockTransSerialId),
                    !StringUtils.validString(stockTransId) ? null
                            : Long.valueOf(stockTransId),
                    !StringUtils.validString(stockTransDetailId) ? null
                            : Long.valueOf(stockTransDetailId),
                    !StringUtils.validString(stockTransDate) ? null
                            : DateTimeUtils.convertStringToDate(stockTransDate),
                    !StringUtils.validString(goodsId) ? null
                            : Long.valueOf(goodsId),
                    goodsCode,
                    goodsName,
                    goodsState,
                    goodsUnitType,
                    goodsUnitTypeName,
                    fromSerial,
                    toSerial,
                    !StringUtils.validString(amountOrder) ? null
                            : Long.valueOf(amountOrder),
                    !StringUtils.validString(amountReal) ? null
                            : Long.valueOf(amountReal),
                    bincode,
                    barcode, cellCode,
                    notes,
                    !StringUtils.validString(createDatetime) ? null
                            : DateTimeUtils.convertStringToDate(createDatetime),
                    addInfor);
            return model;
        } catch (Exception e) {
            System.out.println("DUYOT: Tomodal null"+ e.toString());
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(stockTransSerialId) ? null : Long.valueOf(stockTransSerialId);
    }

    @Override
    public String catchName() {
        return getStockTransDate().toString();
    }

    @Override
    public long getChangedTime() {
        return StockTransSerialDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        StockTransSerialDTO.changedTime = changedTime;
    }

    public String getTmpStockTransDetailId() {
        return tmpStockTransDetailId;
    }

    public void setTmpStockTransDetailId(String tmpStockTransDetailId) {
        this.tmpStockTransDetailId = tmpStockTransDetailId;
    }

    public String getFromDateSearch() {
        return fromDateSearch;
    }

    public void setFromDateSearch(String fromDateSearch) {
        this.fromDateSearch = fromDateSearch;
    }

    public String getToDateSearch() {
        return toDateSearch;
    }

    public void setToDateSearch(String toDateSearch) {
        this.toDateSearch = toDateSearch;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getTmpChangeStockGoodsFromSerial() {
        return tmpChangeStockGoodsFromSerial;
    }

    public void setTmpChangeStockGoodsFromSerial(String tmpChangeStockGoodsFromSerial) {
        this.tmpChangeStockGoodsFromSerial = tmpChangeStockGoodsFromSerial;
    }

    public String getTmpChangeStockGoodsToSerial() {
        return tmpChangeStockGoodsToSerial;
    }

    public void setTmpChangeStockGoodsToSerial(String tmpChangeStockGoodsToSerial) {
        this.tmpChangeStockGoodsToSerial = tmpChangeStockGoodsToSerial;
    }

}
