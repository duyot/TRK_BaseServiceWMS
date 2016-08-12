/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;
import com.viettel.logistic.wms.business.service.InventoryReportInterface;
import com.viettel.logistic.wms.business.service.InventoryResultBusinessInterface;

import com.viettel.logistic.wms.business.service.SerialInventoryBusinessInterface;
import com.viettel.logistic.wms.dto.SerialInventoryDTO;
import com.viettel.logistic.wms.dto.SerialInventoryReportDTO;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 4/6/2016 9:40 AM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.SerialInventoryService")
public class SerialInventoryServiceImpl implements SerialInventoryService {

    @Autowired
    BaseFWServiceInterface serialInventoryBusiness;
    @Autowired
    InventoryReportInterface inventoryReportInterface;
    
    @Autowired
    SerialInventoryBusinessInterface serialInventoryBusiness2;
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;

    @Override
    public String updateSerialInventory(SerialInventoryDTO serialInventoryDTO) {
        return serialInventoryBusiness.update(serialInventoryDTO);
    }

    @Override
    public String deleteSerialInventory(Long id) {
        return serialInventoryBusiness.delete(id);
    }

    @Override
    public String deleteListSerialInventory(List<SerialInventoryDTO> serialInventoryListDTO) {
        return serialInventoryBusiness.delete(serialInventoryListDTO);
    }

    @Override
    public SerialInventoryDTO findSerialInventoryById(Long id) {
        if (id != null && id > 0) {
            return (SerialInventoryDTO) serialInventoryBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<SerialInventoryDTO> getListSerialInventoryDTO(SerialInventoryDTO serialInventoryDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (serialInventoryDTO != null) {
            return serialInventoryBusiness.search(serialInventoryDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public String insertSerialInventory(SerialInventoryDTO serialInventoryDTO) {
        return serialInventoryBusiness.create(serialInventoryDTO);
    }

    @Override
    public String insertOrUpdateListSerialInventory(List<SerialInventoryDTO> serialInventoryDTO) {
        return serialInventoryBusiness.insertList(serialInventoryDTO);
    }

    @Override
    public List<String> getSequense(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return serialInventoryBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<SerialInventoryDTO> getListSerialInventoryByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<SerialInventoryDTO> lstGoods = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
//                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER_DOUBLE)) {
                con.setType(ParamUtils.NUMBER_DOUBLE);
            } else {
                String value = "";
                if (con.getOperator().equalsIgnoreCase(ParamUtils.NAME_LIKE)) {
                    value = StringUtils.formatLike(con.getValue());
                    con.setValue(value.toLowerCase());
                    con.setField(StringUtils.formatFunction("lower", con.getField()));
                } else {
                    value = con.getValue();
                    con.setValue(value);
                    con.setField(con.getField());
                }
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));
        }
        lstGoods = serialInventoryBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstGoods;
    }

    @Override
    public List<SerialInventoryReportDTO> getListSerialInventoryReport(SerialInventoryDTO serialInventoryDTO, int type) {
        return inventoryReportInterface.getListSerialInventoryReport(serialInventoryDTO, type);
    }

    @Override
    public ResultDTO insertListSerialInventoryBatch(List<SerialInventoryDTO> serialInventoryDTO) {
        ResultDTO resultDTO = new ResultDTO();
        Transaction transaction;
        Connection connection = null;
        Session session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();
        SessionFactory sessionFactoryBatch = session.getSessionFactory();
        try {
            connection = sessionFactoryBatch.getSessionFactoryOptions().
                    getServiceRegistry().getService(ConnectionProvider.class).getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(SerialInventoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        resultDTO = serialInventoryBusiness2.insertListSerialInventoryBatch(serialInventoryDTO, connection);
        if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
            rollback(session, transaction, connection);
        }
        commit(session, transaction, connection);
        return resultDTO;
    }

    private void rollback(Session session, Transaction transaction, Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
            if (transaction != null) {
                transaction.rollback();
            }

            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    private void commit(Session session, Transaction transaction, Connection con) {
        try {
            if (transaction != null) {
                transaction.commit();
            }
            if (con != null) {
                con.commit();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }

            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockImportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
