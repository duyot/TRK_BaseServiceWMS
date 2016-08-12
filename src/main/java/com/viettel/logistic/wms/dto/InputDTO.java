/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.viettel.logstic.wms.webservice.dto.ParamsDTO;
import java.util.List;

/**
 *
 * @author Ngocnd6
 */
public class InputDTO {

    private String username;
    private String password;
    private String wscode;

    private String rawData;
    @XStreamImplicit(itemFieldName = "param")
    private List<ParamsDTO> param;

    public InputDTO() {
    }

    public InputDTO(String username, String password, String wscode, List<ParamsDTO> param, String rawdata) {
        this.username = username;
        this.password = password;
        this.wscode = wscode;
        this.param = param;
        this.rawData = rawdata;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWscode() {
        return wscode;
    }

    public void setWscode(String wscode) {
        this.wscode = wscode;
    }

    public List<ParamsDTO> getParams() {
        return param;
    }

    public void setParams(List<ParamsDTO> param) {
        this.param = param;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public List<ParamsDTO> getParam() {
        return param;
    }

    public void setParam(List<ParamsDTO> param) {
        this.param = param;
    }
    
    

}
