/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.model.NoSerialInventory;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author thienng1
 * @version 1.0
 * @since 5/10/2016 4:47 PM
 */
@Repository("noSerialInventoryDAO")
public class NoSerialInventoryDAO extends BaseFWDAOImpl<NoSerialInventory, Long> {

    public NoSerialInventoryDAO() {
        this.model = new NoSerialInventory();
    }

    public NoSerialInventoryDAO(Session session) {
        this.session = session;
    }
}
