package com.viettel.vwf5.base.servicecaller;

import com.viettel.vwf5.base.servicecaller.XStreamStorage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

import java.util.List;

public class XmlStream {

    private XStream xStream = null;
    public static final int SINGLE_TYPE = 1;
    public static final int COLLECTION_TYPE = 2;
    public boolean isSingleType = true;
    private boolean skipUnknowTag = true;
   String[] formats = { "yyyy-MM-dd",
            "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss",
            "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'",
            "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS",
            "MM/dd/yyyy'T'HH:mm:ssZ", "MM/dd/yyyy'T'HH:mm:ss",
            "yyyy:MM:dd HH:mm:ss", "yyyyMMdd","dd/MM/yyyy'T'HH:mm:ss"};
    public XmlStream() {
        xStream = new XStream(new DomDriver());
        xStream.ignoreUnknownElements();
        xStream.registerConverter(new DateConverter("yyyy-MM-dd'T'HH:mm:ss", formats));
    }

    public XmlStream(boolean skipUnknowTag) {
//        if (skipUnknowTag) {  
//            xStream = new XStream() {
//                @Override
//                protected MapperWrapper wrapMapper(MapperWrapper next) {
//                    return new MapperWrapper(next) {
//                        @Override
//                        public boolean shouldSerializeMember(Class definedIn, String fieldName) {
//
//                            if (definedIn == Object.class) {
//                                return false;
//                            }
//                            try {
//                                Field someField = definedIn.getDeclaredField(fieldName);
//                            } catch (NoSuchFieldException ex) {
//                                System.out.println("define in: "+definedIn);
//                                System.out.println(ex);
//                                return false;
//                            } catch (SecurityException ex) {
//                                Logger.getLogger(XmlStream.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            System.out.println("fieldName: " + fieldName + "/ class: " + definedIn);
////                            if(getImplicitCollectionDefForFieldName(definedIn, fieldName)==null){
////                                return false;
////                            }
//                            try {
//                                return super.shouldSerializeMember(definedIn, fieldName);
//
//                            } catch (Exception e) {
//                                return false;
//                            }
//                        }
//                    };
//                }
//            };
//        } else {
//            xStream = new XStream(new DomDriver());
//        }
        xStream = new XStream(new DomDriver());
        this.skipUnknowTag = skipUnknowTag;
        xStream.registerConverter(new DateConverter("yyyy-MM-dd'T'HH:mm:ss", formats));
        if (skipUnknowTag) {
            xStream.ignoreUnknownElements();
        }
    }

    public int getXmlStreamType() {
        if (isSingleType) {
            return SINGLE_TYPE;
        } else {
            return COLLECTION_TYPE;
        }
    }

    protected MapperWrapper wrapMapper(MapperWrapper next) {
        return new MapperWrapper(next) {
            @Override
            public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                if (definedIn == Object.class) {
                    return false;
                }
                return super.shouldSerializeMember(definedIn, fieldName);
            }
        };
    }

    public void config(List<XStreamStorage> xStreamStorageList) throws ClassNotFoundException {
        for (XStreamStorage xStreamStorage : xStreamStorageList) {
            config(xStreamStorage);
        }
    }

    public void config(XStreamStorage xStreamStorage)
            throws ClassNotFoundException {
        switch (xStreamStorage.getType()) {
            case XStreamStorage.ALIAS_TYPE:
                alias(xStreamStorage.getVariableStorage().get(0), xStreamStorage
                        .getVariableStorage().get(1));
                break;
            case XStreamStorage.ALIAS_FIELD_TYPE:
                aliasField(xStreamStorage.getVariableStorage().get(0),
                        xStreamStorage.getVariableStorage().get(1), xStreamStorage
                        .getVariableStorage().get(2));
                break;
            case XStreamStorage.ALIAS_COLLECTION_TYPE:
                addImplicitCollection(xStreamStorage.getVariableStorage().get(0),
                        xStreamStorage.getVariableStorage().get(1));
                isSingleType = false;
                break;
            case XStreamStorage.ALIAS_ARRAY_TYPE:
                addImplicitArray(xStreamStorage.getVariableStorage().get(0),
                        xStreamStorage.getVariableStorage().get(1));
                break;
            case XStreamStorage.ALIAS_MAP_TYPE:
                addImplicitMap(xStreamStorage.getVariableStorage().get(0),
                        xStreamStorage.getVariableStorage().get(1), xStreamStorage
                        .getVariableStorage().get(2), xStreamStorage
                        .getVariableStorage().get(3));
                isSingleType = false;
                break;
            case XStreamStorage.ALIAS_MAP__FIELD_TYPE:
                addImplicitMap(xStreamStorage.getVariableStorage().get(0),
                        xStreamStorage.getVariableStorage().get(1), xStreamStorage
                        .getVariableStorage().get(2), xStreamStorage
                        .getVariableStorage().get(3), xStreamStorage
                        .getVariableStorage().get(4));
                isSingleType = false;
                break;
            default:
                break;
        }
    }

    public void alias(String classType, String aliasName)
            throws ClassNotFoundException {
        // type = 0;
        xStream.alias(aliasName, Class.forName(classType));

    }

    public void aliasField(String classType, String aliasName, String fieldName)
            throws ClassNotFoundException {

        xStream.aliasField(aliasName, Class.forName(classType), fieldName);

    }

    public void addImplicitCollection(String classType, String fieldName)
            throws ClassNotFoundException {

        xStream.addImplicitCollection(Class.forName(classType), fieldName);

    }

    public void addImplicitArray(String classType, String fieldName)
            throws ClassNotFoundException {
        xStream.addImplicitArray(Class.forName(classType), fieldName);

    }

    public void addImplicitMap(String classType, String fieldName,
            String itemType, String keyFieldName) throws ClassNotFoundException {
        xStream.addImplicitMap(Class.forName(classType), fieldName,
                Class.forName(itemType), keyFieldName);

    }

    public void addImplicitMap(String classType, String fieldName,
            String itemName, String itemType, String keyFieldName)
            throws ClassNotFoundException {
        xStream.addImplicitMap(Class.forName(classType), fieldName, itemName,
                Class.forName(itemType), keyFieldName);

    }

    public XStream getxStream() {
        return xStream;
    }

    public void setxStream(XStream xStream) {
        this.xStream = xStream;
    }

    public boolean isSkipUnknowTag() {
        return skipUnknowTag;
    }

    public void setSkipUnknowTag(boolean skipUnknowTag) {
        this.skipUnknowTag = skipUnknowTag;
    }
}
