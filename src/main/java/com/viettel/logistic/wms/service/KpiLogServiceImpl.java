/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.KpiLogDTO;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author duyot
 * @version 1.0
 * @since 12/31/2015 3:40 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.KpiLogService")
public class KpiLogServiceImpl implements KpiLogService {

    @Autowired
    BaseFWServiceInterface kpiLogBusiness;
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;

    @Override
    public String updateKpiLog(KpiLogDTO kpiLogDTO) {
        return kpiLogBusiness.update(kpiLogDTO);
    }

    @Override
    public String deleteKpiLog(Long id) {
        return kpiLogBusiness.delete(id);
    }

    @Override
    public String deleteListKpiLog(List<KpiLogDTO> kpiLogListDTO) {
        return kpiLogBusiness.delete(kpiLogListDTO);
    }

    @Override
    public KpiLogDTO findKpiLogById(Long id) {
        if (id != null && id > 0) {
            return (KpiLogDTO) kpiLogBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<KpiLogDTO> getListKpiLogDTO(KpiLogDTO kpiLogDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (kpiLogDTO != null) {
            return kpiLogBusiness.search(kpiLogDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public String insertKpiLog(KpiLogDTO kpiLogDTO) {
        return kpiLogBusiness.create(kpiLogDTO);
    }

    @Override
    public String insertOrUpdateListKpiLog(List<KpiLogDTO> kpiLogDTO) {
        return kpiLogBusiness.insertList(kpiLogDTO);
    }

    @Override
    public List<String> getSequense(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return kpiLogBusiness.getListSequense(seqName, number);
    }

    
}
