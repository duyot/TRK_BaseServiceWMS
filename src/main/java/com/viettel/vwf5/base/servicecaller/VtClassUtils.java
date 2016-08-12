package com.viettel.vwf5.base.servicecaller;

import org.springframework.util.ClassUtils;

public class VtClassUtils {
	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(.\\d+)?");
	}

	public static boolean isPrimitiveOrWrapper(Class type) {
		return ClassUtils.isPrimitiveOrWrapper(type);
	}
        
        public static void main(String[] args){
            System.out.println(isPrimitiveOrWrapper(double.class));
            System.out.println(isPrimitiveOrWrapper(Double.class));
            System.out.println(isPrimitiveOrWrapper(int.class));
            System.out.println(isPrimitiveOrWrapper(Integer.class));
            System.out.println(isPrimitiveOrWrapper(boolean.class));
            System.out.println(isPrimitiveOrWrapper(Boolean.class));
            
                 
            
        }

}
