/**
 * @(#)StockTransBO.java 5/28/2015 10:29 AM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.StockTransDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 5/28/2015 10:29 AM
 */
@Entity
@Table(name = "STOCK_TRANS")
public class StockTrans extends BaseFWModel {

    //Fields
    private Long stockTransId;
    private Long deptId;
    private Long custId;
    private String stockTransType;
    private Date stockTransDate;
    private Long ownerId;
    private String ownerType;
    private Long ieOwnerId;
    private String ieOwnerType;
    private Long reasonId;
    private String stockTransStatus;
    private Long fromStockTransId;
    private String notes;
    private Long transUserId;
    private String orderIdList;
    private Date orderDate;
    private Date createDatetime;
    private String custCode;
    private String custName;
    private String isUpdateCell;
    private String stockTransCode;
    //new filed duyot
    private Long partnerId;
    //bo sung
    private Date realStockTransDate;
    private String attachFileName;
    //duyot: 6/01/2015: bo sung them 3 truong
    private String orderCode;
    private String orderActionCode;
    private String transUserName;
    //duyot: 08/01/2016
    private String synTransCode;
    //NgocND6 19/02/2016
    private String addInfor;
    //QuyenDM: 04/04/2016 Them truong ma giao dich Voffice
    private String vofficeTransCode;
	    //duyot: 8/4/2016
    private String orderCommandCode;

    //Constructors

    public StockTrans() {
        setColId("stockTransId");
        setColName("stockTransDate");
        setUniqueColumn(new String[]{"stockTransCode"});
    }

