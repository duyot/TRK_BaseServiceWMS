/**
 * @(#)GoodsSetBO.java 08-Apr-15 2:43 PM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.GoodsSetDTO;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:43 PM
 */
@Entity
@Table(name = "GOODS_SET")
public class GoodsSet extends BaseFWModel {

    //Fields
    private Long id;
    private Long masterId;
    private Long detailId;
    private Long quantity;
    private String status;

    //Constructors
    public GoodsSet() {
        setColId("id");
        setColName("masterId");
        setUniqueColumn(new String[]{"id"});
    }

    public GoodsSet(Long id) {
        this.id = id;
    }

    public GoodsSet(Long id, Long masterId, Long detailId, Long quantity, String status) {
        this.id = id;
        this.masterId = masterId;
        this.detailId = detailId;
        this.quantity = quantity;
        this.status = status;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "GOODS_SET_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "MASTER_ID", nullable = false)
    public Long getMasterId() {
        return this.masterId;
    }

    public void setMasterId(final Long masterId) {
        this.masterId = masterId;
    }

    @Column(name = "DETAIL_ID", nullable = false)
    public Long getDetailId() {
        return this.detailId;
    }

    public void setDetailId(final Long detailId) {
        this.detailId = detailId;
    }

    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public GoodsSetDTO toDTO() {
        GoodsSetDTO dto = new GoodsSetDTO(
                id == null ? null : id.toString(), masterId == null ? null : masterId.toString(), detailId == null ? null : detailId.toString(), quantity == null ? null : quantity.toString(), status
        );
        return dto;
    }
}
