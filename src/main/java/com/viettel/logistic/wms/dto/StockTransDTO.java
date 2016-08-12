/**
 * @(#)StockTransForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.logistic.wms.model.StockTrans;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 5/28/2015 10:29 AM
 */
@XmlRootElement(name = "StockTrans")
public class StockTransDTO extends BaseFWDTO<StockTrans> {

    //Fields
    private String stockTransId;
    private String deptId;
    private String custId;
    private String stockTransType;
    private String stockTransDate;
    private String ownerId;
    private String ownerType;
    private String ieOwnerId;
    private String ieOwnerType;
    private String reasonId;
    private String stockTransStatus;
    private String fromStockTransId;
    private String notes;
    private String transUserId;
    private String orderIdList;
    private String orderSource;
    private String orderDate;
    private String createDatetime;
    private String custCode;
    private String custName;
    private String isUpdateCell;
    private String stockTransCode;
    private String couponCode;
    private static long changedTime = 0;

    //duyot new fields
    private String partnerId;
    private String realStockTransDate;
    private String attachFileName;
    private String orderAction;
    //duyot: 06/01/2016
    private String orderCode;
    private String orderActionCode;
    private String transUserName;
    //duyot: 08/01/2016
    private String synTransCode;
    //NgocND6 160216
    private String addInfor;
	//duyot: 8/4/2016
    private String orderCommandCode;
    //QuyenDM: 04/04/2016 Them truong ma giao dich Voffice
    private String vofficeTransCode;
    // 
    @XStreamImplicit(itemFieldName = "lstStockTransDetailDTO")
    List<StockTransDetailDTO> lstStockTransDetailDTO = new ArrayList<>();

    //Constructor

    public StockTransDTO() {
        setDefaultSortField("stockTransDate");
    }

    public StockTransDTO(String stockTransId, String deptId, String custId, String stockTransType,
            String stockTransDate, String ownerId, String ownerType, String ieOwnerId, String ieOwnerType,
            String reasonId, String stockTransStatus, String fromStockTransId, String notes, String transUserId,
            String orderIdList, String orderDate, String createDatetime, String custCode, String custName,
            String isUpdateCell, String stockTransCode, String partnerId,
            String realStockTransDate, String attachFileName, String orderCode, String orderActionCode, String transUserName,
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
        this.realStockTransDate = realStockTransDate;
        this.attachFileName = attachFileName;
        //
        this.orderCode = orderCode;
        this.orderActionCode = orderActionCode;
        this.transUserName = transUserName;
        //
        this.synTransCode = synTransCode;
        this.addInfor = addInfor;
        this.vofficeTransCode = vofficeTransCode;
		//
        this.orderCommandCode = orderCommandCode;
    }
    //Getters and setters

