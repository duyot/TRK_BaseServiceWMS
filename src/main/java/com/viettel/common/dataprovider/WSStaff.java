/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.common.dataprovider;

import com.viettel.logistic.wms.dto.StaffDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
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
 * @author hungkv
 */
public class WSStaff {
  List<StaffDTO> lstStaffDTO;
    //Duong dan Websevice
    public static String strWsWMSUrl = BundelUtils.getkey("cms_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:cms=\"http://cms.viettel.vn\"";
    //Url WS Staff
    public static String strWSUrl = strWsWMSUrl + "staffService";

    //Lay toan bo danh sach staff
    public static List<StaffDTO> getListStaffDTO(StaffDTO staffDTO, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            //Ten ham lay du lieu 
            String functionWS = "cms:getListStaffDTO";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("staffDTO", staffDTO);
            mapObjects.put("rowStart", rowStart);
            mapObjects.put("maxRow", maxRow);
            mapObjects.put("sortType", sortType);
            mapObjects.put("sortFieldList", sortFieldList);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSStaff.class, "lstStaffDTO")
                    .alias(parseObject, WSStaff.class, "ns2:getListStaffDTOResponse")
                    .alias(parseObject, StaffDTO.class, "ns2:staffDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSStaff wsList = (WSStaff) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstStaffDTO;

        } catch (Exception e) {
            throw e;
        }
    }

    //Insert Staff
    public static ResultDTO insertStaff(StaffDTO staffDTO) {
        try {
            String functionWS = "cms:insertStaff";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("staffDTO", staffDTO);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, ResultDTO.class, "ns2:resultDTO");
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            ResultDTO message = (ResultDTO) WebServiceHandler.wsServiceHandler(strWSConfig, xmlStream);
            return message;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Update Staff
    public static String updateStaff(StaffDTO staffDTO) {
        try {
            String functionWS = "cms:updateStaff";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("staffDTO", staffDTO);
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

    //Delete Staff
    public static String deleteStaff(String id) {
        try {
            String functionWS = "cms:deleteStaff";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("staffDTOId", id);
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

    //Find Staff by id
    public static StaffDTO findStaffById(String id) {
        try {
            String functionWS = "cms:findStaffById";
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

    // xoa nhieu Area
    public static String deleteLstStaff(List<StaffDTO> lstStaffDTO) {
        try {
            String functionWS = "cms:deleteListStaff";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("staffListDTO", lstStaffDTO);
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

    public static void main(String[] args) {
//        StaffDTO search = new StaffDTO();
//
//        try {
//            System.out.println(WSStaff.getListStaffDTO(search, 0, 100, "", "orderId").size());
//        } catch (Exception ex) {
//            Logger.getLogger(StaffDTO.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
