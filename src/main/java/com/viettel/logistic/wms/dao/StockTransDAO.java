/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.dto.CardStockInforDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.StockTrans;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 5:25 PM
 */
@Repository("stockTransDAO")
public class StockTransDAO extends BaseFWDAOImpl<StockTrans, Long> {

    public StockTransDAO() {
        this.model = new StockTrans();
    }

    public StockTransDAO(Session session) {
        this.session = session;
    }

    //duyot - 27/07/2015: update stocktrans by sql
    public ResultDTO insertStockTrans(StockTrans stockTrans, Session session) {
        ResultDTO result = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = stockTrans.getStockTransId() + "";
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();

        sql.append("INSERT INTO stock_trans ");
        sql.append("(stock_trans_id,dept_id,cust_id,stock_trans_type,stock_trans_date,owner_id,owner_type,ie_owner_id,ie_owner_type, reason_id,stock_trans_status, ");
        sql.append(" from_stock_trans_id,notes, trans_user_id, order_id_list, order_date,create_datetime, cust_code,cust_name, ");
        sql.append(" is_update_cell, stock_trans_code, order_id, partner_id ) ");
        sql.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
        //
        params.add(stockTrans.getStockTransId());//
        params.add(stockTrans.getDeptId());////
        params.add(stockTrans.getCustId());//
        params.add(stockTrans.getStockTransType());//
        params.add(stockTrans.getStockTransDate());//
        params.add(stockTrans.getOwnerId());//
        params.add(stockTrans.getOwnerType());//
        if (stockTrans.getIeOwnerId() != null) {
            params.add(stockTrans.getIeOwnerId());//
        } else {
            params.add("");//
        }
        if (stockTrans.getIeOwnerType() != null) {
            params.add(stockTrans.getIeOwnerType());//
        } else {
            params.add("");//
        }
        if (stockTrans.getReasonId() != null) {
            params.add(stockTrans.getReasonId());//
        } else {
            params.add("");//
        }
        params.add(stockTrans.getStockTransStatus());//
        if (stockTrans.getFromStockTransId() != null) {
            params.add(stockTrans.getFromStockTransId());//
        } else {
            params.add("");//
        }
        //
        String note = stockTrans.getNotes();
        if (!StringUtils.isStringNullOrEmpty(note)) {
            params.add(note);//
        } else {
            params.add("");//
        }
        //
        params.add(stockTrans.getTransUserId());//
        //
        if (stockTrans.getOrderIdList() != null) {
            params.add(stockTrans.getOrderIdList());//
        } else {
            params.add("");//
        }
        //
        if (stockTrans.getOrderDate() != null) {
            params.add(stockTrans.getOrderDate());//
        } else {
            params.add("");//

        }
        //
        params.add(stockTrans.getCreateDatetime());
        params.add(stockTrans.getCustCode());//
        params.add(stockTrans.getCustName());//
        params.add(stockTrans.getIsUpdateCell());//
        params.add(stockTrans.getStockTransCode());//
        String orderIdList = stockTrans.getOrderIdList();
        if (orderIdList != null) {
            params.add(orderIdList);//
        } else {
            params.add("");//
        }
        Long partner = stockTrans.getPartnerId();
        if (partner != null) {
            params.add(partner);//
        } else {
            params.add("");//
        }
        //
        //
        Query query = session.createSQLQuery(sql.toString());
        for (int idx = 0; idx < params.size(); idx++) {
            query.setParameter(idx, params.get(idx));
        }
        try {
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }

        //
        result.setKey(key);
        result.setMessage(message);
        return result;
        //

    }
    /*
    duyot: cap nhat stock_trans thogn qua session
     */

    public ResultDTO updateSynTransCodeUsingSession(StockTrans stockTrans, Session session) {
        ResultDTO result = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = stockTrans.getStockTransId() + "";
        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE stock_trans SET syn_trans_code = ?,add_infor = ? where stock_trans_id = ? ");
        //
        Query query = session.createSQLQuery(sql.toString());
        query.setParameter(0, stockTrans.getSynTransCode());
        query.setParameter(1, stockTrans.getAddInfor());
        query.setParameter(2, stockTrans.getStockTransId());
        try {
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }

        //
        result.setKey(key);
        result.setMessage(message);
        return result;
        //
    }
    /*
    duyot: cap nhat syn_trans_code
     */

