/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.common.dataprovider;

import com.viettel.vwf5.base.servicecaller.Mapper;
import com.viettel.logistic.wms.dto.CustomerDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
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
 * @author Duyot
 * @version 1.0
 * @since 4/25/2015 2:48 PM
 */
public class WSCustomer {

    List<CustomerDTO> lstCustomerDTO;
    //Duong dan Websevice
    public static String strWsWMSUrl = BundelUtils.getkey("cms_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:cms=\"http://cms.viettel.vn\"";
    //Url WS Stock
    public static String strWSUrl = strWsWMSUrl + "customerService";

    //Lay toan bo danh sach kho
    public static List<CustomerDTO> getListCustomerDTO(CustomerDTO customerDTO, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            //Ten ham lay du lieu kho
            String functionWS = "cms:getListCustomerDTO";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("customerDTO", customerDTO);
            mapObjects.put("rowStart", rowStart);
            mapObjects.put("maxRow", maxRow);
            mapObjects.put("sortType", sortType);
            mapObjects.put("sortFieldList", sortFieldList);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.addImplicitCollection(parseObject, WSCustomer.class, "lstCustomerDTO")
                    .alias(parseObject, WSCustomer.class, "ns2:getListCustomerDTOResponse")
                    .alias(parseObject, CustomerDTO.class, "ns2:customerDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSCustomer wsList = (WSCustomer) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstCustomerDTO;

        } catch (Exception e) {
            throw e;
        }
    }

    //Insert Customer
    public static String insertCustomer(CustomerDTO customerDTO) {
        try {
            String functionWS = "cms:insertCustomer";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("customerDTO", customerDTO);
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

    //Update Customer
    public static String updateCustomer(CustomerDTO customerDTO) {
        try {
            String functionWS = "cms:updateCustomer";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("customerDTO", customerDTO);
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

    //Delete Customer
    public static String deleteCustomer(String id) {
        try {
            String functionWS = "cms:deleteCustomer";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("customerDTOId", id);
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

    //find Customer by id
    public static CustomerDTO findCustomerById(String id) {
        try {
            String functionWS = "cms:findCustomerById";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("customerDTOId", id);
            wsConfig.setBodyArgAlias(mapObjects);

            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, CustomerDTO.class, "ns2:customer");
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            String strWSConfig = WebServiceHandler.webServiceCaller(wsConfig);
            CustomerDTO customerDTO = (CustomerDTO) WebServiceHandler.wsServiceHandler(strWSConfig, xmlStream);
            return customerDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // xoa nhieu Customer
    public static String deleteLstCustomer(List<CustomerDTO> lstCustomerDTO) {
        try {
            String functionWS = "cms:deleteListCustomer";
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);

            Map<String, Object> mapObjects = new HashMap<>();
            mapObjects.put("customerListDTO", lstCustomerDTO);
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

    public static List<CustomerDTO> getListCustomerByCondition(List<ConditionBean> lstCon, int rowStart, int maxRow, String sortType, String sortFieldList) throws Exception {
        try {
            //Ten ham lay du lieu kho
            String functionWS = "cms:getListCustomerByCondition";
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
            Mapper.addImplicitCollection(parseObject, WSCustomer.class, "lstCustomerDTO")
                    .alias(parseObject, WSCustomer.class, "ns2:getListCustomerByConditionResponse")
                    .alias(parseObject, CustomerDTO.class, "ns2:CustomerDTO");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            WSCustomer wsList = (WSCustomer) WebServiceHandler.wsServiceHandler(strResult, xmlStream);
            //
            return wsList.lstCustomerDTO;

        } catch (Exception e) {
            List<CustomerDTO> lstreturn = new ArrayList<>();
            return lstreturn;
        }
    }
}
