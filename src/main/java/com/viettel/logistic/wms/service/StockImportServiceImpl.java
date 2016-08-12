/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.service;

import com.google.common.collect.Lists;
import com.viettel.common.dataprovider.WSBccsGateway;
import com.viettel.common.dataprovider.WSKTTS;
import com.viettel.common.dataprovider.WSStaff;
import com.viettel.common.dataprovider.WSSynLog;
import com.viettel.common.dataprovider.WSSynchonize;
import com.viettel.logistic.common.logs.KPILogger;
import com.viettel.logistic.wms.business.service.CommonBusinessInterface;
import com.viettel.logistic.wms.business.service.KpiLogInterface;
import com.viettel.logistic.wms.business.service.StockGoodsBusinessInterface;
import com.viettel.logistic.wms.business.service.StockGoodsSerialServiceInterface;
import com.viettel.logistic.wms.business.service.StockGoodsSerialStripServiceInterface;
import com.viettel.logistic.wms.business.service.StockGoodsTotalBusinessInterface;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.dto.GoodsInTicketDTO;
import com.viettel.logistic.wms.dto.BillStock;
import com.viettel.logistic.wms.dto.KpiLogDTO;
import com.viettel.logistic.wms.dto.OrderActionDTO;
import com.viettel.logistic.wms.dto.ResultBCCSDTO;
import com.viettel.logistic.wms.dto.ResultKTTSDTO;
import com.viettel.logistic.wms.dto.StaffDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockGoodsTotalDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.model.Stock;
import com.viettel.logistic.wms.model.StockGoods;
import com.viettel.logistic.wms.model.StockGoodsSerialStrip;
import com.viettel.logistic.wms.model.StockTransDetail;
import com.viettel.logistic.wms.webservice.WSOrderAction;
import com.viettel.logistic.wms.webservice.WSOrders;
import com.viettel.logstic.wms.webservice.dto.DataBccs;
import com.viettel.logstic.wms.webservice.dto.DataIEBccs;
import com.viettel.logstic.wms.webservice.dto.OrderGoodsDetailDTO;
import com.viettel.logstic.wms.webservice.dto.OrderGoodsDetailSerialDTO;
import com.viettel.logstic.wms.webservice.dto.OrdersDTO;
import com.viettel.logstic.wms.webservice.dto.ResultSyn;
import com.viettel.logstic.wms.webservice.dto.SynLogDTO;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.DateUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author vtsoft
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.StockImportService")

public class StockImportServiceImpl implements StockImportService {

    @Autowired
    BaseFWServiceInterface stockTransBusiness;
    @Autowired
    BaseFWServiceInterface stockTransDetailBusiness;
    @Autowired
    BaseFWServiceInterface stockTransSerialBusiness;
    @Autowired
    BaseFWServiceInterface stockGoodsBusiness;
    @Autowired
    BaseFWServiceInterface goodsBusiness;
    @Autowired
    BaseFWServiceInterface stockGoodsTotalBusiness;
    @Autowired
    BaseFWServiceInterface stockGoodsSerialBusiness;
    @Autowired
    BaseFWServiceInterface stockGoodsSerialStripBusiness;
    @Autowired
    StockGoodsBusinessInterface stockGoodsInterface;
    @Autowired
    StockGoodsTotalBusinessInterface stockGoodsTotalInterface;
    @Autowired
    StockGoodsSerialStripServiceInterface stockGoodsSerialStripBusiness2;
    @Autowired
    StockGoodsSerialServiceInterface stockGoodsSerialBusiness2;
    @Autowired
    BaseFWServiceInterface stockGoodsSerialErrorBusiness;
    @Autowired
    BaseFWServiceInterface stockBusiness;
    @Autowired
    KpiLogInterface kpiLogBusiness;
    @Autowired
    CommonBusinessInterface commonBusinessInterface;
    //
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;
    //
    String formatDate = "dd/mm/yyyy hh24:mi:ss";
    private static final String IS_SERIAL = "1";
    private static final String TRANS_TYPE_IMPORT = "1";
    private static final String TRANS_TYPE_EXPORT = "2";
    private static final String BCCS_RESPONSE_SUCCESS = "0";
    private static final String KTTS_RESPONSE_SUCCESS = "1";
    private static final String ORDER_BCCS = "1";
    private static final String SYN_SUCC = "1";
    private static final String SYN_FAIL = "0";
    public final static String ORDER_STATUS_IMPORTED_EXPORTED = "6"; // trang thai yeu cau da thuc xuat/nhap
    public final static String ORDER_STATUS_COMMAN_CREATED = "4"; // trang thai yeu cau da thuc xuat/nhap  
    public final static String ORDER_ACTION_STATUS_EXPORTED = "3";

    public final static String ORDER_SOURCE_BCCS = "1";
    public static List<String> lstCurrentOrderActionID = new ArrayList();

    public void removeLock(OrderActionDTO oa) {
        if (oa != null && lstCurrentOrderActionID.contains(oa.getId())) {
            lstCurrentOrderActionID.remove(oa.getId());
        } else {
            lstCurrentOrderActionID.clear();
        }
    }

    //   
    @Override
    public ResultDTO importStockCust(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetailDTO, List<StockTransSerialDTO> lstStockTransSerialDTO) {
        String stockTransDetailId;
        List<StockTransSerialDTO> filterListStockTransSerialDTO;
        String stockTransCode;
        String stockTransSEQ;
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        Map<String, GoodsDTO> mapGoodsDTO;
        Map<String, GoodsDTO> mapGoodsCode2DTO;
        //
        Double indexInsertSuccess = 0D;
        //
        int insertSuccess = 0;
        int insertFail = 0;
        Double amountIssue;
        //
        String stockTransId = "";
        String sysdate;
        //
        Session session;
        Transaction transaction;
        Connection connection = null;
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        //
        sysdate = stockGoodsBusiness.getSysDate(formatDate);
        GoodsDTO goodsDTO;
        //du lieu cho dong bo bccs
        String previousOrderActionStatus = null;
        /*
         duyot: cap nhat 27/08: dong bo bccs
         - cap nhat partner id
         - cap nhat cac truong trong serial neu la tu kh vtt (id = 1)
         - Khoi tao list NHAP - NHAP DIEU CHUYEN
         */
        //DuyOT
        OrderActionDTO orderActionDTO = null;
        OrdersDTO orders = null;
        try {
            //---------------------------------------------------------------------
            //1. KHOI TAO CONNECTION
            //-------------------------------------------------------------------    
            SessionFactory sessionFactoryBatch = session.getSessionFactory();
            connection = sessionFactoryBatch.getSessionFactoryOptions().
                    getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            connection.setAutoCommit(false);
            //-------------------------------------------------------------------
            //KIEM TRA LENH DA DUOC NHAP HAY CHUA
            //1. KIEM TRA TRANG THAI LENH
            String orderIdList = stockTransDTO.getOrderIdList();
            if (!StringUtils.isStringNullOrEmpty(orderIdList)) {//NEU LA NHAP THEO YC -> CHECK
                orders = WSOrders.findOrderById(orderIdList);
                if (orders == null) {//KHONG TIM DUOC YC HOP LE
                    resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.ORDER_NOT_FOUND);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_NOT_FOUND);
                    rollback(session, transaction, connection);
                    return resultDTO;
                }
                //LAY THONG TIN LENH
                List<ConditionBean> lstConditionBeanUpdateOrders = new ArrayList<>();
                lstConditionBeanUpdateOrders.add(new ConditionBean("orderIdList", ParamUtils.NAME_EQUAL, orders.getOrderId(), ParamUtils.TYPE_STRING));
                try {
                    orderActionDTO = WSOrderAction.getListOrderActionByCondition(lstConditionBeanUpdateOrders, 0, Integer.MAX_VALUE, "", "id").get(0);
                } catch (Exception ex) {
                }
                if (orderActionDTO == null) {//KHONG TIM DUOC YC HOP LE
                    resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.ORDER_NOT_FOUND);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_NOT_FOUND);
                    rollback(session, transaction, connection);
                    return resultDTO;
                }
                //kiem tra co ai dang cung nhap k
//                if (lstCurrentOrderActionID.contains(orderActionDTO.getId())) {
//                    resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.SESSION_CONFLIC);
//                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.SESSION_CONFLIC);
//                    return resultDTO;
//                } else {//them vao current process
//                    lstCurrentOrderActionID.add(orderActionDTO.getId());
//                }

                if (orderActionDTO.getStatus().equalsIgnoreCase("3")) {//NEU TRANG THAI LENH = 3 -> DA NHAP -> THONG BAO LENH DA THUC NHAP
                    resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.ORDER_ACTION_UPDATED);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_ACTION_UPDATED);
                    return resultDTO;
                }

                previousOrderActionStatus = orderActionDTO.getStatus();
                String updateMessage = updateOrderAndOrderAction(orders, orderActionDTO);

                if (!updateMessage.equalsIgnoreCase(ParamUtils.SUCCESS)) {//NEU THONG TIN CAP NHAT LOI -> ROLLBACK
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.UPDATE_ORDER_ACTION_FAIL);
                    rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                    rollback(session, transaction, connection);
                    removeLock(orderActionDTO);
                    return resultDTO;
                }
            }
            //
            if (!DataUtil.isListNullOrEmpty(lstStockTransDetailDTO)) {
                //INSERT GIAO DICH STOCK_TRANS
                stockTransSEQ = stockTransBusiness.getSequence("STOCK_TRANS_SEQ");
                stockTransCode = ParamUtils.CODE_IMPORT_STOCK + stockTransSEQ;
                stockTransDTO.setStockTransCode(stockTransCode);
                stockTransDTO.setCreateDatetime(sysdate);
                stockTransDTO.setStockTransDate(sysdate);
                stockTransDTO.setStockTransId(stockTransSEQ);
                resultDTO = commonBusinessInterface.insertStockTrans(stockTransDTO, connection);
                stockTransId = stockTransDTO.getStockTransId();
                stockTransDTO.setStockTransId(stockTransId);
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    removeLock(orderActionDTO);
                    rollback(session, transaction, connection);
                    rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                    return resultDTO;
                }
                //DAY VAO MAP DANH SACH HANG HOA
                String goodsIdList = getGoodsIdList(lstStockTransDetailDTO);
                List<ConditionBean> lstConditionBean = new ArrayList<>();
                lstConditionBean.add(new ConditionBean("custId", ParamUtils.OP_EQUAL, stockTransDTO.getCustId(), ParamUtils.TYPE_NUMBER));
                lstConditionBean.add(new ConditionBean("goodsId", ParamUtils.OP_IN, goodsIdList, ParamUtils.TYPE_NUMBER));
                List<GoodsDTO> lstGoodsDTO = goodsBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "", "code");
                mapGoodsDTO = DataUtil.putGoodsToMap(lstGoodsDTO);
                //LOOP: CHI TIET CHO TUNG MAT HANG
                boolean firstWrong = false;
                Double pre = 0D;
                for (StockTransDetailDTO stockTransDetailDTO : lstStockTransDetailDTO) {
                    //lay seq
                    String stockTransDetailSEQ = stockTransDetailBusiness.getSequence("STOCK_TRANS_DETAIL_SEQ");
                    stockTransDetailDTO.setStockTransId(stockTransId);
                    stockTransDetailDTO.setStockTransDetailId(stockTransDetailSEQ);
                    stockTransDetailDTO.setStockTransDate(sysdate);
                    stockTransDetailDTO.setCreateDatetime(sysdate);
                    goodsDTO = mapGoodsDTO.get(stockTransDetailDTO.getGoodsId());
                    if (goodsDTO == null) {
                        rollback(session, transaction, connection);
                        resultDTO.setMessage(ParamUtils.FAIL);
                        resultDTO.setKey(ParamUtils.GOODS_IS_NOT_EXIST);
                        removeLock(orderActionDTO);
                        rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                        return resultDTO;
                    }
                    //INSERT CHI TIET GIAO DICH KHO STOCK_TRANS_DETAIL
                    stockTransDetailDTO.setGoodsCode(goodsDTO.getCode());
                    stockTransDetailDTO.setGoodsName(goodsDTO.getName());
                    stockTransDetailDTO.setGoodsIsSerial(goodsDTO.getIsSerial());
                    stockTransDetailDTO.setGoodsIsSerialStrip(goodsDTO.getIsSerialStrip());
                    //------
                    resultDTO = commonBusinessInterface.insertStockTransDetail(stockTransDetailDTO, connection);
                    //-------------
                    stockTransDetailId = stockTransDetailSEQ;
                    //
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction, connection);
                        resultDTO.setMessage(ParamUtils.FAIL);
                        resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                        removeLock(orderActionDTO);
                        rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                        return resultDTO;
                    }
                    //MAT HANG K THEO SERIAL
                    if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                        //Cap nhat mat hang theo so luong STOCK_GOODS
                        resultDTO = importStockGoods(stockTransDTO, stockTransDetailDTO, session, ParamUtils.GOODS_IMPORT_STATUS.IMPORTED);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction, connection);
                            removeLock(orderActionDTO);
                            rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                            return resultDTO;
                        }
                        //Cap nhat so luong tong cong STOCK_GOODS_TOTAL
                        resultDTO = importStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction, connection);
                            removeLock(orderActionDTO);
                            rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                            return resultDTO;
                        }
                    } else //MAT HANG QUAN LY THEO SERIAL
                    {
                        //LAY RA DANH SACH SERIAL CUA HANG HOA TUONG UNG
                        filterListStockTransSerialDTO = filterStockTransSerialDTO(stockTransDetailDTO.getTmpStockTransDetailId(), lstStockTransSerialDTO);
                        if (filterListStockTransSerialDTO == null || filterListStockTransSerialDTO.size() < 1) {
                            resultDTO.setMessage(ParamUtils.FAIL);
                            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                            rollback(session, transaction, connection);
                            removeLock(orderActionDTO);
                            rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                            return resultDTO;
                        }
                        //Insert giao dich chi tiet serial
                        //khoi tao list serial duoc nhap kho cho mat hang nay
                        for (StockTransSerialDTO stockTransSerialDTO : filterListStockTransSerialDTO) {
                            //Cap nhat Id giao dich, ID chi tiet giao dichj
                            stockTransSerialDTO.setStockTransId(stockTransId);
                            stockTransSerialDTO.setStockTransDetailId(stockTransDetailId);
                            stockTransSerialDTO.setStockTransDate(sysdate);
                            stockTransSerialDTO.setCreateDatetime(sysdate);
                            stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                            stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                        }
                        //Insert batch VAO KHO STOCK_GOODS_SERIAL
                        resultDTO = importStockGoodsListSerial(stockTransDTO, (StockTransDetailDTO) DataUtil.cloneObject(stockTransDetailDTO), filterListStockTransSerialDTO, session, connection, ParamUtils.GOODS_IMPORT_STATUS.IMPORTED);
                        insertSuccess = resultDTO.getQuantitySucc();
                        insertFail = resultDTO.getQuantityFail();
                        //amountIssue = resultDTO.getAmountIssue();

                        //kiem tra so luong thanh cong cua hang hoa
                        if (stockTransDetailDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {
                            amountIssue = resultDTO.getAmountIssue();
                        } else {
                            if ((insertFail != 0 && !firstWrong) || (insertFail == 0)) {
                                indexInsertSuccess = Double.parseDouble(insertSuccess + "");
                                pre = Double.parseDouble(insertFail + "");
                                firstWrong = true;
                            } else if (insertFail != 0 && firstWrong) {
                                indexInsertSuccess = insertSuccess + pre;
                                pre = Double.parseDouble(insertFail + "");
                            }
                            amountIssue = indexInsertSuccess;
                        }
                        //DUA VAO THONG TIN GIAO DICH: STOCK_TRANS_SERIAL

                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction, connection);
                            removeLock(orderActionDTO);
                            rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                            return resultDTO;
                        }
                        //END FOR FILTER LIST
                        //CAP NHAT LAI STOCK_TRANS_DETAIL VOI SO LUONG INSERT THANH CONG
                        int isUpdate = stockGoodsSerialBusiness2.updateStockTransDetail(stockTransDetailId, amountIssue, connection);
                        //neu update khong thanh cong
                        if (isUpdate < 1) {
                            resultDTO.setMessage(ParamUtils.FAIL);
                            resultDTO.setKey("UPDATE_STOCK_TRANS_DETAIL_ERROR");
                            rollback(session, transaction, connection);
                            removeLock(orderActionDTO);
                            rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                            return resultDTO;
                        }
                        //

                        stockTransDetailDTO.setAmountReal(amountIssue.toString().replace(".0", ""));
                        //CAP NHAT SO LUONG TONG CONG CUA HANG HOA
                        resultDTO = importStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction, connection);
                            removeLock(orderActionDTO);
                            rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                            return resultDTO;
                        }
                    }
                }
            }
            //neu hoan toan k co loi -> commit
            commit(session, transaction, connection);

        } catch (Exception e) {
            rollback(session, transaction, connection);
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setMessage(e.getMessage());
            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
            rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
            removeLock(orderActionDTO);
            return resultDTO;
        } finally {
            try {
                if (session.isOpen()) {
                    session.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        resultDTO.setQuantitySucc(insertSuccess);
        resultDTO.setQuantityFail(insertFail);
        resultDTO.setId(stockTransId);
        removeLock(orderActionDTO);
        return resultDTO;
    }

    //cap nhat lai order - orderaction
    public String rollBackOrderAndOrderAction(OrdersDTO order, OrderActionDTO orderAction, String orderActionStatus) {
        order.setOrderStatus(ORDER_STATUS_COMMAN_CREATED);
        orderAction.setStatus(orderActionStatus);
        return WSOrders.updateOrdersAndOrderAction(order, orderAction);
    }

    public String updateOrderAndOrderAction(OrdersDTO order, OrderActionDTO orderAction) {
        order.setOrderStatus(ORDER_STATUS_IMPORTED_EXPORTED);
        orderAction.setStatus(ORDER_ACTION_STATUS_EXPORTED);
        return WSOrders.updateOrdersAndOrderAction(order, orderAction);
    }

    //EXPORT STOCK 
    public ResultDTO exportStockGoodsTransfer(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetailDTO,
            List<StockTransSerialDTO> lstStockTransSerialDTO, Session session, Transaction transaction,
            Map<String, List<StockGoods>> map, Map<String, List<StockGoodsSerialStrip>> mapSerialTrip) {
        String stockTransDetailId;
        List<StockTransSerialDTO> lstFilterListStockTransSerialDTO;
        String message = "";
        String stockTransCode;
        String sysdate;
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        Map<String, GoodsDTO> mapGoodsDTO;

        sysdate = stockGoodsBusiness.getSysDate(formatDate);
        String stockTransId;
        GoodsDTO goodsDTO;
        //
        try {
            //Insert giao dich kho
            stockTransCode = ParamUtils.CODE_EXPORT_STOCK + stockTransBusiness.getSequence("STOCK_TRANS_SEQ");
            stockTransDTO.setStockTransCode(stockTransCode);
            stockTransDTO.setCreateDatetime(sysdate);
            stockTransDTO.setStockTransDate(sysdate);
            //Set trang thai = 1 <--> da nhap xuat
//            duyot: 21/03 k set lai trang thai giao dich
//            stockTransDTO.setStockTransStatus("1");
            resultDTO = stockTransBusiness.createObjectSession(stockTransDTO, session);
            stockTransId = resultDTO.getId();
            stockTransDTO.setStockTransId(stockTransId);
            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                rollback(session, transaction);
                return resultDTO;
            }
            //
            //Lay danh sach hang hoa yeu cau day vao map
            String goodsIdList = getGoodsIdList(lstStockTransDetailDTO);
            List<ConditionBean> lstConditionBean = new ArrayList<>();
            lstConditionBean.add(new ConditionBean("custId", ParamUtils.OP_EQUAL, stockTransDTO.getCustId(), ParamUtils.TYPE_NUMBER));
            lstConditionBean.add(new ConditionBean("goodsId", ParamUtils.OP_IN, goodsIdList, ParamUtils.TYPE_NUMBER));
            List<GoodsDTO> lstGoodsDTO = goodsBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "asc", "code");
            mapGoodsDTO = DataUtil.putGoodsToMap(lstGoodsDTO);
            //Giao dich chi tiet   
            int index = 0;
            for (StockTransDetailDTO stockTransDetailDTO : lstStockTransDetailDTO) {
                stockTransDetailDTO.setStockTransId(stockTransId);
                stockTransDetailDTO.setStockTransDate(sysdate);
                stockTransDetailDTO.setCreateDatetime(sysdate);
                goodsDTO = mapGoodsDTO.get(stockTransDetailDTO.getGoodsId());
                if (goodsDTO == null) {
                    rollback(session, transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                    return resultDTO;
                }
                //Insert chi tiet giao dich kho
                stockTransDetailDTO.setGoodsCode(goodsDTO.getCode());
                stockTransDetailDTO.setGoodsName(goodsDTO.getName());
                stockTransDetailDTO.setGoodsIsSerial(goodsDTO.getIsSerial());
                stockTransDetailDTO.setGoodsIsSerialStrip(goodsDTO.getIsSerialStrip());
                resultDTO = stockTransDetailBusiness.createObjectSession(stockTransDetailDTO, session);
                stockTransDetailId = resultDTO.getId();
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback(session, transaction);
                    return resultDTO;
                }
                //HANG K SERIAL
                if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                    //Cap nhat mat hang theo so luong 
                    resultDTO = exportStockGoodsForTrans(stockTransDTO, stockTransDetailDTO, session, map, index);

                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //
                    index++;
                    //Cap nhat so luong tong cong
                    resultDTO = exportStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        return resultDTO;
                    }

                } else //HANG QUAN LY SERIAL
                {
                    //Lay danh sach chi tiet giao dich serial theo chi tiet giao dich
                    lstFilterListStockTransSerialDTO = filterStockTransSerialDTO(stockTransDetailDTO.getTmpStockTransDetailId(), lstStockTransSerialDTO);
                    //Insert giao dich chi tiet serial
                    for (StockTransSerialDTO stockTransSerialDTO : lstFilterListStockTransSerialDTO) {
                        //Id giao dich, ID chi tiet giao dichj
                        stockTransSerialDTO.setStockTransId(stockTransId);
                        stockTransSerialDTO.setStockTransDetailId(stockTransDetailId);
                        stockTransSerialDTO.setStockTransDate(sysdate);
                        stockTransSerialDTO.setCreateDatetime(sysdate);
                        //Tao chi tiet giao dich serial
                        stockTransSerialDTO.setGoodsId(goodsDTO.getGoodsId());
                        stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                        stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                        resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction);
                            return resultDTO;
                        }
                        //Xuat kho kho hang hoa theo serial
                        if (stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                            //
                            resultDTO = exportStockGoodsSerial(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, session, mapSerialTrip);
                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                rollback(session, transaction);
                                return resultDTO;
                            }
                        }
                    }
                    //Cap nhat Kho hang hoa theo so luong
                    resultDTO = exportStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        return resultDTO;
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            resultDTO.setMessage(ParamUtils.FAIL);
            rollback(session, transaction);
            return resultDTO;

        }
        resultDTO.setId(stockTransId);
        return resultDTO;
    }

    //IMPORT STOCK 
    public ResultDTO importStockGoodsTransfer(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetailDTO,
            List<StockTransDetailDTO> lstExportedStockTransDetail, List<StockTransSerialDTO> lstStockTransSerialDTO,
            List<StockTransSerialDTO> lstSerialExport, Session session, Transaction transaction,
            Map<String, List<StockGoods>> map, Map<String, List<StockGoodsSerialStrip>> mapSerialTrip,
            List<GoodsInTicketDTO> lstGoods) {
        //lstSerialExport cap nhat lai goods_id voi hang hoa trong lstSerial export                    
        String stockTransDetailId;
        List<StockTransSerialDTO> filterListStockTransSerialDTO;
        String stockTransCode;
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        //
        Map<String, GoodsDTO> mapGoodsDTO;

        int insertSuccess = 0;
        int insertFail = 0;
        Double amountIssue = 0D;
        //
        String sysdate;
        String stockTransId;

        sysdate = stockGoodsBusiness.getSysDate(formatDate);
        GoodsDTO goodsDTO;
        //
        try {
            if (DataUtil.isStringNullOrEmpty(stockTransDTO.getStockTransCode())) {
                //--> Insert giao dich kho
                stockTransCode = ParamUtils.CODE_IMPORT_STOCK + stockTransBusiness.getSequence("STOCK_TRANS_SEQ");
                stockTransDTO.setStockTransCode(stockTransCode);
                stockTransDTO.setCreateDatetime(sysdate);
                stockTransDTO.setStockTransDate(sysdate);
                //duyot: 21/03/2016: k set lai 
//                stockTransDTO.setStockTransStatus("1");
                resultDTO = stockTransBusiness.createObjectSession(stockTransDTO, session);
                stockTransId = resultDTO.getId();
                stockTransDTO.setStockTransId(stockTransId);
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback(session, transaction);
                    return resultDTO;
                }
            }
            //Lay danh sach hang hoa yeu cau day vao map
            String goodsIdList = getGoodsIdList(lstStockTransDetailDTO);
            List<ConditionBean> lstConditionBean = new ArrayList<>();
            lstConditionBean.add(new ConditionBean("custId", ParamUtils.OP_EQUAL, stockTransDTO.getCustId(), ParamUtils.TYPE_NUMBER));
            lstConditionBean.add(new ConditionBean("goodsId", ParamUtils.OP_IN, goodsIdList, ParamUtils.TYPE_NUMBER));
            List<GoodsDTO> lstGoodsDTO = goodsBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "", "code");
            mapGoodsDTO = DataUtil.putGoodsToMap(lstGoodsDTO);
            //Giao dich chi tiet  
            int index = 0;
            //chay vong lap danh sach hang can nhap -> moi hang can nhap se co map chi so tuong ung voi hang da xuat tuong ung
            //dung map de lay ra danh sach stock_goods tuong ung
            for (StockTransDetailDTO stockTransDetailDTO : lstStockTransDetailDTO) {
                stockTransDetailDTO.setStockTransId(stockTransDTO.getStockTransId());
                stockTransDetailDTO.setStockTransDate(sysdate);
                stockTransDetailDTO.setCreateDatetime(sysdate);
                goodsDTO = mapGoodsDTO.get(stockTransDetailDTO.getGoodsId());
                if (goodsDTO == null) {
                    rollback(session, transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                    return resultDTO;
                }
                //Insert chi tiet giao dich kho STOCK_TRANS_DETAIL
                stockTransDetailDTO.setGoodsCode(goodsDTO.getCode());
                stockTransDetailDTO.setGoodsName(goodsDTO.getName());
                stockTransDetailDTO.setGoodsIsSerial(goodsDTO.getIsSerial());
                stockTransDetailDTO.setGoodsIsSerialStrip(goodsDTO.getIsSerialStrip());
                resultDTO = stockTransDetailBusiness.createObjectSession(stockTransDetailDTO, session);
                stockTransDetailId = resultDTO.getId();
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback(session, transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.GOODS_IS_NOT_EXIST);
                    return resultDTO;
                }
                //HANG K SERIAL -> cap nhat vao bang STOCK_GOODS
                if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                    //CAP NHAT STOCK_GOODS
                    resultDTO = importStockGoods(stockTransDTO, stockTransDetailDTO, lstExportedStockTransDetail.get(index), session, map, index);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //duyot: 28/01/2016: truong hop lap lenh -> khong cap nhat total
                    //CAP NHAT STOCK_GOODS_TOTAL
//                    resultDTO = importStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
//                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
//                        rollback(session, transaction);
//                        return resultDTO;
//                    }
                    index++;
                } else //HANG SERIAL
                {
                    //LAY RA SERIAL THUOC DETAIL
                    filterListStockTransSerialDTO = filterStockTransSerialDTO(stockTransDetailDTO.getTmpStockTransDetailId(), lstStockTransSerialDTO);
                    if (filterListStockTransSerialDTO == null || filterListStockTransSerialDTO.size() < 1) {
                        resultDTO.setMessage(ParamUtils.FAIL);
                        resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //Insert giao dich chi tiet serial
                    amountIssue = 0D;
                    StockTransSerialDTO filteredExportSerial = null;
                    for (StockTransSerialDTO stockTransSerialDTO : filterListStockTransSerialDTO) {
                        //LAY RA EXPORT SERIAL (GOODS MOI CHO SERIAL)
                        for (StockTransSerialDTO i : lstSerialExport) {
                            if (i.getFromSerial().equalsIgnoreCase(stockTransSerialDTO.getFromSerial())
                                    && i.getToSerial().equalsIgnoreCase(stockTransSerialDTO.getToSerial())) {
                                filteredExportSerial = i;
                                break;
                            }
                        }
                        if (filteredExportSerial == null) {
                            resultDTO.setMessage(ParamUtils.FAIL);
                            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                            rollback(session, transaction);
                            return resultDTO;
                        }
                        //Id giao dich, ID chi tiet giao dichj
                        stockTransSerialDTO.setStockTransId(stockTransDTO.getStockTransId());
                        stockTransSerialDTO.setStockTransDetailId(stockTransDetailId);
                        stockTransSerialDTO.setStockTransDate(sysdate);
                        stockTransSerialDTO.setCreateDatetime(sysdate);
                        //Insert kho hang hoa theo serial
                        //CAP NHAT STOCK_GOODS_SERIAL_ STOCK_GOODS_SERIAL_TRIP
                        if (!stockTransDetailDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL)) {//NEU LA SERIAL
                            resultDTO = importStockGoodsSerial(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, filteredExportSerial, session);
                            //                            
                        } else {//NEU LA SERIAL STRIP
                            //LAY RA MAP STOCK_GOODS_SERIAL_TRIP DA XUAT
                            String key = lstExportedStockTransDetail.get(index).getGoodsCode() + stockTransSerialDTO.getFromSerial();
                            List<StockGoodsSerialStrip> lstExportedTrip = mapSerialTrip.get(key);
                            resultDTO = importStockGoodsSerialStrip(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, lstExportedTrip, session);
                        }
                        if (!resultDTO.getMessage().equalsIgnoreCase(ParamUtils.SUCCESS)) {
                            //thienng1 - addby 28/04/2016
                            if (ParamUtils.ERROR_MESSAGE.GET_LIST_INFO_FAIL.equals(resultDTO.getKey())) {
                                goodsDTO = mapGoodsDTO.get(resultDTO.getLstStockGoodsSerialInforDTO().get(0).getGoodsId());
                                resultDTO.getLstStockGoodsSerialInforDTO().get(0).setGoodsCode(goodsDTO.getCode());
                                resultDTO.getLstStockGoodsSerialInforDTO().get(0).setGoodsName(goodsDTO.getName());
                            }
                            rollback(session, transaction);
                            return resultDTO;
                        }
                        insertSuccess = insertSuccess + resultDTO.getQuantitySucc();
                        insertFail = insertFail + resultDTO.getQuantityFail();
                        amountIssue = amountIssue + resultDTO.getAmountIssue();
                        //Tao chi tiet giao dich serial
                        //CAP NHAT STOCK_TRANS_SRIAL
                        stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                        stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                        stockTransSerialDTO.setAmountReal(resultDTO.getAmountIssue().toString().replace(".0", ""));
                        stockTransSerialDTO.setQuantity(stockTransSerialDTO.getAmountOrder());
                        resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction);
                            return resultDTO;
                        }
                    }
                    //QuyenDM : Them vao danh sach hang hoa se insert
                    //CAP NHAT LAI STOCK_TRANS_DETAIL VOI SO LUONG INSERT THANH CONG
                    StockTransDetail search = (StockTransDetail) session.load(StockTransDetail.class, Long.parseLong(stockTransDetailId));
                    search.setAmountReal(amountIssue);
                    session.update(search);
                    //
                    stockTransDetailDTO.setAmountReal(amountIssue.toString().replace(".0", ""));
                    GoodsInTicketDTO goods = createGoodsFromDetail(stockTransDetailDTO);
                    //set list serial imported in goods
                    List<StockTransSerialDTO> lstSerial = DataUtil.cloneList(filterListStockTransSerialDTO);
