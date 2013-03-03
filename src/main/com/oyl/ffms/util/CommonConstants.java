package com.oyl.ffms.util;

public interface CommonConstants extends com.oyl.base.util.CommonConstants
{
    public static final String REQUEST_PARAMETER_KEY_LOGIN_ID       ="loginId";
    public static final String REQUEST_PARAMETER_KEY_LOGIN_PASSWD   ="loginPasswd";
    
    public static final String USER_STATUS_ACTIVE   = "A";
    public static final String USER_STATUS_INACTIVE = "I";
    
    public static final String USER_TYPE_ADMINISTRATOR  = "A";
    public static final String USER_TYPE_NORMAL         = "N";
    
    public static final String VALUE_GENDER_MAIL    = "M";
    public static final String VALUE_GENDER_FEMAIL  = "F";
    
    public static final String VALUE_CATEGORY_TYPE_NORMAL   = "N";
    public static final String VALUE_CATEGORY_TYPE_SPECIAL  = "S";
    
    public static final String VALUE_ITEM_STATUS_DRAFT      = "D";
    public static final String VALUE_ITEM_STATUS_COMPLETE   = "C";
    
    public static final String VALUE_BANK_CARD_RECORD_BCRTYPE_WITHDRAW = "O";
    public static final String VALUE_BANK_CARD_RECORD_BCRTYPE_DEPOSITE = "I";
    
    public static final String VALUE_BANK_CARD_RECORD_CTLSTATUS_DRAFT = "D";
    public static final String VALUE_BANK_CARD_RECORD_CTLSTATUS_COMPLETE = "C";
    
    public static final String VALUE_SALARY_RECORD_STATUS_DRAFT      = "D";
    public static final String VALUE_SALARY_RECORD_STATUS_COMPLETE   = "C";
    
    public static final String VALUE_SALARY_RECORD_SRTYPE_SALARY    = "S";
    public static final String VALUE_SALARY_RECORD_SRTYPE_INCENTIVE = "I";
    public static final String VALUE_SALARY_RECORD_SRTYPE_OTHER     = "O";
    
    public static final String VALUE_CREDIT_CARD_RECORD_CCRTYPE_CONSUMPTION = "O";
    public static final String VALUE_CREDIT_CARD_RECORD_CCRTYPE_REPAYMENT   = "I";
    
    public static final String VALUE_CREDIT_CARD_RECORD_CTLSTATUS_DRAFT = "D";
    public static final String VALUE_CREDIT_CARD_RECORD_CTLSTATUS_COMPLETE = "C";
    
    
    public static final String SECT_ID_CTRL="CTRL";
    
    public static final String PARAM_ID_PER_PAGE_RECORDS="PER_PAGE_RECORDS";
    
    public static final String SESSION_COMMON_PARAMETER = "SESSION_COMMON_PARAMETER";
    
    /*################## Action Result ###############*/
    public static final String GLOBAL_EXCEPTION = "exception";
    public static final String FORWARD_COMMON_MESSAGE = "commonMessage";
    public static final String FORWARD_COMMON_REPORT_RESULT="reportResult";
    
    
    /*################## For search ################*/
    public static final String SESSION_KEY_SEARCH_PARAMETER_FAMILY              = "SEARCH_PARAMETER_FAMILY";
    
    public static final String SESSION_KEY_SEARCH_PARAMETER_USER_PROFILE        = "SEARCH_PARAMETER_USER_PROFILE";
    
    public static final String SESSION_KEY_SEARCH_PARAMETER_CATEGORY            = "SEARCH_PARAMETER_CATEGORY";
    
    public static final String SESSION_KEY_SEARCH_PARAMETER_ITEM                = "SEARCH_PARAMETER_ITEM";
    
    public static final String SESSION_KEY_SEARCH_PARAMETER_BANK_CARD           = "SEARCH_PARAMETER_BANK_CARD";
    
    public static final String SESSION_KEY_SEARCH_PARAMETER_BANK_CARD_RECORD    = "SEARCH_PARAMETER_BANK_CARD_RECORD";
    
    public static final String SESSION_KEY_SEARCH_PARAMETER_CREDIT_CARD         = "SEARCH_PARAMETER_CREDIT_CARD";
    
    public static final String SESSION_KEY_SEARCH_PARAMETER_CREDIT_CARD_RECORD  = "SEARCH_PARAMETER_CREDIT_CARD_RECORD";
    
    public static final String SESSION_KEY_SEARCH_PARAMETER_SALARY_RECORD       = "SEARCH_PARAMETER_SALARY_RECORD";
    
}
