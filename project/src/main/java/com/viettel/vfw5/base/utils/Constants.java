/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.vfw5.base.utils;

/**
 *
 * @author BSS_KIENTT
 */
public class Constants {
    public static final String PROJECT_NAME ="VIETWEBFRAMEWORK5";
    public static final String DEFAULT = "DEFAULT";
    public static final String TRANSACTION_SEQUENCE = "transaction_seq";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String REPORT_OBJECT = "objReportObject";
    public static final String USER_TOKEN = "userToken";
    public static final String IS_EXPORT_EXCEL = "isExportExcel";
    public static final String IS_EXPORT_HTML = "isExportHTML";
    public static final String EXCEL_STREAM = "excelStream";
    public static final String SESSION_TIMEOUT = "sessionTimeout";
    public static final String RULE_OBJECT ="objRule";
    public static final String COPY_RULE_OBJECT ="objCopyRule";
    public static final String COPY_RULES_OF_EVENT_OBJECT ="objCopyRulesOfEvent";
    public static final String FIST_VALUE ="-1";
    public static final String UNKNOWN = "UNKNOWN";
    public static final String PLEASE_SELECT = "--Please select--";
    public final class SEQ_CONSTANTS {
        public final static String ruleMapSeq = "RT_RULE_MAP_SEQ";
        public final static String ruleMapDetailSeq = "RT_RULE_MAP_DETAIL_SEQ";
        public final static String ruleSeq = "RT_RULE_SEQ";
        public final static String actionSeq = "RT_ACTION_SEQ";
        public final static String expressionSeq = "RT_EXPRESSION_SEQ";
        public final static String subExpressionSeq = "RT_SUB_EXPRESSION_SEQ";
    }
    public final class CDRTYPE_CONSTANTS{
        public final static String SYSTEM ="SYSTEM";
    }    
    public final class DATA_TYPE{
        public final static String INTEGER ="Integer";
        public final static String LONG ="Long";
        public final static String DOUBLE ="Double";
        public final static String DATE ="Date";
        public final static String STRING ="String";
        public final static String BOOLEAN ="Boolean";
    }

    public final class LOG_ACTION_TYPE{
        public final static String SELECT ="SELECT";
        public final static String INSERT ="INSERT";
        public final static String UPDATE ="UPDATE";
        public final static String DELETE ="DELETE";
        public final static String COPY ="COPY";
    }
    /**
     * constants group item for combobox on form
     */
    public static class GROUP_PARAM_CONSTANTS{
        public final static String DATA_TYPE ="DATA_TYPE";
        public final static String ACCOUNT_GROUP_TYPE ="ACCOUNT_GROUP_TYPE";
        public final static String IS_PARENT_ACCOUNT_TYPE ="IS_PARENT_ACCOUNT_TYPE";
        public final static String TYPE_PRICE_PLAN ="TYPE_PRICE_PLAN";
        public final static String CYCLE_UNIT ="CYCLE_UNIT";
        public final static String CALCULATION_PRICE ="CALCULATION_PRICE";
        public final static String CDR_PROP_TYPE="CDR_PROP_TYPE";
        public final static String CDR_PROP_UNIT_TYPE="CDR_PROP_UNIT_TYPE";
        public final static String SWITCHBOARD="SWITCHBOARD";
        public final static String CDR_GROUP="CDR_GROUP";
        public final static String BACKUP_STYLE="BACKUP_STYLE";
        public final static String DOWNLOAD_TYPE="DOWNLOAD_TYPE";
        public final static String ZONE_RATING_TYPE="ZONE_RATING_TYPE";
        public final static String ACCOUNT_TYPE_TYPE="ACCOUNT_TYPE_TYPE";
        public final static String NEW_BASIC_PRICE="NEW_BASIC_PRICE";
        public final static String TER_BASIC_PRICE="TER_BASIC_PRICE";
        public final static String NORMAL_BASIC_PRICE="NORMAL_BASIC_PRICE";
        public final static String PROCESS_GROUP_NAME="PROCESS_GROUP_NAME";
        public final static String CACHE_GROUP_NAME="CACHE_GROUP_NAME";
        public final static String RECURRING_TYPE="RECURRING_TYPE";
        public final static String RECURRING="recurring";
    }
    public final static String EVENT_GROUP="ONEOFF";

}


