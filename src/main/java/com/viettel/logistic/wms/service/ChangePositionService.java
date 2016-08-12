/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;

/**
 * @author HungKV
 * @version 1.0
 * @since 6/9/2015 9:40 AM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface ChangePositionService {

    @WebMethod(operationName = "getListChangePositionDTO")
    @WebResult(name = "changePositionDTO", targetNamespace = "http://wms.viettel.vn")
    public List<ChangePositionDTO> getListChangePositionDTO(@WebParam(name = "changePositionDTO") ChangePositionDTO changePositionDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //
    @WebMethod(operationName = "updateChangePosition")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateChangePosition(@WebParam(name = "changePositionDTO") ChangePositionDTO changePositionDTO);

    //
    @WebMethod(operationName = "deleteChangePosition")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteChangePosition(@WebParam(name = "changePositionDTOId") Long id);

    //
    @WebMethod(operationName = "deleteListChangePosition")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListChangePosition(@WebParam(name = "changePositionListDTO") List<ChangePositionDTO> changePositionListDTO);

    //
    @WebMethod(operationName = "findChangePositionById")
    @WebResult(name = "changePosition", targetNamespace = "http://wms.viettel.vn")
    public ChangePositionDTO findChangePositionById(@WebParam(name = "changePositionDTOId") Long id);

    //
    @WebMethod(operationName = "insertChangePosition")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertChangePosition(@WebParam(name = "changePositionDTO") ChangePositionDTO changePositionDTO);

    //
    @WebMethod(operationName = "insertOrUpdateListChangePosition")
    @WebResult(name = "insertListChangePosition", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListChangePosition(@WebParam(name = "changePositionDTO") List<ChangePositionDTO> changePositionDTO);

    //
    @WebMethod(operationName = "getSequenseChangePosition")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseChangePosition(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    
    @WebMethod(operationName = "getListChangePositionByCondition")
    @WebResult(name = "ChangePositionDTO", targetNamespace = "http://wms.viettel.vn")
    public List<ChangePositionDTO> getListChangePositionByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //Add by QuyenDM 15/06/2015 - Cap nhat vi tri trong kho - chuc nang don dich kho    
    @WebMethod(operationName = "updateCellChangePosition")
    @WebResult(name = "ListErrors", targetNamespace = "http://wms.viettel.vn")
    public List<ChangePositionDTO> updateCellChangePosition(@WebParam(name = "lstChangePositionDTOs") List<ChangePositionDTO> lstChangePositionDTOs);
}
