/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.PickingListDTO;
import com.viettel.logistic.wms.dto.PickingListGoodsDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author vtsoft
 */
public interface PickingListGoodsBusinessInterface {

   // public ResultDTO updateStockGood(StockGoodsDTO stockGoodsDTO, Double amount, Double amountIssue, String changeDate);    
    public String getListCellCode(PickingListDTO pickingListDTO,PickingListGoodsDTO pickingListGoodsDTO);
}
