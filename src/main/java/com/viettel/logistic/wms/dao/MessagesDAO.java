/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.Messages;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@Repository("messagesDAO")
public class MessagesDAO extends BaseFWDAOImpl<Messages, Long> {

    public MessagesDAO() {
        this.model = new Messages();
    }

    public MessagesDAO(Session session) {
        this.session = session;
    }

}
