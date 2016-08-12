/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.GoodsHandoverSerial;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:41 PM
 */
@Repository("goodsHandoverSerialDAO")
public class GoodsHandoverSerialDAO extends BaseFWDAOImpl<GoodsHandoverSerial, Long> {

    public GoodsHandoverSerialDAO() {
        this.model = new GoodsHandoverSerial();
    }

    public GoodsHandoverSerialDAO(Session session) {
        this.session = session;
    }
}
