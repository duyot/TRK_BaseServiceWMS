/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.dto.InventoryResultDTO;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.InventoryResult;
import com.viettel.vfw5.base.utils.ParamUtils;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:26 PM
 */
@Repository("inventoryResultDAO")
public class InventoryResultDAO extends BaseFWDAOImpl<InventoryResult, Long> {

    public InventoryResultDAO() {
        this.model = new InventoryResult();
    }

    public InventoryResultDAO(Session session) {
        this.session = session;
    }

    public List<InventoryResultDTO> getLstCheckedResult(String inventoryActionId) {
        List<InventoryResultDTO> lstInvenResult = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        //create sql
        sql.append(" SELECT  ");
        sql.append(" NVL(a.inventory_result_id, b.inventory_result_id) inventoryResultId ");
        sql.append(" ,NVL(a.inventory_action_id, b.inventory_action_id) inventoryActionId ");
        sql.append(" ,NVL(a.goods_code, b.goods_code) goodsCode ");
        sql.append(" ,NVL(a.goods_name, b.goods_name) goodsName ");
        sql.append(" ,NVL(a.goods_id, b.goods_id) goodsId ");
        sql.append(" ,NVL(a.unit_id, b.unit_id) unitId  ");
        sql.append(" ,NVL(a.unit_name, b.unit_name) unitName ");
        sql.append(" ,NVL(a.cell_id, b.cell_id) cellId ");
        sql.append(" ,NVL(a.cell_code, b.cell_code) cellCode ");
        sql.append(" ,NVL(a.barcode, b.barcode) barcode ");
        sql.append(" ,NVL(a.from_serial, b.from_serial) fromSerial ");
        sql.append("  ,NVL(a.to_serial, b.to_serial) toSerial ");
        sql.append(" ,NVL(b.amount_inventory,0) amountInventory  ");
        sql.append(" ,NVL(a.amount,0) amount ");
        sql.append(" ,NVL(a.note, b.note) note  ");
        sql.append(" ,NVL(b.amount_inventory,0) - NVL(a.amount,0) as amountFalse  ");
        sql.append(" ,NVL(a.type, b.type) type  ");
        sql.append(" FROM (select * from inventory_result where inventory_action_id = ? and type = '1') a  ");
        sql.append(" full join (select * from inventory_result where inventory_action_id = ? and type = '2') b ");
        sql.append(" on NVL(a.goods_code, 'ABC') = NVL(b.goods_code , 'ABC') ");
        sql.append(" and   NVL(a.cell_code, 'ABC')= NVL(b.cell_code, 'ABC') ");
        sql.append(" and   NVL(a.barcode, 'ABC')= NVL(b.barcode , 'ABC') ");
        sql.append(" and   NVL(a.from_serial, 'ABC')= NVL(b.from_serial , 'ABC') ");
        sql.append(" and   NVL(a.to_serial, 'ABC')= NVL(b.to_serial, 'ABC')  ");

        //SET PARAMETTER
        SQLQuery query = getSession().createSQLQuery(sql.toString());

        query.setResultTransformer(Transformers.aliasToBean(InventoryResultDTO.class));
        query.addScalar("inventoryResultId", new StringType());
        query.addScalar("inventoryActionId", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        query.addScalar("goodsId", new StringType());
        query.addScalar("unitId", new StringType());
        query.addScalar("unitName", new StringType());
        query.addScalar("cellId", new StringType());
        query.addScalar("cellCode", new StringType());
        query.addScalar("barcode", new StringType());
        query.addScalar("fromSerial", new StringType());
        query.addScalar("toSerial", new StringType());
        query.addScalar("amountInventory", new StringType());
        query.addScalar("amount", new StringType());
        query.addScalar("note", new StringType());
        query.addScalar("amountFalse", new StringType());
        query.addScalar("type", new StringType());

        int inventoryId = 0;
        try {
            inventoryId = Integer.parseInt(inventoryActionId);
        } catch (Exception e) {
        }
        //SET PARAMETER
        query.setParameter(0, inventoryId);
        query.setParameter(1, inventoryId);
        //
        lstInvenResult = query.list();
        //List list;
//        if(lstInvenResult == null || lstInvenResult.size()==0)
//        {
//            return lstInvenResult;
//        }
        return lstInvenResult;
    }

    public List<InventoryResultDTO> getInventoryResultByDisplayField(String inventoryActionId, List<String> lstShowField) {
        List<InventoryResultDTO> lstInvenResult = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        String sqlStr = "";
        //
        boolean isShowAll = false;
        //create sql
        //init show file check
        boolean isShowLoaction = false;
        boolean isShowGoodsInfo = false;
        boolean isShowBincode = false;
        boolean isShowAmount = false;
        boolean isShowSerial = false;
        if (lstShowField != null && lstShowField.size() > 0) {
            for (String z : lstShowField) {
                switch (z) {
                    case ParamUtils.INVENTORY_EXPORT_SHOW_FIELD.LOCATION:
                        isShowLoaction = true;
                        break;
                    case ParamUtils.INVENTORY_EXPORT_SHOW_FIELD.GOODS_INFO:
                        isShowGoodsInfo = true;
                        break;
                    case ParamUtils.INVENTORY_EXPORT_SHOW_FIELD.BINCODE_BARCODE:
                        isShowBincode = true;
                        break;
                    case ParamUtils.INVENTORY_EXPORT_SHOW_FIELD.AMOUNT:
                        isShowAmount = true;
                        break;
                    case ParamUtils.INVENTORY_EXPORT_SHOW_FIELD.SERIAL:
                        isShowSerial = true;
                        break;
                }
            }
        } else {
            isShowAll = true;
        }

        if (isShowAll) {
            //CREATE SQL
            sql.append(" SELECT distinct from ");
            sql.append(" a.goods_code goodsCode,");
            sql.append(" a.goods_name goodsName,");
            sql.append(" a.cell_code cellCode,");
            sql.append(" a.barcode barcode,");
            sql.append(" a.from_serial fromSerial,");
            sql.append(" a.to_serial toSerial, ");
            sql.append(" sum (a.amount) as amount ");
            sql.append(" from  inventory_result a ");
            sql.append(" where inventory_action_id = ? and a.type = ? group by goods_code,goods_name,cell_code,barcode,from_serial,to_serial ");
            sql.append(" order by  goods_code ");

        } else {
            boolean isHaveCondition = false;
            //
            sql.append(" SELECT distinct ");
            if (isShowGoodsInfo) {
                if (isHaveCondition) {
                    sql.append(" ,a.goods_code goodsCode ");
                    sql.append(" ,a.goods_name goodsName ");
                } else {
                    sql.append(" a.goods_code goodsCode, ");
                    sql.append(" a.goods_name goodsName ");
                }
                isHaveCondition = true;
            }
            if (isShowLoaction) {
                if (isHaveCondition) {
                    sql.append(" ,a.cell_code cellCode ");
                } else {
                    sql.append(" a.cell_code cellCode ");
                }
                isHaveCondition = true;
            }
            if (isShowBincode) {
                if (isHaveCondition) {
                    sql.append(" ,a.barcode barcode ");
                } else {
                    sql.append(" a.barcode barcode ");
                }
                isHaveCondition = true;
            }
            if (isShowSerial) {
                if (isHaveCondition) {
                    sql.append(" ,a.from_serial fromSerial ");
                    sql.append(" ,a.to_serial toSerial  ");
                } else {
                    sql.append(" a.from_serial fromSerial, ");
                    sql.append(" a.to_serial toSerial ");
                }
                isHaveCondition = true;
            }

            if (isShowAmount) {
                if (isHaveCondition) {
                    sql.append(" ,sum (a.amount) as amount ");
                } else {
                    sql.append(" sum (a.amount) as amount ");
                }
                isHaveCondition = true;
            }

            sql.append(" from  inventory_result a ");
            sql.append(" where inventory_action_id = ? and a.type = ? group by ");

            if (isShowGoodsInfo) {
                sql.append(" goods_code,goods_name,");
            }
            if (isShowLoaction) {
                sql.append(" cell_code,");
            }
            if (isShowBincode) {
                sql.append(" barcode,");
            }
            if (isShowSerial) {
                sql.append(" from_serial,to_serial,");
            }
            if (isShowAmount) {
                sql.append(" amount,");
            }

            sqlStr = sql.substring(0, sql.length() - 1);
            if (isShowGoodsInfo) {
                sqlStr += " order by  goods_code ";
            }
        }

        //SET PARAMETTER
        SQLQuery query = getSession().createSQLQuery(sqlStr);

        query.setResultTransformer(Transformers.aliasToBean(InventoryResultDTO.class));
        if (isShowAll) {
            query.addScalar("goodsCode", new StringType());
            query.addScalar("goodsName", new StringType());
            query.addScalar("cellCode", new StringType());
            query.addScalar("barcode", new StringType());
            query.addScalar("fromSerial", new StringType());
            query.addScalar("toSerial", new StringType());
            query.addScalar("amount", new StringType());
        } else {
            if (isShowGoodsInfo) {
                query.addScalar("goodsCode", new StringType());
                query.addScalar("goodsName", new StringType());
            }
            if (isShowLoaction) {
                query.addScalar("cellCode", new StringType());
            }
            if (isShowBincode) {
                query.addScalar("barcode", new StringType());
            }
            if (isShowSerial) {
                query.addScalar("fromSerial", new StringType());
                query.addScalar("toSerial", new StringType());
            }
            if (isShowAmount) {
                query.addScalar("amount", new StringType());
            }
        }

        int inventoryId = 0;
        try {
            inventoryId = Integer.parseInt(inventoryActionId);
        } catch (Exception e) {
        }
        //SET PARAMETER
        query.setParameter(0, inventoryId);
        query.setParameter(1, "1");
        //
        lstInvenResult = query.list();

        return lstInvenResult;
    }

}
