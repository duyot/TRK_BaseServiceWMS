/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.StockGoodsSerialStripServiceInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.model.StockGoodsSerialStrip;
import com.viettel.logistic.wms.dao.StockGoodsSerialStripDAO;
import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.logistic.wms.model.StockGoodsSerial;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.Constants;
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
 * @since 16-Apr-15 3:34 PM
 */
@Service("stockGoodsSerialStripBusiness")
@Transactional
public class StockGoodsSerialStripBusiness extends BaseFWServiceImpl<StockGoodsSerialStripDAO, StockGoodsSerialStripDTO, StockGoodsSerialStrip> implements StockGoodsSerialStripServiceInterface {

    @Autowired
    private StockGoodsSerialStripDAO stockGoodsSerialStripDAO;

    public StockGoodsSerialStripBusiness() {
        tModel = new StockGoodsSerialStrip();
        tDAO = stockGoodsSerialStripDAO;
    }

    @Override
    public StockGoodsSerialStripDAO gettDAO() {
        return stockGoodsSerialStripDAO;
    }

    public StockGoodsSerialStripBusiness(Session session) {
        this.session = session;
        tModel = new StockGoodsSerialStrip();
        tDAO = stockGoodsSerialStripDAO;
    }

    //Tim kiem va ghep dai serial
    @Override
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerial(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsSerialStripDTO> lstStockGoodsSerial = new ArrayList<>();
        lstStockGoodsSerial = convertListModeltoDTO(gettDAO().getListStockGoodsSerial(stockGoodsSerialStripDTO));
        return lstStockGoodsSerial;
    }

    //Tim kiem serial theo dai
    @Override
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialStrip(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsSerialStripDTO> lstStockGoodsSerial = new ArrayList<>();
        StockGoodsSerialStrip stockGoodsSerialStrip = stockGoodsSerialStripDTO.toModel();
        lstStockGoodsSerial = convertListModeltoDTO(gettDAO().getListStockGoodsSerialStrip(stockGoodsSerialStrip));
        return lstStockGoodsSerial;
    }

    //Tim kiem serial theo dai, tim chinh xac
    @Override
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialStripExacly(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsSerialStripDTO> lstStockGoodsSerial = new ArrayList<>();
        StockGoodsSerialStrip stockGoodsSerialStrip = stockGoodsSerialStripDTO.toModel();
        lstStockGoodsSerial = convertListModeltoDTO(gettDAO().getListStockGoodsSerialStripExacly(stockGoodsSerialStrip));
        return lstStockGoodsSerial;
    }

//    @Override
//    public List<StockGoodsSerialStripDTO> getListSerialIntersection(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
//        List<StockGoodsSerialStripDTO> lstStockGoodsSerialStripDTO = new ArrayList<>();
//        lstStockGoodsSerialStripDTO = convertListModeltoDTO(gettDAO().getListSerialIntersection(stockGoodsSerialStripDTO));
//        return lstStockGoodsSerialStripDTO;
//    }
    //Add by ChuDV: 15/05/2015
    @Override
    public ResultDTO importStockGoodsSerialStrip(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, String stockTransId, Session session) {
        ResultDTO resultDTO;
        StockGoodsSerialStrip stockGoodsSerialStrip = stockGoodsSerialStripDTO.toModel();
        resultDTO = gettDAO().importStockGoodsSerialStrip(stockGoodsSerialStrip, stockTransId, session);
        return resultDTO;
    }

    //
    @Override
    public ResultDTO exportStockGoodsSerialStrip(StockGoodsSerialStripDTO oldStockGoodsSerialStripDTO, StockGoodsSerialStripDTO newStockGoodsSerialStripDTO, Session session) {
        ResultDTO resultDTO;
        StockGoodsSerialStrip oldStockGoodsSerialStrip = oldStockGoodsSerialStripDTO.toModel();
        StockGoodsSerialStrip newStockGoodsSerialStrip = newStockGoodsSerialStripDTO.toModel();
        resultDTO = gettDAO().exportStockGoodsSerialStrip(oldStockGoodsSerialStrip, newStockGoodsSerialStrip, session);
        return resultDTO;
    }

    //
    @Override
    public ResultDTO updateCellStockGoodsSerialStrip(StockTransInforDTO stockTransInforDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO = gettDAO().updateCellStockGoodsSerialStrip(stockTransInforDTO, session);
        return resultDTO;
    }

    //Add by ChuDV 27/05/2015; Lay thong tin chi tiet hang hoa
    @Override
    public List<StockGoodsSerialInforDTO> getListStockGoodsSerialInfor(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, String isSerialStrip) {
        List<StockGoodsSerialInforDTO> lstStockGoodsSerialInforDTO = null;
        if (isSerialStrip.equals(Constants.IS_SERIAL_STRIP)) {
            lstStockGoodsSerialInforDTO = gettDAO().getListStockGoodsSerialStripInfor(stockGoodsSerialStripDTO);
        } else {
            lstStockGoodsSerialInforDTO = gettDAO().getListStockGoodsSerialInfor(stockGoodsSerialStripDTO);
        }
        return lstStockGoodsSerialInforDTO;
    }

