/**
 * @(#)CellsForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.viettel.logistic.wms.dto;

import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.logistic.wms.model.Messages;
import com.viettel.vfw5.base.utils.DateTimeUtils;
import com.viettel.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@XmlRootElement(name = "Messages")
public class MessagesDTO extends BaseFWDTO<Messages> {

    //Fields
    //Fields
    private String id;
    private String email;
    private String messages;
    private String createDateTime;
    private String status;
    private String resent;
    private String sentDateTime;
    private String description;
    private String subject;
    private static long changedTime = 0;

    //Constructor
    public MessagesDTO() {
        setDefaultSortField("id");
    }

    public MessagesDTO(String id, String email, String messages, String createDateTime, String status, String resent, String sentDateTime, String description, String subject) {
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

    //Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResent() {
        return resent;
    }

    public void setResent(String resent) {
        this.resent = resent;
    }

    public String getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(String sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public Messages toModel() {
        try {
            Messages model = new Messages(
                    !StringUtils.validString(id) ? null
                            : Long.valueOf(id),
                    email,
                    messages,
                    !StringUtils.validString(createDateTime) ? null
                            : DateTimeUtils.convertStringToDate(createDateTime),
                    status,
                    !StringUtils.validString(resent) ? null
                            : Long.valueOf(resent),
                    !StringUtils.validString(sentDateTime) ? null
                            : DateTimeUtils.convertStringToDate(sentDateTime),
                    description,
                    subject);
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
        return getId().toString();
    }

    @Override
    public long getChangedTime() {
        return MessagesDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        MessagesDTO.changedTime = changedTime;
    }

}
