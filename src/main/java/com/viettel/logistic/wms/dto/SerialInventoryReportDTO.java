/**
 * @(#)SerialInventoryForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;


/**
 * @author duyot
 * @version 1.0
 * @since 4/6/2016 9:40 AM
 */
public class SerialInventoryReportDTO  {

    //Fields
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    //
    private String quantityState1;
    private String quantityState2;
    private String total;
    //
    private String goodsType;
    private String goodsUnitTypeName;
    

    public SerialInventoryReportDTO() {
    }

    public SerialInventoryReportDTO(String goodsId, String goodsCode, String goodsName, String quantityState1, String quantityState2, String total, String goodsType, String goodsUnitTypeName) {
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.quantityState1 = quantityState1;
        this.quantityState2 = quantityState2;
        this.total = total;
        this.goodsType = goodsType;
        this.goodsUnitTypeName = goodsUnitTypeName;
    }
    
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getQuantityState1() {
        return quantityState1;
    }

    public void setQuantityState1(String quantityState1) {
        this.quantityState1 = quantityState1;
    }

    public String getQuantityState2() {
        return quantityState2;
    }

    public void setQuantityState2(String quantityState2) {
        this.quantityState2 = quantityState2;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsUnitTypeName() {
        return goodsUnitTypeName;
    }

    public void setGoodsUnitTypeName(String goodsUnitTypeName) {
        this.goodsUnitTypeName = goodsUnitTypeName;
    }
    
    
    
    
    
}
