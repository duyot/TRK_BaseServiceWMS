package com.viettel.vwf5.base.servicecaller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XStreamStorage implements Serializable {

    public static final int ALIAS_TYPE = 0;
    public static final int ALIAS_FIELD_TYPE = 1;
    public static final int ALIAS_COLLECTION_TYPE = 2;
    public static final int ALIAS_ARRAY_TYPE = 3;
    public static final int ALIAS_MAP_TYPE = 4;
    public static final int ALIAS_MAP__FIELD_TYPE = 5;

    private int type = 0; //addAlias, addField
    List<String> variableStorage = new ArrayList<String>();

    public XStreamStorage(int type) {
        super();
        this.type = type;
    }

    public XStreamStorage(int type, List<String> streamVariable) {
        super();
        this.type = type;
        this.variableStorage = streamVariable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getVariableStorage() {
        return variableStorage;
    }

    public void setVariableStorage(List<String> variableStorage) {
        this.variableStorage = variableStorage;
    }

}
