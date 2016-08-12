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
public class InputWSBccs {

    String username;
    String password;
    String wscode;
    String rawData;

    public InputWSBccs(String wscode, String rawData) {
        this.wscode = wscode;
        this.rawData = rawData;
        this.username = "8434ef9312bc1486";
        this.password = "8434ef9312bc1486";
    }

    public InputWSBccs() {
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
    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

}
