/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.cms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.cms.dto.AppParamsDTO;
import com.viettel.logistic.cms.model.AppParams;
import com.viettel.logistic.cms.dao.AppParamsDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author truongbx3
 * @version 1.0
 * @since 3/31/2015 2:45 PM
 */
@Service("appParamsBusiness")
@Transactional
public class AppParamsBusiness extends BaseFWServiceImpl<AppParamsDAO, AppParamsDTO, AppParams> {

    @Autowired
    private AppParamsDAO appParamsDAO;

    public AppParamsBusiness() {
        tModel = new AppParams();
        tDAO = appParamsDAO;
    }
    
    @Override
    public AppParamsDAO gettDAO() {
        return appParamsDAO;
    }
    
    public AppParamsBusiness(Session session, Locale locale) {
        this.session = session;
        this.locale = locale;
        tModel = new AppParams();
        tDAO = appParamsDAO;
    }

}
