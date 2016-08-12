/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.HistoryChangeGoodsDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;

/**
 * @author duyot
 * @version 1.0
 * @since 2/3/2016 11:09 AM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface HistoryChangeGoodsService {

    @WebMethod(operationName = "getListHistoryChangeGoodsDTO")
    @WebResult(name = "historyChangeGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<HistoryChangeGoodsDTO> getListHistoryChangeGoodsDTO(@WebParam(name = "historyChangeGoodsDTO") HistoryChangeGoodsDTO historyChangeGoodsDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "updateHistoryChangeGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateHistoryChangeGoods(@WebParam(name = "historyChangeGoodsDTO") HistoryChangeGoodsDTO historyChangeGoodsDTO);

    @WebMethod(operationName = "deleteHistoryChangeGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteHistoryChangeGoods(@WebParam(name = "historyChangeGoodsDTOId") Long id);

    @WebMethod(operationName = "deleteListHistoryChangeGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListHistoryChangeGoods(@WebParam(name = "historyChangeGoodsListDTO") List<HistoryChangeGoodsDTO> historyChangeGoodsListDTO);

    @WebMethod(operationName = "findHistoryChangeGoodsById")
    @WebResult(name = "historyChangeGoods", targetNamespace = "http://wms.viettel.vn")
    public HistoryChangeGoodsDTO findHistoryChangeGoodsById(@WebParam(name = "historyChangeGoodsDTOId") Long id);

    @WebMethod(operationName = "insertHistoryChangeGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertHistoryChangeGoods(@WebParam(name = "historyChangeGoodsDTO") HistoryChangeGoodsDTO historyChangeGoodsDTO);

    @WebMethod(operationName = "insertOrUpdateListHistoryChangeGoods")
    @WebResult(name = "insertListHistoryChangeGoods", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertOrUpdateListHistoryChangeGoods(@WebParam(name = "historyChangeGoodsDTO") List<HistoryChangeGoodsDTO> historyChangeGoodsDTO);

//    
    @WebMethod(operationName = "getSequense")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequense(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    @WebMethod(operationName = "getListHistoryChangeGoodsByCondition")
    @WebResult(name = "HistoryChangeGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<HistoryChangeGoodsDTO> getListHistoryChangeGoodsByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

}