//                    formatSerial(lstSerial);
                    goods.setLstSerial(lstSerial);
                    lstGoods.add(goods);
                    //Ket thuc CAP NHAT LAI STOCK_TRANS_DETAIL VOI SO LUONG INSERT THANH CONG
                    //Cap nhat so luong tong cong
                    //CAP NHAT STOCK_GOODS_TOTAL
                    stockTransDetailDTO.setAmountReal(amountIssue.toString().replace(".0", ""));
//                    duyot: 28/01/2016: bo cap nhat total doi voi tao lenh dong bo
//                    resultDTO = importStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
//                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
//                        rollback(session, transaction);
//                        return resultDTO;
//                    }
                    //
                    index++;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setMessage(e.getMessage());
            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
            rollback(session, transaction);
            return resultDTO;
        }
        //
        resultDTO.setQuantitySucc(insertSuccess);
        resultDTO.setQuantityFail(insertFail);
        resultDTO.setId(stockTransDTO.getStockTransId());
        return resultDTO;
    }

    //--------------SUPPORT FUNCTION FOR IMPORT--------------------
    private ResultDTO importStockGoods(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO,
            StockTransDetailDTO exportedStockTransDetail, Session session, Map<String, List<StockGoods>> map, int index) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        String message = "";
        StockGoodsDTO stockGoodsDTO = new StockGoodsDTO();
        List<StockGoodsDTO> lstStockGoods = new ArrayList<>();
        List<StockGoods> lstExportedStockGoods;
        //Cac tham so dau vao
        String custId = stockTransDTO.getCustId();
        String ownerId = stockTransDTO.getOwnerId();
        String ownerType = stockTransDTO.getOwnerType();
        String goodsId = stockTransDetailDTO.getGoodsId();
        String goodsState = stockTransDetailDTO.getGoodsState();
        String cellCode = stockTransDetailDTO.getCellCode();
        String addInfor = stockTransDetailDTO.getAddInfor();
        String amount = stockTransDetailDTO.getAmountReal();
        String amountIssue = stockTransDetailDTO.getAmountReal();
        String partnerId = stockTransDTO.getPartnerId();
        String barcode = stockTransDetailDTO.getBarcode();
        String bincode = stockTransDetailDTO.getBincode();
        //duyot: neu k co cellcode, bincode -> lay lai thong tin xuat
        //neu k nhap cellCode
        StockGoods keyStockGoods = new StockGoods();
        keyStockGoods.setGoodsId(Long.parseLong(exportedStockTransDetail.getGoodsId()));
        keyStockGoods.setGoodsState(exportedStockTransDetail.getGoodsState());
        keyStockGoods.setBarcode(exportedStockTransDetail.getBarcode());
        keyStockGoods.setCellCode(exportedStockTransDetail.getCellCode());
        if (DataUtil.isStringNullOrEmpty(cellCode) || DataUtil.isStringNullOrEmpty(bincode)) {
            //lay ra danh sach stock_goods hang da xuat
            lstExportedStockGoods = map.get(initKey(keyStockGoods, index));
            //tao ra danh sach stock_goods cho hang nhap voi cell code lay tu hang xuat
            Double amountExported = Double.parseDouble(amount);
            //neu con amount
            int i = 0;
            String exportedCellcode = "";
            StockGoods temp = new StockGoods();
            while (amountExported > 0) {
                if (lstExportedStockGoods.size() > i) {
                    temp = lstExportedStockGoods.get(i);
                    exportedCellcode = lstExportedStockGoods.get(i).getCellCode();
                }
                //Set cac gia tri cho stockGoods
                stockGoodsDTO = new StockGoodsDTO();

                stockGoodsDTO.setCustId(custId);
                stockGoodsDTO.setOwnerId(ownerId);
                stockGoodsDTO.setOwnerType(ownerType);
                stockGoodsDTO.setGoodsId(goodsId);
                stockGoodsDTO.setGoodsState(goodsState);
                stockGoodsDTO.setAddInfor(addInfor);
                stockGoodsDTO.setPartnerId(partnerId);
                stockGoodsDTO.setChangeDate(stockTransDTO.getStockTransDate());
                stockGoodsDTO.setImportDate(stockTransDTO.getStockTransDate());
                //lay barcode - bincode tu stock_goods
                stockGoodsDTO.setBarcode(temp.getBarcode());
                stockGoodsDTO.setBincode(temp.getBarcode());
                stockGoodsDTO.setCellCode(exportedCellcode);

                if (amountExported > temp.getAmountIssue()) {
                    //lay so luong tu hang duoc nhap
                    stockGoodsDTO.setAmount(temp.getAmountIssue() + "");
                    stockGoodsDTO.setAmountIssue(temp.getAmountIssue() + "");
                } else {
                    stockGoodsDTO.setAmount(amountExported + "");
                    stockGoodsDTO.setAmountIssue(amountExported + "");
                }
                //
                lstStockGoods.add(stockGoodsDTO);
                //tang index
                i++;
                //tru so luong export
                amountExported -= temp.getAmountIssue();
            }
        } else {//neu co nhap cell code
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
            stockGoodsDTO.setCellCode(cellCode);

            lstStockGoods.add(stockGoodsDTO);
        }

        //goi ham insert list stock_goods
        resultDTO = stockGoodsInterface.importListStockGoods(lstStockGoods, session);
        //
        return resultDTO;
        //
    }

    //Insert or update Hang hoa theo serial
    private ResultDTO importStockGoodsSerial(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, StockTransSerialDTO importStockTransSerial,
            StockTransSerialDTO exportedStockTransSerial, Session session) {

        ResultDTO resultDTO = new ResultDTO();
        StockGoodsSerialStripDTO importStrip = new StockGoodsSerialStripDTO();
        StockGoodsSerialDTO stockGoodsSerialDTO = new StockGoodsSerialDTO();

        //SET EXPORTED SERIAL
        StockGoodsSerialDTO exportedStockGoodsSerialDTO = new StockGoodsSerialDTO();
        exportedStockGoodsSerialDTO.setCustId(stockTransDTO.getCustId());
        exportedStockGoodsSerialDTO.setOwnerId(stockTransDTO.getOwnerId());
        exportedStockGoodsSerialDTO.setGoodsId(exportedStockTransSerial.getGoodsId());
        exportedStockGoodsSerialDTO.setSerial(exportedStockTransSerial.getFromSerial());
        //duyot: 29/01/2016: set orderid
        exportedStockGoodsSerialDTO.setOrderId(stockTransDTO.getOrderIdList());
        exportedStockGoodsSerialDTO.setImportStockTransId(stockTransDTO.getStockTransId());
        //SET IMPORT SERIAL
        stockGoodsSerialDTO.setGoodsId(importStockTransSerial.getGoodsId());
        stockGoodsSerialDTO.setGoodsState(importStockTransSerial.getGoodsState());
        //duyot: 28/01/2016: cap nhat trang thai khi tao lenh -> trang thai bang
//        stockGoodsSerialDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
        stockGoodsSerialDTO.setStatus(Constants.STATUS_SERIAL_WAIT_STOCK);
        //CAP NHAT THONG TIN GOODS_ID, GOODS_STATE,STATUS)
        //duyot- sua cho truong hop serial gep dai
        String fromSerial = exportedStockTransSerial.getFromSerial();
        String toSerial = exportedStockTransSerial.getToSerial();

        resultDTO = stockGoodsSerialBusiness2.updateNewGoods(stockGoodsSerialDTO, exportedStockGoodsSerialDTO, fromSerial, toSerial, session);

//        }
        //
        return resultDTO;
    }

    //IMPORT LAI HANG SERIAL STRIP
    public ResultDTO importStockGoodsSerialStrip(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO,
            StockTransSerialDTO importStockTransSerial, List<StockGoodsSerialStrip> exportedTrip, Session session) {
        ResultDTO resultDTO;
        StockGoodsSerialStripDTO importStrip = new StockGoodsSerialStripDTO();

        //SET IMPORT TRIP
        importStrip.setGoodsId(importStockTransSerial.getGoodsId());
        importStrip.setOwnerType(stockTransDTO.getOwnerType());
        importStrip.setGoodsState(importStockTransSerial.getGoodsState());
        importStrip.setStatus(Constants.STATUS_SERIAL_WAIT_STOCK);
        importStrip.setQuantity(importStockTransSerial.getAmountReal());
        //duyot: 29/01/2016
        importStrip.setOrderId(stockTransDTO.getOrderIdList());
        importStrip.setImportStockTransId(stockTransDTO.getStockTransId());
        //duyot: 30/01/2016: cap nhat lay thong tin cell code nhap
        importStrip.setCellCode(importStockTransSerial.getCellCode());

        //CAP NHAT
        resultDTO = stockGoodsSerialStripBusiness2.updateNewListGoods(importStrip, exportedTrip, session);

        return resultDTO;

    }

    //Insert or update Hang hoa theo so luong cho giao dich dieu chuyen
    private ResultDTO exportStockGoodsForTrans(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, Session session, Map<String, List<StockGoods>> map, int index) {
        ResultDTO resultDTO = new ResultDTO();
        String message = ParamUtils.SUCCESS;
        String key = "";
        StockGoodsDTO stockGoodsDTO = new StockGoodsDTO();
        //Cac tham so dau vao
        String custId = stockTransDTO.getCustId();
        String ownerId = stockTransDTO.getOwnerId();
        String ownerType = stockTransDTO.getOwnerType();
        String goodsId = stockTransDetailDTO.getGoodsId();
        String amount = stockTransDetailDTO.getAmountReal();
        String amountIssue = stockTransDetailDTO.getAmountReal();
        //
        String goodsState = stockTransDetailDTO.getGoodsState();
        String cellCode;
        String barcode;
        String bincode;
        String addInfor;
        //
        cellCode = stockTransDetailDTO.getCellCode();
        barcode = stockTransDetailDTO.getBarcode();
        bincode = stockTransDetailDTO.getBincode();
        addInfor = stockTransDetailDTO.getAddInfor();
        //
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
        stockGoodsDTO.setChangeDate(stockTransDTO.getStockTransDate());
        //Cap nhat kho hang chi tiet 
        List<StockGoods> lstExported = stockGoodsInterface.exportStockGoodsForTransfer(stockGoodsDTO, session);
        //tra ve list stock_goods da export
        map.put(initKey(stockGoodsDTO, index), lstExported);
        if (lstExported == null) {
            message = String.valueOf(goodsId) + "," + goodsState;
            key = ParamUtils.NOT_ENOUGH_AMOUNT;
        }
        resultDTO.setMessage(message);
        resultDTO.setKey(key);
        //
        return resultDTO;
    }

    //init key from stock_goods using barcode
    public String initKey(StockGoods stockgoods, int index) {
        StringBuilder strKey = new StringBuilder("");
        strKey.append(index).append("");
        strKey.append(stockgoods.getGoodsId());
        strKey.append(stockgoods.getGoodsState());
        if (!StringUtils.isStringNullOrEmpty(stockgoods.getBarcode())) {
            strKey.append(stockgoods.getBarcode());
        }
        if (!StringUtils.isStringNullOrEmpty(stockgoods.getCellCode())) {
            strKey.append(stockgoods.getCellCode());
        }

        return strKey.toString();
    }

    public String initKey(StockGoodsDTO stockgoods, int index) {
        StringBuilder strKey = new StringBuilder("");
        strKey.append(index).append("");
        strKey.append(stockgoods.getGoodsId());
        strKey.append(stockgoods.getGoodsState());
        if (!StringUtils.isStringNullOrEmpty(stockgoods.getBarcode())) {
            strKey.append(stockgoods.getBarcode());
        }
        if (!StringUtils.isStringNullOrEmpty(stockgoods.getCellCode())) {
            strKey.append(stockgoods.getCellCode());
        }

        return strKey.toString();
    }

    //
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

    //Insert or update Hang hoa theo serial
    private ResultDTO exportStockGoodsSerial(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, StockTransSerialDTO stockTransSerialDTO, Session session, Map<String, List<StockGoodsSerialStrip>> map) {
        ResultDTO resultDTO = new ResultDTO();

        StockGoodsSerialStripDTO oldStockGoodsSerialStripDTO = new StockGoodsSerialStripDTO();
        StockGoodsSerialStripDTO newStockGoodsSerialStripDTO = new StockGoodsSerialStripDTO();
        //Thong tin cao nhat
        newStockGoodsSerialStripDTO.setCustId(stockTransDTO.getCustId());
        newStockGoodsSerialStripDTO.setOwnerId(stockTransDTO.getOwnerId());
        newStockGoodsSerialStripDTO.setOwnerType(stockTransDTO.getOwnerType());
        newStockGoodsSerialStripDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
        newStockGoodsSerialStripDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
        //duyot: 28/01/2016: cap nhat: dieu chuyen hang kit -> trang thai cho xuat kho
        newStockGoodsSerialStripDTO.setStatus(Constants.STATUS_SERIAL_WAIT_STOCK);
        //
        newStockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountReal());
        newStockGoodsSerialStripDTO.setChangeUser(stockTransDTO.getTransUserId());
        newStockGoodsSerialStripDTO.setChangeDate(stockTransDTO.getStockTransDate());
        newStockGoodsSerialStripDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
        newStockGoodsSerialStripDTO.setToSerial(stockTransSerialDTO.getToSerial());
        //Cac tham so dau vao        
        oldStockGoodsSerialStripDTO.setCustId(stockTransDTO.getCustId());
        oldStockGoodsSerialStripDTO.setOwnerId(stockTransDTO.getOwnerId());
        oldStockGoodsSerialStripDTO.setOwnerType(stockTransDTO.getOwnerType());
        oldStockGoodsSerialStripDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
        oldStockGoodsSerialStripDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
        oldStockGoodsSerialStripDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
        oldStockGoodsSerialStripDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
        oldStockGoodsSerialStripDTO.setToSerial(stockTransSerialDTO.getToSerial());
        //Mat hang quan ly serial theo dai
        if (stockTransDetailDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {
            //NEU LA SERIAL STRIP
            newStockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountReal());
            List<StockGoodsSerialStrip> lstExported = stockGoodsSerialStripBusiness2.exportStockGoodsSerialStripTransfer(oldStockGoodsSerialStripDTO.toModel(), newStockGoodsSerialStripDTO.toModel(), session);
            if (lstExported == null) {
                resultDTO.setMessage(String.valueOf(stockTransDetailDTO.getGoodsId()) + "," + stockTransDetailDTO.getGoodsState());
                resultDTO.setKey(ParamUtils.NOT_ENOUGH_AMOUNT);
                //            message = ParamUtils.FAIL;
                //            key = ParamUtils.SYSTEM_ERROR;
                //return resultDTO;
            } else if (lstExported.size() == 1 && lstExported.get(0).getGoodsId() == null) {
                resultDTO.setMessage(String.valueOf(stockTransDetailDTO.getGoodsId()) + "," + stockTransDetailDTO.getGoodsState());
                resultDTO.setKey(ParamUtils.NOT_ENOUGH_AMOUNT);
                resultDTO.setFromSerial(lstExported.get(0).getFromSerial());
                resultDTO.setToSerial(lstExported.get(0).getToSerial());
            } else {
                //put to map
                String key = stockTransSerialDTO.getGoodsCode() + stockTransSerialDTO.getFromSerial();
                map.put(key, lstExported);
                //set resutl
                resultDTO.setMessage(ParamUtils.SUCCESS);
            }

        } else //Mat hang quan ly serial don le 
        {
            //Cap nhat serial hang hoa
            resultDTO = stockGoodsSerialBusiness2.exportStockGoodsSerial(oldStockGoodsSerialStripDTO, newStockGoodsSerialStripDTO, session);
        }
        //
        return resultDTO;
    }

    //Add by QuyenDM
    private String getGoodsCodeList(List<StockTransDetailDTO> lstStockTransDetailDTO) {
        String goodsCodeList = "";
        for (StockTransDetailDTO stockTransDetailDTO : lstStockTransDetailDTO) {
            if (!goodsCodeList.contains(stockTransDetailDTO.getTransfersGoodsCode())) {
                goodsCodeList = goodsCodeList + "," + stockTransDetailDTO.getTransfersGoodsCode();
            }
        }
        return goodsCodeList.replaceFirst(",", "");
    }

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

    //Modify by ChuDV: 20/05/2015
