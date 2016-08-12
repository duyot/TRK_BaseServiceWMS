package com.devguerrilla.quickstart.cxf.countrylookup;

import com.viettel.logistic.cms.model.AppParams;
import com.viettel.logistic.cms.business.AppParamsBusiness;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppMain {

    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        BaseFWServiceInterface service = (BaseFWServiceInterface) context.getBean("appParamsBusiness");

        AppParams object = (AppParams)service.findById(1L);

        System.out.println(object.getParCode());
    }
}
