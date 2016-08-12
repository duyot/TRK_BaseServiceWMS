/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.wms.business.service;

import com.viettel.logistic.wms.dto.MapStaffGoodsDTO;
import com.viettel.logistic.wms.model.MapStaffGoods;
import com.viettel.vfw5.base.dto.ResultMapStaffGoodsDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Ngocnd6
 */
public interface MapStaffGoodsBusinessInterface extends BaseFWServiceInterface<MapStaffGoodsDTO, MapStaffGoods> {

    public ResultMapStaffGoodsDTO insertListMapStaffGoods(List<MapStaffGoodsDTO> lstmap, Session session);

    //lay danh sach nhan vien theo ma hang hoa
    public List<MapStaffGoodsDTO> getListStaffByGoods(String codeList, String custId);
}