//    private List<StockTransSerialDTO> filterStockTransSerialDTO(String goodsId, String goodsState, List<StockTransSerialDTO> lstStockTransSerialDTO) {
//        List<StockTransSerialDTO> returnListStockTransSerialDTO = new ArrayList();
//        if (lstStockTransSerialDTO != null && lstStockTransSerialDTO.size() > 0) {
//            for (StockTransSerialDTO stockTransSerialDTO : lstStockTransSerialDTO) {
//                if (stockTransSerialDTO.getGoodsId().equals(goodsId) && stockTransSerialDTO.getGoodsState().equals(goodsState)) {
//                    returnListStockTransSerialDTO.add(stockTransSerialDTO);
//                }
//            }
//        }
//        return returnListStockTransSerialDTO;
//    }
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

    //Insert or update Hang hoa theo so luong
    private ResultDTO importStockGoods(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, Session session, String goodsStatus) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
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
        //Cap nhat kho hang neu da ton tai
        resultDTO = stockGoodsInterface.importStockGoods(stockGoodsDTO, session);
        //
        return resultDTO;
    }

    //Insert Hang khong serial(Su dung trong dong bo voi BCCS,KTTS)
    private ResultDTO synImportStockGoods(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
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
        //bo sung trang thai hang va ID giao dich
        stockGoodsDTO.setStatus(Constants.STATUS_SERIAL_WAIT_STOCK);
        stockGoodsDTO.setImportStockTransId(stockTransDTO.getStockTransId());
        stockGoodsDTO.setOrderId(stockTransDTO.getOrderIdList());
        //Cap nhat kho hang neu da ton tai
        resultDTO = stockGoodsInterface.importStockGoods(stockGoodsDTO, session);
        //
        return resultDTO;
    }

    private ResultDTO importStockGoodsTotal(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, Session session) {
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
        stockGoodsTotalDTO.setChangeDate(stockTransDTO.getStockTransDate());
        //Cap nhat kho hang neu da ton tai
        resultDTO = stockGoodsTotalInterface.importStockGoodsTotal(stockGoodsTotalDTO, session);
        //
        return resultDTO;
    }

    //Insert or update Hang hoa theo serial
    private ResultDTO importStockGoodsSerial(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, StockTransSerialDTO stockTransSerialDTO, Session session
    ) {
        /*
         - mat hang theo dai -> nhap binh thuong
         - mat hang don le -> nhap theo batch
         */
        int insertSuccess;
        int insertFail;
        Double amountIssue;
        //
        ResultDTO resultDTO;
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
            stockGoodsSerialStripDTO.setChangeDate(stockTransDTO.getStockTransDate());
            stockGoodsSerialStripDTO.setImportDate(stockTransDTO.getStockTransDate());
            stockGoodsSerialStripDTO.setSaleDate("");
            stockGoodsSerialStripDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
            stockGoodsSerialStripDTO.setToSerial(stockTransSerialDTO.getToSerial());
            stockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountOrder());
            //
            stockGoodsSerialStripDTO.setPartnerId(stockTransDTO.getPartnerId());
            stockGoodsSerialStripDTO.setImportStockTransId(stockTransDTO.getStockTransId());

            resultDTO = stockGoodsSerialStripBusiness2.importStockGoodsSerialStrip(stockGoodsSerialStripDTO, stockTransDTO.getStockTransId(), session);
            //
            insertSuccess = resultDTO.getQuantitySucc();
            insertFail = resultDTO.getQuantityFail();
            amountIssue = resultDTO.getAmountIssue();
            //DUA VAO THONG TIN GIAO DICH: STOCK_TRANS_SERIAL
//            stockTransSerialDTO.setAmountReal(amountIssue.toString().replace(".0", ""));
//            stockTransSerialDTO.setQuantity(stockTransSerialDTO.getAmountReal());
//            resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
            //luu lai thong tin so luong nhap thanh cong
            resultDTO.setQuantitySucc(insertSuccess);
            resultDTO.setQuantityFail(insertFail);
            resultDTO.setAmountIssue(amountIssue);
            //
        } else //HANG SERIAL DON LE
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
            stockGoodsSerialDTO.setChangeDate(stockTransDTO.getStockTransDate());
            stockGoodsSerialDTO.setImportDate(stockTransDTO.getStockTransDate());
            stockGoodsSerialDTO.setSaleDate("");
            //            
            stockGoodsSerialDTO.setPartnerId(stockTransDTO.getPartnerId());
            stockGoodsSerialDTO.setImportStockTransId(stockTransDTO.getStockTransId());

            resultDTO = stockGoodsSerialBusiness2.importStockGoodsSerial(stockGoodsSerialDTO, stockTransDTO.getStockTransId(), stockTransSerialDTO.getFromSerial(), stockTransSerialDTO.getToSerial(), session);
        }
        return resultDTO;
    }

    //Insert or update Hang hoa theo serial
    //DUYOT: NOTE: STATUS GIONG STATUS CUA STOCK_TRANS
    private ResultDTO importStockGoodsListSerial(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, List<StockTransSerialDTO> lstStockTransSerialDTO, Session session, Connection connection, String serialStatus
    ) {
        int insertSuccess = 0;
        int insertFail = 0;
        Double amountIssue = 0d;
        //
        ResultDTO resultDTO = new ResultDTO();
        StockGoodsSerialStripDTO stockGoodsSerialStripDTO;
        List<StockGoodsSerialInforDTO> lstSerialStripError = new ArrayList();
        /*
         - mat hang theo dai -> nhap binh thuong
         - mat hang don le -> nhap theo batch
         */
        if (stockTransDetailDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {
            //NEU LA HANG THEO DAI -> nhap bt
            for (StockTransSerialDTO stockTransSerialDTO : lstStockTransSerialDTO) {
                stockGoodsSerialStripDTO = new StockGoodsSerialStripDTO();
                //stockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountReal());
                stockGoodsSerialStripDTO.setCustId(stockTransDTO.getCustId());
                stockGoodsSerialStripDTO.setOwnerId(stockTransDTO.getOwnerId());
                stockGoodsSerialStripDTO.setOwnerType(stockTransDTO.getOwnerType());
                stockGoodsSerialStripDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
                stockGoodsSerialStripDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
                stockGoodsSerialStripDTO.setStatus(stockTransDTO.getStockTransStatus());
                stockGoodsSerialStripDTO.setSaleType("");
                stockGoodsSerialStripDTO.setChangeUser("");
                stockGoodsSerialStripDTO.setPrice(null);
                stockGoodsSerialStripDTO.setChannelTypeId(null);
                stockGoodsSerialStripDTO.setBarcode(stockTransSerialDTO.getBarcode());
                stockGoodsSerialStripDTO.setCellCode(stockTransSerialDTO.getCellCode());
                stockGoodsSerialStripDTO.setBincode(stockTransSerialDTO.getBincode());
                stockGoodsSerialStripDTO.setAddInfor(stockTransSerialDTO.getAddInfor());
                stockGoodsSerialStripDTO.setChangeDate(stockTransDTO.getStockTransDate());
                stockGoodsSerialStripDTO.setImportDate(stockTransDTO.getStockTransDate());
                stockGoodsSerialStripDTO.setSaleDate("");
                stockGoodsSerialStripDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
                stockGoodsSerialStripDTO.setToSerial(stockTransSerialDTO.getToSerial());
                stockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountOrder());
                //CAP NHAT ORDER_ID , STOCK_TRANS_ID
                stockGoodsSerialStripDTO.setOrderId(stockTransDTO.getOrderIdList());
                stockGoodsSerialStripDTO.setImportStockTransId(stockTransDTO.getStockTransId());
                //set partner id
                stockGoodsSerialStripDTO.setPartnerId(stockTransDTO.getPartnerId());

                resultDTO = stockGoodsSerialStripBusiness2.importStockGoodsSerialStripConnection(stockGoodsSerialStripDTO, stockTransDTO.getStockTransId(), connection);
//                resultDTO = stockGoodsSerialStripBusiness2.importStockGoodsSerialStrip(stockGoodsSerialStripDTO, stockTransDTO.getStockTransId(), session);
                //

                insertSuccess += resultDTO.getQuantitySucc();
                insertFail += resultDTO.getQuantityFail();
                amountIssue += resultDTO.getAmountIssue();
                if (!DataUtil.isListNullOrEmpty(resultDTO.getLstStockGoodsSerialInforDTO())) {
                    lstSerialStripError.addAll(resultDTO.getLstStockGoodsSerialInforDTO());
                }
                //DUA VAO THONG TIN GIAO DICH: STOCK_TRANS_SERIAL
                stockTransSerialDTO.setAmountReal(stockTransSerialDTO.getAmountReal());
                stockTransSerialDTO.setQuantity(stockTransSerialDTO.getAmountReal());
                stockTransSerialDTO.setStockTransDetailId(stockTransDetailDTO.getStockTransDetailId());
                resultDTO = commonBusinessInterface.importStockTransSerialConnection(stockTransSerialDTO, connection);
                //luu gia tri thanh cong
                resultDTO.setQuantitySucc(insertSuccess);
                resultDTO.setQuantityFail(insertFail);
                resultDTO.setAmountIssue(amountIssue);
                if (!DataUtil.isListNullOrEmpty(lstSerialStripError)) {
                    resultDTO.setLstStockGoodsSerialInforDTO(lstSerialStripError);
                }
            }//END FOR

        } else {//serial don le -> nhap theo lo
            resultDTO = stockGoodsSerialBusiness2.importListStockGoodsSerial(stockTransDTO, stockTransDetailDTO, lstStockTransSerialDTO, connection, serialStatus);
//            resultDTO.setMessage(ParamUtils.SUCCESS);
        }
//        }//END FOR LIST SERIAL
        return resultDTO;
    }

    @Override
    @Transactional
    public ResultDTO updateCell(StockTransDTO stockTransDTO, List<StockTransInforDTO> lstStockTransInforDTO) {
        String stockTransId;
        List<StockTransSerialDTO> filterListStockTransSerialDTO;
        ResultDTO resultDTO = new ResultDTO();
        Session session;
        Transaction transaction;
        //resultDTO.setMessage(ParamUtils.SUCCESS);        
        session = sessionFactory.getSession();
        int insertSuccess = 0;
        int insertFail = 0;
        String sysdate = "";
        //
        try {
            sysdate = stockGoodsBusiness.getSysDate(formatDate, session);
            for (StockTransInforDTO stockTransInforDTO : lstStockTransInforDTO) {
                stockTransId = stockTransDTO.getStockTransId();
                //Cap nhat cell mat hang khong co serial
                if (!stockTransInforDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                    resultDTO = stockGoodsInterface.updateCellStockGoods(stockTransInforDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        return resultDTO;
                    }
                    insertSuccess = insertSuccess + resultDTO.getQuantitySucc();
                } else //Cap nhat cell mat hang quan ly serial theo dai
                if (stockTransInforDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {
                    resultDTO = stockGoodsSerialStripBusiness2.updateCellStockGoodsSerialStrip(stockTransInforDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        return resultDTO;
                    }
                    insertSuccess = insertSuccess + resultDTO.getQuantitySucc();
                } else //Cap nhat hang quan ly serial don le
                {
                    resultDTO = stockGoodsSerialBusiness2.updateCellStockGoodsSerial(stockTransInforDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        return resultDTO;
                    }
                    insertSuccess = insertSuccess + resultDTO.getQuantitySucc();
                }
            }
            //Cap nhat trang thai da cap nhat cell
            stockTransDTO.setIsUpdateCell(Constants.CELL_UPDATED_STATUS);
            stockTransBusiness.updateSession(stockTransDTO, session);

        } catch (Exception e) {
            resultDTO.setMessage(ParamUtils.FAIL);
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            return resultDTO;
        }
        //
        resultDTO.setQuantitySucc(insertSuccess);
        return resultDTO;
    }

    //COMMIT
    private void commit(Session session, Transaction transaction) {
        transaction.commit();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

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

    private void rollback(Session session, Transaction transaction) {
        transaction.rollback();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    //
    @Override
    public ResultDTO importStockCustDAO(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetaiDTO, List<StockTransSerialDTO> lstStockTransSerialDTO) {
        return stockGoodsInterface.importStockDAO(stockTransDTO, lstStockTransDetaiDTO, lstStockTransSerialDTO);
    }

    //AddBy ThienNG1 03/08/2015
    @Override
    public ResultDTO reImportStockRecovered(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetailDTO,
            List<StockTransSerialDTO> lstStockTransSerialDTO) {
        String stockTransDetailId;
        List<StockTransSerialDTO> filterListStockTransSerialDTO;
        String message = "";
        String stockTransCode = "";
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        //
        Map<String, GoodsDTO> mapGoodsDTO;

        int insertSuccess = 0;
        int insertFail = 0;
        Double amountIssue = 0D;
        //
        String stockTransId;
        String sysdate = "";
        //
        Session session;
        Transaction transaction;

        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        sysdate = stockGoodsBusiness.getSysDate(formatDate);
        GoodsDTO goodsDTO = new GoodsDTO();
        String synImportRevoke = Constants.STATUS_SERIAL_IN_STOCK;
        //
        try {
            //Insert giao dich kho
            stockTransCode = ParamUtils.CODE_IMPORT_STOCK + stockTransBusiness.getSequence("STOCK_TRANS_SEQ");
            stockTransDTO.setStockTransCode(stockTransCode);
            stockTransDTO.setCreateDatetime(sysdate);
            stockTransDTO.setStockTransDate(sysdate);
            resultDTO = stockTransBusiness.createObjectSession(stockTransDTO, session);
            stockTransId = resultDTO.getId();
            stockTransDTO.setStockTransId(stockTransId);
            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                rollback(session, transaction);
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
                    rollback(session, transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                    return resultDTO;
                }
                //Insert chi tiet giao dich kho
                stockTransDetailDTO.setGoodsCode(goodsDTO.getCode());
                stockTransDetailDTO.setGoodsName(goodsDTO.getName());
                stockTransDetailDTO.setGoodsIsSerial(goodsDTO.getIsSerial());
                stockTransDetailDTO.setGoodsIsSerialStrip(goodsDTO.getIsSerialStrip());
                resultDTO = stockTransDetailBusiness.createObjectSession(stockTransDetailDTO, session);
                stockTransDetailId = resultDTO.getId();
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback(session, transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.GOODS_IS_NOT_EXIST);
                    return resultDTO;
                }
                //Neu la mat hang khong quan ly serial
                if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                    //Cap nhat mat hang theo so luong 
                    resultDTO = reImportStockGoods(stockTransDTO, stockTransDetailDTO, session, synImportRevoke);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //Cap nhat so luong tong cong
                    resultDTO = importStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        return resultDTO;
                    }
                } else //Mat hang Quang ly theo serial
                {
                    //Lay danh sach chi tiet giao dich serial theo chi tiet giao dich
                    filterListStockTransSerialDTO = filterStockTransSerialDTO(stockTransDetailDTO.getTmpStockTransDetailId(), lstStockTransSerialDTO);
                    if (filterListStockTransSerialDTO == null || filterListStockTransSerialDTO.size() < 1) {
                        resultDTO.setMessage(ParamUtils.FAIL);
                        resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                        rollback(session, transaction);
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

                        //Insert kho hang hoa theo serial
                        if (stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                            resultDTO = reImportStockGoodsSerial(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, session, synImportRevoke);
                            insertSuccess = insertSuccess + resultDTO.getQuantitySucc();
                            insertFail = insertFail + resultDTO.getQuantityFail();
                            amountIssue = amountIssue + resultDTO.getAmountIssue();
                            //Tim kiem bang loi theo import_stock_trans_id
                            if (!stockTransDetailDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {//kiem tra danh sach loi voi serial don le
                                List<StockGoodsSerialInforDTO> lstErrorImportRevoke = stockGoodsSerialBusiness2.getListErrorImportRevoke(stockTransId);
                                if (lstErrorImportRevoke != null) {//TH khong loi he thong
                                    //neu hang hoa khong insert duoc vao stock_goods_serial va insert vao bang loi
                                    if (lstErrorImportRevoke.size() > 0) {
                                        resultDTO.setLstStockGoodsSerialInforDTO(lstErrorImportRevoke);
                                        resultDTO.setMessage(ParamUtils.FAIL);
                                        //resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                                        resultDTO.setId(stockTransDTO.getStockTransId());
                                        rollback(session, transaction);
                                        return resultDTO;
                                    }
                                } else {//neu xay ra loi he thong
                                    rollback(session, transaction);
                                    resultDTO.setMessage(ParamUtils.FAIL);
                                    resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                                    resultDTO.setId(stockTransDTO.getStockTransId());
                                    return resultDTO;
                                }
                            }
                            //add by ThienNG1 19/08/2015
                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                rollback(session, transaction);
                                return resultDTO;
                            }
                        }
                        //Tao chi tiet giao dich serial
                        stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                        stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                        stockTransSerialDTO.setAmountReal(resultDTO.getAmountIssue().toString().replace(".0", ""));
                        resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction);
                            return resultDTO;
                        }
                    }
                    //Cap nhat so luong tong cong
                    stockTransDetailDTO.setAmountReal(amountIssue.toString().replace(".0", ""));
                    resultDTO = importStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        return resultDTO;
                    }
                }
            }
            //Tim kiem bang loi theo import_stock_trans_id
            commit(session, transaction);
        } catch (Exception e) {
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setMessage(e.getMessage());
            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
            rollback(session, transaction);
            return resultDTO;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        //
        resultDTO.setQuantitySucc(insertSuccess);
        resultDTO.setQuantityFail(insertFail);
        resultDTO.setId(stockTransId);
        return resultDTO;
    }

    //AddBy ThienNG1 23/09/2015
    public ResultDTO reSyncImportRecovered(StockTransDTO stockTransDTO, String synImportRevoke,
            Session session, Transaction transaction) {
        String stockTransDetailId;
        List<StockTransSerialDTO> filterListStockTransSerialDTO;
        String message = "";
        String stockTransCode = "";
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        //
        Map<String, GoodsDTO> mapGoodsDTO;

        int insertSuccess = 0;
        int insertFail = 0;
        Double amountIssue = 0D;
        //
        String stockTransId;
        String sysdate = "";
        //
        //Session session;
        //Transaction transaction;
        //session = sessionFactory.openSession();
        //transaction = session.getTransaction();
        //transaction.begin();
        sysdate = stockGoodsBusiness.getSysDate(formatDate);
        GoodsDTO goodsDTO = new GoodsDTO();
        //
        List<StockTransDetailDTO> lstStockTransDetailDTO = new ArrayList();
        lstStockTransDetailDTO = stockTransDTO.getLstStockTransDetailDTO();
        try {
            //Insert giao dich kho
            stockTransCode = ParamUtils.CODE_IMPORT_STOCK + stockTransBusiness.getSequence("STOCK_TRANS_SEQ");
            stockTransDTO.setStockTransCode(stockTransCode);
            stockTransDTO.setCreateDatetime(sysdate);
            stockTransDTO.setStockTransDate(sysdate);
            resultDTO = stockTransBusiness.createObjectSession(stockTransDTO, session);
            stockTransId = resultDTO.getId();
            stockTransDTO.setStockTransId(stockTransId);
            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                //rollback(session, transaction);
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
                    //rollback(session, transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.GOODS_IS_NOT_EXIST);
                    return resultDTO;
                }
                //Insert chi tiet giao dich kho
                stockTransDetailDTO.setGoodsCode(goodsDTO.getCode());
                stockTransDetailDTO.setGoodsName(goodsDTO.getName());
                stockTransDetailDTO.setGoodsIsSerial(goodsDTO.getIsSerial());
                stockTransDetailDTO.setGoodsIsSerialStrip(goodsDTO.getIsSerialStrip());
                resultDTO = stockTransDetailBusiness.createObjectSession(stockTransDetailDTO, session);
                stockTransDetailId = resultDTO.getId();
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    //rollback(session, transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(Constants.ERROR_MESSAGE.INSERT_STOCK_TRANS_DETAIL_ERROR);
                    return resultDTO;
                }
                //Neu la mat hang khong quan ly serial
                if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                    //Cap nhat mat hang theo so luong 
                    resultDTO = reImportStockGoods(stockTransDTO, stockTransDetailDTO, session, synImportRevoke);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        //rollback(session, transaction);
                        return resultDTO;
                    }
                    //Dong bo nen khong cap nhat so luong tong cong
                } else //Mat hang Quan ly theo serial
                {
                    //Lay danh sach chi tiet giao dich serial theo chi tiet giao dich
                    filterListStockTransSerialDTO = stockTransDetailDTO.getLstStockTransSerialDTO();
                    if (filterListStockTransSerialDTO == null || filterListStockTransSerialDTO.size() < 1) {
                        resultDTO.setMessage(ParamUtils.FAIL);
                        resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                        //rollback(session, transaction);
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

                        //Insert kho hang hoa theo serial
                        if (stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                            resultDTO = reImportStockGoodsSerial(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, session, synImportRevoke);
                            insertSuccess = insertSuccess + resultDTO.getQuantitySucc();
                            insertFail = insertFail + resultDTO.getQuantityFail();
                            amountIssue = amountIssue + resultDTO.getAmountIssue();
                            //Tim kiem bang loi theo import_stock_trans_id
                            if (!stockTransDetailDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {//kiem tra danh sach loi voi serial don le
                                List<StockGoodsSerialInforDTO> lstErrorImportRevoke = stockGoodsSerialBusiness2.getListErrorImportRevoke(stockTransId);
                                if (lstErrorImportRevoke != null) {//TH khong loi he thong
                                    //neu hang hoa khong insert duoc vao stock_goods_serial va insert vao bang loi
                                    if (lstErrorImportRevoke.size() > 0) {
                                        //rollback(session, transaction);
                                        resultDTO.setLstStockGoodsSerialInforDTO(lstErrorImportRevoke);
                                        resultDTO.setMessage(Constants.ERROR_MESSAGE.IS_OVERLAP);
                                        resultDTO.setKey(Constants.ERROR_MESSAGE.IS_OVERLAP);
                                        return resultDTO;
                                    }
                                } else {//neu xay ra loi he thong
                                    //rollback(session, transaction);
                                    resultDTO.setMessage(Constants.ERROR_MESSAGE.IS_OVERLAP);
                                    resultDTO.setKey(Constants.ERROR_MESSAGE.IS_OVERLAP);
                                    return resultDTO;
                                }
                            }
                            //add by ThienNG1 19/08/2015
                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                //rollback(session, transaction);
                                return resultDTO;
                            }
                        }
                        //Tao chi tiet giao dich serial
                        stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                        stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                        stockTransSerialDTO.setAmountReal(resultDTO.getAmountIssue().toString().replace(".0", ""));
                        resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            //rollback(session, transaction);
                            return resultDTO;
                        }
                    }
                    //Dong bo nen khong cap nhat so luong tong cong
                }
            }
            //Tim kiem bang loi theo import_stock_trans_id
            //commit(session, transaction);
        } catch (Exception e) {
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setMessage(e.getMessage());
            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
            //rollback(session, transaction);
            return resultDTO;
        }
