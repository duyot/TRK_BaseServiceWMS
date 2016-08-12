/**
 * @(#)InventoryActionGoodsBO.java 6/9/2015 11:28 PM, Copyright 2011 Viettel
 * Telecom. All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.InventoryActionGoodsDTO;

/**
 * @author hongdq4
 * @version 1.0
 * @since 6/9/2015 11:28 PM
 */
@Entity
@Table(name = "INVENTORY_ACTION_GOODS")
public class InventoryActionGoods extends BaseFWModel {

    //Fields
    private Long inventoryActionGoodsId;
    private String goodsCode;
    private String goodsName;
    private Long goodsId;
    private Long unitId;
    private String unitName;
    private Long inventoryActionId;

    //Constructors
    public InventoryActionGoods() {
        setColId("inventoryActionGoodsId");
        setColName("inventoryActionGoodsId");
        setUniqueColumn(new String[]{"inventoryActionGoodsId"});
    }

    public InventoryActionGoods(Long inventoryActionGoodsId) {
        this.inventoryActionGoodsId = inventoryActionGoodsId;
    }

    public InventoryActionGoods(Long inventoryActionGoodsId, String goodsCode, String goodsName, Long goodsId, Long unitId, String unitName, Long inventoryActionId) {
        this.inventoryActionGoodsId = inventoryActionGoodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsId = goodsId;
        this.unitId = unitId;
        this.unitName = unitName;
        this.inventoryActionId = inventoryActionId;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "INVENTORY_ACTION_GOODS_SEQ")
            }
    )
    @Column(name = "INVENTORY_ACTION_GOODS_ID", unique = true, nullable = false)
    public Long getInventoryActionGoodsId() {
        return this.inventoryActionGoodsId;
    }

    public void setInventoryActionGoodsId(final Long inventoryActionGoodsId) {
        this.inventoryActionGoodsId = inventoryActionGoodsId;
    }

    @Column(name = "GOODS_CODE")
    public String getGoodsCode() {
        return this.goodsCode;
    }

    public void setGoodsCode(final String goodsCode) {
        this.goodsCode = goodsCode;
    }

    @Column(name = "GOODS_NAME")
    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(final String goodsName) {
        this.goodsName = goodsName;
    }

    @Column(name = "GOODS_ID")
    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(final Long goodsId) {
        this.goodsId = goodsId;
    }

    @Column(name = "UNIT_ID")
    public Long getUnitId() {
        return this.unitId;
    }

    public void setUnitId(final Long unitId) {
        this.unitId = unitId;
    }

    @Column(name = "UNIT_NAME")
    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(final String unitName) {
        this.unitName = unitName;
    }

    @Column(name = "INVENTORY_ACTION_ID", nullable = false, columnDefinition = "InventoryAction")
    public Long getInventoryActionId() {
        return this.inventoryActionId;
    }

    public void setInventoryActionId(final Long inventoryActionId) {
        this.inventoryActionId = inventoryActionId;
    }

    @Override
    public InventoryActionGoodsDTO toDTO() {
        InventoryActionGoodsDTO dto = new InventoryActionGoodsDTO(
                inventoryActionGoodsId == null ? null : inventoryActionGoodsId.toString(), goodsCode, goodsName, goodsId == null ? null : goodsId.toString(), unitId == null ? null : unitId.toString(), unitName, inventoryActionId == null ? null : inventoryActionId.toString()
        );
        return dto;
    }
}
