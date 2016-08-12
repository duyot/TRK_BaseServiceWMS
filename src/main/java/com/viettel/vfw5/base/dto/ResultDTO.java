/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vfw5.base.dto;

import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vtsoft
 */
@XmlRootElement
public class ResultDTO {

    private String id;
    private String key;
    private String message;
    private int quantity;
    private int quantitySucc;
    private int quantityFail;
    private String fromSerial;
    private String toSerial;
    private Double amount;
    private Double amountIssue;
    private List<StockGoodsSerialInforDTO> lstStockGoodsSerialInforDTO;

    public ResultDTO(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public ResultDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getQuantitySucc() {
        return quantitySucc;
    }

    public void setQuantitySucc(int quantitySucc) {
        this.quantitySucc = quantitySucc;
    }

    public int getQuantityFail() {
        return quantityFail;
    }

    public void setQuantityFail(int quanityFail) {
        this.quantityFail = quanityFail;
    }
    //

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountIssue() {
        return amountIssue;
    }

    public void setAmountIssue(Double amountIssue) {
        this.amountIssue = amountIssue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public List<StockGoodsSerialInforDTO> getLstStockGoodsSerialInforDTO() {
        return lstStockGoodsSerialInforDTO;
    }

    public void setLstStockGoodsSerialInforDTO(List<StockGoodsSerialInforDTO> lstStockGoodsSerialInforDTO) {
        this.lstStockGoodsSerialInforDTO = lstStockGoodsSerialInforDTO;
    }

}
