package com.oyl.ffms.interceptor;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptor extends AbstractInterceptor
{   
    public String intercept(ActionInvocation invocation) throws Exception
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        
        ActionContext ctx=invocation.getInvocationContext();
        List<String> permitUrlList =(List<String>)ctx.getSession().get(com.oyl.base.util.CommonConstants.SESSION_KEY_PERMIT_URL);
        
        if(permitUrlList==null)
        {
            return "noPermit";
        }
        
        String uri = StringUtils.remove(request.getRequestURI(), request.getContextPath());
        if(permitUrlList.contains(uri))
        {
            return invocation.invoke();
        }
        
        return "noPermit";
    }

}
