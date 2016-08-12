

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.ReasonDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import java.util.List;
import java.util.Locale;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
/**
* @author ngocnd6
* @version 1.0
* @since 08-Apr-15 2:46 PM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface ReasonService {
    @WebMethod(operationName = "getListReasonDTO")
    @WebResult(name = "reasonDTO", targetNamespace = "http://wms.viettel.vn")
    public List<ReasonDTO> getListReasonDTO(@WebParam(name="reasonDTO") ReasonDTO reasonDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    @WebMethod(operationName = "updateReason")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateReason(@WebParam(name = "reasonDTO") ReasonDTO reasonDTO);

    @WebMethod(operationName = "deleteReason")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteReason(@WebParam(name = "reasonDTOId") Long id);

    @WebMethod(operationName = "deleteListReason")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListReason(@WebParam(name = "reasonListDTO") List<ReasonDTO> reasonListDTO);

    @WebMethod(operationName = "findReasonById")
    @WebResult(name = "reason", targetNamespace = "http://wms.viettel.vn")
    public ReasonDTO findReasonById(@WebParam(name = "reasonDTOId") Long id);  
    @WebMethod(operationName = "insertReason")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public ResultDTO insertReason(@WebParam(name="reasonDTO") ReasonDTO reasonDTO);
}
