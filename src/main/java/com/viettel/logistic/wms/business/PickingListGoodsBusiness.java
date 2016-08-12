/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.PickingListGoodsBusinessInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.PickingListGoodsDTO;
import com.viettel.logistic.wms.model.PickingListGoods;
import com.viettel.logistic.wms.dao.PickingListGoodsDAO;
import com.viettel.logistic.wms.dto.PickingListDTO;
import com.viettel.logistic.wms.model.PickingList;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 4:07 PM
 */
@Service("pickingListGoodsBusiness")
@Transactional
public class PickingListGoodsBusiness extends BaseFWServiceImpl<PickingListGoodsDAO, PickingListGoodsDTO, PickingListGoods> implements PickingListGoodsBusinessInterface{
	
    @Autowired
    private PickingListGoodsDAO pickingListGoodsDAO;

    public PickingListGoodsBusiness() {
        tModel = new PickingListGoods();
        tDAO = pickingListGoodsDAO;
    }
    @Override
    public PickingListGoodsDAO gettDAO() {
        return pickingListGoodsDAO;
    }
    
    public PickingListGoodsBusiness(Session session) {
        this.session = session;
        tModel = new PickingListGoods();
        tDAO = pickingListGoodsDAO;
    }
    //
    @Override
    public String getListCellCode(PickingListDTO pickingListDTO, PickingListGoodsDTO pickingListGoodsDTO) {
        PickingList pickingList = pickingListDTO.toModel();
        PickingListGoods pickingListGoods = pickingListGoodsDTO.toModel();
        String listCellCode = pickingListGoodsDAO.getListCellCode(pickingList, pickingListGoods);
        return listCellCode;
    }
}


