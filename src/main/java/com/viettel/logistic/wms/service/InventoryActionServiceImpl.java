
/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.google.common.collect.Lists;
import com.viettel.logistic.wms.business.service.InventoryActionGoodsInterface;
import com.viettel.logistic.wms.business.service.InventoryResultBusinessInterface;
import com.viettel.logistic.wms.business.service.StockGoodsBusinessInterface;
import com.viettel.logistic.wms.business.service.StockGoodsSerialServiceInterface;
import com.viettel.logistic.wms.business.service.StockGoodsSerialStripServiceInterface;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.dto.InventoryActionDTO;
import com.viettel.logistic.wms.dto.InventoryActionGoodsDTO;
import com.viettel.logistic.wms.dto.InventoryResultDTO;
import com.viettel.logistic.wms.dto.StockGoodsDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
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
import com.viettel.vfw5.base.utils.DataUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:16 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.InventoryActionService")
public class InventoryActionServiceImpl implements InventoryActionService {

    @Autowired
    BaseFWServiceInterface inventoryActionBusiness;

    @Autowired
    StockGoodsBusinessInterface stockGoodsBusinessInterface;
    @Autowired
    StockGoodsSerialServiceInterface stockGoodsSerialInterface;
    @Autowired
    StockGoodsSerialStripServiceInterface stockGoodsSerialStripServiceInterface;

    @Autowired
    BaseFWServiceInterface inventoryActionGoodsBusiness;

    @Autowired
    BaseFWServiceInterface inventoryResultBusiness;

    @Autowired
    InventoryActionGoodsInterface inventoryActionGoods;

    @Autowired
    InventoryResultBusinessInterface inventoryResult;

    //
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;
    Session session;
    Transaction transaction;
    //
    String formatDate = "dd/MM/yyyy hh24:mi:ss";
    String sysdate = "";
    String inventoryActionId = "";

    @Override
    public String updateInventoryAction(InventoryActionDTO inventoryActionDTO) {
        return inventoryActionBusiness.update(inventoryActionDTO);
    }

    @Override
    public String deleteInventoryAction(Long id) {
        return inventoryActionBusiness.delete(id);
    }

    @Override
    public String deleteListInventoryAction(List<InventoryActionDTO> inventoryActionListDTO) {
        return inventoryActionBusiness.delete(inventoryActionListDTO);
    }

