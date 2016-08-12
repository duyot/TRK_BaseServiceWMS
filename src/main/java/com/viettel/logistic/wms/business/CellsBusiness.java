/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.CellsBusinessInterface;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import com.viettel.logistic.wms.dto.CellsDTO;
import com.viettel.logistic.wms.model.Cells;
import com.viettel.logistic.wms.dao.CellsDAO;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.model.Goods;
import com.viettel.vfw5.base.dto.ResultDTO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@Service("cellsBusiness")
@Transactional
public class CellsBusiness extends BaseFWServiceImpl<CellsDAO, CellsDTO, Cells> implements CellsBusinessInterface{
	
    @Autowired
    private CellsDAO cellsDAO;

    public CellsBusiness() {
        tModel = new Cells();
        tDAO = cellsDAO;
    }
    @Override
    public CellsDAO gettDAO() {
        return cellsDAO;
    }
    
    public CellsBusiness(Session session) {
        this.session = session;
        tModel = new Cells();
        tDAO = cellsDAO;
    }
    
    @Override
    public List<CellsDTO> getListCells(GoodsDTO goodsDTO, Long stockId, Long goodsPackingId) {  
        Goods goods = goodsDTO.toModel();
        List<Cells> lstCells = gettDAO().getListCells(goods, stockId, goodsPackingId);
        return convertListModeltoDTO(lstCells);
    }

    @Override
    public ResultDTO insertListCells(List<CellsDTO> lstCells, String stockId) {
        return gettDAO().insertListCells(convertListDTOtoModel(lstCells),stockId);
    }
}


