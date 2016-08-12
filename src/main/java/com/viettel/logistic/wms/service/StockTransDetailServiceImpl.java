

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.business.service.StockTransDetailBusinessInterface;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
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
* @since 22-Apr-15 4:10 PM
*/
@WebService(endpointInterface = "com.viettel.logistic.wms.service.StockTransDetailService")
public class StockTransDetailServiceImpl implements StockTransDetailService {
    @Autowired
    BaseFWServiceInterface stockTransDetailBusiness;
    @Autowired
    StockTransDetailBusinessInterface detailBusinessInterface;

    @Override
    public String updateStockTransDetail(StockTransDetailDTO stockTransDetailDTO) {
        return stockTransDetailBusiness.update(stockTransDetailDTO);
    }

    @Override
    public String deleteStockTransDetail(Long id) {
        return stockTransDetailBusiness.delete(id);
    }

    @Override
    public String deleteListStockTransDetail(List<StockTransDetailDTO> stockTransDetailListDTO) {
        return stockTransDetailBusiness.delete(stockTransDetailListDTO);
    }

    @Override
    public StockTransDetailDTO findStockTransDetailById(Long id) {
        if (id != null && id > 0) {
            return (StockTransDetailDTO)stockTransDetailBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<StockTransDetailDTO> getListStockTransDetailDTO(StockTransDetailDTO stockTransDetailDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
         if (stockTransDetailDTO != null ) {
            return stockTransDetailBusiness.search(stockTransDetailDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertStockTransDetail(StockTransDetailDTO stockTransDetailDTO) {
        return stockTransDetailBusiness.createObject(stockTransDetailDTO);
    }
    @Override
    public String insertOrUpdateListStockTransDetail(List<StockTransDetailDTO> stockTransDetailDTO) {
         return stockTransDetailBusiness.insertList(stockTransDetailDTO);
    }

    @Override
    public List<String> getSequenseStockTransDetail(String seqName, int... size) {
        int number = ( size[0] > 0 ? size[0] : 1 );
       
        return stockTransDetailBusiness.getListSequense(seqName, number);
    }
    
       @Override
    public List<StockTransDetailDTO> getListStockTransDetailByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<StockTransDetailDTO> lstStockTransDetail = new ArrayList<>();
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
        lstStockTransDetail = stockTransDetailBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstStockTransDetail;
    }

    @Override
    public List<StockTransDetailDTO> getListStockTransDetailByOrderId(String orderId) {
        List<StockTransDetailDTO> lstStockGoodsInfor = detailBusinessInterface.getListStockTransDetailByOrderId(orderId);
        return lstStockGoodsInfor;

    }

}
