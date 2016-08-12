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
public class BillStock {

    //1.thong tin giao dich
    String orderCode;//ma YC
    String orderType;//1:yc nhap, 2: yc xuat
    String stockCode;//kho nhap
    String transCode;//sma phieu nhap
    String ieExpectDate;//loai nhap: 1. theo yc 2.lenh
    //new fields
    String inputType;
    String outputType;
    String stockTransDate;
    //duyot: cap nhat thong tin nguoi thuc nhap sang ktts
    String realIEUserName;
    
    //2.thong tin hang hoa
    /*
     note:
     -   thong tin hang hoa: Object chua danh sach serial cua hang hoa do lstSerialDetail
     -   lstSerialDetail =null -> hang hoa khong theo dai serial
     */
    @XStreamImplicit(itemFieldName = "lstGoods")
    List<GoodsInTicketDTO> lstGoods;
    //-------CONSTRUCTOR--------

    public BillStock() {
    }

    public BillStock(String orderCode, String orderType, String stockCode, String transCode, String ieExpectDate, String inputType, String outputType, List<GoodsInTicketDTO> lstGoods) {
        this.orderCode = orderCode;
        this.orderType = orderType;
        this.stockCode = stockCode;
        this.transCode = transCode;
        this.ieExpectDate = ieExpectDate;
        this.inputType = inputType;
        this.outputType = outputType;
        this.lstGoods = lstGoods;
    }
    
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getIeExpectDate() {
        return ieExpectDate;
    }

    public void setIeExpectDate(String ieExpectDate) {
        this.ieExpectDate = ieExpectDate;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public List<GoodsInTicketDTO> getLstGoods() {
        return lstGoods;
    }

    public void setLstGoods(List<GoodsInTicketDTO> lstGoods) {
        this.lstGoods = lstGoods;
    }

    public String getStockTransDate() {
        return stockTransDate;
    }

    public void setStockTransDate(String stockTransDate) {
        this.stockTransDate = stockTransDate;
    }

    public String getRealIEUserName() {
        return realIEUserName;
    }

    public void setRealIEUserName(String realIEUserName) {
        this.realIEUserName = realIEUserName;
    }
    
    
    
    
    
    

}
