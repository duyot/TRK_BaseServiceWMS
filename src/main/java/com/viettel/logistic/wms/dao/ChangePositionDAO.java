/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.ChangePosition;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author HungKV
 * @version 1.0
 * @since 6/9/2015 9:40 AM
 */
@Repository("changePositionDAO")
public class ChangePositionDAO extends BaseFWDAOImpl<ChangePosition, Long> {

    public ChangePositionDAO() {
        this.model = new ChangePosition();
    }

    public ChangePositionDAO(Session session) {
        this.session = session;
    }
}
