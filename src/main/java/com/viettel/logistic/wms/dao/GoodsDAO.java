
/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.Goods;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ThieuLQ1
 * @version 1.0
 * @since 5/19/2015 3:52 PM
 */
@Repository("goodsDAO")
public class GoodsDAO extends BaseFWDAOImpl<Goods, Long> {

    @Autowired
    HibernateSessionFactoryImpl sessionFactory;

    public GoodsDAO() {
        this.model = new Goods();
    }

    public GoodsDAO(Session session) {
        this.session = session;
    }

    //NgocNd6 Synchronize goods with KTTS system
    @Transactional
    public ResultDTO synchListGoods(List<GoodsDTO> lstGoods, Session session) {
        ResultDTO result = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        result.setMessage(ParamUtils.SUCCESS);
        StringBuffer sql = new StringBuffer();
        sql.append(" UPDATE Goods a ");
        sql.append(" SET a.name =?, a.status=?, a.goods_group=?, a.unit_type=?, a.is_serial=? ,a.is_serial_strip=?, a.origin_price=to_number(?), a.origin_size=?, a.weight=?, a.volume_origin=?, a.volume_real=?, a.description=?, a.goods_type=? ");
        sql.append(" WHERE  a.code IN (:idx0) AND a.cust_id IN (:idx1)");
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        for (GoodsDTO lstGood : lstGoods) {
            List params = new ArrayList();
            ArrayList<String> arrCode = new ArrayList<>();
            ArrayList<Integer> arrName = new ArrayList<>();
            arrCode.add(lstGood.getCode());
            arrName.add(Integer.parseInt(lstGood.getCustId()));
            params.add(lstGood.getName());
            params.add(lstGood.getStatus());
            params.add(lstGood.getGoodsGroup());
            params.add(lstGood.getUnitType());
            params.add(lstGood.getIsSerial());
            params.add(lstGood.getIsSerialStrip());
            params.add(DataUtil.getStringNullOrZero(lstGood.getOriginPrice()));
            params.add(DataUtil.getStringNullOrZero(lstGood.getOriginSize()));
            params.add(DataUtil.getStringNullOrZero(lstGood.getWeight()));
            params.add(DataUtil.getStringNullOrZero(lstGood.getVolumeOrigin()));
            params.add(DataUtil.getStringNullOrZero(lstGood.getVolumeReal()));
            params.add(DataUtil.getStringNullOrZero(lstGood.getDescription()));
            params.add(lstGood.getGoodsType());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            query.setParameterList("idx0", arrCode);
            query.setParameterList("idx1", arrName);

            try {
                int i = query.executeUpdate();
                if (i == 0) {
                    List paramsInsert = new ArrayList();
                    StringBuffer sqlInsert = new StringBuffer();
                    SQLQuery queryInsert;
                    sqlInsert.append(" INSERT INTO Goods (goods_id,cust_id,code,name,status,goods_group,unit_type,is_serial,is_serial_strip,origin_price,origin_size,weight,volume_origin,volume_real,description,goods_type) ");
                    sqlInsert.append(" VALUES (GOODS_SEQ.nextval,TO_NUMBER(?),?,?,?,?,?,?,?,TO_NUMBER(?),?,?,?,?,?,?) ");
                    paramsInsert.clear();
                    paramsInsert.add(lstGood.getCustId());
                    paramsInsert.add(lstGood.getCode());
                    paramsInsert.add(lstGood.getName());
                    paramsInsert.add(lstGood.getStatus());
                    paramsInsert.add(lstGood.getGoodsGroup());
                    paramsInsert.add(lstGood.getUnitType());
                    paramsInsert.add(lstGood.getIsSerial());
                    paramsInsert.add(lstGood.getIsSerialStrip());
                    paramsInsert.add(DataUtil.getStringNullOrZero(lstGood.getOriginPrice()));
                    paramsInsert.add(DataUtil.getStringNullOrZero(lstGood.getOriginSize()));
                    paramsInsert.add(DataUtil.getStringNullOrZero(lstGood.getWeight()));
                    paramsInsert.add(DataUtil.getStringNullOrZero(lstGood.getVolumeOrigin()));
                    paramsInsert.add(DataUtil.getStringNullOrZero(lstGood.getVolumeReal()));
                    paramsInsert.add(DataUtil.getStringNullOrZero(lstGood.getDescription()));
                    paramsInsert.add(lstGood.getGoodsType());
                    queryInsert = getSession().createSQLQuery(sqlInsert.toString());
                    for (int idx = 0; idx < paramsInsert.size(); idx++) {
                        queryInsert.setParameter(idx, paramsInsert.get(idx));
                    }
                    try {
                        int is = queryInsert.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = ParamUtils.FAIL;
                        result.setMessage(message);
                        return result;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                message = ParamUtils.FAIL;
                result.setMessage(message);
                return result;
            }
        }
        result.setMessage(message);
        return result;
    }

    public ResultDTO insertListGoods(List<Goods> lstGoods) {
        ResultDTO resultDTO = new ResultDTO();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        ResultDTO result = new ResultDTO();
        Goods goods = null;
        String lstGoodsId = "";
        long cusID = lstGoods.get(0).getCustId();
        try {
            for (int i = 0; i < lstGoods.size(); i++) {
                goods = lstGoods.get(i);
                lstGoodsId = lstGoodsId + "," + goods.getCode();
                session.save(goods);
                if (i % 50 == 0) { // Same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                    lstGoodsId = "";
                }
            }
            tx.commit();
            result.setMessage(ParamUtils.SUCCESS);
        } catch (HibernateException ex) {

            if (ex.getCause() != null && ex.getCause() instanceof SQLException) {
                SQLException se = (SQLException) ex.getCause();
//                Transaction tx2 = session.beginTransaction();
                if (se.getErrorCode() == 1) {
                    session.clear();
                    ConditionBean condition = new ConditionBean("code", ParamUtils.OP_IN, lstGoodsId.replaceFirst(",", ""), ParamUtils.TYPE_STRING);
                    ConditionBean condition1 = new ConditionBean("custId", ParamUtils.OP_EQUAL, String.valueOf(cusID), ParamUtils.TYPE_NUMBER);
                    List<ConditionBean> lstConditionBeans = new ArrayList<>();
                    lstConditionBeans.add(condition);
                    lstConditionBeans.add(condition1);
                    List<Goods> lstGoodsViolated = findSession("Goods", lstConditionBeans, "code", 0, Integer.MAX_VALUE, null, getSession());
                    if (lstGoodsViolated != null && !lstGoodsViolated.isEmpty()) {
                        String id = "";
                        for (Goods item : lstGoodsViolated) {
                            id = id + "," + item.getCode();
                        }
                        result.setKey(id.replaceFirst(",", ""));
                    }
                }
            }
            tx.rollback();
            result.setMessage(ParamUtils.FAIL);
        } finally {
            session.close();
        }

        return result;
    }

    //-------ROLL-BACK AND COMMIT
    private void rollback(Session session, Transaction transaction) {
        transaction.rollback();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    private void commit(Session session, Transaction transaction) {
        transaction.commit();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    //
    //Lay danh danh sach kho theo KH, kho, mat hang, trang thai hang
    public List<GoodsDTO> getListGoods(GoodsDTO goodsDTO) {
        List<GoodsDTO> lstGoodsDTO = new ArrayList();
        List lstParams = new ArrayList();
        //        

        StringBuffer sql = new StringBuffer();
        //        
        sql.append("SELECT   g.cust_id custId,");
        sql.append("         g.goods_id goodsId,");
        sql.append("  FROM   goods g, stock_goods_total sg");
        sql.append(" WHERE   s.stock_id = sg.owner_id");
        sql.append("     AND g.goods_id = sg.goods_id");
        sql.append("     AND sg.amount_issue > 0");

        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(goodsDTO.getCustId())) {
            sql.append("         AND sg.cust_id = ?");
            sql.append("         AND g.cust_id = ?");
            lstParams.add(goodsDTO.getCustId());
            lstParams.add(goodsDTO.getCustId());
        }

        //
        sql.append(" GROUP BY   sg.cust_id");
        sql.append(" ORDER BY   sg.cust_id");
        //Map DTO
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query
                .setResultTransformer(Transformers.aliasToBean(StockGoodsInforDTO.class
                        ));
        query.addScalar(
                "custId", new StringType());
        query.addScalar(
                "ownerId", new StringType());
        query.addScalar(
                "ownerType", new StringType());
        query.addScalar(
                "ownerCode", new StringType());
        query.addScalar(
                "ownerName", new StringType());
        query.addScalar(
                "goodsType", new StringType());
        query.addScalar(
                "goodsId", new StringType());
        query.addScalar(
                "goodsCode", new StringType());
        query.addScalar(
                "goodsName", new StringType());
        query.addScalar(
                "goodsState", new StringType());
        query.addScalar(
                "amount", new StringType());
        query.addScalar(
                "amountIssue", new StringType());
        query.addScalar(
                "unitType", new StringType());
        //Set cac gia tri tham so
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }

        //
        return query.list();
    }

    //QuyenDM 13/11/2015 Lay hang hoa gom Id, code, name
    public List<GoodsDTO> getListGoodsWithCustId(String custId) {
        List<GoodsDTO> lstGoodsDTO = new ArrayList();
        List lstParams = new ArrayList();
        //        
        if (DataUtil.isStringNullOrEmpty(custId)) {
            return lstGoodsDTO;
        }
        StringBuilder sql = new StringBuilder();
        //        
        sql.append("SELECT   a.goods_id goodsId, a.code code, a.name name, ");
        sql.append("         a.goods_type goodsType, a.unit_type unitType, ");
        sql.append("         a.is_serial isSerial, a.is_serial_strip isSerialStrip  ");
        sql.append("    FROM   goods a ");
        sql.append("    WHERE   a.cust_id = ? AND a.status = '1' ");
        //ChuDV add : 11/12/2015 -- 
        sql.append("    AND EXISTS (SELECT   1 FROM   stock_goods_total WHERE cust_id = ? AND goods_id = a.goods_id AND (amount != 0 OR amount_issue != 0))");

        //Tim kiem theo khach hang
        lstParams.add(custId.trim());
        lstParams.add(custId.trim());
        //Map DTO
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(GoodsDTO.class));
        query.addScalar(
                "goodsId", new StringType());
        query.addScalar(
                "code", new StringType());
        query.addScalar(
                "name", new StringType());
        query.addScalar(
                "goodsType", new StringType());
        query.addScalar(
                "unitType", new StringType());
        query.addScalar(
                "isSerial", new StringType());
        query.addScalar(
                "isSerialStrip", new StringType());

        //Set cac gia tri tham so
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }

        //Tra ve danh sach hang hoa
        return query.list();
    }

