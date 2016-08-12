/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.HistoryFluctuationsDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import java.util.Locale;
import javax.jws.WebService;
import java.util.ArrayList;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.viettel.vfw5.base.dto.ResultDTO;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:43 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.HistoryFluctuationsService")
public class HistoryFluctuationsServiceImpl implements HistoryFluctuationsService {

    @Autowired
    BaseFWServiceInterface historyFluctuationsBusiness;

    @Override
    public String updateHistoryFluctuations(HistoryFluctuationsDTO historyFluctuationsDTO) {
        return historyFluctuationsBusiness.update(historyFluctuationsDTO);
    }

    @Override
    public String deleteHistoryFluctuations(Long id) {
        return historyFluctuationsBusiness.delete(id);
    }

    @Override
    public String deleteListHistoryFluctuations(List<HistoryFluctuationsDTO> historyFluctuationsListDTO) {
        return historyFluctuationsBusiness.delete(historyFluctuationsListDTO);
    }

    @Override
    public HistoryFluctuationsDTO findHistoryFluctuationsById(Long id) {
        if (id != null && id > 0) {
            return (HistoryFluctuationsDTO) historyFluctuationsBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<HistoryFluctuationsDTO> getListHistoryFluctuationsDTO(HistoryFluctuationsDTO historyFluctuationsDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (historyFluctuationsDTO != null) {
            return historyFluctuationsBusiness.search(historyFluctuationsDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertHistoryFluctuations(HistoryFluctuationsDTO historyFluctuationsDTO) {
        return historyFluctuationsBusiness.createObject(historyFluctuationsDTO);
    }

    @Override
    public String insertOrUpdateListHistoryFluctuations(List<HistoryFluctuationsDTO> historyFluctuationsDTO) {
        return historyFluctuationsBusiness.insertList(historyFluctuationsDTO);
    }

    @Override
    public List<String> getSequenseHistoryFluctuations(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return historyFluctuationsBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<HistoryFluctuationsDTO> getListHistoryFluctuationsByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<HistoryFluctuationsDTO> lstHistoryFluctuations = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER_DOUBLE)) {
                con.setType(ParamUtils.NUMBER_DOUBLE);
            } else {
                String value = "";
                if (con.getOperator().equalsIgnoreCase(ParamUtils.NAME_LIKE)) {
                    value = StringUtils.formatLike(con.getValue());
                    con.setValue(value.toLowerCase());
                    con.setField(StringUtils.formatFunction("lower", con.getField()));
                } else {
                    value = con.getValue();
                    con.setValue(value);
                    con.setField(con.getField());
                }
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));
        }
        lstHistoryFluctuations = historyFluctuationsBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstHistoryFluctuations;
    }

}