    public void setStockTransId(String stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getStockTransId() {
        return stockTransId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
    }

    public void setStockTransType(String stockTransType) {
        this.stockTransType = stockTransType;
    }

    public String getStockTransType() {
        return stockTransType;
    }

    public void setStockTransDate(String stockTransDate) {
        this.stockTransDate = stockTransDate;
    }

    public String getStockTransDate() {
        return stockTransDate;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setIeOwnerId(String ieOwnerId) {
        this.ieOwnerId = ieOwnerId;
    }

    public String getIeOwnerId() {
        return ieOwnerId;
    }

    public void setIeOwnerType(String ieOwnerType) {
        this.ieOwnerType = ieOwnerType;
    }

    public String getIeOwnerType() {
        return ieOwnerType;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setStockTransStatus(String stockTransStatus) {
        this.stockTransStatus = stockTransStatus;
    }

    public String getStockTransStatus() {
        return stockTransStatus;
    }

    public void setFromStockTransId(String fromStockTransId) {
        this.fromStockTransId = fromStockTransId;
    }

    public String getFromStockTransId() {
        return fromStockTransId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setTransUserId(String transUserId) {
        this.transUserId = transUserId;
    }

    public String getTransUserId() {
        return transUserId;
    }

    public void setOrderIdList(String orderIdList) {
        this.orderIdList = orderIdList;
    }

    public String getOrderIdList() {
        return orderIdList;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

    public void setIsUpdateCell(String isUpdateCell) {
        this.isUpdateCell = isUpdateCell;
    }

    public String getIsUpdateCell() {
        return isUpdateCell;
    }

    public void setStockTransCode(String stockTransCode) {
        this.stockTransCode = stockTransCode;
    }

    public String getStockTransCode() {
        return stockTransCode;
    }

    public String getRealStockTransDate() {
        return realStockTransDate;
    }

    public void setRealStockTransDate(String realStockTransDate) {
        this.realStockTransDate = realStockTransDate;
    }

    public String getAttachFileName() {
        return attachFileName;
    }

    public void setAttachFileName(String attachFileName) {
        this.attachFileName = attachFileName;
    }

    @Override
    public StockTrans toModel() {
        try {
            StockTrans model = new StockTrans(
                    !StringUtils.validString(stockTransId) ? null
                    : Long.valueOf(stockTransId),
                    !StringUtils.validString(deptId) ? null
                    : Long.valueOf(deptId),
                    !StringUtils.validString(custId) ? null
                    : Long.valueOf(custId),
                    stockTransType,
                    !StringUtils.validString(stockTransDate) ? null
                    : DateTimeUtils.convertStringToDate(stockTransDate),
                    !StringUtils.validString(ownerId) ? null
                    : Long.valueOf(ownerId),
                    ownerType,
                    !StringUtils.validString(ieOwnerId) ? null
                    : Long.valueOf(ieOwnerId),
                    ieOwnerType,
                    !StringUtils.validString(reasonId) ? null
                    : Long.valueOf(reasonId),
                    stockTransStatus,
                    !StringUtils.validString(fromStockTransId) ? null
                    : Long.valueOf(fromStockTransId),
                    notes,
                    !StringUtils.validString(transUserId) ? null
                    : Long.valueOf(transUserId),
                    orderIdList,
                    !StringUtils.validString(orderDate) ? null
                    : DateTimeUtils.convertStringToDate(orderDate),
                    !StringUtils.validString(createDatetime) ? null
                    : DateTimeUtils.convertStringToDate(createDatetime),
                    custCode,
                    custName,
                    isUpdateCell,
                    stockTransCode,
                    !StringUtils.validString(partnerId) ? null : Long.valueOf(partnerId),
                    !StringUtils.validString(realStockTransDate) ? null
                    : DateTimeUtils.convertStringToDate(realStockTransDate),
                    attachFileName, orderCode, orderActionCode, transUserName, synTransCode, addInfor, vofficeTransCode,orderCommandCode
            );

            return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(stockTransId) ? null : Long.valueOf(stockTransId);
    }

    @Override
    public String catchName() {
        return getStockTransDate().toString();
    }

    @Override
    public long getChangedTime() {
        return StockTransDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        StockTransDTO.changedTime = changedTime;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public List<StockTransDetailDTO> getLstStockTransDetailDTO() {
        return lstStockTransDetailDTO;
    }

    public void setLstStockTransDetailDTO(List<StockTransDetailDTO> lstStockTransDetailDTO) {
        this.lstStockTransDetailDTO = lstStockTransDetailDTO;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderActionCode() {
        return orderActionCode;
    }

    public void setOrderActionCode(String orderActionCode) {
        this.orderActionCode = orderActionCode;
    }

    public String getTransUserName() {
        return transUserName;
    }

    public void setTransUserName(String transUserName) {
        this.transUserName = transUserName;
    }

    public String getSynTransCode() {
        return synTransCode;
    }

    public void setSynTransCode(String synTransCode) {
        this.synTransCode = synTransCode;
    }

    public String getAddInfor() {
        return addInfor;
    }

    public void setAddInfor(String addInfor) {
        this.addInfor = addInfor;
    }

    public String getVofficeTransCode() {
        return vofficeTransCode;
    }

    public void setVofficeTransCode(String vofficeTransCode) {
        this.vofficeTransCode = vofficeTransCode;
    }
	
	public String getOrderCommandCode() {
        return orderCommandCode;
    }

    public void setOrderCommandCode(String orderCommandCode) {
        this.orderCommandCode = orderCommandCode;
    }
}
