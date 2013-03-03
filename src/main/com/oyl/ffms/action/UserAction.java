package com.oyl.ffms.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oyl.base.holder.MessageTargetHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.base.util.EncodeUtil;
import com.oyl.base.util.ErrorMsgHelper;
import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.holder.UserProfileHolder;
import com.oyl.ffms.service.FamilyService;
import com.oyl.ffms.service.ItemService;
import com.oyl.ffms.service.UserProfileService;
import com.oyl.ffms.util.CommonConstants;

public class UserAction extends ProjectBaseAction implements CommonConstants
{
    private UserProfileHolder param_;
    private List<String> resOids;
    private List<FamilyHolder> familyList;
    private Map genderMap;
    
    private UserProfileService userProfileService;
    private FamilyService familyService;
    private ItemService itemService;

    
    public UserAction()
    {
        initMsg();
        
        //default target
        MessageTargetHolder target_ = new MessageTargetHolder("initUser",getText("button.ok"));
        target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
        
        msg.addMessageTarget(target_);
        msg.setTitle(getText("label.common.summary"));
    }
    
    
    public String init()
    {
        try
        {
            clearSearchParameter(SESSION_KEY_SEARCH_PARAMETER_USER_PROFILE);
            
            if (param_ == null)
            {
                if (getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_USER_PROFILE) != null)
                {
                    param_ = (UserProfileHolder)getSessionMap().get(SESSION_KEY_SEARCH_PARAMETER_USER_PROFILE);
                }
                else
                {
                    param_ = new UserProfileHolder();
                } 
                
            }
            else
            {
                param_.setAllEmptyStringToNull();
                getSessionMap().put(SESSION_KEY_SEARCH_PARAMETER_USER_PROFILE, param_);   
            }
            
            this.obtainListRecordsOfPagination((BaseService) userProfileService,
                    param_, SORTING_MAP_USER_PROFILE, this.getPageSize());
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
            familyList = familyService.getFamilies(null);
            initGenderMap();
        }
        catch (Exception e)
        {
            log.error("initAdd(): ");
            log.error("Error occur on initAdd user", e);
            
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
            if(param_ != null) param_.trimAllString();
            
            if(!flag && (param_==null || param_.getFamilyOid()==null ))
            {
                this.addActionError(this.getText("user.family.empty"));
                flag = true;
            }
            
            if(!flag && (param_.getUserName()==null || param_.getUserName().equals(BLANK_STR)))
            {
                this.addActionError(this.getText("user.userName.empty"));
                flag = true;
            }
            
            if(!flag && (param_.getUserAlias()==null || param_.getUserAlias().equals(BLANK_STR)))
            {
                this.addActionError(this.getText("user.userAlias.empty"));
                flag = true;
            }
            
            if(!flag && (param_.getLoginId()==null || param_.getLoginId().equals(BLANK_STR)))
            {
                this.addActionError(this.getText("user.loginId.empty"));
                flag = true;
            }
            
            if(!flag && (param_.getLoginPasswd()==null || param_.getLoginPasswd().equals(BLANK_STR)))
            {
                this.addActionError(this.getText("user.loginPasswd.empty"));
                flag = true;
            }
            
            if(!flag && (param_.getConfirmPasswd()==null || param_.getConfirmPasswd().equals(BLANK_STR)))
            {
                this.addActionError(this.getText("user.confirmPasswd.empty"));
                flag = true;
            }
            
            if(!flag && (!param_.getLoginPasswd().equals(param_.getConfirmPasswd())))
            {
                this.addActionError(this.getText("user.loginPasswd.confirmPasswd.mismatch"));
                flag = true;
            }
            
            if(!flag)
            {
                List<UserProfileHolder> uList = familyService.getUsersByFamilyOid(param_.getFamilyOid());
                if(uList!=null && uList.size()>0)
                {
                    for(UserProfileHolder user : uList)
                    {
                        if(user.getUserName().equalsIgnoreCase(param_.getUserName()))
                        {
                            this.addActionError(this.getText("user.userName.exist", new String[]{param_.getUserName()}));
                            flag = true;
                            break;
                        }
                        
                        if(user.getUserAlias().equalsIgnoreCase(param_.getUserAlias()))
                        {
                            this.addActionError(this.getText("user.userAlias.exist", new String[]{param_.getUserAlias()}));
                            flag = true;
                            break;
                        }
                    }
                }
                
            }
            
            if(flag)
            {
                familyList = familyService.getFamilies(null);
                this.initGenderMap();
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveAdd(): ");
            log.error("Error occur on validateSaveAdd user", e);
        }
    }
    
    
    public String saveAdd()
    {
        try
        {
            param_.setUserOid(userProfileService.getOid());
            param_.setUserType(USER_TYPE_NORMAL);
            param_.setCtlStatus(USER_STATUS_ACTIVE);
            param_.setFailedAttempt(new BigDecimal(0));
            param_.setLoginPasswd(EncodeUtil.encodePassword(param_.getLoginPasswd(), "MD5"));
            
            ((DBActionService)userProfileService).insert(this.getCommonParameter(), param_);
            
            
            MessageTargetHolder target_ = new MessageTargetHolder("initUser",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("user.create.success", new String[]{param_.getUserName()}));
        }
        catch(Exception e)
        {
            log.error("saveAdd(): ");
            log.error("Error occur on saveAdd user", e);
            
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
            param_ = userProfileService.getUserProfileByKey(param_.getUserOid());
            
            FamilyHolder family = familyService.getFamilyByUserOid(param_.getUserOid());
            
            param_.setFamilyName(family.getFamilyName());
            
            initGenderMap();
        }
        catch (Exception e)
        {
            log.error("initAdd(): ");
            log.error("Error occur on initEdit user", e);
            
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
            if(param_ != null) param_.trimAllString();
            
            UserProfileHolder old_ = userProfileService.getUserProfileByKey(param_.getUserOid());
            
            if(!flag && (param_.getUserName()==null || param_.getUserName().equals(BLANK_STR)))
            {
                this.addActionError(this.getText("user.userName.empty"));
                flag = true;
            }
            
            if(!flag && (param_.getUserAlias()==null || param_.getUserAlias().equals(BLANK_STR)))
            {
                this.addActionError(this.getText("user.userAlias.empty"));
                flag = true;
            }
            
            if(!flag)
            {
                FamilyHolder family = familyService.getFamilyByUserOid(param_.getUserOid());
                
                List<UserProfileHolder> uList = familyService.getUsersByFamilyOid(family.getFamilyOid());
                if(uList!=null && uList.size()>0)
                {
                    for(UserProfileHolder user : uList)
                    {
                        if(user.getUserOid().equals(param_.getUserOid()))
                            continue;
                        
                        if(user.getUserName().equalsIgnoreCase(param_.getUserName()))
                        {
                            this.addActionError(this.getText("user.userName.exist", new String[]{param_.getUserName()}));
                            flag = true;
                            break;
                        }
                        
                        if(user.getUserAlias().equalsIgnoreCase(param_.getUserAlias()))
                        {
                            this.addActionError(this.getText("user.userAlias.exist", new String[]{param_.getUserAlias()}));
                            flag = true;
                            break;
                        }
                    }
                }
            }
            
            if(flag)
            {
                FamilyHolder family = familyService.getFamilyByUserOid(param_.getUserOid());
                param_.setFamilyName(family.getFamilyName());
                
                this.initGenderMap();
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveEdit(): ");
            log.error("Error occur on validateSaveEdit user", e);
        }
    }
    
    
    public String saveEdit()
    {
        try
        {
            UserProfileHolder oldObj = userProfileService.getUserProfileByKey(param_.getUserOid());
            UserProfileHolder newObj = new UserProfileHolder(oldObj);
            
            newObj.setUserName(param_.getUserName());
            newObj.setUserAlias(param_.getUserAlias());
            newObj.setGender(param_.getGender());
            newObj.setPhone(param_.getPhone());
            newObj.setEmail(param_.getEmail());
            
            ((DBActionService)userProfileService).update(this.getCommonParameter(), newObj, oldObj);
            
            
            MessageTargetHolder target_ = new MessageTargetHolder("initUser",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("user.edit.success"));
        }
        catch(Exception e)
        {
            log.error("saveEdit(): ");
            log.error("Error occur on saveEdit user", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
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
                this.addActionError(this.getText("user.select.no.one"));
                flag = true;
            }
            
            
            if(flag)
            {
                
            }
        }
        catch(Exception e)
        {
            log.error("validateSaveDelete(): ");
            log.error("Error occur on validateSaveDelete user", e);
        }
    }

    
    public String saveDelete()
    {
        try
        {
            for(int i=0; i<resOids.size(); i++)
            {
                UserProfileHolder old_ = userProfileService.getUserProfileByKey(new BigDecimal(resOids.get(i)));
                
                
                List items = itemService.getItemsByUserOid(old_.getUserOid());
                if(items!=null && items.size()>0)
                {
                    msg.saveError(this.getText("user.item.not.empty", new String[]{old_.getUserName()}));
                    continue;
                }
                
                try
                {
                    ((DBActionService)userProfileService).delete(this.getCommonParameter(), old_);
                    msg.saveSuccess(this.getText("user.delete.success", new String[]{old_.getUserName()}));
                }
                catch(Exception e)
                {
                    log.error(ErrorMsgHelper.getStackTrace(e));
                    ErrorMsgHelper.logError(log, e, this, msg);
                }
            }
            
            MessageTargetHolder target_ = new MessageTargetHolder("initUser",getText("button.ok"));
            target_.addRequestParam(REQ_PARAMETER_KEEP_SEARCH_CONDITION, VALUE_YES);
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            
        }
        catch(Exception e)
        {
            log.error("saveDelete(): ");
            log.error("Error occur on saveDelete user", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // private methods
    //*****************************************************
    
    private void initGenderMap()
    {
        genderMap = new HashMap();
        genderMap.put(VALUE_GENDER_MAIL, this.getText("label.male"));
        genderMap.put(VALUE_GENDER_FEMAIL, this.getText("label.female"));
    }
    
    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setFamilyService(FamilyService familyService)
    {
        this.familyService = familyService;
    }
    
    
    public void setUserProfileService(UserProfileService userProfileService)
    {
        this.userProfileService = userProfileService;
    }
    
    
    public UserProfileHolder getParam()
    {
        return param_;
    }

    
    public void setParam(UserProfileHolder param_)
    {
        this.param_ = param_;
    }

    
    public List<FamilyHolder> getFamilyList()
    {
        return familyList;
    }

    
    public Map getGenderMap()
    {
        return genderMap;
    }
    
    
    public void setResOids(List<String> resOids)
    {
        this.resOids = resOids;
    }

    
    public List<String> getResOids()
    {
        return resOids;
    }


    public void setItemService(ItemService itemService)
    {
        this.itemService = itemService;
    }
}
