/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.InventoryResultBusinessInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.InventoryResultDTO;
import com.viettel.logistic.wms.model.InventoryResult;
import com.viettel.logistic.wms.dao.InventoryResultDAO;
import java.util.List;
import org.hibernate.Session;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:26 PM
 */
@Service("inventoryResultBusiness")
@Transactional
public class InventoryResultBusiness extends BaseFWServiceImpl<InventoryResultDAO, InventoryResultDTO, InventoryResult> implements InventoryResultBusinessInterface{
	
    @Autowired
    private InventoryResultDAO inventoryResultDAO;
    
    public InventoryResultBusiness() {
        tModel = new InventoryResult();
        tDAO = inventoryResultDAO;
    }
    @Override
    public InventoryResultDAO gettDAO() {
        return inventoryResultDAO;
    }
    
    public InventoryResultBusiness(Session session) {
        this.session = session;
        tModel = new InventoryResult();
        tDAO = inventoryResultDAO;
    }

    @Override
    public List<InventoryResultDTO> getLstCheckedResult(String  id) {
         return gettDAO().getLstCheckedResult(id);
         
    }
    
     @Override
    public List<InventoryResultDTO> getInventoryResultByDisplayField(String inventoryActionId, List<String> lstShowField) {
         return gettDAO().getInventoryResultByDisplayField(inventoryActionId,lstShowField);
    }
}


