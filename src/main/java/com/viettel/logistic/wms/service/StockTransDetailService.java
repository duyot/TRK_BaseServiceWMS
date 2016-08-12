/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.StockTransDetailDTO;
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
 * @since 22-Apr-15 4:10 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface StockTransDetailService {

    @WebMethod(operationName = "getListStockTransDetailDTO")
    @WebResult(name = "stockTransDetailDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransDetailDTO> getListStockTransDetailDTO(@WebParam(name = "stockTransDetailDTO") StockTransDetailDTO stockTransDetailDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "updateStockTransDetail")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateStockTransDetail(@WebParam(name = "stockTransDetailDTO") StockTransDetailDTO stockTransDetailDTO);

    @WebMethod(operationName = "deleteStockTransDetail")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteStockTransDetail(@WebParam(name = "stockTransDetailDTOId") Long id);

    @WebMethod(operationName = "deleteListStockTransDetail")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListStockTransDetail(@WebParam(name = "stockTransDetailListDTO") List<StockTransDetailDTO> stockTransDetailListDTO);

    @WebMethod(operationName = "findStockTransDetailById")
    @WebResult(name = "stockTransDetail", targetNamespace = "http://wms.viettel.vn")
    public StockTransDetailDTO findStockTransDetailById(@WebParam(name = "stockTransDetailDTOId") Long id);

    @WebMethod(operationName = "insertStockTransDetail")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public ResultDTO insertStockTransDetail(@WebParam(name = "stockTransDetailDTO") StockTransDetailDTO stockTransDetailDTO);

    @WebMethod(operationName = "insertOrUpdateListStockTransDetail")
    @WebResult(name = "insertListStockTransDetail", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertOrUpdateListStockTransDetail(@WebParam(name = "stockTransDetailDTO") List<StockTransDetailDTO> stockTransDetailDTO);

//    
    @WebMethod(operationName = "getSequenseStockTransDetail")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseStockTransDetail(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    @WebMethod(operationName = "getListStockTransDetailByCondition")
    @WebResult(name = "StockTransDetailDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransDetailDTO> getListStockTransDetailByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
    
    //Thienng1 160803 - danh sach hang hoa
    @WebMethod(operationName = "getListStockTransDetailByOrderId")
    @WebResult(name = "StockTransDetailDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockTransDetailDTO> getListStockTransDetailByOrderId(@WebParam(name = "orderId") String orderId);
    
    
}
