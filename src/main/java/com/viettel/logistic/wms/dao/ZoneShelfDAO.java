

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.dao;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.ZoneShelf;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
* @author TruongBX3
* @version 1.0
* @since 06-May-15 9:44 PM
*/
@Repository("zoneShelfDAO")
public class ZoneShelfDAO extends BaseFWDAOImpl<ZoneShelf, Long> {

    public ZoneShelfDAO() {
        this.model= new ZoneShelf();
    }

    public ZoneShelfDAO(Session session) {
        this.session = session;
    }
}

