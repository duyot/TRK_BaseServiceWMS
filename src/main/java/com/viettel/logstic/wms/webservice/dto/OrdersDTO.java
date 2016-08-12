/**
 * @(#)OrdersForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logstic.wms.webservice.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 28-Apr-15 10:52 AM
 */
@XmlRootElement(name = "OrdersDTO")
public class OrdersDTO {

    //Fields
    private String orderId;
    private String orderCode;
    private String orderDatetime;
    private String provinceId;
    private String custId;
    private String custCode;
    private String custName;
    private String partnerId;
    private String partnerCode;
    private String partnerName;
    private String orderType;
    private String orderUser;
    private String orderStockId;
    private String ieExpectDate;
    private String storageDate;
    private String packingListNo;
    private String invoiceNo;
    private String purchaseOrderNo;
    private String orderStatus;
    private String attachFileList;
    private String note;
    private String content;
    private String staffReceivedId;
    private String contactName;
    private String destroyReasonId;
    private String destroyDatetime;
    private String destroyUser;
    private String quantityOrderAction;
    private String quantityOrderActionComplete;
//    private static long changedTime = 0;
    private String sourceOrder;
    private String inputType;
    private String scanTicket;
    private String orderAction;
    private String stockCode;
    private String outputType;
    private String areaCode;
    private String typeReceive;
    private String telName;
    private String expectDate;
    private String deptId;
    private String staffId;
    private String stockerCode;
    //duyot: 01/06/2016: gui thong tin nguoi nhap - ly do 
    private String transUserName;
    private String orderCommandCode;
    //
    //    receive_code from bccs
    private String receiveCode;
//    receive_name from bccs
    private String receiveName;
//bccs
    @XStreamImplicit(itemFieldName = "lstGoods")
    List<OrderGoodsDetailDTO> lstOrderGoodsDetailDTO = new ArrayList<>();

//    ktts
    @XStreamImplicit(itemFieldName = "listGood")
    List<OrderGoodsDetailDTO> listGood = new ArrayList<>();

    // danh sach dia diem
    @XStreamImplicit(itemFieldName = "lstOrderGoodsLocationDTO")
    List<OrderGoodsLocationDTO> lstOrderGoodsLocationDTO = new ArrayList<>();
    //Constructor

