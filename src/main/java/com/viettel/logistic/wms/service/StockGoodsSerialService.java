/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logstic.wms.webservice.dto.OrdersDTO;
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
 * @since 13-Apr-15 7:17 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface StockGoodsSerialService {

    @WebMethod(operationName = "getListStockGoodsSerialDTO")
    @WebResult(name = "stockGoodsSerialDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialDTO> getListStockGoodsSerialDTO(@WebParam(name = "stockGoodsSerialDTO") StockGoodsSerialDTO stockGoodsSerialDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "updateStockGoodsSerial")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateStockGoodsSerial(@WebParam(name = "stockGoodsSerialDTO") StockGoodsSerialDTO stockGoodsSerialDTO);

    @WebMethod(operationName = "deleteStockGoodsSerial")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteStockGoodsSerial(@WebParam(name = "stockGoodsSerialDTOId") Long id);

    @WebMethod(operationName = "deleteListStockGoodsSerial")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListStockGoodsSerial(@WebParam(name = "stockGoodsSerialListDTO") List<StockGoodsSerialDTO> stockGoodsSerialListDTO);

    @WebMethod(operationName = "findStockGoodsSerialById")
    @WebResult(name = "stockGoodsSerial", targetNamespace = "http://wms.viettel.vn")
    public StockGoodsSerialDTO findStockGoodsSerialById(@WebParam(name = "stockGoodsSerialDTOId") Long id);

    @WebMethod(operationName = "insertStockGoodsSerial")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public ResultDTO insertStockGoodsSerial(@WebParam(name = "stockGoodsSerialDTO") StockGoodsSerialDTO stockGoodsSerialDTO);

    @WebMethod(operationName = "insertOrUpdateListStockGoodsSerial")
    @WebResult(name = "insertListStockGoodsSerial", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public String insertOrUpdateListStockGoodsSerial(@WebParam(name = "stockGoodsSerialDTO") List<StockGoodsSerialDTO> stockGoodsSerialDTO);

//    
    @WebMethod(operationName = "getSequense")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequense(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //Tim kiem va ghep dai serial
    @WebMethod(operationName = "getListStockGoodsSerial")
    @WebResult(name = "getListStockGoodsSerial", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerial(@WebParam(name = "stockGoodsSerialStripDTO") StockGoodsSerialStripDTO stockGoodsSerialStripDTO);

    //Tim kiem serial theo dai
    @WebMethod(operationName = "getListStockGoodsSerialStrip")
    @WebResult(name = "getListStockGoodsSerialStrip", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialStrip(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, int startRow, int maxRow, String sortType, String sortField);

    //Tim kiem theo dieu kien conditionBean
    @WebMethod(operationName = "getListStockGoodsSerialByCondition")
    @WebResult(name = "stockGoodsSerialDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialDTO> getListStockGoodsSerialByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "checkSerial")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO checkSerial(List<StockGoodsSerialDTO> lstStockGoodsSerialDTO);

    //QuyenDM 20151216 - Lay danh sach serial theo yeu cau
    @WebMethod(operationName = "getGoodsBySerial")
    @WebResult(name = "goodsSerialInforDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsSerialInforDTO> getGoodsBySerial(@WebParam(name = "ordersDTO") OrdersDTO ordersDTO, @WebParam(name = "lstGoodsSerialInforDTO") List<GoodsSerialInforDTO> lstGoodsSerialInforDTO);

    //Thienng1 06/04/2016
    @WebMethod(operationName = "getGoodsBySerialInventory")
    @WebResult(name = "goodsSerialInforDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsSerialInforDTO> getGoodsBySerialInventory(@WebParam(name = "ordersDTO") OrdersDTO ordersDTO, @WebParam(name = "lstGoodsSerialInforDTO") List<GoodsSerialInforDTO> lstGoodsSerialInforDTO);

}
