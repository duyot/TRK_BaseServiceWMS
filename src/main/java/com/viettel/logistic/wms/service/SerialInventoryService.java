/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.SerialInventoryDTO;
import com.viettel.logistic.wms.dto.SerialInventoryReportDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 4/6/2016 9:40 AM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface SerialInventoryService {

    @WebMethod(operationName = "getListSerialInventoryDTO")
    @WebResult(name = "serialInventoryDTO", targetNamespace = "http://wms.viettel.vn")
    public List<SerialInventoryDTO> getListSerialInventoryDTO(@WebParam(name = "serialInventoryDTO") SerialInventoryDTO serialInventoryDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "updateSerialInventory")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateSerialInventory(@WebParam(name = "serialInventoryDTO") SerialInventoryDTO serialInventoryDTO);

    @WebMethod(operationName = "deleteSerialInventory")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteSerialInventory(@WebParam(name = "serialInventoryDTOId") Long id);

    @WebMethod(operationName = "deleteListSerialInventory")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListSerialInventory(@WebParam(name = "serialInventoryListDTO") List<SerialInventoryDTO> serialInventoryListDTO);

    @WebMethod(operationName = "findSerialInventoryById")
    @WebResult(name = "serialInventory", targetNamespace = "http://wms.viettel.vn")
    public SerialInventoryDTO findSerialInventoryById(@WebParam(name = "serialInventoryDTOId") Long id);

    @WebMethod(operationName = "insertSerialInventory")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertSerialInventory(@WebParam(name = "serialInventoryDTO") SerialInventoryDTO serialInventoryDTO);

    @WebMethod(operationName = "insertOrUpdateListSerialInventory")
    @WebResult(name = "insertListSerialInventory", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertOrUpdateListSerialInventory(@WebParam(name = "serialInventoryDTO") List<SerialInventoryDTO> serialInventoryDTO);

//    
    @WebMethod(operationName = "getSequense")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequense(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //them tim kiem dt
    @WebMethod(operationName = "getListSerialInventoryByCondition")
    @WebResult(name = "serialInventoryDTO", targetNamespace = "http://wms.viettel.vn")
    public List<SerialInventoryDTO> getListSerialInventoryByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
    //
    //duyot: 11/05: lay ra ds bao cao kiem ke
    @WebMethod(operationName = "getListSerialInventoryReport")
    @WebResult(name = "serialInventoryReportDTO", targetNamespace = "http://wms.viettel.vn")
    public List<SerialInventoryReportDTO> getListSerialInventoryReport(@WebParam(name = "serialInventoryDTO") SerialInventoryDTO serialInventoryDTO, @WebParam(name = "type") int type);
    //
    //them tim kiem dt
    @WebMethod(operationName = "insertListSerialInventoryBatch")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertListSerialInventoryBatch(@WebParam(name = "serialInventoryDTO") List<SerialInventoryDTO> serialInventoryDTO);
}