    public ResultDTO updateSynTransCode(String stockTransId, String synTransCode, Connection connection) {
        ResultDTO result = new ResultDTO();
        StringBuilder sql = new StringBuilder();
        //
        String message = ParamUtils.SUCCESS;
        String key = stockTransId;

        sql.append(" UPDATE stock_trans SET syn_trans_code = ? where stock_trans_id = ? ");

        try {
            PreparedStatement stm = connection.prepareStatement(sql.toString());

            stm.setString(1, (String) DataUtil.nvl(synTransCode));
            stm.setString(2, (String) DataUtil.nvl(stockTransId));

            int num = stm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            Logger.getLogger(StockTransDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }
        result.setKey(key);
        result.setMessage(message);
        return result;
        //
    }

    public ResultDTO insertStockTrans(StockTransDTO stockTrans, Connection connection) {
        ResultDTO result = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = stockTrans.getStockTransId() + "";
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();

        sql.append("INSERT INTO stock_trans ");
        sql.append("(stock_trans_id,dept_id,cust_id,stock_trans_type,stock_trans_date,owner_id,owner_type,ie_owner_id,ie_owner_type, reason_id,stock_trans_status, ");
        sql.append(" from_stock_trans_id,notes, trans_user_id, order_id_list, order_date,create_datetime, cust_code,cust_name, ");
        sql.append(" is_update_cell, stock_trans_code, order_id, partner_id,attach_file_name,ORDER_CODE,ORDER_ACTION_CODE,TRANS_USER_NAME,order_command_code,SYN_TRANS_CODE ) ");
        sql.append(" VALUES (?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,?,?) ");
        //
        params.add(stockTrans.getStockTransId());//
        params.add(stockTrans.getDeptId());////
        params.add(stockTrans.getCustId());//
        params.add(stockTrans.getStockTransType());//
        params.add(stockTrans.getStockTransDate());//
        params.add(stockTrans.getOwnerId());//
        params.add(stockTrans.getOwnerType());//
        params.add(DataUtil.nvl(stockTrans.getIeOwnerId(), ""));//
        params.add(DataUtil.nvl(stockTrans.getIeOwnerType(), ""));//
        params.add(DataUtil.nvl(stockTrans.getReasonId(), ""));//
        params.add(stockTrans.getStockTransStatus());//
        params.add(DataUtil.nvl(stockTrans.getFromStockTransId(), ""));//
        params.add(DataUtil.nvl(stockTrans.getNotes(), ""));//
        params.add(stockTrans.getTransUserId());//
        params.add(DataUtil.nvl(stockTrans.getOrderIdList(), ""));//
        params.add(DataUtil.nvl(stockTrans.getOrderDate(), ""));//
        params.add(stockTrans.getCreateDatetime());
        params.add(stockTrans.getCustCode());//
        params.add(stockTrans.getCustName());//
        params.add(stockTrans.getIsUpdateCell());//
        params.add(stockTrans.getStockTransCode());//
        params.add(DataUtil.nvl(stockTrans.getOrderIdList(), ""));//
        params.add(DataUtil.nvl(stockTrans.getPartnerId(), ""));//
        params.add(DataUtil.nvl(stockTrans.getAttachFileName(), ""));//
        //
        params.add(DataUtil.nvl(stockTrans.getOrderCode(), ""));//
        params.add(DataUtil.nvl(stockTrans.getOrderActionCode(), ""));//
        params.add(DataUtil.nvl(stockTrans.getTransUserName(), ""));//
        //duyot: 14/04
        params.add(DataUtil.nvl(stockTrans.getOrderCommandCode(), ""));//
        //QuyenDM: 05/04/2016
        params.add(DataUtil.nvl(stockTrans.getSynTransCode(), ""));//

        try {
            PreparedStatement stm = connection.prepareStatement(sql.toString());

            for (int idx = 0; idx < params.size(); idx++) {
                stm.setString(idx + 1, (String) DataUtil.nvl(params.get(idx), ""));
            }
            int num = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StockTransDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }
        result.setKey(key);
        result.setMessage(message);
        return result;
        //

    }

    public List<StockTransInforDTO> getListStockTransInfor(StockTransInforDTO stockTransInforDTO) {
        List<StockTransInforDTO> lstStockGoodsSerialStrip = null;
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //
        sql.append("SELECT   st.stock_trans_id stockTransId, ");
        sql.append("         to_char(st.stock_trans_date,'dd/mm/yyyy') stockTransDate, ");
        sql.append("         st.stock_trans_status stockTransStatus, ");
        sql.append("         st.cust_id custId, ");
        sql.append("         st.owner_id ownerId, ");
        sql.append("         st.owner_type ownerType, ");
        sql.append("         std.goods_id goodsId, ");
        sql.append("         std.goods_code goodsCode, ");
        sql.append("         std.goods_name goodsName, ");
        sql.append("         std.goods_is_serial goodsIsSerial, ");
        sql.append("         std.goods_is_serial_strip goodsIsSerialStrip, ");
        sql.append("         std.goods_state goodsState, ");
        sql.append("         '' barcode, ");
        sql.append("         '' cellCode ");
        sql.append("  FROM   stock_trans st, stock_trans_detail std ");
        sql.append(" WHERE       st.stock_trans_id = std.stock_trans_id ");
        sql.append("         AND st.stock_trans_date >= TO_DATE (?, 'dd/mm/yyyy') ");
        sql.append("         AND st.stock_trans_date < TO_DATE (?, 'dd/mm/yyyy') + 1 ");
        sql.append("         AND std.stock_trans_date >= TO_DATE (?, 'dd/mm/yyyy') ");
        sql.append("         AND std.stock_trans_date < TO_DATE (?, 'dd/mm/yyyy') + 1 ");
        sql.append("         AND std.goods_is_serial != '1' ");
        sql.append("         AND st.stock_trans_id = ? ");
        sql.append("UNION ALL ");
        sql.append("SELECT   st.stock_trans_id stockTransId, ");
        sql.append("         to_char(st.stock_trans_date,'dd/mm/yyyy') stockTransDate, ");
        sql.append("         st.stock_trans_status stockTransStatus, ");
        sql.append("         st.cust_id custId, ");
        sql.append("         st.owner_id ownerId, ");
        sql.append("         st.owner_type ownerType, ");
        sql.append("         std.goods_id goodsId, ");
        sql.append("         std.goods_code goodsCode, ");
        sql.append("         std.goods_name goodsName, ");
        sql.append("         std.goods_is_serial goodsIsSerial, ");
        sql.append("         std.goods_is_serial_strip goodsIsSerialStrip, ");
        sql.append("         std.goods_state goodsState, ");
        sql.append("         '' barcode, ");
        sql.append("         '' cellCode ");
        sql.append("  FROM   stock_trans st, stock_trans_detail std, stock_trans_serial sts ");
        sql.append(" WHERE       st.stock_trans_id = sts.stock_trans_id ");
        sql.append("         AND std.stock_trans_detail_id = sts.stock_trans_detail_id ");
        sql.append("         AND st.stock_trans_date >= TO_DATE (?, 'dd/mm/yyyy') ");
        sql.append("         AND st.stock_trans_date < TO_DATE (?, 'dd/mm/yyyy') + 1 ");
        sql.append("         AND std.stock_trans_date >= TO_DATE (?, 'dd/mm/yyyy') ");
        sql.append("         AND std.stock_trans_date < TO_DATE (?, 'dd/mm/yyyy') + 1 ");
        sql.append("         AND sts.stock_trans_date >= TO_DATE (?, 'dd/mm/yyyy') ");
        sql.append("         AND sts.stock_trans_date < TO_DATE (?, 'dd/mm/yyyy') + 1 ");
        sql.append("         AND st.stock_trans_id = ? ");
        //        
        lstParams.add(stockTransInforDTO.getFromDate());
        lstParams.add(stockTransInforDTO.getToDate());
        lstParams.add(stockTransInforDTO.getFromDate());
        lstParams.add(stockTransInforDTO.getToDate());
        lstParams.add(stockTransInforDTO.getStockTransId());
        //
        lstParams.add(stockTransInforDTO.getFromDate());
        lstParams.add(stockTransInforDTO.getToDate());
        lstParams.add(stockTransInforDTO.getFromDate());
        lstParams.add(stockTransInforDTO.getToDate());
        lstParams.add(stockTransInforDTO.getFromDate());
        lstParams.add(stockTransInforDTO.getToDate());
        lstParams.add(stockTransInforDTO.getStockTransId());
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockTransInforDTO.class));
        //
        query.addScalar("stockTransId", new StringType());
        query.addScalar("stockTransDate", new StringType());
        query.addScalar("stockTransStatus", new StringType());
        query.addScalar("custId", new StringType());
        query.addScalar("ownerId", new StringType());
        query.addScalar("ownerType", new StringType());
        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        query.addScalar("goodsIsSerial", new StringType());
        query.addScalar("goodsIsSerialStrip", new StringType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("barcode", new StringType());
        query.addScalar("cellCode", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        lstStockGoodsSerialStrip = query.list();
        return lstStockGoodsSerialStrip;
    }

    public List<CardStockInforDTO> getListCardStockInfor(List<CardStockInforDTO> lstCardStockInfor) {
        List<CardStockInforDTO> lstCardStockInforDTO = new ArrayList();
        StringBuffer sql = new StringBuffer();
        List lstParams = null;
        //hang co serial
        sql.append("  SELECT   stock_trans_id stocktransid,");
        sql.append("           goods_id goodsid,");
        sql.append("           goods_code goodscode,");
        sql.append("           goods_name goodsname,");
        sql.append("           goods_state goodsstate,");
        sql.append("           stock_trans_date stocktransdate,");
        sql.append("           stock_trans_type stocktranstype,");
        sql.append("           stock_trans_code stocktranscode,");
        sql.append("           dept_id deptid,");
        sql.append("           trans_user_id transuserid,");
        sql.append("           notes notes,");
        sql.append("           amount_remain amountremain,");
        sql.append("           amount_import amountimport,");
        sql.append("           amount_export amountexport  ");
        sql.append("    FROM   (  SELECT   NULL stock_trans_id,");
        sql.append("                       sdr.goods_id,");
        sql.append("                       sdr.goods_code,");
        sql.append("                       sdr.goods_name,");
        sql.append("                       sdr.goods_state,");
        sql.append("                       remain_date stock_trans_date,");
        sql.append("                       NULL stock_trans_type,");
        sql.append("                       NULL stock_trans_code,");
        sql.append("                       NULL dept_id,");
        sql.append("                       NULL trans_user_id,");
        sql.append("                       NULL notes,");
        sql.append("                       NVL (SUM (amount), 0) amount_remain,");
        sql.append("                       0 amount_import,");
        sql.append("                       0 amount_export ");
        sql.append("                FROM   stock_daily_remain sdr");
        sql.append("               WHERE       remain_date = TO_DATE (?, 'dd/mm/yyyy') - 1");
        sql.append("                       AND cust_id = ?");
        sql.append("                       AND owner_type = ?");
        sql.append("                       AND owner_id = ?");
        sql.append("                       AND (sdr.goods_id = ? AND sdr.goods_state = ?)");
        //sql.append("                            OR (sdr.goods_id = ? AND sdr.goods_state = '2'))");
        sql.append("            GROUP BY   remain_date,");
        sql.append("                       sdr.goods_id,");
        sql.append("                       sdr.goods_code,");
        sql.append("                       sdr.goods_name,");
        sql.append("                       sdr.goods_state");
        sql.append("            UNION ALL");
        sql.append("              SELECT   st.stock_trans_id,");
        sql.append("                       std.goods_id,");
        sql.append("                       std.goods_code,");
        sql.append("                       std.goods_name,");
        sql.append("                       std.goods_state,");
        sql.append("                       st.stock_trans_date,");
        sql.append("                       st.stock_trans_type,");
        sql.append("                       st.stock_trans_code,");
        sql.append("                       st.dept_id,");
        sql.append("                       st.trans_user_id,");
        sql.append("                       st.notes,");
        sql.append("                       0 amount_remain,");
        sql.append("                       (CASE");
        sql.append("                            WHEN (st.stock_trans_type = '1' OR st.stock_trans_type = '4')");
        sql.append("                            THEN");
        sql.append("                                SUM (std.amount_real)");
        sql.append("                            ELSE");
        sql.append("                                0");
        sql.append("                        END)");
        sql.append("                           amount_import,");
        sql.append("                       (CASE");
        sql.append("                            WHEN (st.stock_trans_type = '2' OR st.stock_trans_type = '5')");
        sql.append("                            THEN");
        sql.append("                                SUM (std.amount_real)");
        sql.append("                            ELSE");
        sql.append("                                0");
        sql.append("                        END)");
        sql.append("                           amount_export ");
        sql.append("                FROM   stock_trans st,");
        sql.append("                       stock_trans_detail std");
        sql.append("               WHERE       st.stock_trans_id = std.stock_trans_id ");
        sql.append("                       AND st.stock_trans_status = '1'");
        sql.append("                       AND std.amount_real > 0 ");
        sql.append("                       AND st.cust_id = ?");
        sql.append("                       AND st.owner_type = ?");
        sql.append("                       AND st.owner_id = ?");
        sql.append("                       AND st.stock_trans_date >=");
        sql.append("                              TO_DATE (?, 'dd/mm/yyyy')");
        sql.append("                       AND st.stock_trans_date <");
        sql.append("                              TO_DATE (?, 'dd/mm/yyyy') + 1");
        sql.append("                       AND std.stock_trans_date >=");
        sql.append("                              TO_DATE (?, 'dd/mm/yyyy')");
        sql.append("                       AND std.stock_trans_date <");
        sql.append("                              TO_DATE (?, 'dd/mm/yyyy') + 1");
        sql.append("                       AND  (std.goods_id = ? AND std.goods_state = ?)");
        // sql.append("                            OR (std.goods_id = ? AND std.goods_state = '2'))");
        sql.append("            GROUP BY   st.cust_id,");
        sql.append("                       st.owner_type,");
        sql.append("                       st.owner_id,");
        sql.append("                       st.stock_trans_id,");
        sql.append("                       std.goods_id,");
        sql.append("                       std.goods_code,");
        sql.append("                       std.goods_name,");
        sql.append("                       std.goods_state,");
        sql.append("                       st.stock_trans_date,");
        sql.append("                       st.stock_trans_code,");
        sql.append("                       st.dept_id,");
        sql.append("                       st.trans_user_id,");
        sql.append("                       st.notes,");
        sql.append("                       st.stock_trans_type)");
        sql.append("ORDER BY   goods_id,");
        sql.append("           goods_state,");
        sql.append("           stock_trans_date,");
        sql.append("           stock_trans_id");

        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(CardStockInforDTO.class));

        for (CardStockInforDTO cardStockInforDTO : lstCardStockInfor) {
            lstParams = new ArrayList();
            //
            lstParams.add(cardStockInforDTO.getFromDate());
            lstParams.add(cardStockInforDTO.getCustId());
            lstParams.add(cardStockInforDTO.getOwnerType());
            lstParams.add(cardStockInforDTO.getOwnerId());
            lstParams.add(cardStockInforDTO.getGoodsId());
            lstParams.add(cardStockInforDTO.getGoodsState());
            //hang co serial
            lstParams.add(cardStockInforDTO.getCustId());
            lstParams.add(cardStockInforDTO.getOwnerType());
            lstParams.add(cardStockInforDTO.getOwnerId());
            lstParams.add(cardStockInforDTO.getFromDate());
            lstParams.add(cardStockInforDTO.getToDate());
            lstParams.add(cardStockInforDTO.getFromDate());
            lstParams.add(cardStockInforDTO.getToDate());
            lstParams.add(cardStockInforDTO.getGoodsId());
            lstParams.add(cardStockInforDTO.getGoodsState());

            //
            //
            query.addScalar("stockTransId", new StringType());
            query.addScalar("goodsId", new StringType());
            query.addScalar("goodsCode", new StringType());
            query.addScalar("goodsName", new StringType());
            query.addScalar("goodsState", new StringType());
            query.addScalar("stockTransDate", new StringType());
            query.addScalar("stockTransType", new StringType());
            query.addScalar("stockTransCode", new StringType());
            query.addScalar("deptId", new StringType());
            query.addScalar("transUserId", new StringType());
            query.addScalar("notes", new StringType());
            query.addScalar("amountRemain", new StringType());
            query.addScalar("amountImport", new StringType());
            query.addScalar("amountExport", new StringType());
            //
            for (int i = 0; i < lstParams.size(); i++) {
                query.setParameter(i, lstParams.get(i));
            }
            lstCardStockInforDTO.addAll(query.list());
        }
        return lstCardStockInforDTO;
    }

