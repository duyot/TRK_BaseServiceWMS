/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.dto.StockGoodsDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockGoodsTotalDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.Stock;
import com.viettel.logistic.wms.service.StockImportServiceImpl;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Truongbx3
 * @version 1.0
 * @since 06-Apr-15 11:42 PM
 */
@Repository("manageStockDAO")
public class ManageStockDAO extends BaseFWDAOImpl<Stock, Long> {

//    public ManageStockDAO() {
//        this.model = new Stock();
//    }
//
//    public ManageStockDAO(Session session) {
//        this.session = session;
//    }
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;
    //Session session;

    Map<String, GoodsDTO> mapGoodsDTO;
    //
    String formatDate = "dd/mm/yyyy hh24:mi:ss";
    String sysdate = "";
    String stockTransId;
    //autowire dao
    @Autowired
    private StockTransDAO stockTransDAO;
    @Autowired
    private StockTransDetailDAO stockTransDetailDAO;
    @Autowired
    BaseFWServiceInterface goodsBusiness;
    @Autowired
    private StockTransSerialDAO stockTransSerialDAO;
    @Autowired
    private StockGoodsDAO stockGoodsDAO;
    @Autowired
    private StockGoodsTotalDAO stockGoodsTotalDAO;
    @Autowired
    private StockGoodsSerialStripDAO stockGoodsSerialStripDAO;
    @Autowired
    private StockGoodsSerialDAO stockGoodsSerialDAO;

