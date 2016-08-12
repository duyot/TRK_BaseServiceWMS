/**
 * @(#)OrderGoodsDetailSerialForm.java , Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logstic.wms.webservice.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 09-Apr-15 4:37 PM
 */
@XmlRootElement(name = "OrderGoodsDetailSerial")
public class OrderGoodsDetailSerialDTO {

    //Fields
    private String id;
    private String orderId;
    private String orderIdName;
    private String orderDatetime;
    private String orderLocationId;
    private String orderLocationIdName;
    private String orderGoodsDetailId;
    private String orderGoodsDetailIdName;
    private String goodsId;
    private String fromSerial;
    private String toSerial;
    private String bincode;
    private String quantity;
    private String realquantity;
    private String status;
    private String goodsUnitName;
    private String tmpOrderGoodsLocationId;
    private String errorInfor;
    private static long changedTime = 0;

    //Constructor
    public OrderGoodsDetailSerialDTO() {
    }

    public OrderGoodsDetailSerialDTO(String id, String orderId, String orderDatetime, String orderLocationId,
            String orderGoodsDetailId, String goodsId, String fromSerial, String toSerial,
            String bincode, String quantity,
            String realquantity, String status
    ) {
        this.id = id;
        this.orderId = orderId;
        this.orderDatetime = orderDatetime;
        this.orderLocationId = orderLocationId;
        this.orderGoodsDetailId = orderGoodsDetailId;
        this.goodsId = goodsId;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.bincode = bincode;
        this.quantity = quantity;
        this.realquantity = realquantity;
        this.status = status;
    }

    public OrderGoodsDetailSerialDTO(String orderGoodsDetailId) {
        this.orderGoodsDetailId = orderGoodsDetailId;
    }

    public OrderGoodsDetailSerialDTO(String goodsId, String fromSerial, String toSerial,
            String bincode, String quantity,
            String realquantity, String status
    ) {
        this.id = id;
        this.orderId = orderId;
        this.orderDatetime = orderDatetime;
        this.orderLocationId = orderLocationId;
        this.orderGoodsDetailId = orderGoodsDetailId;
        this.goodsId = goodsId;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.bincode = bincode;
        this.quantity = quantity;
        this.realquantity = realquantity;
        this.status = status;
    }

    public OrderGoodsDetailSerialDTO(String id, String orderId, String orderDatetime, String orderGoodsDetailId, String fromSerial, String toSerial, String bincode, String quantity) {
        this.id = id;
        this.orderId = orderId;
        this.orderDatetime = orderDatetime;
        this.orderGoodsDetailId = orderGoodsDetailId;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.bincode = bincode;
        this.quantity = quantity;
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

    public void setOrderLocationIdName(String orderLocationIdName) {
        this.orderLocationIdName = orderLocationIdName;
    }

    public String getOrderLocationIdName() {
        return orderLocationIdName;
    }

    public void setOrderGoodsDetailId(String orderGoodsDetailId) {
        this.orderGoodsDetailId = orderGoodsDetailId;
    }

    public String getOrderGoodsDetailId() {
        return orderGoodsDetailId;
    }

    public void setOrderGoodsDetailIdName(String orderGoodsDetailIdName) {
        this.orderGoodsDetailIdName = orderGoodsDetailIdName;
    }

    public String getOrderGoodsDetailIdName() {
        return orderGoodsDetailIdName;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setBincode(String bincode) {
        this.bincode = bincode;
    }

    public String getBincode() {
        return bincode;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getRealquantity() {
        return realquantity;
    }

    public void setRealquantity(String realquantity) {
        this.realquantity = realquantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGoodsUnitName() {
        return goodsUnitName;
    }

    public void setGoodsUnitName(String goodsUnitName) {
        this.goodsUnitName = goodsUnitName;
    }

    public String getTmpOrderGoodsLocationId() {
        return tmpOrderGoodsLocationId;
    }

    public void setTmpOrderGoodsLocationId(String tmpOrderGoodsLocationId) {
        this.tmpOrderGoodsLocationId = tmpOrderGoodsLocationId;
    }

    public String getErrorInfor() {
        return errorInfor;
    }

    public void setErrorInfor(String errorInfor) {
        this.errorInfor = errorInfor;
    }

}
