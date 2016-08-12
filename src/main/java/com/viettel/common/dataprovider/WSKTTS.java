/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.common.dataprovider;


import com.viettel.logistic.common.logs.KPILogger;
import com.viettel.logistic.wms.dto.BillStock;
import com.viettel.logistic.wms.dto.GoodsInTicketDTO;
import com.viettel.logistic.wms.dto.InputDTO;
import com.viettel.logistic.wms.dto.ResultKTTSDTO;
import com.viettel.logistic.wms.dto.StockTransSerialDTO;
import com.viettel.vfw5.base.utils.BundelUtils;
import com.viettel.vwf5.base.servicecaller.Mapper;
import com.viettel.vwf5.base.servicecaller.WebServiceHandler;
import com.viettel.vwf5.base.servicecaller.WsRequestCreator;
import com.viettel.vwf5.base.servicecaller.XStreamStorage;
import com.viettel.vwf5.base.servicecaller.XmlStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DuyOt
 */
public class WSKTTS {

    InputDTO inputDTO;
    //Duong dan webservices
     public static String strWsKTTSUrl = BundelUtils.getkey("ktts_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:web=\"http://webservice.qldtktts.viettel.com/\"";
    //Url Webservice Customers

    //Lay danh sach hang hoa dong bo tu he thong BCCS
    public static ResultKTTSDTO synImportStockKTTS(BillStock billStock) {
        try {
            //duyot: bo sung log goi ktts
            System.out.println("-----Bat dau goi ham dong bo KTTS voi ma yeu cau: "+ billStock.getOrderCode());
            KPILogger.createLogs("-----Bat dau goi ham dong bo KTTS voi ma yeu cau: "+ billStock.getOrderCode());
            //
            String functionWS = "web:transStock";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWsKTTSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("bill", billStock);
            
            wsConfig.setBodyArgAlias(mapObjects);
            List<XStreamStorage> parseObject = new ArrayList<>();
            //
            Mapper.alias(parseObject, ResultKTTSDTO.class, "return");
           
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            
            ResultKTTSDTO returnDTO = (ResultKTTSDTO) WebServiceHandler.wsServiceHandlerKTTS(strWSConfig, xmlStream);
            
            KPILogger.createLogs("----Ket thuc goi KTTS-----");
            System.out.println("---Ket thuc goi KTTS----");
            return returnDTO;
        } catch (Exception e) {
            System.out.println("-----Loi goi dong bo----");
            System.out.println("MA YEU CAU: "+ billStock.getOrderCode());
            System.out.println("NOI DUNG LOI: "+ e.toString());
            KPILogger.createLogs("Loi dong bo: " + billStock.getOrderCode() + " thong tin loi: "+ e.toString());
            return null;
        }
    }
    
    public static void main(String[] args) {
        BillStock temp = new BillStock();
        List<GoodsInTicketDTO> lstGoods = new  ArrayList<>();
        List<StockTransSerialDTO> lstSerial = new ArrayList<>();
        temp.setIeExpectDate("05/09/2015 15:37:56");
        temp.setInputType("1");
        temp.setOrderCode("YCNKTCT/15/000522");
        temp.setOrderType("1");
        temp.setStockCode("LBH");
        temp.setTransCode("IMP61878");
        temp.setStockTransDate("05/09/2015 15:37:56");
        
        //
        GoodsInTicketDTO goods = new GoodsInTicketDTO();
        goods.setGoodsCode("001823");
        goods.setGoodsName("001823");
        goods.setGoodsState("1");
        goods.setAmountOrder("1");
        goods.setAmountReal("1");
        goods.setGoodsUnitName("Chi?c");
        
        StockTransSerialDTO serial = new StockTransSerialDTO();
        serial.setFromSerial("12345");
        serial.setToSerial("12345");
        lstSerial.add(serial);
        goods.setLstSerial(lstSerial);
//        GoodsInTicketDTO goodsA = new GoodsInTicketDTO();
//        goodsA.setGoodsCode("001272");
//        goodsA.setGoodsName("001272");
//        goodsA.setGoodsState("1");
//        goodsA.setAmountOrder("1");
//        goodsA.setAmountReal("1");
//        goodsA.setGoodsUnitName("Chi?c");
        
        lstGoods.add(goods);
//        lstGoods.add(goodsA);
        temp.setLstGoods(lstGoods);
        
        synImportStockKTTS(temp);
        
    }
    
}
