/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.business.service.CellsBusinessInterface;
import com.viettel.logistic.wms.dto.CellsDTO;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.model.Goods;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.CellsService")
public class CellsServiceImpl implements CellsService {

    @Autowired
    BaseFWServiceInterface cellsBusiness;
    @Autowired
    CellsBusinessInterface cellsBusinessInterface;

    @Override
    public String updateCells(CellsDTO cellsDTO) {
        return cellsBusiness.update(cellsDTO);
    }

    @Override
    public String deleteCells(Long id) {
        return cellsBusiness.delete(id);
    }

    @Override
    public String deleteListCells(List<CellsDTO> cellsListDTO) {
        return cellsBusiness.delete(cellsListDTO);
    }

    @Override
    public CellsDTO findCellsById(Long id) {
        if (id != null && id > 0) {
            return (CellsDTO) cellsBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<CellsDTO> getListCellsDTO(CellsDTO cellsDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (cellsDTO != null) {
            return cellsBusiness.search(cellsDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertCells(CellsDTO cellsDTO) {
        return cellsBusiness.createObject(cellsDTO);
    }

    @Override
    public String insertOrUpdateListCells(List<CellsDTO> cellsListDTO) {
        return cellsBusiness.insertList(cellsListDTO);
    }

    @Override
    public List<String> getSequense(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return cellsBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<CellsDTO> getListCellsByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<CellsDTO> lstCells = new ArrayList<>();
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
        lstCells = cellsBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstCells;
    }
//    Add by ChuDV: 22/05/2015, Lay Cell theo mat hang

    @Override
    public List<CellsDTO> getListCells(GoodsDTO goodsDTO, Long stockId, Long goodsPackingId) {
        List<CellsDTO> lstCellsDTO = new ArrayList();
        lstCellsDTO = cellsBusinessInterface.getListCells(goodsDTO, stockId, goodsPackingId);
        return lstCellsDTO;
    }

    @Override
    public ResultDTO insertListCells(List<CellsDTO> cellsDTO,String stockId) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO = cellsBusinessInterface.insertListCells(cellsDTO,stockId);
        return resultDTO;
    
    }
    
}
