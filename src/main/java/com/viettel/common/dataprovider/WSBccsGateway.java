/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.common.dataprovider;


import com.viettel.logistic.wms.dto.BillStock;
import com.viettel.logistic.wms.dto.InputDTO;
import com.viettel.logistic.wms.dto.ResultBCCSDTO;
import com.viettel.logistic.wms.dto.ResultKTTSDTO;
import com.viettel.logstic.wms.webservice.dto.DataIEBccs;
import com.viettel.vfw5.base.utils.BundelUtils;
import com.viettel.vwf5.base.servicecaller.bccs.Mapper;
import com.viettel.vwf5.base.servicecaller.bccs.WebServiceHandler;
import com.viettel.vwf5.base.servicecaller.bccs.WsRequestCreator;
import com.viettel.vwf5.base.servicecaller.bccs.XStreamStorage;
import com.viettel.vwf5.base.servicecaller.bccs.XmlStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DuyOt
 */
public class WSBccsGateway {

//    List<GoodsBCCSDTO> listGoodsBCCSDTO;
//    List<GoodsBCCSDTO> lstGoodsBCCSDTOConditionBean;
//    ResultBCCSDTO rbccsdto;
    InputDTO inputDTO;
    //Duong dan webservices
    public static String strWsWMSUrl = BundelUtils.getkey("bccs_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:web=\"http://webservice.bccsgw.viettel.com/\"";
    //Url Webservice Customers
    public static String wscode = "transStock";
    
    //---------------------------------------------------------------------------------
    public static ResultBCCSDTO callServiceTransStockBccs(DataIEBccs billStock) {
        try {
//            String strWsWMSUrl = "http://192.168.176.211:8689/BCCSGatewayWS/BCCSGatewayWS?wsdl";// that
//            String strWsWMSUrl = "http://10.60.34.8:8066/BCCSGateway?wsdl";//test
            //Duong dan ten dich
//            String targetNamePath = "xmlns:web=\"http://webservice.bccsgw.viettel.com/\"";
            //Ten ham lay du lieu kho
            String functionWS = "proc:transStock";
            //
            Map<String, Object> mapObjects = new HashMap<>();
            //Set cac tham so cau hinh
            WsRequestCreator wsConfig = new WsRequestCreator();
            wsConfig.setWsAddress(strWsWMSUrl);
            wsConfig.setServiceName(functionWS);
            wsConfig.setTargetNameSpace(targetNamePath);
            //Put du lieu tham so dau vao
            mapObjects.put("Input", billStock);
            //Set tham so dau vao
            wsConfig.setBodyArgAlias(mapObjects);
            wsConfig.setWsBccs(new WsRequestCreator("transStock"));

//            Cac tham so cau hinh gui sang bccs
            //Map doi tuong 
            List<XStreamStorage> parseObject = new ArrayList<>();
            Mapper.alias(parseObject, ResultBCCSDTO.class, "return");
            //Kho tao doc/ghi du lieu tu xml ra Object
            XmlStream xmlStream = new XmlStream();
            xmlStream.config(parseObject);
            xmlStream.isSingleType = true;
            //Caller WS
            com.viettel.vwf5.base.servicecaller.bccs.WebServiceCaller webServiceCaller = new com.viettel.vwf5.base.servicecaller.bccs.WebServiceCaller();
            String strResult = webServiceCaller.webServiceCaller(wsConfig);
            //Handler WS
            ResultBCCSDTO resultDTO = (ResultBCCSDTO)WebServiceHandler.wsServiceHandlerFromBccs(strResult, xmlStream);
            //
            return resultDTO;

        } catch (Exception e) {
            return null;
        }
    }
    //---------------------------------------------------------------------------------
    //xoa tong tin ns2 nhap duoc tu soap
    //them CDATA + LOGIN
    public static String rowDataProcessing(String rowData){
        rowData = rowData.replaceAll("ns2:", "");
        rowData = rowData.replaceAll("/ns2:", "");
        return "<![CDATA[<proc:transStock>\n" +
"           <accountBCCS>bccs_im</accountBCCS>\n" +
"         <!--Optional:-->\n" +
"         <passBCCS>bccs_im</passBCCS>\n" +
"         <!--Optional:--> "+ rowData + "</proc:transStock>]]>";
    }
    
    public static void main(String[] args) {
//        String rawDataTransStock = WSStockImportWMS.transStock("1806", "1");
//        rawDataTransStock = rawDataTransStock.replaceAll("ns2:", "");
//        rawDataTransStock = rawDataTransStock.replaceAll("/ns2:", "");
//        String raw = "<![CDATA[<proc:transStock>\n" +
//"           <accountBCCS>bccs_im</accountBCCS>\n" +
//"         <!--Optional:-->\n" +
//"         <passBCCS>bccs_im</passBCCS>\n" +
//"         <!--Optional:--> "+ rawDataTransStock + "</proc:transStock>]]>";
//        ResultBCCSDTO result = WSBccsGateway.getRealIEResultBCCSGW(raw);
       
        
    }
    
}
