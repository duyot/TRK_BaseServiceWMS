/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.ChangePositionDTO;
import com.viettel.logistic.wms.model.ChangePosition;
import com.viettel.logistic.wms.dao.ChangePositionDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author HungKV
 * @version 1.0
 * @since 6/9/2015 9:40 AM
 */
@Service("changePositionBusiness")
@Transactional
public class ChangePositionBusiness extends BaseFWServiceImpl<ChangePositionDAO, ChangePositionDTO, ChangePosition> {
	
    @Autowired
    private ChangePositionDAO changePositionDAO;

    public ChangePositionBusiness() {
        tModel = new ChangePosition();
        tDAO = changePositionDAO;
    }
    @Override
    public ChangePositionDAO gettDAO() {
        return changePositionDAO;
    }
    
    public ChangePositionBusiness(Session session) {
        this.session = session;
        tModel = new ChangePosition();
        tDAO = changePositionDAO;
    }
}


