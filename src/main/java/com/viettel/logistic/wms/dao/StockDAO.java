/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.Stock;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author Truongbx3
 * @version 1.0
 * @since 06-Apr-15 11:42 PM
 */
@Repository("stockDAO")
public class StockDAO extends BaseFWDAOImpl<Stock, Long> {

    public StockDAO() {
        this.model = new Stock();
    }

    public StockDAO(Session session) {
        this.session = session;
    }
}
