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
import com.viettel.logistic.wms.dto.BillStock;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.dto.GoodsInTicketDTO;
import com.viettel.logistic.wms.dto.HistoryChangeGoodsDTO;
import com.viettel.logistic.wms.dto.KpiLogDTO;
import com.viettel.logistic.wms.dto.MapStaffGoodsDTO;
import com.viettel.logistic.wms.dto.MessagesDTO;
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
import com.viettel.logistic.wms.model.Stock;
import com.viettel.logistic.wms.model.StockGoods;
import com.viettel.logistic.wms.model.StockGoodsSerialStrip;
import com.viettel.logistic.wms.webservice.WSAppParams;
import com.viettel.logistic.wms.webservice.WSOrderAction;
import com.viettel.logistic.wms.webservice.WSOrders;
import com.viettel.logstic.wms.webservice.dto.AppParamsDTO;
import com.viettel.logstic.wms.webservice.dto.ChangeGoods;
import com.viettel.logstic.wms.webservice.dto.ChangeOrder;
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
import com.viettel.vfw5.base.utils.BundelUtils;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.DateUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.ResourceBundleUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author vtsoft
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.StockExportService")
@Component(value = "stockExportServiceImpl")
public class StockExportServiceImpl implements StockExportService {

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
    StockGoodsSerialServiceInterface stockGoodsSerialBusiness2;
    @Autowired
    StockGoodsSerialStripServiceInterface stockGoodsSerialStripBusiness2;
    @Autowired
    BaseFWServiceInterface stockGoodsSerialErrorBusiness;
    //duyot: bo sung: stock_bussiness
    @Autowired
    BaseFWServiceInterface stockBusiness;
    @Autowired
    CommonBusinessInterface stockTransBusiness2;
    @Autowired
    BaseFWServiceInterface historyChangeGoodsBusiness;
    @Autowired
    KpiLogInterface kpiLogBusiness;
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;
    @Autowired
    BaseFWServiceInterface messagesBusiness;

    //
    String formatDate = "dd/mm/yyyy hh24:mi:ss";
    public final static String ORDER_STATUS_IMPORTED_EXPORTED = "6"; // trang thai yeu cau da thuc xuat/nhap
    public final static String ORDER_STATUS_COMMAN_CREATED = "4"; // trang thai yeu cau da thuc xuat/nhap
    public final static String ORDER_ACTION_STATUS_EXPORTED = "3"; // trang thai lenh da thuc xuat/nhap
    public final static String BCCS_STATUS_SUCCESS = "0";
    public final static String BCCS_STATUS_FAIL = "1";
    public final static String KTTS_STATUS_FAIL = "0";
    public final static String KTTS_STATUS_SUCCESS = "1";
    public final static String SYN_FROM_BCCS = "1";
    public final static String SYN_FROM_KTTS = "2";
    public final static String SYN_SUCC = "1";
    public final static String SYN_FAIL = "0";

    public static final String STOCK_TRANS_TYPE_EXPORT = "2"; // 
    public static final String OWNER_TYPE1 = "1"; // 
    int lengthDescription = 900;
    static List<String> lstCurrentId = Lists.newArrayList();
    static List<String> lstSynCurrentId = Lists.newArrayList();
    //Xuat kho cho khach hang   

