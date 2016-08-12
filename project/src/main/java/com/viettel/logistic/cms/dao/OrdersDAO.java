/*
 * Copyright (C) 2015 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.cms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.cms.model.Orders;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
*
* @author binhnt22@viettel.com.vn
* @version 1.0
* @since Apr 2015
*/
@Repository("ordersDAO")
public class OrdersDAO extends BaseFWDAOImpl<Orders, Long> {

    public OrdersDAO() {
        this.model = new Orders();
    }

    public OrdersDAO(Session session) {
        this.session = session;
    }
}
