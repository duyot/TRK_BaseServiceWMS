/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.StockGoodsBusinessInterface;
import com.viettel.logistic.wms.dao.ManageStockDAO;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.StockGoodsDTO;
import com.viettel.logistic.wms.model.StockGoods;
import com.viettel.logistic.wms.dao.StockGoodsDAO;
import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import org.hibernate.Session;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 14-Apr-15 11:33 PM
 */
@Service("stockGoodsBusiness")
//@Transactional
public class StockGoodsBusiness extends BaseFWServiceImpl<StockGoodsDAO, StockGoodsDTO, StockGoods> implements StockGoodsBusinessInterface {

    @Autowired
    private StockGoodsDAO stockGoodsDAO;
    @Autowired
    private ManageStockDAO manageStockDAO;

    public StockGoodsBusiness() {
        tModel = new StockGoods();
        tDAO = stockGoodsDAO;
    }

    @Override
    public StockGoodsDAO gettDAO() {
        return stockGoodsDAO;
    }

    public StockGoodsBusiness(Session session) {
        this.session = session;
        tModel = new StockGoods();
        tDAO = stockGoodsDAO;
    }

    //
//    @Override
//    public ResultDTO exportStockGoods(StockGoodsDTO stockGoodsDto, Double amount, Double amountIssue, String changeDate, Session session) {
//        ResultDTO resultDTO = new ResultDTO();
//        List<ConditionBean> lstCondition = prepareCondition(stockGoodsDto);
//        lstCondition = prepareCondition(stockGoodsDto);
//        resultDTO = gettDAO().exportStockGoods(lstCondition, amount, amountIssue, changeDate, session);
//        return resultDTO;
//    }
    //Add by ChuDV: 15/05/2015
    @Override
    public ResultDTO importStockGoods(StockGoodsDTO stockGoodsDTO, Session session) {
        ResultDTO resultDTO;
        StockGoods stockGoods = stockGoodsDTO.toModel();
        resultDTO = gettDAO().importStockGoods(stockGoods, session);
        return resultDTO;
    }

    @Override
    public ResultDTO exportStockGoods(StockGoodsDTO stockGoodsDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        StockGoods stockGoods = stockGoodsDTO.toModel();
        resultDTO = gettDAO().exportStockGoods(stockGoods, session);
        return resultDTO;
    }

    @Override
    public ResultDTO waitExportStockGoods(StockGoodsDTO stockGoodsDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        StockGoods stockGoods = stockGoodsDTO.toModel();
        resultDTO = gettDAO().waitExportStockGoods(stockGoods, session);
        return resultDTO;
    }

    //
    @Override
    public List<StockGoodsInforDTO> getSumListStockGoods(StockGoodsInforDTO stockGoodsInforDTO) {
        List<StockGoodsInforDTO> lstStockGoodsInforDTO = gettDAO().getSumListStockGoods(stockGoodsInforDTO);
        return lstStockGoodsInforDTO;
    }

    //
    @Override
    public ResultDTO updateCellStockGoods(StockTransInforDTO stockTransInforDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO = gettDAO().updateCellStockGoods(stockTransInforDTO, session);
        return resultDTO;
    }

    @Override
    public Map<ChangePositionDTO, ResultDTO> updateCellStockGoods(ChangePositionDTO changePositionDTO, Session session) {
//        ResultDTO resultDTO = new ResultDTO();
//        resultDTO = gettDAO().updateCellStockGoods(changePositionDTO, session);
//        return resultDTO;
        return gettDAO().updateCellStockGoods(changePositionDTO, session);
    }

    @Override
    public ResultDTO importStockDAO(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetaiDTO, List<StockTransSerialDTO> lstStockTransSerialDTO) {
        return manageStockDAO.importStockCustDAO(stockTransDTO, lstStockTransDetaiDTO, lstStockTransSerialDTO);
    }

    @Override
    public ResultDTO updateNewGoods(StockGoodsDTO importStock, StockGoodsDTO exportedStock) {
        return gettDAO().updateNewGoods(exportedStock, exportedStock);
    }

    @Override
    public ResultDTO reImportStockGoods(StockGoodsDTO stockGoodsDTO, Session session, String synImportRevoke) {
        ResultDTO resultDTO = new ResultDTO();
        StockGoods stockGoods = stockGoodsDTO.toModel();
        resultDTO = gettDAO().reImportStockGoods(stockGoods, session, synImportRevoke);
        return resultDTO;
    }

    @Override
    public ResultDTO importListStockGoods(List<StockGoodsDTO> stockGoodsDTO, Session session) {
        return gettDAO().importListStockGoods(stockGoodsDTO, session);
    }

    @Override
    public List<StockGoods> exportStockGoodsForTransfer(StockGoodsDTO stockGoodsDTO, Session session) {
        return gettDAO().exportStockGoodsForTransfer(stockGoodsDTO.toModel(), session);
    }

    @Override
    public String updateStockGoodsByOrdersId(StockGoodsDTO stockGoodsDTO, Session session) {
        return gettDAO().updateStockGoodsByOrdersId(stockGoodsDTO, session);
    }

    @Override
    public List<StockGoodsSerialInforDTO> getStockGoodsInforInventory(StockGoodsSerialInforDTO stockGoodsSerialInforDTO, String isSerial, String isExportSerial) {
        return gettDAO().getStockGoodsInforInventory(stockGoodsSerialInforDTO, isSerial, isExportSerial);
    }
   

}
