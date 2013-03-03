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
import com.oyl.ffms.holder.BankCardRecordHolder;
import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.holder.UserProfileHolder;
import com.oyl.ffms.service.BankCardRecordService;
import com.oyl.ffms.service.BankCardService;
import com.oyl.ffms.service.FamilyService;
import com.oyl.ffms.service.UserProfileService;
import com.oyl.ffms.util.CommonConstants;

public class BankCardRecordAction extends ProjectBaseAction implements
        CommonConstants
{
    private BankCardHolder bankCard;
    private BankCardRecordHolder bankCardRecord;
    private List<String> resOids;
    private BigDecimal target;
    
    private Date paramDate;
    private int paramHour;
    private int paramMinute;
    
    private Map bcrTypes;
    private List hourList;
    private List minuteList;
    private Map cardNos;
    
    
    private BankCardRecordService bankCardRecordService;
    private BankCardService bankCardService;
    private UserProfileService userProfileService;
    private FamilyService familyService;
    
    
    public BankCardRecordAction()
    {
        initMsg();
        
        //default target
        MessageTargetHolder target_ = new MessageTargetHolder("initBankCardRecord",getText("button.ok"));
        target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
        
        msg.addMessageTarget(target_);
        msg.setTitle(getText("label.common.summary"));
    }


    public String init()
    {
        try
        {
            clearSearchParameter(SESSION_KEY_SEARCH_PARAMETER_BANK_CARD_RECORD);
            
            if (bankCardRecord == null)
            {
                if (getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_BANK_CARD_RECORD) != null)
                {
                    bankCardRecord = (BankCardRecordHolder)getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_BANK_CARD_RECORD);
                }
                else
                {
                    // impossible to access here for now
                    bankCardRecord = new BankCardRecordHolder();
                }
            }
            else
            {
                bankCardRecord.trimAllString();
                bankCardRecord.setAllEmptyStringToNull();
                
                getSessionMap().put(SESSION_KEY_SEARCH_PARAMETER_BANK_CARD_RECORD, bankCardRecord);
            }
            
            this.obtainListRecordsOfPagination((BaseService) bankCardRecordService,
                    bankCardRecord, SORTING_MAP_BANK_CARD_RECORD, this.getPageSize());
            
            bankCard = bankCardService.getBankCardByKey(bankCardRecord
                    .getBcOid());
            bankCard.setUserName(userProfileService.getUserProfileByKey(
                    bankCard.getUserOid()).getUserName());
        }
        catch (Exception e)
        {
            log.error("init(): ");
            log.error("Error occur on init BankCardRecord", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return SUCCESS;
    }
    
    //*****************************************************
    // create bank card record
    //*****************************************************
    
    public String initAdd()
    {
        try
        {
            bankCard = bankCardService.getBankCardByKey(bankCardRecord.getBcOid());
            
            bcrTypes = this.initBcrTypeMap();
            hourList     = this.initHours();
            minuteList   = this.initMinutes();
            
            paramDate = new Date();
        }
        catch (Exception e)
        {
            log.error("initAdd(): ");
            log.error("Error occur on initAdd bank card record", e);
            
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
            if (bankCardRecord != null)
            {
                bankCardRecord.trimAllString();
            }
            
            
            if (!flag && (bankCardRecord == null || BLANK_STR.equals(bankCardRecord.getDescription())))
            {
                this.addActionError(this.getText("bankCardRecord.description.empty"));
                flag = true;
            }
            
            
            if (!flag &&  null == bankCardRecord.getAmount())
            {
                this.addActionError(this.getText("bankCardRecord.amount.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (bankCardRecord.getAmount().compareTo(new BigDecimal(0)) <= 0))
            {
                this.addActionError(this.getText("bankCardRecord.amount.lessthanzero"));
                flag = true;
            }
            
            
            if (!flag && paramDate == null)
            {
                this.addActionError(this.getText("bankCardRecord.createDate.empty"));
                flag = true;
            }
            
            
            BankCardHolder card = bankCardService
                    .getBankCardByKey(bankCardRecord.getBcOid());
            if (VALUE_BANK_CARD_RECORD_BCRTYPE_WITHDRAW
                    .equalsIgnoreCase(bankCardRecord.getBcrType())
                    && card.getBalance().compareTo(bankCardRecord.getAmount()) < 0)
            {
                this.addActionError(this.getText("bankCardRecord.balance.not.enough"));
                flag = true;
            }
            
            
            if (flag)
            {
                bankCard = bankCardService.getBankCardByKey(bankCardRecord.getBcOid());
                
                bcrTypes = this.initBcrTypeMap();
                hourList     = this.initHours();
                minuteList   = this.initMinutes();
            }
        }
        catch (Exception e)
        {
            log.error("validateSaveAdd(): ");
            log.error("Error occur on validateSaveAdd bank card record", e);
        }
    }
    
    
    public String saveAdd()
    {
        try
        {
            bankCardRecord.setBcrOid(bankCardRecordService.getOid());
            bankCardRecord.setCtlStatus(VALUE_BANK_CARD_RECORD_CTLSTATUS_DRAFT);
            bankCardRecord.setCreateDate(handleDate());
            
            
            bankCardRecord.trimAllString();
            bankCardRecord.setAllEmptyStringToNull();
            ((DBActionService)bankCardRecordService).insert(this.getCommonParameter(), bankCardRecord);
            
            MessageTargetHolder target_ = new MessageTargetHolder("initBankCardRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("bankCardRecord.create.success", new String[]{bankCardRecord.getDescription()}));
        }
        catch (Exception e)
        {
            log.error("saveAdd(): ");
            log.error("Error occur on saveAdd bank card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // edit bank card record
    //*****************************************************
    
    public String initEdit()
    {
        try
        {
            bankCardRecord = bankCardRecordService.getBankCardRecordByKey(bankCardRecord.getBcrOid());
            
            bcrTypes = this.initBcrTypeMap();
            hourList     = this.initHours();
            minuteList   = this.initMinutes();
            
            Calendar c = Calendar.getInstance();
            c.setTime(bankCardRecord.getCreateDate());
            
            paramDate = c.getTime();
            paramHour = c.get(Calendar.HOUR_OF_DAY);
            paramMinute = c.get(Calendar.MINUTE);
        }
        catch (Exception e)
        {
            log.error("initEdit(): ");
            log.error("Error occur on initEdit bank card record", e);
            
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
            if (bankCardRecord != null)
            {
                bankCardRecord.trimAllString();
            }
            
            
            if (!flag && (bankCardRecord == null || BLANK_STR.equals(bankCardRecord.getDescription())))
            {
                this.addActionError(this.getText("bankCardRecord.description.empty"));
                flag = true;
            }
            
            
            if (!flag &&  null == bankCardRecord.getAmount())
            {
                this.addActionError(this.getText("bankCardRecord.amount.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (bankCardRecord.getAmount().compareTo(new BigDecimal(0)) <= 0))
            {
                this.addActionError(this.getText("bankCardRecord.amount.lessthanzero"));
                flag = true;
            }
            
            
            if (!flag && paramDate == null)
            {
                this.addActionError(this.getText("bankCardRecord.createDate.empty"));
                flag = true;
            }
            
            
            BankCardHolder card = bankCardService
                    .getBankCardByKey(bankCardRecord.getBcOid());
            if (VALUE_BANK_CARD_RECORD_BCRTYPE_WITHDRAW
                    .equalsIgnoreCase(bankCardRecord.getBcrType())
                    && card.getBalance().compareTo(bankCardRecord.getAmount()) < 0)
            {
                this.addActionError(this
                        .getText("bankCardRecord.balance.not.enough"));
                flag = true;
            }
            
            
            if (flag)
            {
                bcrTypes     = this.initBcrTypeMap();
                hourList     = this.initHours();
                minuteList   = this.initMinutes();
                
            }
        }
        catch (Exception e)
        {
            log.error("validateSaveAdd(): ");
            log.error("Error occur on validateSaveEdit bank card record", e);
        }
    }
    
    
    public String saveEdit()
    {
        try
        {
            BankCardRecordHolder oldObj = bankCardRecordService.getBankCardRecordByKey(bankCardRecord.getBcrOid());
            
            bankCardRecord.setBcrOid(oldObj.getBcrOid());
            bankCardRecord.setCtlStatus(VALUE_BANK_CARD_RECORD_CTLSTATUS_DRAFT);
            bankCardRecord.setCreateDate(handleDate());
            bankCardRecord.setBcOid(oldObj.getBcOid());
            
            
            bankCardRecord.trimAllString();
            bankCardRecord.setAllEmptyStringToNull();
            ((DBActionService)bankCardRecordService).update(this.getCommonParameter(), bankCardRecord, oldObj);
            
            MessageTargetHolder target_ = new MessageTargetHolder("initBankCardRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("bankCardRecord.edit.success"));
        }
        catch (Exception e)
        {
            log.error("saveEdit(): ");
            log.error("Error occur on saveEdit bank card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // delete bank card record
    //*****************************************************
    
    public void validateSaveDelete()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if(!flag && (resOids==null || resOids.size()==0))
            {
                this.addActionError(this.getText("bankCardRecord.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveDelete(): ");
            log.error("Error occur on validateSaveDelete bank card record", e);
        }
    }
    
    
    public String saveDelete()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                BankCardRecordHolder oldObj_ = bankCardRecordService.getBankCardRecordByKey(new BigDecimal(resOids.get(i).toString()));
                
                if (VALUE_BANK_CARD_RECORD_CTLSTATUS_COMPLETE.equals(oldObj_.getCtlStatus()))
                {
                    msg.saveSuccess(this.getText("bankCardRecord.delete.fail.confirm.already", new String[]{oldObj_.getDescription()}));
                    continue;
                }
                
                
                try
                {
                    ((DBActionService)bankCardRecordService).delete(this.getCommonParameter(), oldObj_);
                    msg.saveSuccess(this.getText("bankCardRecord.delete.success", new String[]{oldObj_.getDescription()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initBankCardRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveDelete(): ");
            log.error("Error occur on saveDelete bank card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // confirm bank card record
    //*****************************************************
    
    public void validateSaveConfirm()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if(!flag && (resOids==null || resOids.size()==0))
            {
                this.addActionError(this.getText("bankCardRecord.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveConfirm(): ");
            log.error("Error occur on validateSaveConfirm bank card record", e);
        }
    }
    
    
    public String saveConfirm()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                BankCardRecordHolder oldObj_ = bankCardRecordService.getBankCardRecordByKey(new BigDecimal(resOids.get(i).toString()));
                
                if (VALUE_BANK_CARD_RECORD_CTLSTATUS_COMPLETE.equals(oldObj_.getCtlStatus()))
                {
                    msg.saveSuccess(this.getText("bankCardRecord.confirm.already", new String[]{oldObj_.getDescription()}));
                    continue;
                }
                
                BankCardHolder card = bankCardService.getBankCardByKey(oldObj_.getBcOid());
                
                if (VALUE_BANK_CARD_RECORD_BCRTYPE_WITHDRAW
                        .equalsIgnoreCase(oldObj_.getBcrType())
                        && card.getBalance().compareTo(oldObj_.getAmount()) < 0)
                {
                    msg.saveSuccess(this.getText(
                            "bankCardRecord.confirm.fail.balance.unenough",
                            new String[] { card.getCardNo(),
                                    oldObj_.getDescription() }));
                    continue;
                }
                
                
                try
                {
                    bankCardRecordService.confirmBankCardRecord(oldObj_, card);
                    msg.saveSuccess(this.getText("bankCardRecord.confirm.success", new String[]{oldObj_.getDescription()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initBankCardRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveDelete(): ");
            log.error("Error occur on saveDelete bank card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // transfer bank card record
    //*****************************************************
    
    public String initTransfer()
    {
        try
        {
            cardNos  = this.initCardNoMap();
            
            hourList     = this.initHours();
            minuteList   = this.initMinutes();
            
            paramDate = new Date();
        }
        catch (Exception e)
        {
            log.error("initTransfer(): ");
            log.error("Error occur on initTransfer bank card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return SUCCESS;
    }
    
    
    
    public void validateSaveTransfer()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if (bankCardRecord != null)
            {
                bankCardRecord.trimAllString();
            }
            
            
            if (!flag &&  null == bankCardRecord.getAmount())
            {
                this.addActionError(this.getText("bankCardRecord.amount.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (bankCardRecord.getAmount().compareTo(new BigDecimal(0)) <= 0))
            {
                this.addActionError(this.getText("bankCardRecord.amount.lessthanzero"));
                flag = true;
            }
            
            
            if (!flag)
            {
                BankCardHolder source = bankCardService.getBankCardByKey(bankCardRecord.getBcOid());
                
                if (source.getBalance().compareTo(bankCardRecord.getAmount()) < 0)
                {
                    this.addActionError(this.getText("bankCardRecord.transfer.fail.balance.unenough"));
                    flag = true;
                }
            }
            
            
            if (!flag && paramDate == null)
            {
                this.addActionError(this.getText("bankCardRecord.createDate.empty"));
                flag = true;
            }
            
            
            if (flag)
            {
                cardNos  = this.initCardNoMap();
                
                hourList     = this.initHours();
                minuteList   = this.initMinutes();
                
            }
        }
        catch (Exception e)
        {
            log.error("validateSaveTransfer(): ");
            log.error("Error occur on validateSaveTransfer bank card record", e);
        }
    }
    
    
    public String saveTransfer()
    {
        try
        {
            BankCardHolder source = bankCardService.getBankCardByKey(bankCardRecord.getBcOid());
            BankCardHolder dest = bankCardService.getBankCardByKey(target);
            
            bankCardRecord.setBcrOid(bankCardRecordService.getOid());
            bankCardRecord.setCtlStatus(VALUE_BANK_CARD_RECORD_CTLSTATUS_COMPLETE);
            bankCardRecord.setBcrType(VALUE_BANK_CARD_RECORD_BCRTYPE_WITHDRAW);
            bankCardRecord.setCreateDate(this.handleDate());
            bankCardRecord.setDescription("\u8f6c\u51fa\uff0c \u91d1\u989d\uff1a" + bankCardRecord.getAmount() + ", \u81f3\u5361\u53f7\uff1a" + dest.getCardNo());
            
            BankCardRecordHolder destRecord = new BankCardRecordHolder();
            destRecord.setBcrOid(bankCardRecordService.getOid());
            destRecord.setBcrType(VALUE_BANK_CARD_RECORD_BCRTYPE_DEPOSITE);
            destRecord.setAmount(bankCardRecord.getAmount());
            destRecord.setDescription("\u8f6c\u5165\uff0c \u91d1\u989d\uff1a" + bankCardRecord.getAmount() + ", \u4ece\u5361\u53f7\uff1a" + source.getCardNo());
            
            destRecord.setCtlStatus(VALUE_BANK_CARD_RECORD_CTLSTATUS_COMPLETE);
            destRecord.setCreateDate(bankCardRecord.getCreateDate());
            destRecord.setBcOid(dest.getBcOid());
            
            source.setBalance(source.getBalance().subtract(bankCardRecord.getAmount()));
            dest.setBalance(dest.getBalance().add(bankCardRecord.getAmount()));
            
            bankCardRecordService.transfer(source, dest, bankCardRecord, destRecord);
            
            
            MessageTargetHolder target_ = new MessageTargetHolder("initBankCardRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("bankCardRecord.transfer.success",
                    new String[] { source.getCardNo(),
                            bankCardRecord.getAmount().toString(), dest.getCardNo()}));
        }
        catch (Exception e)
        {
            log.error("saveTransfer(): ");
            log.error("Error occur on saveTransfer bank card record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // private method
    //*****************************************************
    
    private Map initCardNoMap() throws Exception
    {
        Map rlt = new HashMap();
        
        FamilyHolder family = familyService.getFamilyByUserOid(this.getLoginOfUser().getUserOid());
        
        List<UserProfileHolder> users = familyService.getUsersByFamilyOid(family.getFamilyOid());
        
        for (UserProfileHolder user : users)
        {
            List<BankCardHolder> cards = bankCardService.getBankCarsByUser(user.getUserOid());
            
            for (BankCardHolder card : cards)
            {
                if (!bankCardRecord.getBcOid().equals(card.getBcOid()))
                {
                    rlt.put(card.getBcOid(), user.getUserName()+"---" + card.getCardNo());
                }
            }
        }
        
        return rlt;
    }
    
    
    private Map initBcrTypeMap()
    {
        Map map = new HashMap();
        
        map.put(VALUE_BANK_CARD_RECORD_BCRTYPE_DEPOSITE, this.getText("label.bankCardRecord.bcrType.deposite"));
        map.put(VALUE_BANK_CARD_RECORD_BCRTYPE_WITHDRAW, this.getText("label.bankCardRecord.bcrType.withdraw"));
        
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
    
    public BankCardRecordHolder getBankCardRecord()
    {
        return bankCardRecord;
    }


    public void setBankCardRecord(BankCardRecordHolder bankCardRecord)
    {
        this.bankCardRecord = bankCardRecord;
    }


    public List<String> getResOids()
    {
        return resOids;
    }


    public void setResOids(List<String> resOids)
    {
        this.resOids = resOids;
    }


    public void setBankCardRecordService(BankCardRecordService bankCardRecordService)
    {
        this.bankCardRecordService = bankCardRecordService;
    }


    public BankCardHolder getBankCard()
    {
        return bankCard;
    }


    public void setBankCard(BankCardHolder bankCard)
    {
        this.bankCard = bankCard;
    }


    public void setBankCardService(BankCardService bankCardService)
    {
        this.bankCardService = bankCardService;
    }


    public void setUserProfileService(UserProfileService userProfileService)
    {
        this.userProfileService = userProfileService;
    }


    public Map getBcrTypes()
    {
        return bcrTypes;
    }


    public void setBcrTypes(Map bcrTypes)
    {
        this.bcrTypes = bcrTypes;
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


    public Date getParamDate()
    {
        return paramDate;
    }


    public void setParamDate(Date paramDate)
    {
        this.paramDate = paramDate;
    }


    public Map getCardNos()
    {
        return cardNos;
    }


    public void setCardNos(Map cardNos)
    {
        this.cardNos = cardNos;
    }


    public void setFamilyService(FamilyService familyService)
    {
        this.familyService = familyService;
    }


    public BigDecimal getTarget()
    {
        return target;
    }


    public void setTarget(BigDecimal target)
    {
        this.target = target;
    }
}
