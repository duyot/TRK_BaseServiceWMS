/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.MapStaffGoodsDTO;
import com.viettel.logistic.wms.dto.StaffDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.dto.ResultMapStaffGoodsDTO;

/**
 * @author hongdq4
 * @version 1.0
 * @since 10/16/2015 10:12 AM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface MapStaffGoodsService {

    @WebMethod(operationName = "getListMapStaffGoodsDTO")
    @WebResult(name = "mapStaffGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<MapStaffGoodsDTO> getListMapStaffGoodsDTO(@WebParam(name = "mapStaffGoodsDTO") MapStaffGoodsDTO mapStaffGoodsDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //
    @WebMethod(operationName = "updateMapStaffGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateMapStaffGoods(@WebParam(name = "mapStaffGoodsDTO") MapStaffGoodsDTO mapStaffGoodsDTO);

    //
    @WebMethod(operationName = "deleteMapStaffGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteMapStaffGoods(@WebParam(name = "mapStaffGoodsDTOId") Long id);

    //
    @WebMethod(operationName = "deleteListMapStaffGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListMapStaffGoods(@WebParam(name = "mapStaffGoodsListDTO") List<MapStaffGoodsDTO> mapStaffGoodsListDTO);

    //
    @WebMethod(operationName = "findMapStaffGoodsById")
    @WebResult(name = "mapStaffGoods", targetNamespace = "http://wms.viettel.vn")
    public MapStaffGoodsDTO findMapStaffGoodsById(@WebParam(name = "mapStaffGoodsDTOId") Long id);

    //
    @WebMethod(operationName = "insertMapStaffGoods")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertMapStaffGoods(@WebParam(name = "mapStaffGoodsDTO") MapStaffGoodsDTO mapStaffGoodsDTO);

    //
    @WebMethod(operationName = "insertOrUpdateListMapStaffGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListMapStaffGoods(@WebParam(name = "mapStaffGoodsDTO") List<MapStaffGoodsDTO> mapStaffGoodsDTO);

    //
    @WebMethod(operationName = "getSequenseMapStaffGoods")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseMapStaffGoods(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    
    @WebMethod(operationName = "getListMapStaffGoodsByCondition")
    @WebResult(name = "MapStaffGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<MapStaffGoodsDTO> getListMapStaffGoodsByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "insertListMapStaffGoods")
    @WebResult(name = "resultMapStaffGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultMapStaffGoodsDTO insertListMapStaffGoods(@WebParam(name = "lstMapStaffGoods") List<MapStaffGoodsDTO> lstMapStaffGoodsDTO);
    
    @WebMethod(operationName = "transferRoleGoodsManagement")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String transferRoleGoodsManagement(@WebParam(name = "mapStaffGoodsListDTO") List<MapStaffGoodsDTO> listMapStaffGoodsProcess,@WebParam(name = "StaffDTO") StaffDTO sdto);
}
