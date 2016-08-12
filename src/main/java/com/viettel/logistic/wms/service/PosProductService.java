

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.PosProductDTO;
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
* @since 08-Apr-15 2:45 PM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface PosProductService {
    @WebMethod(operationName = "getListPosProductDTO")
    @WebResult(name = "posProductDTO", targetNamespace = "http://wms.viettel.vn")
    public List<PosProductDTO> getListPosProductDTO(@WebParam(name="posProductDTO") PosProductDTO posProductDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    @WebMethod(operationName = "updatePosProduct")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updatePosProduct(@WebParam(name = "posProductDTO") PosProductDTO posProductDTO);

    @WebMethod(operationName = "deletePosProduct")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deletePosProduct(@WebParam(name = "posProductDTOId") Long id);

    @WebMethod(operationName = "deleteListPosProduct")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListPosProduct(@WebParam(name = "posProductListDTO") List<PosProductDTO> posProductListDTO);

    @WebMethod(operationName = "findPosProductById")
    @WebResult(name = "posProduct", targetNamespace = "http://wms.viettel.vn")
    public PosProductDTO findPosProductById(@WebParam(name = "posProductDTOId") Long id);  
    @WebMethod(operationName = "insertPosProduct")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public ResultDTO insertPosProduct(@WebParam(name="posProductDTO") PosProductDTO posProductDTO);
}
