/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsHandoverSerialDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import java.util.Locale;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;
/**
* @author hongdq4
* @version 1.0
* @since 8/22/2015 2:41 PM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface GoodsHandoverSerialService {
    @WebMethod(operationName = "getListGoodsHandoverSerialDTO")
    @WebResult(name = "goodsHandoverSerialDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsHandoverSerialDTO> getListGoodsHandoverSerialDTO(@WebParam(name="goodsHandoverSerialDTO") GoodsHandoverSerialDTO goodsHandoverSerialDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    //
    @WebMethod(operationName = "updateGoodsHandoverSerial")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateGoodsHandoverSerial(@WebParam(name = "goodsHandoverSerialDTO") GoodsHandoverSerialDTO goodsHandoverSerialDTO);
    //
    @WebMethod(operationName = "deleteGoodsHandoverSerial")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteGoodsHandoverSerial(@WebParam(name = "goodsHandoverSerialDTOId") Long id);
    //
    @WebMethod(operationName = "deleteListGoodsHandoverSerial")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListGoodsHandoverSerial(@WebParam(name = "goodsHandoverSerialListDTO") List<GoodsHandoverSerialDTO> goodsHandoverSerialListDTO);
    //
    @WebMethod(operationName = "findGoodsHandoverSerialById")
    @WebResult(name = "goodsHandoverSerial", targetNamespace = "http://wms.viettel.vn")
    public GoodsHandoverSerialDTO findGoodsHandoverSerialById(@WebParam(name = "goodsHandoverSerialDTOId") Long id);  
    //
    @WebMethod(operationName = "insertGoodsHandoverSerial")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertGoodsHandoverSerial(@WebParam(name="goodsHandoverSerialDTO") GoodsHandoverSerialDTO goodsHandoverSerialDTO);
    //
    @WebMethod(operationName = "insertOrUpdateListGoodsHandoverSerial")
    @WebResult(name = "insertListGoodsHandoverSerial", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListGoodsHandoverSerial(@WebParam(name = "goodsHandoverSerialDTO") List<GoodsHandoverSerialDTO> goodsHandoverSerialDTO);   
    //
    @WebMethod(operationName = "getSequenseGoodsHandoverSerial")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseGoodsHandoverSerial(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);
    //    
    @WebMethod(operationName = "getListGoodsHandoverSerialByCondition")
    @WebResult(name = "GoodsHandoverSerialDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsHandoverSerialDTO> getListGoodsHandoverSerialByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
}
