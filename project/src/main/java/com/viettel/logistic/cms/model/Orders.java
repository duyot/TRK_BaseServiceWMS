/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.logistic.cms.model;

import com.viettel.logistic.cms.dto.OrdersDTO;
import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.model.BaseFWModel;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author makiet
 */
@Entity
@Table(name = "ORDERS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(Orders.class)
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findByOrderId", query = "SELECT o FROM Orders o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "Orders.findByOrderDatetime", query = "SELECT o FROM Orders o WHERE o.orderDatetime = :orderDatetime"),
    @NamedQuery(name = "Orders.findByProvinceId", query = "SELECT o FROM Orders o WHERE o.provinceId = :provinceId"),
    @NamedQuery(name = "Orders.findByCustId", query = "SELECT o FROM Orders o WHERE o.custId = :custId"),
    @NamedQuery(name = "Orders.findByCustCode", query = "SELECT o FROM Orders o WHERE o.custCode = :custCode"),
    @NamedQuery(name = "Orders.findByCustName", query = "SELECT o FROM Orders o WHERE o.custName = :custName"),
    @NamedQuery(name = "Orders.findByOrderType", query = "SELECT o FROM Orders o WHERE o.orderType = :orderType"),
    @NamedQuery(name = "Orders.findByOrderCode", query = "SELECT o FROM Orders o WHERE o.orderCode = :orderCode"),
    @NamedQuery(name = "Orders.findByOrderUser", query = "SELECT o FROM Orders o WHERE o.orderUser = :orderUser"),
    @NamedQuery(name = "Orders.findByOrderStockId", query = "SELECT o FROM Orders o WHERE o.orderStockId = :orderStockId"),
    @NamedQuery(name = "Orders.findByImportExpectDatetime", query = "SELECT o FROM Orders o WHERE o.importExpectDatetime = :importExpectDatetime"),
    @NamedQuery(name = "Orders.findByStorageDatetime", query = "SELECT o FROM Orders o WHERE o.storageDatetime = :storageDatetime"),
    @NamedQuery(name = "Orders.findByPackingListNo", query = "SELECT o FROM Orders o WHERE o.packingListNo = :packingListNo"),
    @NamedQuery(name = "Orders.findByInvoiceNo", query = "SELECT o FROM Orders o WHERE o.invoiceNo = :invoiceNo"),
    @NamedQuery(name = "Orders.findByPurchaseOrderNo", query = "SELECT o FROM Orders o WHERE o.purchaseOrderNo = :purchaseOrderNo"),
    @NamedQuery(name = "Orders.findByOrderStatus", query = "SELECT o FROM Orders o WHERE o.orderStatus = :orderStatus"),
    @NamedQuery(name = "Orders.findByAttachFileList", query = "SELECT o FROM Orders o WHERE o.attachFileList = :attachFileList"),
    @NamedQuery(name = "Orders.findByCreateDatetime", query = "SELECT o FROM Orders o WHERE o.createDatetime = :createDatetime"),
    @NamedQuery(name = "Orders.findByNote", query = "SELECT o FROM Orders o WHERE o.note = :note"),
    @NamedQuery(name = "Orders.findByContent", query = "SELECT o FROM Orders o WHERE o.content = :content")})
public class Orders extends BaseFWModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ORDER_ID")
    private Long orderId;
    @Column(name = "ORDER_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDatetime;
    @Column(name = "PROVINCE_ID")
    private Long provinceId;
    @Column(name = "CUST_ID")
    private String custId;
    @Column(name = "CUST_CODE")
    private String custCode;
    @Column(name = "CUST_NAME")
    private String custName;
    @Basic(optional = false)
    @Column(name = "ORDER_TYPE")
    private String orderType;
    @Column(name = "ORDER_CODE")
    private String orderCode;
    @Basic(optional = false)
    @Column(name = "ORDER_USER")
    private String orderUser;
    @Column(name = "ORDER_STOCK_ID")
    private Long orderStockId;
    @Column(name = "IMPORT_EXPECT_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date importExpectDatetime;
    @Column(name = "STORAGE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date storageDatetime;
    @Column(name = "PACKING_LIST_NO")
    private String packingListNo;
    @Column(name = "INVOICE_NO")
    private String invoiceNo;
    @Column(name = "PURCHASE_ORDER_NO")
    private String purchaseOrderNo;
    @Column(name = "ORDER_STATUS")
    private String orderStatus;
    @Column(name = "ATTACH_FILE_LIST")
    private String attachFileList;
    @Column(name = "CREATE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "CONTENT")
    private String content;

    //Constructors
    public Orders() {
        setColId("orderId");
        setColName("orderCode");
        setUniqueColumn(new String[]{"orderCode"});
    }

    public Orders(Long orderId) {
        this.orderId = orderId;
    }

    public Orders(Long orderId, String orderType, String orderUser) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.orderUser = orderUser;
    }

    public Orders(Long orderId, Date orderDatetime, Long provinceId, String custId, String custCode, String custName, String orderType, String orderCode, String orderUser, Long orderStockId, Date importExpectDatetime, Date storageDatetime, String packingListNo, String invoiceNo, String purchaseOrderNo, String orderStatus, String attachFileList, Date createDatetime, String note, String content) {
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
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.viettel.logistic.cms.model.Orders[ orderId=" + orderId + " ]";
    }

    @Override
    public OrdersDTO toDTO() {
        OrdersDTO dto = new OrdersDTO(orderId, orderDatetime, provinceId, custId, custCode, custName, orderType, orderCode, orderUser, orderStockId, importExpectDatetime, storageDatetime, packingListNo, invoiceNo, purchaseOrderNo, orderStatus, attachFileList, createDatetime, note, content);
        return dto;
    }

}
