/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsSetDTO;
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
 * @since 08-Apr-15 2:43 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface GoodsSetService {

    @WebMethod(operationName = "getListGoodsSetDTO")
    @WebResult(name = "goodsSetDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsSetDTO> getListGoodsSetDTO(@WebParam(name = "goodsSetDTO") GoodsSetDTO goodsSetDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "updateGoodsSet")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateGoodsSet(@WebParam(name = "goodsSetDTO") GoodsSetDTO goodsSetDTO);

    @WebMethod(operationName = "deleteGoodsSet")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteGoodsSet(@WebParam(name = "goodsSetDTOId") Long id);

    @WebMethod(operationName = "insertOrUpdateListGoodsSet")
    @WebResult(name = "insertListGoodsSet", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListGoodsSet(@WebParam(name = "goodsSetDTO") List<GoodsSetDTO> goodsSetDTO);

    @WebMethod(operationName = "deleteListGoodsSet")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListGoodsSet(@WebParam(name = "goodsSetListDTO") List<GoodsSetDTO> goodsSetListDTO);

    @WebMethod(operationName = "findGoodsSetById")
    @WebResult(name = "goodsSet", targetNamespace = "http://wms.viettel.vn")
    public GoodsSetDTO findGoodsSetById(@WebParam(name = "goodsSetDTOId") Long id);

    @WebMethod(operationName = "insertGoodsSet")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
//    thanh cong tra ve SUCCESS
    public ResultDTO insertGoodsSet(@WebParam(name = "goodsSetDTO") GoodsSetDTO goodsSetDTO);
}
