/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.dao;
import com.viettel.logistic.wms.model.KpiLog;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
* @author duyot
* @version 1.0
* @since 12/31/2015 3:40 PM
*/
@Repository("kpiLogDAO")
public class KpiLogDAO extends BaseFWDAOImpl<KpiLog, Long> {

    public KpiLogDAO() {
        this.model= new KpiLog();
    }

    public KpiLogDAO(Session session) {
        this.session = session;
    }
}