//        finally {
//            if (session.isOpen()) {
//                session.close();
//            }
//        }
        //
        resultDTO.setQuantitySucc(insertSuccess);
        resultDTO.setQuantityFail(insertFail);
        resultDTO.setId(stockTransId);
        return resultDTO;
    }

    //AddBy ThienNG1 03/08/2015
    //update Hang hoa theo serial khi hang duoc tra ve
    private ResultDTO reImportStockGoodsSerial(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO,
            StockTransSerialDTO stockTransSerialDTO, Session session, String synImportRevoke) {
        ResultDTO resultDTO = new ResultDTO();
        String message = "";
        StockGoodsSerialStripDTO oldStockGoodsSerialStripDTO = new StockGoodsSerialStripDTO();
        StockGoodsSerialStripDTO newStockGoodsSerialStripDTO = new StockGoodsSerialStripDTO();
        StockGoodsSerialDTO stockGoodsSerialDTO = new StockGoodsSerialDTO();
        //Cac tham so dau vao                             
        //Mat hang quan ly serial theo dai
        if (stockTransDetailDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {
            //stockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountReal());
            newStockGoodsSerialStripDTO.setCustId(stockTransDTO.getCustId());
            newStockGoodsSerialStripDTO.setOwnerId(stockTransDTO.getOwnerId());
            newStockGoodsSerialStripDTO.setOwnerType(stockTransDTO.getOwnerType());
            newStockGoodsSerialStripDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
            newStockGoodsSerialStripDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
            newStockGoodsSerialStripDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
            newStockGoodsSerialStripDTO.setSaleType("");
            newStockGoodsSerialStripDTO.setChangeUser(stockTransDTO.getTransUserId());
            newStockGoodsSerialStripDTO.setPrice(null);
            newStockGoodsSerialStripDTO.setChannelTypeId(null);
            newStockGoodsSerialStripDTO.setBarcode(stockTransSerialDTO.getBarcode());
            newStockGoodsSerialStripDTO.setCellCode(stockTransSerialDTO.getCellCode());
            newStockGoodsSerialStripDTO.setBincode(stockTransSerialDTO.getBincode());
            newStockGoodsSerialStripDTO.setAddInfor(stockTransSerialDTO.getAddInfor());
            newStockGoodsSerialStripDTO.setChangeDate(stockTransDTO.getStockTransDate());
            newStockGoodsSerialStripDTO.setImportDate(stockTransDTO.getStockTransDate());
            newStockGoodsSerialStripDTO.setSaleDate("");
            newStockGoodsSerialStripDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
            newStockGoodsSerialStripDTO.setToSerial(stockTransSerialDTO.getToSerial());
            newStockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountOrder());
            //set partner id
            newStockGoodsSerialStripDTO.setPartnerId(stockTransDTO.getPartnerId());
            newStockGoodsSerialStripDTO.setImportStockTransId(stockTransDTO.getStockTransId());
            newStockGoodsSerialStripDTO.setOrderId(stockTransDTO.getOrderIdList());
            //cac tham so dau vao tim kiem trong DB
            oldStockGoodsSerialStripDTO.setCustId(stockTransDTO.getCustId());
            oldStockGoodsSerialStripDTO.setOwnerId(stockTransDTO.getOwnerId());
            oldStockGoodsSerialStripDTO.setOwnerType(stockTransDTO.getOwnerType());
            oldStockGoodsSerialStripDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
            //oldStockGoodsSerialStripDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
            //oldStockGoodsSerialStripDTO.setStatus(Constants.STATUS_SERIAL_OUT_STOCK);
            oldStockGoodsSerialStripDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
            oldStockGoodsSerialStripDTO.setToSerial(stockTransSerialDTO.getToSerial());

            resultDTO = stockGoodsSerialStripBusiness2.reImportStockGoodsSerialStrip(oldStockGoodsSerialStripDTO, newStockGoodsSerialStripDTO,
                    stockTransDTO.getStockTransId(), session, synImportRevoke);
        } else //Mat hang quan ly serial don le
        {
            stockGoodsSerialDTO.setCustId(stockTransDTO.getCustId());
            stockGoodsSerialDTO.setOwnerId(stockTransDTO.getOwnerId());
            stockGoodsSerialDTO.setOwnerType(stockTransDTO.getOwnerType());
            stockGoodsSerialDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
            stockGoodsSerialDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
            stockGoodsSerialDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
            stockGoodsSerialDTO.setSaleType("");
            stockGoodsSerialDTO.setChangeUser(stockTransDTO.getTransUserId());
            stockGoodsSerialDTO.setPrice(null);
            stockGoodsSerialDTO.setChannelTypeId(null);
            stockGoodsSerialDTO.setCellCode(stockTransSerialDTO.getCellCode());
            stockGoodsSerialDTO.setBarcode(stockTransSerialDTO.getBarcode());
            stockGoodsSerialDTO.setCellCode(stockTransSerialDTO.getCellCode());
            stockGoodsSerialDTO.setBincode(stockTransSerialDTO.getBincode());
            stockGoodsSerialDTO.setAddInfor(stockTransSerialDTO.getAddInfor());
            stockGoodsSerialDTO.setChangeDate(stockTransDTO.getStockTransDate());
            stockGoodsSerialDTO.setImportDate(stockTransDTO.getStockTransDate());
            stockGoodsSerialDTO.setSaleDate("");
            //
            stockGoodsSerialDTO.setPartnerId(stockTransDTO.getPartnerId());
            stockGoodsSerialDTO.setImportStockTransId(stockTransDTO.getStockTransId());
            stockGoodsSerialDTO.setOrderId(stockTransDTO.getOrderIdList());
            //
            resultDTO = stockGoodsSerialBusiness2.reImportStockGoodsSerial(stockGoodsSerialDTO, stockTransDTO.getStockTransId(),
                    stockTransSerialDTO.getFromSerial(), stockTransSerialDTO.getToSerial(), session, synImportRevoke);
        }
        //
        return resultDTO;
    }

    //Update Hang hoa theo so luong
    private ResultDTO reImportStockGoods(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO,
            Session session, String synImportRevoke) {
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
        stockGoodsDTO.setChangeDate(stockTransDTO.getStockTransDate());
        stockGoodsDTO.setImportDate(stockTransDTO.getStockTransDate());
        //
        stockGoodsDTO.setStatus(synImportRevoke);
        stockGoodsDTO.setImportStockTransId(stockTransDTO.getStockTransId());
        stockGoodsDTO.setOrderId(stockTransDTO.getOrderIdList());
        //Cap nhat kho hang neu da ton tai
        resultDTO = stockGoodsInterface.reImportStockGoods(stockGoodsDTO, session, synImportRevoke);
        //
        return resultDTO;
    }

    /*
     - cap nhat thong tin 
     - lay ra giao dich vua cap nhat theo id Yeu cau
    
     */
    @Override
    public List<BillStock> getListTrans(String orderCode, String orderType, String fromDate, String toDate) {
        /*
         1. Tim kiem giao dich tu fromDate -> toDate
         2. Neu orderID != null -> tim theo order
         3. Lay thong tin tuong ung -> tao phieu
         */
        //NEU KHONG NHAP FROMDATE -> TODATE -> RETURN NULL
        if (StringUtils.isStringNullOrEmpty(fromDate) || StringUtils.isStringNullOrEmpty(orderType)) {
            return null;
        }
        //NEU KHONG NHAP TODATE -> MAC DINH LA NGAY HIEN TAI
        if (toDate == null) {
            toDate = stockTransBusiness.getSysDate(formatDate);
        }
        //xu ly fromdate-> todate
        if (fromDate.length() <= 10) {
            fromDate += " 00:00:00";
        }
        if (toDate.length() <= 10) {
            toDate += " 23:59:59";
        }
        List<BillStock> result = new ArrayList<>();
        //1/
        List<ConditionBean> lstSearchOrderCB = new ArrayList<>();
        lstSearchOrderCB.add(new ConditionBean("stockTransDate", ParamUtils.OP_GREATER_EQUAL, fromDate, ParamUtils.TYPE_DATE));
        lstSearchOrderCB.add(new ConditionBean("stockTransDate", ParamUtils.OP_LESS_EQUAL, toDate, ParamUtils.TYPE_DATE));
        //giao dich la nhap hoac xuat kho
        if (orderType.equalsIgnoreCase(TRANS_TYPE_IMPORT)) {
            lstSearchOrderCB.add(new ConditionBean("stockTransType", ParamUtils.OP_EQUAL, TRANS_TYPE_IMPORT, ParamUtils.TYPE_STRING));
        } else {
            lstSearchOrderCB.add(new ConditionBean("stockTransType", ParamUtils.OP_EQUAL, TRANS_TYPE_EXPORT, ParamUtils.TYPE_STRING));
        }
        //
        if (!DataUtil.isStringNullOrEmpty(orderCode)) {
            lstSearchOrderCB.add(new ConditionBean("orderIdList", ParamUtils.OP_EQUAL, orderCode, ParamUtils.TYPE_STRING));
        }
        //format condition
        //get stocktrans result
        List<StockTransDTO> lst = stockTransBusiness.searchByConditionBean(lstSearchOrderCB, 0, Integer.MAX_VALUE, "desc", "stockTransDate");
        if (lst != null && lst.size() > 0) {//NEU CO DU LIEU
            for (StockTransDTO i : lst) {
                BillStock tempTicket;
                //chuyen thong tin giao dich -> thong tin phieu
                tempTicket = createTicketFromTrans(i, orderType);
                result.add(tempTicket);
            }
        } else {
            return null;
        }
        return result;
    }

    public BillStock createTicketFromTrans(StockTransDTO trans, String orderType) {
        //thong tin fig
        String stockCode = mapStock(trans.getOwnerId());
        String inputType = "1";
        String outputType = "1";
        //
        BillStock importTicket = new BillStock();
        //
        //SET THONG TIN CHUNG
        importTicket.setOrderCode(orderIdToCode(trans.getOrderIdList()));
        importTicket.setTransCode(trans.getStockTransCode());
        importTicket.setStockCode(stockCode);
        importTicket.setOrderType(orderType);
        importTicket.setIeExpectDate(trans.getStockTransDate());
        importTicket.setInputType(inputType);
        importTicket.setOutputType(outputType);
        //SET THONG TIN HANG
        List<GoodsInTicketDTO> lstGoodsInTicket = new ArrayList<>();
        //1. lay thong tin stock_trans_detail
        List<StockTransDetailDTO> lstGoods;
        StockTransDetailDTO searchGoods = new StockTransDetailDTO();
        searchGoods.setStockTransId(trans.getStockTransId());
        lstGoods = stockTransDetailBusiness.search(searchGoods, 0, Integer.MAX_VALUE, "", "stockTransDetailId");
        //2. set thong tin
        if (lstGoods != null && lstGoods.size() > 0) {//NEU CO DU LIEU
            for (StockTransDetailDTO i : lstGoods) {
                GoodsInTicketDTO tempGoods;
                //chuyen thong tin giao dich -> thong tin phieu
                tempGoods = createGoodsFromTransDetail(i);
                lstGoodsInTicket.add(tempGoods);
            }
        }
        //set thong tin hang vao ticket
        importTicket.setLstGoods(lstGoodsInTicket);
        //RETURN
        return importTicket;
    }

    public GoodsInTicketDTO createGoodsFromTransDetail(StockTransDetailDTO transDetail) {
        GoodsInTicketDTO goodsInTicket = new GoodsInTicketDTO();
        //SET THONG TIN HANG
        goodsInTicket.setGoodsCode(transDetail.getGoodsCode());
        goodsInTicket.setGoodsName(transDetail.getGoodsName());
        goodsInTicket.setAmountReal(transDetail.getAmountReal());
        goodsInTicket.setAmountOrder(transDetail.getAmountOrder());
        goodsInTicket.setGoodsUnitName(transDetail.getGoodsUnitTypeName());
        goodsInTicket.setGoodsState(transDetail.getGoodsState());
        //set null default sort fields
        goodsInTicket.setDefaultSortField(null);
        //SET THONG TIN SERIAL
        if (!transDetail.getGoodsIsSerial().equalsIgnoreCase(IS_SERIAL)) {//neu la hang k theo serial -> lstserial = null
            goodsInTicket.setLstSerial(null);
        } else {
            //LAY RA THONG TIN SERIAL
            List<StockTransSerialDTO> lstSerial;
            StockTransSerialDTO searchSerial = new StockTransSerialDTO();
            searchSerial.setStockTransDetailId(transDetail.getStockTransDetailId());
            lstSerial = stockTransSerialBusiness.search(searchSerial, 0, Integer.MAX_VALUE, "", "stockTransSerialId");
            //FORMAT //SET NULL TRUONG K CAN THIET
            formatSerial(lstSerial);
            //SET VAO LST
            goodsInTicket.setLstSerial(lstSerial);
        }

        return goodsInTicket;
    }

    public void formatSerial(List<StockTransSerialDTO> lstSerial) {
        for (StockTransSerialDTO i : lstSerial) {
            i.setAmountOrder(i.getAmountOrder().replace(".0", ""));
            i.setAmountReal(i.getAmountReal().replace(".0", ""));
            i.format();
        }
    }

    public String mapStock(String stockId) {
        //mapping process
        return ((Stock) stockBusiness.findById(Long.parseLong(stockId))).getCode();
        //
    }

    //convert list orderId to list order code
    /*
     @input: String list of order id separate by ","
     @return: String list of order code separate by ","
     */
    public String orderIdToCode(String orderIdList) {
        //tach mang id
        StringBuilder strOrderCode = new StringBuilder();
        String[] idList = orderIdList.split(",");
        String orderCode;
        for (String i : idList) {
            try {
                orderCode = WSOrders.findOrderById(i).getOrderCode();
            } catch (Exception ex) {
                orderCode = "";
            }
            strOrderCode.append(orderCode).append(",");
        }

        return strOrderCode.toString().substring(0, strOrderCode.length() - 1);
    }

    @Override
    public BillStock getListTransBCCS(String orderId, String orderType) {
        /*
         1. Tim kiem giao dich tu fromDate -> toDate
         3. Lay thong tin tuong ung -> tao phieu
         */
        if (DataUtil.isStringNullOrEmpty(orderId)) {
            return null;
        }
        BillStock result;
        //1/
        List<ConditionBean> lstSearchOrderCB = new ArrayList<>();
        //giao dich la nhap hoac xuat khos
        if (orderType.equalsIgnoreCase(TRANS_TYPE_IMPORT)) {
            lstSearchOrderCB.add(new ConditionBean("stockTransType", ParamUtils.OP_EQUAL, TRANS_TYPE_IMPORT, ParamUtils.TYPE_STRING));
        } else {
            lstSearchOrderCB.add(new ConditionBean("stockTransType", ParamUtils.OP_EQUAL, TRANS_TYPE_EXPORT, ParamUtils.TYPE_STRING));
        }
        //neu la hang xuat -> tim kiem theo list id
//        if(orderId.contains(",")){
//            lstSearchOrderCB.add(new ConditionBean("orderIdList", ParamUtils.OP_IN,orderId, ParamUtils.TYPE_STRING));
//        }else{
        lstSearchOrderCB.add(new ConditionBean("orderIdList", ParamUtils.OP_EQUAL, orderId, ParamUtils.TYPE_STRING));
//        }
        //format condition
        //get stocktrans result
        List<StockTransDTO> lst = stockTransBusiness.searchByConditionBean(lstSearchOrderCB, 0, Integer.MAX_VALUE, "desc", "stockTransDate");
        if (lst != null && lst.size() > 0) {//NEU CO DU LIEU
            BillStock tempTicket;
            //chuyen thong tin giao dich -> thong tin phieu
            tempTicket = createTicketFromTrans(lst.get(0), orderType);
            result = tempTicket;
        } else {
            return null;
        }
        return result;
    }

    //duyot: new function
    /*
     @params: trans:giao dich nhap kho, lstGoods: danh sach hang hoa da nhap kho, listserial belong to goods
     @return: billStock
     */
    public BillStock createBillStockFromTrans(StockTransDTO trans, String inputType) {
        BillStock billStock = new BillStock();
        //thong tin fig
        String stockCode = mapStock(trans.getOwnerId());
        String outputType = null;
        String orderType = "1";//type import
        String input;
        if (inputType == null) {
            input = "1";
        } else {
            input = inputType;
        }

        //SET THONG TIN CHUNG
        billStock.setOrderCode(orderIdToCode(trans.getOrderIdList()));
        billStock.setTransCode(trans.getStockTransCode());
        billStock.setStockCode(stockCode);
        billStock.setOrderType(orderType);
        billStock.setIeExpectDate(trans.getStockTransDate());
        billStock.setInputType(input);
        billStock.setOutputType(outputType);
        //duyot 15/01/2016: set them thong tin nguoi thuc nhap
        System.out.println("log:Billstock: thong tin nguoi thuc nhap: " + trans.getRealStockTransDate());
        if (!DataUtil.isStringNullOrEmpty(trans.getTransUserName())) {
            billStock.setRealIEUserName(trans.getTransUserName());
        } else {
            billStock.setRealIEUserName("admin-vtp");
        }
        //
        return billStock;
    }

    public GoodsInTicketDTO createGoods(StockTransDetailDTO transDetail) {
        GoodsInTicketDTO goodsInTicket = new GoodsInTicketDTO();
        //SET THONG TIN HANG
        goodsInTicket.setGoodsCode(transDetail.getGoodsCode());
        goodsInTicket.setGoodsName(transDetail.getGoodsName());
        goodsInTicket.setAmountReal(transDetail.getAmountReal());
        goodsInTicket.setAmountOrder(transDetail.getAmountOrder());
        goodsInTicket.setGoodsUnitName(transDetail.getGoodsUnitTypeName());
        goodsInTicket.setGoodsState(transDetail.getGoodsState());
        //set null default sort fields
        goodsInTicket.setDefaultSortField(null);
        //SET THONG TIN SERIAL
        if (!transDetail.getGoodsIsSerial().equalsIgnoreCase(IS_SERIAL)) {//neu la hang k theo serial -> lstserial = null
            goodsInTicket.setLstSerial(null);
        } else {
            //LAY RA THONG TIN SERIAL - thong tin danh sach serial vua insert
            List<StockTransSerialDTO> lstSerial;
            StockTransSerialDTO searchSerial = new StockTransSerialDTO();
            searchSerial.setStockTransDetailId(transDetail.getStockTransDetailId());
            lstSerial = stockTransSerialBusiness.search(searchSerial, 0, Integer.MAX_VALUE, "", "stockTransSerialId");
            //FORMAT //SET NULL TRUONG K CAN THIET
            formatSerial(lstSerial);
            //SET VAO LST
            goodsInTicket.setLstSerial(lstSerial);
        }

        return goodsInTicket;
    }

