/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.google.common.collect.Lists;
import com.viettel.logistic.wms.business.service.MapStaffGoodsBusinessInterface;
import com.viettel.logistic.wms.dto.MapStaffGoodsDTO;
import com.viettel.logistic.wms.dto.StaffDTO;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import javax.jws.WebService;
import java.util.ArrayList;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.dto.ResultMapStaffGoodsDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author hongdq4
 * @version 1.0
 * @since 10/16/2015 10:12 AM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.MapStaffGoodsService")
public class MapStaffGoodsServiceImpl implements MapStaffGoodsService {

    @Autowired
    BaseFWServiceInterface mapStaffGoodsBusiness;

    @Autowired
    MapStaffGoodsBusinessInterface mapStaffGoodsBusiness1;

    @Autowired
    HibernateSessionFactoryImpl sessionFactory;

    @Override
    public String updateMapStaffGoods(MapStaffGoodsDTO mapStaffGoodsDTO) {
        return mapStaffGoodsBusiness.update(mapStaffGoodsDTO);
    }

    @Override
    public String deleteMapStaffGoods(Long id) {
        return mapStaffGoodsBusiness.delete(id);
    }

    @Override
    public String deleteListMapStaffGoods(List<MapStaffGoodsDTO> mapStaffGoodsListDTO) {
        return mapStaffGoodsBusiness.delete(mapStaffGoodsListDTO);
    }

    @Override
    public MapStaffGoodsDTO findMapStaffGoodsById(Long id) {
        if (id != null && id > 0) {
            return (MapStaffGoodsDTO) mapStaffGoodsBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<MapStaffGoodsDTO> getListMapStaffGoodsDTO(MapStaffGoodsDTO mapStaffGoodsDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (mapStaffGoodsDTO != null) {
            return mapStaffGoodsBusiness.search(mapStaffGoodsDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertMapStaffGoods(MapStaffGoodsDTO mapStaffGoodsDTO) {
        return mapStaffGoodsBusiness.createObject(mapStaffGoodsDTO);
    }

    @Override
    public String insertOrUpdateListMapStaffGoods(List<MapStaffGoodsDTO> mapStaffGoodsDTO) {
        return mapStaffGoodsBusiness.insertList(mapStaffGoodsDTO);
    }

    @Override
    public List<String> getSequenseMapStaffGoods(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return mapStaffGoodsBusiness.getListSequense(seqName, number);
    }

    @Override
    public ResultMapStaffGoodsDTO insertListMapStaffGoods(List<MapStaffGoodsDTO> lstMapStaffGoodsDTO) {
        ResultMapStaffGoodsDTO resultDTO = mapStaffGoodsBusiness1.insertListMapStaffGoods(lstMapStaffGoodsDTO, null);
        return resultDTO;
    }

    @Override
    public List<MapStaffGoodsDTO> getListMapStaffGoodsByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<MapStaffGoodsDTO> lstMapStaffGoods = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else {
                String value = StringUtils.formatLike(con.getValue());
                value.toLowerCase();
                con.setValue(value);
                con.setField(StringUtils.formatFunction("lower", con.getField()));
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));

        }
        lstMapStaffGoods = mapStaffGoodsBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstMapStaffGoods;
    }

    /**
     * NgocnND6 290316 Ham thuc hien vien chuyen quyen quan ly hang hoa giua cac
     * nhan vien voi nhau
     *
     * @param listMapStaffGoodsProcess
     * @param sdto
     * @return
     */
    @Override
    public String transferRoleGoodsManagement(List<MapStaffGoodsDTO> listMapStaffGoodsProcess, StaffDTO sdto) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        String result = "";
        List<MapStaffGoodsDTO> listInsertUp = Lists.newArrayList();
        try {
            String resultdel = deleteListMapStaffGoods(listMapStaffGoodsProcess);
            if (resultdel.equalsIgnoreCase("SUCCESS")) {
                for (MapStaffGoodsDTO msgdto : listMapStaffGoodsProcess) {
                    msgdto.setMapId(null);
                    msgdto.setStaffId(sdto.getStaffId());
                    msgdto.setStaffCode(sdto.getCode());
                    msgdto.setStaffName(sdto.getName());
                    msgdto.setStaffType(sdto.getStaffType());
                    msgdto.setStaffTypeName(sdto.getStaffTypeName());
                    listInsertUp.add(msgdto);
                }
                String resultdelete = insertOrUpdateListMapStaffGoods(listInsertUp);
                if (resultdelete.equalsIgnoreCase("SUCCESS")) {
                    result = "SUCCESS";
                } else {
                    if (tx != null) {
                        result = "FAIL";
                        tx.rollback();
                    }
                }
            } else {
                if (tx != null) {
                    result = "FAIL";
                    tx.rollback();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MapStaffGoodsServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
}