    @Override
    public InventoryActionDTO findInventoryActionById(Long id) {
        if (id != null && id > 0) {
            return (InventoryActionDTO) inventoryActionBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<InventoryActionDTO> getListInventoryActionDTO(InventoryActionDTO inventoryActionDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (inventoryActionDTO != null) {
            return inventoryActionBusiness.search(inventoryActionDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertInventoryAction(InventoryActionDTO inventoryActionDTO) {
        inventoryActionDTO.setCreateDate(inventoryActionBusiness.getSysDate(formatDate));
        return inventoryActionBusiness.createObject(inventoryActionDTO);
    }

    @Override
    public String insertOrUpdateListInventoryAction(List<InventoryActionDTO> inventoryActionDTO) {
        return inventoryActionBusiness.insertList(inventoryActionDTO);
    }

    @Override
    public List<String> getSequenseInventoryAction(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return inventoryActionBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<InventoryActionDTO> getListInventoryActionByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<InventoryActionDTO> lstInventoryAction = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else {
                String value = StringUtils.formatLike(con.getValue());
                value.toLowerCase();
                con.setValue(value);
                con.setField(StringUtils.formatFunction("lower", con.getField()));
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));

        }
        lstInventoryAction = inventoryActionBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstInventoryAction;
    }

    @Override
    @Transactional
    public ResultDTO insertInventoryActionAndGoods(InventoryActionDTO inventoryActionDTO, List<InventoryActionGoodsDTO> lstInventoryActionGoodsDTO, List<InventoryResultDTO> lstInventoryResultDTO, boolean isInsert) {
        ResultDTO resultDTO = new ResultDTO();
        String message = "";
        try {
            if (isInsert) {
                inventoryActionDTO.setCreateDate(inventoryActionBusiness.getSysDate(formatDate));
                resultDTO = inventoryActionBusiness.createObject(inventoryActionDTO);
            } else {
                message = inventoryActionBusiness.update(inventoryActionDTO);
            }

            if (resultDTO.getMessage() != null) {
                message = resultDTO.getMessage();
            }
            if (message.equals(ParamUtils.SUCCESS)) { // neu thanh cong inventoryActionDTO
                // get inventoryActionId
                if (isInsert) {
                    inventoryActionId = resultDTO.getId();
                } else {
                    inventoryActionId = inventoryActionDTO.getInventoryActionId();
                }
                List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
                lstCondition.add(new ConditionBean("inventoryActionId", ParamUtils.OP_EQUAL, inventoryActionId, ParamUtils.TYPE_NUMBER));
                int result_1 = 0;
                int result_2 = 0;
//                

                String result1 = inventoryActionGoods.deleteListConditionbean(lstInventoryActionGoodsDTO.get(0).toModel().getModelName(), lstCondition);
                String result2 = inventoryResult.deleteListConditionbean(lstInventoryResultDTO.get(0).toModel().getModelName(), lstCondition);
                for (InventoryActionGoodsDTO inventoryActionGoodsDTO : lstInventoryActionGoodsDTO) {
                    inventoryActionGoodsDTO.setInventoryActionGoodsId(null);
                    inventoryActionGoodsDTO.setInventoryActionId(inventoryActionId);
                }
                message = inventoryActionGoodsBusiness.insertList(lstInventoryActionGoodsDTO); // insert or update list dghdgdfgdf
                //
                for (InventoryResultDTO inventoryResultDTO : lstInventoryResultDTO) {
                    inventoryResultDTO.setInventoryResultId(null);
                    inventoryResultDTO.setInventoryActionId(inventoryActionId);
                }
                message = inventoryResultBusiness.insertList(lstInventoryResultDTO);
            }
        } catch (Exception e) {
            resultDTO.setMessage(e.getMessage());
        }
        resultDTO.setMessage(message);
        return resultDTO;
    }

    @Override
    public ResultDTO insertInventoryActionAndGoodsCondition(InventoryActionDTO inventoryActionDTO, List<InventoryActionGoodsDTO> lstInventoryActionGoodsDTO, List<GoodsDTO> lstGoodsDTO, boolean isInsert) {
        ResultDTO resultDTO = new ResultDTO();
        Boolean checkGoodTrans;
        List<InventoryResultDTO> lstInvResult = Lists.newArrayList();
        List<StockGoodsDTO> lstStockGoodsDTO = Lists.newArrayList();
        List<StockGoodsSerialDTO> lstStockGoodsSerialDTO = Lists.newArrayList();
        List<StockGoodsSerialStripDTO> lstStockGoodsSerialStripDTO = Lists.newArrayList();
        for (GoodsDTO goods : lstGoodsDTO) {
            List<StockGoodsDTO> lstStockGoodsTmp = Lists.newArrayList();
            List<StockGoodsSerialDTO> lstStockGoodsSerialTmp = Lists.newArrayList();
            List<StockGoodsSerialStripDTO> lstStockGoodsSerialStripTmp = Lists.newArrayList();
            for (InventoryActionGoodsDTO invAcGoods : lstInventoryActionGoodsDTO) {
                if (goods.getGoodsId().equalsIgnoreCase(invAcGoods.getGoodsId())) {
                    if (invAcGoods.getLstConditionBean() != null) {
                        for (ConditionBean con : invAcGoods.getLstConditionBean()) {
                            //
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
                        if (goods.getIsSerial() != null && goods.getIsSerial().equalsIgnoreCase("0")) {
                            lstStockGoodsTmp = stockGoodsBusinessInterface.searchByConditionBean(invAcGoods.getLstConditionBean(), 0, Integer.MAX_VALUE, "ASC", "id");
                        }
                        if (goods.getIsSerial() != null && goods.getIsSerialStrip() != null && goods.getIsSerial().equalsIgnoreCase("1") && goods.getIsSerial().equalsIgnoreCase("0")) {
                            lstStockGoodsSerialTmp = stockGoodsSerialInterface.searchByConditionBean(invAcGoods.getLstConditionBean(), 0, Integer.MAX_VALUE, "ASC", "id");
                        }
                        if (goods.getIsSerialStrip() != null && goods.getIsSerialStrip().equalsIgnoreCase("1")) {
                            lstStockGoodsSerialStripTmp = stockGoodsSerialStripServiceInterface.searchByConditionBean(invAcGoods.getLstConditionBean(), 0, Integer.MAX_VALUE, "ASC", "id");
                        }
                    }
                }
            }
            lstStockGoodsDTO.addAll(lstStockGoodsTmp);
            lstStockGoodsSerialDTO.addAll(lstStockGoodsSerialTmp);
            lstStockGoodsSerialStripDTO.addAll(lstStockGoodsSerialStripTmp);
        }
        //

        for (GoodsDTO goods : lstGoodsDTO) {
            checkGoodTrans = false;
            if (!DataUtil.isListNullOrEmpty(lstStockGoodsDTO)) {
                //Set du lieu cho Inventory Result
                InventoryResultDTO invResult1;
                for (StockGoodsDTO stockGoods : lstStockGoodsDTO) {
                    if (goods.getGoodsId().equals(stockGoods.getGoodsId())) {
                        invResult1 = new InventoryResultDTO();
                        invResult1.setGoodsCode(goods.getCode());
                        invResult1.setGoodsName(goods.getName());
                        invResult1.setGoodsId(goods.getGoodsId());
//                        invResult1.setCellId(stockGoods.getCellId());
                        invResult1.setCellCode(stockGoods.getCellCode());
                        invResult1.setBarcode(stockGoods.getBarcode());
                        invResult1.setAmount(stockGoods.getAmount());
                        invResult1.setUnitName(goods.getUnitType());
                        invResult1.setType("1");
                        lstInvResult.add(invResult1);
                        checkGoodTrans = true;
                    }
                }
            }

            if (!DataUtil.isListNullOrEmpty(lstStockGoodsSerialDTO)) {
                InventoryResultDTO invResult2;
                //Set du lieu cho Inventory Result
                for (StockGoodsSerialDTO stockGoodsSerial : lstStockGoodsSerialDTO) {
                    if (goods.getGoodsId().equals(stockGoodsSerial.getGoodsId())) {
                        invResult2 = new InventoryResultDTO();
                        invResult2.setGoodsCode(goods.getCode());
                        invResult2.setGoodsName(goods.getName());
                        invResult2.setGoodsId(goods.getGoodsId());
//                        invResult2.setCellId(stockGoodsSerial.getCellId());
                        invResult2.setCellCode(stockGoodsSerial.getCellCode());
                        invResult2.setFromSerial(stockGoodsSerial.getSerial());
                        invResult2.setToSerial(stockGoodsSerial.getSerial());
                        invResult2.setBarcode(stockGoodsSerial.getBarcode());
                        invResult2.setUnitName(goods.getUnitType());
                        //tam thoi lay bang 1.neu ghep dai thi  them truong  quantity 25/06/2015
                        invResult2.setAmount("1");
                        invResult2.setType("1");
                        lstInvResult.add(invResult2);
                        checkGoodTrans = true;
                    }
                }
            }
            //
            if (!DataUtil.isListNullOrEmpty(lstStockGoodsSerialStripDTO)) {
                //
                InventoryResultDTO invResult3;
                //Set du lieu cho Inventory Result
                for (StockGoodsSerialStripDTO stockGoodsSerialTrip : lstStockGoodsSerialStripDTO) {
                    if (goods.getGoodsId().equals(stockGoodsSerialTrip.getGoodsId())) {
                        invResult3 = new InventoryResultDTO();
                        invResult3.setGoodsCode(goods.getCode());
                        invResult3.setGoodsName(goods.getName());
                        invResult3.setGoodsId(goods.getGoodsId());
//                        invResult3.setCellId(stockGoodsSerialTrip.getCellId());
                        invResult3.setCellCode(stockGoodsSerialTrip.getCellCode());
                        invResult3.setFromSerial(stockGoodsSerialTrip.getFromSerial());
                        invResult3.setToSerial(stockGoodsSerialTrip.getToSerial());
                        invResult3.setBarcode(stockGoodsSerialTrip.getBarcode());
                        invResult3.setAmount(stockGoodsSerialTrip.getQuantity());
                        invResult3.setUnitName(goods.getUnitType());
                        invResult3.setType("1");
                        lstInvResult.add(invResult3);
                        checkGoodTrans = true;
                    }
                }
            }
            if (!checkGoodTrans) {
                resultDTO.setKey(goods.getName());
                resultDTO.setMessage("CHECK_STOCK_TRANS");
                return resultDTO;
            }
        }
        if (lstInvResult == null || lstInvResult.isEmpty()) {
            resultDTO.setKey("");
            resultDTO.setMessage("CHECK_STOCK_TRANS");
            return resultDTO;
        }
        //Service insertInventoryActionAndGoodsCondition o tren Tao lenh kiem ke or sua lenh
        String message = "";
        try {
            if (isInsert) {
                inventoryActionDTO.setCreateDate(inventoryActionBusiness.getSysDate(formatDate));
                resultDTO = inventoryActionBusiness.createObject(inventoryActionDTO);
            } else {
                message = inventoryActionBusiness.update(inventoryActionDTO);
            }

            if (resultDTO.getMessage() != null) {
                message = resultDTO.getMessage();
            }
            if (message.equals(ParamUtils.SUCCESS)) { // neu thanh cong inventoryActionDTO
                // get inventoryActionId
                if (isInsert) {
                    inventoryActionId = resultDTO.getId();
                } else {
                    inventoryActionId = inventoryActionDTO.getInventoryActionId();
                }
                List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
                lstCondition.add(new ConditionBean("inventoryActionId", ParamUtils.OP_EQUAL, inventoryActionId, ParamUtils.TYPE_NUMBER));
                int result_1 = 0;
                int result_2 = 0;
//                

                String result1 = inventoryActionGoods.deleteListConditionbean(lstInventoryActionGoodsDTO.get(0).toModel().getModelName(), lstCondition);
                String result2 = inventoryResult.deleteListConditionbean(lstInvResult.get(0).toModel().getModelName(), lstCondition);
                for (InventoryActionGoodsDTO inventoryActionGoodsDTO : lstInventoryActionGoodsDTO) {
                    inventoryActionGoodsDTO.setInventoryActionGoodsId(null);
                    inventoryActionGoodsDTO.setInventoryActionId(inventoryActionId);
                }
                message = inventoryActionGoodsBusiness.insertList(lstInventoryActionGoodsDTO); // insert or update list dghdgdfgdf
                //
                for (InventoryResultDTO inventoryResultDTO : lstInvResult) {
                    inventoryResultDTO.setInventoryResultId(null);
                    inventoryResultDTO.setInventoryActionId(inventoryActionId);
                }
                message = inventoryResultBusiness.insertList(lstInvResult);
            }
        } catch (Exception e) {
            resultDTO.setMessage(e.getMessage());
        }
        resultDTO.setMessage(message);

        return resultDTO;
    }

}
