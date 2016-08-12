/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.google.common.base.Splitter;
import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.logistic.wms.model.StockGoodsSerial;
import com.viettel.logistic.wms.model.StockGoodsSerialError;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.StockGoodsSerialStrip;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 16-Apr-15 3:34 PM
 */
@Repository("stockGoodsSerialStripDAO")
public class StockGoodsSerialStripDAO extends BaseFWDAOImpl<StockGoodsSerialStrip, Long> {

    public Logger logger = Logger.getLogger(StockGoodsSerialStripDAO.class.getName());

    public StockGoodsSerialStripDAO() {
        this.model = new StockGoodsSerialStrip();
    }

    public StockGoodsSerialStripDAO(Session session) {
        this.session = session;
    }

    //ChuDV: Tim va ghep dai serial
    public List<StockGoodsSerialStrip> getListStockGoodsSerial(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsSerialStrip> lstStockGoodsSerialStrip = null;
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //
        sql.append("SELECT   cust_id custId,");
        sql.append("         owner_id ownerId,");
        sql.append("         owner_type ownerType,");
        sql.append("         cell_code cellCode,");
        sql.append("         goods_id goodsId,");
        sql.append("         state goodsState,");
        sql.append("         status,");
        sql.append("         channel_type_id channelTypeId,");
        sql.append("         from_serial fromSerial,");
        sql.append("         to_serial toSerial, quantity ");
        sql.append("  FROM   table(show_serial_strip (cursor (  SELECT   sts.cust_id,");
        sql.append("                                                     sts.owner_id,");
        sql.append("                                                     sts.owner_type,");
        sql.append("                                                     sts.cell_code,");
        sql.append("                                                     sts.goods_id,");
        sql.append("                                                     sts.goods_state,");
        sql.append("                                                     sts.status,");
        sql.append("                                                     sts.channel_type_id,");
        sql.append("                                                     sts.serial");
        sql.append("                                              FROM   stock_goods_serial sts");
        sql.append("                                             WHERE   1 = 1 ");
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append("AND sts.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append("AND sts.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append("AND sts.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            sql.append("AND sts.goods_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsId());
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsState())) {
            sql.append("AND sts.goods_state = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsState());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND sts.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kiem theo Kenh
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getChannelTypeId())) {
            sql.append("AND sts.channel_type_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getChannelTypeId());
        }
        //Tim kiem theo from serial
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getFromSerial())) {
            sql.append("AND sts.serial >= ? ");
            lstParams.add(stockGoodsSerialStripDTO.getFromSerial());
        }
        //Tim kiem theo to serial
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getToSerial())) {
            sql.append("AND sts.serial <= ? ");
            lstParams.add(stockGoodsSerialStripDTO.getToSerial());
        }
        //Tim theo cell         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCellCode())) {
            sql.append("AND sts.cell_code = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCellCode());
        }
        //Tim theo Barcode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getBarcode())) {
            sql.append("AND sts.barcode = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getBarcode());
        }
        //Tim theo Bincode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getBincode())) {
            sql.append("AND sts.bincode = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getBincode());
        }
        //
        sql.append(" ORDER BY cust_id,owner_id,owner_type,cell_code,goods_id,goods_state,status,serial),");
        sql.append("                    TO_NUMBER (?))) a");
        lstParams.add(stockGoodsSerialStripDTO.getQuantity());
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockGoodsSerialStrip.class));

        query.addScalar("custId", new LongType());
        query.addScalar("ownerId", new LongType());
        query.addScalar("ownerType", new StringType());
        query.addScalar("cellCode", new StringType());
        query.addScalar("goodsId", new LongType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("status", new StringType());
        query.addScalar("channelTypeId", new StringType()); // sua lai LongType -> StringType (tiepnv6)
        query.addScalar("fromSerial", new StringType());
        query.addScalar("toSerial", new StringType());
        query.addScalar("quantity", new LongType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        lstStockGoodsSerialStrip = query.list();
        //
        return lstStockGoodsSerialStrip;
    }

    //
    //Export stock by strip
    //NHAP VAO DAI SERIAL
    public ResultDTO importStockGoodsSerialStripConnection(StockGoodsSerialStripDTO stockGoodsSerialStrip, String stockTransId, Connection con) {
        ResultDTO resultDTO = new ResultDTO();
        Double amount = 0D;
        Double amountIssue = 0D;
        int insertSuccess = 0;
        int insertFail = 0;
        Long quantity;
        List<StockGoodsSerialInforDTO> lstError = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            //
            sql.append(" INSERT INTO stock_goods_serial_strip (id, cust_id, owner_id, owner_type, goods_id,");
            sql.append("       goods_state, status,sale_type, change_user,");
            sql.append("       price,channel_type_id, barcode, change_date,");
            sql.append("       import_date, sale_date, bincode, add_infor, cell_code,from_serial, to_serial, quantity, partner_id, import_stock_trans_id, order_id) ");
            sql.append(" VALUES (STOCK_GOODS_SERIAL_STRIP_SEQ.nextval,?,?,?,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?,");
            sql.append("         to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),NULL,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?) ) ");
            sql.append(" LOG ERRORS   REJECT LIMIT UNLIMITED ");
            //            
            params.add(stockGoodsSerialStrip.getCustId());//cust_id
            params.add(stockGoodsSerialStrip.getOwnerId());//owner_id
            params.add(stockGoodsSerialStrip.getOwnerType());//owner_type
            params.add(stockGoodsSerialStrip.getGoodsId());//goods_id
            params.add(stockGoodsSerialStrip.getGoodsState());///goods_state
            params.add(stockGoodsSerialStrip.getStatus());//status
            params.add(stockGoodsSerialStrip.getSaleType());//sale_type
            params.add(stockGoodsSerialStrip.getChangeUser());//change_user
            params.add(stockGoodsSerialStrip.getPrice());//price
            params.add(stockGoodsSerialStrip.getChannelTypeId());//channel_type_id
            params.add(stockGoodsSerialStrip.getBarcode());//barcode
            params.add(stockGoodsSerialStrip.getChangeDate());//change_date
            params.add(stockGoodsSerialStrip.getImportDate());//import_date
//            params.add("");// //sale date
            params.add(stockGoodsSerialStrip.getBincode());//bincode
            params.add(stockGoodsSerialStrip.getAddInfor());//add_infor
            params.add(stockGoodsSerialStrip.getCellCode());//cell_code
            params.add(stockGoodsSerialStrip.getFromSerial());//from_serial
            params.add(stockGoodsSerialStrip.getToSerial());//to_serial
            quantity = Long.parseLong(stockGoodsSerialStrip.getToSerial()) - Long.parseLong(stockGoodsSerialStrip.getFromSerial()) + 1;
            params.add(quantity + "");//quantity
            params.add(stockGoodsSerialStrip.getPartnerId());//partner_id
            params.add(stockGoodsSerialStrip.getImportStockTransId());//import_stock_trans_id
            params.add(stockGoodsSerialStrip.getOrderId());//order_id

            //tao statement bang preparestatement
            PreparedStatement stm = con.prepareStatement(sql.toString());

            for (int idx = 0; idx < params.size(); idx++) {
                try {
                    stm.setString(idx + 1, DataUtil.nvl(params.get(idx), "").toString());
                } catch (Exception e) {
                    System.out.println(idx);
                }
            }

            int num = stm.executeUpdate();
            stm.close();
            amount = Double.parseDouble(stockGoodsSerialStrip.getQuantity());
            amountIssue = Double.parseDouble(String.valueOf(quantity));
            insertSuccess++;
            //lay danh sach loi insert vao err$
            lstError = getListSerilStripErrorImportStockCust(stockGoodsSerialStrip.getImportStockTransId(), stockGoodsSerialStrip.getFromSerial(), stockGoodsSerialStrip.getToSerial());
            if (lstError.size() > 0) {
                insertFail++;
                amount = 0D;
                amountIssue = 0D;
                resultDTO.setLstStockGoodsSerialInforDTO(lstError);
            }
        } catch (Exception e) {
            //
            System.out.println("Before stockgoodsserialStrip_Errors.." + String.valueOf(stockGoodsSerialStrip.getId()
                    + " cuscID" + String.valueOf(stockGoodsSerialStrip.getCustId() + " goodID")
                    + String.valueOf(stockGoodsSerialStrip.getGoodsId())));
            insertStockGoodsSerialError(stockGoodsSerialStrip.toModel(), stockTransId, session);
            System.out.println("ok stockgoodsserialStrip_Errors");
            insertFail++;
            amount = Double.parseDouble(stockGoodsSerialStrip.getQuantity().toString());
            amountIssue = 0D;
        }
        //
        resultDTO.setMessage(ParamUtils.SUCCESS);
        resultDTO.setQuantityFail(insertFail);
        resultDTO.setQuantitySucc(insertSuccess);
        resultDTO.setAmount(amount);
        resultDTO.setAmountIssue(amountIssue);
        //
        return resultDTO;
    }

    ///ay danh sach mat hang loi
    public List getListSerilStripErrorImportStockCust(String stockTransId, String from_serial, String to_serial) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();

        sql.append("SELECT   ");
        sql.append("         a.ora_err_mesg$ addInfor,");
        sql.append("         a.cust_id custId,");
        sql.append("         a.owner_id ownerId,");
        sql.append("         a.owner_type ownerType,");
        sql.append("         a.goods_id goodsId,");
        sql.append("         a.goods_state goodsState,");
        sql.append("         a.status status,");
        sql.append("         a.from_serial fromSerial,");
        sql.append("         a.to_serial toSerial,");
        sql.append("         a.barcode barcode,");
        sql.append("         a.cell_code cellCode,");
        sql.append("         g.code goodsCode,");
        sql.append("         g.name goodsName ");
        sql.append("  FROM   err$_stock_goods_serial_strip a, goods g ");
        sql.append("  WHERE   a.goods_id = g.goods_id ");
        //
        if (!DataUtil.isStringNullOrEmpty(stockTransId)) {
            sql.append("  AND a.import_stock_trans_id = ? ");
            lstParams.add(stockTransId);
        }
        sql.append("  AND   a.from_serial = ? ");
        sql.append("  AND   a.to_serial = ? ");
        lstParams.add(from_serial);
        lstParams.add(to_serial);
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockGoodsSerialInforDTO.class));

        query.addScalar("custId", new StringType());
        query.addScalar("ownerId", new StringType());
        query.addScalar("ownerType", new StringType());
        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("status", new StringType());
        query.addScalar("fromSerial", new StringType());
        query.addScalar("toSerial", new StringType());
        query.addScalar("addInfor", new StringType());
        query.addScalar("cellCode", new StringType());
        query.addScalar("barcode", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        return query.list();
    }

    //NHAP VAO DAI SERIAL
    public ResultDTO importStockGoodsSerialStrip(StockGoodsSerialStrip stockGoodsSerialStrip, String stockTransId, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        String serial;
        String prefixSerial = "";
        String suffixFromSerial = "";
        String suffixToSerial = "";

        Double amount = 0D;
        Double amountIssue = 0D;
        int insertSuccess = 0;
        int insertFail = 0;
        Long quantity;
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            //
            Query query;
            //
            sql.append(" INSERT INTO stock_goods_serial_strip (id, cust_id, owner_id, owner_type, goods_id,");
            sql.append("       goods_state, status,sale_type, change_user,");
            sql.append("       price,channel_type_id, barcode, change_date,");
            sql.append("       import_date, sale_date, bincode, add_infor, cell_code,from_serial, to_serial, quantity, partner_id, import_stock_trans_id, order_id) ");
            sql.append(" VALUES (STOCK_GOODS_SERIAL_STRIP_SEQ.nextval,?,?,?,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?,");
            sql.append("         ?,?,NULL,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?) ) ");
            //            
            params.add(stockGoodsSerialStrip.getCustId());//cust_id
            params.add(stockGoodsSerialStrip.getOwnerId());//owner_id
            params.add(stockGoodsSerialStrip.getOwnerType());//owner_type
            params.add(stockGoodsSerialStrip.getGoodsId());//goods_id
            params.add(stockGoodsSerialStrip.getGoodsState());///goods_state
            params.add(stockGoodsSerialStrip.getStatus());//status
            params.add(stockGoodsSerialStrip.getSaleType());//sale_type
            params.add(stockGoodsSerialStrip.getChangeUser());//change_user
            params.add(stockGoodsSerialStrip.getPrice());//price
            params.add(stockGoodsSerialStrip.getChannelTypeId());//channel_type_id
            params.add(stockGoodsSerialStrip.getBarcode());//barcode
            params.add(stockGoodsSerialStrip.getChangeDate());//change_date
            params.add(stockGoodsSerialStrip.getImportDate());//import_date
//            params.add("");// //sale date
            params.add(stockGoodsSerialStrip.getBincode());//bincode
            params.add(stockGoodsSerialStrip.getAddInfor());//add_infor
            params.add(stockGoodsSerialStrip.getCellCode());//cell_code
            params.add(stockGoodsSerialStrip.getFromSerial());//from_serial
            params.add(stockGoodsSerialStrip.getToSerial());//to_serial
            quantity = Long.parseLong(stockGoodsSerialStrip.getToSerial()) - Long.parseLong(stockGoodsSerialStrip.getFromSerial()) + 1;
            params.add(quantity);//quantity
            params.add(stockGoodsSerialStrip.getPartnerId());//partner_id
            params.add(stockGoodsSerialStrip.getImportStockTransId());//import_stock_trans_id
            params.add(stockGoodsSerialStrip.getOrderId());//order_id
            //
            query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            query.executeUpdate();
            //            
            amount = Double.parseDouble(stockGoodsSerialStrip.getQuantity().toString());
            amountIssue = Double.parseDouble(String.valueOf(quantity));
            insertSuccess++;
        } catch (Exception e) {
            insertStockGoodsSerialError(stockGoodsSerialStrip, stockTransId, session);
            System.out.println("ok stockgoodsserialStrip_Errors");
            insertFail++;
            amount = Double.parseDouble(stockGoodsSerialStrip.getQuantity().toString());
            amountIssue = 0D;
        }
        //
        resultDTO.setMessage(ParamUtils.SUCCESS);
        resultDTO.setQuantityFail(insertFail);
        resultDTO.setQuantitySucc(insertSuccess);
        resultDTO.setAmount(amount);
        resultDTO.setAmountIssue(amountIssue);
        //
        return resultDTO;
    }

    private void insertStockGoodsSerialError(StockGoodsSerialStrip stockGoodsSerialStrip, String stockTransId, Session session) {
        StockGoodsSerialError stockGoodsSerialError = new StockGoodsSerialError();
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        sql.append(" INSERT INTO stock_goods_serial_error (id,stock_trans_id, cust_id, owner_id, owner_type, goods_id,");
        sql.append("       goods_state, status, from_serial,to_serial, sale_type, change_user,");
        sql.append("       price, channel_type_id, barcode, change_date,");
        sql.append("       import_date, sale_date, bincode, add_infor,quantity) ");
        sql.append(" VALUES (STOCK_GOODS_SERIAL_ERROR_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?,SYSDATE,SYSDATE,NULL,?,?,TO_NUMBER(?)) ");
        //    
        try {
            params.add(stockTransId);
            params.add(stockGoodsSerialStrip.getCustId());
            params.add(stockGoodsSerialStrip.getOwnerId());
            params.add(stockGoodsSerialStrip.getOwnerType());
            params.add(stockGoodsSerialStrip.getGoodsId());
            params.add(stockGoodsSerialStrip.getGoodsState());
            params.add(stockGoodsSerialStrip.getStatus());
            params.add(stockGoodsSerialStrip.getFromSerial());
            params.add(stockGoodsSerialStrip.getToSerial());
            params.add(stockGoodsSerialStrip.getSaleType());
            params.add(stockGoodsSerialStrip.getChangeUser());
            params.add(stockGoodsSerialStrip.getPrice());
            params.add(stockGoodsSerialStrip.getChannelTypeId());
            params.add(stockGoodsSerialStrip.getBarcode());
            params.add(stockGoodsSerialStrip.getBincode());
            params.add(stockGoodsSerialStrip.getAddInfor());
            params.add(stockGoodsSerialStrip.getQuantity());
            //
            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(StockGoodsSerialStripDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    //Tim kiem serial theo dai theo session
    public List<StockGoodsSerialStrip> getListStockGoodsSerialStrip(StockGoodsSerialStrip stockGoodsSerialStrip, Session session) {
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //
        sql.append(" from StockGoodsSerialStrip sts WHERE 1=1 ");

        if (!StringUtils.isLongNullOrEmpty(stockGoodsSerialStrip.getCustId())) {
            sql.append("AND sts.custId = ? ");
            lstParams.add(stockGoodsSerialStrip.getCustId());
        }
        //Tim kiem theo kho hang
//        if (!StringUtils.isLongNullOrEmpty(stockGoodsSerialStrip.getOwnerId())) {
//            sql.append("AND sts.ownerId = ? ");
//            lstParams.add(stockGoodsSerialStrip.getOwnerId());
//        }
//        //Tim kiem theo loai kho hang
//        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getOwnerType())) {
//            sql.append("AND sts.ownerType = ? ");
//            lstParams.add(stockGoodsSerialStrip.getOwnerType());
//        }
        //Tim kien theo mat hang
        if (!StringUtils.isLongNullOrEmpty(stockGoodsSerialStrip.getGoodsId())) {
            sql.append("AND sts.goodsId = ? ");
            lstParams.add(stockGoodsSerialStrip.getGoodsId());
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getGoodsState())) {
            sql.append("AND sts.goodsState = ? ");
            lstParams.add(stockGoodsSerialStrip.getGoodsState());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getStatus())) {
            sql.append("AND sts.status = ? ");
            lstParams.add(stockGoodsSerialStrip.getStatus());
        }
        //Tim kiem theo Kenh
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getChannelTypeId())) {
            sql.append("AND sts.channelTypeId = ? ");
            lstParams.add(stockGoodsSerialStrip.getChannelTypeId());
        }
        //Tim kiem theo from serial
        if (!StringUtils.isNullOrEmpty(stockGoodsSerialStrip.getFromSerial())) {
            sql.append("AND sts.toSerial >= ? ");
            lstParams.add(stockGoodsSerialStrip.getFromSerial());
        }
        //Tim kiem theo to serial
        if (!StringUtils.isNullOrEmpty(stockGoodsSerialStrip.getToSerial())) {
            sql.append("AND sts.fromSerial <= ? ");
            lstParams.add(stockGoodsSerialStrip.getToSerial());
        }
        //Tim theo cell ID                 
        if (!StringUtils.isNullOrEmpty(stockGoodsSerialStrip.getCellCode())) {
            sql.append("AND sts.cellCode = ? ");
            lstParams.add(stockGoodsSerialStrip.getCellCode());
        }
        //Tim theo Barcode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getBarcode())) {
            sql.append("AND sts.barcode = ? ");
            lstParams.add(stockGoodsSerialStrip.getBarcode());
        }
        //Tim theo Bincode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getBincode())) {
            sql.append("AND sts.bincode = ? ");
            lstParams.add(stockGoodsSerialStrip.getBincode());
        }
        //
        sql.append(" ORDER BY sts.custId,sts.ownerId,sts.ownerType,sts.goodsId,sts.goodsState,sts.fromSerial,sts.status ");
        //
        Query query = session.createQuery(sql.toString());
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        //
        return query.list();
    }

    //Tim kiem serial theo dai
    public List<StockGoodsSerialStrip> getListStockGoodsSerialStripExacly(StockGoodsSerialStrip stockGoodsSerialStrip) {
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //
        sql.append(" from StockGoodsSerialStrip sts WHERE 1=1 ");

        if (!StringUtils.isLongNullOrEmpty(stockGoodsSerialStrip.getCustId())) {
            sql.append("AND sts.custId = ? ");
            lstParams.add(stockGoodsSerialStrip.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isLongNullOrEmpty(stockGoodsSerialStrip.getOwnerId())) {
            sql.append("AND sts.ownerId = ? ");
            lstParams.add(stockGoodsSerialStrip.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getOwnerType())) {
            sql.append("AND sts.ownerType = ? ");
            lstParams.add(stockGoodsSerialStrip.getOwnerType());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isLongNullOrEmpty(stockGoodsSerialStrip.getGoodsId())) {
            sql.append("AND sts.goodsId = ? ");
            lstParams.add(stockGoodsSerialStrip.getGoodsId());
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getGoodsState())) {
            sql.append("AND sts.goodsState = ? ");
            lstParams.add(stockGoodsSerialStrip.getGoodsState());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getStatus())) {
            sql.append("AND sts.status = ? ");
            lstParams.add(stockGoodsSerialStrip.getStatus());
        }
        //Tim kiem theo Kenh
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getChannelTypeId())) {
            sql.append("AND sts.channelTypeId = ? ");
            lstParams.add(stockGoodsSerialStrip.getChannelTypeId());
        }
        //Tim kiem theo from serial
        if (!StringUtils.isNullOrEmpty(stockGoodsSerialStrip.getFromSerial())) {
            if (DataUtil.isInteger(stockGoodsSerialStrip.getFromSerial())) {
                sql.append("AND sts.toSerial >= to_number(?) ");
            } else {
                sql.append("AND sts.toSerial >= ? ");
            }
            lstParams.add(stockGoodsSerialStrip.getFromSerial());
        }
        //Tim kiem theo to serial
        if (!StringUtils.isNullOrEmpty(stockGoodsSerialStrip.getToSerial())) {
            if (DataUtil.isInteger(stockGoodsSerialStrip.getToSerial())) {
                sql.append("AND sts.fromSerial <= to_number(?) ");
            } else {
                sql.append("AND sts.fromSerial <= ? ");
            }
            lstParams.add(stockGoodsSerialStrip.getToSerial());
        }
        //Tim theo cell ID                 
        if (!StringUtils.isNullOrEmpty(stockGoodsSerialStrip.getCellCode())) {
            sql.append("AND sts.cellCode = ? ");
            lstParams.add(stockGoodsSerialStrip.getCellCode());
        }
        //Tim theo Barcode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getBarcode())) {
            sql.append("AND sts.barcode = ? ");
            lstParams.add(stockGoodsSerialStrip.getBarcode());
        }
        //Tim theo Bincode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getBincode())) {
            sql.append("AND sts.bincode = ? ");
            lstParams.add(stockGoodsSerialStrip.getBincode());
        }
        //
        sql.append(" ORDER BY sts.custId,sts.ownerId,sts.ownerType,sts.goodsId,sts.goodsState,sts.status,sts.fromSerial ");
        //
        Query query = getSession().createQuery(sql.toString());
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        //
        return query.list();
    }

    //Tim kiem serial theo dai
    public List<StockGoodsSerialStrip> getListStockGoodsSerialStrip(StockGoodsSerialStrip stockGoodsSerialStrip) {
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //
        sql.append(" from StockGoodsSerialStrip sts WHERE 1=1 ");

        if (!StringUtils.isLongNullOrEmpty(stockGoodsSerialStrip.getCustId())) {
            sql.append("AND sts.custId = ? ");
            lstParams.add(stockGoodsSerialStrip.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isLongNullOrEmpty(stockGoodsSerialStrip.getOwnerId())) {
            sql.append("AND sts.ownerId = ? ");
            lstParams.add(stockGoodsSerialStrip.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getOwnerType())) {
            sql.append("AND sts.ownerType = ? ");
            lstParams.add(stockGoodsSerialStrip.getOwnerType());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isLongNullOrEmpty(stockGoodsSerialStrip.getGoodsId())) {
            sql.append("AND sts.goodsId = ? ");
            lstParams.add(stockGoodsSerialStrip.getGoodsId());
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getGoodsState())) {
            sql.append("AND sts.goodsState = ? ");
            lstParams.add(stockGoodsSerialStrip.getGoodsState());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getStatus())) {
            sql.append("AND sts.status = ? ");
            lstParams.add(stockGoodsSerialStrip.getStatus());
        }
        //Tim kiem theo Kenh
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getChannelTypeId())) {
            sql.append("AND sts.channelTypeId = ? ");
            lstParams.add(stockGoodsSerialStrip.getChannelTypeId());
        }
        //Tim kiem theo from serial
        if (!StringUtils.isNullOrEmpty(stockGoodsSerialStrip.getFromSerial())) {
            sql.append("AND sts.toSerial >= ? ");
            lstParams.add(stockGoodsSerialStrip.getFromSerial());
        }
        //Tim kiem theo to serial
        if (!StringUtils.isNullOrEmpty(stockGoodsSerialStrip.getToSerial())) {
            sql.append("AND sts.fromSerial <= ? ");
            lstParams.add(stockGoodsSerialStrip.getToSerial());
        }
        //Tim theo cell ID                 
        if (!StringUtils.isNullOrEmpty(stockGoodsSerialStrip.getCellCode())) {
            sql.append("AND sts.cellCode = ? ");
            lstParams.add(stockGoodsSerialStrip.getCellCode());
        }
        //Tim theo Barcode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getBarcode())) {
            sql.append("AND sts.barcode = ? ");
            lstParams.add(stockGoodsSerialStrip.getBarcode());
        }
        //Tim theo Bincode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getBincode())) {
            sql.append("AND sts.bincode = ? ");
            lstParams.add(stockGoodsSerialStrip.getBincode());
        }
        //
        sql.append(" ORDER BY sts.custId,sts.ownerId,sts.ownerType,sts.goodsId,sts.goodsState,sts.status,sts.fromSerial ");
        //
        Query query = getSession().createQuery(sql.toString());
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        //
        return query.list();
    }

    //
