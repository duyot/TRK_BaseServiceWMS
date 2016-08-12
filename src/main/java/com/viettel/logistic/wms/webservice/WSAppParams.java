/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.webservice;

import com.viettel.vwf5.base.servicecaller.Mapper;
import com.viettel.vwf5.base.servicecaller.WebServiceCaller;
import com.viettel.vwf5.base.servicecaller.WebServiceHandler;
import com.viettel.vwf5.base.servicecaller.WsRequestCreator;
import com.viettel.vwf5.base.servicecaller.XStreamStorage;
import com.viettel.vwf5.base.servicecaller.XmlStream;
import com.viettel.logstic.wms.webservice.dto.AppParamsDTO;
import com.viettel.vfw5.base.utils.BundelUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vtsoft
 */
public class WSAppParams {

    List<AppParamsDTO> lstAppParamsDTO;
    //Duong dan Websevice
    public static String strWsCMSUrl = BundelUtils.getkey("cms_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:cms=\"http://cms.viettel.vn\"";
    //Url WS Stock
    public static String strWSUrl = strWsCMSUrl + "appParamsService";

    //Lay toan bo danh sach kho
    public static List<AppParamsDTO> getListAppParamsDTO(AppParamsDTO appParamsDTO, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            String functionWS = "cms:getListAppParamsDTO";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("appParamsDTO", appParamsDTO);
            mapObjects.put("rowStart", rowStart);
            mapObjects.put("maxRow", maxRow);
            mapObjects.put("sortType", sortType);
            mapObjects.put("sortFieldList", sortFieldList);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSAppParams.class, "lstAppParamsDTO")
                    .alias(parseObject, WSAppParams.class, "ns2:getListAppParamsDTOResponse")
                    .alias(parseObject, AppParamsDTO.class, "ns2:appParamsDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSAppParams wsList = (WSAppParams) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstAppParamsDTO;
        } catch (Exception e) {
            throw e;
        }
    }

}
