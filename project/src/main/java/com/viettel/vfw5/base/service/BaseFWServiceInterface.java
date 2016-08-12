/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.vfw5.base.service;

import com.viettel.vfw5.base.model.BaseFWModel;
import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;

import java.util.List;
import java.util.Locale;

/**
*
* @author kdvt_binhnt22@viettel.com.vn
* @version 1.0
* @since May 2012
*/
public interface BaseFWServiceInterface<TDTO extends BaseFWDTO, TModel extends BaseFWModel> {
    // work with WF3.3+
    public List<ConditionBean> prepareCondition(Locale locale, TDTO tDTO);
    public List<TDTO> getAll();
    public List<TDTO> search(Locale locale, TDTO tDTO,
            int start, int maxResult, String sortType, String sortField);
    public String create(Locale locale, TDTO tDTO);
    public String update(Locale locale, TDTO tDTO);
    public String delete(Long id);
    public String delete(List<TDTO> tDTOOnGrid);
    public TModel findById(Long id);
    public boolean isDuplicate(String name, Long id);
    public long count(Locale locale, TDTO tDTO);
    public List<TDTO> convertListModeltoDTO(List<TModel> listModel);
}