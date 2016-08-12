

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.StockGoodsSerialErrorDTO;
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
* @since 29-Apr-15 8:41 AM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface StockGoodsSerialErrorService {
    @WebMethod(operationName = "getListStockGoodsSerialErrorDTO")
    @WebResult(name = "stockGoodsSerialErrorDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialErrorDTO> getListStockGoodsSerialErrorDTO(@WebParam(name="stockGoodsSerialErrorDTO") StockGoodsSerialErrorDTO stockGoodsSerialErrorDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    //
    @WebMethod(operationName = "updateStockGoodsSerialError")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateStockGoodsSerialError(@WebParam(name = "stockGoodsSerialErrorDTO") StockGoodsSerialErrorDTO stockGoodsSerialErrorDTO);
    //
    @WebMethod(operationName = "deleteStockGoodsSerialError")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteStockGoodsSerialError(@WebParam(name = "stockGoodsSerialErrorDTOId") Long id);
    //
    @WebMethod(operationName = "deleteListStockGoodsSerialError")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListStockGoodsSerialError(@WebParam(name = "stockGoodsSerialErrorListDTO") List<StockGoodsSerialErrorDTO> stockGoodsSerialErrorListDTO);
    //
    @WebMethod(operationName = "findStockGoodsSerialErrorById")
    @WebResult(name = "stockGoodsSerialError", targetNamespace = "http://wms.viettel.vn")
    public StockGoodsSerialErrorDTO findStockGoodsSerialErrorById(@WebParam(name = "stockGoodsSerialErrorDTOId") Long id);  
    //
    @WebMethod(operationName = "insertStockGoodsSerialError")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertStockGoodsSerialError(@WebParam(name="stockGoodsSerialErrorDTO") StockGoodsSerialErrorDTO stockGoodsSerialErrorDTO);
    //
    @WebMethod(operationName = "insertOrUpdateListStockGoodsSerialError")
    @WebResult(name = "insertListStockGoodsSerialError", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListStockGoodsSerialError(@WebParam(name = "stockGoodsSerialErrorDTO") List<StockGoodsSerialErrorDTO> stockGoodsSerialErrorDTO);   
    //
    @WebMethod(operationName = "getSequenseStockGoodsSerialError")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseStockGoodsSerialError(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);
    //    
    @WebMethod(operationName = "getListStockGoodsSerialErrorByCondition")
    @WebResult(name = "StockGoodsSerialErrorDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialErrorDTO> getListStockGoodsSerialErrorByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
}
