
/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.model.Goods;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;

/**
 * @author ThieuLQ1
 * @version 1.0
 * @since 5/19/2015 3:52 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface GoodsService {

    @WebMethod(operationName = "getListGoodsDTO")
    @WebResult(name = "goodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsDTO> getListGoodsDTO(@WebParam(name = "goodsDTO") GoodsDTO goodsDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //
    @WebMethod(operationName = "updateGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateGoods(@WebParam(name = "goodsDTO") GoodsDTO goodsDTO);

    //
    @WebMethod(operationName = "deleteGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteGoods(@WebParam(name = "goodsDTOId") Long id);

    //
    @WebMethod(operationName = "deleteListGoods")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListGoods(@WebParam(name = "goodsListDTO") List<GoodsDTO> goodsListDTO);

    //
    @WebMethod(operationName = "findGoodsById")
    @WebResult(name = "goods", targetNamespace = "http://wms.viettel.vn")
    public GoodsDTO findGoodsById(@WebParam(name = "goodsDTOId") Long id);

    //
    @WebMethod(operationName = "insertGoods")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertGoods(@WebParam(name = "goodsDTO") GoodsDTO goodsDTO);

    //
    @WebMethod(operationName = "insertOrUpdateListGoods")
    @WebResult(name = "insertListGoods", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListGoods(@WebParam(name = "goodsDTO") List<GoodsDTO> goodsDTO);

    @WebMethod(operationName = "insertListGoods")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertListGoods(@WebParam(name = "goodsDTO") List<GoodsDTO> goodsDTO);

    //
    @WebMethod(operationName = "getSequenseGoods")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseGoods(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    
    @WebMethod(operationName = "getListGoodsByCondition")
    @WebResult(name = "GoodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsDTO> getListGoodsByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "importGoodsFile")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO importGoodsFile(@WebParam(name = "listGoodsDTO") List<GoodsDTO> lstGoodsDTO);

    //NgocND Update
    @WebMethod(operationName = "updateListGoodsByCondition")
    @WebResult(name = "updateListGoods", targetNamespace = "http://wms.viettel.vn")
    public String updateListGoodsGoodsByCondition(@WebParam(name = "goodsDTO") List<GoodsDTO> goodsDTO, int customerCode);

    @WebMethod(operationName = "getListGoodsForBCCS")
    @WebResult(name = "goodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsDTO> getListGoodsForBCCS(@WebParam(name = "lstGoodsCode") List<String> lstGoodsCode, @WebParam(name = "cusId") String cusCode);

    @WebMethod(operationName = "synchListGoods")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO synchListGoods(@WebParam(name = "listGoods") List<GoodsDTO> listGoods);

    @WebMethod(operationName = "getListGoodsWithCustId")
    @WebResult(name = "goodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsDTO> getListGoodsWithCustId(@WebParam(name = "custId") String custId);
    
    @WebMethod(operationName = "getListGoodsSerialByCustId")
    @WebResult(name = "goodsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsDTO> getListGoodsSerialByCustId(@WebParam(name = "custId") String custId,@WebParam(name = "isSerial") String isSerial,@WebParam(name = "isSerialStrip") String isSerialStrip);
}
