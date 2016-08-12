/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.logistic.wms.dto.StockGoodsDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.logistic.wms.model.StockGoods;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author vtsoft
 */
public interface StockGoodsBusinessInterface extends BaseFWServiceInterface<StockGoodsDTO, StockGoods>{
    //Xuat kho khach hang
    //public ResultDTO exportStockGoods(StockGoodsDTO stockGoodsDTO, Double amount, Double amountIssue, String changeDate, Session session);
    //    
    public ResultDTO importStockGoods(StockGoodsDTO stockGoodsDTO, Session session);
    
    //
    public ResultDTO exportStockGoods(StockGoodsDTO stockGoodsDTO, Session session);
    public ResultDTO waitExportStockGoods(StockGoodsDTO stockGoodsDTO, Session session);
    //Lay tong so luong kho
    public List<StockGoodsInforDTO> getSumListStockGoods(StockGoodsInforDTO stockGoodsInforDTO);
    //Cap nhat Cell
    public ResultDTO updateCellStockGoods(StockTransInforDTO StockTransInforDTO, Session session);
    //Add by QuyenDM 15/06/2015 - Cap nhat vi tri trong kho - Chuc nang don dich kho
    public Map<ChangePositionDTO, ResultDTO> updateCellStockGoods(ChangePositionDTO changePositionDTO, Session session);
    
    //duyot - import from dao
    public ResultDTO importStockDAO(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetaiDTO, List<StockTransSerialDTO> lstStockTransSerialDTO);
    
    //duyot - update stock_goods for goods-transfer
    public ResultDTO updateNewGoods(StockGoodsDTO importStock, StockGoodsDTO exportedStock);
    //ThienNG1 03/08/2015
    public ResultDTO reImportStockGoods(StockGoodsDTO stockGoodsDTO, Session session, String synImportRevoke);
    
    //duyot- update list stock_goods: su dung cho dieu chuyen hang
    public ResultDTO importListStockGoods(List<StockGoodsDTO> stockGoodsDTO, Session session);
    //duyot
    public List<StockGoods> exportStockGoodsForTransfer(StockGoodsDTO stockGoodsDTO, Session session);
    
    public String updateStockGoodsByOrdersId(StockGoodsDTO stockGoodsDTO, Session session);
    //thienng1 - 19/04/2016
    //kiem ke hang hoa trong kho
    public List<StockGoodsSerialInforDTO> getStockGoodsInforInventory(StockGoodsSerialInforDTO stockGoodsSerialInforDTO, String isSerial, String isExportSerial);
}
