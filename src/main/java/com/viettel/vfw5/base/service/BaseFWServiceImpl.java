/*
 * Copyright (C) 2015 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vfw5.base.service;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.model.BaseFWModel;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.DataUtil;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.ReflectUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author kdvt_binhnt22@viettel.com.vn
 * @version 2.0
 * @since Mar 2015
 */
@Transactional
public class BaseFWServiceImpl<TDAO extends BaseFWDAOImpl, TDTO extends BaseFWDTO, TModel extends BaseFWModel>
        implements BaseFWServiceInterface<TDTO, TModel> {

    protected Session session;
    protected TDAO tDAO;
    protected TModel tModel;
    protected Locale locale;

    public BaseFWServiceImpl() {
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public TModel gettModel() {
        return tModel;
    }

    public void settModel(TModel tModel) {
        this.tModel = tModel;
    }

    public TDAO gettDAO() {
        return tDAO;
    }

    public void settDAO(TDAO tDAO) {
        this.tDAO = tDAO;
    }

    @Override
    public List convertListModeltoDTO(List<TModel> listModel) {
        List<BaseFWDTO> lstForm = new ArrayList<BaseFWDTO>();
        if (listModel != null) {
            for (TModel model : listModel) {
                lstForm.add(model.toDTO());
            }
        }

        return lstForm;
    }

    @Override
    public List convertListDTOtoModel(List<TDTO> listDTO) {

        List<BaseFWModel> lstModel = new ArrayList<BaseFWModel>();
        if (listDTO != null) {
            for (TDTO dto : listDTO) {
                lstModel.add(dto.toModel());
            }
        }
        return lstModel;
    }

    @Override
    public long count(TDTO tForm) {
        return gettDAO().count(tModel.getModelName(), prepareCondition(tForm));
    }

    @Override
    public String delete(Long id) {
        List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
        lstCondition.add(new ConditionBean(
                tModel.getColId(),
                ParamUtils.OP_EQUAL,
                String.valueOf(id),
                ParamUtils.TYPE_NUMBER));

        return gettDAO().delete(tModel.getModelName(), lstCondition);
    }

//    public String delete(Long id, Long transId) {
//        List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
//        lstCondition.add(new ConditionBean(
//                tModel.getColId(),
//                ParamUtils.OP_EQUAL,
//                String.valueOf(id),
//                ParamUtils.TYPE_NUMBER));
//
//        return tDAO.delete(tModel.getModelName(), lstCondition, transId);
//    }
    @Override
    public String delete(List<TDTO> tFormOnGrid) {

        List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
        for (TDTO tForm : tFormOnGrid) {
            lstCondition.add(new ConditionBean(
                    tModel.getColId(),
                    ParamUtils.OP_EQUAL,
                    String.valueOf(tForm.getFWModelId()),
                    ParamUtils.TYPE_NUMBER));

        }
        int countSuccess = gettDAO().deleteList(tModel.getModelName(), lstCondition);
        String returnMessage;
        if (countSuccess == 0) {
            returnMessage = ParamUtils.FAIL;
        } else {
            returnMessage = ParamUtils.SUCCESS;
        }
        return returnMessage;
    }

    @Override
    public TModel findById(Long id) {
        List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
        lstCondition.add(new ConditionBean(
                tModel.getColId(),
                ParamUtils.OP_EQUAL,
                String.valueOf(id),
                ParamUtils.TYPE_NUMBER));
        List<TModel> lstResult = gettDAO().find(tModel.getModelName(), lstCondition);
        if (lstResult.isEmpty()) {
            return null;
        } else {
            return lstResult.get(0);
        }
    }

    @Override
    public TModel findByIdSession(Long id, Session session) {
        List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
        lstCondition.add(new ConditionBean(
                tModel.getColId(),
                ParamUtils.OP_EQUAL,
                String.valueOf(id),
                ParamUtils.TYPE_NUMBER));
        List<TModel> lstResult = gettDAO().find(tModel.getModelName(), lstCondition);
        if (lstResult.isEmpty()) {
            return null;
        } else {
            return lstResult.get(0);
        }
    }

    @Override
    public List getAll() {
        return convertListModeltoDTO(gettDAO().findAll(Order.asc(tModel.getColName())));
    }

    public List getAllForms() {
        return convertListModeltoDTO(gettDAO().findAll(Order.asc(tModel.getColName())));
    }

    @Override
    public boolean isDuplicate(Long id, String... name) {
        List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
        if (id != null) {
            lstCondition.add(new ConditionBean(
                    tModel.getColId(),
                    ParamUtils.OP_NOT_EQUAL,
                    String.valueOf(id),
                    ParamUtils.TYPE_NUMBER));
        }
        Field field = null;
        for (int i = 0; i < name.length; i++) {
            try {
                field = tModel.getClass().getDeclaredField(tModel.getUniqueColumn()[i]);
            } catch (Exception e) {
                System.out.println("ERROR: Wrong declared unique column " + tModel.getUniqueColumn()[i] + " in BO file " + tModel.getModelName());
            }
            String type = ParamUtils.TYPE_STRING;
            if (ParamUtils.TYPE_NUMBER.indexOf(field.getType().getSimpleName().toUpperCase()) >= 0) {
                type = ParamUtils.TYPE_NUMBER;
            }
            lstCondition.add(new ConditionBean(
                    tModel.getUniqueColumn()[i],
                    ParamUtils.OP_EQUAL,
                    name[i],
                    type));
        }
        return gettDAO().find(tModel.getModelName(), lstCondition).isEmpty() ? false : true;
    }

    @Override
    public boolean isDuplicate(String name, Long id) {
        List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
        if (id != null) {
            lstCondition.add(new ConditionBean(
                    tModel.getColId(),
                    ParamUtils.OP_NOT_EQUAL,
                    String.valueOf(id),
                    ParamUtils.TYPE_NUMBER));
        }
        lstCondition.add(new ConditionBean(
                tModel.getColName(),
                ParamUtils.OP_EQUAL,
                name,
                ParamUtils.TYPE_STRING));
        return gettDAO().find(tModel.getModelName(), lstCondition).isEmpty() ? false : true;
    }

    /*
     * @author: binhnt22@viettel.com.vn @version: 1.0 @since: May, 2012
     * @parameter: An interface Form @return: A list Condition made of form
     * attributes if attribute is null then ignored, else string will be
     * compared by "like", number by "=" date by ">=" or "<=" depend on
     * attribute name
     */
    /*
     neu kieu du lieu la number , date thi order by
     kieu du lieu la string thi order by tieng viet
     */
    private String buildOrder(TModel tForm, String sortType, String sortField) {
        String order = "";
        Map<String, String> lstHashMap = new HashMap<>();
        lstHashMap = buildHasMapOrder(sortType, sortField);
        if (tForm == null) {
            for (String item : lstHashMap.keySet()) {
                order = order + " , " + item + " " + lstHashMap.get(item);
            }

            return order.replaceFirst(",", "");
        }
        Method methodForms[] = tForm.getClass().getDeclaredMethods();

        for (String item : lstHashMap.keySet()) {
            try {
                Method methods = tForm.getClass().getMethod(DataUtil.getGetterOfColumn(item));

                String returnType = methods.getReturnType().getSimpleName().toUpperCase();
                if (ParamUtils.TYPE_STRING.indexOf(returnType) >= 0) {
                    order = order + " , " + StringUtils.formatOrder(item, lstHashMap.get(item));

                } else {
                    order = order + " , " + item + " " + lstHashMap.get(item);

                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(BaseFWServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(BaseFWServiceImpl.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        return order.replaceFirst(",", "");
    }

    private Map<String, String> buildHasMapOrder(String sortType, String sortField) {

        Map<String, String> lstHashMap = new HashMap<>();
        List<String> lstSortType = new ArrayList<>();
        List<String> lstSortField = new ArrayList<>();
        lstSortType = DataUtil.parseInputList(sortType);
        lstSortField = DataUtil.parseInputList(sortField);
        for (int i = 0; i < lstSortField.size(); i++) {

            if (i < lstSortType.size()) {
                lstHashMap.put(lstSortField.get(i), lstSortType.get(i));
            } else {
                lstHashMap.put(lstSortField.get(i), "ASC");
            }

        }
        return lstHashMap;
    }

    @Override
    public List prepareCondition(TDTO tForm) {
        List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
        if (tForm == null) {
            return lstCondition;
        }
        Method methods[] = tForm.toModel().getClass().getDeclaredMethods();
        Method methodForms[] = tForm.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (ReflectUtils.isGetter(methods[i])) {
                try {
                    Object value = methods[i].invoke(tForm.toModel());
                    String returnType = methods[i].getReturnType().getSimpleName().toUpperCase();
                    if (value != null && !value.equals("")) {
                        if (ParamUtils.TYPE_STRING.indexOf(returnType) >= 0) {
                            if (!value.equals(String.valueOf(ParamUtils.DEFAULT_VALUE))) {
                                String stringValue = value.toString();
                                String opCompare = ParamUtils.OP_LIKE;
                                String valueCompare = StringUtils.formatLike(stringValue);
                                Column column = methods[i].getAnnotation(Column.class);
                                if (StringUtils.validString(column.columnDefinition())
                                        && column.columnDefinition().equals("param")) {
                                    opCompare = ParamUtils.OP_EQUAL;
                                    valueCompare = stringValue.toLowerCase();
                                }
                                if (!stringValue.trim().equals("")) {
                                    lstCondition.add(new ConditionBean(
                                            StringUtils.formatFunction("lower", ReflectUtils.getColumnBeanName(methods[i])),
                                            opCompare,
                                            valueCompare,
                                            ParamUtils.TYPE_STRING));
                                }
                            }
                        } else if (ParamUtils.TYPE_NUMBER.indexOf(returnType) >= 0) {
                            if (!value.toString().equals(String.valueOf(ParamUtils.DEFAULT_VALUE))) {
                                lstCondition.add(new ConditionBean(
                                        ReflectUtils.getColumnBeanName(methods[i]),
                                        ParamUtils.OP_EQUAL,
                                        value.toString(),
                                        ParamUtils.TYPE_NUMBER));
                            }
                        } else if (ParamUtils.TYPE_DATE.indexOf(returnType) >= 0) {
                            Date dateValue = (Date) value;
                            String methodName = methods[i].getName();
                            String operator = ParamUtils.OP_EQUAL;
                            if (methodName.indexOf("From") >= 0
                                    || methodName.indexOf("Begin") >= 0
                                    || methodName.indexOf("Sta") >= 0) {
                                operator = ParamUtils.OP_GREATER_EQUAL;
                            } else if (methodName.indexOf("To") >= 0
                                    || methodName.indexOf("End") >= 0
                                    || methodName.indexOf("Last") >= 0) {
                                operator = ParamUtils.OP_LESS_EQUAL;
                            }
                            lstCondition.add(new ConditionBean(
                                    StringUtils.formatFunction("trunc", ReflectUtils.getColumnBeanName(methods[i])),
                                    operator,
                                    StringUtils.formatDate(dateValue),
                                    ParamUtils.TYPE_DATE));
                        }
                    } else if (ParamUtils.TYPE_NUMBER.indexOf(returnType) >= 0) {
                        try {
                            Column column = methods[i].getAnnotation(Column.class);
                            if (StringUtils.validString(column.columnDefinition())) {
                                String colId = "";
                                String colName = "";
                                for (int j = 0; j < methodForms.length; j++) {
                                    if (methodForms[j].getName().equals(methods[i].getName() + "Name")) {
                                        value = methodForms[j].invoke(tForm);
                                        if (!StringUtils.validString(value)) {
                                            break;
                                        }
                                        Class cl = Class.forName("com.viettel.ra.database.BO." + column.columnDefinition() + "BO");
                                        Constructor ct = cl.getConstructor();
                                        Object obj = ct.newInstance();
                                        Method mtd[] = cl.getMethods();
                                        for (int k = 0; k < mtd.length; k++) {
                                            if (mtd[k].getName().equals("getColId")) {
                                                colId = mtd[k].invoke(obj).toString();
                                            }
                                            if (mtd[k].getName().equals("getColName")) {
                                                colName = mtd[k].invoke(obj).toString();
                                            }
                                        }
                                        break;
                                    }
                                }
                                if (StringUtils.validString(value)) {
                                    lstCondition.add(new ConditionBean(
                                            ReflectUtils.getColumnBeanName(methods[i]),
                                            ParamUtils.OP_IN,
                                            BaseFWDAOImpl.genSubQuery(column.columnDefinition(), colId, colName, value.toString()),
                                            ParamUtils.TYPE_NUMBER));
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("PrepareCondition Error: " + e.getMessage());
                        }
                    }
                } catch (IllegalAccessException iae) {
                    System.out.println("PrepareCondition Error: " + iae.getMessage());
                } catch (InvocationTargetException ite) {
                    System.out.println("PrepareCondition Error: " + ite.getMessage());
                }
            }
        }
        return lstCondition;
    }

    @Override
    public List search(TDTO tForm, int start, int maxResult, String sortType, String sortField) {
        return convertListModeltoDTO(
                gettDAO().find(tModel.getModelName(),
                        prepareCondition(tForm),
                        buildOrder(tModel, sortType, sortField),
                        start, maxResult));
    }

    @Override
    public List<TDTO> searchSession(TDTO tDTO, int start, int maxResult, String sortType, String sortField, Session session) {
        return convertListModeltoDTO(
                gettDAO().findSession(tModel.getModelName(),
                        prepareCondition(tDTO),
                        buildOrder(tModel, sortType, sortField),
                        start, maxResult, null, session));
    }

    @Override
    public List<TDTO> searchByConditionBean(List<ConditionBean> lstConditionBean, int start, int maxResult, String sortType, String sortField) {
        return convertListModeltoDTO(
                gettDAO().find(tModel.getModelName(),
                        lstConditionBean,
                        buildOrder(tModel, sortType, sortField),
                        start, maxResult));
    }

    @Override
    public String update(TDTO tForm) {
        return gettDAO().update(tForm.toModel());
    }

    public Map<Long, String> getMap() {
        Map<Long, String> map = new HashMap<Long, String>();
        List<TDTO> listForm = convertListModeltoDTO(getAll());
        if (listForm != null && !listForm.isEmpty()) {
            for (TDTO form : listForm) {
                map.put(form.getFWModelId(), form.catchName());
            }
        }
        return map;
    }

    public Long getTransactionId() {
        return gettDAO().getTransactionId();
    }

    @Override
    public String insertList(List<TDTO> tForm) {
        List<TModel> model = convertListDTOtoModel(tForm);
        return gettDAO().saveList(model, tModel.getColId());

    }

    //NgocND6 (24/08/2015) -
    @Override
    public String updateListCondition(List<TDTO> tForm, int customerCode) {
        List<TModel> model = convertListDTOtoModel(tForm);
        return gettDAO().saveListUpdateCondition(model, tModel.getColId());
    }

    @Override
    public String create(TDTO tForm) {
        return gettDAO().save(tForm.toModel());
    }

    @Override
    public List<String> getListSequense(String seq, int size) {
        List<String> lstSequense = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            lstSequense.add(String.valueOf(gettDAO().getSequenId(seq)));
        }
        return lstSequense;
    }

    @Override
    public String getSequence(String seq) {
        return String.valueOf(gettDAO().getSequenId(seq));
    }

    @Override
    public String getSequenceSession(String seq, Session session) {
        return String.valueOf(gettDAO().getSequenId(seq, session));
    }

    @Override
    @Transactional
    public ResultDTO createObject(TDTO tForm) {
        String id = gettDAO().saveObject(tForm.toModel());
        ResultDTO result = new ResultDTO();
        try {
            Long.parseLong(id);
            result.setId(id);
            result.setMessage(ParamUtils.SUCCESS);
        } catch (NumberFormatException nfe) {
            result.setId(null);
            result.setMessage(ParamUtils.FAIL);
        }
        return result;
    }

    //Test thieu
    @Override
    @Transactional
    public ResultDTO createObjectSession(TDTO tForm, Session session) {
        String id = gettDAO().saveObjectSession(tForm.toModel(), session);
        ResultDTO result = new ResultDTO();
        try {
            Long.parseLong(id);
            result.setId(id);
            result.setMessage(ParamUtils.SUCCESS);
        } catch (NumberFormatException nfe) {
            result.setId(null);
            result.setMessage(ParamUtils.FAIL);
        }
        return result;
    }

    @Override
    public String updateSession(TDTO tForm, Session session) {
        return gettDAO().updateSession(tForm.toModel(), session);
    }
    //End test

//    @Override
//    public String insertListNoID(List<TDTO> tForm) {
//        List<TModel> model = convertListDTOtoModel(tForm);
//        return gettDAO().saveListNoId(model);
//
//    }
    @Override
    public String getSysDate(String pattern) {
        String result = "";
        try {
            result = gettDAO().getSysDate(pattern);
        } catch (Exception ex) {
            Logger.getLogger(BaseFWServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public String getSysDate(String pattern, Session session) {
        String result = "";
        try {
            result = gettDAO().getSysDate(pattern, session);
        } catch (Exception ex) {
            Logger.getLogger(BaseFWServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean isDuplicate(Long id, HashMap<String, String> hashmap) {
        List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
        if (id != null) {
            lstCondition.add(new ConditionBean(
                    tModel.getColId(),
                    ParamUtils.OP_NOT_EQUAL,
                    String.valueOf(id),
                    ParamUtils.TYPE_NUMBER));
        }
        Field field = null;
        for (int i = 0; i < hashmap.size(); i++) {
            try {
                field = tModel.getClass().getDeclaredField(tModel.getUniqueColumn()[i]);
            } catch (Exception e) {
                System.out.println("ERROR: Wrong declared unique column " + tModel.getUniqueColumn()[i] + " in BO file " + tModel.getModelName());
            }
            String type = ParamUtils.TYPE_STRING;
            if (ParamUtils.TYPE_NUMBER.indexOf(field.getType().getSimpleName().toUpperCase()) >= 0) {
                type = ParamUtils.TYPE_NUMBER;
            }
            lstCondition.add(new ConditionBean(
                    tModel.getUniqueColumn()[i],
                    ParamUtils.OP_EQUAL,
                    hashmap.get(tModel.getUniqueColumn()[i]),
                    type));
        }
        return gettDAO().find(tModel.getModelName(), lstCondition).isEmpty() ? false : true;
    }

    @Override
    public String deleteListConditionbean(String boName, List<ConditionBean> lstConditionBeans) {
        String returnMessage = "";
        int countSuccess = gettDAO().deleteList(boName, lstConditionBeans);
        if (countSuccess == 0) {
            returnMessage = ParamUtils.FAIL;
        } else {
            returnMessage = ParamUtils.SUCCESS;
        }
        return returnMessage;
    }

    //QuyenDM 27/08/2015
    @Override
    public String insertListSession(List<TDTO> tForm, Session session) {
        List<TModel> model = convertListDTOtoModel(tForm);
        return gettDAO().saveList(model, tModel.getColId(), session);
    }

    //Hongdq
    @Override
    public String deleteListSession(List<TDTO> tDTOOnGrid, Session session) {
        List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
        for (TDTO tForm : tDTOOnGrid) {
            lstCondition.add(new ConditionBean(
                    tModel.getColId(),
                    ParamUtils.OP_EQUAL,
                    String.valueOf(tForm.getFWModelId()),
                    ParamUtils.TYPE_NUMBER));

        }
        int countSuccess = gettDAO().deleteList(tModel.getModelName(), lstCondition, session);
        String returnMessage;
        if (countSuccess == 0) {
            returnMessage = ParamUtils.FAIL;
        } else {
            returnMessage = ParamUtils.SUCCESS;
        }
        return returnMessage;
    }

}
