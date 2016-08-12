/*
 * Copyright (C) 2010 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vfw5.base.utils;

import java.util.ResourceBundle;

/**
 *
 * @author thienkq1@viettel.com.vn
 * @since 12,Apr,2010
 * @version 1.0
 */
public class ResourceBundleUtils {

    /** rb.*/
    private static ResourceBundle rb = null;

    /** Creates a new instance of ResourceBundleUtils */
    private ResourceBundleUtils() {
    }

    /**
     * method get resource
     * @param key String
     * @return String
     */
    public static String getResource(String key) {
        rb = ResourceBundle.getBundle("config");
        return rb.getString(key);
    }
}
