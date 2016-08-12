/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsHandoverDTO;
import com.viettel.logistic.wms.dto.HistoryFluctuationsDTO;
import com.viettel.logistic.wms.dto.ProposedReceiptDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:39 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface GoodsHandoverService {

    @WebMethod(operationName = "getListGoodsHandoverDTO")
    @WebResult(name = "goodsHandoverDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsHandoverDTO> getListGoodsHandoverDTO(@WebParam(name = "goodsHandoverDTO") GoodsHandoverDTO goodsHandoverDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //
    @WebMethod(operationName = "updateGoodsHandover")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateGoodsHandover(@WebParam(name = "goodsHandoverDTO") GoodsHandoverDTO goodsHandoverDTO);

    //
    @WebMethod(operationName = "deleteGoodsHandover")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteGoodsHandover(@WebParam(name = "goodsHandoverDTOId") Long id);

    //
    @WebMethod(operationName = "deleteListGoodsHandover")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListGoodsHandover(@WebParam(name = "goodsHandoverListDTO") List<GoodsHandoverDTO> goodsHandoverListDTO);

    //
    @WebMethod(operationName = "findGoodsHandoverById")
    @WebResult(name = "goodsHandover", targetNamespace = "http://wms.viettel.vn")
    public GoodsHandoverDTO findGoodsHandoverById(@WebParam(name = "goodsHandoverDTOId") Long id);

    //
    @WebMethod(operationName = "insertGoodsHandover")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertGoodsHandover(@WebParam(name = "goodsHandoverDTO") GoodsHandoverDTO goodsHandoverDTO);

    //
    @WebMethod(operationName = "insertOrUpdateListGoodsHandover")
    @WebResult(name = "insertListGoodsHandover", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListGoodsHandover(@WebParam(name = "goodsHandoverDTO") List<GoodsHandoverDTO> goodsHandoverDTO);

    //
    @WebMethod(operationName = "getSequenseGoodsHandover")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseGoodsHandover(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    
    @WebMethod(operationName = "getListGoodsHandoverByCondition")
    @WebResult(name = "GoodsHandoverDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsHandoverDTO> getListGoodsHandoverByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //QuyenDM 25/08/2015 Them phieu de nghi
    @WebMethod(operationName = "insertOrUpdateProposedReceipt")
    @WebResult(name = "ResultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertOrUpdateProposedReceipt(@WebParam(name = "proposedReceiptDTO") ProposedReceiptDTO proposedReceiptDTO, @WebParam(name = "goodsHandoverDTO") List<GoodsHandoverDTO> goodsHandoverDTO);

    //QuyenDM 25/08/2015 Them lich su bien dong
    @WebMethod(operationName = "insertOrUpdateHistoryFluctuations")
    @WebResult(name = "ResultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertOrUpdateHistoryFluctuations(@WebParam(name = "historyFluctuationsDTO") HistoryFluctuationsDTO historyFluctuationsDTO, @WebParam(name = "goodsHandoverDTO") List<GoodsHandoverDTO> goodsHandoverDTO);
}
