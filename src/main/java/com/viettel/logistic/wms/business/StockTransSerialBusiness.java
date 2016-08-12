/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.StockTransSerialBusinessInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.logistic.wms.model.StockTransSerial;
import com.viettel.logistic.wms.dao.StockTransSerialDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 29-Apr-15 10:38 PM
 */
@Service("stockTransSerialBusiness")
@Transactional
public class StockTransSerialBusiness extends BaseFWServiceImpl<StockTransSerialDAO, StockTransSerialDTO, StockTransSerial> implements StockTransSerialBusinessInterface {

    @Autowired
    private StockTransSerialDAO stockTransSerialDAO;

    public StockTransSerialBusiness() {
        tModel = new StockTransSerial();
        tDAO = stockTransSerialDAO;
    }

    @Override
    public StockTransSerialDAO gettDAO() {
        return stockTransSerialDAO;
    }

    public StockTransSerialBusiness(Session session) {
        this.session = session;
        tModel = new StockTransSerial();
        tDAO = stockTransSerialDAO;
    }

    //theo don le

    @Override
    public List<StockTransSerialDTO> getListStockTransSerialBySerial(StockTransSerialDTO stockTransSerialDTO) {
        List<StockTransSerialDTO> lstStockGoodsSerial = new ArrayList<>();
        lstStockGoodsSerial = convertListModeltoDTO(gettDAO().getListStockTransSerialBySerial(stockTransSerialDTO));
        return lstStockGoodsSerial;
    }

    //theo dai

    @Override
    public List<StockTransSerialDTO> getListStockTransSerialBySerialStrip(StockTransSerialDTO stockTransSerialDTO) {
        List<StockTransSerialDTO> lstStockGoodsSerial = new ArrayList<>();
        lstStockGoodsSerial = convertListModeltoDTO(gettDAO().getListStockTransSerialBySerialStrip(stockTransSerialDTO));
        return lstStockGoodsSerial;
    }

    @Override
    public List<StockTransSerialDTO> getListStockTransSerialByOrderId(String orderId) {
        List<StockTransSerialDTO> lstStockGoodsSerial = new ArrayList<>();
        lstStockGoodsSerial = gettDAO().getListStockTransSerialByOrderId(orderId);
        return lstStockGoodsSerial;
    }
}
