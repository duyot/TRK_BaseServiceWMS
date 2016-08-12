/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.dto.SerialInventoryDTO;
import com.viettel.logistic.wms.dto.SerialInventoryReportDTO;
import com.viettel.logistic.wms.dto.SerialInventoryDTO;
import com.viettel.logistic.wms.dto.StockGoodsSerialInforDTO;
import com.viettel.logistic.wms.model.SerialInventory;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.vfw5.base.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.Constants;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 4/6/2016 9:40 AM
 */
@Repository("serialInventoryDAO")
public class SerialInventoryDAO extends BaseFWDAOImpl<SerialInventory, Long> {

    public SerialInventoryDAO() {
        this.model = new SerialInventory();
    }

    public SerialInventoryDAO(Session session) {
        this.session = session;
    }
    //thiet bi
    public List<SerialInventoryReportDTO> getListSerialInventoryReportType1(SerialInventoryDTO serialInventoryDTO){
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();
        sql.append(" select NVL(a.goods_id,b.goods_id) as goodsId ,NVL (a.goods_Code,b.goods_code) as goodsCode , ");
        sql.append("        NVL( a.goods_name,b.goods_name) as goodsName, ");
        sql.append("        NVL( a.goods_unit_type_name,b.goods_unit_type_name) as goodsUnitTypeName, ");
        sql.append("        NVL(quantity_state_1,0) as quantityState1,NVL(quantity_state_2,0) as quantityState2, ");
        sql.append("        (NVL(quantity_state_1,0) + NVL( quantity_state_2,0)) as total ");
        sql.append("        FROM ( ");
        sql.append("        select  goods_id, goods_code,goods_name,goods_unit_type_name,sum(quantity) as quantity_state_1 from serial_inventory  ");
        sql.append("        where cust_id = ? and stock_id  = ? and goods_state = 1  ");
        //------------------------------------------------------------------
        lstParams.add(serialInventoryDTO.getCustId());
        lstParams.add(serialInventoryDTO.getStockId());
        //dieu kien inventory code
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getInventoryCode())) {
            sql.append("   AND inventory_code = ? ");
            lstParams.add(serialInventoryDTO.getInventoryCode());
        }
        //dieu kien ma hang
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getGoodsCode())) {
            sql.append("   AND goods_code in ( ");
            sql.append(parameterToINSQL(serialInventoryDTO.getGoodsCode()));
            sql.append(" ) ");
        }
        //dieu dieu kien tu ngay
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getCreateDate())) {
            sql.append(" AND create_date > to_date(?,'dd/MM/yyyy hh24:mi:ss') ");
            lstParams.add(serialInventoryDTO.getCreateDate());
        }
        //dieu kien den ngay
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getStaffId())) {
            sql.append(" AND create_date < to_date(?,'dd/MM/yyyy hh24:mi:ss') ");
            lstParams.add(serialInventoryDTO.getStaffId());
        }
        //dieu kien nguoi tao
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getStaffName())) {
            sql.append("  AND staff_name like ('%");
            sql.append(serialInventoryDTO.getStaffName());
            sql.append("%') ");
        }
        //------------------------------------------------------------------
        sql.append("   group by goods_id,goods_code,goods_name,goods_unit_type_name,goods_state) a  ");
        sql.append("   FULL JOIN  (  ");
        sql.append("   select  goods_id,goods_code,goods_name,goods_unit_type_name,sum(quantity) as quantity_state_2 from serial_inventory  ");
        sql.append("   where cust_id = ? and stock_id  = ? and goods_state   = 2  ");
        //------------------------------------------------------------------
        lstParams.add(serialInventoryDTO.getCustId());
        lstParams.add(serialInventoryDTO.getStockId());
        //dieu kien inventory code
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getInventoryCode())) {
            sql.append("   AND inventory_code = ? ");
            lstParams.add(serialInventoryDTO.getInventoryCode());
        }
        //dieu kien ma hang
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getGoodsCode())) {
            sql.append("   AND goods_code in ( ");
            sql.append(parameterToINSQL(serialInventoryDTO.getGoodsCode()));
            sql.append(" ) ");
        }
        //dieu dieu kien tu ngay
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getCreateDate())) {
            sql.append(" AND create_date > to_date(?,'dd/MM/yyyy hh24:mi:ss') ");
            lstParams.add(serialInventoryDTO.getCreateDate());
        }
        //dieu kien den ngay
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getStaffId())) {
            sql.append(" AND create_date < to_date(?,'dd/MM/yyyy hh24:mi:ss') ");
            lstParams.add(serialInventoryDTO.getStaffId());
        }
        //dieu kien nguoi tao
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getStaffName())) {
            sql.append("  AND staff_name like ('%");
            sql.append(serialInventoryDTO.getStaffName());
            sql.append("%') ");
        }
        //------------------------------------------------------------------
        sql.append("   group by goods_id,goods_code,goods_name,goods_unit_type_name,goods_state ) b  ");
        sql.append("   ON a.goods_id = b.goods_id  ");
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(SerialInventoryReportDTO.class));

        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        query.addScalar("goodsUnitTypeName", new StringType());
        query.addScalar("quantityState1", new StringType());
        query.addScalar("quantityState2", new StringType());
        query.addScalar("total", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        List<SerialInventoryReportDTO> lstResult =  query.list();
        for(SerialInventoryReportDTO i: lstResult){
            i.setGoodsType("1");
        }
        return lstResult;
    }
    //vat tu
    public List<SerialInventoryReportDTO> getListSerialInventoryReportType2(SerialInventoryDTO serialInventoryDTO){
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();
        sql.append(" select NVL(a.goods_id,b.goods_id) as goodsId ,NVL (a.goods_Code,b.goods_code) as goodsCode , ");
        sql.append("        NVL( a.goods_name,b.goods_name) as goodsName, ");
        sql.append("        NVL( a.goods_unit_type_name,b.goods_unit_type_name) as goodsUnitTypeName, ");
        sql.append("        NVL(quantity_state_1,0) as quantityState1,NVL(quantity_state_2,0) as quantityState2, ");
        sql.append("        (NVL(quantity_state_1,0) + NVL( quantity_state_2,0)) as total ");
        sql.append("        FROM ( ");
        sql.append("        select  goods_id, goods_code,goods_name,goods_unit_type_name,sum(quantity) as quantity_state_1 from no_serial_inventory  ");
        sql.append("        where cust_id = ? and stock_id  = ? and goods_state = 1  ");
        //------------------------------------------------------------------
        lstParams.add(serialInventoryDTO.getCustId());
        lstParams.add(serialInventoryDTO.getStockId());
        //dieu kien inventory code
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getInventoryCode())) {
            sql.append("   AND inventory_code = ? ");
            lstParams.add(serialInventoryDTO.getInventoryCode());
        }
        //dieu kien ma hang
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getGoodsCode())) {
            sql.append("   AND goods_code in ( ");
            sql.append(parameterToINSQL(serialInventoryDTO.getGoodsCode()));
            sql.append(" ) ");
        }
        //dieu dieu kien tu ngay
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getCreateDate())) {
            sql.append(" AND create_date > to_date(?,'dd/MM/yyyy hh24:mi:ss') ");
            lstParams.add(serialInventoryDTO.getCreateDate());
        }
        //dieu kien den ngay
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getStaffId())) {
            sql.append(" AND create_date < to_date(?,'dd/MM/yyyy hh24:mi:ss') ");
            lstParams.add(serialInventoryDTO.getStaffId());
        }
         //dieu kien nguoi tao
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getStaffName())) {
            sql.append("  AND staff_name like ('%");
            sql.append(serialInventoryDTO.getStaffName());
            sql.append("%') ");
        }
        //------------------------------------------------------------------
        sql.append("   group by goods_id,goods_code,goods_name,goods_unit_type_name,goods_state) a  ");
        sql.append("   FULL JOIN  (  ");
        sql.append("   select  goods_id,goods_code,goods_name,goods_unit_type_name,sum(quantity) as quantity_state_2 from no_serial_inventory  ");
        sql.append("   where cust_id = ? and stock_id  = ? and goods_state   = 2  ");
        //------------------------------------------------------------------
        lstParams.add(serialInventoryDTO.getCustId());
        lstParams.add(serialInventoryDTO.getStockId());
        //dieu kien inventory code
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getInventoryCode())) {
            sql.append("   AND inventory_code = ? ");
            lstParams.add(serialInventoryDTO.getInventoryCode());
        }
        //dieu kien ma hang
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getGoodsCode())) {
            sql.append("   AND goods_code in ( ");
            sql.append(parameterToINSQL(serialInventoryDTO.getGoodsCode()));
            sql.append(" ) ");
        }
        //dieu dieu kien tu ngay
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getCreateDate())) {
            sql.append(" AND create_date > to_date(?,'dd/MM/yyyy hh24:mi:ss') ");
            lstParams.add(serialInventoryDTO.getCreateDate());
        }
        //dieu kien den ngay
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getStaffId())) {
            sql.append(" AND create_date < to_date(?,'dd/MM/yyyy hh24:mi:ss') ");
            lstParams.add(serialInventoryDTO.getStaffId());
        }
         //dieu kien nguoi tao
        if (!DataUtil.isStringNullOrEmpty(serialInventoryDTO.getStaffName())) {
            sql.append("  AND staff_name like ('%");
            sql.append(serialInventoryDTO.getStaffName());
            sql.append("%') ");
        }
        //------------------------------------------------------------------
        sql.append("   group by goods_id,goods_code,goods_name,goods_unit_type_name,goods_state ) b  ");
        sql.append("   ON a.goods_id = b.goods_id  ");
        //
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(SerialInventoryReportDTO.class));

        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        query.addScalar("goodsUnitTypeName", new StringType());
        query.addScalar("quantityState1", new StringType());
        query.addScalar("quantityState2", new StringType());
        query.addScalar("total", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        
        List<SerialInventoryReportDTO> lstResult =  query.list();
        for(SerialInventoryReportDTO i: lstResult){
            i.setGoodsType("2");
        }
        return lstResult;
    
    }
    //
     public List<SerialInventoryReportDTO> getListSerialInventoryReport(SerialInventoryDTO serialInventoryDTO, int type){
        List<SerialInventoryReportDTO> lstResult = new ArrayList<>();
        if(type == 1){//hang thiet bi
            lstResult.addAll(getListSerialInventoryReportType1(serialInventoryDTO));
        }else if(type == 2){//hang vat tu
            //lay vat tu
            lstResult.addAll(getListSerialInventoryReportType2(serialInventoryDTO));
        }else{//lay ca 2
            //lay thiet bi
            lstResult.addAll(getListSerialInventoryReportType1(serialInventoryDTO));
            //lay vat tu
            lstResult.addAll(getListSerialInventoryReportType2(serialInventoryDTO));
        }
        
        return lstResult;
     }
     
     public String parameterToINSQL(String code){
         if(code.contains(",")){
            String [] splitedString = code.split(",");
            StringBuilder strBuilder = new StringBuilder();
            for(String i: splitedString){
                String s = "'"+i.trim()+"'";
                strBuilder.append(",").append(s);
            }
            
            return strBuilder.toString().replaceFirst(",", "");
         }else{
           return "'"+ code + "'";
         }
     }
    //

    //Add by Thienng1: 07/09/2015: Nhap serial theo lo
    public ResultDTO insertListSerialInventoryBatch(List<SerialInventoryDTO> lstSerialInventoryDTO, Connection connection) {
        ResultDTO resultDTO = new ResultDTO();
        //connection.
        //PREPARE STATEMENTS
        PreparedStatement prstmtInsertSerialInventory;
        //SQL
        StringBuilder sqlSerialInventory = new StringBuilder();

        int numberNeedToCommit = 0;
        int numberOfSuccess = 0;
        int numberOfFail = 0;
        //
        List paramsSerialInventory;
        //mac dinh la thanh cong
        resultDTO.setMessage(ParamUtils.SUCCESS);
        try {
            //1.KHOI TAO SESSION
            //2.1 TAO STATEMENTS SERIAL_INVENTORY
            sqlSerialInventory.append(" INSERT INTO serial_inventory(id, cust_id, cust_code, cust_name, stock_id,");
            sqlSerialInventory.append("       goods_id, goods_code, goods_name, goods_state,");
            sqlSerialInventory.append("       quantity, from_serial, to_serial, cell_code, barcode,");
            sqlSerialInventory.append("       create_date, note, status, staff_id, staff_code,staff_name, inventory_code, goods_unit_type_name) ");
            sqlSerialInventory.append(" VALUES (SERIAL_INVENTORY_SEQ.nextval, TO_NUMBER(?), ?, ?, TO_NUMBER(?), TO_NUMBER(?), ");
            sqlSerialInventory.append("  ?, ?, ?, TO_NUMBER(?), ?, ?, ?, ?, TO_DATE(?,'dd/MM/yyyy hh24:mi:ss'), ?, TO_NUMBER(?), ");
            sqlSerialInventory.append("  TO_NUMBER(?), ?, ?, ?, ? ) ");
            sqlSerialInventory.append(" LOG ERRORS REJECT LIMIT UNLIMITED ");
            //3. TAO PREPARE STATEMENT
            prstmtInsertSerialInventory = connection.prepareStatement(sqlSerialInventory.toString());
            //Chi tiet serial  
            for (SerialInventoryDTO serialInventoryDTO : lstSerialInventoryDTO) {
                numberNeedToCommit++;
                //SET PARAMS FOR SERIAL_INVENTORY
                paramsSerialInventory = setParamsSerialInventory(serialInventoryDTO);
                //SET PARAMS AND ADD TO BATCH
                for (int idx = 0; idx < paramsSerialInventory.size(); idx++) {
                    prstmtInsertSerialInventory.setString(idx + 1, DataUtil.nvl(paramsSerialInventory.get(idx), "").toString());
                }
                prstmtInsertSerialInventory.addBatch();

                if (numberNeedToCommit >= Constants.COMMIT_NUM) {
                    try {
                        prstmtInsertSerialInventory.executeBatch();
                        numberOfSuccess = numberOfSuccess + numberNeedToCommit;
                    } catch (Exception ex) {
                        numberOfFail = numberOfFail + numberNeedToCommit;
                    }
                    numberNeedToCommit = 0;
                }
            }//END FOR
            if (numberNeedToCommit > 0) {
                try {
                    prstmtInsertSerialInventory.executeBatch();
                    numberOfSuccess += numberNeedToCommit;
                } catch (Exception ex) {
                    //connection.rollback();
                    numberOfFail += numberNeedToCommit;
                }
            }
            prstmtInsertSerialInventory.close();
        } catch (SQLException | NumberFormatException e) {
            Logger.getLogger(StockGoodsSerialDAO.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setMessage(ParamUtils.FAIL);
        }

        //lay so luong hang hoa insert vao ban err$_
        List<StockGoodsSerialInforDTO> lstError = getListErrorSerialInventory(lstSerialInventoryDTO.get(0).getInventoryCode());
        int amountError = 0;
        if (lstError != null) {
            amountError = lstError.size();
        }
        int amountTotal = lstSerialInventoryDTO.size();
        numberOfSuccess = amountTotal - amountError;//So luong ban ghi insert thanh cong
        numberOfFail = amountError;//so luong hang loi da ton tai serial cua khach hang trong ban kiem ke
        //
        resultDTO.setQuantityFail(numberOfFail);
        resultDTO.setQuantitySucc(numberOfSuccess);
        resultDTO.setLstStockGoodsSerialInforDTO(lstError);
        // tra ve list serial loi
        return resultDTO;
    }

    public List setParamsSerialInventory(SerialInventoryDTO inventoryDTO) {
        List paramsStockTrans = new ArrayList();
        paramsStockTrans.add(inventoryDTO.getCustId());
        paramsStockTrans.add(inventoryDTO.getCustCode());
        paramsStockTrans.add(inventoryDTO.getCustName());
        paramsStockTrans.add(inventoryDTO.getStockId());
        paramsStockTrans.add(inventoryDTO.getGoodsId());
        paramsStockTrans.add(inventoryDTO.getGoodsCode());
        paramsStockTrans.add(inventoryDTO.getGoodsName());
        paramsStockTrans.add(inventoryDTO.getGoodsState());
        paramsStockTrans.add(inventoryDTO.getQuantity());
        paramsStockTrans.add(inventoryDTO.getFromSerial());
        paramsStockTrans.add(inventoryDTO.getToSerial());
        paramsStockTrans.add(inventoryDTO.getCellCode());
        paramsStockTrans.add(inventoryDTO.getBarcode());
        paramsStockTrans.add(inventoryDTO.getCreateDate());
        paramsStockTrans.add(inventoryDTO.getNote());
        paramsStockTrans.add(inventoryDTO.getStatus());
        paramsStockTrans.add(inventoryDTO.getStaffId());
        paramsStockTrans.add(inventoryDTO.getStaffCode());
        paramsStockTrans.add(inventoryDTO.getStaffName());
        paramsStockTrans.add(inventoryDTO.getInventoryCode());
        paramsStockTrans.add(inventoryDTO.getGoodsUnitTypeName());
        return paramsStockTrans;
    }

    public List getListErrorSerialInventory(String inventoryCode) {
        StringBuilder sql = new StringBuilder();
        List lstParams = new ArrayList();

        sql.append("SELECT  ");
        sql.append("         a.ora_err_mesg$ addInfor,");
        sql.append("         a.cust_id custId,");
        sql.append("         a.stock_id ownerId,");
        sql.append("         a.goods_id goodsId,");
        sql.append("         a.goods_state goodsState,");
        sql.append("         a.status status,");
        sql.append("         a.from_serial fromSerial,");
        sql.append("         a.to_serial toSerial,");
        sql.append("         a.quantity quantity,");
        sql.append("         a.barcode barcode,");
        sql.append("         a.note notes,");
        sql.append("         a.cell_code cellCode,");
        sql.append("         a.goods_code goodsCode,");
        sql.append("         a.goods_name goodsName ");
        sql.append("  FROM   err$_serial_inventory a");
        sql.append("  ");
        if (!DataUtil.isStringNullOrEmpty(inventoryCode)) {
            sql.append("  WHERE   a.inventory_code = ? ");
            lstParams.add(inventoryCode);
        }
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(StockGoodsSerialInforDTO.class));
        query.addScalar("custId", new StringType());
        query.addScalar("ownerId", new StringType());
        query.addScalar("goodsId", new StringType());
        query.addScalar("goodsState", new StringType());
        query.addScalar("status", new StringType());
        query.addScalar("fromSerial", new StringType());
        query.addScalar("toSerial", new StringType());
        query.addScalar("quantity", new StringType());
        query.addScalar("notes", new StringType());
        query.addScalar("addInfor", new StringType());
        query.addScalar("cellCode", new StringType());
        query.addScalar("barcode", new StringType());
        query.addScalar("goodsCode", new StringType());
        query.addScalar("goodsName", new StringType());
        //
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        return query.list();
    }
}
