
/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.InventoryActionDTO;
import com.viettel.logistic.wms.dto.InventoryResultDTO;
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
* @since 6/9/2015 11:26 PM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface InventoryResultService {
    @WebMethod(operationName = "getListInventoryResultDTO")
    @WebResult(name = "inventoryResultDTO", targetNamespace = "http://wms.viettel.vn")
    public List<InventoryResultDTO> getListInventoryResultDTO(@WebParam(name="inventoryResultDTO") InventoryResultDTO inventoryResultDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    //
    @WebMethod(operationName = "updateInventoryResult")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateInventoryResult(@WebParam(name = "inventoryResultDTO") InventoryResultDTO inventoryResultDTO);
    //
    @WebMethod(operationName = "deleteInventoryResult")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteInventoryResult(@WebParam(name = "inventoryResultDTOId") Long id);
    //
    @WebMethod(operationName = "deleteListInventoryResult")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListInventoryResult(@WebParam(name = "inventoryResultListDTO") List<InventoryResultDTO> inventoryResultListDTO);
    //
    @WebMethod(operationName = "findInventoryResultById")
    @WebResult(name = "inventoryResult", targetNamespace = "http://wms.viettel.vn")
    public InventoryResultDTO findInventoryResultById(@WebParam(name = "inventoryResultDTOId") Long id);  
    //
    @WebMethod(operationName = "insertInventoryResult")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertInventoryResult(@WebParam(name="inventoryResultDTO") InventoryResultDTO inventoryResultDTO);
    //
    @WebMethod(operationName = "insertOrUpdateListInventoryResult")
    @WebResult(name = "insertListInventoryResult", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListInventoryResult(@WebParam(name = "inventoryResultDTO") List<InventoryResultDTO> inventoryResultDTO);   
    //
    @WebMethod(operationName = "getSequenseInventoryResult")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseInventoryResult(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);
    //    
    @WebMethod(operationName = "getListInventoryResultByCondition")
    @WebResult(name = "inventoryResultDTO", targetNamespace = "http://wms.viettel.vn")
    public List<InventoryResultDTO> getListInventoryResultByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
    
    //duyot - cap nhat lenh kiem ke ko
    @WebMethod(operationName = "updateInventoryResultAndAction")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO updateInventoryResultAndAction(@WebParam(name = "inventoryActionDTO") InventoryActionDTO inventoryActionDTO, @WebParam(name = "lstInventoryResult") List<InventoryResultDTO> lstInventoryResult);
    
    
    //duyot - kiem tra ket qua kiem ke
    @WebMethod(operationName = "checkInventoryResult")
    @WebResult(name = "inventoryResultDTO", targetNamespace = "http://wms.viettel.vn")
    public List<InventoryResultDTO> checkInventoryResult(@WebParam(name = "inventoryActionDTO") InventoryActionDTO inventoryActionDTO);
    
    //duyot - get lst inventory result by display field
    @WebMethod(operationName = "getInventoryResultByDisplayField")
    @WebResult(name = "inventoryResultDTO", targetNamespace = "http://wms.viettel.vn")
    public List<InventoryResultDTO> getInventoryResultByDisplayField(@WebParam(name = "inventoryActionDTO") InventoryActionDTO inventoryActionDTO,@WebParam(name = "lstShowField") List<String> lstShowField);


}
