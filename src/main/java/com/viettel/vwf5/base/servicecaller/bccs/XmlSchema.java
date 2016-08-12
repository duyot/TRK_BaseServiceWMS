package com.viettel.vwf5.base.servicecaller.bccs;

//import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.viettel.vwf5.base.servicecaller.WebServiceConfigLoader;
import com.viettel.vwf5.base.servicecaller.WsTemplate;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class XmlSchema {

    public final static String tagStart
            = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";
    public final static String tagEnd
            = "\\</\\w+\\>";
    public final static String tagSelfClosing
            = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";
    public final static String htmlEntity
            = "&[a-zA-Z][a-zA-Z0-9]+;";
    public final static Pattern htmlPattern = Pattern.compile(
            "(" + tagStart + ".*" + tagEnd + ")|(" + tagSelfClosing + ")|(" + htmlEntity + ")",
            Pattern.DOTALL
    );

    /**
     * Will return true if s contains HTML markup tags or entities.
     *
     * @param s String to test
     * @return true if string contains HTML
     */
    public static boolean isHtml(String s) {
        boolean ret = false;
        if (s != null) {
            ret = htmlPattern.matcher(s).find();
        }
        return ret;
    }

    /**
     * parse list of object into xml following by soapUi format
     *
     * @param list
     * @return
     */
    /**
     * return true if the String passed in is something like XML
     *
     * @param inXMLStr a string that might be XML
     * @return true of the string is XML, false otherwise
     */
    public static boolean isXMLLike(String inXMLStr) {

        return isHtml(inXMLStr);
    }

    public static String formatHeaderParameters(List<Object> list) {
        StringBuilder parameterBuilder = new StringBuilder();
        if (list == null || list.isEmpty()) {
            return "";
        }
        XmlStream xmlStream = new XmlStream();
        XStream xstream = xmlStream.getxStream();
        for (int i = 0; i < list.size(); i++) {
            Object object = list.get(i);
            if (object != null) {
                if (VtClassUtils.isPrimitiveOrWrapper(object.getClass())) {
                    xstream.alias("arg" + (list.size()-1), String.class);
                    parameterBuilder.append(xstream.toXML(object.toString()));
                } else if (object.getClass().equals(String.class)) {
                    if (isXMLLike(object.toString())) {
                        String par = "<arg" + (list.size()-1) + ">" + object.toString() + "</arg" + (list.size()-1) + ">";
                        parameterBuilder.append(par);
                    } else {
                        xstream.alias("arg" + (list.size()-1), String.class);
                        parameterBuilder.append(xstream.toXML(object.toString()));
                    }
                } else {
                    if (object instanceof Collection) {
                        for (Object subObject : (List) object) {
                            if (VtClassUtils.isPrimitiveOrWrapper(subObject.getClass())) {
                                xstream.alias("arg" + (list.size()-1), String.class);
                                parameterBuilder.append(xstream.toXML(subObject.toString()));
                            } else {
                                xstream.alias("arg" + (list.size()-1), subObject.getClass());
                                parameterBuilder.append(xstream.toXML(subObject));
                            }
                        }
                    } else {
                        xstream.alias("arg" + (list.size()-1), object.getClass());
                        parameterBuilder.append(xstream.toXML(object));
                    }

                }
            }
        }
        return parameterBuilder.toString();
    }
    public static String formatParameters(List<Object> list) {
        StringBuilder parameterBuilder = new StringBuilder();
        if (list == null || list.isEmpty()) {
            return "";
        }
        XmlStream xmlStream = new XmlStream();
        XStream xstream = xmlStream.getxStream();
        for (int i = 0; i < list.size(); i++) {
            Object object = list.get(i);
            if (object != null) {
                if (VtClassUtils.isPrimitiveOrWrapper(object.getClass())) {
                    xstream.alias("arg" + i, String.class);
                    parameterBuilder.append(xstream.toXML(object.toString()));
                } else if (object.getClass().equals(String.class)) {
                    if (isXMLLike(object.toString())) {
                        String par = "<arg" + i + ">" + object.toString() + "</arg" + i + ">";
                        parameterBuilder.append(par);
                    } else {
                        xstream.alias("arg" + i, String.class);
                        parameterBuilder.append(xstream.toXML(object.toString()));
                    }
                } else {
                    if (object instanceof Collection) {
                        for (Object subObject : (List) object) {
                            if (VtClassUtils.isPrimitiveOrWrapper(subObject.getClass())) {
                                xstream.alias("arg" + i, String.class);
                                parameterBuilder.append(xstream.toXML(subObject.toString()));
                            } else {
                                xstream.alias("arg" + i, subObject.getClass());
                                parameterBuilder.append(xstream.toXML(subObject));
                            }
                        }
                    } else {
                        xstream.alias("arg" + i, object.getClass());
                        parameterBuilder.append(xstream.toXML(object));
                    }

                }
            }
        }
        return parameterBuilder.toString();
    }

    public static String formatHeaderParameters(WsRequestCreator wsRequestCreator) throws Exception {

        if (wsRequestCreator.getHeaderParameterTxt() != null) {
            return wsRequestCreator.getHeaderParameterTxt();
        }

        StringBuilder parameterBuilder = new StringBuilder();
        Map<String, Object> aliasObjects = wsRequestCreator.getHeaderArgAlias();
        XmlStream xmlStream = new XmlStream();
        XStream xstream = xmlStream.getxStream();
        if (aliasObjects != null) {
            Iterator it = aliasObjects.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                String alias = (String) pairs.getKey();
                Object object = pairs.getValue();
                if (object != null) {
                    parameterBuilder = parseParameters(xstream,parameterBuilder,object,alias);
                }
            }
            return parameterBuilder.toString();
        } else {
            List<String> argAlias = null;
            List<Object> list = wsRequestCreator.getHeaderParameters();
            ConcurrentHashMap<String, WsTemplate> templates = WebServiceConfigLoader.getInstance().getWsTemplateMap().get(wsRequestCreator.getWsAddress().replaceAll("(?ui)\\?wsdl", ""));
            if (templates != null) {
                argAlias = templates.get(wsRequestCreator.getServiceName()).getHeaderList();
            } else {
                return formatHeaderParameters(list);
            }

            if (list == null || list.isEmpty()) {
                return "";
            }

            if (argAlias.size() != list.size()) {
                throw new Exception("The numbers of argAlias and parameters are not matched!");
            }
            for (int i = 0; i < list.size(); i++) {
                Object object = list.get(i);
                if (object != null) {
                    parameterBuilder = parseParameters(xstream,parameterBuilder,object,argAlias.get(i));
                }
            }
            return parameterBuilder.toString();
        }
    }

    public static String formatParameters(WsRequestCreator wsRequestCreator) throws Exception {

        if (wsRequestCreator.getBodyParameterTxt() != null) {
            return wsRequestCreator.getBodyParameterTxt();
        }

        StringBuilder parameterBuilder = new StringBuilder();
        Map<String, Object> aliasObjects = wsRequestCreator.getBodyArgAlias();
        XmlStream xmlStream = new XmlStream();
        XStream xstream = xmlStream.getxStream();
        xstream.autodetectAnnotations(true);
        if (aliasObjects != null) {
            Iterator it = aliasObjects.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                String alias = (String) pairs.getKey();
                Object object = pairs.getValue();
                if (object != null) {
                    parameterBuilder = parseParameters(xstream,parameterBuilder,object,alias);

                }
            }
            return parameterBuilder.toString();
        } else {

            List<String> argAlias = null;
            List<Object> list = wsRequestCreator.getBodyParameters();
            ConcurrentHashMap<String, WsTemplate> templates = null;
            if(WebServiceConfigLoader.getInstance().getWsTemplateMap()!=null){
                templates = WebServiceConfigLoader.getInstance().getWsTemplateMap()
                        .get(wsRequestCreator.getWsAddress().replaceAll("(?ui)\\?wsdl", ""));
            }
            if (templates != null) {
                argAlias = templates.get(wsRequestCreator.getServiceName()).getAliasList();
            } else {
                return formatParameters(list);
            }

            if (list == null || list.isEmpty()) {
                return "";
            }

            if (argAlias.size() != list.size()) {
                throw new Exception("The numbers of argAlias and parameters are not matched!");
            }
            for (int i = 0; i < list.size(); i++) {
                Object object = list.get(i);
                if (object != null) {
                    parameterBuilder = parseParameters(xstream,parameterBuilder,object,argAlias.get(i));
                }
            }
            return parameterBuilder.toString();
        }
    }


    private static StringBuilder parseParameters(XStream xstream,StringBuilder parameterBuilder, Object object,String alias){
        if (VtClassUtils.isPrimitiveOrWrapper(object.getClass())) {
            xstream.alias(alias, String.class);
            parameterBuilder.append(xstream.toXML(object.toString()));
        } else if (object.getClass().equals(String.class)) {
            if (isXMLLike(object.toString())) {
                String par = "<" + alias + ">" + object.toString() + "<" + alias + ">";
                parameterBuilder.append(par);
            } else {
                xstream.alias(alias, String.class);
                parameterBuilder.append(xstream.toXML(object.toString()));
            }
        } else {
            if (object instanceof Collection) {
                for (Object subObject : (List) object) {
                    if (VtClassUtils.isPrimitiveOrWrapper(subObject.getClass())) {
                        xstream.alias(alias, String.class);
                        parameterBuilder.append(xstream.toXML(subObject.toString()));
                    } else {
                        xstream.alias(alias, subObject.getClass());
                        parameterBuilder.append(xstream.toXML(subObject));
                    }
                }
            } else {
                xstream.alias(alias, object.getClass());
                parameterBuilder.append(xstream.toXML(object));
            }

        }
        return parameterBuilder;
    }
