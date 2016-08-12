/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.GoodsAddInfor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 22-Apr-15 7:11 PM
 */
@Repository("goodsAddInforDAO")
public class GoodsAddInforDAO extends BaseFWDAOImpl<GoodsAddInfor, Long> {

    public GoodsAddInforDAO() {
        this.model = new GoodsAddInfor();
    }

    public GoodsAddInforDAO(Session session) {
        this.session = session;
    }
}
