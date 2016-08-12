/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logstic.wms.webservice.dto;

/**
 *
 * @author TruongBx3
 */
public class BillStockBccs {
    String errorCode;
    String orderAction;
    String orderCode;
    String status;
    String errorMessage;

    public BillStockBccs() {
    }

    
    public BillStockBccs(String errorCode, String orderAction, String orderCode, String status, String errorMessage) {
        this.errorCode = errorCode;
        this.orderAction = orderAction;
        this.orderCode = orderCode;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}
