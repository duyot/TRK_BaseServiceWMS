/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.StockGoodsSerialDTO;
import com.viettel.logistic.wms.model.StockGoodsSerial;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;

/**
 *
 * @author TruongBx3
 */
public interface StockGoodsSerialInterface extends BaseFWServiceInterface<StockGoodsSerialDTO, StockGoodsSerial> {

    String counNumberSerial(StockGoodsSerialDTO stockGoodsSerialDTO, String[] arraySerial);
}
