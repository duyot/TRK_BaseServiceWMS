/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.webservice;

import com.viettel.logistic.wms.dto.OrderActionDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
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
import com.viettel.vfw5.base.utils.BundelUtils;


/**
 *
 * @author vtsoft
 */
public class WSOrderAction {
     List<String> lstStringSeq;
    List<OrderActionDTO> lstOrderActionDTO;
    List<OrderActionDTO> lstOrderActionDTOConditionBean;
    //Duong dan Websevice
    public static String strWsCMSUrl = BundelUtils.getkey("cms_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:cms=\"http://cms.viettel.vn\"";
    //Url WS Stock
    public static String strWSUrl = strWsCMSUrl + "orderActionService";

    //Lay toan bo danh sach kho
    public static List<OrderActionDTO> getListOrderActionDTO(OrderActionDTO orderActionDTO, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            String functionWS = "cms:getListOrderActionDTO";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("orderActionDTO", orderActionDTO);
            mapObjects.put("rowStart", rowStart);
            mapObjects.put("maxRow", maxRow);
            mapObjects.put("sortType", sortType);
            mapObjects.put("sortFieldList", sortFieldList);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSOrderAction.class, "lstOrderActionDTO")
                    .alias(parseObject, WSOrderAction.class, "ns2:getListOrderActionDTOResponse")
                    .alias(parseObject, OrderActionDTO.class, "ns2:orderActionDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSOrderAction wsList = (WSOrderAction) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstOrderActionDTO;
        } catch (Exception e) {
            throw e;
        }
}
    //Lay sequence
    public static List<String> getListSeqOrderActionDTO(String seqName,int size) throws Exception {
        try {
            String functionWS = "cms:getSequenseOrderAction";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("sequenseName", seqName);
            mapObjects.put("Size", size);

            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSOrderAction.class, "lstStringSeq")
                    .alias(parseObject, WSOrderAction.class, "ns2:getSequenseOrderActionResponse")
                    .alias(parseObject, String.class,"ns2:getSequense");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSOrderAction wsList = (WSOrderAction) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstStringSeq;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
}
    
//    public static void main(String[] args) {
//        OrderActionDTO search = new OrderActionDTO();
//        try {
//            System.out.println(WSOrderAction.getListOrderActionDTO(search, 0, 1000, "", "id").size());
//        } catch (Exception ex) {
//            Logger.getLogger(WSOrderAction.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public static List<OrderActionDTO> getListOrderActionByCondition(List<ConditionBean> lstcon, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            //Ten ham lay du lieu kho
            String functionWS = "cms:getListOrderActionByCondition";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("lstCondition", lstcon);
            mapObjects.put("rowStart", rowStart);
            mapObjects.put("maxRow", maxRow);
            mapObjects.put("sortType", sortType);
            mapObjects.put("sortFieldList", sortFieldList);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSOrderAction.class, "lstOrderActionDTOConditionBean")
                    .alias(parseObject, WSOrderAction.class, "ns2:getListOrderActionByConditionResponse")
                    .alias(parseObject, OrderActionDTO.class, "ns2:OrderActionDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSOrderAction wsList = (WSOrderAction) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstOrderActionDTOConditionBean;

        } catch (Exception e) {
            throw e;
        }
    }
    
    //FindById
    public static OrderActionDTO findOrderActionById(String id) throws Exception {
        try {
            String functionWS = "cms:findOrderActionById";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("orderActionDTOId", id);

            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, OrderActionDTO.class, "ns2:orderAction");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            OrderActionDTO erderActionDTO = (OrderActionDTO) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            return erderActionDTO;
        } catch (Exception e) {
            throw e;
        }
    }
    
    //Update OrderAction
    public static String updateOrderAction(OrderActionDTO orderActionDTO) {
        try {
            orderActionDTO.setStatusName(null);
            orderActionDTO.setOrderStockName(null);
            orderActionDTO.setOrderTypeName(null);
            String functionWS = "cms:updateOrderAction";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
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
}
