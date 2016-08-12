
/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.business.service.GoodsBusinessInterface;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.dto.GoodsPackingDTO;
import com.viettel.logistic.wms.model.Goods;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author ThieuLQ1
 * @version 1.0
 * @since 5/19/2015 3:52 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.GoodsService")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsBusinessInterface goodsBusiness;
    @Autowired
    BaseFWServiceInterface goodsPackingBusiness;
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;

    @Override
    public String updateGoods(GoodsDTO goodsDTO) {
        return goodsBusiness.update(goodsDTO);
    }

    @Override
    public String deleteGoods(Long id) {

        return goodsBusiness.delete(id);
    }

    @Override
    public String deleteListGoods(List<GoodsDTO> goodsListDTO) {
        return goodsBusiness.delete(goodsListDTO);
    }

    @Override
    public GoodsDTO findGoodsById(Long id) {
        if (id != null && id > 0) {
            return (GoodsDTO) goodsBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<GoodsDTO> getListGoodsDTO(GoodsDTO goodsDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (goodsDTO != null) {
            return goodsBusiness.search(goodsDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertGoods(GoodsDTO goodsDTO) {
        ResultDTO resultDTO = new ResultDTO();
        Class<?> c = goodsDTO.getClass();
        HashMap<String, String> hasmap = new HashMap<>();
        Goods goods = new Goods();
        String[] lstColumnUnique = goods.getUniqueColumn();
        for (int i = 0; i < lstColumnUnique.length; i++) {
            try {
                Method method = c.getMethod(DataUtil.getGetterOfColumn(lstColumnUnique[i]));
                try {
                    hasmap.put(lstColumnUnique[i], (String) method.invoke(goodsDTO));
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GoodsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GoodsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GoodsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(GoodsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(GoodsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Boolean check = goodsBusiness.isDuplicate(null, hasmap);
            if (!check) {
                resultDTO = goodsBusiness.createObject(goodsDTO);
                resultDTO.setKey(ParamUtils.SUCCESS);
            } else {

                resultDTO.setKey("ERROR_EXISTED");

            }
        } catch (Exception e) {
            resultDTO.setKey("ERROR_SYSTEM");
        }
        return resultDTO;
    }

    @Override
    public String insertOrUpdateListGoods(List<GoodsDTO> goodsDTO) {
        return goodsBusiness.insertList(goodsDTO);
    }

    @Override
    public List<String> getSequenseGoods(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return goodsBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<GoodsDTO> getListGoodsByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<GoodsDTO> lstGoods = new ArrayList<>();
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
        lstGoods = goodsBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstGoods;
    }

    @Override
    @Transactional
    public ResultDTO importGoodsFile(List<GoodsDTO> lstGoodsDTO) {
        ResultDTO resultDTO = new ResultDTO();
        GoodsPackingDTO goodPackingDTO;
        List<String> listSequenSe = new ArrayList<>();
        try {
            for (GoodsDTO goodsDTO : lstGoodsDTO) {
                goodPackingDTO = new GoodsPackingDTO();
                //Insert du lieu hang hoa
                resultDTO = goodsBusiness.createObject(goodsDTO);
                if (resultDTO.getMessage().equalsIgnoreCase(ParamUtils.SUCCESS)) {
                    goodPackingDTO.setGoodsId(resultDTO.getId());
                    //Set GoodsPacking default & Insert du lieu kieu kien
                    goodPackingDTO.setPackingDefault("1");
                    goodPackingDTO.setUnitTypeName(goodsDTO.getUnitType());
                    goodPackingDTO.setPackingVolume(goodsDTO.getVolumeReal());
                    goodPackingDTO.setPackingWeight(goodsDTO.getWeight());
                    goodPackingDTO.setPackingSize(goodsDTO.getOriginSize());
                    goodPackingDTO.setUnitType(goodsDTO.getUnitType());
                    //Get Sequences
                    listSequenSe = goodsPackingBusiness.getListSequense("GOODS_PACKING_SEQ", 1);
                    if (listSequenSe != null) {
                        goodPackingDTO.setCode(listSequenSe.get(0));
                        goodPackingDTO.setPackingNumber(listSequenSe.get(0));
                    }
                    resultDTO = goodsPackingBusiness.createObject(goodPackingDTO);
                }

            }
        } catch (DataIntegrityViolationException he) {
            resultDTO.setMessage(he.getMessage());
            return resultDTO;
        }

        return resultDTO;
    }

    @Override
    public String updateListGoodsGoodsByCondition(List<GoodsDTO> goodsDTO, int customerCode) {
        return goodsBusiness.insertList(goodsDTO);
    }

    /**
     *
     * @param gdtos
     * @param sessions
     * @param customer
     * @return
     */
    @Override
    public ResultDTO synchListGoods(List<GoodsDTO> gdtos) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if ("SUCCESS".equalsIgnoreCase(resultDTO.getMessage())) {
                resultDTO = goodsBusiness.synchListGoods(gdtos, session);
            } else if ("FAIL".equalsIgnoreCase(resultDTO.getMessage())) {
                tx.rollback();
            }
        } catch (Exception e) {
            tx.rollback();
        }
        return resultDTO;
    }
//lay danh sach hang hoa cua VTT

    @Override
    public List<GoodsDTO> getListGoodsForBCCS(List<String> lstGoodsCode, String cusId) {
        List<GoodsDTO> lstAllGoodsDTO = new ArrayList<>();
        String goodsCode = "";
        for (int i = 0; i < lstGoodsCode.size(); i++) {
            goodsCode = goodsCode + "," + lstGoodsCode.get(i);
            if (i == 0 || i % 49 != 0) {
                continue;
            }
            lstAllGoodsDTO.addAll(getListGoodsForBCCSMainProcess(goodsCode, cusId));
            goodsCode = "";
        }
        lstAllGoodsDTO.addAll(getListGoodsForBCCSMainProcess(goodsCode, cusId));
        return lstAllGoodsDTO;
    }

    public List<GoodsDTO> getListGoodsForBCCSMainProcess(String goodsCode, String cusId) {

        List<GoodsDTO> lstGoodsDTO = new ArrayList<>();
        if (goodsCode.isEmpty()) {
            return lstGoodsDTO;
        }
        Map<String, String> mapGoodsPacking = new HashMap<>();
        List<ConditionBean> lstConditionGoodCode = new ArrayList<>();

        ConditionBean conditionCode = new ConditionBean();
        conditionCode.setOperator(ParamUtils.OP_IN);
        conditionCode.setField("code");
        conditionCode.setType(ParamUtils.TYPE_STRING);
        conditionCode.setValue(goodsCode.replaceFirst(",", ""));
        lstConditionGoodCode.add(conditionCode);
        ConditionBean conditionCustId = new ConditionBean();
        conditionCustId.setOperator(ParamUtils.OP_EQUAL);
        conditionCustId.setField("custId");
        conditionCustId.setType(ParamUtils.TYPE_NUMBER);
        conditionCustId.setValue(cusId);
        lstConditionGoodCode.add(conditionCustId);
        lstGoodsDTO = goodsBusiness.searchByConditionBean(lstConditionGoodCode, 0, Integer.MAX_VALUE, "", "goodsId");
//        get good packing
        if (lstGoodsDTO.size() == 0) {
            return lstGoodsDTO;
        }
        String goodsIdlist = "";
        for (GoodsDTO item : lstGoodsDTO) {
            goodsIdlist = goodsIdlist + "," + item.getGoodsId();
        }
        List<ConditionBean> lstConditionBeans = new ArrayList<>();
        ConditionBean condition = new ConditionBean();
        condition.setOperator(ParamUtils.OP_IN);
        condition.setField("goodsId");
        condition.setType(ParamUtils.TYPE_NUMBER);
        condition.setValue(goodsIdlist.replaceFirst(",", ""));
        lstConditionBeans.add(condition);
        ConditionBean condition1 = new ConditionBean();
        condition1.setOperator(ParamUtils.OP_EQUAL);
        condition1.setField("packingDefault");
        condition1.setType(ParamUtils.TYPE_STRING);
        condition1.setValue("1");
        lstConditionBeans.add(condition1);
        List<GoodsPackingDTO> lstGoodsPackingDTO = goodsPackingBusiness.searchByConditionBean(lstConditionBeans, 0, Integer.MAX_VALUE, "", "Id");
        for (GoodsPackingDTO item : lstGoodsPackingDTO) {
            mapGoodsPacking.put(item.getGoodsId(), item.getId());
        }
//        set goodPAcking for goods
        for (GoodsDTO item : lstGoodsDTO) {
            item.setGoodsPacking(mapGoodsPacking.get(item.getGoodsId()));
        }
        return lstGoodsDTO;
    }

    @Override
    public ResultDTO insertListGoods(List<GoodsDTO> goodsDTO) {

        ResultDTO resultDTO = new ResultDTO();
        resultDTO = goodsBusiness.insertListGoods(goodsDTO);
        return resultDTO;
    }

    @Override
    public List<GoodsDTO> getListGoodsWithCustId(String custId) {
        return goodsBusiness.getListGoodsWithCustId(custId);
    }

    /**
     * NgocND6 get list goods serial by cust id
     *
     * @param custId
     * @param isSerial
     * @param isSerialStrip
     * @return
     */
    @Override
    public List<GoodsDTO> getListGoodsSerialByCustId(String custId, String isSerial, String isSerialStrip) {
        return goodsBusiness.getListGoodsSerialByCustId(custId, isSerial, isSerialStrip);
    }
}
