/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.logistic.wms.dto.StockGoodsDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.StockGoods;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
 * @since 14-Apr-15 11:33 PM
 */
@Repository("stockGoodsDAO")
public class StockGoodsDAO extends BaseFWDAOImpl<StockGoods, Long> {

    private final String formatDate = "dd/mm/yyyy hh24:mi:ss";

    //Map<String,List<StockGoods>> mapStockgoods = new HashMap<>();
    public StockGoodsDAO() {
        this.model = new StockGoods();
    }

    public StockGoodsDAO(Session session) {
        this.session = session;
    }

//    //Cap nhat kho hang theo so luong
//    public ResultDTO exportStockGoods(List<ConditionBean> lstCondition, Double amount, Double amountIssue, String changeDate, Session session) {
//        //
//        ResultDTO resultDTO = new ResultDTO();
//        String message = ParamUtils.SUCCESS;
//        String key = "";
//        resultDTO.setId("");
//        Double amountUpdate;
//        Double amountIssueUpdate;
//        //Cap nhat chi tiet so luong
//        List<StockGoods> lstStockGoods = findSession(model.getModelName(), lstCondition, "", 0, 1, null, session);
//        if (lstStockGoods != null && lstStockGoods.size() > 0) {
//            //
//            resultDTO.setId(lstStockGoods.get(0).getId().toString());
//            //Set so luong thuc te, dap ung
//            amountUpdate = lstStockGoods.get(0).getAmount() + amount;
//            amountIssueUpdate = lstStockGoods.get(0).getAmountIssue() + amountIssue;
//            if (amountUpdate < 0 || amountIssueUpdate < 0) {
//                resultDTO.setMessage(ParamUtils.FAIL);
//                resultDTO.setKey(Constants.NOT_ENOUGH_AMOUNT);
//                return resultDTO;
//            }
//            lstStockGoods.get(0).setAmount(amountUpdate);
//            lstStockGoods.get(0).setAmountIssue(amountIssueUpdate);
//            try {
//                //Set ngay thay doi
//                lstStockGoods.get(0).setChangeDate(DateTimeUtils.convertStringToDate(changeDate));
//            } catch (Exception ex) {
//                Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            //Luu du lieu: trong cung session lay ban ghi ra de cap nhat thi phai dung save ko dung update
//            saveObjectSession(lstStockGoods.get(0), session);
//        } else { //Neu khong tim thay thi tra ve ma loi            
//            message = ParamUtils.FAIL;
//            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
//        }
//        //Tra ket qua
//        resultDTO.setKey(key);
//        resultDTO.setMessage(message);
//        return resultDTO;
//    }
    //
    //Add by ChuDV: 14/05/2015; Cap nhat xuat kho
    public ResultDTO importListStockGoods(List<StockGoodsDTO> lstStockGoods, Session session) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc = 0;
        try {
            //ChuDV Modify : 26/06/2015
            for (StockGoodsDTO i : lstStockGoods) {
                insertStockGoods(i.toModel(), session);
            }

        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }
        //
        resultDTO.setKey(key);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    //Add by ChuDV: 14/05/2015; Cap nhat xuat kho
    public ResultDTO importStockGoods(StockGoods stockGoods, Session session) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        try {
            //ChuDV Modify : 26/06/2015
            insertStockGoods(stockGoods, session);
            /*StringBuilder sql = new StringBuilder();
             List params = new ArrayList();
             sql.append(" UPDATE stock_goods ");
             sql.append(" SET amount = amount + ?,amount_issue = amount_issue + ?,");
             sql.append("     change_date = ? ");
             params.add(stockGoods.getAmount());
             params.add(stockGoods.getAmountIssue());
             params.add(stockGoods.getChangeDate());
             sql.append(" WHERE cust_id=? AND owner_id=? AND owner_type=? ");
             sql.append("   AND goods_id=? AND goods_state=? ");
             //
             params.add(stockGoods.getCustId());
             params.add(stockGoods.getOwnerId());
             params.add(stockGoods.getOwnerType());
             params.add(stockGoods.getGoodsId());
             params.add(stockGoods.getGoodsState());
             //Barcode
             if (!StringUtils.isStringNullOrEmpty(stockGoods.getBarcode())) {
             sql.append(" AND barcode=? ");
             params.add(stockGoods.getBarcode());
             }
             //So thung
             if (!StringUtils.isStringNullOrEmpty(stockGoods.getBincode())) {
             sql.append(" AND bincode=? ");
             params.add(stockGoods.getBincode());
             }
             //Vi tri
             if (!StringUtils.isStringNullOrEmpty(stockGoods.getCellCode())) {
             sql.append(" AND cell_code=? ");
             params.add(stockGoods.getCellCode());
             }
             //
             Query query = session.createSQLQuery(sql.toString());
             for (int idx = 0; idx < params.size(); idx++) {
             query.setParameter(idx, params.get(idx));
             }
             quantitySucc = query.executeUpdate();
             if (quantitySucc < 1) {
             //saveObjectSession(stockGoods, session);
             insertStockGoods(stockGoods, session);
             message = ParamUtils.SUCCESS;
             }*/
        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }
        //
        resultDTO.setKey(key);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    //
    private int insertStockGoods(StockGoods stockGoods, Session session) {
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        //
//        if (DataUtil.isStringNullOrEmpty(stockGoods.getOrderId())) {
//            sql.append("INSERT INTO stock_goods ");
//            sql.append("(id,cust_id,owner_id,owner_type,goods_id,goods_state,amount,amount_issue,change_date,import_date,barcode,bincode,add_infor,cell_code,partner_id, status, import_stock_trans_id) ");
//            sql.append(" VALUES (STOCK_GOODS_SEQ.nextval,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?,?,?,?,?,?,TO_NUMBER(?),?,? ) ");
//        } else {
        sql.append("INSERT INTO stock_goods ");
        sql.append("(id,cust_id,owner_id,owner_type,goods_id,goods_state,amount,amount_issue,change_date,import_date,barcode,bincode,add_infor,cell_code,partner_id, status, import_stock_trans_id, order_id) ");
        sql.append(" VALUES (STOCK_GOODS_SEQ.nextval,?,?,?,?,?,TO_NUMBER(?),TO_NUMBER(?),?,?,?,?,?,?,TO_NUMBER(?),?,?,? ) ");
//        }
        params.add(stockGoods.getCustId());
        params.add(stockGoods.getOwnerId());
        params.add(stockGoods.getOwnerType());
        params.add(stockGoods.getGoodsId());
        params.add(stockGoods.getGoodsState());
        params.add(stockGoods.getAmount());
        params.add(stockGoods.getAmountIssue());
        params.add(stockGoods.getChangeDate());
        params.add(stockGoods.getImportDate());
        params.add(stockGoods.getBarcode());
        params.add(stockGoods.getBincode());
        params.add(stockGoods.getAddInfor());
        params.add(stockGoods.getCellCode());
        params.add(stockGoods.getPartnerId());
        //
        params.add(DataUtil.nvl(stockGoods.getStatus(), ""));
        params.add(DataUtil.nvl(stockGoods.getImportStockTransId(), ""));
        params.add(DataUtil.nvl(stockGoods.getOrderId(), ""));

        Query query = session.createSQLQuery(sql.toString());
        for (int idx = 0; idx < params.size(); idx++) {
            query.setParameter(idx, params.get(idx));
        }
        return query.executeUpdate();
    }

