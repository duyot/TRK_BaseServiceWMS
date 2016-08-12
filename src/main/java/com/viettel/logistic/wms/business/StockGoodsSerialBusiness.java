/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.StockGoodsSerialServiceInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.model.StockGoodsSerial;
import com.viettel.logistic.wms.dao.StockGoodsSerialDAO;
import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.logistic.wms.dto.GoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransGoodsDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.logistic.wms.model.StockTransDetail;
import com.viettel.logstic.wms.webservice.dto.OrderGoodsDetailDTO;
import com.viettel.logstic.wms.webservice.dto.OrdersDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import java.sql.Connection;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 13-Apr-15 7:17 PM
 */
@Service("stockGoodsSerialBusiness")
@Transactional
public class StockGoodsSerialBusiness extends BaseFWServiceImpl<StockGoodsSerialDAO, StockGoodsSerialDTO, StockGoodsSerial> implements StockGoodsSerialServiceInterface {

    @Autowired
    private StockGoodsSerialDAO stockGoodsSerialDAO;

    public StockGoodsSerialBusiness() {
        tModel = new StockGoodsSerial();
        tDAO = stockGoodsSerialDAO;
    }

    @Override
    public StockGoodsSerialDAO gettDAO() {
        return stockGoodsSerialDAO;
    }

    public StockGoodsSerialBusiness(Session session) {
        this.session = session;
        tModel = new StockGoodsSerial();
        tDAO = stockGoodsSerialDAO;
    }

    //    
    @Override
    public ResultDTO importStockGoodsSerial(StockGoodsSerialDTO stockGoodsSerialDTO, String stockTransId, String fromSerial, String toSerial, Session session) {
        StockGoodsSerial stockGoodsSerial = stockGoodsSerialDTO.toModel();
        ResultDTO resultDTO = gettDAO().importStockGoodsSerial(stockGoodsSerial, stockTransId, fromSerial, toSerial, session);
        return resultDTO;
    }

    @Override
    public ResultDTO exportStockGoodsSerial(StockGoodsSerialStripDTO oldStockGoodsSerialStripDTO, StockGoodsSerialStripDTO newStockGoodsSerialStripDTO, Session session) {
        ResultDTO resultDTO = gettDAO().exportStockGoodsSerial(oldStockGoodsSerialStripDTO, newStockGoodsSerialStripDTO, session);
        return resultDTO;
    }

    //
    @Override
    public ResultDTO updateCellStockGoodsSerial(StockTransInforDTO stockTransInforDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO = gettDAO().updateCellStockGoodsSerial(stockTransInforDTO, session);
        return resultDTO;
    }

    @Override
    public Map<ChangePositionDTO, ResultDTO> updateCellStockGoodsSerial(ChangePositionDTO changePositionDTO, Session session) {
//        ResultDTO resultDTO = new ResultDTO();
//        resultDTO = gettDAO().updateCellStockGoodsSerial(changePositionDTO, session);
        return gettDAO().updateCellStockGoodsSerial(changePositionDTO, session);
    }

    @Override
    public ResultDTO updateNewGoods(StockGoodsSerialDTO oldStockGoodsSerial, StockGoodsSerialDTO newStockGoodsSerial, String fromSerial, String toSerial, Session session) {
        return gettDAO().updateNewGoods(oldStockGoodsSerial, newStockGoodsSerial, fromSerial, toSerial, session);
    }

    @Override
    public ResultDTO updateNewSerialGoods(StockGoodsSerialDTO oldStockGoodsSerial, StockGoodsSerialDTO newStockGoodsSerial, String fromSerial, String toSerial, Session session) {
        return gettDAO().updateNewSerialGoods(oldStockGoodsSerial, newStockGoodsSerial, fromSerial, toSerial, session);
    }

    //Addby ThienNG1
    @Override
    public ResultDTO reImportStockGoodsSerial(StockGoodsSerialDTO stockGoodsSerialDTO, String stockTransId, String fromSerial, String toSerial, Session session, String synImportRevoke) {
        StockGoodsSerial stockGoodsSerial = stockGoodsSerialDTO.toModel();
        ResultDTO resultDTO = gettDAO().reImportStockGoodsSerial(stockGoodsSerial, stockTransId, fromSerial, toSerial, session, synImportRevoke);
        return resultDTO;
    }

    //AddBy QuyenDM 20160413
    @Override
    public ResultDTO reImportStockGoodsSerial(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO,
            List<StockTransSerialDTO> lstStockTransSerial, Connection connection, String serialStatus, boolean isUpdate) {
        ResultDTO resultDTO = gettDAO().updateOrInsertSyncImportGoodsRevoke(stockTransDTO,
                stockTransDetailDTO, lstStockTransSerial, connection, serialStatus, isUpdate);
        return resultDTO;
    }

    @Override
    public List<StockGoodsSerialInforDTO> getListErrorImportRevoke(String stockTransId) {
        return gettDAO().getListErrorImportRevoke(stockTransId);
    }

    @Override
    public ResultDTO importListStockGoodsSerial(StockTransDTO stockTrans, StockTransDetailDTO stockTransDetail, List<StockTransSerialDTO> lstStockTransSerial, Connection connection, String serialStatus) {
        ResultDTO resultDTO = gettDAO().importStockGoodsSerialBatch(stockTrans, stockTransDetail, lstStockTransSerial, connection, serialStatus);
        return resultDTO;
    }

    @Override
    public String counNumberSerial(StockGoodsSerialDTO stockGoodsSerialDTO, String[] arraySerial) {
        String numberSrial = gettDAO().checkSerialExist(stockGoodsSerialDTO.toModel(), arraySerial);
        return numberSrial;
    }

    @Override
    public String updateStockGoodsSerialByOrdersId(StockGoodsSerialDTO stockGoodsSerialDTO, Session session) {
        return gettDAO().updateStockGoodsSerialByOrdersId(stockGoodsSerialDTO, session);
    }

    @Override
    public String updateStockGoodsSerialByOrdersIdAndGoods(StockGoodsSerialDTO stockGoodsSerialDTO, Session session) {
        return gettDAO().updateStockGoodsSerialByOrdersIdAndGoods(stockGoodsSerialDTO, session);
    }

    @Override
    public StockTransDetail getStockTransDetail(String stockTransDetailId, Connection connection) {
        return gettDAO().getStockTransDetail(stockTransDetailId, connection);
    }

    @Override
    public int updateStockTransDetail(String stockTransDetailId, double amount, Connection connection) {
        return gettDAO().updateStockTransDetail(stockTransDetailId, amount, connection);
    }

    @Override
    public List<GoodsSerialInforDTO> getGoodsBySerial(OrdersDTO ordersDTO, List<GoodsSerialInforDTO> lstGoodsSerialInforDTO, Connection connection) {
        return gettDAO().getGoodsBySerial(ordersDTO, lstGoodsSerialInforDTO, connection);
    }

    @Override
    public List<GoodsSerialInforDTO> getGoodsBySerialInventory(OrdersDTO ordersDTO, List<GoodsSerialInforDTO> lstGoodsSerialInforDTO, Connection connection) {
        return gettDAO().getGoodsBySerialInventory(ordersDTO, lstGoodsSerialInforDTO, connection);
    }

    @Override
    public List<StockTransGoodsDTO> getListStockTransGoods2Report(String lstStockTransCodes) {
        return gettDAO().getListStockTransGoods2Report(lstStockTransCodes);
    }

}
