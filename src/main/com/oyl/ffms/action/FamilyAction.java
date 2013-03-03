package com.oyl.ffms.action;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.base.holder.MessageTargetHolder;
import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.service.CategoryService;
import com.oyl.ffms.service.FamilyService;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.base.util.ErrorMsgHelper;

public class FamilyAction extends ProjectBaseAction
{
    private FamilyHolder param_;
    private List<String> resOids;
    
    private FamilyService familyService;
    private CategoryService categoryService;
    
    public FamilyAction()
    {
        initMsg();
        
        //default target
        MessageTargetHolder target_ = new MessageTargetHolder("initFamily",getText("button.ok"));
        target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
        
        msg.addMessageTarget(target_);
        msg.setTitle(getText("label.common.summary"));
    }
    
    public String init()
    {
        try
        {
            clearSearchParameter(SESSION_KEY_SEARCH_PARAMETER_FAMILY);
            
            if (param_ == null)
            {
                if (getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_FAMILY) != null)
                {
                    param_ = (FamilyHolder)getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_FAMILY);
                }
                else
                {
                    param_ = new FamilyHolder();
                } 
                
            }
            else
            {
                param_.setAllEmptyStringToNull();
                getSessionMap().put(SESSION_KEY_SEARCH_PARAMETER_FAMILY, param_);   
            }
            
            this.obtainListRecordsOfPagination((BaseService) familyService,
                    param_, SORTING_MAP_FAMILY, this.getPageSize());
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
    // create family
    //*****************************************************
    
    public String initAdd()
    {
        return SUCCESS;
    }
    
    public void validateSaveAdd()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if (param_ != null)
            {
                param_.trimAllString();
                param_.setAllEmptyStringToNull();
            }
            
            
            if(!flag && (param_==null || param_.getFamilyName()==null || param_.getFamilyName().equals(BLANK_STR)))
            {
                this.addActionError(this.getText("family.name.empty"));
                flag = true;
            }
            
            if(!flag && familyService.isFamilyExist(param_.getFamilyName().trim()))
            {
                this.addActionError(this.getText("family.name.exist", new String[]{param_.getFamilyName().trim()}));
                flag = true;
            }
            
            if(!flag && (null == param_.getAddress() || BLANK_STR.equals(param_.getAddress())))
            {
                this.addActionError(this.getText("family.address.empty"));
                flag = true;
            }
            
            if(true)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveAdd(): ");
            log.error("Error occur on validateSaveAdd family", e);
        }
    }
    
    public String saveAdd()
    {
        try
        {
            param_.setFamilyOid(familyService.getOid());
            param_.setFamilyName(param_.getFamilyName().trim());
            
            ((DBActionService)familyService).insert(this.getCommonParameter(), param_);
            
            
            MessageTargetHolder target_ = new MessageTargetHolder("initFamily",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("family.create.success", new String[]{param_.getFamilyName()}));
        }
        catch(Exception e)
        {
            log.error("saveAdd(): ");
            log.error("Error occur on saveAdd family", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return FORWARD_COMMON_MESSAGE;
    }

    //*****************************************************
    // edit family
    //*****************************************************
    
    public String initEdit()
    {
        try
        {
            param_ = familyService.getFamilyByKey(param_.getFamilyOid());
        }
        catch(Exception e)
        {
            log.error("initEdit(): ");
            log.error("Error occur on initEdit family", e);
            
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
            if (param_ != null)
            {
                param_.trimAllString();
                param_.setAllEmptyStringToNull();
            }
            
            FamilyHolder oldObj_ = familyService.getFamilyByKey(param_.getFamilyOid());
            
            if(!flag && (param_==null || param_.getFamilyName()==null || param_.getFamilyName().equals(BLANK_STR)))
            {
                this.addActionError(this.getText("family.name.empty"));
                flag = true;
            }
            
            if(!flag && familyService.isFamilyExist(param_.getFamilyName().trim()) 
                    && !oldObj_.getFamilyName().equalsIgnoreCase(param_.getFamilyName().trim()))
            {
                this.addActionError(this.getText("family.name.exist", new String[]{param_.getFamilyName().trim()}));
                flag = true;
            }
            
            
            if(!flag && (null == param_.getAddress() || BLANK_STR.equals(param_.getAddress())))
            {
                this.addActionError(this.getText("family.address.empty"));
                flag = true;
            }
            
            if(true)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveEdit(): ");
            log.error("Error occur on validateSaveEdit family", e);
        }
    }
    
    public String saveEdit()
    {
        try
        {
            FamilyHolder oldObj_ = familyService.getFamilyByKey(param_.getFamilyOid());
            
            ((DBActionService)familyService).update(this.getCommonParameter(), param_, oldObj_);
            
            
            MessageTargetHolder target_ = new MessageTargetHolder("initFamily",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("family.edit.success"));
        }
        catch(Exception e)
        {
            log.error("saveEdit(): ");
            log.error("Error occur on saveEdit family", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // delete family
    //*****************************************************
    
    public void validateSaveDelete()
    {
        boolean flag = this.hasErrors();
        
        try
        {
            if(!flag && (resOids==null || resOids.size()==0))
            {
                this.addActionError(this.getText("family.select.no.one"));
                flag = true;
            }
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveAdd(): ");
            log.error("Error occur on validateSaveAdd family", e);
        }
    }
    
    public String saveDelete()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                BigDecimal familyOid = new BigDecimal(resOids.get(i));
                
                FamilyHolder old_ = familyService.getFamilyByKey(familyOid);
                
                if(familyService.isFamilyHasUsers(familyOid))
                {
                    msg.saveError(this.getText("family.user.not.empty", new String[]{old_.getFamilyName()}));
                    continue;
                }
                
                List categories = categoryService.getCategoriesByFamily(old_.getFamilyOid());
                if(categories!=null && categories.size()>0 )
                {
                    msg.saveError(this.getText("family.category.not.empty", new String[]{old_.getFamilyName()}));
                    continue;
                }
                
                try
                {
                    ((DBActionService)familyService).delete(this.getCommonParameter(), old_);
                    msg.saveSuccess(this.getText("family.delete.success", new String[]{old_.getFamilyName()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initFamily",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveDelete(): ");
            log.error("Error occur on saveDelete family", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setFamilyService(FamilyService familyService)
    {
        this.familyService = familyService;
    }

    
    public FamilyHolder getParam()
    {
        return param_;
    }

    
    public void setParam(FamilyHolder param_)
    {
        this.param_ = param_;
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
}