    //
    //Add by ChuDV: 14/05/2015; Cap nhat xuat kho
    public ResultDTO exportStockGoods(StockGoods stockGoods, Session session) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc = 0;
        List<StockGoods> lstStockGoods;
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        Double amount = 0D;
        Double amountUpdate = 0D;
        Query query;
        try {
            //Lay danh sach hang trong kho
            StockGoods findStockGoods = new StockGoods();
            findStockGoods.setCustId(stockGoods.getCustId());
            findStockGoods.setOwnerId(stockGoods.getOwnerId());
            findStockGoods.setOwnerType(stockGoods.getOwnerType());
            findStockGoods.setGoodsId(stockGoods.getGoodsId());
            findStockGoods.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
            findStockGoods.setGoodsState(stockGoods.getGoodsState());
            findStockGoods.setCellCode(stockGoods.getCellCode());
            findStockGoods.setBarcode(stockGoods.getBarcode());
            //
            lstStockGoods = getListStockGoods(findStockGoods, session);
            if (lstStockGoods != null && lstStockGoods.size() > 0) {
                //Tong so so luong phai giam tru
                amountUpdate = stockGoods.getAmount();
                //StockGoods 
                StockGoods oneStockGoods;
                //tao list stockgoods da xuat tuong tung de cho vao map
                //List<StockGoods> lstExportedStockGoods = new ArrayList<>();
                //
                for (StockGoods oneStockGoods1 : lstStockGoods) {
//                    oneStockGoods = (StockGoods) DataUtil.cloneObject(oneStockGoods1);
                    //change by duyot 13/08: reason: khong su dng clone object
                    oneStockGoods = oneStockGoods1;
                    //
                    if (oneStockGoods.getAmount() < amountUpdate) {
                        amount = 0D;
                        amountUpdate = amountUpdate - oneStockGoods.getAmount();
                        oneStockGoods.setAmount(amount);
                        oneStockGoods.setAmountIssue(amount);
                        oneStockGoods.setChangeDate(stockGoods.getChangeDate());
                        resultDTO = updateStockGoods(oneStockGoods, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            return resultDTO;
                        }
                        //add to exported stock_goods
                        //lstExportedStockGoods.add(oneStockGoods);
                        //
                    } else {
                        amount = oneStockGoods.getAmount() - amountUpdate;
                        oneStockGoods.setAmount(amount);
                        oneStockGoods.setAmountIssue(amount);
                        oneStockGoods.setChangeDate(stockGoods.getChangeDate());
                        //
                        resultDTO = updateStockGoods(oneStockGoods, session);
                        //add to exported stock_goods
                        //lstExportedStockGoods.add(oneStockGoods);
                        //
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            return resultDTO;
                        }
                        amountUpdate = 0D;
                        break;
                    }
                }
                //So luong giam chua het
                if (amountUpdate > 0) {
//                    message = ParamUtils.FAIL;
                    // tiepnv6, edit 26/06/15: tra ve goods id loi
                    message = String.valueOf(stockGoods.getGoodsId()) + "," + stockGoods.getGoodsState();
                    key = ParamUtils.NOT_ENOUGH_AMOUNT;
                }
            } else {
                key = ParamUtils.NOT_EXIST_STOCK_GOODS;
//                message = ParamUtils.FAIL;
                // tiepnv6, edit 26/06/15: tra ve goods id loi
                message = String.valueOf(stockGoods.getGoodsId()) + "," + stockGoods.getGoodsState();
            }
        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_ERROR;
        }
        //
        resultDTO.setKey(key);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    /**
     * cap nhat hang hoa khong co serial ve trang thai cho` xuat kho
     *
     * @param stockGoods
     * @param session
     * @return
     */
    public ResultDTO waitExportStockGoods(StockGoods stockGoods, Session session) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String messRespon = "";
        String key = "";
        int quantitySucc = 0;
        List<StockGoods> lstStockGoods;
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        Double amount = 0D;
        Double amountUpdate = 0D;
        Query query;
        try {
            //Lay danh sach hang trong kho
            StockGoods findStockGoods = new StockGoods();
            findStockGoods.setCustId(stockGoods.getCustId());
            findStockGoods.setOwnerId(stockGoods.getOwnerId());
            findStockGoods.setOwnerType(stockGoods.getOwnerType());
            findStockGoods.setGoodsId(stockGoods.getGoodsId());
            findStockGoods.setGoodsState(stockGoods.getGoodsState());
            findStockGoods.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
            findStockGoods.setCellCode(stockGoods.getCellCode());
            findStockGoods.setBarcode(stockGoods.getBarcode());
            //
            lstStockGoods = getListStockGoods(findStockGoods, session);
            if (lstStockGoods != null && lstStockGoods.size() > 0) {
                //Tong so so luong phai giam tru
                amountUpdate = stockGoods.getAmount();
                //StockGoods 
//                StockGoods oneStockGoods;
                //tao list stockgoods da xuat tuong tung de cho vao map
                //List<StockGoods> lstExportedStockGoods = new ArrayList<>();
                //
                for (StockGoods oneStockGoods : lstStockGoods) {
//                    oneStockGoods = (StockGoods) DataUtil.cloneObject(oneStockGoods1);
                    //change by duyot 13/08: reason: khong su dng clone object
//                    oneStockGoods = oneStockGoods1;
                    //
                    if (oneStockGoods.getAmount() < amountUpdate) { // cap nhat ve trang thai 2: cho xuat kho
//                        amount = 0D;
                        amountUpdate = amountUpdate - oneStockGoods.getAmount();
//                        oneStockGoods.setAmount(amount);
//                        oneStockGoods.setAmountIssue(amount);
                        oneStockGoods.setChangeDate(stockGoods.getChangeDate());
                        oneStockGoods.setStatus(stockGoods.getStatus());
                        oneStockGoods.setOrderId(stockGoods.getOrderId());
                        messRespon = updateSession(oneStockGoods, session);
//                        resultDTO = updateStockGoods(oneStockGoods, session);
                        if (!messRespon.equals(ParamUtils.SUCCESS)) {
                            message = ParamUtils.FAIL;
                            key = ParamUtils.SYSTEM_ERROR;
                            resultDTO.setKey(key);
                            resultDTO.setMessage(message);
                            return resultDTO;
                        }
                        //add to exported stock_goods
                        //lstExportedStockGoods.add(oneStockGoods);
                        //
                    } else {
                        amount = oneStockGoods.getAmount() - amountUpdate;
                        oneStockGoods.setAmount(amount);
                        oneStockGoods.setAmountIssue(amount);
                        oneStockGoods.setChangeDate(stockGoods.getChangeDate());
                        //
                        resultDTO = updateStockGoods(oneStockGoods, session);
                        //add to exported stock_goods
                        //lstExportedStockGoods.add(oneStockGoods);
                        //
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            return resultDTO;
                        }
                        StockGoods stockGoodsInsertNew = (StockGoods) DataUtil.cloneObject(oneStockGoods);
                        stockGoodsInsertNew.setId(null);
                        stockGoodsInsertNew.setAmount(amountUpdate);
                        stockGoodsInsertNew.setAmountIssue(amountUpdate);
                        stockGoodsInsertNew.setStatus(stockGoods.getStatus());
                        stockGoodsInsertNew.setOrderId(stockGoods.getOrderId());
                        stockGoodsInsertNew.setChangeDate(stockGoods.getChangeDate());
                        messRespon = saveObjectSession(stockGoodsInsertNew, session);
                        Long.parseLong(messRespon);
                        amountUpdate = 0D;
                        break;
                    }
                }
                //So luong giam chua het
                if (amountUpdate > 0) {
//                    message = ParamUtils.FAIL;
                    // tiepnv6, edit 26/06/15: tra ve goods id loi
                    message = String.valueOf(stockGoods.getGoodsId()) + "," + stockGoods.getGoodsState();
                    key = ParamUtils.NOT_ENOUGH_AMOUNT;
                }
            } else {
                key = ParamUtils.NOT_EXIST_STOCK_GOODS;
//                message = ParamUtils.FAIL;
                // tiepnv6, edit 26/06/15: tra ve goods id loi
                message = String.valueOf(stockGoods.getGoodsId()) + "," + stockGoods.getGoodsState();
            }
        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_ERROR;
        }
        //
        resultDTO.setKey(key);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    //ham xuat cho giao dich dieu chuyen tra ve list stock_goods da chuyen
    public List<StockGoods> exportStockGoodsForTransfer(StockGoods stockGoods, Session session) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc = 0;
        List<StockGoods> lstStockGoods = new ArrayList<>();
        List<StockGoods> lstExportedStockGoods = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        Double amount = 0D;
        Double amountUpdate = 0D;
        Query query;
        try {
            //Lay danh sach hang trong kho
            StockGoods findStockGoods = new StockGoods();
            findStockGoods.setCustId(stockGoods.getCustId());
            findStockGoods.setOwnerId(stockGoods.getOwnerId());
            findStockGoods.setOwnerType(stockGoods.getOwnerType());
            findStockGoods.setGoodsId(stockGoods.getGoodsId());
            findStockGoods.setGoodsState(stockGoods.getGoodsState());
            findStockGoods.setCellCode(stockGoods.getCellCode());
            findStockGoods.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
            findStockGoods.setBarcode(stockGoods.getBarcode());
            //
            lstStockGoods = getListStockGoods(findStockGoods, session);
            if (lstStockGoods != null && lstStockGoods.size() > 0) {
                //Tong so so luong phai giam tru
                amountUpdate = stockGoods.getAmount();
                //StockGoods 
                //tao list stockgoods da xuat tuong tung de cho vao map

                //
                for (StockGoods i : lstStockGoods) {
//                  oneStockGoods = (StockGoods) DataUtil.cloneObject(oneStockGoods1);
                    //duyot - 13/08: sua doi bo clone object
                    //oneStockGoods = oneStockGoods1;
                    StockGoods sockGoodsForAdd = (StockGoods) DataUtil.cloneObject(i);
                    //
                    if (i.getAmount() < amountUpdate) {
                        amount = 0D;
                        amountUpdate = amountUpdate - i.getAmount();
                        i.setAmount(amount);
                        i.setAmountIssue(amount);
                        i.setChangeDate(stockGoods.getChangeDate());
                        resultDTO = updateStockGoods(i, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            return null;
                        }
                        //add to exported stock_goods
                        lstExportedStockGoods.add(sockGoodsForAdd);
                        //
                    } else {
                        amount = i.getAmount() - amountUpdate;
                        i.setAmount(amount);
                        i.setAmountIssue(amount);
                        i.setChangeDate(stockGoods.getChangeDate());
                        //
                        resultDTO = updateStockGoods(i, session);
                        //add to exported stock_goods
                        lstExportedStockGoods.add(sockGoodsForAdd);
                        //
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            return null;
                        }
                        amountUpdate = 0D;
                        break;
                    }
                }
                //
                //So luong giam chua het
                if (amountUpdate > 0) {
//                    message = ParamUtils.FAIL;
                    // tiepnv6, edit 26/06/15: tra ve goods id loi
//                    message = String.valueOf(stockGoods.getGoodsId()) + "," + stockGoods.getGoodsState();
//                    key = ParamUtils.NOT_ENOUGH_AMOUNT;
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_ERROR;
            return null;
        }
        return lstExportedStockGoods;
        //
//        resultDTO.setKey(key);
//        resultDTO.setMessage(message);
//        return resultDTO;
    }

    //Add by ChuDV: 23/06/2015; Cap nhat kho hang tru so luong
    public ResultDTO updateStockGoods(StockGoods stockGoods, Session session) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        int quantitySucc = 0;
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();

        Query query;
        try {
            sql.append(" UPDATE stock_goods ");
            sql.append(" SET amount = ?,amount_issue = ?,");
            sql.append("     change_date = ? ");
            //
            params.add(stockGoods.getAmount());
            params.add(stockGoods.getAmountIssue());
            params.add(stockGoods.getChangeDate());
            //
            sql.append(" WHERE amount_issue > 0 AND id=? AND cust_id=? AND owner_id=? AND owner_type=? ");
            sql.append("   AND goods_id=? AND goods_state=? ");
            //
            params.add(stockGoods.getId());
            params.add(stockGoods.getCustId());
            params.add(stockGoods.getOwnerId());
            params.add(stockGoods.getOwnerType());
            params.add(stockGoods.getGoodsId());
            params.add(stockGoods.getGoodsState());
            //
            query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            quantitySucc = query.executeUpdate();
            if (quantitySucc != 1) {
                key = ParamUtils.NOT_EXIST_OR_ONE_MORE_STOCK_GOODS;
//                message = ParamUtils.FAIL;
                // tiepnv6, edit 26/06/15: tra ve goods id loi
                message = String.valueOf(stockGoods.getGoodsId()) + "," + stockGoods.getGoodsState();
            }

        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_ERROR;
        }
        //
        resultDTO.setKey(key);
        resultDTO.setMessage(message);
        //
        return resultDTO;
    }

    //
    //Cap nhat cell
    public ResultDTO updateCellStockGoods(StockTransInforDTO stockTransInforDTO, Session session) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        int quantitySucc = 0;
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" UPDATE stock_goods ");
            sql.append(" SET cell_code = ? ");
            sql.append(" WHERE cust_id=? AND owner_id=? AND owner_type=? ");
            sql.append("   AND goods_id=? AND goods_state=? ");
            params.add(stockTransInforDTO.getCellCode());
            params.add(stockTransInforDTO.getCustId());
            params.add(stockTransInforDTO.getOwnerId());
            params.add(stockTransInforDTO.getOwnerType());
            params.add(stockTransInforDTO.getGoodsId());
            params.add(stockTransInforDTO.getGoodsState());
            if (!StringUtils.isNullOrEmpty(stockTransInforDTO.getBarcode())) {
                sql.append("   AND barcode=? ");
                params.add(stockTransInforDTO.getBarcode());
            } else {
                sql.append("   AND barcode IS NULL ");
            }
            //
            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }
            quantitySucc = query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
        }
        //
        resultDTO.setMessage(message);
        resultDTO.setQuantitySucc(quantitySucc);
        return resultDTO;
    }

    // QuyenDM 15/06/2015: Cap nhat cell voi tham so truyen vao la ChangePositionDTO
    public Map<ChangePositionDTO, ResultDTO> updateCellStockGoods(ChangePositionDTO changePosition, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        Map<ChangePositionDTO, ResultDTO> mapChangePosition2ResultDTO = new HashMap<>();

        int quantitySucc = 0;
        try {
            //Lay tat ca du lieu phu hop voi doi tuong nhap vao de lay tat ca cac ban ghi co the cap nhat
            StockGoodsDTO stockGoodsDTO = new StockGoodsDTO();
            stockGoodsDTO.setGoodsId(changePosition.getGoodsId());
            stockGoodsDTO.setCustId(changePosition.getCustomerId());
            stockGoodsDTO.setGoodsState(changePosition.getGoodsState());
            stockGoodsDTO.setCellCode(changePosition.getCellCodeOld());
            stockGoodsDTO.setOwnerId(changePosition.getStockId());
            stockGoodsDTO.setOwnerType(changePosition.getOwnerType());
            stockGoodsDTO.setStatus("1"); //Chi thuc hien dieu chuyen doi voi hang trong kho           
            //Neu co barcode thi them vao dieu kien tim kiem
            if (!DataUtil.isStringNullOrEmpty(changePosition.getBarcode())) {
                stockGoodsDTO.setBarcode(changePosition.getBarcode());
            }
            List<StockGoods> listUpdates = getListStockGoods(stockGoodsDTO.toModel(), session);

            Double amountTotal = 0D;
            Double soluongNhapvao = Double.parseDouble(changePosition.getQuantity());
            if (listUpdates == null || listUpdates.isEmpty()) {
                message = ParamUtils.FAIL;
                resultDTO.setKey(ParamUtils.NOT_FOUND_DATA);
                changePosition.setErrorInfor(ParamUtils.NOT_FOUND_DATA);
                resultDTO.setMessage(message);
                resultDTO.setQuantitySucc(quantitySucc);
                mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                return mapChangePosition2ResultDTO;
            } else {
//                List<StockGoods> listUpdates = new ArrayList<>();
                //Tinh tong so hang co trong kho
                for (StockGoods sg : listUpdates) {
//                    if (amountTotal < soluongNhapvao) {
                    amountTotal += sg.getAmount();
//                        listUpdates.add(sg);
//                    }
                }
                //Neu so luong co trong kho < so luong nhap vao de chuyen thi khong duoc phep chuyen
                if (amountTotal < soluongNhapvao) {
                    message = ParamUtils.FAIL;
                    resultDTO.setKey(ParamUtils.NOT_ENOUGH_AMOUNT);
                    resultDTO.setMessage(message);
                    resultDTO.setQuantitySucc(quantitySucc);
                    changePosition.setErrorInfor(ParamUtils.NOT_ENOUGH_AMOUNT);
                    mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                    return mapChangePosition2ResultDTO;
                } else if (Objects.equals(amountTotal, soluongNhapvao)) {
                    //Neu so luong co trong kho = so luong nhap vao de chuyen vi tri --> cap nhat tat ca                    
                    int soluongBanghiCapnhat = listUpdates.size();
                    //Neu chi co 1 ban ghi --> cap nhat vi tri cho ban ghi day
                    if (soluongBanghiCapnhat == 1) {
                        StockGoods stockGoodsUpdate = listUpdates.get(0);
                        stockGoodsUpdate.setCellCode(changePosition.getCellCodeNew());
                        if (!updateSession(stockGoodsUpdate, session).equalsIgnoreCase(ParamUtils.SUCCESS)) {
                            message = ParamUtils.FAIL;
                            resultDTO.setMessage(message);
                            resultDTO.setQuantitySucc(quantitySucc);
                            changePosition.setErrorInfor(ParamUtils.FAIL);
                            mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                            return mapChangePosition2ResultDTO;
                        }
                    } else {//Neu co nhieu hon 1 ban ghi --> cap nhat ban ghi cuoi cung sang vi tri moi
                        StockGoods stockGoodsUpdate = listUpdates.get(soluongBanghiCapnhat - 1);
                        stockGoodsUpdate.setCellCode(changePosition.getCellCodeNew());
                        stockGoodsUpdate.setAmount(soluongNhapvao);
                        stockGoodsUpdate.setAmountIssue(soluongNhapvao);
                        if (!updateSession(stockGoodsUpdate, session).equalsIgnoreCase(ParamUtils.SUCCESS)) {
                            message = ParamUtils.FAIL;
                            resultDTO.setMessage(message);
                            resultDTO.setQuantitySucc(quantitySucc);
                            changePosition.setErrorInfor(ParamUtils.FAIL);
                            mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                            return mapChangePosition2ResultDTO;
                        } else {
                            //Cac ban ghi con lai cap nhat so luong = 0                                 
                            for (int i = 0; i <= soluongBanghiCapnhat - 2; i++) {
                                listUpdates.get(i).setAmount(0D);
                                listUpdates.get(i).setAmountIssue(0D);
                                //Neu 1 ban ghi cap nhat that bai thi rollback
                                if (!updateSession(listUpdates.get(i), session).equalsIgnoreCase(ParamUtils.SUCCESS)) {
                                    message = ParamUtils.FAIL;
                                    resultDTO.setMessage(message);
                                    resultDTO.setQuantitySucc(quantitySucc);
                                    changePosition.setErrorInfor(ParamUtils.FAIL);
                                    mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                                    return mapChangePosition2ResultDTO;
                                }
                            }
                        }
                    }
                } else if (amountTotal > soluongNhapvao) {
                    //Neu so luong co trong kho > so luong nhap vao de chuyen vi tri
                    int soluongBanghiCapnhat = listUpdates.size();
                    if (soluongBanghiCapnhat == 1) {
                        //Neu chi co 1 ban ghi
                        //Tao 1 ban ghi moi voi so luong = so luong trong kho - so luong nhap vao
                        StockGoods stockGoodsInsert = (StockGoods) DataUtil.cloneObject(listUpdates.get(0));
                        stockGoodsInsert.setId(null);
                        stockGoodsInsert.setAmount(amountTotal - soluongNhapvao);
                        stockGoodsInsert.setAmountIssue(amountTotal - soluongNhapvao);
                        //Ban ghi da co cap nhat so luong = so luong nhap vao, ma vung = ma vung moi
                        StockGoods stockGoodsUpdate = listUpdates.get(0);
                        stockGoodsUpdate.setCellCode(changePosition.getCellCodeNew());
                        stockGoodsUpdate.setAmount(soluongNhapvao);
                        stockGoodsUpdate.setAmountIssue(soluongNhapvao);
                        //Thuc hien cap nhat vi tri cho ban ghi day voi so luong = so luong nhap vao, ma vung moi
                        if (!updateSession(stockGoodsUpdate, session).equalsIgnoreCase(ParamUtils.SUCCESS)) {
                            message = ParamUtils.FAIL;
                            resultDTO.setMessage(message);
                            resultDTO.setQuantitySucc(quantitySucc);
                            changePosition.setErrorInfor(ParamUtils.FAIL);
                            mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                            return mapChangePosition2ResultDTO;
                        } else//Dong thoi them 1 ban ghi moi voi so luong = so luong trong kho - so luong nhap vao                            
                        if (!StringUtils.isInteger(saveObjectSession(stockGoodsInsert, session))) {
                            message = ParamUtils.FAIL;
                            resultDTO.setMessage(message);
                            resultDTO.setQuantitySucc(quantitySucc);
                            changePosition.setErrorInfor(ParamUtils.FAIL);
                            mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                            return mapChangePosition2ResultDTO;
                        }
                    } else {//Neu co nhieu hon 1 ban ghi --> Cap nhat ban ghi cuoi cung sang vi tri moi voi so luong = so luong nhap vao
                        StockGoods stockGoodsUpdate = listUpdates.get(soluongBanghiCapnhat - 1);
                        stockGoodsUpdate.setCellCode(changePosition.getCellCodeNew());
                        stockGoodsUpdate.setAmount(soluongNhapvao);
                        stockGoodsUpdate.setAmountIssue(soluongNhapvao);
                        //Ban ghi truoc ban ghi cuoi cung se cap nhat so luong = so luong trong kho - so luong nhap vao
                        StockGoods stockGoodsUpdateOnlyAmount = listUpdates.get(soluongBanghiCapnhat - 2);
                        stockGoodsUpdateOnlyAmount.setAmount(amountTotal - soluongNhapvao);
                        stockGoodsUpdateOnlyAmount.setAmountIssue(amountTotal - soluongNhapvao);
                        //Thuc hien cap nhat ban ghi cuoi cung sang vi tri moi voi so luong = so luong nhap vao
                        if (!updateSession(stockGoodsUpdate, session).equalsIgnoreCase(ParamUtils.SUCCESS)) {
                            message = ParamUtils.FAIL;
                            resultDTO.setMessage(message);
                            resultDTO.setQuantitySucc(quantitySucc);
                            changePosition.setErrorInfor(ParamUtils.FAIL);
                            mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                            return mapChangePosition2ResultDTO;
                        } else {
                            //Thuc hien cap nhat ban ghi truoc ban ghi cuoi cung se cap nhat so luong = so luong trong kho - so luong nhap vao
                            if (!updateSession(stockGoodsUpdateOnlyAmount, session).equalsIgnoreCase(ParamUtils.SUCCESS)) {
                                message = ParamUtils.FAIL;
                                resultDTO.setMessage(message);
                                resultDTO.setQuantitySucc(quantitySucc);
                                changePosition.setErrorInfor(ParamUtils.FAIL);
                                mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                                return mapChangePosition2ResultDTO;
                            }
                            //Neu so luong ban ghi cap nhat >2 thi tat cac cac ban ghi con lai se duoc xoa di
                            //Vi neu cap nhat so luong - 0 se gay hieu nham rang mat hang day da duoc xuat kho.                            
                            if (soluongBanghiCapnhat > 2) {
                                //Cac ban ghi con lai cap nhat so luong = 0                            
                                for (int i = 0; i <= soluongBanghiCapnhat - 3; i++) {
                                    listUpdates.get(i).setAmount(0D);
                                    listUpdates.get(i).setAmountIssue(0D);
                                    //Neu 1 ban ghi cap nhat that bai thi rollback
                                    if (!deleteObject(listUpdates.get(i), session).equalsIgnoreCase(ParamUtils.SUCCESS)) {
                                        message = ParamUtils.FAIL;
                                        resultDTO.setMessage(message);
                                        resultDTO.setQuantitySucc(quantitySucc);
                                        changePosition.setErrorInfor(ParamUtils.FAIL);
                                        mapChangePosition2ResultDTO.put(changePosition, resultDTO);
                                        return mapChangePosition2ResultDTO;
                                    }
                                }
                            }
                        }
                    }
                }
                //So luong cac ban ghi cap nhat thanh cong
                quantitySucc = listUpdates.size();
            }

        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class.getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            resultDTO.setMessage(message);
            resultDTO.setQuantitySucc(quantitySucc);
            changePosition.setErrorInfor("Cap nhat hang hoa that bai!");
            mapChangePosition2ResultDTO.put(changePosition, resultDTO);
            return mapChangePosition2ResultDTO;
        }

        //
        resultDTO.setMessage(message);
        resultDTO.setQuantitySucc(quantitySucc);
        mapChangePosition2ResultDTO.put(changePosition, resultDTO);
        return mapChangePosition2ResultDTO;
    }

    //Lay danh danh sach kho theo KH, kho, mat hang, trang thai hang
    public List<StockGoodsInforDTO> getSumListStockGoods(StockGoodsInforDTO stockGoodsInforDTO) {
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

        // if (!StringUtils.isStringNullOrEmpty(stockGoodsInforDTO.getStaffId())) {
        // sql.append("   ");
        //}
        sql.append(" WHERE   s.stock_id = sg.owner_id");
        sql.append("         AND g.goods_id = sg.goods_id");
        sql.append("         AND sg.goods_id = msg.goods_id(+)");
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
            sql.append("      AND g.goods_id = ?");
            lstParams.add(stockGoodsInforDTO.getGoodsId());
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

//Tim kiem du lieu kho hang
    public List<StockGoods> getListStockGoods(StockGoods stockGoods, Session session) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();
        //
        sql.append(" from StockGoods sg WHERE sg.amountIssue > 0 ");

        if (!StringUtils.isLongNullOrEmpty(stockGoods.getCustId())) {
            sql.append("AND sg.custId = ? ");
            lstParams.add(stockGoods.getCustId());
        }
        //Tim kiem theo kho hang
        if (!StringUtils.isLongNullOrEmpty(stockGoods.getOwnerId())) {
            sql.append("AND sg.ownerId = ? ");
            lstParams.add(stockGoods.getOwnerId());
        }
        //Tim kiem theo loai kho hang
        if (!StringUtils.isStringNullOrEmpty(stockGoods.getOwnerType())) {
            sql.append("AND sg.ownerType = ? ");
            lstParams.add(stockGoods.getOwnerType());
        }
        //Tim kien theo mat hang
        if (!StringUtils.isLongNullOrEmpty(stockGoods.getGoodsId())) {
            sql.append("AND sg.goodsId = ? ");
            lstParams.add(stockGoods.getGoodsId());
        }
        //Tim theo tinh trang hang hoa        
        if (!StringUtils.isStringNullOrEmpty(stockGoods.getGoodsState())) {
            sql.append("AND sg.goodsState = ? ");
            lstParams.add(stockGoods.getGoodsState());
        }
        //Tim theo cell ID                 
        if (!StringUtils.isNullOrEmpty(stockGoods.getCellCode())) {
            sql.append("AND sg.cellCode = ? ");
            lstParams.add(stockGoods.getCellCode());
        }
        //Tim theo Barcode         
        if (!StringUtils.isStringNullOrEmpty(stockGoods.getBarcode())) {
            sql.append("AND sg.barcode = ? ");
            lstParams.add(stockGoods.getBarcode());
        }
        //Tim theo Bincode         
        if (!StringUtils.isStringNullOrEmpty(stockGoods.getBincode())) {
            sql.append("AND sg.bincode = ? ");
            lstParams.add(stockGoods.getBincode());
        }
        //Tim theo Bincode         
        if (!StringUtils.isStringNullOrEmpty(stockGoods.getStatus())) {
            sql.append("AND sg.status = ? ");
            lstParams.add(stockGoods.getStatus());
        }
        //QuyenDM 13/08/2015 them dieu kien sap xep theo so luong
        sql.append(" ORDER BY importDate, cellCode, amount");
        //
//        Query query = getSession().createQuery(sql.toString());
        Query query = session.createQuery(sql.toString()); // edit 14:16 15/07/2015
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        //
        return query.list();
    }

    //Add by ThienNG1: 02/06/2015;
    public List<StockGoodsInforDTO> getSumListStockGoodsExportExcel(StockGoodsSerialDTO stockGoodsSerialDTO, StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List lstParams = new ArrayList();
        //        
        StringBuilder sql = new StringBuilder();
        //

        //Tim kiem serial don le theo trang thai
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialDTO.getStatus())) {
            sql.append("         AND sgs.status = ?");
            lstParams.add(stockGoodsSerialDTO.getStatus());
        }
        //Tim kiem serial dai theo trang thai
        if (!StringUtils.isStringNullOrEmpty(stockGoodsSerialStripDTO.getStatus())) {
            sql.append("         AND sgst.status = ?");
            lstParams.add(stockGoodsSerialStripDTO.getStatus());
        }

        //Map DTO
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query
                .setResultTransformer(Transformers.aliasToBean(StockGoodsInforDTO.class
                        ));
        query.addScalar(
                "zoneName", new StringType());
        query.addScalar(
                "goodsType", new StringType());
        query.addScalar(
                "goodsCode", new StringType());
        query.addScalar(
                "goodsName", new StringType());
        query.addScalar(
                "amount", new StringType());
        //Set cac gia tri tham so
        for (int i = 0;
                i < lstParams.size();
                i++) {
            query.setParameter(i, lstParams.get(i));
        }

        //
        return query.list();
    }

