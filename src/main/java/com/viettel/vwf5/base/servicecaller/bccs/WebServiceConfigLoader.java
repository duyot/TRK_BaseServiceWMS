package com.viettel.vwf5.base.servicecaller.bccs;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.support.http.SoapUIMultiThreadedHttpConnectionManager;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.model.iface.Operation;
import com.viettel.vfw5.base.utils.Constants;
import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vtsoft on 1/16/2015.
 */
public class WebServiceConfigLoader {

    CombinedConfiguration configuration;
    private final String DEFAULT_ROOT_FILE = "wsconfig.xml";

    private ConcurrentHashMap<String, ConcurrentHashMap<String, WsTemplate>> wsTemplateMap = new ConcurrentHashMap<>();

    private static WebServiceConfigLoader instance;

    static {
        instance = new WebServiceConfigLoader();
    }

    public WebServiceConfigLoader() {
        XMLConfiguration rootConfig = null;
        try {
            configuration = new CombinedConfiguration();
            //load all config file
            rootConfig = new XMLConfiguration(DEFAULT_ROOT_FILE);
            List<Object> configList = rootConfig.getList("files");
            configuration.addConfiguration(rootConfig);
            for (Object configFile : configList) {
                if (!configFile.toString().equals(DEFAULT_ROOT_FILE)) {
                    XMLConfiguration newRootConfig = null;
                    newRootConfig = new XMLConfiguration(configFile.toString());
                    configuration.addConfiguration(newRootConfig);
                }
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (configuration != null) {
            try {
                loadAllWsConfig();
            } catch (Exception ex) {
                Logger.getLogger(WebServiceConfigLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void loadAllWsConfig() {
        //prepare hashmap for webservice
        try {
            Iterator<String> keys = configuration.getKeys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object object = configuration.getProperty(key);
                if (key.contains("config-class") && object.equals(WsRequestCreator.class.getName())) {
                    WsRequestCreator wsConfig = null;
                    try {
                        wsConfig = getWsConfig(key.replaceAll("\\[.*\\]", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String wsdl = wsConfig.getWsAddress();
                    //test if wsdl is handled
                    if (wsdl != null) {
                        if (wsTemplateMap.get(wsdl) == null) {
                            ConcurrentHashMap<String, WsTemplate> temp = null;
                            try {
                                temp = importWSDL(wsdl);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (!temp.isEmpty()) {
                                wsTemplateMap.put(wsdl.replaceAll("(?ui)\\?wsdl", ""), temp);
                            }
                        }
                    }
                }
            }
        } finally {
            closeSoapUI();
        }
    }

    private ConcurrentHashMap<String, WsTemplate> importWSDL(String wsdl) throws Exception {
        ConcurrentHashMap<String, WsTemplate> temp = new ConcurrentHashMap<>();
        WsdlProject project = new WsdlProject();
        for (Operation operation : WsdlImporter.importWsdl(project, wsdl)[0].getAllOperations()) {
            WsdlOperation wsdlOperation = (WsdlOperation) operation;
            ByteArrayInputStream is = new ByteArrayInputStream(wsdlOperation.createRequest(true).getBytes());
            SOAPMessage request = MessageFactory.newInstance().createMessage(null, is);
            WsTemplate template = new WsTemplate();
            //prepare for header
            SOAPHeader header = request.getSOAPHeader();
            if (header.getChildNodes() != null && header.getChildNodes().getLength() > 0) {
                List<String> headerNodeList = new ArrayList<>();
                template.setHeaderList(printAlias(headerNodeList, header.getChildNodes()));
                header.removeContents();
                header.setTextContent(Constants.WEB_SERVICE_CONS.ARG_HEADER);
            }
            //prepare for body
            SOAPBody body = request.getSOAPBody();
            String operatorMethod = body.getChildNodes().item(1).getNodeName();
            List<String> list = new ArrayList<>();
            template.setAliasList(printAlias(list, body.getChildNodes().item(1).getChildNodes()));
            body.removeContents();
            SOAPBodyElement bodyElement = body
                    .addBodyElement(new QName(operatorMethod));
            bodyElement.setTextContent(Constants.WEB_SERVICE_CONS.ARG_BODY);
            template.setSoapMessage(XmlSchema.node2String(request.getSOAPPart()));
            if (operatorMethod.contains(Constants.WEB_SERVICE_CONS.SEPERATE_OPERATOR)) {
                operatorMethod = operatorMethod.split(Constants.WEB_SERVICE_CONS.SEPERATE_OPERATOR)[1];
            }
            temp.put(operatorMethod, template);
        }
        return temp;

    }

    public static WebServiceConfigLoader getInstance() {
        return instance;
    }

    private CombinedConfiguration getConfiguration() {
        return configuration;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, WsTemplate>> getWsTemplateMap() {
        return wsTemplateMap;
    }

    public void setWsTemplateMap(ConcurrentHashMap<String, ConcurrentHashMap<String, WsTemplate>> wsTemplateMap) {
        this.wsTemplateMap = wsTemplateMap;
    }

    //use print note to get all level 1 alias
    private List<String> printAlias(List<String> list, NodeList nodeList) {
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            // get node name
            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                list.add(tempNode.getNodeName());
            }
        }
        return list;

    }

    private void closeSoapUI() {
        // Need to shutdown all the threads invoked by each SoapUI TestSuite
        SoapUI.getThreadPool().shutdown();
        try {
            SoapUI.getThreadPool().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Now to shutdown the monitor thread setup by SoapUI
        Thread[] tarray = new Thread[Thread.activeCount()];
        Thread.enumerate(tarray);
        for (Thread t : tarray) {
            if (t instanceof SoapUIMultiThreadedHttpConnectionManager.IdleConnectionMonitorThread) {
                ((SoapUIMultiThreadedHttpConnectionManager.IdleConnectionMonitorThread) t)
                        .shutdown();
            }
        }
        // Finally Shutdown SoapUI itself.
        SoapUI.shutdown();
    }

    public WsRequestCreator getWsConfig(String key) throws Exception {
        BeanDeclaration decl = new XMLBeanDeclaration(configuration, key);
        if (decl == null) {
            throw new Exception("The key " + key + "not found");
        }
        WsRequestCreator ws = (WsRequestCreator) BeanHelper.createBean(decl);
        return ws;
    }

    public static void main(String[] args) throws Exception {
//        WebServiceConfigLoader webServiceConfigLoader =   new WebServiceConfigLoader();
//        webServiceConfigLoader.importWSDL("http://10.58.71.129:8007/OrderExplorer/bpm-service/orderService?wsdl");
////        webServiceConfigLoader.importWSDL("http://10.60.19.23:9898/wsocs?wsdl");
//        webServiceConfigLoader.closeSoapUI();

    }

}
