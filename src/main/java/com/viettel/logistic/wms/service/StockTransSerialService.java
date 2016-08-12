/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import java.util.Locale;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 10:38 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface StockTransSerialService {

    @WebMethod(operationName = "getListStockTransSerialDTO")
    @WebResult(name = "stockTransSerialDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransSerialDTO> getListStockTransSerialDTO(@WebParam(name = "stockTransSerialDTO") StockTransSerialDTO stockTransSerialDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //
    @WebMethod(operationName = "updateStockTransSerial")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateStockTransSerial(@WebParam(name = "stockTransSerialDTO") StockTransSerialDTO stockTransSerialDTO);

    //
    @WebMethod(operationName = "deleteStockTransSerial")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteStockTransSerial(@WebParam(name = "stockTransSerialDTOId") Long id);

    //
    @WebMethod(operationName = "deleteListStockTransSerial")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListStockTransSerial(@WebParam(name = "stockTransSerialListDTO") List<StockTransSerialDTO> stockTransSerialListDTO);

    //
    @WebMethod(operationName = "findStockTransSerialById")
    @WebResult(name = "stockTransSerial", targetNamespace = "http://wms.viettel.vn")
    public StockTransSerialDTO findStockTransSerialById(@WebParam(name = "stockTransSerialDTOId") Long id);

    //
    @WebMethod(operationName = "insertStockTransSerial")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertStockTransSerial(@WebParam(name = "stockTransSerialDTO") StockTransSerialDTO stockTransSerialDTO);

    //
    @WebMethod(operationName = "insertOrUpdateListStockTransSerial")
    @WebResult(name = "insertListStockTransSerial", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListStockTransSerial(@WebParam(name = "stockTransSerialDTO") List<StockTransSerialDTO> stockTransSerialDTO);

    //
    @WebMethod(operationName = "getSequenseStockTransSerial")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseStockTransSerial(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    
    @WebMethod(operationName = "getListStockTransSerialByCondition")
    @WebResult(name = "StockTransSerialDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransSerialDTO> getListStockTransSerialByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //ThienNG1 AddBy 23/06/2015
    @WebMethod(operationName = "getListStockTransSerialBySerial")
    @WebResult(name = "getListStockTransSerialBySerial", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransSerialDTO> getListStockTransSerialBySerial(@WebParam(name = "stockTransSerialDTO") StockTransSerialDTO StockTransSerialDTO);
    
    @WebMethod(operationName = "getListStockTransSerialBySerialStrip")
    @WebResult(name = "getListStockTransSerialBySerialStrip", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransSerialDTO> getListStockTransSerialBySerialStrip(@WebParam(name = "stockTransSerialDTO") StockTransSerialDTO StockTransSerialDTO);

    //Thienng1 160803 - danh sach hang hoa
    @WebMethod(operationName = "getListStockTransSerialByOrderId")
    @WebResult(name = "StockTransSerialDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransSerialDTO> getListStockTransSerialByOrderId(@WebParam(name = "orderId") String orderId);
}
