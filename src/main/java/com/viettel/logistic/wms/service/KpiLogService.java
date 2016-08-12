/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.KpiLogDTO;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
/**
* @author duyot
* @version 1.0
* @since 12/31/2015 3:40 PM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface KpiLogService {
    @WebMethod(operationName = "getListKpiLogDTO")
    @WebResult(name = "kpiLogDTO", targetNamespace = "http://wms.viettel.vn")
    public List<KpiLogDTO> getListKpiLogDTO(@WebParam(name="kpiLogDTO") KpiLogDTO kpiLogDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    @WebMethod(operationName = "updateKpiLog")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateKpiLog(@WebParam(name = "kpiLogDTO") KpiLogDTO kpiLogDTO);

    @WebMethod(operationName = "deleteKpiLog")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteKpiLog(@WebParam(name = "kpiLogDTOId") Long id);

    @WebMethod(operationName = "deleteListKpiLog")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListKpiLog(@WebParam(name = "kpiLogListDTO") List<KpiLogDTO> kpiLogListDTO);

    @WebMethod(operationName = "findKpiLogById")
    @WebResult(name = "kpiLog", targetNamespace = "http://wms.viettel.vn")
    public KpiLogDTO findKpiLogById(@WebParam(name = "kpiLogDTOId") Long id);  
    @WebMethod(operationName = "insertKpiLog")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertKpiLog(@WebParam(name="kpiLogDTO") KpiLogDTO kpiLogDTO);
    @WebMethod(operationName = "insertOrUpdateListKpiLog")
    @WebResult(name = "insertListKpiLog", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertOrUpdateListKpiLog(@WebParam(name = "kpiLogDTO") List<KpiLogDTO> kpiLogDTO);
    @WebMethod(operationName = "getSequense")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequense(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);
}
