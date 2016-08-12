/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.BillStock;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * @author vtsoft
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface StockImportService {

    //NHAP KHO
    @WebMethod(operationName = "importStockCust")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO importStockCust(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetaiDTO, List<StockTransSerialDTO> lstStockTransSerialDTO);
    //---------------------HAM DONG BO------------------------
    /*
     duyot- dong bo
     @usage: su dung khi dong bo: cap nhat thong tin lenh,gui phoi phieu sang bccs - ktts
     */

    @WebMethod(operationName = "synImportStockCust")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO synImportStockCust(StockTransDTO stockTransDTO);
    /*
     - CAP NHAT TRANG THAI = 1 DOI VOI GIAO DICH, THONG TIN SERIAL
     - CAP NHAT REAL_TRANS_DATE = SYSDATE
     - CAP NHAT STOCK_GOODS_TOTAL
     */

    @WebMethod(operationName = "synRealImportStockCust")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO synRealImportStockCust(String orderId);
    //---------------------END HAM DONG BO------------------------

//    @WebMethod(operationName = "synImportStockCust")
//    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
//    ResultDTO synImportStockCust(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetaiDTO, List<StockTransSerialDTO> lstStockTransSerialDTO);
//    
    //Xuat kho
    @WebMethod(operationName = "updateCell")
    @WebResult(name = "ResultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO updateCell(StockTransDTO stockTransDTO, List<StockTransInforDTO> lstStockTransInforDTO);

    //nhap kho - DAO
    @WebMethod(operationName = "importStockCustDAO")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO importStockCustDAO(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetaiDTO, List<StockTransSerialDTO> lstStockTransSerialDTO);

    //ThienNG1 03/08/2015
    //Nhap kho cho hang thu hoi
    @WebMethod(operationName = "reImportStockRecovered")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO reImportStockRecovered(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetaiDTO, List<StockTransSerialDTO> lstStockTransSerialDTO);

    //duyot: [tichhop]: lay thong tin phieu nhap
    @WebMethod(operationName = "getListTrans")
    @WebResult(name = "importTicketDTO", targetNamespace = "http://wms.viettel.vn")
    List<BillStock> getListTrans(@WebParam(name = "orderCode") String orderCode, @WebParam(name = "orderType") String orderType, @WebParam(name = "startChangeDate") String fromDate, @WebParam(name = "endChangeDate") String toDate);

    //duyot: [tichhop]: lay thong tin phieu nhap
    @WebMethod(operationName = "getListTransBCCS")
    @WebResult(name = "billStock", targetNamespace = "http://wms.viettel.vn")
    BillStock getListTransBCCS(@WebParam(name = "orderId") String orderId, @WebParam(name = "orderType") String orderType);

    //getListErrorImportRevoke
    //thienng1: search thong tin hang hoa loi trong err$_stock_goods_serial
    @WebMethod(operationName = "getListErrorImportRevoke")
    @WebResult(name = "stockTransId", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialInforDTO> getListErrorImportRevoke(@WebParam(name = "stockTransId") String stockTransId);

    @WebMethod(operationName = "getListSerilStripErrorImportRevoke")
    @WebResult(name = "stockTransId", targetNamespace = "http://wms.viettel.vn")
    public List<StockGoodsSerialInforDTO> getListSerilStripErrorImportRevoke(@WebParam(name = "stockTransId") String stockTransId);
}
