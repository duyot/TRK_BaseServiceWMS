/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vwf5.base.servicecaller.bccs;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TruongBx3
 */
public class CustomizeDomDriver extends DomDriver {

    private static final List CDATA_FIELDS;
    WsRequestCreator wsRequest;

    static {
        CDATA_FIELDS = new ArrayList();
        CDATA_FIELDS.add("rawData");
    }

    public CustomizeDomDriver(WsRequestCreator wsRequest) {
        super();
        this.wsRequest = wsRequest;
    }

    @Override
    public HierarchicalStreamWriter createWriter(Writer out) {
        return new PrettyPrintWriter(out) {
            boolean cdata = false;

            public void startNode(String name) {
                super.startNode(name);
                cdata = CDATA_FIELDS.contains(name);

            }

            protected void writeText(QuickWriter writer, String text) {
                if (cdata) {
                    writer.write("<![CDATA[");
                    String value = text.replace("Input",wsRequest.getServiceName());
                    writer.write(value);
                    writer.write("]]>");

                } else {
                    writer.write(text);
                }
            }
        };

    }
}
