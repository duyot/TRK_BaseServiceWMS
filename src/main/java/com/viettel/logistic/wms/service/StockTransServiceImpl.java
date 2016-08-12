/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.business.service.CommonBusinessInterface;
import com.viettel.logistic.wms.business.service.GoodsBusinessInterface;
import com.viettel.logistic.wms.business.service.StockGoodsBusinessInterface;
import com.viettel.logistic.wms.business.service.StockGoodsSerialServiceInterface;
import com.viettel.logistic.wms.business.service.StockGoodsTotalBusinessInterface;
import com.viettel.logistic.wms.business.service.StockTransBusinessInterface;
import com.viettel.logistic.wms.dto.CardStockInforDTO;
import com.viettel.logistic.wms.dto.CustomerDTO;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.dto.StockDTO;
import com.viettel.logistic.wms.dto.StockGoodsDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockGoodsTotalDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransGoodsDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.vfw5.base.dao.HibernateSessionFactory;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import javax.jws.WebService;
import java.util.ArrayList;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.DateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Transaction;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 5:25 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.StockTransService")
public class StockTransServiceImpl implements StockTransService {

    @Autowired
    BaseFWServiceInterface stockTransBusiness;
    @Autowired
    GoodsBusinessInterface goodsBusiness;
    @Autowired
    CommonBusinessInterface commonBusinessInterface;
    @Autowired
    BaseFWServiceInterface stockTransDetailBusiness;
    @Autowired
    BaseFWServiceInterface stockTransSerialBusiness;
    @Autowired
    HibernateSessionFactory sessionFactory;
    @Autowired
    StockGoodsBusinessInterface stockGoodsInterface;
    @Autowired
    StockGoodsTotalBusinessInterface stockGoodsTotalInterface;
    @Autowired
    StockGoodsSerialServiceInterface stockGoodsSerialBusiness2;
    @Autowired
    StockTransBusinessInterface stockTransBusiness2;
    @Autowired
    BaseFWServiceInterface stockGoodsSerialBusiness;

    private final String FORMAT_DATE = "dd/mm/yyyy hh24:mi:ss";
    private String SYS_DATE = DateUtil.dateToStringWithPattern(new Date(), FORMAT_DATE);

    @Override
    public String updateStockTrans(StockTransDTO stockTransDTO) {
        return stockTransBusiness.update(stockTransDTO);
    }

    @Override
    public String deleteStockTrans(Long id) {
        return stockTransBusiness.delete(id);
    }

    @Override
    public String deleteListStockTrans(List<StockTransDTO> stockTransListDTO) {
        return stockTransBusiness.delete(stockTransListDTO);
    }

