/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.google.common.base.Splitter;
import com.viettel.logistic.wms.dto.MapStaffGoodsDTO;
import com.viettel.logistic.wms.model.MapStaffGoods;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.vfw5.base.dto.ResultMapStaffGoodsDTO;
import com.viettel.vfw5.base.utils.ParamUtils;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * @author hongdq4
 * @version 1.0
 * @since 10/16/2015 10:12 AM
 */
@Repository("mapStaffGoodsDAO")
public class MapStaffGoodsDAO extends BaseFWDAOImpl<MapStaffGoods, Long> {

    public MapStaffGoodsDAO() {
        this.model = new MapStaffGoods();
    }

    public MapStaffGoodsDAO(Session session) {
        this.session = session;
    }

    public ResultMapStaffGoodsDTO insertListMapStaffGoods(List<MapStaffGoodsDTO> lstmap, Session session) {
        ResultMapStaffGoodsDTO resultDTO = new ResultMapStaffGoodsDTO();
        List<MapStaffGoodsDTO> lstMapStaffGoodsDTOError = new ArrayList();
        for (MapStaffGoodsDTO mapStaffGoodsDTO : lstmap) {
            try {
                insertMapStaffGoods(mapStaffGoodsDTO);
            } catch (Exception e) {
                e.printStackTrace();
                lstMapStaffGoodsDTOError.add(mapStaffGoodsDTO);
            }
        }
        resultDTO.setMessage(ParamUtils.SUCCESS);
        resultDTO.setLstMapStaffGoodsDTO(lstMapStaffGoodsDTOError);
        return resultDTO;
    }

    private int insertMapStaffGoods(MapStaffGoodsDTO mapStaffGoodsDTO) {
        StringBuilder sql = new StringBuilder();
        List params = new ArrayList();
        //

        sql.append("INSERT INTO map_staff_goods ");
        sql.append("(map_id,staff_id,staff_code,staff_name,staff_type,staff_type_name,");
        sql.append("goods_id, goods_code, goods_name, unit_type, unit_type_name, goods_type, goods_type_name, staff_email,staff_phone ) ");
        sql.append(" VALUES (MAP_STAFF_GOODS_SEQ.nextval,TO_NUMBER(?),?,?,?,?,");
        sql.append(" TO_NUMBER(?),?,?,?,?,?,?,?,? )");
        //
        params.add(mapStaffGoodsDTO.getStaffId());
        params.add(mapStaffGoodsDTO.getStaffCode());
        params.add(mapStaffGoodsDTO.getStaffName());
        params.add(mapStaffGoodsDTO.getStaffType());
        params.add(mapStaffGoodsDTO.getStaffTypeName());
        //
        params.add(mapStaffGoodsDTO.getGoodsId());
        params.add(mapStaffGoodsDTO.getGoodsCode());
        params.add(mapStaffGoodsDTO.getGoodsName());
        params.add(mapStaffGoodsDTO.getUnitType());
        params.add(mapStaffGoodsDTO.getUnitTypeName());
        params.add(mapStaffGoodsDTO.getGoodsType());
        params.add(mapStaffGoodsDTO.getGoodsTypeName());
        params.add(mapStaffGoodsDTO.getStaffEmail());
        params.add(mapStaffGoodsDTO.getStaffPhone());

        Query query = getSession().createSQLQuery(sql.toString());
        for (int idx = 0; idx < params.size(); idx++) {
            query.setParameter(idx, params.get(idx));
        }
        return query.executeUpdate();
    }

    public List<MapStaffGoodsDTO> getListStaffByGoods(String codeList, String custId) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();
        sql.append(" SELECT  DISTINCT m.staff_id staffId,");
        sql.append("         m.staff_code staffCode,");
        sql.append("         m.staff_name staffName,");
        sql.append("         m.staff_type staffType,");
        sql.append("         m.staff_email staffEmail");
        sql.append("  FROM   map_staff_goods m, goods g");
        sql.append("  WHERE m.goods_id = g.goods_id ");
        sql.append("  AND  g.status = 1");
        sql.append("  AND  m.staff_email IS NOT NULL");
        sql.append("  AND  g.cust_id = ?");
        lstParams.add(custId);
        sql.append("  AND m.goods_code IN (:idx)");

        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(MapStaffGoodsDTO.class));
        query.addScalar("staffId", new StringType());
        query.addScalar("staffCode", new StringType());
        query.addScalar("staffName", new StringType());
        query.addScalar("staffType", new StringType());
        query.addScalar("staffEmail", new StringType());

        for (int i = 0;
                i < lstParams.size();
                i++) {
            query.setParameter(i, lstParams.get(i));
        }
        List<String> lst = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(codeList);
        query.setParameterList("idx", lst);
        return query.list();
    }

}
