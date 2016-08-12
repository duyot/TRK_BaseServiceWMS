/**
 * @(#)NoSerialInventoryBO.java 5/10/2016 4:47 PM, Copyright 2011 Viettel
 * Telecom. All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.logistic.wms.dto.NoSerialInventoryDTO;
import com.viettel.vfw5.base.model.BaseFWModel;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 5/10/2016 4:47 PM
 */
@Entity
@Table(name = "NO_SERIAL_INVENTORY")
public class NoSerialInventory extends BaseFWModel {

    //Fields
    private Long id;
    private Long custId;
    private String custCode;
    private String custName;
    private Long stockId;
    private Long goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private Double quantity;
    private String cellCode;
    private String barcode;
    private Date createDate;
    private String note;
    private Long status;
    private Long staffId;
    private String staffCode;
    private String staffName;
    private String inventoryCode;
    private String goodsUnitTypeName;

    //Constructors
    public NoSerialInventory() {
        setColId("id");
        setColName("id");
        setUniqueColumn(new String[]{"id"});
    }

    public NoSerialInventory(Long id) {
        this.id = id;
    }

    public NoSerialInventory(Long id, Long custId, String custCode, String custName, Long stockId, Long goodsId, String goodsCode, String goodsName, String goodsState, Double quantity, String cellCode, String barcode, Date createDate, String note, Long status, Long staffId, String staffCode, String staffName, String inventoryCode, String goodsUnitTypeName) {
        this.id = id;
        this.custId = custId;
        this.custCode = custCode;
        this.custName = custName;
        this.stockId = stockId;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsState = goodsState;
        this.quantity = quantity;
        this.cellCode = cellCode;
        this.barcode = barcode;
        this.createDate = createDate;
        this.note = note;
        this.status = status;
        this.staffId = staffId;
        this.staffCode = staffCode;
        this.staffName = staffName;
        this.inventoryCode = inventoryCode;
        this.goodsUnitTypeName = goodsUnitTypeName;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "NO_SERIAL_INVENTORY_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "CUST_ID", nullable = false)
    public Long getCustId() {
        return this.custId;
    }

    public void setCustId(final Long custId) {
        this.custId = custId;
    }

    @Column(name = "CUST_CODE")
    public String getCustCode() {
        return this.custCode;
    }

    public void setCustCode(final String custCode) {
        this.custCode = custCode;
    }

    @Column(name = "CUST_NAME")
    public String getCustName() {
        return this.custName;
    }

    public void setCustName(final String custName) {
        this.custName = custName;
    }

    @Column(name = "STOCK_ID")
    public Long getStockId() {
        return this.stockId;
    }

    public void setStockId(final Long stockId) {
        this.stockId = stockId;
    }

    @Column(name = "GOODS_ID", nullable = false)
    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(final Long goodsId) {
        this.goodsId = goodsId;
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

    @Column(name = "GOODS_STATE")
    public String getGoodsState() {
        return this.goodsState;
    }

    public void setGoodsState(final String goodsState) {
        this.goodsState = goodsState;
    }

    @Column(name = "QUANTITY")
    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Double quantity) {
        this.quantity = quantity;
    }

    @Column(name = "CELL_CODE")
    public String getCellCode() {
        return this.cellCode;
    }

    public void setCellCode(final String cellCode) {
        this.cellCode = cellCode;
    }

    @Column(name = "BARCODE")
    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(final String barcode) {
        this.barcode = barcode;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "NOTE")
    public String getNote() {
        return this.note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Column(name = "STATUS")
    public Long getStatus() {
        return this.status;
    }

    public void setStatus(final Long status) {
        this.status = status;
    }

    @Column(name = "STAFF_ID")
    public Long getStaffId() {
        return this.staffId;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }

    @Column(name = "STAFF_CODE")
    public String getStaffCode() {
        return this.staffCode;
    }

    public void setStaffCode(final String staffCode) {
        this.staffCode = staffCode;
    }

    @Column(name = "STAFF_NAME")
    public String getStaffName() {
        return this.staffName;
    }

    public void setStaffName(final String staffName) {
        this.staffName = staffName;
    }

    @Column(name = "INVENTORY_CODE")
    public String getInventoryCode() {
        return this.inventoryCode;
    }

    public void setInventoryCode(final String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    @Column(name = "GOODS_UNIT_TYPE_NAME")
    public String getGoodsUnitTypeName() {
        return goodsUnitTypeName;
    }

    public void setGoodsUnitTypeName(String goodsUnitTypeName) {
        this.goodsUnitTypeName = goodsUnitTypeName;
    }

    @Override
    public NoSerialInventoryDTO toDTO() {
        NoSerialInventoryDTO dto = new NoSerialInventoryDTO(
                id == null ? null : id.toString(), custId == null ? null : custId.toString(), custCode, custName, stockId == null ? null : stockId.toString(), goodsId == null ? null : goodsId.toString(), goodsCode, goodsName, goodsState, quantity == null ? null : quantity.toString(), cellCode, barcode, createDate == null ? null : DateTimeUtils.convertDateToString(createDate, ParamUtils.ddMMyyyy), note, status == null ? null : status.toString(), staffId == null ? null : staffId.toString(), staffCode, staffName, inventoryCode, goodsUnitTypeName
        );
        return dto;
    }
}
