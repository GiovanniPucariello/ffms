package com.oyl.ffms.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.oyl.base.holder.MessageTargetHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.base.util.ErrorMsgHelper;
import com.oyl.ffms.holder.BankCardHolder;
import com.oyl.ffms.holder.CreditCardHolder;
import com.oyl.ffms.holder.CreditCardRecordHolder;
import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.holder.UserProfileHolder;
import com.oyl.ffms.service.BankCardService;
import com.oyl.ffms.service.CreditCardRecordService;
import com.oyl.ffms.service.CreditCardService;
import com.oyl.ffms.service.FamilyService;
import com.oyl.ffms.service.UserProfileService;
import com.oyl.ffms.util.CommonConstants;

public class CreditCardRecordAction extends ProjectBaseAction implements
        CommonConstants
{
    private CreditCardHolder creditCard;
    private CreditCardRecordHolder creditCardRecord;
    private List<String> resOids;
    
    private Date paramDate;
    private int paramHour;
    private int paramMinute;
    
    private String fromBankCard;
    private Map ccrTypes;
    private List hourList;
    private List minuteList;
    private Map cardNos;
    private Map bankCardNos;
    
    private CreditCardService creditCardService;
    private CreditCardRecordService creditCardRecordService;
    private UserProfileService userProfileService;
    private FamilyService familyService;
    private BankCardService bankCardService;
    
    public CreditCardRecordAction()
    {
        initMsg();
        
        //default target
        MessageTargetHolder target_ = new MessageTargetHolder("initCreditCardRecord",getText("button.ok"));
        target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
        
        msg.addMessageTarget(target_);
        msg.setTitle(getText("label.common.summary"));
    }
    
    
    public String init()
    {
        try
        {
            clearSearchParameter(SESSION_KEY_SEARCH_PARAMETER_CREDIT_CARD_RECORD);
            
            if (creditCardRecord == null)
            {
                if (getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_CREDIT_CARD_RECORD) != null)
                {
                    creditCardRecord = (CreditCardRecordHolder)getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_CREDIT_CARD_RECORD);
                }
                else
                {
                    // impossible to access here for now
                    creditCardRecord = new CreditCardRecordHolder();
                }
            }
            else
            {
                creditCardRecord.trimAllString();
                creditCardRecord.setAllEmptyStringToNull();
                
                getSessionMap().put(SESSION_KEY_SEARCH_PARAMETER_CREDIT_CARD_RECORD, creditCardRecord);
            }
            
            this.obtainListRecordsOfPagination((BaseService) creditCardRecordService,
                    creditCardRecord, SORTING_MAP_CREDIT_CARD_RECORD, this.getPageSize());
            
            creditCard = creditCardService.getCreditCardByKey(creditCardRecord
                    .getCcOid());
            creditCard.setUserName(userProfileService.getUserProfileByKey(
                    creditCard.getUserOid()).getUserName());
        }
        catch (Exception e)
        {
            log.error("init(): ");
            log.error("Error occur on init CreditCardRecord", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return SUCCESS;
    }


    //*****************************************************
    // create credit card record
    //*****************************************************
    
    public String initAdd()
    {
        try
        {
            creditCard = creditCardService.getCreditCardByKey(creditCardRecord.getCcOid());
            
            bankCardNos = this.initBankCardNos();
            ccrTypes = this.initCcrTypeMap();
            hourList     = this.initHours();
            minuteList   = this.initMinutes();
            
            paramDate = new Date();
        }
        catch (Exception e)
        {
            log.error("initAdd(): ");
            log.error("Error occur on initAdd credit card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return SUCCESS;
    }
    
    
    public void validateSaveAdd()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if (creditCardRecord != null)
            {
                creditCardRecord.trimAllString();
            }
            
            
            if (!flag && (creditCardRecord == null || BLANK_STR.equals(creditCardRecord.getDescription())))
            {
                this.addActionError(this.getText("creditCardRecord.description.empty"));
                flag = true;
            }
            
            
            if (!flag &&  null == creditCardRecord.getAmount())
            {
                this.addActionError(this.getText("creditCardRecord.amount.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (creditCardRecord.getAmount().compareTo(new BigDecimal(0)) <= 0))
            {
                this.addActionError(this.getText("creditCardRecord.amount.lessthanzero"));
                flag = true;
            }
            
            
            if (!flag && paramDate == null)
            {
                this.addActionError(this.getText("creditCardRecord.createDate.empty"));
                flag = true;
            }
            
            
            
            CreditCardHolder card = creditCardService.getCreditCardByKey(creditCardRecord.getCcOid());
            
            if (!flag && VALUE_CREDIT_CARD_RECORD_CCRTYPE_REPAYMENT.equals(creditCardRecord.getCcrType())
                    && creditCardRecord.getAmount().compareTo(card.getDebt()) > 0 )
            {
                this.addActionError(this.getText("creditCardRecord.amount.lagerthan.debt"));
                flag = true;
            }
            
            
            if (!flag && VALUE_CREDIT_CARD_RECORD_CCRTYPE_CONSUMPTION.equals(creditCardRecord.getCcrType())
                    && creditCardRecord.getAmount().compareTo(card.getQuota()) > 0 )
            {
                this.addActionError(this.getText("creditCardRecord.amount.lagerthan.quota"));
                flag = true;
            }
            
            
            if (!flag
                    && VALUE_CREDIT_CARD_RECORD_CCRTYPE_REPAYMENT
                            .equals(creditCardRecord.getCcrType())
                    && VALUE_YES.equalsIgnoreCase(fromBankCard))
            {
                if (null == creditCardRecord.getBcOid())
                {
                    this.addActionError(this.getText("creditCardRecord.bankCard.empty"));
                    flag = true;
                }
            }
            
            
            if (!flag
                    && VALUE_CREDIT_CARD_RECORD_CCRTYPE_REPAYMENT
                            .equals(creditCardRecord.getCcrType())
                    && VALUE_YES.equalsIgnoreCase(fromBankCard))
            {
                BankCardHolder bankCard = bankCardService.getBankCardByKey(creditCardRecord.getBcOid());
                
                if (creditCardRecord.getAmount().compareTo(bankCard.getBalance()) > 0)
                {
                    this.addActionError(this.getText("creditCardRecord.bankCard.balance.not.enough", new String[]{bankCard.getCardNo()}));
                    flag = true;
                }
            }
            
            
            if (flag)
            {
                creditCard = creditCardService.getCreditCardByKey(creditCardRecord.getCcOid());
                
                bankCardNos = this.initBankCardNos();
                ccrTypes = this.initCcrTypeMap();
                hourList     = this.initHours();
                minuteList   = this.initMinutes();
            }
        }
        catch (Exception e)
        {
            log.error("validateSaveAdd(): ");
            log.error("Error occur on validateSaveAdd credit card record", e);
        }
    }
    
    
    public String saveAdd()
    {
        try
        {
            creditCardRecord.setCcrOid(creditCardRecordService.getOid());
            creditCardRecord.setCtlStatus(VALUE_CREDIT_CARD_RECORD_CTLSTATUS_DRAFT);
            creditCardRecord.setCreateDate(handleDate());
            if (!VALUE_YES.equalsIgnoreCase(fromBankCard)
                    || creditCardRecord.getCcrType().equals(
                            VALUE_CREDIT_CARD_RECORD_CCRTYPE_CONSUMPTION))
            {
                creditCardRecord.setBcOid(null);
            }
            
            
            creditCardRecord.trimAllString();
            creditCardRecord.setAllEmptyStringToNull();
            ((DBActionService)creditCardRecordService).insert(this.getCommonParameter(), creditCardRecord);
            
            MessageTargetHolder target_ = new MessageTargetHolder("initCreditCardRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("creditCardRecord.create.success", new String[]{creditCardRecord.getDescription()}));
        }
        catch (Exception e)
        {
            log.error("saveAdd(): ");
            log.error("Error occur on saveAdd credit card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // edit credit card record
    //*****************************************************
    
    public String initEdit()
    {
        try
        {
            creditCardRecord = creditCardRecordService.getCreditCardRecordByKey(creditCardRecord.getCcrOid());
            
            bankCardNos = this.initBankCardNos();
            ccrTypes = this.initCcrTypeMap();
            hourList     = this.initHours();
            minuteList   = this.initMinutes();
            
            Calendar c = Calendar.getInstance();
            c.setTime(creditCardRecord.getCreateDate());
            
            paramDate = c.getTime();
            paramHour = c.get(Calendar.HOUR_OF_DAY);
            paramMinute = c.get(Calendar.MINUTE);
            
            if (null != creditCardRecord.getBcOid())
            {
                fromBankCard = VALUE_YES;
            }
        }
        catch (Exception e)
        {
            log.error("initEdit(): ");
            log.error("Error occur on initEdit credit card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return SUCCESS;
    }
    
    
    public void validateSaveEdit()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if (creditCardRecord != null)
            {
                creditCardRecord.trimAllString();
            }
            
            
            CreditCardRecordHolder oldObj = creditCardRecordService.getCreditCardRecordByKey(creditCardRecord.getCcrOid());
            
            
            if (!flag && (creditCardRecord == null || BLANK_STR.equals(creditCardRecord.getDescription())))
            {
                this.addActionError(this.getText("creditCardRecord.description.empty"));
                flag = true;
            }
            
            
            if (!flag &&  null == creditCardRecord.getAmount())
            {
                this.addActionError(this.getText("creditCardRecord.amount.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (creditCardRecord.getAmount().compareTo(new BigDecimal(0)) <= 0))
            {
                this.addActionError(this.getText("creditCardRecord.amount.lessthanzero"));
                flag = true;
            }
            
            
            if (!flag && paramDate == null)
            {
                this.addActionError(this.getText("creditCardRecord.createDate.empty"));
                flag = true;
            }
            
            
            CreditCardHolder card = creditCardService.getCreditCardByKey(oldObj.getCcOid());
            
            if (!flag && VALUE_CREDIT_CARD_RECORD_CCRTYPE_REPAYMENT.equals(creditCardRecord.getCcrType())
                    && creditCardRecord.getAmount().compareTo(card.getDebt()) > 0 )
            {
                this.addActionError(this.getText("creditCardRecord.amount.lagerthan.debt"));
                flag = true;
            }
            
            
            if (!flag && VALUE_CREDIT_CARD_RECORD_CCRTYPE_CONSUMPTION.equals(creditCardRecord.getCcrType())
                    && creditCardRecord.getAmount().compareTo(card.getQuota()) > 0 )
            {
                this.addActionError(this.getText("creditCardRecord.amount.lagerthan.quota"));
                flag = true;
            }
            
            
            if (!flag
                    && VALUE_CREDIT_CARD_RECORD_CCRTYPE_REPAYMENT
                            .equals(creditCardRecord.getCcrType())
                    && VALUE_YES.equalsIgnoreCase(fromBankCard))
            {
                if (null == creditCardRecord.getBcOid())
                {
                    this.addActionError(this.getText("creditCardRecord.bankCard.empty"));
                    flag = true;
                }
            }
            
            
            if (!flag
                    && VALUE_CREDIT_CARD_RECORD_CCRTYPE_REPAYMENT
                            .equals(creditCardRecord.getCcrType())
                    && VALUE_YES.equalsIgnoreCase(fromBankCard))
            {
                BankCardHolder bankCard = bankCardService.getBankCardByKey(creditCardRecord.getBcOid());
                
                if (creditCardRecord.getAmount().compareTo(bankCard.getBalance()) > 0)
                {
                    this.addActionError(this.getText("creditCardRecord.bankCard.balance.not.enough", new String[]{bankCard.getCardNo()}));
                    flag = true;
                }
            }
            
            
            if (flag)
            {
                creditCard = creditCardService.getCreditCardByKey(creditCardRecord.getCcOid());
                
                bankCardNos = this.initBankCardNos();
                ccrTypes = this.initCcrTypeMap();
                hourList     = this.initHours();
                minuteList   = this.initMinutes();
            }
        }
        catch (Exception e)
        {
            log.error("validateSaveEdit(): ");
            log.error("Error occur on validateSaveEdit credit card record", e);
        }
    }
    
    
    public String saveEdit()
    {
        try
        {
            CreditCardRecordHolder oldObj = creditCardRecordService.getCreditCardRecordByKey(creditCardRecord.getCcrOid());
            
            creditCardRecord.setCcrOid(oldObj.getCcrOid());
            creditCardRecord.setCtlStatus(VALUE_CREDIT_CARD_RECORD_CTLSTATUS_DRAFT);
            creditCardRecord.setCreateDate(handleDate());
            creditCardRecord.setCcOid(oldObj.getCcOid());
            if (!VALUE_YES.equalsIgnoreCase(fromBankCard)
                    || creditCardRecord.getCcrType().equals(
                            VALUE_CREDIT_CARD_RECORD_CCRTYPE_CONSUMPTION))
            {
                creditCardRecord.setBcOid(null);
            }
            
            
            creditCardRecord.trimAllString();
            creditCardRecord.setAllEmptyStringToNull();
            ((DBActionService)creditCardRecordService).update(this.getCommonParameter(), creditCardRecord, oldObj);
            
            MessageTargetHolder target_ = new MessageTargetHolder("initCreditCardRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("creditCardRecord.edit.success"));
        }
        catch (Exception e)
        {
            log.error("saveEdit(): ");
            log.error("Error occur on saveEdit credit card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // delete credit card record
    //*****************************************************
    
    public void validateSaveDelete()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if(!flag && (resOids==null || resOids.size()==0))
            {
                this.addActionError(this.getText("creditCardRecord.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveDelete(): ");
            log.error("Error occur on validateSaveDelete credit card record", e);
        }
    }
    
    
    public String saveDelete()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                CreditCardRecordHolder oldObj_ = creditCardRecordService.getCreditCardRecordByKey(new BigDecimal(resOids.get(i).toString()));
                
                if (VALUE_CREDIT_CARD_RECORD_CTLSTATUS_COMPLETE.equals(oldObj_.getCtlStatus()))
                {
                    msg.saveSuccess(this.getText("creditCardRecord.delete.fail.confirm.already", new String[]{oldObj_.getDescription()}));
                    continue;
                }
                
                
                try
                {
                    ((DBActionService)creditCardRecordService).delete(this.getCommonParameter(), oldObj_);
                    msg.saveSuccess(this.getText("creditCardRecord.delete.success", new String[]{oldObj_.getDescription()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initCreditCardRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveDelete(): ");
            log.error("Error occur on saveDelete credit card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // confirm credit card record
    //*****************************************************
    
    public void validateSaveConfirm()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if(!flag && (resOids==null || resOids.size()==0))
            {
                this.addActionError(this.getText("creditCardRecord.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveConfirm(): ");
            log.error("Error occur on validateSaveConfirm credit card record", e);
        }
    }
    
    
    public String saveConfirm()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                CreditCardRecordHolder oldObj_ = creditCardRecordService.getCreditCardRecordByKey(new BigDecimal(resOids.get(i).toString()));
                CreditCardHolder ccard = creditCardService.getCreditCardByKey(oldObj_.getCcOid());
                
                if (VALUE_CREDIT_CARD_RECORD_CTLSTATUS_COMPLETE.equals(oldObj_.getCtlStatus()))
                {
                    msg.saveSuccess(this.getText("creditCardRecord.confirm.already", new String[]{oldObj_.getDescription()}));
                    continue;
                }
                
                if (VALUE_CREDIT_CARD_RECORD_CCRTYPE_CONSUMPTION.equals(oldObj_.getCcrType())
                        && oldObj_.getAmount().compareTo(ccard.getQuota()) > 0 )
                {
                    msg.saveSuccess(this.getText("creditCardRecord.confirm.fail.quota.not.enough", new String[]{oldObj_.getDescription()}));
                    continue;
                }
                
                if (VALUE_CREDIT_CARD_RECORD_CCRTYPE_REPAYMENT.equals(oldObj_.getCcrType())
                        && oldObj_.getAmount().compareTo(ccard.getDebt()) > 0 )
                {
                    msg.saveSuccess(this.getText("creditCardRecord.confirm.fail.amount.lager.than.debt", new String[]{oldObj_.getDescription()}));
                    continue;
                }
                
                
                if (VALUE_CREDIT_CARD_RECORD_CCRTYPE_REPAYMENT.equalsIgnoreCase(oldObj_.getCcrType())  && 
                        null != oldObj_.getBcOid())
                {
                    BankCardHolder card = bankCardService.getBankCardByKey(oldObj_.getBcOid());
                    
                    if (oldObj_.getAmount().compareTo(card.getBalance()) > 0)
                    {
                        msg.saveSuccess(this
                                .getText(
                                        "creditCardRecord.confirm.fail.bankcard.balance.not.enough",
                                        new String[] {
                                                oldObj_.getDescription(),
                                                card.getCardNo() }));
                        continue;
                    }
                }
                
                
                try
                {
                    creditCardRecordService.confirmCreditCardRecord(oldObj_);
                    msg.saveSuccess(this.getText("creditCardRecord.confirm.success", new String[]{oldObj_.getDescription()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initCreditCardRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveConfirm(): ");
            log.error("Error occur on saveConfirm credit card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // private methods
    //*****************************************************
    
    
    private Map initCcrTypeMap()
    {
        Map map = new HashMap();
        
        map.put(VALUE_CREDIT_CARD_RECORD_CCRTYPE_REPAYMENT, this.getText("label.creditCardRecord.ccrType.repayment"));
        map.put(VALUE_CREDIT_CARD_RECORD_CCRTYPE_CONSUMPTION, this.getText("label.creditCardRecord.ccrType.consumption"));
        
        return map;
    }
    
    
    private Map initBankCardNos() throws Exception
    {
        Map map = new HashMap();
        
        FamilyHolder family = familyService.getFamilyByUserOid(this.getLoginOfUser().getUserOid());
        
        List<UserProfileHolder> userProfiles = familyService.getUsersByFamilyOid(family.getFamilyOid());
        
        for (UserProfileHolder user : userProfiles)
        {
            List<BankCardHolder> cards = bankCardService.getBankCarsByUser(user.getUserOid());
            
            for (BankCardHolder card : cards)
            {
                map.put(card.getBcOid(), user.getUserName() + "---"
                        + card.getCardNo());
            }
        }
        
        return map;
    }
    
    
    private List initHours()
    {
        List rlt = new ArrayList();
        
        for(int i=0; i<24; i++)
        {
            rlt.add(Integer.toString(i));
        }
        
        return rlt;
    }
    
    
    private List initMinutes()
    {
        List rlt = new ArrayList();
        
        for(int i=0; i<60; i+=5)
        {
            rlt.add(Integer.toString(i));
        }
        
        return rlt;
    }
    
    
    private Date handleDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        calendar.set(Calendar.HOUR_OF_DAY, paramHour);
        calendar.set(Calendar.MINUTE, paramMinute);
        calendar.set(Calendar.SECOND, new Random().nextInt(60));
        
        return calendar.getTime();
    }
    
    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public CreditCardHolder getCreditCard()
    {
        return creditCard;
    }


    public void setCreditCard(CreditCardHolder creditCard)
    {
        this.creditCard = creditCard;
    }


    public CreditCardRecordHolder getCreditCardRecord()
    {
        return creditCardRecord;
    }


    public void setCreditCardRecord(CreditCardRecordHolder creditCardRecord)
    {
        this.creditCardRecord = creditCardRecord;
    }


    public List<String> getResOids()
    {
        return resOids;
    }


    public void setResOids(List<String> resOids)
    {
        this.resOids = resOids;
    }


    public Date getParamDate()
    {
        return paramDate;
    }


    public void setParamDate(Date paramDate)
    {
        this.paramDate = paramDate;
    }


    public int getParamHour()
    {
        return paramHour;
    }


    public void setParamHour(int paramHour)
    {
        this.paramHour = paramHour;
    }


    public int getParamMinute()
    {
        return paramMinute;
    }


    public void setParamMinute(int paramMinute)
    {
        this.paramMinute = paramMinute;
    }


    public Map getCcrTypes()
    {
        return ccrTypes;
    }


    public void setCcrTypes(Map ccrTypes)
    {
        this.ccrTypes = ccrTypes;
    }


    public List getHourList()
    {
        return hourList;
    }


    public void setHourList(List hourList)
    {
        this.hourList = hourList;
    }


    public List getMinuteList()
    {
        return minuteList;
    }


    public void setMinuteList(List minuteList)
    {
        this.minuteList = minuteList;
    }


    public Map getCardNos()
    {
        return cardNos;
    }


    public void setCardNos(Map cardNos)
    {
        this.cardNos = cardNos;
    }


    public void setCreditCardService(CreditCardService creditCardService)
    {
        this.creditCardService = creditCardService;
    }


    public void setCreditCardRecordService(
            CreditCardRecordService creditCardRecordService)
    {
        this.creditCardRecordService = creditCardRecordService;
    }


    public void setUserProfileService(UserProfileService userProfileService)
    {
        this.userProfileService = userProfileService;
    }


    public Map getBankCardNos()
    {
        return bankCardNos;
    }


    public void setBankCardNos(Map bankCardNos)
    {
        this.bankCardNos = bankCardNos;
    }


    public void setFamilyService(FamilyService familyService)
    {
        this.familyService = familyService;
    }


    public void setBankCardService(BankCardService bankCardService)
    {
        this.bankCardService = bankCardService;
    }


    public String getFromBankCard()
    {
        return fromBankCard;
    }


    public void setFromBankCard(String fromBankCard)
    {
        this.fromBankCard = fromBankCard;
    }
    
}
