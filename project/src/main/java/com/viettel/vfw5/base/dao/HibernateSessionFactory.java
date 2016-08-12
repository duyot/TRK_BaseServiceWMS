package com.viettel.vfw5.base.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by sadupa on 8/6/14.
 */
public interface HibernateSessionFactory {

    Session getSession();
}
