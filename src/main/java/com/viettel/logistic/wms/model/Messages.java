/**
 * @(#)CellsBO.java 08-Apr-15 9:36 AM, Copyright 2011 Viettel Telecom. All
 * rights reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.model;

import com.viettel.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.viettel.logistic.wms.dto.MessagesDTO;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.ParamUtils;
import java.util.Date;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@Entity
@Table(name = "MESSAGES")
public class Messages extends BaseFWModel {

    //Fields
    private Long id;
    private String email;
    private String messages;
    private Date createDateTime;
    private String status;
    private Long resent;
    private Date sentDateTime;
    private String description;
    private String subject;

    //Constructors
    public Messages() {
        setColId("id");
        setColName("id");
        setUniqueColumn(new String[]{"id"});
    }

    public Messages(Long id) {
        this.id = id;
    }

    public Messages(Long id, String email, String messages, Date createDateTime, String status, Long resent, Date sentDateTime, String description, String subject) {
        this.id = id;
        this.email = email;
        this.messages = messages;
        this.createDateTime = createDateTime;
        this.status = status;
        this.resent = resent;
        this.sentDateTime = sentDateTime;
        this.description = description;
        this.subject = subject;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "MESSAGES_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "EMAIL", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "MESSAGES", nullable = false)
    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    @Column(name = "CREATE_DATETIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "RESENT")
    public Long getResent() {
        return resent;
    }

    public void setResent(Long resent) {
        this.resent = resent;
    }

    @Column(name = "SENT_DATETIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(Date sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "SUBJECT", nullable = false)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public MessagesDTO toDTO() {
        MessagesDTO dto = new MessagesDTO(
                id == null ? null : id.toString(), email, messages,
                createDateTime == null ? null : DateTimeUtils.convertDateToString(createDateTime, ParamUtils.ddMMyyyyHHmmss),
                status, resent == null ? null : resent.toString(),
                sentDateTime == null ? null : DateTimeUtils.convertDateToString(sentDateTime, ParamUtils.ddMMyyyyHHmmss),
                description, subject
        );
        return dto;
    }
}
