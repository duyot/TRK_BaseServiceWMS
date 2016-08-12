

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.PickingListGoodsDTO;
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
* @since 08-May-15 4:07 PM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface PickingListGoodsService {
    @WebMethod(operationName = "getListPickingListGoodsDTO")
    @WebResult(name = "pickingListGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<PickingListGoodsDTO> getListPickingListGoodsDTO(@WebParam(name="pickingListGoodsDTO") PickingListGoodsDTO pickingListGoodsDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    //
    @WebMethod(operationName = "updatePickingListGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updatePickingListGoods(@WebParam(name = "pickingListGoodsDTO") PickingListGoodsDTO pickingListGoodsDTO);
    //
    @WebMethod(operationName = "deletePickingListGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deletePickingListGoods(@WebParam(name = "pickingListGoodsDTOId") Long id);
    //
    @WebMethod(operationName = "deleteListPickingListGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListPickingListGoods(@WebParam(name = "pickingListGoodsListDTO") List<PickingListGoodsDTO> pickingListGoodsListDTO);
    //
    @WebMethod(operationName = "findPickingListGoodsById")
    @WebResult(name = "pickingListGoods", targetNamespace = "http://wms.viettel.vn")
    public PickingListGoodsDTO findPickingListGoodsById(@WebParam(name = "pickingListGoodsDTOId") Long id);  
    //
    @WebMethod(operationName = "insertPickingListGoods")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertPickingListGoods(@WebParam(name="pickingListGoodsDTO") PickingListGoodsDTO pickingListGoodsDTO);
    //
    @WebMethod(operationName = "insertOrUpdateListPickingListGoods")
    @WebResult(name = "insertListPickingListGoods", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListPickingListGoods(@WebParam(name = "pickingListGoodsDTO") List<PickingListGoodsDTO> pickingListGoodsDTO);   
    //
    @WebMethod(operationName = "getSequensePickingListGoods")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequensePickingListGoods(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);
    //    
    @WebMethod(operationName = "getListPickingListGoodsByCondition")
    @WebResult(name = "PickingListGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<PickingListGoodsDTO> getListPickingListGoodsByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
}
