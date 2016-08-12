/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.google.common.base.Splitter;
import com.viettel.logistic.wms.model.PickingList;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.PickingListGoods;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 4:07 PM
 */
@Repository("pickingListGoodsDAO")
public class PickingListGoodsDAO extends BaseFWDAOImpl<PickingListGoods, Long> {

    public PickingListGoodsDAO() {
        this.model = new PickingListGoods();
    }

    public PickingListGoodsDAO(Session session) {
        this.session = session;
    }

    public String getListCellCode(PickingList pickingList, PickingListGoods pickingListGoods) {
        String listCellCode = "";
        StringBuffer sql = new StringBuffer();
        List lstParams = new ArrayList();
        //Mat hang khong co serial
        if (!pickingListGoods.getGoodsIsSerial().equals(Constants.IS_SERIAL)) {
            sql.append("SELECT   cell_code || ': ' || amount ");
            sql.append("FROM   (SELECT   change_date, ");
            sql.append("     cell_code, ");
            sql.append("     amount, ");
            sql.append("     SUM (amount) OVER (PARTITION BY 1 ORDER BY change_date, cell_code) ");
            sql.append("         running_total ");
            sql.append("FROM   (  SELECT   1, ");
            sql.append("       change_date, ");
            sql.append("       cell_code, ");
            sql.append("       SUM (amount) amount ");
            sql.append("FROM stock_goods WHERE 1=1 ");
            sql.append("     AND cust_id = ? ");
            sql.append("     AND owner_id = ? ");
            sql.append("     AND owner_type = ? ");
            sql.append("     AND goods_id = ? ");
            sql.append("     AND goods_state = ? AND status = 1  ");
            //bo sung them so luong lon hon 0
            sql.append("     AND amount > 0  ");
            //
            sql.append("GROUP BY   change_date, cell_code ");
            sql.append("ORDER BY   change_date, cell_code)) ");
            sql.append("WHERE   running_total < ? + amount ");
            lstParams.add(pickingList.getCustId());
            lstParams.add(pickingList.getStockId());
            lstParams.add(Constants.STOCK_OWNER);
            lstParams.add(pickingListGoods.getGoodsId());
            lstParams.add(pickingListGoods.getGoodsState());
            lstParams.add(pickingListGoods.getAmount());
        } //Mat hang quan ly serial theo dai
        else if (pickingListGoods.getGoodsIsSerialStrip().equals(Constants.IS_SERIAL_STRIP)) {
            if (DataUtil.isStringNullOrEmpty(pickingListGoods.getFromSerial())) {
                //ney hang theo dai nhung thong tin serial == null
                sql.append("SELECT   cell_code || ': ' || amount ");
                sql.append("  FROM   (SELECT   import_date, ");
                sql.append("                   cell_code, ");
                sql.append("                   amount, ");
                sql.append("                   SUM (amount) OVER (PARTITION BY 1 ORDER BY import_date, cell_code) ");//ThienNg1 14/08/2015 bo sung cell_code
                sql.append("                       running_total ");
                sql.append("            FROM   (  SELECT   1, ");
                sql.append("                               import_date, ");
                sql.append("                               cell_code, ");
                sql.append("                               sum (quantity) amount ");
                sql.append("                        FROM   stock_goods_serial_strip ");
                sql.append("                       WHERE   cust_id = ? AND owner_id = ? AND owner_type = ? ");
                sql.append("                               AND goods_id = ? AND goods_state = ? AND status = 1 ");//ThienNg1 14/08/2015 bo sung dieu kien status =1
                sql.append("                    GROUP BY   import_date, cell_code ");
                sql.append("                    ORDER BY   import_date, cell_code)) ");
                sql.append(" WHERE   running_total < ? + amount");
                lstParams.add(pickingList.getCustId());
                lstParams.add(pickingList.getStockId());
                lstParams.add(Constants.STOCK_OWNER);
                lstParams.add(pickingListGoods.getGoodsId());
                lstParams.add(pickingListGoods.getGoodsState());
                lstParams.add(pickingListGoods.getAmount());
            } else {// hang theo dai co day du thong tin serial
                sql.append("SELECT   cell_code || ': ' || ?");
                sql.append("  FROM   (SELECT   cell_code");
                sql.append("            FROM   (  SELECT   1, cell_code");
                sql.append("                        FROM   stock_goods_serial_strip");
                sql.append("                       WHERE       cust_id = ?");
                sql.append("                               AND owner_id = ?");
                sql.append("                               AND owner_type = ?");
                sql.append("                               AND goods_id = ?");
                sql.append("                               AND goods_state = ?");
                sql.append("                               AND status = 1");
                sql.append("                               AND from_serial <=");
                sql.append("                                      TO_NUMBER (?)");
                sql.append("                               AND to_serial >= TO_NUMBER (?)");
                sql.append("                    GROUP BY   cell_code");
                sql.append("                    ORDER BY   cell_code))");

                lstParams.add(pickingListGoods.getAmount());
                lstParams.add(pickingList.getCustId());
                lstParams.add(pickingList.getStockId());
                lstParams.add(Constants.STOCK_OWNER);
                lstParams.add(pickingListGoods.getGoodsId());
                lstParams.add(pickingListGoods.getGoodsState());
                lstParams.add(pickingListGoods.getToSerial());
                lstParams.add(pickingListGoods.getFromSerial());
            }
        } else //Mat hang quan ly serial don le
        {
            List<String> lstSerial = new ArrayList();
            if (!DataUtil.isStringNullOrEmpty(pickingListGoods.getFromSerial())) {//Neu thong tin hang hoa co thong tin serial
                if (pickingListGoods.getFromSerial().equals(pickingListGoods.getToSerial())) {//Neu from_serial trung to_serial
                    sql.append("SELECT   cell_code || ': ' || amount ");
                    sql.append("  FROM   (SELECT   import_date, ");
                    sql.append("                   cell_code, ");
                    sql.append("                   amount, ");
                    sql.append("                   SUM (amount) OVER (PARTITION BY 1 ORDER BY import_date, cell_code) ");//ThienNg1 14/08/2015 bo sung cell_code
                    sql.append("                       running_total ");
                    sql.append("            FROM   (  SELECT   1, ");
                    sql.append("                               import_date, ");
                    sql.append("                               cell_code, ");
                    sql.append("                               COUNT ( * ) amount ");
                    sql.append("                        FROM   stock_goods_serial ");
                    sql.append("                       WHERE   cust_id = ? AND owner_id = ? AND owner_type = ? ");
                    sql.append("                               AND goods_id = ? AND goods_state = ? AND status = 1 ");//ThienNg1 14/08/2015 bo sung dieu kien status =1
                    //addby ThienNg1 - them dieu kien neu hang co thong tin serial
                    sql.append("                               AND serial in (?) ");
                    //
                    sql.append("                    GROUP BY   import_date, cell_code ");
                    sql.append("                    ORDER BY   import_date, cell_code)) ");
                    sql.append(" WHERE   running_total < ? + amount");
                    lstParams.add(pickingList.getCustId());
                    lstParams.add(pickingList.getStockId());
                    lstParams.add(Constants.STOCK_OWNER);
                    lstParams.add(pickingListGoods.getGoodsId());
                    lstParams.add(pickingListGoods.getGoodsState());
                    //them param serial
                    lstParams.add(pickingListGoods.getFromSerial());
                    //
                    lstParams.add(pickingListGoods.getAmount());
                } else {//neu from_serial khac to_serial
                    int sizeSearchIn = 995;
                    String serial;
                    String prefixSerial = "";
                    String suffixFromSerial = "";
                    String suffixToSerial = "";
                    String fromSerial = pickingListGoods.getFromSerial();
                    String toSerial = pickingListGoods.getToSerial();
                    //Serial chua ky tu
                    if (!StringUtils.isInteger(fromSerial) || !StringUtils.isInteger(toSerial)) {
                        serial = fromSerial;
                        lstSerial.add(serial);
                    } else { //Serial dang so 
                        //Kiem tra do dai serial kneu >19 thi cat do kieu Long chi co do dai toi da 19
                        int iLengthSuffixSerial = 0;
                        if (fromSerial.length() > Constants.SERIAL_LIMIT) {
                            prefixSerial = fromSerial.substring(0, fromSerial.length() - Constants.SERIAL_LIMIT);
                            suffixFromSerial = fromSerial.substring(fromSerial.length() - Constants.SERIAL_LIMIT, fromSerial.length());
                            suffixToSerial = toSerial.substring(toSerial.length() - Constants.SERIAL_LIMIT, toSerial.length());
                            iLengthSuffixSerial = suffixFromSerial.length();
                        } else {
                            suffixFromSerial = fromSerial;
                            suffixToSerial = toSerial;
                            iLengthSuffixSerial = fromSerial.length();
                        }
                        //
                        String tmpSuffixSerial;
                        for (Long lSerial = Long.parseLong(suffixFromSerial); lSerial <= Long.parseLong(suffixToSerial); lSerial++) {
                            tmpSuffixSerial = DataUtil.lPad(lSerial.toString(), "0", iLengthSuffixSerial);
                            serial = prefixSerial + tmpSuffixSerial;
                            lstSerial.add(serial);
                        }
                    }
                    int numberSheet = 0;
                    if (!DataUtil.isListNullOrEmpty(lstSerial)) {
                        if (lstSerial.size() % sizeSearchIn == 0) {
                            numberSheet = lstSerial.size() / sizeSearchIn;
                        } else {
                            numberSheet = lstSerial.size() / sizeSearchIn + 1;
                        }
                    }
                    sql.append("SELECT   cell_code || ': ' || quantity ");
                    sql.append("  FROM   (SELECT   ");
                    sql.append("                   cell_code,");
                    sql.append("                   quantity,");
                    sql.append("                   SUM (quantity)");
                    sql.append("                       OVER (PARTITION BY 1 ORDER BY  cell_code)");
                    sql.append("                       running_total");
                    sql.append("            FROM   (  SELECT   1,       ");
                    sql.append("                               cell_code,");
                    sql.append("                               SUM (amount) quantity");
                    sql.append("                        FROM   ( ");

                    for (int index = 0; index < numberSheet; index++) {
                        //moi vong truy van 1 luot
                        if (index == numberSheet - 1) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = sizeSearchIn * index; i < lstSerial.size(); i++) {
                                sb.append(",");
                                sb.append(lstSerial.get(i));
                            }
                            String serialSearch = sb.toString().replaceFirst(",", "");
                            if (numberSheet != 1) {
                                sql.append("  UNION ALL ");
                            }
                            sql.append("   SELECT   1,");
                            sql.append("   cell_code,");
                            sql.append("    COUNT ( * ) amount");
                            sql.append("   FROM   stock_goods_serial");
                            sql.append("      WHERE       cust_id = ?");
                            sql.append("            AND owner_id = ?");
                            sql.append("          AND owner_type = ?");
                            sql.append("          AND goods_id = ?");
                            sql.append("          AND goods_state = ?");
                            sql.append("           AND status = 1");
                            sql.append("            AND serial IN (" + serialSearch + ") ");
                            sql.append("   GROUP BY    cell_code)");
                            //
                            lstParams.add(pickingList.getCustId());
                            lstParams.add(pickingList.getStockId());
                            lstParams.add(Constants.STOCK_OWNER);
                            lstParams.add(pickingListGoods.getGoodsId());
                            lstParams.add(pickingListGoods.getGoodsState());
                        } else {
                            StringBuilder sb = new StringBuilder();
                            for (int i = sizeSearchIn * index; i < sizeSearchIn * (index + 1); i++) {
                                sb.append(",");
                                sb.append(lstSerial.get(i));
                            }
                            String serialSearch = sb.toString().replaceFirst(",", "");
                            if (index != 0) {
                                sql.append("  UNION ALL ");
                            }
                            sql.append("   SELECT   1,     ");
                            sql.append("    cell_code,");
                            sql.append("   COUNT ( * ) amount");
                            sql.append("   FROM   stock_goods_serial");
                            sql.append("   WHERE       cust_id = ?");
                            sql.append("   AND owner_id = ?");
                            sql.append("   AND owner_type = ?");
                            sql.append("   AND goods_id = ?");
                            sql.append("   AND goods_state = ?");
                            sql.append("   AND status = 1");
                            sql.append("            AND serial IN (" + serialSearch + ") ");
                            sql.append("   GROUP BY    cell_code ");
                            //
                            lstParams.add(pickingList.getCustId());
                            lstParams.add(pickingList.getStockId());
                            lstParams.add(Constants.STOCK_OWNER);
                            lstParams.add(pickingListGoods.getGoodsId());
                            lstParams.add(pickingListGoods.getGoodsState());
                        }
                    }
                    sql.append("           GROUP BY    cell_code))");
                    sql.append(" WHERE   running_total < ? + quantity");
                    lstParams.add(pickingListGoods.getAmount());
                }
            } else {//Neu thong tin hang hoa khong co thong tin serial
                //
                sql.append("SELECT   cell_code || ': ' || amount ");
                sql.append("  FROM   (SELECT   import_date, ");
                sql.append("                   cell_code, ");
                sql.append("                   amount, ");
                sql.append("                   SUM (amount) OVER (PARTITION BY 1 ORDER BY import_date, cell_code) ");//ThienNg1 14/08/2015 bo sung cell_code
                sql.append("                       running_total ");
                sql.append("            FROM   (  SELECT   1, ");
                sql.append("                               import_date, ");
                sql.append("                               cell_code, ");
                sql.append("                               COUNT ( * ) amount ");
                sql.append("                        FROM   stock_goods_serial ");
                sql.append("                       WHERE   cust_id = ? AND owner_id = ? AND owner_type = ? ");
                sql.append("                               AND goods_id = ? AND goods_state = ? AND status = 1 ");//ThienNg1 14/08/2015 bo sung dieu kien status =1
                sql.append("                    GROUP BY   import_date, cell_code ");
                sql.append("                    ORDER BY   import_date, cell_code)) ");
                sql.append(" WHERE   running_total < ? + amount");
                lstParams.add(pickingList.getCustId());
                lstParams.add(pickingList.getStockId());
                lstParams.add(Constants.STOCK_OWNER);

                lstParams.add(pickingListGoods.getGoodsId());
                lstParams.add(pickingListGoods.getGoodsState());
                lstParams.add(pickingListGoods.getAmount());
            }
        }
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        List<List> lstList = query.list();
        //List list;
        //
        if (lstList != null && lstList.size() > 0) {
            for (int j = 0; j < lstList.size(); j++) {
                if (listCellCode.equals("")) {
                    listCellCode = lstList.get(j) + "";
                } else {
                    listCellCode = listCellCode + "\r\n" + lstList.get(j);
//                    if (j == lstList.size() - 1) {
//                        listCellCode = listCellCode + lstList.get(j);
//                    } else {
//                        listCellCode = listCellCode + "\r\n" + lstList.get(j);
//                    }
                }
            }
        }
        return listCellCode;
    }
}
