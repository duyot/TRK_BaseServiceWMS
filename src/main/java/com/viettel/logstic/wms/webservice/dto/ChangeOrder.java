/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logstic.wms.webservice.dto;

/**
 *
 * @author Thienng1
 */
public class ChangeOrder {

    private String custId;
    private String custName;
    private String custCode;//ma khach hang
    private String orderCode;//ma yeu cau
    private String stockId;
    private String stockName;
    private String stockCode;//kho thuc hien
    private String expectedDate;//Ngay gui yeu cau
    private String orderUserName;//Nguoi gui yeu cau
    private String description;

    public ChangeOrder() {
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    @Override
    public String toString() {
        return "ChangeOrder{" + "custCode=" + custCode + ", orderCode=" + orderCode + ", stockCode=" + stockCode + ", expectedDate=" + expectedDate + ", orderUserName=" + orderUserName + '}';
    }

}
