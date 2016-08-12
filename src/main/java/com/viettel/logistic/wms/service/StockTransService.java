/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.CardStockInforDTO;
import com.viettel.logistic.wms.dto.CustomerDTO;
import com.viettel.logistic.wms.dto.StockDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransGoodsDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 5:25 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface StockTransService {

    @WebMethod(operationName = "getListStockTransDTO")
    @WebResult(name = "stockTransDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransDTO> getListStockTransDTO(@WebParam(name = "stockTransDTO") StockTransDTO stockTransDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //
    @WebMethod(operationName = "updateStockTrans")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateStockTrans(@WebParam(name = "stockTransDTO") StockTransDTO stockTransDTO);

    //
    @WebMethod(operationName = "deleteStockTrans")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteStockTrans(@WebParam(name = "stockTransDTOId") Long id);

    //
    @WebMethod(operationName = "deleteListStockTrans")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListStockTrans(@WebParam(name = "stockTransListDTO") List<StockTransDTO> stockTransListDTO);

    //
    @WebMethod(operationName = "findStockTransById")
    @WebResult(name = "stockTrans", targetNamespace = "http://wms.viettel.vn")
    public StockTransDTO findStockTransById(@WebParam(name = "stockTransDTOId") Long id);

    //
    @WebMethod(operationName = "insertStockTrans")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertStockTrans(@WebParam(name = "stockTransDTO") StockTransDTO stockTransDTO);

    //
    @WebMethod(operationName = "insertOrUpdateListStockTrans")
    @WebResult(name = "insertListStockTrans", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListStockTrans(@WebParam(name = "stockTransDTO") List<StockTransDTO> stockTransDTO);

    //
    @WebMethod(operationName = "getSequenseStockTrans")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseStockTrans(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    
    @WebMethod(operationName = "getListStockTransByCondition")
    @WebResult(name = "StockTransDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransDTO> getListStockTransByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "getListStockTransInfor")
    @WebResult(name = "listStockTransInfor", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransInforDTO> getListStockTransInfor(StockTransInforDTO stockTransInforDTO);

    @WebMethod(operationName = "getListCardStockInfor")
    @WebResult(name = "cardStockInforDTO", targetNamespace = "http://wms.viettel.vn")
    public List<CardStockInforDTO> getListCardStockInfor(@WebParam(name = "lstCardStockInforDTO") List<CardStockInforDTO> lstCardStockInforDTO);

    @WebMethod(operationName = "getListStockTransAndDetailByCondition")
    @WebResult(name = "StockTransDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransDTO> getListStockTransAndDetailByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "getListStockTrans")
    @WebResult(name = "stockTransDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransDTO> getListStockTrans(@WebParam(name = "stockTransDTO") StockTransDTO stockTransDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "inventoryInStock")
    @WebResult(name = "result", targetNamespace = "http://wms.viettel.vn")
    public Boolean inventoryInStock(@WebParam(name = "stockTransDTO") List<StockTransDTO> lstStockTrans,
            @WebParam(name = "customer") CustomerDTO customer, @WebParam(name = "stock") StockDTO stock);

    @WebMethod(operationName = "getListStockTrans2Inventory")
    @WebResult(name = "stockTransDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransDTO> getListStockTrans2Inventory(
            @WebParam(name = "stockTransDTO") StockTransDTO stockTransDTO,
            @WebParam(name = "fromDate") String fromDate,
            @WebParam(name = "toDate") String toDate,
            @WebParam(name = "stockTransType") String stockTransType);
        
    
    @WebMethod(operationName = "getListStockTransGoods2Report")
    @WebResult(name = "StockTransGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransGoodsDTO> getListStockTransGoods2Report(
//            @WebParam(name = "custId") String custId,
//            @WebParam(name = "ownerId") String ownerId,
//            @WebParam(name = "fromDate") String fromDate,
            @WebParam(name = "lstStockTransCodes") String lstStockTransCodes);
}
