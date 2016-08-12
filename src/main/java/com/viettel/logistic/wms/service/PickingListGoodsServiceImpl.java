/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.PickingListGoodsDTO;
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
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 4:07 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.PickingListGoodsService")
public class PickingListGoodsServiceImpl implements PickingListGoodsService {

    @Autowired
    BaseFWServiceInterface pickingListGoodsBusiness;

    @Override
    public String updatePickingListGoods(PickingListGoodsDTO pickingListGoodsDTO) {
        return pickingListGoodsBusiness.update(pickingListGoodsDTO);
    }

    @Override
    public String deletePickingListGoods(Long id) {
        return pickingListGoodsBusiness.delete(id);
    }

    @Override
    public String deleteListPickingListGoods(List<PickingListGoodsDTO> pickingListGoodsListDTO) {
        return pickingListGoodsBusiness.delete(pickingListGoodsListDTO);
    }

    @Override
    public PickingListGoodsDTO findPickingListGoodsById(Long id) {
        if (id != null && id > 0) {
            return (PickingListGoodsDTO) pickingListGoodsBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<PickingListGoodsDTO> getListPickingListGoodsDTO(PickingListGoodsDTO pickingListGoodsDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (pickingListGoodsDTO != null) {
            return pickingListGoodsBusiness.search(pickingListGoodsDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertPickingListGoods(PickingListGoodsDTO pickingListGoodsDTO) {
        return pickingListGoodsBusiness.createObject(pickingListGoodsDTO);
    }

    @Override
    public String insertOrUpdateListPickingListGoods(List<PickingListGoodsDTO> pickingListGoodsDTO) {
        return pickingListGoodsBusiness.insertList(pickingListGoodsDTO);
    }

    @Override
    public List<String> getSequensePickingListGoods(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return pickingListGoodsBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<PickingListGoodsDTO> getListPickingListGoodsByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<PickingListGoodsDTO> lstPickingListGoods = new ArrayList<>();
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
        lstPickingListGoods = pickingListGoodsBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstPickingListGoods;
    }

}
