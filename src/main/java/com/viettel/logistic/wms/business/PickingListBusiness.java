/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.PickingListDTO;
import com.viettel.logistic.wms.model.PickingList;
import com.viettel.logistic.wms.dao.PickingListDAO;
import com.viettel.logistic.wms.dto.PickingListGoodsDTO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 4:02 PM
 */
@Service("pickingListBusiness")
@Transactional
public class PickingListBusiness extends BaseFWServiceImpl<PickingListDAO, PickingListDTO, PickingList> {
	
    @Autowired
    private PickingListDAO pickingListDAO;

    public PickingListBusiness() {
        tModel = new PickingList();
        tDAO = pickingListDAO;
    }
    @Override
    public PickingListDAO gettDAO() {
        return pickingListDAO;
    }
    
    public PickingListBusiness(Session session) {
        this.session = session;
        tModel = new PickingList();
        tDAO = pickingListDAO;
    }      
}


