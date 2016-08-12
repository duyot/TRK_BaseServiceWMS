/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.webservice;

import com.viettel.logistic.wms.dto.MapStaffGoodsDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.dto.ResultMapStaffGoodsDTO;
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
 * @author hongdq4
 * @version 1.0
 * @since 10/12/2015 9:46 AM
 */
public class WSMapStaffGoods {

    List<MapStaffGoodsDTO> lstMapStaffGoodsDTO;
    //Duong dan Websevice
    public static String strWsWMSUrl = BundelUtils.getStringCas("wms_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:wms=\"http://wms.viettel.vn\"";
    //Url WS Stock
    public static String strWSUrl = strWsWMSUrl + "mapStaffGoodsService";

    //Lay toan bo danh sach kho
    public static List<MapStaffGoodsDTO> getListMapStaffGoodsDTO(MapStaffGoodsDTO mapStaffGoodsDTO, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            //Ten ham lay du lieu kho
            String functionWS = "WMS:getListMapStaffGoodsDTO";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("mapStaffGoodsDTO", mapStaffGoodsDTO);
            mapObjects.put("rowStart", rowStart);
            mapObjects.put("maxRow", maxRow);
            mapObjects.put("sortType", sortType);
            mapObjects.put("sortFieldList", sortFieldList);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSMapStaffGoods.class, "lstMapStaffGoodsDTO")
                    .alias(parseObject, WSMapStaffGoods.class, "ns2:getListMapStaffGoodsDTOResponse")
                    .alias(parseObject, MapStaffGoodsDTO.class, "ns2:mapStaffGoodsDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSMapStaffGoods wsList = (WSMapStaffGoods) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstMapStaffGoodsDTO;

        } catch (Exception e) {
            throw e;
        }
    }

    //Insert MapStaffGoods
    public static ResultDTO insertMapStaffGoods(MapStaffGoodsDTO mapStaffGoodsDTO) {
        try {
            String functionWS = "WMS:insertMapStaffGoods";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("mapStaffGoodsDTO", mapStaffGoodsDTO);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, ResultDTO.class, "ns2:resultDTO");
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            ResultDTO resultDTO = (ResultDTO) WebServiceHandler.wsServiceHandler(strWSConfig, xmlStream);
            return resultDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return new ResultDTO();

        }
    }

    public static ResultMapStaffGoodsDTO insertListMapStaffGoods(List<MapStaffGoodsDTO> lstMapStaffGoods) {
        try {
            String functionWS = "wms:insertListMapStaffGoods";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("lstMapStaffGoods", lstMapStaffGoods);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, ResultMapStaffGoodsDTO.class, "ns2:resultMapStaffGoodsDTO");
            //
            Mapper.addImplicitCollection(parseObject, ResultMapStaffGoodsDTO.class, "lstMapStaffGoodsDTO");
            Mapper.alias(parseObject, MapStaffGoodsDTO.class, "lstMapStaffGoodsDTO");
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            ResultMapStaffGoodsDTO resultDTO = (ResultMapStaffGoodsDTO) WebServiceHandler.wsServiceHandler(strWSConfig, xmlStream);
            return resultDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMapStaffGoodsDTO();

        }
    }

    //Update MapStaffGoods
    public static String updateMapStaffGoods(MapStaffGoodsDTO mapStaffGoodsDTO) {
        try {
            String functionWS = "WMS:updateMapStaffGoods";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("mapStaffGoodsDTO", mapStaffGoodsDTO);
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

    //Delete MapStaffGoods
    public static String deleteMapStaffGoods(String id) {
        try {
            String functionWS = "WMS:deleteMapStaffGoods";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("mapStaffGoodsDTOId", id);
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

    /**
     * 150316 NgocND6 - Search list goods by condition
     *
     * @param id
     * @return
     */
    public static List<MapStaffGoodsDTO> getListMapSGByCondition(List<ConditionBean> lstCon, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            //Ten ham lay du lieu kho
            String functionWS = "wms:getListMapStaffGoodsByCondition";
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
            Mapper.addImplicitCollection(parseObject, WSMapStaffGoods.class, "lstMapStaffGoodsDTO")
                    .alias(parseObject, WSMapStaffGoods.class, "ns2:getListMapStaffGoodsByConditionResponse")
                    .alias(parseObject, MapStaffGoodsDTO.class, "ns2:MapStaffGoodsDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSMapStaffGoods wsList = (WSMapStaffGoods) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstMapStaffGoodsDTO;

        } catch (Exception e) {
            return new ArrayList<MapStaffGoodsDTO>();
        }
    }

    //find MapStaffGoods by id
    public static MapStaffGoodsDTO findMapStaffGoodsById(String id) {
        try {
            String functionWS = "wms:findMapStaffGoodsById";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("mapStaffGoodsDTOId", id);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, MapStaffGoodsDTO.class, "ns2:mapStaffGoods");
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            MapStaffGoodsDTO mapStaffGoodsDTO = (MapStaffGoodsDTO) WebServiceHandler.wsServiceHandler(strWSConfig, xmlStream);
            return mapStaffGoodsDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // xoa nhieu MapStaffGoods
    public static String deleteLstMapStaffGoods(List<MapStaffGoodsDTO> lstMapStaffGoodsDTO) {
        try {
            String functionWS = "wms:deleteListMapStaffGoods";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("mapStaffGoodsListDTO", lstMapStaffGoodsDTO);
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

    //NgocND6 160316

    public static String insertOrUpdateListMapStaffGoodss(List<MapStaffGoodsDTO> lstMapStaffGoodsDTOs) {
        try {
            String functionWS = "wms:insertOrUpdateListMapStaffGoods";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("mapStaffGoodsDTO", lstMapStaffGoodsDTOs);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, String.class, "ns2:insertOrUpdateListMapStaffGoodsResponse");
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
        public static String insertOrUpdateListMapStaffGoods(List<MapStaffGoodsDTO> lstMapStaffGoodsDTOs) {
        try {
            String functionWS = "wms:insertOrUpdateListMapStaffGoods";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("mapStaffGoodsDTO", lstMapStaffGoodsDTOs);
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
