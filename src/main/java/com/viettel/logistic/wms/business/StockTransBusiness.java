/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.CommonBusinessInterface;
import com.viettel.logistic.wms.business.service.StockTransBusinessInterface;
import com.viettel.logistic.wms.dao.ManageStockDAO;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.model.StockTrans;
import com.viettel.logistic.wms.dao.StockTransDAO;
import com.viettel.logistic.wms.dao.StockTransDetailDAO;
import com.viettel.logistic.wms.dao.StockTransSerialDAO;
import com.viettel.logistic.wms.dto.CardStockInforDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.ParamUtils;
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
 * @since 08-May-15 5:25 PM
 */
@Service("stockTransBusiness")
@Transactional
public class StockTransBusiness extends BaseFWServiceImpl<StockTransDAO, StockTransDTO, StockTrans> implements CommonBusinessInterface, StockTransBusinessInterface {

    @Autowired
    private StockTransDAO stockTransDAO;

    @Autowired
    private ManageStockDAO manageStockDAO;

    @Autowired
    private StockTransDetailDAO stockTransDetailDAO;

    @Autowired
    private StockTransSerialDAO stockTransSerialDAO;

    public StockTransBusiness() {
        tModel = new StockTrans();
        tDAO = stockTransDAO;
    }

    @Override
    public StockTransDAO gettDAO() {
        return stockTransDAO;
    }

    public StockTransBusiness(Session session) {
        this.session = session;
        tModel = new StockTrans();
        tDAO = stockTransDAO;
    }

    @Override
    public List<StockTransInforDTO> getListStockTransInfor(StockTransInforDTO stockTransInforDTO) {
        List<StockTransInforDTO> lstStockTransInforDTO = gettDAO().getListStockTransInfor(stockTransInforDTO);
        return lstStockTransInforDTO;
    }

    @Override
    public List<CardStockInforDTO> getListCardStockInfor(List<CardStockInforDTO> lstCardStockInforDTO) {
        List<CardStockInforDTO> lstCardStockInfor = gettDAO().getListCardStockInfor(lstCardStockInforDTO);
        return lstCardStockInfor;
    }

    public ResultDTO importStockCustDAO(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetailDTO, List<StockTransSerialDTO> lstStockTransSerialDTO) {
        return manageStockDAO.importStockCustDAO(stockTransDTO, lstStockTransDetailDTO, lstStockTransSerialDTO);
    }

    @Override
    //duyot: 15/09: luu thong tin stock_trans by connection
    public ResultDTO insertStockTrans(StockTransDTO stockTransDTO, Connection con) {
        return gettDAO().insertStockTrans(stockTransDTO, con);
    }

    @Override
    //duyot: 15/09: luu thong tin stock_trans by connection
    public ResultDTO insertStockTransDetail(StockTransDetailDTO stockTransDetailDTO, Connection con) {
        return stockTransDetailDAO.insertStockTransDetail(stockTransDetailDTO, con);
    }

    @Override
    public String updateStockTransByOrdersId(StockTransDTO stockTransDTO, Session session) {
        String message = ParamUtils.SUCCESS;
        message = gettDAO().updateStockTransByOrdersId(stockTransDTO, session);
        return message;
    }

    @Override
    public ResultDTO importStockTransSerialConnection(StockTransSerialDTO stockTransSerial, Connection con) {
        return stockTransSerialDAO.insertStockTransSerialConnection(stockTransSerial, con);
    }

    @Override
    public ResultDTO updateSynTransCode(String stockTransId, String synTransCode, Connection connection) {
        return stockTransDAO.updateSynTransCode(stockTransId, synTransCode, connection);
    }

    @Override
    public ResultDTO updateSynTransCodeUsingSession(StockTransDTO stockTransDTO, Session session) {
        return stockTransDAO.updateSynTransCodeUsingSession(stockTransDTO.toModel(), session);
    }

    @Override
    public List<StockTransDTO> getListStockTrans2Inventory(StockTransDTO stockTransDTO,
            String fromDate, String toDate, String stockTransType) {
        return stockTransDAO.getListStockTrans2Inventory(stockTransDTO, fromDate, toDate, stockTransType);
    }
}
