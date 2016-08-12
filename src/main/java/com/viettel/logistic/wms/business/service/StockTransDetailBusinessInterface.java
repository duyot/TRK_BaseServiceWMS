/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import java.util.List;

/**
 *
 * @author vtsoft
 */
public interface StockTransDetailBusinessInterface {

    public List<StockTransDetailDTO> getListStockTransDetailByOrderId(String orderId);
}
