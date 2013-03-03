package com.oyl.ffms.action;

import java.util.ArrayList;
import java.util.List;

import com.oyl.base.holder.MessageTargetHolder;
import com.oyl.base.util.EncodeUtil;
import com.oyl.base.util.ErrorMsgHelper;

import com.oyl.ffms.service.ModuleService;
import com.oyl.ffms.service.UserProfileService;
import com.oyl.ffms.util.CommonConstants;
import com.oyl.ffms.holder.ModuleHolder;
import com.oyl.ffms.holder.UserProfileHolder;

public class LoginAction extends ProjectBaseAction implements CommonConstants
{
    private UserProfileService userProfileService;
    private ModuleService moduleService;
    
    public LoginAction()
    {
        initMsg();
        
        //default target
        MessageTargetHolder target_ = new MessageTargetHolder("home",getText("button.ok"));
        msg.addMessageTarget(target_);
        msg.setTitle(getText("error.common.target.title"));
    }

    
    public void validateLogin()
    {
        this.initSystem();
        
        boolean flag = false;
        
        try
        {
            String loginId_ = (String) this.getRequest().getParameter(REQUEST_PARAMETER_KEY_LOGIN_ID);
            String password_ = (String) this.getRequest().getParameter(REQUEST_PARAMETER_KEY_LOGIN_PASSWD);
            
            
            if (loginId_ == null || password_ == null || 
                    BLANK_STR.equals(loginId_.trim()) || BLANK_STR.equals(password_.trim()))
            {
                throw new Exception(getText("error.login.mdl.loginid.pwd.required"));
            }
            
            UserProfileHolder user_ = userProfileService.getUserProfileByLoginId(loginId_.trim());
            if (user_ == null)
            {
                throw new Exception(getText("error.login.mdl.loginid.doesnot.exist", new String[]{loginId_}));
            }
            
            if (!user_.getLoginPasswd().equalsIgnoreCase(EncodeUtil.encodePassword(password_, "MD5")))
            {
                throw new Exception(getText("error.login.mdl.loginid.password.unmatch", new String[]{loginId_, password_}));
            }
          
            if (!USER_STATUS_ACTIVE.equalsIgnoreCase(user_.getCtlStatus()))
            {
                throw new Exception(getText("error.login.mdl.user.is.inactive", new String[]{loginId_}));
            }
            
        }
        catch (Exception e)
        {
            flag = true;
            
            log.error("validateLogin: ");
            log.error("Error occur on validateLogin ", e);
        }
        
        if (flag)
        {           
            this.addActionError(getText("error.login.mdl.failed"));

            this.getRequest().setAttribute("ERROR_LOGIN_FAILED",
                    getText("error.login.mdl.failed"));
        }
    }
    
    
    public String login()
    {
        try
        {
            String loginId_ = (String) this.getRequest().getParameter(REQUEST_PARAMETER_KEY_LOGIN_ID);
            String password_ = (String) this.getRequest().getParameter(REQUEST_PARAMETER_KEY_LOGIN_PASSWD);
            
            UserProfileHolder user = userProfileService.getUserProfileByLoginId(loginId_);
            
            List<ModuleHolder> modules = moduleService.getUserModules(user.getUserOid());
            List<ModuleHolder> userModules = new ArrayList<ModuleHolder>();
            
            for(int i=0; i<modules.size(); i++)
            {
                ModuleHolder module = modules.get(i);
                
                if(module.getModuleLevel()==1)
                {
                    userModules.add(module);
                }
                else if(module.getModuleLevel()==2)
                {
                    if(userModules.get(userModules.size()-1).getSubModules()==null)
                    {
                        List<ModuleHolder> subModules = new ArrayList();
                        subModules.add(module);
                        userModules.get(userModules.size()-1).setSubModules(subModules);
                    }
                    else
                    {
                        userModules.get(userModules.size()-1).getSubModules().add(module);
                    }
                }
            }
            
            this.getSession().put(SESSION_KEY_MENU, userModules);
            this.getSession().put(SESSION_KEY_CLIENT_IP,this.getRequest().getRemoteAddr());
            this.getSession().put(SESSION_KEY_USER_AGENT,this.getRequest().getHeader("User-Agent"));
            this.getSession().put(SESSION_KEY_USER, user);
        }
        catch(Exception e)
        {
            log.error("login: ");
            log.error("Error occur on login ", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        
        return SUCCESS;
    }


    public String logout()
    {
        if (this.getSession().get(SESSION_KEY_MENU) != null)
            this.getSession().remove(SESSION_KEY_MENU);
        
        if (this.getSession().get(SESSION_KEY_CLIENT_IP) != null)
            this.getSession().remove(SESSION_KEY_CLIENT_IP);
        
        if (this.getSession().get(SESSION_KEY_USER_AGENT) != null)
            this.getSession().remove(SESSION_KEY_USER_AGENT);
        
        if (this.getSession().get(SESSION_KEY_USER) != null)
            this.getSession().remove(SESSION_KEY_USER);
        
        this.getSessionMap().invalidate();
        
        return SUCCESS;
    }


    // *****************************************************
    // setter and getter
    // *****************************************************

    public void setUserProfileService(UserProfileService userProfileService)
    {
        this.userProfileService = userProfileService;
    }
    

    public void setModuleService(ModuleService moduleService)
    {
        this.moduleService = moduleService;
    }
}
