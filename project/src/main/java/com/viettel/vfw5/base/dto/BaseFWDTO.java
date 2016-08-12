/*
 * Copyright (C) 2012 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vfw5.base.dto;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kdvt_binhnt22@viettel.com.vn
 * @version 1.0
 * @since May 2012
 */
public abstract class BaseFWDTO<TBO extends BaseFWModel> implements BaseFWDTOInterface<TBO> {
    protected String defaultSortField = "name";

    public String getDefaultSortField() {
        return defaultSortField;
    }

    public void setDefaultSortField(String defaultSortField) {
        this.defaultSortField = defaultSortField;
    }
    
    @Override
    public PojoDTO toBean() {
        PojoDTO bean = new PojoDTO(getFWModelId().toString(), catchName());
        return bean;
    }
 
    public static List<PojoDTO> convertListFormToBean(List<? extends BaseFWDTO> listForm) {
        List<PojoDTO> lstBean = new ArrayList<PojoDTO>();
        if (listForm != null) {
            for (BaseFWDTOInterface bo : listForm) {
                lstBean.add(bo.toBean());
            }
        }
        return lstBean;
    }

    @Override
    public int compareTo(BaseFWDTOInterface o) {
        return catchName().compareTo(o.catchName());
    }
    
}
