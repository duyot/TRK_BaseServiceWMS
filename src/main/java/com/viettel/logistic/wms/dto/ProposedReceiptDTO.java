/**
 * @(#)ProposedReceiptForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.ProposedReceipt;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:44 PM
 */
@XmlRootElement(name = "ProposedReceipt")
public class ProposedReceiptDTO extends BaseFWDTO<ProposedReceipt> implements Serializable {

    //Fields
    private String proposedReceiptId;
    private String shipmentCode;
    private String proposedReceiptCode;
    private String stockId;
    private String customerId;
    private String numberInvoice;
    private String expectedDate;
    private String createDate;
    private String implementerName;
    private String note;
    private String contactPerson;
    private String status;
    private String receiveLocation;
    private String attachFileList;
    private String recentHistoryId;
    private static long changedTime = 0;

    private String goodsName;
    private String expectedDateToDate;
    private String createDateToDate;

    //Constructor

    public ProposedReceiptDTO() {
        setDefaultSortField("proposedReceiptId");
    }

    public ProposedReceiptDTO(String proposedReceiptId, String shipmentCode, String proposedReceiptCode, String stockId, String customerId, String numberInvoice, String expectedDate, String createDate, String implementerName, String note, String contactPerson, String status, String receiveLocation, String attachFileList, String recentHistoryId) {
        this.proposedReceiptId = proposedReceiptId;
        this.shipmentCode = shipmentCode;
        this.proposedReceiptCode = proposedReceiptCode;
        this.stockId = stockId;
        this.customerId = customerId;
        this.numberInvoice = numberInvoice;
        this.expectedDate = expectedDate;
        this.createDate = createDate;
        this.implementerName = implementerName;
        this.note = note;
        this.contactPerson = contactPerson;
        this.status = status;
        this.receiveLocation = receiveLocation;
        this.attachFileList = attachFileList;
        this.recentHistoryId = recentHistoryId;
    }

    //Getters and setters
    public void setProposedReceiptId(String proposedReceiptId) {
        this.proposedReceiptId = proposedReceiptId;
    }

    public String getProposedReceiptId() {
        return proposedReceiptId;
    }

    public void setShipmentCode(String shipmentCode) {
        this.shipmentCode = shipmentCode;
    }

    public String getShipmentCode() {
        return shipmentCode;
    }

    public void setProposedReceiptCode(String proposedReceiptCode) {
        this.proposedReceiptCode = proposedReceiptCode;
    }

    public String getProposedReceiptCode() {
        return proposedReceiptCode;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setNumberInvoice(String numberInvoice) {
        this.numberInvoice = numberInvoice;
    }

    public String getNumberInvoice() {
        return numberInvoice;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setImplementerName(String implementerName) {
        this.implementerName = implementerName;
    }

    public String getImplementerName() {
        return implementerName;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setReceiveLocation(String receiveLocation) {
        this.receiveLocation = receiveLocation;
    }

    public String getReceiveLocation() {
        return receiveLocation;
    }

    public void setAttachFileList(String attachFileList) {
        this.attachFileList = attachFileList;
    }

    public String getAttachFileList() {
        return attachFileList;
    }

    public String getRecentHistoryId() {
        return recentHistoryId;
    }

    public void setRecentHistoryId(String recentHistoryId) {
        this.recentHistoryId = recentHistoryId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getExpectedDateToDate() {
        return expectedDateToDate;
    }

    public void setExpectedDateToDate(String expectedDateToDate) {
        this.expectedDateToDate = expectedDateToDate;
    }

    public String getCreateDateToDate() {
        return createDateToDate;
    }

    public void setCreateDateToDate(String createDateToDate) {
        this.createDateToDate = createDateToDate;
    }

    @Override
    public ProposedReceipt toModel() {
        try {
            ProposedReceipt model = new ProposedReceipt(
                    !StringUtils.validString(proposedReceiptId) ? null
                            : Long.valueOf(proposedReceiptId),
                    shipmentCode,
                    proposedReceiptCode,
                    !StringUtils.validString(stockId) ? null
                            : Long.valueOf(stockId),
                    !StringUtils.validString(customerId) ? null
                            : Long.valueOf(customerId),
                    numberInvoice,
                    !StringUtils.validString(expectedDate) ? null
                            : DateTimeUtils.convertStringToDate(expectedDate),
                    !StringUtils.validString(createDate) ? null
                            : DateTimeUtils.convertStringToDate(createDate),
                    implementerName,
                    note,
                    contactPerson,
                    status,
                    receiveLocation,
                    attachFileList,
                    !StringUtils.validString(recentHistoryId) ? null
                            : Long.valueOf(recentHistoryId));
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(proposedReceiptId) ? null : Long.valueOf(proposedReceiptId);
    }

    @Override
    public String catchName() {
        return getProposedReceiptId().toString();
    }

    @Override
    public long getChangedTime() {
        return ProposedReceiptDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        ProposedReceiptDTO.changedTime = changedTime;
    }

}
