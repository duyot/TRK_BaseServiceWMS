/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.google.common.collect.Lists;
import com.viettel.logistic.wms.dto.GoodsHandoverDTO;
import com.viettel.logistic.wms.dto.GoodsHandoverSerialDTO;
import com.viettel.logistic.wms.dto.HistoryFluctuationsDTO;
import com.viettel.logistic.wms.dto.ProposedReceiptDTO;
import com.viettel.logistic.wms.model.ProposedReceipt;
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
import com.viettel.vfw5.base.utils.DataUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hongdq4
 * @version 1.0
 * @since 8/22/2015 2:39 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.GoodsHandoverService")
public class GoodsHandoverServiceImpl implements GoodsHandoverService {

    @Autowired
    BaseFWServiceInterface goodsHandoverBusiness;
    @Autowired
    BaseFWServiceInterface goodsHandoverSerialBusiness;
    @Autowired
    BaseFWServiceInterface proposedReceiptBusiness;
    @Autowired
    BaseFWServiceInterface historyFluctuationsBusiness;
    @Autowired
    HibernateSessionFactoryImpl sessionFactory;

    @Override
    public String updateGoodsHandover(GoodsHandoverDTO goodsHandoverDTO) {
        return goodsHandoverBusiness.update(goodsHandoverDTO);
    }

    @Override
    public String deleteGoodsHandover(Long id) {
        return goodsHandoverBusiness.delete(id);
    }

    @Override
    public String deleteListGoodsHandover(List<GoodsHandoverDTO> goodsHandoverListDTO) {
        return goodsHandoverBusiness.delete(goodsHandoverListDTO);
    }

