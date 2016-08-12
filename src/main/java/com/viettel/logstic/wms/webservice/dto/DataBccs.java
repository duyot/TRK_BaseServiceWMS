/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logstic.wms.webservice.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TruongBx3
 */
@XmlRootElement(name = "DataBccs")
public class DataBccs {

    String accountBCCS;
    String passBCCS;
    @XStreamImplicit(itemFieldName = "lstBillStock")
    List<OrdersDTO> lstBillStock;

    public DataBccs(List<OrdersDTO> lstBillStock) {
        this.lstBillStock = lstBillStock;
        this.accountBCCS = "bccs_im";
        this.passBCCS = "bccs_im";
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

    public List<OrdersDTO> getLstBillStock() {
        return lstBillStock;
    }

    public void setLstBillStock(List<OrdersDTO> lstBillStock) {
        this.lstBillStock = lstBillStock;
    }

}