//
//    public static String objectToJson(Object object) {
//        StringBuilder parameterBuilder = new StringBuilder();
//        if (object != null) {
//            Gson gson = new Gson();
//            parameterBuilder.append(gson.toJson(object));
//        }
//
//        return parameterBuilder.toString();
//
//    }

    public static String objectToXml(Object object, String alias) {
        if (object != null) {
            StringBuilder parameterBuilder = new StringBuilder();
            XmlStream xmlStream = new XmlStream();
            XStream xstream = xmlStream.getxStream();

            if (VtClassUtils.isPrimitiveOrWrapper(object.getClass())) {
                xstream.alias(alias, String.class);
                parameterBuilder.append(xstream.toXML(object.toString()));
            } else if (object.getClass().equals(String.class)) {
                if (isXMLLike(object.toString())) {
                    parameterBuilder.append(object.toString());
                } else {
                    xstream.alias(alias, String.class);
                    parameterBuilder.append(xstream.toXML(object.toString()));
                }
            } else {
                xstream.alias(alias, object.getClass());
                parameterBuilder.append(xstream.toXML(object));
            }
            return parameterBuilder.toString();
        }
        return "<null/>";

    }

    public static String toXml(Object object) {
        return new XmlStream().getxStream().toXML(object);

    }

    public static String objectToXml(Object object) {
        return objectToXml(object, "return");

    }

    //    public static void main(String[] args) throws ClassNotFoundException {
