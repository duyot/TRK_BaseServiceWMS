/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.OrderActionDTO;
import com.viettel.logistic.wms.dto.PickingListDTO;
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
 * @since 08-May-15 4:02 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface PickingListService {

    @WebMethod(operationName = "getListPickingListDTO")
    @WebResult(name = "pickingListDTO", targetNamespace = "http://wms.viettel.vn")
    public List<PickingListDTO> getListPickingListDTO(@WebParam(name = "pickingListDTO") PickingListDTO pickingListDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //
    @WebMethod(operationName = "updatePickingList")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updatePickingList(@WebParam(name = "pickingListDTO") PickingListDTO pickingListDTO);

    //
    @WebMethod(operationName = "deletePickingList")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deletePickingList(@WebParam(name = "pickingListDTOId") Long id);

    //
    @WebMethod(operationName = "deleteListPickingList")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListPickingList(@WebParam(name = "pickingListListDTO") List<PickingListDTO> pickingListListDTO);

    //
    @WebMethod(operationName = "findPickingListById")
    @WebResult(name = "pickingList", targetNamespace = "http://wms.viettel.vn")
    public PickingListDTO findPickingListById(@WebParam(name = "pickingListDTOId") Long id);

    //
    @WebMethod(operationName = "insertPickingList")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertPickingList(@WebParam(name = "pickingListDTO") PickingListDTO pickingListDTO);

    //
    @WebMethod(operationName = "insertOrUpdateListPickingList")
    @WebResult(name = "insertListPickingList", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListPickingList(@WebParam(name = "pickingListDTO") List<PickingListDTO> pickingListDTO);

    //
    @WebMethod(operationName = "getSequensePickingList")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequensePickingList(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    
    @WebMethod(operationName = "getListPickingListByCondition")
    @WebResult(name = "PickingListDTO", targetNamespace = "http://wms.viettel.vn")
    public List<PickingListDTO> getListPickingListByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "insertListPickingList")
    @WebResult(name = "resultDTO", targetNamespace = "http://cms.viettel.vn")
    ResultDTO insertListPickingList(List<PickingListDTO> lstPickingListDTO, List<PickingListGoodsDTO> lstPickingListGoodsDTO);

    @WebMethod(operationName = "canceledListPickingList")
    @WebResult(name = "resultDTO", targetNamespace = "http://cms.viettel.vn")
    ResultDTO canceledListPickingList(@WebParam(name = "orderActionDTO") OrderActionDTO actionDTO);
}
