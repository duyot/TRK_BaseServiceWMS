/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.StockTransSerial;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 10:38 PM
 */
@Repository("stockTransSerialDAO")
public class StockTransSerialDAO extends BaseFWDAOImpl<StockTransSerial, Long> {

    public StockTransSerialDAO() {
        this.model = new StockTransSerial();
    }

    public StockTransSerialDAO(Session session) {
        this.session = session;
    }

    //duyot - 27/07/2015: update stocktransserial by sql
    public ResultDTO insertStockTransSerial(StockTransSerial stockTrans, Session session) {
        ResultDTO result = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = stockTrans.getStockTransId() + "";
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();

        sql.append("INSERT INTO stock_trans_serial ");
        sql.append(" ( stock_trans_serial_id, stock_trans_id,"
                + "       stock_trans_detail_id, stock_trans_date, goods_id,"
                + "       goods_code, goods_name, goods_state, goods_unit_type,"
                + "       from_serial, to_serial,"
                + "       amount_order, amount_real, bincode, barcode, "
                + "       create_datetime,"
                + "       cell_code ) ");
        sql.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

        //
        params.add(stockTrans.getStockTransSerialId());
        params.add(stockTrans.getStockTransId());
        params.add(stockTrans.getStockTransDetailId());
        params.add(stockTrans.getStockTransDate());
        params.add(stockTrans.getGoodsId());
        params.add(stockTrans.getGoodsCode());
        params.add(stockTrans.getGoodsName());
        params.add(stockTrans.getGoodsState());
        params.add(stockTrans.getGoodsUnitType());
        params.add(stockTrans.getFromSerial());
        params.add(stockTrans.getToSerial());
        params.add(stockTrans.getAmountOrder());
        params.add(stockTrans.getAmountReal());
        params.add(stockTrans.getBincode());
        params.add(stockTrans.getBarcode());
        params.add(stockTrans.getCreateDatetime());
        params.add(stockTrans.getCellCode());
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
    //duyot - 27/07/2015: update stocktransserial by sql connection
    public ResultDTO insertStockTransSerialConnection(StockTransSerialDTO stockTrans,Connection con) {
        ResultDTO result = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = stockTrans.getStockTransId() + "";
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();

        sql.append("INSERT INTO stock_trans_serial ");
        sql.append(" ( stock_trans_serial_id, stock_trans_id,"
                + "       stock_trans_detail_id, stock_trans_date, goods_id,"
                + "       goods_code, goods_name, goods_state, goods_unit_type,"
                + "       from_serial, to_serial,"
                + "       amount_order, amount_real, bincode, barcode, "
                + "       create_datetime,"
                + "       cell_code ) ");
        sql.append(" VALUES (STOCK_TRANS_SERIAL_SEQ.nextval,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),?) ");
        //
//        params.add(stockTrans.getStockTransSerialId());
        params.add(stockTrans.getStockTransId());
        params.add(stockTrans.getStockTransDetailId());
        params.add(stockTrans.getStockTransDate());
        params.add(stockTrans.getGoodsId());
        params.add(stockTrans.getGoodsCode());
        params.add(stockTrans.getGoodsName());
        params.add(stockTrans.getGoodsState());
        params.add(stockTrans.getGoodsUnitType());
        params.add(stockTrans.getFromSerial());
        params.add(stockTrans.getToSerial());
        params.add(stockTrans.getAmountOrder());
        params.add(stockTrans.getAmountReal());
        params.add(stockTrans.getBincode());
        params.add(stockTrans.getBarcode());
        params.add(stockTrans.getCreateDatetime());
        params.add(stockTrans.getCellCode());
        //
        //tao statement bang preparestatement
        try {
            PreparedStatement stm = con.prepareStatement(sql.toString());

            for (int idx = 0; idx < params.size(); idx++) {
                    stm.setString(idx + 1, DataUtil.nvl(params.get(idx), "").toString());
            }
            
            int num = stm.executeUpdate();
            stm.close();
        //
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

    public List<StockTransSerial> getListStockTransSerialBySerial(StockTransSerialDTO stockTransSerialDTO) {
        List<StockTransSerial> lst = new ArrayList();
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();

        sql.append("SELECT   a.stock_trans_serial_id stockTransSerialId,");
        sql.append("         a.stock_trans_id stockTransId,");
        sql.append("         a.stock_trans_detail_id stockTransDetailId,");
        sql.append("         a.stock_trans_date stockTransDate,");
        sql.append("         a.goods_id goodsId,");
        sql.append("         a.goods_code goodsCode,");
        sql.append("         a.goods_name goodsName,");
        sql.append("         a.goods_state goodsState,");
        sql.append("         a.goods_unit_type goodsUnitType,");
        sql.append("         a.goods_unit_type_name goodsUnitTypeName,");
        sql.append("         a.from_serial fromSerial,");
        sql.append("         a.to_serial toserial,");
        sql.append("         a.amount_order amountOrder,");
        sql.append("         a.amount_real amountReal,");
        sql.append("         a.bincode bincode,");
        sql.append("         a.barcode barcode,");
        sql.append("         a.cell_code cellCode,");
        sql.append("         a.notes notes,");
        sql.append("         a.create_datetime createDatetime,");
        sql.append("         a.add_infor addInfor");
        sql.append("  FROM   stock_trans_serial a");
        sql.append(" WHERE   1 = 1 AND");
        sql.append("        goods_id = ?");
        sql.append("        AND");
        sql.append("        from_serial <= ?");
        sql.append("        AND");
        sql.append("        to_serial >= ?");
        sql.append("        AND stock_trans_date >= TO_DATE(?,'dd/mm/yyyy')");
        sql.append("        AND stock_trans_date <= TO_DATE(?,'dd/mm/yyyy') + 1");
        sql.append("        Order by stock_trans_date ASC");

        //
        lstParams.add(stockTransSerialDTO.getGoodsId());
        lstParams.add(stockTransSerialDTO.getFromSerial());
        lstParams.add(stockTransSerialDTO.getToSerial());
        lstParams.add(stockTransSerialDTO.getFromDateSearch());
        lstParams.add(stockTransSerialDTO.getToDateSearch());
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockTransSerial.class));
        //
        query.addScalar("stockTransSerialId", new LongType());
        query.addScalar("stockTransId", new LongType());
        query.addScalar("stockTransDetailId", new LongType());
        query.addScalar("stockTransDate", new DateType());
        query.addScalar("goodsId", new LongType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("goodsUnitType", new StringType());
        query.addScalar("goodsUnitTypeName", new StringType());
        query.addScalar("fromSerial", new StringType());
        query.addScalar("toSerial", new StringType());
        query.addScalar("amountOrder", new LongType());
        query.addScalar("amountReal", new LongType());
        query.addScalar("bincode", new StringType());
        query.addScalar("barcode", new StringType());
        query.addScalar("cellCode", new StringType());
        query.addScalar("notes", new StringType());
        query.addScalar("createDatetime", new DateType());
        query.addScalar("addInfor", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        lst = query.list();

        return lst;
    }

    public List<StockTransSerial> getListStockTransSerialBySerialStrip(StockTransSerialDTO stockTransSerialDTO) {
        List<StockTransSerial> lst = new ArrayList();
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        sql.append("SELECT   a.stock_trans_serial_id stockTransSerialId,");
        sql.append("         a.stock_trans_id stockTransId,");
        sql.append("         a.stock_trans_detail_id stockTransDetailId,");
        sql.append("         a.stock_trans_date stockTransDate,");
        sql.append("         a.goods_id goodsId,");
        sql.append("         a.goods_code goodsCode,");
        sql.append("         a.goods_name goodsName,");
        sql.append("         a.goods_state goodsState,");
        sql.append("         a.goods_unit_type goodsUnitType,");
        sql.append("         a.goods_unit_type_name goodsUnitTypeName,");
        sql.append("         a.from_serial fromSerial,");
        sql.append("         a.to_serial toserial,");
        sql.append("         a.amount_order amountOrder,");
        sql.append("         a.amount_real amountReal,");
        sql.append("         a.bincode bincode,");
        sql.append("         a.barcode barcode,");
        sql.append("         a.cell_code cellCode,");
        sql.append("         a.notes notes,");
        sql.append("         a.create_datetime createDatetime,");
        sql.append("         a.add_infor addInfor");
        sql.append("  FROM   stock_trans_serial a");
        sql.append(" WHERE   1 = 1 AND");
        sql.append("        goods_id = ?");
        sql.append("        AND");
        sql.append("        from_serial <= ?");
        sql.append("        AND");
        sql.append("        to_serial >= ? ");
        sql.append("        AND stock_trans_date >= TO_DATE(?,'dd/mm/yyyy')");
        sql.append("        AND stock_trans_date <= TO_DATE(?,'dd/mm/yyyy') + 1");
        sql.append("        Order by stock_trans_date ASC");

        //
        lstParams.add(stockTransSerialDTO.getGoodsId());
        lstParams.add(stockTransSerialDTO.getToSerial());
        lstParams.add(stockTransSerialDTO.getFromSerial());
        lstParams.add(stockTransSerialDTO.getFromDateSearch());
        lstParams.add(stockTransSerialDTO.getToDateSearch());
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockTransSerial.class));
        //
        query.addScalar("stockTransSerialId", new LongType());
        query.addScalar("stockTransId", new LongType());
        query.addScalar("stockTransDetailId", new LongType());
        query.addScalar("stockTransDate", new DateType());
        query.addScalar("goodsId", new LongType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("goodsUnitType", new StringType());
        query.addScalar("goodsUnitTypeName", new StringType());
        query.addScalar("fromSerial", new StringType());
        query.addScalar("toSerial", new StringType());
        query.addScalar("amountOrder", new LongType());
        query.addScalar("amountReal", new LongType());
        query.addScalar("bincode", new StringType());
        query.addScalar("barcode", new StringType());
        query.addScalar("cellCode", new StringType());
        query.addScalar("notes", new StringType());
        query.addScalar("createDatetime", new DateType());
        query.addScalar("addInfor", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        lst = query.list();

        return lst;
    }

    public List<StockTransSerialDTO> getListStockTransSerialByOrderId(String orderId) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();

        sql.append("  SELECT    sts.stock_trans_serial_id stockTransSerialId, ");
        sql.append("            sts.stock_trans_id stockTransId,");
        sql.append("            sts.stock_trans_detail_id stockTransDetailId,");
        sql.append("            sts.goods_id goodsId,");
        sql.append("            sts.goods_code goodsCode,");
        sql.append("            sts.goods_name goodsName,");
        sql.append("            sts.goods_state goodsState,");
        sql.append("            sts.from_serial fromSerial,");
        sql.append("            sts.to_serial toSerial,");
        sql.append("            sts.amount_order amountOrder,");
        sql.append("            sts.amount_real amountReal");
        sql.append("    FROM   stock_trans st,");
        sql.append("           stock_trans_serial sts");
        sql.append("   WHERE       st.stock_trans_id = sts.stock_trans_id");
        sql.append("           AND st.stock_trans_status <> 0");
        sql.append("           AND st.order_id_list = ?");

        lstParams.add(orderId);

        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockTransSerialDTO.class));

        query.addScalar("stockTransSerialId", new StringType());
        query.addScalar("stockTransId", new StringType());
        query.addScalar("stockTransDetailId", new StringType());
        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("fromSerial", new StringType());
        query.addScalar("toSerial", new StringType());
        query.addScalar("amountOrder", new StringType());
        query.addScalar("amountReal", new StringType());

        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        return query.list();
    }

}
