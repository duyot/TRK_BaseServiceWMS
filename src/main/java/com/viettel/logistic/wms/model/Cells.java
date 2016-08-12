/**
 * @(#)CellsBO.java 08-Apr-15 9:36 AM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.CellsDTO;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@Entity
@Table(name = "CELLS")
public class Cells extends BaseFWModel {

    //Fields
    private Long cellId;
    private Long stockId;
    private Long zoneId;
    private Long zoneShelfId;
    private String code;
    private String name;
    private Double hight;
    private Double wide;
    private Double length;
    private Double volume;
    private Double volumeReal;
    private String status;

    //Constructors
    public Cells() {
        setColId("cellId");
        setColName("cellId");
        setUniqueColumn(new String[]{"stockId", "code"});
    }

    public Cells(Long cellId) {
        this.cellId = cellId;
    }

    public Cells(Long cellId, Long stockId, Long zoneId, Long zoneShelfId, String code, String name, Double hight, Double wide, Double length, Double volume, Double volumeReal, String status) {
        this.cellId = cellId;
        this.stockId = stockId;
        this.zoneId = zoneId;
        this.zoneShelfId = zoneShelfId;
        this.code = code;
        this.name = name;
        this.hight = hight;
        this.wide = wide;
        this.length = length;
        this.volume = volume;
        this.volumeReal = volumeReal;
        this.status = status;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "CELLS_SEQ")
            }
    )
    @Column(name = "CELL_ID", unique = true, nullable = false)
    public Long getCellId() {
        return this.cellId;
    }

    public void setCellId(final Long cellId) {
        this.cellId = cellId;
    }

    @Column(name = "STOCK_ID", nullable = false)
    public Long getStockId() {
        return this.stockId;
    }

    public void setStockId(final Long stockId) {
        this.stockId = stockId;
    }

    @Column(name = "ZONE_ID", nullable = false)
    public Long getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(final Long zoneId) {
        this.zoneId = zoneId;
    }

    @Column(name = "ZONE_SHELF_ID",  columnDefinition = "ZoneShelf")
    public Long getZoneShelfId() {
        return this.zoneShelfId;
    }

    public void setZoneShelfId(final Long zoneShelfId) {
        this.zoneShelfId = zoneShelfId;
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

    @Column(name = "STATUS", nullable = false)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public CellsDTO toDTO() {
        CellsDTO dto = new CellsDTO(
                cellId == null ? null : cellId.toString(), stockId == null ? null : stockId.toString(), zoneId == null ? null : zoneId.toString(), zoneShelfId == null ? null : zoneShelfId.toString(), code, name, hight == null ? null : hight.toString(), wide == null ? null : wide.toString(), length == null ? null : length.toString(), volume == null ? null : volume.toString(), volumeReal == null ? null : volumeReal.toString(), status
        );
        return dto;
    }
}
