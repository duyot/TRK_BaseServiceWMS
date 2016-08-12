/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vwf5.base.servicecaller;

import com.viettel.vwf5.base.servicecaller.WebServiceConfigLoader;
import com.viettel.vwf5.base.servicecaller.WebServiceResponseLoader;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;
import java.util.List;
import java.util.Map;

/**
 * @author thiendn1 duyot thong bao lai voi tac gia truoc khi thuc hien bat cu
 * su thay doi nao
 */
public class WebServiceHandler {

    public static boolean showRequest = true;
    public static boolean showResponse = true;
    private static final WebServiceCaller webServiceCaller = new WebServiceCaller();
    private static final WebServiceResponseLoader webServiceResponseLoader = new WebServiceResponseLoader();

    @Deprecated
    public static String webServiceCaller(String wsUrl,
            String wsOperator, String wsTargetNamespace, List<Object> wsParameters,
            String username, String password) throws Exception {
        return webServiceCaller.webServiceCaller(wsUrl,
                wsOperator, wsTargetNamespace, wsParameters,
                username, password);
    }

    @Deprecated
    public static String webServiceCaller(String wsUrl,
            String wsOperator, String wsTargetNamespace, String parameters,
            String header) throws Exception {
        return webServiceCaller.webServiceCaller(wsUrl,
                wsOperator, wsTargetNamespace, parameters,
                header);
    }

    @Deprecated
    public static String webServiceCaller(String wsUrl,
            String wsOperator, String wsTargetNamespace, List<Object> wsParameters, String header) throws Exception {
        return webServiceCaller.webServiceCaller(wsUrl,
                wsOperator, wsTargetNamespace, wsParameters, header);

    }

    public static String webServiceCaller(WsRequestCreator wsConfig) throws Exception {
        return webServiceCaller.webServiceCaller(wsConfig);
    }

    public static String webServiceCaller(String wsConfigKey, List<Object> wsHeaderParameters,
            List<Object> wsParameters) throws Exception {
        WsRequestCreator wsConfig = WebServiceConfigLoader.getInstance().getWsConfig(wsConfigKey);
        wsConfig.setHeaderParameters(wsHeaderParameters);
        wsConfig.setBodyParameters(wsParameters);
        return webServiceCaller.webServiceCaller(wsConfig);
    }

    public static String webServiceAliasCaller(String wsConfigKey, Map<String, Object> wsHeaderParameters,
            Map<String, Object> wsParameters) throws Exception {
        WsRequestCreator wsConfig = WebServiceConfigLoader.getInstance().getWsConfig(wsConfigKey);
        wsConfig.setBodyArgAlias(wsParameters);
        wsConfig.setHeaderArgAlias(wsHeaderParameters);
        return webServiceCaller.webServiceCaller(wsConfig);
    }

    public static String webServiceCaller(String wsConfigKey, String serviceMethod, List<Object> wsHeaderParameters,
            List<Object> wsParameters) throws Exception {
        WsRequestCreator wsConfig = WebServiceConfigLoader.getInstance().getWsConfig(wsConfigKey);
        wsConfig.setServiceName(serviceMethod);
        wsConfig.setBodyParameters(wsParameters);
        wsConfig.setHeaderParameters(wsHeaderParameters);
        return webServiceCaller.webServiceCaller(wsConfig);
    }

    public static String webServiceAliasCaller(String wsConfigKey, String serviceMethod,
            Map<String, Object> wsHeaderParameters, Map<String, Object> wsParameters) throws Exception {
        WsRequestCreator wsConfig = WebServiceConfigLoader.getInstance().getWsConfig(wsConfigKey);
        wsConfig.setServiceName(serviceMethod);
        wsConfig.setBodyArgAlias(wsParameters);
        wsConfig.setHeaderArgAlias(wsHeaderParameters);
        return webServiceCaller.webServiceCaller(wsConfig);
    }

    public static void setTimeOut(int timeOut) {
        webServiceCaller.setTimeOutConnection(timeOut);
    }

    public static void setKeepAlive(int timeOut) {
        webServiceCaller.setKeepAlive(timeOut);
    }

    public static String prepareParseContent(String unhandleContent, XmlStream xmlStream) throws TransformerException {
        Document doc = XmlSchema.parseXmlFile(unhandleContent);
        NodeList tags = doc.getElementsByTagName("soap:Body");
        NodeList nList = null;
        Object responseObject = null;
        if (xmlStream.getXmlStreamType() == XmlStream.SINGLE_TYPE) {
            nList = doc.getElementsByTagName(tags.item(0).getChildNodes().item(0).getNodeName());
            nList = doc.getElementsByTagName(nList.item(0).getChildNodes().item(0).getNodeName());

        } else {
            nList = doc.getElementsByTagName(tags.item(0).getChildNodes().item(0).getNodeName());
        }
        return XmlSchema.node2String(nList.item(0));
    }

