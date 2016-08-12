/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.HistoryFluctuationsDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import java.util.Locale;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:43 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface HistoryFluctuationsService {

    @WebMethod(operationName = "getListHistoryFluctuationsDTO")
    @WebResult(name = "historyFluctuationsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<HistoryFluctuationsDTO> getListHistoryFluctuationsDTO(@WebParam(name = "historyFluctuationsDTO") HistoryFluctuationsDTO historyFluctuationsDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //

    @WebMethod(operationName = "updateHistoryFluctuations")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateHistoryFluctuations(@WebParam(name = "historyFluctuationsDTO") HistoryFluctuationsDTO historyFluctuationsDTO);

    //

    @WebMethod(operationName = "deleteHistoryFluctuations")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteHistoryFluctuations(@WebParam(name = "historyFluctuationsDTOId") Long id);

    //

    @WebMethod(operationName = "deleteListHistoryFluctuations")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListHistoryFluctuations(@WebParam(name = "historyFluctuationsListDTO") List<HistoryFluctuationsDTO> historyFluctuationsListDTO);

    //

    @WebMethod(operationName = "findHistoryFluctuationsById")
    @WebResult(name = "historyFluctuations", targetNamespace = "http://wms.viettel.vn")
    public HistoryFluctuationsDTO findHistoryFluctuationsById(@WebParam(name = "historyFluctuationsDTOId") Long id);

    //

    @WebMethod(operationName = "insertHistoryFluctuations")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertHistoryFluctuations(@WebParam(name = "historyFluctuationsDTO") HistoryFluctuationsDTO historyFluctuationsDTO);

    //

    @WebMethod(operationName = "insertOrUpdateListHistoryFluctuations")
    @WebResult(name = "insertListHistoryFluctuations", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListHistoryFluctuations(@WebParam(name = "historyFluctuationsDTO") List<HistoryFluctuationsDTO> historyFluctuationsDTO);

    //

    @WebMethod(operationName = "getSequenseHistoryFluctuations")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseHistoryFluctuations(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    

    @WebMethod(operationName = "getListHistoryFluctuationsByCondition")
    @WebResult(name = "HistoryFluctuationsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<HistoryFluctuationsDTO> getListHistoryFluctuationsByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
}
