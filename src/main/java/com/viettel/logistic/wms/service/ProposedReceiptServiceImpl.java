/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.business.service.ProposedReceiptBussinessInterface;
import com.viettel.logistic.wms.dto.ProposedReceiptDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import com.viettel.vfw5.base.dto.ResultDTO;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:44 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.ProposedReceiptService")
public class ProposedReceiptServiceImpl implements ProposedReceiptService {

    @Autowired
    BaseFWServiceInterface proposedReceiptBusiness;

    @Autowired
    ProposedReceiptBussinessInterface proposedReceiptBussiness2;

    @Override
    public String updateProposedReceipt(ProposedReceiptDTO proposedReceiptDTO) {
        return proposedReceiptBusiness.update(proposedReceiptDTO);
    }

    @Override
    public String deleteProposedReceipt(Long id) {
        return proposedReceiptBusiness.delete(id);
    }

    @Override
    public String deleteListProposedReceipt(List<ProposedReceiptDTO> proposedReceiptListDTO) {
        return proposedReceiptBusiness.delete(proposedReceiptListDTO);
    }

    @Override
    public ProposedReceiptDTO findProposedReceiptById(Long id) {
        if (id != null && id > 0) {
            return (ProposedReceiptDTO) proposedReceiptBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<ProposedReceiptDTO> getListProposedReceiptDTO(ProposedReceiptDTO proposedReceiptDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (proposedReceiptDTO != null) {
            return proposedReceiptBusiness.search(proposedReceiptDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertProposedReceipt(ProposedReceiptDTO proposedReceiptDTO) {
        return proposedReceiptBusiness.createObject(proposedReceiptDTO);
    }

    @Override
    public String insertOrUpdateListProposedReceipt(List<ProposedReceiptDTO> proposedReceiptDTO) {
        return proposedReceiptBusiness.insertList(proposedReceiptDTO);
    }

    @Override
    public List<String> getSequenseProposedReceipt(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return proposedReceiptBusiness.getListSequense(seqName, number);
    }

    //QuyenDM 03/09/2015 Tim kiem phieu de nghi nhan hang
    @Override
    public List<ProposedReceiptDTO> getListProposedReceiptByCondition(ProposedReceiptDTO proposedReceiptDTO) {
        return proposedReceiptBussiness2.getListProposedReceiptByCondition(proposedReceiptDTO);
    }
}
