/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.SerialInventoryDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author vtsoft
 */
public interface SerialInventoryBusinessInterface {

    public ResultDTO insertListSerialInventoryBatch(List<SerialInventoryDTO> serialInventoryDTO, Connection connection);
}
