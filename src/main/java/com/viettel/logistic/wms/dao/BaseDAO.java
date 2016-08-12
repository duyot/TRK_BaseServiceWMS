/*
 * Copyright (C) 2015 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import java.sql.Date;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author BinhNT22
 * @version: 2.0
 * @param <T> - a Model that extends BaseFWModel
 * @param <ID> - id of the model
 * @since: 31/03/2015
 */
@Repository
public class BaseDAO {

    // Trong truong hop co nhieu data source thi tao SessionFactory moi
    // extend HibernateSessionFactory va khai bao them vao hibernate-config.xml
    long i;
    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Session session;

    public Date getSysDate() throws Exception {
        String queryString = "SELECT sysdate  from dual";
        Query query = getSession().createSQLQuery(queryString);
        return (Date) query.list();
    }
}
