/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.dto;

/**
 *
 * @author TruongBx3
 */
public class ResultBCCS {

    String orderCode;
    String orderType;
    String status;
    String reason;

    public ResultBCCS() {
    }

    public ResultBCCS(String orderCode, String orderType, String status, String reason) {
        this.orderCode = orderCode;
        this.orderType = orderType;
        this.status = status;
        this.reason = reason;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
