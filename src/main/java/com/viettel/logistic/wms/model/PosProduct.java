/**
 * @(#)PosProductBO.java 08-Apr-15 2:45 PM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.PosProductDTO;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:45 PM
 */
@Entity
@Table(name = "POS_PRODUCT")
public class PosProduct extends BaseFWModel {

    //Fields
    private Long id;
    private Long max;
    private Long positionId;
    private Long goodsId;
    private Long custId;

    //Constructors
    public PosProduct() {
        setColId("id");
        setColName("positionId");
        setUniqueColumn(new String[]{"id"});
    }

    public PosProduct(Long id) {
        this.id = id;
    }

    public PosProduct(Long id, Long max, Long positionId, Long goodsId, Long custId) {
        this.id = id;
        this.max = max;
        this.positionId = positionId;
        this.goodsId = goodsId;
        this.custId = custId;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "POS_PRODUCT_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "MAX")
    public Long getMax() {
        return this.max;
    }

    public void setMax(final Long max) {
        this.max = max;
    }

    @Column(name = "POSITION_ID", nullable = false)
    public Long getPositionId() {
        return this.positionId;
    }

    public void setPositionId(final Long positionId) {
        this.positionId = positionId;
    }

    @Column(name = "GOODS_ID", nullable = false)
    public Long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(final Long goodsId) {
        this.goodsId = goodsId;
    }

    @Column(name = "CUST_ID", nullable = false)
    public Long getCustId() {
        return this.custId;
    }

    public void setCustId(final Long custId) {
        this.custId = custId;
    }

    @Override
    public PosProductDTO toDTO() {
        PosProductDTO dto = new PosProductDTO(
                id == null ? null : id.toString(), max == null ? null : max.toString(), positionId == null ? null : positionId.toString(), goodsId == null ? null : goodsId.toString(), custId == null ? null : custId.toString()
        );
        return dto;
    }
}
