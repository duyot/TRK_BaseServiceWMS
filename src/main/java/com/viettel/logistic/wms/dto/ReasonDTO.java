/**
 * @(#)ReasonForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.Reason;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:46 PM
 */
@XmlRootElement(name = "Reason")
public class ReasonDTO extends BaseFWDTO<Reason> {

    //Fields

    private String reasonId;
    private String code;
    private String name;
    private String reasonType;
    private String status;
    private static long changedTime = 0;

    //Constructor

    public ReasonDTO() {
        setDefaultSortField("reasonType");
    }

    public ReasonDTO(String reasonId, String code, String name, String reasonType, String status) {
        this.reasonId = reasonId;
        this.code = code;
        this.name = name;
        this.reasonType = reasonType;
        this.status = status;
    }
	//Getters and setters

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public Reason toModel() {
        Reason model = new Reason(
                !StringUtils.validString(reasonId) ? null
                        : Long.valueOf(reasonId),
                code,
                name,
                reasonType,
                status);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(reasonId) ? null : Long.valueOf(reasonId);
    }

    @Override
    public String catchName() {
        return getReasonType().toString();
    }

    @Override
    public long getChangedTime() {
        return ReasonDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        ReasonDTO.changedTime = changedTime;
    }
}
