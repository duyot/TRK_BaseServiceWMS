/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.logistic.wms.dto.GoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockTransGoodsDTO;
import com.viettel.logistic.wms.model.StockGoodsSerial;
import com.viettel.logistic.wms.model.StockTransDetail;
import com.viettel.logstic.wms.webservice.dto.ChangeGoods;
import com.viettel.logstic.wms.webservice.dto.OrdersDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import java.sql.Connection;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author vtsoft
 */
public interface StockGoodsSerialServiceInterface extends BaseFWServiceInterface<StockGoodsSerialDTO, StockGoodsSerial> {

    public ResultDTO importStockGoodsSerial(StockGoodsSerialDTO stockGoodsSerialDTO, String stockTransId, String fromSerial, String toSerial, Session session);

    /*getListErrorImportRevoke
     duyot: insert list serial by batch
     */
    public ResultDTO importListStockGoodsSerial(StockTransDTO stockTrans, StockTransDetailDTO stockTransDetail, List<StockTransSerialDTO> lstStockTransSerial, Connection connection, String serialStatus);
    
    /*
     thienng1: search stocktransdetail
     */
    public StockTransDetail getStockTransDetail(String stockTransDetailId, Connection connection);

    public int updateStockTransDetail(String stockTransDetailId, double amount, Connection connection);

    public ResultDTO exportStockGoodsSerial(StockGoodsSerialStripDTO oldStockGoodsSerialStripDTO, StockGoodsSerialStripDTO newStockGoodsSerialStripDTO, Session session);

    public ResultDTO updateCellStockGoodsSerial(StockTransInforDTO stockTransInforDTO, Session session);

    //Add by QuyenDM 15/06/2015
    public Map<ChangePositionDTO, ResultDTO> updateCellStockGoodsSerial(ChangePositionDTO changePositionDTO, Session session);

    //
    public ResultDTO updateNewGoods(StockGoodsSerialDTO oldStockGoodsSerial, StockGoodsSerialDTO newStockGoodsSerial, String fromSerial, String toSerial, Session session);

    public ResultDTO updateNewSerialGoods(StockGoodsSerialDTO oldStockGoodsSerial, StockGoodsSerialDTO newStockGoodsSerial, String fromSerial, String toSerial, Session session);

    //ThienNG1 03/08/2015
    public ResultDTO reImportStockGoodsSerial(StockGoodsSerialDTO stockGoodsSerialDTO, String stockTransId, String fromSerial, String toSerial, Session session, String synImportRevoke);

    //QuyenDM 20160413 - Viet lai webservice   
    public ResultDTO reImportStockGoodsSerial(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO,
            List<StockTransSerialDTO> lstStockTransSerial, Connection connection, String serialStatus, boolean isUpdate);

    //ThienNG1 09/09/2015
    public List<StockGoodsSerialInforDTO> getListErrorImportRevoke(String stockTransId);

    public String counNumberSerial(StockGoodsSerialDTO stockGoodsSerialDTO, String[] arraySerial);

    public String updateStockGoodsSerialByOrdersId(StockGoodsSerialDTO stockGoodsSerialDTO, Session session);

    public String updateStockGoodsSerialByOrdersIdAndGoods(StockGoodsSerialDTO stockGoodsSerialDTO, Session session);

    public List<GoodsSerialInforDTO> getGoodsBySerial(OrdersDTO ordersDTO, List<GoodsSerialInforDTO> lstGoodsSerialInforDTO, Connection connection);

    public List<GoodsSerialInforDTO> getGoodsBySerialInventory(OrdersDTO ordersDTO, List<GoodsSerialInforDTO> lstGoodsSerialInforDTO, Connection connection);
    
    public List<StockTransGoodsDTO> getListStockTransGoods2Report(String lstStockTransCodes);

}
