/**
 * @(#)KpiLogBO.java 12/31/2015 3:40 PM,
 * Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.logistic.wms.dto.KpiLogDTO;
import com.viettel.vfw5.base.model.BaseFWModel;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author duyot
 * @version 1.0
 * @since 12/31/2015 3:40 PM
 */
@Entity
@Table(name = "KPI_LOG")
public class KpiLog extends BaseFWModel {

    //Fields
    private Long kpiLogId;
    private Date createDatetime;//Thoi gian tao
    private String transactionCode;//Ma yeu cau
    private String functionCode;//Ma chuc nang
    private String description;//Mo ta
    private Date startTime;//Thoi gian bat dau
    private Date endTime;//Thoi gian ket thuc
    private Long duration;//Tong thoi gian
    private String stockTransStatus;//Ket qua
    private String reason;//Ly do that bai

    //Constructors
    public KpiLog() {
        setColId("kpiLogId");
        setColName("createDatetime");
        setUniqueColumn(new String[]{"kpiLogId"});
    }

    public KpiLog(Long kpiLogId) {
        this.kpiLogId = kpiLogId;
    }

    public KpiLog(Long kpiLogId, Date createDatetime, String transactionCode, String functionCode, String description, Date startTime, Date endTime, Long duration, String stockTransStatus, String reason) {
        this.kpiLogId = kpiLogId;
        this.createDatetime = createDatetime;
        this.transactionCode = transactionCode;
        this.functionCode = functionCode;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.stockTransStatus = stockTransStatus;
        this.reason = reason;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "KPI_LOG_SEQ")
            }
    )
    @Column(name = "KPI_LOG_ID", unique = true, nullable = false)
    public Long getKpiLogId() {
        return this.kpiLogId;
    }

    public void setKpiLogId(final Long kpiLogId) {
        this.kpiLogId = kpiLogId;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATETIME")
    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(final Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Column(name = "TRANSACTION_CODE")
    public String getTransactionCode() {
        return this.transactionCode;
    }

    public void setTransactionCode(final String transactionCode) {
        this.transactionCode = transactionCode;
    }

    @Column(name = "FUNCTION_CODE")
    public String getFunctionCode() {
        return this.functionCode;
    }

    public void setFunctionCode(final String functionCode) {
        this.functionCode = functionCode;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "START_TIME")
    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "END_TIME")
    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "DURATION")
    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(final Long duration) {
        this.duration = duration;
    }

    @Column(name = "STOCK_TRANS_STATUS", nullable = false)
    public String getStockTransStatus() {
        return this.stockTransStatus;
    }

    public void setStockTransStatus(final String stockTransStatus) {
        this.stockTransStatus = stockTransStatus;
    }

    @Column(name = "REASON")
    public String getReason() {
        return this.reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    @Override
    public KpiLogDTO toDTO() {
        KpiLogDTO dto = new KpiLogDTO(
                kpiLogId == null ? null : kpiLogId.toString(), createDatetime == null ? null : DateTimeUtils.convertDateToString(createDatetime, ParamUtils.ddMMyyyy), transactionCode, functionCode, description, startTime == null ? null : DateTimeUtils.convertDateToString(startTime, ParamUtils.ddMMyyyy), endTime == null ? null : DateTimeUtils.convertDateToString(endTime, ParamUtils.ddMMyyyy), duration == null ? null : duration.toString(), stockTransStatus, reason
        );
        return dto;
    }
}
