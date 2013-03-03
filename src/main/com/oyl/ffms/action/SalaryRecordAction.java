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
import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.holder.SalaryRecordHolder;
import com.oyl.ffms.holder.UserProfileHolder;
import com.oyl.ffms.service.FamilyService;
import com.oyl.ffms.service.SalaryRecordService;
import com.oyl.ffms.util.CommonConstants;

public class SalaryRecordAction extends ProjectBaseAction implements
        CommonConstants
{
    private SalaryRecordHolder salaryRecord;
    private List<String> resOids;
    private String isSaveToBankCard;
    
    
    private Date paramDate;
    private int paramHour;
    private int paramMinute;
    
    private List<UserProfileHolder> users;
    private Map srTypes;
    private Map salaryRecordStatus;
    private List hourList;
    private List minuteList;
    
    private SalaryRecordService salaryRecordService;
    private FamilyService familyService;
    
    public SalaryRecordAction()
    {
        initMsg();
        
        //default target
        MessageTargetHolder target_ = new MessageTargetHolder("initSalaryRecord",getText("button.ok"));
        target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
        
        msg.addMessageTarget(target_);
        msg.setTitle(getText("label.common.summary"));
    }
    
    
    public String init()
    {
        try
        {
            users = initUsers();
            salaryRecordStatus = this.initCtlStatus();
            srTypes = this.initSrTypes();
            
            clearSearchParameter(SESSION_KEY_SEARCH_PARAMETER_SALARY_RECORD);
            
            if (salaryRecord == null)
            {
                if (getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_SALARY_RECORD) != null)
                {
                    salaryRecord = (SalaryRecordHolder)getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_SALARY_RECORD);
                }
                else
                {
                    salaryRecord = new SalaryRecordHolder();
                    salaryRecord.setCurUserOid(this.getLoginOfUser().getUserOid());
                }
            }
            else
            {
                salaryRecord.trimAllString();
                salaryRecord.setAllEmptyStringToNull();
                salaryRecord.setCurUserOid(this.getLoginOfUser().getUserOid());
                
                if (new BigDecimal(-1).equals(salaryRecord.getUserOid()))
                {
                    salaryRecord.setUserOid(null);
                }
                
                if ("-1".equals(salaryRecord.getCtlStatus()))
                {
                    salaryRecord.setCtlStatus(null);
                }
                
                if ("-1".equals(salaryRecord.getSrType()))
                {
                    salaryRecord.setSrType(null);
                }
                
                if (null != salaryRecord.getFromDate())
                {
                    Date from = salaryRecord.getFromDate();
                    Calendar c = Calendar.getInstance();
                    c.setTime(from);
                    
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    
                    salaryRecord.setFromDate(c.getTime());
                }
                
                if (null != salaryRecord.getToDate())
                {
                    Date to = salaryRecord.getToDate();
                    Calendar c = Calendar.getInstance();
                    c.setTime(to);
                    
                    c.set(Calendar.HOUR_OF_DAY, 23);
                    c.set(Calendar.MINUTE, 59);
                    c.set(Calendar.SECOND, 59);
                    c.set(Calendar.MILLISECOND, 999);
                    
                    salaryRecord.setToDate(c.getTime());
                }
                
                getSessionMap().put(SESSION_KEY_SEARCH_PARAMETER_SALARY_RECORD, salaryRecord);
            }
            
            this.obtainListRecordsOfPagination((BaseService) salaryRecordService,
                    salaryRecord, SORTING_MAP_SALARY_RECORD, this.getPageSize());
            
        }
        catch (Exception e)
        {
            log.error("init(): ");
            log.error("Error occur on init Salary record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return SUCCESS;
    }
    
    //*****************************************************
    // create salary records
    //*****************************************************
    
    public String initAdd()
    {
        try
        {
            users = initUsers();
            srTypes = this.initSrTypes();
            
            hourList     = this.initHours();
            minuteList   = this.initMinutes();
            
            paramDate = new Date();
        }
        catch (Exception e)
        {
            log.error("initAdd(): ");
            log.error("Error occur on initAdd salary record", e);
            
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
            if (salaryRecord != null)
            {
                salaryRecord.trimAllString();
            }
            
            
            if (!flag && (salaryRecord == null || BLANK_STR.equals(salaryRecord.getDescription())))
            {
                this.addActionError(this.getText("salaryRecord.description.empty"));
                flag = true;
            }
            
            
            if (!flag &&  null == salaryRecord.getAmount())
            {
                this.addActionError(this.getText("salaryRecord.amount.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (salaryRecord.getAmount().compareTo(new BigDecimal(0)) < 0))
            {
                this.addActionError(this.getText("salaryRecord.amount.lessthanzero"));
                flag = true;
            }
            
            
            if (!flag && (VALUE_YES.equalsIgnoreCase(isSaveToBankCard)))
            {
                if (null == salaryRecord.getBcOid())
                {
                    this.addActionError(this.getText("salaryRecord.bankCard.empty"));
                    flag = true;
                }
            }
            
            
            if (flag)
            {
                users = initUsers();
                srTypes = this.initSrTypes();
                
                hourList     = this.initHours();
                minuteList   = this.initMinutes();
                
            }
        }
        catch (Exception e)
        {
            log.error("validateSaveAdd(): ");
            log.error("Error occur on validateSaveAdd salary record", e);
        }
    }
    
    
    public String saveAdd()
    {
        try
        {
            salaryRecord.setSrOid(salaryRecordService.getOid());
            salaryRecord.setCtlStatus(VALUE_SALARY_RECORD_STATUS_DRAFT);
            salaryRecord.setCreateDate(this.handleDate());
            if (!VALUE_YES.equalsIgnoreCase(isSaveToBankCard))
            {
                salaryRecord.setBcOid(null);
            }
            
            salaryRecord.trimAllString();
            salaryRecord.setAllEmptyStringToNull();
            ((DBActionService)salaryRecordService).insert(this.getCommonParameter(), salaryRecord);
            
            
            MessageTargetHolder target_ = new MessageTargetHolder("initSalaryRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("salaryRecord.create.success", new String[]{salaryRecord.getDescription()}));
        }
        catch (Exception e)
        {
            log.error("saveAdd(): ");
            log.error("Error occur on saveAdd salary record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // edit salary records
    //*****************************************************
    
    public String initEdit()
    {
        try
        {
            salaryRecord = salaryRecordService.getSalaryRecordByKey(salaryRecord.getSrOid());
            
            if (null != salaryRecord.getBcOid())
            {
                isSaveToBankCard = VALUE_YES;
            }
            
            users = initUsers();
            srTypes = this.initSrTypes();
            
            hourList     = this.initHours();
            minuteList   = this.initMinutes();
            
            
            Calendar c = Calendar.getInstance();
            c.setTime(salaryRecord.getCreateDate());
            
            paramDate = c.getTime();
            paramHour = c.get(Calendar.HOUR_OF_DAY);
            paramMinute = c.get(Calendar.MINUTE);
        }
        catch (Exception e)
        {
            log.error("initEdit(): ");
            log.error("Error occur on initEdit salary record", e);
            
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
            if (salaryRecord != null)
            {
                salaryRecord.trimAllString();
            }
            
            
            if (!flag && (salaryRecord == null || BLANK_STR.equals(salaryRecord.getDescription())))
            {
                this.addActionError(this.getText("salaryRecord.description.empty"));
                flag = true;
            }
            
            
            if (!flag &&  null == salaryRecord.getAmount())
            {
                this.addActionError(this.getText("salaryRecord.amount.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (salaryRecord.getAmount().compareTo(new BigDecimal(0)) < 0))
            {
                this.addActionError(this.getText("salaryRecord.amount.lessthanzero"));
                flag = true;
            }
            
            
            if (!flag && (VALUE_YES.equalsIgnoreCase(isSaveToBankCard)))
            {
                if (null == salaryRecord.getBcOid())
                {
                    this.addActionError(this.getText("salaryRecord.bankCard.empty"));
                    flag = true;
                }
            }
            
            
            if (flag)
            {
                users = initUsers();
                srTypes = this.initSrTypes();
                
                hourList     = this.initHours();
                minuteList   = this.initMinutes();
                
            }
        }
        catch (Exception e)
        {
            log.error("validateInitEdit(): ");
            log.error("Error occur on validateInitEdit salary record", e);
        }
    }
    
    
    public String saveEdit()
    {
        try
        {
            SalaryRecordHolder oldObj = salaryRecordService.getSalaryRecordByKey(salaryRecord.getSrOid());
            
            
            salaryRecord.setCtlStatus(oldObj.getCtlStatus());
            salaryRecord.setCreateDate(this.handleDate());
            if (!VALUE_YES.equalsIgnoreCase(isSaveToBankCard))
            {
                salaryRecord.setBcOid(null);
            }
            
            salaryRecord.trimAllString();
            salaryRecord.setAllEmptyStringToNull();
            ((DBActionService)salaryRecordService).update(this.getCommonParameter(), salaryRecord, oldObj);
            
            
            MessageTargetHolder target_ = new MessageTargetHolder("initSalaryRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("salaryRecord.edit.success"));
        }
        catch (Exception e)
        {
            log.error("saveEdit(): ");
            log.error("Error occur on saveEdit salary record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    //*****************************************************
    // delete salary records
    //*****************************************************
    
    
    public void validateSaveDelete()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if(!flag && (resOids==null || resOids.size()==0))
            {
                this.addActionError(this.getText("salaryRecord.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveDelete(): ");
            log.error("Error occur on validateSaveDelete salary record", e);
        }
    }
    
    
    public String saveDelete()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                SalaryRecordHolder oldObj_ = salaryRecordService.getSalaryRecordByKey(new BigDecimal(resOids.get(i).toString()));
                
                if (VALUE_SALARY_RECORD_STATUS_COMPLETE.equals(oldObj_.getCtlStatus()))
                {
                    msg.saveSuccess(this.getText("salaryRecord.delete.fail.confirm.already", new String[]{oldObj_.getDescription()}));
                    continue;
                }
                
                
                try
                {
                    ((DBActionService)salaryRecordService).delete(this.getCommonParameter(), oldObj_);
                    msg.saveSuccess(this.getText("salaryRecord.delete.success", new String[]{oldObj_.getDescription()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            
            
            
            MessageTargetHolder target_ = new MessageTargetHolder("initSalaryRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
        }
        catch (Exception e)
        {
            log.error("saveDelete(): ");
            log.error("Error occur on saveDelete salary record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // confirm salary records
    //*****************************************************
    
    public void validateSaveConfirm()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if(!flag && (resOids==null || resOids.size()==0))
            {
                this.addActionError(this.getText("salaryRecord.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveConfirm(): ");
            log.error("Error occur on validateSaveConfirm salary record", e);
        }
    }
    
    
    public String saveConfirm()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                SalaryRecordHolder oldObj_ = salaryRecordService.getSalaryRecordByKey(new BigDecimal(resOids.get(i).toString()));
                
                if (VALUE_SALARY_RECORD_STATUS_COMPLETE.equals(oldObj_.getCtlStatus()))
                {
                    msg.saveSuccess(this.getText("salaryRecord.confirm.already", new String[]{oldObj_.getDescription()}));
                    continue;
                }
                
                try
                {
                    salaryRecordService.confirmSalaryRecord(oldObj_);
                    msg.saveSuccess(this.getText("salaryRecord.confirm.success", new String[]{oldObj_.getDescription()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initSalaryRecord",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveDelete(): ");
            log.error("Error occur on saveDelete salary record", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    
    //*****************************************************
    // private methods
    //*****************************************************
    
    private List<UserProfileHolder> initUsers() throws Exception
    {
        FamilyHolder family = familyService.getFamilyByUserOid(this.getLoginOfUser().getUserOid());
        
        return familyService.getUsersByFamilyOid(family.getFamilyOid());
    }
    
    
    private Map initSrTypes()
    {
        Map rlt = new HashMap();
        
        rlt.put(VALUE_SALARY_RECORD_SRTYPE_SALARY, this.getText("label.salaryRecord.srType.salary"));
        rlt.put(VALUE_SALARY_RECORD_SRTYPE_INCENTIVE, this.getText("label.salaryRecord.srType.incentive"));
        rlt.put(VALUE_SALARY_RECORD_SRTYPE_OTHER, this.getText("\u5176\u5b83"));
        
        return rlt;
    }
    
    private Map initCtlStatus()
    {
        Map rlt = new HashMap();
        
        rlt.put(VALUE_SALARY_RECORD_STATUS_COMPLETE, this.getText("label.salaryRecord.ctlStatus.complete"));
        rlt.put(VALUE_SALARY_RECORD_STATUS_DRAFT, this.getText("label.salaryRecord.ctlStatus.draft"));
        
        return rlt;
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
    
    public SalaryRecordHolder getSalaryRecord()
    {
        return salaryRecord;
    }


    public void setSalaryRecord(SalaryRecordHolder salaryRecord)
    {
        this.salaryRecord = salaryRecord;
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


    public void setSalaryRecordService(SalaryRecordService salaryRecordService)
    {
        this.salaryRecordService = salaryRecordService;
    }


    public List<UserProfileHolder> getUsers()
    {
        return users;
    }


    public void setUsers(List<UserProfileHolder> users)
    {
        this.users = users;
    }


    public Map getSrTypes()
    {
        return srTypes;
    }


    public void setSrTypes(Map srTypes)
    {
        this.srTypes = srTypes;
    }


    public Map getSalaryRecordStatus()
    {
        return salaryRecordStatus;
    }


    public void setSalaryRecordStatus(Map salaryRecordStatus)
    {
        this.salaryRecordStatus = salaryRecordStatus;
    }


    public void setFamilyService(FamilyService familyService)
    {
        this.familyService = familyService;
    }


    public String getIsSaveToBankCard()
    {
        return isSaveToBankCard;
    }


    public void setIsSaveToBankCard(String isSaveToBankCard)
    {
        this.isSaveToBankCard = isSaveToBankCard;
    }
    
}
