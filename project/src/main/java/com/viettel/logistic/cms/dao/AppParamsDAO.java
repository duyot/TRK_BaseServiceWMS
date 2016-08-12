/*
 * Copyright (C) 2015 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.cms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.cms.model.AppParams;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author truongbx3
 * @version 1.0
 * @since 3/31/2015 2:45 PM
 */
@Repository("appParamsDAO")
public class AppParamsDAO extends BaseFWDAOImpl<AppParams, Long> {

    public AppParamsDAO() {
        this.model = new AppParams();
    }

    public AppParamsDAO(Session session) {
        this.session = session;
    }
}
