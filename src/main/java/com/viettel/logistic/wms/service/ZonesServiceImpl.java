/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.ZonesDTO;
import com.viettel.logistic.wms.model.Zones;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.List;
import java.util.Locale;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 13-Apr-15 2:43 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.ZonesService")
public class ZonesServiceImpl implements ZonesService {

    @Autowired
    BaseFWServiceInterface zonesBusiness;

    @Override
    public String updateZones(ZonesDTO zonesDTO) {
        return zonesBusiness.update(zonesDTO);
    }

    @Override
    public String deleteZones(Long id) {
        return zonesBusiness.delete(id);
    }

    @Override
    public String deleteListZones(List<ZonesDTO> zonesListDTO) {
        return zonesBusiness.delete(zonesListDTO);
    }

    @Override
    public ZonesDTO findZonesById(Long id) {
        if (id != null && id > 0) {
            return (ZonesDTO) zonesBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<ZonesDTO> getListZonesDTO(ZonesDTO zonesDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (zonesDTO != null) {
            return zonesBusiness.search(zonesDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

//    @Override
//    public ResultDTO insertZones(ZonesDTO zonesDTO) {
//        return zonesBusiness.createObject(zonesDTO);
//    }
    //
    @Override
    public ResultDTO insertZones(ZonesDTO zonesDTO) {
        ResultDTO resultDTO = new ResultDTO();
        Class<?> c = zonesDTO.getClass();
        HashMap<String, String> hasmap = new HashMap<>();
        Zones zones = new Zones();
        String[] lstColumnUnique = zones.getUniqueColumn();
        for (int i = 0; i < lstColumnUnique.length; i++) {
            try {
                Method method = c.getMethod(DataUtil.getGetterOfColumn(lstColumnUnique[i]));
                try {
                    hasmap.put(lstColumnUnique[i], (String) method.invoke(zonesDTO));
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ZonesServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(ZonesServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(ZonesServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(ZonesServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(ZonesServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Boolean check = zonesBusiness.isDuplicate(null, hasmap);
            if (!check) {
                resultDTO = zonesBusiness.createObject(zonesDTO);
                resultDTO.setKey(ParamUtils.SUCCESS);
            } else {

                resultDTO.setKey("ERROR_EXISTED");

            }
        } catch (Exception e) {
            resultDTO.setKey("ERROR_SYSTEM");
        }
        return resultDTO;
    }

    @Override
    public String insertOrUpdateListZones(List<ZonesDTO> zonesDTO) {
        return zonesBusiness.insertList(zonesDTO);
    }

    @Override
    public List<String> getSequense(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return zonesBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<ZonesDTO> getListZonesByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<ZonesDTO> lstZones = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER_DOUBLE)) {
                con.setType(ParamUtils.NUMBER_DOUBLE);
            } else {
                String value = "";
                if (con.getOperator().equalsIgnoreCase(ParamUtils.NAME_LIKE)) {
                    value = StringUtils.formatLike(con.getValue());
                } else {
                    value = con.getValue();
                }
                con.setValue(value.toLowerCase());
                con.setField(StringUtils.formatFunction("lower", con.getField()));
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));
        }
        lstZones = zonesBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstZones;
    }
}