    public StockTrans(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public StockTrans(Long stockTransId, Long deptId, Long custId, String stockTransType,
            Date stockTransDate, Long ownerId, String ownerType, Long ieOwnerId,
            String ieOwnerType, Long reasonId, String stockTransStatus, Long fromStockTransId, String notes,
            Long transUserId, String orderIdList, Date orderDate, Date createDatetime, String custCode,
            String custName, String isUpdateCell, String stockTransCode, Long partnerId,
            Date realStockTransDate, String attachFileName, String orderCode, String orderActionCode, String transUserName,
            String synTransCode, String addInfor, String vofficeTransCode,String orderCommandCode
    ) {
        this.stockTransId = stockTransId;
        this.deptId = deptId;
        this.custId = custId;
        this.stockTransType = stockTransType;
        this.stockTransDate = stockTransDate;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.ieOwnerId = ieOwnerId;
        this.ieOwnerType = ieOwnerType;
        this.reasonId = reasonId;
        this.stockTransStatus = stockTransStatus;
        this.fromStockTransId = fromStockTransId;
        this.notes = notes;
        this.transUserId = transUserId;
        this.orderIdList = orderIdList;
        this.orderDate = orderDate;
        this.createDatetime = createDatetime;
        this.custCode = custCode;
        this.custName = custName;
        this.isUpdateCell = isUpdateCell;
        this.stockTransCode = stockTransCode;
        //
        this.partnerId = partnerId;
        //
        this.realStockTransDate = realStockTransDate;
        this.attachFileName = attachFileName;
        //
        this.orderCode = orderCode;
        this.orderActionCode = orderActionCode;
        this.transUserName = transUserName;
        this.synTransCode = synTransCode;
        this.addInfor = addInfor;
        this.vofficeTransCode = vofficeTransCode;
		//
        this.orderCommandCode = orderCommandCode;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "STOCK_TRANS_SEQ")
            }
    )
    @Column(name = "STOCK_TRANS_ID", unique = true, nullable = false)
    public Long getStockTransId() {
        return this.stockTransId;
    }

    public void setStockTransId(final Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    @Column(name = "DEPT_ID")
    public Long getDeptId() {
        return this.deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    @Column(name = "CUST_ID")
    public Long getCustId() {
        return this.custId;
    }

    public void setCustId(final Long custId) {
        this.custId = custId;
    }

    @Column(name = "STOCK_TRANS_TYPE")
    public String getStockTransType() {
        return this.stockTransType;
    }

    public void setStockTransType(final String stockTransType) {
        this.stockTransType = stockTransType;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "STOCK_TRANS_DATE")
    public Date getStockTransDate() {
        return this.stockTransDate;
    }

    public void setStockTransDate(final Date stockTransDate) {
        this.stockTransDate = stockTransDate;
    }

    @Column(name = "OWNER_ID")
    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name = "OWNER_TYPE")
    public String getOwnerType() {
        return this.ownerType;
    }

    public void setOwnerType(final String ownerType) {
        this.ownerType = ownerType;
    }

    @Column(name = "IE_OWNER_ID")
    public Long getIeOwnerId() {
        return this.ieOwnerId;
    }

    public void setIeOwnerId(final Long ieOwnerId) {
        this.ieOwnerId = ieOwnerId;
    }

    @Column(name = "IE_OWNER_TYPE")
    public String getIeOwnerType() {
        return this.ieOwnerType;
    }

    public void setIeOwnerType(final String ieOwnerType) {
        this.ieOwnerType = ieOwnerType;
    }

    @Column(name = "REASON_ID")
    public Long getReasonId() {
        return this.reasonId;
    }

    public void setReasonId(final Long reasonId) {
        this.reasonId = reasonId;
    }

    @Column(name = "STOCK_TRANS_STATUS", nullable = false)
    public String getStockTransStatus() {
        return this.stockTransStatus;
    }

    public void setStockTransStatus(final String stockTransStatus) {
        this.stockTransStatus = stockTransStatus;
    }

    @Column(name = "FROM_STOCK_TRANS_ID")
    public Long getFromStockTransId() {
        return this.fromStockTransId;
    }

    public void setFromStockTransId(final Long fromStockTransId) {
        this.fromStockTransId = fromStockTransId;
    }

    @Column(name = "NOTES")
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    @Column(name = "TRANS_USER_ID")
    public Long getTransUserId() {
        return this.transUserId;
    }

    public void setTransUserId(final Long transUserId) {
        this.transUserId = transUserId;
    }

    @Column(name = "ORDER_ID_LIST")
    public String getOrderIdList() {
        return this.orderIdList;
    }

    public void setOrderIdList(final String orderIdList) {
        this.orderIdList = orderIdList;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "ORDER_DATE")
    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATETIME")
    public Date getCreateDatetime() {
        return this.createDatetime;
    }
     public void setCreateDatetime(final Date createDatetime) {
        this.createDatetime = createDatetime;
    }
    //real stock_trans_date
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "REAL_STOCK_TRANS_DATE")
    public Date getRealStockTransDate() {
        return realStockTransDate;
    }

    public void setRealStockTransDate(Date realStockTransDate) {
        this.realStockTransDate = realStockTransDate;
    }

    @Column(name = "ATTACH_FILE_NAME")
    public String getAttachFileName() {
        return attachFileName;
    }

    public void setAttachFileName(String attachFileName) {
        this.attachFileName = attachFileName;
    }
    
    @Column(name = "CUST_CODE")
    public String getCustCode() {
        return this.custCode;
    }

    public void setCustCode(final String custCode) {
        this.custCode = custCode;
    }

    @Column(name = "CUST_NAME")
    public String getCustName() {
        return this.custName;
    }

    public void setCustName(final String custName) {
        this.custName = custName;
    }

    @Column(name = "IS_UPDATE_CELL")
    public String getIsUpdateCell() {
        return this.isUpdateCell;
    }

    public void setIsUpdateCell(final String isUpdateCell) {
        this.isUpdateCell = isUpdateCell;
    }

    @Column(name = "STOCK_TRANS_CODE")
    public String getStockTransCode() {
        return this.stockTransCode;
    }

    public void setStockTransCode(final String stockTransCode) {
        this.stockTransCode = stockTransCode;
    }

    //NEW FIELDS PARTNER
    @Column(name = "PARTNER_ID")
    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    //------------------------------------------------------------------
    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "ORDER_ACTION_CODE")
    public String getOrderActionCode() {
        return orderActionCode;
    }

    public void setOrderActionCode(String orderActionCode) {
        this.orderActionCode = orderActionCode;
    }

    @Column(name = "TRANS_USER_NAME")
    public String getTransUserName() {
        return transUserName;
    }

    public void setTransUserName(String transUserName) {
        this.transUserName = transUserName;
    }

    @Column(name = "SYN_TRANS_CODE")
    public String getSynTransCode() {
        return synTransCode;
    }

    public void setSynTransCode(String synTransCode) {
        this.synTransCode = synTransCode;
    }
    @Column(name = "ADD_INFOR")
    public String getAddInfor() {
        return addInfor;
    }

    public void setAddInfor(String addInfor) {
        this.addInfor = addInfor;
    }

    @Column(name = "VOFFICE_TRANSACTION_CODE")
    public String getVofficeTransCode() {
        return vofficeTransCode;
    }

    public void setVofficeTransCode(String vofficeTransCode) {
        this.vofficeTransCode = vofficeTransCode;
    }
	
	 @Column(name = "ORDER_COMMAND_CODE")
    public String getOrderCommandCode() {
        return orderCommandCode;
    }

    public void setOrderCommandCode(String orderCommandCode) {
        this.orderCommandCode = orderCommandCode;
    }

     //------------------------------------------------------------------
    @Override
    public StockTransDTO toDTO() {
        StockTransDTO dto = new StockTransDTO(
                stockTransId == null ? null : stockTransId.toString(), deptId == null ? null : deptId.toString(),
                custId == null ? null : custId.toString(), stockTransType,
                stockTransDate == null ? null : DateTimeUtils.convertDateToString(stockTransDate, ParamUtils.ddMMyyyyHHmmss),
                ownerId == null ? null : ownerId.toString(), ownerType, ieOwnerId == null ? null : ieOwnerId.toString(),
                ieOwnerType, reasonId == null ? null : reasonId.toString(), stockTransStatus,
                fromStockTransId == null ? null : fromStockTransId.toString(), notes,
                transUserId == null ? null : transUserId.toString(), orderIdList,
                orderDate == null ? null : DateTimeUtils.convertDateToString(orderDate, ParamUtils.ddMMyyyy),
                createDatetime == null ? null : DateTimeUtils.convertDateToString(createDatetime, ParamUtils.ddMMyyyy),
                custCode, custName, isUpdateCell, stockTransCode, partnerId == null ? null : partnerId.toString(),
                realStockTransDate == null ? null : DateTimeUtils.convertDateToString(realStockTransDate, ParamUtils.ddMMyyyy), attachFileName,
                orderCode, orderActionCode, transUserName, synTransCode, addInfor, vofficeTransCode,orderCommandCode
        );
        return dto;
    }
}
