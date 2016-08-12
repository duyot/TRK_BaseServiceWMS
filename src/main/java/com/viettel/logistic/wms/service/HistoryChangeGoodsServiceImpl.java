/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.HistoryChangeGoodsDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author duyot
 * @version 1.0
 * @since 2/3/2016 11:09 AM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.HistoryChangeGoodsService")
public class HistoryChangeGoodsServiceImpl implements HistoryChangeGoodsService {

    @Autowired
    BaseFWServiceInterface historyChangeGoodsBusiness;

    @Override
    public String updateHistoryChangeGoods(HistoryChangeGoodsDTO historyChangeGoodsDTO) {
        return historyChangeGoodsBusiness.update(historyChangeGoodsDTO);
    }

    @Override
    public String deleteHistoryChangeGoods(Long id) {
        return historyChangeGoodsBusiness.delete(id);
    }

    @Override
    public String deleteListHistoryChangeGoods(List<HistoryChangeGoodsDTO> historyChangeGoodsListDTO) {
        return historyChangeGoodsBusiness.delete(historyChangeGoodsListDTO);
    }

    @Override
    public HistoryChangeGoodsDTO findHistoryChangeGoodsById(Long id) {
        if (id != null && id > 0) {
            return (HistoryChangeGoodsDTO) historyChangeGoodsBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<HistoryChangeGoodsDTO> getListHistoryChangeGoodsDTO(HistoryChangeGoodsDTO historyChangeGoodsDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (historyChangeGoodsDTO != null) {
            return historyChangeGoodsBusiness.search(historyChangeGoodsDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public String insertHistoryChangeGoods(HistoryChangeGoodsDTO historyChangeGoodsDTO) {
        return historyChangeGoodsBusiness.create(historyChangeGoodsDTO);
    }

    @Override
    public String insertOrUpdateListHistoryChangeGoods(List<HistoryChangeGoodsDTO> historyChangeGoodsDTO) {
        return historyChangeGoodsBusiness.insertList(historyChangeGoodsDTO);
    }

    @Override
    public List<String> getSequense(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return historyChangeGoodsBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<HistoryChangeGoodsDTO> getListHistoryChangeGoodsByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<HistoryChangeGoodsDTO> lstHistoryChangeGoodsDTOs = new ArrayList<>();
        if (lstCondition != null && lstCondition.size() > 0) {
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
        }
        lstHistoryChangeGoodsDTOs = historyChangeGoodsBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstHistoryChangeGoodsDTOs;
    }

}
