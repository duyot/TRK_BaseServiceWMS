/*
 * Copyright (C) 2010 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vfw5.base.utils;

/**
 *
 * @author thangdd8@viettel.com.vn
 * @since Apr 12, 2010
 * @version 1.0
 */
public class ValidateUtils {

    /**
     * private contructor
     */
    private ValidateUtils() {
    }

    /**
     * <P>Check is Integer or not</P>
     *
     * @param str String to check
     * @param str
     * @return @boolean true if valid, false if not valid
     */
    public static boolean isInteger(String str) {
        if (str == null || !str.matches("[0-9]+$")) {
            return false;
        }
        return true;
    }
}
