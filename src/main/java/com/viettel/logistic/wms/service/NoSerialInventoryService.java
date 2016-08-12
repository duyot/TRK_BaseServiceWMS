/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.NoSerialInventoryDTO;
import com.viettel.logistic.wms.dto.SerialInventoryDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;

/**
 * @author thienng1
 * @version 1.0
 * @since 5/10/2016 4:47 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface NoSerialInventoryService {

    @WebMethod(operationName = "getListNoSerialInventoryDTO")
    @WebResult(name = "noSerialInventoryDTO", targetNamespace = "http://wms.viettel.vn")
    public List<NoSerialInventoryDTO> getListNoSerialInventoryDTO(@WebParam(name = "noSerialInventoryDTO") NoSerialInventoryDTO noSerialInventoryDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "updateNoSerialInventory")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateNoSerialInventory(@WebParam(name = "noSerialInventoryDTO") NoSerialInventoryDTO noSerialInventoryDTO);

    @WebMethod(operationName = "deleteNoSerialInventory")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteNoSerialInventory(@WebParam(name = "noSerialInventoryDTOId") Long id);

    @WebMethod(operationName = "deleteListNoSerialInventory")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListNoSerialInventory(@WebParam(name = "noSerialInventoryListDTO") List<NoSerialInventoryDTO> noSerialInventoryListDTO);

    @WebMethod(operationName = "findNoSerialInventoryById")
    @WebResult(name = "noSerialInventory", targetNamespace = "http://wms.viettel.vn")
    public NoSerialInventoryDTO findNoSerialInventoryById(@WebParam(name = "noSerialInventoryDTOId") Long id);

    @WebMethod(operationName = "insertNoSerialInventory")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertNoSerialInventory(@WebParam(name = "noSerialInventoryDTO") NoSerialInventoryDTO noSerialInventoryDTO);

    @WebMethod(operationName = "insertOrUpdateListNoSerialInventory")
    @WebResult(name = "insertListNoSerialInventory", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertOrUpdateListNoSerialInventory(@WebParam(name = "noSerialInventoryDTO") List<NoSerialInventoryDTO> noSerialInventoryDTO);

//    
    @WebMethod(operationName = "getSequense")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequense(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //------------------------------------------------------------------
    //them tim kiem dt
    @WebMethod(operationName = "getListNoSerialInventoryByCondition")
    @WebResult(name = "noSerialInventoryDTO", targetNamespace = "http://wms.viettel.vn")
    public List<NoSerialInventoryDTO> getListNoSerialInventoryByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
    //
}
