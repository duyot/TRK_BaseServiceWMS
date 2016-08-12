/**
 * @(#)ProposedReceiptBO.java 8/22/2015 2:44 PM, Copyright 2011 Viettel Telecom.
 * All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.ProposedReceiptDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:44 PM
 */
@Entity
@Table(name = "PROPOSED_RECEIPT")
public class ProposedReceipt extends BaseFWModel {

    //Fields
    private Long proposedReceiptId;
    private String shipmentCode;
    private String proposedReceiptCode;
    private Long stockId;
    private Long customerId;
    private String numberInvoice;
    private Date expectedDate;
    private Date createDate;
    private String implementerName;
    private String note;
    private String contactPerson;
    private String status;
    private String receiveLocation;
    private String attachFileList;
    private Long recentHistoryId;

    //Constructors
    public ProposedReceipt() {
        setColId("proposedReceiptId");
        setColName("proposedReceiptId");
        setUniqueColumn(new String[]{"proposedReceiptId"});
    }

    public ProposedReceipt(Long proposedReceiptId) {
        this.proposedReceiptId = proposedReceiptId;
    }

    public ProposedReceipt(Long proposedReceiptId, String shipmentCode, String proposedReceiptCode, Long stockId, Long customerId, String numberInvoice, Date expectedDate, Date createDate, String implementerName, String note, String contactPerson, String status, String receiveLocation, String attachFileList, Long recentHistoryId) {
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

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "PROPOSED_RECEIPT_SEQ")
            }
    )
    @Column(name = "PROPOSED_RECEIPT_ID", unique = true, nullable = false)
    public Long getProposedReceiptId() {
        return this.proposedReceiptId;
    }

    public void setProposedReceiptId(final Long proposedReceiptId) {
        this.proposedReceiptId = proposedReceiptId;
    }

    @Column(name = "SHIPMENT_CODE")
    public String getShipmentCode() {
        return this.shipmentCode;
    }

    public void setShipmentCode(final String shipmentCode) {
        this.shipmentCode = shipmentCode;
    }

    @Column(name = "PROPOSED_RECEIPT_CODE")
    public String getProposedReceiptCode() {
        return this.proposedReceiptCode;
    }

    public void setProposedReceiptCode(final String proposedReceiptCode) {
        this.proposedReceiptCode = proposedReceiptCode;
    }

    @Column(name = "STOCK_ID", nullable = false)
    public Long getStockId() {
        return this.stockId;
    }

    public void setStockId(final Long stockId) {
        this.stockId = stockId;
    }

    @Column(name = "CUSTOMER_ID", nullable = false)
    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(final Long customerId) {
        this.customerId = customerId;
    }

    @Column(name = "NUMBER_INVOICE")
    public String getNumberInvoice() {
        return this.numberInvoice;
    }

    public void setNumberInvoice(final String numberInvoice) {
        this.numberInvoice = numberInvoice;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "EXPECTED_DATE")
    public Date getExpectedDate() {
        return this.expectedDate;
    }

    public void setExpectedDate(final Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "IMPLEMENTER_NAME")
    public String getImplementerName() {
        return this.implementerName;
    }

    public void setImplementerName(final String implementerName) {
        this.implementerName = implementerName;
    }

    @Column(name = "NOTE")
    public String getNote() {
        return this.note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Column(name = "CONTACT_PERSON")
    public String getContactPerson() {
        return this.contactPerson;
    }

    public void setContactPerson(final String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Column(name = "RECEIVE_LOCATION")
    public String getReceiveLocation() {
        return this.receiveLocation;
    }

    public void setReceiveLocation(final String receiveLocation) {
        this.receiveLocation = receiveLocation;
    }

    @Column(name = "ATTACH_FILE_LIST")
    public String getAttachFileList() {
        return this.attachFileList;
    }

    public void setAttachFileList(final String attachFileList) {
        this.attachFileList = attachFileList;
    }

    @Column(name = "RECENT_HISTORY_ID")
    public Long getRecentHistoryId() {
        return this.recentHistoryId;
    }

    public void setRecentHistoryId(final Long recentHistoryId) {
        this.recentHistoryId = recentHistoryId;
    }

    @Override
    public ProposedReceiptDTO toDTO() {
        ProposedReceiptDTO dto = new ProposedReceiptDTO(
                proposedReceiptId == null ? null : proposedReceiptId.toString(), shipmentCode, proposedReceiptCode, stockId == null ? null : stockId.toString(), customerId == null ? null : customerId.toString(), numberInvoice, expectedDate == null ? null : DateTimeUtils.convertDateToString(expectedDate, ParamUtils.ddMMyyyy), createDate == null ? null : DateTimeUtils.convertDateToString(createDate, ParamUtils.ddMMyyyyHHmmss), implementerName, note, contactPerson, status, receiveLocation, attachFileList, recentHistoryId == null ? null : recentHistoryId.toString()
        );
        return dto;
    }
}
