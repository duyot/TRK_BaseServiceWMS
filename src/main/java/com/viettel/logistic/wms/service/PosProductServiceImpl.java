/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.PosProductDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import java.util.Locale;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:45 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.PosProductService")
public class PosProductServiceImpl implements PosProductService {

    @Autowired
    BaseFWServiceInterface posProductBusiness;

    @Override
    public String updatePosProduct(PosProductDTO posProductDTO) {
        return posProductBusiness.update(posProductDTO);
    }

    @Override
    public String deletePosProduct(Long id) {
        return posProductBusiness.delete(id);
    }

    @Override
    public String deleteListPosProduct(List<PosProductDTO> posProductListDTO) {
        return posProductBusiness.delete(posProductListDTO);
    }

    @Override
    public PosProductDTO findPosProductById(Long id) {
        if (id != null && id > 0) {
            return (PosProductDTO) posProductBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<PosProductDTO> getListPosProductDTO(PosProductDTO posProductDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (posProductDTO != null) {
            return posProductBusiness.search(posProductDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertPosProduct(PosProductDTO posProductDTO) {
        return posProductBusiness.createObject(posProductDTO);
    }
}
