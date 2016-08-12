/* @(#)InventoryActionGoodsForm.java , Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.InventoryActionGoods;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:28 PM
 */
@XmlRootElement(name = "InventoryActionGoods")
public class InventoryActionGoodsDTO extends BaseFWDTO<InventoryActionGoods> {

    //Fields

    private String inventoryActionGoodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsId;
    private String unitId;
    private String unitName;
    private String inventoryActionId;
    private String inventoryActionIdName;
    private static long changedTime = 0;
    @XStreamImplicit(itemFieldName = "lstConditionBean")
    List<ConditionBean> lstConditionBean;

    //Constructor

    public InventoryActionGoodsDTO() {
        setDefaultSortField("inventoryActionGoodsId");
    }

    public InventoryActionGoodsDTO(String inventoryActionGoodsId, String goodsCode, String goodsName, String goodsId, String unitId, String unitName, String inventoryActionId) {
        this.inventoryActionGoodsId = inventoryActionGoodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsId = goodsId;
        this.unitId = unitId;
        this.unitName = unitName;
        this.inventoryActionId = inventoryActionId;
    }
//Getters and setters

    public void setInventoryActionGoodsId(String inventoryActionGoodsId) {
        this.inventoryActionGoodsId = inventoryActionGoodsId;
    }

    public String getInventoryActionGoodsId() {
        return inventoryActionGoodsId;
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

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setInventoryActionId(String inventoryActionId) {
        this.inventoryActionId = inventoryActionId;
    }

    public String getInventoryActionId() {
        return inventoryActionId;
    }

    public void setInventoryActionIdName(String inventoryActionIdName) {
        this.inventoryActionIdName = inventoryActionIdName;
    }

    public String getInventoryActionIdName() {
        return inventoryActionIdName;
    }

    @Override
    public InventoryActionGoods toModel() {
        InventoryActionGoods model = new InventoryActionGoods(
                !StringUtils.validString(inventoryActionGoodsId) ? null
                        : Long.valueOf(inventoryActionGoodsId),
                goodsCode,
                goodsName,
                !StringUtils.validString(goodsId) ? null
                        : Long.valueOf(goodsId),
                !StringUtils.validString(unitId) ? null
                        : Long.valueOf(unitId),
                unitName,
                !StringUtils.validString(inventoryActionId) ? null
                        : Long.valueOf(inventoryActionId));
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(inventoryActionGoodsId) ? null : Long.valueOf(inventoryActionGoodsId);
    }

    @Override
    public String catchName() {
        return getInventoryActionGoodsId().toString();
    }

    @Override
    public long getChangedTime() {
        return InventoryActionGoodsDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        InventoryActionGoodsDTO.changedTime = changedTime;
    }

    public List<ConditionBean> getLstConditionBean() {
        return lstConditionBean;
    }

    public void setLstConditionBean(List<ConditionBean> lstConditionBean) {
        this.lstConditionBean = lstConditionBean;
    }
    
}