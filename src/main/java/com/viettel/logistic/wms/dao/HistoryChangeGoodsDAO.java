/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.model.HistoryChangeGoods;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author duyot
 * @version 1.0
 * @since 2/3/2016 11:09 AM
 */
@Repository("historyChangeGoodsDAO")
public class HistoryChangeGoodsDAO extends BaseFWDAOImpl<HistoryChangeGoods, Long> {

    public HistoryChangeGoodsDAO() {
        this.model = new HistoryChangeGoods();
    }

    public HistoryChangeGoodsDAO(Session session) {
        this.session = session;
    }
}
