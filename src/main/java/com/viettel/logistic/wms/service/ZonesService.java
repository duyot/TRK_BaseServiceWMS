/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.ZonesDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import java.util.Locale;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 13-Apr-15 2:43 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface ZonesService {

    @WebMethod(operationName = "getListZonesDTO")
    @WebResult(name = "zonesDTO", targetNamespace = "http://wms.viettel.vn")
    public List<ZonesDTO> getListZonesDTO(@WebParam(name = "zonesDTO") ZonesDTO zonesDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "updateZones")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateZones(@WebParam(name = "zonesDTO") ZonesDTO zonesDTO);

    @WebMethod(operationName = "deleteZones")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteZones(@WebParam(name = "zonesDTOId") Long id);

    @WebMethod(operationName = "deleteListZones")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListZones(@WebParam(name = "zonesListDTO") List<ZonesDTO> zonesListDTO);

    @WebMethod(operationName = "findZonesById")
    @WebResult(name = "zones", targetNamespace = "http://wms.viettel.vn")
    public ZonesDTO findZonesById(@WebParam(name = "zonesDTOId") Long id);

    @WebMethod(operationName = "insertZones")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public ResultDTO insertZones(@WebParam(name = "zonesDTO") ZonesDTO zonesDTO);

    @WebMethod(operationName = "insertOrUpdateListZones")
    @WebResult(name = "insertListZones", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertOrUpdateListZones(@WebParam(name = "zonesDTO") List<ZonesDTO> zonesDTO);

//    
    @WebMethod(operationName = "getSequense")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequense(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    @WebMethod(operationName = "getListZonesByCondition")
    @WebResult(name = "ZonesDTO", targetNamespace = "http://wms.viettel.vn")
    public List<ZonesDTO> getListZonesByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

}
