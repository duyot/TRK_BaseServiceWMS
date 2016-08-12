/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.business.service.StockTransSerialBusinessInterface;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
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
 * @since 29-Apr-15 10:38 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.StockTransSerialService")
public class StockTransSerialServiceImpl implements StockTransSerialService {

    @Autowired
    StockTransSerialBusinessInterface stockTransSerialBusinessInterface;

    @Autowired
    BaseFWServiceInterface stockTransSerialBusiness;

    @Override
    public String updateStockTransSerial(StockTransSerialDTO stockTransSerialDTO) {
        return stockTransSerialBusiness.update(stockTransSerialDTO);
    }

    @Override
    public String deleteStockTransSerial(Long id) {
        return stockTransSerialBusiness.delete(id);
    }

    @Override
    public String deleteListStockTransSerial(List<StockTransSerialDTO> stockTransSerialListDTO) {
        return stockTransSerialBusiness.delete(stockTransSerialListDTO);
    }

    @Override
    public StockTransSerialDTO findStockTransSerialById(Long id) {
        if (id != null && id > 0) {
            return (StockTransSerialDTO) stockTransSerialBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<StockTransSerialDTO> getListStockTransSerialDTO(StockTransSerialDTO stockTransSerialDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (stockTransSerialDTO != null) {
            return stockTransSerialBusiness.search(stockTransSerialDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertStockTransSerial(StockTransSerialDTO stockTransSerialDTO) {
        return stockTransSerialBusiness.createObject(stockTransSerialDTO);
    }

    @Override
    public String insertOrUpdateListStockTransSerial(List<StockTransSerialDTO> stockTransSerialDTO) {
        return stockTransSerialBusiness.insertList(stockTransSerialDTO);
    }

    @Override
    public List<String> getSequenseStockTransSerial(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return stockTransSerialBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<StockTransSerialDTO> getListStockTransSerialByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<StockTransSerialDTO> lstStockTransSerial = new ArrayList<>();
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
        lstStockTransSerial = stockTransSerialBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstStockTransSerial;
    }

    //THienNG1 AddBy23/06/2015
    @Override
    public List<StockTransSerialDTO> getListStockTransSerialBySerial(StockTransSerialDTO stockTransSerialDTO) {
        return stockTransSerialBusinessInterface.getListStockTransSerialBySerial(stockTransSerialDTO);
    }

    @Override
    public List<StockTransSerialDTO> getListStockTransSerialBySerialStrip(StockTransSerialDTO stockTransSerialDTO) {
        return stockTransSerialBusinessInterface.getListStockTransSerialBySerialStrip(stockTransSerialDTO);
    }

    @Override
    public List<StockTransSerialDTO> getListStockTransSerialByOrderId(String orderId) {
        List<StockTransSerialDTO> lstStockGoodsInfor = stockTransSerialBusinessInterface.getListStockTransSerialByOrderId(orderId);
        return lstStockGoodsInfor;
    }

}
