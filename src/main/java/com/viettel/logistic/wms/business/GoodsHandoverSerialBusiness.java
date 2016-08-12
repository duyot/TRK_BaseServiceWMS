/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.GoodsHandoverSerialDTO;
import com.viettel.logistic.wms.model.GoodsHandoverSerial;
import com.viettel.logistic.wms.dao.GoodsHandoverSerialDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:41 PM
 */
@Service("goodsHandoverSerialBusiness")
@Transactional
public class GoodsHandoverSerialBusiness extends BaseFWServiceImpl<GoodsHandoverSerialDAO, GoodsHandoverSerialDTO, GoodsHandoverSerial> {
	
    @Autowired
    private GoodsHandoverSerialDAO goodsHandoverSerialDAO;

    public GoodsHandoverSerialBusiness() {
        tModel = new GoodsHandoverSerial();
        tDAO = goodsHandoverSerialDAO;
    }
    @Override
    public GoodsHandoverSerialDAO gettDAO() {
        return goodsHandoverSerialDAO;
    }
    
    public GoodsHandoverSerialBusiness(Session session) {
        this.session = session;
        tModel = new GoodsHandoverSerial();
        tDAO = goodsHandoverSerialDAO;
    }
}


