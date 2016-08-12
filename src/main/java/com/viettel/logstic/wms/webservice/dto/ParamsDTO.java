/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logstic.wms.webservice.dto;

import java.util.Date;

/**
 *
 * @author Ngocnd6
 */
public class ParamsDTO {
    private String name;
    private String value;

    public ParamsDTO() {
    }

    public ParamsDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }

    ParamsDTO(String startChangeDate, Date startdate) {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
