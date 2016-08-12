/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.ProposedReceiptBussinessInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.ProposedReceiptDTO;
import com.viettel.logistic.wms.model.ProposedReceipt;
import com.viettel.logistic.wms.dao.ProposedReceiptDAO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:44 PM
 */
@Service("proposedReceiptBusiness")
@Transactional
public class ProposedReceiptBusiness extends BaseFWServiceImpl<ProposedReceiptDAO, ProposedReceiptDTO, ProposedReceipt> implements ProposedReceiptBussinessInterface {

    @Autowired
    private ProposedReceiptDAO proposedReceiptDAO;

    public ProposedReceiptBusiness() {
        tModel = new ProposedReceipt();
        tDAO = proposedReceiptDAO;
    }

    @Override
    public ProposedReceiptDAO gettDAO() {
        return proposedReceiptDAO;
    }

    public ProposedReceiptBusiness(Session session) {
        this.session = session;
        tModel = new ProposedReceipt();
        tDAO = proposedReceiptDAO;
    }

    @Override
    public List<ProposedReceiptDTO> getListProposedReceiptByCondition(ProposedReceiptDTO proposedReceiptDTO) {
        List<ProposedReceiptDTO> lstProposedReceiptDTOs = new ArrayList<>();
        lstProposedReceiptDTOs = convertListModeltoDTO(gettDAO().getListProposedReceiptByCondition(proposedReceiptDTO));
        return lstProposedReceiptDTOs;
    }

}
