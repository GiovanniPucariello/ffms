package com.oyl.ffms.action;

import com.oyl.base.action.BaseAction;
import com.oyl.base.holder.CommonParameterHolder;
import com.oyl.base.util.ErrorMsgHelper;
import com.oyl.base.util.MessageCodeConstants;
import com.oyl.ffms.holder.CtlParamHolder;
import com.oyl.ffms.holder.UserProfileHolder;
import com.oyl.ffms.service.CtlParamService;
import com.oyl.ffms.util.CommonConstants;
import com.oyl.ffms.util.CustomAppConfigHelper;
import com.oyl.ffms.util.SortingMapConstants;

/**
 * TODO To provide an overview of this class.
 *
 * @author OuYangLiang
 */
public class ProjectBaseAction extends BaseAction implements CommonConstants,
        MessageCodeConstants, SortingMapConstants
{   
    protected CtlParamService ctlParamService;
    protected CustomAppConfigHelper appConfig;
        
    protected String getLoginIdOfUser()
    {
        return ((UserProfileHolder) this.getSession().get(SESSION_KEY_USER)).getLoginId();
    }
    
    protected UserProfileHolder getLoginOfUser()
    {
        return (UserProfileHolder) this.getSession().get(SESSION_KEY_USER);
    }
    
    protected CommonParameterHolder getCommonParameter()
    {
        if (this.getSession().get(SESSION_COMMON_PARAMETER) == null)
            return null;
        else
            return (CommonParameterHolder)this.getSession().get(SESSION_COMMON_PARAMETER);
    }
    
    protected int getPageSize()
    {
        return ((Integer)this.getCommonParameter().getValue("PAGE_SIZE")).intValue();
    }
        
    protected String getPasswordAlgorithm()
    {
    	return "MD5";
    }

    protected void initSystem()
    {
        if (getCommonParameter() != null) return;
        
        try
        {
            CtlParamHolder ctlParam = ctlParamService.getCtlParamByKey(SECT_ID_CTRL, PARAM_ID_PER_PAGE_RECORDS);
            
            CommonParameterHolder commParam_ = new CommonParameterHolder();
            
            commParam_.setValue("PAGE_SIZE", new Integer(ctlParam.getCtlValue()).intValue());
                        
            this.getSession().put(SESSION_COMMON_PARAMETER, commParam_);
        }
        catch(Exception e)
        {
            log.error("Error occur on initSystem ", e);
            ErrorMsgHelper.logError(log, this.toString(), e);
        }
    }
    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public CustomAppConfigHelper getAppConfig()
    {
        return appConfig;
    }
    
    public void setAppConfig(CustomAppConfigHelper appConfig)
    {
        this.appConfig = appConfig;
    }

    public CtlParamService getCtlParamService()
    {
        return ctlParamService;
    }

    public void setCtlParamService(CtlParamService ctlParamService)
    {
        this.ctlParamService = ctlParamService;
    }
}
