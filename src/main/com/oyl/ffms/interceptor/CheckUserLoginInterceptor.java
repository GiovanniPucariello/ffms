package com.oyl.ffms.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author OuYangLiang
 * Jul 17, 2009
 */
public class CheckUserLoginInterceptor extends AbstractInterceptor
{
    public String intercept(ActionInvocation invocation) throws Exception
    {
        HttpServletRequest request = ServletActionContext.getRequest();

        Object currentUser = ActionContext.getContext().getSession()
                .get(com.oyl.base.util.CommonConstants.SESSION_KEY_USER);

        if (currentUser != null)
        {
            return invocation.invoke();
        }

        return "lostLogin";
    }
}