    @Override
    public ResultDTO exportStockCust(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetailDTO, List<StockTransSerialDTO> lstStockTransSerialDTO) {

        String stockTransDetailId;
        List<StockTransSerialDTO> lstFilterListStockTransSerialDTO;
        String stockTransCode;
        //
        String sysdate;
        String stockTransId;
        //
        Map<String, GoodsDTO> mapGoodsDTO;
        //
        Session session;
        Transaction transaction;
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        //
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        sysdate = stockGoodsBusiness.getSysDate(formatDate);
        GoodsDTO goodsDTO;
        //
        //du lieu cho dong bo bccs
        BillStock billStock;
        List<GoodsInTicketDTO> lstGoods = new ArrayList<>();
        // lay thong tin yeu cau
        String orderIdList = stockTransDTO.getOrderIdList();
        String messErr = "";
        if (!DataUtil.isStringNullOrEmpty(orderIdList)) {
            if (lstCurrentId.contains(orderIdList)) {
                messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.ORDER_MULTI_USER_PROCESS);
                resultDTO.setMessage(messErr);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_MULTI_USER_PROCESS);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                return resultDTO;
            } else {
                lstCurrentId.add(orderIdList);
            }
        }
        //
        try {
            OrdersDTO ordersDTO = null;
            if (orderIdList != null && !orderIdList.equals("")) {
                ordersDTO = WSOrders.findOrderById(orderIdList);
                if (ordersDTO != null && ORDER_STATUS_IMPORTED_EXPORTED.equalsIgnoreCase(ordersDTO.getOrderStatus())) {
//                    resultDTO.setMessage(ParamUtils.FAIL);
                    // 12/10/15 tiepnv
                    messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.ORDER_IE);
                    messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                    resultDTO.setMessage(messErr);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_IE);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                    return resultDTO;
                }
            }
            //Insert giao dich kho
            stockTransCode = ParamUtils.CODE_EXPORT_STOCK + stockTransBusiness.getSequence("STOCK_TRANS_SEQ");
            stockTransDTO.setStockTransCode(stockTransCode);
            stockTransDTO.setCreateDatetime(sysdate);
            stockTransDTO.setStockTransDate(sysdate);
            resultDTO = stockTransBusiness.createObjectSession(stockTransDTO, session);
            stockTransId = resultDTO.getId();
            stockTransDTO.setStockTransId(stockTransId);
            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                rollback(session, transaction);
                // 12/10/15 tiepnv
                messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_CREATE_STOCKTRANS);
                messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                resultDTO.setMessage(messErr);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                return resultDTO;
            }
            //
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
//                    resultDTO.setMessage(ParamUtils.FAIL);
                    // 12/10/15 tiepnv
                    messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS_IS_NOT_EXIST);
                    messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, stockTransDetailDTO.getGoodsCode());
                    resultDTO.setMessage(messErr);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
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
                    messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_CREATE_STOCKTRANS);
                    messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                    resultDTO.setMessage(messErr);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                    return resultDTO;
                }
                //Neu la mat hang khong quan ly serial
                if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                    //Cap nhat mat hang theo so luong 
                    resultDTO = exportStockGoods(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS));
                        if (!DataUtil.isStringNullOrEmpty(stockTransDetailDTO.getCellCode())) {
                            stringBuilder.append(BundelUtils.getkey(ParamUtils.CELLCODE));
                        }
                        if (!DataUtil.isStringNullOrEmpty(stockTransDetailDTO.getBarcode())) {
                            stringBuilder.append(BundelUtils.getkey(ParamUtils.BARCODE));
                        }
                        stringBuilder.append(BundelUtils.getkey(resultDTO.getKey()));
                        messErr = stringBuilder.toString();
                        messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                        messErr = messErr.replace(ParamUtils.REP_GOODS_STATE, BundelUtils.getkey(ParamUtils.GOODS_STATE_VAL + stockTransDetailDTO.getGoodsState()));
                        if (messErr.contains(ParamUtils.REP_CELL_CODE)) {
                            messErr = messErr.replace(ParamUtils.REP_CELL_CODE, stockTransDetailDTO.getCellCode());
                        }
                        if (messErr.contains(ParamUtils.REP_BARCODE)) {
                            messErr = messErr.replace(ParamUtils.REP_BARCODE, stockTransDetailDTO.getBarcode());
                        }
                        resultDTO.setMessage(messErr);
                        resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                        return resultDTO;
                    }
                    //Cap nhat so luong tong cong
                    resultDTO = exportStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS));
                        stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_ENOUGH_AMOUNT_TOTAL));
                        messErr = stringBuilder.toString();
                        messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                        messErr = messErr.replace(ParamUtils.REP_GOODS_STATE, BundelUtils.getkey(ParamUtils.GOODS_STATE_VAL + stockTransDetailDTO.getGoodsState()));
                        resultDTO.setMessage(messErr);
                        resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                        return resultDTO;
                    }

                    GoodsInTicketDTO goods = createGoodsFromDetail(stockTransDetailDTO);
                    lstGoods.add(goods);

                } else //Mat hang Quan ly theo serial
                {
                    //Lay danh sach chi tiet giao dich serial theo chi tiet giao dich
                    lstFilterListStockTransSerialDTO = filterStockTransSerialDTO(stockTransDetailDTO.getTmpStockTransDetailId(), lstStockTransSerialDTO);

                    // kiem tra du lieu chi tiet serial sai
                    if (lstFilterListStockTransSerialDTO == null || lstFilterListStockTransSerialDTO.isEmpty()) {
                        resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                        resultDTO.setMessage(ParamUtils.FAIL);
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //Insert giao dich chi tiet serial
                    for (StockTransSerialDTO stockTransSerialDTO : lstFilterListStockTransSerialDTO) {
                        //Id giao dich, ID chi tiet giao dich
                        stockTransSerialDTO.setStockTransId(stockTransId);
                        stockTransSerialDTO.setStockTransDetailId(stockTransDetailId);
                        stockTransSerialDTO.setStockTransDate(sysdate);
                        stockTransSerialDTO.setCreateDatetime(sysdate);
                        //Tao chi tiet giao dich serial
                        stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                        stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                        stockTransSerialDTO.setQuantity(stockTransSerialDTO.getAmountReal());
                        resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction);
                            messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_CREATE_STOCKTRANS);
                            messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                            resultDTO.setMessage(messErr);
                            resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                            return resultDTO;
                        }
                        //Xuat kho kho hang hoa theo serial
                        if (stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                            //
                            resultDTO = exportStockGoodsSerial(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, session);
                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                rollback(session, transaction);
                                // tiepnv
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS_NOT_STATE));
                                if (resultDTO.getKey().equals(ParamUtils.NOT_ENOUGH_AMOUNT)) { // serial khong theo dai
                                    stringBuilder.append(
                                            BundelUtils.getkey(ParamUtils.NOT_ENOUGH_AMOUNT_SERIAL)
                                            .replace(ParamUtils.REP_SUCCESS, String.valueOf(resultDTO.getQuantitySucc()))
                                            .replace(ParamUtils.REP_QUAN, String.valueOf(resultDTO.getQuantity())));
                                } else { // serial theo dai
                                    stringBuilder.append(BundelUtils.getkey(resultDTO.getKey()));
                                }
                                messErr = stringBuilder.toString();
                                messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                                messErr = messErr.replace(ParamUtils.REP_FROM_SERIAL, resultDTO.getFromSerial());
                                messErr = messErr.replace(ParamUtils.REP_TO_SERIAL, resultDTO.getToSerial());
                                resultDTO.setMessage(messErr);
                                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                                return resultDTO;
                            }
                        }
                    }
                    //Cap nhat Kho hang hoa theo so luong
                    resultDTO = exportStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS));
                        stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_ENOUGH_AMOUNT_TOTAL));
                        messErr = stringBuilder.toString();
                        messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                        messErr = messErr.replace(ParamUtils.REP_GOODS_STATE, BundelUtils.getkey(ParamUtils.GOODS_STATE_VAL + stockTransDetailDTO.getGoodsState()));
                        resultDTO.setMessage(messErr);
                        resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                        rollback(session, transaction);
                        return resultDTO;
                    }

                    //stockTransDetailBusiness.update(search);
                    GoodsInTicketDTO goods = createGoodsFromDetail(stockTransDetailDTO);
                    //set list serial imported in goods
                    List<StockTransSerialDTO> lstSerial = DataUtil.cloneList(lstFilterListStockTransSerialDTO);
                    formatSerial(lstSerial);
                    goods.setLstSerial(lstSerial);
                    lstGoods.add(goods);
                }
            }

            /*
             duyot: dong bo sang bccs: gui thong tin thuc xuat
             1. lay ra thong tin nhap kho theo format: billstock-listgoods-list serial
             2. goi sang service bccs
             3. check ket qua tra ve
             */
            //1. lay thong tin
            if (orderIdList == null || orderIdList.isEmpty()) {
                commit(session, transaction);
                return resultDTO;
            }

            // update status orders, order action
            String orderStatus = ordersDTO.getOrderStatus();
            ordersDTO.setOrderStatus(ORDER_STATUS_IMPORTED_EXPORTED);

            //
            lstConditionBean.clear();
            lstConditionBean.add(new ConditionBean("orderIdList", ParamUtils.NAME_EQUAL, orderIdList, ParamUtils.TYPE_STRING));
            OrderActionDTO orderActionDTO = WSOrderAction.getListOrderActionByCondition(lstConditionBean, 0, Integer.MAX_VALUE, "", "id").get(0);
            String orderActionStatus = orderActionDTO.getStatus();
            orderActionDTO.setStatus(ORDER_ACTION_STATUS_EXPORTED);
            String message = WSOrders.updateOrdersAndOrderAction(ordersDTO, orderActionDTO);
            if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.UPDATE_ORDER_ACTION_FAIL);
                messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                resultDTO.setMessage(messErr);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                rollback(session, transaction);
                return resultDTO;
            }
            //
            String isBCCS = ordersDTO.getSourceOrder();
            if (isBCCS != null && isBCCS.equalsIgnoreCase(SYN_FROM_BCCS)) {//NEU  LA YC TU DONG TU BCCS
                billStock = createBillStockFromTrans(stockTransDTO, ordersDTO);
                billStock.setLstGoods(lstGoods);
                //          //gui yc sang ht bccs
                DataIEBccs data = new DataIEBccs(billStock);
                ResultBCCSDTO result = WSBccsGateway.callServiceTransStockBccs(data);
                //luu thong tin giao dich
                SynLogDTO synLog = new SynLogDTO();
                synLog.setAppCode(ParamUtils.APP_CODE.WMS_EXPORT_STOCK);
                synLog.setKey(orderIdToCode(stockTransDTO.getOrderIdList()));
                synLog.setDescription(result.getDescription());
                synLog.setStatus(result.getResponseCode());
                WSSynLog.insertSynLog(synLog);
                //NEU RESPONSE_CODE TRA VE THANH CONG -> COMMIT
                if (result.getResponseCode().equalsIgnoreCase(BCCS_STATUS_SUCCESS)) {
//                    commit(session, transaction);
                } else {
                    ordersDTO.setOrderStatus(orderStatus);
                    orderActionDTO.setStatus(orderActionStatus);
                    message = WSOrders.updateOrdersAndOrderAction(ordersDTO, orderActionDTO);
                    if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                        // 
                    }
                    resultDTO.setMessage(ParamUtils.SYSTEM_OR_DATA_ERROR);
                    resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                    rollback(session, transaction);
                    return resultDTO;
                }
            } else if (isBCCS != null && isBCCS.equalsIgnoreCase(SYN_FROM_KTTS)) {//NEU  LA YC TU DONG TU KTTS
                billStock = createBillStockFromTrans(stockTransDTO, ordersDTO);
                billStock.setLstGoods(lstGoods);
                ResultKTTSDTO result = WSKTTS.synImportStockKTTS(billStock);
                //luu thong tin giao dich
                SynLogDTO synLog = new SynLogDTO();
                synLog.setAppCode(ParamUtils.APP_CODE.WMS_EXPORT_STOCK);
                synLog.setKey(orderIdToCode(stockTransDTO.getOrderIdList()));
                synLog.setDescription(result.getReason());
                synLog.setStatus(result.getStatus());
                WSSynLog.insertSynLog(synLog);
                if (result.getStatus().equalsIgnoreCase(KTTS_STATUS_SUCCESS)) { // tra ve thanh cong
//                    commit(session, transaction);
                } else {
                    ordersDTO.setOrderStatus(orderStatus);
                    orderActionDTO.setStatus(orderActionStatus);
                    message = WSOrders.updateOrdersAndOrderAction(ordersDTO, orderActionDTO);
                    if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                        // 
                    }
                    resultDTO.setMessage(ParamUtils.SYSTEM_OR_DATA_ERROR);
                    resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                    rollback(session, transaction);
                    return resultDTO;
                }
            }
            // end update status orders

            transaction.commit();
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            resultDTO.setMessage(ParamUtils.FAIL);
            rollback(session, transaction);
            return resultDTO;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            lstCurrentId.remove(orderIdList);
        }
        resultDTO.setId(stockTransId);
        return resultDTO;
    }

    @Override
    public ResultDTO synExportStockCust(StockTransDTO stockTransDTO) {
        //Do KPI
        KpiLogDTO synKpiLog = new KpiLogDTO();
        String sStartTime = DateUtil.sysdateString();
        String functionCode;
//        String description;
        String result;
        String reason = "";
        String transactionCode;
        synKpiLog.setStartTime(sStartTime);
        synKpiLog.setCreateDatetime(sStartTime);
//        StockTransDTO stockTransDTO = getData2CreateCommandSyn(ordersDTO);
        OrdersDTO ordersDTO = null;
        String stockTransDetailId;
        List<StockTransSerialDTO> lstFilterListStockTransSerialDTO;
        List<StockTransDetailDTO> lstStockTransDetailDTO = stockTransDTO.getLstStockTransDetailDTO();
        String stockTransCode;
        //
        String sysdate;
        String stockTransId;
        //
        Map<String, GoodsDTO> mapGoodsDTO;
        //
        Session session;
        Transaction transaction;
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        //
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        sysdate = stockGoodsBusiness.getSysDate(formatDate);
        GoodsDTO goodsDTO;
        String messErr = "";
        String orderIdList = "";
        //
        long startTime = System.currentTimeMillis();
        functionCode = "CREATE_BILL_EXPORT_LOG";
        transactionCode = orderIdList;
        try {
            // lay thong tin yeu cau
            orderIdList = stockTransDTO.getOrderIdList();
            if (lstCurrentId.contains(orderIdList)) {
                messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.ORDER_MULTI_USER_PROCESS);
                resultDTO.setMessage(messErr);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_MULTI_USER_PROCESS);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);

                return resultDTO;
            } else {
                lstSynCurrentId.add(orderIdList);
            }
            if (orderIdList != null && !orderIdList.equals("")) {
                ordersDTO = WSOrders.findOrderById(orderIdList);
                if (ordersDTO != null && ordersDTO.getOrderStatus().equalsIgnoreCase(ORDER_STATUS_IMPORTED_EXPORTED)) {
                    // 12/10/15 tiepnv
                    messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.ORDER_IE);
                    messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                    resultDTO.setMessage(messErr);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_IE);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                    return resultDTO;
                }
                transactionCode = ordersDTO.getOrderCode();
            }
            //Insert giao dich kho
            stockTransCode = ParamUtils.CODE_EXPORT_STOCK + stockTransBusiness.getSequence("STOCK_TRANS_SEQ");
            stockTransDTO.setStockTransCode(stockTransCode);
            stockTransDTO.setCreateDatetime(sysdate);
            stockTransDTO.setStockTransDate(sysdate);
            resultDTO = stockTransBusiness.createObjectSession(stockTransDTO, session);
            stockTransId = resultDTO.getId();
            stockTransDTO.setStockTransId(stockTransId);
            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                rollback(session, transaction);
                // 12/10/15 tiepnv
                messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_CREATE_STOCKTRANS);
                messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                resultDTO.setMessage(messErr);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);

                return resultDTO;
            }
            //
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
//                    resultDTO.setMessage(ParamUtils.FAIL);
//                    resultDTO.setMessage(String.valueOf(goodsDTO.getGoodsId()) + "," + " ");
//                    resultDTO.setKey(ParamUtils.GOODS_IS_NOT_EXIST);
                    // 12/10/15 tiepnv
                    messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS_IS_NOT_EXIST);
                    messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, stockTransDetailDTO.getGoodsCode());
                    resultDTO.setMessage(messErr);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);

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
                    // 12/10/15 tiepnv
                    messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_CREATE_STOCKTRANS);
                    messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                    resultDTO.setMessage(messErr);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);

                    return resultDTO;
                }
                //Neu la mat hang khong quan ly serial
                if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                    //Cap nhat mat hang theo so luong 
                    resultDTO = exportStockGoods(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS));
                        if (!DataUtil.isStringNullOrEmpty(stockTransDetailDTO.getCellCode())) {
                            stringBuilder.append(BundelUtils.getkey(ParamUtils.CELLCODE));
                        }
                        if (!DataUtil.isStringNullOrEmpty(stockTransDetailDTO.getBarcode())) {
                            stringBuilder.append(BundelUtils.getkey(ParamUtils.BARCODE));
                        }
                        stringBuilder.append(BundelUtils.getkey(resultDTO.getKey()));
                        messErr = stringBuilder.toString();
                        messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                        messErr = messErr.replace(ParamUtils.REP_GOODS_STATE, BundelUtils.getkey(ParamUtils.GOODS_STATE_VAL + stockTransDetailDTO.getGoodsState()));
                        if (messErr.contains(ParamUtils.REP_CELL_CODE)) {
                            messErr = messErr.replace(ParamUtils.REP_CELL_CODE, stockTransDetailDTO.getCellCode());
                        }
                        if (messErr.contains(ParamUtils.REP_BARCODE)) {
                            messErr = messErr.replace(ParamUtils.REP_BARCODE, stockTransDetailDTO.getBarcode());
                        }
                        resultDTO.setMessage(messErr);
                        resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                        return resultDTO;
                    }
                    //Cap nhat so luong tong cong
                    resultDTO = exportStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS));
                        stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_ENOUGH_AMOUNT_TOTAL));
                        messErr = stringBuilder.toString();
                        messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                        messErr = messErr.replace(ParamUtils.REP_GOODS_STATE, BundelUtils.getkey(ParamUtils.GOODS_STATE_VAL + stockTransDetailDTO.getGoodsState()));
                        resultDTO.setMessage(messErr);
                        resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                        return resultDTO;
                    }

                } else //Mat hang Quan ly theo serial
                {
                    //Lay danh sach chi tiet giao dich serial theo chi tiet giao dich
                    lstFilterListStockTransSerialDTO = stockTransDetailDTO.getLstStockTransSerialDTO();

                    // kiem tra du lieu chi tiet serial sai
                    if (lstFilterListStockTransSerialDTO == null || lstFilterListStockTransSerialDTO.isEmpty()) {
                        resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                        resultDTO.setMessage(ParamUtils.FAIL);
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //Insert giao dich chi tiet serial
                    for (StockTransSerialDTO stockTransSerialDTO : lstFilterListStockTransSerialDTO) {
                        //Id giao dich, ID chi tiet giao dichj
                        stockTransSerialDTO.setStockTransId(stockTransId);
                        stockTransSerialDTO.setStockTransDetailId(stockTransDetailId);
                        stockTransSerialDTO.setStockTransDate(sysdate);
                        stockTransSerialDTO.setCreateDatetime(sysdate);
                        //Tao chi tiet giao dich serial
                        stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                        stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                        stockTransSerialDTO.setQuantity(stockTransSerialDTO.getAmountReal());
                        //duyot: bat truong hop truyen so luong dang 1.0
                        stockTransSerialDTO.setQuantity(DataUtil.getQuantity(stockTransSerialDTO.getAmountReal()));
                        stockTransSerialDTO.setAmountOrder(DataUtil.getQuantity(stockTransSerialDTO.getAmountOrder()));
                        stockTransSerialDTO.setAmountReal(DataUtil.getQuantity(stockTransSerialDTO.getAmountReal()));
                        //
                        resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction);
                            messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_CREATE_STOCKTRANS);
                            messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                            resultDTO.setMessage(messErr);
                            resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                            return resultDTO;
                        }
                        //Xuat kho kho hang hoa theo serial
                        if (stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                            //
                            resultDTO = exportStockGoodsSerial(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, session);
                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                rollback(session, transaction);
                                // tiepnv
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS_NOT_STATE));
                                if (resultDTO.getKey().equals(ParamUtils.NOT_ENOUGH_AMOUNT)) { // serial khong theo dai
                                    stringBuilder.append(
                                            BundelUtils.getkey(ParamUtils.NOT_ENOUGH_AMOUNT_SERIAL)
                                            .replace(ParamUtils.REP_SUCCESS, String.valueOf(resultDTO.getQuantitySucc()))
                                            .replace(ParamUtils.REP_QUAN, String.valueOf(resultDTO.getQuantity())));
                                } else { // serial theo dai
                                    stringBuilder.append(BundelUtils.getkey(resultDTO.getKey()));
                                }
                                messErr = stringBuilder.toString();
                                messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                                messErr = messErr.replace(ParamUtils.REP_FROM_SERIAL, resultDTO.getFromSerial());
                                messErr = messErr.replace(ParamUtils.REP_TO_SERIAL, resultDTO.getToSerial());
                                resultDTO.setMessage(messErr);
                                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                                return resultDTO;
                            }
                        }
                    }
                    //Cap nhat Kho hang hoa theo so luong
                    resultDTO = exportStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS));
                        stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_ENOUGH_AMOUNT_TOTAL));
                        messErr = stringBuilder.toString();
                        messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                        messErr = messErr.replace(ParamUtils.REP_GOODS_STATE, BundelUtils.getkey(ParamUtils.GOODS_STATE_VAL + stockTransDetailDTO.getGoodsState()));
                        resultDTO.setMessage(messErr);
                        resultDTO.setKey(ParamUtils.ERROR_MESSAGE.MY_ERROR);
                        rollback(session, transaction);
                        return resultDTO;
                    }
                }
            }
            //
            resultDTO = getListOrdersTicket(stockTransDTO, ordersDTO, session);
            if (!ParamUtils.SUCCESS.equals(resultDTO.getMessage())) {
                rollback(session, transaction);
                return resultDTO;
            }
            //
            transaction.commit();
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            resultDTO.setMessage(ParamUtils.FAIL);
            rollback(session, transaction);
            return resultDTO;
        } finally {
            long time = System.currentTimeMillis() - startTime;
//            result = resultDTO.getMessage().equalsIgnoreCase(null) ? resultDTO.getKey() : resultDTO.getMessage();
//            String params = result;
//            KPILogger.createLogsEndAction(PARAMS, DECRIPTION, startTime);
            System.out.println("Tong thoi gian viet phieu xuat " + ordersDTO.getOrderCode() + " : " + time);
            KPILogger.createLogs("Tong thoi gian viet phieu xuat " + ordersDTO.getOrderCode() + " : " + time);
//            description = resultDTO.getKey();
            if (ParamUtils.SUCCESS.equalsIgnoreCase(resultDTO.getMessage())) {
//                description = "Tong thoi gian viet phieu xuat " + ordersDTO.getOrderCode() + " : " + time;
                result = SYN_SUCC;
            } else {
                result = SYN_FAIL;
                reason = resultDTO.getMessage();
            }
            synKpiLog.setEndTime(DateUtil.sysdateString());
//            description = "Tao phieu xuat dong bo tren HT LOG";
            synKpiLog.setDescription("Tao phieu xuat dong bo tren HT LOG");
            synKpiLog.setReason(reason);
            synKpiLog.setTransactionCode(transactionCode);
            synKpiLog.setFunctionCode(functionCode);
            synKpiLog.setDuration(String.valueOf(time));
            synKpiLog.setStockTransStatus(result);
            kpiLogBusiness.createKpiLog(synKpiLog);
//            System.out.println(DateUtil.sysdateString() + "|" + DECRIPTION + "| result : " + PARAMS + " |Tong thoi gian " + time);
            lstSynCurrentId.remove(orderIdList);
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        resultDTO.setId(stockTransId);
        return resultDTO;
    }

    @Override
    public ResultDTO synExportStockCusts(List<StockTransDTO> lstStockTransDTO) {

//        StockTransDTO stockTransDTO = getData2CreateCommandSyn(ordersDTO);
        OrdersDTO ordersDTO = null;
        List<OrdersDTO> lstOrdersDTO = null;
        String stockTransDetailId;
        List<StockTransSerialDTO> lstFilterListStockTransSerialDTO;
        String stockTransCode;
        //
        String sysdate;
        String stockTransId;
        //
        Map<String, GoodsDTO> mapGoodsDTO;
        //
        Session session;
        Transaction transaction = null;
        session = sessionFactory.openSession();
        //
        ResultDTO resultDTO = new ResultDTO();
        ResultDTO resultSynCalling = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        sysdate = stockGoodsBusiness.getSysDate(formatDate, session);
        GoodsDTO goodsDTO;
        String lstOrderId = "";
        Map<String, OrdersDTO> mapsOrdersDTO;
        boolean isErr = false;
        boolean isUpdateStockGoodsTotal = true;
        List<StockGoodsSerialInforDTO> lstStockGoodsSerialInforDTO = Lists.newArrayList();
        //duyot: list loi tra ve do dong bo
        List<StockGoodsSerialInforDTO> lstSynError = Lists.newArrayList();
        StringBuilder strOrderIdSynTransCode = new StringBuilder();
        StockGoodsSerialInforDTO stockGoodsSerialInforDTO;
        //
        System.out.println(sysdate + " - SynExportStockCusts: Call ws tao lenh xuat kho cho khach hang voi yeu cau dau tien = " + lstStockTransDTO.get(0).getOrderIdList());
        long startTime = System.currentTimeMillis();
        try {
            if (DataUtil.isListNullOrEmpty(lstStockTransDTO)) {
                resultDTO.setMessage(ParamUtils.FAIL);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.NOT_FOUND_ORDER);
                return resultDTO;
            }
            for (StockTransDTO stockTransDTO : lstStockTransDTO) {
                lstOrderId += stockTransDTO.getOrderIdList() + ",";
            }
            lstOrderId = lstOrderId.substring(0, lstOrderId.length() - 1);
            List<ConditionBean> lstConditionBean = Lists.newArrayList();
            lstConditionBean.add(new ConditionBean("orderId", ParamUtils.NAME_IN, lstOrderId, ParamUtils.TYPE_NUMBER));
            lstOrdersDTO = WSOrders.getListOrdersByCondition(lstConditionBean, 0, Integer.MAX_VALUE, "", "orderDatetime");
            if (DataUtil.isListNullOrEmpty(lstOrdersDTO)) {
                resultDTO.setMessage(ParamUtils.FAIL);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.NOT_FOUND_ORDER);
                return resultDTO;
            }
            String messErr = "";
            mapsOrdersDTO = DataUtil.putOrderToMap(lstOrdersDTO);
            for (StockTransDTO stockTransDTO : lstStockTransDTO) {
                transaction = session.getTransaction();
                transaction.begin();
                isErr = false;
                stockGoodsSerialInforDTO = new StockGoodsSerialInforDTO();
                try {
                    // lay thong tin yeu cau
                    List<StockTransDetailDTO> lstStockTransDetailDTO = stockTransDTO.getLstStockTransDetailDTO();
                    ordersDTO = mapsOrdersDTO.get(stockTransDTO.getOrderIdList());
                    if (ordersDTO == null || (ordersDTO.getOrderStatus().equalsIgnoreCase(ORDER_STATUS_IMPORTED_EXPORTED))) {
                        resultDTO.setMessage(ParamUtils.FAIL);
                        resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_IE);
                        isErr = true;
                        stockGoodsSerialInforDTO.setOrderId(stockTransDTO.getOrderIdList());
                        stockGoodsSerialInforDTO.setCouponCode(stockTransDTO.getCouponCode());
                        messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.ORDER_IE);
                        messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                        stockGoodsSerialInforDTO.setErrorInfo(messErr);
                        lstStockGoodsSerialInforDTO.add(stockGoodsSerialInforDTO);
                        
//                        resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                        //duyot: dua vao list loi global
                        lstSynError.add(stockGoodsSerialInforDTO);
                        continue;
//                        return resultDTO;
                    }
                    //Insert giao dich kho
                    stockTransCode = ParamUtils.CODE_EXPORT_STOCK + stockTransBusiness.getSequenceSession("STOCK_TRANS_SEQ", session);
                    stockTransDTO.setStockTransCode(stockTransCode);
                    stockTransDTO.setCreateDatetime(sysdate);
                    stockTransDTO.setStockTransDate(sysdate);
                    System.out.println("LOG: ORDER_COMMAND_CODE"+ stockTransDTO.getOrderCommandCode());
                    resultDTO = stockTransBusiness.createObjectSession(stockTransDTO, session);
                    stockTransId = resultDTO.getId();
                    stockTransDTO.setStockTransId(stockTransId);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(transaction);
                        isErr = true;
                        stockGoodsSerialInforDTO.setOrderId(stockTransDTO.getOrderIdList());
                        stockGoodsSerialInforDTO.setCouponCode(stockTransDTO.getCouponCode());
                        messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_CREATE_STOCKTRANS);
                        messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                        stockGoodsSerialInforDTO.setErrorInfo(messErr);
                        lstStockGoodsSerialInforDTO.add(stockGoodsSerialInforDTO);
                        
