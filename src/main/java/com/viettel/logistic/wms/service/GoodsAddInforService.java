/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsAddInforDTO;
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
 * @since 22-Apr-15 7:11 PM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface GoodsAddInforService {

    @WebMethod(operationName = "getListGoodsAddInforDTO")
    @WebResult(name = "goodsAddInforDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsAddInforDTO> getListGoodsAddInforDTO(@WebParam(name = "goodsAddInforDTO") GoodsAddInforDTO goodsAddInforDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //

    @WebMethod(operationName = "updateGoodsAddInfor")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateGoodsAddInfor(@WebParam(name = "goodsAddInforDTO") GoodsAddInforDTO goodsAddInforDTO);

    //

    @WebMethod(operationName = "deleteGoodsAddInfor")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteGoodsAddInfor(@WebParam(name = "goodsAddInforDTOId") Long id);

    //

    @WebMethod(operationName = "deleteListGoodsAddInfor")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListGoodsAddInfor(@WebParam(name = "goodsAddInforListDTO") List<GoodsAddInforDTO> goodsAddInforListDTO);

    //

    @WebMethod(operationName = "findGoodsAddInforById")
    @WebResult(name = "goodsAddInfor", targetNamespace = "http://wms.viettel.vn")
    public GoodsAddInforDTO findGoodsAddInforById(@WebParam(name = "goodsAddInforDTOId") Long id);

    //

    @WebMethod(operationName = "insertGoodsAddInfor")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertGoodsAddInfor(@WebParam(name = "goodsAddInforDTO") GoodsAddInforDTO goodsAddInforDTO);

    //

    @WebMethod(operationName = "insertOrUpdateListGoodsAddInfor")
    @WebResult(name = "insertListGoodsAddInfor", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListGoodsAddInfor(@WebParam(name = "goodsAddInforDTO") List<GoodsAddInforDTO> goodsAddInforDTO);

    //

    @WebMethod(operationName = "getSequenseGoodsAddInfor")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequenseGoodsAddInfor(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    

    @WebMethod(operationName = "getListGoodsAddInforByCondition")
    @WebResult(name = "GoodsAddInforDTO", targetNamespace = "http://wms.viettel.vn")
    public List<GoodsAddInforDTO> getListGoodsAddInforByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
}
