/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vwf5.base.servicecaller.bccs;

import com.viettel.vwf5.base.servicecaller.WsAttContent;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thiendn1
 */
public class WsRequestCreator implements Serializable {

    private String wsAddress;
    private String targetNameSpace;
    /*
     name of webmethod
     */
    private String serviceName;
    /*
     prepare for usernameToken
     */
    private String username;
    private String password;
    WsAttContent wsBody = new WsAttContent();
    WsAttContent wsHeader = new WsAttContent();
    private String wscode;
    WsRequestCreator wsBccs;
    /*
     /*
     close httClient after get the response
     */
    private boolean closeOnResponse;

    public WsRequestCreator() {

    }

    public WsRequestCreator(String wscode) {
        this.wscode = wscode;
        this.targetNameSpace = "xmlns:web=\"http://webservice.bccsgw.viettel.com/\"";
        this.serviceName = "web:gwOperation";
    }

    public WsRequestCreator(String wsAddress, String targetNameSpace, String serviceName, String username, String password) {
        this.wsAddress = wsAddress;
        this.targetNameSpace = targetNameSpace;
        setServiceName(serviceName);
        this.username = username;
        this.password = password;
    }

    public WsRequestCreator(String wsAddress, String targetNameSpace, String serviceName) {
        this.wsAddress = wsAddress;
        this.targetNameSpace = targetNameSpace;
        setServiceName(serviceName);
    }

    public String getWsAddress() {
        return wsAddress;
    }

    public void setWsAddress(String wsAddress) {
        this.wsAddress = wsAddress;
    }

    public String getTargetNameSpace() {
        return targetNameSpace;
    }

    public void setTargetNameSpace(String targetNameSpace) {
        this.targetNameSpace = targetNameSpace;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
//        if(serviceName.contains(Const.WEB_SERVICE_CONS.SEPERATE_OPERATOR)){
//            serviceName = serviceName.split(Const.WEB_SERVICE_CONS.SEPERATE_OPERATOR)[1];
//        }
        this.serviceName = serviceName;
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

    public Map<String, Object> getBodyArgAlias() {
        return wsBody.getArgAlias();
    }

    public void setBodyArgAlias(Map<String, Object> argAlias) {
        wsBody.setArgAlias(argAlias);
    }

    public List<Object> getBodyParameters() {
        return wsBody.getParameters();
    }

    public void setBodyParameters(List<Object> parameters) {
        wsBody.setParameters(parameters);
    }

    public boolean isCloseOnResponse() {
        return closeOnResponse;
    }

    public void setCloseOnResponse(boolean closeOnResponse) {
        this.closeOnResponse = closeOnResponse;
    }

    public String getBodyParameterTxt() {
        return wsBody.getParameterTxt();
    }

    public void setBodyParameterTxt(String parameterTxt) {
        wsBody.setParameterTxt(parameterTxt);
    }

    public Map<String, Object> getHeaderArgAlias() {
        return wsHeader.getArgAlias();
    }

    public void setHeaderArgAlias(Map<String, Object> argAlias) {
        wsHeader.setArgAlias(argAlias);
    }

    public List<Object> getHeaderParameters() {
        return wsHeader.getParameters();
    }

    public void setHeaderParameters(List<Object> parameters) {
        wsHeader.setParameters(parameters);
    }

    public String getHeaderParameterTxt() {
        return wsHeader.getParameterTxt();
    }

    public void setHeaderParameterTxt(String parameterTxt) {
        wsHeader.setParameterTxt(parameterTxt);
    }

    public String getWscode() {
        return wscode;
    }

    public void setWscode(String wscode) {
        this.wscode = wscode;
    }

    public WsRequestCreator getWsBccs() {
        return wsBccs;
    }

    public void setWsBccs(WsRequestCreator wsBccs) {
        this.wsBccs = wsBccs;
    }

}
