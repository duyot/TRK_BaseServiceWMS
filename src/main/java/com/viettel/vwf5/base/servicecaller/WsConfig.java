/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vwf5.base.servicecaller;

import com.viettel.vfw5.base.utils.Constants;
import java.io.Serializable;

/**
 * @author thiendn1
 */
public class WsConfig implements Serializable {
    private String wsAddress;
    private String targetNameSpace;
    /*
    name of webmethod
     */
    private String serviceName;

    public WsConfig() {

    }

    public WsConfig(String wsAddress, String targetNameSpace, String serviceName, String username, String password) {
        this.wsAddress = wsAddress;
        this.targetNameSpace = targetNameSpace;
        setServiceName(serviceName);

    }

    public WsConfig(String wsAddress, String targetNameSpace, String serviceName) {
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
        if (serviceName.contains(Constants.WEB_SERVICE_CONS.SEPERATE_OPERATOR)) {
            serviceName = serviceName.split(Constants.WEB_SERVICE_CONS.SEPERATE_OPERATOR)[1];
        }
        this.serviceName = serviceName;
    }


}
