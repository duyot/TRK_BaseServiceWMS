package com.viettel.vwf5.base.servicecaller.bccs;

import java.util.List;

/**
 * Created by vtsoft on 1/19/2015.
 */
public class WsTemplate {
    String soapMessage;
    List<String> headerList;
    List<String> aliasList;

    public String getSoapMessage() {
        return soapMessage;
    }

    public void setSoapMessage(String soapMessage) {
        this.soapMessage = soapMessage;
    }

    public List<String> getAliasList() {
        return aliasList;
    }

    public void setAliasList(List<String> aliasList) {
        this.aliasList = aliasList;
    }

    public List<String> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<String> headerList) {
        this.headerList = headerList;
    }
}
