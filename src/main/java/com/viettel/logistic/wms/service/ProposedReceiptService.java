/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.ProposedReceiptDTO;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:44 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface ProposedReceiptService {

    @WebMethod(operationName = "getListProposedReceiptDTO")
    @WebResult(name = "proposedReceiptDTO", targetNamespace = "http://wms.viettel.vn")
    public List<ProposedReceiptDTO> getListProposedReceiptDTO(@WebParam(name = "proposedReceiptDTO") ProposedReceiptDTO proposedReceiptDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //
    @WebMethod(operationName = "updateProposedReceipt")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateProposedReceipt(@WebParam(name = "proposedReceiptDTO") ProposedReceiptDTO proposedReceiptDTO);

    //
    @WebMethod(operationName = "deleteProposedReceipt")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteProposedReceipt(@WebParam(name = "proposedReceiptDTOId") Long id);

    //
    @WebMethod(operationName = "deleteListProposedReceipt")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListProposedReceipt(@WebParam(name = "proposedReceiptListDTO") List<ProposedReceiptDTO> proposedReceiptListDTO);

    //
    @WebMethod(operationName = "findProposedReceiptById")
    @WebResult(name = "proposedReceipt", targetNamespace = "http://wms.viettel.vn")
    public ProposedReceiptDTO findProposedReceiptById(@WebParam(name = "proposedReceiptDTOId") Long id);

    //
    @WebMethod(operationName = "insertProposedReceipt")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertProposedReceipt(@WebParam(name = "proposedReceiptDTO") ProposedReceiptDTO proposedReceiptDTO);

    //
    @WebMethod(operationName = "insertOrUpdateListProposedReceipt")
    @WebResult(name = "insertListProposedReceipt", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListProposedReceipt(@WebParam(name = "proposedReceiptDTO") List<ProposedReceiptDTO> proposedReceiptDTO);

    //
    @WebMethod(operationName = "getSequenseProposedReceipt")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseProposedReceipt(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    
    @WebMethod(operationName = "getListProposedReceiptByCondition")
    @WebResult(name = "ProposedReceiptDTO", targetNamespace = "http://wms.viettel.vn")
    public List<ProposedReceiptDTO> getListProposedReceiptByCondition(@WebParam(name = "proposedReceiptDTO") ProposedReceiptDTO proposedReceiptDTO);
}
