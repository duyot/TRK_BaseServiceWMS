package com.viettel.vwf5.base.servicecaller;

import java.util.List;
import java.util.Map;

/**
 * Created by vtsoft on 1/19/2015.
 */
public class WsAttContent {

    private Map<String, Object> argAlias;
    private List<Object> parameters;
    private String parameterTxt;

    public Map<String, Object> getArgAlias() {
        return argAlias;
    }

    public void setArgAlias(Map<String, Object> argAlias) {
        this.argAlias = argAlias;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }


    public String getParameterTxt() {
        return parameterTxt;
    }

    public void setParameterTxt(String parameterTxt) {
        this.parameterTxt = parameterTxt;
    }

}
