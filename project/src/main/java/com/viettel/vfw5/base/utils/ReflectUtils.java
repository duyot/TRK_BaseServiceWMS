/*
 * Copyright (C) 2012 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vfw5.base.utils;

//import com.viettel.ra.database.BO.RaProcessBO;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import javax.persistence.Column;

/**
 *
 * @author kdvt_binhnt22@viettel.com.vn
 * @version 1.0
 * @since May 2012
 */
public class ReflectUtils {

    public static final String ANNO_COLUMN_NAME = "name=";
    public static final String ANNO_TEMPORAL_TYPE = "value=";

    public static void main(String args[]) {
//        RaProcessBO test = new RaProcessBO();
//        test.setDescription("======abc======");
//        test.setProcessCode("-----code--------");
//        Method[] methods = test.getClass().getMethods();
//        System.out.println("getMethods");
//        for (int i = 0; i < methods.length; i++) {
//            if (isGetter(methods[i])) {
//                System.out.print("\tColumn name: " + getColumnBeanName(methods[i]));
//                try {
//                    System.out.println("\t" + String.valueOf(methods[i].invoke(test)));
//                } catch (Exception ex) {
//                }
//            }
//        }
//        System.out.println("getDeclareMethod");
//        methods = test.getClass().getDeclaredMethods();
//        for (int i = 0; i < methods.length; i++) {
//            if (isGetter(methods[i])) {
//                System.out.print("\tColumn name: " + getColumnBeanName(methods[i]));
//                System.out.print("\t" + methods[i].getReturnType().getSimpleName());
//                System.out.println("\t" + methods[i].getGenericReturnType().toString());
//            }
//        }
    }

    public static String getAnnotationProperty(Method method, String property) {
        String result = "";
        Annotation annos = method.getAnnotation(Column.class);
        if (annos != null) {
            String annoColumn = annos.toString();
            result = annoColumn.substring(
                    annoColumn.indexOf(property) + property.length(),
                    annoColumn.indexOf(",", annoColumn.indexOf(property)));
        }
        return result;
    }

    public static String getColumnBeanName(Method method) {
        String methodName = method.getName();
        return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
    }

    public static String getColumnName(Method method) {
        return getAnnotationProperty(method, ANNO_COLUMN_NAME);
    }

    public static String getTemporalType(Method method) {
        return getAnnotationProperty(method, ANNO_TEMPORAL_TYPE);
    }

    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        if (method.getReturnType().equals(Void.TYPE)) {
            return false;
        }
        return true;
    }

    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        if (!method.getReturnType().equals(Void.TYPE)) {
            return false;
        }
        return true;
    }
}
