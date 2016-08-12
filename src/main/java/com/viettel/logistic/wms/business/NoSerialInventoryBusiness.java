/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.dao.NoSerialInventoryDAO;
import com.viettel.logistic.wms.dto.NoSerialInventoryDTO;
import com.viettel.logistic.wms.model.NoSerialInventory;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import org.hibernate.Session;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ngocnd6
 * @version 1.0
 * @since 5/10/2016 4:47 PM
 */
@Service("noSerialInventoryBusiness")
@Transactional
public class NoSerialInventoryBusiness extends BaseFWServiceImpl<NoSerialInventoryDAO, NoSerialInventoryDTO, NoSerialInventory> {

    @Autowired
    private NoSerialInventoryDAO noSerialInventoryDAO;

    public NoSerialInventoryBusiness() {
        tModel = new NoSerialInventory();
        tDAO = noSerialInventoryDAO;
    }

    @Override
    public NoSerialInventoryDAO gettDAO() {
        return noSerialInventoryDAO;
    }

    public NoSerialInventoryBusiness(Session session) {
        this.session = session;
        tModel = new NoSerialInventory();
        tDAO = noSerialInventoryDAO;
    }
}
