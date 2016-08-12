/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.GoodsBusinessInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.model.Goods;
import com.viettel.logistic.wms.dao.GoodsDAO;
import com.viettel.vfw5.base.dto.ResultDTO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ThieuLQ1
 * @version 1.0
 * @since 5/19/2015 3:52 PM
 */
@Service("goodsBusiness")
@Transactional
public class GoodsBusiness extends BaseFWServiceImpl<GoodsDAO, GoodsDTO, Goods> implements GoodsBusinessInterface {

    @Autowired
    private GoodsDAO goodsDAO;

    public GoodsBusiness() {
        tModel = new Goods();
        tDAO = goodsDAO;
    }

    @Override
    public GoodsDAO gettDAO() {
        return goodsDAO;
    }

    public GoodsBusiness(Session session) {
        this.session = session;
        tModel = new Goods();
        tDAO = goodsDAO;
    }

    @Override
    public ResultDTO synchListGoods(List<GoodsDTO> lstGoods, Session session) {
        return gettDAO().synchListGoods(lstGoods, session);
    }

    @Override
    public ResultDTO insertListGoods(List<GoodsDTO> lstGoods) {
        return gettDAO().insertListGoods(convertListDTOtoModel(lstGoods));
    }
    
    @Override
    public List<GoodsDTO> getListGoodsWithCustId(String custId){
        return gettDAO().getListGoodsWithCustId(custId);
    }

    @Override
    public List<GoodsDTO> getListGoodsSerialByCustId(String custId, String isSerial, String isSerialStrip) {
        return gettDAO().getListGoodsSerialByCustId(custId, isSerial, isSerialStrip);
    }
}
