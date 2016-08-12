/**
 * @(#)PickingListForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.logistic.wms.model.PickingList;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 4:02 PM
 */
@XmlRootElement(name = "PickingList")
public class PickingListDTO extends BaseFWDTO<PickingList> {

    //Fields

    private String id;
    private String custId;
    private String custCode;
    private String custName;
    private String stockId;
    private String createdDate;
    private String code;
    private String status;
    private String staffId;
    private String staffCode;
    private String staffName;
    private String orderActionIdList;
    private String completeDatetime;
    private String exploitationAreaId;
    private String exploitationAreaName;
    private static long changedTime = 0;
    //
    private String tmpPickingListId;

    //Constructor

    public PickingListDTO() {
        setDefaultSortField("code");
    }

    public PickingListDTO(String id, String custId, String custCode, String custName, String stockId, String createdDate, String code, String status, String staffId, String staffCode, String staffName, String orderActionIdList, String completeDatetime, String exploitationAreaId, String exploitationAreaName) {
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
	//Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setOrderActionIdList(String orderActionIdList) {
        this.orderActionIdList = orderActionIdList;
    }

    public String getOrderActionIdList() {
        return orderActionIdList;
    }

    public void setCompleteDatetime(String completeDatetime) {
        this.completeDatetime = completeDatetime;
    }

    public String getCompleteDatetime() {
        return completeDatetime;
    }

    public void setExploitationAreaId(String exploitationAreaId) {
        this.exploitationAreaId = exploitationAreaId;
    }

    public String getExploitationAreaId() {
        return exploitationAreaId;
    }

    public void setExploitationAreaName(String exploitationAreaName) {
        this.exploitationAreaName = exploitationAreaName;
    }

    public String getExploitationAreaName() {
        return exploitationAreaName;
    }

    @Override
    public PickingList toModel() {
        try {
            PickingList model = new PickingList(
                    !StringUtils.validString(id) ? null
                            : Long.valueOf(id),
                    !StringUtils.validString(custId) ? null
                            : Long.valueOf(custId),
                    custCode,
                    custName,
                    !StringUtils.validString(stockId) ? null
                            : Long.valueOf(stockId),
                    !StringUtils.validString(createdDate) ? null
                            : DateTimeUtils.convertStringToDate(createdDate),
                    code,
                    status,
                    !StringUtils.validString(staffId) ? null
                            : Long.valueOf(staffId),
                    staffCode,
                    staffName,
                    orderActionIdList,
                    !StringUtils.validString(completeDatetime) ? null
                            : DateTimeUtils.convertStringToDate(completeDatetime),
                    !StringUtils.validString(exploitationAreaId) ? null
                            : Long.valueOf(exploitationAreaId),
                    exploitationAreaName);
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id) ? null : Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getCode().toString();
    }

    @Override
    public long getChangedTime() {
        return PickingListDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        PickingListDTO.changedTime = changedTime;
    }

    public String getTmpPickingListId() {
        return tmpPickingListId;
    }

    public void setTmpPickingListId(String tmpPickingListId) {
        this.tmpPickingListId = tmpPickingListId;
    }
}
