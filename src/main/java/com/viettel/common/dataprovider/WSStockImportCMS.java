/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.common.dataprovider;

/**
 *
 * @author vtsoft
 */
import com.viettel.logistic.wms.dto.OrderActionDTO;
import com.viettel.logistic.wms.dto.OrderStorageDTO;
import com.viettel.logistic.wms.dto.OrderStorageZoneDTO;
import com.viettel.vwf5.base.servicecaller.Mapper;
import com.viettel.vwf5.base.servicecaller.WebServiceCaller;
import com.viettel.vwf5.base.servicecaller.WebServiceHandler;
import com.viettel.vwf5.base.servicecaller.WsRequestCreator;
import com.viettel.vwf5.base.servicecaller.XStreamStorage;
import com.viettel.vwf5.base.servicecaller.XmlStream;
import com.viettel.logstic.wms.webservice.dto.OrderGoodsDetailDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.BundelUtils;
import com.viettel.wms.dto.StockPlanDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thienng1
 */
public class WSStockImportCMS {

    //Duong dan Websevice
    public static String strWsWMSUrl = BundelUtils.getkey("cms_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:cms=\"http://cms.viettel.vn\"";
    //Url WS Stock
    public static String strWSUrl = strWsWMSUrl + "stockManagementService";

    //Lay toan bo danh sach kho
    public static ResultDTO createStockComand(OrderActionDTO orderActionDTO, List<OrderGoodsDetailDTO> lstOrderGoodsDetailDTO, OrderStorageDTO orderStorageDTO) throws Exception {
        try {
            //Ten ham lay du lieu kho
            String functionWS = "cms:createStockComand";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("arg0", orderActionDTO);
            mapObjects.put("arg1", lstOrderGoodsDetailDTO);
            mapObjects.put("arg2", orderStorageDTO);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, ResultDTO.class, "ns2:resultDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            ResultDTO resultDTO = (ResultDTO) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return resultDTO;

        } catch (Exception e) {
            return null;
        }
    }

    public static ResultDTO createImportStockPlan(OrderActionDTO orderActionDTO, StockPlanDTO stockPlanDTO, List<OrderStorageZoneDTO> lstOrderStorageZoneDTO) throws Exception {
        try {
            //Ten ham lay du lieu kho
            String functionWS = "cms:createStockImportPlan";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("arg0", orderActionDTO);
            mapObjects.put("arg1", stockPlanDTO);
            mapObjects.put("arg2", lstOrderStorageZoneDTO);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, ResultDTO.class, "ns2:resultDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            ResultDTO resultDTO = (ResultDTO) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return resultDTO;

        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
//        OrderActionDTO orderAction = new OrderActionDTO(null, "hongdq", null, null, "30/12/1991", "05/01/1999", null, null, null, null, null, null, null, null, null, null, null, null,null);
//        List<OrderGoodsDetailDTO> lstOrderGoodsDetailDTO = new ArrayList<>();
//        //lstOrderGoodsDetailDTO.add(new OrderGoodsDetailDTO("1", "1", null, "123", null, null, null, null, null, null, null, null));
//        OrderStorageDTO orderStorageDTO = new OrderStorageDTO("10", "91", "30/12/1991", "1000");
//
//        try {
//            WSStockImportCMS.createStockComand(orderAction, lstOrderGoodsDetailDTO, orderStorageDTO);
//        } catch (Exception ex) {
//            Logger.getLogger(WSOrders.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
