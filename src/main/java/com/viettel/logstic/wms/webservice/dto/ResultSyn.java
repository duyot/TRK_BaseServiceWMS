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
public class ResultSyn {
    
    String status;
    String reason;
    String addInfor;
    String synTransCode;

    public ResultSyn(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public ResultSyn(String status, String reason, String addInfor, String synTransCode) {
        this.status = status;
        this.reason = reason;
        this.addInfor = addInfor;
        this.synTransCode = synTransCode;
    }
    
    

    public ResultSyn() {
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

    public String getAddInfor() {
        return addInfor;
    }

    public void setAddInfor(String addInfor) {
        this.addInfor = addInfor;
    }

    public String getSynTransCode() {
        return synTransCode;
    }

    public void setSynTransCode(String synTransCode) {
        this.synTransCode = synTransCode;
    }
    
    
    
}
