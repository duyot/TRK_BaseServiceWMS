
/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logstic.wms.webservice.dto;


/**
* @author duyot
* @version 1.0
* @since 9/1/2015 3:42 PM
*/
public class SynLogDTO extends BaseDTO{    
	//Fields
    private String id;
    private String appCode;
    private String key;
    private String status;
    private String description;
    private String dateTime;
    //Constructor
	public SynLogDTO() {	
	}
	public SynLogDTO(String id, String appCode, String key, String status, String description, String dateTime) {
		this.id = id;
		this.appCode = appCode;
		this.key = key;
		this.status = status;
		this.description = description;
		this.dateTime = dateTime;
	}	
	//Getters and setters
    
	public void setId(String id) {
        this.id = id;
    }
	public String getId() {		
        return id;		
    }
    
	public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
	public String getAppCode() {		
        return appCode;		
    }
    
	public void setKey(String key) {
        this.key = key;
    }
	public String getKey() {		
        return key;		
    }
    
	public void setStatus(String status) {
        this.status = status;
    }
	public String getStatus() {		
        return status;		
    }
    
	public void setDescription(String description) {
        this.description = description;
    }
	public String getDescription() {		
        return description;		
    }
    
	public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
	public String getDateTime() {		
        return dateTime;		
    }
    
	
}

