/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.CardStockInforDTO;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.logistic.wms.dto.StockTransDetailDTO;
import com.viettel.logistic.wms.dto.StockTransInforDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import java.sql.Connection;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author vtsoft
 */
public interface CommonBusinessInterface {

    public List<StockTransInforDTO> getListStockTransInfor(StockTransInforDTO stockTransInforDTO);

    public List<CardStockInforDTO> getListCardStockInfor(List<CardStockInforDTO> lstCardStockInforDTO);

    public ResultDTO insertStockTrans(StockTransDTO stockTransDTO, Connection con);

    //duyot: 11/01/2016: cap nhat syn_trans_code
    public ResultDTO updateSynTransCode(String stockTransId, String synTransCode, Connection connection);

    //duyot: 11/01/2016: cap nhat syn_trans_code

    public ResultDTO updateSynTransCodeUsingSession(StockTransDTO stockTransDTO, Session session);

    public ResultDTO insertStockTransDetail(StockTransDetailDTO stockTransDetailDTO, Connection con);

    public ResultDTO importStockTransSerialConnection(StockTransSerialDTO stockTransSerial, Connection con);

    // cap nhat trang thai stocktrans
    public String updateStockTransByOrdersId(StockTransDTO stockTransDTO, Session session);  

}