//                        resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                        //duyot: dua vao list loi global
                        lstSynError.add(stockGoodsSerialInforDTO);
                        continue;
//                        return resultDTO;
                    }
                    //
                    //Lay danh sach hang hoa yeu cau day vao map
                    String goodsIdList = getGoodsIdList(lstStockTransDetailDTO);
                    lstConditionBean.clear();
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
//                    resultDTO.setMessage(ParamUtils.FAIL);
                            resultDTO.setMessage(String.valueOf(goodsDTO.getGoodsId()) + "," + " ");
                            resultDTO.setKey(ParamUtils.GOODS_IS_NOT_EXIST);
                            isErr = true;
                            //
                            stockGoodsSerialInforDTO.setOrderId(stockTransDTO.getOrderIdList());
                            stockGoodsSerialInforDTO.setCouponCode(stockTransDTO.getCouponCode());
                            stockGoodsSerialInforDTO.setGoodsCode(goodsDTO.getCode());
                            //
                            messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS_IS_NOT_EXIST);
                            messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, stockTransDetailDTO.getGoodsCode());
                            stockGoodsSerialInforDTO.setErrorInfo(messErr);
                            lstStockGoodsSerialInforDTO.add(stockGoodsSerialInforDTO);
                            
//                            resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                            //duyot: dua vao list loi global
                            lstSynError.add(stockGoodsSerialInforDTO);
                            break;
//                            return resultDTO;
                        }
                        //Insert chi tiet giao dich kho
                        stockTransDetailDTO.setGoodsCode(goodsDTO.getCode());
                        stockTransDetailDTO.setGoodsName(goodsDTO.getName());
                        stockTransDetailDTO.setGoodsIsSerial(goodsDTO.getIsSerial());
                        stockTransDetailDTO.setGoodsIsSerialStrip(goodsDTO.getIsSerialStrip());
                        resultDTO = stockTransDetailBusiness.createObjectSession(stockTransDetailDTO, session);
                        stockTransDetailId = resultDTO.getId();
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(transaction);
                            isErr = true;
                            //
                            stockGoodsSerialInforDTO.setOrderId(stockTransDTO.getOrderIdList());
                            stockGoodsSerialInforDTO.setCouponCode(stockTransDTO.getCouponCode());
                            stockGoodsSerialInforDTO.setGoodsCode(goodsDTO.getCode());
                            //
                            messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_CREATE_STOCKTRANS);
                            messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                            stockGoodsSerialInforDTO.setErrorInfo(messErr);
                            lstStockGoodsSerialInforDTO.add(stockGoodsSerialInforDTO);
                            //
//                            resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                            //duyot: dua vao list loi global
                            lstSynError.add(stockGoodsSerialInforDTO);
                            break;
//                            return resultDTO;
                        }
                        //Neu la mat hang khong quan ly serial
                        if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                            //Cap nhat mat hang theo so luong 
                            resultDTO = exportStockGoods(stockTransDTO, stockTransDetailDTO, session);
                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                rollback(transaction);
                                isErr = true;
                                stockGoodsSerialInforDTO.setOrderId(stockTransDTO.getOrderIdList());
                                stockGoodsSerialInforDTO.setCouponCode(stockTransDTO.getCouponCode());
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS));
                                if (!DataUtil.isStringNullOrEmpty(stockTransDetailDTO.getCellCode())) {
                                    stringBuilder.append(BundelUtils.getkey(ParamUtils.CELLCODE));
                                }
                                if (!DataUtil.isStringNullOrEmpty(stockTransDetailDTO.getBarcode())) {
                                    stringBuilder.append(BundelUtils.getkey(ParamUtils.BARCODE));
                                }
                                stringBuilder.append(BundelUtils.getkey(resultDTO.getKey()));
                                messErr = stringBuilder.toString();
                                messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                                messErr = messErr.replace(ParamUtils.REP_GOODS_STATE, BundelUtils.getkey(ParamUtils.GOODS_STATE_VAL + stockTransDetailDTO.getGoodsState()));
                                if (messErr.contains(ParamUtils.REP_CELL_CODE)) {
                                    messErr = messErr.replace(ParamUtils.REP_CELL_CODE, stockTransDetailDTO.getCellCode());
                                }
                                if (messErr.contains(ParamUtils.REP_BARCODE)) {
                                    messErr = messErr.replace(ParamUtils.REP_BARCODE, stockTransDetailDTO.getBarcode());
                                }
                                stockGoodsSerialInforDTO.setErrorInfo(messErr);
                                lstStockGoodsSerialInforDTO.add(stockGoodsSerialInforDTO);
                                //
                                stockGoodsSerialInforDTO.setGoodsCode(goodsDTO.getCode());
                                stockGoodsSerialInforDTO.setGoodsState(stockTransDetailDTO.getGoodsState());
//                                resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                                //duyot: dua vao list loi global
                                lstSynError.add(stockGoodsSerialInforDTO);
                                break;
//                                return resultDTO;
                            }
                            //Cap nhat so luong tong cong
                            resultDTO = exportStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
//                                rollback(session, transaction);
//                                return resultDTO;
                                rollback(transaction);
                                isErr = true;
                                //
                                stockGoodsSerialInforDTO.setOrderId(stockTransDTO.getOrderIdList());
                                stockGoodsSerialInforDTO.setCouponCode(stockTransDTO.getCouponCode());
                                //
                                stockGoodsSerialInforDTO.setGoodsCode(goodsDTO.getCode());
                                stockGoodsSerialInforDTO.setGoodsState(stockTransDetailDTO.getGoodsState());
                                //
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS));
                                stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_ENOUGH_AMOUNT_TOTAL));
                                messErr = stringBuilder.toString();
                                messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                                messErr = messErr.replace(ParamUtils.REP_GOODS_STATE, BundelUtils.getkey(ParamUtils.GOODS_STATE_VAL + stockTransDetailDTO.getGoodsState()));
                                stockGoodsSerialInforDTO.setErrorInfo(messErr);
                                lstStockGoodsSerialInforDTO.add(stockGoodsSerialInforDTO);
                                
//                                resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                                //duyot: dua vao list loi global
                                lstSynError.add(stockGoodsSerialInforDTO);
                                break;
                            }

                        } else //Mat hang Quan ly theo serial
                        {
                            //Lay danh sach chi tiet giao dich serial theo chi tiet giao dich
                            lstFilterListStockTransSerialDTO = stockTransDetailDTO.getLstStockTransSerialDTO();

                            // kiem tra du lieu chi tiet serial sai
                            if (lstFilterListStockTransSerialDTO == null || lstFilterListStockTransSerialDTO.isEmpty()) {
                                resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                                resultDTO.setMessage(ParamUtils.FAIL);
                                rollback(transaction);
                                isErr = true;
                                //
                                stockGoodsSerialInforDTO.setOrderId(stockTransDTO.getOrderIdList());
                                stockGoodsSerialInforDTO.setCouponCode(stockTransDTO.getCouponCode());
                                //
                                stockGoodsSerialInforDTO.setGoodsCode(goodsDTO.getCode());
                                //
                                String errorMessage = ResourceBundleUtils.getResource("NOT_IMPORT_SERIAL");
                                errorMessage = errorMessage.replace("@goods", goodsDTO.getName());
                                stockGoodsSerialInforDTO.setErrorInfo(errorMessage);
                                lstStockGoodsSerialInforDTO.add(stockGoodsSerialInforDTO);
                                
//                                resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                                //duyot: dua vao list loi global
                                lstSynError.add(stockGoodsSerialInforDTO);
                                break;
//                                rollback(session, transaction);
//                                return resultDTO;
                            }
                            //Insert giao dich chi tiet serial
                            isUpdateStockGoodsTotal = true;
                            for (StockTransSerialDTO stockTransSerialDTO : lstFilterListStockTransSerialDTO) {
                                //Id giao dich, ID chi tiet giao dichj
                                stockTransSerialDTO.setStockTransId(stockTransId);
                                stockTransSerialDTO.setStockTransDetailId(stockTransDetailId);
                                stockTransSerialDTO.setStockTransDate(sysdate);
                                stockTransSerialDTO.setCreateDatetime(sysdate);
                                //Tao chi tiet giao dich serial
                                stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                                stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                                stockTransSerialDTO.setQuantity(stockTransSerialDTO.getAmountReal());
                                resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
                                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
//                                    rollback(session, transaction);
//                                    return resultDTO;
                                    rollback(transaction);
                                    isErr = true;
                                    //
                                    stockGoodsSerialInforDTO.setOrderId(stockTransDTO.getOrderIdList());
                                    stockGoodsSerialInforDTO.setCouponCode(stockTransDTO.getCouponCode());
                                    stockGoodsSerialInforDTO.setGoodsCode(goodsDTO.getCode());
                                    //
                                    messErr = BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_CREATE_STOCKTRANS);
                                    messErr = messErr.replace(ParamUtils.REP_ORDER_CODE, ordersDTO.getOrderCode());
                                    stockGoodsSerialInforDTO.setErrorInfo(messErr);
                                    lstStockGoodsSerialInforDTO.add(stockGoodsSerialInforDTO);
                                    //
//                                    resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                                    //duyot: dua vao list loi global
                                    lstSynError.add(stockGoodsSerialInforDTO);
                                    isUpdateStockGoodsTotal = false;
                                    break;
                                }
                                //Xuat kho kho hang hoa theo serial
                                if (stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                                    //
                                    resultDTO = exportStockGoodsSerial(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, session);
                                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                        isUpdateStockGoodsTotal = false;
//                                        rollback(session, transaction);
//                                        return resultDTO;
                                        rollback(transaction);
                                        isErr = true;
                                        stockGoodsSerialInforDTO.setOrderId(stockTransDTO.getOrderIdList());
                                        stockGoodsSerialInforDTO.setCouponCode(stockTransDTO.getCouponCode());
                                        // tiepnv
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS_NOT_STATE));
                                        if (resultDTO.getKey().equals(ParamUtils.NOT_ENOUGH_AMOUNT)) { // serial khong theo dai
                                            stringBuilder.append(
                                                    BundelUtils.getkey(ParamUtils.NOT_ENOUGH_AMOUNT_SERIAL)
                                                    .replace(ParamUtils.REP_SUCCESS, String.valueOf(resultDTO.getQuantitySucc()))
                                                    .replace(ParamUtils.REP_QUAN, String.valueOf(resultDTO.getQuantity())));
                                        } else { // serial theo dai
                                            stringBuilder.append(BundelUtils.getkey(resultDTO.getKey()));
                                        }
                                        messErr = stringBuilder.toString();
                                        messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                                        messErr = messErr.replace(ParamUtils.REP_FROM_SERIAL, resultDTO.getFromSerial());
                                        messErr = messErr.replace(ParamUtils.REP_TO_SERIAL, resultDTO.getToSerial());
                                        stockGoodsSerialInforDTO.setErrorInfo(messErr);
                                        //duyot: 12/04
                                        stockGoodsSerialInforDTO.setGoodsCode(goodsDTO.getCode());
                                        stockGoodsSerialInforDTO.setFromSerial(resultDTO.getFromSerial());
                                        stockGoodsSerialInforDTO.setToSerial(resultDTO.getToSerial());
                                        
                                        lstStockGoodsSerialInforDTO.add(stockGoodsSerialInforDTO);
                                        //
//                                        resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                                        //duyot: dua vao list loi global
                                        lstSynError.add(stockGoodsSerialInforDTO);
                                        break;
                                    }
                                }
                            }
                            //Cap nhat Kho hang hoa theo so luong
                            if (isUpdateStockGoodsTotal) {
                                resultDTO = exportStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
//                                rollback(session, transaction);
//                                return resultDTO;
                                    rollback(transaction);
                                    isErr = true;
                                    //
                                    stockGoodsSerialInforDTO.setOrderId(stockTransDTO.getOrderIdList());
                                    stockGoodsSerialInforDTO.setCouponCode(stockTransDTO.getCouponCode());
                                    stockGoodsSerialInforDTO.setGoodsCode(goodsDTO.getCode());
                                    //
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.GOODS));
                                    stringBuilder.append(BundelUtils.getkey(ParamUtils.ERROR_MESSAGE.NOT_ENOUGH_AMOUNT_TOTAL));
                                    messErr = stringBuilder.toString();
                                    messErr = messErr.replace(ParamUtils.REP_GOODS_CODE, goodsDTO.getCode());
                                    messErr = messErr.replace(ParamUtils.REP_GOODS_STATE, BundelUtils.getkey(ParamUtils.GOODS_STATE_VAL + stockTransDetailDTO.getGoodsState()));
                                    stockGoodsSerialInforDTO.setErrorInfo(messErr);
                                    lstStockGoodsSerialInforDTO.add(stockGoodsSerialInforDTO);
                                    //
