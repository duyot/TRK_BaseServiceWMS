/**
 * @(#)PickingListBO.java 08-May-15 4:02 PM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.PickingListDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 4:02 PM
 */
@Entity
@Table(name = "PICKING_LIST")
public class PickingList extends BaseFWModel {

    //Fields
    private Long id;
    private Long custId;
    private String custCode;
    private String custName;
    private Long stockId;
    private Date createdDate;
    private String code;
    private String status;
    private Long staffId;
    private String staffCode;
    private String staffName;
    private String orderActionIdList;
    private Date completeDatetime;
    private Long exploitationAreaId;
    private String exploitationAreaName;

    //Constructors
    public PickingList() {
        setColId("id");
        setColName("code");
        setUniqueColumn(new String[]{"code"});
    }

    public PickingList(Long id) {
        this.id = id;
    }

    public PickingList(Long id, Long custId, String custCode, String custName, Long stockId, Date createdDate, String code, String status, Long staffId, String staffCode, String staffName, String orderActionIdList, Date completeDatetime, Long exploitationAreaId, String exploitationAreaName) {
        this.id = id;
        this.custId = custId;
        this.custCode = custCode;
        this.custName = custName;
        this.stockId = stockId;
        this.createdDate = createdDate;
        this.code = code;
        this.status = status;
        this.staffId = staffId;
        this.staffCode = staffCode;
        this.staffName = staffName;
        this.orderActionIdList = orderActionIdList;
        this.completeDatetime = completeDatetime;
        this.exploitationAreaId = exploitationAreaId;
        this.exploitationAreaName = exploitationAreaName;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "PICKING_LIST_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "CUST_ID", nullable = false)
    public Long getCustId() {
        return this.custId;
    }

    public void setCustId(final Long custId) {
        this.custId = custId;
    }

    @Column(name = "CUST_CODE")
    public String getCustCode() {
        return this.custCode;
    }

    public void setCustCode(final String custCode) {
        this.custCode = custCode;
    }

    @Column(name = "CUST_NAME")
    public String getCustName() {
        return this.custName;
    }

    public void setCustName(final String custName) {
        this.custName = custName;
    }

    @Column(name = "STOCK_ID", nullable = false)
    public Long getStockId() {
        return this.stockId;
    }

    public void setStockId(final Long stockId) {
        this.stockId = stockId;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "CODE")
    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Column(name = "STAFF_ID")
    public Long getStaffId() {
        return this.staffId;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }

    @Column(name = "STAFF_CODE")
    public String getStaffCode() {
        return this.staffCode;
    }

    public void setStaffCode(final String staffCode) {
        this.staffCode = staffCode;
    }

    @Column(name = "STAFF_NAME")
    public String getStaffName() {
        return this.staffName;
    }

    public void setStaffName(final String staffName) {
        this.staffName = staffName;
    }

    @Column(name = "ORDER_ACTION_ID_LIST")
    public String getOrderActionIdList() {
        return this.orderActionIdList;
    }

    public void setOrderActionIdList(final String orderActionIdList) {
        this.orderActionIdList = orderActionIdList;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "COMPLETE_DATETIME")
    public Date getCompleteDatetime() {
        return this.completeDatetime;
    }

    public void setCompleteDatetime(final Date completeDatetime) {
        this.completeDatetime = completeDatetime;
    }

    @Column(name = "EXPLOITATION_AREA_ID")
    public Long getExploitationAreaId() {
        return this.exploitationAreaId;
    }

    public void setExploitationAreaId(final Long exploitationAreaId) {
        this.exploitationAreaId = exploitationAreaId;
    }

    @Column(name = "EXPLOITATION_AREA_NAME")
    public String getExploitationAreaName() {
        return this.exploitationAreaName;
    }

    public void setExploitationAreaName(final String exploitationAreaName) {
        this.exploitationAreaName = exploitationAreaName;
    }

    @Override
    public PickingListDTO toDTO() {
        PickingListDTO dto = new PickingListDTO(
                id == null ? null : id.toString(), custId == null ? null : custId.toString(), custCode, custName, stockId == null ? null : stockId.toString(), createdDate == null ? null : DateTimeUtils.convertDateToString(createdDate, ParamUtils.ddMMyyyy), code, status, staffId == null ? null : staffId.toString(), staffCode, staffName, orderActionIdList, completeDatetime == null ? null : DateTimeUtils.convertDateToString(completeDatetime, ParamUtils.ddMMyyyy), exploitationAreaId == null ? null : exploitationAreaId.toString(), exploitationAreaName
        );
        return dto;
    }
}