    public ResultDTO importStockCustDAO(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetailDTO, List<StockTransSerialDTO> lstStockTransSerialDTO) {
        String stockTransDetailId;
        List<StockTransSerialDTO> filterListStockTransSerialDTO;
        String message = "";
        String stockTransCode = "";
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        //
        int insertSuccess = 0;
        int insertFail = 0;
        Double amountIssue = 0D;
        //
        Session session = sessionFactory.openSession();
        Transaction transaction;
        transaction = session.getTransaction();
        transaction.begin();
        try {
            sysdate = getSysDate(formatDate, session);
        } catch (Exception ex) {
            Logger.getLogger(ManageStockDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        GoodsDTO goodsDTO = new GoodsDTO();

        //
//        for(int i = 0 ;i<20000;i++){
//            System.out.println("abcd");
//        }
        //
        try {
            //Insert giao dich kho
            stockTransId = getSequenId("STOCK_TRANS_SEQ", session) + "";
            stockTransCode = ParamUtils.CODE_IMPORT_STOCK + stockTransId;
            stockTransDTO.setStockTransId(stockTransId + "");
            stockTransDTO.setStockTransCode(stockTransCode);
            stockTransDTO.setCreateDatetime(sysdate);
            stockTransDTO.setStockTransDate(sysdate);
            //luu thong tin giao dich stock_trans
            //-------------------------
            resultDTO = stockTransDAO.insertStockTrans(stockTransDTO.toModel(), session);
            //-----------
            //stockTransId = resultDTO.getId();
            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                rollback(transaction);
                return resultDTO;
            }
            //Lay danh sach hang hoa yeu cau day vao map
            String goodsIdList = getGoodsIdList(lstStockTransDetailDTO);
            List<ConditionBean> lstConditionBean = new ArrayList<>();
            lstConditionBean.add(new ConditionBean("custId", ParamUtils.OP_EQUAL, stockTransDTO.getCustId(), ParamUtils.TYPE_NUMBER));
            lstConditionBean.add(new ConditionBean("goodsId", ParamUtils.OP_IN, goodsIdList, ParamUtils.TYPE_NUMBER));
            List<GoodsDTO> lstGoodsDTO = goodsBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "", "code");
            mapGoodsDTO = DataUtil.putGoodsToMap(lstGoodsDTO);

            //Giao dich chi tiet            
            for (StockTransDetailDTO stockTransDetailDTO : lstStockTransDetailDTO) {
                stockTransDetailDTO.setStockTransId(stockTransId);
                stockTransDetailDTO.setStockTransDate(sysdate);
                stockTransDetailDTO.setCreateDatetime(sysdate);
                goodsDTO = mapGoodsDTO.get(stockTransDetailDTO.getGoodsId());
                if (goodsDTO == null) {
                    rollback(transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                    return resultDTO;
                }
                //Insert chi tiet giao dich kho
                Long stockTransDetailIdL = getSequenId("STOCK_TRANS_DETAIL_SEQ", session);
                stockTransDetailDTO.setStockTransDetailId(stockTransDetailIdL + "");
                stockTransDetailDTO.setStockTransId(stockTransId + "");
                stockTransDetailDTO.setGoodsCode(goodsDTO.getCode());
                stockTransDetailDTO.setGoodsName(goodsDTO.getName());
                stockTransDetailDTO.setGoodsIsSerial(goodsDTO.getIsSerial());
                stockTransDetailDTO.setGoodsIsSerialStrip(goodsDTO.getIsSerialStrip());
                //resultDTO = stockTransDetailDAO.insertStockTransDetail(stockTransDetailDTO.toModel(), session);
                stockTransDetailId = stockTransDetailIdL + "";
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback(transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.GOODS_IS_NOT_EXIST);
                    return resultDTO;
                }
                //Neu la mat hang khong quan ly serial
                if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                    //Cap nhat mat hang theo so luong 
                    //-----------------------------
                    resultDTO = importStockGoods(stockTransDTO, stockTransDetailDTO);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(transaction);
                        return resultDTO;
                    }
                    //Cap nhat so luong tong cong
                    //-------------
                    resultDTO = importStockGoodsTotal(stockTransDTO, stockTransDetailDTO);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(transaction);
                        return resultDTO;
                    }
                } else //Mat hang Quang ly theo serial
                {
                    //Lay danh sach chi tiet giao dich serial theo chi tiet giao dich
                    filterListStockTransSerialDTO = filterStockTransSerialDTO(stockTransDetailDTO.getTmpStockTransDetailId(), lstStockTransSerialDTO);
                    if (filterListStockTransSerialDTO == null || filterListStockTransSerialDTO.size() < 1) {
                        resultDTO.setMessage(ParamUtils.FAIL);
                        resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                        rollback(transaction);
                        return resultDTO;
                    }
                    //Insert giao dich chi tiet serial
                    amountIssue = 0D;
                    for (StockTransSerialDTO stockTransSerialDTO : filterListStockTransSerialDTO) {
                        //Id giao dich, ID chi tiet giao dichj
                        stockTransSerialDTO.setStockTransId(stockTransId);
                        stockTransSerialDTO.setStockTransDetailId(stockTransDetailId);
                        stockTransSerialDTO.setStockTransDate(sysdate);
                        stockTransSerialDTO.setCreateDatetime(sysdate);
                        //Tao chi tiet giao dich serial
                        Long stockTransSerialId = getSequenId("STOCK_TRANS_SERIAL_SEQ", session);
                        stockTransSerialDTO.setStockTransSerialId(stockTransSerialId + "");

                        //Insert kho hang hoa theo serial
                        //-------------
                        if (stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                            resultDTO = importStockGoodsSerial(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO);
                            insertSuccess = insertSuccess + resultDTO.getQuantitySucc();
                            insertFail = insertFail + resultDTO.getQuantityFail();
                            amountIssue = amountIssue + resultDTO.getAmountIssue();
                            //                           
                        }
                        stockTransSerialDTO.setStockTransId(stockTransId + "");
                        stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                        stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                        stockTransSerialDTO.setAmountReal(resultDTO.getAmountIssue().toString().replace(".0", ""));
                        //---------------------------
                        resultDTO = stockTransSerialDAO.insertStockTransSerial(stockTransSerialDTO.toModel(), session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(transaction);
                            return resultDTO;
                        }
                    }
                    //Cap nhat so luong tong cong
                    //------------------------------
                    stockTransDetailDTO.setAmountReal(amountIssue.toString().replace(".0", ""));
                    resultDTO = importStockGoodsTotal(stockTransDTO, stockTransDetailDTO);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(transaction);
                        return resultDTO;
                    }
                }
            }
            commit(transaction);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setMessage(e.getMessage());
            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
            rollback(transaction);
            return resultDTO;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        //
        resultDTO.setQuantitySucc(insertSuccess);
        resultDTO.setQuantityFail(insertFail);
        resultDTO.setId(stockTransId);
        return resultDTO;
    }

    //
    private void rollback(Transaction transaction) {
        transaction.rollback();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    private void commit(Transaction transaction) {
        transaction.commit();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    //-----------------------------------------------------------------------------
    //Add by ChuDV: 16/05/2015: Lay danh sach hang theo yeu cau
    private String getGoodsIdList(List<StockTransDetailDTO> lstStockTransDetailDTO) {
        List<GoodsDTO> lstGoodsDTO = new ArrayList();
        String goodsIdList = "";
        for (StockTransDetailDTO stockTransDetailDTO : lstStockTransDetailDTO) {
            if (!goodsIdList.contains(stockTransDetailDTO.getGoodsId())) {
                goodsIdList = goodsIdList + "," + stockTransDetailDTO.getGoodsId();
            }
        }
        return goodsIdList.replaceFirst(",", "");
    }

    //Insert or update Hang hoa theo so luong
    private ResultDTO importStockGoods(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        String message = "";
        StockGoodsDTO stockGoodsDTO = new StockGoodsDTO();
        StockGoodsTotalDTO stockGoodsTotalDTO = new StockGoodsTotalDTO();

        //Cac tham so dau vao
        String custId = stockTransDTO.getCustId();
        String ownerId = stockTransDTO.getOwnerId();
        String ownerType = stockTransDTO.getOwnerType();
        String goodsId = stockTransDetailDTO.getGoodsId();
        String goodsState = stockTransDetailDTO.getGoodsState();
        String cellCode = stockTransDetailDTO.getCellCode();
        String barcode = stockTransDetailDTO.getBarcode();
        String bincode = stockTransDetailDTO.getBincode();
        String addInfor = stockTransDetailDTO.getAddInfor();
        String amount = stockTransDetailDTO.getAmountReal();
        String amountIssue = stockTransDetailDTO.getAmountReal();

        String partnerId = stockTransDTO.getPartnerId();
        //Set cac gia tri cho stockGoods
        stockGoodsDTO.setCustId(custId);
        stockGoodsDTO.setOwnerId(ownerId);
        stockGoodsDTO.setOwnerType(ownerType);
        stockGoodsDTO.setGoodsId(goodsId);
        stockGoodsDTO.setGoodsState(goodsState);
        stockGoodsDTO.setCellCode(cellCode);
        stockGoodsDTO.setBarcode(barcode);
        stockGoodsDTO.setBincode(bincode);
        stockGoodsDTO.setAddInfor(addInfor);
        stockGoodsDTO.setAmount(amount);
        stockGoodsDTO.setAmountIssue(amountIssue);
        stockGoodsDTO.setPartnerId(partnerId);
        stockGoodsDTO.setChangeDate(sysdate);
        stockGoodsDTO.setImportDate(sysdate);
        //Cap nhat kho hang neu da ton tai
//        resultDTO = stockGoodsInterface.importStockGoods(stockGoodsDTO, session);
        resultDTO = stockGoodsDAO.importStockGoods(stockGoodsDTO.toModel(), session);
        //
        return resultDTO;
    }

    private ResultDTO importStockGoodsTotal(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        String message = "";
        StockGoodsDTO stockGoodsDTO = new StockGoodsDTO();
        StockGoodsTotalDTO stockGoodsTotalDTO = new StockGoodsTotalDTO();

        //Cac tham so dau vao
        String custId = stockTransDTO.getCustId();
        String ownerId = stockTransDTO.getOwnerId();
        String ownerType = stockTransDTO.getOwnerType();
        String goodsId = stockTransDetailDTO.getGoodsId();
        String goodsState = stockTransDetailDTO.getGoodsState();
        String amount = stockTransDetailDTO.getAmountOrder();
        String amountIssue = stockTransDetailDTO.getAmountReal();
        //Cap nhat so luong tong cong
        stockGoodsTotalDTO.setCustId(custId);
        stockGoodsTotalDTO.setOwnerId(ownerId);
        stockGoodsTotalDTO.setOwnerType(ownerType);
        stockGoodsTotalDTO.setGoodsId(goodsId);
        stockGoodsTotalDTO.setGoodsState(goodsState);
        stockGoodsTotalDTO.setAmount(amountIssue);
        stockGoodsTotalDTO.setAmountIssue(amountIssue);
        stockGoodsTotalDTO.setChangeDate(sysdate);
        //Cap nhat kho hang neu da ton tai
//        resultDTO = stockGoodsTotalInterface.importStockGoodsTotal(stockGoodsTotalDTO, session);
        resultDTO = stockGoodsTotalDAO.importStockGoodsTotal(stockGoodsTotalDTO.toModel(), session);
        //
        return resultDTO;
    }

    //Filter danh sach serial theo chi tiet hang hoa
    private List<StockTransSerialDTO> filterStockTransSerialDTO(String tmpStockTransDetailId, List<StockTransSerialDTO> lstStockTransSerialDTO) {
        List<StockTransSerialDTO> returnListStockTransSerialDTO = new ArrayList();
        if (lstStockTransSerialDTO != null && lstStockTransSerialDTO.size() > 0) {
            for (StockTransSerialDTO stockTransSerialDTO : lstStockTransSerialDTO) {
                if (stockTransSerialDTO.getTmpStockTransDetailId().equals(tmpStockTransDetailId)) {
                    returnListStockTransSerialDTO.add(stockTransSerialDTO);
                }
            }
        }
        return returnListStockTransSerialDTO;
    }

    //Insert or update Hang hoa theo serial
    private ResultDTO importStockGoodsSerial(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, StockTransSerialDTO stockTransSerialDTO) {
        ResultDTO resultDTO = new ResultDTO();
        String message = "";
        StockGoodsSerialStripDTO stockGoodsSerialStripDTO = new StockGoodsSerialStripDTO();
        StockGoodsSerialDTO stockGoodsSerialDTO = new StockGoodsSerialDTO();
        //Cac tham so dau vao                             
        //Mat hang quan ly serial theo dai
        if (stockTransDetailDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {
            //stockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountReal());
            stockGoodsSerialStripDTO.setCustId(stockTransDTO.getCustId());
            stockGoodsSerialStripDTO.setOwnerId(stockTransDTO.getOwnerId());
            stockGoodsSerialStripDTO.setOwnerType(stockTransDTO.getOwnerType());
            stockGoodsSerialStripDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
            stockGoodsSerialStripDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
            stockGoodsSerialStripDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
            stockGoodsSerialStripDTO.setSaleType("");
            stockGoodsSerialStripDTO.setChangeUser("");
            stockGoodsSerialStripDTO.setPrice(null);
            stockGoodsSerialStripDTO.setChannelTypeId(null);
            stockGoodsSerialStripDTO.setBarcode(stockTransSerialDTO.getBarcode());
            stockGoodsSerialStripDTO.setCellCode(stockTransSerialDTO.getCellCode());
            stockGoodsSerialStripDTO.setBincode(stockTransSerialDTO.getBincode());
            stockGoodsSerialStripDTO.setAddInfor(stockTransSerialDTO.getAddInfor());
            stockGoodsSerialStripDTO.setChangeDate(sysdate);
            stockGoodsSerialStripDTO.setImportDate(sysdate);
            stockGoodsSerialStripDTO.setSaleDate("");
            stockGoodsSerialStripDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
            stockGoodsSerialStripDTO.setToSerial(stockTransSerialDTO.getToSerial());
            stockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountOrder());
            //set partner id
            stockGoodsSerialStripDTO.setPartnerId(stockTransDTO.getPartnerId());

//            resultDTO = stockGoodsSerialStripBusiness2.importStockGoodsSerialStrip(stockGoodsSerialStripDTO, stockTransId, session);
            resultDTO = stockGoodsSerialStripDAO.importStockGoodsSerialStrip(stockGoodsSerialStripDTO.toModel(), stockTransId, session);
        } else //Mat hang quan ly serial don le 
        {
            stockGoodsSerialDTO.setCustId(stockTransDTO.getCustId());
            stockGoodsSerialDTO.setOwnerId(stockTransDTO.getOwnerId());
            stockGoodsSerialDTO.setOwnerType(stockTransDTO.getOwnerType());
            stockGoodsSerialDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
            stockGoodsSerialDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
            stockGoodsSerialDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
            stockGoodsSerialDTO.setSaleType("");
            stockGoodsSerialDTO.setChangeUser("");
            stockGoodsSerialDTO.setPrice(null);
            stockGoodsSerialDTO.setChannelTypeId(null);
            stockGoodsSerialDTO.setCellCode(stockTransSerialDTO.getCellCode());
            stockGoodsSerialDTO.setBarcode(stockTransSerialDTO.getBarcode());
            stockGoodsSerialDTO.setCellCode(stockTransSerialDTO.getCellCode());
            stockGoodsSerialDTO.setBincode(stockTransSerialDTO.getBincode());
            stockGoodsSerialDTO.setAddInfor(stockTransSerialDTO.getAddInfor());
            stockGoodsSerialDTO.setChangeDate(sysdate);
            stockGoodsSerialDTO.setImportDate(sysdate);
            stockGoodsSerialDTO.setSaleDate("");
            //
            stockGoodsSerialDTO.setPartnerId(stockTransDTO.getPartnerId());

            resultDTO = stockGoodsSerialDAO.importStockGoodsSerial(stockGoodsSerialDTO.toModel(), stockTransId, stockTransSerialDTO.getFromSerial(), stockTransSerialDTO.getToSerial(), session);
        }
        //
        return resultDTO;
    }

}
