/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.google.common.collect.Lists;
import com.viettel.logistic.wms.business.service.StockGoodsBusinessInterface;
import com.viettel.logistic.wms.dto.StockGoodsDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.List;
import java.util.Locale;
import javax.jws.WebService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 14-Apr-15 11:33 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.StockGoodsService")
public class StockGoodsServiceImpl implements StockGoodsService {

    @Autowired
    BaseFWServiceInterface stockGoodsBusiness;

    @Autowired
    StockGoodsBusinessInterface stockGoodsInterface;

    @Override
    public String updateStockGoods(StockGoodsDTO stockGoodsDTO) {
        return stockGoodsBusiness.update(stockGoodsDTO);
    }

    @Override
    public String deleteStockGoods(Long id) {
        return stockGoodsBusiness.delete(id);
    }

    @Override
    public String deleteListStockGoods(List<StockGoodsDTO> stockGoodsListDTO) {
        return stockGoodsBusiness.delete(stockGoodsListDTO);
    }

    @Override
    public StockGoodsDTO findStockGoodsById(Long id) {
        if (id != null && id > 0) {
            return (StockGoodsDTO) stockGoodsBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<StockGoodsDTO> getListStockGoodsDTO(StockGoodsDTO stockGoodsDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (stockGoodsDTO != null) {
            if(stockGoodsDTO.getPartnerId() == null){
                stockGoodsDTO.setPartnerId("");
            }   
            return stockGoodsBusiness.search(stockGoodsDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertStockGoods(StockGoodsDTO stockGoodsDTO) {
        return stockGoodsBusiness.createObject(stockGoodsDTO);
    }

    @Override
    public String insertOrUpdateListStockGoods(List<StockGoodsDTO> stockGoodsDTO) {
        return stockGoodsBusiness.insertList(stockGoodsDTO);
    }

    @Override
    public List<String> getSequense(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return stockGoodsBusiness.getListSequense(seqName, number);
    }

    //Lay danh danh sach kho theo KH, kho, mat hang, trang thai hang
    @Override
    public List<StockGoodsInforDTO> getSumListStockGoods(StockGoodsInforDTO stockGoodsInforDTO) {
        return stockGoodsInterface.getSumListStockGoods(stockGoodsInforDTO);
    }

    //Lay danh sach stockGoods theo conditionBean
    @Override
    public List<StockGoodsDTO> getListStockGoodsByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<StockGoodsDTO> lstStockGoodsDTOs = Lists.newArrayList();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else {
                String value = StringUtils.formatLike(con.getValue());
                value.toLowerCase();
                con.setValue(value);
                con.setField(StringUtils.formatFunction("lower", con.getField()));
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));

        }
        lstStockGoodsDTOs = stockGoodsBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstStockGoodsDTOs;
    }

    @Override
    public List<StockGoodsSerialInforDTO> getStockGoodsInforInventory(StockGoodsSerialInforDTO stockGoodsSerialInforDTO, String isSerial, String isExportSerial) {
        return stockGoodsInterface.getStockGoodsInforInventory(stockGoodsSerialInforDTO, isSerial, isExportSerial);
    }

}
