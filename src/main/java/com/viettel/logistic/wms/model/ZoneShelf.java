/**
 * @(#)ZoneShelfBO.java 06-May-15 9:44 PM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.ZoneShelfDTO;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 06-May-15 9:44 PM
 */
@Entity
@Table(name = "ZONE_SHELF")
public class ZoneShelf extends BaseFWModel {

    //Fields
    private Long id;
    private Long stockId;
    private Long zoneId;
    private String code;
    private String name;
    private Long amount;
    private Long layerNum;
    private Long cellNum;
    private Long hightLayer;
    private Double hight;
    private Double wide;
    private Double length;
    private Double volume;
    private Double volumeReal;
    private String isExportedCell;

    //Constructors
    public ZoneShelf() {
        setColId("id");
        setColName("zoneId");
        setUniqueColumn(new String[]{"stockId", "zoneId", "code"});
    }

    public ZoneShelf(Long id) {
        this.id = id;
    }

    public ZoneShelf(Long id, Long stockId, Long zoneId, String code, String name, Long amount, Long layerNum, Long cellNum, Long hightLayer, Double hight, Double wide, Double length, Double volume, Double volumeReal, String isExportedCell) {
        this.id = id;
        this.stockId = stockId;
        this.zoneId = zoneId;
        this.code = code;
        this.name = name;
        this.amount = amount;
        this.layerNum = layerNum;
        this.cellNum = cellNum;
        this.hightLayer = hightLayer;
        this.hight = hight;
        this.wide = wide;
        this.length = length;
        this.volume = volume;
        this.volumeReal = volumeReal;
        this.isExportedCell = isExportedCell;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "ZONE_SHELF_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "STOCK_ID", nullable = false)
    public Long getStockId() {
        return this.stockId;
    }

    public void setStockId(final Long stockId) {
        this.stockId = stockId;
    }

    @Column(name = "ZONE_ID", nullable = false, columnDefinition = "Zones")
    public Long getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(final Long zoneId) {
        this.zoneId = zoneId;
    }

    @Column(name = "CODE", nullable = false)
    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(name = "AMOUNT", nullable = false)
    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(final Long amount) {
        this.amount = amount;
    }

    @Column(name = "LAYER_NUM")
    public Long getLayerNum() {
        return this.layerNum;
    }

    public void setLayerNum(final Long layerNum) {
        this.layerNum = layerNum;
    }

    @Column(name = "CELL_NUM")
    public Long getCellNum() {
        return this.cellNum;
    }

    public void setCellNum(final Long cellNum) {
        this.cellNum = cellNum;
    }

    @Column(name = "HIGHT_LAYER")
    public Long getHightLayer() {
        return this.hightLayer;
    }

    public void setHightLayer(final Long hightLayer) {
        this.hightLayer = hightLayer;
    }

    @Column(name = "HIGHT")
    public Double getHight() {
        return this.hight;
    }

    public void setHight(final Double hight) {
        this.hight = hight;
    }

    @Column(name = "WIDE")
    public Double getWide() {
        return this.wide;
    }

    public void setWide(final Double wide) {
        this.wide = wide;
    }

    @Column(name = "LENGTH")
    public Double getLength() {
        return this.length;
    }

    public void setLength(final Double length) {
        this.length = length;
    }

    @Column(name = "VOLUME")
    public Double getVolume() {
        return this.volume;
    }

    public void setVolume(final Double volume) {
        this.volume = volume;
    }

    @Column(name = "VOLUME_REAL")
    public Double getVolumeReal() {
        return this.volumeReal;
    }

    public void setVolumeReal(final Double volumeReal) {
        this.volumeReal = volumeReal;
    }

    @Column(name = "IS_EXPORTED_CELL", nullable = false)
    public String getIsExportedCell() {
        return this.isExportedCell;
    }

    public void setIsExportedCell(final String isExportedCell) {
        this.isExportedCell = isExportedCell;
    }

    @Override
    public ZoneShelfDTO toDTO() {
        ZoneShelfDTO dto = new ZoneShelfDTO(
                id == null ? null : id.toString(), stockId == null ? null : stockId.toString(), zoneId == null ? null : zoneId.toString(), code, name, amount == null ? null : amount.toString(), layerNum == null ? null : layerNum.toString(), cellNum == null ? null : cellNum.toString(), hightLayer == null ? null : hightLayer.toString(), hight == null ? null : hight.toString(), wide == null ? null : wide.toString(), length == null ? null : length.toString(), volume == null ? null : volume.toString(), volumeReal == null ? null : volumeReal.toString(), isExportedCell
        );
        return dto;
    }
}
