/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vwf5.base.servicecaller;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.viettel.vfw5.base.utils.Constants;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.transform.TransformerException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author thiendn1
 */
public class WebServiceSchema {


    protected final String SOAP_PREDIX = "soapenv";
    protected final String WSSE_PREDIX = "wsse";
    Logger logger = LoggerFactory.getLogger(WebServiceSchema.class);


    public SOAPMessage getSoapMessageFormatRequest() throws SOAPException, TransformerException {
        //create SOAP
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        soapEnvelope.setPrefix(SOAP_PREDIX);
        soapEnvelope.removeNamespaceDeclaration("SOAP-ENV");
        soapEnvelope.addNamespaceDeclaration("ser", "$xmlns:ser$");

        SOAPHeader soapHeader = soapEnvelope.getHeader();
        soapHeader.setPrefix(SOAP_PREDIX);
        soapHeader.setTextContent(Constants.WEB_SERVICE_CONS.ARG_HEADER);
        SOAPBody soapBody = soapEnvelope.getBody();
        soapBody.setPrefix(SOAP_PREDIX);
        SOAPBodyElement bodyElement = soapBody.addBodyElement(new QName("$service_name$"));
        bodyElement.setTextContent(Constants.WEB_SERVICE_CONS.ARG_BODY);
        return soapMessage;
    }

    /**
     *
     * @return
     * @throws javax.xml.soap.SOAPException
     * @throws javax.xml.transform.TransformerException
     */
    @SuppressWarnings("thiendn1: not use now")
    public SOAPMessage getSoapSecurityMessageFormatRequest() throws SOAPException, TransformerException {

        SOAPMessage soapMessage = getSoapMessageFormatRequest();
        SOAPHeader soapHeader = soapMessage.getSOAPPart().getEnvelope().getHeader();

        SOAPElement security
                = soapHeader.addChildElement("Security", WSSE_PREDIX, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

        security.addNamespaceDeclaration("mustUnderstand", "0");
        security.addNamespaceDeclaration("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

        SOAPElement usernameToken
                = security.addChildElement("UsernameToken", WSSE_PREDIX);
        usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

        SOAPElement username
                = usernameToken.addChildElement("Username", WSSE_PREDIX);
        username.addTextNode("$usernameToken$");

        SOAPElement password
                = usernameToken.addChildElement("Password", WSSE_PREDIX);
        password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
        password.addTextNode("$passwordToken$");

//        SOAPElement nonce
//                = usernameToken.addChildElement("Nonce", WSSE_PREDIX);
//        nonce.addTextNode(SecurityHelper.generateNonce());
//        nonce.setAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
//
//        SOAPElement timeStamp
//                = usernameToken.addChildElement("Created", "wsu");
//        timeStamp.addTextNode(SecurityHelper.generateTimestamp());
        return soapMessage;
    }

    public String getDefaultFormatRequest() throws SOAPException, TransformerException {
        //create SOAP
        SOAPMessage soapMessage = getSoapMessageFormatRequest();
        return XmlSchema.node2String(soapMessage.getSOAPPart()).replace("xmlns:ser=\"$xmlns:ser$\"", "$xmlns:ser$");
    }

    public String getDefaultFormatRequest(String header) throws TransformerException, SOAPException {
        if (header == null) {
            return getDefaultFormatRequest();
        }
        StringBuilder br = new StringBuilder();
        br.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" $xmlns:ser$"
                + ">");
        br.append("<soapenv:Header>").append(header).append("</soapenv:Header>");
        br.append("<soapenv:Body>");
        br.append("<$service_name$>");
        br.append("$argument$");
        br.append("</$service_name$>");
        br.append("</soapenv:Body>");
        br.append("</soapenv:Envelope>");
        return br.toString();
    }

    public String getDefaultFormatSecurityRequest() throws TransformerException, SOAPException {
        //create SOAP
        return getDefaultFormatRequest();
    }


    private String parseRequest(WsRequestCreator wsConfig) throws TransformerException, SOAPException {
        String request = null;
        ConcurrentHashMap<String, WsTemplate> templates = WebServiceConfigLoader.getInstance().getWsTemplateMap().get(wsConfig.getWsAddress().replaceAll("(?ui)\\?wsdl", ""));
        boolean isDefaultRequest = true;
        WsTemplate wsTemplate = templates.get(wsConfig.getServiceName());
        if (templates != null&&wsTemplate!=null) {
            request = wsTemplate.getSoapMessage();
        } else {
            request = getDefaultFormatSecurityRequest();
        }
        return request;
    }

    /**
     * using httpClient to call soap web service
     *
     * @return
     * @throws java.io.UnsupportedEncodingException
     * @throws java.io.IOException
     */
    public String parseParameters(WsRequestCreator wsConfig) throws TransformerException, SOAPException {
        String operator = wsConfig.getServiceName();
        String targetNamespace = wsConfig.getTargetNameSpace();
        String parameters = wsConfig.getBodyParameterTxt();
        String header = wsConfig.getHeaderParameterTxt();
        String request = null;
        ConcurrentHashMap<String, WsTemplate> templates = WebServiceConfigLoader.getInstance().getWsTemplateMap().get(wsConfig.getWsAddress().replaceAll("(?ui)\\?wsdl", ""));
        WsTemplate wsTemplate = null;
        if(templates!=null){
            wsTemplate = templates.get(wsConfig.getServiceName());
        }
        if (templates != null&&wsTemplate!=null) {
            request = wsTemplate.getSoapMessage();
        } else {
            request = getDefaultFormatSecurityRequest();
            if (targetNamespace.contains("\"")) {
                if (targetNamespace.contains("xmlns:")) {
                    request = request.replace("$xmlns:ser$", targetNamespace);
                } else {
                    request = request.replace("$xmlns:ser$", "xmlns:" + targetNamespace);
                }
            } else {
                request = request.replace("$xmlns:ser$", "xmlns:ser=\"" + targetNamespace + "\"");
            }
            if (operator.contains(":")) {
                request = request.replace("$service_name$", operator);
            } else {
                operator = "ser:" + operator;
                request = request.replace("$service_name$", operator);
            }
        }
        if (header == null) {
            request = request.replace(Constants.WEB_SERVICE_CONS.ARG_HEADER, "");
        } else {
            request = request.replace(Constants.WEB_SERVICE_CONS.ARG_HEADER, header);
        }
        if (parameters == null) {
            request = request.replace(Constants.WEB_SERVICE_CONS.ARG_BODY, "");
        } else {
            request = request.replace(Constants.WEB_SERVICE_CONS.ARG_BODY, parameters);
        }
        if (WebServiceHandler.showRequest) {
            String formatRequest = XmlSchema.formatXML(request);
            logger.info(formatRequest);
        }
        return request;
    }



    /**
     * @return
     * @throws Exception
     * @author NamDX
     */
    public String getSoapRequest(WsRequestCreator wsConfig, String header) throws Exception {
        String request = parseParameters(wsConfig);
        return XmlSchema.formatXML(request);
    }

    public String getSoapRequest(WsRequestCreator wsConfig) throws Exception {
        String request = parseParameters(wsConfig);
        return XmlSchema.formatXML(request);
    }


}
