/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.webservice;

import com.viettel.logistic.wms.dto.StaffDTO;
import com.viettel.logstic.wms.webservice.dto.StaffStockDTO;
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
 * @author hongdq4
 */
public class WSStaffStock {

    List<StaffStockDTO> lstStaffStockDTO;
    //Duong dan Websevice
    public static String strWsWMSUrl = BundelUtils.getkey("cms_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:cms=\"http://cms.viettel.vn\"";
    //Url WS Stock
    public static String strWSUrl = strWsWMSUrl + "staffStockService";

    //find Staff by id
    public static StaffDTO findStaffById(String id) {
        try {
            String functionWS = "cms:findStaffStockById";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("staffDTOId", id);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, StaffDTO.class, "ns2:staff");
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            StaffDTO staffDTO = (StaffDTO) WebServiceHandler.wsServiceHandler(strWSConfig, xmlStream);
            return staffDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Lay toan bo danh sach staff
    public static List<StaffStockDTO> getListStaffStockDTO(StaffStockDTO staffStockDTO, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            //Ten ham lay du lieu 
            String functionWS = "cms:getListStaffStockDTO";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("staffStockDTO", staffStockDTO);
            mapObjects.put("rowStart", rowStart);
            mapObjects.put("maxRow", maxRow);
            mapObjects.put("sortType", sortType);
            mapObjects.put("sortFieldList", sortFieldList);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSStaffStock.class, "lstStaffStockDTO")
                    .alias(parseObject, WSStaffStock.class, "ns2:getListStaffStockDTOResponse")
                    .alias(parseObject, StaffStockDTO.class, "ns2:staffStockDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSStaffStock wsList = (WSStaffStock) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstStaffStockDTO;

        } catch (Exception e) {
            throw e;
        }
    }

}
