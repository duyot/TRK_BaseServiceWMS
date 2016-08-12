/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.MessagesBusinessInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dao.MessagesDAO;
import com.viettel.logistic.wms.dto.MessagesDTO;
import com.viettel.logistic.wms.model.Messages;
import com.viettel.vfw5.base.dto.ResultDTO;
import java.util.List;
import org.hibernate.Session;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@Service("messagesBusiness")
@Transactional
public class MessagesBusiness extends BaseFWServiceImpl<MessagesDAO, MessagesDTO, Messages> implements MessagesBusinessInterface {

    @Autowired
    private MessagesDAO messagesDAO;

    public MessagesBusiness() {
        tModel = new Messages();
        tDAO = messagesDAO;
    }

    @Override
    public MessagesDAO gettDAO() {
        return messagesDAO;
    }

    public MessagesBusiness(Session session) {
        this.session = session;
        tModel = new Messages();
        tDAO = messagesDAO;
    }

}
