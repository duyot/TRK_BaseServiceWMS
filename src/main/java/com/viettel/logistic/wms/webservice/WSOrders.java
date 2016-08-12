/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.webservice;

import com.viettel.logistic.wms.dto.OrderActionDTO;
import com.viettel.logstic.wms.webservice.dto.OrderGoodsDetailDTO;
import com.viettel.logstic.wms.webservice.dto.OrderGoodsDetailSerialDTO;
import com.viettel.logstic.wms.webservice.dto.OrderGoodsLocationDTO;
import com.viettel.logstic.wms.webservice.dto.OrdersDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.BundelUtils;
import com.viettel.vwf5.base.servicecaller.Mapper;
import com.viettel.vwf5.base.servicecaller.WebServiceCaller;
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
 * @author vtsoft
 */
public class WSOrders {

    List<OrdersDTO> lstOrdersDTO;
    List<OrdersDTO> lstOrdersDTOConditionBean;
    //Duong dan Websevice
    public static String strWsWMSUrl = BundelUtils.getkey("cms_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:cms=\"http://cms.viettel.vn\"";
    //Url WS Stock
    public static String strWSUrl = strWsWMSUrl + "ordersService";
    public static String strWSStockManagementUrl = strWsWMSUrl + "stockManagementService";

    //Lay toan bo danh sach kho
    public static List<OrdersDTO> getListOrderDTO(OrdersDTO ordersDTO, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            //Ten ham lay du lieu kho
            String functionWS = "cms:getListOrdersDTO";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("ordersDTO", ordersDTO);
            mapObjects.put("rowStart", rowStart);
            mapObjects.put("maxRow", maxRow);
            mapObjects.put("sortType", sortType);
            mapObjects.put("sortFieldList", sortFieldList);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSOrders.class, "lstOrdersDTO")
                    .alias(parseObject, WSOrders.class, "ns2:getListOrdersDTOResponse")
                    .alias(parseObject, OrdersDTO.class,"ns2:ordersDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSOrders wsList = (WSOrders) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstOrdersDTO;

        } catch (Exception e) {
            throw e;
        }
    }

