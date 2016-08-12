/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.common.dataprovider.WSCustomer;
import com.viettel.common.dataprovider.WSMapStock;
import com.viettel.common.dataprovider.WSSynLog;
import com.viettel.logistic.wms.business.service.MapStaffGoodsBusinessInterface;
import com.viettel.logistic.wms.business.service.StockGoodsBusinessInterface;
import com.viettel.logistic.wms.business.service.StockGoodsSerialServiceInterface;
import com.viettel.logistic.wms.business.service.StockGoodsTotalBusinessInterface;
import com.viettel.logistic.wms.dto.CustomerDTO;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.dto.HistoryChangeGoodsDTO;
import com.viettel.logistic.wms.dto.MapStaffGoodsDTO;
import com.viettel.logistic.wms.dto.MapStockDTO;
import com.viettel.logistic.wms.dto.StockDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsTotalDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.logistic.wms.webservice.WSStaffStock;
import com.viettel.logstic.wms.webservice.dto.ChangeGoods;
import com.viettel.logstic.wms.webservice.dto.ChangeOrder;
import com.viettel.logstic.wms.webservice.dto.ResultKTTS;
import com.viettel.logstic.wms.webservice.dto.StaffStockDTO;
import com.viettel.logstic.wms.webservice.dto.SynLogDTO;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import javax.jws.WebService;
import java.util.ArrayList;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.BundelUtils;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.DataUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
* @author TruongBX3
* @version 1.0
* @since 29-Apr-15 11:36 AM
*/
@WebService(endpointInterface = "com.viettel.logistic.wms.service.StockGoodsTotalService")
public class StockGoodsTotalServiceImpl extends SpringBeanAutowiringSupport implements StockGoodsTotalService {

    String formatDate = "dd/mm/yyyy hh24:mi:ss";
    @Autowired
    BaseFWServiceInterface stockGoodsTotalBusiness;
    @Autowired
    StockGoodsTotalBusinessInterface stockGoodsTotalBusinessInterface;
    @Autowired
    StockGoodsSerialServiceInterface stockGoodsSerialServiceInterface;
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;
    @Autowired
    BaseFWServiceInterface goodsBusiness;
    @Autowired
    BaseFWServiceInterface stockBusiness;
    @Autowired
    StockGoodsBusinessInterface stockGoodsBusinessInterface;
    @Autowired
    BaseFWServiceInterface stockTransBusiness;
    @Autowired
    BaseFWServiceInterface historyChangeGoodsBusiness;
    @Autowired
    MapStaffGoodsBusinessInterface mapStaffGoodsBusinessInterface;
    @Autowired
    BaseFWServiceInterface messagesBusiness;

    @Autowired
    StockExportService stockExportService;

    @Override
    public String updateStockGoodsTotal(StockGoodsTotalDTO stockGoodsTotalDTO) {
        return stockGoodsTotalBusiness.update(stockGoodsTotalDTO);
    }

    @Override
    public String deleteStockGoodsTotal(Long id) {
        return stockGoodsTotalBusiness.delete(id);
    }

    @Override
    public String deleteListStockGoodsTotal(List<StockGoodsTotalDTO> stockGoodsTotalListDTO) {
        return stockGoodsTotalBusiness.delete(stockGoodsTotalListDTO);
    }

