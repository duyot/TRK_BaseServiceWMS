/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.StockDTO;
import com.viettel.logistic.wms.model.Stock;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.List;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author thieulq1
 * @version 1.0
 * @since 06-Apr-15 5:28 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.StockService")
public class StockServiceImpl implements StockService {

    @Autowired
    BaseFWServiceInterface stockBusiness;

    @Override
    public List<StockDTO> getAllStock() {
        return stockBusiness.getAll();
    }

    @Override
    public String updateStock(StockDTO stockDTO) {
        return stockBusiness.update(stockDTO);
    }

    @Override
    public String deleteStock(Long id) {
        return stockBusiness.delete(id);
    }

    @Override
    public String deleteListStock(List<StockDTO> stockListDTO) {
        return stockBusiness.delete(stockListDTO);
    }

    @Override
    public StockDTO findStockById(Long id) {
        if (id != null && id > 0) {
            Stock stock = (Stock) stockBusiness.findById(id);
            if (stock == null) {
                return null;
            }
            return (StockDTO) stock.toDTO();
        }
        return null;
    }

    @Override
    public List<StockDTO> getListStockDTO(StockDTO stockDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (stockDTO != null) {
            return stockBusiness.search(stockDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

//    @Override
//    public ResultDTO insertStock(StockDTO stockDTO) {
//        return stockBusiness.createObject(stockDTO);
//    }
    @Override
    public ResultDTO insertStock(StockDTO stockDTO) {
        ResultDTO resultDTO = new ResultDTO();
        Class<?> c = stockDTO.getClass();
        HashMap<String, String> hasmap = new HashMap<>();
        Stock stock = new Stock();
        String[] lstColumnUnique = stock.getUniqueColumn();
        for (int i = 0; i < lstColumnUnique.length; i++) {
            try {
                Method method = c.getMethod(DataUtil.getGetterOfColumn(lstColumnUnique[i]));
                try {
                    hasmap.put(lstColumnUnique[i], (String) method.invoke(stockDTO));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(StockServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException | SecurityException ex) {
                Logger.getLogger(StockServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Boolean check = stockBusiness.isDuplicate(null, hasmap);
            if (!check) {
                resultDTO = stockBusiness.createObject(stockDTO);
                resultDTO.setKey(ParamUtils.SUCCESS);
            } else {

                resultDTO.setKey("ERROR_EXISTED");

            }
        } catch (Exception e) {
            resultDTO.setKey("ERROR_SYSTEM");
        }
        return resultDTO;
    }

    @Override
    public String getCountStockDTO(StockDTO stockDTO) {
        long count = stockBusiness.count(stockDTO);
        String returnValue = String.valueOf(count);
        return returnValue;
    }

    @Override
    public List<StockDTO> getListStockByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<StockDTO> lstStock = new ArrayList<>();
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
        lstStock = stockBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstStock;
    }
}
