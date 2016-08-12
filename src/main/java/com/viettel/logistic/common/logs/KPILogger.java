/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.common.logs;

import com.viettel.logistic.wms.dto.KpiLogDTO;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.viettel.vfw5.base.utils.BundelUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author quyendm
 */
public class KPILogger {

    private static final String SYSTEM_DEFAULT = "BaseServiceWMS";
    //Duong dan Websevice
    public static String strWsWMSUrl = BundelUtils.getkey("wms_ws_url");
    //Duong dan ten dich
    public static String targetNamePath = "xmlns:cms=\"http://cms.viettel.vn\"";
    //Url WS Stock
    public static String strWSUrl = strWsWMSUrl + "orderGoodsDetailService";
    private List<KpiLogDTO> lstKpiLog;
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;
    @Autowired
    BaseFWServiceInterface kpiLogBusiness;

    public KPILogger() {
    }

    public static void createLogsStartAction(String description) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        Date date = new Date();
        Logger businessLog = Logger.getLogger("kpiLog");
        businessLog.error("START_ACTION|"
                + SYSTEM_DEFAULT + "|"
                + dateFormat.format(date) + "|"
                + description);
    }

    //log_type|app_code|YYYY/MM/DD hh:mi:ss:ms|username|ip_address|path|function|param_list|class|duration|description
    public static void createLogsEndAction(Object paramList, String description, long startTime) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        Date date = new Date();
        Logger businessLog = Logger.getLogger("kpiLog");
        long endTime = System.currentTimeMillis() - startTime;
        businessLog.error("END_ACTION|"
                + SYSTEM_DEFAULT + "|"
                + dateFormat.format(date) + "|"
                + paramList + "|"
                + description + "| Thoi gian : "
                + endTime);
    }

    //log_type|app_code|YYYY/MM/DD hh:mi:ss:ms|username|ip_address|path|function|param_list|class|duration|description
    public static void createLogs(String message) {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
//        Date date = new Date();
        Logger businessLog = Logger.getLogger("kpiLog");
        businessLog.error(message);
    }

    public static KPILogger getInstance() {
        return new KPILogger();
    }

    //Tao log luu ra bang kpi_log
    public void create(KpiLogDTO kpiLogDTO) {
        Session session;
        Transaction transaction;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            kpiLogBusiness.createObjectSession(kpiLogDTO, session);
            transaction.commit();
            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                checkMemory(kpiLogDTO.getFunctionCode());
            } catch (Exception ex) {

            } finally {
                System.out.println("finally");
            }
        }
    }

    public static void checkMemory(String functionName) {
        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024 * 1024;

        System.out.println("##### Heap utilization statistics [MB] #####");

        //Print used memory
        System.out.println(functionName + " Used Memory:"
                + (runtime.totalMemory() - runtime.freeMemory()) / mb);

        //Print free memory
        System.out.println(functionName + " Free Memory:"
                + runtime.freeMemory() / mb);

        //Print total available memory
        System.out.println(functionName + " Total Memory:" + runtime.totalMemory() / mb);

        //Print Maximum available memory

        System.out.println(functionName + " Max Memory:" + runtime.maxMemory() / mb);

    }
}
