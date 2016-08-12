/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.Reason;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:46 PM
 */
@Repository("reasonDAO")
public class ReasonDAO extends BaseFWDAOImpl<Reason, Long> {

    public ReasonDAO() {
        this.model = new Reason();
    }

    public ReasonDAO(Session session) {
        this.session = session;
    }
}
