/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.business;

import com.viettel.logistic.wms.business.service.InventoryReportInterface;
import com.viettel.logistic.wms.business.service.SerialInventoryBusinessInterface;
import com.viettel.logistic.wms.dao.SerialInventoryDAO;
import com.viettel.logistic.wms.dto.SerialInventoryDTO;
import com.viettel.logistic.wms.dto.SerialInventoryReportDTO;
import com.viettel.logistic.wms.model.SerialInventory;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import java.util.List;
import java.sql.Connection;
import java.util.List;
import org.hibernate.Session;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ngocnd6
 * @version 1.0
 * @since 4/6/2016 9:40 AM
 */
@Service("serialInventoryBusiness")
@Transactional
	
public class SerialInventoryBusiness extends BaseFWServiceImpl<SerialInventoryDAO, SerialInventoryDTO, SerialInventory> implements InventoryReportInterface,SerialInventoryBusinessInterface {

    @Autowired
    private SerialInventoryDAO serialInventoryDAO;

    public SerialInventoryBusiness() {
        tModel = new SerialInventory();
        tDAO   = serialInventoryDAO;
    }

    @Override
    public SerialInventoryDAO gettDAO() {
        return serialInventoryDAO;
    }

    public SerialInventoryBusiness(Session session) {
        this.session = session;
        tModel = new SerialInventory();
        tDAO = serialInventoryDAO;
    }

    @Override
    public List<SerialInventoryReportDTO> getListSerialInventoryReport(SerialInventoryDTO serialInventoryDTO, int type) {
        return serialInventoryDAO.getListSerialInventoryReport(serialInventoryDTO,type);
    }
    
     @Override
    public ResultDTO insertListSerialInventoryBatch(List<SerialInventoryDTO> serialInventoryDTO, Connection connection) {
        return gettDAO().insertListSerialInventoryBatch(serialInventoryDTO, connection);
    }
    
}
