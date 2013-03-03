package com.oyl.ffms.action;

import java.util.List;

import com.oyl.base.holder.MessageTargetHolder;
import com.oyl.base.service.DBActionService;
import com.oyl.base.util.EncodeUtil;
import com.oyl.base.util.ErrorMsgHelper;
import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.holder.UserProfileHolder;
import com.oyl.ffms.service.FamilyService;
import com.oyl.ffms.service.UserProfileService;

public class MyProfileAction extends ProjectBaseAction
{
    private UserProfileHolder param_;
    private UserProfileService userProfileService;
    private FamilyService familyService;
    
    private String isPwdChanged;
    
    public MyProfileAction()
    {
        initMsg();

        // default target
        MessageTargetHolder target_ = new MessageTargetHolder("initEditMyProfile",
                getText("button.ok"));

        msg.addMessageTarget(target_);
        msg.setTitle(getText("label.common.summary"));
    }
    
    
    public String initEdit()
    {
        param_ = new UserProfileHolder(this.getLoginOfUser());
        isPwdChanged = VALUE_NO;
        
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
            }
            
            if(!flag && (param_.getUserName()==null || param_.getUserName().equals(BLANK_STR)))
            {
                this.addActionError(this.getText("myprofile.username.empty"));
                flag = true;
            }
            
            
            if(!flag && (param_.getUserAlias()==null || param_.getUserAlias().equals(BLANK_STR)))
            {
                this.addActionError(this.getText("myprofile.useralias.empty"));
                flag = true;
            }
            
            
            if (VALUE_YES.equalsIgnoreCase(isPwdChanged))
            {
                if (!flag && (param_.getLoginPasswd()==null || BLANK_STR.equalsIgnoreCase(param_.getLoginPasswd())))
                {
                    this.addActionError(this.getText("myprofile.loginPwd.empty"));
                    flag = true;
                }
                
                
                if (!flag && (param_.getConfirmPasswd()==null || BLANK_STR.equalsIgnoreCase(param_.getConfirmPasswd())))
                {
                    this.addActionError(this.getText("myprofile.loginConPwd.empty"));
                    flag = true;
                }
                
                
                if (!flag && (!param_.getConfirmPasswd().equals(param_.getLoginPasswd())))
                {
                    this.addActionError(this.getText("myprofile.loginPwd.unequals.loginConPwd"));
                    flag = true;
                }
            }
            
            if(!flag)
            {
                FamilyHolder family = familyService.getFamilyByUserOid(param_.getUserOid());
                
                if (null != family)
                {   // admin user does not belong to any family
                    List<UserProfileHolder> uList = familyService.getUsersByFamilyOid(family.getFamilyOid());
                    if (uList != null && !uList.isEmpty())
                    {
                        for(UserProfileHolder user : uList)
                        {
                            if(user.getUserOid().equals(param_.getUserOid()))
                                continue;
                            
                            if(user.getUserName().equalsIgnoreCase(param_.getUserName()))
                            {
                                this.addActionError(this.getText("myprofile.username.exist", new String[]{param_.getUserName()}));
                                flag = true;
                                break;
                            }
                            
                            if(user.getUserAlias().equalsIgnoreCase(param_.getUserAlias()))
                            {
                                this.addActionError(this.getText("myprofile.useralias.exist", new String[]{param_.getUserAlias()}));
                                flag = true;
                                break;
                            }
                        }
                    }
                }
            }
            
            if(flag)
            {
                isPwdChanged = VALUE_NO;
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
            newObj.setPhone(param_.getPhone());
            newObj.setEmail(param_.getEmail());
            
            if (VALUE_YES.equalsIgnoreCase(isPwdChanged))
            {
                newObj.setLoginPasswd(EncodeUtil.encodePassword(param_.getLoginPasswd(), "MD5"));
            }
            
            
            oldObj.trimAllString();
            oldObj.setAllEmptyStringToNull();
            ((DBActionService)userProfileService).update(this.getCommonParameter(), newObj, oldObj);
            
            this.getSession().put(SESSION_KEY_USER, newObj);
            
            MessageTargetHolder target_ = new MessageTargetHolder("initEditMyProfile",getText("button.ok"));
            
            msg.clearTargets();
            msg.addMessageTarget(target_);
            msg.saveSuccess(getText("myprofile.edit.success"));
        }
        catch (Exception e)
        {
            log.error("saveEdit(): ");
            log.error("Error occur on saveEdit Myprofile", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);
        }
        
        return FORWARD_COMMON_MESSAGE;
    }
    
    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public UserProfileHolder getParam()
    {
        return param_;
    }


    public void setParam(UserProfileHolder param)
    {
        param_ = param;
    }


    public void setUserProfileService(UserProfileService userProfileService)
    {
        this.userProfileService = userProfileService;
    }


    public String getIsPwdChanged()
    {
        return isPwdChanged;
    }


    public void setIsPwdChanged(String isPwdChanged)
    {
        this.isPwdChanged = isPwdChanged;
    }


    public void setFamilyService(FamilyService familyService)
    {
        this.familyService = familyService;
    }
}
