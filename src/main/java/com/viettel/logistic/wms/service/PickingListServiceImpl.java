/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.business.service.PickingListGoodsBusinessInterface;
import com.viettel.logistic.wms.dto.OrderActionDTO;
import com.viettel.logistic.wms.dto.PickingListDTO;
import com.viettel.logistic.wms.dto.PickingListGoodsDTO;
import com.viettel.logistic.wms.webservice.WSOrderAction;
import com.viettel.vfw5.base.dao.HibernateSessionFactoryImpl;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import javax.jws.WebService;
import java.util.ArrayList;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.utils.Constants;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 08-May-15 4:02 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.PickingListService")
public class PickingListServiceImpl implements PickingListService {

    @Autowired
    BaseFWServiceInterface pickingListBusiness;
    @Autowired
    BaseFWServiceInterface pickingListGoodsBusiness;
    @Autowired
    PickingListGoodsBusinessInterface pickingListGoodsBusinessInterface;
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;
    Session session;
    //
    String formatDate = "dd/mm/yyyy hh24:mi:ss";
    String sysdate = "";
    //Danh sach cac lenh xuat kho
    public static List<String> lstCurrentProcessOrderActionExportId = new ArrayList<>();

    @Override
    public String updatePickingList(PickingListDTO pickingListDTO) {
        return pickingListBusiness.update(pickingListDTO);
    }

    @Override
    public String deletePickingList(Long id) {
        return pickingListBusiness.delete(id);
    }

    @Override
    public String deleteListPickingList(List<PickingListDTO> pickingListListDTO) {
        return pickingListBusiness.delete(pickingListListDTO);
    }

    @Override
    public PickingListDTO findPickingListById(Long id) {
        if (id != null && id > 0) {
            return (PickingListDTO) pickingListBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<PickingListDTO> getListPickingListDTO(PickingListDTO pickingListDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (pickingListDTO != null) {
            return pickingListBusiness.search(pickingListDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertPickingList(PickingListDTO pickingListDTO) {
        return pickingListBusiness.createObject(pickingListDTO);
    }

    @Override
    public String insertOrUpdateListPickingList(List<PickingListDTO> pickingListDTO) {
        return pickingListBusiness.insertList(pickingListDTO);
    }

    @Override
    public List<String> getSequensePickingList(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return pickingListBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<PickingListDTO> getListPickingListByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<PickingListDTO> lstPickingList = new ArrayList<>();
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

        lstPickingList = pickingListBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstPickingList;
    }

    //
    //ChuDV: Tao sanh sach Picking list
    @Override
    @Transactional
    public ResultDTO insertListPickingList(List<PickingListDTO> lstPickingListDTO, List<PickingListGoodsDTO> lstPickingListGoodsDTO) {
        ResultDTO resultDTO = new ResultDTO();
        String pickingListId;
        String orderActionID = lstPickingListDTO.get(0).getOrderActionIdList();
        session = sessionFactory.getSession();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        sysdate = pickingListBusiness.getSysDate(formatDate);
        List<PickingListGoodsDTO> lstFilterPickingListGoodsDTO;
        String cellCodeList = "";
        //     
        try {
            //Kiem tra xem lieu yeu cau co dang duoc xu ly khong
            lstCurrentProcessOrderActionExportId.clear();
            if (lstCurrentProcessOrderActionExportId.contains(orderActionID)) {
                resultDTO.setKey(ParamUtils.ORDER_ACTION_IS_PROCESSING);
                resultDTO.setMessage(ParamUtils.ORDER_ACTION_IS_PROCESSING);
                return resultDTO;
            }
            lstCurrentProcessOrderActionExportId.add(orderActionID);//Neu kho co trong danh sach thi them vao sanh sach
            for (PickingListDTO pickingListDTO : lstPickingListDTO) {
                //Tao picking List
                pickingListDTO.setCreatedDate(sysdate);
                resultDTO = pickingListBusiness.createObjectSession(pickingListDTO, session);
                pickingListId = resultDTO.getId();
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback();
                    return resultDTO;
                }
                //Tao chi tiet hang hoa theo Picking list
                lstFilterPickingListGoodsDTO = filterPickingListGoodsDTO(pickingListDTO.getTmpPickingListId(), lstPickingListGoodsDTO);
                for (PickingListGoodsDTO pickingListGoodsDTO : lstFilterPickingListGoodsDTO) {
                    pickingListGoodsDTO.setPickingListId(pickingListId);
                    pickingListGoodsDTO.setCreatedDate(sysdate);
                    //
                    cellCodeList = pickingListGoodsBusinessInterface.getListCellCode(pickingListDTO, pickingListGoodsDTO);
                    pickingListGoodsDTO.setCellCodeList(cellCodeList);
                    resultDTO = pickingListGoodsBusiness.createObjectSession(pickingListGoodsDTO, session);
                }
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback();
                    return resultDTO;
                }
            }
            OrderActionDTO actionDTO = WSOrderAction.findOrderActionById(orderActionID);
            actionDTO.setStatus(Constants.ORDER_ACTION_PLAN);
            String messUpdateOrderAction = WSOrderAction.updateOrderAction(actionDTO);
            if (!ParamUtils.SUCCESS.contains(messUpdateOrderAction)) {
                rollback();
                resultDTO.setMessage(ParamUtils.FAIL);
                return resultDTO;
            }
        } catch (Exception e) {
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            resultDTO.setMessage(ParamUtils.FAIL);
            return resultDTO;
        } finally {
            try {
                lstCurrentProcessOrderActionExportId.remove(orderActionID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //
        return resultDTO;
    }

    @Override
    public ResultDTO canceledListPickingList(OrderActionDTO actionDTO) {
        ResultDTO resultDTO = new ResultDTO();
        List<PickingListDTO> lstPickingListDTO = new ArrayList();
        String orderActionId = actionDTO.getId();
        resultDTO.setMessage(ParamUtils.SUCCESS);

        Session session;
        Transaction transaction;
        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();

        try {
            PickingListDTO pickingListDTO = new PickingListDTO();
            pickingListDTO.setOrderActionIdList(orderActionId);
            lstPickingListDTO = pickingListBusiness.search(pickingListDTO, 0, Integer.MAX_VALUE, "", "id");
            //Kiem tra xem lieu yeu cau co dang duoc xu ly khong
            for (PickingListDTO pickingList : lstPickingListDTO) {
                pickingList.setStatus("0");
                String updateResult = pickingListBusiness.updateSession(pickingList, session);
                //
                if (!ParamUtils.SUCCESS.equals(updateResult)) {
                    transaction.rollback();
                    if (session != null && session.isOpen()) {
                        session.close();
                    }
                    return resultDTO;
                }
            }

            transaction.commit();
            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (Exception e) {
            resultDTO.setKey(ParamUtils.SYSTEM_ERROR);
            resultDTO.setMessage(ParamUtils.FAIL);
            return resultDTO;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        //
        return resultDTO;
    }

    //
    private List<PickingListGoodsDTO> filterPickingListGoodsDTO(String tmpPickingListId, List<PickingListGoodsDTO> lstPickingListGoodsDTO) {
        List<PickingListGoodsDTO> returnListPickingListGoodsDTO = new ArrayList();
        for (PickingListGoodsDTO pickingListGoodsDTO : lstPickingListGoodsDTO) {
            if (pickingListGoodsDTO.getTmpPickingListId().equals(tmpPickingListId)) {
                returnListPickingListGoodsDTO.add(pickingListGoodsDTO);
            }
        }
        return returnListPickingListGoodsDTO;
    }

    //
    private void rollback() throws Exception {
        throw new java.lang.Exception();
    }
}