    public static Object wsServiceHandler(String unhandleContent,
            XmlStream xmlStream) throws Exception {
        return xmlStream.getxStream().fromXML(prepareParseContent(unhandleContent, xmlStream));
    }

    //duyot: ham handler for ktts
    public static String prepareParseContentKTTS(String unhandleContent, XmlStream xmlStream) throws TransformerException {
        Document doc = XmlSchema.parseXmlFile(unhandleContent);
        NodeList tags = doc.getElementsByTagName("S:Body");
        NodeList nList = null;
        Object responseObject = null;
        if (xmlStream.getXmlStreamType() == XmlStream.SINGLE_TYPE) {
            nList = doc.getElementsByTagName(tags.item(0).getChildNodes().item(0).getNodeName());
            nList = doc.getElementsByTagName(nList.item(0).getChildNodes().item(0).getNodeName());

        } else {
            nList = doc.getElementsByTagName(tags.item(0).getChildNodes().item(0).getNodeName());
        }
        return XmlSchema.node2String(nList.item(0));
    }

    public static Object wsServiceHandlerKTTS(String unhandleContent,
            XmlStream xmlStream) throws Exception {
        return xmlStream.getxStream().fromXML(prepareParseContentKTTS(unhandleContent, xmlStream));
    }
    //end----------------------------------------------

    public static Object wsServiceHandler(String unhandleContent,
            XmlStream xmlStream, String removePredix) throws Exception {
        return xmlStream.getxStream().fromXML(prepareParseContent(unhandleContent, xmlStream).replaceAll(removePredix + ":", ""));
    }

    public static Object wsServiceHandlerByConfig(String unhandleContent, String configFile, String configId) throws Exception {
        return webServiceResponseLoader.wsServiceHandler(configFile, configId, unhandleContent);
    }

    /**
     * @param wsUrl
     * @param wsOperator
     * @param wsTargetNamespace
     * @param parameters
     * @param header
     * @return
     * @throws Exception
     * @author NamDX
     */
    @Deprecated
    public static String webServiceRequest(String wsUrl,
            String wsOperator, String wsTargetNamespace, String parameters,
            String header) throws Exception {
        return webServiceCaller.webServiceRequest(wsUrl,
                wsOperator, wsTargetNamespace, parameters,
                header);
    }

    @Deprecated
    public static String webServiceRequest(String wsUrl,
            String wsOperator, String wsTargetNamespace, List<Object> wsParameters,
            String username, String password) throws Exception {
        return webServiceCaller.webServiceRequest(wsUrl,
                wsOperator, wsTargetNamespace, wsParameters,
                username, password);
    }

    public static Object wsServiceHandlerFromBccs(String unhandleContent,
            XmlStream xmlStream) throws Exception {
        return xmlStream.getxStream().fromXML(prepareParseContentFromBccs(unhandleContent, xmlStream));
    }

    public static Object wsServiceHandlerFromKtts(String unhandleContent,
            XmlStream xmlStream) throws Exception {
        return xmlStream.getxStream().fromXML(prepareParseContentFromKtts(unhandleContent, xmlStream));
    }

    private static String prepareParseContentFromBccs(String unhandleContent, XmlStream xmlStream) throws TransformerException {
        Document doc = XmlSchema.parseXmlFile(unhandleContent);
        NodeList tags = doc.getElementsByTagName("S:Body");
        NodeList nList = null;
        Object responseObject = null;
        if (xmlStream.getXmlStreamType() == XmlStream.SINGLE_TYPE) {
            nList = doc.getElementsByTagName(tags.item(0).getChildNodes().item(0).getNodeName());
            nList = doc.getElementsByTagName(nList.item(0).getChildNodes().item(0).getNodeName());

        } else {
            nList = doc.getElementsByTagName(tags.item(0).getChildNodes().item(0).getNodeName());
        }

        Document doc1 = XmlSchema.parseXmlFile(XmlSchema.node2String(nList.item(0)));
        NodeList tags1 = doc1.getElementsByTagName("S:Body");
        Document doc2 = XmlSchema.parseXmlFile(XmlSchema.node2String(tags1.item(0)));
        NodeList tags2 = doc2.getElementsByTagName("return");

        return XmlSchema.node2String(tags2.item(0));
    }

    private static String prepareParseContentFromKtts(String unhandleContent, XmlStream xmlStream) throws TransformerException {
        Document doc = XmlSchema.parseXmlFile(unhandleContent);
        NodeList tags = doc.getElementsByTagName("S:Body");
        NodeList nList = null;
        Object responseObject = null;
        if (xmlStream.getXmlStreamType() == XmlStream.SINGLE_TYPE) {
            nList = doc.getElementsByTagName(tags.item(0).getChildNodes().item(0).getNodeName());
            nList = doc.getElementsByTagName(nList.item(0).getChildNodes().item(0).getNodeName());

        } else {
            nList = doc.getElementsByTagName(tags.item(0).getChildNodes().item(0).getNodeName());
        }
        return XmlSchema.node2String(nList.item(0));
    }
}