//                                    resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                                    //duyot: dua vao list loi global
                                    lstSynError.add(stockGoodsSerialInforDTO);
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }

                    //
                    if (!isErr) {
                        resultSynCalling = getListOrdersTicket(stockTransDTO, ordersDTO, session);
                        if (!ParamUtils.SUCCESS.equals(resultSynCalling.getMessage())) {
                            rollback(transaction);
                            isErr = true;
                            //duyot: 14/04: dua vao list loi
                            StockGoodsSerialInforDTO synError = new StockGoodsSerialInforDTO();
                            synError.setOrderId(stockTransDTO.getOrderIdList());
                            synError.setCouponCode(stockTransDTO.getCouponCode());
                            String errorMessage = resultSynCalling.getMessage();
                            synError.setErrorInfo(errorMessage);
                            lstSynError.add(synError);
                            //
//                            resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                            continue;
                        } else {
                            strOrderIdSynTransCode.append(ordersDTO.getOrderId()).append("-").append(resultSynCalling.getKey()).append(",");
                            transaction.commit();
                        }
                    } else {
                        rollback(transaction);
                    }
                    //
                } catch (Exception e) {
//            e.printStackTrace();
                    Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
                    resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
                    resultDTO.setMessage(ParamUtils.FAIL);
//                    rollback(session, transaction);
//                    return resultDTO;
                    rollback(transaction);
                    isErr = true;
                    stockGoodsSerialInforDTO.setOrderId(stockTransDTO.getOrderIdList());
                    stockGoodsSerialInforDTO.setCouponCode(stockTransDTO.getCouponCode());
                    String errorMessage = resultDTO.getMessage();
                    stockGoodsSerialInforDTO.setErrorInfo(errorMessage);
                    lstStockGoodsSerialInforDTO.add(stockGoodsSerialInforDTO);
                    resultDTO.setLstStockGoodsSerialInforDTO(lstStockGoodsSerialInforDTO);
                    continue;
                }
            }
        } catch (Exception e) {
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            resultDTO.setMessage(ParamUtils.FAIL);
            rollback(transaction);
            return resultDTO;
        } finally {
            System.out.println("Result: " + resultDTO != null ? "null" : resultDTO.getMessage());
            long time = System.currentTimeMillis() - startTime;
            System.out.println(sysdate + " - SynExportStockCusts: Thuc hien xong dong bo yeu cau xuat kho dau tien = " + lstStockTransDTO.get(0).getStockTransCode() + " thoi gian " + time);
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        //neu co list syn_error -> dua vao resultDTO
        if(!DataUtil.isListNullOrEmpty(lstSynError)){
            //lay ra list loi trong result dto
            List<StockGoodsSerialInforDTO> lstResultError = resultDTO.getLstStockGoodsSerialInforDTO();
            if(!DataUtil.isListNullOrEmpty(lstResultError)){
                lstResultError.addAll(lstSynError);
                resultDTO.setLstStockGoodsSerialInforDTO(lstResultError);
            }else{
                resultDTO.setLstStockGoodsSerialInforDTO(lstSynError);
            }
        }
        System.out.println("LOG MAP ORDER_ID & TRANS_CODE: "+ strOrderIdSynTransCode.toString());
        resultDTO.setKey(strOrderIdSynTransCode.toString());
        return resultDTO;
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
            lstOrderGoodsDetailDTO.add(orderGoodsDetailDTO);
            lstOrderGoodsDetailSerialDTO = Lists.newArrayList();
            for (StockTransSerialDTO transSerialDTO : dTO.getLstStockTransSerialDTO()) {
                orderGoodsDetailSerialDTO = new OrderGoodsDetailSerialDTO();
                orderGoodsDetailSerialDTO.setFromSerial(transSerialDTO.getFromSerial());
                orderGoodsDetailSerialDTO.setToSerial(transSerialDTO.getToSerial());
                orderGoodsDetailSerialDTO.setQuantity(transSerialDTO.getAmountReal());
                lstOrderGoodsDetailSerialDTO.add(orderGoodsDetailSerialDTO);
            }//set list detail serial in detail
            orderGoodsDetailDTO.setLstOrderGoodsDetailSerialDTO(lstOrderGoodsDetailSerialDTO);
            orderGoodsDetailDTO.setLstDetailSerial(lstOrderGoodsDetailSerialDTO);
        }
        ordersDTO.setLstOrderGoodsDetailDTO(lstOrderGoodsDetailDTO);
        ordersDTO.setListGood(lstOrderGoodsDetailDTO);
        return ordersDTO;
    }

    public ResultDTO getListOrdersTicket(StockTransDTO stockTransDTO,
            OrdersDTO orders, Session session) {
        getData2CreateCommandSyn(stockTransDTO, orders);
        Map<String, String> mapOrderAction = new HashMap<>();
        List<OrdersDTO> lstOrderDTO = new ArrayList<>();
        ResultDTO result = new ResultDTO();
        String orderCode = orders.getOrderCode();
        String sourceOrder = orders.getSourceOrder();
        //------duyot: 2/11/2015------------------------------------------------
        //bo sung: them truong stockerCode khi gui sang BCCS
        String stockerCode = "";
        //2/ lay account V-office cua staff
        if (stockTransDTO.getTransUserId() != null) {
            StaffDTO staff = WSStaff.findStaffById(stockTransDTO.getTransUserId());
            if (staff != null) {
                stockerCode = (String) DataUtil.nvl(staff.getVofficeAccount(), "");
            }
        }
        //----------------------------------------------------------------------
        //Do thoi gian chay cua ham tao phieu xuat ben KTTS va BCCS
        //QuyenDM 20160216 - Ghi log kpi ra DB
        //Do thoi gian chay cua ham thuc xuat ben KTTS va BCCS        
        String synSys = Constants.TYPE_ORDERS_BCCS.equalsIgnoreCase(orders.getSourceOrder()) ? "VTT" : "VTN";
        String functionCode = "CREATE_BILL_EXPORT_" + synSys;
        String sStartTime = DateUtil.sysdateString();
        String transactionCode = orders.getOrderCode();
        KpiLogDTO stockExportKpi = new KpiLogDTO();
        stockExportKpi.setStartTime(sStartTime);
        stockExportKpi.setCreateDatetime(sStartTime);
        stockExportKpi.setTransactionCode(transactionCode);
        stockExportKpi.setFunctionCode(functionCode);

//        String LOG = "Call ws viet phieu nhap cho yeu cau " + orders.getOrderCode();
//        System.out.println(LOG);        
//        String DECRIPTION = "Viet phieu xuat dong bo voi " + SYN_SYS + " | " + orders.getOrderCode();
//        KPILogger.createLogsStartAction(DECRIPTION);
        long startTime = System.currentTimeMillis();
        long time;
        ResultSyn resultSyn = null;
        try {
            if (orders.getSourceOrder().equalsIgnoreCase(SYN_FROM_BCCS)) {
                orders.setSourceOrder(null);
                orders.setOrderDatetime(null);
                orders.setCustName(null);
                //set list order_goods_detail null
                orders.setListGood(null);
                //------------------------------------------------------------------
                //duyot: set lai null list detail trong serial
                List<OrderGoodsDetailDTO> lstDetail = orders.getLstOrderGoodsDetailDTO();
                if(!DataUtil.isListNullOrEmpty(lstDetail)){
                   for(OrderGoodsDetailDTO i: lstDetail){
                       i.setLstOrderGoodsDetailSerialDTO(null);
                   }
                }
                //------------------------------------------------------------------
                orders.setOrderAction(stockTransDTO.getOrderAction());
                orders.setLstOrderGoodsLocationDTO(null);
                //set stockerCode to order
                if (!DataUtil.isStringNullOrEmpty(stockerCode)) {
                    orders.setStockerCode(stockerCode);
                }
                //
                lstOrderDTO.add(orders);
                DataBccs data = new DataBccs(lstOrderDTO);
                resultSyn = WSSynchonize.callServiceCreateBillBccs(data);

            } else if (orders.getSourceOrder().equalsIgnoreCase(SYN_FROM_KTTS)) {
//            orders.setListGood(orders.getLstOrderGoodsDetailDTO());
                orders.setSourceOrder(null);
                orders.setOrderDatetime(null);
                orders.setCustName(null);
                lstOrderDTO.add(orders);
//            DataBccs data = new DataBccs(lstOrderDTO);
                orders.setLstOrderGoodsDetailDTO(null);
                //duyot: 13/04: set lai lst serial------------------------------
                List<OrderGoodsDetailDTO> lstDetail = orders.getListGood();
                if(!DataUtil.isListNullOrEmpty(lstDetail)){
                   for(OrderGoodsDetailDTO i: lstDetail){
                       i.setLstDetailSerial(null);
                   }
                }
                //--------------------------------------------------------------
                orders.setLstOrderGoodsLocationDTO(null);
                orders.setOrderAction(stockTransDTO.getOrderAction());
                //duyot
                orders.setContent(stockTransDTO.getNotes());
                orders.setTransUserName(stockTransDTO.getTransUserName());
                resultSyn = WSSynchonize.callServiceCreateBillKtts(orders);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            time = System.currentTimeMillis() - startTime;
            String messageTimeCalc = "Thoi gian viet phieu xuat " +
                    synSys + " YC: " + orders.getOrderCode() + " la: " + time;
            System.out.println(messageTimeCalc);
            KPILogger.createLogs(messageTimeCalc);
//            stockExportKpi.setDescription(messageTimeCalc);
        }
//        System.out.println(DateUtil.sysdateString() + " Thuc hien xong viet phieu xuat dong bo " + SYN_SYS + " YC: " + orders.getOrderCode() + " thoi gian " + time);
//        KPILogger.createLogsEndAction(resultSyn != null ? resultSyn.getReason() : " connection False", messageTimeCalc, startTime);
        SynLogDTO synlogCreateBill = new SynLogDTO();
        synlogCreateBill.setAppCode(ParamUtils.APP_CODE.CREATE_BILL_EXPORT);
        synlogCreateBill.setKey(orderCode);
        if (resultSyn == null) {
            //LUU THONG TIN SYN_LOG
            synlogCreateBill.setStatus("69");
            synlogCreateBill.setDescription(ParamUtils.APP_CODE.CONNECTION_FAIL);
            //LUU THONG TIN TRA VE RESULT
            result.setMessage(ParamUtils.APP_CODE.CONNECT_FAIL);
            result.setKey(ParamUtils.ERROR_MESSAGE.SYNC_FAIL);
        } else {
            String statusResp = resultSyn.getStatus() == null ? "" : resultSyn.getStatus();
            String synTransCodeBCCS = resultSyn.getSynTransCode()== null ? "" : resultSyn.getSynTransCode();
            String reason = resultSyn.getReason() == null ? "" : resultSyn.getReason();
            String addInfor = resultSyn.getAddInfor() == null ? "" : resultSyn.getAddInfor();
            System.out.println(" Thong tin bo sung tra ve tu KTTS" + addInfor);
            synlogCreateBill.setStatus(statusResp);
            synlogCreateBill.setDescription(reason.length() > lengthDescription ? reason.substring(0, lengthDescription) : reason);
            //------------------------------------------------------------------
            if ("1".equalsIgnoreCase(statusResp)) {
                result.setMessage(ParamUtils.SUCCESS);
                //neu la tu bccs
                if(sourceOrder.equalsIgnoreCase(SYN_FROM_BCCS)){
                    System.out.println("SYN_TRANS_CODE BCCS: " + synTransCodeBCCS);
                    result.setKey(synTransCodeBCCS);
                    //duyot 13/04/2016
                    //cap nhat lai thong tin bang stock_trans: truong syn_trans_code
                    if (!DataUtil.isStringNullOrEmpty(synTransCodeBCCS)) {
                        stockTransDTO.setSynTransCode(synTransCodeBCCS);
                        //NgocND6 160216 update addinfor
                        stockTransDTO.setAddInfor(addInfor);
                        System.out.println("Thong tin bo sung sau khi da lua addinfor vao doi tuong" + stockTransDTO.getAddInfor());
                        ResultDTO resultUpdateTrans = stockTransBusiness2.updateSynTransCodeUsingSession(stockTransDTO, session);
                        if (!resultUpdateTrans.getMessage().equalsIgnoreCase(ParamUtils.SUCCESS)) {
                            synlogCreateBill.setDescription("Cap nhat ma phieu tu BCCS khong thanh cong: " + resultUpdateTrans.getMessage());
                        }
                    }
                }else{
//                  duyot: 14/01/2016 cap nhat ma phieu dong bo vao ky, bo ham set key
                    result.setKey(reason);
                    System.out.println("SYN_TRANS_CODE KTTS: " + reason);
                    //duyot 11/01/2016
                    //cap nhat lai thong tin bang stock_trans: truong syn_trans_code
                    if (!DataUtil.isStringNullOrEmpty(reason)) {
                        stockTransDTO.setSynTransCode(reason);
                        //NgocND6 160216 update addinfor
                        stockTransDTO.setAddInfor(addInfor);
                        System.out.println("Thong tin bo sung sau khi da lua addinfor vao doi tuong" + stockTransDTO.getAddInfor());
                        ResultDTO resultUpdateTrans = stockTransBusiness2.updateSynTransCodeUsingSession(stockTransDTO, session);
                        if (!resultUpdateTrans.getMessage().equalsIgnoreCase(ParamUtils.SUCCESS)) {
                            synlogCreateBill.setDescription("Cap nhat ma phieu tu ktts khong thanh cong: " + resultUpdateTrans.getMessage());
                        }
                    }
                }
                synlogCreateBill.setStatus(statusResp);
                //
            } else {
                System.out.println("RUNNING IN FAIL: STATUS: "+ statusResp);
                result.setMessage(reason);
                result.setKey(ParamUtils.ERROR_MESSAGE.SYNC_FAIL);
            }
        }
        //Ghi log kpi
        if (!SYN_SUCC.equalsIgnoreCase(synlogCreateBill.getStatus())) {
            stockExportKpi.setReason(synlogCreateBill.getDescription());
            stockExportKpi.setStockTransStatus(SYN_FAIL);
        } else {
            stockExportKpi.setStockTransStatus(SYN_SUCC);
        }
        stockExportKpi.setDescription("Tao phieu xuat dong bo voi HT " + synSys);
        stockExportKpi.setDuration(String.valueOf(time));
        stockExportKpi.setEndTime(DateUtil.sysdateString());
        kpiLogBusiness.createKpiLog(stockExportKpi);
        //GHI LOG - TRA VE KET QUA
        WSSynLog.insertSynLog(synlogCreateBill);
        return result;
    }

    /**
     * Thuc xuat
     *
     * @param stockTransDTO
     * @return
     */
    @Override
    public ResultDTO updateStatusStockTrans(StockTransDTO stockTransDTO) {
        //Khoi tao doi tuong de ghi log ra kpi_log
        String functionCode = "WMS_EXPORT_STOCK_LOG";
        String functionCodeSyn = "WMS_EXPORT_STOCK_";
        String transactionCode = "";
        String reasonLog = "";
        String reason = "";
        String sEndTime = "";
        String sEndTimeSyn = "";
        String resultLog = "";
        String resultSyn = "";
        String duration = "";
        String durationSyn = "";
        String reasonSyn = "";
        String descriptionSyn = "";
        String sStartTime = DateUtil.sysdateString();
        String sStartTimeSyn = "";
        KpiLogDTO realExportKpiLog = new KpiLogDTO();
        realExportKpiLog.setStartTime(sStartTime);
        realExportKpiLog.setCreateDatetime(sStartTime);
        realExportKpiLog.setFunctionCode(functionCode);
        KpiLogDTO synKpiLog = new KpiLogDTO();
        synKpiLog.setCreateDatetime(sStartTime);

        String message;
        Session session;
        boolean isUpdateStockGoods = false;
        boolean isUpdateStockGoodsSerial = false;
        boolean isUpdateStockGoodsSerialStrip = false;
        Transaction transaction;
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        //
        BillStock billStock;
        List<GoodsInTicketDTO> lstGoods = new ArrayList<>();
        //Do thoi gian thuc xuat
        long startTime = System.currentTimeMillis();
        long endTimeLogisitcs = 0;
        //
        OrdersDTO ordersDTO = null;
        OrderActionDTO orderActionDTO = null;
        String orderStatus = "";
        String orderActionStatus = "";

        try {
            String sysdate = stockGoodsBusiness.getSysDate(formatDate);
            stockTransDTO.setRealStockTransDate(sysdate);
            message = stockTransBusiness2.updateStockTransByOrdersId(stockTransDTO, session);
            if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                rollback(session, transaction);
                resultDTO.setMessage(ParamUtils.FAIL);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.UPDATE_STOCKTRANS_FAIL);
                reasonLog = "Loi khi cap nhat giao dich " + stockTransDTO.getStockTransCode();
                reason = ParamUtils.ERROR_MESSAGE.UPDATE_STOCKTRANS_FAIL;
                resultLog = SYN_FAIL;
                transactionCode = stockTransDTO.getOrderIdList();
                sEndTime = DateUtil.sysdateString();
                return resultDTO;
            }

            transactionCode = stockTransDTO.getOrderIdList();
            //
            StockTransDTO stockTransDTOSearch = new StockTransDTO();
            stockTransDTOSearch.setOrderIdList(stockTransDTO.getOrderIdList());
            stockTransDTOSearch = (StockTransDTO) stockTransBusiness.searchSession(stockTransDTO, 0, Integer.MAX_VALUE, "", "createDatetime", session).get(0);
            List<StockTransDetailDTO> lstStockTransDetailDTO;
            List<StockTransSerialDTO> lstStockTransSerialDTO;
            List<StockTransSerialDTO> lstFilterStockTransSerialDTO;
            StockTransDetailDTO stockTransDetailDTO = new StockTransDetailDTO();
            StockTransSerialDTO stockTransSerialDTO = new StockTransSerialDTO();
            stockTransDetailDTO.setStockTransId(stockTransDTOSearch.getStockTransId());
            stockTransSerialDTO.setStockTransId(stockTransDTOSearch.getStockTransId());
            transactionCode = stockTransDTOSearch.getOrderCode();
            lstStockTransDetailDTO = stockTransDetailBusiness.searchSession(stockTransDetailDTO, 0, Integer.MAX_VALUE, "", "createDatetime", session);
            lstStockTransSerialDTO = stockTransSerialBusiness.searchSession(stockTransSerialDTO, 0, Integer.MAX_VALUE, "", "createDatetime", session);
            GoodsInTicketDTO goods;
            for (StockTransDetailDTO dTO : lstStockTransDetailDTO) {
                if (!dTO.getGoodsIsSerial().equalsIgnoreCase(Constants.IS_SERIAL)) {
                    goods = createGoodsFromDetail(dTO);
                    lstGoods.add(goods);
                    isUpdateStockGoods = true;
                } else {
                    goods = createGoodsFromDetail(dTO);
                    lstFilterStockTransSerialDTO = filterDetailSerial(dTO.getStockTransDetailId(), lstStockTransSerialDTO);
                    List<StockTransSerialDTO> lstSerial = DataUtil.cloneList(lstFilterStockTransSerialDTO);
                    formatSerial(lstSerial);
                    goods.setLstSerial(lstSerial);
//                    goods.setLstStockTransSerialDTO(lstSerial);
                    lstGoods.add(goods);
                    if (!dTO.getGoodsIsSerialStrip().equalsIgnoreCase(Constants.IS_SERIAL_STRIP)) {
                        isUpdateStockGoodsSerial = true;
                    } else {
                        isUpdateStockGoodsSerialStrip = true;
                    }
                }
            }
            if (isUpdateStockGoods) { // update stock goods
                StockGoodsDTO stockGoodsDTO = new StockGoodsDTO();
                stockGoodsDTO.setStatus(Constants.STATUS_SERIAL_OUT_STOCK);
                stockGoodsDTO.setChangeDate(sysdate);
                stockGoodsDTO.setOrderId(stockTransDTO.getOrderIdList());
                stockGoodsDTO.setOldStatus(Constants.STATUS_SERIAL_WAIT_STOCK);
                message = stockGoodsInterface.updateStockGoodsByOrdersId(stockGoodsDTO, session);
                if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                    rollback(session, transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.UPDATE_STOCKGOODS_FAIL);
                    reasonLog = "Loi khi cap nhat hang hoa khong serial";
                    reason = ParamUtils.ERROR_MESSAGE.UPDATE_STOCKGOODS_FAIL;
                    resultLog = SYN_FAIL;
                    sEndTime = DateUtil.sysdateString();
                    return resultDTO;
                }
            }
            if (isUpdateStockGoodsSerial) { // update stock goods serial
                StockGoodsSerialDTO stockGoodsSerialDTO = new StockGoodsSerialDTO();
                stockGoodsSerialDTO.setStatus(Constants.STATUS_SERIAL_OUT_STOCK);
                stockGoodsSerialDTO.setChangeDate(sysdate);
                stockGoodsSerialDTO.setOrderId(stockTransDTO.getOrderIdList());
                stockGoodsSerialDTO.setOldStatus(Constants.STATUS_SERIAL_WAIT_STOCK);
                message = stockGoodsSerialBusiness2.updateStockGoodsSerialByOrdersId(stockGoodsSerialDTO, session);
                if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                    rollback(session, transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.UPDATE_STOCK_GOODS_SERIAL_FAIL);
                    reasonLog = "Loi khi cap nhat hang hoa quan ly theo serial";
                    reason = ParamUtils.ERROR_MESSAGE.UPDATE_STOCK_GOODS_SERIAL_FAIL;
                    resultLog = SYN_FAIL;
                    sEndTime = DateUtil.sysdateString();
                    return resultDTO;
                }
            }
            if (isUpdateStockGoodsSerialStrip) { // update stock goods serial strip
                StockGoodsSerialStripDTO stockGoodsSerialStripDTO = new StockGoodsSerialStripDTO();
                stockGoodsSerialStripDTO.setStatus(Constants.STATUS_SERIAL_OUT_STOCK);
                stockGoodsSerialStripDTO.setChangeDate(sysdate);
                stockGoodsSerialStripDTO.setOrderId(stockTransDTO.getOrderIdList());
                stockGoodsSerialStripDTO.setOldStatus(Constants.STATUS_SERIAL_WAIT_STOCK);
                message = stockGoodsSerialStripBusiness2.updateStockGoodsSerialStripByOrdersId(stockGoodsSerialStripDTO, session);
                if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                    rollback(session, transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.UPDATE_STOCK_GOODS_SERIAL_STRIP_FAIL);
                    reasonLog = "Loi khi cap nhat hang hoa quan ly theo serial theo dai";
                    reason = ParamUtils.ERROR_MESSAGE.UPDATE_STOCK_GOODS_SERIAL_STRIP_FAIL;
                    resultLog = SYN_FAIL;
                    sEndTime = DateUtil.sysdateString();
                    return resultDTO;
                }
            }
            // 
            String orderIdList = stockTransDTO.getOrderIdList();
