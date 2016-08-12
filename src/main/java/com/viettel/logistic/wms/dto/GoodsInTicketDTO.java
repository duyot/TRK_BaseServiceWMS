/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 *
 * @author Duyot
 */
public class GoodsInTicketDTO extends StockTransDetailDTO {
    //thong tin them
    //
    //list Serial
    String goodsUnitName;
    @XStreamImplicit(itemFieldName = "lstSerial")
    List<StockTransSerialDTO> lstSerial;
    @XStreamImplicit(itemFieldName = "lstOrderGoodsDetailSerialDTO")
    List<StockTransSerialDTO> lstOrderGoodsDetailSerialDTO;

    public GoodsInTicketDTO() {
    }

    public GoodsInTicketDTO(String stockTransDetailId, String stockTransId, String stockTransDate, String goodsId, String goodsCode, String goodsName, String goodsState, String goodsUnitType, String goodsUnitTypeName, String goodsIsSerial, String goodsIsSerialStrip, String amountOrder, String amountReal, String bincode, String barcode, String cellCode, String notes, String createDatetime, String addInfor) {
        super(stockTransDetailId, stockTransId, stockTransDate, goodsId, goodsCode, goodsName, goodsState, goodsUnitType, goodsUnitTypeName, goodsIsSerial, goodsIsSerialStrip, amountOrder, amountReal, bincode, barcode, cellCode, notes, createDatetime, addInfor);
    }
    
    

    public GoodsInTicketDTO(List<StockTransSerialDTO> lstSerial) {
        this.lstSerial = lstSerial;
    }

    public List<StockTransSerialDTO> getLstSerial() {
        return lstSerial;
    }

    public void setLstSerial(List<StockTransSerialDTO> lstSerial) {
        this.lstSerial = lstSerial;
    }

    public String getGoodsUnitName() {
        return goodsUnitName;
    }

    public void setGoodsUnitName(String goodsUnitName) {
        this.goodsUnitName = goodsUnitName;
    }
    
    

}
