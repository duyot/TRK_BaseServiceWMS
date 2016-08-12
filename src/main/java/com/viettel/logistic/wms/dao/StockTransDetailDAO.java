/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.StockTransDetail;
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
 * @since 22-Apr-15 4:10 PM
 */
@Repository("stockTransDetailDAO")
public class StockTransDetailDAO extends BaseFWDAOImpl<StockTransDetail, Long> {

    public StockTransDetailDAO() {
        this.model = new StockTransDetail();
    }

    public StockTransDetailDAO(Session session) {
        this.session = session;
    }

    //duyot - 27/07/2015: update stocktransdetail by sql
    public ResultDTO insertStockTransDetail(StockTransDetail stockTrans, Session session) {

        ResultDTO result = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = stockTrans.getStockTransId() + "";
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();

        sql.append("INSERT INTO stock_trans_detail ");
        sql.append("(stock_trans_detail_id,stock_trans_id,stock_trans_date,goods_id,goods_code,goods_name,goods_state,goods_unit_type, goods_unit_type_name,goods_is_serial, ");
        sql.append(" goods_is_serial_strip,amount_order, amount_real, bincode, barcode,notes, create_datetime,add_infor, ");
        sql.append(" cell_code) ");
        sql.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

        //
        params.add(stockTrans.getStockTransDetailId());
        params.add(stockTrans.getStockTransId());
        params.add(stockTrans.getStockTransDate());
        params.add(stockTrans.getGoodsId());
        params.add(stockTrans.getGoodsCode());
        params.add(stockTrans.getGoodsName());
        params.add(stockTrans.getGoodsState());
        params.add(stockTrans.getGoodsUnitType());
        params.add(stockTrans.getGoodsUnitTypeName());
        params.add(stockTrans.getGoodsIsSerial());
        params.add(stockTrans.getGoodsIsSerialStrip());
        params.add(stockTrans.getAmountOrder());
        params.add(stockTrans.getAmountReal());
        String bincode = stockTrans.getBincode();
        if (!StringUtils.isStringNullOrEmpty(bincode)) {
            params.add(bincode);
        } else {
            params.add("");
        }
        String barcode = stockTrans.getBarcode();
        if (!StringUtils.isStringNullOrEmpty(barcode)) {
            params.add(barcode);
        } else {
            params.add("");
        }
        String note = stockTrans.getNotes();
        if (!StringUtils.isStringNullOrEmpty(note)) {
            params.add(note);
        } else {
            params.add("");
        }
        params.add(stockTrans.getCreateDatetime());
        String addInfo = stockTrans.getAddInfor();
        if (!StringUtils.isStringNullOrEmpty(addInfo)) {
            params.add(addInfo);
        } else {
            params.add("");
        }
        String cellCode = stockTrans.getCellCode();
        if (!StringUtils.isStringNullOrEmpty(cellCode)) {
            params.add(cellCode);
        } else {
            params.add("");
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

    //duyot - 27/07/2015: update stocktransdetail by sql
    public ResultDTO insertStockTransDetail(StockTransDetailDTO stockTransDetailDTO, Connection con) {

        ResultDTO result = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = stockTransDetailDTO.getStockTransId() + "";
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();

        sql.append("INSERT INTO stock_trans_detail ");
        sql.append("(stock_trans_detail_id,stock_trans_id,stock_trans_date,goods_id,goods_code,goods_name,goods_state,goods_unit_type, goods_unit_type_name,goods_is_serial, ");
        sql.append(" goods_is_serial_strip,amount_order, amount_real, bincode, barcode,notes, create_datetime,add_infor, ");
        sql.append(" cell_code) ");
        sql.append(" VALUES (?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?) ");

        //
        params.add(stockTransDetailDTO.getStockTransDetailId());
        params.add(stockTransDetailDTO.getStockTransId());
        params.add(stockTransDetailDTO.getStockTransDate());
        params.add(stockTransDetailDTO.getGoodsId());
        params.add(stockTransDetailDTO.getGoodsCode());
        params.add(stockTransDetailDTO.getGoodsName());
        params.add(stockTransDetailDTO.getGoodsState());
        params.add(stockTransDetailDTO.getGoodsUnitType());
        params.add(stockTransDetailDTO.getGoodsUnitTypeName());
        params.add(stockTransDetailDTO.getGoodsIsSerial());
        params.add(stockTransDetailDTO.getGoodsIsSerialStrip());
        params.add(stockTransDetailDTO.getAmountOrder());
        params.add(stockTransDetailDTO.getAmountReal());
        String bincode = stockTransDetailDTO.getBincode();
        if (!StringUtils.isStringNullOrEmpty(bincode)) {
            params.add(bincode);
        } else {
            params.add("");
        }
        String barcode = stockTransDetailDTO.getBarcode();
        if (!StringUtils.isStringNullOrEmpty(barcode)) {
            params.add(barcode);
        } else {
            params.add("");
        }
        String note = stockTransDetailDTO.getNotes();
        if (!StringUtils.isStringNullOrEmpty(note)) {
            params.add(note);
        } else {
            params.add("");
        }
        params.add(stockTransDetailDTO.getCreateDatetime());
        String addInfo = stockTransDetailDTO.getAddInfor();
        if (!StringUtils.isStringNullOrEmpty(addInfo)) {
            params.add(addInfo);
        } else {
            params.add("");
        }
        String cellCode = stockTransDetailDTO.getCellCode();
        if (!StringUtils.isStringNullOrEmpty(cellCode)) {
            params.add(cellCode);
        } else {
            params.add("");
        }
        //
        try {
            PreparedStatement stm = con.prepareStatement(sql.toString());

            for (int idx = 0; idx < params.size(); idx++) {
                stm.setString(idx + 1, (String) DataUtil.nvl(params.get(idx), ""));
            }
            int num = stm.executeUpdate();
            stm.close();
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

    public List<StockTransDetailDTO> getListStockTransDetailByOrderId(String orderId) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();

        sql.append("  SELECT    std.stock_trans_detail_id stockTransDetailId,");
        sql.append("            st.stock_trans_id stockTransId,");
        sql.append("            std.goods_id goodsId,");
        sql.append("            std.goods_code goodsCode,");
        sql.append("            std.goods_name goodsName,");
        sql.append("            std.goods_state goodsState,");
        sql.append("            g.goods_type goodsType,");
        sql.append("            std.goods_unit_type goodsUnitType,");
        sql.append("            std.goods_unit_type_name goodsUnitTypeName,");
        sql.append("            std.goods_is_serial goodsIsSerial,");
        sql.append("            std.goods_is_serial_strip goodsIsSerialStrip,");
        sql.append("            std.amount_order amountOrder,");
        sql.append("            std.amount_real amountReal,");
        sql.append("            std.create_datetime createDatetime");
        sql.append("    FROM   stock_trans st,");
        sql.append("           goods g,");
        sql.append("           stock_trans_detail std");
        sql.append("   WHERE       st.stock_trans_id = std.stock_trans_id");
        sql.append("           AND std.goods_id = g.goods_id");
        sql.append("           AND st.stock_trans_status <> 0");
        sql.append("           AND st.order_id_list = ? ");

        lstParams.add(orderId);

        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockTransDetailDTO.class));

        query.addScalar("stockTransDetailId", new StringType());
        query.addScalar("stockTransId", new StringType());
        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("goodsType", new StringType());
        query.addScalar("goodsUnitType", new StringType());
        query.addScalar("goodsUnitTypeName", new StringType());
        query.addScalar("goodsIsSerial", new StringType());
        query.addScalar("goodsIsSerialStrip", new StringType());
        query.addScalar("amountOrder", new StringType());
        query.addScalar("amountReal", new StringType());
        query.addScalar("createDatetime", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        return query.list();
    }
}
