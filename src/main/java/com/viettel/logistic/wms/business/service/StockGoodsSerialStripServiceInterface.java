/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.logistic.wms.model.StockGoodsSerial;
import com.viettel.logistic.wms.model.StockGoodsSerialStrip;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author vtsoft
 */
public interface StockGoodsSerialStripServiceInterface extends BaseFWServiceInterface<StockGoodsSerialStripDTO, StockGoodsSerialStrip> {

    List<StockGoodsSerialStripDTO> getListStockGoodsSerial(StockGoodsSerialStripDTO stockGoodsSerialStripDTO);

    List<StockGoodsSerialStripDTO> getListStockGoodsSerialStrip(StockGoodsSerialStripDTO stockGoodsSerialStripDTO);

    List<StockGoodsSerialStripDTO> getListStockGoodsSerialStripExacly(StockGoodsSerialStripDTO stockGoodsSerialStripDTO);

    //Nhap kho
    ResultDTO importStockGoodsSerialStrip(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, String stockTransId, Session session);

    //Nhap kho cho trip bang connection
    ResultDTO importStockGoodsSerialStripConnection(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, String stockTransId, Connection con);

    //Xuat kho 
    ResultDTO exportStockGoodsSerialStrip(StockGoodsSerialStripDTO oldStockGoodsSerialStripDTO, StockGoodsSerialStripDTO newStockGoodsSerialStripDTO, Session session);

    ResultDTO updateCellStockGoodsSerialStrip(StockTransInforDTO stockTransInforDTO, Session session);

    //Add by ChuDV 27/05/2015; Lay thong tin chi tiet hang hoa
    List<StockGoodsSerialInforDTO> getListStockGoodsSerialInfor(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, String isSerialStrip);

    //Add by ThienNG1 02/06/2015;
    List<StockGoodsInforDTO> getListStockGoodsInforByZone(StockGoodsSerialStripDTO stockGoodsSerialStripDTO);

    //Add by ThienNG1 22/06/2015;
    List<StockGoodsSerialStripDTO> getListStockGoodsSerialBySerial(StockGoodsSerialStripDTO stockGoodsSerialStripDTO);

    //Add by QuyenDM 15/06/2015;
    Map<ChangePositionDTO, ResultDTO> updateCellStockGoodsSerialStrip(ChangePositionDTO changePositionDTO, Session session);

    //duyot- dieu chuyen hang hoa - cap nhat hang hoa cho serial trip
    ResultDTO updateNewGoods(StockGoodsSerialStripDTO oldTrip, StockGoodsSerialStripDTO newTrip, Session session);

    //duyot- dieu chuyen hang hoa - cap nhat hang hoa cho serial trip ver 2
    ResultDTO updateNewListGoods(StockGoodsSerialStripDTO oldTrip, List<StockGoodsSerialStrip> newTrip, Session session);

    //duyot - ham xuat kho serial tra ve danh sach stock_goods da xuat
    List<StockGoodsSerialStrip> exportStockGoodsSerialStripTransfer(StockGoodsSerialStrip oldStockGoodsSerialStrip, StockGoodsSerialStrip newStockGoodsSerialStrip, Session session);

    //Addby ThienNg1 - 03/08/2015
    ResultDTO reImportStockGoodsSerialStrip(StockGoodsSerialStripDTO oldStockGoodsSerialStripDTO, StockGoodsSerialStripDTO newStockGoodsSerialStripDTO, String stockTransId, Session session, String synImportRevoke);

    public List<StockGoodsSerialInforDTO> getListSerilStripErrorImportRevoke(String stockTransId);

    //DUYOT: 18/09
    /*
     - CAP NHAT DANH SACH STRIP: TRANG THAI
     */
    //Nhap kho
//    ResultDTO updateStockGoodsSerialStrip(String goodsId,String orderId,String status, Session session);
    public String updateStockGoodsSerialStripByOrdersIdAndGoods(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, Session session);

    public String updateStockGoodsSerialStripByOrdersId(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, Session session);

    public List<StockGoodsSerialStrip> getListSerialStripKitInStockByOrderId(String orderId, StockTransSerialDTO stockTransSerialDTO, Session session);

    public List<StockGoodsSerial> getListSerialKitInStockByOrderId(String orderId, StockTransSerialDTO stockTransSerialDTO, Session session);
}
