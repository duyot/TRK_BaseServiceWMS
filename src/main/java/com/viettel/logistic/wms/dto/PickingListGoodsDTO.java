/**
 * @(#)PickingListGoodsForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.logistic.wms.model.PickingListGoods;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 4:07 PM
 */
@XmlRootElement(name = "PickingListGoods")

    //Fields
public class PickingListGoodsDTO extends BaseFWDTO<PickingListGoods> {

    //Fields
    private String id;
    private String idName;
    private String createdDate;
    private String goodsType;
    private String goodsTypeName;
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String goodsState;
    private String goodsStateName;
    private String goodsUnitId;
    private String goodsUnitName;
    private String goodsIsSerial;
    private String goodsIsSerialStrip;
    private String amount;
    private String pickingListId;
    private String cellCodeList;
    private static long changedTime = 0;
    //
    private String tmpPickingListId;
    //
    private String fromSerial;
    private String toSerial;

    //Constructor
    public PickingListGoodsDTO() {
        setDefaultSortField("goodsType");
    }

    public PickingListGoodsDTO(String id, String createdDate, String goodsType, String goodsTypeName, String goodsId,
            String goodsCode, String goodsName, String goodsState, String goodsStateName,
            String goodsUnitId, String goodsUnitName, String goodsIsSerial, String goodsIsSerialStrip,
            String amount, String pickingListId, String cellCodeList, String fromSerial, String toSerial) {
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
    //Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getIdName() {
        return idName;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
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

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsStateName(String goodsStateName) {
        this.goodsStateName = goodsStateName;
    }

    public String getGoodsStateName() {
        return goodsStateName;
    }

    public void setGoodsUnitId(String goodsUnitId) {
        this.goodsUnitId = goodsUnitId;
    }

    public String getGoodsUnitId() {
        return goodsUnitId;
    }

    public void setGoodsUnitName(String goodsUnitName) {
        this.goodsUnitName = goodsUnitName;
    }

    public String getGoodsUnitName() {
        return goodsUnitName;
    }

    public void setGoodsIsSerial(String goodsIsSerial) {
        this.goodsIsSerial = goodsIsSerial;
    }

    public String getGoodsIsSerial() {
        return goodsIsSerial;
    }

    public void setGoodsIsSerialStrip(String goodsIsSerialStrip) {
        this.goodsIsSerialStrip = goodsIsSerialStrip;
    }

    public String getGoodsIsSerialStrip() {
        return goodsIsSerialStrip;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setPickingListId(String pickingListId) {
        this.pickingListId = pickingListId;
    }

    public String getPickingListId() {
        return pickingListId;
    }

    public void setCellCodeList(String cellCodeList) {
        this.cellCodeList = cellCodeList;
    }

    public String getCellCodeList() {
        return cellCodeList;
    }

    @Override
    public PickingListGoods toModel() {
        try {
            PickingListGoods model = new PickingListGoods(
                    !StringUtils.validString(id) ? null
                            : Long.valueOf(id),
                    !StringUtils.validString(createdDate) ? null
                            : DateTimeUtils.convertStringToDate(createdDate),
                    goodsType,
                    goodsTypeName,
                    !StringUtils.validString(goodsId) ? null
                            : Long.valueOf(goodsId),
                    goodsCode,
                    goodsName,
                    goodsState,
                    goodsStateName,
                    !StringUtils.validString(goodsUnitId) ? null
                            : Long.valueOf(goodsUnitId),
                    goodsUnitName,
                    goodsIsSerial,
                    goodsIsSerialStrip,
                    !StringUtils.validString(amount) ? null
                            : Double.parseDouble(amount),
                    !StringUtils.validString(pickingListId) ? null
                            : Long.valueOf(pickingListId),
                    cellCodeList,
                    fromSerial,
                    toSerial);
            return model;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id) ? null : Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getGoodsType().toString();
    }

    @Override
    public long getChangedTime() {
        return PickingListGoodsDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        PickingListGoodsDTO.changedTime = changedTime;
    }

    public String getTmpPickingListId() {
        return tmpPickingListId;
    }

    public void setTmpPickingListId(String tmpPickingListId) {
        this.tmpPickingListId = tmpPickingListId;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

}