//    //Tim kiem dai serial trung chom
//    public List<StockGoodsSerialStrip> getListSerialIntersection(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
//        StringBuffer sql = new StringBuffer();
//        //
//        sql.append(" from StockGoodsSerialStrip ");
//        sql.append(" WHERE custId = ? AND goodsId = ? "
//                + "                  AND ( (? >= fromSerial "
//                + "                    AND ? <= toSerial)"
//                + "                  OR ( ? >= fromSerial "
//                + "                    AND ? <= toSerial)) ");
//        //
//        Query query = getSession().createQuery(sql.toString());
//        query.setParameter(0, Long.parseLong(stockGoodsSerialStripDTO.getCustId()));
//        query.setParameter(1, Long.parseLong(stockGoodsSerialStripDTO.getGoodsId()));
//        query.setParameter(2, Double.parseDouble(stockGoodsSerialStripDTO.getFromSerial()));
//        query.setParameter(3, Double.parseDouble(stockGoodsSerialStripDTO.getFromSerial()));
//        query.setParameter(4, Double.parseDouble(stockGoodsSerialStripDTO.getToSerial()));
//        query.setParameter(5, Double.parseDouble(stockGoodsSerialStripDTO.getToSerial()));
//        //
//        return query.list();
//    }
    /*\
     test: epxort_stock_serial_strip tra ve list stock_godos_serial_strip da xuat
    
     */
    public ResultDTO exportStockGoodsSerialStrip(StockGoodsSerialStrip oldStockGoodsSerialStrip, StockGoodsSerialStrip newStockGoodsSerialStrip, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        List<StockGoodsSerialStrip> lstStockGoodsSerialStrip = new ArrayList<>();
        StockGoodsSerialStrip insertStockGoodsSerialStrip;
        //List<StockGoodsSerialStrip> lstFilterStockGoodsSerialStrip = new ArrayList<>();
        try {
            //Tim kiem serial
            lstStockGoodsSerialStrip = getListStockGoodsSerialStrip(oldStockGoodsSerialStrip, session);
            if (lstStockGoodsSerialStrip == null || lstStockGoodsSerialStrip.isEmpty()) {
                resultDTO.setKey(ParamUtils.NOT_FOUND_DATA);
                resultDTO.setFromSerial(oldStockGoodsSerialStrip.getFromSerial());
                resultDTO.setToSerial(oldStockGoodsSerialStrip.getToSerial());
//                resultDTO.setMessage(ParamUtils.FAIL);
                // tiepnv6, edit 26/06/15: tra ve goods id loi
                message = String.valueOf(oldStockGoodsSerialStrip.getGoodsId()) + "," + oldStockGoodsSerialStrip.getGoodsState();
                resultDTO.setMessage(message);
                return resultDTO;
            }
            //
            String fromSerial = newStockGoodsSerialStrip.getFromSerial();
            String toSerial = newStockGoodsSerialStrip.getToSerial();
            //

            Long quantity = newStockGoodsSerialStrip.getQuantity();
            Long quantityTotal = 0L;
            Long quantityNew = 0L;
            //Insert du lieu 
            int iSize = lstStockGoodsSerialStrip.size();

            StockGoodsSerialStrip stockGoodsSerialStrip;
            for (int i = 0; i < iSize; i++) {
                stockGoodsSerialStrip = lstStockGoodsSerialStrip.get(i);
                insertStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(newStockGoodsSerialStrip);
                //newStockGoodsSerialStrip = stockGoodsSerialStrip;
                //Xu ly ban ghi dau tien
                if (i == 0) {
                    if (Long.parseLong(fromSerial) > Long.parseLong(stockGoodsSerialStrip.getFromSerial())) {
                        insertStockGoodsSerialStrip.setFromSerial(fromSerial);
                    } else {
                        insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                    }
                    //
                    if (Long.parseLong(toSerial) < Long.parseLong(stockGoodsSerialStrip.getToSerial())) {
                        insertStockGoodsSerialStrip.setToSerial(toSerial);
                    } else {
                        insertStockGoodsSerialStrip.setToSerial(stockGoodsSerialStrip.getToSerial());
                    }
                } else //Xu ly ban ghi cuoi cung
                if (i == iSize - 1) {
                    if (Long.parseLong(toSerial) < Long.parseLong(stockGoodsSerialStrip.getToSerial())) {
                        insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                        insertStockGoodsSerialStrip.setToSerial(toSerial);
                    } else {
                        insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                        insertStockGoodsSerialStrip.setToSerial(stockGoodsSerialStrip.getToSerial());
                    }

                } else {
                    insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                    insertStockGoodsSerialStrip.setToSerial(stockGoodsSerialStrip.getToSerial());
                }
                //
                quantityNew = Long.parseLong(insertStockGoodsSerialStrip.getToSerial()) - Long.parseLong(insertStockGoodsSerialStrip.getFromSerial()) + 1;
                insertStockGoodsSerialStrip.setQuantity(quantityNew);
                insertStockGoodsSerialStrip.setChannelTypeId(stockGoodsSerialStrip.getChannelTypeId());
                insertStockGoodsSerialStrip.setCellCode(stockGoodsSerialStrip.getCellCode());
                insertStockGoodsSerialStrip.setBincode(stockGoodsSerialStrip.getBincode());
                insertStockGoodsSerialStrip.setBarcode(stockGoodsSerialStrip.getBarcode());
                insertStockGoodsSerialStrip.setPrice(stockGoodsSerialStrip.getPrice());
                insertStockGoodsSerialStrip.setAddInfor(stockGoodsSerialStrip.getAddInfor());
                insertStockGoodsSerialStrip.setImportDate(stockGoodsSerialStrip.getImportDate());
                insertStockGoodsSerialStrip.setSaleDate(stockGoodsSerialStrip.getSaleDate());
                insertStockGoodsSerialStrip.setSaleType(stockGoodsSerialStrip.getSaleType());
                //duyot: 22/04: them truong import_stock_trans_id
                insertStockGoodsSerialStrip.setImportStockTransId(stockGoodsSerialStrip.getImportStockTransId());
                //Insert du lieu dai serial                
                resultDTO = updateStockGoodsSerialStrip(stockGoodsSerialStrip, insertStockGoodsSerialStrip, session);
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    return resultDTO;
                }
                quantityTotal = quantityTotal + insertStockGoodsSerialStrip.getQuantity();
            }
            //Kiem tra so luong can xuat thanh cong
            if (quantityTotal < quantity) {
                key = ParamUtils.NOT_EQUAL_QUANTITY;
                //message = ParamUtils.FAIL;
                message = String.valueOf(oldStockGoodsSerialStrip.getGoodsId()) + "," + oldStockGoodsSerialStrip.getGoodsState();
                resultDTO.setFromSerial(fromSerial);
                resultDTO.setMessage(message);
                resultDTO.setToSerial(toSerial);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(StockGoodsSerialStripDAO.class.getName()).log(Level.SEVERE, null, ex);
            key = ParamUtils.SYSTEM_ERROR;
            message = ParamUtils.FAIL;
            resultDTO.setMessage(message);
        }
        //
        resultDTO.setMessage(message);
        resultDTO.setKey(key);
        return resultDTO;
    }
    //-----------------------------------------------------------------------------------

    //Export stock by strip
    public List<StockGoodsSerialStrip> exportStockGoodsSerialStripTransfer(StockGoodsSerialStrip oldStockGoodsSerialStrip, StockGoodsSerialStrip newStockGoodsSerialStrip, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        List<StockGoodsSerialStrip> lstExported = new ArrayList<>();
        List<StockGoodsSerialStrip> lstStockGoodsSerialStrip = new ArrayList<>();
        StockGoodsSerialStrip insertStockGoodsSerialStrip;
        //List<StockGoodsSerialStrip> lstFilterStockGoodsSerialStrip = new ArrayList<>();
        try {
            //Tim kiem serial
            lstStockGoodsSerialStrip = getListStockGoodsSerialStrip(oldStockGoodsSerialStrip, session);
            if (lstStockGoodsSerialStrip == null || lstStockGoodsSerialStrip.isEmpty()) {
                resultDTO.setKey(ParamUtils.NOT_FOUND_DATA);
//                resultDTO.setMessage(ParamUtils.FAIL);
                // tiepnv6, edit 26/06/15: tra ve goods id loi
                message = String.valueOf(oldStockGoodsSerialStrip.getGoodsId()) + "," + oldStockGoodsSerialStrip.getGoodsState();
                resultDTO.setMessage(message);
                //duyot: 28/01/2016: tra ve dai serial loi
                //tra ve danh sach trip chua serial loi
                StockGoodsSerialStrip errorStrip = new StockGoodsSerialStrip();
                errorStrip.setFromSerial(oldStockGoodsSerialStrip.getFromSerial());
                errorStrip.setToSerial(oldStockGoodsSerialStrip.getToSerial());
                lstExported.add(errorStrip);
                return lstExported;
            }
            //
            String fromSerial = newStockGoodsSerialStrip.getFromSerial();
            String toSerial = newStockGoodsSerialStrip.getToSerial();
            //

            Long quantity = newStockGoodsSerialStrip.getQuantity();
            Long quantityTotal = 0L;
            Long quantityNew = 0L;
            //Insert du lieu 
            int iSize = lstStockGoodsSerialStrip.size();
            StockGoodsSerialStrip stockGoodsSerialStrip;
            for (int i = 0; i < iSize; i++) {
                stockGoodsSerialStrip = lstStockGoodsSerialStrip.get(i);
                insertStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(newStockGoodsSerialStrip);
                //newStockGoodsSerialStrip = stockGoodsSerialStrip;
                //Xu ly ban ghi dau tien
                if (i == 0) {
                    if (Long.parseLong(fromSerial) > Long.parseLong(stockGoodsSerialStrip.getFromSerial())) {
                        insertStockGoodsSerialStrip.setFromSerial(fromSerial);
                    } else {
                        insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                    }
                    //
                    if (Long.parseLong(toSerial) < Long.parseLong(stockGoodsSerialStrip.getToSerial())) {
                        insertStockGoodsSerialStrip.setToSerial(toSerial);
                    } else {
                        insertStockGoodsSerialStrip.setToSerial(stockGoodsSerialStrip.getToSerial());
                    }
                } else //Xu ly ban ghi cuoi cung
                if (i == iSize - 1) {
                    if (Long.parseLong(toSerial) < Long.parseLong(stockGoodsSerialStrip.getToSerial())) {
                        insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                        insertStockGoodsSerialStrip.setToSerial(toSerial);
                    } else {
                        insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                        insertStockGoodsSerialStrip.setToSerial(stockGoodsSerialStrip.getToSerial());
                    }

                } else {
                    insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                    insertStockGoodsSerialStrip.setToSerial(stockGoodsSerialStrip.getToSerial());
                }
                //
                quantityNew = Long.parseLong(insertStockGoodsSerialStrip.getToSerial()) - Long.parseLong(insertStockGoodsSerialStrip.getFromSerial()) + 1;
                insertStockGoodsSerialStrip.setQuantity(quantityNew);
                insertStockGoodsSerialStrip.setChannelTypeId(stockGoodsSerialStrip.getChannelTypeId());
                insertStockGoodsSerialStrip.setCellCode(stockGoodsSerialStrip.getCellCode());
                insertStockGoodsSerialStrip.setBincode(stockGoodsSerialStrip.getBincode());
                insertStockGoodsSerialStrip.setBarcode(stockGoodsSerialStrip.getBarcode());
                insertStockGoodsSerialStrip.setPrice(stockGoodsSerialStrip.getPrice());
                insertStockGoodsSerialStrip.setAddInfor(stockGoodsSerialStrip.getAddInfor());
                insertStockGoodsSerialStrip.setImportDate(stockGoodsSerialStrip.getImportDate());
                insertStockGoodsSerialStrip.setSaleDate(stockGoodsSerialStrip.getSaleDate());
                insertStockGoodsSerialStrip.setSaleType(stockGoodsSerialStrip.getSaleType());
                //Insert du lieu dai serial                
                resultDTO = updateStockGoodsSerialStrip(stockGoodsSerialStrip, insertStockGoodsSerialStrip, session);
                lstExported.add(insertStockGoodsSerialStrip);
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    return null;
                }

                quantityTotal = quantityTotal + insertStockGoodsSerialStrip.getQuantity();
            }
            //Kiem tra so luong can xuat thanh cong
            if (quantityTotal < quantity) {
                key = ParamUtils.NOT_EQUAL_QUANTITY;
//                message = ParamUtils.FAIL;
                // tiepnv6, edit 26/06/15: tra ve goods id loi
                message = String.valueOf(oldStockGoodsSerialStrip.getGoodsId()) + "," + oldStockGoodsSerialStrip.getGoodsState();
                resultDTO.setFromSerial(fromSerial);
                resultDTO.setMessage(message);
                resultDTO.setToSerial(toSerial);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(StockGoodsSerialStripDAO.class.getName()).log(Level.SEVERE, null, ex);
            key = ParamUtils.SYSTEM_ERROR;
            message = ParamUtils.FAIL;
            resultDTO.setMessage(message);
        }
        //
//            resultDTO.setMessage(message);
//        resultDTO.setKey(key);
        return lstExported;
    }

    //
    private ResultDTO updateStockGoodsSerialStrip(StockGoodsSerialStrip oldStockGoodsSerialStrip, StockGoodsSerialStrip stockGoodsSerialStrip, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        //Xoa dai serial cu
        String message = ParamUtils.SUCCESS;
        resultDTO = deleteStockGoodsSerialStrip(oldStockGoodsSerialStrip, session);
        Long firtQuantity = 0L;
        Long lastQuantity = 0L;
        StockGoodsSerialStrip firstOldStockGoodsSerialStrip;
        StockGoodsSerialStrip lastOldStockGoodsSerialStrip;
        int lengthSerial = stockGoodsSerialStrip.getFromSerial().length();
        //Insert dai serial dau (cu)
        if (Long.parseLong(oldStockGoodsSerialStrip.getFromSerial()) < Long.parseLong(stockGoodsSerialStrip.getFromSerial())) {
            firstOldStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(oldStockGoodsSerialStrip);
            String firstToSerial = Long.parseLong(stockGoodsSerialStrip.getFromSerial()) - 1 + "";
            firstOldStockGoodsSerialStrip.setToSerial(DataUtil.lPad(firstToSerial, "0", lengthSerial));
            firtQuantity = Long.parseLong(firstOldStockGoodsSerialStrip.getToSerial()) - Long.parseLong(firstOldStockGoodsSerialStrip.getFromSerial()) + 1;
            firstOldStockGoodsSerialStrip.setQuantity(firtQuantity);
            //firstOldStockGoodsSerialStrip.setBarcode(message);
            saveObjectSession(firstOldStockGoodsSerialStrip, session);
        }
        //Insert dai serial cuoi (cu)
        if (Long.parseLong(oldStockGoodsSerialStrip.getToSerial()) > Long.parseLong(stockGoodsSerialStrip.getToSerial())) {
            lastOldStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(oldStockGoodsSerialStrip);
            String lastFromSerial = Long.parseLong(stockGoodsSerialStrip.getToSerial()) + 1 + "";
            lastOldStockGoodsSerialStrip.setFromSerial(DataUtil.lPad(lastFromSerial, "0", lengthSerial));
            lastQuantity = Long.parseLong(lastOldStockGoodsSerialStrip.getToSerial()) - Long.parseLong(lastOldStockGoodsSerialStrip.getFromSerial()) + 1;
            lastOldStockGoodsSerialStrip.setQuantity(lastQuantity);
            saveObjectSession(lastOldStockGoodsSerialStrip, session);
        }
        //Insert dai serail theo kho moi
        saveObjectSession(stockGoodsSerialStrip, session);
        //stockGoodsSerialStrip = new StockGoodsSerialStrip();        
        //
        resultDTO.setMessage(message);
        return resultDTO;
    }

    //
    private ResultDTO deleteStockGoodsSerialStrip(StockGoodsSerialStrip stockGoodsSerialStrip, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();
        int iDelete;
        //
        sql.append("DELETE stock_goods_serial_strip ");
        sql.append(" WHERE cust_id = ? ");
        sql.append(" AND owner_id = ? AND owner_type = ?");
        sql.append(" AND goods_id = ? AND goods_state = ? AND status = ? ");
        sql.append(" AND from_serial = ? AND to_serial = ? ");
        //
        lstParams.add(stockGoodsSerialStrip.getCustId());
        lstParams.add(stockGoodsSerialStrip.getOwnerId());
        lstParams.add(stockGoodsSerialStrip.getOwnerType());
        lstParams.add(stockGoodsSerialStrip.getGoodsId());
        lstParams.add(stockGoodsSerialStrip.getGoodsState());
        lstParams.add(stockGoodsSerialStrip.getStatus());
        lstParams.add(stockGoodsSerialStrip.getFromSerial());
        lstParams.add(stockGoodsSerialStrip.getToSerial());
        //
        if (!StringUtils.isNullOrEmpty(stockGoodsSerialStrip.getCellCode())) {
            sql.append("AND cell_code = ? ");
            lstParams.add(stockGoodsSerialStrip.getCellCode());
        }
        //Tim theo Barcode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getBarcode())) {
            sql.append("AND barcode = ? ");
            lstParams.add(stockGoodsSerialStrip.getBarcode());
        }
        //Tim theo Bincode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getBincode())) {
            sql.append("AND bincode = ? ");
            lstParams.add(stockGoodsSerialStrip.getBincode());
        }
        //
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStrip.getChannelTypeId())) {
            sql.append("AND channel_type_id = ? ");
            lstParams.add(stockGoodsSerialStrip.getChannelTypeId());
        }
        //
        Query query = session.createSQLQuery(sql.toString());
        for (int idx = 0; idx < lstParams.size(); idx++) {
            query.setParameter(idx, lstParams.get(idx));
        }
        //
        iDelete = query.executeUpdate();
        if (iDelete < 1) {
            key = ParamUtils.NOT_EXIST_SERIAL_STRIP_IN_STOCK;
            message = ParamUtils.FAIL;
        }
        //
        resultDTO.setMessage(message);
        resultDTO.setKey(key);
        return resultDTO;
    }

    //Add by ChuDV: 09/05/2015
    public ResultDTO updateCellStockGoodsSerialStrip(StockTransInforDTO stockTransInforDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc = 0;
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" UPDATE stock_goods_serial_strip ");
            sql.append(" SET cell_code = ? ");
            sql.append(" WHERE cust_id=? AND owner_id=? AND owner_type=? ");
            sql.append("   AND goods_id=? AND goods_state=? ");
            sql.append("   AND barcode=? ");
            //
            params.add(stockTransInforDTO.getCellCode());
            params.add(stockTransInforDTO.getCustId());
            params.add(stockTransInforDTO.getOwnerId());
            params.add(stockTransInforDTO.getOwnerType());
            params.add(stockTransInforDTO.getGoodsId());
            params.add(stockTransInforDTO.getGoodsState());
            params.add(stockTransInforDTO.getBarcode());
            //
            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            quantitySucc = query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }
        //
        resultDTO.setMessage(message);
        resultDTO.setKey(key);
        resultDTO.setQuantitySucc(quantitySucc);
        return resultDTO;
    }

    //Add by QuyenDM: 15/06/2015 - Cap nhat vi tri - cho chuc nang don dich kho
    public Map<ChangePositionDTO, ResultDTO> updateCellStockGoodsSerialStrip(ChangePositionDTO changePosition, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        Map<ChangePositionDTO, ResultDTO> mapChangePosition2ResultDTO = new HashMap<>();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc = 0;
        try {
            //Kiem tra so luong nhap vao co dap ung duoc khong
            Double amountTotal = getAmountInStockGoodsTotal(changePosition);
            Double soluongNhapvao = Double.parseDouble(changePosition.getQuantity());
            if (amountTotal < soluongNhapvao) {
                //Neu tong so luong khac voi so luong nhap vao --> Bao loi khong du so luong
                resultDTO.setMessage(ParamUtils.NOT_ENOUGH_AMOUNT);
                resultDTO.setKey(ParamUtils.FAIL);
                resultDTO.setQuantitySucc(quantitySucc);
                changePosition.setErrorInfor(ParamUtils.NOT_ENOUGH_AMOUNT);
                mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                return mapChangePosition2ResultDTO;
            }
            if (soluongNhapvao < amountTotal && DataUtil.isStringNullOrEmpty(changePosition.getFromSerial())) {
                //Neu tong so luong nho hon so luong nhap vao va k nhap serial --> Bao loi phai nhap serial
                resultDTO.setMessage("needSerial");
                resultDTO.setKey(ParamUtils.FAIL);
                resultDTO.setQuantitySucc(quantitySucc);
                changePosition.setErrorInfor("needSerial");
                mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                return mapChangePosition2ResultDTO;
            }
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" UPDATE stock_goods_serial_strip ");
            sql.append(" SET cell_code = ?,change_date = ?");
            sql.append(" WHERE cust_id=? AND owner_id=? AND owner_type=? AND goods_id=? AND status='1' AND cell_code = ? ");
            //
            params.add(changePosition.getCellCodeNew());
            params.add(new Date());
            params.add(changePosition.getCustomerId());
            params.add(changePosition.getStockId());
            params.add(changePosition.getOwnerType());
            params.add(changePosition.getGoodsId());
            params.add(changePosition.getCellCodeOld());
            //TH 2: Neu co barcode va khong co fromSerial, khong co toSerial
            if (!StringUtils.isNullOrEmpty(changePosition.getBarcode())) {
                sql.append("   AND barcode=? ");
                params.add(changePosition.getBarcode());
            }
//            //TH 1: Neu co vi tri cu va khong co barcode,khong co fromSerial,khong co toSerial
//            if (!StringUtils.isNullOrEmpty(changePosition.getCellCodeOld())
//                    && StringUtils.isNullOrEmpty(changePosition.getBarcode())
//                    && StringUtils.isNullOrEmpty(changePosition.getFromSerial())
//                    && StringUtils.isNullOrEmpty(changePosition.getToSerial())) {
//                sql.append("   AND cell_code = ? ");
//                params.add(changePosition.getCellCodeOld());
//            }
            //TH 3: Neu co fromSerial va toSerial
            if (!StringUtils.isNullOrEmpty(changePosition.getFromSerial())
                    && !StringUtils.isNullOrEmpty(changePosition.getToSerial())) {
                sql.append("   AND from_serial = ? ");
                sql.append("   AND to_serial = ? ");
                params.add(changePosition.getFromSerial());
                params.add(changePosition.getToSerial());
            }
            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            quantitySucc = query.executeUpdate();
            //Neu so luong hang hoa thanh cong = 0
            if (quantitySucc < 1) {
                message = ParamUtils.NOT_FOUND_DATA;
                resultDTO.setMessage(message);
                resultDTO.setKey(key);
                resultDTO.setQuantitySucc(0);
                changePosition.setErrorInfor(ParamUtils.NOT_FOUND_DATA);
                mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                return mapChangePosition2ResultDTO;
            }
        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            resultDTO.setKey(ParamUtils.NOT_FOUND_DATA);
            changePosition.setErrorInfor(ParamUtils.NOT_FOUND_DATA);
            resultDTO.setMessage(message);
            resultDTO.setQuantitySucc(0);
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }
        //
        resultDTO.setMessage(message);
        resultDTO.setKey(key);
        resultDTO.setQuantitySucc(quantitySucc);
        mapChangePosition2ResultDTO.put(changePosition, resultDTO);
        return mapChangePosition2ResultDTO;

    }

    public Double getAmountInStockGoodsTotal(ChangePositionDTO changePositionDTO) {
        StringBuilder sql = new StringBuilder();
        sql.append("       SELECT   sum(a.quantity) ");
        sql.append("       FROM   wms_owner.stock_goods_serial_strip a");
        sql.append("       WHERE       a.goods_id = ? ");
        sql.append("            AND a.owner_type = ? ");
        sql.append("            AND a.owner_id = ? ");
        sql.append("            AND a.cust_id = ? ");
        sql.append("            AND a.cell_code = ? ");
        sql.append("            AND a.status = '1' ");
        List lstParams = new ArrayList<>();
        lstParams.add(changePositionDTO.getGoodsId());
        lstParams.add(changePositionDTO.getOwnerType());
        lstParams.add(changePositionDTO.getStockId());
        lstParams.add(changePositionDTO.getCustomerId());
        lstParams.add(changePositionDTO.getCellCodeOld());
        if (!DataUtil.isStringNullOrEmpty(changePositionDTO.getBarcode())) {
            sql.append("            AND a.barcode = ? ");
            lstParams.add(changePositionDTO.getBarcode());
        }
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        for (int idx = 0; idx < lstParams.size(); idx++) {
            query.setParameter(idx, lstParams.get(idx));
        }
        List listResult = query.list();
        BigDecimal result;
        if (listResult != null && listResult.size() > 0) {
            result = (BigDecimal) listResult.get(0);
            return result.doubleValue();
        }
        return 0D;
    }

