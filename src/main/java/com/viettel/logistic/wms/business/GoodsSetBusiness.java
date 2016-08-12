/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.GoodsSetDTO;
import com.viettel.logistic.wms.model.GoodsSet;
import com.viettel.logistic.wms.dao.GoodsSetDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:43 PM
 */
@Service("goodsSetBusiness")
@Transactional
public class GoodsSetBusiness extends BaseFWServiceImpl<GoodsSetDAO, GoodsSetDTO, GoodsSet> {
	
    @Autowired
    private GoodsSetDAO goodsSetDAO;

    public GoodsSetBusiness() {
        tModel = new GoodsSet();
        tDAO = goodsSetDAO;
    }
    @Override
    public GoodsSetDAO gettDAO() {
        return goodsSetDAO;
    }
    
    public GoodsSetBusiness(Session session) {
        this.session = session;
        tModel = new GoodsSet();
        tDAO = goodsSetDAO;
    }
}


