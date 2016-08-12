/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vfw5.base.pojo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vtsoft
 */
@XmlRootElement
public class ResultDTO {
    private String id;
    private String message;

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
    
    
}
