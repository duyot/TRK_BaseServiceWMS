/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.CellsDTO;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import java.util.List;
import java.util.Locale;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface CellsService {

    @WebMethod(operationName = "getListCellsDTO")
    @WebResult(name = "cellsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<CellsDTO> getListCellsDTO(@WebParam(name = "cellsDTO") CellsDTO cellsDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //

    @WebMethod(operationName = "updateCells")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String updateCells(@WebParam(name = "cellsDTO") CellsDTO cellsDTO);

    //

    @WebMethod(operationName = "deleteCells")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteCells(@WebParam(name = "cellsDTOId") Long id);

    //

    @WebMethod(operationName = "deleteListCells")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String deleteListCells(@WebParam(name = "cellsListDTO") List<CellsDTO> cellsListDTO);

    //

    @WebMethod(operationName = "findCellsById")
    @WebResult(name = "cells", targetNamespace = "http://wms.viettel.vn")
    public CellsDTO findCellsById(@WebParam(name = "cellsDTOId") Long id);

    //

    @WebMethod(operationName = "insertCells")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertCells(@WebParam(name = "cellsDTO") CellsDTO cellsDTO);

    //

    @WebMethod(operationName = "insertOrUpdateListCells")
    @WebResult(name = "message", targetNamespace = "http://wms.viettel.vn")
    public String insertOrUpdateListCells(@WebParam(name = "cellsListDTO") List<CellsDTO> cellsListDTO);

    //    

    @WebMethod(operationName = "getListCellsByCondition")
    @WebResult(name = "CellsDTO", targetNamespace = "http://wms.viettel.vn")
    public List<CellsDTO> getListCellsByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    @WebMethod(operationName = "getSequense")
    @WebResult(name = "getSequense", targetNamespace = "http://wms.viettel.vn")
    public List<String> getSequense(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);
    
    //Add by ChuDV: 22/05/2015, get danh sach Cell
    @WebMethod(operationName = "getListCells")
    @WebResult(name = "getListCells", targetNamespace = "http://wms.viettel.vn")
    public List<CellsDTO> getListCells(GoodsDTO goodsDTO, Long stockId, Long goodsPackingId);
    
    @WebMethod(operationName = "insertListCells")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    public ResultDTO insertListCells(@WebParam(name = "cellsDTO") List<CellsDTO> cellsDTO,@WebParam(name = "stockId") String stockId );
}
