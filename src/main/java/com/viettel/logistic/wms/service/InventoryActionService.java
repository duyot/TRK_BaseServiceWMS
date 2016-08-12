
/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.dto.InventoryActionDTO;
import com.viettel.logistic.wms.dto.InventoryActionGoodsDTO;
import com.viettel.logistic.wms.dto.InventoryResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;

/**
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:16 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface InventoryActionService {

    @WebMethod(operationName = "getListInventoryActionDTO")
    @WebResult(name = "inventoryActionDTO", targetNamespace = "http://wms.viettel.vn")
    public List<InventoryActionDTO> getListInventoryActionDTO(@WebParam(name = "inventoryActionDTO") InventoryActionDTO inventoryActionDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //

    @WebMethod(operationName = "updateInventoryAction")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateInventoryAction(@WebParam(name = "inventoryActionDTO") InventoryActionDTO inventoryActionDTO);

    //

    @WebMethod(operationName = "deleteInventoryAction")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteInventoryAction(@WebParam(name = "inventoryActionDTOId") Long id);

    //

    @WebMethod(operationName = "deleteListInventoryAction")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListInventoryAction(@WebParam(name = "inventoryActionListDTO") List<InventoryActionDTO> inventoryActionListDTO);

    //

    @WebMethod(operationName = "findInventoryActionById")
    @WebResult(name = "inventoryAction", targetNamespace = "http://wms.viettel.vn")
    public InventoryActionDTO findInventoryActionById(@WebParam(name = "inventoryActionDTOId") Long id);

    //

    @WebMethod(operationName = "insertInventoryAction")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertInventoryAction(@WebParam(name = "inventoryActionDTO") InventoryActionDTO inventoryActionDTO);

    //

    @WebMethod(operationName = "insertOrUpdateListInventoryAction")
    @WebResult(name = "insertListInventoryAction", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListInventoryAction(@WebParam(name = "inventoryActionDTO") List<InventoryActionDTO> inventoryActionDTO);

    //

    @WebMethod(operationName = "getSequenseInventoryAction")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseInventoryAction(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    

    @WebMethod(operationName = "getListInventoryActionByCondition")
    @WebResult(name = "inventoryActionDTO", targetNamespace = "http://wms.viettel.vn")
    public List<InventoryActionDTO> getListInventoryActionByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //
    @WebMethod(operationName = "insertInventoryActionAndGoods")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertInventoryActionAndGoods(InventoryActionDTO inventoryActionDTO, List<InventoryActionGoodsDTO> lstInventoryActionGoodsDTO, List<InventoryResultDTO> lstInventoryResultDTO, boolean isInsert);

    @WebMethod(operationName = "insertInventoryActionAndGoodsCondition")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertInventoryActionAndGoodsCondition(InventoryActionDTO inventoryActionDTO, List<InventoryActionGoodsDTO> lstInventoryActionGoodsDTO,List<GoodsDTO> lstGoodsDTO, boolean isInsert);
}