    @Override
    public StockGoodsTotalDTO findStockGoodsTotalById(Long id) {
        if (id != null && id > 0) {
            return (StockGoodsTotalDTO)stockGoodsTotalBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<StockGoodsTotalDTO> getListStockGoodsTotalDTO(StockGoodsTotalDTO stockGoodsTotalDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
         if (stockGoodsTotalDTO != null ) {
            return stockGoodsTotalBusiness.search(stockGoodsTotalDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertStockGoodsTotal(StockGoodsTotalDTO stockGoodsTotalDTO) {
        return stockGoodsTotalBusiness.createObject(stockGoodsTotalDTO);
    }
    @Override
    public String insertOrUpdateListStockGoodsTotal(List<StockGoodsTotalDTO> stockGoodsTotalDTO) {
        for(StockGoodsTotalDTO stockGoodsTT : stockGoodsTotalDTO){
            stockGoodsTT.setChangeDate(stockGoodsTotalBusiness.getSysDate(formatDate));
        }
         return stockGoodsTotalBusiness.insertList(stockGoodsTotalDTO);
    }

    @Override
    public List<String> getSequenseStockGoodsTotal(String seqName, int... size) {
        int number = ( size[0] > 0 ? size[0] : 1 );
       
        return stockGoodsTotalBusiness.getListSequense(seqName, number);
    }
    
       @Override
    public List<StockGoodsTotalDTO> getListStockGoodsTotalByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<StockGoodsTotalDTO> lstStockGoodsTotal = new ArrayList<>();
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
        lstStockGoodsTotal = stockGoodsTotalBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstStockGoodsTotal;
    }

    @Override
    public List<StockGoodsTotalDTO> getListDemo() {
//     return   stockGoods.demoGoodsTotal();
        return null;
    }

    @Override
    public List<StockGoodsInforDTO> getSumListStockGoods(StockGoodsInforDTO stockGoodsInforDTO) {
        return stockGoodsTotalBusinessInterface.getSumListStockGoods(stockGoodsInforDTO);
    }

    //transaction
    StockTransDTO transImport = new StockTransDTO();
    StockTransDTO transExport = new StockTransDTO();
    //detail
    List<StockTransDetailDTO> lstTransImportDetail = new ArrayList<>();
    List<StockTransDetailDTO> lstTransExportDetail = new ArrayList<>();
    //serial
    List<StockTransSerialDTO> lstTransExportSerial = new ArrayList<>();
    List<StockTransSerialDTO> lstTransImportSerial = new ArrayList<>();
    List<ChangeGoods> lstChangeGoodsError = new ArrayList();
    List<MapStaffGoodsDTO> lstMapStaffGoodsDTOs = new ArrayList<>();

    /**
     * Ham dieu chinh trang thai/serial/so luong hang hoa theo yêu cau
     *
     * @param changeOrder
     * @param lstChangeGoods
     * @return
     */
    @Override
    public ResultKTTS changeStockGoods(ChangeOrder changeOrder, List<ChangeGoods> lstChangeGoods) {
        //day thong tin ra LOG
        System.out.println("START OUTPUT DATA CHANGE GOODS: " + changeOrder.getOrderCode());
        System.out.println(changeOrder.toString());
        for (ChangeGoods changeGods : lstChangeGoods) {
            System.out.println(changeGods.toString());
        }
        System.out.println("END OUTPUT DATA CHANGE GOODS: " + changeOrder.getOrderCode());
        //
        ResultKTTS resultKTTS = new ResultKTTS();
        if (DataUtil.isNullObject(changeOrder)) {
            resultKTTS.setStatus(ParamUtils.SYNC.FAIL);
            resultKTTS.setReason(BundelUtils.getkey("infor.changeOrder.zero"));
            return resultKTTS;
        }
        resultKTTS.setOrderCode(changeOrder.getOrderCode());
        List<ConditionBean> lstConditionBeanSearch = new ArrayList<>();
        //danh sach trang thai hang hoa hop le
        List<String> lstGoodsState = new ArrayList();
        lstGoodsState.add("1");
        lstGoodsState.add("2");
        //danh sach lich su can luu lai
        List<HistoryChangeGoodsDTO> lstHistoryChangeGoodsDTOs = new ArrayList();
        List<ChangeGoods> lstChangeGoodsFilter = new ArrayList();
        List<String> lstGoodsCode = new ArrayList();
        Map<String, GoodsDTO> mapGoodsDTO = new HashMap<>();
        Map<String, GoodsDTO> mapGoodsDTOById = new HashMap<>();
        StockDTO stockDTOInfor = new StockDTO();
        List<GoodsDTO> lstGoodsDTO = new ArrayList();
        List<ConditionBean> lstConditionBean = new ArrayList<>();
        List<ChangeGoods> lstErrList = new ArrayList();
        String custId = "";
        String custName = "";
        SynLogDTO synLogDTO = new SynLogDTO();
        synLogDTO.setAppCode("WMS_CHANGE_STOCK_GOODS");
        try {
            //kiem tra thong tin yeu cau dau vao
            StringBuilder sbError = new StringBuilder();
            StringBuilder contentError = new StringBuilder();
            if (DataUtil.isStringNullOrEmpty(changeOrder.getOrderCode())) {//thieu thong tin ma yeu cau
                sbError.append(", ");
                sbError.append(BundelUtils.getkey("infor.orders.orderCode.zero"));
                System.out.println("Thieu thong tin ma yeu cau");
            } else {
                synLogDTO.setKey(changeOrder.getOrderCode());
            }
            if (DataUtil.isStringNullOrEmpty(changeOrder.getStockCode())) {//thieu thong tin kho
                sbError.append(", ");
                sbError.append(BundelUtils.getkey("infor.orders.stockCode.zero"));
                System.out.println("Thieu thong tin ma kho");
            }
            if (DataUtil.isStringNullOrEmpty(changeOrder.getExpectedDate())) {//thieu thong tin ngay gui yc
                sbError.append(", ");
                sbError.append(BundelUtils.getkey("infor.orders.expectedDate.zero"));
                System.out.println("Thieu thong tin ngay gui yeu cau");
            }
            if (DataUtil.isStringNullOrEmpty(changeOrder.getOrderUserName())) {//thieu thong tin nguoi gui yc
                sbError.append(", ");
                sbError.append(BundelUtils.getkey("infor.orders.orderUserName.zero"));
                System.out.println("Thieu thong tin nguoi gui yeu cau");
            }
            if (!DataUtil.isStringNullOrEmpty(sbError.toString())) {
                contentError.append(BundelUtils.getkey("infor.orders.zero"));
                contentError.append(":");
                contentError.append(sbError.toString().replaceFirst(",", ""));
                resultKTTS.setStatus(ParamUtils.SYNC.FAIL);
                resultKTTS.setReason(contentError.toString());
                insertSynLog(synLogDTO, ParamUtils.SYNC.FAIL, BundelUtils.getkey("infor.orders.zero"));
                return resultKTTS;
            }
            // kiem tra la duy nhat tren he thong
            boolean iCheckOrder = checkOrderCodeInTrans(changeOrder.getOrderCode());
            if (!iCheckOrder) {
                resultKTTS.setStatus(ParamUtils.SYNC.FAIL);
                resultKTTS.setReason(BundelUtils.getkey("notifi.orderCode.exists"));
                insertSynLog(synLogDTO, ParamUtils.SYNC.FAIL, BundelUtils.getkey("notifi.orderCode.exists"));
                return resultKTTS;
            }
            //map 2 kho ben KTTS vs Logistics
            MapStockDTO mapStockDTO = new MapStockDTO();
            mapStockDTO.setStockCode(changeOrder.getStockCode());
            mapStockDTO.setType("1");
            List<MapStockDTO> lstMapStockDTOs = WSMapStock.getListMapStockDTO(mapStockDTO, 0,
                    Integer.MAX_VALUE, "", "");
            if (!DataUtil.isListNullOrEmpty(lstMapStockDTOs)) {
                changeOrder.setStockCode(lstMapStockDTOs.get(0).getLogStockCode());
            } else {
                contentError.append(BundelUtils.getkey("notifi.stock.notsearch"));
                resultKTTS.setStatus(ParamUtils.SYNC.FAIL);
                resultKTTS.setReason(contentError.toString());
                insertSynLog(synLogDTO, ParamUtils.SYNC.FAIL, BundelUtils.getkey("notifi.stock.notsearch"));
                return resultKTTS;
            }
            //tim kiem thong tin khach hang
            if (!DataUtil.isStringNullOrEmpty(changeOrder.getCustCode())) {
                lstConditionBeanSearch.clear();
                lstConditionBeanSearch.add(new ConditionBean("status", ParamUtils.NAME_EQUAL, "0", ParamUtils.TYPE_STRING));
                lstConditionBeanSearch.add(new ConditionBean("code", ParamUtils.NAME_EQUAL, changeOrder.getCustCode(), ParamUtils.TYPE_STRING));
                List<CustomerDTO> lstTempCus = WSCustomer.getListCustomerByCondition(lstConditionBeanSearch, 0, Integer.MAX_VALUE, "", "");
                if (!DataUtil.isListNullOrEmpty(lstTempCus)) {
                    custId = lstTempCus.get(0).getCustId();
                    custName = lstTempCus.get(0).getName();
                    changeOrder.setCustId(custId);
                    changeOrder.setCustName(custName);
                }
            }
            //kiem tra danh sach hang hoa dau vao
            if (DataUtil.isListNullOrEmpty(lstChangeGoods)) {
                resultKTTS.setStatus(ParamUtils.SYNC.FAIL);
                resultKTTS.setReason(BundelUtils.getkey("infor.listGoods.zero"));
                insertSynLog(synLogDTO, ParamUtils.SYNC.FAIL, BundelUtils.getkey("infor.listGoods.zero"));
                return resultKTTS;
            }
            for (ChangeGoods change : lstChangeGoods) {
                if (DataUtil.isStringNullOrEmpty(change.getOldGoodsCode())) {//kiem tra ma hang hoa
                    System.out.println("Thieu thong tin ma hang hoa");
                    addInforErrorForList(lstErrList, change, "infor.goodscode.zero");
                    continue;
                }
                if (DataUtil.isStringNullOrEmpty(change.getOldState())) {//kiem tra trang thai
                    System.out.println("Thieu thong tin trang thai");
                    addInforErrorForList(lstErrList, change, "infor.goodsstate.zero");
                    continue;
                } else {
                    if (!lstGoodsState.contains(change.getOldState())) {
                        System.out.println("Sai thong tin tran thai");
                        addInforErrorForList(lstErrList, change, "infor.goodsstate.error");
                        continue;
                    }
                }
                if (DataUtil.isStringNullOrEmpty(change.getAmount())) {//kiem tra so luong
                    System.out.println("Thieu thong tin so luong");
                    addInforErrorForList(lstErrList, change, "infor.amount.zero");
                    continue;
                } else {
                    change.setAmount(DataUtil.getQuantity(change.getAmount()));
                    if ("0".equals(change.getAmount())) {//neu amount = 0 thi bo qua hang hoa(do hang hoa khong thay doi)
                        if (!lstGoodsCode.contains(change.getOldGoodsCode())) {
                            lstGoodsCode.add(change.getOldGoodsCode());
                        }
                        if (!DataUtil.isStringNullOrEmpty(change.getNewGoodsCode())) {
                            if (!lstGoodsCode.contains(change.getNewGoodsCode())) {
                                lstGoodsCode.add(change.getNewGoodsCode());
                            }
                        }
                        continue;
                    }
                }
                if (!DataUtil.isStringNullOrEmpty(change.getChangeType())) {
                    if (change.getChangeType().equals("1")) {//dieu chinh trang thai
                        if (DataUtil.isStringNullOrEmpty(change.getNewState())) {//kiem tra trang thai hang hoa
                            addInforErrorForList(lstErrList, change, "infor.goodsstate.zero");
                            System.out.println("Thieu thong tin trang thai moi");
                            continue;
                        }
                        Double amount = Double.parseDouble(change.getAmount());
                        if (amount < 0) {//neu amount nho hon 0 thi hang hoa chuyen trang thai tu hong => tot
                            amount = amount * (-1);
                            change.setAmount(DataUtil.getQuantity(amount.toString()));
                            String oldState = change.getOldState();
                            String newState = change.getNewState();
                            change.setOldState(newState);
                            change.setNewState(oldState);
                        }
                    } else if (change.getChangeType().equals("2")) {//dieu chinh serial_1 =>serial_2
                        if (!DataUtil.isStringNullOrEmpty(change.getOldFromSerial())
                                && !DataUtil.isStringNullOrEmpty(change.getOldToSerial())
                                && !DataUtil.isStringNullOrEmpty(change.getNewFromSerial())
                                && !DataUtil.isStringNullOrEmpty(change.getNewToSerial())) {
                            //Kiem tra do dai serial neu >19 thi cat do kieu Long chi co do dai toi da 19
                            Long oldAmount = amountInSerial(change.getOldFromSerial(), change.getOldToSerial());
                            Long newAmount = amountInSerial(change.getNewFromSerial(), change.getNewToSerial());
                            Long amount = Long.parseLong(DataUtil.getQuantity(change.getAmount()));
                            if (!amount.equals(oldAmount) || !amount.equals(newAmount)) {
                                addInforErrorForList(lstErrList, change, "infor.amountEqualSerial.zero");
                                System.out.println("Sai thong tin amount != oldAmount != newAmuont");
                                continue;
                            }
                        } else {
                            addInforErrorForList(lstErrList, change, "infor.serial.zero");
                            System.out.println("Thieu thong tin serial");
                            continue;
                        }
                    } else if (change.getChangeType().equals("3")) {//dieu chinh ma hang hoa
                        if (DataUtil.isStringNullOrEmpty(change.getNewGoodsCode())) {
                            addInforErrorForList(lstErrList, change, "infor.goodscode.zero");
                            System.out.println("Thieu thong tin ma hang hoa moi");
                            continue;
                        }
                        if (!lstGoodsCode.contains(change.getNewGoodsCode())) {
                            lstGoodsCode.add(change.getNewGoodsCode());
                        }
                    } else if (change.getChangeType().equals("4")) {//dieu chinh ca ma hang hoa va trang thai
                        if (DataUtil.isStringNullOrEmpty(change.getNewState())) {
                            addInforErrorForList(lstErrList, change, "infor.goodsstate.zero");
                            System.out.println("Thieu thong tin trang thai moi");
                            continue;
                        }
                        if (DataUtil.isStringNullOrEmpty(change.getNewGoodsCode())) {
                            addInforErrorForList(lstErrList, change, "infor.goodscode.zero");
                            System.out.println("Thieu thong tin ma hang hoa moi");
                            continue;
                        }
                        if (!lstGoodsCode.contains(change.getNewGoodsCode())) {
                            lstGoodsCode.add(change.getNewGoodsCode());
                        }
                    } else {//ma dieu chuyen khong dung
                        System.out.println("Ma dieu chuyen khong dung");
                        addInforErrorForList(lstErrList, change, "infor.changeType.error");
                        continue;
                    }
                } else {//thieu thong tin dieu chuyen
                    addInforErrorForList(lstErrList, change, "infor.changeType.zero");
                    System.out.println("Thieu thong tin loai dieu chuyen");
                    continue;
                }
                if (!lstGoodsCode.contains(change.getOldGoodsCode())) {
                    lstGoodsCode.add(change.getOldGoodsCode());
                }
                lstChangeGoodsFilter.add(change);
            }
            if (!DataUtil.isListNullOrEmpty(lstErrList)) {
                StringBuilder messReason = new StringBuilder();
                for (ChangeGoods goodsError : lstErrList) {
                    if (!DataUtil.isStringNullOrEmpty(goodsError.getOldGoodsName())) {
                        messReason.append(BundelUtils.getkey("change.goodsCode"));
                        messReason.append(" ");
                        messReason.append(goodsError.getOldGoodsCode());
                        messReason.append(": ");
                        messReason.append(goodsError.getInforError());
                        messReason.append("; ");
                    }
                }
                if (DataUtil.isStringNullOrEmpty(messReason)) {
                    messReason.append(BundelUtils.getkey("infor.goodscode.zero"));
                }
                //resultKTTS.setLstChangeGoods(lstErrList);
                resultKTTS.setStatus(ParamUtils.SYNC.FAIL);
                resultKTTS.setReason(BundelUtils.getkey("infor.inforError") + ". " + messReason.toString());
                insertSynLog(synLogDTO, ParamUtils.SYNC.FAIL,
                        BundelUtils.getkey("infor.inforError") + messReason.toString());
                return resultKTTS;
            }
            //lay danh sach hang hoa va dua vao map
            lstGoodsDTO = getListGoods(lstGoodsCode, custId);
            mapGoodsDTO = putGoodsToMap(lstGoodsDTO);
            mapGoodsDTOById = putGoodsToMapById(lstGoodsDTO);
            //map danh sach kho
            if (!DataUtil.isStringNullOrEmpty(changeOrder.getStockCode())) {
                lstConditionBean.clear();
                lstConditionBean.add(new ConditionBean("code", ParamUtils.OP_IN, changeOrder.getStockCode(), ParamUtils.TYPE_STRING));
                List<StockDTO> lstTemp = stockBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "", "code");
                if (!DataUtil.isListNullOrEmpty(lstTemp)) {
                    stockDTOInfor = (StockDTO) DataUtil.cloneObject(lstTemp.get(0));
                    changeOrder.setStockId(stockDTOInfor.getStockId());
                    changeOrder.setStockName(stockDTOInfor.getName());
                    //
                    StaffStockDTO staffStockDTO = new StaffStockDTO();
                    staffStockDTO.setStatus("1");
                    staffStockDTO.setStockId(stockDTOInfor.getStockId());
                    List<StaffStockDTO> lstStaffStockDTO = WSStaffStock.getListStaffStockDTO(staffStockDTO, 0, Integer.MAX_VALUE, "", "id");
                    //loc lay danh sach hang hoa theo kho
                    fillterMapStaffGoodsByStock(lstStaffStockDTO);
                }
            }
            //tao giao dich, chi tiet giao dich, chi tiet serial giao dich
            boolean isError = initGoodsTransferTrans(lstChangeGoodsFilter, mapGoodsDTO, changeOrder);
            if (!isError) {
                StringBuilder messReason = new StringBuilder();
                for (ChangeGoods goodsError : lstChangeGoodsError) {
                    messReason.append(BundelUtils.getkey("change.goodsCode"));
                    messReason.append(" ");
                    messReason.append(goodsError.getOldGoodsCode());
                    messReason.append(": ");
                    messReason.append(goodsError.getInforError());
                    messReason.append("; ");
                }
                resultKTTS.setStatus(ParamUtils.SYNC.FAIL);
                resultKTTS.setReason(BundelUtils.getkey("infor.inforError") + ". " + messReason);
                insertSynLog(synLogDTO, ParamUtils.SYNC.FAIL, BundelUtils.getkey("infor.inforError") + ". " + messReason);
                return resultKTTS;
            }
            //
            ResultDTO result = new ResultDTO();
            /*
             Map giua hang da xuat -> danh sach stock_goods cua hang do
             */
            if (!DataUtil.isListNullOrEmpty(lstTransExportDetail)) {
                result = stockExportService.goodsTransferSynsKTTS(transExport, transImport,
                        lstTransExportDetail, lstTransImportDetail,
                        lstTransExportSerial, lstTransImportSerial,
                        changeOrder, lstChangeGoods, mapGoodsDTO, lstMapStaffGoodsDTOs);
                if (!result.getMessage().equals(ParamUtils.SUCCESS)) {
                    StringBuilder sb1 = new StringBuilder();
                    String sb = "";
                    String addInfor = "";
                    if ("NotEnoughAmount".equals(result.getKey())) {
                        BundelUtils.getkey("NotEnoughAmount");
                        try {
                            if (!DataUtil.isStringNullOrEmpty(result.getFromSerial())
                                    && !DataUtil.isStringNullOrEmpty(result.getToSerial())) {
                                sb1.append(BundelUtils.getkey("notitfi.trans.NotEnoughAmount"));
                                sb = sb1.toString();
                                sb = sb.replace("@fromSerial", result.getFromSerial());
                                sb = sb.replace("@toSerial", result.getToSerial());
                            } else {
                                String message = result.getMessage();
                                if (!DataUtil.isStringNullOrEmpty(message)) {
                                    message = message.substring(0, message.indexOf(","));
                                }
                                GoodsDTO gdto = mapGoodsDTOById.get(message);
                                sb = BundelUtils.getkey("notitfi.trans.NotEnoughAmount2");
                                sb = sb.replace("@goodCode", gdto.getCode());
                            }
                        } catch (Exception e) {
                            sb = BundelUtils.getkey("notitfi.trans.NotEnoughAmount3");
                        }
                    } else if ("INSERT_ERROR_HISTORY".equals(result.getKey())) {
                        sb = BundelUtils.getkey("notifi.insert.history");
                        addInfor = BundelUtils.getkey("notifi.addInfor.errorHistory");
                    } else if ("INSERT_ERROR_MESSAGES".equals(result.getKey())) {
                        sb = BundelUtils.getkey("notifi.insert.history");
                        addInfor = BundelUtils.getkey("notifi.addInfor.errorMessage");
                    }
                    resultKTTS.setStatus(ParamUtils.SYNC.FAIL);
                    String mess = BundelUtils.getkey("changeGoods.error.trans")
                            + sb;
                    resultKTTS.setReason(BundelUtils.getkey("changeGoods.error.trans") + ". " + sb);
                    insertSynLog(synLogDTO, ParamUtils.SYNC.FAIL, mess + ". " + addInfor);
                    return resultKTTS;
                }
            } else {
                lstHistoryChangeGoodsDTOs = convertFromHistoryChangeGoods(changeOrder, lstChangeGoods, mapGoodsDTO);
                String messInsert = historyChangeGoodsBusiness.insertList(lstHistoryChangeGoodsDTOs);
                if (!ParamUtils.SUCCESS.equals(messInsert)) {
                    insertSynLog(synLogDTO, ParamUtils.SYNC.SUCCESS, BundelUtils.getkey("notifi.insert.history"));
                    resultKTTS.setStatus(ParamUtils.SYNC.FAIL);
                    return resultKTTS;
                }
                result.setKey(BundelUtils.getkey("notifi.trans.empty"));
            }
            StringBuilder mesSucc = new StringBuilder();
            mesSucc.append(BundelUtils.getkey("changeStockGoods.success"));
            mesSucc.append(result.getKey() == null ? "" : result.getKey());
            insertSynLog(synLogDTO, ParamUtils.SYNC.SUCCESS, mesSucc.toString());
            //-> commit
            resultKTTS.setStatus(ParamUtils.SYNC.SUCCESS);
            return resultKTTS;
        } catch (Exception e) {
            //e.printStackTrace();
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultKTTS.setStatus(ParamUtils.SYNC.FAIL);
            resultKTTS.setReason(BundelUtils.getkey(ParamUtils.SYSTEM_OR_DATA_ERROR));
            insertSynLog(synLogDTO, ParamUtils.SYNC.FAIL, BundelUtils.getkey(ParamUtils.SYSTEM_OR_DATA_ERROR));
            return resultKTTS;
        } finally {
        }
    }

    public static Map<String, GoodsDTO> putGoodsToMap(List<GoodsDTO> lstGoods) {
        Map<String, GoodsDTO> mapGoodsDTO = new HashMap<>();
        if (lstGoods != null) {
            for (GoodsDTO goodsDTO : lstGoods) {
                mapGoodsDTO.put(goodsDTO.getCode(), goodsDTO);
            }
        }
        return mapGoodsDTO;
    }

    public static Map<String, GoodsDTO> putGoodsToMapById(List<GoodsDTO> lstGoods) {
        Map<String, GoodsDTO> mapGoodsDTO = new HashMap<>();
        if (lstGoods != null) {
            for (GoodsDTO goodsDTO : lstGoods) {
                mapGoodsDTO.put(goodsDTO.getGoodsId(), goodsDTO);
            }
        }
        return mapGoodsDTO;
    }

    public static Map<String, StockDTO> putStockToMap(List<StockDTO> lstStocks) {
        Map<String, StockDTO> mapstock = new HashMap<>();
        if (lstStocks != null) {
            for (StockDTO stockDTO : lstStocks) {
                mapstock.put(stockDTO.getCode(), stockDTO);
            }
        }
        return mapstock;
    }

    private void rollback(Session session, Transaction transaction) {
        transaction.rollback();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    private void commit(Session session, Transaction transaction) {
        transaction.commit();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    private Long amountInSerial(String fromSerial, String toSerial) {
        Long fromSerialInt = 0l;
        Long toSerialInt = 0l;
        try {
            if (fromSerial.length() > Constants.SERIAL_LIMIT) {
                fromSerialInt = Long.parseLong(fromSerial.substring(fromSerial.length() - Constants.SERIAL_LIMIT));
            } else {
                fromSerialInt = Long.parseLong(fromSerial);
            }
        } catch (Exception e) {
            fromSerialInt = 0l;
        }
        try {
            if (toSerial.length() > Constants.SERIAL_LIMIT) {
                toSerialInt = Long.parseLong(toSerial.substring(toSerial.length() - Constants.SERIAL_LIMIT));
            } else {
                toSerialInt = Long.parseLong(toSerial);
            }
        } catch (Exception e) {
            toSerialInt = 0l;
        }
        return (toSerialInt - fromSerialInt + 1);
    }

    private void addInforErrorForList(List<ChangeGoods> lstError, ChangeGoods changeGoods, String keyError) {
        changeGoods.setInforError(BundelUtils.getkey(keyError));
        lstError.add(changeGoods);
    }

    public boolean initGoodsTransferTrans(List<ChangeGoods> lstcChangeGoodses,
            Map<String, GoodsDTO> mapGoodsDTO, ChangeOrder changeOrder) {
        clearTransInfo();
        boolean isError = false;
        lstChangeGoodsError.clear();
        //init transaction info
        transImport = initStocktrans("4", changeOrder);
        transExport = initStocktrans("5", changeOrder);
        //init detail and serial
        StockTransDetailDTO oldStockTransDetailDTO;
        StockTransDetailDTO newStockTransDetailDTO;
        StockTransSerialDTO oldStockTransSerialDTO;
        StockTransSerialDTO newStockTransSerialDTO;

        StringBuilder contentError = new StringBuilder();
        for (ChangeGoods changeGoods : lstcChangeGoodses) {
            contentError.setLength(0);
            //LAY RA THONG TIN HANG CU - MOI
            GoodsDTO oldGoodsDTO = mapGoodsDTO.get(changeGoods.getOldGoodsCode());
            GoodsDTO newGoodsDTO;
            if (changeGoods.getChangeType().equals("3") || changeGoods.getChangeType().equals("4")) {
                newGoodsDTO = mapGoodsDTO.get(changeGoods.getNewGoodsCode());
            } else {
                newGoodsDTO = mapGoodsDTO.get(changeGoods.getOldGoodsCode());
            }
            //XAY DUNG DU LIEu
            if (oldGoodsDTO != null && newGoodsDTO != null) {
                if (oldGoodsDTO.getIsSerial().equals(Constants.IS_SERIAL)
                        && newGoodsDTO.getIsSerial().equals(Constants.IS_SERIAL)) {//NEU CUNG LA SERIAL
                    //CHECK: NEU KHAC LOAI SERIAL -> LOI
                    if (!oldGoodsDTO.getIsSerialStrip().equals(newGoodsDTO.getIsSerialStrip())) {
                        contentError.append(BundelUtils.getkey("errormessage.goodstransfer.errorserialtype"));
                        System.out.println("initGoodsTransferTrans: Hang hoa phai cung la hang co serial hoac khong serial");
                        isError = true;
                    }
                    if (DataUtil.isStringNullOrEmpty(changeGoods.getOldFromSerial())
                            || DataUtil.isStringNullOrEmpty(changeGoods.getOldToSerial())) {
                        contentError.append("Thieu thong tin serial");
                        System.out.println("initGoodsTransferTrans: Hang serial thieu thong tin serial");
                        isError = true;
                    }
                    //NEU LA HANG CO SERIAL
                    //-> giao dich xuat -> old
                    oldStockTransDetailDTO = new StockTransDetailDTO();
                    oldStockTransDetailDTO.setGoodsId(oldGoodsDTO.getGoodsId());
                    oldStockTransDetailDTO.setGoodsUnitType(oldGoodsDTO.getUnitType());
                    oldStockTransDetailDTO.setGoodsState(changeGoods.getOldState());
                    oldStockTransDetailDTO.setAmountReal(changeGoods.getAmount());
                    oldStockTransDetailDTO.setAmountOrder(changeGoods.getAmount());
                    oldStockTransDetailDTO.setTmpStockTransDetailId(oldGoodsDTO.getGoodsId() + oldStockTransDetailDTO.getGoodsState());
                    //-> giao dich nhap -> new
                    newStockTransDetailDTO = new StockTransDetailDTO();
                    newStockTransDetailDTO.setGoodsId(newGoodsDTO.getGoodsId());
                    newStockTransDetailDTO.setGoodsUnitType(newGoodsDTO.getUnitType());
                    newStockTransDetailDTO.setGoodsState(changeGoods.getOldState());
                    if (changeGoods.getChangeType().equals("1")
                            || changeGoods.getChangeType().equals("4")) {
                        newStockTransDetailDTO.setGoodsState(changeGoods.getNewState());
                    }
                    newStockTransDetailDTO.setAmountReal(changeGoods.getAmount());
                    newStockTransDetailDTO.setAmountOrder(changeGoods.getAmount());
                    newStockTransDetailDTO.setTmpStockTransDetailId(newGoodsDTO.getGoodsId() + newStockTransDetailDTO.getGoodsState());
                    //set detail
                    //duyot: neu la hang theo dai -> khong gop detail
                    if (oldGoodsDTO.getIsSerialStrip().equals(Constants.IS_SERIAL)) {
                        //NEU LA HANG STRIP -> set laij temp voi them thong tin serial
                        oldStockTransDetailDTO.setTmpStockTransDetailId(oldGoodsDTO.getGoodsId() + oldStockTransDetailDTO.getGoodsState() + changeGoods.getOldFromSerial());
                        newStockTransDetailDTO.setTmpStockTransDetailId(newGoodsDTO.getGoodsId() + newStockTransDetailDTO.getGoodsState() + changeGoods.getOldFromSerial());
                        //
                        setStockTransDetailFroNoSerial(lstTransExportDetail, oldStockTransDetailDTO);
                        setStockTransDetailFroNoSerial(lstTransImportDetail, newStockTransDetailDTO);
                    } else {
                        setStockTransDetail(lstTransExportDetail, oldStockTransDetailDTO);
                        setStockTransDetail(lstTransImportDetail, newStockTransDetailDTO);
                    }
                    //SET CHI TIET SERIAL
                    // OLDs
                    oldStockTransSerialDTO = new StockTransSerialDTO();
                    oldStockTransSerialDTO.setGoodsId(oldGoodsDTO.getGoodsId());
                    oldStockTransSerialDTO.setGoodsUnitType(oldGoodsDTO.getUnitType());
                    oldStockTransSerialDTO.setGoodsState(changeGoods.getOldState());
                    oldStockTransSerialDTO.setFromSerial(changeGoods.getOldFromSerial());
                    oldStockTransSerialDTO.setToSerial(changeGoods.getOldToSerial());
                    oldStockTransSerialDTO.setAmountReal(changeGoods.getAmount());
                    oldStockTransSerialDTO.setAmountOrder(changeGoods.getAmount());
                    oldStockTransSerialDTO.setTmpStockTransDetailId(oldGoodsDTO.getGoodsId() + changeGoods.getOldState());
                    lstTransExportSerial.add(oldStockTransSerialDTO);

                    //NEW
                    newStockTransSerialDTO = new StockTransSerialDTO();
                    newStockTransSerialDTO.setGoodsId(newGoodsDTO.getGoodsId());
                    newStockTransSerialDTO.setGoodsUnitType(newGoodsDTO.getUnitType());
                    newStockTransSerialDTO.setGoodsState(changeGoods.getOldState());
                    if (changeGoods.getChangeType().equals("1")
                            || changeGoods.getChangeType().equals("4")) {
                        newStockTransSerialDTO.setGoodsState(changeGoods.getNewState());
                    }
                    newStockTransSerialDTO.setFromSerial(changeGoods.getOldFromSerial());
                    newStockTransSerialDTO.setToSerial(changeGoods.getOldToSerial());
                    if (changeGoods.getChangeType().equals("3")) {
                        if (!DataUtil.isStringNullOrEmpty(changeGoods.getNewFromSerial())
                                && !DataUtil.isStringNullOrEmpty(changeGoods.getNewToSerial())) {
                            newStockTransSerialDTO.setFromSerial(changeGoods.getNewFromSerial());
                            newStockTransSerialDTO.setToSerial(changeGoods.getNewToSerial());
                            newStockTransSerialDTO.setChangeType("2");
                        }
                    }
                    if (changeGoods.getChangeType().equals("2")) {
                        newStockTransSerialDTO.setFromSerial(changeGoods.getNewFromSerial());
                        newStockTransSerialDTO.setToSerial(changeGoods.getNewToSerial());
                        newStockTransSerialDTO.setChangeType(changeGoods.getChangeType());
                    }
                    newStockTransSerialDTO.setAmountReal(changeGoods.getAmount());
                    newStockTransSerialDTO.setAmountOrder(changeGoods.getAmount());
                    newStockTransSerialDTO.setTmpStockTransDetailId(newGoodsDTO.getGoodsId() + changeGoods.getOldState());
                    if (changeGoods.getChangeType().equals("1")
                            || changeGoods.getChangeType().equals("4")) {
                        newStockTransSerialDTO.setTmpStockTransDetailId(newGoodsDTO.getGoodsId() + changeGoods.getNewState());
                    }
                    newStockTransSerialDTO.setTmpChangeStockGoodsFromSerial(changeGoods.getOldFromSerial());
                    newStockTransSerialDTO.setTmpChangeStockGoodsToSerial(changeGoods.getOldToSerial());
                    lstTransImportSerial.add(newStockTransSerialDTO);

                    //duyot: neu la hang theo dai -> khong gop detail -> ser detail chuan cho hang serial
                    if (oldGoodsDTO.getIsSerialStrip().equals(Constants.IS_SERIAL)) {
                        //NEU LA HANG STRIP -> set laij temp voi them thong tin serial
                        oldStockTransSerialDTO.setTmpStockTransDetailId(oldGoodsDTO.getGoodsId() + oldStockTransDetailDTO.getGoodsState() + changeGoods.getOldFromSerial());
                        newStockTransSerialDTO.setTmpStockTransDetailId(newGoodsDTO.getGoodsId() + newStockTransDetailDTO.getGoodsState() + changeGoods.getOldFromSerial());
                        //
                    }
                } else {//NEU LA HANG K SERIAL
                    //kiem tra xem co 1 trong 2 thang la serial hay khong
                    if (oldGoodsDTO.getIsSerial().equalsIgnoreCase(Constants.IS_SERIAL) || newGoodsDTO.getIsSerial().equalsIgnoreCase(Constants.IS_SERIAL)) {
                        contentError.append(BundelUtils.getkey("errormessage.goodstransfer.errornoserial"));
                        System.out.println("initGoodsTransferTrans: Hang serial thieu thong tin serial");
                        isError = true;
                    }
                    //Set Chi tiet giao dich khong co serial
                    //SET TEMP FOR TRANS DETAIL
                    StringBuilder strTemp = new StringBuilder(oldGoodsDTO.getGoodsId());
                    strTemp.append(changeGoods.getOldState());

                    //xuat 
                    oldStockTransDetailDTO = new StockTransDetailDTO();
                    oldStockTransDetailDTO.setGoodsId(oldGoodsDTO.getGoodsId());
                    oldStockTransDetailDTO.setGoodsUnitType(oldGoodsDTO.getUnitType());
                    oldStockTransDetailDTO.setGoodsState(changeGoods.getOldState());
                    oldStockTransDetailDTO.setAmountReal(changeGoods.getAmount());
                    oldStockTransDetailDTO.setAmountOrder(changeGoods.getAmount());
                    oldStockTransDetailDTO.setTmpStockTransDetailId(strTemp.toString());
                    //15/08: doi voi hang no serial: giu nguyen detail, khong gom nhom
                    setStockTransDetailFroNoSerial(lstTransExportDetail, oldStockTransDetailDTO);
                    //nhap
                    //SET TEMP FOR TRANS DETAIL
                    StringBuilder strTempNew = new StringBuilder(newGoodsDTO.getGoodsId());
                    strTempNew.append(changeGoods.getNewState());

                    newStockTransDetailDTO = new StockTransDetailDTO();
                    newStockTransDetailDTO.setGoodsId(newGoodsDTO.getGoodsId());
                    newStockTransDetailDTO.setGoodsUnitType(newGoodsDTO.getUnitType());
                    newStockTransDetailDTO.setGoodsState(changeGoods.getOldState());
                    if (changeGoods.getChangeType().equals("1")
                            || changeGoods.getChangeType().equals("4")) {
                        newStockTransDetailDTO.setGoodsState(changeGoods.getNewState());
                    }
                    newStockTransDetailDTO.setAmountReal(changeGoods.getAmount());
                    newStockTransDetailDTO.setAmountOrder(changeGoods.getAmount());
                    newStockTransDetailDTO.setTmpStockTransDetailId(strTempNew.toString());
                    setStockTransDetailFroNoSerial(lstTransImportDetail, newStockTransDetailDTO);
                }
                changeGoods.setInforError(contentError.toString());
                if (!DataUtil.isStringNullOrEmpty(contentError)) {
                    lstChangeGoodsError.add(changeGoods);
                }
            } else {
                contentError.append(BundelUtils.getkey("infor.goodscode.zero"));
                System.out.println("initGoodsTransferTrans: Ma hang hoa khong ton tai trong kho");
                changeGoods.setInforError(contentError.toString());
                if (!DataUtil.isStringNullOrEmpty(contentError)) {
                    lstChangeGoodsError.add(changeGoods);
                }
                isError = true;
            }
        }
        return !isError;
    }

    private StockTransDTO initStocktrans(String type, ChangeOrder changeOrder) {
        StockTransDTO stockTransDTO = new StockTransDTO();
        stockTransDTO.setOrderCode(changeOrder.getOrderCode());
        //stockTransDTO.setTransUserId(staffInforDTO.getStaffId());
        //stockTransDTO.setDeptId(staffInforDTO.getDeptId());
        stockTransDTO.setCustId(changeOrder.getCustId());
        stockTransDTO.setCustName(changeOrder.getCustName());
        stockTransDTO.setCustCode(changeOrder.getCustCode());
        stockTransDTO.setOwnerId(changeOrder.getStockId());
        stockTransDTO.setOwnerType("1");
        stockTransDTO.setStockTransStatus(ParamUtils.TRANS_STATUS.IMPORTED);
        stockTransDTO.setStockTransType(type);
        stockTransDTO.setTransUserName(changeOrder.getOrderUserName());
        stockTransDTO.setOrderDate(changeOrder.getExpectedDate());
        //THONG TIN GHI CHU
        stockTransDTO.setNotes(changeOrder.getDescription());

        return stockTransDTO;
    }

    private void setStockTransDetailFroNoSerial(List<StockTransDetailDTO> lstStockTransDetail, StockTransDetailDTO stockTransDetailDTO) {
        lstStockTransDetail.add(stockTransDetailDTO);
    }

    //SET STOCK_TRANS_DETAIL FOR SERIAL: GOM NHOM DETAIL
    private void setStockTransDetail(List<StockTransDetailDTO> lstStockTransDetail, StockTransDetailDTO stockTransDetailDTO) {
        Long amount;
        boolean check = true;
        if (stockTransDetailDTO == null) {
            return;
        }
        if (!DataUtil.isListNullOrEmpty(lstStockTransDetail)) {
            for (StockTransDetailDTO stdDTO : lstStockTransDetail) {
                if (stdDTO.getTmpStockTransDetailId().equals(stockTransDetailDTO.getTmpStockTransDetailId())) {
                    amount = Long.parseLong(stdDTO.getAmountReal()) + Long.parseLong(stockTransDetailDTO.getAmountReal());
                    stdDTO.setAmountOrder(amount.toString());
                    stdDTO.setAmountReal(amount.toString());
                    check = false;
                }
            }
            if (check) {
                lstStockTransDetail.add(stockTransDetailDTO);
            }
        } else {
            lstStockTransDetail.add(stockTransDetailDTO);
        }
    }

    private void clearTransInfo() {
        lstTransExportDetail.clear();
        lstTransImportDetail.clear();
        lstTransImportSerial.clear();
        lstTransExportSerial.clear();
    }

    private void insertSynLog(SynLogDTO synLogDTO, String status, String description) {
        synLogDTO.setStatus(status);
        synLogDTO.setDescription(description);
        synLogDTO.setDateTime(stockGoodsTotalBusiness.getSysDate(formatDate));
        WSSynLog.insertSynLog(synLogDTO);
    }

    private List<GoodsDTO> getListGoods(List<String> lstGoodsCode, String custId) {
        lstMapStaffGoodsDTOs.clear();
        StringBuilder goodsCode = new StringBuilder();
        List<GoodsDTO> lstGoodsDTO = new ArrayList();
        List<ConditionBean> lstConditionBean = new ArrayList();
        String codeList = "";
        int numberLoop = 0;
        if (lstGoodsCode.size() % 1000 == 0) {
            numberLoop = lstGoodsCode.size() / 1000;
        } else {
            numberLoop = lstGoodsCode.size() / 1000 + 1;
        }
        for (int i = 0; i < numberLoop; i++) {
            if (i == numberLoop - 1) {
                goodsCode.setLength(0);
                for (int j = 1000 * i; j < lstGoodsCode.size(); j++) {
                    goodsCode.append(",");
                    goodsCode.append(lstGoodsCode.get(j));
                }
                codeList = goodsCode.toString().replaceFirst(",", "");
                lstConditionBean.clear();
                //custId
                if (!DataUtil.isStringNullOrEmpty(custId)) {
                    lstConditionBean.add(new ConditionBean("custId", ParamUtils.OP_EQUAL, custId, ParamUtils.TYPE_NUMBER));
                }
                lstConditionBean.add(new ConditionBean("status", ParamUtils.OP_EQUAL, ParamUtils.GOODS_IMPORT_STATUS.IMPORTED, ParamUtils.TYPE_STRING));
                lstConditionBean.add(new ConditionBean("code", ParamUtils.OP_IN, codeList, ParamUtils.TYPE_STRING));
                List<GoodsDTO> lstTemp = goodsBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "", "code");
                if (!DataUtil.isListNullOrEmpty(lstTemp)) {
                    lstGoodsDTO.addAll(lstTemp);
                }
                //lay danh sach nhan vien theo hang hoa
                List<MapStaffGoodsDTO> lstMapTemp = mapStaffGoodsBusinessInterface.getListStaffByGoods(codeList, custId);
                if (!DataUtil.isListNullOrEmpty(lstMapTemp)) {
                    lstMapStaffGoodsDTOs.addAll(lstMapTemp);
                }
            } else {
                goodsCode.setLength(0);
                for (int j = 1000 * i; j < 1000 * (i + 1); j++) {
                    goodsCode.append(",");
                    goodsCode.append(lstGoodsCode.get(j));
                }
                codeList = goodsCode.toString().replaceFirst(",", "");
                lstConditionBean.clear();
                if (!DataUtil.isStringNullOrEmpty(custId)) {
                    lstConditionBean.add(new ConditionBean("custId", ParamUtils.OP_EQUAL, custId, ParamUtils.TYPE_NUMBER));
                }
                lstConditionBean.add(new ConditionBean("status", ParamUtils.OP_EQUAL, ParamUtils.GOODS_IMPORT_STATUS.IMPORTED, ParamUtils.TYPE_STRING));
                lstConditionBean.add(new ConditionBean("code", ParamUtils.OP_IN, codeList, ParamUtils.TYPE_STRING));
                List<GoodsDTO> lstTemp = goodsBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "", "code");
                if (!DataUtil.isListNullOrEmpty(lstTemp)) {
                    lstGoodsDTO.addAll(lstTemp);
                }
                //lay danh sach nhan vien theo hang hoa
                List<MapStaffGoodsDTO> lstMapTemp = mapStaffGoodsBusinessInterface.getListStaffByGoods(codeList, custId);
                if (!DataUtil.isListNullOrEmpty(lstMapTemp)) {
                    lstMapStaffGoodsDTOs.addAll(lstMapTemp);
                }
            }
        }
        return lstGoodsDTO;
    }

    /**
     * Ham kiem tra xem ma yeu cau da ton tai chua
     *
     * @param orderCode
     * @return
     */
    private boolean checkOrderCodeInTrans(String orderCode) {
        List<ConditionBean> lstConditionBean = new ArrayList();
        lstConditionBean.add(new ConditionBean("stockTransType", ParamUtils.OP_EQUAL, "4", ParamUtils.TYPE_STRING));
        lstConditionBean.add(new ConditionBean("stockTransStatus", ParamUtils.OP_EQUAL, "1", ParamUtils.TYPE_STRING));
        lstConditionBean.add(new ConditionBean("orderCode", ParamUtils.OP_EQUAL, orderCode, ParamUtils.TYPE_STRING));
        List<StockTransDTO> lstTemp = stockTransBusiness.searchByConditionBean(lstConditionBean, 0, Integer.MAX_VALUE, "", "stockTransCode");
        if (DataUtil.isListNullOrEmpty(lstTemp)) {
            return true;
        }
        return false;
    }

    /**
     * thienng1 Ham convert sang HistoryChangeGoods
     */
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

    private void fillterMapStaffGoodsByStock(List<StaffStockDTO> lstStaffStockDTOs) {
        List<MapStaffGoodsDTO> lstMapStaffGoodsDTOsRemove = new ArrayList<>();
        boolean flag = false;
        for (MapStaffGoodsDTO mapStaffGoods : lstMapStaffGoodsDTOs) {
            flag = false;
            for (StaffStockDTO staffStockDTO : lstStaffStockDTOs) {
                if (mapStaffGoods.getStaffId().equals(staffStockDTO.getStaffId())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                lstMapStaffGoodsDTOsRemove.add(mapStaffGoods);
            }
        }
        lstMapStaffGoodsDTOs.removeAll(lstMapStaffGoodsDTOsRemove);
    }

}
