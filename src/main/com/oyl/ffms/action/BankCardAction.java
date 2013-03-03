package com.oyl.ffms.action;

import java.math.BigDecimal;
import java.util.List;

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
import com.oyl.ffms.util.CommonConstants;

public class BankCardAction extends ProjectBaseAction implements
        CommonConstants
{
    private BankCardHolder bankCard;
    private List<String> resOids;
    
    private List<UserProfileHolder> users;
    
    private BankCardService bankCardService;
    private BankCardRecordService bankCardRecordService;
    private FamilyService familyService;
    
   
    public BankCardAction()
    {
        initMsg();
        
        //default target
        MessageTargetHolder target_ = new MessageTargetHolder("initBankCard",getText("button.ok"));
        target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
        
        msg.addMessageTarget(target_);
        msg.setTitle(getText("label.common.summary"));
    }
    
    
    public String init()
    {
        try
        {
            clearSearchParameter(SESSION_KEY_SEARCH_PARAMETER_BANK_CARD);
            
            if (bankCard == null)
            {
                if (getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_BANK_CARD) != null)
                {
                    bankCard = (BankCardHolder)getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_BANK_CARD);
                }
                else
                {
                    bankCard = new BankCardHolder();
                    bankCard.setCurUserOid(this.getLoginOfUser().getUserOid());
                }
            }
            else
            {
                bankCard.trimAllString();
                bankCard.setAllEmptyStringToNull();
                bankCard.setCurUserOid(this.getLoginOfUser().getUserOid());
                
                getSessionMap().put(SESSION_KEY_SEARCH_PARAMETER_BANK_CARD, bankCard);
            }
            
            this.obtainListRecordsOfPagination((BaseService) bankCardService,
                    bankCard, SORTING_MAP_BANK_CARD, this.getPageSize());
        }
        catch (Exception e)
        {
            log.error("init(): ");
            log.error("Error occur on init BankCard", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return SUCCESS;
    }

    //*****************************************************
    // create bank card
    //*****************************************************
    
    public String initAdd()
    {
        try
        {
            users = this.initUsers();
        }
        catch (Exception e)
        {
            log.error("initAdd(): ");
            log.error("Error occur on initAdd bank card", e);
            
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
            if (bankCard != null)
            {
                bankCard.trimAllString();
            }
            
            
            if (!flag && (bankCard == null || BLANK_STR.equals(bankCard.getCardNo())))
            {
                this.addActionError(this.getText("bankCard.cardNo.empty"));
                flag = true;
            }
            
            
            if (!flag && !bankCard.getCardNo().matches("[0-9]+"))
            {
                this.addActionError(this.getText("bankCard.cardNo.invalid"));
                flag = true;
            }
            
            
            if (!flag && (bankCardService.getBankCardByCardNo(bankCard.getCardNo()) != null))
            {
                this.addActionError(this.getText("bankCard.cardNo.exist", new String[]{bankCard.getCardNo()}));
                flag = true;
            }
            
            
            if (!flag &&  null == bankCard.getBalance())
            {
                this.addActionError(this.getText("bankCard.balance.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (bankCard.getBalance().compareTo(new BigDecimal(0)) < 0))
            {
                this.addActionError(this.getText("bankCard.balance.lessthanzero"));
                flag = true;
            }
            
            
            if (flag)
            {
                users = this.initUsers();
            }
        }
        catch (Exception e)
        {
            log.error("validateSaveAdd(): ");
            log.error("Error occur on validateSaveAdd bank card", e);
        }
    }
    
    
    public String saveAdd()
    {
        try
        {
            bankCard.setBcOid(bankCardService.getOid());
            
            bankCard.trimAllString();
            bankCard.setAllEmptyStringToNull();
            ((DBActionService)bankCardService).insert(this.getCommonParameter(), bankCard);
            
            MessageTargetHolder target_ = new MessageTargetHolder("initBankCard",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("bankCard.create.success", new String[]{bankCard.getCardNo()}));
        }
        catch (Exception e)
        {
            log.error("saveAdd(): ");
            log.error("Error occur on saveAdd bank card", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // edit bank card
    //*****************************************************
    
    public String initEdit()
    {
        try
        {
            users = this.initUsers();
            
            bankCard = bankCardService.getBankCardByKey(bankCard.getBcOid());
        }
        catch (Exception e)
        {
            log.error("initEdit(): ");
            log.error("Error occur on initEdit bank card", e);
            
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
            if (bankCard != null)
            {
                bankCard.trimAllString();
            }
            
            BankCardHolder oldObj = bankCardService.getBankCardByKey(bankCard.getBcOid());
            
            
            if (!flag && (bankCard == null || BLANK_STR.equals(bankCard.getCardNo())))
            {
                this.addActionError(this.getText("bankCard.cardNo.empty"));
                flag = true;
            }
            
            
            if (!flag && !bankCard.getCardNo().matches("[0-9]+"))
            {
                this.addActionError(this.getText("bankCard.cardNo.invalid"));
                flag = true;
            }
            
            
            if (!flag && (bankCardService.getBankCardByCardNo(bankCard.getCardNo()) != null))
            {
                if (!bankCard.getCardNo().equalsIgnoreCase(oldObj.getCardNo()))
                {
                    this.addActionError(this.getText("bankCard.cardNo.exist", new String[]{bankCard.getCardNo()}));
                    flag = true;
                }
            }
            
            
            if (!flag &&  null == bankCard.getBalance())
            {
                this.addActionError(this.getText("bankCard.balance.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (bankCard.getBalance().compareTo(new BigDecimal(0)) < 0))
            {
                this.addActionError(this.getText("bankCard.balance.lessthanzero"));
                flag = true;
            }
            
            
            if (flag)
            {
                users = this.initUsers();
            }
        }
        catch (Exception e)
        {
            log.error("validateSaveAdd(): ");
            log.error("Error occur on validateSaveEdit bank card", e);
        }
    }
    
    
    public String saveEdit()
    {
        try
        {
            BankCardHolder oldObj = bankCardService.getBankCardByKey(bankCard.getBcOid());
            
            bankCard.trimAllString();
            bankCard.setAllEmptyStringToNull();
            ((DBActionService)bankCardService).update(this.getCommonParameter(), bankCard, oldObj);
            
            MessageTargetHolder target_ = new MessageTargetHolder("initBankCard",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("bankCard.edit.success"));
        }
        catch (Exception e)
        {
            log.error("saveEdit(): ");
            log.error("Error occur on saveEdit bank card", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // delete bank card
    //*****************************************************
    
    public void validateSaveDelete()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if(!flag && (resOids==null || resOids.size()==0))
            {
                this.addActionError(this.getText("bankCard.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveDelete(): ");
            log.error("Error occur on validateSaveDelete bank card", e);
        }
    }
    
    
    public String saveDelete()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                BankCardHolder oldObj_ = bankCardService.getBankCardByKey(new BigDecimal(resOids.get(i).toString()));
                
                List<BankCardRecordHolder> rlt = bankCardRecordService.getBankCardRecordsByBankCard(oldObj_.getBcOid());
                
                if (rlt != null && !rlt.isEmpty())
                {
                    msg.saveSuccess(this.getText("bankCard.delete.failed.recordnotnull", new String[]{oldObj_.getCardNo()}));
                    continue;
                }

                
                if (bankCardService.isBankCardUsedBySalaryRecord(oldObj_.getBcOid()))
                {
                    msg.saveSuccess(this.getText("bankCard.delete.failed.used.by.salaryrecord", new String[]{oldObj_.getCardNo()}));
                    continue;
                }
                
                
                try
                {
                    ((DBActionService)bankCardService).delete(this.getCommonParameter(), oldObj_);
                    msg.saveSuccess(this.getText("bankCard.delete.success", new String[]{oldObj_.getCardNo()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initBankCard",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveDelete(): ");
            log.error("Error occur on saveDelete bank card", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // private method
    //*****************************************************
    
    private List<UserProfileHolder> initUsers() throws Exception
    {
        FamilyHolder family = familyService.getFamilyByUserOid(this
                .getLoginOfUser().getUserOid());

        return familyService.getUsersByFamilyOid(family.getFamilyOid());
    }
    
    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public BankCardHolder getBankCard()
    {
        return bankCard;
    }


    public void setBankCard(BankCardHolder bankCard)
    {
        this.bankCard = bankCard;
    }


    public List<String> getResOids()
    {
        return resOids;
    }


    public void setResOids(List<String> resOids)
    {
        this.resOids = resOids;
    }


    public void setBankCardService(BankCardService bankCardService)
    {
        this.bankCardService = bankCardService;
    }


    public List<UserProfileHolder> getUsers()
    {
        return users;
    }


    public void setFamilyService(FamilyService familyService)
    {
        this.familyService = familyService;
    }


    public void setBankCardRecordService(BankCardRecordService bankCardRecordService)
    {
        this.bankCardRecordService = bankCardRecordService;
    }
    
    
}
