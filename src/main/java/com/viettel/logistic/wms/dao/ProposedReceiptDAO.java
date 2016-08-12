/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.dao;

import com.viettel.logistic.wms.dto.ProposedReceiptDTO;
import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.logistic.wms.model.ProposedReceipt;
import com.viettel.vfw5.base.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:44 PM
 */
@Repository("proposedReceiptDAO")
public class ProposedReceiptDAO extends BaseFWDAOImpl<ProposedReceipt, Long> {

    public ProposedReceiptDAO() {
        this.model = new ProposedReceipt();
    }

    public ProposedReceiptDAO(Session session) {
        this.session = session;
    }

    //QuyenDM getListProposedReceiptByCondition 03/09/2015
    public List<ProposedReceipt> getListProposedReceiptByCondition(ProposedReceiptDTO proposedReceiptDTO) {
        List<ProposedReceipt> lstProposedReceiptDTOs = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            List params = new ArrayList();
            sql.append(" SELECT  a.proposed_receipt_id proposedReceiptId,");
            sql.append("         a.shipment_code shipmentCode,");
            sql.append("         a.proposed_receipt_code proposedReceiptCode,");
            sql.append("         a.stock_id stockId,");
            sql.append("         a.customer_id customerId,");
            sql.append("         a.number_invoice numberInvoice,");
            sql.append("         a.expected_date expectedDate,");
            sql.append("         a.create_date createDate,");
            sql.append("         a.implementer_name implementerName,");
            sql.append("         a.note note,");
            sql.append("         a.contact_person contactPerson,");
            sql.append("         a.status status,");
            sql.append("         a.receive_location receiveLocation,");
            sql.append("         a.attach_file_list attachFileList,");
            sql.append("         a.recent_history_id recentHistoryId");
            sql.append("    FROM   proposed_receipt a");
            sql.append("    WHERE  1=1");

            //Tim kiem theo mat hang
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getGoodsName())) {
                sql.append(" AND a.proposed_receipt_id IN (SELECT DISTINCT  c.request_id ");
                sql.append("                                    FROM   goods_handover c");
                sql.append("                                    WHERE   lower(c.goods_name) LIKE lower(?) ");
                sql.append("                                        AND c.type = '1')");
                params.add("%" + proposedReceiptDTO.getGoodsName() + "%");
                //
            }
            //Tim kiem theo nguoi tao
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getImplementerName())) {
                sql.append(" AND a.implementer_name = ?");
                params.add(proposedReceiptDTO.getImplementerName());
            }
            //Tim kiem theo kho
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getStockId())) {
                sql.append(" AND a.stock_id = ?");
                params.add(proposedReceiptDTO.getStockId());
            }
            //Tim kiem theo khach hang
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getCustomerId())) {
                sql.append(" AND a.customer_id = ?");
                params.add(proposedReceiptDTO.getCustomerId());
            }
            //Tim kiem theo trang thai
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getStatus())) {
                sql.append(" AND a.status = ?");
                params.add(proposedReceiptDTO.getStatus());
            }
            //Tim kiem theo ngay du kien nhap tu ngay
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getExpectedDate())) {
                sql.append(" AND a.expected_date >= to_date(?,'dd/mm/yyyy')");
                params.add(proposedReceiptDTO.getExpectedDate());
            }
            //Tim kiem theo ngay du kien nhap den ngay
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getExpectedDateToDate())) {
                sql.append(" AND a.expected_date <= to_date(?,'dd/mm/yyyy') + 1");
                params.add(proposedReceiptDTO.getExpectedDateToDate());
            }
            //Tim kiem theo ngay tao tu ngay
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getCreateDate())) {
                sql.append(" AND a.create_date >= to_date(?,'dd/mm/yyyy')");
                params.add(proposedReceiptDTO.getCreateDate());
            }
            //Tim kiem theo ngay tao den ngay
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getCreateDateToDate())) {
                sql.append(" AND a.create_date <= to_date(?,'dd/mm/yyyy') + 1");
                params.add(proposedReceiptDTO.getCreateDateToDate());
            }
            //Tim kiem theo ma phieu
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getProposedReceiptCode())) {
                sql.append(" AND lower(a.proposed_receipt_code) like lower(?)");
                params.add("%" + proposedReceiptDTO.getProposedReceiptCode() + "%");
            }
            //Tim kiem theo ma lo hang
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getShipmentCode())) {
                sql.append(" AND lower(a.shipment_code) like lower(?)");
                params.add("%" + proposedReceiptDTO.getShipmentCode() + "%");
            }
            //Tim kiem theo so invoice
            if (!DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getNumberInvoice())) {
                sql.append(" AND lower(a.number_invoice) like lower(?) ");
                params.add("%" + proposedReceiptDTO.getNumberInvoice() + "% \n");
            }
            //Sap xep theo ma phieu
            sql.append(" ORDER BY a.create_date DESC");
            //Tao cau lenh sql
            SQLQuery query = getSession().createSQLQuery(sql.toString());
            //Thuc hien chuyen du lieu lay ve thanh thanh doi tuong            
            query.setResultTransformer(Transformers.aliasToBean(ProposedReceipt.class));
            query.addScalar("proposedReceiptId", new LongType());
            query.addScalar("shipmentCode", new StringType());
            query.addScalar("proposedReceiptCode", new StringType());
            query.addScalar("stockId", new LongType());
            query.addScalar("customerId", new LongType());
            query.addScalar("numberInvoice", new StringType());
            query.addScalar("expectedDate", new DateType());
            query.addScalar("createDate", new DateType());
            query.addScalar("implementerName", new StringType());
            query.addScalar("note", new StringType());
            query.addScalar("contactPerson", new StringType());
            query.addScalar("status", new StringType());
            query.addScalar("receiveLocation", new StringType());
            query.addScalar("attachFileList", new StringType());
            query.addScalar("recentHistoryId", new LongType());
            //Truyen cac tham so truyen vao de thuc hien tim kiem
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
            //Day du lieu ra danh sach doi tuong
            lstProposedReceiptDTOs = query.list();

        } catch (Exception e) {
            lstProposedReceiptDTOs = new ArrayList<>();
            e.printStackTrace();
        }
        return lstProposedReceiptDTOs;
    }
}
