/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.logistic.wms.dto.GoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransGoodsDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.StockGoodsSerial;
import com.viettel.logistic.wms.model.StockGoodsSerialError;
import com.viettel.logistic.wms.model.StockTransDetail;
import com.viettel.logstic.wms.webservice.dto.ChangeGoods;
import com.viettel.logstic.wms.webservice.dto.OrdersDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 13-Apr-15 7:17 PM
 */
@Repository("stockGoodsSerialDAO")
public class StockGoodsSerialDAO extends BaseFWDAOImpl<StockGoodsSerial, Long> {

    public Logger logger = Logger.getLogger(StockGoodsSerialDAO.class.getName());

    public StockGoodsSerialDAO() {
        this.model = new StockGoodsSerial();
    }

    public StockGoodsSerialDAO(Session session) {
        this.session = session;
    }
    //
    String formatDate = "dd/mm/yyyy hh24:mi:ss";

    //
    public ResultDTO exportStockGoodsSerial(StockGoodsSerialStripDTO oldStockGoodsSerialStripDTO, StockGoodsSerialStripDTO newStockGoodsSerialStripDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" UPDATE stock_goods_serial ");
            sql.append(" SET change_date=to_date(?,'dd/mm/yyyy hh24:mi:ss') ");
            params.add(newStockGoodsSerialStripDTO.getChangeDate());
            //Cap nhat khach hang
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getCustId())) {
                sql.append(",cust_id=? ");
                params.add(newStockGoodsSerialStripDTO.getCustId());
            }
            //Cap nhat Kho
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getOwnerId())) {
                sql.append(",owner_id=? ");
                params.add(newStockGoodsSerialStripDTO.getOwnerId());
            }
            //Cap nhat loai kho
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getOwnerType())) {
                sql.append(",owner_type=? ");
                params.add(newStockGoodsSerialStripDTO.getOwnerType());
            }
            //Cap nhat mat hang
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getGoodsId())) {
                sql.append(",goods_id=? ");
                params.add(newStockGoodsSerialStripDTO.getGoodsId());
            }
            //Cap nhat tinh trang hang
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getGoodsState())) {
                sql.append(",goods_state=? ");
                params.add(newStockGoodsSerialStripDTO.getGoodsState());
            }
            //Cap nhat trang thai
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getStatus())) {
                sql.append(",status=? ");
                params.add(newStockGoodsSerialStripDTO.getStatus());
            }
            //Cap nhat nguoi thay doi
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getChangeUser())) {
                sql.append(",change_user=? ");
                params.add(newStockGoodsSerialStripDTO.getChangeUser());
            }
            //Cap nhat loai ban hang
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getSaleType())) {
                sql.append(",sale_type=? ");
                params.add(newStockGoodsSerialStripDTO.getSaleType());
            }
            //Cao nhat ngay ban
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getSaleDate())) {
                sql.append(",sale_date=to_date(?,'dd/mm/yyyy hh24:mi:ss')");
                params.add(newStockGoodsSerialStripDTO.getSaleDate());
            }
            //Cap nhat gia
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getPrice())) {
                sql.append(",price=? ");
                params.add(newStockGoodsSerialStripDTO.getPrice());;
            }
            //Cap nhat kenh
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getChannelTypeId())) {
                sql.append(",channel_type_id=? ");
                params.add(newStockGoodsSerialStripDTO.getChannelTypeId());
            }
            //Cap nhat cell
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getCellCode())) {
                sql.append(",cell_code=? ");
                params.add(newStockGoodsSerialStripDTO.getCellCode());
            }
            //Cap nhat barcode
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getBarcode())) {
                sql.append(",barcode=? ");
                params.add(newStockGoodsSerialStripDTO.getBarcode());
            }
            //Cap nhat bincode
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getBincode())) {
                sql.append(",bincode= ? ");
                params.add(newStockGoodsSerialStripDTO.getBincode());
            }
            //Cap nhat thong tin bo sung
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getAddInfor())) {
                sql.append(",add_infor= ? ");
                params.add(newStockGoodsSerialStripDTO.getAddInfor());
            }
            //Cap nhat thong tin ma yeu cau
            if (!StringUtils.isNullOrEmpty(newStockGoodsSerialStripDTO.getOrderId())) {
                sql.append(",order_id=? ");
                params.add(newStockGoodsSerialStripDTO.getOrderId());
            }
            //
            sql.append(" WHERE cust_id=? AND owner_id=? AND owner_type=? ");
            sql.append("   AND goods_id=? AND goods_state=? AND status=? ");
            sql.append("   AND serial>=? AND serial<=? ");
            params.add(oldStockGoodsSerialStripDTO.getCustId());
            params.add(oldStockGoodsSerialStripDTO.getOwnerId());
            params.add(oldStockGoodsSerialStripDTO.getOwnerType());
            params.add(oldStockGoodsSerialStripDTO.getGoodsId());
            params.add(oldStockGoodsSerialStripDTO.getGoodsState());
            params.add(oldStockGoodsSerialStripDTO.getStatus());
            params.add(oldStockGoodsSerialStripDTO.getFromSerial());
            params.add(oldStockGoodsSerialStripDTO.getToSerial());
            //
            if (!StringUtils.isNullOrEmpty(oldStockGoodsSerialStripDTO.getCellCode())) {
                sql.append("   AND cell_code=? ");
                params.add(oldStockGoodsSerialStripDTO.getCellCode());
            }
            //
            if (!StringUtils.isNullOrEmpty(oldStockGoodsSerialStripDTO.getBarcode())) {
                sql.append("   AND barcode=? ");
                params.add(oldStockGoodsSerialStripDTO.getBarcode());
            }
            //
            if (!StringUtils.isNullOrEmpty(oldStockGoodsSerialStripDTO.getBincode())) {
                sql.append("   AND bincode=? ");
                params.add(oldStockGoodsSerialStripDTO.getBincode());
            }
            //
            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            int iUpdate = query.executeUpdate();
            if (iUpdate != Integer.parseInt(newStockGoodsSerialStripDTO.getQuantity())) {
                // tiepnv6, edit 26/06/15: tra ve goods id loi
                message = String.valueOf(oldStockGoodsSerialStripDTO.getGoodsId()) + "," + oldStockGoodsSerialStripDTO.getGoodsState();
//                message = ParamUtils.FAIL;
                key = ParamUtils.NOT_ENOUGH_AMOUNT;
                resultDTO.setQuantity(Integer.parseInt(newStockGoodsSerialStripDTO.getQuantity()));
                resultDTO.setQuantitySucc(iUpdate);
                resultDTO.setQuantityFail(Integer.parseInt(newStockGoodsSerialStripDTO.getQuantity()) - iUpdate);
                resultDTO.setFromSerial(oldStockGoodsSerialStripDTO.getFromSerial());
                resultDTO.setToSerial(oldStockGoodsSerialStripDTO.getToSerial());
            } else {
                resultDTO.setMessage(ParamUtils.SUCCESS);
                resultDTO.setQuantitySucc(iUpdate);
                resultDTO.setQuantityFail(0);
            }
        } catch (Exception ex) {
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
            message = ParamUtils.FAIL;
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        resultDTO.setMessage(message);
        resultDTO.setKey(key);
        return resultDTO;
    }

    //ChuDV: 09/05/2015
    public ResultDTO updateCellStockGoodsSerial(StockTransInforDTO stockTransInforDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc = 0;
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" UPDATE stock_goods_serial ");
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

    //QuyenDM: 15/06/2015 Cap nhat vi tri trong kho - Chuc nang don dich kho
    public Map<ChangePositionDTO, ResultDTO> updateCellStockGoodsSerial(ChangePositionDTO changePosition, Session session) {
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
                changePosition.setErrorInfor(ParamUtils.NOT_ENOUGH_AMOUNT);
                resultDTO.setKey(ParamUtils.FAIL);
                resultDTO.setQuantitySucc(quantitySucc);
                mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                return mapChangePosition2ResultDTO;
            }
            if (soluongNhapvao < amountTotal && DataUtil.isStringNullOrEmpty(changePosition.getFromSerial())) {
                //Neu tong so luong nho hon so luong nhap vao va k nhap serial --> Bao loi phai nhap serial
                resultDTO.setMessage("needSerial");
                changePosition.setErrorInfor("needSerial");
                resultDTO.setKey(ParamUtils.FAIL);
                resultDTO.setQuantitySucc(quantitySucc);
                mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                return mapChangePosition2ResultDTO;
            }
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" UPDATE stock_goods_serial ");
            sql.append(" SET cell_code = ? ");
            sql.append(" WHERE cust_id=? AND owner_id=? AND owner_type=? AND goods_id=? AND status='1' AND cell_code = ? ");
            params.add(changePosition.getCellCodeNew());
            params.add(changePosition.getCustomerId());
            params.add(changePosition.getStockId());
            params.add(changePosition.getOwnerType());
            params.add(changePosition.getGoodsId());
            params.add(changePosition.getCellCodeOld());
            //Neu co barcode va khong co Serial
            if (!StringUtils.isNullOrEmpty(changePosition.getBarcode())) {
                sql.append("   AND barcode = ? ");
                params.add(changePosition.getBarcode());
            }
