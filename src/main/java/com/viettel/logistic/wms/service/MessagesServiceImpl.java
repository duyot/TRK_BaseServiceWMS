/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.business.service.MessagesBusinessInterface;
import com.viettel.logistic.wms.dto.MessagesDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 9:36 AM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.MessagesService")
public class MessagesServiceImpl implements MessagesService {

    @Autowired
    BaseFWServiceInterface messagesBusiness;
    @Autowired
    MessagesBusinessInterface messagesBusinessInterface;

    @Override
    public String updateMessages(MessagesDTO messagesDTO) {
        return messagesBusiness.update(messagesDTO);
    }

    @Override
    public String deleteMessages(Long id) {
        return messagesBusiness.delete(id);
    }

    @Override
    public String deleteListMessages(List<MessagesDTO> messagesListDTO) {
        return messagesBusiness.delete(messagesListDTO);
    }

    @Override
    public MessagesDTO findMessagesById(Long id) {
        if (id != null && id > 0) {
            return (MessagesDTO) messagesBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<MessagesDTO> getListMessagesDTO(MessagesDTO messagesDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (messagesDTO != null) {
            return messagesBusiness.search(messagesDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertMessages(MessagesDTO messagesDTO) {
        return messagesBusiness.createObject(messagesDTO);
    }

    @Override
    public String insertOrUpdateListMessages(List<MessagesDTO> messagesListDTO) {
        return messagesBusiness.insertList(messagesListDTO);
    }

    @Override
    public List<String> getSequense(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return messagesBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<MessagesDTO> getListMessagesByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<MessagesDTO> lstMessages = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER_DOUBLE)) {
                con.setType(ParamUtils.NUMBER_DOUBLE);
            } else {
                String value = "";
                if (con.getOperator().equalsIgnoreCase(ParamUtils.NAME_LIKE)) {
                    value = StringUtils.formatLike(con.getValue());
                    con.setValue(value.toLowerCase());
                    con.setField(StringUtils.formatFunction("lower", con.getField()));
                } else {
                    value = con.getValue();
                    con.setValue(value);
                    con.setField(con.getField());
                }
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));
        }
        lstMessages = messagesBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstMessages;
    }

}
