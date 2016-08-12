/*
 * Copyright (C) 2015 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vfw5.base.service;

import com.viettel.vfw5.base.dao.BaseFWDAOImpl;
import com.viettel.vfw5.base.dto.BaseFWDTO;
import com.viettel.vfw5.base.model.BaseFWModel;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.LanguageBundleUtils;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.ReflectUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
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
    public long count(Locale locale, TDTO tForm) {
        return gettDAO().count(tModel.getModelName(), prepareCondition(locale, tForm));
    }

    @Override
    public String create(Locale locale, TDTO tForm) {
        return gettDAO().save(tForm.toModel(locale));
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
        int deleteSucess = 0;
        Long transId = getTransactionId();
        String failRecord = "";
        for (TDTO tForm : tFormOnGrid) {
//            if (delete(tForm.getId(), transId).equals(ParamUtils.SUCCESS)) {
//                deleteSucess++;
//            } else {
//                failRecord += tForm.catchName() + ", ";
//            }
        }
        String returnMessage;
        if (deleteSucess == tFormOnGrid.size()) {
            returnMessage = ParamUtils.SUCCESS;
        } else {
            returnMessage = failRecord;
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
    public List getAll() {
        return convertListModeltoDTO(gettDAO().findAll(Order.asc(tModel.getColName())));
    }

    public List getAllForms() {
        return convertListModeltoDTO(gettDAO().findAll(Order.asc(tModel.getColName())));
    }

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
    @Override
    public List prepareCondition(Locale locale, TDTO tForm) {
        List<ConditionBean> lstCondition = new ArrayList<ConditionBean>();
        if (tForm == null) {
            return lstCondition;
        }
        Method methods[] = tForm.toModel(locale).getClass().getDeclaredMethods();
        Method methodForms[] = tForm.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (ReflectUtils.isGetter(methods[i])) {
                try {
                    Object value = methods[i].invoke(tForm.toModel(locale));
                    String returnType = methods[i].getReturnType().getSimpleName().toUpperCase();
                    if (value != null && !value.equals("")) {
                        if (ParamUtils.TYPE_STRING.indexOf(returnType) >= 0) {
                            if (!value.equals(String.valueOf(ParamUtils.DEFAULT_VALUE))
                                    && !value.equals(String.valueOf(LanguageBundleUtils.getString(locale, "MSG.CHOOSE_ITEM")))
                                    && !value.equals(String.valueOf(LanguageBundleUtils.getString(locale, "MSG.PLEASE_SELECT")))) {
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
    public List search(Locale locale, TDTO tForm, int start, int maxResult, String sortType, String sortField) {
        return convertListModeltoDTO(
                gettDAO().find(tModel.getModelName(),
                        prepareCondition(locale, tForm),
                        StringUtils.formatOrder(sortField, sortType),
                        start, maxResult));
    }

    @Override
    public String update(Locale locale, TDTO tForm) {
        return gettDAO().update(tForm.toModel(locale));
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
}
