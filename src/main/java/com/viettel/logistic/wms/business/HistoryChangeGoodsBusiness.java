/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.dao.HistoryChangeGoodsDAO;
import com.viettel.logistic.wms.dto.HistoryChangeGoodsDTO;
import com.viettel.logistic.wms.model.HistoryChangeGoods;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import org.hibernate.Session;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author duyot
 * @version 1.0
 * @since 2/3/2016 11:09 AM
 */
@Service("historyChangeGoodsBusiness")
@Transactional
public class HistoryChangeGoodsBusiness extends BaseFWServiceImpl<HistoryChangeGoodsDAO, HistoryChangeGoodsDTO, HistoryChangeGoods> {
	
    @Autowired
    private HistoryChangeGoodsDAO historyChangeGoodsDAO;

    public HistoryChangeGoodsBusiness() {
        tModel = new HistoryChangeGoods();
        tDAO = historyChangeGoodsDAO;
    }
    @Override
    public HistoryChangeGoodsDAO gettDAO() {
        return historyChangeGoodsDAO;
    }
    
    public HistoryChangeGoodsBusiness(Session session) {
        this.session = session;
        tModel = new HistoryChangeGoods();
        tDAO = historyChangeGoodsDAO;
    }
}


