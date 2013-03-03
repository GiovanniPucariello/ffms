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
import com.oyl.ffms.holder.CategoryHolder;
import com.oyl.ffms.holder.CreditCardHolder;
import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.holder.ItemHolder;
import com.oyl.ffms.holder.UserProfileHolder;
import com.oyl.ffms.service.BankCardService;
import com.oyl.ffms.service.CategoryService;
import com.oyl.ffms.service.CreditCardService;
import com.oyl.ffms.service.FamilyService;
import com.oyl.ffms.service.ItemService;
import com.oyl.ffms.util.CommonConstants;

public class ItemAction extends ProjectBaseAction implements CommonConstants
{
    private ItemHolder param_;
    private List<String> resOids;
    private Date paramDate;

	private int paramHour;
    private int paramMinute;
    private String isFromCreditCard;
    private String isFromBankCard;
    

	private List<CategoryHolder> categories;
    private List<UserProfileHolder> users;
    private Map creditCards;
    private Map bankCards;
    private Map  itemStatusList;
    private List itemQuantity;
    private List hourList;
    private List minuteList;
    
    private ItemService itemService;
    private CategoryService categoryService;
    private FamilyService familyService;
    private CreditCardService creditCardService;
    private BankCardService bankCardService;
    
    public ItemAction()
    {
        initMsg();
        
        //default target
        MessageTargetHolder target_ = new MessageTargetHolder("initItem",getText("button.ok"));
        target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
        
        msg.addMessageTarget(target_);
        msg.setTitle(getText("label.common.summary"));
    }

    
    public String init()
    {
        try
        {
        	FamilyHolder family = familyService.getFamilyByUserOid(this.getLoginOfUser().getUserOid());
        	users = familyService.getUsersByFamilyOid(family.getFamilyOid());
        	itemStatusList = this.initItemStatus();
        	
            clearSearchParameter(SESSION_KEY_SEARCH_PARAMETER_ITEM);
            
            if (param_ == null)
            {
                if (getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_ITEM) != null)
                {
                    param_ = (ItemHolder)getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_ITEM);
                }
                else
                {
                    param_ = new ItemHolder();
                    param_.setUserOid(this.getLoginOfUser().getUserOid());
                } 
                
            }
            else
            {
                param_.setAllEmptyStringToNull();
                param_.setUserOid(this.getLoginOfUser().getUserOid());
                
                if (new BigDecimal(-1).equals(param_.getSelectedUserOid()))
                    param_.setSelectedUserOid(null);
                
                if ("-1".equals(param_.getCtlStatus()))
                    param_.setCtlStatus(null);
                
                if (null != param_.getFromDate())
                {
                    Date from = param_.getFromDate();
                    Calendar c = Calendar.getInstance();
                    c.setTime(from);
                    
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    
                    param_.setFromDate(c.getTime());
                }
                
                if (null != param_.getToDate())
                {
                    Date to = param_.getToDate();
                    Calendar c = Calendar.getInstance();
                    c.setTime(to);
                    
                    c.set(Calendar.HOUR_OF_DAY, 23);
                    c.set(Calendar.MINUTE, 59);
                    c.set(Calendar.SECOND, 59);
                    c.set(Calendar.MILLISECOND, 999);
                    
                    param_.setToDate(c.getTime());
                }
                
                
                getSessionMap().put(SESSION_KEY_SEARCH_PARAMETER_ITEM, param_);
            }
            
