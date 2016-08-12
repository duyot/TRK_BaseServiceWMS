/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 16-Apr-15 3:34 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface StockGoodsSerialStripService {

    @WebMethod(operationName = "getListStockGoodsSerialStripDTO")
    @WebResult(name = "stockGoodsSerialStripDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialStripDTO(@WebParam(name = "stockGoodsSerialStripDTO") StockGoodsSerialStripDTO stockGoodsSerialStripDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "updateStockGoodsSerialStrip")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateStockGoodsSerialStrip(@WebParam(name = "stockGoodsSerialStripDTO") StockGoodsSerialStripDTO stockGoodsSerialStripDTO);

    @WebMethod(operationName = "deleteStockGoodsSerialStrip")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteStockGoodsSerialStrip(@WebParam(name = "stockGoodsSerialStripDTOId") Long id);

    @WebMethod(operationName = "deleteListStockGoodsSerialStrip")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListStockGoodsSerialStrip(@WebParam(name = "stockGoodsSerialStripListDTO") List<StockGoodsSerialStripDTO> stockGoodsSerialStripListDTO);

    @WebMethod(operationName = "findStockGoodsSerialStripById")
    @WebResult(name = "stockGoodsSerialStrip", targetNamespace = "http://wms.viettel.vn")
    public StockGoodsSerialStripDTO findStockGoodsSerialStripById(@WebParam(name = "stockGoodsSerialStripDTOId") Long id);

    @WebMethod(operationName = "insertStockGoodsSerialStrip")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public ResultDTO insertStockGoodsSerialStrip(@WebParam(name = "stockGoodsSerialStripDTO") StockGoodsSerialStripDTO stockGoodsSerialStripDTO);

    @WebMethod(operationName = "insertOrUpdateListStockGoodsSerialStrip")
    @WebResult(name = "insertListStockGoodsSerialStrip", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertOrUpdateListStockGoodsSerialStrip(@WebParam(name = "stockGoodsSerialStripDTO") List<StockGoodsSerialStripDTO> stockGoodsSerialStripDTO);

//    
    @WebMethod(operationName = "getSequenseStockGoodsSerialStrip")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseStockGoodsSerialStrip(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    @WebMethod(operationName = "getListStockGoodsSerialStripByCondition")
    @WebResult(name = "StockGoodsSerialStripDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialStripByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //Tim kiem serial theo dai
    @WebMethod(operationName = "getListStockGoodsSerialInfor")
    @WebResult(name = "getListStockGoodsSerialInfor", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialInforDTO> getListStockGoodsSerialInfor(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, String isSerialStrip);

    //Tim kiem hang hoa theo vi tri
    @WebMethod(operationName = "getListStockGoodsInforByZone")
    @WebResult(name = "getListStockGoodsInforByZone", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsInforDTO> getListStockGoodsInforByZone(StockGoodsSerialStripDTO stockGoodsSerialStripDTO);

    //ThienNG1 addby 22/06/2015
    //Tim kiem hang hoa theo Serial
    @WebMethod(operationName = "getListStockGoodsSerialBySerial")
    @WebResult(name = "getListStockGoodsSerialBySerial", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialBySerial(StockGoodsSerialStripDTO stockGoodsSerialStripDTO);

    @WebMethod(operationName = "checkSerialExitsInSerialStrip")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO checkSerialExitsInSerialStrip(@WebParam(name = "lstStockGoodsSerialStrip") List<StockGoodsSerialStripDTO> lstStockGoodsSerialStrip);

}

