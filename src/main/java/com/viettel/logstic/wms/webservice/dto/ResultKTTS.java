/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logstic.wms.webservice.dto;

import java.util.List;

/**
 *
 * @author Thienng1
 */
public class ResultKTTS {

    String orderCode;
    String orderType;
    String status;
    String reason;
    private List<ChangeGoods> lstChangeGoods;

    public ResultKTTS() {
    }

    public List<ChangeGoods> getLstChangeGoods() {
        return lstChangeGoods;
    }

    public void setLstChangeGoods(List<ChangeGoods> lstChangeGoods) {
        this.lstChangeGoods = lstChangeGoods;
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
