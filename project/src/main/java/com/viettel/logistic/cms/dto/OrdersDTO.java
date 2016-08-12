/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.cms.dto;

import com.viettel.logistic.cms.model.Orders;
import com.viettel.vfw5.base.dto.BaseFWDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author makiet
 */
@XmlRootElement
public class OrdersDTO extends BaseFWDTO<Orders> implements Serializable {

    private Long orderId;
    private Date orderDatetime;
    private Long provinceId;
    private String custId;
    private String custCode;
    private String custName;
    private String orderType;
    private String orderCode;
    private String orderUser;
    private Long orderStockId;
    private Date importExpectDatetime;
    private Date storageDatetime;
    private String packingListNo;
    private String invoiceNo;
    private String purchaseOrderNo;
    private String orderStatus;
    private String attachFileList;
    private Date createDatetime;
    private String note;
    private String content;
    private static long changedTime = 0;

    public OrdersDTO(Long orderId, Date orderDatetime, Long provinceId, String custId, String custCode, String custName, String orderType, String orderCode, String orderUser, Long orderStockId, Date importExpectDatetime, Date storageDatetime, String packingListNo, String invoiceNo, String purchaseOrderNo, String orderStatus, String attachFileList, Date createDatetime, String note, String content) {
        this.orderId = orderId;
        this.orderDatetime = orderDatetime;
        this.provinceId = provinceId;
        this.custId = custId;
        this.custCode = custCode;
        this.custName = custName;
        this.orderType = orderType;
        this.orderCode = orderCode;
        this.orderUser = orderUser;
        this.orderStockId = orderStockId;
        this.importExpectDatetime = importExpectDatetime;
        this.storageDatetime = storageDatetime;
        this.packingListNo = packingListNo;
        this.invoiceNo = invoiceNo;
        this.purchaseOrderNo = purchaseOrderNo;
        this.orderStatus = orderStatus;
        this.attachFileList = attachFileList;
        this.createDatetime = createDatetime;
        this.note = note;
        this.content = content;
    }

    public OrdersDTO() {
        setDefaultSortField("orderCode");
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(Date orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }

    public Long getOrderStockId() {
        return orderStockId;
    }

    public void setOrderStockId(Long orderStockId) {
        this.orderStockId = orderStockId;
    }

    public Date getImportExpectDatetime() {
        return importExpectDatetime;
    }

    public void setImportExpectDatetime(Date importExpectDatetime) {
        this.importExpectDatetime = importExpectDatetime;
    }

    public Date getStorageDatetime() {
        return storageDatetime;
    }

    public void setStorageDatetime(Date storageDatetime) {
        this.storageDatetime = storageDatetime;
    }

    public String getPackingListNo() {
        return packingListNo;
    }

    public void setPackingListNo(String packingListNo) {
        this.packingListNo = packingListNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    } 

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAttachFileList() {
        return attachFileList;
    }

    public void setAttachFileList(String attachFileList) {
        this.attachFileList = attachFileList;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Orders toModel(Locale locale) {
        Orders model = new Orders(orderId, orderDatetime, provinceId, custId, custCode, custName, orderType, orderCode, orderUser, orderStockId, importExpectDatetime, storageDatetime, packingListNo, invoiceNo, purchaseOrderNo, orderStatus, attachFileList, createDatetime, note, content);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return getOrderId();
    }

    @Override
    public String catchName() {
        return getOrderCode();
    }
    
    @Override
    public long getChangedTime() {
        return OrdersDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        OrdersDTO.changedTime = changedTime;
    }

}
