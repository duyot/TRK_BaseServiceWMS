/**
 * @(#)InventoryActionForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.logistic.wms.model.InventoryAction;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:16 PM
 */
@XmlRootElement(name = "InventoryAction")
public class InventoryActionDTO extends BaseFWDTO<InventoryAction> {

    //Fields

    private String inventoryActionId;
    private String stockId;
    private String code;
    private String content;
    private String customerId;
    private String finishOrderDate;
    private String implementerId;
    private String implementerName;
    private String note;
    private String displayInfo;
    private String createDate;
    private String createUserId;
    private String createUserName;
    private String status;
    private static long changedTime = 0;

    //Constructor

    public InventoryActionDTO() {
        setDefaultSortField("inventoryActionId");
    }

    public InventoryActionDTO(String inventoryActionId, String stockId, String code, String content, String customerId, String finishOrderDate, String implementerId, String implementerName, String note, String displayInfo, String createDate, String createUserId, String createUserName, String status) {
        this.inventoryActionId = inventoryActionId;
        this.stockId = stockId;
        this.code = code;
        this.content = content;
        this.customerId = customerId;
        this.finishOrderDate = finishOrderDate;
        this.implementerId = implementerId;
        this.implementerName = implementerName;
        this.note = note;
        this.displayInfo = displayInfo;
        this.createDate = createDate;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.status = status;
    }
	//Getters and setters

    public void setInventoryActionId(String inventoryActionId) {
        this.inventoryActionId = inventoryActionId;
    }

    public String getInventoryActionId() {
        return inventoryActionId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setFinishOrderDate(String finishOrderDate) {
        this.finishOrderDate = finishOrderDate;
    }

    public String getFinishOrderDate() {
        return finishOrderDate;
    }

    public void setImplementerId(String implementerId) {
        this.implementerId = implementerId;
    }

    public String getImplementerId() {
        return implementerId;
    }

    public void setImplementerName(String implementerName) {
        this.implementerName = implementerName;
    }

    public String getImplementerName() {
        return implementerName;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setDisplayInfo(String displayInfo) {
        this.displayInfo = displayInfo;
    }

    public String getDisplayInfo() {
        return displayInfo;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public InventoryAction toModel() {
        try {
            InventoryAction model = new InventoryAction(
                    !StringUtils.validString(inventoryActionId) ? null
                            : Long.valueOf(inventoryActionId),
                    !StringUtils.validString(stockId) ? null
                            : Long.valueOf(stockId),
                    code,
                    content,
                    !StringUtils.validString(customerId) ? null
                            : Long.valueOf(customerId),
                    !StringUtils.validString(finishOrderDate) ? null
                            : DateTimeUtils.convertStringToDate(finishOrderDate),
                    !StringUtils.validString(implementerId) ? null
                            : Long.valueOf(implementerId),
                    implementerName,
                    note,
                    displayInfo,
                    !StringUtils.validString(createDate) ? null
                            : DateTimeUtils.convertStringToDate(createDate),
                    !StringUtils.validString(createUserId) ? null
                            : Long.valueOf(createUserId),
                    createUserName,
                    status);
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(inventoryActionId) ? null : Long.valueOf(inventoryActionId);
    }

    @Override
    public String catchName() {
        return getInventoryActionId().toString();
    }

    @Override
    public long getChangedTime() {
        return InventoryActionDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        InventoryActionDTO.changedTime = changedTime;
    }
}
