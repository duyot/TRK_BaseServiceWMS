/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.CellsDTO;
import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author vtsoft
 */
public interface CellsBusinessInterface {
    //Get list Cells
     public List<CellsDTO> getListCells(GoodsDTO goodsDTO, Long stockId, Long goodsPackingId);
     public ResultDTO insertListCells(List<CellsDTO> lstCells,String stockId);
}
