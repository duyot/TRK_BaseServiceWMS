
/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;
import com.viettel.logistic.wms.business.service.InventoryResultBusinessInterface;
import com.viettel.logistic.wms.dto.InventoryActionDTO;
import com.viettel.logistic.wms.dto.InventoryResultDTO;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

/**
* @author hongdq4
* @version 1.0
* @since 6/9/2015 11:26 PM
*/
@WebService(endpointInterface = "com.viettel.logistic.wms.service.InventoryResultService")
public class InventoryResultServiceImpl implements InventoryResultService {
    @Autowired
    BaseFWServiceInterface inventoryResultBusiness;
    
    @Autowired
    BaseFWServiceInterface inventoryActionGoodsBusiness;
    
    @Autowired
    InventoryResultBusinessInterface inventoryResultBusinessInterface;
    
    
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;
    
    Session session;
    Transaction transaction;
    String formatDate = "dd/mm/yyyy hh24:mi:ss";
    
    private static final String INVENT_ACTION_RESULT_UPDATED = "1";
    private static final String INVENT_RESULT_TYPE_WHEN_CREATE = "1";
    private static final String INVENT_RESULT_TYPE_IMPORTED = "2";
    
    @Override
    public String updateInventoryResult(InventoryResultDTO inventoryResultDTO) {
        return inventoryResultBusiness.update(inventoryResultDTO);
    }

    @Override
    public String deleteInventoryResult(Long id) {
        return inventoryResultBusiness.delete(id);
    }

    @Override
    public String deleteListInventoryResult(List<InventoryResultDTO> inventoryResultListDTO) {
        return inventoryResultBusiness.delete(inventoryResultListDTO);
    }

    @Override
    public InventoryResultDTO findInventoryResultById(Long id) {
        if (id != null && id > 0) {
            return (InventoryResultDTO)inventoryResultBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<InventoryResultDTO> getListInventoryResultDTO(InventoryResultDTO inventoryResultDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
         if (inventoryResultDTO != null ) {
            return inventoryResultBusiness.search(inventoryResultDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertInventoryResult(InventoryResultDTO inventoryResultDTO) {
        return inventoryResultBusiness.createObject(inventoryResultDTO);
    }
    @Override
    public String insertOrUpdateListInventoryResult(List<InventoryResultDTO> inventoryResultDTO) {
         return inventoryResultBusiness.insertList(inventoryResultDTO);
    }

    @Override
    public List<String> getSequenseInventoryResult(String seqName, int... size) {
        int number = ( size[0] > 0 ? size[0] : 1 );
       
        return inventoryResultBusiness.getListSequense(seqName, number);
    }
    
       @Override
    public List<InventoryResultDTO> getListInventoryResultByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<InventoryResultDTO> lstInventoryResult = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else {
                String value = StringUtils.formatLike(con.getValue());
                value.toLowerCase();
                con.setValue(value);
                con.setField(StringUtils.formatFunction("lower",con.getField()));
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));

        }
        lstInventoryResult = inventoryResultBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstInventoryResult;
    }

    @Override
    @Transactional
    public ResultDTO updateInventoryResultAndAction(InventoryActionDTO inventoryActionDTO, List<InventoryResultDTO> lstInventoryResult) {
            ResultDTO result = new ResultDTO();
            
            try {
                inventoryActionDTO.setStatus(INVENT_ACTION_RESULT_UPDATED);
                String updateActionResult = inventoryResultBusiness.update(inventoryActionDTO);
                if(!updateActionResult.equalsIgnoreCase(ParamUtils.SUCCESS))
                {
                    result.setMessage(ParamUtils.FAIL);
                    return result;
                }
                //remove previous data
                //get list updated
                InventoryResultDTO searchUpdatedInvent = new InventoryResultDTO();
                searchUpdatedInvent.setInventoryActionId(inventoryActionDTO.getInventoryActionId());
                searchUpdatedInvent.setType(INVENT_RESULT_TYPE_IMPORTED);
                List<InventoryResultDTO> lstUpdated = inventoryResultBusiness.search(searchUpdatedInvent, 0, Integer.MAX_VALUE, "", "inventoryResultId");
                
                if(lstUpdated != null && lstUpdated.size()>0){
                    //xoa toan bo
                    String deleteResult = inventoryResultBusiness.delete(lstUpdated);
                    if(deleteResult.equalsIgnoreCase(ParamUtils.FAIL)){
                        result.setMessage(ParamUtils.FAIL);
                        return result;
                    }
                }
                //update list inventory
                String updateInvenResult = inventoryResultBusiness.insertList(lstInventoryResult);
                if(updateInvenResult.equalsIgnoreCase(ParamUtils.SUCCESS))
                {
                    result.setMessage(ParamUtils.SUCCESS);
                    //commit();
                }else{
                    result.setMessage(ParamUtils.FAIL);
                    //rollback();
                }
            
            } catch (Exception e) {
                e.printStackTrace();
                Logger.getLogger(InventoryResultServiceImpl.class.getName()).log(Level.SEVERE, null, e);
                result.setMessage(e.getMessage());
                result.setKey(ParamUtils.FAIL);
                //rollback();
                return result;
            }
            
            return result;
    }

    
    @Override
    public List<InventoryResultDTO> checkInventoryResult(InventoryActionDTO inventoryActionDTO) {
        return inventoryResultBusinessInterface.getLstCheckedResult(inventoryActionDTO.getInventoryActionId());
    }
    
    //duyot - get list inventory by displey fields
    public List<InventoryResultDTO> getInventoryResultByDisplayField(InventoryActionDTO inventoryActionDTO, List<String> lstShowField) {
        return inventoryResultBusinessInterface.getInventoryResultByDisplayField(inventoryActionDTO.getInventoryActionId(),lstShowField);
    }
    
}