//            OrdersDTO ordersDTO = null;
            if (orderIdList != null && !orderIdList.equals("")) {
                ordersDTO = WSOrders.findOrderById(orderIdList);
                if (ordersDTO != null && ordersDTO.getOrderStatus().equalsIgnoreCase(ORDER_STATUS_IMPORTED_EXPORTED)) {
                    rollback(session, transaction);
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_IE);
                    reasonLog = "Yeu cau da thuc xuat";
                    reason = ParamUtils.ERROR_MESSAGE.ORDER_IE;
                    resultLog = SYN_FAIL;
                    sEndTime = DateUtil.sysdateString();
                    return resultDTO;
                }
            }
            //

            // update status orders, order action
            if (ordersDTO != null) {
                orderStatus = ordersDTO.getOrderStatus();
                ordersDTO.setOrderStatus(ORDER_STATUS_IMPORTED_EXPORTED);
            } else {
                System.out.println("Khong tim thay yeu cau..");
            }
            //
            List<ConditionBean> lstConditionBean = Lists.newArrayList();
            lstConditionBean.add(new ConditionBean("orderIdList", ParamUtils.NAME_EQUAL, orderIdList, ParamUtils.TYPE_STRING));
            orderActionDTO = WSOrderAction.getListOrderActionByCondition(lstConditionBean, 0, Integer.MAX_VALUE, "", "id").get(0);
            orderActionStatus = orderActionDTO.getStatus();
            orderActionDTO.setStatus(ORDER_ACTION_STATUS_EXPORTED);
            message = WSOrders.updateOrdersAndOrderAction(ordersDTO, orderActionDTO);
            if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                resultDTO.setMessage(ParamUtils.FAIL);
                resultDTO.setKey(ParamUtils.ERROR_MESSAGE.UPDATE_ORDER_ACTION_FAIL);
                rollback(session, transaction);
                reasonLog = "Loi khi cap nhat lenh xuat kho";
                reason = ParamUtils.ERROR_MESSAGE.UPDATE_ORDER_ACTION_FAIL;
                resultLog = SYN_FAIL;
                sEndTime = DateUtil.sysdateString();
                return resultDTO;
            }
            //Ghi log
            resultLog = SYN_SUCC;
            long endTimeSyn = 0;
            //Goi ham thuc xuat dong bo KTTS/BCCS                       
            String isBCCS = ordersDTO.getSourceOrder();
            String synSys = Constants.TYPE_ORDERS_KTTS.equals(ordersDTO.getSourceOrder()) ? "VTN" : "VTT";
            functionCodeSyn += synSys;
            descriptionSyn = "Thuc xuat tren he thong " + synSys;
            SynLogDTO synLog = null;
            if (isBCCS != null && isBCCS.equalsIgnoreCase(SYN_FROM_BCCS)) {//NEU  LA YC TU DONG TU BCCS
                billStock = createBillStockFromTrans(stockTransDTOSearch, ordersDTO);
                billStock.setLstGoods(lstGoods);
                //          //gui yc sang ht bccs
                DataIEBccs data = new DataIEBccs(billStock);
                //Chot thoi gian xu ly cua he thong LOG
                sEndTime = DateUtil.sysdateString();
                endTimeLogisitcs = System.currentTimeMillis() - startTime;
//                reasonLog = "Thoi gian thuc xuat tren he thong LOG la : " + endTimeLogisitcs;
                //Bat dau tinh thoi gian xu ly cua HT dong bo
                startTime = System.currentTimeMillis();
                sStartTimeSyn = DateUtil.sysdateString();
                //Goi ham dong bo BCCS
                ResultBCCSDTO result = WSBccsGateway.callServiceTransStockBccs(data);
                //luu thong tin giao dich
                synLog = new SynLogDTO();
                synLog.setAppCode(ParamUtils.APP_CODE.WMS_EXPORT_STOCK);
                synLog.setKey(orderIdToCode(stockTransDTO.getOrderIdList()));
                //NEU RESPONSE_CODE TRA VE THANH CONG -> COMMIT
                if (result == null) { // mat ket noi --> rollback                    
                    //duyot 20/01
                    //GHI LOG
                    synLog.setDescription("Mat ket noi voi BCCS");
                    synLog.setStatus("0");
                    resultSyn = SYN_FAIL;
                    reasonSyn = "Mat ket noi voi BCCS";
                    endTimeSyn = System.currentTimeMillis() - startTime;
                    durationSyn = String.valueOf(endTimeSyn);
                    WSSynLog.insertSynLog(synLog);
//                  duyot: 15/01/2015 rollback trang thai lenh - yeu cau
                    ordersDTO.setOrderStatus(orderStatus);
                    orderActionDTO.setStatus(orderActionStatus);
                    //
                    message = WSOrders.updateOrdersAndOrderAction(ordersDTO, orderActionDTO);
                    //
                    if (ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                        System.out.println("LOG BCCS: Loi mat ket noi,Rollback trang thai lenh - yeu cau thanh cong");
                    } else {
                        System.out.println("LOG BCCS: Loi mat ket noi,Rollback trang thai lenh - yeu cau khong thanh cong");
                    }
                    //duyot: 20/01/2016: rollback giao dich
                    rollback(session, transaction);
                    //SET THONG TIN VE CLIENT
                    resultDTO.setMessage(ParamUtils.APP_CODE.CONNECT_FAIL);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.SYNC_FAIL);
                    //
                    return resultDTO;
                    //duyot:27/01/2016: cap nhat: check ca truong hop status tra ve == null
                } else if (result.getResponseCode() != null && result.getResponseCode().equalsIgnoreCase(BCCS_STATUS_SUCCESS)) {
                    //THONG TIN TRA VE THANH CONG
                    synLog.setStatus("1");
                    synLog.setDescription(result.getDescription());
                } else {
                    resultSyn = SYN_FAIL;
                    reasonSyn = result.getDescription();
                    endTimeSyn = System.currentTimeMillis() - startTime;
                    durationSyn = String.valueOf(endTimeSyn);
                    //GHI LOG
                    synLog.setStatus("0");
                    synLog.setDescription(result.getDescription());
                    WSSynLog.insertSynLog(synLog);
                    //ROLLBACK ORDER-ORDER-ACTION
                    ordersDTO.setOrderStatus(orderStatus);
                    orderActionDTO.setStatus(orderActionStatus);
                    message = WSOrders.updateOrdersAndOrderAction(ordersDTO, orderActionDTO);
                    if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                        // 
                    }
                    //ROLBACK SESION
                    rollback(session, transaction);
                    //RETURN
                    resultDTO.setMessage(result.getDescription());
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.SYNC_FAIL);
                    return resultDTO;
                }

                WSSynLog.insertSynLog(synLog);
            } else if (isBCCS != null && isBCCS.equalsIgnoreCase(SYN_FROM_KTTS)) {//NEU  LA YC TU DONG TU KTTS
                billStock = createBillStockFromTrans(stockTransDTOSearch, ordersDTO);
                billStock.setLstGoods(lstGoods);
                //Chot thoi gian xu ly cua he thong LOG
                sEndTime = DateUtil.sysdateString();
                endTimeLogisitcs = System.currentTimeMillis() - startTime;
//                reasonLog = "Thoi gian thuc xuat tren he thong LOG la : " + endTimeLogisitcs;
                //Bat dau tinh thoi gian xu ly cua HT dong bo
                startTime = System.currentTimeMillis();
                sStartTimeSyn = DateUtil.sysdateString();
                ResultKTTSDTO result = WSKTTS.synImportStockKTTS(billStock);
                //luu thong tin giao dich
                synLog = new SynLogDTO();
                synLog.setAppCode(ParamUtils.APP_CODE.WMS_EXPORT_STOCK);
                synLog.setKey(orderIdToCode(stockTransDTO.getOrderIdList()));

                if (result == null) { // Mat ket noi --> rolslback
                    //GHI LOG
                    synLog.setDescription("Mat ket noi voi KTTS");
                    synLog.setStatus("0");
                    resultSyn = SYN_FAIL;
                    reasonSyn = "Mat ket noi voi KTTS";
                    endTimeSyn = System.currentTimeMillis() - startTime;
                    durationSyn = String.valueOf(endTimeSyn);
                    WSSynLog.insertSynLog(synLog);
//                  duyot: 15/01/2015 rollback trang thai lenh - yeu cau
                    ordersDTO.setOrderStatus(orderStatus);
                    orderActionDTO.setStatus(orderActionStatus);
                    //rollback order_order_action
                    message = WSOrders.updateOrdersAndOrderAction(ordersDTO, orderActionDTO);
                    //
                    if (ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                        System.out.println("LOG KTTS: Loi mat ket noi,Rollback trang thai lenh - yeu cau thanh cong");
                    } else {
                        System.out.println("LOG KTTS: Loi mat ket noi,Rollback trang thai lenh - yeu cau khong thanh cong");
                    }
                    //duyot: 20/01/2016
                    rollback(session, transaction);
                    //RETURN
                    resultDTO.setMessage(ParamUtils.APP_CODE.CONNECT_FAIL);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.SYNC_FAIL);
                    return resultDTO;
                    //duyot:27/01/2016: cap nhat: check ca truong hop status tra ve == null
                } else if (result.getStatus() != null && result.getStatus().equalsIgnoreCase(KTTS_STATUS_SUCCESS)) { // tra ve thanh cong
                    synLog.setDescription(result.getReason());
                    synLog.setStatus(result.getStatus());
                } else {//TRA VE THONG TIN LOI
                    //duyot:27/01/2016: cap nhat: ghi log khi status tra ve == null
                    if (result.getStatus() == null) {
                        System.out.println("LOG: KTTS status null..");
                    }
                    if (!SYN_SUCC.equals(result.getStatus())) {
                        resultSyn = SYN_FAIL;
                        reasonSyn = result.getReason();
                        endTimeSyn = System.currentTimeMillis() - startTime;
                        durationSyn = String.valueOf(endTimeSyn);
                    }
                    //GHI LOG
                    synLog.setDescription(result.getReason());
                    synLog.setStatus(result.getStatus());
                    WSSynLog.insertSynLog(synLog);
                    //ROLLBACK ORDER - ORDER ACTION
                    ordersDTO.setOrderStatus(orderStatus);
                    orderActionDTO.setStatus(orderActionStatus);
                    message = WSOrders.updateOrdersAndOrderAction(ordersDTO, orderActionDTO);
                    if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                        System.out.println("LOG thuc xuat dong bo: cap nhat yeu cau - lenh khong thanh cong");
                    }
                    //
                    if (ParamUtils.ERROR_MESSAGE.SYNC_FAIL.equals(resultDTO.getKey())) {
                        reasonSyn = resultDTO.getMessage();
                    }
                    sEndTimeSyn = DateUtil.sysdateString();

                    //rollback giao dich
                    rollback(session, transaction);
                    //luu vao ket qua tra ve
                    resultDTO.setMessage(result.getReason());
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.SYNC_FAIL);
                    return resultDTO;
                }
                WSSynLog.insertSynLog(synLog);
            }
            //Thoi gian thuc xuat cua he thong dong bo ghi ra kpi_log
            endTimeSyn = System.currentTimeMillis() - startTime;
            durationSyn = String.valueOf(endTimeSyn);
            resultSyn = synLog != null ? synLog.getStatus() : SYN_FAIL;
            sEndTimeSyn = DateUtil.sysdateString();
            if (SYN_FAIL.equals(resultSyn)) {
                reasonSyn = synLog != null ? synLog.getDescription() : "Loi ket noi";
            }
            String messageTimeCalc = "Thoi gian thuc xuat yc: " + ordersDTO.getOrderCode() + " " + synSys + " : " + endTimeSyn + " | LOGISTICS : " + endTimeLogisitcs;
            System.out.println(messageTimeCalc);
            KPILogger.createLogs(messageTimeCalc);
            // end update status orders
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Loi thuc xuat dong bo: yeu cau id " + stockTransDTO.getOrderIdList());
            e.printStackTrace();