//duyot - dieu chuyen hang hoa - cap nhat stock_goods
    public ResultDTO updateNewGoods(StockGoodsDTO importedStockGoods, StockGoodsDTO exportedStockGoods) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        int quantitySucc = 0;
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();

            sql.append(" UPDATE stock_goods "
                    + " SET "
                    + "    goods_id = ?, "
                    + "    goods_state = ?, "
                    + "    amount = ?,"
                    + "    amount_issue = ?, "
                    + "    change_date  = SYSDATE "
                    + " WHERE "
                    + "    goods_id = ? AND goods_state = ?  ");
            //add params
            params.add(importedStockGoods.getGoodsId());
            params.add(importedStockGoods.getGoodsState());
            params.add(importedStockGoods.getAmount());
            params.add(importedStockGoods.getAmountIssue());
            params.add(exportedStockGoods.getGoodsId());
            params.add(exportedStockGoods.getGoodsState());
            //them dieu kien cell_code
            if (!StringUtils.isStringNullOrEmpty(exportedStockGoods.getCellCode())) {
                sql.append("     AND cell_code = ? ");
                params.add(exportedStockGoods.getCellCode());
            }
            //them dieu kien bin_code
            if (!StringUtils.isStringNullOrEmpty(exportedStockGoods.getBincode())) {
                sql.append("     AND bincode = ? ");
                params.add(exportedStockGoods.getBincode());
            }

            //
            Query query = session.createSQLQuery(sql.toString());
            for (int idx = 0; idx < params.size(); idx++) {
                query.setParameter(idx, params.get(idx));
            }

            quantitySucc = query.executeUpdate();
            if (quantitySucc > 1) {
                message = ParamUtils.FAIL;
                resultDTO.setKey(ParamUtils.SYSTEM_ERROR);

            }

        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;

            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
        }
