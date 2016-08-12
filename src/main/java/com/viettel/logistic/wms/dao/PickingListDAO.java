/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.PickingList;
import com.viettel.vfw5.base.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 4:02 PM
 */
@Repository("pickingListDAO")
public class PickingListDAO extends BaseFWDAOImpl<PickingList, Long> {

    public PickingListDAO() {
        this.model = new PickingList();
    }

    public PickingListDAO(Session session) {
        this.session = session;
    }
}
