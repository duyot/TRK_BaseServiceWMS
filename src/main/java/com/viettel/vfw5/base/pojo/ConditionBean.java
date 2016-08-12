/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.vfw5.base.pojo;

/**
*
* @author kdvt_binhnt22@viettel.com.vn
* @version 1.0
* @since since_text
*/

public class ConditionBean {
    private String field;
    private String value;
    private String operator;
    private String type;

    public ConditionBean() {
        
    }

    public ConditionBean(String field, String operator, String value) {
        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    public ConditionBean(String field, String operator, String value, String type) {
        this.field = field;
        this.value = value;
        this.operator = operator;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}