    /**
     * NgocND6 get list goods serial by cust id
     * @param custId
     * @param isSerial
     * @param isSerialStrip
     * @return
     */
    public List<GoodsDTO> getListGoodsSerialByCustId(String custId, String isSerial, String isSerialStrip) {
        List<GoodsDTO> lstGoodsDTO = new ArrayList();
        List lstParams = new ArrayList();
        if (DataUtil.isStringNullOrEmpty(custId)) {
            return lstGoodsDTO;
        }
        StringBuilder sql = new StringBuilder();
        //        
        sql.append("SELECT   a.goods_id goodsId, a.code code, a.name name, ");
        sql.append("         a.unit_type unitType, ");
        sql.append("         a.is_serial isSerial, a.is_serial_strip isSerialStrip, a.goods_type goodsType  ");
        sql.append("    FROM   goods a ");
        sql.append("    WHERE   a.cust_id = ? AND a.status = '1' ");
        lstParams.add(custId.trim());
        if (!DataUtil.isStringNullOrEmpty(isSerial)) {
            sql.append("     AND a.is_serial = ? ");
            lstParams.add(isSerial);
        }
        if (!DataUtil.isStringNullOrEmpty(isSerialStrip)) {
            sql.append("     AND a.is_serial_strip = ? ");
            lstParams.add(isSerialStrip);
        }
        //Map DTO
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(GoodsDTO.class));
        query.addScalar(
                "goodsId", new StringType());
        query.addScalar(
                "code", new StringType());
        query.addScalar(
                "name", new StringType());
        query.addScalar(
                "goodsType", new StringType());
        query.addScalar(
                "unitType", new StringType());
        query.addScalar(
                "isSerial", new StringType());
        query.addScalar(
                "isSerialStrip", new StringType());

        //Set cac gia tri tham so
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }

        //Tra ve danh sach hang hoa
        return query.list();
    }
}
