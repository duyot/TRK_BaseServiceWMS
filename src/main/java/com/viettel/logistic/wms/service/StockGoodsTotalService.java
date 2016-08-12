

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.ResultBCCS;
import com.viettel.logistic.wms.dto.StockGoodsTotalDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logstic.wms.webservice.dto.ChangeGoods;
import com.viettel.logstic.wms.webservice.dto.ChangeOrder;
import com.viettel.logstic.wms.webservice.dto.ResultKTTS;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;
/**
* @author TruongBX3
* @version 1.0
* @since 29-Apr-15 11:36 AM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface StockGoodsTotalService {
    @WebMethod(operationName = "getListStockGoodsTotalDTO")
    @WebResult(name = "stockGoodsTotalDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsTotalDTO> getListStockGoodsTotalDTO(@WebParam(name="stockGoodsTotalDTO") StockGoodsTotalDTO stockGoodsTotalDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    //
    @WebMethod(operationName = "updateStockGoodsTotal")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateStockGoodsTotal(@WebParam(name = "stockGoodsTotalDTO") StockGoodsTotalDTO stockGoodsTotalDTO);
    //
    @WebMethod(operationName = "deleteStockGoodsTotal")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteStockGoodsTotal(@WebParam(name = "stockGoodsTotalDTOId") Long id);
    //
    @WebMethod(operationName = "deleteListStockGoodsTotal")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListStockGoodsTotal(@WebParam(name = "stockGoodsTotalListDTO") List<StockGoodsTotalDTO> stockGoodsTotalListDTO);
    //
    @WebMethod(operationName = "findStockGoodsTotalById")
    @WebResult(name = "stockGoodsTotal", targetNamespace = "http://wms.viettel.vn")
    public StockGoodsTotalDTO findStockGoodsTotalById(@WebParam(name = "stockGoodsTotalDTOId") Long id);  
    //
    @WebMethod(operationName = "insertStockGoodsTotal")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertStockGoodsTotal(@WebParam(name="stockGoodsTotalDTO") StockGoodsTotalDTO stockGoodsTotalDTO);
    //
    @WebMethod(operationName = "insertOrUpdateListStockGoodsTotal")
    @WebResult(name = "insertListStockGoodsTotal", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListStockGoodsTotal(@WebParam(name = "stockGoodsTotalDTO") List<StockGoodsTotalDTO> stockGoodsTotalDTO);   
    //
    @WebMethod(operationName = "getSequenseStockGoodsTotal")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseStockGoodsTotal(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);
    //    
    @WebMethod(operationName = "getListStockGoodsTotalByCondition")
    @WebResult(name = "StockGoodsTotalDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsTotalDTO> getListStockGoodsTotalByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
    @WebMethod(operationName = "getListDemo")
    @WebResult(name = "StockGoodsTotalDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsTotalDTO> getListDemo();

    //Lay danh danh sach kho theo KH, kho, mat hang, trang thai hang
    @WebMethod(operationName = "getSumListStockGoods")
    @WebResult(name = "sumStockGoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsInforDTO> getSumListStockGoods(StockGoodsInforDTO stockGoodsInforDTO);

    /**
     * Ham thay doi thong tin hang hoa
     *
     * @param changeOrder thong tin yeu cau
     * @param lstChangeGoods thong tin hang hoa can thay doi
     * @return
     */
    @WebMethod(operationName = "changeStockGoods")
    @WebResult(name = "ResultKTTS", targetNamespace = "http://wms.viettel.vn")
    public ResultKTTS changeStockGoods(ChangeOrder changeOrder, List<ChangeGoods> lstChangeGoods);
}
