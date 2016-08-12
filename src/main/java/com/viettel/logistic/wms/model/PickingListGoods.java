/**
 * @(#)PickingListGoodsBO.java 08-May-15 4:07 PM, Copyright 2011 Viettel
 * Telecom. All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.PickingListGoodsDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 4:07 PM
 */
@Entity
@Table(name = "PICKING_LIST_GOODS")
public class PickingListGoods extends BaseFWModel {

    //Fields
    private Long id;
    private Date createdDate;
    private String goodsType;
    private String goodsTypeName;
    private Long goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private String goodsStateName;
    private Long goodsUnitId;
    private String goodsUnitName;
    private String goodsIsSerial;
    private String goodsIsSerialStrip;
    private Double amount;
    private Long pickingListId;
    private String cellCodeList;

    private String fromSerial;
    private String toSerial;

    //Constructors
    public PickingListGoods() {
        setColId("id");
        setColName("goodsType");
        setUniqueColumn(new String[]{"id"});
    }

    public PickingListGoods(Long id) {
        this.id = id;
    }

    public PickingListGoods(Long id, Date createdDate, String goodsType, String goodsTypeName, Long goodsId,
            String goodsCode, String goodsName, String goodsState, String goodsStateName,
            Long goodsUnitId, String goodsUnitName, String goodsIsSerial, String goodsIsSerialStrip,
            Double amount, Long pickingListId, String cellCodeList, String fromSerial, String toSerial) {
        this.id = id;
        this.createdDate = createdDate;
        this.goodsType = goodsType;
        this.goodsTypeName = goodsTypeName;
        this.goodsId = goodsId;
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsState = goodsState;
        this.goodsStateName = goodsStateName;
        this.goodsUnitId = goodsUnitId;
        this.goodsUnitName = goodsUnitName;
        this.goodsIsSerial = goodsIsSerial;
        this.goodsIsSerialStrip = goodsIsSerialStrip;
        this.amount = amount;
        this.pickingListId = pickingListId;
        this.cellCodeList = cellCodeList;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "PICKING_LIST_GOODS_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false, columnDefinition = "PickingList")
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "GOODS_TYPE")
    public String getGoodsType() {
        return this.goodsType;
    }

    public void setGoodsType(final String goodsType) {
        this.goodsType = goodsType;
    }

    @Column(name = "GOODS_TYPE_NAME")
    public String getGoodsTypeName() {
        return this.goodsTypeName;
    }

    public void setGoodsTypeName(final String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    @Column(name = "GOODS_ID")
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

    @Column(name = "GOODS_STATE_NAME")
    public String getGoodsStateName() {
        return this.goodsStateName;
    }

    public void setGoodsStateName(final String goodsStateName) {
        this.goodsStateName = goodsStateName;
    }

    @Column(name = "GOODS_UNIT_ID")
    public Long getGoodsUnitId() {
        return this.goodsUnitId;
    }

    public void setGoodsUnitId(final Long goodsUnitId) {
        this.goodsUnitId = goodsUnitId;
    }

    @Column(name = "GOODS_UNIT_NAME")
    public String getGoodsUnitName() {
        return this.goodsUnitName;
    }

    public void setGoodsUnitName(final String goodsUnitName) {
        this.goodsUnitName = goodsUnitName;
    }

    @Column(name = "GOODS_IS_SERIAL")
    public String getGoodsIsSerial() {
        return this.goodsIsSerial;
    }

    public void setGoodsIsSerial(final String goodsIsSerial) {
        this.goodsIsSerial = goodsIsSerial;
    }

    @Column(name = "GOODS_IS_SERIAL_STRIP")
    public String getGoodsIsSerialStrip() {
        return this.goodsIsSerialStrip;
    }

    public void setGoodsIsSerialStrip(final String goodsIsSerialStrip) {
        this.goodsIsSerialStrip = goodsIsSerialStrip;
    }

    @Column(name = "AMOUNT")
    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    @Column(name = "PICKING_LIST_ID", nullable = false)
    public Long getPickingListId() {
        return this.pickingListId;
    }

    public void setPickingListId(final Long pickingListId) {
        this.pickingListId = pickingListId;
    }

    @Column(name = "CELL_CODE_LIST")
    public String getCellCodeList() {
        return this.cellCodeList;
    }

    public void setCellCodeList(final String cellCodeList) {
        this.cellCodeList = cellCodeList;
    }

    @Column(name = "FROM_SERIAL")
    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(final String fromSerial) {
        this.fromSerial = fromSerial;
    }

    @Column(name = "TO_SERIAL")
    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(final String toSerial) {
        this.toSerial = toSerial;
    }

    @Override
    public PickingListGoodsDTO toDTO() {
        PickingListGoodsDTO dto = new PickingListGoodsDTO(
                id == null ? null : id.toString(), createdDate == null ? null : DateTimeUtils.convertDateToString(createdDate,
                                ParamUtils.ddMMyyyy), goodsType, goodsTypeName, goodsId == null ? null : goodsId.toString(),
                goodsCode, goodsName, goodsState, goodsStateName, goodsUnitId == null ? null : goodsUnitId.toString(),
                goodsUnitName, goodsIsSerial, goodsIsSerialStrip, amount == null ? null : amount.toString(),
                pickingListId == null ? null : pickingListId.toString(), cellCodeList,
                fromSerial == null ? null : fromSerial.toString(), toSerial == null ? null : toSerial.toString()
        );
        return dto;
    }
}
