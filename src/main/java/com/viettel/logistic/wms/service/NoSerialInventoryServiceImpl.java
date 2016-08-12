/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.NoSerialInventoryDTO;
import com.viettel.logistic.wms.dto.SerialInventoryDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author thienng
 * @version 1.0
 * @since 5/10/2016 4:47 PM
 */                              
@WebService(endpointInterface = "com.viettel.logistic.wms.service.NoSerialInventoryService")
public class NoSerialInventoryServiceImpl implements NoSerialInventoryService {

    @Autowired
    BaseFWServiceInterface noSerialInventoryBusiness;

    @Override
    public String updateNoSerialInventory(NoSerialInventoryDTO noSerialInventoryDTO) {
        return noSerialInventoryBusiness.update(noSerialInventoryDTO);
    }

    @Override
    public String deleteNoSerialInventory(Long id) {
        return noSerialInventoryBusiness.delete(id);
    }

    @Override
    public String deleteListNoSerialInventory(List<NoSerialInventoryDTO> noSerialInventoryListDTO) {
        return noSerialInventoryBusiness.delete(noSerialInventoryListDTO);
    }

    @Override
    public NoSerialInventoryDTO findNoSerialInventoryById(Long id) {
        if (id != null && id > 0) {
            return (NoSerialInventoryDTO) noSerialInventoryBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<NoSerialInventoryDTO> getListNoSerialInventoryDTO(NoSerialInventoryDTO noSerialInventoryDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (noSerialInventoryDTO != null) {
            return noSerialInventoryBusiness.search(noSerialInventoryDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public String insertNoSerialInventory(NoSerialInventoryDTO noSerialInventoryDTO) {
        return noSerialInventoryBusiness.create(noSerialInventoryDTO);
    }

    @Override
    public String insertOrUpdateListNoSerialInventory(List<NoSerialInventoryDTO> noSerialInventoryDTO) {
        return noSerialInventoryBusiness.insertList(noSerialInventoryDTO);
    }

    @Override
    public List<String> getSequense(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return noSerialInventoryBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<NoSerialInventoryDTO> getListNoSerialInventoryByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<NoSerialInventoryDTO> lstGoods = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
//                con.setField(StringUtils.formatFunction("trunc", con.getField()));
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
        lstGoods = noSerialInventoryBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstGoods;
    }

}
