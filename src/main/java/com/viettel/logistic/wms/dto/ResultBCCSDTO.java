/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.dto;

/**
 *
 * @author Duyot
 */
public class ResultBCCSDTO {
    String responseCode;
    String description;
    String original;

    public ResultBCCSDTO() {
    }
    public ResultBCCSDTO(String responseCode, String description, String original) {
        this.responseCode = responseCode;
        this.description = description;
        this.original = original;
    }
    
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    
    
    
}
