/**
 * @(#)HistoryFluctuationsForm.java ,
 * Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.HistoryFluctuations;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:43 PM
 */
@XmlRootElement(name = "HistoryFluctuations")
public class HistoryFluctuationsDTO extends BaseFWDTO<HistoryFluctuations> {

    //Fields
    private String historyFluctuationsId;
    private String proposedReceiptId;
    private String reportCode;
    private String implementDate;
    private String type;
    private String implementPerson;
    private String contactPerson;
    private String reason;
    private String attachFileList;
    private String content;
    private String status;
    private String note;
    private String endExpectedDate;
    private static long changedTime = 0;

    //Constructor
    public HistoryFluctuationsDTO() {
        setDefaultSortField("historyFluctuationsId");
    }

    public HistoryFluctuationsDTO(String historyFluctuationsId, String proposedReceiptId, String reportCode, String implementDate, String type, String implementPerson, String contactPerson, String reason, String attachFileList, String content, String status, String note, String endExpectedDate) {
        this.historyFluctuationsId = historyFluctuationsId;
        this.proposedReceiptId = proposedReceiptId;
        this.reportCode = reportCode;
        this.implementDate = implementDate;
        this.type = type;
        this.implementPerson = implementPerson;
        this.contactPerson = contactPerson;
        this.reason = reason;
        this.attachFileList = attachFileList;
        this.content = content;
        this.status = status;
        this.note = note;
        this.endExpectedDate = endExpectedDate;
    }
    //Getters and setters

    public void setHistoryFluctuationsId(String historyFluctuationsId) {
        this.historyFluctuationsId = historyFluctuationsId;
    }

    public String getHistoryFluctuationsId() {
        return historyFluctuationsId;
    }

    public void setProposedReceiptId(String proposedReceiptId) {
        this.proposedReceiptId = proposedReceiptId;
    }

    public String getProposedReceiptId() {
        return proposedReceiptId;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setImplementDate(String implementDate) {
        this.implementDate = implementDate;
    }

    public String getImplementDate() {
        return implementDate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setImplementPerson(String implementPerson) {
        this.implementPerson = implementPerson;
    }

    public String getImplementPerson() {
        return implementPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setAttachFileList(String attachFileList) {
        this.attachFileList = attachFileList;
    }

    public String getAttachFileList() {
        return attachFileList;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
    
	public void setStatus(String status) {
        this.status = status;
    }
	public String getStatus() {		
        return status;		
    }
    
	public void setNote(String note) {
        this.note = note;
    }
	public String getNote() {		
        return note;		
    }
    
	public void setEndExpectedDate(String endExpectedDate) {
        this.endExpectedDate = endExpectedDate;
    }
	public String getEndExpectedDate() {		
        return endExpectedDate;		
    }
    
	@Override
	public HistoryFluctuations toModel() {
        try {
		HistoryFluctuations model = new HistoryFluctuations(
            !StringUtils.validString(historyFluctuationsId)?null:
                Long.valueOf(historyFluctuationsId), 
            !StringUtils.validString(proposedReceiptId)?null:
                Long.valueOf(proposedReceiptId), 
            reportCode, 
            !StringUtils.validString(implementDate)?null:
                DateTimeUtils.convertStringToDate(implementDate), 
            type, 
            implementPerson, 
            contactPerson, 
            reason, 
            attachFileList, 
            content, 
            status, 
            note, 
            !StringUtils.validString(endExpectedDate)?null:
                DateTimeUtils.convertStringToDate(endExpectedDate));
		return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(historyFluctuationsId) ? null : Long.valueOf(historyFluctuationsId);
    }

    @Override
    public String catchName() {
        return getHistoryFluctuationsId().toString();
    }

    @Override
    public long getChangedTime() {
        return HistoryFluctuationsDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        HistoryFluctuationsDTO.changedTime = changedTime;
    }
}
