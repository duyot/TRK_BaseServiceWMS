/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vfw5.base.dto;

import com.viettel.logistic.wms.dto.MapStaffGoodsDTO;
import java.util.List;

/**
 *
 * @author thienng1
 */
public class ResultMapStaffGoodsDTO extends ResultDTO {

    private List<MapStaffGoodsDTO> lstMapStaffGoodsDTO;

    public List<MapStaffGoodsDTO> getLstMapStaffGoodsDTO() {
        return lstMapStaffGoodsDTO;
    }

    public void setLstMapStaffGoodsDTO(List<MapStaffGoodsDTO> lstMapStaffGoodsDTO) {
        this.lstMapStaffGoodsDTO = lstMapStaffGoodsDTO;
    }

}
