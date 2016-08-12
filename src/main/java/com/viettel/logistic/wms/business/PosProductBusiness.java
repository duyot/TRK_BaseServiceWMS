/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.PosProductDTO;
import com.viettel.logistic.wms.model.PosProduct;
import com.viettel.logistic.wms.dao.PosProductDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:45 PM
 */
@Service("posProductBusiness")
@Transactional
public class PosProductBusiness extends BaseFWServiceImpl<PosProductDAO, PosProductDTO, PosProduct> {
	
    @Autowired
    private PosProductDAO posProductDAO;

    public PosProductBusiness() {
        tModel = new PosProduct();
        tDAO = posProductDAO;
    }
    @Override
    public PosProductDAO gettDAO() {
        return posProductDAO;
    }
    
    public PosProductBusiness(Session session) {
        this.session = session;
        tModel = new PosProduct();
        tDAO = posProductDAO;
    }
}