//    tao goodsinTicket from trans detial chua co thong tin serial
    public GoodsInTicketDTO createGoodsFromDetail(StockTransDetailDTO transDetail) {
        GoodsInTicketDTO goodsInTicket = new GoodsInTicketDTO();
        //SET THONG TIN HANG
        goodsInTicket.setGoodsCode(transDetail.getGoodsCode());
        goodsInTicket.setGoodsName(transDetail.getGoodsName());
        goodsInTicket.setAmountReal(transDetail.getAmountReal().replace(".0", ""));
        goodsInTicket.setAmountOrder(transDetail.getAmountOrder().replace(".0", ""));
        goodsInTicket.setGoodsUnitName(transDetail.getGoodsUnitTypeName());
        goodsInTicket.setGoodsState(transDetail.getGoodsState());
        //set null default sort fields
        goodsInTicket.setDefaultSortField(null);
        //SET THONG TIN SERIAL

        return goodsInTicket;
    }
    /*
     DUYOT
     1. cap nhat trang thai giao dich
     2. cap nhat trang thai serial trong stock_goods_serial
     */
//    SUPPORT FUNCTIONS

    public String updateTransStatus(StockTransDTO trans, String status, String sysdate, Session session) {
//        ParamUtils.TRANS_STATUS.IMPORTED
        trans.setStockTransStatus(status);
        trans.setRealStockTransDate(sysdate);
        //
        return stockTransBusiness.updateSession(trans, session);
    }

    @Override
    public ResultDTO synRealImportStockCust(String orderId) {
        //Tao doi tuong ghi log cho Log va HT dong bo
        KpiLogDTO synRealKpiLog = new KpiLogDTO();
        KpiLogDTO synRealKpiLogSyn = new KpiLogDTO();
        String sStartTime = DateUtil.sysdateString();
        String sEndTime = "";
        String sEndTimeSyn = "";
        String functionCode;
        String functionCodeSyn = "";
        String description = "";
        String descriptionSyn = "";
        String resultLog = "";
        String resultRealSyn = "";
        String reasonLog = "";
        String reasonSyn = "";
        String transactionCode = "";
        String transactionCodeSyn = "";
        String duration = "";
        String durationSyn = "";
        synRealKpiLog.setStartTime(sStartTime);
        synRealKpiLog.setCreateDatetime(sStartTime);

        functionCode = "WMS_IMPORT_STOCK_LOG";
//        transactionCode = orderId;
        //INIT SESSION
        Session session;
        Transaction transaction;
        Connection connection = null;
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        //
        String sysdate = stockGoodsBusiness.getSysDate(formatDate);

        List<GoodsInTicketDTO> lstGoods = new ArrayList<>();
        ResultDTO resultDTO = new ResultDTO();
        //Bat dau ghi thoi gian thuc nhap ben Logistics        
        long startTime = System.currentTimeMillis();
        StockTransDTO importedTrans = null;
        OrdersDTO orders = null;
        OrderActionDTO orderActionDTO = null;
        String previousOrderActionStatus = "";
        long endTimeSyn = 0;
        try {
            //1.----------------------------------------------------------------
            StockTransDTO searchStockTrans = new StockTransDTO();
            searchStockTrans.setOrderIdList(orderId);
            List<StockTransDTO> lstStockTransResults = stockTransBusiness.search(searchStockTrans, 0, Integer.MAX_VALUE, "", "stockTransDate");
            //KIEM TRA KET QUA TRA VE
            if (DataUtil.isListNullOrEmpty(lstStockTransResults)) {//neu k co du lieu
                resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.TRANS_NOT_FOUND);
                rollback(session, transaction);
                return resultDTO;
            }
            //@@@-------------------------------------------------------------------------------------
            //duyot: 29/01/2016: BCCS:CAP NHAT TRUONG HOP NHAP HANG SIM - KIT
            //1. Neu ds stock_trans_dto >1 -> giao dich dieu chuyen || nhap kit
            if (lstStockTransResults.size() > 1) {
                StockTransDTO importedKITTrans = null;
                StockTransDTO exportedKITTrans = null;
                String stockKITExportedId = "";
                //lay ra thong tin giao dich nhap
                for (StockTransDTO z : lstStockTransResults) {
                    if (!DataUtil.isStringNullOrEmpty(z.getFromStockTransId())) {
                        importedKITTrans = z;
                        stockKITExportedId = z.getFromStockTransId();
                        break;
                    }
                }

                if (importedKITTrans != null) {//neu la dung co giao dich nhap sim-kit
                    importedTrans = importedKITTrans;
                    //lay ra thong tin giao dich xuat
                    for (StockTransDTO z : lstStockTransResults) {
                        if (stockKITExportedId.equalsIgnoreCase(z.getStockTransId())) {
                            exportedKITTrans = z;
                            break;
                        }
                    }
                    //cap nhat lai trang thai giao dich nhap = 1
                    if (!updateTransStatus(importedKITTrans, ParamUtils.TRANS_STATUS.IMPORTED, sysdate, session).equalsIgnoreCase(ParamUtils.SUCCESS)) {//NEU K CAP NHAT DUOC GIAO DICH
                        resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.UPDATE_TRANS_FAIL);
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //cap nhat trang thai giao dich xuat ->3
                    if (!updateTransStatus(exportedKITTrans, ParamUtils.TRANS_STATUS.EXPORTED, sysdate, session).equalsIgnoreCase(ParamUtils.SUCCESS)) {//NEU K CAP NHAT DUOC GIAO DICH
                        resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.UPDATE_TRANS_FAIL);
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //cap nhat lai so luong total cua kit da nhap
                    //lay ra danh sach detail cua giao dich nhap
                    //1. lay ra danh sach hang hoa thuoc giao dich
                    StockTransDetailDTO searchStockTransDetail = new StockTransDetailDTO();
                    searchStockTransDetail.setStockTransId(importedKITTrans.getStockTransId());
                    List<StockTransDetailDTO> lstTransDetailDTO = stockTransDetailBusiness.search(searchStockTransDetail, 0, Integer.MAX_VALUE, "", "stockTransDetailId");
                    //KIEM TRA KET QUA TRA VE
                    if (DataUtil.isListNullOrEmpty(lstTransDetailDTO)) {//neu k co du lieu
                        resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.NOT_FOUND_GOODS_IN_TRANS);
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //LOOP QUA DS HANG HOA
                    for (StockTransDetailDTO i : lstTransDetailDTO) {
                        //tao thong tin hang hoa
                        GoodsInTicketDTO goods = createGoodsFromDetail(i);
                        //CAP NHAT STOCK_GOODS_TOTAL
                        importedKITTrans.setStockTransDate(sysdate);
                        resultDTO = importStockGoodsTotal(importedKITTrans, i, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction);
                            return resultDTO;
                        }
                        //CAP NHAT STOCK_GOODS_SERIAL VE TRANG THAI 1
                        //CAP NHAT TAT CA CAC STRIP CO ORDER ID TUONG UNG
                        StockGoodsSerialStripDTO stockGoodsSerialStrip = new StockGoodsSerialStripDTO();
                        stockGoodsSerialStrip.setStatus(ParamUtils.TRANS_STATUS.IMPORTED);
                        stockGoodsSerialStrip.setGoodsId(i.getGoodsId());
                        stockGoodsSerialStrip.setGoodsState(i.getGoodsState());
                        stockGoodsSerialStrip.setChangeDate(sysdate);
                        stockGoodsSerialStrip.setOrderId(orderId);
                        stockGoodsSerialStrip.setOldStatus(ParamUtils.TRANS_STATUS.WAITING_IMPORT);
                        String updateStockGoodsSerialStrip = stockGoodsSerialStripBusiness2.updateStockGoodsSerialStripByOrdersIdAndGoods(stockGoodsSerialStrip, session);
                        if (!updateStockGoodsSerialStrip.equalsIgnoreCase(ParamUtils.SUCCESS)) {//CAP NHAT KHONG THANH CONG
                            System.out.println("LOG: Cap nhat stock_goods_serial khong thanh cong");
                            resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.UPDATE_INFO_FAIL);
                            rollback(session, transaction);
                            return resultDTO;
                        }
                        //lay ra danh sach serial vua update
                        StockTransSerialDTO searchStockTransSerial = new StockTransSerialDTO();
                        searchStockTransSerial.setStockTransId(importedKITTrans.getStockTransId());
                        List<StockTransSerialDTO> lstUpdatedStockTransSerial = stockTransSerialBusiness.search(searchStockTransSerial, 0, Integer.MAX_VALUE, "", "stockTransSerialId");
                        for (StockTransSerialDTO z : lstUpdatedStockTransSerial) {
                            z.setQuantity(z.getAmountOrder());
                            z.format();
                        }
                        goods.setLstSerial(lstUpdatedStockTransSerial);

                        //add vao list goods
                        lstGoods.add(goods);
                    }
                }
                lstStockTransResults.remove(importedKITTrans);
                lstStockTransResults.remove(exportedKITTrans);
            }
            //@@@-------------------------------------------------------------------------------------
            //CAP NHAT STOCK_TRANS
            //###------------------------------------------------------------------
            StockTransDTO stockTransDTO = null;
            if (lstStockTransResults.size() > 0) {
                stockTransDTO = lstStockTransResults.get(0);
                stockTransDTO.setStockTransStatus(ParamUtils.TRANS_STATUS.IMPORTED);
                stockTransDTO.setRealStockTransDate(sysdate);
                String updateStockTransResult = stockTransBusiness.updateSession(stockTransDTO, session);
                if (!updateStockTransResult.equalsIgnoreCase(ParamUtils.SUCCESS)) {//NEU K CAP NHAT DUOC GIAO DICH
                    resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.UPDATE_TRANS_FAIL);
                    //Dien cac thong tin ghi log
                    sEndTime = DateUtil.sysdateString();
                    description = resultDTO.getMessage();
                    reasonLog = "Cap nhat giao dich bi loi";
                    duration = String.valueOf(System.currentTimeMillis() - startTime);
                    resultLog = SYN_FAIL;
                    //Rollback cac thao tac truoc do
                    rollback(session, transaction);
                    return resultDTO;
                }
                //end 1.------------------------------------------------------------
                //CAP NHAT DANH SACH SERIAL-----------------------------------------
                //1. lay ra danh sach hang hoa thuoc giao dich
                StockTransDetailDTO searchStockTransDetail = new StockTransDetailDTO();
                searchStockTransDetail.setStockTransId(stockTransDTO.getStockTransId());
                List<StockTransDetailDTO> lstTransDetailDTO = stockTransDetailBusiness.search(searchStockTransDetail, 0, Integer.MAX_VALUE, "", "stockTransDetailId");
                //KIEM TRA KET QUA TRA VE
                if (DataUtil.isListNullOrEmpty(lstTransDetailDTO)) {//neu k co du lieu
                    resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.NOT_FOUND_GOODS_IN_TRANS);
                    //Dien cac thong tin ghi log
                    sEndTime = DateUtil.sysdateString();
                    description = resultDTO.getMessage();
                    reasonLog = "Khong co danh sach hang hoa thuoc giao dich";
                    duration = String.valueOf(System.currentTimeMillis() - startTime);
                    resultLog = SYN_FAIL;
                    //Rollback cac thao tac truoc do
                    rollback(session, transaction);
                    return resultDTO;
                }
                //LOOP QUA DANH SACH HANG HOA
                for (StockTransDetailDTO i : lstTransDetailDTO) {
                    //tao thong tin hang hoa
                    GoodsInTicketDTO goods = createGoodsFromDetail(i);

                    if (!i.getGoodsIsSerial().equalsIgnoreCase(IS_SERIAL)) {//NEU LA HANG K SERIAL
                        //TIM KIEM STOCK_GOODS THUOC HANG HOA
                        StockGoodsDTO searchStockGoods = new StockGoodsDTO();
                        searchStockGoods.setOrderId(orderId);
                        searchStockGoods.setGoodsId(i.getGoodsId());
                        searchStockGoods.setGoodsState(i.getGoodsState());
                        List<StockGoodsDTO> lstStockGoods = stockGoodsBusiness.search(searchStockGoods, 0, Integer.MAX_VALUE, "", "id");
                        if (DataUtil.isListNullOrEmpty(lstTransDetailDTO)) {//neu k co du lieu
                            resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.NOT_FOUND_GOODS_IN_TRANS);
                            //Dien cac thong tin ghi log
                            sEndTime = DateUtil.sysdateString();
                            description = resultDTO.getMessage();
                            reasonLog = "Khong co danh sach hang hoa khong serial";
                            resultLog = SYN_FAIL;
                            duration = String.valueOf(System.currentTimeMillis() - startTime);
                            //Rollback cac thao tac truoc do
                            rollback(session, transaction);
                            return resultDTO;
                        }
                        //CAP NHAT TRANG THAI STOCK_GOODS
                        for (StockGoodsDTO stockGoods : lstStockGoods) {
                            stockGoods.setStatus(ParamUtils.TRANS_STATUS.IMPORTED);
                            stockGoods.setGoodsState(i.getGoodsState());
                            String updateStockGoodsResult = stockGoodsBusiness.updateSession(stockGoods, session);
                            if (!updateStockGoodsResult.equalsIgnoreCase(ParamUtils.SUCCESS)) {//CAP NHAT KHONG THANH CONG
                                resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.UPDATE_INFO_FAIL);
                                //Dien cac thong tin ghi log
                                sEndTime = DateUtil.sysdateString();
                                description = resultDTO.getMessage();
                                reasonLog = "Cap nhat danh sach hang hoa khong serial khong thanh cong hang hoa = " + i.getGoodsCode();
                                resultLog = SYN_FAIL;
                                duration = String.valueOf(System.currentTimeMillis() - startTime);
                                //Rollback cac thao tac truoc do
                                rollback(session, transaction);
                                return resultDTO;
                            }
                        }
                        //-------------------------
                    } else {//NEU LA HANG SERIAL

                        if (i.getGoodsIsSerialStrip().equalsIgnoreCase(IS_SERIAL)) {//NEU LA HANG SERIAL STRIP
                            //CAP NHAT TAT CA CAC STRIP CO ORDER ID TUONG UNG
                            StockGoodsSerialStripDTO stockGoodsSerialStrip = new StockGoodsSerialStripDTO();
                            stockGoodsSerialStrip.setStatus(ParamUtils.TRANS_STATUS.IMPORTED);
                            stockGoodsSerialStrip.setGoodsId(i.getGoodsId());
                            stockGoodsSerialStrip.setGoodsState(i.getGoodsState());
                            stockGoodsSerialStrip.setChangeDate(sysdate);
                            stockGoodsSerialStrip.setOrderId(orderId);
                            stockGoodsSerialStrip.setOldStatus(ParamUtils.TRANS_STATUS.WAITING_IMPORT);
                            String updateStockGoodsSerialStrip = stockGoodsSerialStripBusiness2.updateStockGoodsSerialStripByOrdersIdAndGoods(stockGoodsSerialStrip, session);
                            if (!updateStockGoodsSerialStrip.equalsIgnoreCase(ParamUtils.SUCCESS)) {//CAP NHAT KHONG THANH CONG
                                resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.UPDATE_INFO_FAIL);
                                //Dien cac thong tin ghi log
                                sEndTime = DateUtil.sysdateString();
                                description = resultDTO.getMessage();
                                reasonLog = "Cap nhat danh sach hang hoa co serial theo dai khong thanh cong hang hoa = " + i.getGoodsCode();
                                resultLog = SYN_FAIL;
                                duration = String.valueOf(System.currentTimeMillis() - startTime);
                                //Rollback cac thao tac truoc do
                                rollback(session, transaction);
                                return resultDTO;
                            }
                            //lay ra danh sach serial vua update
                        } else {//NEU LA HANG SERIALs
                            StockGoodsSerialDTO stockGoodsSerial = new StockGoodsSerialDTO();
                            stockGoodsSerial.setStatus(ParamUtils.TRANS_STATUS.IMPORTED);
                            stockGoodsSerial.setGoodsId(i.getGoodsId());
                            stockGoodsSerial.setGoodsState(i.getGoodsState());
                            stockGoodsSerial.setChangeDate(sysdate);
                            stockGoodsSerial.setOrderId(orderId);
                            stockGoodsSerial.setOldStatus(ParamUtils.TRANS_STATUS.WAITING_IMPORT);
                            String updateStockGoodsSerial = stockGoodsSerialBusiness2.updateStockGoodsSerialByOrdersIdAndGoods(stockGoodsSerial, session);
                            if (!updateStockGoodsSerial.equalsIgnoreCase(ParamUtils.SUCCESS)) {//CAP NHAT KHONG THANH CONG
                                resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.UPDATE_INFO_FAIL);
                                //Dien cac thong tin ghi log
                                sEndTime = DateUtil.sysdateString();
                                description = resultDTO.getMessage();
                                reasonLog = "Cap nhat danh sach hang hoa co serial khong thanh cong hang hoa = " + i.getGoodsCode();
                                resultLog = SYN_FAIL;
                                duration = String.valueOf(System.currentTimeMillis() - startTime);
                                //Rollback cac thao tac truoc do
                                rollback(session, transaction);
                                return resultDTO;
                            }
                        }
                        //lay ra danh sach serial vua update
                        StockTransSerialDTO searchStockTransSerial = new StockTransSerialDTO();
                        searchStockTransSerial.setStockTransId(stockTransDTO.getStockTransId());
                        //duyot: 25/01/2016: tim lai serial da update theo goods-id + goods_state
                        searchStockTransSerial.setGoodsId(i.getGoodsId());
                        searchStockTransSerial.setGoodsState(i.getGoodsState());
                        List<StockTransSerialDTO> lstUpdatedStockTransSerial = stockTransSerialBusiness.search(searchStockTransSerial, 0, Integer.MAX_VALUE, "", "stockTransSerialId");
                        for (StockTransSerialDTO z : lstUpdatedStockTransSerial) {
                            z.setQuantity(z.getAmountOrder());
                            z.format();
                        }
                        goods.setLstSerial(lstUpdatedStockTransSerial);
                    }//END IF
                    //CAP NHAT STOCK_GOODS_TOTAL
                    stockTransDTO.setStockTransDate(sysdate);
                    resultDTO = importStockGoodsTotal(stockTransDTO, i, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        //Dien cac thong tin ghi log
                        sEndTime = DateUtil.sysdateString();
                        description = resultDTO.getMessage();
                        reasonLog = "Cap nhat stock_goods_total khong thanh cong cho hang hoa = " + i.getGoodsCode();
                        resultLog = SYN_FAIL;
                        duration = String.valueOf(System.currentTimeMillis() - startTime);
                        //Rollback cac thao tac truoc do
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //add vao list goods
                    lstGoods.add(goods);
                }
            } else if (importedTrans != null) {
                stockTransDTO = importedTrans;
            } else {
                System.out.println("LOG: LOI K TON TAI GIAO DICH NHAP DE DONG BO");
                resultDTO.setMessage(ParamUtils.SYSTEM_OR_DATA_ERROR);
                resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                rollback(session, transaction);
                return resultDTO;
            }
            //CAP NHAT THONG TIN LENH - ORDERS
            //---CAP NHAT THONG TIN ORDER - ORDERACTION
            //NEU LA NHAP KHONG THEO YC -> COMMIT
            orders = WSOrders.findOrderById(orderId);
            if (orders == null) {//KHONG TIM DUOC YC HOP LE
                resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.ORDER_NOT_FOUND);
                //Dien cac thong tin ghi log
                sEndTime = DateUtil.sysdateString();
