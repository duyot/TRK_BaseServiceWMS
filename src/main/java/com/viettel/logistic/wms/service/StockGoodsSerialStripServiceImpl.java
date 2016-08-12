/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.google.common.collect.Lists;
import com.viettel.logistic.wms.business.service.StockGoodsSerialStripServiceInterface;
import com.viettel.logistic.wms.dto.StockGoodsDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialStripDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import javax.jws.WebService;
import java.util.ArrayList;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.Collections;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 16-Apr-15 3:34 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.StockGoodsSerialStripService")
public class StockGoodsSerialStripServiceImpl implements StockGoodsSerialStripService {

    @Autowired
    BaseFWServiceInterface stockGoodsSerialStripBusiness;
    @Autowired
    StockGoodsSerialStripServiceInterface stockGoodsSerialStripServiceInterface;

    @Override
    public String updateStockGoodsSerialStrip(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        return stockGoodsSerialStripBusiness.update(stockGoodsSerialStripDTO);
    }

    @Override
    public String deleteStockGoodsSerialStrip(Long id) {
        return stockGoodsSerialStripBusiness.delete(id);
    }

    @Override
    public String deleteListStockGoodsSerialStrip(List<StockGoodsSerialStripDTO> stockGoodsSerialStripListDTO) {
        return stockGoodsSerialStripBusiness.delete(stockGoodsSerialStripListDTO);
    }

    @Override
    public StockGoodsSerialStripDTO findStockGoodsSerialStripById(Long id) {
        if (id != null && id > 0) {
            return (StockGoodsSerialStripDTO) stockGoodsSerialStripBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialStripDTO(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (stockGoodsSerialStripDTO != null) {
            return stockGoodsSerialStripBusiness.search(stockGoodsSerialStripDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertStockGoodsSerialStrip(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        return stockGoodsSerialStripBusiness.createObject(stockGoodsSerialStripDTO);
    }

    @Override
    public String insertOrUpdateListStockGoodsSerialStrip(List<StockGoodsSerialStripDTO> stockGoodsSerialStripDTO) {
        return stockGoodsSerialStripBusiness.insertList(stockGoodsSerialStripDTO);
    }

    @Override
    public List<String> getSequenseStockGoodsSerialStrip(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return stockGoodsSerialStripBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialStripByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<StockGoodsSerialStripDTO> lstStockGoodsSerialStrip = new ArrayList<>();
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
        lstStockGoodsSerialStrip = stockGoodsSerialStripBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);

        return lstStockGoodsSerialStrip;
    }

    @Override
    public List<StockGoodsSerialInforDTO> getListStockGoodsSerialInfor(StockGoodsSerialStripDTO stockGoodsSerialStripDTO, String isSerialStrip) {
        List<StockGoodsSerialInforDTO> lstStockGoodsSerialInfor = stockGoodsSerialStripServiceInterface.getListStockGoodsSerialInfor(stockGoodsSerialStripDTO, isSerialStrip);
        return lstStockGoodsSerialInfor;
    }

    @Override
    public List<StockGoodsInforDTO> getListStockGoodsInforByZone(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsInforDTO> lstStockGoodsInfor = stockGoodsSerialStripServiceInterface.getListStockGoodsInforByZone(stockGoodsSerialStripDTO);
        return lstStockGoodsInfor;
    }

    @Override
    public List<StockGoodsSerialStripDTO> getListStockGoodsSerialBySerial(StockGoodsSerialStripDTO stockGoodsSerialStripDTO) {
        List<StockGoodsSerialStripDTO> lstStockGoodsInfor = stockGoodsSerialStripServiceInterface.getListStockGoodsSerialBySerial(stockGoodsSerialStripDTO);
        return lstStockGoodsInfor;
    }

    @Override
    public ResultDTO checkSerialExitsInSerialStrip(List<StockGoodsSerialStripDTO> lstStockGoodsSerialStrip) {
        ResultDTO result = new ResultDTO();
        result.setMessage(ParamUtils.SUCCESS);
        for (StockGoodsSerialStripDTO item : lstStockGoodsSerialStrip) {
            long fromSerial = Long.parseLong(item.getFromSerial());
            long toSerial = Long.parseLong(item.getToSerial());

            List<StockGoodsSerialStripDTO> lstItemGoodsSerialStrip = stockGoodsSerialStripServiceInterface.getListStockGoodsSerialStrip(item);
            if (lstItemGoodsSerialStrip == null || lstItemGoodsSerialStrip.isEmpty()) {
                result.setMessage("Ma hang hoa "
                        + item.getGoodsCode() + " from serial "
                        + String.valueOf(fromSerial) + " toSerial "
                        + String.valueOf(toSerial) + " khong con trong kho");
                return result;
            }
//            ObjectComparator comparator = new ObjectComparator();
//
//            Collections.sort(lstItemGoodsSerialStrip, comparator);

            int i = lstItemGoodsSerialStrip.size();
//            min serialStrip
            StockGoodsSerialStripDTO firstSerialStrip = lstItemGoodsSerialStrip.get(0);
//            max serialStrip
            StockGoodsSerialStripDTO lastSerialStrip = lstItemGoodsSerialStrip.get(i - 1);

            if (fromSerial < Long.parseLong(firstSerialStrip.getFromSerial()) || toSerial > Long.parseLong(lastSerialStrip.getToSerial())) {
                result.setMessage("Ma hang hoa "
                        + item.getGoodsCode() + " from serial "
                        + String.valueOf(fromSerial) + " toSerial "
                        + String.valueOf(toSerial) + " serial khong du dap ung");
                return result;
            }
            StockGoodsSerialStripDTO preSerialStrip = null;
            for (int j = 0; j < i; j++) {
                if (preSerialStrip == null) {
                    preSerialStrip = lstItemGoodsSerialStrip.get(j);
                    continue;
                }
                StockGoodsSerialStripDTO currentSerialStrip = lstItemGoodsSerialStrip.get(j);
                if ((Long.parseLong(currentSerialStrip.getFromSerial()) - Long.parseLong(preSerialStrip.getToSerial())) != 1) {
                    result.setMessage("Ma hang hoa "
                            + item.getGoodsCode() + " from serial "
                            + String.valueOf(fromSerial) + " toSerial "
                            + String.valueOf(toSerial) + " serial khong du dap ung");
                    return result;
                }
            }

        }
        return result;
    }

    public class ObjectComparator implements Comparator<StockGoodsSerialStripDTO> {

        public int compare(StockGoodsSerialStripDTO item1, StockGoodsSerialStripDTO item2) {

            Long fromSerial1 = Long.parseLong(item1.getFromSerial());
            Long fromSerial2 = Long.parseLong(item2.getFromSerial());

            return fromSerial2.compareTo(fromSerial1);
        }

    }
}
