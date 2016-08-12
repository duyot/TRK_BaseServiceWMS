/**
 * @(#)AppParamsBO.java 3/31/2015 2:45 PM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.cms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import com.viettel.logistic.cms.dto.AppParamsDTO;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
//import com.viettel.logistic.client.form.AppParamsForm;

/**
 * @author truongbx3
 * @version 1.0
 * @since 3/31/2015 2:45 PM
 */
@Entity
@Table(name = "APP_PARAMS")
public class AppParams extends BaseFWModel {

    //Fields
    private Long parId;
    private String parType;
    private String parCode;
    private String parName;
    private String parOrder;
    private String description;
    private String status;

    //Constructors
    public AppParams() {
        setColId("parId");
        setColName("parCode");
        setUniqueColumn(new String[]{"parCode"});
    }

    public AppParams(Long parId) {
        this.parId = parId;
    }

    public AppParams(Long parId, String parType, String parCode, String parName, String parOrder, String description, String status) {
        this.parId = parId;
        this.parType = parType;
        this.parCode = parCode;
        this.parName = parName;
        this.parOrder = parOrder;
        this.description = description;
        this.status = status;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "APP_PARAMS_SEQ")
            }
    )
    @Column(name = "PAR_ID", unique = true, nullable = false)
    public Long getParId() {
        return this.parId;
    }

    public void setParId(final Long parId) {
        this.parId = parId;
    }

    @Column(name = "PAR_TYPE", nullable = false)
    public String getParType() {
        return this.parType;
    }

    public void setParType(final String parType) {
        this.parType = parType;
    }

    @Column(name = "PAR_CODE", nullable = false)
    public String getParCode() {
        return this.parCode;
    }

    public void setParCode(final String parCode) {
        this.parCode = parCode;
    }

    @Column(name = "PAR_NAME")
    public String getParName() {
        return this.parName;
    }

    public void setParName(final String parName) {
        this.parName = parName;
    }

    @Column(name = "PAR_ORDER")
    public String getParOrder() {
        return this.parOrder;
    }

    public void setParOrder(final String parOrder) {
        this.parOrder = parOrder;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public AppParamsDTO toDTO() {
        AppParamsDTO dto = new AppParamsDTO(parId == null ? null : parId.toString(), parType, parCode, parName, parOrder, description, status);
        return dto;
    }
}
