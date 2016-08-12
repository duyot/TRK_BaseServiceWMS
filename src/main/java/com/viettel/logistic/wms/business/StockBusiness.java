/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.StockDTO;
import com.viettel.logistic.wms.model.Stock;
import com.viettel.logistic.wms.dao.StockDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author Truongbx3
 * @version 1.0
 * @since 06-Apr-15 11:42 PM
 */
@Service("stockBusiness")
@Transactional
public class StockBusiness extends BaseFWServiceImpl<StockDAO, StockDTO, Stock> {
	
    @Autowired
    private StockDAO stockDAO;

    public StockBusiness() {
        tModel = new Stock();
        tDAO = stockDAO;
    }
    @Override
    public StockDAO gettDAO() {
        return stockDAO;
    }
    
    public StockBusiness(Session session, Locale locale) {
        this.session = session;
        this.locale = locale;
        tModel = new Stock();
        tDAO = stockDAO;
    }
}


