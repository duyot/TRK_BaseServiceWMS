/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.GoodsSet;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:43 PM
 */
@Repository("goodsSetDAO")
public class GoodsSetDAO extends BaseFWDAOImpl<GoodsSet, Long> {

    public GoodsSetDAO() {
        this.model = new GoodsSet();
    }

    public GoodsSetDAO(Session session) {
        this.session = session;
    }
}
