/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.dto.MapStaffGoodsDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsTotalDTO;
import com.viettel.logstic.wms.webservice.dto.ChangeGoods;
import com.viettel.logstic.wms.webservice.dto.ChangeOrder;

import java.util.List;
import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * @author vtsoft
 */
@WebService(targetNamespace = "http://wms.viettel.vn")
public interface StockExportService {

    @WebMethod(operationName = "exportStockCust")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO exportStockCust(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetaiDTO, List<StockTransSerialDTO> lstStockTransSerialDTO);

    // xuat kho o trang thai cho.
    @WebMethod(operationName = "synExportStockCust")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO synExportStockCust(StockTransDTO stockTransDTO);
    // xuat kho o trang thai cho.
    @WebMethod(operationName = "synExportStockCusts")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO synExportStockCusts(List<StockTransDTO> lstStockTransDTO);
    // cap nhat lai trang thai stocktrans khi thuc xuat
    @WebMethod(operationName = "updateStatusStockTrans")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO updateStatusStockTrans(StockTransDTO stockTransDTO);

    //
    @WebMethod(operationName = "exportStockGoodsTotal")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO exportStockGoodsTotal(List<StockGoodsTotalDTO> lstStockGoodsTotalDTO);
    //
    @WebMethod(operationName = "exportStockGoodsTotalForSyn")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO exportStockGoodsTotalForSyn(List<StockGoodsTotalDTO> lstStockGoodsTotalDTO);

    //duyot: dieu chuyen hang hoa
    @WebMethod(operationName = "goodsTransfer")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO goodsTransfer(StockTransDTO exportStockTransDTO, StockTransDTO importStockTransDTO, List<StockTransDetailDTO> lstExportDetail, List<StockTransDetailDTO> lstImportDetail,
            List<StockTransSerialDTO> lstExportSerial, List<StockTransSerialDTO> lstImportSerial);

    //thienng1: yeu cau dieu chinh hang hoa
    @WebMethod(operationName = "goodsTransferSynsKTTS")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO goodsTransferSynsKTTS(StockTransDTO exportStockTransDTO, StockTransDTO importStockTransDTO, List<StockTransDetailDTO> lstExportDetail, List<StockTransDetailDTO> lstImportDetail,
            List<StockTransSerialDTO> lstExportSerial,
            List<StockTransSerialDTO> lstImportSerial, ChangeOrder changeOrder,
            List<ChangeGoods> lstChangeGoods, Map<String, GoodsDTO> mapGoodsDTO, List<MapStaffGoodsDTO> lstMapStaffGoodsDTOs);

    //

    @WebMethod(operationName = "exportStockMultiLocation")
    @WebResult(name = "resultDTO", targetNamespace = "http://wms.viettel.vn")
    ResultDTO exportStockMultiLocation(List<StockTransDTO> lstStockTransDTO);
}
