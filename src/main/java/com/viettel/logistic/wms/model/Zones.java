/**
 * @(#)ZonesBO.java 13-Apr-15 2:43 PM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.ZonesDTO;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 13-Apr-15 2:43 PM
 */
@Entity
@Table(name = "ZONES")
public class Zones extends BaseFWModel {

    //Fields
    private Long zoneId;
    private Long stockId;
    private String code;
    private String name;
    private String description;
    private Long type;
    private String status;
    private Double realVolume;
    private Double usedVolume;
    private String custIdList;
    private String goodsTypeIdList;

    //Constructors
    public Zones() {
        setColId("zoneId");
        setColName("code");
        setUniqueColumn(new String[]{"stockId", "code"});
    }

    public Zones(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Zones(Long zoneId, Long stockId, String code, String name, String description, Long type, String status, Double realVolume, Double usedVolume, String custIdList, String goodsTypeIdList) {
        this.zoneId = zoneId;
        this.stockId = stockId;
        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.status = status;
        this.realVolume = realVolume;
        this.usedVolume = usedVolume;
        this.custIdList = custIdList;
        this.goodsTypeIdList = goodsTypeIdList;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "ZONES_SEQ")
            }
    )
    @Column(name = "ZONE_ID", unique = true, nullable = false)
    public Long getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(final Long zoneId) {
        this.zoneId = zoneId;
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

    @Column(name = "NAME")
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Column(name = "TYPE")
    public Long getType() {
        return this.type;
    }

    public void setType(final Long type) {
        this.type = type;
    }

    @Column(name = "STATUS", nullable = false)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Column(name = "REAL_VOLUME")
    public Double getRealVolume() {
        return this.realVolume;
    }

    public void setRealVolume(final Double realVolume) {
        this.realVolume = realVolume;
    }

    @Column(name = "USED_VOLUME")
    public Double getUsedVolume() {
        return this.usedVolume;
    }

    public void setUsedVolume(final Double usedVolume) {
        this.usedVolume = usedVolume;
    }

    @Column(name = "CUST_ID_LIST")
    public String getCustIdList() {
        return this.custIdList;
    }

    public void setCustIdList(final String custIdList) {
        this.custIdList = custIdList;
    }

    @Column(name = "GOODS_TYPE_ID_LIST")
    public String getGoodsTypeIdList() {
        return this.goodsTypeIdList;
    }

    public void setGoodsTypeIdList(final String goodsTypeIdList) {
        this.goodsTypeIdList = goodsTypeIdList;
    }

    @Override
    public ZonesDTO toDTO() {
        ZonesDTO dto = new ZonesDTO(
                zoneId == null ? null : zoneId.toString(), stockId == null ? null : stockId.toString(), code, name, description, type == null ? null : type.toString(), status, realVolume == null ? null : String.format("%.0f", realVolume), usedVolume == null ? null : String.format("%.0f", usedVolume), custIdList, goodsTypeIdList
        );
        return dto;
    }
}
