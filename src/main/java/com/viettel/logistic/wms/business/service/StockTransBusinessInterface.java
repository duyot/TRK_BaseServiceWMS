/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.model.StockTrans;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;

/**
 *
 * @author vtsoft
 */
public interface StockTransBusinessInterface extends BaseFWServiceInterface<StockTransDTO, StockTrans> {

    public List<StockTransDTO> getListStockTrans2Inventory(StockTransDTO stockTransDTO,
            String fromDate, String toDate, String stockTransType);
}
