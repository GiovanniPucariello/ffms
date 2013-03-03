//*****************************************************************************
//
// File Name       :  InitSessionInterceptor.java
// Date Created    :  2008-1-3
// Last Changed By :  $Author: $
// Last Changed On :  $Date: $
// Revision        :  $Rev: $
// Description     :  TODO To fill in a brief description of the purpose of
//                    this class.
//
// PracBiz Pte Ltd.  Copyright (c) 2008.  All Rights Reserved.
//
//*****************************************************************************

package com.oyl.ffms.interceptor;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsConstants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * TODO To provide an overview of this class.
 * 
 * @author xuchengqing
 */
public class InitSessionInterceptor extends AbstractInterceptor
{
    private static Logger log = Logger.getLogger(InitSessionInterceptor.class);

    /**
     * in fact, if need to support multiple layout theme for different customer,
     * the layoutTheme should be from database.
     */
    private String layoutTheme = "default";

    private String defaultTheme = null;

    private boolean devMode = false;


    public String intercept(ActionInvocation invocation) throws Exception
    {
        Map session = ActionContext.getContext().getSession();
        // layout theme
        if (!session.containsKey("layoutTheme"))
        {
            session.put("layoutTheme", layoutTheme);
        }

        if (log.isDebugEnabled())
        {
            log.debug("defaultTheme:" + defaultTheme);
        }

        // theme for each html element
        if (!session.containsKey("theme"))
        {
            if (defaultTheme != null)
            {
                session.put("theme", defaultTheme);
            }
            else
            {
                session.put("theme", "custom-default");
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("devMode:" + devMode);
        }

        if (!session.containsKey("devMode"))
        {
            session.put("devMode", devMode);
        }

        String result = invocation.invoke();

        // may place some code here after action invoke

        return result;
    }


    @Inject(StrutsConstants.STRUTS_DEVMODE)
    public void setDevMode(String mode)
    {
        this.devMode = "true".equals(mode);
    }


    @Inject(StrutsConstants.STRUTS_UI_THEME)
    public void setDefaultTheme(String theme)
    {
        this.defaultTheme = theme;
    }
}
