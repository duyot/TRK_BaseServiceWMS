/**
 * @(#)GoodsAddInforForm.java , Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.GoodsAddInfor;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 22-Apr-15 7:11 PM
 */
@XmlRootElement(name = "GoodsAddInfor")
public class GoodsAddInforDTO extends BaseFWDTO<GoodsAddInfor> {

    //Fields

    private String id;
    private String goodsId;
    private String code;
    private String name;
    private static long changedTime = 0;

    //Constructor

    public GoodsAddInforDTO() {
        setDefaultSortField("goodsId");
    }

    public GoodsAddInforDTO(String id, String goodsId, String code, String name) {
        this.id = id;
        this.goodsId = goodsId;
        this.code = code;
        this.name = name;
    }
	//Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public GoodsAddInfor toModel() {
        GoodsAddInfor model = new GoodsAddInfor(
                !StringUtils.validString(id) ? null
                        : Long.valueOf(id),
                !StringUtils.validString(goodsId) ? null
                        : Long.valueOf(goodsId),
                code,
                name);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id) ? null : Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getGoodsId().toString();
    }

    @Override
    public long getChangedTime() {
        return GoodsAddInforDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        GoodsAddInforDTO.changedTime = changedTime;
    }
}
