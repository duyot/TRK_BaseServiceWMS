/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.dto;

import com.viettel.logstic.wms.webservice.dto.BaseDTO;

/**
 *
 * @author vtsoft
 */
public class OrderStorageDTO extends BaseDTO{    
	//Fields
    private String id;
    private String stockId;
    private String orderDate;
    private String orderVolume;
    //Constructor
	public OrderStorageDTO() {	
	}
        public OrderStorageDTO(String id,String orderDate) {
            this.id = id;
            this.orderDate = orderDate;
	}
	public OrderStorageDTO(String id, String stockId, String orderDate, String orderVolume) {
		this.id = id;
		this.stockId = stockId;
		this.orderDate = orderDate;
		this.orderVolume = orderVolume;
	}	
	//Getters and setters
    
	public void setId(String id) {
        this.id = id;
    }
	public String getId() {		
        return id;		
    }
    
	public void setStockId(String stockId) {
        this.stockId = stockId;
    }
	public String getStockId() {		
        return stockId;		
    }
    
	public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
	public String getOrderDate() {		
        return orderDate;		
    }
    
	public void setOrderVolume(String orderVolume) {
        this.orderVolume = orderVolume;
    }
	public String getOrderVolume() {		
        return orderVolume;		
    }
    
	
}

