/**
 * @(#)StockBO.java 06-Apr-15 11:42 PM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.StockDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author Truongbx3
 * @version 1.0
 * @since 06-Apr-15 11:42 PM
 */
@Entity
@Table(name = "STOCK")
public class Stock extends BaseFWModel {

    //Fields
    private Long stockId;
    private String code;
    private String name;
    private String address;
    private String type;
    private Long provinceId;
    private Long districtId;
    private Long wardId;
    private Long parentStockId;
    private Double square;
    private Double volume;
    private Date effectiveDate;
    private Date expiryDate;
    private Long stockPath;
    private String status;
    private Long deptId;
    private Double usedVolume;

    //Constructors
    public Stock() {
        setColId("stockId");
        setColName("name");
        setUniqueColumn(new String[]{"code"});
    }

    public Stock(Long stockId) {
        this.stockId = stockId;
    }

    public Stock(Long stockId, String code, String name, String address, String type, Long provinceId, Long districtId, Long wardId, Long parentStockId, Double square, Double volume, Date effectiveDate, Date expiryDate, Long stockPath, String status, Long deptId, Double usedVolume) {
        this.stockId = stockId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.type = type;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.parentStockId = parentStockId;
        this.square = square;
        this.volume = volume;
        this.effectiveDate = effectiveDate;
        this.expiryDate = expiryDate;
        this.stockPath = stockPath;
        this.status = status;
        this.deptId = deptId;
        this.usedVolume = usedVolume;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "STOCK_SEQ")
            }
    )
    @Column(name = "STOCK_ID", unique = true, nullable = false)
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

    @Column(name = "ADDRESS")
    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    @Column(name = "TYPE")
    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Column(name = "PROVINCE_ID")
    public Long getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(final Long provinceId) {
        this.provinceId = provinceId;
    }

    @Column(name = "DISTRICT_ID")
    public Long getDistrictId() {
        return this.districtId;
    }

    public void setDistrictId(final Long districtId) {
        this.districtId = districtId;
    }

    @Column(name = "WARD_ID")
    public Long getWardId() {
        return this.wardId;
    }

    public void setWardId(final Long wardId) {
        this.wardId = wardId;
    }

    @Column(name = "PARENT_STOCK_ID")
    public Long getParentStockId() {
        return this.parentStockId;
    }

    public void setParentStockId(final Long parentStockId) {
        this.parentStockId = parentStockId;
    }

    @Column(name = "SQUARE")
    public Double getSquare() {
        return this.square;
    }

    public void setSquare(final Double square) {
        this.square = square;
    }

    @Column(name = "VOLUME")
    public Double getVolume() {
        return this.volume;
    }

    public void setVolume(final Double volume) {
        this.volume = volume;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "EFFECTIVE_DATE")
    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(final Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "EXPIRY_DATE")
    public Date getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(final Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Column(name = "STOCK_PATH")
    public Long getStockPath() {
        return this.stockPath;
    }

    public void setStockPath(final Long stockPath) {
        this.stockPath = stockPath;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Column(name = "DEPT_ID")
    public Long getDeptId() {
        return this.deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    @Column(name = "USED_VOLUME")
    public Double getUsedVolume() {
        return this.usedVolume;
    }

    public void setUsedVolume(final Double usedVolume) {
        this.usedVolume = usedVolume;
    }

    @Override
    public StockDTO toDTO() {
        StockDTO dto = new StockDTO(
                stockId == null ? null : stockId.toString(),
                code,
                name,
                address,
                type,
                provinceId == null ? null : provinceId.toString(),
                districtId == null ? null : districtId.toString(),
                wardId == null ? null : wardId.toString(),
                parentStockId == null ? null : parentStockId.toString(),
                square == null ? null : square.toString(),
                volume == null ? null : volume.toString(),
                effectiveDate == null ? null : DateTimeUtils.convertDateToString(effectiveDate, ParamUtils.ddMMyyyy),
                expiryDate == null ? null : DateTimeUtils.convertDateToString(expiryDate, ParamUtils.ddMMyyyy),
                stockPath == null ? null : stockPath.toString(),
                status,
                deptId == null ? null : deptId.toString(),
                usedVolume == null ? null : usedVolume.toString()
        );
        return dto;
    }
}
