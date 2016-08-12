/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsPackingDTO;
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
 * @since 5/20/2015 6:43 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.GoodsPackingService")
public class GoodsPackingServiceImpl implements GoodsPackingService {

    @Autowired
    BaseFWServiceInterface goodsPackingBusiness;

    @Override
    public String updateGoodsPacking(GoodsPackingDTO goodsPackingDTO) {
        return goodsPackingBusiness.update(goodsPackingDTO);
    }

    @Override
    public String deleteGoodsPacking(Long id) {
        return goodsPackingBusiness.delete(id);
    }

    @Override
    public String deleteListGoodsPacking(List<GoodsPackingDTO> goodsPackingListDTO) {
        return goodsPackingBusiness.delete(goodsPackingListDTO);
    }

    @Override
    public GoodsPackingDTO findGoodsPackingById(Long id) {
        if (id != null && id > 0) {
            return (GoodsPackingDTO) goodsPackingBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<GoodsPackingDTO> getListGoodsPackingDTO(GoodsPackingDTO goodsPackingDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (goodsPackingDTO != null) {
            return goodsPackingBusiness.search(goodsPackingDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertGoodsPacking(GoodsPackingDTO goodsPackingDTO) {
        return goodsPackingBusiness.createObject(goodsPackingDTO);
    }

    @Override
    public String insertOrUpdateListGoodsPacking(List<GoodsPackingDTO> goodsPackingDTO) {
        return goodsPackingBusiness.insertList(goodsPackingDTO);
    }

    @Override
    public List<String> getSequenseGoodsPacking(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return goodsPackingBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<GoodsPackingDTO> getListGoodsPackingByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<GoodsPackingDTO> lstGoodsPacking = new ArrayList<>();
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

        lstGoodsPacking = goodsPackingBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstGoodsPacking;
    }

}
