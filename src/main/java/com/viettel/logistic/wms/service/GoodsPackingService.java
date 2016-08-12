/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsPackingDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import java.util.Locale;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.viettel.vfw5.base.dto.ResultDTO;
/**
* @author TruongBX3
* @version 1.0
* @since 5/20/2015 6:43 PM
*/
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface GoodsPackingService {
    @WebMethod(operationName = "getListGoodsPackingDTO")
    @WebResult(name = "goodsPackingDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsPackingDTO> getListGoodsPackingDTO(@WebParam(name="goodsPackingDTO") GoodsPackingDTO goodsPackingDTO,@WebParam(name="rowStart") int rowStart,@WebParam(name="maxRow") int maxRow,@WebParam(name="sortType") String sortType,@WebParam(name="sortFieldList") String sortFieldList);
    //
    @WebMethod(operationName = "updateGoodsPacking")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateGoodsPacking(@WebParam(name = "goodsPackingDTO") GoodsPackingDTO goodsPackingDTO);
    //
    @WebMethod(operationName = "deleteGoodsPacking")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteGoodsPacking(@WebParam(name = "goodsPackingDTOId") Long id);
    //
    @WebMethod(operationName = "deleteListGoodsPacking")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListGoodsPacking(@WebParam(name = "goodsPackingListDTO") List<GoodsPackingDTO> goodsPackingListDTO);
    //
    @WebMethod(operationName = "findGoodsPackingById")
    @WebResult(name = "goodsPacking", targetNamespace = "http://wms.viettel.vn")
    public GoodsPackingDTO findGoodsPackingById(@WebParam(name = "goodsPackingDTOId") Long id);  
    //
    @WebMethod(operationName = "insertGoodsPacking")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertGoodsPacking(@WebParam(name="goodsPackingDTO") GoodsPackingDTO goodsPackingDTO);
    //
    @WebMethod(operationName = "insertOrUpdateListGoodsPacking")
    @WebResult(name = "insertListGoodsPacking", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListGoodsPacking(@WebParam(name = "goodsPackingDTO") List<GoodsPackingDTO> goodsPackingDTO);   
    //
    @WebMethod(operationName = "getSequenseGoodsPacking")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseGoodsPacking(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);
    //    
    @WebMethod(operationName = "getListGoodsPackingByCondition")
    @WebResult(name = "GoodsPackingDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsPackingDTO> getListGoodsPackingByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
}
