/**
 * @(#)OrderGoodsDetailForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logstic.wms.webservice.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 11-Apr-15 11:11 AM
 */
@XmlRootElement(name = "OrderGoodsDetail")
public class OrderGoodsDetailDTO {

    //Fields
    private String id;
    private String orderId;
    private String orderIdName;
    private String orderDatetime;
    private String orderLocationId;
    private String goodsType;
    private String goodsTypeName;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsIsSerial;
    private String goodsIsSerialStrip;
    private String goodsState;
    private String goodsStateName;
    private String goodsUnitName;
    private String amount;
    private String amountOrder;
    private String amountReal;
    private String orderActionId;
    private String goodsPackingId;
    private String status;
    private String volume;
    private String weight;
    private String typeGoodsExport;
    private String amountIssue;
    private String location;
    private String deliveryDateTime;
    private String phoneNumber;
    private String goodsPackingCode;
    private String fromSerial;
    private String toSerial;
    private String quantity;
    private String binCode;
    private String barCode;
    private String cellCode;
    private String listSN;
    //lst serial for kts
    @XStreamImplicit(itemFieldName = "lstOrderGoodsDetailSerialDTO")
    List<OrderGoodsDetailSerialDTO> lstOrderGoodsDetailSerialDTO;
    //Constructor lst seril for bccs
    @XStreamImplicit(itemFieldName = "lstSerial")
    List<OrderGoodsDetailSerialDTO> lstDetailSerial;

    public OrderGoodsDetailDTO() {
    }

    public OrderGoodsDetailDTO(String orderId) {
        this.orderId = orderId;
    }

    public OrderGoodsDetailDTO(String id, String orderId, String orderDatetime, String orderLocationId, String goodsType, String goodsTypeName, String goodsId, String goodsCode, String goodsName, String goodsState, String goodsStateName, String goodsUnitName, String amount, String orderActionId, String goodsPackingId, String status, String volume, String weight) {
        this.id = id;
        this.orderId = orderId;
        this.orderDatetime = orderDatetime;
        this.orderLocationId = orderLocationId;
        this.goodsType = goodsType;
        this.goodsTypeName = goodsTypeName;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsState = goodsState;
        this.goodsStateName = goodsStateName;
        this.goodsUnitName = goodsUnitName;
        this.amount = amount;
        this.orderActionId = orderActionId;
        this.goodsPackingId = goodsPackingId;
        this.status = status;
        this.volume = volume;
        this.weight = weight;
    }
    //Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderIdName(String orderIdName) {
        this.orderIdName = orderIdName;
    }

    public String getOrderIdName() {
        return orderIdName;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderLocationId(String orderLocationId) {
        this.orderLocationId = orderLocationId;
    }

    public String getOrderLocationId() {
        return orderLocationId;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsStateName(String goodsStateName) {
        this.goodsStateName = goodsStateName;
    }

    public String getGoodsStateName() {
        return goodsStateName;
    }

    public void setGoodsUnitName(String goodsUnitName) {
        this.goodsUnitName = goodsUnitName;
    }

    public String getGoodsUnitName() {
        return goodsUnitName;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setOrderActionId(String orderActionId) {
        this.orderActionId = orderActionId;
    }

    public String getOrderActionId() {
        return orderActionId;
    }

    public void setGoodsPackingId(String goodsPackingId) {
        this.goodsPackingId = goodsPackingId;
    }

    public String getGoodsPackingId() {
        return goodsPackingId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolume() {
        return volume;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }

    public String getGoodsIsSerial() {
        return goodsIsSerial;
    }

    public void setGoodsIsSerial(String goodsIsSerial) {
        this.goodsIsSerial = goodsIsSerial;
    }

    public String getGoodsIsSerialStrip() {
        return goodsIsSerialStrip;
    }

    public void setGoodsIsSerialStrip(String goodsIsSerialStrip) {
        this.goodsIsSerialStrip = goodsIsSerialStrip;
    }

    public String getTypeGoodsExport() {
        return typeGoodsExport;
    }

    public void setTypeGoodsExport(String typeGoodsExport) {
        this.typeGoodsExport = typeGoodsExport;
    }

    public String getAmountIssue() {
        return amountIssue;
    }

    public void setAmountIssue(String amountIssue) {
        this.amountIssue = amountIssue;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGoodsPackingCode() {
        return goodsPackingCode;
    }

    public void setGoodsPackingCode(String goodsPackingCode) {
        this.goodsPackingCode = goodsPackingCode;
    }

    public List<OrderGoodsDetailSerialDTO> getLstOrderGoodsDetailSerialDTO() {
        return lstOrderGoodsDetailSerialDTO;
    }

    public void setLstOrderGoodsDetailSerialDTO(List<OrderGoodsDetailSerialDTO> lstOrderGoodsDetailSerialDTO) {
        this.lstOrderGoodsDetailSerialDTO = lstOrderGoodsDetailSerialDTO;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getCellCode() {
        return cellCode;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public String getAmountOrder() {
        return amountOrder;
    }

    public void setAmountOrder(String amountOrder) {
        this.amountOrder = amountOrder;
    }

    public String getAmountReal() {
        return amountReal;
    }

    public void setAmountReal(String amountReal) {
        this.amountReal = amountReal;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getListSN() {
        return listSN;
    }

    public void setListSN(String listSN) {
        this.listSN = listSN;
    }

    public List<OrderGoodsDetailSerialDTO> getLstDetailSerial() {
        return lstDetailSerial;
    }

    public void setLstDetailSerial(List<OrderGoodsDetailSerialDTO> lstDetailSerial) {
        this.lstDetailSerial = lstDetailSerial;
    }
    
    

}
