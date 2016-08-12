/**
 * @(#)KpiLogForm.java ,
 * Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.logistic.wms.model.KpiLog;
import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author duyot
 * @version 1.0
 * @since 12/31/2015 3:40 PM
 */
@XmlRootElement(name = "KpiLog")
public class KpiLogDTO extends BaseFWDTO<KpiLog> {

    //Fields
    private String kpiLogId;
    private String createDatetime;//Ngay tao
    private String transactionCode;//Ma giao dich
    private String functionCode;//
    private String description;//Mo ta
    private String startTime;//Thoi gian bat dau
    private String endTime;//Thoi gian ket thuc
    private String duration;//Tong thoi gian
    private String stockTransStatus;//Trang thai giao dich
    private String reason;//Ly do
    private static long changedTime = 0;

    //Constructor
    public KpiLogDTO() {
        setDefaultSortField("createDatetime");
    }

    public KpiLogDTO(String kpiLogId, String createDatetime, String transactionCode, String functionCode, String description, String startTime, String endTime, String duration, String stockTransStatus, String reason) {
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

    public KpiLogDTO(String createDatetime, String transactionCode, String functionCode, String description, String startTime, String endTime, String duration, String stockTransStatus, String reason) {
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

    //Getters and setters
    public void setKpiLogId(String kpiLogId) {
        this.kpiLogId = kpiLogId;
    }

    public String getKpiLogId() {
        return kpiLogId;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setStockTransStatus(String stockTransStatus) {
        this.stockTransStatus = stockTransStatus;
    }

    public String getStockTransStatus() {
        return stockTransStatus;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public KpiLog toModel() {
        try {
            KpiLog model = new KpiLog(
                    !StringUtils.validString(kpiLogId) ? null
                    : Long.valueOf(kpiLogId),
                    !StringUtils.validString(createDatetime) ? null
                    : DateTimeUtils.convertStringToDate(createDatetime),
                    transactionCode,
                    functionCode,
                    description,
                    !StringUtils.validString(startTime) ? null
                    : DateTimeUtils.convertStringToDate(startTime),
                    !StringUtils.validString(endTime) ? null
                    : DateTimeUtils.convertStringToDate(endTime),
                    !StringUtils.validString(duration) ? null
                    : Long.valueOf(duration),
                    stockTransStatus,
                    reason);
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(kpiLogId) ? null : Long.valueOf(kpiLogId);
    }

    @Override
    public String catchName() {
        return getCreateDatetime().toString();
    }

    @Override
    public long getChangedTime() {
        return KpiLogDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        KpiLogDTO.changedTime = changedTime;
    }
}
