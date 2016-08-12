/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logstic.wms.webservice.dto;

import com.viettel.logistic.wms.dto.BillStock;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TruongBx3
 */
@XmlRootElement(name = "DataIEBccs")
public class DataIEBccs {

    String accountBCCS;
    String passBCCS;
    BillStock billStock;
    
    public DataIEBccs(BillStock billStock) {
        this.accountBCCS = "bccs_im";
        this.passBCCS    = "bccs_im";
        this.billStock = billStock;
    }

       
     
    public String getAccountBCCS() {
        return accountBCCS;
    }

    public void setAccountBCCS(String accountBCCS) {
        this.accountBCCS = accountBCCS;
    }

    public String getPassBCCS() {
        return passBCCS;
    }

    public void setPassBCCS(String passBCCS) {
        this.passBCCS = passBCCS;
    }

    public BillStock getBillStock() {
        return billStock;
    }

    public void setBillStock(BillStock billStock) {
        this.billStock = billStock;
    }
  
        

}
