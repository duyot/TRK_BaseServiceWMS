/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.StockTransDetailBusinessInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.model.StockTransDetail;
import com.viettel.logistic.wms.dao.StockTransDetailDAO;
import java.util.List;
import org.hibernate.Session;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 22-Apr-15 4:10 PM
 */
@Service("stockTransDetailBusiness")
@Transactional
public class StockTransDetailBusiness extends BaseFWServiceImpl<StockTransDetailDAO, StockTransDetailDTO, StockTransDetail> implements StockTransDetailBusinessInterface {

    @Autowired
    private StockTransDetailDAO stockTransDetailDAO;

    public StockTransDetailBusiness() {
        tModel = new StockTransDetail();
        tDAO = stockTransDetailDAO;
    }
    @Override
    public StockTransDetailDAO gettDAO() {
        return stockTransDetailDAO;
    }
    
    public StockTransDetailBusiness(Session session) {
        this.session = session;
        tModel = new StockTransDetail();
        tDAO = stockTransDetailDAO;
    }

    @Override
    public List<StockTransDetailDTO> getListStockTransDetailByOrderId(String orderId) {
        List<StockTransDetailDTO> lst = gettDAO().getListStockTransDetailByOrderId(orderId);
        return lst;
    }
}
