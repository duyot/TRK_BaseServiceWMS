/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.GoodsHandoverDTO;
import com.viettel.logistic.wms.model.GoodsHandover;
import com.viettel.logistic.wms.dao.GoodsHandoverDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:39 PM
 */
@Service("goodsHandoverBusiness")
@Transactional
public class GoodsHandoverBusiness extends BaseFWServiceImpl<GoodsHandoverDAO, GoodsHandoverDTO, GoodsHandover> {
	
    @Autowired
    private GoodsHandoverDAO goodsHandoverDAO;

    public GoodsHandoverBusiness() {
        tModel = new GoodsHandover();
        tDAO = goodsHandoverDAO;
    }
    @Override
    public GoodsHandoverDAO gettDAO() {
        return goodsHandoverDAO;
    }
    
    public GoodsHandoverBusiness(Session session) {
        this.session = session;
        tModel = new GoodsHandover();
        tDAO = goodsHandoverDAO;
    }
}