    public String updateStockTransByOrdersId(StockTransDTO stockTransDTO, Session session) {
        String message = ParamUtils.SUCCESS;
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        int quantitySucc = 0;
        Query query;
        try {
            sql.append(" UPDATE STOCK_TRANS ");
            sql.append(" SET STOCK_TRANS_STATUS = ?, REAL_STOCK_TRANS_DATE = TO_DATE(?,'dd/MM/yyyy hh24:mi:ss') ");
            params.add(stockTransDTO.getStockTransStatus());
            params.add(stockTransDTO.getRealStockTransDate());
            sql.append(" WHERE ORDER_ID_LIST = ? ");
            params.add(stockTransDTO.getOrderIdList());
            //
            query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            quantitySucc = query.executeUpdate();
            if (quantitySucc != 1) {
                message = ParamUtils.FAIL;
            }
        } catch (Exception e) {
            message = ParamUtils.FAIL;
        }
        return message;
    }

    public List<StockTransDTO> getListStockTrans2Inventory(StockTransDTO stockTrans,
            String fromDate, String toDate, String stockTransType) {
        List<StockTransDTO> lstReturns = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT a.STOCK_TRANS_ID stockTransId, ");
            sql.append("       a.stock_trans_code stockTransCode, ");
            sql.append("       a.DEPT_ID deptId, ");
            sql.append("       a.STOCK_TRANS_TYPE stockTransType, ");
            sql.append("       to_char(a.stock_trans_date,'dd/MM/yyyy hh24:mi:ss') stockTransDate, ");
            sql.append("       a.OWNER_ID ownerId, ");
            sql.append("       a.owner_type ownerType, ");
            sql.append("       a.IE_OWNER_ID ieOwnerId, ");
            sql.append("       a.IE_OWNER_TYPE ieOwnerType, ");
            sql.append("       a.CUST_CODE custCode, ");
            sql.append("       a.CUST_ID custId, ");
            sql.append("       a.REASON_ID reasonId, ");
            sql.append("       a.STOCK_TRANS_STATUS stockTransStatus, ");
            sql.append("       a.FROM_STOCK_TRANS_ID fromStockTransId, ");
            sql.append("       a.NOTES notes, ");
            sql.append("       a.ORDER_ID_LIST orderIdList, ");
            sql.append("       to_char(a.ORDER_DATE,'dd/MM/yyyy hh24:mi:ss') orderDate, ");
            sql.append("       a.ORDER_CODE orderCode, ");
            sql.append("       to_char(a.CREATE_DATETIME,'dd/MM/yyyy hh24:mi:ss') createDatetime, ");
            sql.append("       a.CUST_NAME custName, ");
            sql.append("       a.IS_UPDATE_CELL isUpdateCell, ");
            sql.append("       a.PARTNER_ID partnerId, ");
            sql.append("       to_char(a.REAL_STOCK_TRANS_DATE,'dd/MM/yyyy hh24:mi:ss') realStockTransDate, ");
            sql.append("       a.ATTACH_FILE_NAME attachFileName, ");
            sql.append("       a.ORDER_ACTION_CODE orderActionCode, ");
            sql.append("       a.TRANS_USER_ID transUserId, ");
            sql.append("       a.TRANS_USER_NAME transUserName, ");
            sql.append("       a.SYN_TRANS_CODE synTransCode, ");
            sql.append("       a.ADD_INFOR addInfor, ");
            sql.append("       a.VOFFICE_TRANSACTION_CODE vofficeTransCode, ");
            sql.append("       a.ORDER_COMMAND_CODE orderCommandCode ");
            sql.append(" FROM stock_trans a ");
            sql.append(" WHERE  a.CUST_ID          = :idx0 ");
            sql.append("    AND a.OWNER_ID         = :idx1 ");
            sql.append("    AND a.STOCK_TRANS_TYPE IN ( :idx2 ) ");
            sql.append("    AND a.STOCK_TRANS_STATUS IN ( :idx3 ) ");
            sql.append("    AND TRUNC(a.CREATE_DATETIME)   >= TO_DATE(:idx4, 'dd/MM/yyyy') ");
            sql.append("    AND TRUNC(a.CREATE_DATETIME)   <= TO_DATE(:idx5, 'dd/MM/yyyy') ");
            sql.append("    AND EXISTS                 ");
            sql.append("            (SELECT st.STOCK_TRANS_CODE ");
            sql.append("            FROM stock_trans st ");
            sql.append("            WHERE   a.STOCK_TRANS_CODE   =  st.STOCK_TRANS_CODE ");
            sql.append("            GROUP BY st.STOCK_TRANS_CODE ");
            sql.append("            HAVING COUNT(st.STOCK_TRANS_CODE) <= 1 ");
            sql.append("            )");
            sql.append(" ORDER By a.stock_trans_date asc, stockTransType asc ");
            SQLQuery query = getSession().createSQLQuery(sql.toString());
            query.setResultTransformer(Transformers.aliasToBean(StockTransDTO.class));
            //Truyen cac tham so
            List lstParams = new ArrayList();
            lstParams.add(stockTrans.getCustId());
            lstParams.add(stockTrans.getOwnerId());
            lstParams.add(DataUtil.parseInputListString(stockTransType));
            //Chi lay cac giao dich co trang thai = 1, 2
            String stockTransStatus = "1,2";
            lstParams.add(DataUtil.parseInputListString(stockTransStatus));
            lstParams.add(fromDate);
            lstParams.add(toDate);
            //Kieu cua du lieu tra ra
            query.addScalar("stockTransId", new StringType());
            query.addScalar("stockTransCode", new StringType());
            query.addScalar("deptId", new StringType());
            query.addScalar("stockTransType", new StringType());
            query.addScalar("stockTransDate", new StringType());
            query.addScalar("ownerId", new StringType());
            query.addScalar("ownerType", new StringType());
            query.addScalar("ieOwnerId", new StringType());
            query.addScalar("ieOwnerType", new StringType());
            query.addScalar("custCode", new StringType());
            query.addScalar("custId", new StringType());
            query.addScalar("reasonId", new StringType());
            query.addScalar("stockTransStatus", new StringType());
            query.addScalar("fromStockTransId", new StringType());
            query.addScalar("notes", new StringType());
            query.addScalar("orderIdList", new StringType());
            query.addScalar("orderDate", new StringType());
            query.addScalar("orderCode", new StringType());
            query.addScalar("createDatetime", new StringType());
            query.addScalar("isUpdateCell", new StringType());
            query.addScalar("partnerId", new StringType());
            query.addScalar("realStockTransDate", new StringType());
            query.addScalar("attachFileName", new StringType());
            query.addScalar("orderActionCode", new StringType());
            query.addScalar("transUserId", new StringType());
            query.addScalar("transUserName", new StringType());
            query.addScalar("synTransCode", new StringType());
            query.addScalar("addInfor", new StringType());
            query.addScalar("vofficeTransCode", new StringType());
            query.addScalar("orderCommandCode", new StringType());

            for (int i = 0; i < lstParams.size(); i++) {
                if (lstParams.get(i) instanceof String[]) {
                    query.setParameterList("idx" + String.valueOf(i), (Object[]) lstParams.get(i));
                } else {
                    query.setParameter("idx" + String.valueOf(i), lstParams.get(i));
                }
            }
            lstReturns = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstReturns;
    }
}
