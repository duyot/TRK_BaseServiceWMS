/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.GoodsDTO;
import com.viettel.logistic.wms.model.Goods;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Ngocnd6
 */
public interface GoodsBusinessInterface extends BaseFWServiceInterface<GoodsDTO, Goods> {

    public ResultDTO synchListGoods(List<GoodsDTO> lstGoods, Session session);

    public ResultDTO insertListGoods(List<GoodsDTO> lstGoods);

    public List<GoodsDTO> getListGoodsWithCustId(String custId);
    
    public List<GoodsDTO> getListGoodsSerialByCustId(String custId, String isSerial, String isSerialStrip);

}
