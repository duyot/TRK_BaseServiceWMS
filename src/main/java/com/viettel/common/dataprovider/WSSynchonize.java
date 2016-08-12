/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.common.dataprovider;

import com.viettel.logistic.wms.dto.OrderActionDTO;
import com.viettel.logstic.wms.webservice.dto.BillStockBccs;
import com.viettel.logstic.wms.webservice.dto.DataBccs;
import com.viettel.logstic.wms.webservice.dto.OrdersDTO;
import com.viettel.logstic.wms.webservice.dto.ResultSyn;
import com.viettel.logstic.wms.webservice.dto.ReturnCreateBill;
import com.viettel.vfw5.base.dto.ResultDTO;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author truongbx3
 * @version 1.0
 * @since 8/24/2015 10:16 AM
 */
public class WSSynchonize {

    List<String> lstStringSeq;
    List<OrdersDTO> lstOrdersDTO;
    List<OrderActionDTO> lstOrderActionDTO;
    //Duong dan Websevice

    /**
     *
     */
    public static String strWsWMSUrl = BundelUtils.getkey("wms_ws_url");
    public static String strWsBCCSUrl = BundelUtils.getkey("bccs_ws_url");
    public static String strWsKTTSUrl = BundelUtils.getkey("ktts_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:wms=\"http://http://wms.viettel.vn\"";
    //Url WS Stock
    public static String strWSUrl = strWsWMSUrl + "mapStockService?wsdl";
private static final Logger LOG = Logger.getLogger(WSSynchonize.class.getName());
    //Lay toan bo danh sach kho
//     tao lenh nhap kho
    public static ResultSyn callServiceCreateBillBccs(DataBccs order) {
        try {
            //Duong dan ten dich
            String targetNamePath = "xmlns:web=\"http://webservice.bccsgw.viettel.com/\"";
            //Ten ham lay du lieu kho
            String functionWS = "proc:createBill";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            ResultSyn resultSyn = new ResultSyn();
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWsBCCSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("Input", order);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            wsConfig.setWsBccs(new WsRequestCreator("createBill"));

//            Cac tham so cau hinh gui sang bccs
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, ResultDTO.class, "ReturnCreateBill");

            Mapper.alias(parseObject, ReturnCreateBill.class, "return");
            Mapper.addImplicitArray(parseObject, ReturnCreateBill.class, "lstBillStockResponse");
            Mapper.alias(parseObject, BillStockBccs.class, "lstBillStock");

            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            //Caller WS
            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            ReturnCreateBill resultDTO = (ReturnCreateBill) WebServiceHandler.wsServiceHandlerFromBccs(strResult, xmlStream);
            //
            BillStockBccs bill = resultDTO.getLstBillStockResponse().get(0);
            resultSyn.setReason(bill.getErrorMessage());
            //duyot
            System.out.println("LOG IN WS SYN: synTransCode from BCCS: "+ resultDTO.getSynTransCode());
            resultSyn.setSynTransCode(resultDTO.getSynTransCode());
            //
            if (bill.getStatus().trim().equalsIgnoreCase("0")) {
                resultSyn.setStatus("1");
            } else {
                resultSyn.setStatus("0");
            }
            return resultSyn;

        } catch (Exception e) {
            LOG.info("QuyenDM: Co loi khi goi BCCS Chi tiet loi: " + e.getCause().getMessage());
            return null;
        }
    }

    public static ResultSyn callServiceCreateBillKtts(OrdersDTO order) {
        try {
            //Duong dan ten dich
            String targetNamePath = "xmlns:web=\"http://webservice.qldtktts.viettel.com/\"";
            //Ten ham lay du lieu kho
            String functionWS = "web:createBill";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWsKTTSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("bill", order);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);

//            Cac tham so cau hinh gui sang bccs
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();

            Mapper.alias(parseObject, ResultSyn.class, "return");

            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            //Caller WS
            System.out.println("bat dau goi " + order.getOrderCode());

            WebServiceCaller webServiceCaller = new WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            System.out.println("ket qua tra ve " + order.getOrderCode());
            //Handler WS
            ResultSyn resultSyn = (ResultSyn) WebServiceHandler.wsServiceHandlerFromKtts(strResult, xmlStream);
            //
            return resultSyn;

        } catch (Exception e) {
            LOG.log(Level.INFO, "QuyenDM: Co loi khi goi KTTS voi yc: {0} Chi tiet loi: {1}", new Object[]{order.getOrderCode(), e.getCause().getMessage()});
            return null;
        }
    }
}
