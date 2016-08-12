/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.KpiLogInterface;
import com.viettel.logistic.wms.dao.KpiLogDAO;
import com.viettel.logistic.wms.dto.KpiLogDTO;
import com.viettel.logistic.wms.model.KpiLog;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import org.hibernate.Session;
import javax.transaction.Transactional;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author duyot
 * @version 1.0
 * @since 12/31/2015 3:40 PM
 */
@Service("kpiLogBusiness")
@Transactional
public class KpiLogBusiness extends BaseFWServiceImpl<KpiLogDAO, KpiLogDTO, KpiLog> implements KpiLogInterface {

    @Autowired
    private KpiLogDAO kpiLogDAO;
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;

    public KpiLogBusiness() {
        tModel = new KpiLog();
        tDAO = kpiLogDAO;
    }

    @Override
    public KpiLogDAO gettDAO() {
        return kpiLogDAO;
    }

    public KpiLogBusiness(Session session) {
        this.session = session;
        tModel = new KpiLog();
        tDAO = kpiLogDAO;
    }

    @Override
    public void createKpiLog(KpiLogDTO kpiLogDTO) {
        Session session;
        Transaction transaction;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            kpiLogDAO.saveObjectSession(kpiLogDTO.toModel(), session);
            transaction.commit();
            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
            try {
                checkMemory(kpiLogDTO.getFunctionCode(), kpiLogDTO.getTransactionCode());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                System.out.println("finally");
            }
        }
    }

    private void checkMemory(String functionName, String transactionCode) {
        
        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024 * 1024;

        System.out.println("##### Heap utilization statistics [MB] #####");
        System.out.println("##### Transaction code = " + transactionCode + " #####");

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
