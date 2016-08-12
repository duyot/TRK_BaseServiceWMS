/**
 * @(#)ReasonBO.java 08-Apr-15 2:46 PM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.ReasonDTO;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:46 PM
 */
@Entity
@Table(name = "REASON")
public class Reason extends BaseFWModel {

    //Fields
    private Long reasonId;
    private String code;
    private String name;
    private String reasonType;
    private String status;

    //Constructors
    public Reason() {
        setColId("reasonId");
        setColName("reasonType");
        setUniqueColumn(new String[]{"code"});
    }

    public Reason(Long reasonId) {
        this.reasonId = reasonId;
    }

    public Reason(Long reasonId, String code, String name, String reasonType, String status) {
        this.reasonId = reasonId;
        this.code = code;
        this.name = name;
        this.reasonType = reasonType;
        this.status = status;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "REASON_SEQ")
            }
    )
    @Column(name = "REASON_ID", unique = true, nullable = false)
    public Long getReasonId() {
        return this.reasonId;
    }

    public void setReasonId(final Long reasonId) {
        this.reasonId = reasonId;
    }

    @Column(name = "CODE", unique = true, nullable = false)
    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(name = "REASON_TYPE", nullable = false)
    public String getReasonType() {
        return this.reasonType;
    }

    public void setReasonType(final String reasonType) {
        this.reasonType = reasonType;
    }

    @Column(name = "STATUS", nullable = false)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public ReasonDTO toDTO() {
        ReasonDTO dto = new ReasonDTO(
                reasonId == null ? null : reasonId.toString(), code, name, reasonType, status
        );
        return dto;
    }
}
