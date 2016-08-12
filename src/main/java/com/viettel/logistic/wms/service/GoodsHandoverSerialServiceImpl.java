/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsHandoverSerialDTO;
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
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:41 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.GoodsHandoverSerialService")
public class GoodsHandoverSerialServiceImpl implements GoodsHandoverSerialService {

    @Autowired
    BaseFWServiceInterface goodsHandoverSerialBusiness;

    @Override
    public String updateGoodsHandoverSerial(GoodsHandoverSerialDTO goodsHandoverSerialDTO) {
        return goodsHandoverSerialBusiness.update(goodsHandoverSerialDTO);
    }

    @Override
    public String deleteGoodsHandoverSerial(Long id) {
        return goodsHandoverSerialBusiness.delete(id);
    }

    @Override
    public String deleteListGoodsHandoverSerial(List<GoodsHandoverSerialDTO> goodsHandoverSerialListDTO) {
        return goodsHandoverSerialBusiness.delete(goodsHandoverSerialListDTO);
    }

    @Override
    public GoodsHandoverSerialDTO findGoodsHandoverSerialById(Long id) {
        if (id != null && id > 0) {
            return (GoodsHandoverSerialDTO) goodsHandoverSerialBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<GoodsHandoverSerialDTO> getListGoodsHandoverSerialDTO(GoodsHandoverSerialDTO goodsHandoverSerialDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (goodsHandoverSerialDTO != null) {
            return goodsHandoverSerialBusiness.search(goodsHandoverSerialDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertGoodsHandoverSerial(GoodsHandoverSerialDTO goodsHandoverSerialDTO) {
        return goodsHandoverSerialBusiness.createObject(goodsHandoverSerialDTO);
    }

    @Override
    public String insertOrUpdateListGoodsHandoverSerial(List<GoodsHandoverSerialDTO> goodsHandoverSerialDTO) {
        return goodsHandoverSerialBusiness.insertList(goodsHandoverSerialDTO);
    }

    @Override
    public List<String> getSequenseGoodsHandoverSerial(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return goodsHandoverSerialBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<GoodsHandoverSerialDTO> getListGoodsHandoverSerialByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<GoodsHandoverSerialDTO> lstGoodsHandoverSerial = new ArrayList<>();
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
        lstGoodsHandoverSerial = goodsHandoverSerialBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstGoodsHandoverSerial;
    }

}