    @Override
    public GoodsHandoverDTO findGoodsHandoverById(Long id) {
        if (id != null && id > 0) {
            return (GoodsHandoverDTO) goodsHandoverBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<GoodsHandoverDTO> getListGoodsHandoverDTO(GoodsHandoverDTO goodsHandoverDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (goodsHandoverDTO != null) {
            return goodsHandoverBusiness.search(goodsHandoverDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertGoodsHandover(GoodsHandoverDTO goodsHandoverDTO) {
        return goodsHandoverBusiness.createObject(goodsHandoverDTO);
    }

    //QuyenDM 25/08/2015 Them hang hoa de nghi nhap
    @Override
    @Transactional
    public String insertOrUpdateListGoodsHandover(List<GoodsHandoverDTO> goodsHandoverDTO) {
        String goodsHandoverId;
        List<GoodsHandoverSerialDTO> lstGoodsHandoverSerialDTOs;
        ResultDTO resultDTO;
        String updateResult;
        for (GoodsHandoverDTO ghdto : goodsHandoverDTO) {
            lstGoodsHandoverSerialDTOs = ghdto.getLstGoodsHandoverSerialDTOs();
            if (ghdto.getGoodsHandoverId() == null) {
                resultDTO = goodsHandoverBusiness.createObject(ghdto);
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    return ParamUtils.FAIL;
                }
                goodsHandoverId = resultDTO.getId();
                for (GoodsHandoverSerialDTO ghsdto : lstGoodsHandoverSerialDTOs) {
                    ghsdto.setGoodsHandoverId(goodsHandoverId);
                }
            } else {
                updateResult = goodsHandoverBusiness.update(ghdto);
                if (!updateResult.equals(ParamUtils.SUCCESS)) {
                    return ParamUtils.FAIL;
                }
            }
            //Sau khi insert hoac update xong thi thuc hien insert chi tiet serial
            if (lstGoodsHandoverSerialDTOs != null && !lstGoodsHandoverSerialDTOs.isEmpty()) {
                updateResult = goodsHandoverSerialBusiness.insertList(lstGoodsHandoverSerialDTOs);
                if (!updateResult.equals(ParamUtils.SUCCESS)) {
                    return ParamUtils.FAIL;
                }
            }
        }
        return ParamUtils.SUCCESS;
    }

    @Override
    public List<String> getSequenseGoodsHandover(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return goodsHandoverBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<GoodsHandoverDTO> getListGoodsHandoverByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<GoodsHandoverDTO> lstGoodsHandover = new ArrayList<>();
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
        lstGoodsHandover = goodsHandoverBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstGoodsHandover;
    }

    @Override
    public ResultDTO insertOrUpdateProposedReceipt(ProposedReceiptDTO proposedReceiptDTO, List<GoodsHandoverDTO> goodsHandoverDTO) {
        String proposedReceiptId;
        String goodsHandoverId;
        List<GoodsHandoverSerialDTO> lstGoodsHandoverSerialDTOs;
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        String updateResult;
        Session session;
        Transaction transaction;

        session = sessionFactory.openSession();
        transaction = session.getTransaction();
        transaction.begin();

        try {
            //Neu Id = null --> them moi
            if (DataUtil.isStringNullOrEmpty(proposedReceiptDTO.getProposedReceiptId())) {
                resultDTO = historyFluctuationsBusiness.createObjectSession(proposedReceiptDTO, session);
                //Them doi tuong phieu de nghi
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback(session, transaction);
                    return resultDTO;
                }
                proposedReceiptId = resultDTO.getId();
            } else {//Neu khong thi thuc hien cap nhat
                updateResult = historyFluctuationsBusiness.updateSession(proposedReceiptDTO, session);
                if (!updateResult.equals(ParamUtils.SUCCESS)) {
                    resultDTO.setMessage(updateResult);
                    rollback(session, transaction);
                    return resultDTO;
                }
                proposedReceiptId = proposedReceiptDTO.getProposedReceiptId();
                //Xoa danh sach hang hoa cu
                //Lay danh sach ung voi requestId = proposedReceiptId
                List<GoodsHandoverDTO> lstGoodsHandoverDTOs = goodsHandoverBusiness.search(new GoodsHandoverDTO(proposedReceiptId, "1"), 0, Integer.MAX_VALUE, "asc", "goodsHandoverId");
                List<GoodsHandoverSerialDTO> lstHandoverSerialDTOs;
                String deleteResult;
                if (!DataUtil.isListNullOrEmpty(lstGoodsHandoverDTOs)) {
                    for (GoodsHandoverDTO ghsdto : lstGoodsHandoverDTOs) {
                        lstHandoverSerialDTOs = goodsHandoverSerialBusiness.search(new GoodsHandoverSerialDTO(ghsdto.getGoodsHandoverId()), 0, Integer.MAX_VALUE, "asc", "id");
                        if (!DataUtil.isListNullOrEmpty(lstHandoverSerialDTOs)) {
                            deleteResult = goodsHandoverSerialBusiness.delete(lstHandoverSerialDTOs);
                            if (!deleteResult.equals(ParamUtils.SUCCESS)) {
                                resultDTO.setMessage(deleteResult);
                                rollback(session, transaction);
                                return resultDTO;
                            }
                        }
                    }
                    deleteResult = goodsHandoverBusiness.delete(lstGoodsHandoverDTOs);
                    if (!deleteResult.equals(ParamUtils.SUCCESS)) {
                        resultDTO.setMessage(deleteResult);
                        rollback(session, transaction);
                        return resultDTO;
                    }
                }
            }
            //Sau khi them thanh cong thi thuc hien set du lieu cho tung hang hoa
            for (GoodsHandoverDTO ghdto : goodsHandoverDTO) {
                ghdto.setGoodsHandoverId(null);
                ghdto.setRequestId(proposedReceiptId);
                ghdto.setType("1");
                resultDTO = goodsHandoverBusiness.createObjectSession(ghdto, session);
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback(session, transaction);
                    return resultDTO;
                }
                goodsHandoverId = resultDTO.getId();
                //Sau khi insert hoac update xong thi thuc hien insert chi tiet serial
                lstGoodsHandoverSerialDTOs = ghdto.getLstGoodsHandoverSerialDTOs();
                if (!DataUtil.isListNullOrEmpty(lstGoodsHandoverSerialDTOs)) {
                    for (GoodsHandoverSerialDTO ghsdto : lstGoodsHandoverSerialDTOs) {
                        ghsdto.setGoodsHandoverId(goodsHandoverId);
                    }
                    updateResult = goodsHandoverSerialBusiness.insertListSession(lstGoodsHandoverSerialDTOs, session);
                    if (!updateResult.equals(ParamUtils.SUCCESS)) {
                        resultDTO.setMessage(updateResult);
                        rollback(session, transaction);
                        return resultDTO;
                    }
                }
            }
            commit(session, transaction);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(GoodsHandoverServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setMessage(e.getMessage());
            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
            return resultDTO;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        resultDTO.setId(proposedReceiptId);
        resultDTO.setMessage(ParamUtils.SUCCESS);
        return resultDTO;
    }

    @Override
    public ResultDTO insertOrUpdateHistoryFluctuations(HistoryFluctuationsDTO historyFluctuationsDTO, List<GoodsHandoverDTO> goodsHandoverDTO) {
        String historyFluctuationsId;
        String proposedReceiptId = "";
        String goodsHandoverId;
        List<GoodsHandoverSerialDTO> lstGoodsHandoverSerialDTOs;
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(ParamUtils.SUCCESS);
        String updateResult = "";
        Session session;
        Transaction transaction;

        session = sessionFactory.openSession();
        transaction = session.getTransaction();

        transaction.begin();
        try {
            proposedReceiptId = historyFluctuationsDTO.getProposedReceiptId();
            //Neu Id = null --> them moi
            if (DataUtil.isStringNullOrEmpty(historyFluctuationsDTO.getHistoryFluctuationsId())) {
                resultDTO = historyFluctuationsBusiness.createObjectSession(historyFluctuationsDTO, session);
                //Them doi tuong phieu de nghi
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback(session, transaction);
                    return resultDTO;
                }
                historyFluctuationsId = resultDTO.getId();
            } else {//Neu khong thi thuc hien cap nhat
                historyFluctuationsId = historyFluctuationsDTO.getHistoryFluctuationsId();
                updateResult = historyFluctuationsBusiness.updateSession(historyFluctuationsDTO, session);
                if (!updateResult.equals(ParamUtils.SUCCESS)) {
                    rollback(session, transaction);
                    return resultDTO;
                }

                //Xoa danh sach hang hoa cu
                //Lay danh sach ung voi requestId = proposedReceiptId
                GoodsHandoverDTO dTO = new GoodsHandoverDTO(historyFluctuationsId, "2");
                List<GoodsHandoverDTO> lstGoodsHandoverDTOs = goodsHandoverBusiness.searchSession(dTO, 0, Integer.MAX_VALUE, "asc", "goodsHandoverId", session);
                List<GoodsHandoverSerialDTO> lstHandoverSerialDTOs;
                String deleteResult;
                if (!DataUtil.isListNullOrEmpty(lstGoodsHandoverDTOs)) {
                    GoodsHandoverSerialDTO handoverSerialDTO;
                    for (GoodsHandoverDTO ghsdto : lstGoodsHandoverDTOs) {
                        handoverSerialDTO = new GoodsHandoverSerialDTO(ghsdto.getGoodsHandoverId());
                        lstHandoverSerialDTOs = goodsHandoverSerialBusiness.searchSession(handoverSerialDTO, 0, Integer.MAX_VALUE, "asc", "id", session);
                        if (!DataUtil.isListNullOrEmpty(lstHandoverSerialDTOs)) {
                            deleteResult = goodsHandoverSerialBusiness.deleteListSession(lstHandoverSerialDTOs, session);
                            if (!deleteResult.equals(ParamUtils.SUCCESS)) {
                                resultDTO.setMessage(deleteResult);
                                rollback(session, transaction);
                                return resultDTO;
                            }
                        }
                    }
                    deleteResult = goodsHandoverBusiness.deleteListSession(lstGoodsHandoverDTOs, session);
                    if (!deleteResult.equals(ParamUtils.SUCCESS)) {
                        resultDTO.setMessage(deleteResult);
                        rollback(session, transaction);
                        return resultDTO;
                    }
                }

            }
            //cap nhat historyId cho Phieu tam nhap 
            ProposedReceiptDTO receiptDTO = new ProposedReceiptDTO();
            receiptDTO.setProposedReceiptId(proposedReceiptId);
            ProposedReceiptDTO proposedReceiptDTO = (ProposedReceiptDTO) proposedReceiptBusiness.findByIdSession(Long.valueOf(proposedReceiptId), session).toDTO();
//            ProposedReceiptDTO proposedReceiptDTO = lstProposedReceiptDTO.get(0);
            proposedReceiptDTO.setRecentHistoryId(historyFluctuationsId);
            ProposedReceiptDTO proposedReceiptDTOSave = (ProposedReceiptDTO) DataUtil.cloneObject(proposedReceiptDTO);
            List<ProposedReceiptDTO> lstProposedReceiptDTO = Lists.newArrayList();
            lstProposedReceiptDTO.add(proposedReceiptDTOSave);
            updateResult = proposedReceiptBusiness.insertListSession(lstProposedReceiptDTO, session);
            //Them doi tuong phieu de nghi
            if (!updateResult.equals(ParamUtils.SUCCESS)) {
                rollback(session, transaction);
                return resultDTO;
            }
            //Sau khi them thanh cong thi thuc hien set du lieu cho tung hang hoa
            for (GoodsHandoverDTO ghdto : goodsHandoverDTO) {
                ghdto.setGoodsHandoverId(null);
                ghdto.setRequestId(historyFluctuationsId);
                ghdto.setType("2");
                lstGoodsHandoverSerialDTOs = ghdto.getLstGoodsHandoverSerialDTOs();
                resultDTO = goodsHandoverBusiness.createObjectSession(ghdto, session);
                if (!resultDTO.getMessage().equals(ParamUtils.SUCCESS)) {
                    rollback(session, transaction);
                    return resultDTO;
                }
                goodsHandoverId = resultDTO.getId();
                for (GoodsHandoverSerialDTO ghsdto : lstGoodsHandoverSerialDTOs) {
                    ghsdto.setId(null);
                    ghsdto.setGoodsHandoverId(goodsHandoverId);
                }
                //Sau khi insert hoac update xong thi thuc hien insert chi tiet serial
                if (lstGoodsHandoverSerialDTOs != null && !lstGoodsHandoverSerialDTOs.isEmpty()) {
                    updateResult = goodsHandoverSerialBusiness.insertListSession(lstGoodsHandoverSerialDTOs, session);
                    if (!updateResult.equals(ParamUtils.SUCCESS)) {
                        rollback(session, transaction);
                        resultDTO.setMessage(updateResult);
                        return resultDTO;
                    }
                }

            }
            commit(session, transaction);
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(GoodsHandoverServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            resultDTO.setMessage(e.getMessage());
            resultDTO.setKey(ParamUtils.SYSTEM_OR_DATA_ERROR);
            rollback(session, transaction);
            return resultDTO;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        resultDTO.setId(historyFluctuationsId);
        resultDTO.setMessage(ParamUtils.SUCCESS);
        return resultDTO;
        //
    }

    private void rollback(Session session, Transaction transaction) {
        transaction.rollback();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    private void commit(Session session, Transaction transaction) {
        transaction.commit();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
