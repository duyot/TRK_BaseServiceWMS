/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.InventoryActionGoodsInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.InventoryActionGoodsDTO;
import com.viettel.logistic.wms.model.InventoryActionGoods;
import com.viettel.logistic.wms.dao.InventoryActionGoodsDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:28 PM
 */
@Service("inventoryActionGoodsBusiness")
@Transactional
public class InventoryActionGoodsBusiness extends BaseFWServiceImpl<InventoryActionGoodsDAO, InventoryActionGoodsDTO, InventoryActionGoods> implements InventoryActionGoodsInterface{
	
    @Autowired
    private InventoryActionGoodsDAO inventoryActionGoodsDAO;

    public InventoryActionGoodsBusiness() {
        tModel = new InventoryActionGoods();
        tDAO = inventoryActionGoodsDAO;
    }
    @Override
    public InventoryActionGoodsDAO gettDAO() {
        return inventoryActionGoodsDAO;
    }
    
    public InventoryActionGoodsBusiness(Session session) {
        this.session = session;
        tModel = new InventoryActionGoods();
        tDAO = inventoryActionGoodsDAO;
    }
}


