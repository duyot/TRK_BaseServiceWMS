/**
 * @(#)GoodsAddInforBO.java 22-Apr-15 7:11 PM, Copyright 2011 Viettel Telecom.
 * All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.GoodsAddInforDTO;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 22-Apr-15 7:11 PM
 */
@Entity
@Table(name = "GOODS_ADD_INFOR")
public class GoodsAddInfor extends BaseFWModel {

    //Fields
    private Long id;
    private Long goodsId;
    private String code;
    private String name;

    //Constructors
    public GoodsAddInfor() {
        setColId("id");
        setColName("goodsId");
        setUniqueColumn(new String[]{"goodsId", "code"});
    }

    public GoodsAddInfor(Long id) {
        this.id = id;
    }

    public GoodsAddInfor(Long id, Long goodsId, String code, String name) {
        this.id = id;
        this.goodsId = goodsId;
        this.code = code;
        this.name = name;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "GOODS_ADD_INFOR_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "GOODS_ID", nullable = false)
    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(final Long goodsId) {
        this.goodsId = goodsId;
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

    @Override
    public GoodsAddInforDTO toDTO() {
        GoodsAddInforDTO dto = new GoodsAddInforDTO(
                id == null ? null : id.toString(), goodsId == null ? null : goodsId.toString(), code, name
        );
        return dto;
    }
}