//
        resultDTO.setMessage(message);
        resultDTO.setQuantitySucc(quantitySucc);

        return resultDTO;
    }

    //THienNG1 03/08/2015
    public ResultDTO reImportStockGoods(StockGoods stockGoods, Session session, String synImportRevoke) {
        //
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        try {
            //ChuDV Modify : 26/06/2015
            insertStockGoods(stockGoods, session);

        } catch (Exception ex) {
            Logger.getLogger(StockGoodsDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            message = ParamUtils.FAIL;
            key = ParamUtils.SYSTEM_OR_DATA_ERROR;
        }
//
        resultDTO.setKey(key);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    //init key from stock_goods
    public String initKey(StockGoods stockgoods) {
        StringBuilder strKey = new StringBuilder("");
        strKey.append(stockgoods.getGoodsId());
        strKey.append(stockgoods.getGoodsState());
        if (!StringUtils.isStringNullOrEmpty(stockgoods.getBarcode())) {
            strKey.append(stockgoods.getBarcode());
        } else {

        }
        if (!StringUtils.isStringNullOrEmpty(stockgoods.getCellCode())) {
            strKey.append(stockgoods.getCellCode());
        }

        return strKey.toString();
    }

    /**
     *
     * @param stockGoodsDTO
     * @param session
     * @return
     */
    public String updateStockGoodsByOrdersId(StockGoodsDTO stockGoodsDTO, Session session) {
        String message = ParamUtils.SUCCESS;
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        int quantitySucc = 0;
        Query query;
        try {
            sql.append(" UPDATE STOCK_GOODS ");
            sql.append(" SET STATUS = ?, CHANGE_DATE = TO_DATE(?,'dd/MM/yyyy hh24:mi:ss') ");
            params.add(stockGoodsDTO.getStatus());
            params.add(stockGoodsDTO.getChangeDate());
            sql.append(" WHERE ORDER_ID = ? AND STATUS = ?");
            params.add(stockGoodsDTO.getOrderId());
            params.add(stockGoodsDTO.getOldStatus());
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

    //thienng1 - 19/04/2016
    //Ham kiem ke hang hoa khong serial
    public List<StockGoodsSerialInforDTO> getStockGoodsInforInventory(StockGoodsSerialInforDTO inforDTO,
            String isSerial, String isExportSerial) {
        if ("1".equals(isSerial)) {
            if ("1".equals(isExportSerial)) {
                return getStockGoodsSerialForInventory(inforDTO);
            } else {
                return getStockGoodsSerialForInventoryNotSerial(inforDTO);
            }
        } else {
            return getStockGoodsForInventory(inforDTO);
        }
    }

    public List<StockGoodsSerialInforDTO> getStockGoodsForInventory(StockGoodsSerialInforDTO inforDTO) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();
        sql.append("SELECT   NVL (a.goodsid, b.goodsid) goodsId,");
        sql.append("         NVL (a.goodsCode, b.goodsCode) goodsCode,");
        sql.append("         NVL (a.goodsName, b.goodsName) goodsName,");
        //sql.append("         NVL (a.goodsstate, b.goodsstate) goodsState,");
        sql.append("         NVL (a.goodsUnitType, b.goodsUnitType) goodsUnitType,");
        sql.append("         NVL (a.amount, 0) amount,");
        sql.append("         NVL (b.amount, 0) amountInventory,");
        sql.append("         NVL (b.amount, 0) - NVL (a.amount, 0) AS amountFalse");
        sql.append("  FROM       (  SELECT   sg.goods_id goodsid,");
        //sql.append("                         sg.goods_state goodsstate,");
        sql.append("                         g.code goodsCode,");
        sql.append("                         g.name goodsName,");
        sql.append("                         g.unit_Type goodsUnitType,");
        sql.append("                         SUM (sg.amount) amount");
        sql.append("                  FROM   stock_goods sg, goods g");
        sql.append("                 WHERE   sg.goods_id =  g.goods_id ");
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getCustId())) {
            sql.append("    AND sg.cust_id = ? ");
            lstParams.add(inforDTO.getCustId());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getOwnerId())) {
            sql.append("    AND sg.owner_id = ? ");
            lstParams.add(inforDTO.getOwnerId());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getGoodsCode())) {
            sql.append("    AND g.code IN ( ");
            sql.append(inforDTO.getGoodsCode());
            sql.append(" ) ");
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getStatus())) {
            sql.append("    AND sg.status IN ( ");
            sql.append(inforDTO.getStatus());
            sql.append(" ) "); 
        }
        sql.append("         GROUP BY   sg.goods_id, g.code, g.name, g.unit_Type) a");
        sql.append("         FULL JOIN");
        sql.append("             (  SELECT   sg.goods_id goodsid,");
        //sql.append("                         sg.goods_state goodsstate,");
        sql.append("                         g.code goodsCode,");
        sql.append("                         g.name goodsName,");
        sql.append("                         g.unit_Type goodsUnitType,");
        sql.append("                         SUM (sg.amount) amount");
        sql.append("                  FROM   stock_goods sg, goods g");
        sql.append("                 WHERE   sg.goods_id =  g.goods_id ");
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getCustIdInventory())) {
            sql.append("   AND sg.cust_id = ? ");
            lstParams.add(inforDTO.getCustIdInventory());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getOwnerIdInventory())) {
            sql.append("    AND sg.owner_id = ? ");
            lstParams.add(inforDTO.getOwnerIdInventory());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getGoodsCode())) {
            sql.append("    AND g.code IN ( ");
            sql.append(inforDTO.getGoodsCode());
            sql.append(" ) ");
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getStatus())) {
            sql.append("    AND sg.status IN ( ");
            sql.append(inforDTO.getStatus());
            sql.append(" ) ");
        }
        sql.append("              GROUP BY   sg.goods_id, g.code, g.name, g.unit_Type) b");
        sql.append("         ON NVL (a.goodsCode, 'ABC') = NVL (b.goodsCode, 'ABC')");
        //sql.append("         AND NVL (a.goodsstate,'1') =  NVL (b.goodsstate,'1')");
        sql.append("         ORDER BY goodsId");
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockGoodsSerialInforDTO.class));

        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        //query.addScalar("goodsState", new StringType());
        query.addScalar("goodsUnitType", new StringType());
        query.addScalar("amount", new StringType());
        query.addScalar("amountInventory", new StringType());
        query.addScalar("amountFalse", new StringType());

        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        return query.list();
    }

    public List<StockGoodsSerialInforDTO> getStockGoodsSerialForInventory(StockGoodsSerialInforDTO inforDTO) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();
        sql.append(" SELECT   NVL (a.goodsid, b.goodsid) goodsId,");
        sql.append("         NVL (a.goodsCode, b.goodsCode) goodsCode,");
        sql.append("         NVL (a.goodsName, b.goodsName) goodsName,");
        //sql.append("         NVL (a.goodsState, b.goodsState) goodsState,");
        sql.append("         NVL (a.goodsUnitType, b.goodsUnitType) goodsUnitType,");
        sql.append("         NVL (a.serial, b.serial) fromSerial,");
        sql.append("         NVL (a.serial, b.serial) toSerial,");
        sql.append("         NVL (a.amount, 0) amount,");
        sql.append("         NVL (b.amount, 0) amountInventory,");
        sql.append("         NVL (b.amount, 0) - NVL (a.amount, 0) AS amountFalse");
        sql.append("  FROM       (  SELECT   sgs.goods_id goodsid,");
        sql.append("                         g.code goodsCode,");
        sql.append("                         g.name goodsName,");
        sql.append("                         g.unit_Type goodsUnitType,");
        //sql.append("                         sgs.goods_state goodsstate,");
        sql.append("                         UPPER(sgs.serial) serial,");
        sql.append("                         TO_NUMBER(1) amount");
        sql.append("                  FROM   stock_goods_serial sgs, goods g");
        sql.append("                 WHERE    sgs .goods_id = g.goods_id ");
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getCustId())) {
            sql.append("   AND sgs.cust_id = ? ");
            lstParams.add(inforDTO.getCustId());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getOwnerId())) {
            sql.append("    AND sgs.owner_id = ? ");
            lstParams.add(inforDTO.getOwnerId());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getGoodsCode())) {
            sql.append("    AND g.code IN ( ");
            sql.append(inforDTO.getGoodsCode());
            sql.append(" ) ");
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getStatus())) {
            sql.append("    AND sgs.status IN ( ");
            sql.append(inforDTO.getStatus());
            sql.append(" ) ");
        }
        sql.append("         ) a ");
        sql.append("         FULL JOIN");
        sql.append("             (  SELECT   sgs.goods_id goodsid,");
        sql.append("                         g.code goodsCode,");
        sql.append("                         g.name goodsName,");
        sql.append("                         g.unit_Type goodsUnitType,");
        //sql.append("                         sgs.goods_state goodsstate,");
        sql.append("                         UPPER(sgs.serial) serial,");
        sql.append("                         TO_NUMBER(1) amount");
        sql.append("                  FROM   stock_goods_serial sgs, goods g");
        sql.append("                 WHERE   sgs .goods_id = g.goods_id ");
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getCustIdInventory())) {
            sql.append("    AND sgs.cust_id = ? ");
            lstParams.add(inforDTO.getCustIdInventory());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getOwnerIdInventory())) {
            sql.append("    AND sgs.owner_id = ? ");
            lstParams.add(inforDTO.getOwnerIdInventory());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getGoodsCode())) {
            sql.append("    AND g.code IN ( ");
            sql.append(inforDTO.getGoodsCode());
            sql.append(" ) ");
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getStatus())) {
            sql.append("    AND sgs.status IN ( ");
            sql.append(inforDTO.getStatus());
            sql.append(" ) ");
        }
        sql.append("         ) b");
        sql.append("         ON NVL (a.goodsCode, 'ABC') = NVL (b.goodsCode, 'ABC')");
        //sql.append("         AND NVL (a.goodsstate,'1') =  NVL (b.goodsstate,'1') ");
        sql.append("         AND NVL (a.serial, 'ABC') = NVL (b.serial, 'ABC') ");
        sql.append("         ORDER BY goodsId, fromSerial");
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockGoodsSerialInforDTO.class));

        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        //query.addScalar("goodsState", new StringType());
        query.addScalar("goodsUnitType", new StringType());
        query.addScalar("fromSerial", new StringType());
        query.addScalar("toSerial", new StringType());
        query.addScalar("amount", new StringType());
        query.addScalar("amountInventory", new StringType());
        query.addScalar("amountFalse", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        return query.list();
    }

    public List<StockGoodsSerialInforDTO> getStockGoodsSerialForInventoryNotSerial(StockGoodsSerialInforDTO inforDTO) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();
        sql.append(" SELECT  NVL (a.goodsid, b.goodsid) goodsid,");
        sql.append("         NVL (a.goodscode, b.goodscode) goodscode,");
        sql.append("         NVL (a.goodsname, b.goodsname) goodsname,");
        //sql.append("         NVL (a.goodsstate, b.goodsstate) goodsState,");
        sql.append("         NVL (a.goodsUnitType, b.goodsUnitType) goodsUnitType,");
        sql.append("         NVL (a.amount, 0) amount,");
        sql.append("         NVL (b.amount, 0) amountinventory,");
        sql.append("         NVL (b.amount, 0) - NVL (a.amount, 0) AS amountfalse");
        sql.append("  FROM       (SELECT   sgs.goods_id goodsid,");
        sql.append("                       g.code goodscode,");
        sql.append("                       g.name goodsname,");
        sql.append("                       g.unit_Type goodsUnitType,");
        //sql.append("                       sgs.goods_state goodsstate,");
        sql.append("                       Count(*) amount");
        sql.append("                FROM   stock_goods_serial sgs, goods g");
        sql.append("               WHERE       sgs.goods_id = g.goods_id");
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getCustId())) {
            sql.append("   AND sgs.cust_id = ? ");
            lstParams.add(inforDTO.getCustId());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getOwnerId())) {
            sql.append("    AND sgs.owner_id = ? ");
            lstParams.add(inforDTO.getOwnerId());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getGoodsCode())) {
            sql.append("    AND g.code IN (");
            sql.append(inforDTO.getGoodsCode());
            sql.append(" ) ");
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getStatus())) {
            sql.append("    AND sgs.status IN ( ");
            sql.append(inforDTO.getStatus());
            sql.append(" ) ");
        }
        sql.append("         GROUP BY sgs.goods_id,g.code, g.name, g.unit_type ) a ");
        sql.append("         FULL JOIN");
        sql.append("             (SELECT   sgs.goods_id goodsid,");
        sql.append("                       g.code goodscode,");
        sql.append("                       g.name goodsname,");
        sql.append("                       g.unit_Type goodsUnitType,");
        //sql.append("                       sgs.goods_state goodsstate,");
        sql.append("                       Count(*) amount");
        sql.append("                FROM   stock_goods_serial sgs, goods g");
        sql.append("               WHERE       sgs.goods_id = g.goods_id");
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getCustIdInventory())) {
            sql.append("    AND sgs.cust_id = ? ");
            lstParams.add(inforDTO.getCustIdInventory());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getOwnerIdInventory())) {
            sql.append("    AND sgs.owner_id = ? ");
            lstParams.add(inforDTO.getOwnerIdInventory());
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getGoodsCode())) {
            sql.append("    AND g.code IN (");
            sql.append(inforDTO.getGoodsCode());
            sql.append(" ) ");
        }
        if (!DataUtil.isStringNullOrEmpty(inforDTO.getStatus())) {
            sql.append("    AND sgs.status IN ( ");
            sql.append(inforDTO.getStatus());
            sql.append(" ) ");
        }
        sql.append("         GROUP BY sgs.goods_id,g.code, g.name,g.unit_type ) b");
        sql.append("         ON NVL (a.goodsCode, 'ABC') = NVL (b.goodsCode, 'ABC')");
        //sql.append("         AND NVL (a.goodsstate,'1') =  NVL (b.goodsstate,'1') ");
        sql.append("         ORDER BY goodsId");
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockGoodsSerialInforDTO.class));

        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        //query.addScalar("goodsState", new StringType());
        query.addScalar("goodsUnitType", new StringType());
        query.addScalar("amount", new StringType());
        query.addScalar("amountInventory", new StringType());
        query.addScalar("amountFalse", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        return query.list();
    }
}