//ChuDV: Lay danh sach hang hoa theo dai
    public List<StockGoodsSerialInforDTO> getListStockGoodsSerialInfor(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStaffId())) {
            return getListStockGoodsSerialInforByStaff(stockGoodsSerialStripDTO);
        } else {
            return getListStockGoodsSerialInforAll(stockGoodsSerialStripDTO);
        }
    }

    //Lay danh sach hang hoa theo nhan vien quan ly loai hang hoa
    public List<StockGoodsSerialInforDTO> getListStockGoodsSerialInforByStaff(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsSerialInforDTO> lstStockGoodsSerialInfor = null;
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //
        sql.append("SELECT   a.cust_id custId,");
        sql.append("         a.owner_id ownerId,");
        sql.append("         a.owner_type ownerType,");
        sql.append("         a.cell_code cellCode,");
        sql.append("         a.goods_id goodsId,");
        sql.append("         a.state goodsState,");
        sql.append("         a.status,");
        sql.append("         a.channel_type_id channelTypeId,");
        sql.append("         a.from_serial fromSerial,");
        sql.append("         a.to_serial toSerial, a.quantity, ");
        sql.append("         g.goods_type goodsType, g.goods_group goodsGroup, ");
        sql.append("         g.code goodsCode, g.name goodsName, ");
        sql.append("         msg.staff_id staffId, msg.staff_code staffCode, msg.staff_name staffName ");
        sql.append("  FROM   table(show_serial_strip (cursor (  SELECT   sts.cust_id,");
        sql.append("                                                     sts.owner_id,");
        sql.append("                                                     sts.owner_type,");
        sql.append("                                                     sts.cell_code,");
        sql.append("                                                     sts.goods_id,");
        sql.append("                                                     sts.goods_state,");
        sql.append("                                                     sts.status,");
        sql.append("                                                     sts.channel_type_id,");
        sql.append("                                                     sts.serial");
        sql.append("                                              FROM   stock_goods_serial sts");
        sql.append("                                             WHERE   1 = 1 ");
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append("AND sts.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append("AND sts.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append("AND sts.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            sql.append("AND sts.goods_id IN (");
            sql.append(stockGoodsSerialStripDTO.getGoodsId());
            sql.append(") ");
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsState())) {
            sql.append("AND sts.goods_state = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsState());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND sts.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kiem theo Kenh
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getChannelTypeId())) {
            sql.append("AND sts.channel_type_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getChannelTypeId());
        }
        //Tim kiem theo from serial
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getFromSerial())) {
            sql.append("AND sts.serial >= ? ");
            lstParams.add(stockGoodsSerialStripDTO.getFromSerial());
        }
        //Tim kiem theo to serial
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getToSerial())) {
            sql.append("AND sts.serial <= ? ");
            lstParams.add(stockGoodsSerialStripDTO.getToSerial());
        }
        //Tim theo cell         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCellCode())) {
            sql.append("AND sts.cell_code = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCellCode());
        }
        //Tim theo Barcode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getBarcode())) {
            sql.append("AND sts.barcode = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getBarcode());
        }
        //Tim theo Bincode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getBincode())) {
            sql.append("AND sts.bincode = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getBincode());
        }
        //
        sql.append(" ORDER BY sts.cust_id,sts.owner_id,sts.owner_type,sts.cell_code,");
        sql.append("          sts.goods_id,sts.goods_state,sts.status,sts.serial),");
        sql.append("                    TO_NUMBER (?))) a, goods g, map_staff_goods msg ");

        sql.append("  WHERE g.cust_id = a.cust_id AND g.goods_id = a.goods_id ");
        sql.append("         AND a.goods_id = msg.goods_id(+)");
        lstParams.add(stockGoodsSerialStripDTO.getQuantity());
        //tim kiem theo nhan vien
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStaffId())) {
            sql.append("    AND msg.staff_id = ?");
            lstParams.add(stockGoodsSerialStripDTO.getStaffId());
        }
        //ThienNG1- Begin AddBy 18/06/2015 
        //Tim kien theo loai mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsType())) {
            //sql.append("AND g.goods_type in (?) ");
            if (stockGoodsSerialStripDTO.getGoodsType().contains(",")) {
                sql.append("AND g.goods_type IN (:gdstype) ");
            } else {
                sql.append("AND g.goods_type IN (?) ");
            }
            lstParams.add(stockGoodsSerialStripDTO.getGoodsType());
        }
        //End
        sql.append(" ORDER BY a.cust_id,a.owner_id,a.owner_type,g.goods_type,g.code,a.cell_code,a.state,a.from_serial,a.status ");

        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query
                .setResultTransformer(Transformers.aliasToBean(StockGoodsSerialInforDTO.class
                        ));

        query.addScalar(
                "custId", new StringType());
        query.addScalar(
                "ownerId", new StringType());
        query.addScalar(
                "ownerType", new StringType());
        query.addScalar(
                "cellCode", new StringType());
        query.addScalar(
                "goodsId", new StringType());
        query.addScalar(
                "goodsState", new StringType());
        query.addScalar(
                "status", new StringType());
        query.addScalar(
                "channelTypeId", new StringType());
        query.addScalar(
                "fromSerial", new StringType());
        query.addScalar(
                "toSerial", new StringType());
        query.addScalar(
                "quantity", new StringType());
        query.addScalar(
                "goodsType", new StringType());
        query.addScalar(
                "goodsGroup", new StringType());
        query.addScalar(
                "goodsCode", new StringType());
        query.addScalar(
                "goodsName", new StringType());
        query.addScalar(
                "staffId", new StringType());
        query.addScalar(
                "staffCode", new StringType());
        query.addScalar(
                "staffName", new StringType());
        //ThienNG1 update 03/08/2015
        for (int i = 0;
                i < lstParams.size();
                i++) {
            if (lstParams.get(i) != null) {
                if (lstParams.get(i).toString().contains(",")) {
                    List<String> lst = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(stockGoodsSerialStripDTO.getGoodsType().toString());
                    query.setParameterList("gdstype", lst);
                } else {
                    query.setParameter(i, lstParams.get(i));
                }
            } else {
                query.setParameter(i, lstParams.get(i));
            }
        }

        //
        return query.list();
    }

    //
    public List<StockGoodsSerialInforDTO> getListStockGoodsSerialInforAll(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsSerialInforDTO> lstStockGoodsSerialInfor = null;
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //
        sql.append("SELECT   a.cust_id custId,");
        sql.append("         a.owner_id ownerId,");
        sql.append("         a.owner_type ownerType,");
        sql.append("         a.cell_code cellCode,");
        sql.append("         a.goods_id goodsId,");
        sql.append("         a.state goodsState,");
        sql.append("         a.status,");
        sql.append("         a.channel_type_id channelTypeId,");
        sql.append("         a.from_serial fromSerial,");
        sql.append("         a.to_serial toSerial, a.quantity, ");
        sql.append("         g.goods_type goodsType, g.goods_group goodsGroup, ");
        sql.append("         g.code goodsCode, g.name goodsName ");
        sql.append("  FROM   table(show_serial_strip (cursor (  SELECT   sts.cust_id,");
        sql.append("                                                     sts.owner_id,");
        sql.append("                                                     sts.owner_type,");
        sql.append("                                                     sts.cell_code,");
        sql.append("                                                     sts.goods_id,");
        sql.append("                                                     sts.goods_state,");
        sql.append("                                                     sts.status,");
        sql.append("                                                     sts.channel_type_id,");
        sql.append("                                                     sts.serial");
        sql.append("                                              FROM   stock_goods_serial sts");
        sql.append("                                             WHERE   1 = 1 ");
        //Tim kien theo mat hang
//        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
//            sql.append("AND sts.goods_id = ? ");
//            lstParams.add(stockGoodsSerialStripDTO.getGoodsId());
//        }
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            sql.append("AND sts.goods_id IN (");
            sql.append(stockGoodsSerialStripDTO.getGoodsId());
            sql.append(") ");
        }
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append("AND sts.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append("AND sts.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append("AND sts.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsState())) {
            sql.append("AND sts.goods_state = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsState());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND sts.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kiem theo Kenh
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getChannelTypeId())) {
            sql.append("AND sts.channel_type_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getChannelTypeId());
        }
        //Tim kiem theo from serial
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getFromSerial())) {
            sql.append("AND sts.serial >= ? ");
            lstParams.add(stockGoodsSerialStripDTO.getFromSerial());
        }
        //Tim kiem theo to serial
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getToSerial())) {
            sql.append("AND sts.serial <= ? ");
            lstParams.add(stockGoodsSerialStripDTO.getToSerial());
        }
        //Tim theo cell         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCellCode())) {
            sql.append("AND sts.cell_code = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCellCode());
        }
        //Tim theo Barcode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getBarcode())) {
            sql.append("AND sts.barcode = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getBarcode());
        }
        //Tim theo Bincode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getBincode())) {
            sql.append("AND sts.bincode = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getBincode());
        }
        //
        sql.append(" ORDER BY sts.cust_id,sts.owner_id,sts.owner_type,sts.cell_code,");
        sql.append("          sts.goods_id,sts.goods_state,sts.status,sts.serial),");
        sql.append("                    TO_NUMBER (?))) a, goods g ");

        sql.append("  WHERE g.cust_id = a.cust_id AND g.goods_id = a.goods_id ");
        lstParams.add(stockGoodsSerialStripDTO.getQuantity());

        //ThienNG1- Begin AddBy 18/06/2015 
        //Tim kien theo loai mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsType())) {
            //sql.append("AND g.goods_type in (?) ");
            if (stockGoodsSerialStripDTO.getGoodsType().contains(",")) {
                sql.append("AND g.goods_type IN (:gdstype) ");
            } else {
                sql.append("AND g.goods_type IN (?) ");
            }
            lstParams.add(stockGoodsSerialStripDTO.getGoodsType());
        }
        //End
        sql.append(" ORDER BY a.cust_id,a.owner_id,a.owner_type,g.goods_type,g.code,a.cell_code,a.state,a.from_serial,a.status ");

        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query
                .setResultTransformer(Transformers.aliasToBean(StockGoodsSerialInforDTO.class
                        ));

        query.addScalar(
                "custId", new StringType());
        query.addScalar(
                "ownerId", new StringType());
        query.addScalar(
                "ownerType", new StringType());
        query.addScalar(
                "cellCode", new StringType());
        query.addScalar(
                "goodsId", new StringType());
        query.addScalar(
                "goodsState", new StringType());
        query.addScalar(
                "status", new StringType());
        query.addScalar(
                "channelTypeId", new StringType());
        query.addScalar(
                "fromSerial", new StringType());
        query.addScalar(
                "toSerial", new StringType());
        query.addScalar(
                "quantity", new StringType());
        query.addScalar(
                "goodsType", new StringType());
        query.addScalar(
                "goodsGroup", new StringType());
        query.addScalar(
                "goodsCode", new StringType());
        query.addScalar(
                "goodsName", new StringType());
        //ThienNG1 update 03/08/2015

        for (int i = 0; i < lstParams.size(); i++) {
            if (lstParams.get(i) != null) {
                if (lstParams.get(i).toString().contains(",")) {
                    List<String> lst = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(stockGoodsSerialStripDTO.getGoodsType().toString());
                    query.setParameterList("gdstype", lst);
                } else {
                    query.setParameter(i, lstParams.get(i));
                }
            } else {
                query.setParameter(i, lstParams.get(i));
            }
        }

        //
        return query.list();
    }

    public List<StockGoodsSerialInforDTO> getListStockGoodsSerialStripInfor(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStaffId())) {
            return getListStockGoodsSerialStripInforByStaff(stockGoodsSerialStripDTO);
        } else {
            return getListStockGoodsSerialStripInforAll(stockGoodsSerialStripDTO);
        }
    }

    //
    //ChuDV: Lay danh sach hang hoa theo dai
    public List<StockGoodsSerialInforDTO> getListStockGoodsSerialStripInforByStaff(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsSerialInforDTO> lstStockGoodsSerialInfor = null;
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //
        sql.append("SELECT   a.cust_id custId,");
        sql.append("         a.owner_id ownerId,");
        sql.append("         a.owner_type ownerType,");
        sql.append("         a.cell_code cellCode,");
        sql.append("         a.goods_id goodsId,");
        sql.append("         a.goods_state goodsState,");
        sql.append("         a.status,");
        sql.append("         a.channel_type_id channelTypeId,");
        sql.append("         a.from_serial fromSerial,");
        sql.append("         a.to_serial toSerial, a.quantity, ");
        sql.append("         g.goods_type goodsType, g.goods_group goodsGroup, ");
        sql.append("         g.code goodsCode, g.name goodsName, ");
        sql.append("         msg.staff_id staffId, msg.staff_code staffCode, msg.staff_name staffName ");
        sql.append("  FROM   stock_goods_serial_strip a, goods g, map_staff_goods msg ");
        sql.append("  WHERE g.cust_id = a.cust_id AND g.goods_id = a.goods_id ");
        sql.append("         AND a.goods_id = msg.goods_id(+) ");
        //tim kiem theo nhan vien
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStaffId())) {
            sql.append("    AND msg.staff_id = ?");
            lstParams.add(stockGoodsSerialStripDTO.getStaffId());
        }
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append("AND a.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append("AND a.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append("AND a.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            sql.append("AND a.goods_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsId());
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsState())) {
            sql.append("AND a.goods_state = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsState());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND a.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kiem theo Kenh
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getChannelTypeId())) {
            sql.append("AND a.channel_type_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getChannelTypeId());
        }
//        //Tim kiem theo from serial
//        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getFromSerial())) {
//            sql.append("AND sts.serial >= ? ");
//            lstParams.add(stockGoodsSerialStripDTO.getFromSerial());
//        }
//        //Tim kiem theo to serial
//        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getToSerial())) {
//            sql.append("AND sts.serial <= ? ");
//            lstParams.add(stockGoodsSerialStripDTO.getToSerial());
//        }
        //Tim theo cell         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCellCode())) {
            sql.append("AND a.cell_code = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCellCode());
        }
        //Tim theo Barcode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getBarcode())) {
            sql.append("AND a.barcode = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getBarcode());
        }
        //Tim theo Bincode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getBincode())) {
            sql.append("AND a.bincode = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getBincode());
        }
        //ThienNG1- Begin AddBy 18/06/2015 
        //Tim kien theo loai mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsType())) {
            //ThienNG1 Update 03/08/2015
            if (stockGoodsSerialStripDTO.getGoodsType().contains(",")) {
                sql.append("AND g.goods_type IN (:gdstype) ");
            } else {
                sql.append("AND g.goods_type IN (?) ");
            }
            lstParams.add(stockGoodsSerialStripDTO.getGoodsType());
        }
        //End
        sql.append(" ORDER BY a.cust_id,a.owner_id,a.owner_type,g.goods_type,g.code,a.goods_state,a.cell_code,a.from_serial,a.status ");
        //lstParams.add(stockGoodsSerialStripDTO.getQuantity());
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query
                .setResultTransformer(Transformers.aliasToBean(StockGoodsSerialInforDTO.class
                        ));

        query.addScalar(
                "custId", new StringType());
        query.addScalar(
                "ownerId", new StringType());
        query.addScalar(
                "ownerType", new StringType());
        query.addScalar(
                "cellCode", new StringType());
        query.addScalar(
                "goodsId", new StringType());
        query.addScalar(
                "goodsState", new StringType());
        query.addScalar(
                "status", new StringType());
        query.addScalar(
                "channelTypeId", new StringType());
        query.addScalar(
                "fromSerial", new StringType());
        query.addScalar(
                "toSerial", new StringType());
        query.addScalar(
                "quantity", new StringType());
        query.addScalar(
                "goodsType", new StringType());
        query.addScalar(
                "goodsGroup", new StringType());
        query.addScalar(
                "goodsCode", new StringType());
        query.addScalar(
                "goodsName", new StringType());
        query.addScalar(
                "staffId", new StringType());
        query.addScalar(
                "staffCode", new StringType());
        query.addScalar(
                "staffName", new StringType());
        //ThienNG1 Update 03/08/2015
        for (int i = 0;
                i < lstParams.size();
                i++) {
            if (lstParams.get(i).toString().contains(",")) {
                List<String> lst = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(stockGoodsSerialStripDTO.getGoodsType().toString());
                query.setParameterList("gdstype", lst);
            } else {
                query.setParameter(i, lstParams.get(i));
            }
        }

        //
        return query.list();
    }

    public List<StockGoodsSerialInforDTO> getListStockGoodsSerialStripInforAll(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsSerialInforDTO> lstStockGoodsSerialInfor = null;
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //
        sql.append("SELECT   a.cust_id custId,");
        sql.append("         a.owner_id ownerId,");
        sql.append("         a.owner_type ownerType,");
        sql.append("         a.cell_code cellCode,");
        sql.append("         a.goods_id goodsId,");
        sql.append("         a.goods_state goodsState,");
        sql.append("         a.status,");
        sql.append("         a.channel_type_id channelTypeId,");
        sql.append("         a.from_serial fromSerial,");
        sql.append("         a.to_serial toSerial, a.quantity, ");
        sql.append("         g.goods_type goodsType, g.goods_group goodsGroup, ");
        sql.append("         g.code goodsCode, g.name goodsName ");
        sql.append("  FROM   stock_goods_serial_strip a, goods g ");
        sql.append("  WHERE g.cust_id = a.cust_id AND g.goods_id = a.goods_id ");

        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append("AND a.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append("AND a.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append("AND a.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            sql.append("AND a.goods_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsId());
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsState())) {
            sql.append("AND a.goods_state = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsState());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND a.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kiem theo Kenh
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getChannelTypeId())) {
            sql.append("AND a.channel_type_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getChannelTypeId());
        }
//        //Tim kiem theo from serial
//        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getFromSerial())) {
//            sql.append("AND sts.serial >= ? ");
//            lstParams.add(stockGoodsSerialStripDTO.getFromSerial());
//        }
//        //Tim kiem theo to serial
//        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getToSerial())) {
//            sql.append("AND sts.serial <= ? ");
//            lstParams.add(stockGoodsSerialStripDTO.getToSerial());
//        }
        //Tim theo cell         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCellCode())) {
            sql.append("AND a.cell_code = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCellCode());
        }
        //Tim theo Barcode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getBarcode())) {
            sql.append("AND a.barcode = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getBarcode());
        }
        //Tim theo Bincode         
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getBincode())) {
            sql.append("AND a.bincode = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getBincode());
        }
        //ThienNG1- Begin AddBy 18/06/2015 
        //Tim kien theo loai mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsType())) {
            //ThienNG1 Update 03/08/2015
            if (stockGoodsSerialStripDTO.getGoodsType().contains(",")) {
                sql.append("AND g.goods_type IN (:gdstype) ");
            } else {
                sql.append("AND g.goods_type IN (?) ");
            }
            lstParams.add(stockGoodsSerialStripDTO.getGoodsType());
        }
        //End
        sql.append(" ORDER BY a.cust_id,a.owner_id,a.owner_type,g.goods_type,g.code,a.goods_state,a.cell_code,a.from_serial,a.status ");
        //lstParams.add(stockGoodsSerialStripDTO.getQuantity());
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query
                .setResultTransformer(Transformers.aliasToBean(StockGoodsSerialInforDTO.class
                        ));

        query.addScalar(
                "custId", new StringType());
        query.addScalar(
                "ownerId", new StringType());
        query.addScalar(
                "ownerType", new StringType());
        query.addScalar(
                "cellCode", new StringType());
        query.addScalar(
                "goodsId", new StringType());
        query.addScalar(
                "goodsState", new StringType());
        query.addScalar(
                "status", new StringType());
        query.addScalar(
                "channelTypeId", new StringType());
        query.addScalar(
                "fromSerial", new StringType());
        query.addScalar(
                "toSerial", new StringType());
        query.addScalar(
                "quantity", new StringType());
        query.addScalar(
                "goodsType", new StringType());
        query.addScalar(
                "goodsGroup", new StringType());
        query.addScalar(
                "goodsCode", new StringType());
        query.addScalar(
                "goodsName", new StringType());
        //ThienNG1 Update 03/08/2015
        for (int i = 0;
                i < lstParams.size();
                i++) {
            if (lstParams.get(i).toString().contains(",")) {
                List<String> lst = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(stockGoodsSerialStripDTO.getGoodsType().toString());
                query.setParameterList("gdstype", lst);
            } else {
                query.setParameter(i, lstParams.get(i));
            }
        }

        //
        return query.list();
    }

    //ThienNG1 02.06.2015: Lay danh sach hang hoa theo vi tri
