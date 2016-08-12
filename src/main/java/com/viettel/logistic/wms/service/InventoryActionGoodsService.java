
/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.InventoryActionGoodsDTO;
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
* @since 6/9/2015 11:28 PM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface InventoryActionGoodsService {
    @WebMethod(operationName = "getListInventoryActionGoodsDTO")
    @WebResult(name = "inventoryActionGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<InventoryActionGoodsDTO> getListInventoryActionGoodsDTO(@WebParam(name="inventoryActionGoodsDTO") InventoryActionGoodsDTO inventoryActionGoodsDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    //
    @WebMethod(operationName = "updateInventoryActionGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateInventoryActionGoods(@WebParam(name = "inventoryActionGoodsDTO") InventoryActionGoodsDTO inventoryActionGoodsDTO);
    //
    @WebMethod(operationName = "deleteInventoryActionGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteInventoryActionGoods(@WebParam(name = "inventoryActionGoodsDTOId") Long id);
    //
    @WebMethod(operationName = "deleteListInventoryActionGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListInventoryActionGoods(@WebParam(name = "inventoryActionGoodsListDTO") List<InventoryActionGoodsDTO> inventoryActionGoodsListDTO);
    //
    @WebMethod(operationName = "findInventoryActionGoodsById")
    @WebResult(name = "inventoryActionGoods", targetNamespace = "http://wms.viettel.vn")
    public InventoryActionGoodsDTO findInventoryActionGoodsById(@WebParam(name = "inventoryActionGoodsDTOId") Long id);  
    //
    @WebMethod(operationName = "insertInventoryActionGoods")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertInventoryActionGoods(@WebParam(name="inventoryActionGoodsDTO") InventoryActionGoodsDTO inventoryActionGoodsDTO);
    //
    @WebMethod(operationName = "insertOrUpdateListInventoryActionGoods")
    @WebResult(name = "insertListInventoryActionGoods", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListInventoryActionGoods(@WebParam(name = "inventoryActionGoodsDTO") List<InventoryActionGoodsDTO> inventoryActionGoodsDTO);   
    //
    @WebMethod(operationName = "getSequenseInventoryActionGoods")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseInventoryActionGoods(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);
    //    
    @WebMethod(operationName = "getListInventoryActionGoodsByCondition")
    @WebResult(name = "inventoryActionGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<InventoryActionGoodsDTO> getListInventoryActionGoodsByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
}