//        List<Object> list = new ArrayList<>();
//        list.add(new BpmParameter("test",100L,Long.class.getSimpleName()));
//        System.out.println(formatParameters(list));
//        System.out.println(new BpmParameter("test", 100L, Long.class.getSimpleName()).getValue());
//        System.out.println(localXmlToObject("&lt;return&gt;1000&lt;/return&gt;","Long"));
//    }
    //parsing for order data
    public static Object localXmlToObject(String xml, String type) throws ClassNotFoundException {
        if (xml != null && type != null) {
            XmlStream xmlStream = new XmlStream();
            XStream xstream = xmlStream.getxStream();
            switch (type) {
                case "Boolean":
                    xstream.alias("return", Boolean.class);
                    break;
                case "Date":
                    xstream.alias("return", Date.class);
                    break;
                case "Long":
                    xstream.alias("return", Long.class);
                    break;
                case "Double":
                    xstream.alias("return", Double.class);
                    break;
                case "WsConfig":
                    xstream.alias("return", WsConfig.class);
                    break;
                case "String":
                    xstream.alias("return", String.class);
                    break;
                case "ArrayList":
                case "List":
                    xstream.alias("list", List.class);
                    break;
                default: {
                    xstream.alias("return", Class.forName(type));
                }
                break;
            }
            return xstream.fromXML(xml);
        }
        return null;

    }

    public static Object xmlToObjectFullType(String xml, String type) throws ClassNotFoundException {
        if (xml != null && type != null) {
            XmlStream xmlStream = new XmlStream();
            XStream xstream = xmlStream.getxStream();
            xstream.alias("return", Class.forName(type));
            return xstream.fromXML(xml);
        }
        return null;

    }

    /**
     * @param content
     * @return
     */
    public static Map<String, String> getPairKeyValues(String content) {

        Document doc = parseXmlFile(content);

        Map<String, String> dataSuggestions = new HashMap<>();

        if (doc.hasChildNodes()) {

            dataSuggestions = printLoopNote(dataSuggestions, doc.getChildNodes());

        }
        return dataSuggestions;
    }

    private static Map<String, String> printLoopNote(Map<String, String> map, NodeList nodeList) {
        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                // get node name and value
//                System.out.println("\nNode Name =" + tempNode.getNodeName());
//                System.out.println("Node Value =" + tempNode.getTextContent());
                if (tempNode.hasAttributes()) {
                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {

                        Node node = nodeMap.item(i);
//                        System.out.println("attr name : " + node.getNodeName());
//                        System.out.println("attr value : " + node.getNodeValue());
                        map.put(node.getNodeName(), node.getNodeValue());
                    }
                }

                if (tempNode.hasChildNodes()) {

                    // loop again if has child nodes
                    printLoopNote(map, tempNode.getChildNodes());

                } else {
                    map.put(tempNode.getNodeName(), tempNode.getNodeValue());
                }

//                System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
            }

        }
        return map;

    }

    /**
     * formated one character sequence
     *
     * @param unformattedXml
     * @return
     */
    public static String formatXML(String unformattedXml) {
        try {
            Document document = parseXmlFile(unformattedXml);
            OutputFormat format = new OutputFormat(document);
            format.setIndenting(true);
            format.setIndent(3);
            format.setOmitXMLDeclaration(true);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String node2String(Node node)
            throws TransformerFactoryConfigurationError, TransformerException {
        // you may prefer to use single instances of Transformer, and
        // StringWriter rather than create each time. That would be up to your
        // judgement and whether your app is single threaded etc
        StreamResult xmlOutput = new StreamResult(new StringWriter());
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(new DOMSource(node), xmlOutput);
        return xmlOutput.getWriter().toString();
    }

    /**
     * parse data into document (xml document)
     *
     * @param data
     * @return
     */
    public static Document parseXmlFile(String data) {
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(data));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String unformat = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:getOrderTypesResponse xmlns:ns2=\"http://server.activiti.bpm.viettel.com/\"><return><actRefProcdef>CLOSE_BOOK_BLOCK_CYCLE_REAL:1:268385</actRefProcdef><businessRequest>add14</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-17T09:26:27+07:00</createTime><name>add14</name><orTypeId>25206</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>14</telServiceCode></return><return><actRefProcdef>test_call_web_service:7:302791</actRefProcdef><businessRequest>testid</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-16T17:51:17+07:00</createTime><name>kiendv</name><orTypeId>25051</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>123</telServiceCode></return><return><actRefProcdef>process:2:11</actRefProcdef><businessRequest>test</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-15T09:58:45+07:00</createTime><name>kiendv2</name><orTypeId>21100</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>1</subSystemId><subsystemDTO><code>PRODUCT</code><createTime>2014-10-23T10:42:22+07:00</createTime><name>PRODUCT</name><status>49</status><subsystemId>1</subsystemId></subsystemDTO><telServiceCode>123</telServiceCode></return><return><actRefProcdef>RegisterProblem:58:317591</actRefProcdef><businessRequest>test</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-17T08:18:48+07:00</createTime><name>kiendv2_add</name><orTypeId>25150</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>12</telServiceCode></return><return><actRefProcdef>RegisterProblem:29:122939</actRefProcdef><businessRequest>test12</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-17T09:12:07+07:00</createTime><name>kiendv2_add12</name><orTypeId>25204</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>12</telServiceCode></return><return><actRefProcdef>CLOSE_BOOK_PROCESS_REAL:5:287508</actRefProcdef><businessRequest>add13</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-17T09:25:53+07:00</createTime><name>kiendv2_add13</name><orTypeId>25205</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>13</telServiceCode></return><return><actRefProcdef>RegisterProblem:58:317591</actRefProcdef><businessRequest>test1</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-16T17:19:48+07:00</createTime><name>kiendv2_checkAddOrdertype</name><orTypeId>25050</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>12342</telServiceCode></return><return><actRefProcdef>startonly:6:297512</actRefProcdef><businessRequest>muahanhvsmoija</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-17T11:02:07+07:00</createTime><name>phuanhanh</name><orTypeId>25251</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>112</telServiceCode></return><return><actRefProcdef>test_call_web_service:7:302791</actRefProcdef><businessRequest>testhangphu</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-17T11:00:51+07:00</createTime><name>phuvk</name><orTypeId>25250</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>ngonngot</telServiceCode></return><return><actRefProcdef>RegisterProblem:55:252504</actRefProcdef><businessRequest>RegisterTicket</businessRequest><createEmulatorOrder>0</createEmulatorOrder><createTime>2014-12-01T17:29:59+07:00</createTime><name>problem-test</name><orTypeId>12100</orTypeId><preExecutionId>storeVariables:8:92504</preExecutionId><startOrderExecution>0</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>Mobile</telServiceCode></return><return><actRefProcdef>saleConnection:7:468007</actRefProcdef><businessRequest>CONNECTION</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-13T12:01:13+07:00</createTime><name>Saleconnection2</name><orTypeId>20051</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>2</subSystemId><subsystemDTO><code>Sale</code><createTime>2014-11-13T11:14:06+07:00</createTime><name>Sale</name><status>49</status><subsystemId>2</subsystemId></subsystemDTO><telServiceCode>PSTN</telServiceCode></return><return><actRefProcdef>test_call_web_service:1:302507</actRefProcdef><businessRequest>business</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-15T14:44:43+07:00</createTime><name>TestCallWebservice</name><orTypeId>22050</orTypeId><startOrderExecution>0</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>telecomservice</telServiceCode></return><return><actRefProcdef>process:61:327504</actRefProcdef><businessRequest>thiendn1</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-16T18:56:04+07:00</createTime><name>thiendn1</name><orTypeId>25100</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>thiendn1</telServiceCode></return><return><actRefProcdef>RegisterProblem:55:252504</actRefProcdef><businessRequest>Viettel</businessRequest><createEmulatorOrder>1</createEmulatorOrder><createTime>2014-12-15T08:29:55+07:00</createTime><name>Thongluong</name><orTypeId>21050</orTypeId><startOrderExecution>1</startOrderExecution><status>1</status><subSystemId>3</subSystemId><subsystemDTO><code>Problem</code><createTime>2014-11-26T00:00:00+07:00</createTime><name>Problem</name><status>49</status><subsystemId>3</subsystemId></subsystemDTO><telServiceCode>VT</telServiceCode></return></ns2:getOrderTypesResponse></soap:Body></soap:Envelope>";
    }

}
