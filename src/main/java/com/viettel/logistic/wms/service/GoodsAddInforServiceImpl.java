/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsAddInforDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
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
 * @since 22-Apr-15 7:11 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.GoodsAddInforService")
public class GoodsAddInforServiceImpl implements GoodsAddInforService {

    @Autowired
    BaseFWServiceInterface goodsAddInforBusiness;

    @Override
    public String updateGoodsAddInfor(GoodsAddInforDTO goodsAddInforDTO) {
        return goodsAddInforBusiness.update(goodsAddInforDTO);
    }

    @Override
    public String deleteGoodsAddInfor(Long id) {
        return goodsAddInforBusiness.delete(id);
    }

    @Override
    public String deleteListGoodsAddInfor(List<GoodsAddInforDTO> goodsAddInforListDTO) {
        return goodsAddInforBusiness.delete(goodsAddInforListDTO);
    }

    @Override
    public GoodsAddInforDTO findGoodsAddInforById(Long id) {
        if (id != null && id > 0) {
            return (GoodsAddInforDTO) goodsAddInforBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<GoodsAddInforDTO> getListGoodsAddInforDTO(GoodsAddInforDTO goodsAddInforDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (goodsAddInforDTO != null) {
            return goodsAddInforBusiness.search(goodsAddInforDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertGoodsAddInfor(GoodsAddInforDTO goodsAddInforDTO) {
        return goodsAddInforBusiness.createObject(goodsAddInforDTO);
    }

    @Override
    public String insertOrUpdateListGoodsAddInfor(List<GoodsAddInforDTO> goodsAddInforDTO) {
        return goodsAddInforBusiness.insertList(goodsAddInforDTO);
    }

    @Override
    public List<String> getSequenseGoodsAddInfor(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return goodsAddInforBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<GoodsAddInforDTO> getListGoodsAddInforByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<GoodsAddInforDTO> lstGoodsAddInfor = new ArrayList<>();
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
        lstGoodsAddInfor = goodsAddInforBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstGoodsAddInfor;
    }

}
