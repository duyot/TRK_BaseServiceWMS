/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.ctc.wstx.util.DataUtil;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.StockGoodsTotal;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 11:36 AM
 */
@Repository("stockGoodsTotalDAO")
public class StockGoodsTotalDAO extends BaseFWDAOImpl<StockGoodsTotal, Long> {



    public StockGoodsTotalDAO() {
        this.model = new StockGoodsTotal();
    }

    public StockGoodsTotalDAO(Session session) {
        this.session = session;
    }

    //ChuDV: 06/05/2015
    public ResultDTO exportStockGoodsTotal(List<ConditionBean> lstCondition, Double amount, Double amountIssue, String changeDate, Session sessions) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        Double amountUpdate;
        Double amountIssueUpdate;
        //Tim kiem kho hang
        List<StockGoodsTotal> lstStockGoodsTotal = find(model.getModelName(), lstCondition, "", 0, 1, null);
        if (lstStockGoodsTotal != null && lstStockGoodsTotal.size() > 0) {
            //
            resultDTO.setId(lstStockGoodsTotal.get(0).getId().toString());
            //
            amountUpdate = lstStockGoodsTotal.get(0).getAmount() + amount;
            amountIssueUpdate = lstStockGoodsTotal.get(0).getAmountIssue() + amountIssue;
            if (amountUpdate < 0D || amountIssueUpdate < 0D) {
                resultDTO.setMessage(ParamUtils.FAIL);
                resultDTO.setKey(String.valueOf(lstStockGoodsTotal.get(0).getGoodsId()));
                return resultDTO;
            }
            //Set so luong thuc te, dap uang
            lstStockGoodsTotal.get(0).setAmount(amountUpdate);
            lstStockGoodsTotal.get(0).setAmountIssue(amountIssueUpdate);
            try {
                //Set ngay thay doi
                lstStockGoodsTotal.get(0).setChangeDate(DateTimeUtils.convertStringToDate(changeDate));
            } catch (Exception ex) {
                Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Luu du lieu: trong cung session lay ban ghi ra de cap nhat thi phai dung save ko dung update
            sessions.evict(lstStockGoodsTotal.get(0));
            updateSession(lstStockGoodsTotal.get(0), sessions);
        } else { //Neu khong tim thay thi tra ve ma loi
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }

        //Tra ket qua
        resultDTO.setKey(key);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    //truongbx3 03/12/2015
    public ResultDTO exportStockGoodsTotalForSyn(List<ConditionBean> lstCondition, Double amount, Double amountIssue, String changeDate, Session sessions, StockGoodsTotal stockGoodsTotal) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        Double amountUpdate;
        Double amountIssueUpdate;
        //Tim kiem kho hang
        List<StockGoodsTotal> lstStockGoodsTotal = find(model.getModelName(), lstCondition, "", 0, 1, null);
        if (lstStockGoodsTotal != null && lstStockGoodsTotal.size() > 0) {
            //
            resultDTO.setId(lstStockGoodsTotal.get(0).getId().toString());
            //
            amountUpdate = lstStockGoodsTotal.get(0).getAmount() + amount;
            amountIssueUpdate = lstStockGoodsTotal.get(0).getAmountIssue() + amountIssue;

            //Set so luong thuc te, dap uang
            lstStockGoodsTotal.get(0).setAmount(amountUpdate);
            lstStockGoodsTotal.get(0).setAmountIssue(amountIssueUpdate);
            try {
                //Set ngay thay doi
                lstStockGoodsTotal.get(0).setChangeDate(DateTimeUtils.convertStringToDate(changeDate));
            } catch (Exception ex) {
                Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Luu du lieu: trong cung session lay ban ghi ra de cap nhat thi phai dung save ko dung update
            sessions.evict(lstStockGoodsTotal.get(0));
            updateSession(lstStockGoodsTotal.get(0), sessions);
        } else { //Neu khong tim thay thi tra ve ma loi
//            message = ParamUtils.FAIL;
//            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
            stockGoodsTotal.setAmount(amount);
            stockGoodsTotal.setAmountIssue(amountIssue);
            sessions.save(stockGoodsTotal);
        }

        //Tra ket qua
        resultDTO.setKey(key);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    //

    //Add by ChuDV: 15/05/2015; Cap nhat nhap kho
    public ResultDTO importStockGoodsTotal(StockGoodsTotal stockGoodsTotal, Session session) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc = 0;
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" UPDATE stock_goods_total ");
            sql.append(" SET amount = amount + ?, amount_issue = amount_issue + ?,");
            sql.append("     change_date = ? ");
            params.add(stockGoodsTotal.getAmount());
            params.add(stockGoodsTotal.getAmountIssue());
            params.add(stockGoodsTotal.getChangeDate());
            sql.append(" WHERE cust_id=? AND owner_id=? AND owner_type=? ");
            sql.append("   AND goods_id=? AND goods_state=? ");
            //
            params.add(stockGoodsTotal.getCustId());
            params.add(stockGoodsTotal.getOwnerId());
            params.add(stockGoodsTotal.getOwnerType());
            params.add(stockGoodsTotal.getGoodsId());
            params.add(stockGoodsTotal.getGoodsState());
            //

            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            quantitySucc = query.executeUpdate();
            if (quantitySucc < 1) {
                insertStockGoodsTotal(stockGoodsTotal, session);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(StockGoodsTotalDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }
        //
        resultDTO.setKey(key);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    private void insertStockGoodsTotal(StockGoodsTotal stockGoodsTotal, Session session) {

        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        //
        sql.append("INSERT INTO stock_goods_total ");
        sql.append("(id,cust_id,owner_id,owner_type,goods_id,goods_state,amount,amount_issue,change_date) ");
        sql.append(" VALUES (STOCK_GOODS_TOTAL_SEQ.nextval,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?) ");
        //
        params.add(stockGoodsTotal.getCustId());
        params.add(stockGoodsTotal.getOwnerId());
        params.add(stockGoodsTotal.getOwnerType());
        params.add(stockGoodsTotal.getGoodsId());
        params.add(stockGoodsTotal.getGoodsState());
        params.add(stockGoodsTotal.getAmount());
        params.add(stockGoodsTotal.getAmountIssue());
        params.add(stockGoodsTotal.getChangeDate());
        //
        Query query = session.createSQLQuery(sql.toString());
        for (int idx = 0; idx < params.size(); idx++) {
            query.setParameter(idx, params.get(idx));
        }
        query.executeUpdate();
    }

    //Add by ChuDV: 14/05/2015; Cap nhat xuat kho
    public ResultDTO exportStockGoodsTotal(StockGoodsTotal stockGoodsTotal, Session session) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc = 0;
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" UPDATE stock_goods_total ");
            sql.append(" SET amount = amount - ?, amount_issue = amount_issue - ?,");
            sql.append("     change_date = ? ");
            params.add(stockGoodsTotal.getAmount());
            params.add(stockGoodsTotal.getAmountIssue());
            params.add(stockGoodsTotal.getChangeDate());
            sql.append(" WHERE cust_id=? AND owner_id=? AND owner_type=? ");
            sql.append("   AND goods_id=? AND goods_state=? ");
            //
            params.add(stockGoodsTotal.getCustId());
            params.add(stockGoodsTotal.getOwnerId());
            params.add(stockGoodsTotal.getOwnerType());
            params.add(stockGoodsTotal.getGoodsId());
            params.add(stockGoodsTotal.getGoodsState());
            //
            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            quantitySucc = query.executeUpdate();
            if (quantitySucc != 1) {
                key = ParamUtils.NOT_ENOUGH_AMOUNT;
//                message = ParamUtils.FAIL;
                // tiepnv6, edit 26/06/15: tra ve goods id loi
                message = String.valueOf(stockGoodsTotal.getGoodsId()) + "," + stockGoodsTotal.getGoodsState();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(StockGoodsTotalDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }
        //
        resultDTO.setKey(key);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    //
    public List<StockGoodsInforDTO> getSumListStockGoods(StockGoodsInforDTO stockGoodsInforDTO) {
        //Bao cao theo nhan vien
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getStaffId())) {
            return getSumListGoodsByStaff(stockGoodsInforDTO);
        } else {
            //Bao cao tat ca
            return getSumListGoodsAll(stockGoodsInforDTO);
        }
    }

