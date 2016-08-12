/**
 * @(#)StockTransDetailForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.logistic.wms.model.StockTransDetail;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 23-Apr-15 10:52 AM
 */
@XmlRootElement(name = "StockTransDetail")
public class StockTransDetailDTO extends BaseFWDTO<StockTransDetail> {

    //Fields
    private String stockTransDetailId;
    private String stockTransId;
    private String stockTransIdName;
    private String stockTransDate;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private String goodsType;
    private String goodsUnitType;
    private String goodsUnitTypeName;
    private String goodsIsSerial;
    private String goodsIsSerialStrip;
    private String goodsIEStatus;
    private String amountOrder;
    private String amountReal;
    private String bincode;
    private String barcode;
    private String cellCode;
    private String notes;
    private String createDatetime;
    private String addInfor;
    private static long changedTime = 0;
    private String tmpStockTransDetailId;
    private String transfersGoodsCode;
    //Constructor
    List<StockTransSerialDTO> lstStockTransSerialDTO = new ArrayList<>();

    public StockTransDetailDTO() {
        setDefaultSortField("stockTransDate");
    }

    public StockTransDetailDTO(String stockTransDetailId, String stockTransId, String stockTransDate, String goodsId, String goodsCode, String goodsName, String goodsState, String goodsUnitType, String goodsUnitTypeName, String goodsIsSerial, String goodsIsSerialStrip, String amountOrder, String amountReal, String bincode, String barcode, String cellCode, String notes, String createDatetime, String addInfor) {
        this.stockTransDetailId = stockTransDetailId;
        this.stockTransId = stockTransId;
        this.stockTransDate = stockTransDate;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsState = goodsState;
        this.goodsUnitType = goodsUnitType;
        this.goodsUnitTypeName = goodsUnitTypeName;
        this.goodsIsSerial = goodsIsSerial;
        this.goodsIsSerialStrip = goodsIsSerialStrip;
        this.amountOrder = amountOrder;
        this.amountReal = amountReal;
        this.bincode = bincode;
        this.barcode = barcode;
        this.cellCode = cellCode;
        this.notes = notes;
        this.createDatetime = createDatetime;
        this.addInfor = addInfor;
    }
    //Getters and setters

    public void setStockTransDetailId(String stockTransDetailId) {
        this.stockTransDetailId = stockTransDetailId;
    }

    public String getStockTransDetailId() {
        return stockTransDetailId;
    }

    public void setStockTransId(String stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getStockTransId() {
        return stockTransId;
    }

    public void setStockTransIdName(String stockTransIdName) {
        this.stockTransIdName = stockTransIdName;
    }

    public String getStockTransIdName() {
        return stockTransIdName;
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

    public void setGoodsIsSerial(String goodsIsSerial) {
        this.goodsIsSerial = goodsIsSerial;
    }

    public String getGoodsIsSerial() {
        return goodsIsSerial;
    }

    public void setGoodsIsSerialStrip(String goodsIsSerialStrip) {
        this.goodsIsSerialStrip = goodsIsSerialStrip;
    }

    public String getGoodsIsSerialStrip() {
        return goodsIsSerialStrip;
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
    public StockTransDetail toModel() {
        try {
            StockTransDetail model = new StockTransDetail(
                    !StringUtils.validString(stockTransDetailId) ? null
                            : Long.valueOf(stockTransDetailId),
                    !StringUtils.validString(stockTransId) ? null
                            : Long.valueOf(stockTransId),
                    !StringUtils.validString(stockTransDate) ? null
                            : DateTimeUtils.convertStringToDate(stockTransDate),
                    !StringUtils.validString(goodsId) ? null
                            : Long.valueOf(goodsId),
                    goodsCode,
                    goodsName,
                    goodsState,
                    goodsUnitType,
                    goodsUnitTypeName,
                    goodsIsSerial,
                    goodsIsSerialStrip,
                    !StringUtils.validString(amountOrder) ? null
                            : Double.parseDouble(amountOrder),
                    !StringUtils.validString(amountReal) ? null
                            : Double.parseDouble(amountReal),
                    bincode,
                    barcode, cellCode,
                    notes,
                    !StringUtils.validString(createDatetime) ? null
                            : DateTimeUtils.convertStringToDate(createDatetime),
                    addInfor);
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(stockTransDetailId) ? null : Long.valueOf(stockTransDetailId);
    }

    @Override
    public String catchName() {
        return getStockTransDate().toString();
    }

    @Override
    public long getChangedTime() {
        return StockTransDetailDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        StockTransDetailDTO.changedTime = changedTime;
    }

    public String getTmpStockTransDetailId() {
        return tmpStockTransDetailId;
    }

    public void setTmpStockTransDetailId(String tmpStockTransDetailId) {
        this.tmpStockTransDetailId = tmpStockTransDetailId;
    }

    public String getTransfersGoodsCode() {
        return transfersGoodsCode;
    }

    public void setTransfersGoodsCode(String transfersGoodsCode) {
        this.transfersGoodsCode = transfersGoodsCode;
    }

    public List<StockTransSerialDTO> getLstStockTransSerialDTO() {
        return lstStockTransSerialDTO;
    }

    public void setLstStockTransSerialDTO(List<StockTransSerialDTO> lstStockTransSerialDTO) {
        this.lstStockTransSerialDTO = lstStockTransSerialDTO;
    }

    public String getGoodsIEStatus() {
        return goodsIEStatus;
    }

    public void setGoodsIEStatus(String goodsIEStatus) {
        this.goodsIEStatus = goodsIEStatus;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

}