//            //Neu co vi tri cu va khong co barcode, khong co serial
//            if (!StringUtils.isNullOrEmpty(changePosition.getCellCodeOld())
//                    && StringUtils.isNullOrEmpty(changePosition.getBarcode())
//                    && StringUtils.isStringNullOrEmpty(changePosition.getFromSerial())
//                    && StringUtils.isStringNullOrEmpty(changePosition.getToSerial())) {
//                sql.append("   AND cell_code = ? ");
//                params.add(changePosition.getCellCodeOld());
//            }
            //Neu co serial
            if (!StringUtils.isNullOrEmpty(changePosition.getFromSerial()) && !StringUtils.isNullOrEmpty(changePosition.getToSerial())) {
                sql.append("   AND serial >= ? ");
                sql.append("   AND serial <= ? ");
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
                resultDTO.setQuantitySucc(quantitySucc);
                changePosition.setErrorInfor(ParamUtils.NOT_FOUND_DATA);
                mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                return mapChangePosition2ResultDTO;
            }
        } catch (Exception ex) {
            changePosition.setErrorInfor(ParamUtils.FAIL);
            Logger.getLogger(StockGoodsSerialDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
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
        sql.append("       SELECT   count(*) ");
        sql.append("       FROM   wms_owner.stock_goods_serial a");
        sql.append("       WHERE       a.goods_id = ? ");
        sql.append("            AND a.owner_type = ? ");
        sql.append("            AND a.owner_id = ? ");
        sql.append("            AND a.cust_id = ? ");
        sql.append("            AND a.cell_code = ? ");
        sql.append("            AND a.status = ? ");
        List lstParams = new ArrayList<>();
        lstParams.add(changePositionDTO.getGoodsId());
        lstParams.add(changePositionDTO.getOwnerType());
        lstParams.add(changePositionDTO.getStockId());
        lstParams.add(changePositionDTO.getCustomerId());
        lstParams.add(changePositionDTO.getCellCodeOld());
        lstParams.add("1");
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

    //
    public ResultDTO importStockGoodsSerial(StockGoodsSerial stockGoodsSerial, String stockTransId, String fromSerial, String toSerial, Session session) {
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
        int lengSerial = fromSerial.length();
        try {
            //Serial chua ky tu
            if (!StringUtils.isInteger(fromSerial) || !StringUtils.isInteger(toSerial)) {
                serial = fromSerial;
                stockGoodsSerial.setSerial(serial);
                //
                try {
                    insertStockGoodsSerial(stockGoodsSerial, session);
                    insertSuccess++;
                    amount++;
                    amountIssue++;
                } catch (Exception e) {
                    //
                    e.printStackTrace();
                    insertStockGoodsSerialError(stockGoodsSerial, stockTransId, session);
                    insertFail++;
                    amount++;
                }
            } else { //Serial dang so 
                //Kiem tra do dai serial kneu >19 thi cat do kieu Long chi co do dai toi da 19
                int iLengthSuffixSerial = 0;
                if (fromSerial.length() > Constants.SERIAL_LIMIT) {
                    prefixSerial = fromSerial.substring(0, fromSerial.length() - Constants.SERIAL_LIMIT);
                    suffixFromSerial = fromSerial.substring(fromSerial.length() - Constants.SERIAL_LIMIT, fromSerial.length());
                    suffixToSerial = toSerial.substring(toSerial.length() - Constants.SERIAL_LIMIT, toSerial.length());
                    iLengthSuffixSerial = suffixFromSerial.length();
                } else {
                    suffixFromSerial = fromSerial;
                    suffixToSerial = toSerial;
                    iLengthSuffixSerial = fromSerial.length();
                }
                //
                String tmpSuffixSerial;
                for (Long lSerial = Long.parseLong(suffixFromSerial); lSerial <= Long.parseLong(suffixToSerial); lSerial++) {
                    tmpSuffixSerial = DataUtil.lPad(lSerial.toString(), "0", iLengthSuffixSerial);
                    serial = prefixSerial + tmpSuffixSerial;
                    stockGoodsSerial.setSerial(serial);
                    try {
                        insertStockGoodsSerial(stockGoodsSerial, session);
                        insertSuccess++;
                        amount++;
                        amountIssue++;
                    } catch (Exception e) {
                        e.printStackTrace();
//                        Logger.getLogger(StockGoodsSerialDAO.class.getName()).log(Level.SEVERE, null, e);
//                        System.out.println("Before stockgoodsserial_Errors.." + String.valueOf(stockGoodsSerial.getId()
//                                + " cuscID" + String.valueOf(stockGoodsSerial.getCustId() + " goodID")
//                                + String.valueOf(stockGoodsSerial.getGoodsId())));

                        insertStockGoodsSerialError(stockGoodsSerial, stockTransId, session);
                        insertFail++;
                        amount++;
                    }
                }
            }
            //            
        } catch (Exception e) {
            //e.printStackTrace();
            Logger.getLogger(StockGoodsTotalDAO.class.getName()).log(Level.SEVERE, null, e);
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

    //
    private void insertStockGoodsSerial(StockGoodsSerial stockGoodsSerial, Session session) {
        //session.save(stockGoodsSerial);
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        sql.append(" INSERT INTO stock_goods_serial (id, cust_id, owner_id, owner_type, goods_id,");
        sql.append("       goods_state, status,sale_type, change_user,");
        sql.append("       price,channel_type_id, barcode, change_date,");
        sql.append("       import_date, sale_date, bincode, add_infor, cell_code,serial,partner_id, import_stock_trans_id) ");
        sql.append(" VALUES (STOCK_GOODS_SERIAL_SEQ.nextval,?,?,?,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?,?,?,NULL,?,?,?,?,TO_NUMBER(?),?) ");
//        sql.append(" LOG ERRORS   REJECT LIMIT UNLIMITED ");
        //
        params.add(stockGoodsSerial.getCustId());
        params.add(stockGoodsSerial.getOwnerId());
        params.add(stockGoodsSerial.getOwnerType());
        params.add(stockGoodsSerial.getGoodsId());
        params.add(stockGoodsSerial.getGoodsState());
        params.add(stockGoodsSerial.getStatus());
        params.add(stockGoodsSerial.getSaleType());
        params.add(stockGoodsSerial.getChangeUser());
        //
        params.add(stockGoodsSerial.getPrice());
        params.add(stockGoodsSerial.getChannelTypeId());
        params.add(stockGoodsSerial.getBarcode());
        params.add(stockGoodsSerial.getChangeDate());
        //
        params.add(stockGoodsSerial.getImportDate());
        params.add(stockGoodsSerial.getBincode());
        params.add(stockGoodsSerial.getAddInfor());
        params.add(stockGoodsSerial.getCellCode());
        params.add(stockGoodsSerial.getSerial());
        //
        params.add(stockGoodsSerial.getPartnerId());
        params.add(stockGoodsSerial.getImportStockTransId());
        //
        Query query = session.createSQLQuery(sql.toString());
        for (int idx = 0; idx < params.size(); idx++) {
            query.setParameter(idx, params.get(idx));
        }

        query.executeUpdate();
    }

    //Cap nhat hang thu hoi
    private void insertStockGoodsSerialReVoke(StockGoodsSerial stockGoodsSerial, Session session) {
        //session.save(stockGoodsSerial);
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();

        sql.append(" INSERT INTO stock_goods_serial (id, cust_id, owner_id, owner_type, goods_id,");
        sql.append("       goods_state, status,sale_type, change_user,");
        sql.append("       price,channel_type_id, barcode, change_date,");
        sql.append("       import_date, sale_date, bincode, add_infor, cell_code,serial,partner_id, import_stock_trans_id, order_id ) ");
        sql.append(" VALUES (STOCK_GOODS_SERIAL_SEQ.nextval,?,?,?,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?,?,?,NULL,?,?,?,?,TO_NUMBER(?),?,?) ");
        sql.append(" LOG ERRORS   REJECT LIMIT UNLIMITED ");
        //
        params.add(stockGoodsSerial.getCustId());
        params.add(stockGoodsSerial.getOwnerId());
        params.add(stockGoodsSerial.getOwnerType());
        params.add(stockGoodsSerial.getGoodsId());
        params.add(stockGoodsSerial.getGoodsState());
        params.add(stockGoodsSerial.getStatus());
        params.add(stockGoodsSerial.getSaleType());
        params.add(stockGoodsSerial.getChangeUser());
        //
        params.add(stockGoodsSerial.getPrice());
        params.add(stockGoodsSerial.getChannelTypeId());
        params.add(stockGoodsSerial.getBarcode());
        params.add(stockGoodsSerial.getChangeDate());
        //
        params.add(stockGoodsSerial.getImportDate());
        params.add(stockGoodsSerial.getBincode());
        params.add(stockGoodsSerial.getAddInfor());
        params.add(stockGoodsSerial.getCellCode());
        params.add(stockGoodsSerial.getSerial());
        //
        params.add(stockGoodsSerial.getPartnerId());
        params.add(stockGoodsSerial.getImportStockTransId());
        params.add(DataUtil.nvl(stockGoodsSerial.getOrderId(), ""));
        //
        Query query = session.createSQLQuery(sql.toString());
        for (int idx = 0; idx < params.size(); idx++) {
            query.setParameter(idx, params.get(idx));
        }

        query.executeUpdate();
    }

    private void insertStockGoodsSerialError(StockGoodsSerial stockGoodsSerial, String stockTransId, Session session) {
        StockGoodsSerialError stockGoodsSerialError = new StockGoodsSerialError();
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" INSERT INTO stock_goods_serial_error (id,stock_trans_id, cust_id, owner_id, owner_type, goods_id,");
            sql.append("       goods_state, status, from_serial,to_serial, sale_type, change_user,");
            sql.append("       price, channel_type_id, barcode, change_date,");
            sql.append("       import_date, sale_date, bincode, add_infor) ");
            sql.append(" VALUES (STOCK_GOODS_SERIAL_ERROR_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?,SYSDATE,SYSDATE,SYSDATE,?,?) ");
            //        
            params.add(stockTransId);
            params.add(stockGoodsSerial.getCustId());
            params.add(stockGoodsSerial.getOwnerId());
            params.add(stockGoodsSerial.getOwnerType());
            params.add(stockGoodsSerial.getGoodsId());
            params.add(stockGoodsSerial.getGoodsState());
            params.add(stockGoodsSerial.getStatus());
            params.add(stockGoodsSerial.getSerial());
            params.add(stockGoodsSerial.getSerial());
            params.add(stockGoodsSerial.getSaleType());
            params.add(stockGoodsSerial.getChangeUser());
            params.add(stockGoodsSerial.getPrice());
            params.add(stockGoodsSerial.getChannelTypeId());
            params.add(stockGoodsSerial.getBarcode());
            params.add(stockGoodsSerial.getBincode());
            params.add(stockGoodsSerial.getAddInfor());
            //
            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //duyot - dieu chuyen hang hoa - cap nhat stock_goods_serial
    //
    public ResultDTO updateNewGoods(StockGoodsSerialDTO importStockGoodsSerial, StockGoodsSerialDTO exportedStockGoodsSerial, String fromSerial, String toSerial, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc = 0;
        Double amountIssue = 0D;

        //XU LY DOI VOI SERIAL CO KI TU -> SERIAL KHONG THEO DAI
        if (!StringUtils.isInteger(fromSerial) || !StringUtils.isInteger(toSerial)) {
            importStockGoodsSerial.setSerial(fromSerial + "");
            try {
                StringBuilder sql = new StringBuilder();
                sql.append(" UPDATE stock_goods_serial ");
                sql.append(" SET goods_id = ? , ");
                sql.append("  goods_state = ? , ");
                sql.append("  status = ? , ");
                sql.append("  import_stock_trans_id = ? ");
                sql.append(" WHERE goods_id =? AND serial=? ");
                sql.append(" AND cust_id =? AND owner_id=? ");
                //
                //
                Query query = session.createSQLQuery(sql.toString());

                query.setParameter(0, importStockGoodsSerial.getGoodsId());
                query.setParameter(1, importStockGoodsSerial.getGoodsState());
                query.setParameter(2, importStockGoodsSerial.getStatus());
                query.setParameter(3, importStockGoodsSerial.getImportStockTransId());
                query.setParameter(4, exportedStockGoodsSerial.getGoodsId());
                query.setParameter(5, exportedStockGoodsSerial.getSerial());
                query.setParameter(6, exportedStockGoodsSerial.getCustId());
                query.setParameter(7, exportedStockGoodsSerial.getOwnerId());

                quantitySucc = query.executeUpdate();
                if (quantitySucc == 0) {
                    message = ParamUtils.FAIL;
                    key = ParamUtils.SYSTEM_OR_DATA_ERROR;
                }
                amountIssue++;

            } catch (Exception ex) {
                Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
                message = ParamUtils.FAIL;
                key = ParamUtils.SYSTEM_OR_DATA_ERROR;
            }
        } else {
            //XU LY CHO SERIAL DAI HON 18 KI TU
            Long fromSerL = 0L;
            Long toSerL = 0L;

            if (fromSerial.length() > 18) {
                fromSerL = Long.parseLong(fromSerial.substring(fromSerial.length() - 18));
            } else {
                fromSerL = Long.parseLong(fromSerial);
            }

            if (toSerial.length() > 18) {
                toSerL = Long.parseLong(toSerial.substring(toSerial.length() - 18));
            } else {
                toSerL = Long.parseLong(toSerial);
            }

            for (Long i = fromSerL; i <= toSerL; i++) {
                importStockGoodsSerial.setSerial(i + "");
                try {
                    StringBuilder sql = new StringBuilder();
                    sql.append(" UPDATE stock_goods_serial ");
                    sql.append(" SET goods_id = ? , ");
                    sql.append("  goods_state = ? , ");
                    sql.append("  status = ? , ");
                    sql.append("  import_stock_trans_id = ? ");
                    sql.append(" WHERE goods_id =? AND serial=? ");
                    sql.append(" AND cust_id =? AND owner_id=? ");
                    //
                    Query query = session.createSQLQuery(sql.toString());

                    query.setParameter(0, importStockGoodsSerial.getGoodsId());
                    query.setParameter(1, importStockGoodsSerial.getGoodsState());
                    query.setParameter(2, importStockGoodsSerial.getStatus());
                    query.setParameter(3, importStockGoodsSerial.getImportStockTransId());
                    query.setParameter(4, exportedStockGoodsSerial.getGoodsId());
                    query.setParameter(5, exportedStockGoodsSerial.getSerial());
                    query.setParameter(6, exportedStockGoodsSerial.getCustId());
                    query.setParameter(7, exportedStockGoodsSerial.getOwnerId());

                    quantitySucc = query.executeUpdate();
                    if (quantitySucc == 0) {
                        message = ParamUtils.FAIL;
                        key = ParamUtils.SYSTEM_OR_DATA_ERROR;
                    }
                    amountIssue++;

                } catch (Exception ex) {
                    Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
                    message = ParamUtils.FAIL;
                    key = ParamUtils.SYSTEM_OR_DATA_ERROR;
                }
            }
        }
        //
        resultDTO.setMessage(message);
        resultDTO.setKey(key);
        resultDTO.setQuantitySucc(quantitySucc);
        resultDTO.setAmountIssue(amountIssue);
        return resultDTO;
    }

    //AddBy ThienNG1 03/08/2015
    public ResultDTO reImportStockGoodsSerial(StockGoodsSerial stockGoodsSerial, String stockTransId, String fromSerial,
            String toSerial, Session session, String synImportRevoke) {
        ResultDTO resultDTO = new ResultDTO();
        String serial;
        String prefixSerial = "";
        String suffixFromSerial = "";
        String suffixToSerial = "";
        Double amount = 0D;
        Double amountIssue = 0D;
        int insertSuccess = 0;
        int insertFail = 0;
        try {
            //Serial chua ky tu
            if (!StringUtils.isInteger(fromSerial) || !StringUtils.isInteger(toSerial)) {
                serial = fromSerial;
                stockGoodsSerial.setSerial(serial);
//                int lengSerial = fromSerial.length();
                //cap nhat hang thu hoi
                updateStockGoodsSerialRevoke(stockGoodsSerial, session, synImportRevoke);
                amount++;
                amountIssue++;
            } else { //Serial dang so 
                //Kiem tra do dai serial kneu >19 thi cat do kieu Long chi co do dai toi da 19
                int iLengthSuffixSerial = 0;
                if (fromSerial.length() > Constants.SERIAL_LIMIT) {
                    prefixSerial = fromSerial.substring(0, fromSerial.length() - Constants.SERIAL_LIMIT);
                    suffixFromSerial = fromSerial.substring(fromSerial.length() - Constants.SERIAL_LIMIT, fromSerial.length());
                    suffixToSerial = toSerial.substring(toSerial.length() - Constants.SERIAL_LIMIT, toSerial.length());
                    iLengthSuffixSerial = suffixFromSerial.length();
                } else {
                    suffixFromSerial = fromSerial;
                    suffixToSerial = toSerial;
                    iLengthSuffixSerial = fromSerial.length();
                }
                //
                String tmpSuffixSerial;
                for (Long lSerial = Long.parseLong(suffixFromSerial); lSerial <= Long.parseLong(suffixToSerial); lSerial++) {
                    tmpSuffixSerial = DataUtil.lPad(lSerial.toString(), "0", iLengthSuffixSerial);
                    serial = prefixSerial + tmpSuffixSerial;
                    stockGoodsSerial.setSerial(serial);
                    //cap nhat hang thu hoi
                    updateStockGoodsSerialRevoke(stockGoodsSerial, session, synImportRevoke);
                    amount++;
                    amountIssue++;
                }
            }
            //            
        } catch (Exception e) {
            e.printStackTrace();
            resultDTO.setQuantityFail(insertFail);
            resultDTO.setQuantitySucc(insertSuccess);
            resultDTO.setAmount(amount);
            resultDTO.setMessage(ParamUtils.FAIL);
            return resultDTO;
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

    //ThienNG1 cap nhap hang thu hoi
    private void updateStockGoodsSerialRevoke(StockGoodsSerial stockGoodsSerial, Session session, String synImportRevoke) {
        //session.save(stockGoodsSerial);
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        int iUpdateRevoke;
        //
        if (Constants.ACTIVE_STATUS.equals(synImportRevoke)) {
            sql.append(" UPDATE stock_goods_serial ");
            sql.append(" SET status = ?, change_date = ?, cell_code = ?, barcode = ?, ");
            sql.append(" bincode = ?, goods_state = ?, import_stock_trans_id = ?,  ");
            sql.append(" owner_id = ?, owner_type = ? ");
            sql.append(" WHERE cust_id = ? AND goods_id = ? AND serial = ? ");
            sql.append(" AND status = ? ");
        } else {
            sql.append(" UPDATE stock_goods_serial ");
            sql.append(" SET status = ?, change_date = ?, cell_code = ?, barcode = ?, ");
            sql.append(" bincode = ?, goods_state = ?, import_stock_trans_id = ?, order_id = ?, ");
            sql.append(" owner_id = ?, owner_type = ? ");
            sql.append(" WHERE cust_id = ? AND goods_id = ? AND serial = ? ");
            sql.append(" AND status = ? ");
        }
        //
        //Constants.STATUS_SERIAL_IN_STOCK - trong kho
        //Constants.STATUS_SERIAL_WAIT_STOCK - cho trong kho
        params.add(synImportRevoke);
        params.add(stockGoodsSerial.getChangeDate());
        params.add(stockGoodsSerial.getCellCode());
        params.add(stockGoodsSerial.getBarcode());
        params.add(stockGoodsSerial.getBincode());
        //if (Constants.STATUS_SERIAL_WAIT_STOCK.equals(synImportRevoke)) {
        params.add(stockGoodsSerial.getGoodsState());
        params.add(stockGoodsSerial.getImportStockTransId());
        if (!Constants.ACTIVE_STATUS.equals(synImportRevoke)) {
            params.add(DataUtil.nvl(stockGoodsSerial.getOrderId(), ""));
        }
        params.add(stockGoodsSerial.getOwnerId());
        params.add(stockGoodsSerial.getOwnerType());
        //}
        params.add(stockGoodsSerial.getCustId());
        params.add(stockGoodsSerial.getGoodsId());
        params.add(stockGoodsSerial.getSerial());
        //params.add(stockGoodsSerial.getGoodsState());
        params.add(Constants.STATUS_SERIAL_OUT_STOCK);

        //
        Query query = session.createSQLQuery(sql.toString());
        for (int idx = 0; idx < params.size(); idx++) {
            query.setParameter(idx, params.get(idx));
        }
        iUpdateRevoke = query.executeUpdate();
        if (iUpdateRevoke < 1) {
            //Constants.STATUS_SERIAL_IN_STOCK - trong kho
            //Constants.STATUS_SERIAL_WAIT_STOCK - cho trong kho 
            stockGoodsSerial.setStatus(synImportRevoke);
            insertStockGoodsSerialReVoke(stockGoodsSerial, session);
        }
    }

    private void reInsertStockGoodsSerialError(StockGoodsSerial stockGoodsSerial, String stockTransId, Session session,
            String addInfor) {
        StockGoodsSerialError stockGoodsSerialError = new StockGoodsSerialError();
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" INSERT INTO stock_goods_serial_error (id,stock_trans_id, cust_id, owner_id, owner_type, goods_id,");
            sql.append("       goods_state, status, from_serial,to_serial, sale_type, change_user,");
            sql.append("       price, channel_type_id, barcode, change_date,");
            sql.append("       import_date, sale_date, bincode, add_infor) ");
            sql.append(" VALUES (STOCK_GOODS_SERIAL_ERROR_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?,SYSDATE,SYSDATE,SYSDATE,?,?) ");
            //        
            params.add(stockTransId);
            params.add(stockGoodsSerial.getCustId());
            params.add(stockGoodsSerial.getOwnerId());
            params.add(stockGoodsSerial.getOwnerType());
            params.add(stockGoodsSerial.getGoodsId());
            params.add(stockGoodsSerial.getGoodsState());
            params.add(stockGoodsSerial.getStatus());
            params.add(stockGoodsSerial.getSerial());
            params.add(stockGoodsSerial.getSerial());
            params.add(stockGoodsSerial.getSaleType());
            params.add(stockGoodsSerial.getChangeUser());
            params.add(stockGoodsSerial.getPrice());
            params.add(stockGoodsSerial.getChannelTypeId());
            params.add(stockGoodsSerial.getBarcode());
            params.add(stockGoodsSerial.getBincode());
            params.add(addInfor);
            //
            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            query.executeUpdate();
        } catch (Exception e) {
        }
    }

    //Add by ChuDV: 07/09/2015: Nhap serial theo lo
    public ResultDTO importStockGoodsSerialBatch(StockTransDTO stockTrans, StockTransDetailDTO stockTransDetail, List<StockTransSerialDTO> lstStockTransSerial, Connection connection, String serialStatus) {
        ResultDTO resultDTO = new ResultDTO();
        //connection.
        //THONG TIN SO LUONG NHAP
        Double amount = 0D;
        Double amountIssue = 0D;
        //PREPARE STATEMENTS
        PreparedStatement prstmtInsertStockTransSerial;
        PreparedStatement prstmtInsertStockGoodsSerial;
        //SQL
        StringBuilder sqlStockGoodsSerial = new StringBuilder();
        StringBuilder sqlStockTransSerial = new StringBuilder();
        String serial;
        String prefixSerial = "";
        String suffixFromSerial;
        String suffixToSerial;
        String fromSerial;
        String toSerial;
        int numberNeedToCommit = 0;
        int numberOfSuccess = 0;
        int numberOfFail = 0;
        //
        List paramsStockTransSerial;
        List paramsStockGoodsSerial;

        try {
            //1.KHOI TAO SESSION
            //2.1 TAO STATEMENTS STOCK_GOODS_SERIAL
            sqlStockGoodsSerial.append(" INSERT INTO stock_goods_serial (id, cust_id, owner_id, owner_type, goods_id,");
            sqlStockGoodsSerial.append("       goods_state, status,sale_type, change_user,");
            sqlStockGoodsSerial.append("       price,channel_type_id, barcode, change_date,");
            sqlStockGoodsSerial.append("       import_date, sale_date, bincode, add_infor, cell_code,serial,partner_id,import_stock_trans_id,order_id) ");
            sqlStockGoodsSerial.append(" VALUES (STOCK_GOODS_SERIAL_SEQ.nextval,?,?,?,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?)) ");
            sqlStockGoodsSerial.append(" LOG ERRORS REJECT LIMIT UNLIMITED ");
            //2.2 TAO STATEMENTS STOCK_TRANS_SERIAL
            sqlStockTransSerial.append("INSERT INTO stock_trans_serial ");
            sqlStockTransSerial.append(" ( stock_trans_serial_id, stock_trans_id,"
                    + "       stock_trans_detail_id, stock_trans_date, goods_id,"
                    + "       goods_code, goods_name, goods_state, goods_unit_type,"
                    + "       from_serial, to_serial,"
                    + "       amount_order, amount_real, bincode, barcode, "
                    + "       create_datetime,"
                    + "       cell_code ) ");
            sqlStockTransSerial.append(" VALUES (STOCK_TRANS_SERIAL_SEQ.nextval,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),?) LOG ERRORS REJECT LIMIT UNLIMITED ");
            //3. TAO PREPARE STATEMENT
            prstmtInsertStockTransSerial = connection.prepareStatement(sqlStockTransSerial.toString());
            prstmtInsertStockGoodsSerial = connection.prepareStatement(sqlStockGoodsSerial.toString());
            //Chi tiet serial  
            for (StockTransSerialDTO stockTransSerial : lstStockTransSerial) {
                fromSerial = stockTransSerial.getFromSerial();
                toSerial = stockTransSerial.getToSerial();
                numberNeedToCommit++;
                //SET PARAMS FOR STOCK_TRANS_SERIAL
                paramsStockTransSerial = setParamsStockTransSerial(stockTransSerial);
                //SET PARAMS AND ADD TO BATCH
                for (int idx = 0; idx < paramsStockTransSerial.size(); idx++) {
                    prstmtInsertStockTransSerial.setString(idx + 1, DataUtil.nvl(paramsStockTransSerial.get(idx), "").toString());
                }
                prstmtInsertStockTransSerial.addBatch();
                //Insert chi tiet serial
                if (!StringUtils.isInteger(fromSerial) || !StringUtils.isInteger(toSerial)) {//Serial la ky tu
                    serial = fromSerial;
                    paramsStockGoodsSerial = setParamsStockGoodsSerial(stockTrans, stockTransDetail, stockTransSerial, serial, serialStatus);
                    //SET PARAMS AND ADD TO BATCH
                    for (int idx = 0; idx < paramsStockGoodsSerial.size(); idx++) {
                        try {
                            prstmtInsertStockGoodsSerial.setString(idx + 1, DataUtil.nvl(paramsStockGoodsSerial.get(idx), "").toString());
                        } catch (Exception e) {
                            System.out.println(idx);
                        }
                    }
                    prstmtInsertStockGoodsSerial.addBatch();
                    //bo sung them amount issue
                } else {//Serial la so
                    //Kiem tra do dai serial kneu >19 thi cat do kieu Long chi co do dai toi da 19
                    int iLengthSuffixSerial = 0;
                    if (fromSerial.length() > Constants.SERIAL_LIMIT) {
                        prefixSerial = fromSerial.substring(0, fromSerial.length() - Constants.SERIAL_LIMIT);
                        suffixFromSerial = fromSerial.substring(fromSerial.length() - Constants.SERIAL_LIMIT, fromSerial.length());
                        suffixToSerial = toSerial.substring(toSerial.length() - Constants.SERIAL_LIMIT, toSerial.length());
                        iLengthSuffixSerial = suffixFromSerial.length();
                    } else {
                        suffixFromSerial = fromSerial;
                        suffixToSerial = toSerial;
                        iLengthSuffixSerial = fromSerial.length();
                    }
                    //
                    String tmpSuffixSerial;
                    for (Long lSerial = Long.parseLong(suffixFromSerial); lSerial <= Long.parseLong(suffixToSerial); lSerial++) {
                        tmpSuffixSerial = DataUtil.lPad(lSerial.toString(), "0", iLengthSuffixSerial);
                        serial = prefixSerial + tmpSuffixSerial;
                        paramsStockGoodsSerial = setParamsStockGoodsSerial(stockTrans, stockTransDetail, stockTransSerial, serial, serialStatus);
                        //SET PARAMS AND ADD TO BATCH
                        for (int idx = 0; idx < paramsStockGoodsSerial.size(); idx++) {
                            prstmtInsertStockGoodsSerial.setString(idx + 1, DataUtil.nvl(paramsStockGoodsSerial.get(idx), "").toString());
                        }
                        prstmtInsertStockGoodsSerial.addBatch();
                        //Bo sung them thong tin so luong amount issue
                        //amountIssue++;
                    }
                }//END IF
                if (numberNeedToCommit >= Constants.COMMIT_NUM) {
                    try {
                        prstmtInsertStockGoodsSerial.executeBatch();
                        prstmtInsertStockTransSerial.executeBatch();
                        numberOfSuccess = numberOfSuccess + numberNeedToCommit;
                    } catch (Exception ex) {
                        numberOfFail = numberOfFail + numberNeedToCommit;
                    }
                    numberNeedToCommit = 0;
                }
            }//END FOR
            if (numberNeedToCommit > 0) {
                try {
                    prstmtInsertStockTransSerial.executeBatch();
                    prstmtInsertStockGoodsSerial.executeBatch();
                    numberOfSuccess += numberNeedToCommit;
                } catch (Exception ex) {
//                    connection.rollback();
                    numberOfFail += numberNeedToCommit;
                }
            }
            prstmtInsertStockTransSerial.close();
            prstmtInsertStockGoodsSerial.close();
        } catch (SQLException | NumberFormatException e) {
            Logger.getLogger(StockGoodsSerialDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        //lay so luong hang hoa insert vao ban err$_
        List<StockGoodsSerialInforDTO> lstError = getListErrorImportRevoke(stockTrans.getStockTransId());
        int amountError = 0;
        if (lstError != null) {
            amountError = lstError.size();
        }
        Double strAmount = Double.parseDouble(stockTransDetail.getAmountReal() + "");
        numberOfSuccess = Integer.parseInt(String.format("%.0f", strAmount)) - amountError;//tru so luong hang insert loi => so luong hang insert thanh cong
        numberOfFail = amountError;//so luong hang loi do ta ton tai serial cua khach hang trong kho
        amountIssue = (double) numberOfSuccess;
        //
        resultDTO.setMessage(ParamUtils.SUCCESS);
        resultDTO.setQuantityFail(numberOfFail);
        resultDTO.setQuantitySucc(numberOfSuccess);
        resultDTO.setAmount(amount);
        resultDTO.setAmountIssue(amountIssue);
        // tra ve list serial loi
        resultDTO.setLstStockGoodsSerialInforDTO(lstError);
        return resultDTO;
    }

    public StockTransDetail getStockTransDetail(String stockTransDetailId, Connection connection) {
        StockTransDetail detail = new StockTransDetail();
        PreparedStatement prstmtSearch;
        List paramsStockTrans = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT   * ");
        sql.append("  FROM   stock_trans_detail ");
        sql.append(" WHERE   stock_trans_detail_id = ? ");
        //
        paramsStockTrans.add(stockTransDetailId);
        //
        try {
            prstmtSearch = connection.prepareStatement(sql.toString());
            for (int idx = 0; idx < paramsStockTrans.size(); idx++) {
                prstmtSearch.setString(idx + 1, DataUtil.nvl(paramsStockTrans.get(idx), "").toString());
            }
            ResultSet resultSet = prstmtSearch.executeQuery();
            while (resultSet.next()) {
                //detail.setStockTransDetailId(resultSet.getLong(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockGoodsSerialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detail;
    }
    /* 
     thienng1
     cap nhat so luong stock_tran_detail
     */

    public int updateStockTransDetail(String stockTransDetailId, double amount, Connection connection) {
        int isUpdateAmount = 0;
        PreparedStatement prstmtSearch;
        List paramsStockTrans = new ArrayList();
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlSelect = new StringBuilder();

        sqlSelect.append(" select * from stock_trans_detail where stock_trans_detail_id = ? ");
        PreparedStatement prstmtSelect;
        try {
            prstmtSelect = connection.prepareStatement(sqlSelect.toString());
            prstmtSelect.setString(1, stockTransDetailId);
            ResultSet rs = prstmtSelect.executeQuery();
            while (rs.next()) {
                String ps = rs.getString(1);
            }
            prstmtSelect.close();
        } catch (SQLException ex) {
            Logger.getLogger(StockGoodsSerialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        sql.append(" UPDATE stock_trans_detail ");
        sql.append(" SET amount_real = ? ");
        sql.append(" WHERE stock_trans_detail_id = ? ");
        //
        paramsStockTrans.add(amount);
        paramsStockTrans.add(stockTransDetailId);
        //
        try {
            prstmtSearch = connection.prepareStatement(sql.toString());
            for (int idx = 0; idx < paramsStockTrans.size(); idx++) {
                prstmtSearch.setString(idx + 1, DataUtil.nvl(paramsStockTrans.get(idx), "").toString());
            }
            isUpdateAmount = prstmtSearch.executeUpdate();
            prstmtSearch.close();
        } catch (SQLException ex) {
            Logger.getLogger(StockGoodsSerialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isUpdateAmount;
    }

    //support function
    //test insert stock_trans_serial
    public ResultDTO insertStockTransSerial(StockTransSerialDTO stockTransSerial, Session session) {
        ResultDTO result = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        StringBuilder sql = new StringBuilder();
        List paramsStockTrans = new ArrayList();

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
        paramsStockTrans.add(stockTransSerial.getStockTransId());//stock_trans_id
        paramsStockTrans.add(stockTransSerial.getStockTransDetailId());//stock_trans_detail_id
        paramsStockTrans.add(stockTransSerial.getStockTransDate());//stock_trans_date
        paramsStockTrans.add(stockTransSerial.getGoodsId());//goods_id
        paramsStockTrans.add(stockTransSerial.getGoodsCode());//goods_code
        paramsStockTrans.add(stockTransSerial.getGoodsName());//goods_name
        paramsStockTrans.add(stockTransSerial.getGoodsState());//goods_state, 
        paramsStockTrans.add(stockTransSerial.getGoodsUnitType());//goods_unit_type
        paramsStockTrans.add(stockTransSerial.getFromSerial());//from_serial, 
        paramsStockTrans.add(stockTransSerial.getToSerial());//to_serial
        paramsStockTrans.add(stockTransSerial.getAmountOrder());//amount_order, 
        paramsStockTrans.add(stockTransSerial.getAmountReal());//amount_real
        paramsStockTrans.add(stockTransSerial.getBincode());//bincode
        paramsStockTrans.add(stockTransSerial.getBarcode());//barcode
        paramsStockTrans.add(stockTransSerial.getCreateDatetime());// create_datetime
        paramsStockTrans.add(stockTransSerial.getCellCode());//cell_code
        //
        Query query = session.createSQLQuery(sql.toString());
        for (int idx = 0; idx < paramsStockTrans.size(); idx++) {
            query.setParameter(idx, paramsStockTrans.get(idx));
        }
        try {
            query.executeUpdate();
        } catch (Exception e) {
            message = ParamUtils.FAIL;
        }
        //
        result.setKey("1");
        result.setMessage(message);
        return result;
        //
    }

    //
    public List setParamsStockGoodsSerial(StockTransDTO stockTrans, StockTransDetailDTO stockTransDetail,
            StockTransSerialDTO stockTransSerial, String serial, String serialStatus) {
        List paramsStockGoods = new ArrayList();
        paramsStockGoods.add(stockTrans.getCustId());
        paramsStockGoods.add(stockTrans.getOwnerId());
        paramsStockGoods.add(stockTrans.getOwnerType());
        paramsStockGoods.add(stockTransDetail.getGoodsId());
        paramsStockGoods.add(stockTransDetail.getGoodsState());
        paramsStockGoods.add(serialStatus);//trang thai cho trong kho
        paramsStockGoods.add("");//sale_type
        paramsStockGoods.add("");//change_user
        paramsStockGoods.add("");//price
        paramsStockGoods.add("");//channel_type_id
        paramsStockGoods.add(stockTransSerial.getBarcode());//barcode
        paramsStockGoods.add(stockTransSerial.getStockTransDate());//change_date
        paramsStockGoods.add(stockTransSerial.getStockTransDate());//import_date
        paramsStockGoods.add("");//sale_date
        paramsStockGoods.add(stockTransSerial.getBincode());//bincode
        paramsStockGoods.add(stockTransSerial.getAddInfor());//add_infor
        paramsStockGoods.add(stockTransSerial.getCellCode());//cell_code
        paramsStockGoods.add(serial);//serial
        paramsStockGoods.add(stockTrans.getPartnerId());//partner_id
        paramsStockGoods.add(stockTrans.getStockTransId());//import_stock_trans_id
        paramsStockGoods.add(stockTrans.getOrderIdList());//order_id

        return paramsStockGoods;
    }

    public List setParamsStockTransSerial(StockTransSerialDTO stockTransSerial) {
        List paramsStockTrans = new ArrayList();
        paramsStockTrans.add(stockTransSerial.getStockTransId());//stock_trans_id
        paramsStockTrans.add(stockTransSerial.getStockTransDetailId());//stock_trans_detail_id
        paramsStockTrans.add(stockTransSerial.getStockTransDate());//stock_trans_date
        paramsStockTrans.add(stockTransSerial.getGoodsId());//goods_id
        paramsStockTrans.add(stockTransSerial.getGoodsCode());//goods_code
        paramsStockTrans.add(stockTransSerial.getGoodsName());//goods_name
        paramsStockTrans.add(stockTransSerial.getGoodsState());//goods_state, 
        paramsStockTrans.add(stockTransSerial.getGoodsUnitType());//goods_unit_type
        paramsStockTrans.add(stockTransSerial.getFromSerial());//from_serial, 
        paramsStockTrans.add(stockTransSerial.getToSerial());//to_serial
        paramsStockTrans.add(stockTransSerial.getAmountOrder());//amount_order, , , 
        paramsStockTrans.add(stockTransSerial.getAmountReal());//amount_real
        paramsStockTrans.add(stockTransSerial.getBincode());//bincode
        paramsStockTrans.add(stockTransSerial.getBarcode());//barcode
        paramsStockTrans.add(stockTransSerial.getCreateDatetime());// create_datetime
        paramsStockTrans.add(stockTransSerial.getCellCode());//cell_code

        return paramsStockTrans;
    }

    public List getListErrorImportRevoke(String stockTransId) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();

        sql.append("SELECT  ");
        sql.append("         a.ora_err_mesg$ addInfor,");
        sql.append("         a.cust_id custId,");
        sql.append("         a.owner_id ownerId,");
        sql.append("         a.owner_type ownerType,");
        sql.append("         a.goods_id goodsId,");
        sql.append("         a.goods_state goodsState,");
        sql.append("         a.status status,");
        sql.append("         a.serial fromSerial,");
        sql.append("         a.barcode barcode,");
        sql.append("         a.cell_code cellCode,");
        sql.append("         g.code goodsCode,");
        sql.append("         g.name goodsName ");
        sql.append("  FROM   err$_stock_goods_serial a, goods g");
        sql.append("  WHERE   a.goods_id = g.goods_id");
        if (!DataUtil.isStringNullOrEmpty(stockTransId)) {
            sql.append("  AND a.import_stock_trans_id = ? ");
            lstParams.add(stockTransId);
        }

        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockGoodsSerialInforDTO.class));

        query.addScalar("custId", new StringType());
        query.addScalar("ownerId", new StringType());
        query.addScalar("ownerType", new StringType());
        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("status", new StringType());
        query.addScalar("fromSerial", new StringType());
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

    public String checkSerialExist(StockGoodsSerial stockGoodsSerial, String[] lstSerial) {
        Criteria crit = session.createCriteria(StockGoodsSerial.class);
        crit.setProjection(Projections.rowCount());
        crit.add(Restrictions.eq("custId", stockGoodsSerial.getCustId()));
        crit.add(Restrictions.eq("ownerId", stockGoodsSerial.getOwnerId()));
        crit.add(Restrictions.eq("goodsId", stockGoodsSerial.getGoodsId()));
        crit.add(Restrictions.eq("goodsState", stockGoodsSerial.getGoodsState()));
        crit.add(Restrictions.in("serial", lstSerial));
        crit.add(Restrictions.eq("status", "1"));
        String number = (String) crit.uniqueResult();
        return number;
    }

    /**
     *
     * @param stockGoodsSerialDTO
     * @param session
     * @return
     * @returnupdateStockGoodsSerialByOrdersId
     */
    public String updateStockGoodsSerialByOrdersIdAndGoods(StockGoodsSerialDTO stockGoodsSerialDTO, Session session) {
        String message = ParamUtils.SUCCESS;
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        int quantitySucc;
        Query query;
        try {
            sql.append(" UPDATE STOCK_GOODS_SERIAL ");
            sql.append(" SET STATUS = ?,CHANGE_DATE = TO_DATE(?,'dd/MM/yyyy hh24:mi:ss') ");
            params.add(stockGoodsSerialDTO.getStatus());
            params.add(stockGoodsSerialDTO.getChangeDate());
            sql.append(" WHERE ORDER_ID = ? AND STATUS = ? AND GOODS_ID = ? AND GOODS_STATE = ?");
            params.add(stockGoodsSerialDTO.getOrderId());
            params.add(stockGoodsSerialDTO.getOldStatus());
            params.add(stockGoodsSerialDTO.getGoodsId());
            params.add(stockGoodsSerialDTO.getGoodsState());
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

    public String updateStockGoodsSerialByOrdersId(StockGoodsSerialDTO stockGoodsSerialDTO, Session session) {
        String message = ParamUtils.SUCCESS;
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        int quantitySucc;
        Query query;
        try {
            sql.append(" UPDATE STOCK_GOODS_SERIAL ");
            sql.append(" SET STATUS = ?,CHANGE_DATE = TO_DATE(?,'dd/MM/yyyy hh24:mi:ss') ");
            params.add(stockGoodsSerialDTO.getStatus());
            params.add(stockGoodsSerialDTO.getChangeDate());
            sql.append(" WHERE ORDER_ID = ? AND STATUS = ?");
            params.add(stockGoodsSerialDTO.getOrderId());
            params.add(stockGoodsSerialDTO.getOldStatus());
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
     * add by: ChuDV
     *
     * @param ordersDTO yeu cau xuat kho
     * @param lstGoodsSerialInforDTO - danh sach serial tai len
     * @param connection
     * @return
     * @since 17/12/15 9:30 AM
     * @Desc: Lay danh sach hang hoa theo serial don le
     */
    public List<GoodsSerialInforDTO> getGoodsBySerial(OrdersDTO ordersDTO, List<GoodsSerialInforDTO> lstGoodsSerialInforDTO, Connection connection) {
        List<GoodsSerialInforDTO> lstGoodsReturn = new ArrayList();
        String message = ParamUtils.SUCCESS;
        String GOODS_IN_STOCK = "1";
        StringBuilder sqlInsertSerialTmp = new StringBuilder();
        StringBuilder sqlGetGoodsSerial = new StringBuilder();
        StringBuilder sqlGoodsNotEnough = new StringBuilder();
        StringBuilder sqlSerialDuplicate = new StringBuilder();
        StringBuilder sqlUpdateSerialWrong = new StringBuilder();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        GoodsSerialInforDTO serialInforDTO;
        List<String> lstDupplicateSerial = new ArrayList<>();
        try {
            //Insert serial vao bang tam
            sqlInsertSerialTmp.append(" INSERT INTO SERIAL_TMP (cust_id,owner_id,owner_type,serial) ");
            sqlInsertSerialTmp.append(" VALUES (?,?,?,?)");
            preparedStatement = connection.prepareStatement(sqlInsertSerialTmp.toString());
            for (GoodsSerialInforDTO goodsSerialInforDTO : lstGoodsSerialInforDTO) {
                preparedStatement.setString(1, ordersDTO.getCustId());
                preparedStatement.setString(2, ordersDTO.getOrderStockId());
                preparedStatement.setString(3, ParamUtils.OWNER_TYPE.STOCK);
                preparedStatement.setString(4, goodsSerialInforDTO.getFromSerial());
                //SET PARAMS FOR STOCK_TRANS_SERIAL                                                
                preparedStatement.addBatch();
            }//END FOR
            //Thuc thi batch
            preparedStatement.executeBatch();
            //ChuDV add 25/12/2015 --Cap nhat serial bo ky tu dau
            sqlUpdateSerialWrong.append(" UPDATE   serial_tmp ");
            sqlUpdateSerialWrong.append(" SET   serial = SUBSTR (serial, 2) ");
            sqlUpdateSerialWrong.append(" WHERE   serial IN ( ");
            sqlUpdateSerialWrong.append(" SELECT   serial ");
            sqlUpdateSerialWrong.append("   FROM   serial_tmp ");
            sqlUpdateSerialWrong.append(" MINUS ");
            sqlUpdateSerialWrong.append(" SELECT   serial ");
            sqlUpdateSerialWrong.append("   FROM   stock_goods_serial sgs ");
            sqlUpdateSerialWrong.append("  WHERE       sgs.cust_id = ? ");
            sqlUpdateSerialWrong.append("          AND sgs.owner_id = ? ");
            sqlUpdateSerialWrong.append("          AND sgs.owner_type = ? ");
            sqlUpdateSerialWrong.append("          AND sgs.status = ? ) ");
            sqlUpdateSerialWrong.append(" AND (serial like 'S%' OR serial like 's%') ");
            preparedStatement = connection.prepareCall(sqlUpdateSerialWrong.toString());
            preparedStatement.setString(1, ordersDTO.getCustId());
            preparedStatement.setString(2, ordersDTO.getOrderStockId());
            preparedStatement.setString(3, ParamUtils.OWNER_TYPE.STOCK);
            preparedStatement.setString(4, GOODS_IN_STOCK);
            preparedStatement.executeUpdate();

            //Kiem tra hang thieu       
            sqlGoodsNotEnough.append(" SELECT   serial ");
            sqlGoodsNotEnough.append("   FROM   serial_tmp ");
            sqlGoodsNotEnough.append(" MINUS ");
            sqlGoodsNotEnough.append(" SELECT   serial ");
            sqlGoodsNotEnough.append("   FROM   stock_goods_serial sgs ");
            sqlGoodsNotEnough.append("  WHERE       sgs.cust_id = ? ");
            sqlGoodsNotEnough.append("          AND sgs.owner_id = ? ");
            sqlGoodsNotEnough.append("          AND sgs.owner_type = ? ");
            sqlGoodsNotEnough.append("          AND sgs.status = ? ");
            preparedStatement = connection.prepareCall(sqlGoodsNotEnough.toString());
            preparedStatement.setString(1, ordersDTO.getCustId());
            preparedStatement.setString(2, ordersDTO.getOrderStockId());
            preparedStatement.setString(3, ParamUtils.OWNER_TYPE.STOCK);
            preparedStatement.setString(4, GOODS_IN_STOCK);
            //Lay ket qua query
            resultSet = preparedStatement.executeQuery();
//                GoodsSerialInforDTO serialInforDTO;
            while (resultSet.next()) {
                serialInforDTO = new GoodsSerialInforDTO();
                serialInforDTO.setFromSerial(resultSet.getString("serial"));
                serialInforDTO.setToSerial(resultSet.getString("serial"));
                serialInforDTO.setNotes("SERIAL_MISSING");
//                serialInforDTO.setCustId(ordersDTO.getCustId());
//                serialInforDTO.setOwnerId(ordersDTO.getOrderStockId());
                lstGoodsReturn.add(serialInforDTO);
            }

            //Kiem tra trung
            sqlSerialDuplicate.append(" SELECT   st.serial");
            sqlSerialDuplicate.append("   FROM   goods g, serial_tmp st, stock_goods_serial sgs ");
            sqlSerialDuplicate.append("  WHERE       g.goods_id = sgs.goods_id ");
            sqlSerialDuplicate.append("          AND st.cust_id = sgs.cust_id ");
            sqlSerialDuplicate.append("          AND st.owner_id = sgs.owner_id ");
            sqlSerialDuplicate.append("          AND st.owner_type = sgs.owner_type ");
            sqlSerialDuplicate.append("          AND st.serial = sgs.serial ");
            sqlSerialDuplicate.append("          AND sgs.cust_id = ? ");
            sqlSerialDuplicate.append("          AND sgs.owner_id = ? ");
            sqlSerialDuplicate.append("          AND sgs.owner_type = ? ");
            sqlSerialDuplicate.append("          AND sgs.status = ? ");
            sqlSerialDuplicate.append("   GROUP BY   st.serial HAVING   COUNT ( * ) > 1 ");
            preparedStatement = connection.prepareCall(sqlSerialDuplicate.toString());
            preparedStatement.setString(1, ordersDTO.getCustId());
            preparedStatement.setString(2, ordersDTO.getOrderStockId());
            preparedStatement.setString(3, ParamUtils.OWNER_TYPE.STOCK);
            preparedStatement.setString(4, GOODS_IN_STOCK);
            //Lay ket qua query
            resultSet = preparedStatement.executeQuery();
//                GoodsSerialInforDTO serialInforDTO;
            while (resultSet.next()) {
                serialInforDTO = new GoodsSerialInforDTO();
                serialInforDTO.setFromSerial(resultSet.getString("serial"));
                serialInforDTO.setToSerial(resultSet.getString("serial"));
                serialInforDTO.setNotes("SERIAL_DUPPLICATE");
                lstDupplicateSerial.add(serialInforDTO.getFromSerial());
//                serialInforDTO.setCustId(ordersDTO.getCustId());
//                serialInforDTO.setOwnerId(ordersDTO.getOrderStockId());
                lstGoodsReturn.add(serialInforDTO);
            }

            //Lay danh sach hang hoa
            sqlGetGoodsSerial.append(" SELECT   g.goods_id goodsId, ");
            sqlGetGoodsSerial.append("          g.code goodsCode, ");
            sqlGetGoodsSerial.append("          g.name goodsName, ");
            sqlGetGoodsSerial.append("          st.serial serial, ");
            sqlGetGoodsSerial.append("          sgs.goods_state goodsState, ");
            sqlGetGoodsSerial.append("          sgs.cell_code cellCode ");
            sqlGetGoodsSerial.append("   FROM   goods g, serial_tmp st, stock_goods_serial sgs ");
            sqlGetGoodsSerial.append("  WHERE       g.goods_id = sgs.goods_id ");
            sqlGetGoodsSerial.append("          AND st.cust_id = sgs.cust_id ");
            sqlGetGoodsSerial.append("          AND st.owner_id = sgs.owner_id ");
            sqlGetGoodsSerial.append("          AND st.owner_type = sgs.owner_type ");
            sqlGetGoodsSerial.append("          AND st.serial = sgs.serial ");
            sqlGetGoodsSerial.append("          AND sgs.cust_id = ? ");
            sqlGetGoodsSerial.append("          AND sgs.owner_id = ? ");
            sqlGetGoodsSerial.append("          AND sgs.owner_type = ? ");
            sqlGetGoodsSerial.append("          AND sgs.status = ? ");
            sqlGetGoodsSerial.append("  ORDER BY goodsCode, goodsState, serial ");

            preparedStatement = connection.prepareCall(sqlGetGoodsSerial.toString());
            preparedStatement.setString(1, ordersDTO.getCustId());
            preparedStatement.setString(2, ordersDTO.getOrderStockId());
            preparedStatement.setString(3, ParamUtils.OWNER_TYPE.STOCK);
            preparedStatement.setString(4, GOODS_IN_STOCK);
            //Lay ket qua query
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                serialInforDTO = new GoodsSerialInforDTO();
                serialInforDTO.setGoodsId(resultSet.getString("goodsId"));
                serialInforDTO.setGoodsCode(resultSet.getString("goodsCode"));
                serialInforDTO.setGoodsName(resultSet.getString("goodsName"));
                serialInforDTO.setGoodsState(resultSet.getString("goodsState"));
                serialInforDTO.setCellCode(resultSet.getString("cellCode"));
                serialInforDTO.setFromSerial(resultSet.getString("serial"));
                serialInforDTO.setToSerial(resultSet.getString("serial"));
                serialInforDTO.setQuantity("1");
                if (lstDupplicateSerial.contains(serialInforDTO.getFromSerial())) {
                    serialInforDTO.setNotes("SERIAL_DUPPLICATE");
                }
//                serialInforDTO.setCustId(ordersDTO.getCustId());
//                serialInforDTO.setOwnerId(ordersDTO.getOrderStockId());
                lstGoodsReturn.add(serialInforDTO);
            }
            preparedStatement.close();
        } catch (Exception e) {
            message = ParamUtils.FAIL;
            lstGoodsReturn = new ArrayList<>();
            e.printStackTrace();
        }
        return lstGoodsReturn;
    }

    /**
     * thienng1 lay danh sach hang theo theo dieu kien tim kiem
     */
    public List<StockGoodsSerialDTO> getListStockGoodsSerial(ChangeGoods changeGoods) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();
        StringBuilder path1 = new StringBuilder();
        StringBuilder path2 = new StringBuilder();
        StringBuilder path3 = new StringBuilder();
        path1.append(" SELECT sts.id id, sts.cust_id custId, sts.owner_id ownerId, sts.goods_id goodsId,");
        path1.append("        sts.goods_state goodsState, sts.status status, sts.serial serial, sts.cell_code cellCode ");
        path1.append("        FROM stock_goods_serial sts, goods g ");

        path2.append(" WHERE sts.goods_id = g.goods_id ");

        path3.append("    AND g.code = ? ");
        lstParams.add(changeGoods.getOldGoodsCode());

        if (!DataUtil.isStringNullOrEmpty(changeGoods.getStockCode())) {
            path1.append(", stock s ");
            path2.append("    AND sts.owner_id = s.stock_id ");
            path3.append("    AND s.code = UPPER(?) ");
            lstParams.add(changeGoods.getStockCode());
        }
        if (!DataUtil.isStringNullOrEmpty(changeGoods.getCellCode())) {
            path3.append("    AND sts.cell_code = UPPER(?) ");
            lstParams.add(changeGoods.getCellCode());
        }
        if (!DataUtil.isStringNullOrEmpty(changeGoods.getCustId())) {
            path3.append("    AND sts.cust_id = ? ");
            lstParams.add(changeGoods.getCustId());
        }

        if (!DataUtil.isStringNullOrEmpty(changeGoods.getOldFromSerial())) {
            path3.append("    AND sts.serial = UPPER(?) ");
            lstParams.add(changeGoods.getOldFromSerial());
        }
        sql.append(path1);
        sql.append(path2);
        sql.append(path3);

        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockGoodsSerialDTO.class));

        query.addScalar("id", new StringType());
        query.addScalar("custId", new StringType());
        query.addScalar("ownerId", new StringType());
        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("status", new StringType());
        query.addScalar("serial", new StringType());
        query.addScalar("cellCode", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        return query.list();
    }

    /**
     * Ham cap nhat trang thai hang hoa
     *
     * @param changeGoods
     * @param session
     * @return
     */
    public int updateChangeGoodsState(ChangeGoods changeGoods, Session session) {
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" UPDATE stock_goods_serial ");
            sql.append(" SET goods_state = ? ");
            sql.append(" WHERE goods_id = ? AND goods_state = ? ");
            sql.append("   AND serial = ? AND STATUS = ? ");
            //
            params.add(changeGoods.getNewState());
            params.add(changeGoods.getOldGoodsId());
            params.add(changeGoods.getOldState());
            params.add(changeGoods.getOldFromSerial());
            params.add(ParamUtils.GOODS_IMPORT_STATUS.IMPORTED);
            if (!DataUtil.isStringNullOrEmpty(changeGoods.getCustId())) {
                sql.append(" AND cust_id = ? ");
                params.add(changeGoods.getCustId());
            }
            if (!DataUtil.isStringNullOrEmpty(changeGoods.getStockId())) {
                sql.append(" AND owner_id = ? ");
                params.add(changeGoods.getStockId());
            }
            if (!DataUtil.isStringNullOrEmpty(changeGoods.getCellCode())) {
                sql.append(" AND cell_code = ? ");
                params.add(changeGoods.getCellCode());
            }

            //
            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            int iUpdate = query.executeUpdate();
            return iUpdate;
        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    /**
     * Thienng1 Addby 26/01/2015 Dieu chinh serial hang hoa
     */
    public ResultDTO updateNewSerialGoods(StockGoodsSerialDTO importStockGoodsSerial, StockGoodsSerialDTO exportedStockGoodsSerial,
            String fromSerial, String toSerial, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc = 0;
        Double amountIssue = 0D;

        //XU LY DOI VOI SERIAL CO KI TU -> SERIAL KHONG THEO DAI
        if (!StringUtils.isInteger(fromSerial) || !StringUtils.isInteger(toSerial)) {
            importStockGoodsSerial.setSerial(fromSerial + "");
            try {
                StringBuilder sql = new StringBuilder();
                sql.append(" UPDATE stock_goods_serial ");
                sql.append(" SET serial = ?, status =?, goods_id = ?, ");
                sql.append("  import_stock_trans_id = ? ");
                sql.append(" WHERE goods_id =? AND serial=? ");
                sql.append(" AND cust_id =? AND owner_id=? ");
                //
                //
                Query query = session.createSQLQuery(sql.toString());

                query.setParameter(0, importStockGoodsSerial.getSerial());
                query.setParameter(1, importStockGoodsSerial.getStatus());
                query.setParameter(2, importStockGoodsSerial.getGoodsId());
                query.setParameter(3, importStockGoodsSerial.getImportStockTransId());
                query.setParameter(4, exportedStockGoodsSerial.getGoodsId());
                query.setParameter(5, exportedStockGoodsSerial.getSerial());
                query.setParameter(6, exportedStockGoodsSerial.getCustId());
                query.setParameter(7, exportedStockGoodsSerial.getOwnerId());

                quantitySucc = query.executeUpdate();
                if (quantitySucc == 0) {
                    message = ParamUtils.FAIL;
                    key = ParamUtils.SYSTEM_OR_DATA_ERROR;
                }
                amountIssue++;

            } catch (Exception ex) {
                Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
                message = ParamUtils.FAIL;
                key = ParamUtils.SYSTEM_OR_DATA_ERROR;
            }
        } else {
            //xu ly chuoi serial export lon hon 19 ky tu
            String serial;
            String serialImport;
            String prefixSerial = "";
            String suffixFromSerial;
            String suffixToSerial;
            int iLengthSuffixSerial = 0;
            if (fromSerial.length() > Constants.SERIAL_LIMIT) {
                prefixSerial = fromSerial.substring(0, fromSerial.length() - Constants.SERIAL_LIMIT);
                suffixFromSerial = fromSerial.substring(fromSerial.length() - Constants.SERIAL_LIMIT, fromSerial.length());
                suffixToSerial = toSerial.substring(toSerial.length() - Constants.SERIAL_LIMIT, toSerial.length());
                iLengthSuffixSerial = suffixFromSerial.length();
            } else {
                suffixFromSerial = fromSerial;
                suffixToSerial = toSerial;
                iLengthSuffixSerial = fromSerial.length();
            }
            //xu ly chuoi serial Import
            String prefixSerialImport = "";
            String suffixSerialImport;
            int iLengthSuffixSerialImport = 0;
            if (importStockGoodsSerial.getSerial().length() > Constants.SERIAL_LIMIT) {
                prefixSerialImport = importStockGoodsSerial.getSerial().substring(0,
                        importStockGoodsSerial.getSerial().length() - Constants.SERIAL_LIMIT);
                suffixSerialImport = importStockGoodsSerial.getSerial().substring(importStockGoodsSerial.getSerial().length()
                        - Constants.SERIAL_LIMIT, fromSerial.length());
                iLengthSuffixSerialImport = suffixSerialImport.length();
            } else {
                suffixSerialImport = importStockGoodsSerial.getSerial();
                iLengthSuffixSerialImport = importStockGoodsSerial.getSerial().length();
            }
            //
            String tmpSuffixSerial;
            String tmpSuffixSerialImport;
            Long index = 0L;
            for (Long lSerial = Long.parseLong(suffixFromSerial); lSerial <= Long.parseLong(suffixToSerial); lSerial++) {
                tmpSuffixSerial = DataUtil.lPad(lSerial.toString(), "0", iLengthSuffixSerial);
                serial = prefixSerial + tmpSuffixSerial;
                importStockGoodsSerial.setSerial(serial + "");
                //
                Long lSerialImport = Long.parseLong(suffixSerialImport) + index;
                tmpSuffixSerialImport = DataUtil.lPad(lSerialImport.toString(), "0", iLengthSuffixSerialImport);
                serialImport = prefixSerialImport + tmpSuffixSerialImport;
                index += 1;

                try {
                    StringBuilder sql = new StringBuilder();
                    sql.append(" UPDATE stock_goods_serial ");
                    sql.append(" SET serial = ?, status =?, goods_id = ?,  ");
                    sql.append("  import_stock_trans_id = ? ");
                    sql.append(" WHERE goods_id =? AND serial=? ");
                    sql.append(" AND cust_id =? AND owner_id=? ");
                    //
                    Query query = session.createSQLQuery(sql.toString());

                    query.setParameter(0, serialImport);
                    query.setParameter(1, importStockGoodsSerial.getStatus());
                    query.setParameter(2, importStockGoodsSerial.getGoodsId());
                    query.setParameter(3, importStockGoodsSerial.getImportStockTransId());
                    query.setParameter(4, exportedStockGoodsSerial.getGoodsId());
                    query.setParameter(5, exportedStockGoodsSerial.getSerial());
                    query.setParameter(6, exportedStockGoodsSerial.getCustId());
                    query.setParameter(7, exportedStockGoodsSerial.getOwnerId());

                    quantitySucc = query.executeUpdate();
                    if (quantitySucc == 0) {
                        message = ParamUtils.FAIL;
                        key = ParamUtils.SYSTEM_OR_DATA_ERROR;
                    }
                    amountIssue++;

                } catch (Exception ex) {
                    Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
                    message = ParamUtils.FAIL;
                    key = ParamUtils.SYSTEM_OR_DATA_ERROR;
                }
            }
        }
        //
        resultDTO.setMessage(message);
        resultDTO.setKey(key);
        resultDTO.setQuantitySucc(quantitySucc);
        resultDTO.setAmountIssue(amountIssue);
        return resultDTO;
    }

    //thienng1 --modify 06/04/2016
    //kiem ke hang hoa
    public List<GoodsSerialInforDTO> getGoodsBySerialInventory(OrdersDTO ordersDTO, List<GoodsSerialInforDTO> lstGoodsSerialInforDTO, Connection connection) {
        List<GoodsSerialInforDTO> lstGoodsReturn = new ArrayList();
        String message = ParamUtils.SUCCESS;
        String GOODS_IN_STOCK = "1,2";
        StringBuilder sqlInsertSerialTmp = new StringBuilder();
        StringBuilder sqlGetGoodsSerial = new StringBuilder();
        StringBuilder sqlGoodsNotEnough = new StringBuilder();
        StringBuilder sqlSerialDuplicate = new StringBuilder();
        StringBuilder sqlUpdateSerialWrong = new StringBuilder();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        GoodsSerialInforDTO serialInforDTO;
        List<String> lstDupplicateSerial = new ArrayList<>();
        try {
            //Insert serial vao bang tam
            sqlInsertSerialTmp.append(" INSERT INTO SERIAL_INVENTORY_TMP (cust_id,owner_id,owner_type,serial) ");
            sqlInsertSerialTmp.append(" VALUES (?,?,?,?)");
            preparedStatement = connection.prepareStatement(sqlInsertSerialTmp.toString());
            for (GoodsSerialInforDTO goodsSerialInforDTO : lstGoodsSerialInforDTO) {
                preparedStatement.setString(1, ordersDTO.getCustId());
                preparedStatement.setString(2, ordersDTO.getOrderStockId());
                preparedStatement.setString(3, ParamUtils.OWNER_TYPE.STOCK);
                preparedStatement.setString(4, goodsSerialInforDTO.getFromSerial().toUpperCase());
                //SET PARAMS FOR STOCK_TRANS_SERIAL                                        
                preparedStatement.addBatch();
            }//END FOR
            //Thuc thi batch
            preparedStatement.executeBatch();
            //ChuDV add 25/12/2015 --Cap nhat serial bo ky tu dau
            sqlUpdateSerialWrong.append(" UPDATE   serial_inventory_tmp ");
            sqlUpdateSerialWrong.append(" SET   serial = SUBSTR (serial, 2) ");
            sqlUpdateSerialWrong.append(" WHERE   serial IN ( ");
            sqlUpdateSerialWrong.append(" SELECT   UPPER(serial) ");
            sqlUpdateSerialWrong.append("   FROM   serial_inventory_tmp ");
            sqlUpdateSerialWrong.append(" MINUS ");
            sqlUpdateSerialWrong.append(" SELECT   UPPER(serial) ");
            sqlUpdateSerialWrong.append("   FROM   stock_goods_serial sgs ");
            sqlUpdateSerialWrong.append("  WHERE       sgs.cust_id = ? ");
            sqlUpdateSerialWrong.append("          AND sgs.owner_id = ? ");
            sqlUpdateSerialWrong.append("          AND sgs.owner_type = ? ");
            sqlUpdateSerialWrong.append("          AND sgs.status IN (1,2) ) ");
            sqlUpdateSerialWrong.append(" AND (serial like 'S%' OR serial like 's%') ");
            preparedStatement = connection.prepareCall(sqlUpdateSerialWrong.toString());
            preparedStatement.setString(1, ordersDTO.getCustId());
            preparedStatement.setString(2, ordersDTO.getOrderStockId());
            preparedStatement.setString(3, ParamUtils.OWNER_TYPE.STOCK);
            preparedStatement.executeUpdate();

            //Kiem tra hang thieu       
            sqlGoodsNotEnough.append(" SELECT   serial");
            sqlGoodsNotEnough.append("   FROM   serial_inventory_tmp ");
            sqlGoodsNotEnough.append(" MINUS ");
            sqlGoodsNotEnough.append(" SELECT   UPPER(serial) serial");
            sqlGoodsNotEnough.append("   FROM   stock_goods_serial sgs ");
            sqlGoodsNotEnough.append("  WHERE       sgs.cust_id = ? ");
            sqlGoodsNotEnough.append("          AND sgs.owner_id = ? ");
            sqlGoodsNotEnough.append("          AND sgs.owner_type = ? ");
            sqlGoodsNotEnough.append("          AND sgs.status IN (1,2) ");
            preparedStatement = connection.prepareCall(sqlGoodsNotEnough.toString());
            preparedStatement.setString(1, ordersDTO.getCustId());
            preparedStatement.setString(2, ordersDTO.getOrderStockId());
            preparedStatement.setString(3, ParamUtils.OWNER_TYPE.STOCK);
            //Lay ket qua query
            resultSet = preparedStatement.executeQuery();
//                GoodsSerialInforDTO serialInforDTO;
            while (resultSet.next()) {
                serialInforDTO = new GoodsSerialInforDTO();
                serialInforDTO.setFromSerial(resultSet.getString("serial"));
                serialInforDTO.setToSerial(resultSet.getString("serial"));
                serialInforDTO.setNotes("SERIAL_MISSING");
//                serialInforDTO.setCustId(ordersDTO.getCustId());
//                serialInforDTO.setOwnerId(ordersDTO.getOrderStockId());
                lstGoodsReturn.add(serialInforDTO);
            }

            //Kiem tra trung
            sqlSerialDuplicate.append(" SELECT   st.serial");
            sqlSerialDuplicate.append("   FROM   goods g, serial_inventory_tmp st, stock_goods_serial sgs ");
            sqlSerialDuplicate.append("  WHERE       g.goods_id = sgs.goods_id ");
            sqlSerialDuplicate.append("          AND st.cust_id = sgs.cust_id ");
            sqlSerialDuplicate.append("          AND st.owner_id = sgs.owner_id ");
            sqlSerialDuplicate.append("          AND st.owner_type = sgs.owner_type ");
            sqlSerialDuplicate.append("          AND UPPER(st.serial) = UPPER(sgs.serial) ");
            sqlSerialDuplicate.append("          AND sgs.cust_id = ? ");
            sqlSerialDuplicate.append("          AND sgs.owner_id = ? ");
            sqlSerialDuplicate.append("          AND sgs.owner_type = ? ");
            sqlSerialDuplicate.append("          AND sgs.status IN (1,2) ");
            sqlSerialDuplicate.append("   GROUP BY   st.serial HAVING   COUNT ( * ) > 1 ");
            preparedStatement = connection.prepareCall(sqlSerialDuplicate.toString());
            preparedStatement.setString(1, ordersDTO.getCustId());
            preparedStatement.setString(2, ordersDTO.getOrderStockId());
            preparedStatement.setString(3, ParamUtils.OWNER_TYPE.STOCK);
            //Lay ket qua query
            resultSet = preparedStatement.executeQuery();
//                GoodsSerialInforDTO serialInforDTO;
            while (resultSet.next()) {
                serialInforDTO = new GoodsSerialInforDTO();
                serialInforDTO.setFromSerial(resultSet.getString("serial"));
                serialInforDTO.setToSerial(resultSet.getString("serial"));
                serialInforDTO.setNotes("SERIAL_DUPPLICATE");
                lstDupplicateSerial.add(serialInforDTO.getFromSerial());
//                serialInforDTO.setCustId(ordersDTO.getCustId());
//                serialInforDTO.setOwnerId(ordersDTO.getOrderStockId());
                lstGoodsReturn.add(serialInforDTO);
            }

            //Lay danh sach hang hoa
            sqlGetGoodsSerial.append(" SELECT   g.goods_id goodsId, ");
            sqlGetGoodsSerial.append("          g.code goodsCode, ");
            sqlGetGoodsSerial.append("          g.name goodsName, ");
            sqlGetGoodsSerial.append("          g.unit_type unitType, ");
            sqlGetGoodsSerial.append("          sgs.barcode barcode, ");
            sqlGetGoodsSerial.append("          st.serial serial, ");
            sqlGetGoodsSerial.append("          sgs.goods_state goodsState, ");
            sqlGetGoodsSerial.append("          sgs.cell_code cellCode ");
            sqlGetGoodsSerial.append("   FROM   goods g, serial_inventory_tmp st, stock_goods_serial sgs ");
            sqlGetGoodsSerial.append("  WHERE       g.goods_id = sgs.goods_id ");
            sqlGetGoodsSerial.append("          AND st.cust_id = sgs.cust_id ");
            sqlGetGoodsSerial.append("          AND st.owner_id = sgs.owner_id ");
            sqlGetGoodsSerial.append("          AND st.owner_type = sgs.owner_type ");
            sqlGetGoodsSerial.append("          AND UPPER(st.serial) = UPPER(sgs.serial) ");
            sqlGetGoodsSerial.append("          AND sgs.cust_id = ? ");
            sqlGetGoodsSerial.append("          AND sgs.owner_id = ? ");
            sqlGetGoodsSerial.append("          AND sgs.owner_type = ? ");
            sqlGetGoodsSerial.append("          AND sgs.status IN (1,2) ");
            sqlGetGoodsSerial.append("  ORDER BY goodsCode, goodsState, serial ");

            preparedStatement = connection.prepareCall(sqlGetGoodsSerial.toString());
            preparedStatement.setString(1, ordersDTO.getCustId());
            preparedStatement.setString(2, ordersDTO.getOrderStockId());
            preparedStatement.setString(3, ParamUtils.OWNER_TYPE.STOCK);
            //Lay ket qua query
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                serialInforDTO = new GoodsSerialInforDTO();
                serialInforDTO.setGoodsId(resultSet.getString("goodsId"));
                serialInforDTO.setGoodsCode(resultSet.getString("goodsCode"));
                serialInforDTO.setGoodsName(resultSet.getString("goodsName"));
                serialInforDTO.setGoodsState(resultSet.getString("goodsState"));
                serialInforDTO.setCellCode(resultSet.getString("cellCode"));
                serialInforDTO.setBarcode(resultSet.getString("barcode"));
                serialInforDTO.setFromSerial(resultSet.getString("serial"));
                serialInforDTO.setToSerial(resultSet.getString("serial"));
                serialInforDTO.setUnitType(resultSet.getString("unitType"));
                serialInforDTO.setQuantity("1");
                if (lstDupplicateSerial.contains(serialInforDTO.getFromSerial())) {
                    serialInforDTO.setNotes("SERIAL_DUPPLICATE");
                }
//                serialInforDTO.setCustId(ordersDTO.getCustId());
//                serialInforDTO.setOwnerId(ordersDTO.getOrderStockId());
                lstGoodsReturn.add(serialInforDTO);
            }
            preparedStatement.close();
        } catch (Exception e) {
            message = ParamUtils.FAIL;
            lstGoodsReturn = new ArrayList<>();
            e.printStackTrace();
        }
        return lstGoodsReturn;
    }

    //QuyenDM 20160413 - Cap nhap hang thu hoi doi voi serial don le
    public ResultDTO updateOrInsertSyncImportGoodsRevoke(StockTransDTO stockTrans,
            StockTransDetailDTO stockTransDetail, List<StockTransSerialDTO> lstStockTransSerial,
            Connection connection, String serialStatus, boolean isUpdate) {
        ResultDTO resultDTO = new ResultDTO();
        //THONG TIN SO LUONG NHAP
        Double amount = 0D;
        Double amountIssue = 0D;
        //Cau lenh cap nhat serial
        StringBuilder sqlStockGoodsSerial = new StringBuilder();
        List paramsStockGoodsSerial;
        List paramsStockTransSerial;
        PreparedStatement prstmtInsertStockTransSerial;
        PreparedStatement prstmtInsertStockGoodsSerial;
        String fromSerial;
        String toSerial;
        String serial;
        String prefixSerial = "";
        String suffixFromSerial;
        String suffixToSerial;
        int numberNeedToCommit = 0;
        int numberOfSuccess = 0;
        int numberOfFail = 0;
        if (isUpdate) {//Neu la cap nhat
            sqlStockGoodsSerial.append(" UPDATE stock_goods_serial ");
            sqlStockGoodsSerial.append(" SET status = ?, change_date = to_date(?,'dd/MM/yyyy hh24:mi:ss'), cell_code = ?, barcode = ?, ");
            sqlStockGoodsSerial.append(" bincode = ?, goods_state = ?, import_stock_trans_id = ?, order_id = ?, ");
            sqlStockGoodsSerial.append(" owner_id = ?, owner_type = ? ");
            sqlStockGoodsSerial.append(" WHERE cust_id = ? AND goods_id = ? AND serial = ? ");
            sqlStockGoodsSerial.append(" AND status = ? ");
            sqlStockGoodsSerial.append(" LOG ERRORS REJECT LIMIT UNLIMITED");
        } else {//Neu la them moi
            sqlStockGoodsSerial.append(" INSERT INTO stock_goods_serial (id, cust_id, owner_id, owner_type, goods_id,");
            sqlStockGoodsSerial.append("       goods_state, status,sale_type, change_user,");
            sqlStockGoodsSerial.append("       price,channel_type_id, barcode, change_date,");
            sqlStockGoodsSerial.append("       import_date, sale_date, bincode, add_infor, cell_code,");
            sqlStockGoodsSerial.append("       serial,partner_id,import_stock_trans_id,order_id) ");
            sqlStockGoodsSerial.append(" VALUES (STOCK_GOODS_SERIAL_SEQ.nextval,?,?,?,?,?,?,?,?,TO_NUMBER(?),");
            sqlStockGoodsSerial.append(" TO_NUMBER(?),?,to_date(?,'dd/MM/yyyy hh24:mi:ss'),");
            sqlStockGoodsSerial.append(" to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),TO_NUMBER(?)) ");
            sqlStockGoodsSerial.append(" LOG ERRORS REJECT LIMIT UNLIMITED ");
        }
        //Cau lenh them moi giao dich
        StringBuilder sqlStockTransSerial = new StringBuilder();
        sqlStockTransSerial.append(" INSERT INTO stock_trans_serial ");
        sqlStockTransSerial.append(" ( stock_trans_serial_id, stock_trans_id,");
        sqlStockTransSerial.append("       stock_trans_detail_id, stock_trans_date, goods_id,");
        sqlStockTransSerial.append("       goods_code, goods_name, goods_state, goods_unit_type,");
        sqlStockTransSerial.append("       from_serial, to_serial,");
        sqlStockTransSerial.append("       amount_order, amount_real, bincode, barcode, ");
        sqlStockTransSerial.append("       create_datetime,");
        sqlStockTransSerial.append("       cell_code ) ");
        sqlStockTransSerial.append(" VALUES (STOCK_TRANS_SERIAL_SEQ.nextval,?,?,");
        sqlStockTransSerial.append(" to_date(?,'dd/MM/yyyy hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,?,");
        sqlStockTransSerial.append(" to_date(?,'dd/MM/yyyy hh24:mi:ss'),?) ");
        sqlStockTransSerial.append(" LOG ERRORS REJECT LIMIT UNLIMITED ");
        try {
            //3. TAO PREPARE STATEMENT
            prstmtInsertStockTransSerial = connection.prepareStatement(sqlStockTransSerial.toString());
            prstmtInsertStockGoodsSerial = connection.prepareStatement(sqlStockGoodsSerial.toString());
            //Chi tiet serial  
            for (StockTransSerialDTO stockTransSerial : lstStockTransSerial) {
                fromSerial = stockTransSerial.getFromSerial();
                toSerial = stockTransSerial.getToSerial();
                stockTransSerial.setStockTransId(stockTrans.getStockTransId());
                stockTransSerial.setStockTransDetailId(stockTransDetail.getStockTransDetailId());
                numberNeedToCommit++;
                //SET PARAMS FOR STOCK_TRANS_SERIAL
                paramsStockTransSerial = setParamsStockTransSerial(stockTransSerial);
                //SET PARAMS AND ADD TO BATCH
                for (int idx = 0; idx < paramsStockTransSerial.size(); idx++) {
                    prstmtInsertStockTransSerial.setString(idx + 1, DataUtil.nvl(paramsStockTransSerial.get(idx), "").toString());
                }
                prstmtInsertStockTransSerial.addBatch();
                //Insert chi tiet serial
                if (!StringUtils.isInteger(fromSerial) || !StringUtils.isInteger(toSerial)) {//Serial la ky tu
                    serial = fromSerial;
                    if (isUpdate) {
                        paramsStockGoodsSerial = setParamForStockGoodsSerialInvoke(stockTrans, stockTransDetail,
                                stockTransSerial, serial, serialStatus);
                    } else {
                        paramsStockGoodsSerial = setParamsStockGoodsSerial(stockTrans, stockTransDetail,
                                stockTransSerial, serial, serialStatus);
                    }
                    //SET PARAMS AND ADD TO BATCH
                    for (int idx = 0; idx < paramsStockGoodsSerial.size(); idx++) {
                        try {
                            prstmtInsertStockGoodsSerial.setString(idx + 1, DataUtil.nvl(paramsStockGoodsSerial.get(idx), "").toString());
                        } catch (Exception e) {
                            System.out.println(idx);
                        }
                    }
                    prstmtInsertStockGoodsSerial.addBatch();
                    //bo sung them amount issue
                } else {//Serial la so
                    //Kiem tra do dai serial kneu >19 thi cat do kieu Long chi co do dai toi da 19
                    int iLengthSuffixSerial = 0;
                    if (fromSerial.length() > Constants.SERIAL_LIMIT) {
                        prefixSerial = fromSerial.substring(0, fromSerial.length() - Constants.SERIAL_LIMIT);
                        suffixFromSerial = fromSerial.substring(fromSerial.length() - Constants.SERIAL_LIMIT, fromSerial.length());
                        suffixToSerial = toSerial.substring(toSerial.length() - Constants.SERIAL_LIMIT, toSerial.length());
                        iLengthSuffixSerial = suffixFromSerial.length();
                    } else {
                        suffixFromSerial = fromSerial;
                        suffixToSerial = toSerial;
                        iLengthSuffixSerial = fromSerial.length();
                    }
                    //
                    String tmpSuffixSerial;
                    for (Long lSerial = Long.parseLong(suffixFromSerial); lSerial <= Long.parseLong(suffixToSerial); lSerial++) {
                        tmpSuffixSerial = DataUtil.lPad(lSerial.toString(), "0", iLengthSuffixSerial);
                        serial = prefixSerial + tmpSuffixSerial;
                        if (isUpdate) {
                            paramsStockGoodsSerial = setParamForStockGoodsSerialInvoke(stockTrans, stockTransDetail,
                                    stockTransSerial, serial, serialStatus);
                        } else {
                            paramsStockGoodsSerial = setParamsStockGoodsSerial(stockTrans, stockTransDetail,
                                    stockTransSerial, serial, serialStatus);
                        }
                        //SET PARAMS AND ADD TO BATCH
                        for (int idx = 0; idx < paramsStockGoodsSerial.size(); idx++) {
                            prstmtInsertStockGoodsSerial.setString(idx + 1, DataUtil.nvl(paramsStockGoodsSerial.get(idx), "").toString());
                        }
                        prstmtInsertStockGoodsSerial.addBatch();
                        //Bo sung them thong tin so luong amount issue
                        //amountIssue++;
                    }
                }//END IF
                if (numberNeedToCommit >= Constants.COMMIT_NUM) {
                    try {
                        prstmtInsertStockGoodsSerial.executeBatch();
                        prstmtInsertStockTransSerial.executeBatch();
                        numberOfSuccess = numberOfSuccess + numberNeedToCommit;
                    } catch (Exception ex) {
                        numberOfFail = numberOfFail + numberNeedToCommit;
                    }
                    numberNeedToCommit = 0;
                }
            }//END FOR
            if (numberNeedToCommit > 0) {
                try {
                    prstmtInsertStockGoodsSerial.executeBatch();
                    prstmtInsertStockTransSerial.executeBatch();
                    numberOfSuccess += numberNeedToCommit;
                } catch (Exception ex) {
//                    connection.rollback();
                    numberOfFail += numberNeedToCommit;
                }
            }
            prstmtInsertStockGoodsSerial.close();
            prstmtInsertStockTransSerial.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //lay so luong hang hoa insert vao ban err$_
        List<StockGoodsSerialInforDTO> lstError = getListErrorImportRevoke(stockTrans.getStockTransId());
        int amountError = 0;
        if (lstError != null) {
            amountError = lstError.size();
        }
        Double strAmount = Double.parseDouble(lstStockTransSerial.size() + "");
        numberOfSuccess = Integer.parseInt(String.format("%.0f", strAmount)) - amountError;//tru so luong hang insert loi => so luong hang insert thanh cong
        numberOfFail = amountError;//so luong hang loi do ta ton tai serial cua khach hang trong kho
        amountIssue = (double) numberOfSuccess;
        //
        resultDTO.setMessage(ParamUtils.SUCCESS);
        resultDTO.setQuantityFail(numberOfFail);
        resultDTO.setQuantitySucc(numberOfSuccess);
        resultDTO.setAmount(amount);
        resultDTO.setAmountIssue(amountIssue);
        // tra ve list serial loi
        resultDTO.setLstStockGoodsSerialInforDTO(lstError);
        return resultDTO;
    }

    //QuyenDM 20160413 - Set cac tham so cho cau lenh update
    public List setParamForStockGoodsSerialInvoke(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetail,
            StockTransSerialDTO stockTransSerial, String serial, String serialStatus) {
        List params = new ArrayList();
        //Nhap hang thu hoi trang thai tam nhap
        params.add(serialStatus);
        params.add(stockTransSerial.getStockTransDate());
        params.add(stockTransSerial.getCellCode());
        params.add(stockTransSerial.getBarcode());
        params.add(stockTransSerial.getBincode());
        //if (Constants.STATUS_SERIAL_WAIT_STOCK.equals(synImportRevoke)) {
        params.add(stockTransSerial.getGoodsState());
        params.add(stockTransDTO.getStockTransId());
        params.add(DataUtil.nvl(stockTransDTO.getOrderIdList(), ""));
        params.add(stockTransDTO.getOwnerId());
        params.add(stockTransDTO.getOwnerType());
        //}
        params.add(stockTransDTO.getCustId());
        params.add(stockTransDetail.getGoodsId());
        params.add(serial);
        //params.add(stockGoodsSerial.getGoodsState());
        params.add(Constants.STATUS_SERIAL_OUT_STOCK);
        return params;
    }

    public List<StockTransGoodsDTO> getListStockTransGoods2Report(String lstStockTransCodes) {
        StringBuilder sqlGetStockTransGoods = new StringBuilder();
        sqlGetStockTransGoods.append("SELECT   st.stock_trans_type stockTransType, ");
        sqlGetStockTransGoods.append("         st.stock_trans_status stockTransStatus, ");
        sqlGetStockTransGoods.append("         st.order_code orderCode, ");
        sqlGetStockTransGoods.append("         st.order_action_code orderActionCode, ");
        sqlGetStockTransGoods.append("         st.stock_trans_code stockTransCode, ");
        sqlGetStockTransGoods.append("         st.syn_trans_code synTransCode, ");
        sqlGetStockTransGoods.append("         TO_CHAR (st.real_stock_trans_date, 'dd/MM/yyyy') realStockTransDate, ");
        sqlGetStockTransGoods.append("         TO_CHAR (st.stock_trans_date, 'dd/MM/yyyy') stockTransDate, ");
        sqlGetStockTransGoods.append("         st.trans_user_name transUserName, ");
        sqlGetStockTransGoods.append("         st.notes notes, ");
        sqlGetStockTransGoods.append("         st.order_id_list orderId,");
        sqlGetStockTransGoods.append("         st.owner_id stockId, ");
        sqlGetStockTransGoods.append("         a.code stockCode, ");
        sqlGetStockTransGoods.append("         o.receive_name receiveName, ");
        sqlGetStockTransGoods.append("         std.goods_code goodsCode, ");
        sqlGetStockTransGoods.append("         std.goods_name goodsName, ");
        sqlGetStockTransGoods.append("         g.unit_type goodsUnitType, ");
        sqlGetStockTransGoods.append("         std.goods_unit_type_name goodsUnitTypeName, ");
        sqlGetStockTransGoods.append("         std.amount_real amountReal ");
        sqlGetStockTransGoods.append("  FROM               stock_trans st ");
        sqlGetStockTransGoods.append("                   JOIN ");
        sqlGetStockTransGoods.append("                       stock_trans_detail std ");
        sqlGetStockTransGoods.append("                   ON std.stock_trans_id = st.stock_trans_id ");
        sqlGetStockTransGoods.append("               JOIN goods g ");
        sqlGetStockTransGoods.append("                   ON std.goods_id = g.goods_id");
        sqlGetStockTransGoods.append("               JOIN ");
        sqlGetStockTransGoods.append("                   stock a ");
        sqlGetStockTransGoods.append("               ON a.stock_id = st.owner_id ");
        sqlGetStockTransGoods.append("           LEFT JOIN  ");
        sqlGetStockTransGoods.append("               cms_owner.orders o ");
        sqlGetStockTransGoods.append("           ON o.order_id = st.order_id_list ");
        sqlGetStockTransGoods.append(" WHERE       st.stock_trans_code in ( :idx0 ) ");
        sqlGetStockTransGoods.append("GROUP BY   st.owner_id, ");
        sqlGetStockTransGoods.append("           st.order_id_list, ");
        sqlGetStockTransGoods.append("           o.receive_name, ");
        sqlGetStockTransGoods.append("           a.code, ");
        sqlGetStockTransGoods.append("           st.order_code, ");
        sqlGetStockTransGoods.append("           st.order_action_code, ");
        sqlGetStockTransGoods.append("           st.stock_trans_code, ");
        sqlGetStockTransGoods.append("           st.stock_trans_type, ");
        sqlGetStockTransGoods.append("           st.syn_trans_code, ");
        sqlGetStockTransGoods.append("           st.real_stock_trans_date, ");
        sqlGetStockTransGoods.append("           st.stock_trans_date, ");
        sqlGetStockTransGoods.append("           std.goods_code, ");
        sqlGetStockTransGoods.append("           std.goods_name, ");
        sqlGetStockTransGoods.append("           g.unit_type, ");
        sqlGetStockTransGoods.append("           std.goods_unit_type_name, ");
        sqlGetStockTransGoods.append("           std.amount_real, ");
        sqlGetStockTransGoods.append("           st.trans_user_name, ");
        sqlGetStockTransGoods.append("           st.notes, ");
        sqlGetStockTransGoods.append("           st.stock_trans_status ");
        sqlGetStockTransGoods.append("ORDER BY  st.stock_trans_date desc, stockTransCode, goodsName ");
        List lstParams = new ArrayList<>();
        if (lstStockTransCodes.contains(ParamUtils.SPLITTER)) {
            lstParams.add(lstStockTransCodes.split(ParamUtils.SPLITTER));
        } else {
            lstParams.add(lstStockTransCodes);
        }
        SQLQuery query = getSession().createSQLQuery(sqlGetStockTransGoods.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockTransGoodsDTO.class));
        query.addScalar("goodsName", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsUnitType", new StringType());
        query.addScalar("goodsUnitTypeName", new StringType());
        query.addScalar("amountReal", new DoubleType());
        query.addScalar("stockTransType", new StringType());
        query.addScalar("orderCode", new StringType());
        query.addScalar("orderActionCode", new StringType());
        query.addScalar("stockTransCode", new StringType());
        query.addScalar("synTransCode", new StringType());
        query.addScalar("realStockTransDate", new StringType());
        query.addScalar("stockTransDate", new StringType());
        query.addScalar("transUserName", new StringType());
        query.addScalar("notes", new StringType());
        query.addScalar("orderId", new StringType());
        query.addScalar("stockId", new StringType());
        query.addScalar("stockCode", new StringType());
        query.addScalar("receiveName", new StringType());
        query.addScalar("stockTransStatus", new StringType());

        for (int i = 0; i < lstParams.size(); i++) {
            if (lstParams.get(i) instanceof String[]) {
                query.setParameterList("idx" + String.valueOf(i), (Object[]) lstParams.get(i));
            } else {
                query.setParameter("idx" + String.valueOf(i), lstParams.get(i));
            }
        }

        return query.list();
    }
}