//                description = resultDTO.getMessage();
                reasonLog = "Khong tim duoc yeu cau hop le co id = " + orderId;
                resultLog = SYN_FAIL;
                duration = String.valueOf(System.currentTimeMillis() - startTime);
                //Rollback cac thao tac truoc do
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_NOT_FOUND);
                rollback(session, transaction);
                return resultDTO;
            }
            //LAY THONG TIN LENH
            List<ConditionBean> lstConditionBeanUpdateOrders = new ArrayList<>();
            lstConditionBeanUpdateOrders.add(new ConditionBean("orderIdList", ParamUtils.NAME_EQUAL, orders.getOrderId(), ParamUtils.TYPE_STRING));
            try {
                orderActionDTO = WSOrderAction.getListOrderActionByCondition(lstConditionBeanUpdateOrders, 0, Integer.MAX_VALUE, "", "id").get(0);
            } catch (Exception ex) {
            }
            if (orderActionDTO == null) {//KHONG TIM DUOC YC HOP LE
                resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.ORDER_NOT_FOUND);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_NOT_FOUND);
                //Dien cac thong tin ghi log
                sEndTime = DateUtil.sysdateString();
//                description = resultDTO.getMessage();
                reasonLog = "Khong tim duoc lenh nhap hop le voi id yeu cau = " + orderId;
                duration = String.valueOf(System.currentTimeMillis() - startTime);
                resultLog = SYN_FAIL;
                //Rollback cac thao tac truoc do
                rollback(session, transaction);
                return resultDTO;
            }
            previousOrderActionStatus = orderActionDTO.getStatus();
            String updateMessage = updateOrderAndOrderAction(orders, orderActionDTO);

            if (!updateMessage.equalsIgnoreCase(ParamUtils.SUCCESS)) {//NEU THONG TIN CAP NHAT LOI -> ROLLBACK
                resultDTO.setMessage(ParamUtils.FAIL);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.UPDATE_ORDER_ACTION_FAIL);
                //Dien cac thong tin ghi log
                sEndTime = DateUtil.sysdateString();
//                description = resultDTO.getMessage();
                reasonLog = "Cap nhat yeu cau va lenh nhap loi orderId = "
                        + orderId + " orderActionId = " + orderActionDTO.getId();
                resultLog = SYN_FAIL;
                duration = String.valueOf(System.currentTimeMillis() - startTime);
                //Rollback cac thao tac truoc do
                rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                rollback(session, transaction);
                return resultDTO;
            }
            //END CAP NHAT THONG TIN LENH
            //----------------GUI THONG TIN DONG BO--------------
            //TAO DU LIEU
            BillStock billStock = createBillStockFromTrans(stockTransDTO, orders.getInputType());
            billStock.setLstGoods(lstGoods);
            billStock.setStockTransDate(stockTransDTO.getStockTransDate());
            long endTimeLogistics = System.currentTimeMillis() - startTime;
            //Ket thuc ghi thoi gian thuc nhap HT Logistics

            resultLog = ParamUtils.SUCCESS.equalsIgnoreCase(updateMessage) ? SYN_SUCC : SYN_FAIL;
            sEndTime = DateUtil.sysdateString();
            duration = String.valueOf(endTimeLogistics);
            String sysSyn = Constants.TYPE_ORDERS_KTTS.equals(orders.getSourceOrder())
                    ? "VTN" : "VTT";
            descriptionSyn = "Thuc nhap dong bo tren he thong " + sysSyn;
            functionCodeSyn = "WMS_IMPORT_STOCK_" + sysSyn;

//            description = "Thoi gian thuc nhap ben HT Logistics = " + endTimeLogistics;
//            System.out.println("Thoi gian thuc nhap ben HT Logistics = " + endTimeLogistics);
            //Bat dau ghi thoi gian thuc nhap cua HT BCCS - KTTS
            long startTimeSyn = System.currentTimeMillis();
            String sStartTimeSyn = DateUtil.sysdateString();
            synRealKpiLogSyn.setStartTime(sStartTimeSyn);
            synRealKpiLogSyn.setCreateDatetime(sStartTimeSyn);
            //GUI REQUEST
            SynLogDTO synLog = new SynLogDTO();
            synLog.setAppCode(ParamUtils.APP_CODE.WMS_IMPORT_STOCK);
            synLog.setKey(orderIdToCode(stockTransDTO.getOrderIdList()));
            ResultKTTSDTO resultSyn;
            transactionCodeSyn = orders.getOrderCode();  
            transactionCode = orders.getOrderCode();
            if (orders.getSourceOrder().equalsIgnoreCase(ORDER_BCCS)) {//NEU LA YC DONG BO TU BCCS
//                 init data
                DataIEBccs data = new DataIEBccs(billStock);
                //GUI REQUEST
                ResultBCCSDTO result = WSBccsGateway.callServiceTransStockBccs(data);
                //CHECK RESULT
                if (result == null) {//neu mat ket noi -> tra ve null
                    //Luu log ra kpi_log
                    reasonSyn = "Mat ket noi voi BCCS";
                    endTimeSyn = System.currentTimeMillis() - startTimeSyn;
//                    reasonSyn = ParamUtils.APP_CODE.CONNECT_FAIL;
                    resultRealSyn = SYN_FAIL;
                    sEndTimeSyn = DateUtil.sysdateString();
                    //luu log ra syn_log
                    synLog.setDescription(reasonSyn);
                    synLog.setStatus(resultRealSyn);
                    WSSynLog.insertSynLog(synLog);
                    //rollback
                    rollback(session, transaction);
                    rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                    //tra ve ket qua
                    resultDTO.setKey(reasonSyn);
                    resultDTO.setMessage(ParamUtils.APP_CODE.CONNECT_FAIL);
                    return resultDTO;
                } else {
                    //Luu log ra kpi_log
//                    descriptionSyn = result.getDescription();
                    //luu thong tin giao dich
                    synLog.setDescription(result.getDescription());
                    String responseCode = result.getResponseCode();
                    if (responseCode != null && responseCode.equalsIgnoreCase(BCCS_RESPONSE_SUCCESS)) {
                        synLog.setStatus(SYN_SUCC);
                        resultRealSyn = SYN_SUCC;
                    } else {
                        synLog.setStatus(SYN_FAIL);
                        resultRealSyn = SYN_FAIL;
                        reasonSyn = result.getDescription();
                    }
                    WSSynLog.insertSynLog(synLog);
                    //NEU RESPONSE_CODE TRA VE K THANH CONG -> ROLLBACK
                    if (!result.getResponseCode().equalsIgnoreCase(BCCS_RESPONSE_SUCCESS)) {
                        //TRA VE THONG TIN KET QUA
                        resultDTO.setKey(result.getDescription());
                        resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.REFUSED_BY_BCCS);
                        //ROLLBACK
                        rollback(session, transaction);
                        rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                        //
                        return resultDTO;
                    }
                }
            } else {

                resultSyn = WSKTTS.synImportStockKTTS(billStock);
                //duyot: 15/01/2016: cap nhat ghi log khi mat ket noi
                if (resultSyn == null) {//mat ket noi
                    //Ghi ra Kpi_log
                    reasonSyn = "Mat ket noi voi KTTS";
                    resultRealSyn = SYN_FAIL;
                    sEndTimeSyn = DateUtil.sysdateString();
                    endTimeSyn = System.currentTimeMillis() - startTimeSyn;
                    //LUU KET QUA
                    resultDTO.setMessage(ParamUtils.APP_CODE.CONNECT_FAIL);
                    resultDTO.setKey("Mat ket noi voi KTTS");
                    //rollback
                    rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                    rollback(session, transaction);
                    //LUU THONG TIN LOG
                    synLog.setStatus("0");
                    synLog.setDescription("Mat ket noi voi ktts");
                    WSSynLog.insertSynLog(synLog);
                    return resultDTO;
                } else {
                    //Ghi ra Kpi_log
                    resultRealSyn = SYN_SUCC;
                    //LUU THONG TIN LOG
                    synLog.setDescription(resultSyn.getReason());
                    synLog.setStatus(resultSyn.getStatus());
                    WSSynLog.insertSynLog(synLog);
                    //NEU RESPONSE_CODE TRA VE LOI -> COMMIT
                    if (resultSyn.getStatus() == null || !resultSyn.getStatus().equalsIgnoreCase(KTTS_RESPONSE_SUCCESS)) {
                        resultRealSyn = SYN_FAIL;
                        reasonSyn = resultSyn.getDescription();
                        //LUU
                        if (resultSyn.getStatus() == null) {
                            System.out.println("LOG: KTTS TRA VE TRANG THAI NULL");
                        }
                        resultDTO.setMessage(ParamUtils.ERROR_MESSAGE.REFUSED_BY_KTTS);
                        resultDTO.setKey(resultSyn.getReason());
                        //ROLLBACK
                        rollBackOrderAndOrderAction(orders, orderActionDTO, previousOrderActionStatus);
                        rollback(session, transaction);
                        return resultDTO;
                    }
                }

            }
            //duyot
            //commit thong tin
            commit(session, transaction);
            resultDTO.setMessage(ParamUtils.SUCCESS);
            //Bat dau ghi thoi gian thuc nhap ra log

            endTimeSyn = System.currentTimeMillis() - startTimeSyn;
