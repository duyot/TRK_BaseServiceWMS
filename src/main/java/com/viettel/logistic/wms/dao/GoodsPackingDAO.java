/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.GoodsPacking;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 5/20/2015 6:43 PM
 */
@Repository("goodsPackingDAO")
public class GoodsPackingDAO extends BaseFWDAOImpl<GoodsPacking, Long> {

    public GoodsPackingDAO() {
        this.model = new GoodsPacking();
    }

    public GoodsPackingDAO(Session session) {
        this.session = session;
    }
}
