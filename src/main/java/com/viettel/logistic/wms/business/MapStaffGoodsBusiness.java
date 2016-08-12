/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.MapStaffGoodsBusinessInterface;
import com.viettel.logistic.wms.dao.MapStaffGoodsDAO;
import com.viettel.logistic.wms.dto.MapStaffGoodsDTO;
import com.viettel.logistic.wms.model.MapStaffGoods;
import com.viettel.vfw5.base.dto.ResultMapStaffGoodsDTO;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import java.util.List;
import org.hibernate.Session;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hongdq4
 * @version 1.0
 * @since 10/16/2015 10:12 AM
 */
@Service("mapStaffGoodsBusiness")
@Transactional
public class MapStaffGoodsBusiness extends BaseFWServiceImpl<MapStaffGoodsDAO, MapStaffGoodsDTO, MapStaffGoods> implements MapStaffGoodsBusinessInterface {

    @Autowired
    private MapStaffGoodsDAO mapStaffGoodsDAO;

    public MapStaffGoodsBusiness() {
        tModel = new MapStaffGoods();
        tDAO = mapStaffGoodsDAO;
    }

    @Override
    public MapStaffGoodsDAO gettDAO() {
        return mapStaffGoodsDAO;
    }

    public MapStaffGoodsBusiness(Session session) {
        this.session = session;
        tModel = new MapStaffGoods();
        tDAO = mapStaffGoodsDAO;
    }

    @Override
    public ResultMapStaffGoodsDTO insertListMapStaffGoods(List<MapStaffGoodsDTO> lstmap, Session session) {
        return gettDAO().insertListMapStaffGoods(lstmap, session);
    }

    @Override
    public List<MapStaffGoodsDTO> getListStaffByGoods(String codeList, String custId) {
        List<MapStaffGoodsDTO> lstMapStaffGoodsDTO = gettDAO().getListStaffByGoods(codeList, custId);
        return lstMapStaffGoodsDTO;
    }

}
