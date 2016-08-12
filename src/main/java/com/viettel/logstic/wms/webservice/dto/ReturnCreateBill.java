/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logstic.wms.webservice.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TruongBx3
 */
public class ReturnCreateBill {
    
    String responseCode;
    List<BillStockBccs> lstBillStockResponse ;
    //duyot: them truong synTransCode
    String synTransCode;

    public ReturnCreateBill(String responseCode, List<BillStockBccs> lstBillStockResponse) {
        this.responseCode = responseCode;
        this.lstBillStockResponse = lstBillStockResponse;
    }

    
    
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<BillStockBccs> getLstBillStockResponse() {
        return lstBillStockResponse;
    }

    public void setLstBillStockResponse(List<BillStockBccs> lstBillStockResponse) {
        this.lstBillStockResponse = lstBillStockResponse;
    }

    public ReturnCreateBill(String responseCode, List<BillStockBccs> lstBillStockResponse, String synTransCode) {
        this.responseCode = responseCode;
        this.lstBillStockResponse = lstBillStockResponse;
        this.synTransCode = synTransCode;
    }
    
    

    public String getSynTransCode() {
        return synTransCode;
    }

    public void setSynTransCode(String synTransCode) {
        this.synTransCode = synTransCode;
    }

    
 
    
}