    public OrdersDTO() {
    }

    
    public OrdersDTO(String orderId, String orderCode, String orderDatetime, String provinceId, String custId, String custCode, String custName, String partnerId, String partnerCode, String partnerName, String orderType, String orderUser, String orderStockId, String ieExpectDate, String storageDate, String packingListNo, String invoiceNo, String purchaseOrderNo, String orderStatus, String attachFileList, String note, String content, String staffReceivedId, String contactName, String destroyReasonId, String destroyDatetime, String destroyUser, String quantityOrderAction, String quantityOrderActionComplete, String sourceOrder, String inputType, String outputType) {
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.orderDatetime = orderDatetime;
        this.provinceId = provinceId;
        this.custId = custId;
        this.custCode = custCode;
        this.custName = custName;
        this.partnerId = partnerId;
        this.partnerCode = partnerCode;
        this.partnerName = partnerName;
        this.orderType = orderType;
        this.orderUser = orderUser;
        this.orderStockId = orderStockId;
        this.ieExpectDate = ieExpectDate;
        this.storageDate = storageDate;
        this.packingListNo = packingListNo;
        this.invoiceNo = invoiceNo;
        this.purchaseOrderNo = purchaseOrderNo;
        this.orderStatus = orderStatus;
        this.attachFileList = attachFileList;
        this.note = note;
        this.content = content;
        this.staffReceivedId = staffReceivedId;
        this.contactName = contactName;
        this.destroyReasonId = destroyReasonId;
        this.destroyDatetime = destroyDatetime;
        this.destroyUser = destroyUser;
        this.quantityOrderAction = quantityOrderAction;
        this.quantityOrderActionComplete = quantityOrderActionComplete;
        this.sourceOrder = sourceOrder;
        this.inputType = inputType;
        this.outputType = outputType;

    }
    //Getters and setters

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceId() {
        return provinceId;
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

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderStockId(String orderStockId) {
        this.orderStockId = orderStockId;
    }

    public String getOrderStockId() {
        return orderStockId;
    }

    public void setIeExpectDate(String ieExpectDate) {
        this.ieExpectDate = ieExpectDate;
    }

    public String getIeExpectDate() {
        return ieExpectDate;
    }

    public void setStorageDate(String storageDate) {
        this.storageDate = storageDate;
    }

    public String getStorageDate() {
        return storageDate;
    }

    public void setPackingListNo(String packingListNo) {
        this.packingListNo = packingListNo;
    }

    public String getPackingListNo() {
        return packingListNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setAttachFileList(String attachFileList) {
        this.attachFileList = attachFileList;
    }

    public String getAttachFileList() {
        return attachFileList;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setStaffReceivedId(String staffReceivedId) {
        this.staffReceivedId = staffReceivedId;
    }

    public String getStaffReceivedId() {
        return staffReceivedId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setDestroyReasonId(String destroyReasonId) {
        this.destroyReasonId = destroyReasonId;
    }

    public String getDestroyReasonId() {
        return destroyReasonId;
    }

    public void setDestroyDatetime(String destroyDatetime) {
        this.destroyDatetime = destroyDatetime;
    }

    public String getDestroyDatetime() {
        return destroyDatetime;
    }

    public void setDestroyUser(String destroyUser) {
        this.destroyUser = destroyUser;
    }

    public String getDestroyUser() {
        return destroyUser;
    }

    public void setQuantityOrderAction(String quantityOrderAction) {
        this.quantityOrderAction = quantityOrderAction;
    }

    public String getQuantityOrderAction() {
        return quantityOrderAction;
    }

    public void setQuantityOrderActionComplete(String quantityOrderActionComplete) {
        this.quantityOrderActionComplete = quantityOrderActionComplete;
    }

    public String getQuantityOrderActionComplete() {
        return quantityOrderActionComplete;
    }

   
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getSourceOrder() {
        return sourceOrder;
    }

    public void setSourceOrder(String sourceOrder) {
        this.sourceOrder = sourceOrder;
    }

    

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getScanTicket() {
        return scanTicket;
    }

    public void setScanTicket(String scanTicket) {
        this.scanTicket = scanTicket;
    }

    public List<OrderGoodsDetailDTO> getLstOrderGoodsDetailDTO() {
        return lstOrderGoodsDetailDTO;
    }

    public void setLstOrderGoodsDetailDTO(List<OrderGoodsDetailDTO> lstOrderGoodsDetailDTO) {
        this.lstOrderGoodsDetailDTO = lstOrderGoodsDetailDTO;
    }

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTypeReceive() {
        return typeReceive;
    }

    public void setTypeReceive(String typeReceive) {
        this.typeReceive = typeReceive;
    }

    public String getTelName() {
        return telName;
    }

    public void setTelName(String telName) {
        this.telName = telName;
    }

    public String getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(String expectDate) {
        this.expectDate = expectDate;
    }

    public List<OrderGoodsDetailDTO> getListGood() {
        return listGood;
    }

    public void setListGood(List<OrderGoodsDetailDTO> listGood) {
        this.listGood = listGood;
    }

    public List<OrderGoodsLocationDTO> getLstOrderGoodsLocationDTO() {
        return lstOrderGoodsLocationDTO;
    }

    public void setLstOrderGoodsLocationDTO(List<OrderGoodsLocationDTO> lstOrderGoodsLocationDTO) {
        this.lstOrderGoodsLocationDTO = lstOrderGoodsLocationDTO;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStockerCode() {
        return stockerCode;
    }

    public void setStockerCode(String stockerCode) {
        this.stockerCode = stockerCode;
    }

    public String getTransUserName() {
        return transUserName;
    }

    public void setTransUserName(String transUserName) {
        this.transUserName = transUserName;
    }

    public String getOrderCommandCode() {
        return orderCommandCode;
    }

    public void setOrderCommandCode(String orderCommandCode) {
        this.orderCommandCode = orderCommandCode;
    }

    public String getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }
    
    
    
    
}
