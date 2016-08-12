/*
* Copyright (C) 2015 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.cms.service;

import com.viettel.logistic.cms.dto.OrdersDTO;
import java.util.List;
import java.util.Locale;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
*
* @author binhnt22@viettel.com.vn
* @version 1.0
* @since Apr 2015
*/
@WebService(targetNamespace = "http://pms.logistic.viettel.vn")
public interface OrdersService {
    @WebMethod(operationName = "getAll")
    @WebResult(name="listOrders", targetNamespace = "http://pms.logistic.viettel.vn")
    public List<OrdersDTO> getAll();
    @WebMethod(operationName = "create")
    @WebResult(name="message", targetNamespace = "http://pms.logistic.viettel.vn")
    public String create(Locale locale, OrdersDTO tDTO);
    @WebMethod(operationName = "update")
    @WebResult(name="message", targetNamespace = "http://pms.logistic.viettel.vn")
    public String update(Locale locale, OrdersDTO tDTO);
    @WebMethod(operationName = "delete")
    @WebResult(name="message", targetNamespace = "http://pms.logistic.viettel.vn")
    public String delete(Long id);
    @WebMethod(operationName = "deleteList")
    @WebResult(name="message", targetNamespace = "http://pms.logistic.viettel.vn")
    public String delete(List<OrdersDTO> tDTOOnTable);
    @WebMethod(operationName = "find")
    @WebResult(name="Orders", targetNamespace = "http://pms.logistic.viettel.vn")
    public OrdersDTO findById(Long id);  
}
