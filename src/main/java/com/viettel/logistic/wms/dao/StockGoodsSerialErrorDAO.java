/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.StockGoodsSerialError;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 8:41 AM
 */
@Repository("stockGoodsSerialErrorDAO")
public class StockGoodsSerialErrorDAO extends BaseFWDAOImpl<StockGoodsSerialError, Long> {

    public StockGoodsSerialErrorDAO() {
        this.model = new StockGoodsSerialError();
    }

    public StockGoodsSerialErrorDAO(Session session) {
        this.session = session;
    }
}
