package com.oyl.ffms.action;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oyl.base.holder.MessageTargetHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.base.util.ErrorMsgHelper;
import com.oyl.ffms.holder.CategoryHolder;
import com.oyl.ffms.service.CategoryService;
import com.oyl.ffms.service.FamilyService;
import com.oyl.ffms.service.ItemService;
import com.oyl.ffms.util.CommonConstants;

public class CategoryAction extends ProjectBaseAction implements
        CommonConstants
{
    private CategoryHolder param_;
    private List<String> resOids;
    
    private Map type;
    
    private CategoryService categoryService;
    private FamilyService familyService;
    private ItemService itemService;
    
    public CategoryAction()
    {
        initMsg();
        
        //default target
        MessageTargetHolder target_ = new MessageTargetHolder("initCategory",getText("button.ok"));
        target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
        
        msg.addMessageTarget(target_);
        msg.setTitle(getText("label.common.summary"));
    }
    
    
    public String init()
    {
        try
        {
            clearSearchParameter(SESSION_KEY_SEARCH_PARAMETER_CATEGORY);
            
            if (param_ == null)
            {
                if (getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_CATEGORY) != null)
                {
                    param_ = (CategoryHolder)getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_CATEGORY);
                }
                else
                {
                    param_ = new CategoryHolder();
                    param_.setUserOid(this.getLoginOfUser().getUserOid());
                } 
                
            }
            else
            {
                param_.setAllEmptyStringToNull();
                getSessionMap().put(SESSION_KEY_SEARCH_PARAMETER_CATEGORY, param_);   
            }
            
            Calendar c = Calendar.getInstance();
            if(param_.getItemFromDate()!=null)
            {
                c.setTime(param_.getItemFromDate());
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                
                param_.setItemFromDate(c.getTime());
            }
            if(param_.getItemToDate()!=null)
            {
                c.setTime(param_.getItemToDate());
                c.set(Calendar.HOUR_OF_DAY, 23);
                c.set(Calendar.MINUTE, 59);
                c.set(Calendar.SECOND, 59);
                c.set(Calendar.MILLISECOND, 999);
                
                param_.setItemToDate(c.getTime());
            }

            this.obtainListRecordsOfPagination((BaseService) categoryService,
                    param_, SORTING_MAP_CATEGORY, this.getPageSize());
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
            type = this.initCategoryType();
        }
        catch(Exception e)
        {
            log.error("initAdd(): ");
            log.error("Error occur on initAdd category", e);
            
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
            if(!flag && (param_==null || "".equals(param_.getCategoryName().trim())))
            {
                this.addActionError(this.getText("category.categoryName.empty"));
                flag = true;
            }
            
            if(!flag && categoryService.isCategoryExist(familyService.getFamilyByUserOid(
                    this.getLoginOfUser().getUserOid()).getFamilyOid(),param_.getCategoryName().trim()))
            {
                this.addActionError(this.getText("category.categoryName.exist", new String[]{param_.getCategoryName().trim()}));
                flag = true;
            }
            
            if(flag)
            {
                type = this.initCategoryType();
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveAdd(): ");
            log.error("Error occur on validateSaveAdd category", e);
        }
    }
    
    
    public String saveAdd()
    {
        try
        {
            param_.setCategoryOid(categoryService.getOid());
            param_.setFamilyOid(familyService.getFamilyByUserOid(this.getLoginOfUser().getUserOid()).getFamilyOid());
            
            param_.trimAllString();
            ((DBActionService)categoryService).insert(this.getCommonParameter(), param_);

            
            MessageTargetHolder target_ = new MessageTargetHolder("initCategory",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("category.create.success", new String[]{param_.getCategoryName()}));
        }
        catch(Exception e)
        {
            log.error("saveAdd(): ");
            log.error("Error occur on saveAdd category", e);
            
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
            param_ = categoryService.getCategoryByKey(param_.getCategoryOid());
            
            type = this.initCategoryType();
        }
        catch(Exception e)
        {
            log.error("initEdit(): ");
            log.error("Error occur on initEdit category", e);
            
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
            CategoryHolder oldObj_ = categoryService.getCategoryByKey(param_.getCategoryOid());
            
            if(!flag && (param_==null || "".equals(param_.getCategoryName().trim())))
            {
                this.addActionError(this.getText("category.categoryName.empty"));
                flag = true;
            }
            
            if(!flag && !param_.getCategoryName().equalsIgnoreCase(oldObj_.getCategoryName())
                    && categoryService.isCategoryExist(familyService.getFamilyByUserOid(
                    this.getLoginOfUser().getUserOid()).getFamilyOid(),param_.getCategoryName().trim()))
            {
                this.addActionError(this.getText("category.categoryName.exist", new String[]{param_.getCategoryName().trim()}));
                flag = true;
            }
            
            if(flag)
            {
                type = this.initCategoryType();
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveEdit(): ");
            log.error("Error occur on validateSaveEdit category", e);
        }
    }
    
    
    public String saveEdit()
    {
        try
        {
            CategoryHolder oldObj_ = categoryService.getCategoryByKey(param_.getCategoryOid());
            param_.setFamilyOid(oldObj_.getFamilyOid());
            
            ((DBActionService)categoryService).update(this.getCommonParameter(), param_, oldObj_);
            
            
            MessageTargetHolder target_ = new MessageTargetHolder("initCategory",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("category.edit.success"));
        }
        catch(Exception e)
        {
            log.error("saveEdit(): ");
            log.error("Error occur on saveEdit category", e);
            
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
                this.addActionError(this.getText("category.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveDelete(): ");
            log.error("Error occur on validateSaveDelete category", e);
        }
    }
    
    
    public String saveDelete()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                CategoryHolder old_ = categoryService.getCategoryByKey(new BigDecimal(resOids.get(i)));
                
                List items = itemService.getItemsByCategoryOid(old_.getCategoryOid());
                if(items!=null && items.size()>0)
                {
                    msg.saveError(this.getText("category.item.not.empty", new String[]{old_.getCategoryName()}));
                    continue;
                }
                
                try
                {
                    ((DBActionService)categoryService).delete(this.getCommonParameter(), old_);
                    msg.saveSuccess(this.getText("category.delete.success", new String[]{old_.getCategoryName()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initCategory",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveDelete(): ");
            log.error("Error occur on saveDelete category", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    
    //*****************************************************
    // private methods
    //*****************************************************
    
    private Map initCategoryType()
    {
        Map map = new HashMap();
        
        map.put(VALUE_CATEGORY_TYPE_NORMAL, this.getText("label.category.type.normal"));
        map.put(VALUE_CATEGORY_TYPE_SPECIAL, this.getText("label.category.type.special"));
        
        return map;
    }
    
    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public List<String> getResOids()
    {
        return resOids;
    }
    

    public void setResOids(List<String> resOids)
    {
        this.resOids = resOids;
    }

    
    public CategoryHolder getParam()
    {
        return param_;
    }

    
    public void setParam(CategoryHolder param_)
    {
        this.param_ = param_;
    }


    public void setCategoryService(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }
    
    
    public void setFamilyService(FamilyService familyService)
    {
        this.familyService = familyService;
    }


    public Map getType()
    {
        return type;
    }


    public void setType(Map type)
    {
        this.type = type;
    }


    public void setItemService(ItemService itemService)
    {
        this.itemService = itemService;
    }
    
}