//           duyot: 15/01/2015 rollback trang thai lenh - yeu cau
            if (ordersDTO != null && orderActionDTO != null && !DataUtil.isStringNullOrEmpty(orderStatus)
                    && !DataUtil.isStringNullOrEmpty(orderActionStatus)) {
                ordersDTO.setOrderStatus(orderStatus);
                orderActionDTO.setStatus(orderActionStatus);
                //rollback order_order_action
                message = WSOrders.updateOrdersAndOrderAction(ordersDTO, orderActionDTO);
                //
                if (ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                    System.out.println("LOG KTTS: Loi mat ket noi,Rollback trang thai lenh - yeu cau thanh cong");
                } else {
                    System.out.println("LOG KTTS: Loi mat ket noi,Rollback trang thai lenh - yeu cau khong thanh cong");
                }
            }
            //------------------------------------------------------------------
            rollback(session, transaction);
            resultDTO.setMessage(ParamUtils.FAIL);
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            //Ghi log
            reasonLog = e.getMessage();
            resultLog = SYN_FAIL;
            sEndTime = DateUtil.sysdateString();
            return resultDTO;
        } finally {
            if (SYN_SUCC.equals(resultSyn)) {
                //Ghi log cua HT LOG
                duration = String.valueOf(endTimeLogisitcs);
                if (!SYN_SUCC.equals(resultLog)) {
                    realExportKpiLog.setReason(reasonLog);
                }
                realExportKpiLog.setDuration(duration);
                realExportKpiLog.setEndTime(sEndTime);
                realExportKpiLog.setFunctionCode(functionCode);
                realExportKpiLog.setStockTransStatus(resultLog);
                reason = "Thuc xuat tren he thong LOG";
                realExportKpiLog.setDescription(reason);
                realExportKpiLog.setTransactionCode(transactionCode);
                kpiLogBusiness.createKpiLog(realExportKpiLog);
            }
            //Ghi log cua HT dong bo
            if (!"".equals(resultSyn)) {
                if (ParamUtils.ERROR_MESSAGE.SYNC_FAIL.equals(resultDTO.getKey())) {
                    reasonSyn = resultDTO.getMessage();
                }
                synKpiLog.setTransactionCode(transactionCode);
                synKpiLog.setReason(reasonSyn);
                synKpiLog.setDuration(durationSyn);
                synKpiLog.setEndTime(sEndTimeSyn);
                synKpiLog.setFunctionCode(functionCodeSyn);
                synKpiLog.setStockTransStatus(resultSyn);
                synKpiLog.setDescription(descriptionSyn);
                synKpiLog.setStartTime(sStartTimeSyn);
                kpiLogBusiness.createKpiLog(synKpiLog);
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return resultDTO;
    }

    public List<StockTransSerialDTO> filterDetailSerial(String detailId, List<StockTransSerialDTO> lstStockTransSerialDTO) {
        List<StockTransSerialDTO> lstFilter = Lists.newArrayList();
        for (StockTransSerialDTO stockTransSerialDTO : lstStockTransSerialDTO) {
            if (detailId.equalsIgnoreCase(stockTransSerialDTO.getStockTransDetailId())) {
                lstFilter.add(stockTransSerialDTO);
            }
        }
        return lstFilter;
    }

    public void formatSerial(List<StockTransSerialDTO> lstSerial) {
        for (StockTransSerialDTO i : lstSerial) {
            i.setQuantity(i.getAmountReal());
            i.format();
        }
    }

    //
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
     private ResultDTO exportStockGoods(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        String message = "";
        StockGoodsDTO stockGoodsDTO = new StockGoodsDTO();
        StockGoodsDTO oneStockGoodsDTO = new StockGoodsDTO();

        String orderSource = stockTransDTO.getOrderSource();
        boolean isSyn = (orderSource != null && (orderSource.equals(Constants.TYPE_ORDERS_BCCS) || orderSource.equals(Constants.TYPE_ORDERS_KTTS)));
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
        if (isSyn) { // yeu cau dong bo xuat kho o trang thai cho xuat
            stockGoodsDTO.setStatus(Constants.STATUS_SERIAL_WAIT_STOCK);
            stockGoodsDTO.setOrderId(stockTransDTO.getOrderIdList());
            resultDTO = stockGoodsInterface.waitExportStockGoods(stockGoodsDTO, session);
        } else {
            stockGoodsDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
            resultDTO = stockGoodsInterface.exportStockGoods(stockGoodsDTO, session);
        }
        //tra ve list stock_goods da export
        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
            return resultDTO;
        }
        //
        return resultDTO;
    }

    //Insert or update Hang hoa theo so luong cho giao dich dieu chuyen
    private ResultDTO exportStockGoodsForTrans(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, Session session, Map<String, List<StockGoods>> map, int index) {
        ResultDTO resultDTO = new ResultDTO();
        //resultDTO.setMessage(ParamUtils.SUCCESS);
        String message = ParamUtils.SUCCESS;
        String key = "";
        StockGoodsDTO stockGoodsDTO = new StockGoodsDTO();
        //StockGoodsDTO oneStockGoodsDTO = new StockGoodsDTO();

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
//            message = ParamUtils.FAIL;
//            key = ParamUtils.SYSTEM_ERROR;
            //return resultDTO;
        }
        resultDTO.setMessage(message);
        resultDTO.setKey(key);
        //
        return resultDTO;
    }

    //
    private ResultDTO exportStockGoodsTotal(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        String message = "";
        StockGoodsDTO stockGoodsDTO = new StockGoodsDTO();
        StockGoodsDTO oneStockGoodsDTO = new StockGoodsDTO();

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
        newStockGoodsSerialStripDTO.setStatus(Constants.STATUS_SERIAL_OUT_STOCK);
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
                resultDTO.setFromSerial(stockTransSerialDTO.getFromSerial());
                resultDTO.setToSerial(stockTransSerialDTO.getToSerial());
                //            message = ParamUtils.FAIL;
                //            key = ParamUtils.SYSTEM_ERROR;
                //return resultDTO;
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

    //-----------------------------------------
    //Insert or update Hang hoa theo serial
    private ResultDTO exportStockGoodsSerial(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, StockTransSerialDTO stockTransSerialDTO, Session session) {
        ResultDTO resultDTO;
        String message = "";
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
            newStockGoodsSerialStripDTO.setQuantity(stockTransSerialDTO.getAmountReal());
            resultDTO = stockGoodsSerialStripBusiness2.exportStockGoodsSerialStrip(oldStockGoodsSerialStripDTO, newStockGoodsSerialStripDTO, session);
        } else //Mat hang quan ly serial don le 
        {
            //Cap nhat serial hang hoa
            resultDTO = stockGoodsSerialBusiness2.exportStockGoodsSerial(oldStockGoodsSerialStripDTO, newStockGoodsSerialStripDTO, session);
        }
        //
        return resultDTO;
    }
    //------------------------------------------

//    //
//    //Ghi log serial loi
//    private void insertStockGoodsSerialError(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
//        StockGoodsSerialErrorDTO stockGoodsSerialErrorDTO = new StockGoodsSerialErrorDTO();
//        //
//        stockGoodsSerialErrorDTO.setCustId(stockGoodsSerialStripDTO.getCustId());
//        stockGoodsSerialErrorDTO.setStockTransId(stockTransId);
//        stockGoodsSerialErrorDTO.setOwnerId(stockGoodsSerialStripDTO.getOwnerId());
//        stockGoodsSerialErrorDTO.setOwnerType(stockGoodsSerialStripDTO.getOwnerType());
//        stockGoodsSerialErrorDTO.setGoodsId(stockGoodsSerialStripDTO.getGoodsId());
//        stockGoodsSerialErrorDTO.setGoodsState(stockGoodsSerialStripDTO.getGoodsState());
//        stockGoodsSerialErrorDTO.setStatus(stockGoodsSerialStripDTO.getStatus());
//        stockGoodsSerialErrorDTO.setFromSerial(stockGoodsSerialStripDTO.getFromSerial());
//        stockGoodsSerialErrorDTO.setToSerial(stockGoodsSerialStripDTO.getToSerial());
//        stockGoodsSerialErrorDTO.setSaleType(stockGoodsSerialStripDTO.getSaleType());
//        stockGoodsSerialErrorDTO.setChangeUser(stockGoodsSerialStripDTO.getChangeUser());
//        stockGoodsSerialErrorDTO.setPrice(stockGoodsSerialStripDTO.getPrice());
//        stockGoodsSerialErrorDTO.setChannelTypeId(stockGoodsSerialStripDTO.getChannelTypeId());
//        stockGoodsSerialErrorDTO.setBarcode(stockGoodsSerialStripDTO.getBarcode());
//        stockGoodsSerialErrorDTO.setBincode(stockGoodsSerialStripDTO.getBincode());
//        stockGoodsSerialStripDTO.setAddInfor(stockGoodsSerialStripDTO.getAddInfor());
//        stockGoodsSerialErrorDTO.setChangeDate(stockGoodsSerialStripDTO.getChangeDate());
//        stockGoodsSerialErrorDTO.setImportDate(stockGoodsSerialStripDTO.getImportDate());
//        stockGoodsSerialErrorDTO.setSaleDate(stockGoodsSerialStripDTO.getSaleDate());
//        stockGoodsSerialErrorDTO.setCreateDatetime(sysdate);
//        //
//        stockGoodsSerialErrorBusiness.createObjectSession(stockGoodsSerialErrorDTO, session);
//    }
    //Cap nhat tong so luong 
    @Override
    public synchronized ResultDTO exportStockGoodsTotal(List<StockGoodsTotalDTO> lstStockGoodsTotalDTO) {
        ResultDTO resultDTO = new ResultDTO();
        Session session;
        Transaction transaction;

        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        String sysdate;
        //
        try {
            Double amountIssue;
            Double amount;
//            String changeDate = "";
            sysdate = stockGoodsTotalBusiness.getSysDate(formatDate);
            for (StockGoodsTotalDTO stockGoodsTotalDTO : lstStockGoodsTotalDTO) {
                amount = Double.parseDouble(stockGoodsTotalDTO.getAmount());
                amountIssue = Double.parseDouble(stockGoodsTotalDTO.getAmountIssue());
                stockGoodsTotalDTO.setAmount("");
                stockGoodsTotalDTO.setAmountIssue("");
                stockGoodsTotalDTO.setChangeDate("");
                resultDTO = stockGoodsTotalInterface.exportStockGoodsTotal(stockGoodsTotalDTO, amount, amountIssue, sysdate, session);
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    resultDTO.setKey(stockGoodsTotalDTO.getGoodsCode());
//                    session.getTransaction().rollback(session,transaction);
                    rollback(session, transaction);
                    break;
                }

            }
            transaction.commit();
        } catch (Exception e) {
            //resultDTO.setMessage(e.getMessage());
            e.printStackTrace();
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            return resultDTO;
        } finally {
            session.close();
        }
        //
        return resultDTO;
    }