    @Override
    public StockTransDTO findStockTransById(Long id) {
        if (id != null && id > 0) {
            return (StockTransDTO) stockTransBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<StockTransDTO> getListStockTransDTO(StockTransDTO stockTransDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (stockTransDTO != null) {
            return stockTransBusiness.search(stockTransDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertStockTrans(StockTransDTO stockTransDTO) {
        return stockTransBusiness.createObject(stockTransDTO);
    }

    @Override
    public String insertOrUpdateListStockTrans(List<StockTransDTO> stockTransDTO) {
        return stockTransBusiness.insertList(stockTransDTO);
    }

    @Override
    public List<String> getSequenseStockTrans(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return stockTransBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<StockTransDTO> getListStockTransByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<StockTransDTO> lstStockTrans = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER_DOUBLE)) {
                con.setType(ParamUtils.NUMBER_DOUBLE);
            } else {
                String value = "";
                if (con.getOperator().equalsIgnoreCase(ParamUtils.NAME_LIKE)) {
                    value = StringUtils.formatLike(con.getValue());
                    con.setValue(value.toLowerCase());
                    con.setField(StringUtils.formatFunction("lower", con.getField()));
                } else {
                    value = con.getValue();
                    con.setValue(value);
                    con.setField(con.getField());
                }
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));
        }

        lstStockTrans = stockTransBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstStockTrans;
    }

    @Override
    public List<StockTransInforDTO> getListStockTransInfor(StockTransInforDTO stockTransInforDTO) {
        //Lay thong tin nha vien
        List<StockTransInforDTO> lstStockTransInforDTO = commonBusinessInterface.getListStockTransInfor(stockTransInforDTO);
        //Lay danh sach kho theo nhan vien       
        return lstStockTransInforDTO;
    }

    //thienNG1 addby 28/07/2015 
    //The kho
    @Override
    public List<CardStockInforDTO> getListCardStockInfor(List<CardStockInforDTO> lstCardStockInforDTO) {
        List<CardStockInforDTO> lstCardStockInfor = commonBusinessInterface.getListCardStockInfor(lstCardStockInforDTO);
        return lstCardStockInfor;
    }

    @Override
    public List<StockTransDTO> getListStockTransAndDetailByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<StockTransDTO> lstStockTrans = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER_DOUBLE)) {
                con.setType(ParamUtils.NUMBER_DOUBLE);
            } else {
                String value = "";
                if (con.getOperator().equalsIgnoreCase(ParamUtils.NAME_LIKE)) {
                    value = StringUtils.formatLike(con.getValue());
                    con.setValue(value.toLowerCase());
                    con.setField(StringUtils.formatFunction("lower", con.getField()));
                } else {
                    value = con.getValue();
                    con.setValue(value);
                    con.setField(con.getField());
                }
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));
        }

        lstStockTrans = stockTransBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        if (lstStockTrans == null) {
            return new ArrayList<>();
        }
        List<StockTransDetailDTO> lstStockTransDetailDTO;
        for (StockTransDTO stockTransDTO : lstStockTrans) {
            lstCondition.clear();
            lstCondition.add(new ConditionBean("stockTransId", ParamUtils.OP_EQUAL, stockTransDTO.getStockTransId(), ParamUtils.TYPE_NUMBER));
            lstStockTransDetailDTO = stockTransDetailBusiness.searchByConditionBean(lstCondition, 0, Integer.MAX_VALUE, "", "stockTransDate");
            stockTransDTO.setLstStockTransDetailDTO(lstStockTransDetailDTO);
        }
        return lstStockTrans;
    }

    @Override
    public List<StockTransDTO> getListStockTrans(StockTransDTO stockTransDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Cong nhap - xuat tru
    @Override
    public Boolean inventoryInStock(List<StockTransDTO> lstStockTrans,
            CustomerDTO customer, StockDTO stock) {
        SYS_DATE = stockTransBusiness.getSysDate(FORMAT_DATE);
        Session session;
        Connection connection = null;
        Transaction transaction = null;
        session = sessionFactory.openSession();
        SessionFactory sessionFactoryBatch = session.getSessionFactory();
        StockTransDTO tempStockTrans;
        String oldStockTranId;
        for (StockTransDTO stockTrans : lstStockTrans) {
            //Thuc hien them moi stockTrans
            oldStockTranId = stockTrans.getStockTransId();
            //Neu la giao dich nhap thuc hien tao moi connection
            if ("1".equalsIgnoreCase(stockTrans.getStockTransType())) {
                tempStockTrans = initNewStockTransDTO(customer, stock, stockTrans, true);
                inventoryImportTransaction(session, connection, sessionFactoryBatch, tempStockTrans, oldStockTranId);
            } else if ("2".equalsIgnoreCase(stockTrans.getStockTransType())) {
                tempStockTrans = initNewStockTransDTO(customer, stock, stockTrans, false);
                inventoryExportTransaction(session, transaction, tempStockTrans, oldStockTranId);
            }
        }
        if (session.isOpen()) {
            session.close();
        }
        return Boolean.TRUE;
    }

    private void inventoryImportTransaction(Session session, Connection connection, SessionFactory sessionFactoryBatch,
            StockTransDTO tempStockTrans, String oldStockTranId) {
        try {
            boolean isSuccess = false;
            connection = sessionFactoryBatch.getSessionFactoryOptions().
                    getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            connection.setAutoCommit(false);
            if (createStockTrans(tempStockTrans, connection)) {
                //Thuc hien them hang hoa giao dich va cac bang du lieu lien quan
                isSuccess = insertStockTransDetailsImport(oldStockTranId,
                        tempStockTrans, session, connection);
            }
            //Neu thuc hien thanh cong thi commit
            if (isSuccess) {
                commit(null, null, connection);
            } else {//Neu khong rollback
                rollback(null, null, connection);
            }
        } catch (SQLException ex) {
            rollback(null, null, connection);
            Logger.getLogger(StockTransServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Kiem ke voi giao dich xuat
    private void inventoryExportTransaction(Session session, Transaction transaction,
            StockTransDTO tempStockTrans, String oldStockTranId) {
        boolean isSuccess;
        transaction = session.getTransaction();
        transaction.begin();
        if (createStockTrans(tempStockTrans, session)) {
            //Thuc hien them hang hoa giao dich va cac bang du lieu lien quan
            isSuccess = insertStockTransDetailsExport(oldStockTranId, tempStockTrans, session);
        } else {
            isSuccess = false;
        }
        if (isSuccess) {
            transaction.commit();
        } else {
            transaction.rollback();
        }
    }

    //Them moi Hang hoa giao dich kho doi voi giao dich nhap
    private boolean insertStockTransDetailsImport(String oldStockTransID,
            StockTransDTO newStockTrans, Session session, Connection connection) {
        //Lay danh sach hang hoa thuoc giao dich cu
        List<StockTransDetailDTO> lstStockTransDetails = getLstStockTransDetailsByStockTransId(oldStockTransID);
        //
        StockTransDetailDTO tempStockTransDetail;
        ResultDTO resultDTO = new ResultDTO();

        for (StockTransDetailDTO stdd : lstStockTransDetails) {
//            //12/05/2016 - Chi thuc hien voi hang hoa co serial don le
//            if (Constants.IS_SERIAL.equalsIgnoreCase(stdd.getGoodsIsSerial())
//                    && !Constants.IS_SERIAL_STRIP.equalsIgnoreCase(stdd.getGoodsIsSerialStrip())) {
            //Tao moi hang hoa giao dich
            tempStockTransDetail = initNewStockTransDetailDTO(newStockTrans, stdd, true);
            //Tim kiem va tim kiem goodsId dua tren custId va goodsCode
            GoodsDTO newGoods = new GoodsDTO();
            newGoods.setCustId(newStockTrans.getCustId());
            newGoods.setCode(stdd.getGoodsCode());
            List<GoodsDTO> lstGoods = goodsBusiness.search(newGoods, 0, 1, "", "");
            if (DataUtil.isListNullOrEmpty(lstGoods)) {
                return false;
            } else {
                //Set lai goodsId
                tempStockTransDetail.setGoodsId(lstGoods.get(0).getGoodsId());
            }
            //Tao hang hoa giao dich
            if (createStockTransDetail(tempStockTransDetail, connection)) {
                //Cap nhat cac du lieu khac
                resultDTO = updateImportOtherTables(oldStockTransID, newStockTrans,
                        tempStockTransDetail, session, connection);
            } else {
                return false;
//                }
            }
        }
        return ParamUtils.SUCCESS.equalsIgnoreCase(resultDTO.getMessage());
    }

    //Them moi Hang hoa giao dich kho doi voi giao dich xuat
    private boolean insertStockTransDetailsExport(String oldStockTransID,
            StockTransDTO newStockTrans, Session session) {
        //Lay danh sach hang hoa thuoc giao dich cu
        List<StockTransDetailDTO> lstStockTransDetails = getLstStockTransDetailsByStockTransId(oldStockTransID);
        StockTransDetailDTO tempStockTransDetail;
        ResultDTO resultDTO = new ResultDTO();
        for (StockTransDetailDTO stdd : lstStockTransDetails) {
            //12/05/2016 - Chi thuc hien voi hang hoa co serial don le
//            if (Constants.IS_SERIAL.equalsIgnoreCase(stdd.getGoodsIsSerial())
//                    && !Constants.IS_SERIAL_STRIP.equalsIgnoreCase(stdd.getGoodsIsSerialStrip())) {
            //Tao moi hang hoa giao dich
            tempStockTransDetail = initNewStockTransDetailDTO(newStockTrans, stdd, false);
            //Tim kiem va tim kiem goodsId dua tren custId va goodsCode
            GoodsDTO newGoods = new GoodsDTO();
            newGoods.setCustId(newStockTrans.getCustId());
            newGoods.setCode(stdd.getGoodsCode());
            List<GoodsDTO> lstGoods = goodsBusiness.search(newGoods, 0, 1, "", "");
            if (DataUtil.isListNullOrEmpty(lstGoods)) {
                return false;
            } else {
                //Set lai goodsId
                tempStockTransDetail.setGoodsId(lstGoods.get(0).getGoodsId());
            }
            //Tao hang hoa giao dich
            if (createStockTransDetail(tempStockTransDetail, session)) {
                //Cap nhat cac du lieu khac
                resultDTO = updateExportOtherTables(oldStockTransID, newStockTrans,
                        tempStockTransDetail, session);
                if (!ParamUtils.SUCCESS.equalsIgnoreCase(resultDTO.getMessage())) {
                    return false;
                }
            } else {
                return false;
            }
//            }
        }
        return ParamUtils.SUCCESS.equalsIgnoreCase(resultDTO.getMessage());
    }

    //Lay danh sach hang hoa giao dich trong bang stock_trans_detail
    private List<StockTransDetailDTO> getLstStockTransDetailsByStockTransId(String oldStockTransId) {
        List<StockTransDetailDTO> lstReturns;
        StockTransDetailDTO stockTrans = new StockTransDetailDTO();
        stockTrans.setStockTransId(oldStockTransId);
        lstReturns = stockTransDetailBusiness.search(stockTrans, 0, Integer.MAX_VALUE, "asc", "");
        return lstReturns;
    }

    //Them moi Giao dich kho moi
    private boolean createStockTrans(StockTransDTO stockTrans, Connection connection) {
        ResultDTO result;
        try {
            result = commonBusinessInterface.insertStockTrans(stockTrans, connection);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDTO();
        }
        if ("SUCCESS".equalsIgnoreCase(result.getMessage())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean createStockTrans(StockTransDTO stockTrans, Session session) {
        ResultDTO result;
        try {
            result = stockTransBusiness.createObjectSession(stockTrans, session);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDTO();
        }
        if ("SUCCESS".equalsIgnoreCase(result.getMessage())) {
            stockTrans.setStockTransId(result.getId());
            return true;
        } else {
            return false;
        }
    }

    //Them moi Giao dich kho moi
    private boolean createStockTransDetail(StockTransDetailDTO stockTransDetail, Connection connection) {
        ResultDTO result;
        try {
            result = commonBusinessInterface.insertStockTransDetail(stockTransDetail, connection);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDTO();
        }
        if ("SUCCESS".equalsIgnoreCase(result.getMessage())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean createStockTransDetail(StockTransDetailDTO stockTransDetail, Session session) {
        ResultDTO result;
        try {
            result = stockTransDetailBusiness.createObjectSession(stockTransDetail, session);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResultDTO();
        }
        if ("SUCCESS".equalsIgnoreCase(result.getMessage())) {
            stockTransDetail.setStockTransDetailId(result.getId());
            return true;
        } else {
            return false;
        }
    }

    //Sua doi du lieu cua giao dich sang kho moi, khach hang moi
    private StockTransDTO initNewStockTransDTO(CustomerDTO cdto, StockDTO stockDTO, StockTransDTO stockTran, boolean isAddId) {
        StockTransDTO returnDTO = (StockTransDTO) DataUtil.cloneObject(stockTran);
        if (isAddId) {
            String stockTransId = stockTransBusiness.getSequence("STOCK_TRANS_SEQ");
            returnDTO.setStockTransId(stockTransId);
        } else {
            returnDTO.setStockTransId(null);
        }
//        returnDTO.setStockTransDate(SYS_DATE);
//        returnDTO.setCreateDatetime(SYS_DATE);
        returnDTO.setOwnerId(stockDTO.getStockId());
        returnDTO.setCustId(cdto.getCustId());
        returnDTO.setCustCode(cdto.getCode());
        return returnDTO;
    }

    //Sua doi du lieu cua hang hoa giao dich sang kho moi, khach hang moi
    private StockTransDetailDTO initNewStockTransDetailDTO(StockTransDTO newStockTransDTO,
            StockTransDetailDTO stockTransDetail, boolean isAddId) {
        StockTransDetailDTO returnDTO = (StockTransDetailDTO) DataUtil.cloneObject(stockTransDetail);
        if (isAddId) {
            //lay seq        
            String stockTransDetailSEQ = stockTransDetailBusiness.getSequence("STOCK_TRANS_DETAIL_SEQ");
            returnDTO.setStockTransDetailId(stockTransDetailSEQ);
        } else {
            returnDTO.setStockTransDetailId(null);
        }
        returnDTO.setStockTransId(newStockTransDTO.getStockTransId());
//        returnDTO.setStockTransDate(SYS_DATE);
//        returnDTO.setCreateDatetime(SYS_DATE);
        return returnDTO;
    }

    //COMMIT
    private void commit(Session session, Transaction transaction, Connection con) {
        try {
            if (transaction != null) {
                transaction.commit();
            }
            if (con != null) {
                con.commit();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }

            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //ROLLBACK
    private void rollback(Session session, Transaction transaction, Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
            if (transaction != null) {
                transaction.rollback();
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    //Thuc hien nhap du lieu vao cac bang kho (Stock_goods, stock_goods_serial, stock_goods_total)
    private ResultDTO updateImportOtherTables(String oldStockTransId, StockTransDTO stockTransDTO,
            StockTransDetailDTO stockTransDetailDTO, Session session, Connection connection) {
        ResultDTO resultDTO;
        //Neu la hang hoa quan ly theo serial
        if (Constants.IS_SERIAL.equalsIgnoreCase(stockTransDetailDTO.getGoodsIsSerial())) {
            //Thuc hien them du lieu vao bang stock_goods_serial va stock_trans_serial
            //khoi tao list serial duoc nhap kho cho mat hang nay
            List<StockTransSerialDTO> lstStockTransSerials
                    = getListStockTransSerials(oldStockTransId, stockTransDetailDTO);
            //Insert batch VAO KHO STOCK_GOODS_SERIAL            
            resultDTO = importStockGoodsListSerial(stockTransDTO,
                    (StockTransDetailDTO) DataUtil.cloneObject(stockTransDetailDTO),
                    lstStockTransSerials, connection, ParamUtils.GOODS_IMPORT_STATUS.IMPORTED);
            //Cap nhat lai so luong hang hoa giao dich
            if (resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                Double amountIssue = resultDTO.getAmountIssue();
                //CAP NHAT LAI STOCK_TRANS_DETAIL VOI SO LUONG INSERT THANH CONG
                stockTransDetailDTO.setAmountReal(amountIssue.toString().replace(".0", ""));
                int isUpdate = stockGoodsSerialBusiness2.updateStockTransDetail(
                        stockTransDetailDTO.getStockTransDetailId(), amountIssue, connection);
                //neu update khong thanh cong
                if (isUpdate < 1) {
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey("UPDATE_STOCK_TRANS_DETAIL_ERROR");
                    rollback(session, null, connection);
                    return resultDTO;
                }
            }
        } else { //Neu la hang hoa khong quan ly theo serial
            //Thuc hien cap nhat stock_goods
            resultDTO = importStockGoods(stockTransDTO, stockTransDetailDTO,
                    session, ParamUtils.GOODS_IMPORT_STATUS.IMPORTED);
        }
        if (resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
            resultDTO = importStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
        }
        return resultDTO;
    }

    //Thuc hien nhap du lieu vao cac bang kho (Stock_goods, stock_goods_serial, stock_goods_total)
    private ResultDTO updateExportOtherTables(String oldStockTransId, StockTransDTO stockTransDTO,
            StockTransDetailDTO stockTransDetailDTO, Session session) {
        ResultDTO resultDTO = null;
        boolean isSuccess = true;
        //Neu la hang hoa quan ly theo serial
        if (Constants.IS_SERIAL.equalsIgnoreCase(stockTransDetailDTO.getGoodsIsSerial())) {
            //Thuc hien them du lieu vao bang stock_goods_serial va stock_trans_serial
            //khoi tao list serial duoc nhap kho cho mat hang nay
            List<StockTransSerialDTO> lstStockTransSerials
                    = getListStockTransSerials(oldStockTransId, stockTransDetailDTO);
            //Insert giao dich chi tiet serial
            for (StockTransSerialDTO stockTransSerialDTO : lstStockTransSerials) {
                //
                resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
                if (resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    //Xuat kho kho hang hoa theo serial
                    stockTransSerialDTO.setStockTransSerialId(resultDTO.getId());
                    resultDTO = exportStockGoodsSerial(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        isSuccess = false;
                        break;
                    }
                } else {
                    isSuccess = false;
                    break;
                }
            }
        } else { //Neu la hang hoa khong quan ly theo serial
            //Thuc hien cap nhat stock_goods
            resultDTO = exportStockGoods(stockTransDTO, stockTransDetailDTO,
                    session, ParamUtils.GOODS_IMPORT_STATUS.EXPORTED);
            if (resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                isSuccess = true;
            } else {
                isSuccess = false;
            }
        }
        if (isSuccess) {
            //Cap nhat Kho hang hoa theo so luong
            resultDTO = exportStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
        }
        return resultDTO;
    }

//Thuc hien them moi nhap kho - stock_goods
    private ResultDTO importStockGoods(StockTransDTO stockTransDTO,
            StockTransDetailDTO stockTransDetailDTO, Session session, String goodsStatus) {
        ResultDTO resultDTO;
        //Tao ra Stock_goods tu giao dich va hang hoa giao dich
        StockGoodsDTO stockGoodsDTO = initStockGoods(stockTransDTO, stockTransDetailDTO, goodsStatus);
        //Cap nhat kho hang neu da ton tai
        resultDTO = stockGoodsInterface.importStockGoods(stockGoodsDTO, session);
        return resultDTO;
    }

    //Thuc hien them moi stock_goods - xuat kho
    private ResultDTO exportStockGoods(StockTransDTO stockTransDTO,
            StockTransDetailDTO stockTransDetailDTO, Session session, String goodsStatus) {
        ResultDTO resultDTO;
        //Tao ra Stock_goods tu giao dich va hang hoa giao dich
        StockGoodsDTO stockGoodsDTO = initStockGoods(stockTransDTO, stockTransDetailDTO, goodsStatus);
        //Cap nhat kho hang neu da ton tai
        resultDTO = stockGoodsInterface.exportStockGoods(stockGoodsDTO, session);
        return resultDTO;
    }

    //Khoi tao 1 hang hoa trong kho tu giao dich va hang hoa giao dich
    private StockGoodsDTO initStockGoods(StockTransDTO stockTransDTO,
            StockTransDetailDTO stockTransDetailDTO, String goodsStatus) {
        StockGoodsDTO stockGoodsDTO = new StockGoodsDTO();
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
        stockGoodsDTO.setChangeDate(stockTransDTO.getStockTransDate());
        stockGoodsDTO.setImportDate(stockTransDTO.getStockTransDate());
        //CAP NHAT TRANG THAI - ORDER_ID - IMPORT_STOCK_TRANS_ID
        stockGoodsDTO.setStatus(goodsStatus);
        stockGoodsDTO.setOrderId(stockTransDTO.getOrderIdList());
        stockGoodsDTO.setImportStockTransId(stockTransDTO.getStockTransId());
        return stockGoodsDTO;
    }

    //Cap nhat vao stock_goods_total
    private ResultDTO importStockGoodsTotal(StockTransDTO stockTransDTO,
            StockTransDetailDTO stockTransDetailDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        StockGoodsTotalDTO stockGoodsTotalDTO = new StockGoodsTotalDTO();
        //Cac tham so dau vao
        String custId = stockTransDTO.getCustId();
        String ownerId = stockTransDTO.getOwnerId();
        String ownerType = stockTransDTO.getOwnerType();
        String goodsId = stockTransDetailDTO.getGoodsId();
        String goodsState = stockTransDetailDTO.getGoodsState();
        String amountIssue = stockTransDetailDTO.getAmountReal();
        //Cap nhat so luong tong cong
        stockGoodsTotalDTO.setCustId(custId);
        stockGoodsTotalDTO.setOwnerId(ownerId);
        stockGoodsTotalDTO.setOwnerType(ownerType);
        stockGoodsTotalDTO.setGoodsId(goodsId);
        stockGoodsTotalDTO.setGoodsState(goodsState);
        stockGoodsTotalDTO.setAmount(amountIssue);
        stockGoodsTotalDTO.setAmountIssue(amountIssue);
        stockGoodsTotalDTO.setChangeDate(stockTransDTO.getStockTransDate());
        //Cap nhat kho hang neu da ton tai
        resultDTO = stockGoodsTotalInterface.importStockGoodsTotal(stockGoodsTotalDTO, session);
        return resultDTO;
    }

    private ResultDTO exportStockGoodsTotal(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);

        //Cac tham so dau vao
        String custId = stockTransDTO.getCustId();
        String ownerId = stockTransDTO.getOwnerId();
        String ownerType = stockTransDTO.getOwnerType();
        String goodsId = stockTransDetailDTO.getGoodsId();
        String amount = stockTransDetailDTO.getAmountReal();
        String orderIdList = stockTransDTO.getOrderIdList(); // danh sach id yeu cau xuat kho
        String amountIssue;
        if (DataUtil.isStringNullOrEmpty(orderIdList)) {// Neu xuat hang da ko co yeu cau xuat -> tru ca so luong dap ung 
            amountIssue = stockTransDetailDTO.getAmountReal();
        } else { // Neu xuat hang da co yeu cau -> Da tru so luong luc tao yeu cau
            amountIssue = "0";
        }
        String goodsState = stockTransDetailDTO.getGoodsState();
        //Cap nhat kho hang tong cong
        StockGoodsTotalDTO stockGoodsTotalDTO = new StockGoodsTotalDTO();
        stockGoodsTotalDTO.setCustId(custId);
        stockGoodsTotalDTO.setOwnerId(ownerId);
        stockGoodsTotalDTO.setOwnerType(ownerType);
        stockGoodsTotalDTO.setGoodsId(goodsId);
        stockGoodsTotalDTO.setGoodsState(goodsState);
        stockGoodsTotalDTO.setAmount(amount);
        stockGoodsTotalDTO.setAmountIssue(amountIssue);
        stockGoodsTotalDTO.setChangeDate(stockTransDTO.getStockTransDate());
        resultDTO = stockGoodsTotalInterface.exportStockGoodsTotal(stockGoodsTotalDTO, session);
        //
        return resultDTO;
    }

    //Tao danh sach de them moi serial vao bang stock_trans_serial
    private List<StockTransSerialDTO> getListStockTransSerials(String oldStockTransId,
            StockTransDetailDTO stockTransDetail) {
        List<StockTransSerialDTO> lstReturns;
        StockTransSerialDTO searchDTO = new StockTransSerialDTO();
        searchDTO.setStockTransId(oldStockTransId);
        lstReturns = stockTransSerialBusiness.search(searchDTO, 0, Integer.MAX_VALUE, "", "");
        //Sua lai thong tin stock_trans_id va stock_trans_detail_id
        for (StockTransSerialDTO stockTransSerialDTO : lstReturns) {
            //Cap nhat Id giao dich, ID chi tiet giao dichj
            stockTransSerialDTO.setStockTransId(stockTransDetail.getStockTransId());
            stockTransSerialDTO.setStockTransDetailId(stockTransDetail.getStockTransDetailId());
//            stockTransSerialDTO.setStockTransDate(SYS_DATE);
//            stockTransSerialDTO.setCreateDatetime(SYS_DATE);
            stockTransSerialDTO.setGoodsId(stockTransDetail.getGoodsId());
        }
        return lstReturns;
    }

    //Nhap hang hoa serial vao bang stock_goods_serial va hang hoa serial giao dich stock_trans_serial
    private ResultDTO importStockGoodsListSerial(StockTransDTO stockTransDTO,
            StockTransDetailDTO stockTransDetail,
            List<StockTransSerialDTO> lstStockTransSerials,
            Connection connection, String IMPORTED) {
        ResultDTO resultDTO = new ResultDTO();
        int quanlitySuccess = 0;
        int quanlityFail = 0;
//        List<StockTransSerialDTO> lstStockTransSerials = stockTransDetail.getLstStockTransSerialDTO();
        //Map giua giao dich va danh sach serial
        Map<StockTransSerialDTO, List<String>> mapStockTransSerial2ListSerial = new HashMap<>();
        //Tach nhung serial don le nhung duoc gop thanh nhung serial theo dai
        List<String> lstSerial2Search = DataUtil.buildSingleSerial(lstStockTransSerials, mapStockTransSerial2ListSerial);
        //Lay danh sach stock_goods_serial tu nhung serial nhap len co trang thai = 1
        List<StockGoodsSerialDTO> lstGoodsSerialInStock
                = findStockGoodsSerial(stockTransDTO, stockTransDetail, lstSerial2Search, "1");
        if (!DataUtil.isListNullOrEmpty(lstGoodsSerialInStock)) {
            //Tra ve danh sach loi va thong bao serial da co trong kho                 
//            resultDTO.setLstStockGoodsSerialInforDTO(
//                    convert2StockGoodsSerialInfor(lstGoodsSerialInStock, stockTransDetail));
            resultDTO.setMessage(Constants.ERROR_MESSAGE.GOODS_SERIAL_IN_STOCK);
            resultDTO.setKey(Constants.ERROR_MESSAGE.GOODS_SERIAL_IN_STOCK);
            return resultDTO;
        }
        //Lay danh sach serial co trang thai = 3
        List<StockGoodsSerialDTO> lstGoodsSerialOutOfStock
                = findStockGoodsSerial(stockTransDTO, stockTransDetail, lstSerial2Search, "3");
        //Thuc hien cap nhat theo batch cho cac serial co trang thai = 3
        List<StockTransSerialDTO> lstStockTransSerialOutOfStock = null;
        //QuyenDM 27/04/2016 : Danh sach cac serial loi khong cap nhat hoac them moi duoc vao csdl
        List<StockGoodsSerialInforDTO> lstError = new ArrayList<>();
        if (!DataUtil.isListNullOrEmpty(lstGoodsSerialOutOfStock)) {
            //Lay danh sach giao dich voi cac hang hoa o khong o trong kho 
            lstStockTransSerialOutOfStock
                    = getLstStockTransSerialFromListGoodsSerial(lstGoodsSerialOutOfStock, mapStockTransSerial2ListSerial);
            //Thuc hien viec cap nhat du lieu cho cac serial tu trang thai o ngoai kho = 3 ve dang cho trong kho = 2
            resultDTO = stockGoodsSerialBusiness2.reImportStockGoodsSerial(
                    stockTransDTO, stockTransDetail, lstStockTransSerialOutOfStock,
                    connection, IMPORTED, true);
            quanlitySuccess = resultDTO.getQuantitySucc();
            quanlityFail = resultDTO.getQuantityFail();
            //Neu co danh sach loi 
            if (!DataUtil.isListNullOrEmpty(resultDTO.getLstStockGoodsSerialInforDTO())) {
                lstError.addAll(resultDTO.getLstStockGoodsSerialInforDTO());
            }
        }
        //Kiem tra xem co serial nao chua co trong db khong. Neu chua co thi thuc hien them moi = ham them moi cua duyot
        List<StockTransSerialDTO> lstStockTransSerialInsert2Stock = new ArrayList<>();
        if (DataUtil.isListNullOrEmpty(lstStockTransSerialOutOfStock)) {
            lstStockTransSerialInsert2Stock = lstStockTransSerials;
        } else {
            for (StockTransSerialDTO stsd : lstStockTransSerials) {
                if (!lstStockTransSerialOutOfStock.contains(stsd)) {
                    lstStockTransSerialInsert2Stock.add(stsd);
                }
            }
        }
        if (!DataUtil.isListNullOrEmpty(lstStockTransSerialInsert2Stock)) {
            //Thuc hien viec them moi du lieu cho cac serial tu trang thai o ngoai kho = 3 ve dang cho trong kho = 2
            resultDTO = stockGoodsSerialBusiness2.reImportStockGoodsSerial(
                    stockTransDTO, stockTransDetail, lstStockTransSerialInsert2Stock,
                    connection, IMPORTED, false);
            quanlitySuccess += resultDTO.getQuantitySucc();
            quanlityFail = resultDTO.getQuantityFail();
            //Neu co danh sach loi khi them moi
            if (!DataUtil.isListNullOrEmpty(resultDTO.getLstStockGoodsSerialInforDTO())) {
                lstError.addAll(resultDTO.getLstStockGoodsSerialInforDTO());
            }
        }
        resultDTO.setQuantitySucc(quanlitySuccess);
        resultDTO.setQuantityFail(quanlityFail);
        resultDTO.setLstStockGoodsSerialInforDTO(lstError);
        return resultDTO;

//        return stockGoodsSerialBusiness2.importListStockGoodsSerial(stockTransDTO,
//                stockTransDetailDTO, lstStockTransSerials, connection, IMPORTED);
    }

    //Tim kiem stock_goods_serial theo danh sach serial tai len
    private List<StockGoodsSerialDTO> findStockGoodsSerial(StockTransDTO stockTransDTO,
            StockTransDetailDTO stockTransDetail, List<String> lstSerial, String status) {
        List<ConditionBean> lstConditions;
        List<StockGoodsSerialDTO> lstTemp;
        List<StockGoodsSerialDTO> lstReturn = new ArrayList<>();
        for (String serial : lstSerial) {
            lstConditions = new ArrayList<>();
            ConditionBean goodsId = new ConditionBean("goodsId", ParamUtils.OP_EQUAL, stockTransDetail.getGoodsId(), ParamUtils.TYPE_NUMBER);
            ConditionBean custId = new ConditionBean("custId", ParamUtils.OP_EQUAL, stockTransDTO.getCustId(), ParamUtils.TYPE_NUMBER);
//            ConditionBean goodsState = new ConditionBean("goodsState", ParamUtils.OP_EQUAL, stockTransDetail.getGoodsState(), ParamUtils.TYPE_STRING);
            ConditionBean statusCon = new ConditionBean("status", ParamUtils.OP_EQUAL, status, ParamUtils.TYPE_STRING);
            lstConditions.add(goodsId);
            lstConditions.add(custId);
//            lstConditions.add(goodsState);
            lstConditions.add(statusCon);
            ConditionBean fromSerial = new ConditionBean("serial", ParamUtils.OP_IN, serial, ParamUtils.TYPE_STRING);
            lstConditions.add(fromSerial);
            lstTemp = stockGoodsSerialBusiness.searchByConditionBean(lstConditions, 0, Integer.MAX_VALUE, "asc", "status");
            if (!DataUtil.isListNullOrEmpty(lstTemp)) {
                lstReturn.addAll(lstTemp);
            }
        }
        return lstReturn;
    }

    //Lay danh sach stock_trans_serial tu danh sach hang hoa serial
    private List<StockTransSerialDTO> getLstStockTransSerialFromListGoodsSerial(
            List<StockGoodsSerialDTO> lstStockGoodsSerial,
            Map<StockTransSerialDTO, List<String>> mapStockTransSerial2ListSerial) {
        List<StockTransSerialDTO> lstReturns = new ArrayList<>();
        String serial;
        List<String> lstSerialContain = new ArrayList<>();
        List<String> lstSerial;
        for (StockGoodsSerialDTO stockGoodsSerial : lstStockGoodsSerial) {
            serial = stockGoodsSerial.getSerial();
            if (!lstSerialContain.contains(serial)) {
                for (StockTransSerialDTO stockTransSerial : mapStockTransSerial2ListSerial.keySet()) {
                    lstSerial = mapStockTransSerial2ListSerial.get(stockTransSerial);
                    if (lstSerial.contains(serial)) {
                        lstSerialContain.addAll(lstSerial);
                        lstReturns.add(stockTransSerial);
                    }
                }
            }
        }
        return lstReturns;
    }

    @Override
    public List<StockTransDTO> getListStockTrans2Inventory(StockTransDTO stockTransDTO,
            String fromDate, String toDate, String stockTransType) {
        return stockTransBusiness2.getListStockTrans2Inventory(stockTransDTO, fromDate, toDate, stockTransType);
    }

    //-----------------------------------------
    //Insert or update Hang hoa theo serial
    private ResultDTO exportStockGoodsSerial(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO,
            StockTransSerialDTO stockTransSerialDTO, Session session) {
        ResultDTO resultDTO;
        StockGoodsSerialStripDTO oldStockGoodsSerialStripDTO = new StockGoodsSerialStripDTO();
        StockGoodsSerialStripDTO newStockGoodsSerialStripDTO = new StockGoodsSerialStripDTO();
        //Thong tin cao nhat
        newStockGoodsSerialStripDTO.setCustId(stockTransDTO.getCustId());
        newStockGoodsSerialStripDTO.setOwnerId(stockTransDTO.getOwnerId());
        newStockGoodsSerialStripDTO.setOwnerType(stockTransDTO.getOwnerType());
        newStockGoodsSerialStripDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
        newStockGoodsSerialStripDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
//        newStockGoodsSerialStripDTO.setStatus(Constants.STATUS_SERIAL_OUT_STOCK);
        if (!DataUtil.isStringNullOrEmpty(stockTransDetailDTO.getGoodsIEStatus())) { // trang thai cho nhap/xuat kho
            newStockGoodsSerialStripDTO.setStatus(stockTransDetailDTO.getGoodsIEStatus());
        } else {
            newStockGoodsSerialStripDTO.setStatus(Constants.STATUS_SERIAL_OUT_STOCK); // trang thai xuat kho
        }
        newStockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountReal());
        newStockGoodsSerialStripDTO.setChangeUser(stockTransDTO.getTransUserId());
        newStockGoodsSerialStripDTO.setChangeDate(stockTransDTO.getStockTransDate());
        newStockGoodsSerialStripDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
        newStockGoodsSerialStripDTO.setToSerial(stockTransSerialDTO.getToSerial());
        newStockGoodsSerialStripDTO.setOrderId(stockTransDTO.getOrderIdList());
        //duyot
//        newStockGoodsSerialStripDTO.setCellCode(stockTransSerialDTO.getCellCode());
//        newStockGoodsSerialStripDTO.setBarcode(stockTransSerialDTO.getBarcode());        
        //Cac tham so dau vao        
        oldStockGoodsSerialStripDTO.setCustId(stockTransDTO.getCustId());
        oldStockGoodsSerialStripDTO.setOwnerId(stockTransDTO.getOwnerId());
        oldStockGoodsSerialStripDTO.setOwnerType(stockTransDTO.getOwnerType());
        oldStockGoodsSerialStripDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
        oldStockGoodsSerialStripDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
        oldStockGoodsSerialStripDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
        oldStockGoodsSerialStripDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
        oldStockGoodsSerialStripDTO.setToSerial(stockTransSerialDTO.getToSerial());
//        //duyot 
//        oldStockGoodsSerialStripDTO.setCellCode(stockTransSerialDTO.getCellCode());
//        oldStockGoodsSerialStripDTO.setBarcode(stockTransSerialDTO.getBarcode());
        //Mat hang quan ly serial theo dai
        if (stockTransDetailDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {
            resultDTO = new ResultDTO();
        } else //Mat hang quan ly serial don le 
        {
            //Cap nhat serial hang hoa
            resultDTO = stockGoodsSerialBusiness2.exportStockGoodsSerial(oldStockGoodsSerialStripDTO, newStockGoodsSerialStripDTO, session);
        }
        //
        return resultDTO;
    }

    @Override
    public List<StockTransGoodsDTO> getListStockTransGoods2Report(String lstStockTransCodes) {
        return stockGoodsSerialBusiness2.getListStockTransGoods2Report(lstStockTransCodes);
    }

}
