/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.StockGoodsTotalBusinessInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.StockGoodsTotalDTO;
import com.viettel.logistic.wms.model.StockGoodsTotal;
import com.viettel.logistic.wms.dao.StockGoodsTotalDAO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 11:36 AM
 */
@Service("stockGoodsTotalBusiness")
@Transactional
public class StockGoodsTotalBusiness extends BaseFWServiceImpl<StockGoodsTotalDAO, StockGoodsTotalDTO, StockGoodsTotal> implements StockGoodsTotalBusinessInterface {

    @Autowired
    private StockGoodsTotalDAO stockGoodsTotalDAO;
    String formatDate = "dd/mm/yyyy hh24:mi:ss";
    public StockGoodsTotalBusiness() {
        tModel = new StockGoodsTotal();
        tDAO = stockGoodsTotalDAO;
    }

    @Override
    public StockGoodsTotalDAO gettDAO() {
        return stockGoodsTotalDAO;
    }

    public StockGoodsTotalBusiness(Session session) {
        this.session = session;
        tModel = new StockGoodsTotal();
        tDAO = stockGoodsTotalDAO;
    }

//    @Override
//    public ResultDTO updateStockGoodsTotal(StockGoodsTotalDTO stockGoodsToalDTO, Double amount, Double amountIssue, String changeDate) {
//       ResultDTO resultDTO = new ResultDTO();
//        List<ConditionBean> lstCondition = prepareCondition(stockGoodsToalDTO);
//        lstCondition = prepareCondition(stockGoodsToalDTO);
//       resultDTO = gettDAO().updateStockGoodsTotal(lstCondition, amount, amountIssue, changeDate);
//       return resultDTO;
//    }
    //
    @Override
    public ResultDTO exportStockGoodsTotal(StockGoodsTotalDTO stockGoodsToalDTO, Double amount, Double amountIssue, String changeDate, Session session) {

        ResultDTO resultDTO = new ResultDTO();
        List<ConditionBean> lstCondition = prepareCondition(stockGoodsToalDTO);
        lstCondition = prepareCondition(stockGoodsToalDTO);
//        resultDTO = gettDAO().updateStockGoodsTotal(lstCondition, amount, amountIssue, changeDate);
//        return resultDTO;
        resultDTO = gettDAO().exportStockGoodsTotal(lstCondition, amount, amountIssue, changeDate, session);
        return resultDTO;
    }
    
    @Override
    public ResultDTO exportStockGoodsTotalForSyn(StockGoodsTotalDTO stockGoodsToalDTO, Double amount, Double amountIssue, String changeDate, Session session) {

        ResultDTO resultDTO = new ResultDTO();
        List<ConditionBean> lstCondition = prepareCondition(stockGoodsToalDTO);
//        lstCondition = prepareCondition(stockGoodsToalDTO);
//         getSysDate(changeDate);
//        resultDTO = gettDAO().updateStockGoodsTotal(lstCondition, amount, amountIssue, changeDate);
//        return resultDTO;
         stockGoodsToalDTO.setChangeDate(getSysDate(formatDate));
        resultDTO = gettDAO().exportStockGoodsTotalForSyn(lstCondition, amount, amountIssue, changeDate, session,stockGoodsToalDTO.toModel());
        return resultDTO;
    }

    @Override
    public List<StockGoodsTotalDTO> demoGoodsTotal() {
        StringBuilder sql = new StringBuilder();
        sql.append(" from ");
        sql.append(" StockGoodsTotal");
        Query query = getSession().createQuery(sql.toString());
        return query.list();
    }

    //Add bu ChuDV: 15/05/2015
    @Override
    public ResultDTO importStockGoodsTotal(StockGoodsTotalDTO stockGoodsToalDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        StockGoodsTotal stockGoodsTotal = stockGoodsToalDTO.toModel();
        resultDTO = gettDAO().importStockGoodsTotal(stockGoodsTotal, session);
        return resultDTO;
    }

    @Override
    public ResultDTO exportStockGoodsTotal(StockGoodsTotalDTO stockGoodsToalDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        StockGoodsTotal stockGoodsTotal = stockGoodsToalDTO.toModel();
        resultDTO = gettDAO().exportStockGoodsTotal(stockGoodsTotal, session);
        return resultDTO;
    }

    @Override
    public List<StockGoodsInforDTO> getSumListStockGoods(StockGoodsInforDTO stockGoodsInforDTO) {
        return gettDAO().getSumListStockGoods(stockGoodsInforDTO);
    }

}
