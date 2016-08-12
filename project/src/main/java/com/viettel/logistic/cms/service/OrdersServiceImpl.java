/*
* Copyright (C) 2015 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.cms.service;

import com.viettel.logistic.cms.dto.OrdersDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import java.util.Locale;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
*
* @author binhnt22@viettel.com.vn
* @version 1.0
* @since Apr 2015
*/
@WebService(endpointInterface = "com.viettel.logistic.cms.service.OrdersService")
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    BaseFWServiceInterface ordersBusiness;
    
    @Override
    public List<OrdersDTO> getAll() {
        return ordersBusiness.getAll();
    }

    @Override
    public String create(Locale locale, OrdersDTO tDTO) {
        return ordersBusiness.create(locale, tDTO);
    }

    @Override
    public String update(Locale locale, OrdersDTO tDTO) {
        return ordersBusiness.update(locale, tDTO);
    }

    @Override
    public String delete(Long id) {
        return delete(id);
    }

    @Override
    public String delete(List<OrdersDTO> tDTOOnTable) {
        return delete(tDTOOnTable);
    }

    @Override
    public OrdersDTO findById(Long id) {
        if (id != null && id > 0) {
            return (OrdersDTO)ordersBusiness.findById(id).toDTO();
        }
        return null;
    }
}