    //Add by ThienNG1 02/06/2015; Lay thong tin hang hoa theo vi tri
    @Override
    public List<StockGoodsInforDTO> getListStockGoodsInforByZone(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsInforDTO> lstStockGoodsInforDTO = null;
        lstStockGoodsInforDTO = gettDAO().getListStockGoodsByZone(stockGoodsSerialStripDTO);
        return lstStockGoodsInforDTO;
    }

    //Add by ThienNG1 22/06/2015; Lay thong tin hang hoa theo vi tri
    @Override
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialBySerial(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsSerialStripDTO> lstStockGoodsInforDTO = null;
        lstStockGoodsInforDTO = gettDAO().getListStockGoodsSerialBySerial(stockGoodsSerialStripDTO);
        return lstStockGoodsInforDTO;
    }

    //Add by QuyenDM 15/06/2015 Cap nhat vi tri - chuc nang don dich
    @Override
    public Map<ChangePositionDTO, ResultDTO> updateCellStockGoodsSerialStrip(ChangePositionDTO changePositionDTO, Session session) {
//        ResultDTO resultDTO = new ResultDTO();
//        resultDTO = gettDAO().updateCellStockGoodsSerialStrip(changePositionDTO, session);
//        return resultDTO;
        return gettDAO().updateCellStockGoodsSerialStrip(changePositionDTO, session);
    }

    //duyot
    @Override
    public ResultDTO updateNewGoods(StockGoodsSerialStripDTO oldTrip, StockGoodsSerialStripDTO newTrip, Session session) {
        return gettDAO().updateNewGoods(oldTrip, newTrip, session);
    }

    //AddBy ThienNg1
    @Override
    public ResultDTO reImportStockGoodsSerialStrip(StockGoodsSerialStripDTO oldStockGoodsSerialStripDTO,
            StockGoodsSerialStripDTO newStockGoodsSerialStripDTO, String stockTransId, Session session, String synImportRevoke) {
        ResultDTO resultDTO;
        StockGoodsSerialStrip oldStockGoodsSerialStrip = oldStockGoodsSerialStripDTO.toModel();
        StockGoodsSerialStrip newStockGoodsSerialStrip = newStockGoodsSerialStripDTO.toModel();
        resultDTO = gettDAO().reImportStockGoodsSerialStrip(oldStockGoodsSerialStrip, newStockGoodsSerialStrip, stockTransId, session, synImportRevoke);
        return resultDTO;
    }

    @Override
    public ResultDTO updateNewListGoods(StockGoodsSerialStripDTO oldTrip, List<StockGoodsSerialStrip> newTrip, Session session) {
        return gettDAO().updateNewListGoods(oldTrip, newTrip, session);
    }

    @Override
    public List<StockGoodsSerialStrip> exportStockGoodsSerialStripTransfer(StockGoodsSerialStrip oldStockGoodsSerialStrip, StockGoodsSerialStrip newStockGoodsSerialStrip, Session session) {
        return gettDAO().exportStockGoodsSerialStripTransfer(oldStockGoodsSerialStrip, newStockGoodsSerialStrip, session);
    }

    @Override
    public List<StockGoodsSerialInforDTO> getListSerilStripErrorImportRevoke(String stockTransId) {
        return gettDAO().getListSerilStripErrorImportRevoke(stockTransId);
    }

    @Override
    public String updateStockGoodsSerialStripByOrdersId(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, Session session) {
        return gettDAO().updateStockGoodsSerialStripByOrdersId(stockGoodsSerialStripDTO, session);
    }

    @Override
    public ResultDTO importStockGoodsSerialStripConnection(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, String stockTransId, Connection con) {
        return gettDAO().importStockGoodsSerialStripConnection(stockGoodsSerialStripDTO, stockTransId, con);
    }

    @Override
    public String updateStockGoodsSerialStripByOrdersIdAndGoods(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, Session session) {
        return gettDAO().updateStockGoodsSerialStripByOrdersIdAndGoods(stockGoodsSerialStripDTO, session);
    }

    @Override
    public List<StockGoodsSerialStrip> getListSerialStripKitInStockByOrderId(String orderId, StockTransSerialDTO stockTransSerialDTO, Session session) {
        return gettDAO().getListSerialStripKitInStockByOrderId(orderId, stockTransSerialDTO, session);
    }

    @Override
    public List<StockGoodsSerial> getListSerialKitInStockByOrderId(String orderId, StockTransSerialDTO stockTransSerialDTO, Session session) {
        return gettDAO().getListSerialKitInStockByOrderId(orderId, stockTransSerialDTO, session);
    }

}