            this.obtainListRecordsOfPagination((BaseService) itemService,
                    param_, SORTING_MAP_ITEM, this.getPageSize());
        }
        catch(Exception e)
        {
            log.error("init(): ");
            log.error("Error occur on init ", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return SUCCESS;
    }
    
    //*****************************************************
    // create
    //*****************************************************
    public String initAdd()
    {
        try
        {
            categories   = this.initCategories();
            itemQuantity = this.initQuantity();
            hourList     = this.initHours();
            minuteList   = this.initMinutes();
            creditCards  = this.initCreditCardNos();
            bankCards    = this.initBankCardNos();
            
            paramDate = new Date();
            
            isFromCreditCard = VALUE_NO;
            isFromBankCard = VALUE_NO;
        }
        catch(Exception e)
        {
            log.error("initAdd(): ");
            log.error("Error occur on initAdd item", e);
            
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
            if(!flag && param_.getCategoryOid()==null)
            {
                this.addActionError(this.getText("item.category.empty"));
                flag = true;
            }
            
            if(!flag && (param_.getItemDesc()==null || "".equals(param_.getItemDesc().trim())))
            {
                this.addActionError(this.getText("item.itemDesc.empty"));
                flag = true;
            }
            
            if(!flag && null==param_.getItemCost())
            {
                this.addActionError(this.getText("item.itemCost.empty"));
                flag = true;
            }
            
            if(!flag && param_.getItemCost().compareTo(new BigDecimal(0))<=0)
            {
                this.addActionError(this.getText("item.itemCost.minus"));
                flag = true;
            }
            
            if (!flag && VALUE_YES.equalsIgnoreCase(isFromCreditCard))
            {
                if (param_.getCcOid() == null)
                {
                    this.addActionError(this.getText("item.creditCard.empty"));
                    flag = true;
                }
            }
            
            if (!flag && VALUE_YES.equalsIgnoreCase(isFromCreditCard))
            {
                CreditCardHolder card = creditCardService.getCreditCardByKey(param_.getCcOid());
                
                if (card.getQuota().compareTo(param_.getItemCost().multiply(param_.getItemQuantity())) < 0)
                {
                    this.addActionError(this.getText("item.creditCard.quota.not.enough", new String[]{card.getCardNo()}));
                    flag = true;
                }
            }
            
            if (!flag && VALUE_YES.equalsIgnoreCase(isFromBankCard))
            {
                if (param_.getBcOid() == null)
                {
                    this.addActionError(this.getText("item.bankCard.empty"));
                    flag = true;
                }
            }
            
            if (!flag && VALUE_YES.equalsIgnoreCase(isFromBankCard))
            {
                BankCardHolder card = bankCardService.getBankCardByKey(param_.getBcOid());
                
                if (card.getBalance().compareTo(param_.getItemCost().multiply(param_.getItemQuantity())) < 0)
                {
                    this.addActionError(this.getText("item.bankCard.balance.not.enough", new String[]{card.getCardNo()}));
                    flag = true;
                }
            }
            
            if(flag)
            {
                categories   = this.initCategories();
                itemQuantity = this.initQuantity();
                hourList     = this.initHours();
                minuteList   = this.initMinutes();
                creditCards  = this.initCreditCardNos();
                bankCards    = this.initBankCardNos();
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveAdd(): ");
            log.error("Error occur on validateSaveAdd item", e);
        }
    }
    
    
    public String saveAdd()
    {
        try
        {
            param_.setItemOid(itemService.getOid());
            param_.setUserOid(this.getLoginOfUser().getUserOid());
            param_.setCtlStatus(VALUE_ITEM_STATUS_DRAFT);
            param_.setCreateDate(handleDate());
            
            
            CategoryHolder c = categoryService.getCategoryByKey(param_.getCategoryOid());
            
            if (c.getCategoryType().equals(VALUE_CATEGORY_TYPE_SPECIAL))
            {
                param_.setItemQuantity(new BigDecimal(1));
            }
            
            if (!(VALUE_YES.equalsIgnoreCase(isFromCreditCard)))
            {
                param_.setCcOid(null);
            }
            if (!(VALUE_YES.equalsIgnoreCase(isFromBankCard)))
            {
                param_.setBcOid(null);
            }
            
            param_.trimAllString();
            param_.setAllEmptyStringToNull();
            ((DBActionService)itemService).insert(this.getCommonParameter(), param_);

            
            MessageTargetHolder target_ = new MessageTargetHolder("initItem",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("item.create.success", new String[]{param_.getItemDesc()}));
        }
        catch(Exception e)
        {
            log.error("saveAdd(): ");
            log.error("Error occur on saveAdd item", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // edit
    //*****************************************************
    
    public String initEdit()
    {
        try
        {
            categories   = this.initCategories();
            itemQuantity = this.initQuantity();
            hourList     = this.initHours();
            minuteList   = this.initMinutes();
            creditCards  = this.initCreditCardNos();
            bankCards    = this.initBankCardNos();
            
            param_ = itemService.getItemByKey(param_.getItemOid());
            
            if (null != param_.getCcOid())
            {
                isFromCreditCard = VALUE_YES;
            }
            if (null != param_.getBcOid())
            {
                isFromBankCard = VALUE_YES;
            }
            
            Calendar c = Calendar.getInstance();
            c.setTime(param_.getCreateDate());
            
            paramDate   = c.getTime();
            paramHour   = c.get(Calendar.HOUR_OF_DAY);
            paramMinute = c.get(Calendar.MINUTE);
        }
        catch(Exception e)
        {
            log.error("initEdit(): ");
            log.error("Error occur on initEdit item", e);
            
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
            if(!flag && param_.getCategoryOid()==null)
            {
                this.addActionError(this.getText("item.category.empty"));
                flag = true;
            }
            
            if(!flag && (param_.getItemDesc()==null || "".equals(param_.getItemDesc().trim())))
            {
                this.addActionError(this.getText("item.itemDesc.empty"));
                flag = true;
            }
            
            if(!flag && null==param_.getItemCost())
            {
                this.addActionError(this.getText("item.itemCost.empty"));
                flag = true;
            }
            
            if(!flag && param_.getItemCost().compareTo(new BigDecimal(0))<=0)
            {
                this.addActionError(this.getText("item.itemCost.minus"));
                flag = true;
            }
            
            if (!flag && VALUE_YES.equalsIgnoreCase(isFromCreditCard))
            {
                if (param_.getCcOid() == null)
                {
                    this.addActionError(this.getText("item.creditCard.empty"));
                    flag = true;
                }
            }
            
            if (!flag && VALUE_YES.equalsIgnoreCase(isFromCreditCard))
            {
                CreditCardHolder card = creditCardService.getCreditCardByKey(param_.getCcOid());
                
                if (card.getQuota().compareTo(param_.getItemCost().multiply(param_.getItemQuantity())) < 0)
                {
                    this.addActionError(this.getText("item.creditCard.quota.not.enough", new String[]{card.getCardNo()}));
                    flag = true;
                }
            }
            
            if (!flag && VALUE_YES.equalsIgnoreCase(isFromBankCard))
            {
                if (param_.getBcOid() == null)
                {
                    this.addActionError(this.getText("item.bankCard.empty"));
                    flag = true;
                }
            }
            
            if (!flag && VALUE_YES.equalsIgnoreCase(isFromBankCard))
            {
                BankCardHolder card = bankCardService.getBankCardByKey(param_.getBcOid());
                
                if (card.getBalance().compareTo(param_.getItemCost().multiply(param_.getItemQuantity())) < 0)
                {
                    this.addActionError(this.getText("item.bankCard.balance.not.enough", new String[]{card.getCardNo()}));
                    flag = true;
                }
            }
            
            
            if(flag)
            {
                categories   = this.initCategories();
                itemQuantity = this.initQuantity();
                hourList     = this.initHours();
                minuteList   = this.initMinutes();
                creditCards  = this.initCreditCardNos();
                bankCards    = this.initBankCardNos();
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveEdit(): ");
            log.error("Error occur on validateSaveEdit item", e);
        }
    }
    
    
    public String saveEdit()
    {
        ItemHolder oldObj_ = null;
        
        try
        {
            oldObj_ = itemService.getItemByKey(param_.getItemOid());
            
            param_.setUserOid(oldObj_.getUserOid());
            param_.setCtlStatus(oldObj_.getCtlStatus());
            param_.setCreateDate(handleDate());
            
            CategoryHolder c = categoryService.getCategoryByKey(param_.getCategoryOid());
            
            if (c.getCategoryType().equals(VALUE_CATEGORY_TYPE_SPECIAL))
            {
                param_.setItemQuantity(new BigDecimal(1));
            }
            
            if (!(VALUE_YES.equalsIgnoreCase(isFromCreditCard)))
            {
                param_.setCcOid(null);
            }
            if (!(VALUE_YES.equalsIgnoreCase(isFromBankCard)))
            {
                param_.setBcOid(null);
            }
            
            
            param_.trimAllString();
            param_.setAllEmptyStringToNull();
            ((DBActionService)itemService).update(this.getCommonParameter(), param_, oldObj_);

            
            MessageTargetHolder target_ = new MessageTargetHolder("initItem",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("item.edit.success"));
        }
        catch(Exception e)
        {
            log.error("saveEdit(): ");
            log.error("Error occur on saveEdit item", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    //*****************************************************
    // delete
    //*****************************************************
    
    public void validateSaveDelete()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if(!flag && (resOids==null || resOids.size()==0))
            {
                this.addActionError(this.getText("item.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveDelete(): ");
            log.error("Error occur on validateSaveDelete item", e);
        }
    }
    
    
    public String saveDelete()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                ItemHolder oldObj_ = itemService.getItemByKey(new BigDecimal(resOids.get(i).toString()));
                
                if(oldObj_.getCtlStatus().equals(VALUE_ITEM_STATUS_COMPLETE))
                {
                    msg.saveError(this.getText("item.delete.error.complete", new String[]{oldObj_.getItemDesc()}));
                    continue;
                }
                
                try
                {
                    ((DBActionService)itemService).delete(this.getCommonParameter(), oldObj_);
                    msg.saveSuccess(this.getText("item.delete.success", new String[]{oldObj_.getItemDesc()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initItem",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveDelete(): ");
            log.error("Error occur on saveDelete item", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    //*****************************************************
    // confirm
    //*****************************************************
    public void validateSaveConfirm()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if(!flag && (resOids==null || resOids.size()==0))
            {
                this.addActionError(this.getText("item.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveConfirm(): ");
            log.error("Error occur on validateSaveConfirm item", e);
        }
    }
    
    
    public String saveConfirm()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                ItemHolder oldObj_ = itemService.getItemByKey(new BigDecimal(resOids.get(i).toString()));
                
                if(oldObj_.getCtlStatus().equals(VALUE_ITEM_STATUS_COMPLETE))
                {
                    msg.saveError(this.getText("item.already.confirm", new String[]{oldObj_.getItemDesc()}));
                    continue;
                }
                
                
                if (null != oldObj_.getCcOid())
                {
                    CreditCardHolder creditCard = creditCardService
                            .getCreditCardByKey(oldObj_.getCcOid());

                    if (creditCard.getQuota().compareTo(
                            oldObj_.getItemCost().multiply(
                                    oldObj_.getItemQuantity())) < 0)
                    {
                        msg.saveError(this.getText(
                                "item.confirm.fail.quota.not.enough",
                                new String[] { oldObj_.getItemDesc(),
                                        creditCard.getCardNo() }));
                        continue;
                    }
                }
                
                if (null != oldObj_.getBcOid())
                {
                    BankCardHolder bankCard = bankCardService
                            .getBankCardByKey(oldObj_.getBcOid());

                    if (bankCard.getBalance().compareTo(
                            oldObj_.getItemCost().multiply(
                                    oldObj_.getItemQuantity())) < 0)
                    {
                        msg.saveError(this.getText(
                                "item.confirm.fail.balance.not.enough",
                                new String[] { oldObj_.getItemDesc(),
                                        bankCard.getCardNo() }));
                        continue;
                    }
                }
                
                
                try
                {
                    itemService.confirmItem(oldObj_);
                    msg.saveSuccess(this.getText("item.confirm.success", new String[]{oldObj_.getItemDesc()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initItem",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveConfirm(): ");
            log.error("Error occur on saveConfirm item", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    //*****************************************************
    // private methods
    //*****************************************************
    private List initQuantity()
    {
        List rlt = new ArrayList();
        
        for(int i=1; i<21; i++)
        {
            rlt.add(Integer.toString(i));
        }
        
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
    
    
    private Map initItemStatus()
    {
        Map rlt = new HashMap();
        rlt.put(VALUE_ITEM_STATUS_COMPLETE, this.getText("label.item.ctlStatus.complete"));
        rlt.put(VALUE_ITEM_STATUS_DRAFT, this.getText("label.item.ctlStatus.draft"));
        
        return rlt;
    }
    
    
    private Map initCreditCardNos() throws Exception
    {
        Map map = new HashMap();
        
        FamilyHolder family = familyService.getFamilyByUserOid(this.getLoginOfUser().getUserOid());
        
        List<UserProfileHolder> userProfiles = familyService.getUsersByFamilyOid(family.getFamilyOid());
        
        for (UserProfileHolder user : userProfiles)
        {
            List<CreditCardHolder> cards = creditCardService.getCreditCarsByUser(user.getUserOid());
            
            for (CreditCardHolder card : cards)
            {
                map.put(card.getCcOid(), user.getUserName() + "---"
                        + card.getCardNo());
            }
        }
        
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
    
    
    private List<CategoryHolder> initCategories() throws Exception
    {
        List rlt = null;
        
        FamilyHolder family = familyService.getFamilyByUserOid(this.getLoginOfUser().getUserOid());
        
        CategoryHolder param = new CategoryHolder();
        param.setFamilyOid(family.getFamilyOid());
        
        rlt = categoryService.getCategories(param);

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
    public ItemHolder getParam()
    {
        return param_;
    }

    
    public void setParam(ItemHolder param_)
    {
        this.param_ = param_;
    }
    

    public void setItemService(ItemService itemService)
    {
        this.itemService = itemService;
    }


    public List<String> getResOids()
    {
        return resOids;
    }


    public void setResOids(List<String> resOids)
    {
        this.resOids = resOids;
    }


    public void setCategoryService(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }


    public void setFamilyService(FamilyService familyService)
    {
        this.familyService = familyService;
    }


    public List<CategoryHolder> getCategories()
    {
        return categories;
    }


    public void setCategories(List<CategoryHolder> categories)
    {
        this.categories = categories;
    }


    public List getItemQuantity()
    {
        return itemQuantity;
    }


    public void setItemQuantity(List itemQuantity)
    {
        this.itemQuantity = itemQuantity;
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


    public List getMinuteList()
    {
        return minuteList;
    }


    public Date getParamDate()
    {
        return paramDate;
    }


    public void setParamDate(Date paramDate)
    {
        this.paramDate = paramDate;
    }
    
    
    public List<UserProfileHolder> getUsers()
    {
		return users;
	}


    public Map getItemStatusList()
    {
        return itemStatusList;
    }


    public Map getCreditCards()
    {
        return creditCards;
    }


    public void setCreditCardService(CreditCardService creditCardService)
    {
        this.creditCardService = creditCardService;
    }


    public String getIsFromCreditCard()
    {
        return isFromCreditCard;
    }


    public void setIsFromCreditCard(String isFromCreditCard)
    {
        this.isFromCreditCard = isFromCreditCard;
    }


    public String getIsFromBankCard()
    {
        return isFromBankCard;
    }


    public void setIsFromBankCard(String isFromBankCard)
    {
        this.isFromBankCard = isFromBankCard;
    }


    public Map getBankCards()
    {
        return bankCards;
    }


    public void setBankCardService(BankCardService bankCardService)
    {
        this.bankCardService = bankCardService;
    }
    
}