    //Insert Order
    //Update Stock
    public static void updateOrder(OrdersDTO orderDTO) {
        String functionWS = "cms:updateOrders";
        WsRequestCreator wsConfig = new WsRequestCreator();
        wsConfig.setWsAddress(strWSUrl);
        wsConfig.setServiceName(functionWS);
        wsConfig.setTargetNameSpace(targetNamePath);

        Map<String, Object> mapObjects = new HashMap<>();
        mapObjects.put("orderDTO", orderDTO);

        wsConfig.setBodyArgAlias(mapObjects);
        try {
            String message = WebServiceHandler.webServiceCaller(wsConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String updateOrders(OrdersDTO orderDTO) {
        try {
            String functionWS = "cms:updateOrders";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("ordersDTO", orderDTO);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, String.class, "ns2:message");
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            String message = (String) WebServiceHandler.wsServiceHandler(strWSConfig, xmlStream);
            return message;

        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
    }

    //
    public static String updateOrdersAndOrderAction(OrdersDTO orderDTO, OrderActionDTO orderActionDTO) {
        try {
            String functionWS = "cms:updateOrdersAndOrderAction";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("ordersDTO", orderDTO);
            mapObjects.put("orderActionDTO", orderActionDTO);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, String.class, "ns2:message");
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            String message = (String) WebServiceHandler.wsServiceHandler(strWSConfig, xmlStream);
            return message;

        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
    }

    //
    public static OrdersDTO findOrderById(String id) throws Exception {
        try {
            String functionWS = "cms:findOrdersById";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("ordersDTOId", id);

            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, OrdersDTO.class, "ns2:orders");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            OrdersDTO orderDTO = (OrdersDTO) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            return orderDTO;
        } catch (Exception e) {
            return new OrdersDTO();
        }
    }

    // insert list
    public static String insertOrUpdateListOrders(List<OrdersDTO> lstOrdersDTO) {
        try {
            String functionWS = "cms:insertOrUpdateListOrders";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("ordersDTO", lstOrdersDTO);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, String.class, "ns2:insertListOrders");
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            String message = (String) WebServiceHandler.wsServiceHandler(strWSConfig, xmlStream);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
    }

    public static List<OrdersDTO> getListOrdersByCondition(List<ConditionBean> lstCon, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            //Ten ham lay du lieu kho
            String functionWS = "cms:getListOrdersByCondition";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("lstCondition", lstCon);
            mapObjects.put("rowStart", rowStart);
            mapObjects.put("maxRow", maxRow);
            mapObjects.put("sortType", sortType);
            mapObjects.put("sortFieldList", sortFieldList);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSOrders.class, "lstOrdersDTOConditionBean")
                    .alias(parseObject, WSOrders.class, "ns2:getListOrdersByConditionResponse")
                    .alias(parseObject, OrdersDTO.class, "ns2:OrdersDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSOrders wsList = (WSOrders) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstOrdersDTOConditionBean;

        } catch (Exception e) {
            return new ArrayList<OrdersDTO>();
        }
    }

    // lay thong tin order, detail, detail serial and location
    public static List<OrdersDTO> getListOrdersAndDetailsByCondition(List<ConditionBean> lstCon, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            //Ten ham lay du lieu kho
            String functionWS = "cms:getListOrdersAndDetailsByCondition";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("lstCondition", lstCon);
            mapObjects.put("rowStart", rowStart);
            mapObjects.put("maxRow", maxRow);
            mapObjects.put("sortType", sortType);
            mapObjects.put("sortFieldList", sortFieldList);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSOrders.class, "lstOrdersDTOConditionBean")
                    .alias(parseObject, WSOrders.class, "ns2:getListOrdersAndDetailsByConditionResponse")
                    .alias(parseObject, OrdersDTO.class, "ns2:OrdersDTO");
            Mapper.addImplicitCollection(parseObject, OrdersDTO.class, "lstOrderGoodsLocationDTO");
            Mapper.alias(parseObject, OrderGoodsLocationDTO.class, "lstOrderGoodsLocationDTO");
            //
            Mapper.addImplicitCollection(parseObject, OrderGoodsLocationDTO.class, "lstOrderGoodsDetailDTO");
            Mapper.alias(parseObject, OrderGoodsDetailDTO.class, "lstOrderGoodsDetailDTO");
            //
            Mapper.addImplicitCollection(parseObject, OrderGoodsDetailDTO.class, "lstOrderGoodsDetailSerialDTO");
            Mapper.alias(parseObject, OrderGoodsDetailSerialDTO.class, "lstOrderGoodsDetailSerialDTO");
            //
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSOrders wsList = (WSOrders) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstOrdersDTOConditionBean;

        } catch (Exception e) {
            return new ArrayList<OrdersDTO>();
        }
    }

    public static String deleteListOrders(List<OrdersDTO> lstOrderDTO) {
        try {
            String functionWS = "cmseleteListOrders";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("ordersListDTO", lstOrderDTO);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, String.class, "ns2:message");
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            String message = (String) WebServiceHandler.wsServiceHandler(strWSConfig, xmlStream);
            return message;

        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
    }
//    
//    // xoa kho
//    public static void deleteStock(String id) {
//        String functionWS = "wms:deleteStock";
//        WsRequestCreator wsConfig = new WsRequestCreator();
//        wsConfig.setWsAddress(strWSUrl);
//        wsConfig.setServiceName(functionWS);
//        wsConfig.setTargetNameSpace(targetNamePath);
//
//        Map<String, Object> mapObjects = new HashMap<>();
//        mapObjects.put("stockDTOId", id);
//
//        wsConfig.setBodyArgAlias(mapObjects);
//        try {
//            String message = WebServiceHandler.webServiceCaller(wsConfig);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    // xoa nhieu kho
//    public static void deleteLstStock(List<StockDTO> lstStock) {
//        String functionWS = "wms:deleteListStock";
//        WsRequestCreator wsConfig = new WsRequestCreator();
//        wsConfig.setWsAddress(strWSUrl);
//        wsConfig.setServiceName(functionWS);
//        wsConfig.setTargetNameSpace(targetNamePath);
//
//        Map<String, Object> mapObjects = new HashMap<>();
//        mapObjects.put("stockListDTO", lstStock);
//
//        wsConfig.setBodyArgAlias(mapObjects);
//        try {
//            String message = WebServiceHandler.webServiceCaller(wsConfig);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
