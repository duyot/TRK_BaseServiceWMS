/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.StockGoodsSerialErrorDTO;
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
 * @since 29-Apr-15 8:41 AM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.StockGoodsSerialErrorService")
public class StockGoodsSerialErrorServiceImpl implements StockGoodsSerialErrorService {

    @Autowired
    BaseFWServiceInterface stockGoodsSerialErrorBusiness;

    @Override
    public String updateStockGoodsSerialError(StockGoodsSerialErrorDTO stockGoodsSerialErrorDTO) {
        return stockGoodsSerialErrorBusiness.update(stockGoodsSerialErrorDTO);
    }

    @Override
    public String deleteStockGoodsSerialError(Long id) {
        return stockGoodsSerialErrorBusiness.delete(id);
    }

    @Override
    public String deleteListStockGoodsSerialError(List<StockGoodsSerialErrorDTO> stockGoodsSerialErrorListDTO) {
        return stockGoodsSerialErrorBusiness.delete(stockGoodsSerialErrorListDTO);
    }

    @Override
    public StockGoodsSerialErrorDTO findStockGoodsSerialErrorById(Long id) {
        if (id != null && id > 0) {
            return (StockGoodsSerialErrorDTO) stockGoodsSerialErrorBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<StockGoodsSerialErrorDTO> getListStockGoodsSerialErrorDTO(StockGoodsSerialErrorDTO stockGoodsSerialErrorDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (stockGoodsSerialErrorDTO != null) {
            return stockGoodsSerialErrorBusiness.search(stockGoodsSerialErrorDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertStockGoodsSerialError(StockGoodsSerialErrorDTO stockGoodsSerialErrorDTO) {
        return stockGoodsSerialErrorBusiness.createObject(stockGoodsSerialErrorDTO);
    }

    @Override
    public String insertOrUpdateListStockGoodsSerialError(List<StockGoodsSerialErrorDTO> stockGoodsSerialErrorDTO) {
        return stockGoodsSerialErrorBusiness.insertList(stockGoodsSerialErrorDTO);
    }

    @Override
    public List<String> getSequenseStockGoodsSerialError(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return stockGoodsSerialErrorBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<StockGoodsSerialErrorDTO> getListStockGoodsSerialErrorByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<StockGoodsSerialErrorDTO> lstStockGoodsSerialError = new ArrayList<>();
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
        lstStockGoodsSerialError = stockGoodsSerialErrorBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstStockGoodsSerialError;
    }

}
