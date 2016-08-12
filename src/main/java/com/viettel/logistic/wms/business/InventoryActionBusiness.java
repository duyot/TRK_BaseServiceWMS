/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.InventoryActionGoodsInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.InventoryActionDTO;
import com.viettel.logistic.wms.model.InventoryAction;
import com.viettel.logistic.wms.dao.InventoryActionDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:16 PM
 */
@Service("inventoryActionBusiness")
@Transactional
public class InventoryActionBusiness extends BaseFWServiceImpl<InventoryActionDAO, InventoryActionDTO, InventoryAction>{
	
    @Autowired
    private InventoryActionDAO inventoryActionDAO;

    public InventoryActionBusiness() {
        tModel = new InventoryAction();
        tDAO = inventoryActionDAO;
    }
    @Override
    public InventoryActionDAO gettDAO() {
        return inventoryActionDAO;
    }
    
    public InventoryActionBusiness(Session session) {
        this.session = session;
        tModel = new InventoryAction();
        tDAO = inventoryActionDAO;
    }
}


