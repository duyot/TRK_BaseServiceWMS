/**
 * @(#)InventoryActionBO.java 6/9/2015 11:16 PM, Copyright 2011 Viettel Telecom.
 * All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.InventoryActionDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:16 PM
 */
@Entity
@Table(name = "INVENTORY_ACTION")
public class InventoryAction extends BaseFWModel {

    //Fields
    private Long inventoryActionId;
    private Long stockId;
    private String code;
    private String content;
    private Long customerId;
    private Date finishOrderDate;
    private Long implementerId;
    private String implementerName;
    private String note;
    private String displayInfo;
    private Date createDate;
    private Long createUserId;
    private String createUserName;
    private String status;

    //Constructors
    public InventoryAction() {
        setColId("inventoryActionId");
        setColName("inventoryActionId");
        setUniqueColumn(new String[]{"inventoryActionId"});
    }

    public InventoryAction(Long inventoryActionId) {
        this.inventoryActionId = inventoryActionId;
    }

    public InventoryAction(Long inventoryActionId, Long stockId, String code, String content, Long customerId, Date finishOrderDate, Long implementerId, String implementerName, String note, String displayInfo, Date createDate, Long createUserId, String createUserName, String status) {
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

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "INVENTORY_ACTION_SEQ")
            }
    )
    @Column(name = "INVENTORY_ACTION_ID", unique = true, nullable = false)
    public Long getInventoryActionId() {
        return this.inventoryActionId;
    }

    public void setInventoryActionId(final Long inventoryActionId) {
        this.inventoryActionId = inventoryActionId;
    }

    @Column(name = "STOCK_ID", nullable = false)
    public Long getStockId() {
        return this.stockId;
    }

    public void setStockId(final Long stockId) {
        this.stockId = stockId;
    }

    @Column(name = "CODE")
    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Column(name = "CONTENT")
    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    @Column(name = "CUSTOMER_ID")
    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(final Long customerId) {
        this.customerId = customerId;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "FINISH_ORDER_DATE")
    public Date getFinishOrderDate() {
        return this.finishOrderDate;
    }

    public void setFinishOrderDate(final Date finishOrderDate) {
        this.finishOrderDate = finishOrderDate;
    }

    @Column(name = "IMPLEMENTER_ID")
    public Long getImplementerId() {
        return this.implementerId;
    }

    public void setImplementerId(final Long implementerId) {
        this.implementerId = implementerId;
    }

    @Column(name = "IMPLEMENTER_NAME")
    public String getImplementerName() {
        return this.implementerName;
    }

    public void setImplementerName(final String implementerName) {
        this.implementerName = implementerName;
    }

    @Column(name = "NOTE")
    public String getNote() {
        return this.note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Column(name = "DISPLAY_INFO")
    public String getDisplayInfo() {
        return this.displayInfo;
    }

    public void setDisplayInfo(final String displayInfo) {
        this.displayInfo = displayInfo;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "CREATE_USER_ID")
    public Long getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(final Long createUserId) {
        this.createUserId = createUserId;
    }

    @Column(name = "CREATE_USER_NAME")
    public String getCreateUserName() {
        return this.createUserName;
    }

    public void setCreateUserName(final String createUserName) {
        this.createUserName = createUserName;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public InventoryActionDTO toDTO() {
        InventoryActionDTO dto = new InventoryActionDTO(
                inventoryActionId == null ? null : inventoryActionId.toString(), stockId == null ? null : stockId.toString(), code, content, customerId == null ? null : customerId.toString(), finishOrderDate == null ? null : DateTimeUtils.convertDateToString(finishOrderDate, ParamUtils.ddMMyyyyHHmmss), implementerId == null ? null : implementerId.toString(), implementerName, note, displayInfo, createDate == null ? null : DateTimeUtils.convertDateToString(createDate, ParamUtils.ddMMyyyyHHmmss), createUserId == null ? null : createUserId.toString(), createUserName, status
        );
        return dto;
    }
}