//QuyenDM 19/06/2015 lay them custId va goodsId
//QuyenDM 22/06/2015 them dieu kien cellCode
    public List<StockGoodsInforDTO> getListStockGoodsByZone(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStaffId())) {
            return getListStockGoodsByZoneByStaff(stockGoodsSerialStripDTO);
        } else {
            return getListStockGoodsByZoneAll(stockGoodsSerialStripDTO);
        }
    }

    //lay danh sach hang hoa theo vi tri(hang hoa duoc gan cho nhan vien)
    public List<StockGoodsInforDTO> getListStockGoodsByZoneByStaff(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //Mat hang ko theo serial
        sql.append(" SELECT zoneName,cellCode,goodsType,goodsCode,goodsName,amount,custId,goodsId, ");
        sql.append(" staffId, staffCode, staffName  FROM ");
        sql.append("( SELECT     z.name zoneName,");
        sql.append("            sg.cell_code cellCode,");
        sql.append("           g.goods_type goodsType,");
        sql.append("           g.code goodsCode,");
        sql.append("           g.name goodsName,");
        sql.append("           sg.goods_id goodsId,");
        sql.append("           sg.cust_id custId,");
        sql.append("           SUM (amount) amount,  msg.staff_id staffId, msg.staff_code staffCode, msg.staff_name staffName ");
        sql.append("    FROM   cells c,");
        sql.append("           zones z,");
        sql.append("           goods g,");
        sql.append("           stock_goods sg, map_staff_goods msg ");
        sql.append("   WHERE   c.zone_id = z.zone_id");
        sql.append("           AND g.cust_id = sg.cust_id");
        sql.append("           AND g.goods_id = sg.goods_id");
        sql.append("           AND c.code = sg.cell_code ");
        sql.append("           AND sg.goods_id = msg.goods_id(+)");
        //Tim kiem theo nham vien
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStaffId())) {
            sql.append("      AND msg.staff_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStaffId());
        }
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append(" AND sg.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append(" AND sg.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append(" AND sg.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            //sql.append("AND sg.goods_id = ? ");
            //lstParams.add(stockGoodsSerialStripDTO.getGoodsId());
            sql.append("AND sg.goods_id IN (");
            sql.append(stockGoodsSerialStripDTO.getGoodsId());
            sql.append(") ");
        }
        //Tim kien theo loai mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsType())) {
            sql.append("AND g.goods_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsType());
        }
        //Tim kiem trang thai serial hang hoa - QuyenDM them ngay 05/10/2015
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND sg.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kien gan dung theo cellCode
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCellCode())) {
            sql.append("AND sg.cell_code LIKE ? ");
            lstParams.add("%" + stockGoodsSerialStripDTO.getCellCode() + "%");
        }
        sql.append(" GROUP BY z.name, sg.cell_code,g.goods_type,g.code,g.name,sg.goods_id,sg.cust_id,msg.staff_id, msg.staff_code, msg.staff_name ");
        //Mat hang theo serial don le
        sql.append(" UNION ALL ");
        sql.append("SELECT     z.name zoneName,");
        sql.append("           sgs.cell_code cellCode,");
        sql.append("           g.goods_type goodsType,");
        sql.append("           g.code goodsCode,");
        sql.append("           g.name goodsName,");
        sql.append("           sgs.goods_id goodsId,");
        sql.append("           sgs.cust_id custId,");
        sql.append("           COUNT ( * ) amount, msg.staff_id staffId, msg.staff_code staffCode, msg.staff_name staffName  ");
        sql.append("    FROM   cells c,");
        sql.append("           zones z,");
        sql.append("           goods g,");
        sql.append("           stock_goods_serial sgs, map_staff_goods msg ");
        sql.append("   WHERE   c.zone_id = z.zone_id");
        sql.append("           AND g.cust_id = sgs.cust_id");
        sql.append("           AND g.goods_id = sgs.goods_id");
        sql.append("           AND c.code = sgs.cell_code ");
        sql.append("           AND sgs.goods_id = msg.goods_id(+)");
        //Tim kiem theo nham vien
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStaffId())) {
            sql.append("      AND msg.staff_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStaffId());
        }
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append("AND sgs.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append("AND sgs.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append("AND sgs.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND sgs.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            //sql.append("AND sgs.goods_id = ? ");
            //lstParams.add(stockGoodsSerialStripDTO.getGoodsId());
            sql.append("AND sgs.goods_id IN (");
            sql.append(stockGoodsSerialStripDTO.getGoodsId());
            sql.append(") ");
        }
        //Tim kien theo loai mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsType())) {
            sql.append("AND g.goods_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsType());
        }
        //Tim kien gan dung theo cellCode
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCellCode())) {
            sql.append("AND sgs.cell_code LIKE ? ");
            lstParams.add("%" + stockGoodsSerialStripDTO.getCellCode() + "%");
        }
        sql.append(" GROUP BY z.name,sgs.cell_code,g.goods_type,g.code,g.name,sgs.goods_id,sgs.cust_id,msg.staff_id, msg.staff_code, msg.staff_name ");
        //
        //Mat hang serial theo dai
        sql.append(" UNION ALL ");
        sql.append("SELECT     z.name zoneName,");
        sql.append("           sgst.cell_code cellCode,");
        sql.append("           g.goods_type goodsType,");
        sql.append("           g.code goodsCode,");
        sql.append("           g.name goodsName,");
        sql.append("           sgst.goods_id goodsId,");
        sql.append("           sgst.cust_id custId,");
        sql.append("           SUM (sgst.quantity) amount, msg.staff_id staffId, msg.staff_code staffCode, msg.staff_name staffName ");
        sql.append("    FROM   cells c,");
        sql.append("           zones z,");
        sql.append("           goods g,");
        sql.append("           stock_goods_serial_strip sgst, map_staff_goods msg ");
        sql.append("   WHERE   c.zone_id = z.zone_id");
        sql.append("           AND g.cust_id = sgst.cust_id");
        sql.append("           AND g.goods_id = sgst.goods_id");
        sql.append("           AND c.code = sgst.cell_code ");
        sql.append("           AND sgst.goods_id = msg.goods_id(+)");
        //Tim kiem theo nham vien
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStaffId())) {
            sql.append("      AND msg.staff_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStaffId());
        }
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append("AND sgst.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang    
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append("AND sgst.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append("AND sgst.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND sgst.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            //sql.append("AND sgst.goods_id = ? ");
            //lstParams.add(stockGoodsSerialStripDTO.getGoodsId());
            sql.append("AND sgst.goods_id IN (");
            sql.append(stockGoodsSerialStripDTO.getGoodsId());
            sql.append(") ");
        }
        //Tim kien theo loai mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsType())) {
            sql.append("AND g.goods_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsType());
        }
        //Tim kien gan dung theo cellCode
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCellCode())) {
            sql.append("AND sgst.cell_code LIKE ? ");
            lstParams.add("%" + stockGoodsSerialStripDTO.getCellCode() + "%");
        }
        sql.append(" GROUP BY z.name,sgst.cell_code,g.goods_type,g.code,g.name,sgst.goods_id,sgst.cust_id,msg.staff_id, msg.staff_code, msg.staff_name ");
        sql.append(" ) ");
        sql.append(" WHERE amount > 0 ");
        sql.append(" ORDER BY zoneName,goodsType,cellCode,goodsCode ");

        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query
                .setResultTransformer(Transformers.aliasToBean(StockGoodsInforDTO.class
                        ));

        query.addScalar(
                "zoneName", new StringType());
        query.addScalar(
                "cellCode", new StringType());
        query.addScalar(
                "amount", new StringType());
        query.addScalar(
                "goodsType", new StringType());
        query.addScalar(
                "goodsCode", new StringType());
        query.addScalar(
                "goodsName", new StringType());
        query.addScalar(
                "goodsId", new StringType());
        query.addScalar(
                "custId", new StringType());
        query.addScalar(
                "staffId", new StringType());
        query.addScalar(
                "staffCode", new StringType());
        query.addScalar(
                "staffName", new StringType());
        //
        for (int i = 0;
                i < lstParams.size();
                i++) {
            query.setParameter(i, lstParams.get(i));
        }
        //

        return query.list();
    }

    public List<StockGoodsInforDTO> getListStockGoodsByZoneAll(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //Mat hang ko theo serial
        sql.append(" SELECT zoneName,cellCode,goodsType,goodsCode,goodsName,amount,custId,goodsId ");
        sql.append("  FROM ( SELECT     z.name zoneName,");
        sql.append("            sg.cell_code cellCode,");
        sql.append("           g.goods_type goodsType,");
        sql.append("           g.code goodsCode,");
        sql.append("           g.name goodsName,");
        sql.append("           sg.goods_id goodsId,");
        sql.append("           sg.cust_id custId,");
        sql.append("           SUM (amount) amount ");
        sql.append("    FROM   cells c,");
        sql.append("           zones z,");
        sql.append("           goods g,");
        sql.append("           stock_goods sg ");
        sql.append("   WHERE   c.zone_id = z.zone_id");
        sql.append("           AND g.cust_id = sg.cust_id");
        sql.append("           AND g.goods_id = sg.goods_id");
        sql.append("           AND c.code = sg.cell_code ");
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append(" AND sg.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append(" AND sg.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append(" AND sg.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            sql.append("AND sg.goods_id IN (");
            sql.append(stockGoodsSerialStripDTO.getGoodsId());
            sql.append(") ");
        }
        //Tim kien theo loai mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsType())) {
            sql.append("AND g.goods_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsType());
        }
        //Tim kiem trang thai serial hang hoa - QuyenDM them ngay 05/10/2015
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND sg.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kien gan dung theo cellCode
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCellCode())) {
            sql.append("AND sg.cell_code LIKE ? ");
            lstParams.add("%" + stockGoodsSerialStripDTO.getCellCode() + "%");
        }
        sql.append(" GROUP BY z.name, sg.cell_code,g.goods_type,g.code,g.name,sg.goods_id,sg.cust_id ");
        //Mat hang theo serial don le
        sql.append(" UNION ALL ");
        sql.append("SELECT     z.name zoneName,");
        sql.append("           sgs.cell_code cellCode,");
        sql.append("           g.goods_type goodsType,");
        sql.append("           g.code goodsCode,");
        sql.append("           g.name goodsName,");
        sql.append("           sgs.goods_id goodsId,");
        sql.append("           sgs.cust_id custId,");
        sql.append("           COUNT ( * ) amount  ");
        sql.append("    FROM   cells c,");
        sql.append("           zones z,");
        sql.append("           goods g,");
        sql.append("           stock_goods_serial sgs ");
        sql.append("   WHERE   c.zone_id = z.zone_id");
        sql.append("           AND g.cust_id = sgs.cust_id");
        sql.append("           AND g.goods_id = sgs.goods_id");
        sql.append("           AND c.code = sgs.cell_code ");
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append("AND sgs.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append("AND sgs.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append("AND sgs.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND sgs.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            //sql.append("AND sgs.goods_id = ? ");
            //lstParams.add(stockGoodsSerialStripDTO.getGoodsId());
            sql.append("AND sgs.goods_id IN (");
            sql.append(stockGoodsSerialStripDTO.getGoodsId());
            sql.append(") ");
        }
        //Tim kien theo loai mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsType())) {
            sql.append("AND g.goods_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsType());
        }
        //Tim kien gan dung theo cellCode
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCellCode())) {
            sql.append("AND sgs.cell_code LIKE ? ");
            lstParams.add("%" + stockGoodsSerialStripDTO.getCellCode() + "%");
        }
        sql.append(" GROUP BY z.name,sgs.cell_code,g.goods_type,g.code,g.name,sgs.goods_id,sgs.cust_id ");
        //
        //Mat hang serial theo dai
        sql.append(" UNION ALL ");
        sql.append("SELECT     z.name zoneName,");
        sql.append("           sgst.cell_code cellCode,");
        sql.append("           g.goods_type goodsType,");
        sql.append("           g.code goodsCode,");
        sql.append("           g.name goodsName,");
        sql.append("           sgst.goods_id goodsId,");
        sql.append("           sgst.cust_id custId,");
        sql.append("           SUM (sgst.quantity) amount ");
        sql.append("    FROM   cells c,");
        sql.append("           zones z,");
        sql.append("           goods g,");
        sql.append("           stock_goods_serial_strip sgst ");
        sql.append("   WHERE   c.zone_id = z.zone_id");
        sql.append("           AND g.cust_id = sgst.cust_id");
        sql.append("           AND g.goods_id = sgst.goods_id");
        sql.append("           AND c.code = sgst.cell_code ");

        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append("AND sgst.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang    
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append("AND sgst.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append("AND sgst.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND sgst.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            //sql.append("AND sgst.goods_id = ? ");
            //lstParams.add(stockGoodsSerialStripDTO.getGoodsId());
            sql.append("AND sgst.goods_id IN (");
            sql.append(stockGoodsSerialStripDTO.getGoodsId());
            sql.append(") ");
        }
        //Tim kien theo loai mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsType())) {
            sql.append("AND g.goods_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsType());
        }
        //Tim kien gan dung theo cellCode
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCellCode())) {
            sql.append("AND sgst.cell_code LIKE ? ");
            lstParams.add("%" + stockGoodsSerialStripDTO.getCellCode() + "%");
        }
        sql.append(" GROUP BY z.name,sgst.cell_code,g.goods_type,g.code,g.name,sgst.goods_id,sgst.cust_id ");
        sql.append(" ) ");
        sql.append(" WHERE amount > 0 ");
        sql.append(" ORDER BY zoneName,goodsType,cellCode,goodsCode ");

        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query
                .setResultTransformer(Transformers.aliasToBean(StockGoodsInforDTO.class
                        ));

        query.addScalar(
                "zoneName", new StringType());
        query.addScalar(
                "cellCode", new StringType());
        query.addScalar(
                "amount", new StringType());
        query.addScalar(
                "goodsType", new StringType());
        query.addScalar(
                "goodsCode", new StringType());
        query.addScalar(
                "goodsName", new StringType());
        query.addScalar(
                "goodsId", new StringType());
        query.addScalar(
                "custId", new StringType());
        //
        for (int i = 0;
                i < lstParams.size();
                i++) {
            query.setParameter(i, lstParams.get(i));
        }
        //

        return query.list();
    }

