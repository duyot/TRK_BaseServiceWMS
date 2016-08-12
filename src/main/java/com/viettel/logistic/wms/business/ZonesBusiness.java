/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.ZonesDTO;
import com.viettel.logistic.wms.model.Zones;
import com.viettel.logistic.wms.dao.ZonesDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 13-Apr-15 2:43 PM
 */
@Service("zonesBusiness")
@Transactional
public class ZonesBusiness extends BaseFWServiceImpl<ZonesDAO, ZonesDTO, Zones> {
	
    @Autowired
    private ZonesDAO zonesDAO;

    public ZonesBusiness() {
        tModel = new Zones();
        tDAO = zonesDAO;
    }
    @Override
    public ZonesDAO gettDAO() {
        return zonesDAO;
    }
    
    public ZonesBusiness(Session session) {
        this.session = session;
        tModel = new Zones();
        tDAO = zonesDAO;
    }
}


