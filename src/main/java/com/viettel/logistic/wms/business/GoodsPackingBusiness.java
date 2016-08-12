/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.GoodsPackingDTO;
import com.viettel.logistic.wms.model.GoodsPacking;
import com.viettel.logistic.wms.dao.GoodsPackingDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 5/20/2015 6:43 PM
 */
@Service("goodsPackingBusiness")
@Transactional
public class GoodsPackingBusiness extends BaseFWServiceImpl<GoodsPackingDAO, GoodsPackingDTO, GoodsPacking> {
	
    @Autowired
    private GoodsPackingDAO goodsPackingDAO;

    public GoodsPackingBusiness() {
        tModel = new GoodsPacking();
        tDAO = goodsPackingDAO;
    }
    @Override
    public GoodsPackingDAO gettDAO() {
        return goodsPackingDAO;
    }
    
    public GoodsPackingBusiness(Session session) {
        this.session = session;
        tModel = new GoodsPacking();
        tDAO = goodsPackingDAO;
    }
}


