/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.ZoneShelfDTO;
import com.viettel.logistic.wms.model.ZoneShelf;
import com.viettel.logistic.wms.dao.ZoneShelfDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 06-May-15 9:44 PM
 */
@Service("zoneShelfBusiness")
@Transactional
public class ZoneShelfBusiness extends BaseFWServiceImpl<ZoneShelfDAO, ZoneShelfDTO, ZoneShelf> {
	
    @Autowired
    private ZoneShelfDAO zoneShelfDAO;

    public ZoneShelfBusiness() {
        tModel = new ZoneShelf();
        tDAO = zoneShelfDAO;
    }
    @Override
    public ZoneShelfDAO gettDAO() {
        return zoneShelfDAO;
    }
    
    public ZoneShelfBusiness(Session session) {
        this.session = session;
        tModel = new ZoneShelf();
        tDAO = zoneShelfDAO;
    }
}


