/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.HistoryFluctuationsDTO;
import com.viettel.logistic.wms.model.HistoryFluctuations;
import com.viettel.logistic.wms.dao.HistoryFluctuationsDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:43 PM
 */
@Service("historyFluctuationsBusiness")
@Transactional
public class HistoryFluctuationsBusiness extends BaseFWServiceImpl<HistoryFluctuationsDAO, HistoryFluctuationsDTO, HistoryFluctuations> {
	
    @Autowired
    private HistoryFluctuationsDAO historyFluctuationsDAO;

    public HistoryFluctuationsBusiness() {
        tModel = new HistoryFluctuations();
        tDAO = historyFluctuationsDAO;
    }
    @Override
    public HistoryFluctuationsDAO gettDAO() {
        return historyFluctuationsDAO;
    }
    
    public HistoryFluctuationsBusiness(Session session) {
        this.session = session;
        tModel = new HistoryFluctuations();
        tDAO = historyFluctuationsDAO;
    }
}


