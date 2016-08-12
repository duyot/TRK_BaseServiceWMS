/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.StockGoodsTotalDTO;
import com.viettel.logistic.wms.dto.StockGoodsInforDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author vtsoft
 */
public interface StockGoodsTotalBusinessInterface {
    //public ResultDTO updateStockGoodsTotal(StockGoodsTotalDTO stockGoodsTotalDTO, Double amount, Double amountIssue, String changeDate);    
    public ResultDTO exportStockGoodsTotal(StockGoodsTotalDTO stockGoodsTotalDTO, Double amount, Double amountIssue, String changeDate, Session session);    
    public ResultDTO exportStockGoodsTotalForSyn(StockGoodsTotalDTO stockGoodsTotalDTO, Double amount, Double amountIssue, String changeDate, Session session);    

    public List<StockGoodsTotalDTO> demoGoodsTotal();    

    public ResultDTO importStockGoodsTotal(StockGoodsTotalDTO stockGoodsTotalDTO, Session session);    
    public ResultDTO exportStockGoodsTotal(StockGoodsTotalDTO stockGoodsTotalDTO, Session session); 
    public List<StockGoodsInforDTO> getSumListStockGoods(StockGoodsInforDTO stockGoodsInforDTO);
}
