/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.ZoneShelfDTO;
import com.viettel.logistic.wms.model.ZoneShelf;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import java.util.Locale;
import javax.jws.WebService;
import java.util.ArrayList;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.DataUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 06-May-15 9:44 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.ZoneShelfService")
public class ZoneShelfServiceImpl implements ZoneShelfService {

    @Autowired
    BaseFWServiceInterface zoneShelfBusiness;

    @Override
    public String updateZoneShelf(ZoneShelfDTO zoneShelfDTO) {
        return zoneShelfBusiness.update(zoneShelfDTO);
    }

    @Override
    public String deleteZoneShelf(Long id) {
        return zoneShelfBusiness.delete(id);
    }

    @Override
    public String deleteListZoneShelf(List<ZoneShelfDTO> zoneShelfListDTO) {
        return zoneShelfBusiness.delete(zoneShelfListDTO);
    }

    @Override
    public ZoneShelfDTO findZoneShelfById(Long id) {
        if (id != null && id > 0) {
            return (ZoneShelfDTO) zoneShelfBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<ZoneShelfDTO> getListZoneShelfDTO(ZoneShelfDTO zoneShelfDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (zoneShelfDTO != null) {
            return zoneShelfBusiness.search(zoneShelfDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

//    @Override
//    public ResultDTO insertZoneShelf(ZoneShelfDTO zoneShelfDTO) {
//        return zoneShelfBusiness.createObject(zoneShelfDTO);
//    }
    @Override
    public ResultDTO insertZoneShelf(ZoneShelfDTO zoneShelfDTO) {
        ResultDTO resultDTO = new ResultDTO();
        Class<?> c = zoneShelfDTO.getClass();
        HashMap<String, String> hasmap = new HashMap<>();
        ZoneShelf zoneShelf = new ZoneShelf();
        String[] lstColumnUnique = zoneShelf.getUniqueColumn();
        for (int i = 0; i < lstColumnUnique.length; i++) {
            try {
                Method method = c.getMethod(DataUtil.getGetterOfColumn(lstColumnUnique[i]));
                try {
                    hasmap.put(lstColumnUnique[i], (String) method.invoke(zoneShelfDTO));
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ZoneShelfServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(ZoneShelfServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(ZoneShelfServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(ZoneShelfServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(ZoneShelfServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Boolean check = zoneShelfBusiness.isDuplicate(null, hasmap);
            if (!check) {
                resultDTO = zoneShelfBusiness.createObject(zoneShelfDTO);
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
    public String insertOrUpdateListZoneShelf(List<ZoneShelfDTO> zoneShelfDTO) {
        return zoneShelfBusiness.insertList(zoneShelfDTO);
    }

    @Override
    public List<String> getSequenseZoneShelf(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return zoneShelfBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<ZoneShelfDTO> getListZoneShelfByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<ZoneShelfDTO> lstZoneShelf = new ArrayList<>();
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

        lstZoneShelf = zoneShelfBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstZoneShelf;
    }

}
