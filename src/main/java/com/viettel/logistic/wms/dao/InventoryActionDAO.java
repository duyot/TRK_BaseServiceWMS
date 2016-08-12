
/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.InventoryAction;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:16 PM
 */
@Repository("inventoryActionDAO")
public class InventoryActionDAO extends BaseFWDAOImpl<InventoryAction, Long> {

    public InventoryActionDAO() {
        this.model = new InventoryAction();
    }

    public InventoryActionDAO(Session session) {
        this.session = session;
    }
}
