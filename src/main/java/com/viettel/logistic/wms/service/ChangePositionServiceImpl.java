/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.business.service.StockGoodsBusinessInterface;
import com.viettel.logistic.wms.business.service.StockGoodsSerialServiceInterface;
import com.viettel.logistic.wms.business.service.StockGoodsSerialStripServiceInterface;
import com.viettel.logistic.wms.dto.ChangePositionDTO;
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
import com.viettel.vfw5.base.utils.Constants;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author HungKV
 * @version 1.0
 * @since 6/9/2015 9:40 AM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.ChangePositionService")
public class ChangePositionServiceImpl implements ChangePositionService {

    Session session;
    String sysdate = "";
    String formatDate = "dd/mm/yyyy hh24:mi:ss";
    @Autowired
    BaseFWServiceInterface changePositionBusiness;
    @Autowired
    BaseFWServiceInterface stockGoodsBusiness;
    @Autowired
    BaseFWServiceInterface stockGoodsSerialBusiness;
    @Autowired
    BaseFWServiceInterface stockGoodsSerialStripBusiness;
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;
    @Autowired
    StockGoodsBusinessInterface stockGoodsInterface;
    @Autowired
    StockGoodsSerialStripServiceInterface stockGoodsSerialStripInterface;
    @Autowired
    StockGoodsSerialServiceInterface stockGoodsSerialInterface;

    @Override
    public String updateChangePosition(ChangePositionDTO changePositionDTO) {
        return changePositionBusiness.update(changePositionDTO);
    }

    @Override
    public String deleteChangePosition(Long id) {
        return changePositionBusiness.delete(id);
    }

    @Override
    public String deleteListChangePosition(List<ChangePositionDTO> changePositionListDTO) {
        return changePositionBusiness.delete(changePositionListDTO);
    }

    @Override
    public ChangePositionDTO findChangePositionById(Long id) {
        if (id != null && id > 0) {
            return (ChangePositionDTO) changePositionBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<ChangePositionDTO> getListChangePositionDTO(ChangePositionDTO changePositionDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (changePositionDTO != null) {
            return changePositionBusiness.search(changePositionDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertChangePosition(ChangePositionDTO changePositionDTO) {
        return changePositionBusiness.createObject(changePositionDTO);
    }

    @Override
    public String insertOrUpdateListChangePosition(List<ChangePositionDTO> changePositionDTO) {
        return changePositionBusiness.insertList(changePositionDTO);
    }

    @Override
    public List<String> getSequenseChangePosition(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return changePositionBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<ChangePositionDTO> getListChangePositionByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<ChangePositionDTO> lstChangePosition = new ArrayList<>();
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
        lstChangePosition = changePositionBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstChangePosition;
    }

    //Add by QuyenDM 15/06/2015 - Cap nhat vi tri trong kho - chuc nang don dich kho    
    @Override
    public List<ChangePositionDTO> updateCellChangePosition(List<ChangePositionDTO> lstChangePositionDTOs) {
        ResultDTO resultDTO;
        //resultDTO.setMessage(ParamUtils.SUCCESS);
        //sysdate = stockGoodsBusiness.getSysDate(formatDate);
        Session openSession = sessionFactory.openSession();
        Transaction transaction;        
        int insertSuccess = 0;
        String message = "";
        List<ChangePositionDTO> listErrors = new ArrayList<>();
        boolean isRollback;
        //
        try {
            for (ChangePositionDTO changePositionDTO : lstChangePositionDTOs) {
                isRollback = false;
                transaction = openSession.getTransaction();
                transaction.begin();
                try {

                    //Cap nhat cell mat hang khong co serial
                    if (!changePositionDTO.getIsSerial().equals(Constants.IS_SERIAL)) {
                        Map<ChangePositionDTO, ResultDTO> mapChangePositionDTO2ResultDTO;
                        mapChangePositionDTO2ResultDTO = stockGoodsInterface.updateCellStockGoods(changePositionDTO, openSession);
                        resultDTO = mapChangePositionDTO2ResultDTO.get(changePositionDTO);
                        if (resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
//                        changePositionDTO.setQuantity(resultDTO.getQuantitySucc() + "");
                            message = changePositionBusiness.create(changePositionDTO);
                            insertSuccess = insertSuccess++;
                        } else {
                            listErrors.add(changePositionDTO);
                            isRollback = true;
                        }
                    } else //Cap nhat cell mat hang quan ly serial theo dai
                    if (changePositionDTO.getIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {
                        Map<ChangePositionDTO, ResultDTO> mapChangePositionDTO2ResultDTO;
                        mapChangePositionDTO2ResultDTO = stockGoodsSerialStripInterface.updateCellStockGoodsSerialStrip(changePositionDTO, openSession);
                        resultDTO = mapChangePositionDTO2ResultDTO.get(changePositionDTO);
                        if (resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
//                        changePositionDTO.setQuantity(resultDTO.getQuantitySucc() + "");
                            message = changePositionBusiness.create(changePositionDTO);
                            insertSuccess = insertSuccess++;

                        } else {
                            listErrors.add(changePositionDTO);
                            isRollback = true;
                        }
                    } else //Cap nhat hang quan ly serial don le
                    {
                        Map<ChangePositionDTO, ResultDTO> mapChangePositionDTO2ResultDTO;
                        mapChangePositionDTO2ResultDTO = stockGoodsSerialInterface.updateCellStockGoodsSerial(changePositionDTO, openSession);
                        resultDTO = mapChangePositionDTO2ResultDTO.get(changePositionDTO);
                        if (resultDTO.getMessage().equals(ParamUtils.SUCCESS) && resultDTO.getQuantitySucc() >= 1) {
//                        changePositionDTO.setQuantity(resultDTO.getQuantitySucc() + "");
                            message = changePositionBusiness.create(changePositionDTO);
                            insertSuccess = insertSuccess + resultDTO.getQuantitySucc();
                        } else {
                            listErrors.add(changePositionDTO);
                            isRollback = true;
                        }
                    }
                    //Neu gap exception thi rollback
                } catch (Exception e) {
                    rollback(transaction);
                    System.out.println(e.getMessage());
                }
                //Neu co loi trong tung ban ghi thi rollback lai
                if (isRollback) {
                    rollback(transaction);
                } else {
                    transaction.commit();
                }
            }
        } catch (Exception e) {
//            resultDTO.setMessage(ParamUtils.FAIL);
//            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            return listErrors;
        }
        //
//        resultDTO.setQuantitySucc(insertSuccess);
        return listErrors;
    }

    private void rollback(Transaction transaction) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
