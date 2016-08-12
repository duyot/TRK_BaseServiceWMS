/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.StockGoodsSerialErrorDTO;
import com.viettel.logistic.wms.model.StockGoodsSerialError;
import com.viettel.logistic.wms.dao.StockGoodsSerialErrorDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 8:41 AM
 */
@Service("stockGoodsSerialErrorBusiness")
@Transactional
public class StockGoodsSerialErrorBusiness extends BaseFWServiceImpl<StockGoodsSerialErrorDAO, StockGoodsSerialErrorDTO, StockGoodsSerialError> {
	
    @Autowired
    private StockGoodsSerialErrorDAO stockGoodsSerialErrorDAO;

    public StockGoodsSerialErrorBusiness() {
        tModel = new StockGoodsSerialError();
        tDAO = stockGoodsSerialErrorDAO;
    }
    @Override
    public StockGoodsSerialErrorDAO gettDAO() {
        return stockGoodsSerialErrorDAO;
    }
    
    public StockGoodsSerialErrorBusiness(Session session) {
        this.session = session;
        tModel = new StockGoodsSerialError();
        tDAO = stockGoodsSerialErrorDAO;
    }
}


