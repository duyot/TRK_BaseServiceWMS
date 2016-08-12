/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vwf5.base.servicecaller;

/**
 *
 * @author thiendn1
 */
import javax.xml.ws.WebFault;

@WebFault(name = "WSException")
public class WSException extends Exception {

    String message;

    public WSException(String message) {
        super(message);
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }
}
