/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.InventoryActionDTO;
import com.viettel.logistic.wms.dto.InventoryResultDTO;
import com.viettel.logistic.wms.model.InventoryAction;
import com.viettel.logistic.wms.model.InventoryResult;
import com.viettel.vfw5.base.model.BaseFWModel;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;

/**
 *
 * @author duyot
 */
public interface InventoryResultBusinessInterface extends BaseFWServiceInterface<InventoryResultDTO, InventoryResult>{
    //get list checked inventory result
     public List<InventoryResultDTO> getLstCheckedResult(String inventoryActionId);
     //get list checked inventory result
     public List<InventoryResultDTO> getInventoryResultByDisplayField(String inventoryActionId,List<String> lstShowField);
}
