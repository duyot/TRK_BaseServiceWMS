/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.business.service.StockGoodsSerialServiceInterface;
import com.viettel.logistic.wms.business.service.StockGoodsSerialStripServiceInterface;
import com.viettel.logistic.wms.dto.GoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logstic.wms.webservice.dto.OrdersDTO;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 13-Apr-15 7:17 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.StockGoodsSerialService")
public class StockGoodsSerialServiceImpl implements StockGoodsSerialService {

    @Autowired
    StockGoodsSerialServiceInterface stockGoodsSerialBusiness;

    @Autowired
    StockGoodsSerialStripServiceInterface stockGoodsSerialStripBusiness2;

    @Autowired
    BaseFWServiceInterface stockGoodsSerialStripBusiness;

    @Autowired
    HibernateSessionFactoryImpl sessionFactory;

    @Override
    public String updateStockGoodsSerial(StockGoodsSerialDTO stockGoodsSerialDTO) {
        return stockGoodsSerialBusiness.update(stockGoodsSerialDTO);
    }

    @Override
    public String deleteStockGoodsSerial(Long id) {
        return stockGoodsSerialBusiness.delete(id);
    }

    @Override
    public String deleteListStockGoodsSerial(List<StockGoodsSerialDTO> stockGoodsSerialListDTO) {
        return stockGoodsSerialBusiness.delete(stockGoodsSerialListDTO);
    }

