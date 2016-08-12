

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.StockGoodsDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
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
* @since 14-Apr-15 11:33 PM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface StockGoodsService {
    @WebMethod(operationName = "getListStockGoodsDTO")
    @WebResult(name = "stockGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsDTO> getListStockGoodsDTO(@WebParam(name="stockGoodsDTO") StockGoodsDTO stockGoodsDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    //
    @WebMethod(operationName = "updateStockGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateStockGoods(@WebParam(name = "stockGoodsDTO") StockGoodsDTO stockGoodsDTO);
    //
    @WebMethod(operationName = "deleteStockGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteStockGoods(@WebParam(name = "stockGoodsDTOId") Long id);
    //
    @WebMethod(operationName = "deleteListStockGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListStockGoods(@WebParam(name = "stockGoodsListDTO") List<StockGoodsDTO> stockGoodsListDTO);
    //
    @WebMethod(operationName = "findStockGoodsById")
    @WebResult(name = "stockGoods", targetNamespace = "http://wms.viettel.vn")
    public StockGoodsDTO findStockGoodsById(@WebParam(name = "stockGoodsDTOId") Long id);  
    //
    @WebMethod(operationName = "insertStockGoods")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertStockGoods(@WebParam(name="stockGoodsDTO") StockGoodsDTO stockGoodsDTO);
    //
    @WebMethod(operationName = "insertOrUpdateListStockGoods")
    @WebResult(name = "insertListStockGoods", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListStockGoods(@WebParam(name = "stockGoodsDTO") List<StockGoodsDTO> stockGoodsDTO);
    //    
    @WebMethod(operationName = "getSequense")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequense(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);
    //Lay danh danh sach kho theo KH, kho, mat hang, trang thai hang
    @WebMethod(operationName = "getSumListStockGoods")
    @WebResult(name = "sumStockGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsInforDTO> getSumListStockGoods(StockGoodsInforDTO stockGoodsInforDTO);
    //QuyenDM 09/06/2015 them tim kiem theo conditionBean
    @WebMethod(operationName = "getListStockGoodsByCondition")
    @WebResult(name = "stockGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsDTO> getListStockGoodsByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
	
	//thienng1
    //Tim kiem hang hoa theo dai va khong serial
    @WebMethod(operationName = "getStockGoodsInforInventory")
    @WebResult(name = "getStockGoodsInforInventory", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialInforDTO> getStockGoodsInforInventory(@WebParam(name = "stockGoodsSerialInforDTO") StockGoodsSerialInforDTO stockGoodsSerialInforDTO, @WebParam(name = "isSerial") String isSerial, @WebParam(name = "isExportSerial") String isExportSerial);
}
