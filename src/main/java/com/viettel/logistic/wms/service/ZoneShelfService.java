

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.ZoneShelfDTO;
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
* @since 06-May-15 9:44 PM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface ZoneShelfService {
    @WebMethod(operationName = "getListZoneShelfDTO")
    @WebResult(name = "zoneShelfDTO", targetNamespace = "http://wms.viettel.vn")
    public List<ZoneShelfDTO> getListZoneShelfDTO(@WebParam(name="zoneShelfDTO") ZoneShelfDTO zoneShelfDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    //
    @WebMethod(operationName = "updateZoneShelf")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateZoneShelf(@WebParam(name = "zoneShelfDTO") ZoneShelfDTO zoneShelfDTO);
    //
    @WebMethod(operationName = "deleteZoneShelf")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteZoneShelf(@WebParam(name = "zoneShelfDTOId") Long id);
    //
    @WebMethod(operationName = "deleteListZoneShelf")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListZoneShelf(@WebParam(name = "zoneShelfListDTO") List<ZoneShelfDTO> zoneShelfListDTO);
    //
    @WebMethod(operationName = "findZoneShelfById")
    @WebResult(name = "zoneShelf", targetNamespace = "http://wms.viettel.vn")
    public ZoneShelfDTO findZoneShelfById(@WebParam(name = "zoneShelfDTOId") Long id);  
    //
    @WebMethod(operationName = "insertZoneShelf")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertZoneShelf(@WebParam(name="zoneShelfDTO") ZoneShelfDTO zoneShelfDTO);
    //
    @WebMethod(operationName = "insertOrUpdateListZoneShelf")
    @WebResult(name = "insertListZoneShelf", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListZoneShelf(@WebParam(name = "zoneShelfDTO") List<ZoneShelfDTO> zoneShelfDTO);   
    //
    @WebMethod(operationName = "getSequenseZoneShelf")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseZoneShelf(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);
    //    
    @WebMethod(operationName = "getListZoneShelfByCondition")
    @WebResult(name = "ZoneShelfDTO", targetNamespace = "http://wms.viettel.vn")
    public List<ZoneShelfDTO> getListZoneShelfByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
}
