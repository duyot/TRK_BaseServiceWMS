/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.MessagesDTO;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import java.util.Locale;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface MessagesService {

    @WebMethod(operationName = "getListMessagesDTO")
    @WebResult(name = "MessagesDTO", targetNamespace = "http://wms.viettel.vn")
    public List<MessagesDTO> getListMessagesDTO(@WebParam(name = "messagesDTO") MessagesDTO messagesDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //
    @WebMethod(operationName = "updateMessages")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateMessages(@WebParam(name = "messagesDTO") MessagesDTO messagesDTO);

    //
    @WebMethod(operationName = "deleteMessages")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteMessages(@WebParam(name = "messagesDTOId") Long id);

    //
    @WebMethod(operationName = "deleteListMessages")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListMessages(@WebParam(name = "messagesListDTO") List<MessagesDTO> messagesListDTO);

    //
    @WebMethod(operationName = "findMessagesById")
    @WebResult(name = "messages", targetNamespace = "http://wms.viettel.vn")
    public MessagesDTO findMessagesById(@WebParam(name = "messagesDTOId") Long id);

    //
    @WebMethod(operationName = "insertMessages")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertMessages(@WebParam(name = "messagesDTO") MessagesDTO messagesDTO);

    //
    @WebMethod(operationName = "insertOrUpdateListMessages")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListMessages(@WebParam(name = "messagesListDTO") List<MessagesDTO> messagesListDTO);

    //    
    @WebMethod(operationName = "getListMessagesByCondition")
    @WebResult(name = "MessagesDTO", targetNamespace = "http://wms.viettel.vn")
    public List<MessagesDTO> getListMessagesByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "getSequense")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequense(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

}
