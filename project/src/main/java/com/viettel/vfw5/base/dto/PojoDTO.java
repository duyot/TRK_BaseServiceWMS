/*
 * Copyright (C) 2012 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vfw5.base.dto;

import java.util.List;

/**
 *
 * @author kdvt_binhnt22@viettel.com.vn
 * @version 1.0
 * @since May 2012
 */
public class PojoDTO {

    public static String IDENTIFIER = "key";
    public static String LABEL = "value";
    private Object key;
    private String value;

    public PojoDTO(Object key, String value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean checkInclude(List<PojoDTO> listBean, Object sValue) {
        if (sValue != null) {
            if (listBean != null && !listBean.isEmpty()) {
                for (int i = 0; i < listBean.size(); i++) {
                    if (listBean.get(i).getValue().toString().equals(sValue.toString())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkIncludeKey(List<PojoDTO> listBean, Object sValue) {
        if (sValue != null) {
            if (listBean != null && !listBean.isEmpty()) {
                for (int i = 0; i < listBean.size(); i++) {
                    if (listBean.get(i).getKey().toString().equals(sValue.toString())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
