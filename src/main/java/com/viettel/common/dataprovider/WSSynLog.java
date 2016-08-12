
/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.common.dataprovider;
import com.viettel.vwf5.base.servicecaller.Mapper;
import com.viettel.logstic.wms.webservice.dto.SynLogDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.BundelUtils;
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
* @author duyot
* @version 1.0
* @since 9/1/2015 3:42 PM
*/

public class WSSynLog {

    List<SynLogDTO> lstSynLogDTO;
    //Duong dan Websevice
    public static String strWsWMSUrl =  BundelUtils.getkey("cms_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:cms=\"http://cms.viettel.vn\"";
    //Url WS Stock
    public static String strWSUrl = strWsWMSUrl + "synLogService";

    //Lay toan bo danh sach kho
    public static List<SynLogDTO> getListSynLogDTO(SynLogDTO synLogDTO, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            //Ten ham lay du lieu kho
            String functionWS = "cms:getListSynLogDTO";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("synLogDTO", synLogDTO);
            mapObjects.put("rowStart", rowStart);
            mapObjects.put("maxRow", maxRow);
            mapObjects.put("sortType", sortType);
            mapObjects.put("sortFieldList", sortFieldList);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSSynLog.class, "lstSynLogDTO")
                    .alias(parseObject, WSSynLog.class, "ns2:getListSynLogDTOResponse")
                    .alias(parseObject, SynLogDTO.class, "ns2:synLogDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSSynLog wsList = (WSSynLog) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstSynLogDTO;

        } catch (Exception e) {
            throw e;
        }
    }

    //Insert SynLog
    public static ResultDTO insertSynLog(SynLogDTO synLogDTO) {
        try {
            String functionWS = "cms:insertSynLog";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("synLogDTO", synLogDTO);
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

    //Update SynLog
    public static String updateSynLog(SynLogDTO synLogDTO) {
        try {
            String functionWS = "cms:updateSynLog";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("synLogDTO", synLogDTO);
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

    //Delete SynLog
    public static String deleteSynLog(String id) {
        try {
            String functionWS = "cms:deleteSynLog";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("synLogDTOId", id);
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

    //find SynLog by id
    public static SynLogDTO findSynLogById(String id) {
        try {
            String functionWS = "cms:findSynLogById";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("synLogDTOId", id);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, SynLogDTO.class, "ns2:synLog");
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            SynLogDTO synLogDTO = (SynLogDTO) WebServiceHandler.wsServiceHandler(strWSConfig, xmlStream);
            return synLogDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // xoa nhieu SynLog
    public static String deleteLstSynLog(List<SynLogDTO> lstSynLogDTO) {
        try {
            String functionWS = "cms:deleteListSynLog";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("synLogListDTO", lstSynLogDTO);
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
