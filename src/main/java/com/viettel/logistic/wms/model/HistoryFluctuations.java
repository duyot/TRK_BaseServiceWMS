/**
 * @(#)HistoryFluctuationsBO.java 8/22/2015 2:43 PM, Copyright 2011 Viettel
 * Telecom. All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.HistoryFluctuationsDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:43 PM
 */
@Entity
@Table(name = "HISTORY_FLUCTUATIONS")
public class HistoryFluctuations extends BaseFWModel {

    //Fields
    private Long historyFluctuationsId;
    private Long proposedReceiptId;
    private String reportCode;
    private Date implementDate;
    private String type;
    private String implementPerson;
    private String contactPerson;
    private String reason;
    private String attachFileList;
    private String content;
    private String status;
    private String note;
    private Date endExpectedDate;

    //Constructors
    public HistoryFluctuations() {
        setColId("historyFluctuationsId");
        setColName("historyFluctuationsId");
        setUniqueColumn(new String[]{"historyFluctuationsId"});
    }

    public HistoryFluctuations(Long historyFluctuationsId) {
        this.historyFluctuationsId = historyFluctuationsId;
    }

    public HistoryFluctuations(Long historyFluctuationsId, Long proposedReceiptId, String reportCode, Date implementDate, String type, String implementPerson, String contactPerson, String reason, String attachFileList, String content,String status,String note,Date endExpectedDate) {
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

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "HISTORY_FLUCTUATIONS_SEQ")
            }
    )
    @Column(name = "HISTORY_FLUCTUATIONS_ID", unique = true, nullable = false)
    public Long getHistoryFluctuationsId() {
        return this.historyFluctuationsId;
    }

    public void setHistoryFluctuationsId(final Long historyFluctuationsId) {
        this.historyFluctuationsId = historyFluctuationsId;
    }

    @Column(name = "PROPOSED_RECEIPT_ID", nullable = false)
    public Long getProposedReceiptId() {
        return this.proposedReceiptId;
    }

    public void setProposedReceiptId(final Long proposedReceiptId) {
        this.proposedReceiptId = proposedReceiptId;
    }

    @Column(name = "REPORT_CODE")
    public String getReportCode() {
        return this.reportCode;
    }

    public void setReportCode(final String reportCode) {
        this.reportCode = reportCode;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "IMPLEMENT_DATE", nullable = false)
    public Date getImplementDate() {
        return this.implementDate;
    }

    public void setImplementDate(final Date implementDate) {
        this.implementDate = implementDate;
    }

    @Column(name = "TYPE")
    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Column(name = "IMPLEMENT_PERSON")
    public String getImplementPerson() {
        return this.implementPerson;
    }

    public void setImplementPerson(final String implementPerson) {
        this.implementPerson = implementPerson;
    }

    @Column(name = "CONTACT_PERSON")
    public String getContactPerson() {
        return this.contactPerson;
    }

    public void setContactPerson(final String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Column(name = "REASON")
    public String getReason() {
        return this.reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    @Column(name = "ATTACH_FILE_LIST")
    public String getAttachFileList() {
        return this.attachFileList;
    }

    public void setAttachFileList(final String attachFileList) {
        this.attachFileList = attachFileList;
    }

    @Column(name = "CONTENT")
    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }
    @Column(name = "STATUS")
    public String getStatus() {
        return this.status;
    }
	public void setStatus(final String status) {
		this.status = status;
	}
    @Column(name = "NOTE"  )
    public String getNote() {
        return this.note;
    }
	public void setNote(final String note) {
		this.note = note;
	}
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "END_EXPECTED_DATE"  )
    public Date getEndExpectedDate() {
        return this.endExpectedDate;
    }

    public void setEndExpectedDate(final Date endExpectedDate) {
        this.endExpectedDate = endExpectedDate;
    }

    @Override
    public HistoryFluctuationsDTO toDTO() {
        HistoryFluctuationsDTO dto = new HistoryFluctuationsDTO(
                historyFluctuationsId == null ? null : historyFluctuationsId.toString(), proposedReceiptId == null ? null : proposedReceiptId.toString(), reportCode, implementDate == null ? null : DateTimeUtils.convertDateToString(implementDate, ParamUtils.ddMMyyyyHHmmss), type, implementPerson, contactPerson, reason, attachFileList, content,status,note,endExpectedDate == null ? null : DateTimeUtils.convertDateToString(endExpectedDate, ParamUtils.ddMMyyyyHHmmss)
        );
        return dto;
    }
}
