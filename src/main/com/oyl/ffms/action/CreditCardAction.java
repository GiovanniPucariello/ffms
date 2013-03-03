package com.oyl.ffms.action;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.base.holder.MessageTargetHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.base.util.ErrorMsgHelper;
import com.oyl.ffms.holder.CreditCardHolder;
import com.oyl.ffms.holder.CreditCardRecordHolder;
import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.holder.UserProfileHolder;
import com.oyl.ffms.service.CreditCardRecordService;
import com.oyl.ffms.service.CreditCardService;
import com.oyl.ffms.service.FamilyService;
import com.oyl.ffms.util.CommonConstants;

public class CreditCardAction extends ProjectBaseAction implements
        CommonConstants
{
    private CreditCardHolder creditCard;
    private List<String> resOids;
    
    private List<UserProfileHolder> users;
    
    
    private CreditCardService creditCardService;
    private CreditCardRecordService creditCardRecordService;
    private FamilyService familyService;
    
    public CreditCardAction()
    {
        initMsg();
        
        //default target
        MessageTargetHolder target_ = new MessageTargetHolder("initCreditCard",getText("button.ok"));
        target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
        
        msg.addMessageTarget(target_);
        msg.setTitle(getText("label.common.summary"));
    }
    
    
    public String init()
    {
        try
        {
            clearSearchParameter(SESSION_KEY_SEARCH_PARAMETER_CREDIT_CARD);
            
            if (creditCard == null)
            {
                if (getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_CREDIT_CARD) != null)
                {
                    creditCard = (CreditCardHolder)getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_CREDIT_CARD);
                }
                else
                {
                    creditCard = new CreditCardHolder();
                    creditCard.setCurUserOid(this.getLoginOfUser().getUserOid());
                }
            }
            else
            {
                creditCard.trimAllString();
                creditCard.setAllEmptyStringToNull();
                creditCard.setCurUserOid(this.getLoginOfUser().getUserOid());
                
                getSessionMap().put(SESSION_KEY_SEARCH_PARAMETER_CREDIT_CARD, creditCard);
            }
            
            this.obtainListRecordsOfPagination((BaseService) creditCardService,
                    creditCard, SORTING_MAP_CREDIT_CARD, this.getPageSize());
        }
        catch (Exception e)
        {
            log.error("init(): ");
            log.error("Error occur on init CreditCard", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return SUCCESS;
    }
    
    
    //*****************************************************
    // create credit card
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
            log.error("Error occur on initAdd credit card", e);
            
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
            if (creditCard != null)
            {
                creditCard.trimAllString();
            }
            
            
            if (!flag && (creditCard == null || BLANK_STR.equals(creditCard.getCardNo())))
            {
                this.addActionError(this.getText("creditCard.cardNo.empty"));
                flag = true;
            }
            
            
            if (!flag && !creditCard.getCardNo().matches("[0-9]+"))
            {
                this.addActionError(this.getText("creditCard.cardNo.invalid"));
                flag = true;
            }
            
            
            if (!flag && (creditCardService.getCreditCardByCardNo(creditCard.getCardNo()) != null))
            {
                this.addActionError(this.getText("creditCard.cardNo.exist", new String[]{creditCard.getCardNo()}));
                flag = true;
            }
            
            
            if (!flag &&  null == creditCard.getQuota())
            {
                this.addActionError(this.getText("creditCard.quota.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (creditCard.getQuota().compareTo(new BigDecimal(0)) <= 0))
            {
                this.addActionError(this.getText("creditCard.quota.lessthanzero"));
                flag = true;
            }
            
            
            if (!flag && null == creditCard.getDebt())
            {
                this.addActionError(this.getText("creditCard.debt.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (creditCard.getDebt().compareTo(new BigDecimal(0)) < 0))
            {
                this.addActionError(this.getText("creditCard.debt.lessthanzero"));
                flag = true;
            }
            
            
            /*if (!flag && (creditCard.getDebt().compareTo(creditCard.getQuota()) > 0))
            {
                this.addActionError(this.getText("creditCard.debt.lagerthan.quota"));
                flag = true;
            }*/
            
            
            if (!flag && null == creditCard.getPoint())
            {
                this.addActionError(this.getText("creditCard.point.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (creditCard.getPoint().compareTo(new BigDecimal(0)) < 0))
            {
                this.addActionError(this.getText("creditCard.point.lessthanzero"));
                flag = true;
            }
            
            
            if (!flag && null == creditCard.getPointCondition())
            {
                this.addActionError(this.getText("creditCard.pointCondition.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (creditCard.getPointCondition().compareTo(new BigDecimal(0)) <= 0))
            {
                this.addActionError(this.getText("creditCard.pointCondition.lessthanzero"));
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
            log.error("Error occur on validateSaveAdd credit card", e);
        }
    }
    
    
    public String saveAdd()
    {
        try
        {
            creditCard.setCcOid(creditCardService.getOid());
            
            creditCard.trimAllString();
            creditCard.setAllEmptyStringToNull();
            ((DBActionService)creditCardService).insert(this.getCommonParameter(), creditCard);
            
            MessageTargetHolder target_ = new MessageTargetHolder("initCreditCard",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("creditCard.create.success", new String[]{creditCard.getCardNo()}));
        }
        catch (Exception e)
        {
            log.error("saveAdd(): ");
            log.error("Error occur on saveAdd credit card", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }

    
    //*****************************************************
    // edit credit card
    //*****************************************************
    
    public String initEdit()
    {
        try
        {
            users = this.initUsers();
            
            creditCard = creditCardService.getCreditCardByKey(creditCard.getCcOid());
        }
        catch (Exception e)
        {
            log.error("initEdit(): ");
            log.error("Error occur on initEdit credit card", e);
            
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
            if (creditCard != null)
            {
                creditCard.trimAllString();
            }
            
            CreditCardHolder oldObj = creditCardService.getCreditCardByKey(creditCard.getCcOid());
            
            
            if (!flag && (creditCard == null || BLANK_STR.equals(creditCard.getCardNo())))
            {
                this.addActionError(this.getText("creditCard.cardNo.empty"));
                flag = true;
            }
            
            
            if (!flag && !creditCard.getCardNo().matches("[0-9]+"))
            {
                this.addActionError(this.getText("creditCard.cardNo.invalid"));
                flag = true;
            }
            
            
            if (!flag && (creditCardService.getCreditCardByCardNo(creditCard.getCardNo()) != null))
            {
                if (!creditCard.getCardNo().equalsIgnoreCase(oldObj.getCardNo()))
                {
                    this.addActionError(this.getText("creditCard.cardNo.exist", new String[]{creditCard.getCardNo()}));
                    flag = true;
                }
            }
            
            
            if (!flag &&  null == creditCard.getQuota())
            {
                this.addActionError(this.getText("creditCard.quota.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (creditCard.getQuota().compareTo(new BigDecimal(0)) <= 0))
            {
                this.addActionError(this.getText("creditCard.quota.lessthanzero"));
                flag = true;
            }
            
            
            if (!flag && null == creditCard.getDebt())
            {
                this.addActionError(this.getText("creditCard.debt.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (creditCard.getDebt().compareTo(new BigDecimal(0)) < 0))
            {
                this.addActionError(this.getText("creditCard.debt.lessthanzero"));
                flag = true;
            }
            
            
            /*if (!flag && (creditCard.getDebt().compareTo(creditCard.getQuota()) > 0))
            {
                this.addActionError(this.getText("creditCard.debt.lagerthan.quota"));
                flag = true;
            }*/
            
            
            if (!flag && null == creditCard.getPoint())
            {
                this.addActionError(this.getText("creditCard.point.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (creditCard.getPoint().compareTo(new BigDecimal(0)) < 0))
            {
                this.addActionError(this.getText("creditCard.point.lessthanzero"));
                flag = true;
            }
            
            
            if (!flag && null == creditCard.getPointCondition())
            {
                this.addActionError(this.getText("creditCard.pointCondition.empty"));
                flag = true;
            }
            
            
            if (!flag &&  (creditCard.getPointCondition().compareTo(new BigDecimal(0)) <= 0))
            {
                this.addActionError(this.getText("creditCard.pointCondition.lessthanzero"));
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
            log.error("Error occur on validateSaveEdit credit card", e);
        }
    }
    
    
    public String saveEdit()
    {
        try
        {
            CreditCardHolder oldObj = creditCardService.getCreditCardByKey(creditCard.getCcOid());
            
            creditCard.trimAllString();
            creditCard.setAllEmptyStringToNull();
            ((DBActionService)creditCardService).update(this.getCommonParameter(), creditCard, oldObj);
            
            MessageTargetHolder target_ = new MessageTargetHolder("initCreditCard",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("creditCard.edit.success"));
        }
        catch (Exception e)
        {
            log.error("saveEdit(): ");
            log.error("Error occur on saveEdit credit card", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // delete credit card
    //*****************************************************
    
    public void validateSaveDelete()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if(!flag && (resOids==null || resOids.size()==0))
            {
                this.addActionError(this.getText("creditCard.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveDelete(): ");
            log.error("Error occur on validateSaveDelete credit card", e);
        }
    }
    
    
    public String saveDelete()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                CreditCardHolder oldObj_ = creditCardService.getCreditCardByKey(new BigDecimal(resOids.get(i).toString()));
                
                List<CreditCardRecordHolder> rlt = creditCardRecordService.getCreditCardRecordsByCreditCard(oldObj_.getCcOid());
                
                if (rlt != null && !rlt.isEmpty())
                {
                    msg.saveSuccess(this.getText("creditCard.delete.failed.recordnotnull", new String[]{oldObj_.getCardNo()}));
                    continue;
                }
                
                
                if (creditCardService.isCreditCardUsedByAnyItem(oldObj_.getCcOid()))
                {
                    msg.saveSuccess(this.getText("creditCard.delete.failed.used.by.items", new String[]{oldObj_.getCardNo()}));
                    continue;
                }
                
                
                try
                {
                    ((DBActionService)creditCardService).delete(this.getCommonParameter(), oldObj_);
                    msg.saveSuccess(this.getText("creditCard.delete.success", new String[]{oldObj_.getCardNo()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initCreditCard",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveDelete(): ");
            log.error("Error occur on saveDelete credit card", e);
            
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
    
    public CreditCardHolder getCreditCard()
    {
        return creditCard;
    }


    public void setCreditCard(CreditCardHolder creditCard)
    {
        this.creditCard = creditCard;
    }


    public List<String> getResOids()
    {
        return resOids;
    }


    public void setResOids(List<String> resOids)
    {
        this.resOids = resOids;
    }


    public List<UserProfileHolder> getUsers()
    {
        return users;
    }


    public void setUsers(List<UserProfileHolder> users)
    {
        this.users = users;
    }


    public void setCreditCardService(CreditCardService creditCardService)
    {
        this.creditCardService = creditCardService;
    }


    public void setFamilyService(FamilyService familyService)
    {
        this.familyService = familyService;
    }


    public void setCreditCardRecordService(
            CreditCardRecordService creditCardRecordService)
    {
        this.creditCardRecordService = creditCardRecordService;
    }

}