//    xua kho cho dong bo

    @Override
    public synchronized ResultDTO exportStockGoodsTotalForSyn(List<StockGoodsTotalDTO> lstStockGoodsTotalDTO) {
        ResultDTO resultDTO = new ResultDTO();
        Session session;
        Transaction transaction;

        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        String sysdate;
        //
        try {
            Double amountIssue;
            Double amount;
//            String changeDate = "";
            sysdate = stockGoodsTotalBusiness.getSysDate(formatDate);
            for (StockGoodsTotalDTO stockGoodsTotalDTO : lstStockGoodsTotalDTO) {
                amount = Double.parseDouble(stockGoodsTotalDTO.getAmount());
                amountIssue = Double.parseDouble(stockGoodsTotalDTO.getAmountIssue());
                stockGoodsTotalDTO.setAmount("");
                stockGoodsTotalDTO.setAmountIssue("");
                stockGoodsTotalDTO.setChangeDate("");
                resultDTO = stockGoodsTotalInterface.exportStockGoodsTotalForSyn(stockGoodsTotalDTO, amount, amountIssue, sysdate, session);
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    resultDTO.setKey(stockGoodsTotalDTO.getGoodsCode());
//                    session.getTransaction().rollback(session,transaction);
                    rollback(session, transaction);
                    break;
                }

            }
            transaction.commit();
        } catch (Exception e) {
            //resultDTO.setMessage(e.getMessage());
            session.close();
            e.printStackTrace();
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            return resultDTO;
        }
        //
        return resultDTO;
    }

    //DUYOT - 1 GOODS-TRANSFER
    @Override
    public ResultDTO goodsTransfer(StockTransDTO exportStockTransDTO, StockTransDTO importStockTransDTO, List<StockTransDetailDTO> lstExportDetail, List<StockTransDetailDTO> lstImportDetail, List<StockTransSerialDTO> lstExportSerial, List<StockTransSerialDTO> lstImportSerial) {
        ResultDTO result = new ResultDTO();
        /*
         Map giua hang da xuat -> danh sach stock_goods cua hang do
         */
        Map<String, List<StockGoods>> mapStockgoods = new HashMap<>();
        Map<String, List<StockGoodsSerialStrip>> mapSerialTrip = new HashMap<>();
        //1--> BEGIN TRANS
        /*
         - khi da xuat hang -> dua ra map hang da ban cho phan nhap
         */
        //
        Session session;
        Transaction transaction;

        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        //
        try {
            //-->2 THUC HIEN GIAO DICH XUAT
            result = exportStockGoodsTransfer(exportStockTransDTO, lstExportDetail, lstExportSerial, session, transaction, mapStockgoods, mapSerialTrip);
            if (!result.getMessage().equals(ParamUtils.SUCCESS)) {
                //rollback(session,transaction);
                return result;
            }
            //-->3 THUC HIEN GIAO DICH NHAP
            //1. Cap nhat vao transaction
            //set fromstocktransid
            importStockTransDTO.setFromStockTransId(result.getId());
            //
            result = importStockGoodsTransfer(importStockTransDTO, lstImportDetail, lstExportDetail, lstImportSerial, lstExportSerial, session, transaction, mapStockgoods, mapSerialTrip);
            if (!result.getMessage().equals(ParamUtils.SUCCESS)) {
                return result;
            }
            //-> commit
            commit(session, transaction);
            return result;

        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            result.setKey(ParamUtils.SYSTEM_ERROR);
            result.setMessage(ParamUtils.FAIL);
            rollback(session, transaction);
            return result;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    //THIENNG1 - 1 GOODS-TRANSFER(dieu chinh hang hoa)
    @Override
    public ResultDTO goodsTransferSynsKTTS(StockTransDTO exportStockTransDTO, StockTransDTO importStockTransDTO,
            List<StockTransDetailDTO> lstExportDetail, List<StockTransDetailDTO> lstImportDetail,
            List<StockTransSerialDTO> lstExportSerial,
            List<StockTransSerialDTO> lstImportSerial, ChangeOrder changeOrder,
            List<ChangeGoods> lstChangeGoods, Map<String, GoodsDTO> mapGoodsDTO,
            List<MapStaffGoodsDTO> lstMapStaffGoodsDTOs) {
        ResultDTO result = new ResultDTO();
        /*
         Map giua hang da xuat -> danh sach stock_goods cua hang do
         */
        Map<String, List<StockGoods>> mapStockgoods = new HashMap<>();
        Map<String, List<StockGoodsSerialStrip>> mapSerialTrip = new HashMap<>();
        //1--> BEGIN TRANS
        /*
         - khi da xuat hang -> dua ra map hang da ban cho phan nhap
         */
        //
        Session session;
        Transaction transaction;

        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        //thienng1
        StringBuilder sbStockTrans = new StringBuilder();
        //
        try {
            //-->2 THUC HIEN GIAO DICH XUAT
            result = exportStockGoodsTransfer(exportStockTransDTO, lstExportDetail, lstExportSerial, session, transaction, mapStockgoods, mapSerialTrip);
            sbStockTrans.append(BundelUtils.getkey("stockTransExport"));
            sbStockTrans.append(result.getId());
            if (!result.getMessage().equals(ParamUtils.SUCCESS)) {
                //rollback(session,transaction);
                return result;
            }
            //-->3 THUC HIEN GIAO DICH NHAP
            //1. Cap nhat vao transaction
            //set fromstocktransid
            importStockTransDTO.setFromStockTransId(result.getId());
            //
            result = importStockGoodsTransfer(importStockTransDTO, lstImportDetail,
                    lstExportDetail, lstImportSerial, lstExportSerial, session, transaction,
                    mapStockgoods, mapSerialTrip);
            sbStockTrans.append(", ");
            sbStockTrans.append(BundelUtils.getkey("stockTransImport"));
            sbStockTrans.append(result.getId());
            if (!result.getMessage().equals(ParamUtils.SUCCESS)) {
                return result;
            }
            List<HistoryChangeGoodsDTO> lstHistoryChangeGoodsDTOs = new ArrayList();
            lstHistoryChangeGoodsDTOs = convertFromHistoryChangeGoods(changeOrder, lstChangeGoods, mapGoodsDTO);
            String messInsert = historyChangeGoodsBusiness.insertListSession(lstHistoryChangeGoodsDTOs, session);
            if (!ParamUtils.SUCCESS.equals(messInsert)) {
                rollback(session, transaction);
                result.setMessage("INSERT_ERROR_HISTORY");
                result.setKey("INSERT_ERROR_HISTORY");
                return result;
            } else {
                if (!DataUtil.isListNullOrEmpty(lstMapStaffGoodsDTOs)) {
                    //neu chuyen doi thanh cong thi luu thong tin vao bang message de thuc hien gui email
                    List<MessagesDTO> lstMessagesDTOs = new ArrayList();
                    AppParamsDTO appParamsDTO = new AppParamsDTO();
                    appParamsDTO.setParType("CHANGE_GOODS_MESSAGES");
                    appParamsDTO.setStatus("1");
                    List<AppParamsDTO> lstMessagesContent = WSAppParams.getListAppParamsDTO(appParamsDTO, 0, 100, "", "parType");
                    appParamsDTO.setParType("CHANGE_GOODS_SUBJECT_EMAIL");
                    appParamsDTO.setStatus("1");
                    List<AppParamsDTO> lstMessagesSubject = WSAppParams.getListAppParamsDTO(appParamsDTO, 0, 100, "", "parType");
                    for (MapStaffGoodsDTO mapStaffGoodsDTO : lstMapStaffGoodsDTOs) {
                        MessagesDTO messagesDTO = new MessagesDTO();
                        if (!DataUtil.isStringNullOrEmpty(mapStaffGoodsDTO.getStaffEmail())) {
                            messagesDTO.setEmail(mapStaffGoodsDTO.getStaffEmail());
                        } else {
                            messagesDTO.setEmail(BundelUtils.getkey("logistics.email"));
                        }
                        String content = lstMessagesContent.get(0).getParName().replace("@orderCode", changeOrder.getOrderCode());
                        messagesDTO.setMessages(content);
                        messagesDTO.setSubject(lstMessagesSubject.get(0).getParName());
                        messagesDTO.setCreateDateTime(messagesBusiness.getSysDate(formatDate));
                        messagesDTO.setStatus("0");
                        lstMessagesDTOs.add(messagesDTO);
                    }
                    String resultInsertMessage = messagesBusiness.insertListSession(lstMessagesDTOs, session);
                    if (!ParamUtils.SUCCESS.equals(resultInsertMessage)) {
                        rollback(session, transaction);
                        result.setMessage("INSERT_ERROR_MESSAGES");
                        result.setKey("INSERT_ERROR_MESSAGES");
                        return result;
                    }
                }
            }
            //-> commit
            commit(session, transaction);
            result.setKey(sbStockTrans.toString());
            return result;

        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            result.setKey(ParamUtils.SYSTEM_ERROR);
            result.setMessage(ParamUtils.FAIL);
            rollback(session, transaction);
            return result;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    private List<HistoryChangeGoodsDTO> convertFromHistoryChangeGoods(ChangeOrder changeOrder,
            List<ChangeGoods> lstChangeGoods, Map<String, GoodsDTO> mapGoodsDTO) {
        List<HistoryChangeGoodsDTO> lstResult = new ArrayList();
        for (ChangeGoods changeGoods : lstChangeGoods) {
            GoodsDTO oldGoodsDTO = mapGoodsDTO.get(changeGoods.getOldGoodsCode());
            GoodsDTO newGoodsDTO;
            if (changeGoods.getChangeType().equals("3") || changeGoods.getChangeType().equals("4")) {
                newGoodsDTO = mapGoodsDTO.get(changeGoods.getNewGoodsCode());
            } else {
                newGoodsDTO = mapGoodsDTO.get(changeGoods.getOldGoodsCode());
            }
            HistoryChangeGoodsDTO historyChangeGoodsDTO = new HistoryChangeGoodsDTO();
            //thong tin yeu cau
            historyChangeGoodsDTO.setCustId(changeOrder.getCustId());
            historyChangeGoodsDTO.setCustCode(changeOrder.getCustCode());
            historyChangeGoodsDTO.setCustName(changeOrder.getCustName());
            historyChangeGoodsDTO.setStockId(changeOrder.getStockId());
            historyChangeGoodsDTO.setStockCode(changeOrder.getStockCode());
            historyChangeGoodsDTO.setStockName(changeOrder.getStockName());
            historyChangeGoodsDTO.setOrderCode(changeOrder.getOrderCode());
            historyChangeGoodsDTO.setOrderUserName(changeOrder.getOrderUserName());
            historyChangeGoodsDTO.setExpectedDate(changeOrder.getExpectedDate());
            historyChangeGoodsDTO.setCompleteDate(stockGoodsTotalBusiness.getSysDate(formatDate));
            historyChangeGoodsDTO.setNotes(changeOrder.getDescription());
            //thong tin hang hoa
            historyChangeGoodsDTO.setChangeType(changeGoods.getChangeType());
            historyChangeGoodsDTO.setAmount(changeGoods.getAmount());
            if (!DataUtil.isNullObject(oldGoodsDTO)) {
                historyChangeGoodsDTO.setOldGoodsId(oldGoodsDTO.getGoodsId());
                historyChangeGoodsDTO.setOldGoodsCode(oldGoodsDTO.getCode());
                historyChangeGoodsDTO.setOldGoodsName(oldGoodsDTO.getName());
                historyChangeGoodsDTO.setOldGoodsUnitType(oldGoodsDTO.getUnitType());
            } else {
                historyChangeGoodsDTO.setOldGoodsCode(changeGoods.getOldGoodsCode());
                historyChangeGoodsDTO.setOldGoodsName(changeGoods.getOldGoodsName());
            }
            if (!DataUtil.isNullObject(newGoodsDTO)) {
                historyChangeGoodsDTO.setNewGoodsId(newGoodsDTO.getGoodsId());
                historyChangeGoodsDTO.setNewGoodsCode(newGoodsDTO.getCode());
                historyChangeGoodsDTO.setNewGoodsName(newGoodsDTO.getName());
                historyChangeGoodsDTO.setNewGoodsUnitType(newGoodsDTO.getUnitType());
            } else {
                historyChangeGoodsDTO.setOldGoodsCode(changeGoods.getOldGoodsCode());
                historyChangeGoodsDTO.setOldGoodsName(changeGoods.getOldGoodsName());
                if (!DataUtil.isStringNullOrEmpty(changeGoods.getNewGoodsCode())) {
                    historyChangeGoodsDTO.setOldGoodsCode(changeGoods.getNewGoodsCode());
                    historyChangeGoodsDTO.setOldGoodsName(changeGoods.getNewGoodsName());
                }
            }
            historyChangeGoodsDTO.setOldFromSerial(changeGoods.getOldFromSerial());
            historyChangeGoodsDTO.setOldToSerial(changeGoods.getOldToSerial());
            historyChangeGoodsDTO.setNewFromSerial(changeGoods.getOldFromSerial());
            historyChangeGoodsDTO.setNewToSerial(changeGoods.getOldToSerial());
            if (!DataUtil.isStringNullOrEmpty(changeGoods.getNewFromSerial())
                    && !DataUtil.isStringNullOrEmpty(changeGoods.getNewToSerial())) {
                historyChangeGoodsDTO.setNewFromSerial(changeGoods.getNewFromSerial());
                historyChangeGoodsDTO.setNewToSerial(changeGoods.getNewToSerial());
            }
            historyChangeGoodsDTO.setOldGoodsState(changeGoods.getOldState());
            historyChangeGoodsDTO.setNewGoodsState(changeGoods.getOldState());
            if (!DataUtil.isStringNullOrEmpty(changeGoods.getNewState())) {
                historyChangeGoodsDTO.setNewGoodsState(changeGoods.getNewState());
            }
            historyChangeGoodsDTO.setDescription(changeGoods.getDesciption());
            lstResult.add(historyChangeGoodsDTO);
        }
        return lstResult;

    }

    //EXPORT STOCK 
    public ResultDTO exportStockGoodsTransfer(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetailDTO, List<StockTransSerialDTO> lstStockTransSerialDTO, Session session, Transaction transaction, Map<String, List<StockGoods>> map, Map<String, List<StockGoodsSerialStrip>> mapSerialTrip) {
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
            List<GoodsDTO> lstGoodsDTO = goodsBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "", "code");
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
                    index++;
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
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
    public ResultDTO importStockGoodsTransfer(StockTransDTO stockTransDTO, List<StockTransDetailDTO> lstStockTransDetailDTO, List<StockTransDetailDTO> lstExportedStockTransDetail, List<StockTransSerialDTO> lstStockTransSerialDTO,
            List<StockTransSerialDTO> lstSerialExport, Session session, Transaction transaction, Map<String, List<StockGoods>> map, Map<String, List<StockGoodsSerialStrip>> mapSerialTrip) {
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
            //--> Insert giao dich kho
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
            int index = 0;
            //chay vong lap danh sach hang can nhap -> moi hang can nhap se co map chi so tuong ung voi hang da xuat tuong ung
            //dung map de lay ra danh sach stock_goods tuong ung
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
                    //CAP NHAT STOCK_GOODS_TOTAL
                    resultDTO = importStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        return resultDTO;
                    }
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
                        if (DataUtil.isStringNullOrEmpty(stockTransSerialDTO.getChangeType())
                                || !stockTransSerialDTO.getChangeType().equals("2")) {
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
                        } else {
                            for (StockTransSerialDTO i : lstSerialExport) {
                                if (i.getFromSerial().equalsIgnoreCase(stockTransSerialDTO.getTmpChangeStockGoodsFromSerial())
                                        && i.getToSerial().equalsIgnoreCase(stockTransSerialDTO.getTmpChangeStockGoodsToSerial())) {
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
                        }
                        //Id giao dich, ID chi tiet giao dichj
                        stockTransSerialDTO.setStockTransId(stockTransId);
                        stockTransSerialDTO.setStockTransDetailId(stockTransDetailId);
                        stockTransSerialDTO.setStockTransDate(sysdate);
                        stockTransSerialDTO.setCreateDatetime(sysdate);
                        //Insert kho hang hoa theo serial
                        //CAP NHAT STOCK_GOODS_SERIAL_ STOCK_GOODS_SERIAL_TRIP
                        if (!stockTransDetailDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL)) {//NEU LA SERIAL
                            if (DataUtil.isStringNullOrEmpty(stockTransSerialDTO.getChangeType())
                                    || !stockTransSerialDTO.getChangeType().equals("2")) {
                                resultDTO = importStockGoodsSerial(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, filteredExportSerial, session);
                            } else {
                                resultDTO = importStockGoodsSerialNew(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, filteredExportSerial, session);
                            }
                            //                            
                        } else {//NEU LA SERIAL STRIP
                            //LAY RA MAP STOCK_GOODS_SERIAL_TRIP DA XUAT
                            String key = lstExportedStockTransDetail.get(index).getGoodsCode() + stockTransSerialDTO.getFromSerial();
                            List<StockGoodsSerialStrip> lstExportedTrip = mapSerialTrip.get(key);
                            resultDTO = importStockGoodsSerialStrip(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, lstExportedTrip, session);
                        }
                        if (!resultDTO.getMessage().equalsIgnoreCase(ParamUtils.SUCCESS)) {
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
                        resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction);
                            return resultDTO;
                        }
                    }
                    //Cap nhat so luong tong cong
                    //CAP NHAT STOCK_GOODS_TOTAL
                    stockTransDetailDTO.setAmountReal(amountIssue.toString().replace(".0", ""));
                    resultDTO = importStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                    if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //
                    index++;
                }
            }
            //commit();
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setMessage(e.getMessage());
            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
            rollback(session, transaction);
            return resultDTO;
        }
        //
        resultDTO.setQuantitySucc(insertSuccess);
        resultDTO.setQuantityFail(insertFail);
        resultDTO.setId(stockTransId);
        return resultDTO;
    }

    //--------------SUPPORT FUNCTION FOR IMPORT--------------------
    private ResultDTO importStockGoods(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, StockTransDetailDTO exportedStockTransDetail, Session session, Map<String, List<StockGoods>> map, int index) {
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
                stockGoodsDTO.setImportDate(DateUtil.dateTime2String(temp.getImportDate()));
                stockGoodsDTO.setImportStockTransId(stockTransDTO.getStockTransId());
                //lay barcode - bincode tu stock_goods
                stockGoodsDTO.setBarcode(temp.getBarcode());
                stockGoodsDTO.setBincode(temp.getBarcode());
                stockGoodsDTO.setCellCode(exportedCellcode);
                stockGoodsDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK); // status

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
            stockGoodsDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK); // status
            stockGoodsDTO.setImportStockTransId(stockTransDTO.getStockTransId());

            lstStockGoods.add(stockGoodsDTO);
        }

        //goi ham insert list stock_goods
        resultDTO = stockGoodsInterface.importListStockGoods(lstStockGoods, session);
        //
        return resultDTO;
        //
    }

    //----------------
    private ResultDTO importStockGoodsTotal(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, Session session) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
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

    //-------------------------------------------------------------------------------------------
    //IMPORT LAI HANG SERIAL STRIP
    public ResultDTO importStockGoodsSerialStrip(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, StockTransSerialDTO importStockTransSerial,
            List<StockGoodsSerialStrip> exportedTrip, Session session) {

        ResultDTO resultDTO;
        StockGoodsSerialStripDTO importStrip = new StockGoodsSerialStripDTO();

        //SET EXPORTED
//            StockGoodsSerialStripDTO exportedTrip = new StockGoodsSerialStripDTO();
//            
//            exportedTrip.setCustId(stockTransDTO.getCustId());
//            exportedTrip.setOwnerId(stockTransDTO.getOwnerId());
//            exportedTrip.setOwnerType(stockTransDTO.getOwnerType());
//            exportedTrip.setGoodsId(exportedStockTransSerial.getGoodsId());
//            exportedTrip.setGoodsState(exportedStockTransSerial.getGoodsState());
//            exportedTrip.setFromSerial(exportedStockTransSerial.getFromSerial());
//            exportedTrip.setToSerial(exportedStockTransSerial.getToSerial());
        //SET IMPORT TRIP
        importStrip.setGoodsId(importStockTransSerial.getGoodsId());
        importStrip.setOwnerType(stockTransDTO.getOwnerType());
        importStrip.setGoodsState(importStockTransSerial.getGoodsState());
        importStrip.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
        importStrip.setQuantity(importStockTransSerial.getAmountReal());
        importStrip.setImportStockTransId(stockTransDTO.getStockTransId());
        //thienng1 -- Addby 28/03/2016
        importStrip.setCellCode(importStockTransSerial.getCellCode());
        //CAP NHAT
//            resultDTO = stockGoodsSerialStripBusiness2.updateNewGoods(importStrip, exportedTrip, session);

        resultDTO = stockGoodsSerialStripBusiness2.updateNewListGoods(importStrip, exportedTrip, session);

        return resultDTO;

    }

    //Insert or update Hang hoa theo serial
    private ResultDTO importStockGoodsSerial(StockTransDTO stockTransDTO, StockTransDetailDTO stockTransDetailDTO, StockTransSerialDTO importStockTransSerial,
            StockTransSerialDTO exportedStockTransSerial, Session session) {

        ResultDTO resultDTO = new ResultDTO();
        StockGoodsSerialStripDTO importStrip = new StockGoodsSerialStripDTO();
        StockGoodsSerialDTO stockGoodsSerialDTO = new StockGoodsSerialDTO();

        //Cac tham so dau vao                             
        //-> NEU LA HANG QUAN LY THEO DAI 
//        if (stockTransDetailDTO.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {
//            //SET EXPORTED
//            StockGoodsSerialStripDTO exportedTrip = new StockGoodsSerialStripDTO();
//            
//            exportedTrip.setCustId(stockTransDTO.getCustId());
//            exportedTrip.setOwnerId(stockTransDTO.getOwnerId());
//            exportedTrip.setOwnerType(stockTransDTO.getOwnerType());
//            exportedTrip.setGoodsId(exportedStockTransSerial.getGoodsId());
//            exportedTrip.setGoodsState(exportedStockTransSerial.getGoodsState());
//            exportedTrip.setFromSerial(exportedStockTransSerial.getFromSerial());
//            exportedTrip.setToSerial(exportedStockTransSerial.getToSerial());
//            //SET IMPORT TRIP
//            importStrip.setGoodsId(importStockTransSerial.getGoodsId());
//            importStrip.setOwnerType(stockTransDTO.getOwnerType());
//            importStrip.setGoodsState(importStockTransSerial.getGoodsState());
//            importStrip.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
//            importStrip.setQuantity(importStockTransSerial.getAmountReal());
//            //CAP NHAT
////            resultDTO = stockGoodsSerialStripBusiness2.updateNewGoods(importStrip, exportedTrip, session);
//            
//            resultDTO = stockGoodsSerialStripBusiness2.updateNewListGoods(importStrip, exportedTrip, session);
//        } else //NEU LA HANG SERIAL DON LE
//        {
        //SET EXPORTED SERIAL
        StockGoodsSerialDTO exportedStockGoodsSerialDTO = new StockGoodsSerialDTO();
        exportedStockGoodsSerialDTO.setCustId(stockTransDTO.getCustId());
        exportedStockGoodsSerialDTO.setOwnerId(stockTransDTO.getOwnerId());
        exportedStockGoodsSerialDTO.setGoodsId(exportedStockTransSerial.getGoodsId());
        exportedStockGoodsSerialDTO.setSerial(exportedStockTransSerial.getFromSerial());
        //SET IMPORT SERIAL
        stockGoodsSerialDTO.setGoodsId(importStockTransSerial.getGoodsId());
        stockGoodsSerialDTO.setGoodsState(importStockTransSerial.getGoodsState());
        stockGoodsSerialDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
        stockGoodsSerialDTO.setImportStockTransId(stockTransDTO.getStockTransId());
        //CAP NHAT THONG TIN GOODS_ID, GOODS_STATE,STATUS)
        //duyot- sua cho truong hop serial gep dai
        String fromSerial = importStockTransSerial.getFromSerial();
        String toSerial = importStockTransSerial.getToSerial();

        resultDTO = stockGoodsSerialBusiness2.updateNewGoods(stockGoodsSerialDTO, exportedStockGoodsSerialDTO, fromSerial, toSerial, session);

//        }
        //
        return resultDTO;
    }

    //Insert or update Hang hoa theo serial
    private ResultDTO importStockGoodsSerialNew(StockTransDTO stockTransDTO,
            StockTransDetailDTO stockTransDetailDTO, StockTransSerialDTO importStockTransSerial,
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
        //SET IMPORT SERIAL
        stockGoodsSerialDTO.setGoodsId(importStockTransSerial.getGoodsId());
        stockGoodsSerialDTO.setGoodsState(importStockTransSerial.getGoodsState());
        stockGoodsSerialDTO.setStatus(Constants.STATUS_SERIAL_IN_STOCK);
        stockGoodsSerialDTO.setSerial(importStockTransSerial.getFromSerial());
        stockGoodsSerialDTO.setImportStockTransId(stockTransDTO.getStockTransId());
        //CAP NHAT THONG TIN GOODS_ID, GOODS_STATE,STATUS)
        //duyot- sua cho truong hop serial gep dai
        String fromSerial = importStockTransSerial.getFromSerial();
        String toSerial = importStockTransSerial.getToSerial();

        resultDTO = stockGoodsSerialBusiness2.updateNewSerialGoods(stockGoodsSerialDTO, exportedStockGoodsSerialDTO, fromSerial, toSerial, session);

//        }
        //
        return resultDTO;
    }

    //-------ROLL-BACK AND COMMIT
    private void rollback(Session session, Transaction transaction) {
        transaction.rollback();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    private void rollback(Transaction transaction) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }

    private void commit(Session session, Transaction transaction) {
        transaction.commit();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    //
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

    //duyot: new function
    /*
     @params: trans:giao dich nhap kho, lstGoods: danh sach hang hoa da nhap kho, listserial belong to goods
     @return: billStock
     */
    public BillStock createBillStockFromTrans(StockTransDTO trans, OrdersDTO ordersDTO) {
        BillStock billStock = new BillStock();
        //thong tin fig
        String stockCode = mapStock(trans.getOwnerId());
        String inputType = "1";
        //
        String outputType = ordersDTO.getOutputType();
        String orderType = "2";//type export
        //SET THONG TIN CHUNG
        billStock.setOrderCode(orderIdToCode(trans.getOrderIdList()));
        billStock.setTransCode(trans.getStockTransCode());
        billStock.setStockCode(stockCode);
        billStock.setOrderType(orderType);
        billStock.setIeExpectDate(trans.getStockTransDate());
        billStock.setInputType(inputType);
        billStock.setOutputType(outputType);
        //duyot 15/01/2016: set them thong tin nguoi thuc nhap
        if (!DataUtil.isStringNullOrEmpty(trans.getTransUserName())) {
            billStock.setRealIEUserName(trans.getTransUserName());
        } else {
            billStock.setRealIEUserName("admin-vtp");
        }
        //
        return billStock;
    }

    public String mapStock(String stockId) {
        //mapping process
        return ((Stock) stockBusiness.findById(Long.parseLong(stockId))).getCode();
        //
    }

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

    public GoodsInTicketDTO createGoodsFromDetail(StockTransDetailDTO transDetail) {
        GoodsInTicketDTO goodsInTicket = new GoodsInTicketDTO();
        //SET THONG TIN HANG
        goodsInTicket.setGoodsCode(transDetail.getGoodsCode());
        goodsInTicket.setGoodsName(transDetail.getGoodsName());
        goodsInTicket.setAmountReal(DataUtil.removeDotInteger(transDetail.getAmountReal()));
        goodsInTicket.setAmountOrder(DataUtil.removeDotInteger(transDetail.getAmountOrder()));
        goodsInTicket.setGoodsUnitName(transDetail.getGoodsUnitTypeName());
        goodsInTicket.setGoodsState(transDetail.getGoodsState());
        //set null default sort fields
        goodsInTicket.setDefaultSortField(null);
        return goodsInTicket;
    }

    @Override
    public ResultDTO exportStockMultiLocation(List<StockTransDTO> lstStockTransDTO) {

        String stockTransDetailId;
        List<StockTransSerialDTO> lstFilterListStockTransSerialDTO;
        String stockTransCode;
        //
        String sysdate;
        String stockTransId;
        //
        Map<String, GoodsDTO> mapGoodsDTO;
        //
        Session session;
        Transaction transaction;
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        //
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);

        GoodsDTO goodsDTO;
        //
        //du lieu cho dong bo bccs
        BillStock billStock;
        List<GoodsInTicketDTO> lstGoods = new ArrayList<>();
        List<StockTransDetailDTO> lstStockTransDetailDTO = new ArrayList<>();
        List<StockTransSerialDTO> lstStockTransSerialDTO = new ArrayList<>();
        List<ConditionBean> lstConditionBean = new ArrayList<>();
        boolean isRollBack = false;
        StringBuilder builder = new StringBuilder();
        //
        try {
            for (StockTransDTO stockTransDTO : lstStockTransDTO) {

                isRollBack = false;
                sysdate = stockGoodsBusiness.getSysDate(formatDate, session);
                // lay thong tin yeu cau
                String orderIdList = stockTransDTO.getOrderIdList();
//                lstConditionBean.add(new ConditionBean("orderId", ParamUtils.NAME_EQUAL, orderIdList, ParamUtils.TYPE_NUMBER));
                OrdersDTO ordersDTO = WSOrders.findOrderById(orderIdList);
                if (ordersDTO != null && ordersDTO.getOrderStatus().equalsIgnoreCase(ORDER_STATUS_IMPORTED_EXPORTED)) {
                    resultDTO.setMessage(orderIdList);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_IE);
                    return resultDTO;
//                    isRollBack = true;
//                    continue;
                }

                //Insert giao dich kho
                stockTransCode = ParamUtils.CODE_EXPORT_STOCK + stockTransBusiness.getSequenceSession("STOCK_TRANS_SEQ", session);
                stockTransDTO.setStockTransCode(stockTransCode);
                stockTransDTO.setCreateDatetime(sysdate);
                stockTransDTO.setStockTransDate(sysdate);
                resultDTO = stockTransBusiness.createObjectSession(stockTransDTO, session);
                stockTransId = resultDTO.getId();
                stockTransDTO.setStockTransId(stockTransId);
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback(session, transaction);
                    resultDTO.setMessage(orderIdList);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.ORDER_IE);
                    return resultDTO;
//                    continue;
                }
                //
                //Lay danh sach hang hoa yeu cau day vao map
                lstStockTransDetailDTO = stockTransDTO.getLstStockTransDetailDTO();
                String goodsIdList = getGoodsIdList(lstStockTransDetailDTO);
                lstConditionBean.clear();
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
//                        transaction.rollback();
//                    resultDTO.setMessage(ParamUtils.FAIL);
                        resultDTO.setMessage(String.valueOf(goodsDTO.getGoodsId()) + "," + " ");
                        resultDTO.setKey(ParamUtils.GOODS_IS_NOT_EXIST);
