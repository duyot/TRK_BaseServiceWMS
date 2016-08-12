/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.Zones;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 13-Apr-15 2:43 PM
 */
@Repository("zonesDAO")
public class ZonesDAO extends BaseFWDAOImpl<Zones, Long> {

    public ZonesDAO() {
        this.model = new Zones();
    }

    public ZonesDAO(Session session) {
        this.session = session;
    }
}