//            descriptionSyn = "Thoi gian thuc nhap ben HT " + sysSyn + " = " + endTimeSyn;
//            resultRealSyn = SYN_SUCC;
            sEndTimeSyn = DateUtil.sysdateString();
            String messageTimeCalc = "Thoi gian thuc nhap yc: " + orders.getOrderCode() + " " + sysSyn + " : " + endTimeSyn + " | LOGISTICS : " + endTimeLogistics;
            System.out.println(messageTimeCalc);
            KPILogger.createLogs(messageTimeCalc);
        } catch (Exception e) {
            resultDTO.setMessage(ParamUtils.SYSTEM_OR_DATA_ERROR);
            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
            //Dien cac thong tin ghi log            
            sEndTime = DateUtil.sysdateString();
//            reason = e.getMessage();
            reasonLog = resultDTO.getMessage();
            resultLog = SYN_FAIL;
            duration = String.valueOf(System.currentTimeMillis() - startTime);
            //Rollback cac thao tac truoc do
            rollback(session, transaction);
            return resultDTO;
        } finally {
            //Ket thuc thuc nhap KTTS            
            //QuyenDM - Ghi log thoi gian xu ly cua LOGISITC cho phan thuc nhap            
            synRealKpiLog.setEndTime(sEndTime);
            synRealKpiLog.setFunctionCode(functionCode);
            synRealKpiLog.setStockTransStatus(resultLog);
            description = "Thuc nhap dong bo tren HT LOG";
            synRealKpiLog.setDescription(description);
            synRealKpiLog.setReason(reasonLog);
            synRealKpiLog.setTransactionCode(transactionCode);
            synRealKpiLog.setDuration(duration);
            kpiLogBusiness.createKpiLog(synRealKpiLog);
            if (!"".equals(resultRealSyn)) {
                //QuyenDM - Ghi log thoi gian xu ly cua HT dong bo cho phan thuc nhap            
                synRealKpiLogSyn.setEndTime(sEndTimeSyn);
                synRealKpiLogSyn.setFunctionCode(functionCodeSyn);
                synRealKpiLogSyn.setStockTransStatus(resultRealSyn);
                synRealKpiLogSyn.setReason(reasonSyn);
                synRealKpiLogSyn.setDescription(descriptionSyn);
                synRealKpiLogSyn.setTransactionCode(transactionCodeSyn);
                durationSyn = String.valueOf(endTimeSyn);
                synRealKpiLogSyn.setDuration(durationSyn);
                kpiLogBusiness.createKpiLog(synRealKpiLogSyn);
            }
        }
        return resultDTO;
    }

    @Override
    public List<StockGoodsSerialInforDTO> getListErrorImportRevoke(String stockTransId) {
        return stockGoodsSerialBusiness2.getListErrorImportRevoke(stockTransId);
    }

    //duyot: ham goi khi tao lenh dong.
    /*
     @usage: ham goi khi viet phieu nhap (tao lenh dong bo voi ht bccs - ktts)
     @return: resultDTO
     @params: order action; trans infor
     @step:
     1. luu thong tin lenh...
     2. gui thong tin phoi phieu
     3. ok -> commit tra ve ket qua
     */
    @Override
    public ResultDTO synImportStockCust(StockTransDTO stockTransDTO) {
        //Do KPI
        KpiLogDTO synKpiLog = new KpiLogDTO();
        String sStartTime = DateUtil.sysdateString();
        String functionCode;
        String reason;
        String strResult = "";
        String descr;
        String transactionCode = null;
        synKpiLog.setStartTime(sStartTime);
        synKpiLog.setCreateDatetime(sStartTime);
        //Ket thuc khoi tao doi tuong do kpi
        String stockTransDetailId;
        List<StockTransSerialDTO> filterListStockTransSerialDTO;
        String stockTransCode;
        String stockTransSEQ;
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        Map<String, GoodsDTO> mapGoodsDTO;

        int insertSuccess = 0;
        int insertFail = 0;
        Double amountIssue;
        //
        String stockTransId = "";
        String sysdate;
        //INIT TRANSACTION
        Session session;
        Transaction transaction;
        Connection connection = null;
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        //
        sysdate = stockGoodsBusiness.getSysDate(formatDate);
        GoodsDTO goodsDTO;
        //du lieu cho dong bo bccs
        OrdersDTO ordersDTO = null;
        long startTime = System.currentTimeMillis();
        long time;
        //=====================================        
        List<StockTransDetailDTO> lstStockTransDetail = new ArrayList<>();
        List<StockTransDetailDTO> lstStockTransDetailDTOsImportAndUpdate = new ArrayList<>();
        //DUYOT: 27/01/2016: loc ra danh sach hang kit
        for (StockTransDetailDTO i : stockTransDTO.getLstStockTransDetailDTO()) {
            //Neu TH nhap kit --> se truyen vao transfersGoodsCode la code cua hang hoa sim--> Thuc hien xuat sim va nhap kit
            if (!DataUtil.isStringNullOrEmpty(i.getTransfersGoodsCode())) {
                lstStockTransDetailDTOsImportAndUpdate.add(i);
            } else {
                lstStockTransDetail.add(i);
            }
        }

        //DUYOT: 28/01/2016: check lai trang thai cua yeu cau - lenh---------------------
        //CHECK THONG TIN YEU CAU - LENH CO TRANG THAI HOP LE-------------------
        String orderIdList = stockTransDTO.getOrderIdList();
        if (orderIdList == null || orderIdList.equals("")) {
            resultDTO.setMessage(ParamUtils.FAIL);
            resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_IE);
            return resultDTO;
        }
        try {
            ordersDTO = WSOrders.findOrderById(orderIdList);
        } catch (Exception ex) {
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ordersDTO != null && ORDER_STATUS_IMPORTED_EXPORTED.equalsIgnoreCase(ordersDTO.getOrderStatus())) {
            resultDTO.setMessage(ParamUtils.FAIL);
            resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_IE);
            return resultDTO;
        }
        //---------------------------------------------------------------------------------
        //
        functionCode = "CREATE_BILL_IMPORT_LOG";
        if (ordersDTO != null) {
            transactionCode = ordersDTO.getOrderCode();
        }
        try {
            //-------------------------------------------------------------------  
            //1. KHOI TAO CONNECTION
            //-------------------------------------------------------------------    
            SessionFactory sessionFactoryBatch = session.getSessionFactory();
            connection = sessionFactoryBatch.getSessionFactoryOptions().
                    getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            connection.setAutoCommit(false);
            //-------------------------------------------------------------------
            //THUC HIEN GIAO DICH THU HOI DOI VOI HANG KIT ( XUAT SIM -> NHAP KIT)-----------------
            if (!DataUtil.isListNullOrEmpty(lstStockTransDetailDTOsImportAndUpdate)) {
                System.out.println("LOG: BAT DAU THUC HIEN DONG BO NHAP KIT");
                StockTransDTO exportStockTransDTO = (StockTransDTO) DataUtil.cloneObject(stockTransDTO);
                exportStockTransDTO.setStockTransStatus(ParamUtils.TRANS_STATUS.WAITING_IMPORT);
                //duyot 27/01/2016
                //tao danh sach serial cho tat cat hang hoa -> loc sau
                List<StockTransSerialDTO> lstStockTransSerialDTO = new ArrayList<>();
                int countTemp = 0;
                for (StockTransDetailDTO i : lstStockTransDetailDTOsImportAndUpdate) {
                    //set lai lst temp de filter
                    String temp = countTemp + "";
                    i.setTmpStockTransDetailId(temp);
                    //
                    List<StockTransSerialDTO> lstSerialInDetail = i.getLstStockTransSerialDTO();
                    for (StockTransSerialDTO j : lstSerialInDetail) {
                        j.setTmpStockTransDetailId(temp);
                        lstStockTransSerialDTO.add(j);
                    }
//                    lstStockTransSerialDTO.addAll(lstSerialInDetail);
                    countTemp++;
                }
                //
                exportStockTransDTO.setStockTransType("2");
                exportStockTransDTO.setStockTransId(null);
                //Lay danh sach goods code tu transfersGoodsCode
                String goodsCodeList = getGoodsCodeList(lstStockTransDetailDTOsImportAndUpdate);
                List<ConditionBean> lstConditionBean = new ArrayList<>();
                lstConditionBean.add(new ConditionBean("custId", ParamUtils.OP_EQUAL, stockTransDTO.getCustId(), ParamUtils.TYPE_NUMBER));
                lstConditionBean.add(new ConditionBean("code", ParamUtils.OP_IN, goodsCodeList, ParamUtils.TYPE_STRING));
                List<GoodsDTO> lstGoodsDTO = goodsBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "", "code");
                //DAY VAO MAP DANH SACH HANG HOA
                Map<String, GoodsDTO> mapGoodsCode2DTO = DataUtil.putGoodsCodeToMap(lstGoodsDTO);
                ResultDTO result;
                // Map giua hang da xuat -> danh sach stock_goods cua hang do
                Map<String, List<StockGoods>> mapStockgoods = new HashMap<>();
                Map<String, List<StockGoodsSerialStrip>> mapSerialTrip = new HashMap<>();
                //Danh sach sim trang se xuat kho
                List<StockTransDetailDTO> lstExportDetail = DataUtil.cloneList(lstStockTransDetailDTOsImportAndUpdate);
                GoodsDTO goods;
                for (StockTransDetailDTO o : lstExportDetail) {
                    goods = mapGoodsCode2DTO.get(o.getTransfersGoodsCode());
                    o.setGoodsCode(goods.getCode());
                    o.setGoodsId(goods.getGoodsId());
                    o.setGoodsName(goods.getName());
                }
                //1--> BEGIN TRANS
                    /*
                 - khi da xuat hang -> dua ra map hang da ban cho phan nhap
                 */
                //-->2 THUC HIEN GIAO DICH XUAT MAT HANG SIM TRANG
                result = exportStockGoodsTransfer(exportStockTransDTO, lstExportDetail,
                        DataUtil.cloneList(lstStockTransSerialDTO), session, transaction, mapStockgoods, mapSerialTrip);
                if (!result.getMessage().equals(ParamUtils.SUCCESS)) {
                    return result;
                }
                //-->3 THUC HIEN GIAO DICH NHAP KIT DUOC XUAT TU SIM TRANG
                //1. Cap nhat vao transaction
                //set fromstocktransid
                stockTransDTO.setFromStockTransId(result.getId());
                //
                List<GoodsInTicketDTO> lstGoods = new ArrayList<>();
                stockTransDTO.setStockTransStatus(ParamUtils.TRANS_STATUS.WAITING_IMPORT);
                result = importStockGoodsTransfer(stockTransDTO, lstStockTransDetailDTOsImportAndUpdate, lstExportDetail,
                        DataUtil.cloneList(lstStockTransSerialDTO), DataUtil.cloneList(lstStockTransSerialDTO),
                        session, transaction, mapStockgoods, mapSerialTrip, lstGoods);
                if (!result.getMessage().equals(ParamUtils.SUCCESS)) {
                    return result;
                }
                stockTransId = result.getId();
            }
            //END QUYENDM: KET THUC NHAP KIT-------------------------------------------------------------
            //Ghi log ra file KPI        
            String synSys = Constants.TYPE_ORDERS_BCCS.equalsIgnoreCase(ordersDTO.getSourceOrder()) ? "BCCS" : "KTTS";
            String description = "Viet phieu nhap dong bo " + synSys + "| YC: " + orderIdList;
            System.out.println(DateUtil.sysdateString() + " | " + description);
            //DOI VOI HANG HOA KHONG PHAI LA HANG KIT---------------------------------------------------
            if (!DataUtil.isListNullOrEmpty(lstStockTransDetail)) {
                //Kiem tra xem giao dich nhap thu hoi hay nhap moi
                if (ordersDTO.getInputType() != null && !ordersDTO.getInputType().equalsIgnoreCase("1")) {
                    //Begin - ThienNG1 - NEU LA GIAO DICH THU HOI
//                    resultDTO = reSyncImportRecovered(stockTransDTO, Constants.STATUS_SERIAL_WAIT_STOCK, session, transaction);
                    //Begin - QuyenDM 20160413 - VIET LAI GIAO DICH THU HOI
                    //----------------------------------------
                    //1. KHOI TAO CONNECTION                    
                    resultDTO = reSyncImportRecovered(stockTransDTO, Constants.STATUS_SERIAL_WAIT_STOCK, connection, session);
                    if (!resultDTO.getMessage().equalsIgnoreCase(ParamUtils.SUCCESS)) {
                        rollback(session, transaction, connection);
                        return resultDTO;
                    }

                    //End - QuyenDM 20160413 - VIET LAI GIAO DICH THU HOI
                } else {
                    //NEU LA NHAP HANG BT
                    //INSERT GIAO DICH STOCK_TRANS
                    stockTransSEQ = stockTransBusiness.getSequence("STOCK_TRANS_SEQ");
                    stockTransCode = ParamUtils.CODE_IMPORT_STOCK + stockTransSEQ;
                    stockTransDTO.setStockTransCode(stockTransCode);
                    stockTransDTO.setCreateDatetime(sysdate);
                    stockTransDTO.setStockTransDate(sysdate);
                    stockTransDTO.setStockTransId(stockTransSEQ);
//                    resultDTO = stockTransBusiness.createObjectSession(stockTransDTO, session);
                    resultDTO = commonBusinessInterface.insertStockTrans(stockTransDTO, connection);

                    stockTransId = stockTransDTO.getStockTransId();
                    stockTransDTO.setStockTransId(stockTransId);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction, connection);
                        return resultDTO;
                    }
                    //DAY VAO MAP DANH SACH HANG HOA
                    String goodsIdList = getGoodsIdList(lstStockTransDetail);
                    List<ConditionBean> lstConditionBean = new ArrayList<>();
                    lstConditionBean.add(new ConditionBean("custId", ParamUtils.OP_EQUAL, stockTransDTO.getCustId(), ParamUtils.TYPE_NUMBER));
                    lstConditionBean.add(new ConditionBean("goodsId", ParamUtils.OP_IN, goodsIdList, ParamUtils.TYPE_NUMBER));
                    List<GoodsDTO> lstGoodsDTO = goodsBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "", "code");
                    mapGoodsDTO = DataUtil.putGoodsToMap(lstGoodsDTO);
                    //LOOP: CHI TIET CHO TUNG MAT HANG
                    for (StockTransDetailDTO stockTransDetailDTO : lstStockTransDetail) {
                        //lay seq
                        String stockTransDetailSEQ = stockTransDetailBusiness.getSequence("STOCK_TRANS_DETAIL_SEQ");
                        stockTransDetailDTO.setStockTransId(stockTransId);
                        stockTransDetailDTO.setStockTransDetailId(stockTransDetailSEQ);
                        stockTransDetailDTO.setStockTransDate(sysdate);
                        stockTransDetailDTO.setCreateDatetime(sysdate);
                        goodsDTO = mapGoodsDTO.get(stockTransDetailDTO.getGoodsId());
                        if (goodsDTO == null) {
                            rollback(session, transaction, connection);
                            resultDTO.setMessage(ParamUtils.GOODS_IS_NOT_EXIST);
                            resultDTO.setKey(ParamUtils.GOODS_IS_NOT_EXIST);
                            return resultDTO;
                        }
                        //INSERT CHI TIET GIAO DICH KHO STOCK_TRANS_DETAIL
                        stockTransDetailDTO.setGoodsCode(goodsDTO.getCode());
                        stockTransDetailDTO.setGoodsName(goodsDTO.getName());
                        stockTransDetailDTO.setGoodsIsSerial(goodsDTO.getIsSerial());
                        stockTransDetailDTO.setGoodsIsSerialStrip(goodsDTO.getIsSerialStrip());
                        //------
                        resultDTO = commonBusinessInterface.insertStockTransDetail(stockTransDetailDTO, connection);
                        //-------------
                        stockTransDetailDTO.setStockTransDetailId(stockTransDetailSEQ);
                        stockTransDetailId = stockTransDetailSEQ;
                        //
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction, connection);
                            resultDTO.setMessage(Constants.ERROR_MESSAGE.INSERT_STOCK_TRANS_DETAIL_ERROR);
                            resultDTO.setKey(Constants.ERROR_MESSAGE.INSERT_STOCK_TRANS_DETAIL_ERROR);
                            return resultDTO;
                        }
                        //MAT HANG K THEO SERIAL
                        if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                            //Cap nhat mat hang theo so luong STOCK_GOODS
                            resultDTO = importStockGoods(stockTransDTO, stockTransDetailDTO, session, ParamUtils.GOODS_IMPORT_STATUS.WAITING_IMPORT);
                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                rollback(session, transaction, connection);
                                return resultDTO;
                            }
                        } else //MAT HANG QUAN LY THEO SERIAL
                        {
                            //LAY RA DANH SACH SERIAL CUA HANG HOA TUONG UNG
                            filterListStockTransSerialDTO = stockTransDetailDTO.getLstStockTransSerialDTO();
                            if (filterListStockTransSerialDTO == null || filterListStockTransSerialDTO.size() < 1) {
                                resultDTO.setMessage(ParamUtils.FAIL);
                                resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                                rollback(session, transaction, connection);
                                return resultDTO;
                            }
                            //Insert giao dich chi tiet serial
                            amountIssue = 0D;
                            //khoi tao list serial duoc nhap kho cho mat hang nay
                            for (StockTransSerialDTO stockTransSerialDTO : filterListStockTransSerialDTO) {
                                //Cap nhat Id giao dich, ID chi tiet giao dichj
                                stockTransSerialDTO.setStockTransId(stockTransId);
                                stockTransSerialDTO.setStockTransDetailId(stockTransDetailId);
                                stockTransSerialDTO.setStockTransDate(sysdate);
                                stockTransSerialDTO.setCreateDatetime(sysdate);
                                stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                                stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                            }
                            //Insert batch VAO KHO STOCK_GOODS_SERIAL
                            resultDTO = importStockGoodsListSerial(stockTransDTO, stockTransDetailDTO, filterListStockTransSerialDTO, session, connection, ParamUtils.GOODS_IMPORT_STATUS.WAITING_IMPORT);
                            insertSuccess = insertSuccess + resultDTO.getQuantitySucc();
                            insertFail = insertFail + resultDTO.getQuantityFail();
                            amountIssue = resultDTO.getAmountIssue();
                            //

                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                rollback(session, transaction, connection);
                                return resultDTO;
                            }
                            //KIEM TRA SO LUONG UPDATE SERIAL -> NEU CO LOI -> DAU RA THONG BAO
                            if (insertFail > 0) {
                                resultDTO.setQuantitySucc(insertSuccess);
                                resultDTO.setQuantityFail(insertFail);
                                resultDTO.setId(stockTransId);
                                resultDTO.setMessage(Constants.ERROR_MESSAGE.IS_OVERLAP);
                                rollback(session, transaction, connection);
                                return resultDTO;
                            }
                            //END FOR FILTER LIST
                            //CAP NHAT LAI STOCK_TRANS_DETAIL VOI SO LUONG INSERT THANH CONG
                            stockTransDetailDTO.setAmountReal(amountIssue.toString().replace(".0", ""));
                            int isUpdate = stockGoodsSerialBusiness2.updateStockTransDetail(stockTransDetailId, amountIssue, connection);
                            //neu update khong thanh cong
                            if (isUpdate < 1) {
                                resultDTO.setMessage(ParamUtils.FAIL);
                                resultDTO.setKey("UPDATE_STOCK_TRANS_DETAIL_ERROR");
                                rollback(session, transaction, connection);
                                return resultDTO;
                            }
                        }
                    }//KET THUC NHAP HANG BT
                    //
                }//KET THUC NHHAP K PHAI KIT
            }
            //DUYOT: KET THUC: GOI HAM DONG BO--------------------------------------
        /*
             duyot: dong bo sang bccs: gui thong tin thuc nhap
             1. lay ra thong tin nhap kho theo format: billstock-listgoods-list serial
             2. goi sang service bccs
             3. check ket qua tra ve
             */
            resultDTO = getListOrdersTicket(stockTransDTO, ordersDTO, connection, session);
            if (!ParamUtils.SUCCESS.equals(resultDTO.getMessage())) {
                rollback(session, transaction, connection);
//                    resultDTO.setMessage(ParamUtils.FAIL);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.SYNC_FAIL);
                return resultDTO;
            }
            //neu hoan toan k co loi -> commit
            commit(session, transaction, connection);

        } catch (Exception e) {
            rollback(session, transaction, connection);
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setMessage(e.getMessage());
            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
            return resultDTO;
        } finally {
            time = System.currentTimeMillis() - startTime;
            //Ghi ra file KPI.log
            if (resultDTO.getMessage() != null) {
                strResult = resultDTO.getMessage().equalsIgnoreCase(null) ? resultDTO.getKey() : resultDTO.getMessage();
            }
            //Ghi ra file Catalina.out  
            //Ghi log ra file KPI        
            synKpiLog.setFunctionCode(functionCode);
            synKpiLog.setTransactionCode(transactionCode);
            descr = "Tong thoi gian viet phieu nhap " + transactionCode + " : " + time;
            System.out.println(descr);
            KPILogger.createLogs(descr);
            if (!strResult.equalsIgnoreCase(ParamUtils.SUCCESS)) {
                reason = resultDTO.getMessage();
                synKpiLog.setReason(reason);
                synKpiLog.setStockTransStatus(SYN_FAIL);
            } else {
                synKpiLog.setStockTransStatus(SYN_SUCC);
            }
            synKpiLog.setEndTime(DateUtil.sysdateString());
            synKpiLog.setDuration(String.valueOf(time));
            synKpiLog.setDescription("Tao phieu nhap dong bo tren HT LOG");
            //Ghi ra bang kpi_log
            kpiLogBusiness.createKpiLog(synKpiLog);
            try {
                if (session.isOpen()) {
                    session.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        resultDTO.setQuantitySucc(insertSuccess);
        resultDTO.setQuantityFail(insertFail);
        resultDTO.setId(stockTransId);

        return resultDTO;

    }

    public ResultDTO getListOrdersTicket(StockTransDTO stockTransDTO,
            OrdersDTO orders, Connection con, Session session) {
        getData2CreateCommandSyn(stockTransDTO, orders);
        List<OrdersDTO> lstOrderDTO = new ArrayList<>();
        ResultDTO result = new ResultDTO();
        String orderCode = orders.getOrderCode();
        ResultSyn resultSyn;
        String sourceOrder = orders.getSourceOrder().trim();
        boolean isFromBCCS = sourceOrder.equalsIgnoreCase(ORDER_SOURCE_BCCS);
        //------duyot: 2/11/2015------------------------------------------------
        //bo sung: them truong stockerCode khi gui sang BCCS
        String stockerCode = "";
        //2/ lay account V-office cua staff
        StaffDTO staff = WSStaff.findStaffById(stockTransDTO.getTransUserId());
        if (staff != null) {
            stockerCode = (String) DataUtil.nvl(staff.getVofficeAccount(), "");
        }
        //----------------------------------------------------------------------
        //QuyenDM 20160216 - Ghi log kpi ra DB
        //Do thoi gian chay cua ham thuc xuat ben KTTS va BCCS        
        String synSys = Constants.TYPE_ORDERS_BCCS.equalsIgnoreCase(orders.getSourceOrder()) ? "VTT" : "VTN";
        String functionCode = "CREATE_BILL_IMPORT_" + synSys;
        String startTime = DateUtil.sysdateString();
        String transactionCode = orders.getOrderCode();
        KpiLogDTO stockImportKpi = new KpiLogDTO();
        stockImportKpi.setStartTime(startTime);
        stockImportKpi.setCreateDatetime(startTime);
        stockImportKpi.setTransactionCode(transactionCode);
        stockImportKpi.setFunctionCode(functionCode);
        long lstartTime = System.currentTimeMillis();
        //-----------------------------------------------------------------------
        if (isFromBCCS) {
            orders.setSourceOrder(null);
            orders.setOrderDatetime(null);
            orders.setCustName(null);
            //------------------------------------------------------------------
            //duyot: set lai null list detail trong serial
            orders.setListGood(null);
            List<OrderGoodsDetailDTO> lstDetail = orders.getLstOrderGoodsDetailDTO();
            if (!DataUtil.isListNullOrEmpty(lstDetail)) {
                for (OrderGoodsDetailDTO i : lstDetail) {
                    i.setLstOrderGoodsDetailSerialDTO(null);
                }
            }
            //------------------------------------------------------------------
            orders.setOrderAction(stockTransDTO.getOrderAction());
            orders.setLstOrderGoodsLocationDTO(null);
            //set stockerCode to order
            orders.setStockerCode(stockerCode);
            //
            lstOrderDTO.add(orders);
            DataBccs data = new DataBccs(lstOrderDTO);
            resultSyn = WSSynchonize.callServiceCreateBillBccs(data);

        } else {
            orders.setSourceOrder(null);
            orders.setOrderDatetime(null);
            orders.setCustName(null);
            lstOrderDTO.add(orders);
            //------------------------------------------------------------------
            orders.setLstOrderGoodsDetailDTO(null);
            //duyot: 13/04: set lai lst serial------------------------------
            List<OrderGoodsDetailDTO> lstDetail = orders.getListGood();
            if (!DataUtil.isListNullOrEmpty(lstDetail)) {
                for (OrderGoodsDetailDTO i : lstDetail) {
                    i.setLstDetailSerial(null);
                }
            }
            //------------------------------------------------------------------
            orders.setLstOrderGoodsLocationDTO(null);
            orders.setOrderAction(stockTransDTO.getOrderAction());
            //duyot
            orders.setContent(stockTransDTO.getNotes());
            orders.setTransUserName(stockTransDTO.getTransUserName());

            resultSyn = WSSynchonize.callServiceCreateBillKtts(orders);
        }

        long time = System.currentTimeMillis() - lstartTime;
        System.out.println(DateUtil.sysdateString() + " Thoi gian viet phieu nhap " + synSys + " YC: " + orders.getOrderCode() + " la: " + time);
        String description = " Thoi gian viet phieu nhap " + synSys + " YC: " + orders.getOrderCode() + " la: " + time;
        KPILogger.createLogs(description);
        String sysdate = stockGoodsBusiness.getSysDate(formatDate);
        SynLogDTO synlogCreateBill = new SynLogDTO();
        synlogCreateBill.setAppCode(ParamUtils.APP_CODE.CREATE_IMPORT_STOCK_COMMAND);
        synlogCreateBill.setDateTime(sysdate);
        synlogCreateBill.setKey(orderCode);
        stockImportKpi.setDescription("Tao phieu nhap dong bo tren HT " + synSys);
        //------------------------------------------------------------------
        if (resultSyn == null) {
            //LUU THONG TIN SYN_LOG
            synlogCreateBill.setStatus("69");
            synlogCreateBill.setDescription("connection False");
            //LUU THONG TIN TRA VE RESULT
            result.setMessage(ParamUtils.APP_CODE.CONNECT_FAIL);
            result.setKey(ParamUtils.ERROR_MESSAGE.SYNC_FAIL);
        } else {//NEU CO THONG TIN TRA VE
            synlogCreateBill.setStatus(resultSyn.getStatus());
            //duyot: 04/02/2015
            if (synlogCreateBill.getStatus() == null) {//TRA VE NULL
                result.setMessage(ParamUtils.APP_CODE.CONNECT_FAIL);
                result.setKey(ParamUtils.ERROR_MESSAGE.SYNC_FAIL);
                //
                synlogCreateBill.setDescription("RETURN NULL");
            } else if (resultSyn.getStatus().equalsIgnoreCase("0")) {
                //NEU KHONG THANH CONG
//                lay thong tin ly do
                String failReason;
                //neu la bccs
                if (isFromBCCS) {
                    failReason = resultSyn.getReason();
                } else {
                    failReason = synlogCreateBill.getDescription();
                }
                //
                result.setMessage(failReason);
                result.setKey(ParamUtils.ERROR_MESSAGE.SYNC_FAIL);
                //
                synlogCreateBill.setDescription(failReason);
            } else {
//              NEU THANH CONG
                //lay thong tin syn_trans_code
                String synTransCode;
                if (isFromBCCS) {
                    synTransCode = resultSyn.getSynTransCode();
                } else {
                    synTransCode = resultSyn.getReason();
                }
                //LUU THONG TIN MA PHIEU VAO KEY
                result.setKey(synTransCode);
                result.setMessage(ParamUtils.SUCCESS);
                //
                synlogCreateBill.setDescription(ParamUtils.SUCCESS);
                //duyot 11/01/2016
                //cap nhat lai thong tin bang stock_trans: truong syn_trans_code
                //21/03/2016: neu la tu ktts thi moi cap nhat
                if (!DataUtil.isStringNullOrEmpty(synTransCode) && sourceOrder != null) {
                    if (con != null && orders.getInputType().equalsIgnoreCase("1")) {//NEU LA NHAP BT
                        ResultDTO resultUpdateTrans = commonBusinessInterface.updateSynTransCode(stockTransDTO.getStockTransId(), synTransCode, con);
                        if (resultUpdateTrans != null && !resultUpdateTrans.getMessage().equalsIgnoreCase(ParamUtils.SUCCESS)) {
                            synlogCreateBill.setDescription("Cap nhat ma phieu: " + synTransCode + " tu khong thanh cong ( connection )");
                        }
                    } else {//NEU LA NHAP HANG THU HOI
                        ResultDTO resultUpdateTrans = commonBusinessInterface.updateSynTransCode(stockTransDTO.getStockTransId(), synTransCode, con);
                        if (resultUpdateTrans != null && !resultUpdateTrans.getMessage().equalsIgnoreCase(ParamUtils.SUCCESS)) {
                            synlogCreateBill.setDescription("Cap nhat ma phieu: " + synTransCode + " tu khong thanh cong ( connection )");
                        }
//                        stockTransDTO.setSynTransCode(synTransCode);
//                        /*
//                         duyot: test cap nhat lai stock_trans
//                         */
//                        ResultDTO resultUpdateTrans = commonBusinessInterface.updateSynTransCodeUsingSession(stockTransDTO, session);
//                        if (resultUpdateTrans != null && !resultUpdateTrans.getMessage().equalsIgnoreCase(ParamUtils.SUCCESS)) {
//                            synlogCreateBill.setDescription("Cap nhat ma phieu: " + synTransCode + " tu khong thanh cong ( session )");
//                        }
                    }
                    //END
                }
            }//END THANH CONG

        }
        //GHI LOG - TRA VE KET QUA
        WSSynLog.insertSynLog(synlogCreateBill);
        //Ghi log kpi        
        if (!SYN_SUCC.equals(DataUtil.getStringNullOrZero(resultSyn == null ? "0" : resultSyn.getStatus()))) {
            stockImportKpi.setReason(result.getMessage());
            stockImportKpi.setStockTransStatus(SYN_FAIL);
        } else {
            stockImportKpi.setStockTransStatus(SYN_SUCC);
        }
        stockImportKpi.setDuration(String.valueOf(time));
        stockImportKpi.setEndTime(DateUtil.sysdateString());
        kpiLogBusiness.createKpiLog(stockImportKpi);
//        System.out.println("QuyenDM ghi kpi_log");
        return result;
    }

    public OrdersDTO getData2CreateCommandSyn(StockTransDTO stockTransDTO, OrdersDTO ordersDTO) {
        OrderGoodsDetailDTO orderGoodsDetailDTO;
        OrderGoodsDetailSerialDTO orderGoodsDetailSerialDTO;
        List<OrderGoodsDetailDTO> lstOrderGoodsDetailDTO = Lists.newArrayList();
        List<OrderGoodsDetailSerialDTO> lstOrderGoodsDetailSerialDTO;
        StockTransDetailDTO stockTransDetailDTO;
        StockTransSerialDTO stockTransSerialDTO;
        List<StockTransDetailDTO> lstStockTransDetailDTO;
        List<StockTransSerialDTO> lstStockTransSerialDTO;
        lstStockTransDetailDTO = stockTransDTO.getLstStockTransDetailDTO();
        for (StockTransDetailDTO dTO : lstStockTransDetailDTO) {
            orderGoodsDetailDTO = new OrderGoodsDetailDTO();
            orderGoodsDetailDTO.setGoodsCode(dTO.getGoodsCode());
            orderGoodsDetailDTO.setGoodsName(dTO.getGoodsName());
            orderGoodsDetailDTO.setAmount(dTO.getAmountReal());
            orderGoodsDetailDTO.setAmountOrder(DataUtil.getQuantity(dTO.getAmountOrder()));
            orderGoodsDetailDTO.setAmountReal(DataUtil.getQuantity(dTO.getAmountReal()));
            orderGoodsDetailDTO.setGoodsUnitName(dTO.getGoodsUnitTypeName());
            orderGoodsDetailDTO.setGoodsState(dTO.getGoodsState());
            lstOrderGoodsDetailSerialDTO = Lists.newArrayList();
            lstOrderGoodsDetailDTO.add(orderGoodsDetailDTO);
            for (StockTransSerialDTO transSerialDTO : dTO.getLstStockTransSerialDTO()) {
                orderGoodsDetailSerialDTO = new OrderGoodsDetailSerialDTO();
                orderGoodsDetailSerialDTO.setFromSerial(transSerialDTO.getFromSerial());
                orderGoodsDetailSerialDTO.setToSerial(transSerialDTO.getToSerial());
                orderGoodsDetailSerialDTO.setQuantity(transSerialDTO.getAmountReal());
                lstOrderGoodsDetailSerialDTO.add(orderGoodsDetailSerialDTO);
            }
            orderGoodsDetailDTO.setLstOrderGoodsDetailSerialDTO(lstOrderGoodsDetailSerialDTO);
            orderGoodsDetailDTO.setLstDetailSerial(lstOrderGoodsDetailSerialDTO);
        }
        ordersDTO.setLstOrderGoodsDetailDTO(lstOrderGoodsDetailDTO);
        ordersDTO.setListGood(lstOrderGoodsDetailDTO);
        return ordersDTO;
    }

    @Override
    public List<StockGoodsSerialInforDTO> getListSerilStripErrorImportRevoke(String stockTransId) {
        return stockGoodsSerialStripBusiness2.getListSerilStripErrorImportRevoke(stockTransId);
    }

    //QuyenDM 20160411 - Nhap hang thu hoi (Viet lai service)
    private ResultDTO reSyncImportRecovered(StockTransDTO stockTransDTO, String synImportRevoke,
            Connection connection, Session session) {
        List<StockTransSerialDTO> filterListStockTransSerialDTO;
        String stockTransCode;
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        Map<String, GoodsDTO> mapGoodsDTO;
        int insertSuccess = 0;
        int insertFail = 0;
        String stockTransId;
        String sysdate = stockGoodsBusiness.getSysDate(formatDate);

        List<StockTransDetailDTO> lstStockTransDetailDTO = stockTransDTO.getLstStockTransDetailDTO();
        try {
            //Insert giao dich kho
            stockTransId = stockTransBusiness.getSequence("STOCK_TRANS_SEQ");
            stockTransCode = ParamUtils.CODE_IMPORT_STOCK + stockTransId;
            stockTransDTO.setStockTransCode(stockTransCode);
            stockTransDTO.setCreateDatetime(sysdate);
            stockTransDTO.setStockTransDate(sysdate);
            stockTransDTO.setStockTransId(stockTransId);
            resultDTO = commonBusinessInterface.insertStockTrans(stockTransDTO, connection);
            System.out.println("QuyenDM: Check " + stockTransDTO.getSynTransCode());
            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                return resultDTO;
            }
            //Lay danh sach hang hoa yeu cau day vao map
            String goodsIdList = getGoodsIdList(lstStockTransDetailDTO);
            List<ConditionBean> lstConditionBean = new ArrayList<>();
            lstConditionBean.add(new ConditionBean("custId", ParamUtils.OP_EQUAL, stockTransDTO.getCustId(), ParamUtils.TYPE_NUMBER));
            lstConditionBean.add(new ConditionBean("goodsId", ParamUtils.OP_IN, goodsIdList, ParamUtils.TYPE_NUMBER));
            List<GoodsDTO> lstGoodsDTO = goodsBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "", "code");
            mapGoodsDTO = DataUtil.putGoodsToMap(lstGoodsDTO);
            GoodsDTO goodsDTO;
            //Giao dich chi tiet            
            String stockTransDetailSEQ;
            for (StockTransDetailDTO stockTransDetailDTO : lstStockTransDetailDTO) {
                stockTransDetailSEQ = stockTransDetailBusiness.getSequence("STOCK_TRANS_DETAIL_SEQ");
                stockTransDetailDTO.setStockTransId(stockTransId);
                stockTransDetailDTO.setStockTransDetailId(stockTransDetailSEQ);
                stockTransDetailDTO.setStockTransId(stockTransId);
                stockTransDetailDTO.setStockTransDate(sysdate);
                stockTransDetailDTO.setCreateDatetime(sysdate);
                goodsDTO = mapGoodsDTO.get(stockTransDetailDTO.getGoodsId());
                if (goodsDTO == null) {
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.GOODS_IS_NOT_EXIST);
                    return resultDTO;
                }
                //Insert chi tiet giao dich kho
                stockTransDetailDTO.setGoodsCode(goodsDTO.getCode());
                stockTransDetailDTO.setGoodsName(goodsDTO.getName());
                stockTransDetailDTO.setGoodsIsSerial(goodsDTO.getIsSerial());
                stockTransDetailDTO.setGoodsIsSerialStrip(goodsDTO.getIsSerialStrip());
                resultDTO = commonBusinessInterface.insertStockTransDetail(stockTransDetailDTO, connection);
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(Constants.ERROR_MESSAGE.INSERT_STOCK_TRANS_DETAIL_ERROR);
                    return resultDTO;
                }
                //Neu la mat hang khong quan ly serial
                if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                    //Cap nhat mat hang theo so luong 
                    resultDTO = reImportStockGoods(stockTransDTO, stockTransDetailDTO, session, synImportRevoke);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        return resultDTO;
                    }
                    //Dong bo nen khong cap nhat so luong tong cong
                } else //Mat hang Quan ly theo serial
                {
                    //Lay danh sach chi tiet giao dich serial theo chi tiet giao dich
                    filterListStockTransSerialDTO = stockTransDetailDTO.getLstStockTransSerialDTO();
                    if (filterListStockTransSerialDTO == null || filterListStockTransSerialDTO.size() < 1) {
                        resultDTO.setMessage(ParamUtils.FAIL);
                        resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                        return resultDTO;
                    }
                    //QuyenDM 20160411 - Chia cu the hang quan ly serial theo dai va khong theo dai
                    if (Constants.IS_SERIAL_STRIP.equals(stockTransDetailDTO.getGoodsIsSerialStrip())) {
                        //Hang theo dai van lam nhu ThienNG1
                        //add by ThienNG1 19/08/2015
                        for (StockTransSerialDTO stockTransSerialDTO : filterListStockTransSerialDTO) {
                            //Cap nhat trang thai cho cac serial ql theo dai
                            resultDTO = updateSerialStripReInvoke(stockTransDTO, stockTransDetailDTO,
                                    stockTransSerialDTO, session, synImportRevoke);
                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                return resultDTO;
                            }
                            //Insert vao bang stock_trans_serial giao dich vua thuc hien thanh cong
                            //Id giao dich, ID chi tiet giao dich
                            stockTransSerialDTO.setStockTransId(stockTransId);
                            stockTransSerialDTO.setStockTransDetailId(stockTransDetailDTO.getStockTransDetailId());
                            stockTransSerialDTO.setStockTransDate(sysdate);
                            stockTransSerialDTO.setCreateDatetime(sysdate);
                            stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                            stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                            stockTransSerialDTO.setAmountReal(resultDTO.getAmountIssue().toString().replace(".0", ""));
                            resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                return resultDTO;
                            }
                        }
                    } else {
                        //Hang quan ly theo serial don le
                        resultDTO = updateSerialReInvoke(stockTransDTO, stockTransDetailDTO, connection, synImportRevoke);
                        insertSuccess = resultDTO.getQuantitySucc();
                        insertFail = resultDTO.getQuantityFail();
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            return resultDTO;
                        }
                        //KIEM TRA SO LUONG UPDATE SERIAL -> NEU CO LOI -> DAY RA THONG BAO
                        if (insertFail > 0) {
                            resultDTO.setQuantitySucc(insertSuccess);
                            resultDTO.setQuantityFail(insertFail);
                            resultDTO.setId(stockTransId);
                            resultDTO.setMessage(Constants.ERROR_MESSAGE.IS_OVERLAP);
                            return resultDTO;
                        }
                        //Cap nhat lai so luong hang hoa giao dich
                        stockTransDetailDTO.setAmountReal(String.valueOf(insertSuccess));
                        int isUpdate = stockGoodsSerialBusiness2.updateStockTransDetail(
                                stockTransDetailDTO.getStockTransDetailId(), insertSuccess, connection);
                        //neu update khong thanh cong
                        if (isUpdate < 1) {
                            resultDTO.setMessage(ParamUtils.FAIL);
                            resultDTO.setKey("UPDATE_STOCK_TRANS_DETAIL_ERROR");
                            return resultDTO;
                        }
                        //
                    }
                    //Dong bo nen khong cap nhat so luong tong cong
                }
            }
            //Tim kiem bang loi theo import_stock_trans_id
            //commit(session, transaction);
        } catch (Exception e) {
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setMessage(e.getMessage());
            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
            return resultDTO;
        }

        resultDTO.setQuantitySucc(insertSuccess);
        resultDTO.setQuantityFail(insertFail);
        resultDTO.setId(stockTransId);
        return resultDTO;
    }

    //QuyenDM 20160411 - Thuc hien cap nhat hang hoa co serial theo dai cho hang thu hoi
    private ResultDTO updateSerialStripReInvoke(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO,
            StockTransSerialDTO stockTransSerialDTO, Session session, String synImportRevoke) {
        ResultDTO resultDTO;
        StockGoodsSerialStripDTO oldDTO = new StockGoodsSerialStripDTO();
        StockGoodsSerialStripDTO newDTO = new StockGoodsSerialStripDTO();
        //stockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountReal());
        newDTO.setCustId(stockTransDTO.getCustId());
        newDTO.setOwnerId(stockTransDTO.getOwnerId());
        newDTO.setOwnerType(stockTransDTO.getOwnerType());
        newDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
        newDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
        newDTO.setStatus(synImportRevoke);
        newDTO.setSaleType("");
        newDTO.setChangeUser(stockTransDTO.getTransUserId());
        newDTO.setPrice(null);
        newDTO.setChannelTypeId(null);
        newDTO.setBarcode(stockTransSerialDTO.getBarcode());
        newDTO.setCellCode(stockTransSerialDTO.getCellCode());
        newDTO.setBincode(stockTransSerialDTO.getBincode());
        newDTO.setAddInfor(stockTransSerialDTO.getAddInfor());
        newDTO.setChangeDate(stockTransDTO.getStockTransDate());
        newDTO.setImportDate(stockTransDTO.getStockTransDate());
        newDTO.setSaleDate("");
        newDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
        newDTO.setToSerial(stockTransSerialDTO.getToSerial());
        newDTO.setQuantity(stockTransSerialDTO.getAmountOrder());
        //set partner id
        newDTO.setPartnerId(stockTransDTO.getPartnerId());
        newDTO.setImportStockTransId(stockTransDTO.getStockTransId());
        newDTO.setOrderId(stockTransDTO.getOrderIdList());
        //cac tham so dau vao tim kiem trong DB
        oldDTO.setCustId(stockTransDTO.getCustId());
        oldDTO.setOwnerId(stockTransDTO.getOwnerId());
        oldDTO.setOwnerType(stockTransDTO.getOwnerType());
        oldDTO.setGoodsId(stockTransDetailDTO.getGoodsId());
        //oldStockGoodsSerialStripDTO.setGoodsState(stockTransSerialDTO.getGoodsState());
        //oldStockGoodsSerialStripDTO.setStatus(Constants.STATUS_SERIAL_OUT_STOCK);
        oldDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
        oldDTO.setToSerial(stockTransSerialDTO.getToSerial());

        resultDTO = stockGoodsSerialStripBusiness2.reImportStockGoodsSerialStrip(oldDTO, newDTO,
                stockTransDTO.getStockTransId(), session, synImportRevoke);
        return resultDTO;
    }

    //QuyenDM 20160411 - Thuc hien cap nhat hang hoa co serial don le cho hang thu hoi theo batch
    private ResultDTO updateSerialReInvoke(StockTransDTO stockTransDTO,
            StockTransDetailDTO stockTransDetail, Connection connection, String synImportRevoke) {
        ResultDTO resultDTO = new ResultDTO();
        int quanlitySuccess = 0;
        int quanlityFail = 0;
        List<StockTransSerialDTO> lstStockTransSerials = stockTransDetail.getLstStockTransSerialDTO();
        //Map giua giao dich va danh sach serial
        Map<StockTransSerialDTO, List<String>> mapStockTransSerial2ListSerial = new HashMap<>();
        //Tach nhung serial don le nhung duoc gop thanh nhung serial theo dai
        List<String> lstSerial2Search = DataUtil.buildSingleSerial(lstStockTransSerials, mapStockTransSerial2ListSerial);
        //Lay danh sach stock_goods_serial tu nhung serial nhap len co trang thai = 1
        List<StockGoodsSerialDTO> lstGoodsSerialInStock
                = findStockGoodsSerial(stockTransDTO, stockTransDetail, lstSerial2Search, "1");
        if (!DataUtil.isListNullOrEmpty(lstGoodsSerialInStock)) {
            //Tra ve danh sach loi va thong bao serial da co trong kho                 
            resultDTO.setLstStockGoodsSerialInforDTO(
                    convert2StockGoodsSerialInfor(lstGoodsSerialInStock, stockTransDetail));
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
                    connection, synImportRevoke, true);
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
                    connection, synImportRevoke, false);
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

    //Chuyen doi tu danh sach StockGoodsSerialDTO sang StockGoodsSerialInforDTO
    private List<StockGoodsSerialInforDTO> convert2StockGoodsSerialInfor(
            List<StockGoodsSerialDTO> lstStockGoodsSerials, StockTransDetailDTO stockTransDetail) {
        List<StockGoodsSerialInforDTO> lstReturns = new ArrayList<>();
        StockGoodsSerialInforDTO temp;
        for (StockGoodsSerialDTO sts : lstStockGoodsSerials) {
            temp = sts.convert2StockGoodsSerialInforDTO();
            temp.setAddInfor(Constants.ERROR_MESSAGE.GOODS_SERIAL_IN_STOCK);
            temp.setGoodsCode(stockTransDetail.getGoodsCode());
            temp.setGoodsName(stockTransDetail.getGoodsName());
            //duyot: 20/5: set lai trang thai hang hoa dung theo detail
            temp.setGoodsState(stockTransDetail.getGoodsState());
            //
            lstReturns.add(temp);
        }
        return lstReturns;
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
}
