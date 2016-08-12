/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.GoodsAddInforDTO;
import com.viettel.logistic.wms.model.GoodsAddInfor;
import com.viettel.logistic.wms.dao.GoodsAddInforDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 22-Apr-15 7:11 PM
 */
@Service("goodsAddInforBusiness")
@Transactional
public class GoodsAddInforBusiness extends BaseFWServiceImpl<GoodsAddInforDAO, GoodsAddInforDTO, GoodsAddInfor> {
	
    @Autowired
    private GoodsAddInforDAO goodsAddInforDAO;

    public GoodsAddInforBusiness() {
        tModel = new GoodsAddInfor();
        tDAO = goodsAddInforDAO;
    }
    @Override
    public GoodsAddInforDAO gettDAO() {
        return goodsAddInforDAO;
    }
    
    public GoodsAddInforBusiness(Session session) {
        this.session = session;
        tModel = new GoodsAddInfor();
        tDAO = goodsAddInforDAO;
    }
}


