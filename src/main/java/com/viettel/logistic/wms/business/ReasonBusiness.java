/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.ReasonDTO;
import com.viettel.logistic.wms.model.Reason;
import com.viettel.logistic.wms.dao.ReasonDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:46 PM
 */
@Service("reasonBusiness")
@Transactional
public class ReasonBusiness extends BaseFWServiceImpl<ReasonDAO, ReasonDTO, Reason> {
	
    @Autowired
    private ReasonDAO reasonDAO;

    public ReasonBusiness() {
        tModel = new Reason();
        tDAO = reasonDAO;
    }
    @Override
    public ReasonDAO gettDAO() {
        return reasonDAO;
    }
    
    public ReasonBusiness(Session session) {
        this.session = session;
        tModel = new Reason();
        tDAO = reasonDAO;
    }
}


