/**
 * @(#)AppParamsForm.java , Copyright 2015 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.cms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.cms.model.AppParams;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.Locale;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author truongbx3
 * @version 1.0
 * @since 3/31/2015 2:45 PM
 */
@XmlRootElement(name = "AppParams")
public class AppParamsDTO extends BaseFWDTO<AppParams> {

    //Fields

    private String parId;
    private String parType;
    private String parCode;
    private String parName;
    private String parOrder;
    private String description;
    private String status;
    private static long changedTime = 0;

    //Constructor

    public AppParamsDTO() {
        setDefaultSortField("parCode");
    }

    public AppParamsDTO(String parId, String parType, String parCode, String parName, String parOrder, String description, String status) {
        this.parId = parId;
        this.parType = parType;
        this.parCode = parCode;
        this.parName = parName;
        this.parOrder = parOrder;
        this.description = description;
        this.status = status;
    }
	//Getters and setters

    public void setParId(String parId) {
        this.parId = parId;
    }

    public String getParId() {
        return parId;
    }

    public void setParType(String parType) {
        this.parType = parType;
    }

    public String getParType() {
        return parType;
    }

    public void setParCode(String parCode) {
        this.parCode = parCode;
    }

    public String getParCode() {
        return parCode;
    }

    public void setParName(String parName) {
        this.parName = parName;
    }

    public String getParName() {
        return parName;
    }

    public void setParOrder(String parOrder) {
        this.parOrder = parOrder;
    }

    public String getParOrder() {
        return parOrder;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public AppParams toModel(Locale locale) {
        AppParams model = new AppParams(
                !StringUtils.validString(parId) ? null : Long.valueOf(parId),
                parType,
                parCode,
                parName,
                parOrder,
                description,
                status);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(parId) ? null : Long.valueOf(parId);
    }

    @Override
    public String catchName() {
        return getParCode().toString();
    }

    @Override
    public long getChangedTime() {
        return AppParamsDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        AppParamsDTO.changedTime = changedTime;
    }
}
