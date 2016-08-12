/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.dto;

/**
 *
 * @author quyendm
 */
public class StockTransGoodsDTO {

    private String goodsName;
    private String goodsCode;
    private String goodsUnitType;
    private String goodsUnitTypeName;
    private Double amountReal;
    private String stockTransType;
    private String stockTransStatus;
    private String orderCode;
    private String orderActionCode;
    private String stockTransCode;
    private String synTransCode;
    private String realStockTransDate;
    private String stockTransDate;
    private String transUserName;
    private String notes;
    private String orderId;
    private String stockId;
    private String stockCode;
    private String receiveName;

    public StockTransGoodsDTO(String goodsName, String goodsCode, String goodsUnitType, String goodsUnitTypeName, Double amountReal, String stockTransType, String stockTransStatus, String orderCode, String orderActionCode, String stockTransCode, String synTransCode, String realStockTransDate, String stockTransDate, String transUserName, String notes, String orderId, String stockId, String stockCode, String receiveName) {
        this.goodsName = goodsName;
        this.goodsCode = goodsCode;
        this.goodsUnitType = goodsUnitType;
        this.goodsUnitTypeName = goodsUnitTypeName;
        this.amountReal = amountReal;
        this.stockTransType = stockTransType;
        this.stockTransStatus = stockTransStatus;
        this.orderCode = orderCode;
        this.orderActionCode = orderActionCode;
        this.stockTransCode = stockTransCode;
        this.synTransCode = synTransCode;
        this.realStockTransDate = realStockTransDate;
        this.stockTransDate = stockTransDate;
        this.transUserName = transUserName;
        this.notes = notes;
        this.orderId = orderId;
        this.stockId = stockId;
        this.stockCode = stockCode;
        this.receiveName = receiveName;
    }

    public StockTransGoodsDTO() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsUnitType() {
        return goodsUnitType;
    }

    public void setGoodsUnitType(String goodsUnitType) {
        this.goodsUnitType = goodsUnitType;
    }

    public String getGoodsUnitTypeName() {
        return goodsUnitTypeName;
    }

    public void setGoodsUnitTypeName(String goodsUnitTypeName) {
        this.goodsUnitTypeName = goodsUnitTypeName;
    }

    public Double getAmountReal() {
        return amountReal;
    }

    public void setAmountReal(Double amountReal) {
        this.amountReal = amountReal;
    }

    public String getStockTransType() {
        return stockTransType;
    }

    public void setStockTransType(String stockTransType) {
        this.stockTransType = stockTransType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderActionCode() {
        return orderActionCode;
    }

    public void setOrderActionCode(String orderActionCode) {
        this.orderActionCode = orderActionCode;
    }

    public String getStockTransCode() {
        return stockTransCode;
    }

    public void setStockTransCode(String stockTransCode) {
        this.stockTransCode = stockTransCode;
    }

    public String getSynTransCode() {
        return synTransCode;
    }

    public void setSynTransCode(String synTransCode) {
        this.synTransCode = synTransCode;
    }

    public String getRealStockTransDate() {
        return realStockTransDate;
    }

    public void setRealStockTransDate(String realStockTransDate) {
        this.realStockTransDate = realStockTransDate;
    }

    public String getStockTransDate() {
        return stockTransDate;
    }

    public void setStockTransDate(String stockTransDate) {
        this.stockTransDate = stockTransDate;
    }

    public String getTransUserName() {
        return transUserName;
    }

    public void setTransUserName(String transUserName) {
        this.transUserName = transUserName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getStockTransStatus() {
        return stockTransStatus;
    }

    public void setStockTransStatus(String stockTransStatus) {
        this.stockTransStatus = stockTransStatus;
    }
}
