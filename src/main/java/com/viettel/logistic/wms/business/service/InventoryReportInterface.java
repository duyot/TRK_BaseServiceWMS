/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.SerialInventoryDTO;
import com.viettel.logistic.wms.dto.SerialInventoryReportDTO;
import com.viettel.logistic.wms.model.SerialInventory;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;

/**
 *
 * @author HongDQ4
 */
public interface InventoryReportInterface extends BaseFWServiceInterface<SerialInventoryDTO,SerialInventory>{
      //duyot
    //duyot 11/05
    public List<SerialInventoryReportDTO> getListSerialInventoryReport(SerialInventoryDTO serialInventoryDTO, int type);
}