//ThienNG1 22/06/2015
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialBySerial(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsSerialStripDTO> lstStockGoodsSerialStrips = null;
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //
        sql.append("SELECT   a.cust_id custId,");
        sql.append("         a.owner_id ownerId,");
        sql.append("         a.owner_type ownerType,");
        sql.append("         a.cell_code cellCode,");
        sql.append("         a.goods_id goodsId,");
        sql.append("         a.state goodsState,");
        sql.append("         a.status,");
        sql.append("         a.channel_type_id channelTypeId,");
        sql.append("         a.from_serial fromSerial,");
        sql.append("         a.to_serial toSerial, a.quantity, ");
        sql.append("         g.goods_type goodsType, g.goods_group goodsGroup, ");
        sql.append("         g.code goodsCode, g.name goodsName ");
        sql.append("  FROM   table(show_serial_strip (cursor (  SELECT   sts.cust_id,");
        sql.append("                                                     sts.owner_id,");
        sql.append("                                                     sts.owner_type,");
        sql.append("                                                     sts.cell_code,");
        sql.append("                                                     sts.goods_id,");
        sql.append("                                                     sts.goods_state,");
        sql.append("                                                     sts.status,");
        sql.append("                                                     sts.channel_type_id,");
        sql.append("                                                     sts.serial");
        sql.append("                                              FROM   stock_goods_serial sts");
        sql.append("                                             WHERE   1 = 1 ");
        //Tim kiem theo khach hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getCustId())) {
            sql.append("AND sts.cust_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerId())) {
            sql.append("AND sts.owner_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getOwnerType())) {
            sql.append("AND sts.owner_type = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getOwnerType());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsId())) {
            sql.append("AND sts.goods_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsId());
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsState())) {
            sql.append("AND sts.goods_state = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsState());
        }
        //Tim kiem trang thai serial hang hoa
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("AND sts.status = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }
        //Tim kiem theo Kenh
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getChannelTypeId())) {
            sql.append("AND sts.channel_type_id = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getChannelTypeId());
        }
        //Tim kiem theo from serial
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getFromSerial())) {
            sql.append("AND sts.serial >= ? ");
            lstParams.add(stockGoodsSerialStripDTO.getFromSerial());
        }
        //Tim kiem theo to serial
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getToSerial())) {
            sql.append("AND sts.serial <= ? ");
            lstParams.add(stockGoodsSerialStripDTO.getToSerial());
        }

        //
        sql.append(" ORDER BY sts.cust_id,sts.owner_id,sts.owner_type,sts.cell_code, ");
        sql.append("          sts.goods_id,sts.goods_state,sts.status,sts.serial),");
        sql.append("                    TO_NUMBER (?))) a, goods g WHERE g.cust_id = a.cust_id AND g.goods_id = a.goods_id ");
        lstParams.add(stockGoodsSerialStripDTO.getQuantity());
        //Tim kien theo loai mat hang
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getGoodsGroup())) {
            sql.append("AND g.goods_group = ? ");
            lstParams.add(stockGoodsSerialStripDTO.getGoodsGroup());
        }
        sql.append(" ORDER BY a.cust_id,a.owner_id,a.owner_type,a.cell_code, g.goods_type,g.code,a.state,a.status ");

        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query
                .setResultTransformer(Transformers.aliasToBean(StockGoodsSerialStripDTO.class
                        ));

        query.addScalar(
                "custId", new StringType());
        query.addScalar(
                "ownerId", new StringType());
        query.addScalar(
                "ownerType", new StringType());
        query.addScalar(
                "cellCode", new StringType());
        query.addScalar(
                "goodsId", new StringType());
        query.addScalar(
                "goodsState", new StringType());
        query.addScalar(
                "status", new StringType());
        query.addScalar(
                "channelTypeId", new StringType());
        query.addScalar(
                "fromSerial", new StringType());
        query.addScalar(
                "toSerial", new StringType());
        query.addScalar(
                "quantity", new StringType());
        query.addScalar(
                "goodsType", new StringType());
        query.addScalar(
                "goodsGroup", new StringType());
        query.addScalar(
                "goodsCode", new StringType());
        query.addScalar(
                "goodsName", new StringType());
        //
        for (int i = 0;
                i < lstParams.size();
                i++) {
            query.setParameter(i, lstParams.get(i));
        }

        //
        return query.list();
    }