    //
    //Lay danh danh sach kho theo KH, kho, mat hang, trang thai hang
    public List<StockGoodsInforDTO> getSumListGoodsAll(StockGoodsInforDTO stockGoodsInforDTO) {
        List lstParams = new ArrayList();
        StringBuilder sql = new StringBuilder();
        //        
        sql.append("SELECT   sg.cust_id custId,");
        sql.append("         s.stock_id ownerId,");
        sql.append("         sg.owner_type ownerType,");
        sql.append("         s.code ownerCode,");
        sql.append("         s.name ownerName,");
        sql.append("         g.goods_type goodsType,");
        sql.append("         g.goods_id goodsId,");
        sql.append("         g.code goodsCode,");
        sql.append("         g.name goodsName,");
        sql.append("         sg.goods_state goodsState,");
        sql.append("         SUM(sg.amount) amount,");
        sql.append("         SUM(sg.amount_issue) amountIssue,");
        sql.append("         g.unit_type unitType ");
        sql.append("  FROM   stock_goods_total sg, goods g, stock s ");
        sql.append(" WHERE   s.stock_id = sg.owner_id");
        sql.append("         AND g.goods_id = sg.goods_id");        
        //ChuDV add 11/12/2015  --Lay cac mat hang con trong kho       
        sql.append("         AND (sg.amount != 0 OR sg.amount_issue != 0) ");
        //
        //tim kiem theo dieu kien hang hoa co hieu luc
        sql.append("         AND g.status = ?");
        lstParams.add(Constants.ACTIVE_STATUS);
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getCustId())) {
            sql.append("     AND sg.cust_id = ?");
            sql.append("     AND g.cust_id = ?");
            lstParams.add(stockGoodsInforDTO.getCustId());
            lstParams.add(stockGoodsInforDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getOwnerId())) {
            sql.append("     AND sg.owner_id = ?");
            lstParams.add(stockGoodsInforDTO.getOwnerId());
        }
        //Tim kiem theo loai kho
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getOwnerType())) {
            sql.append("     AND sg.owner_type = ?");
            lstParams.add(stockGoodsInforDTO.getOwnerType());
        }
        //Tim kiem theo nhom mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getGoodsType())) {
            sql.append("      AND g.goods_type = ?");
            lstParams.add(stockGoodsInforDTO.getGoodsType());
        }
        //Tim kiem theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getGoodsId())) {
            //sql.append("      AND g.goods_id = ?");
            //lstParams.add(stockGoodsInforDTO.getGoodsId());
            sql.append("AND g.goods_id IN (");
            sql.append(stockGoodsInforDTO.getGoodsId());
            sql.append(") ");
        }
        //Tim kiem theo trang thai hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getGoodsState())) {
            sql.append("      AND sg.goods_state = ?");
            lstParams.add(stockGoodsInforDTO.getGoodsState());
        }
        //Tim kiem theo don vi tinh
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getUnitType())) {
            sql.append("      AND g.unit_type = ?");
            lstParams.add(stockGoodsInforDTO.getUnitType());
        }
        //
        sql.append(" GROUP BY sg.cust_id,s.stock_id,sg.owner_type,s.code,s.name,g.goods_type,g.goods_id,g.code, g.name,sg.goods_state,g.unit_type ");
        sql.append(" ORDER BY sg.cust_id,s.code,g.goods_type,g.code,g.name,sg.goods_state,g.unit_type");
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
        for (int i = 0;
                i < lstParams.size();
                i++) {
            query.setParameter(i, lstParams.get(i));
        }

        //
        return query.list();
    }

    //Lay danh danh sach kho theo KH, kho, mat hang, trang thai hang
    public List<StockGoodsInforDTO> getSumListGoodsByStaff(StockGoodsInforDTO stockGoodsInforDTO) {
        List lstParams = new ArrayList();
        StringBuilder sql = new StringBuilder();
        //        
        sql.append("SELECT   sg.cust_id custId,");
        sql.append("         s.stock_id ownerId,");
        sql.append("         sg.owner_type ownerType,");
        sql.append("         s.code ownerCode,");
        sql.append("         s.name ownerName,");
        sql.append("         g.goods_type goodsType,");
        sql.append("         g.goods_id goodsId,");
        sql.append("         g.code goodsCode,");
        sql.append("         g.name goodsName,");
        sql.append("         sg.goods_state goodsState,");
        sql.append("         SUM(sg.amount) amount,");
        sql.append("         SUM(sg.amount_issue) amountIssue,");
        sql.append("         g.unit_type unitType,");
        sql.append("         msg.staff_id staffId, msg.staff_code staffCode, msg.staff_name staffName ");
        sql.append("  FROM   stock_goods_total sg, goods g, stock s, map_staff_goods msg ");
        sql.append(" WHERE   s.stock_id = sg.owner_id");
        sql.append("         AND g.goods_id = sg.goods_id");
        sql.append("         AND sg.goods_id = msg.goods_id(+)");
        //
        //ChuDV add 11/12/2015  --Lay cac mat hang con trong kho       
        sql.append("         AND (sg.amount != 0 OR sg.amount_issue != 0) ");
        //Tim kiem theo nhan vien
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getStaffId())) {
            sql.append("     AND msg.staff_id = ?");
            lstParams.add(stockGoodsInforDTO.getStaffId());
        }
        //tim kiem theo dieu kien hang hoa co hieu luc
        sql.append("         AND g.status = ?");
        lstParams.add(Constants.ACTIVE_STATUS);
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getCustId())) {
            sql.append("     AND sg.cust_id = ?");
            sql.append("     AND g.cust_id = ?");
            lstParams.add(stockGoodsInforDTO.getCustId());
            lstParams.add(stockGoodsInforDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getOwnerId())) {
            sql.append("     AND sg.owner_id = ?");
            lstParams.add(stockGoodsInforDTO.getOwnerId());
        }
        //Tim kiem theo loai kho
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getOwnerType())) {
            sql.append("     AND sg.owner_type = ?");
            lstParams.add(stockGoodsInforDTO.getOwnerType());
        }
        //Tim kiem theo nhom mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getGoodsType())) {
            sql.append("      AND g.goods_type = ?");
            lstParams.add(stockGoodsInforDTO.getGoodsType());
        }
        //Tim kiem theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getGoodsId())) {
            //sql.append("      AND g.goods_id = ?");
            //lstParams.add(stockGoodsInforDTO.getGoodsId());
            sql.append("AND g.goods_id IN (");
            sql.append(stockGoodsInforDTO.getGoodsId());
            sql.append(") ");
        }
        //Tim kiem theo trang thai hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getGoodsState())) {
            sql.append("      AND sg.goods_state = ?");
            lstParams.add(stockGoodsInforDTO.getGoodsState());
        }
        //Tim kiem theo don vi tinh
        if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getUnitType())) {
            sql.append("      AND g.unit_type = ?");
            lstParams.add(stockGoodsInforDTO.getUnitType());
        }
        //
        sql.append(" GROUP BY sg.cust_id,s.stock_id,sg.owner_type,s.code,s.name,g.goods_type,g.goods_id,g.code, g.name,sg.goods_state,g.unit_type, ");
        sql.append("          msg.staff_id, msg.staff_code, msg.staff_name ");
        sql.append(" ORDER BY sg.cust_id,s.code,g.goods_type,g.code,g.name,sg.goods_state,g.unit_type");
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
        query.addScalar(
                "staffId", new StringType());
        query.addScalar(
                "staffCode", new StringType());
        query.addScalar(
                "staffName", new StringType());
        //Set cac gia tri tham so
        for (int i = 0;
                i < lstParams.size();
                i++) {
            query.setParameter(i, lstParams.get(i));
        }

        //
        return query.list();
    }
}
