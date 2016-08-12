/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.StockDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author thieulq1
 * @version 1.0
 * @since 06-Apr-15 5:28 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface StockService {

    @WebMethod(operationName = "getAllStock")
    @WebResult(name = "listStock", targetNamespace = "http://wms.viettel.vn")
    public List<StockDTO> getAllStock();


    @WebMethod(operationName = "updateStock")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateStock(@WebParam(name = "stockDTO") StockDTO stockDTO);

    @WebMethod(operationName = "deleteStock")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteStock(@WebParam(name = "stockDTOId") Long id);

    @WebMethod(operationName = "deleteListStock")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListStock(@WebParam(name = "stockListDTO") List<StockDTO> stockListDTO);

    @WebMethod(operationName = "findStockById")
    @WebResult(name = "stock", targetNamespace = "http://wms.viettel.vn")
    public StockDTO findStockById(@WebParam(name = "stockDTOId") Long id);
    @WebMethod(operationName = "getListStockDTO")
    @WebResult(name = "stockDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockDTO> getListStockDTO(@WebParam(name="stockDTO") StockDTO stockDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    
    //    thanh cong tra ve SUCCESS
    @WebMethod(operationName = "insertStock")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertStock(@WebParam(name="stockDTO") StockDTO stockDTO);
    
    //    thanh cong tra ve SUCCESS
    @WebMethod(operationName = "getCountStockDTO")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String getCountStockDTO(@WebParam(name = "stockDTO") StockDTO stockDTO);
    
    @WebMethod(operationName = "getListStockByCondition")
    @WebResult(name = "stockDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockDTO> getListStockByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
}