//duyot - dieu chuyen hang hoa - cap nhat thong tin hang hoa serialtrip
//duyot: cap nhat:
/*
     Khi hang xuat phai ghep nhieu dai serial -> list da xuat la danh sach stock_goods_serial_trip
     */
    public ResultDTO updateNewListGoods(StockGoodsSerialStripDTO importStrip, List<StockGoodsSerialStrip> exportedTrip, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc;
        //create sql
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE stock_goods_serial_strip ");
        sql.append(" SET goods_id = ?, ");
        sql.append("  goods_state = ?, ");
        sql.append("  status = ?, ");
        //duyot: 29/01/2016
        if (!DataUtil.isStringNullOrEmpty(importStrip.getOrderId())) {
            sql.append("  order_id = ?, ");
        }
        sql.append("  import_stock_trans_id = ?, ");
        //duyot: 30/01/2016
        sql.append("  cell_code = ? ");

        sql.append(" WHERE cust_id=? AND owner_id=? AND goods_id=? ");
        sql.append("   AND from_serial=? AND to_serial=? ");
        //clone ra doi tuong de kiem tra KIT ton tai
        List<StockGoodsSerialInforDTO> lstError = new ArrayList();
        StockGoodsSerialStrip cloneDTO = (StockGoodsSerialStrip) DataUtil.cloneObject(importStrip.toModel());
        cloneDTO.setStatus("1");//trang thai hang hoa trong kho
        try {//cap nhat lai danh sach list da xuat
            for (StockGoodsSerialStrip i : exportedTrip) {
                //kiem tra KIT co trong kho hay chua
                cloneDTO.setFromSerial(i.getFromSerial());
                cloneDTO.setToSerial(i.getToSerial());
                cloneDTO.setCustId(i.getCustId());
                cloneDTO.setOwnerId(i.getOwnerId());
                cloneDTO.setGoodsState(null);
                List<StockGoodsSerialStrip> lstListStockGoodsSerialStrip = getListStockGoodsSerialStrip(cloneDTO);
                if (!DataUtil.isListNullOrEmpty(lstListStockGoodsSerialStrip)) {
                    message = ParamUtils.FAIL;
                    key = ParamUtils.ERROR_MESSAGE.GET_LIST_INFO_FAIL;//Key loi da ton tai KIT trong kho
                    StockGoodsSerialInforDTO serialInforDTO = new StockGoodsSerialInforDTO();
                    serialInforDTO.setFromSerial(i.getFromSerial());
                    serialInforDTO.setToSerial(i.getToSerial());
                    serialInforDTO.setGoodsId(importStrip.getGoodsId());
                    lstError.add(serialInforDTO);
                    break;
                } else {
                    Query query = session.createSQLQuery(sql.toString());
                    if (!DataUtil.isStringNullOrEmpty(importStrip.getOrderId())) {
                        query.setParameter(0, importStrip.getGoodsId());
                        query.setParameter(1, importStrip.getGoodsState());
                        query.setParameter(2, importStrip.getStatus());
                        //
                        query.setParameter(3, importStrip.getOrderId());
                        query.setParameter(4, importStrip.getImportStockTransId());
                        //
                        query.setParameter(5, importStrip.getCellCode());
                        //
                        query.setParameter(6, i.getCustId());
                        query.setParameter(7, i.getOwnerId());
                        query.setParameter(8, i.getGoodsId());
                        query.setParameter(9, i.getFromSerial());
                        query.setParameter(10, i.getToSerial());
                    } else {
                        query.setParameter(0, importStrip.getGoodsId());
                        query.setParameter(1, importStrip.getGoodsState());
                        query.setParameter(2, importStrip.getStatus());
                        //
                        query.setParameter(3, importStrip.getImportStockTransId());
                        //
                        query.setParameter(4, importStrip.getCellCode());
                        //
                        query.setParameter(5, i.getCustId());
                        query.setParameter(6, i.getOwnerId());
                        query.setParameter(7, i.getGoodsId());
                        query.setParameter(8, i.getFromSerial());
                        query.setParameter(9, i.getToSerial());
                    }
                    //cap nhat
                    quantitySucc = query.executeUpdate();
                    //neu k the cap 
                    if (quantitySucc == 0) {
                        message = ParamUtils.FAIL;
                        key = ParamUtils.NOT_FOUND_DATA;
                    }
                }
            }//end for
        } catch (Exception ex) {
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_ERROR;
        }
        //
        resultDTO.setMessage(message);
        resultDTO.setKey(key);
        resultDTO.setQuantitySucc(Integer.parseInt(importStrip.getQuantity()));
        resultDTO.setQuantityFail(0);
        resultDTO.setAmount(Double.parseDouble(importStrip.getQuantity()));
        resultDTO.setAmountIssue(Double.parseDouble(importStrip.getQuantity()));
        resultDTO.setLstStockGoodsSerialInforDTO(lstError);
        return resultDTO;
    }

    public ResultDTO updateNewGoods(StockGoodsSerialStripDTO importStrip, StockGoodsSerialStripDTO exportedTrip, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE stock_goods_serial_strip ");
            sql.append(" SET goods_id = ?, ");
            sql.append("  goods_state = ?, ");
            sql.append("  status = ? ");
            sql.append(" WHERE cust_id=? AND owner_id=? AND goods_id=? ");
            sql.append("   AND from_serial=? AND to_serial=? ");
            //
            //
            Query query = session.createSQLQuery(sql.toString());

            query.setParameter(0, importStrip.getGoodsId());
            query.setParameter(1, importStrip.getGoodsState());
            query.setParameter(2, importStrip.getStatus());
            query.setParameter(3, exportedTrip.getCustId());
            query.setParameter(4, exportedTrip.getOwnerId());
            query.setParameter(5, exportedTrip.getGoodsId());
            query.setParameter(6, exportedTrip.getFromSerial());
            query.setParameter(7, exportedTrip.getToSerial());

            //cap nhat
            quantitySucc = query.executeUpdate();
            //neu k the cap 
            if (quantitySucc == 0) {
                message = ParamUtils.FAIL;
                key = ParamUtils.NOT_FOUND_DATA;
            }
        } catch (Exception ex) {
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_ERROR;
        }
        //
        resultDTO.setMessage(message);
        resultDTO.setKey(key);
        resultDTO.setQuantitySucc(Integer.parseInt(importStrip.getQuantity()));
        resultDTO.setQuantityFail(0);
        resultDTO.setAmount(Double.parseDouble(importStrip.getQuantity()));
        resultDTO.setAmountIssue(Double.parseDouble(importStrip.getQuantity()));
        return resultDTO;
    }

    //addBy ThienNg1 cap nhat hang thu hoi 
    public ResultDTO reImportStockGoodsSerialStrip(StockGoodsSerialStrip oldStockGoodsSerialStrip,
            StockGoodsSerialStrip newStockGoodsSerialStrip, String stockTransId, Session session, String synImportRevoke) {
        //
        if (Constants.STATUS_SERIAL_WAIT_STOCK.equals(synImportRevoke)) {
            newStockGoodsSerialStrip.setStatus(synImportRevoke);
        }
        //
        ResultDTO resultDTO = new ResultDTO();
        Double amount = 0D;
        Double amountIssue = 0D;
        Long quantity = 0L;
        int insertSuccess = 0;
        int insertFail = 0;
        String message = ParamUtils.SUCCESS;
        String key = "";
        int lenghtSerialImport = newStockGoodsSerialStrip.getFromSerial().length();
        //Tinh so luong cua hang thu hoi
        String fromSerial = newStockGoodsSerialStrip.getFromSerial();
        String toSerial = newStockGoodsSerialStrip.getToSerial();
        //Danh sach Insert khi so luong khong du
        List<StockGoodsSerialStrip> lstInsertStockGoodsSerialStrip = new ArrayList();

        //doi tuong loi
        StockGoodsSerialInforDTO stockGoodsSerialInforErrorDTO = new StockGoodsSerialInforDTO();
        stockGoodsSerialInforErrorDTO.setGoodsId(newStockGoodsSerialStrip.getGoodsId().toString());
        stockGoodsSerialInforErrorDTO.setGoodsState(newStockGoodsSerialStrip.getGoodsState());
        stockGoodsSerialInforErrorDTO.setFromSerial(newStockGoodsSerialStrip.getFromSerial());
        stockGoodsSerialInforErrorDTO.setToSerial(newStockGoodsSerialStrip.getToSerial());
        stockGoodsSerialInforErrorDTO.setQuantity(newStockGoodsSerialStrip.getQuantity().toString());
        stockGoodsSerialInforErrorDTO.setBarcode(newStockGoodsSerialStrip.getBarcode());
        //
        String suffixFromSerial = "";
        String suffixToSerial = "";
        Long lFromSerial = 0L;
        Long lToSerial = 0L;

        if (newStockGoodsSerialStrip.getFromSerial().length() >= Constants.SERIAL_LIMIT) {
            suffixFromSerial = fromSerial.substring(fromSerial.length() - Constants.SERIAL_LIMIT, fromSerial.length());
            suffixToSerial = toSerial.substring(toSerial.length() - Constants.SERIAL_LIMIT, toSerial.length());
            lFromSerial = Long.parseLong(suffixFromSerial);
            lToSerial = Long.parseLong(suffixToSerial);
        } else {
            lFromSerial = Long.parseLong(newStockGoodsSerialStrip.getFromSerial());
            lToSerial = Long.parseLong(newStockGoodsSerialStrip.getToSerial());
        }
        quantity = lToSerial - lFromSerial + 1;
        Long quantityTotal = 0L;
        Long quantityNew = 0L;

        //
        List<StockGoodsSerialStrip> lstStockGoodsSerialStrip = new ArrayList<>();
        StockGoodsSerialStrip insertStockGoodsSerialStrip;
        try {
            //Tim kiem danh sach hang hoa theo serial thu hoi
            lstStockGoodsSerialStrip = getListStockGoodsSerialStrip(oldStockGoodsSerialStrip, session);
            //Neu khong tim thay ban ghi nao trong DB thi coi nhu hang moi va Insert vao stock_goods_serial_strip
            //
            if (Constants.STATUS_SERIAL_WAIT_STOCK.equals(synImportRevoke)) {
                newStockGoodsSerialStrip.setStatus(synImportRevoke);
            }
            //
            if (lstStockGoodsSerialStrip == null || lstStockGoodsSerialStrip.isEmpty()) {
                //them moi cho hang can thu hoi
                try {
                    String saveMeg = saveObjectSession(newStockGoodsSerialStrip, session);
                    if (!StringUtils.isInteger(saveMeg)) {
                        resultDTO.setMessage(Constants.ERROR_MESSAGE.INSERT_GOODS_NEW_ERROR);
                        List<StockGoodsSerialInforDTO> lstError = new ArrayList();
                        lstError.add(stockGoodsSerialInforErrorDTO);
                        resultDTO.setLstStockGoodsSerialInforDTO(lstError);
                    } else {
                        resultDTO.setMessage(message);
                    }
                } catch (Exception e) {
                    //tra ra resultDTO
                    resultDTO.setKey(newStockGoodsSerialStrip.getGoodsId().toString() + "," + newStockGoodsSerialStrip.getGoodsState());
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setLstStockGoodsSerialInforDTO(null);
                    List<StockGoodsSerialInforDTO> lstError = new ArrayList();
                    lstError.add(stockGoodsSerialInforErrorDTO);
                    resultDTO.setLstStockGoodsSerialInforDTO(lstError);
                    return resultDTO;
                }
                resultDTO.setAmount(Double.parseDouble(quantity.toString()));
                resultDTO.setAmountIssue(Double.parseDouble(quantity.toString()));
                return resultDTO;
            }

            for (StockGoodsSerialStrip lstStockGoodsSerialStrip1 : lstStockGoodsSerialStrip) {
                //neu trong danh sach co 1 phan tu co trang thai = 1(trong kho)=> rollback
                if (!Constants.STATUS_SERIAL_OUT_STOCK.equals(lstStockGoodsSerialStrip1.getStatus())
                        && lstStockGoodsSerialStrip1.getFromSerial().length() == lenghtSerialImport) {
                    //tra ve resultDTO voi dai serial co trang thai = 1
                    resultDTO.setFromSerial(newStockGoodsSerialStrip.getFromSerial());
                    resultDTO.setToSerial(newStockGoodsSerialStrip.getToSerial());
                    //resultDTO.setKey(newStockGoodsSerialStrip.getGoodsId().toString() + "," + newStockGoodsSerialStrip.getGoodsState());
                    resultDTO.setAmount(amount);
                    resultDTO.setAmountIssue(amountIssue);
                    if (Constants.STATUS_SERIAL_WAIT_STOCK.equals(lstStockGoodsSerialStrip1.getStatus())) {
                        resultDTO.setMessage(Constants.ERROR_MESSAGE.GOODS_SERIAL_WAIT_STOCK);
                    } else {
                        resultDTO.setMessage(Constants.ERROR_MESSAGE.GOODS_SERIAL_IN_STOCK);
                    }
                    List<StockGoodsSerialInforDTO> lstError = new ArrayList();
                    lstError.add(stockGoodsSerialInforErrorDTO);
                    resultDTO.setLstStockGoodsSerialInforDTO(lstError);
                    return resultDTO;
                }
            }

            //Insert du lieu 
            int iSize = lstStockGoodsSerialStrip.size();
            StockGoodsSerialStrip stockGoodsSerialStrip;
            int indexError = 0;
            //Kiem tra so luong can xuat thanh cong
            for (int i = 0; i < iSize; i++) {
                stockGoodsSerialStrip = lstStockGoodsSerialStrip.get(i);
                //check thua, thieu so 0
                if (stockGoodsSerialStrip.getFromSerial().length() == lenghtSerialImport) {
                    insertStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(newStockGoodsSerialStrip);
                    //Xu ly ban ghi dau tien
                    if (i == 0) {
                        if (Long.parseLong(fromSerial) > Long.parseLong(stockGoodsSerialStrip.getFromSerial())) {
                            insertStockGoodsSerialStrip.setFromSerial(fromSerial);
                        } else {
                            insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                        }
                        //
                        if (Long.parseLong(toSerial) < Long.parseLong(stockGoodsSerialStrip.getToSerial())) {
                            insertStockGoodsSerialStrip.setToSerial(toSerial);
                        } else {
                            insertStockGoodsSerialStrip.setToSerial(stockGoodsSerialStrip.getToSerial());
                        }
                    } else //Xu ly ban ghi cuoi cung
                    if (i == iSize - 1) {
                        if (Long.parseLong(toSerial) < Long.parseLong(stockGoodsSerialStrip.getToSerial())) {
                            insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                            insertStockGoodsSerialStrip.setToSerial(toSerial);
                        } else {
                            insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                            insertStockGoodsSerialStrip.setToSerial(stockGoodsSerialStrip.getToSerial());
                        }
                    } else {
                        insertStockGoodsSerialStrip.setFromSerial(stockGoodsSerialStrip.getFromSerial());
                        insertStockGoodsSerialStrip.setToSerial(stockGoodsSerialStrip.getToSerial());
                    }
                    //
                    quantityNew = Long.parseLong(insertStockGoodsSerialStrip.getToSerial()) - Long.parseLong(insertStockGoodsSerialStrip.getFromSerial()) + 1;
                    insertStockGoodsSerialStrip.setQuantity(quantityNew);
                    insertStockGoodsSerialStrip.setChannelTypeId(stockGoodsSerialStrip.getChannelTypeId());
                    insertStockGoodsSerialStrip.setCellCode(stockGoodsSerialStrip.getCellCode());
                    insertStockGoodsSerialStrip.setBincode(stockGoodsSerialStrip.getBincode());
                    insertStockGoodsSerialStrip.setBarcode(stockGoodsSerialStrip.getBarcode());
                    insertStockGoodsSerialStrip.setPrice(stockGoodsSerialStrip.getPrice());
                    insertStockGoodsSerialStrip.setAddInfor(stockGoodsSerialStrip.getAddInfor());
                    insertStockGoodsSerialStrip.setImportDate(stockGoodsSerialStrip.getImportDate());
                    insertStockGoodsSerialStrip.setSaleDate(stockGoodsSerialStrip.getSaleDate());
                    insertStockGoodsSerialStrip.setSaleType(stockGoodsSerialStrip.getSaleType());
                    //Insert du lieu dai serial                
                    resultDTO = deleteAndInsertStockGoodsSerialStrip(stockGoodsSerialStrip, insertStockGoodsSerialStrip, newStockGoodsSerialStrip, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        //tra ra resultDTO
                        //resultDTO.setKey(newStockGoodsSerialStrip.getGoodsId().toString() + "," + newStockGoodsSerialStrip.getGoodsState());
                        resultDTO.setAmount(Double.parseDouble(quantity.toString()));
                        resultDTO.setAmountIssue(Double.parseDouble(quantity.toString()));
                        resultDTO.setMessage(Constants.ERROR_MESSAGE.DELELE_AND_UPDATE_ERROR);
                        List<StockGoodsSerialInforDTO> lstError = new ArrayList();
                        lstError.add(stockGoodsSerialInforErrorDTO);
                        resultDTO.setLstStockGoodsSerialInforDTO(lstError);
                        return resultDTO;
                    }
                    quantityTotal = quantityTotal + insertStockGoodsSerialStrip.getQuantity();
                } else {
                    indexError++;
                }
            }
            if (indexError != iSize) {
                //kiem tra neu so luong khong du thi Insert dai con thieu
                if (quantityTotal < quantity) {
                    Long firtQuantity = 0L;
                    Long lastQuantity = 0L;
                    Long centerQuantity = 0L;
                    if (iSize == 1) {
                        //ban ghi dau
                        StockGoodsSerialStrip firstStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(newStockGoodsSerialStrip);
                        String firstToSerial = Long.parseLong(lstStockGoodsSerialStrip.get(0).getFromSerial()) - 1 + "";
                        firstStockGoodsSerialStrip.setToSerial(DataUtil.lPad(firstToSerial, "0", lenghtSerialImport));
                        firtQuantity = Long.parseLong(firstStockGoodsSerialStrip.getToSerial()) - Long.parseLong(firstStockGoodsSerialStrip.getFromSerial()) + 1;
                        firstStockGoodsSerialStrip.setQuantity(firtQuantity);
                        lstInsertStockGoodsSerialStrip.add(firstStockGoodsSerialStrip);
                        //ban ghi cuoi
                        StockGoodsSerialStrip lastStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(newStockGoodsSerialStrip);
                        String lastFromSerial = Long.parseLong(lstStockGoodsSerialStrip.get(0).getToSerial()) + 1 + "";
                        lastStockGoodsSerialStrip.setFromSerial(DataUtil.lPad(lastFromSerial, "0", lenghtSerialImport));
                        lastQuantity = Long.parseLong(lastStockGoodsSerialStrip.getToSerial()) - Long.parseLong(lastStockGoodsSerialStrip.getFromSerial()) + 1;
                        lastStockGoodsSerialStrip.setQuantity(lastQuantity);
                        lstInsertStockGoodsSerialStrip.add(lastStockGoodsSerialStrip);
                    } else {
                        for (int i = 0; i < iSize - 1; i++) {
                            if (i == 0) {//ban ghi dau tien
                                if (Long.parseLong(newStockGoodsSerialStrip.getFromSerial()) < Long.parseLong(lstStockGoodsSerialStrip.get(i).getFromSerial())) {
                                    StockGoodsSerialStrip firstStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(newStockGoodsSerialStrip);
                                    String firstToSerial = Long.parseLong(lstStockGoodsSerialStrip.get(i).getFromSerial()) - 1 + "";
                                    firstStockGoodsSerialStrip.setToSerial(DataUtil.lPad(firstToSerial, "0", lenghtSerialImport));
                                    firtQuantity = Long.parseLong(firstStockGoodsSerialStrip.getToSerial()) - Long.parseLong(firstStockGoodsSerialStrip.getFromSerial()) + 1;
                                    firstStockGoodsSerialStrip.setQuantity(firtQuantity);
                                    lstInsertStockGoodsSerialStrip.add(firstStockGoodsSerialStrip);
                                }
                            }
                            if (Long.parseLong(lstStockGoodsSerialStrip.get(i).getToSerial())
                                    != Long.parseLong(lstStockGoodsSerialStrip.get(i + 1).getFromSerial())) {
                                StockGoodsSerialStrip centerStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(newStockGoodsSerialStrip);
                                String centerFromSerial = Long.parseLong(lstStockGoodsSerialStrip.get(i).getToSerial()) + 1 + "";
                                String centerToSerial = Long.parseLong(lstStockGoodsSerialStrip.get(i + 1).getFromSerial()) - 1 + "";
                                centerStockGoodsSerialStrip.setFromSerial((DataUtil.lPad(centerFromSerial, "0", lenghtSerialImport)));
                                centerStockGoodsSerialStrip.setToSerial((DataUtil.lPad(centerToSerial, "0", lenghtSerialImport)));
                                centerQuantity = Long.parseLong(centerStockGoodsSerialStrip.getToSerial()) - Long.parseLong(centerStockGoodsSerialStrip.getFromSerial()) + 1;
                                centerStockGoodsSerialStrip.setQuantity(centerQuantity);
                                lstInsertStockGoodsSerialStrip.add(centerStockGoodsSerialStrip);
                            }
                            if (i == iSize - 2) {//ban ghi cuoi cung
                                if (Long.parseLong(newStockGoodsSerialStrip.getToSerial()) > Long.parseLong(lstStockGoodsSerialStrip.get(i + 1).getToSerial())) {
                                    StockGoodsSerialStrip lastStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(newStockGoodsSerialStrip);
                                    String lastFromSerial = Long.parseLong(lstStockGoodsSerialStrip.get(i + 1).getToSerial()) + 1 + "";
                                    lastStockGoodsSerialStrip.setFromSerial(DataUtil.lPad(lastFromSerial, "0", lenghtSerialImport));
                                    lastQuantity = Long.parseLong(lastStockGoodsSerialStrip.getToSerial()) - Long.parseLong(lastStockGoodsSerialStrip.getFromSerial()) + 1;
                                    lastStockGoodsSerialStrip.setQuantity(lastQuantity);
                                    lstInsertStockGoodsSerialStrip.add(lastStockGoodsSerialStrip);
                                }
                            }
                        }
                    }
                }

                //vong lap insert nhung ban ghi le
                for (StockGoodsSerialStrip serialStripInsertNew : lstInsertStockGoodsSerialStrip) {
                    try {
                        String saveMeg = saveObjectSession(serialStripInsertNew, session);
                        if (!StringUtils.isInteger(saveMeg)) {
                            resultDTO.setMessage(Constants.ERROR_MESSAGE.INSERT_GOODS_NEW_ERROR);
                            List<StockGoodsSerialInforDTO> lstError = new ArrayList();
                            lstError.add(stockGoodsSerialInforErrorDTO);
                            resultDTO.setLstStockGoodsSerialInforDTO(lstError);
                            resultDTO.setAmount(Double.parseDouble(quantity.toString()));
                            resultDTO.setAmountIssue(Double.parseDouble(quantity.toString()));
                            return resultDTO;
                        }
                    } catch (Exception e) {
                        resultDTO.setMessage(ParamUtils.FAIL);
                        //resultDTO.setKey(newStockGoodsSerialStrip.getGoodsId().toString() + "," + newStockGoodsSerialStrip.getGoodsState());
                        resultDTO.setFromSerial(newStockGoodsSerialStrip.getFromSerial());
                        resultDTO.setToSerial(newStockGoodsSerialStrip.getToSerial());
                        List<StockGoodsSerialInforDTO> lstError = new ArrayList();
                        lstError.add(stockGoodsSerialInforErrorDTO);
                        resultDTO.setLstStockGoodsSerialInforDTO(lstError);
                        return resultDTO;
                    }
                }
            } else {
                //them moi cho hang can thu hoi
                try {
                    String saveMeg = saveObjectSession(newStockGoodsSerialStrip, session);
                    if (!StringUtils.isInteger(saveMeg)) {
                        resultDTO.setMessage(Constants.ERROR_MESSAGE.INSERT_GOODS_NEW_ERROR);
                        List<StockGoodsSerialInforDTO> lstError = new ArrayList();
                        lstError.add(stockGoodsSerialInforErrorDTO);
                        resultDTO.setLstStockGoodsSerialInforDTO(lstError);
                    } else {
                        resultDTO.setMessage(message);
                    }
                } catch (Exception e) {
                    //tra ra resultDTO
                    //resultDTO.setKey(newStockGoodsSerialStrip.getGoodsId().toString() + "," + newStockGoodsSerialStrip.getGoodsState());
                    resultDTO.setMessage(ParamUtils.FAIL);
                    List<StockGoodsSerialInforDTO> lstError = new ArrayList();
                    lstError.add(stockGoodsSerialInforErrorDTO);
                    resultDTO.setLstStockGoodsSerialInforDTO(lstError);
                    return resultDTO;
                }
                resultDTO.setAmount(Double.parseDouble(quantity.toString()));
                resultDTO.setAmountIssue(Double.parseDouble(quantity.toString()));
                return resultDTO;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        amount = Double.parseDouble(quantity.toString());
        amountIssue = Double.parseDouble(quantity.toString());
        resultDTO.setAmount(amount);
        resultDTO.setAmountIssue(amountIssue);
        resultDTO.setQuantitySucc(insertSuccess);
        resultDTO.setQuantityFail(insertFail);
        resultDTO.setMessage(message);
        //resultDTO.setKey(newStockGoodsSerialStrip.getGoodsId().toString() + "," + newStockGoodsSerialStrip.getGoodsState());
        return resultDTO;
    }

    private ResultDTO deleteAndInsertStockGoodsSerialStrip(StockGoodsSerialStrip oldStockGoodsSerialStrip,
            StockGoodsSerialStrip insretStockGoodsSerialStrip,
            StockGoodsSerialStrip newStockGoodsSerialStrip, Session session) {
        ResultDTO resultDTO = new ResultDTO();

        //Xoa dai serial cu
        String message = ParamUtils.SUCCESS;
        resultDTO = deleteStockGoodsSerialStrip(oldStockGoodsSerialStrip, session);
        //
        Long firtQuantity = 0L;
        Long lastQuantity = 0L;
        StockGoodsSerialStrip firstOldStockGoodsSerialStrip = null;
        StockGoodsSerialStrip lastOldStockGoodsSerialStrip = null;
        if (!ParamUtils.SUCCESS.equals(resultDTO.getMessage())) {
            importStockGoodsSerialStrip(newStockGoodsSerialStrip, newStockGoodsSerialStrip.getImportStockTransId() + "", session);
            //resultDTO.setMessage(ParamUtils.FAIL);
            resultDTO.setMessage(Constants.ERROR_MESSAGE.DELELE_AND_UPDATE_ERROR);
            resultDTO.setFromSerial(newStockGoodsSerialStrip.getFromSerial());
            resultDTO.setToSerial(newStockGoodsSerialStrip.getToSerial());
            return resultDTO;
        } else {
            int lengthSerial = insretStockGoodsSerialStrip.getFromSerial().length();
            //Insert dai serial dau (cu)
            if (Long.parseLong(oldStockGoodsSerialStrip.getFromSerial()) < Long.parseLong(insretStockGoodsSerialStrip.getFromSerial())) {
                firstOldStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(oldStockGoodsSerialStrip);
                String firstToSerial = Long.parseLong(insretStockGoodsSerialStrip.getFromSerial()) - 1 + "";
                firstOldStockGoodsSerialStrip.setToSerial(DataUtil.lPad(firstToSerial, "0", lengthSerial));
                firtQuantity = Long.parseLong(firstOldStockGoodsSerialStrip.getToSerial()) - Long.parseLong(firstOldStockGoodsSerialStrip.getFromSerial()) + 1;
                firstOldStockGoodsSerialStrip.setQuantity(firtQuantity);
                //firstOldStockGoodsSerialStrip.setBarcode(message);
                try {
                    String saveMeg = saveObjectSession(firstOldStockGoodsSerialStrip, session);
                    if (!StringUtils.isInteger(saveMeg)) {
                        resultDTO.setMessage(Constants.ERROR_MESSAGE.DELELE_AND_UPDATE_ERROR);
                        return resultDTO;
                    }
                } catch (Exception e) {
                    importStockGoodsSerialStrip(newStockGoodsSerialStrip, newStockGoodsSerialStrip.getImportStockTransId() + "", session);
                    resultDTO.setMessage(Constants.ERROR_MESSAGE.DELELE_AND_UPDATE_ERROR);
                    resultDTO.setFromSerial(newStockGoodsSerialStrip.getFromSerial());
                    resultDTO.setToSerial(newStockGoodsSerialStrip.getToSerial());
                    return resultDTO;
                }
            }
            //Insert dai serial cuoi (cu)
            if (Long.parseLong(oldStockGoodsSerialStrip.getToSerial()) > Long.parseLong(insretStockGoodsSerialStrip.getToSerial())) {
                lastOldStockGoodsSerialStrip = (StockGoodsSerialStrip) DataUtil.cloneObject(oldStockGoodsSerialStrip);
                String lastFromSerial = Long.parseLong(insretStockGoodsSerialStrip.getToSerial()) + 1 + "";
                lastOldStockGoodsSerialStrip.setFromSerial(DataUtil.lPad(lastFromSerial, "0", lengthSerial));
                lastQuantity = Long.parseLong(lastOldStockGoodsSerialStrip.getToSerial()) - Long.parseLong(lastOldStockGoodsSerialStrip.getFromSerial()) + 1;
                lastOldStockGoodsSerialStrip.setQuantity(lastQuantity);
                //;
                try {
                    String saveMeg = saveObjectSession(lastOldStockGoodsSerialStrip, session);
                    if (!StringUtils.isInteger(saveMeg)) {
                        resultDTO.setMessage(ParamUtils.FAIL);
                        resultDTO.setMessage(Constants.ERROR_MESSAGE.DELELE_AND_UPDATE_ERROR);
                        return resultDTO;
                    }
                } catch (Exception e) {
                    importStockGoodsSerialStrip(newStockGoodsSerialStrip, newStockGoodsSerialStrip.getImportStockTransId() + "", session);
                    resultDTO.setMessage(Constants.ERROR_MESSAGE.DELELE_AND_UPDATE_ERROR);
                    resultDTO.setFromSerial(newStockGoodsSerialStrip.getFromSerial());
                    resultDTO.setToSerial(newStockGoodsSerialStrip.getToSerial());
                    return resultDTO;
                }
            }
            //Insert dai serail theo kho moi
            insretStockGoodsSerialStrip.setBarcode(newStockGoodsSerialStrip.getBarcode());
            insretStockGoodsSerialStrip.setBincode(newStockGoodsSerialStrip.getBincode());
            insretStockGoodsSerialStrip.setCellCode(newStockGoodsSerialStrip.getCellCode());
            insretStockGoodsSerialStrip.setChangeDate(newStockGoodsSerialStrip.getChangeDate());
            try {
                String saveMeg = saveObjectSession(insretStockGoodsSerialStrip, session);
                if (!StringUtils.isInteger(saveMeg)) {
                    resultDTO.setMessage(Constants.ERROR_MESSAGE.DELELE_AND_UPDATE_ERROR);
                    return resultDTO;
                }
            } catch (Exception e) {
                importStockGoodsSerialStrip(newStockGoodsSerialStrip, newStockGoodsSerialStrip.getImportStockTransId() + "", session);
                resultDTO.setMessage(Constants.ERROR_MESSAGE.DELELE_AND_UPDATE_ERROR);
                resultDTO.setFromSerial(newStockGoodsSerialStrip.getFromSerial());
                resultDTO.setToSerial(newStockGoodsSerialStrip.getToSerial());
                return resultDTO;
            }
        }
        //session.evict(insretStockGoodsSerialStrip);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    private void reInsertStockGoodsSerialError(StockGoodsSerialStrip stockGoodsSerialStrip, String stockTransId,
            Session session, String addInfor) {
        StockGoodsSerialError stockGoodsSerialError = new StockGoodsSerialError();
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        sql.append(" INSERT INTO stock_goods_serial_error (id,stock_trans_id, cust_id, owner_id, owner_type, goods_id,");
        sql.append("       goods_state, status, from_serial,to_serial, sale_type, change_user,");
        sql.append("       price, channel_type_id, barcode, change_date,");
        sql.append("       import_date, sale_date, bincode, add_infor,quantity) ");
        sql.append(" VALUES (STOCK_GOODS_SERIAL_ERROR_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?,SYSDATE,SYSDATE,NULL,?,?,TO_NUMBER(?)) ");
        //        
        try {
            params.add(stockTransId);
            params.add(stockGoodsSerialStrip.getCustId());
            params.add(stockGoodsSerialStrip.getOwnerId());
            params.add(stockGoodsSerialStrip.getOwnerType());
            params.add(stockGoodsSerialStrip.getGoodsId());
            params.add(stockGoodsSerialStrip.getGoodsState());
            params.add(stockGoodsSerialStrip.getStatus());
            params.add(stockGoodsSerialStrip.getFromSerial());
            params.add(stockGoodsSerialStrip.getToSerial());
            params.add(stockGoodsSerialStrip.getSaleType());
            params.add(stockGoodsSerialStrip.getChangeUser());
            params.add(stockGoodsSerialStrip.getPrice());
            params.add(stockGoodsSerialStrip.getChannelTypeId());
            params.add(stockGoodsSerialStrip.getBarcode());
            params.add(stockGoodsSerialStrip.getBincode());
            params.add(addInfor);
            params.add(stockGoodsSerialStrip.getQuantity());
            //
            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(StockGoodsSerialStripDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public List getListSerilStripErrorImportRevoke(String stockTransId) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();

        sql.append("SELECT   ");
        sql.append("         a.ora_err_mesg$ addInfor,");
        sql.append("         a.cust_id custId,");
        sql.append("         a.owner_id ownerId,");
        sql.append("         a.owner_type ownerType,");
        sql.append("         a.goods_id goodsId,");
        sql.append("         a.goods_state goodsState,");
        sql.append("         a.status status,");
        sql.append("         a.from_serial fromSerial,");
        sql.append("         a.to_serial toSerial,");
        sql.append("         a.barcode barcode,");
        sql.append("         a.cell_code cellCode,");
        sql.append("         g.code goodsCode,");
        sql.append("         g.name goodsName ");
        sql.append("  FROM   err$_stock_goods_serial_strip a, goods g ");
        sql.append("  WHERE   a.goods_id = g.goods_id ");
        //
        if (!DataUtil.isStringNullOrEmpty(stockTransId)) {
            sql.append("  AND a.import_stock_trans_id = ? ");
            lstParams.add(stockTransId);
        }
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockGoodsSerialInforDTO.class));

        query.addScalar("custId", new StringType());
        query.addScalar("ownerId", new StringType());
        query.addScalar("ownerType", new StringType());
        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("status", new StringType());
        query.addScalar("fromSerial", new StringType());
        query.addScalar("toSerial", new StringType());
        query.addScalar("addInfor", new StringType());
        query.addScalar("cellCode", new StringType());
        query.addScalar("barcode", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        return query.list();
    }

    /**
     *
     * @param stockGoodsSerialStripDTO
     * @param session
     * @return
     */
    public String updateStockGoodsSerialStripByOrdersId(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, Session session) {
        String message = ParamUtils.SUCCESS;
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        int quantitySucc = 0;
        Query query;
        try {
            sql.append(" UPDATE STOCK_GOODS_SERIAL_STRIP ");
            sql.append(" SET STATUS = ?,CHANGE_DATE = TO_DATE(?,'dd/MM/yyyy hh24:mi:ss') ");
            params.add(stockGoodsSerialStripDTO.getStatus());
            params.add(stockGoodsSerialStripDTO.getChangeDate());
            sql.append(" WHERE ORDER_ID = ? AND STATUS = ? ");
            params.add(stockGoodsSerialStripDTO.getOrderId());
            params.add(stockGoodsSerialStripDTO.getOldStatus());
            //
            query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            quantitySucc = query.executeUpdate();
            if (quantitySucc == 0) {
                message = ParamUtils.FAIL;
            }
        } catch (Exception e) {
            message = ParamUtils.FAIL;
        }
        return message;
    }

    public String updateStockGoodsSerialStripByOrdersIdAndGoods(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, Session session) {
        String message = ParamUtils.SUCCESS;
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        int quantitySucc = 0;
        Query query;
        try {
            sql.append(" UPDATE STOCK_GOODS_SERIAL_STRIP ");
            sql.append(" SET STATUS = ?,CHANGE_DATE = TO_DATE(?,'dd/MM/yyyy hh24:mi:ss') ");
            params.add(stockGoodsSerialStripDTO.getStatus());
            params.add(stockGoodsSerialStripDTO.getChangeDate());
            sql.append(" WHERE ORDER_ID = ? AND STATUS = ? AND GOODS_ID = ? AND GOODS_STATE = ?");
            params.add(stockGoodsSerialStripDTO.getOrderId());
            params.add(stockGoodsSerialStripDTO.getOldStatus());
            params.add(stockGoodsSerialStripDTO.getGoodsId());
            params.add(stockGoodsSerialStripDTO.getGoodsState());
            //
            query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            quantitySucc = query.executeUpdate();
            if (quantitySucc == 0) {
                message = ParamUtils.FAIL;
            }
        } catch (Exception e) {
            message = ParamUtils.FAIL;
        }
        return message;
    }

    /**
     * Thienng1 addby 28/03/2016 Kiem tra xem Kit da co trong kho hay khong
     *
     * @param orderId
     * @param stockTransSerialDTO
     * @param session
     * @return
     */
    //Tim kiem serial theo dai theo session
    public List<StockGoodsSerialStrip> getListSerialStripKitInStockByOrderId(String orderId, StockTransSerialDTO stockTransSerialDTO, Session session) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();
        //
        sql.append(" SELECT sts.goods_id goodsId, sts.goods_state goodsState,");
        sql.append(" sts.status status, sts.from_serial fromSerial, sts.to_serial toSerial, sts.quantity quantity ");
        sql.append("  FROM Stock_Goods_Serial_Strip sts WHERE 1=1 ");
        sql.append("AND sts.status = 1 ");//trang thai cho kho

        if (!StringUtils.isStringNullOrEmpty(orderId)) {
            sql.append("AND sts.order_id = ? ");
            lstParams.add(orderId);
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockTransSerialDTO.getGoodsId())) {
            sql.append("AND sts.goods_id = ? ");
            lstParams.add(stockTransSerialDTO.getGoodsId());
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockTransSerialDTO.getGoodsState())) {
            sql.append("AND sts.goods_state = ? ");
            lstParams.add(stockTransSerialDTO.getGoodsState());
        }
        //Tim kiem theo from serial
        if (!StringUtils.isNullOrEmpty(stockTransSerialDTO.getFromSerial())) {
            sql.append("AND sts.to_serial >= ? ");
            lstParams.add(stockTransSerialDTO.getFromSerial());
        }
        //Tim kiem theo to serial
        if (!StringUtils.isStringNullOrEmpty(stockTransSerialDTO.getToSerial())) {
            sql.append("AND sts.from_serial <= ? ");
            lstParams.add(stockTransSerialDTO.getToSerial());
        }

        SQLQuery query = session.createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockGoodsSerialStrip.class));

        query.addScalar("goodsId", new LongType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("status", new StringType());
        query.addScalar("fromSerial", new StringType());
        query.addScalar("toSerial", new StringType());
        query.addScalar("quantity", new LongType());

        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        //
        return query.list();
    }

    /**
     * Thienng1 addby 28/03/2016 Kiem tra xem Kit da co trong kho hay khong(hang
     * serial don le)
     *
     * @param stockTransSerialDTO
     * @param orderId
     * @param session
     * @return
     */
    //Tim kiem serial theo dai theo session
    public List<StockGoodsSerial> getListSerialKitInStockByOrderId(String orderId, StockTransSerialDTO stockTransSerialDTO, Session session) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();
        //
        sql.append(" SELECT * FROM sts.goods_id goodsId, sts.goods_state goodsState, ");
        sql.append(" sts.status status, sts.serial serial, sts.quantity quantity");
        sql.append("Stock_Goods_Serial sts WHERE 1=1 ");
        sql.append("AND sts.status = 1 ");

        //tim kiem theo yeu cau
        if (!StringUtils.isStringNullOrEmpty(orderId)) {
            sql.append("AND sts.order_id = ? ");
            lstParams.add(orderId);
        }
        //Tim kien theo mat hang
        if (!StringUtils.isStringNullOrEmpty(stockTransSerialDTO.getGoodsId())) {
            sql.append("AND sts.goods_id = ? ");
            lstParams.add(stockTransSerialDTO.getGoodsId());
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockTransSerialDTO.getGoodsState())) {
            sql.append("AND sts.goods_state = ? ");
            lstParams.add(stockTransSerialDTO.getGoodsState());
        }

        //Tim kiem theo serial
        if (!StringUtils.isStringNullOrEmpty(stockTransSerialDTO.getFromSerial())) {
            sql.append("AND sts.serial = ? ");
            lstParams.add(stockTransSerialDTO.getFromSerial());
        }

        SQLQuery query = session.createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockGoodsSerial.class));

        query.addScalar("goodsId", new LongType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("status", new StringType());
        query.addScalar("serial", new StringType());
        query.addScalar("quantity", new LongType());

        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        //
        return query.list();
    }

}
