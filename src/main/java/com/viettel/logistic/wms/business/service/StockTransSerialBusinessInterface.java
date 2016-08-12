/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import java.util.List;

/**
 *
 * @author thienNG1
 */
public interface StockTransSerialBusinessInterface {

    //tim kiem serial neu hang don le
    public List<StockTransSerialDTO> getListStockTransSerialBySerial(StockTransSerialDTO stockTransSerialDTO);

    //tim kiem serial neu hang theo dai
    public List<StockTransSerialDTO> getListStockTransSerialBySerialStrip(StockTransSerialDTO stockTransSerialDTO);
    //thienng1- ly danh sach serial theo yeu cau
    public List<StockTransSerialDTO> getListStockTransSerialByOrderId(String orderId);

}