//                        isRollBack = true;
//                        break;
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
                        isRollBack = true;
//                        transaction.rollback();
//                        break;
                        rollback(session, transaction);
                        return resultDTO;
                    }
                    //Neu la mat hang khong quan ly serial
                    if (!stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                        //Cap nhat mat hang theo so luong 
                        resultDTO = exportStockGoods(stockTransDTO, stockTransDetailDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction);
                            return resultDTO;
//                            isRollBack = true;
//                            transaction.rollback();
//                            break;
                        }
                        //Cap nhat so luong tong cong
                        resultDTO = exportStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction);
                            return resultDTO;
//                            isRollBack = true;
//                            transaction.rollback();
//                            break;
                        }

                        GoodsInTicketDTO goods = createGoodsFromDetail(stockTransDetailDTO);
                        lstGoods.add(goods);

                    } else //Mat hang Quan ly theo serial
                    {
                        //Lay danh sach chi tiet giao dich serial theo chi tiet giao dich
//                        lstFilterListStockTransSerialDTO = filterStockTransSerialDTO(stockTransDetailDTO.getTmpStockTransDetailId(), lstStockTransSerialDTO);
                        lstFilterListStockTransSerialDTO = stockTransDetailDTO.getLstStockTransSerialDTO();
                        // kiem tra du lieu chi tiet serial sai
                        if (lstFilterListStockTransSerialDTO == null || lstFilterListStockTransSerialDTO.isEmpty()) {
                            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
                            resultDTO.setMessage(ParamUtils.FAIL);
                            rollback(session, transaction);
                            return resultDTO;
//                            isRollBack = true;
//                            transaction.rollback();
//                            break;
                        }
                        //Insert giao dich chi tiet serial
                        for (StockTransSerialDTO stockTransSerialDTO : lstFilterListStockTransSerialDTO) {
                            //Id giao dich, ID chi tiet giao dichj
                            stockTransSerialDTO.setStockTransId(stockTransId);
                            stockTransSerialDTO.setStockTransDetailId(stockTransDetailId);
                            stockTransSerialDTO.setStockTransDate(sysdate);
                            stockTransSerialDTO.setCreateDatetime(sysdate);
                            //Tao chi tiet giao dich serial
                            stockTransSerialDTO.setGoodsCode(goodsDTO.getCode());
                            stockTransSerialDTO.setGoodsName(goodsDTO.getName());
                            stockTransSerialDTO.setQuantity(stockTransSerialDTO.getAmountReal());
                            resultDTO = stockTransSerialBusiness.createObjectSession(stockTransSerialDTO, session);
                            if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                rollback(session, transaction);
                                return resultDTO;
//                                isRollBack = true;
//                                transaction.rollback();
//                                break;
                            }
                            //Xuat kho kho hang hoa theo serial
                            if (stockTransDetailDTO.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
                                //
                                resultDTO = exportStockGoodsSerial(stockTransDTO, stockTransDetailDTO, stockTransSerialDTO, session);
                                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                                    rollback(session, transaction);
                                    return resultDTO;
//                                    isRollBack = true;
//                                    transaction.rollback();
//                                    break;
                                }
                            }
                        }
                        //Cap nhat Kho hang hoa theo so luong
                        resultDTO = exportStockGoodsTotal(stockTransDTO, stockTransDetailDTO, session);
                        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                            rollback(session, transaction);
                            return resultDTO;
//                            isRollBack = true;
//                            transaction.rollback();
//                            break;
                        }

                        //stockTransDetailBusiness.update(search);
                        GoodsInTicketDTO goods = createGoodsFromDetail(stockTransDetailDTO);
                        //set list serial imported in goods
                        List<StockTransSerialDTO> lstSerial = DataUtil.cloneList(lstFilterListStockTransSerialDTO);
                        formatSerial(lstSerial);
                        goods.setLstSerial(lstSerial);
                        lstGoods.add(goods);
                    }
                }
//                if (isRollBack) {
//                    continue;
//                }

                // update status orders, order action
                String orderStatus = ordersDTO.getOrderStatus();
                ordersDTO.setOrderStatus(ORDER_STATUS_IMPORTED_EXPORTED);

                //
                lstConditionBean.clear();
                lstConditionBean.add(new ConditionBean("orderIdList", ParamUtils.NAME_EQUAL, orderIdList, ParamUtils.TYPE_STRING));
                OrderActionDTO orderActionDTO = WSOrderAction.getListOrderActionByCondition(lstConditionBean, 0, Integer.MAX_VALUE, "", "id").get(0);
                String orderActionStatus = orderActionDTO.getStatus();
                orderActionDTO.setStatus(ORDER_ACTION_STATUS_EXPORTED);
                String message = WSOrders.updateOrdersAndOrderAction(ordersDTO, orderActionDTO);
                if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                    resultDTO.setMessage(ParamUtils.FAIL);
                    resultDTO.setKey(ParamUtils.ERROR_MESSAGE.UPDATE_ORDER_ACTION_FAIL);
                    rollback(session, transaction);
                    return resultDTO;
//                    transaction.rollback();
//                    continue;
                }
                //
                String isBCCS = ordersDTO.getSourceOrder();
                if (isBCCS != null && isBCCS.equalsIgnoreCase("1")) {//NEU  LA YC TU DONG TU BCCS
                    billStock = createBillStockFromTrans(stockTransDTO, ordersDTO);
                    billStock.setLstGoods(lstGoods);
                    //          //gui yc sang ht bccs
                    DataIEBccs data = new DataIEBccs(billStock);
                    ResultBCCSDTO result = WSBccsGateway.callServiceTransStockBccs(data);
                    //luu thong tin giao dich
                    SynLogDTO synLog = new SynLogDTO();
                    synLog.setAppCode(ParamUtils.APP_CODE.WMS_EXPORT_STOCK);
                    synLog.setKey(orderIdToCode(stockTransDTO.getOrderIdList()));
                    synLog.setDescription(result.getDescription());
                    synLog.setStatus(result.getResponseCode());
                    WSSynLog.insertSynLog(synLog);
                    //NEU RESPONSE_CODE TRA VE THANH CONG -> COMMIT
                    if (result.getResponseCode().equalsIgnoreCase("0")) {
//                    commit(session, transaction);
                    } else {
                        ordersDTO.setOrderStatus(orderStatus);
                        orderActionDTO.setStatus(orderActionStatus);
                        message = WSOrders.updateOrdersAndOrderAction(ordersDTO, orderActionDTO);
                        if (!ParamUtils.SUCCESS.equalsIgnoreCase(message)) {
                            // 
                        }
                        resultDTO.setMessage(ParamUtils.SYSTEM_OR_DATA_ERROR);
                        resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
//                        transaction.rollback();
//                        continue;
                        rollback(session, transaction);
                        return resultDTO;
                    }
                }
                // end update status orders
            }
            transaction.commit();
//            builder.append(orderIdList);
//            builder.append("-");
        } catch (Exception e) {
            e.printStackTrace();
//                Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            resultDTO.setMessage(ParamUtils.FAIL);
            rollback(session, transaction);
//            transaction.rollback();
            return resultDTO;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
//        resultDTO.setKey(builder.toString());
        return resultDTO;
    }
}