    @Override
    public StockGoodsSerialDTO findStockGoodsSerialById(Long id) {
        if (id != null && id > 0) {
            return (StockGoodsSerialDTO) stockGoodsSerialBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<StockGoodsSerialDTO> getListStockGoodsSerialDTO(StockGoodsSerialDTO stockGoodsSerialDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (stockGoodsSerialDTO != null) {
            return stockGoodsSerialBusiness.search(stockGoodsSerialDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertStockGoodsSerial(StockGoodsSerialDTO stockGoodsSerialDTO) {
        return stockGoodsSerialBusiness.createObject(stockGoodsSerialDTO);
    }

    //
    @Override
    public String insertOrUpdateListStockGoodsSerial(List<StockGoodsSerialDTO> stockGoodsSerialDTO) {
        return stockGoodsSerialBusiness.insertList(stockGoodsSerialDTO);
    }

    @Override
    public List<String> getSequense(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return stockGoodsSerialBusiness.getListSequense(seqName, number);
    }

    //Tim kiem va ghep dai serial
    @Override
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerial(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        return stockGoodsSerialStripBusiness2.getListStockGoodsSerial(stockGoodsSerialStripDTO);
    }

    @Override
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialStrip(List<ConditionBean> lstCondition, int startRow, int maxRow, String sortType, String sortField) {
        List<StockGoodsSerialStripDTO> lstStockGoodsSerialStripDTO = new ArrayList<>();
        StockGoodsSerialStripDTO stockGoodsSerialStripDTOSearch = new StockGoodsSerialStripDTO();
        for (ConditionBean condition : lstCondition) {
            if (condition.getField().equalsIgnoreCase("fromSerial") && !StringUtils.isNullOrEmpty(condition.getValue())) {
                stockGoodsSerialStripDTOSearch.setFromSerial(condition.getValue());
            }
            //
            if (condition.getField().equalsIgnoreCase("toSerial") && !StringUtils.isNullOrEmpty(condition.getValue())) {
                stockGoodsSerialStripDTOSearch.setToSerial(condition.getValue());
            }
            //custId
            if (condition.getField().equalsIgnoreCase("custId") && !StringUtils.isNullOrEmpty(condition.getValue())) {
                stockGoodsSerialStripDTOSearch.setCustId(condition.getValue());
            }
            //goodsType
            if (condition.getField().equalsIgnoreCase("goodsType") && !StringUtils.isNullOrEmpty(condition.getValue())) {
                stockGoodsSerialStripDTOSearch.setGoodsType(condition.getValue());
            }
            //goodsId
            if (condition.getField().equalsIgnoreCase("goodsId") && !StringUtils.isNullOrEmpty(condition.getValue())) {
                stockGoodsSerialStripDTOSearch.setGoodsId(condition.getValue());
            }
            //ownerId
            if (condition.getField().equalsIgnoreCase("ownerId") && !StringUtils.isNullOrEmpty(condition.getValue())) {
                stockGoodsSerialStripDTOSearch.setOwnerId(condition.getValue());
            }
            //status
            if (condition.getField().equalsIgnoreCase("status") && !StringUtils.isNullOrEmpty(condition.getValue())) {
                stockGoodsSerialStripDTOSearch.setStatus(condition.getValue());
            }
            //status
            if (condition.getField().equalsIgnoreCase("goodsState") && !StringUtils.isNullOrEmpty(condition.getValue())) {
                stockGoodsSerialStripDTOSearch.setGoodsState(condition.getValue());
            }
        }

        List<StockGoodsSerialStripDTO> lstFilterStockGoodsSerialStripDTO = new ArrayList<>();
//        for (ConditionBean con : lstCondition) {
//            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
//                con.setField(StringUtils.formatFunction("trunc", con.getField()));
//            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
//                con.setType(ParamUtils.TYPE_NUMBER);
//            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER_DOUBLE)) {
//                con.setType(ParamUtils.NUMBER_DOUBLE);
//            } else {
//                String value = "";
//                if (con.getOperator().equalsIgnoreCase(ParamUtils.NAME_LIKE)) {
//                    value = StringUtils.formatLike(con.getValue());
//                    con.setField(StringUtils.formatFunction("lower", con.getField()));
//                    con.setValue(value.toLowerCase());
//                } else {
//                    value = con.getValue();
//                    con.setField(con.getField());
//                    con.setValue(value);
//                }
//            }
//            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));
//        }
        String fromSerial = "";
        String toSerial = "";
        Long quantity = 0L;
        Long quantityTotal = 0L;
//        ConditionBean removeCondition = new ConditionBean();
//        //Lay gia tri from_serial, to_serial truyen vao
//        for (ConditionBean condition : lstCondition) {
//            if (condition.getField().equalsIgnoreCase("fromSerial") && !StringUtils.isNullOrEmpty(condition.getValue())) {
//                toSerial = condition.getValue();
//            }
//            //
//            if (condition.getField().equalsIgnoreCase("toSerial") && !StringUtils.isNullOrEmpty(condition.getValue())) {
//                fromSerial = condition.getValue();
//            }
//            //
//            if (condition.getField().equalsIgnoreCase("quantity") && !StringUtils.isNullOrEmpty(condition.getValue())) {
//                quantity = Long.parseLong(condition.getValue());
//                removeCondition = condition;
//            }
//        }
//        lstCondition.remove(removeCondition);
        //Tim kiem serial
        //lstStockGoodsSerialStripDTO = stockGoodsSerialStripBusiness.searchByConditionBean(lstCondition, startRow, maxRow, sortType, sortField);
//        lstStockGoodsSerialStripDTO = stockGoodsSerialStripBusiness.searchByConditionBean(lstCondition, startRow, maxRow, "", "goodsId, custId,ownerId,ownerType,goodsId,goodsState,status,fromSerial");
        lstStockGoodsSerialStripDTO = stockGoodsSerialStripBusiness2.getListStockGoodsSerialStripExacly(stockGoodsSerialStripDTOSearch);
        StockGoodsSerialStripDTO firstStockGoodsSerialStripDTO;
        StockGoodsSerialStripDTO lastStockGoodsSerialStripDTO;
        //        
        //
        //Neu khong truyen tham so from serial,to serial,quantity thi lay tat ca
        if (quantity.equals(0L) && StringUtils.isNullOrEmpty(fromSerial) && StringUtils.isNullOrEmpty(toSerial)) {
            return lstStockGoodsSerialStripDTO;
        }
        //Lay dai dai serial theo so luong
        int iIndex = 0;
        StockGoodsSerialStripDTO stockGoodsSerialStripDTO;
        if (lstStockGoodsSerialStripDTO != null && lstStockGoodsSerialStripDTO.size() > 0) {
            //Tim kiem theo so luong
            if (!quantity.equals(0L)) {
                //ChuDV
                // while (quantityTotal <= quantity && iIndex < lstStockGoodsSerialStripDTO.size()) {
                // stockGoodsSerialStripDTO = lstStockGoodsSerialStripDTO.get(iIndex);
                // quantityTotal = quantityTotal + Long.parseLong(stockGoodsSerialStripDTO.getQuantity());
                // iIndex++;
                // lstFilterStockGoodsSerialStripDTO.add(stockGoodsSerialStripDTO);
                //}
                //
                //ThienNG1 edit by 10.06.2016 Begin
                while (quantityTotal < quantity && iIndex < lstStockGoodsSerialStripDTO.size()) {
                    stockGoodsSerialStripDTO = lstStockGoodsSerialStripDTO.get(iIndex);
                    quantityTotal = quantityTotal + Long.parseLong(stockGoodsSerialStripDTO.getQuantity());
                    iIndex++;
                    lstFilterStockGoodsSerialStripDTO.add(stockGoodsSerialStripDTO);
                }
                //ThienNG1 edit by 10.06.2016 End
            } else {//Khong theo so luong
                lstFilterStockGoodsSerialStripDTO = lstStockGoodsSerialStripDTO;
            }
        }
        //Xu ly dai serial dau tien va cuoi cung theo tham so, from serial va to_serial truyen vao         
        int iSize = lstFilterStockGoodsSerialStripDTO.size();
        Long firstQuantity;
        Long lastQuantity;
        int lengthSerial = 0;
        String firstToSerial = "";
        //ThienNG addBy 01.06.2015
        String lastToSerial = "";
        //

        if (lstFilterStockGoodsSerialStripDTO != null && lstFilterStockGoodsSerialStripDTO.size() > 0) {
            //Xu ly dai serial dau tien            
            firstStockGoodsSerialStripDTO = lstFilterStockGoodsSerialStripDTO.get(0);
            lengthSerial = firstStockGoodsSerialStripDTO.getFromSerial().length();
            if (!StringUtils.isNullOrEmpty(fromSerial) && Long.parseLong(fromSerial) > Long.parseLong(firstStockGoodsSerialStripDTO.getFromSerial())) {
                firstStockGoodsSerialStripDTO.setFromSerial(fromSerial);
            }
            if (!StringUtils.isNullOrEmpty(toSerial) && Double.parseDouble(toSerial) < Double.parseDouble(firstStockGoodsSerialStripDTO.getToSerial())) {
                firstStockGoodsSerialStripDTO.setToSerial(toSerial);
            }
            //Cap nhat lai so luong
            firstQuantity = Long.parseLong(firstStockGoodsSerialStripDTO.getToSerial()) - Long.parseLong(firstStockGoodsSerialStripDTO.getFromSerial()) + 1;
            //Chan du lieu tim theo so luong
            //ThienNG1 EditBy 18/06/2015 
            //them dieu kien khi khong tim kiem theo so luong (quantity = 0L)
            if (!quantity.equals(0L)) {
                if (firstQuantity > quantity) {
                    firstQuantity = quantity;
                    firstToSerial = Long.parseLong(firstStockGoodsSerialStripDTO.getFromSerial()) + quantity - 1 + "";
                    firstStockGoodsSerialStripDTO.setToSerial(DataUtil.lPad(firstToSerial, "0", lengthSerial));
                }
            }
            //End
            firstStockGoodsSerialStripDTO.setQuantity(firstQuantity.toString());
            //ThienNG1- Begin EditBy 18/06/2015
            //update so luong khi khong tim kiem theo so luong
            if (quantity.equals(0L)) {
                Long amount = Long.parseLong(firstStockGoodsSerialStripDTO.getToSerial()) - Long.parseLong(firstStockGoodsSerialStripDTO.getFromSerial()) + 1;
                firstStockGoodsSerialStripDTO.setQuantity(amount.toString());
            }
            //End
            //
            lstFilterStockGoodsSerialStripDTO.set(0, firstStockGoodsSerialStripDTO);
            //Xu ly dai serial cuoi cung
            if (iSize > 1) {
                lastStockGoodsSerialStripDTO = lstFilterStockGoodsSerialStripDTO.get(iSize - 1);
                if (!StringUtils.isNullOrEmpty(toSerial) && Double.parseDouble(toSerial) < Double.parseDouble(lastStockGoodsSerialStripDTO.getToSerial())) {
                    lastStockGoodsSerialStripDTO.setToSerial(toSerial);
                    //Cap nhat lai so luong               
                    lastQuantity = Long.parseLong(lastStockGoodsSerialStripDTO.getToSerial()) - Long.parseLong(lastStockGoodsSerialStripDTO.getFromSerial()) + 1;
                    lastStockGoodsSerialStripDTO.setQuantity(lastQuantity.toString());
                    //
                    lstFilterStockGoodsSerialStripDTO.set(iSize - 1, lastStockGoodsSerialStripDTO);
                }
                //Chan du lieu tim tho so luong
                if (quantityTotal > quantity) {
                    //ThienNG1 addBy 01.06.2015 - begin 
                    Long amount = 0L;
                    Long amountTotal = 0L;
                    for (int i = 0; i < lstFilterStockGoodsSerialStripDTO.size() - 1; i++) {
                        amountTotal += Long.parseLong(lstFilterStockGoodsSerialStripDTO.get(i).getQuantity());
                    }
                    amount = quantity - amountTotal;
                    lastToSerial = Long.parseLong(lstFilterStockGoodsSerialStripDTO.get(iSize - 1).getFromSerial()) + amount - 1 + "";
                    lastStockGoodsSerialStripDTO.setToSerial(DataUtil.lPad(lastToSerial, "0", lengthSerial));
                    lastStockGoodsSerialStripDTO.setQuantity(amount.toString());
                    //end
                }
            }
        }
        //
        return lstFilterStockGoodsSerialStripDTO;
    }

    //QuyenDM 09/06/2015. Them tim kiem theo conditionBean
    @Override
    public List<StockGoodsSerialDTO> getListStockGoodsSerialByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<StockGoodsSerialDTO> lstStockGoodsSerialStrip = new ArrayList<>();
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
        lstStockGoodsSerialStrip = stockGoodsSerialBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstStockGoodsSerialStrip;
    }

    @Override
    public ResultDTO checkSerial(List<StockGoodsSerialDTO> lstStockGoodsSerialDTO) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        for (StockGoodsSerialDTO item : lstStockGoodsSerialDTO) {

            String[] arraySerial = DataUtil.parseInputListString(item.getSerial());
            String number = stockGoodsSerialBusiness.counNumberSerial(item, arraySerial);
            if (Integer.parseInt(number) != arraySerial.length) {
                resultDTO.setMessage(ParamUtils.FAIL);
                return resultDTO;
            }
        }
        return resultDTO;
    }

    @Override
    public List<GoodsSerialInforDTO> getGoodsBySerial(OrdersDTO ordersDTO, List<GoodsSerialInforDTO> lstGoodsSerialInforDTO) {
        Session session;
        Connection connection;
        session = sessionFactory.openSession();
        List<GoodsSerialInforDTO> lstReturn = null;
        try {
            SessionFactory sessionFactoryBatch = session.getSessionFactory();
            connection = sessionFactoryBatch.getSessionFactoryOptions().
                    getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            connection.setAutoCommit(false);
            lstReturn = stockGoodsSerialBusiness.getGoodsBySerial(ordersDTO, lstGoodsSerialInforDTO, connection);
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstReturn;
    }

    @Override
    public List<GoodsSerialInforDTO> getGoodsBySerialInventory(OrdersDTO ordersDTO, List<GoodsSerialInforDTO> lstGoodsSerialInforDTO) {
        Session session;
        Connection connection;
        session = sessionFactory.openSession();
        List<GoodsSerialInforDTO> lstReturn = null;
        try {
            SessionFactory sessionFactoryBatch = session.getSessionFactory();
            connection = sessionFactoryBatch.getSessionFactoryOptions().
                    getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            connection.setAutoCommit(false);
            lstReturn = stockGoodsSerialBusiness.getGoodsBySerialInventory(ordersDTO, lstGoodsSerialInforDTO, connection);
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstReturn;
    }

}
