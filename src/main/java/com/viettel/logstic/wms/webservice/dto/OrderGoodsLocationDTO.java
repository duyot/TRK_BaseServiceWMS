/**
 * @(#)OrderGoodsLocationForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logstic.wms.webservice.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 28-Apr-15 10:54 AM
 */
public class OrderGoodsLocationDTO {

    //Fields
    private String id;
    private String orderDatetime;
    private String orderId;
    private String orderType;
    private String location;
    private String provinceId;
    private String provinceName;
    private String districtId;
    private String districtName;
    private String wardId;
    private String wardName;
    private String contactName;
    private String telNumber;
    private String attachServiceIdList;
    private String expectDate;
    private String displayName;
    @XStreamImplicit(itemFieldName = "lstOrderGoodsDetailDTO")
    List<OrderGoodsDetailDTO> lstOrderGoodsDetailDTO;

    //Constructor
    public OrderGoodsLocationDTO() {
    }

    public OrderGoodsLocationDTO(String id, String orderDatetime, String orderId, String orderType, String location, String provinceId, String provinceName, String districtId, String districtName, String wardId, String wardName, String contactName, String telNumber, String attachServiceIdList, String expectDate) {
        this.id = id;
        this.orderDatetime = orderDatetime;
        this.orderId = orderId;
        this.orderType = orderType;
        this.location = location;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.contactName = contactName;
        this.telNumber = telNumber;
        this.attachServiceIdList = attachServiceIdList;
        this.expectDate = expectDate;
    }
    //Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardName() {
        return wardName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setAttachServiceIdList(String attachServiceIdList) {
        this.attachServiceIdList = attachServiceIdList;
    }

    public String getAttachServiceIdList() {
        return attachServiceIdList;
    }

    public void setExpectDate(String expectDate) {
        this.expectDate = expectDate;
    }

    public String getExpectDate() {
        return expectDate;
    }

    public List<OrderGoodsDetailDTO> getLstOrderGoodsDetailDTO() {
        return lstOrderGoodsDetailDTO;
    }

    public void setLstOrderGoodsDetailDTO(List<OrderGoodsDetailDTO> lstOrderGoodsDetailDTO) {
        this.lstOrderGoodsDetailDTO = lstOrderGoodsDetailDTO;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
