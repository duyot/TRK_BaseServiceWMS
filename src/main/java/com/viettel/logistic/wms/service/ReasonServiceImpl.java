

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;
import com.viettel.logistic.wms.dto.ReasonDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import java.util.Locale;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author ngocnd6
* @version 1.0
* @since 08-Apr-15 2:46 PM
*/
@WebService(endpointInterface = "com.viettel.logistic.wms.service.ReasonService")
public class ReasonServiceImpl implements ReasonService {
    @Autowired
    BaseFWServiceInterface reasonBusiness;
    
   
    @Override
    public String updateReason(ReasonDTO reasonDTO) {
        return reasonBusiness.update(reasonDTO);
    }

    @Override
    public String deleteReason(Long id) {
        return reasonBusiness.delete(id);
    }

    @Override
    public String deleteListReason(List<ReasonDTO> reasonListDTO) {
        return reasonBusiness.delete(reasonListDTO);
    }

    @Override
    public ReasonDTO findReasonById(Long id) {
        if (id != null && id > 0) {
            return (ReasonDTO)reasonBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<ReasonDTO> getListReasonDTO(ReasonDTO reasonDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
         if (reasonDTO != null ) {
            return reasonBusiness.search(reasonDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertReason(ReasonDTO reasonDTO) {
        return reasonBusiness.createObject(reasonDTO);
    }
}
