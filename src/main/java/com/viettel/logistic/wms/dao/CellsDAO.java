/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.dto.CellsDTO;
import com.viettel.logistic.wms.model.Goods;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.Cells;
import com.viettel.logistic.wms.model.GoodsPacking;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@Repository("cellsDAO")
public class CellsDAO extends BaseFWDAOImpl<Cells, Long> {
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;

    public CellsDAO() {
        this.model = new Cells();
    }

    public CellsDAO(Session session) {
        this.session = session;
    }

    public List<Cells> getListCells(Goods goods, Long stockId, Long goodsPackingId) {
        List<Cells> lstCells = new ArrayList();
        GoodsPacking goodsPacking = new GoodsPacking();
        //
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //Danh sach cell trong
        sql.append(" FROM Cells c WHERE 1=1 ");
        sql.append(" AND c.stockId = ? ");
        lstParams.add(stockId);
        //Mat hang kho co serial
        sql.append(" AND NOT EXISTS (SELECT   1 ");
        sql.append("                           FROM   StockGoods ");
        sql.append("                          WHERE   cellCode = c.code AND amount > 0) ");
        //Mat hang co serial theo dai
        sql.append(" AND NOT EXISTS (SELECT   1 ");
        sql.append("                           FROM   StockGoodsSerialStrip ");
        sql.append("                          WHERE   cellCode = c.code AND status != '3') ");
        //Mat hang serial don le 
        sql.append(" AND NOT EXISTS (SELECT   1 ");
        sql.append("                           FROM   StockGoodsSerial ");
        sql.append("                          WHERE   cellCode = c.code AND status != '3') ");
        //
        sql.append(" ORDER BY c.code ");
        //
        Query query = getSession().createQuery(sql.toString());
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        lstCells = query.list();
        //Lay so luong Cell theo mat hang
//        sql = new StringBuffer();
//        sql.append(" FROM GoodsPacking gp WHERE id =?");
//        query = getSession().createQuery(sql.toString());
//        query.setParameter(0, goodsPackingId);
//        List<GoodsPacking> lstGoodsPacking = query.list();
//        if (lstGoodsPacking != null && lstGoodsPacking.size() > 0) {
//            goodsPacking = lstGoodsPacking.get(0);
//        }
//        //
//        Long packingNumber = goodsPacking.getPackingNumber();
//        
        //
        return lstCells;
    }
    /**
     * NgocND6 insert list cell using import excel
     * @param lstCell
     * @param stockID
     * @return 
     */
     public ResultDTO insertListCells(List<Cells> lstCell, String stockID) {
        Session openSession;
        openSession = sessionFactory.openSession();
        Transaction tx = openSession.beginTransaction();
        ResultDTO result = new ResultDTO();
        Cells cells = null;
        String lstCellsId = "";
        try {
            for (int i = 0; i < lstCell.size(); i++) {
                cells = lstCell.get(i);
                lstCellsId = lstCellsId + "," + cells.getCode();
                openSession.save(cells);
                if (i % 50 == 0) { // Same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    openSession.flush();
                    openSession.clear();
                    lstCellsId = "";
                }
            }
            tx.commit();
            result.setMessage(ParamUtils.SUCCESS);
        } catch (HibernateException ex) {

            if (ex.getCause() != null && ex.getCause() instanceof SQLException) {
                SQLException se = (SQLException) ex.getCause();
                if (se.getErrorCode() == 1) {
                    openSession.clear();
                    ConditionBean condition = new ConditionBean("code", ParamUtils.OP_IN, lstCellsId.replaceFirst(",", ""), ParamUtils.TYPE_STRING);
                    ConditionBean condition1 = new ConditionBean("stockId", ParamUtils.OP_EQUAL, String.valueOf(stockID), ParamUtils.TYPE_NUMBER);
                    List<ConditionBean> lstConditionBeans = new ArrayList<>();
                    lstConditionBeans.add(condition);
                    lstConditionBeans.add(condition1);
                    List<Cells> lstCellsViolated = findSession("Cells", lstConditionBeans, "code", 0, Integer.MAX_VALUE, null, getSession());
                    if (lstCellsViolated != null && !lstCellsViolated.isEmpty()) {
                        String id = "";
                        for (Cells item : lstCellsViolated) {
                            id = id + "," + item.getCode();
                        }
                        result.setKey(id.replaceFirst(",", ""));
                    }
                }
            }
            tx.rollback();
            result.setMessage(ParamUtils.FAIL);
        } finally {
            openSession.close();
        }

        return result;
    }
}
