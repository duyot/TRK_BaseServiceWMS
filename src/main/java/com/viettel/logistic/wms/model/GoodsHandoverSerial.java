/**
 * @(#)GoodsHandoverSerialBO.java 8/22/2015 2:41 PM, Copyright 2011 Viettel
 * Telecom. All rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.GoodsHandoverSerialDTO;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:41 PM
 */
@Entity
@Table(name = "GOODS_HANDOVER_SERIAL")
public class GoodsHandoverSerial extends BaseFWModel {

    //Fields
    private Long id;
    private String cellCode;
    private Long goodsHandoverId;
    private Long quantity;
    private String barcode;
    private String fromSerial;
    private String toSerial;
    private String note;

    //Constructors
    public GoodsHandoverSerial() {
        setColId("id");
        setColName("id");
        setUniqueColumn(new String[]{"id"});
    }

    public GoodsHandoverSerial(Long id) {
        this.id = id;
    }

    public GoodsHandoverSerial(Long id, String cellCode, Long goodsHandoverId, Long quantity, String barcode, String fromSerial, String toSerial, String note) {
        this.id = id;
        this.cellCode = cellCode;
        this.goodsHandoverId = goodsHandoverId;
        this.quantity = quantity;
        this.barcode = barcode;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.note = note;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "GOODS_HANDOVER_SERIAL_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "CELL_CODE")
    public String getCellCode() {
        return this.cellCode;
    }

    public void setCellCode(final String cellCode) {
        this.cellCode = cellCode;
    }

    @Column(name = "GOODS_HANDOVER_ID", nullable = false, columnDefinition = "GoodsHandover")
    public Long getGoodsHandoverId() {
        return this.goodsHandoverId;
    }

    public void setGoodsHandoverId(final Long goodsHandoverId) {
        this.goodsHandoverId = goodsHandoverId;
    }

    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "BARCODE")
    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(final String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "FROM_SERIAL")
    public String getFromSerial() {
        return this.fromSerial;
    }

    public void setFromSerial(final String fromSerial) {
        this.fromSerial = fromSerial;
    }

    @Column(name = "TO_SERIAL")
    public String getToSerial() {
        return this.toSerial;
    }

    public void setToSerial(final String toSerial) {
        this.toSerial = toSerial;
    }

    @Column(name = "NOTE")
    public String getNote() {
        return this.note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Override
    public GoodsHandoverSerialDTO toDTO() {
        GoodsHandoverSerialDTO dto = new GoodsHandoverSerialDTO(
                id == null ? null : id.toString(), cellCode, goodsHandoverId == null ? null : goodsHandoverId.toString(), quantity == null ? null : quantity.toString(), barcode, fromSerial, toSerial, note
        );
        return dto;
    }
}
