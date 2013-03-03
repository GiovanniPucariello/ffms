package com.oyl.base.holder;

import java.util.ArrayList;
import java.util.List;

public class MessageTargetHolder
{
    private String targetURI;
    private String targetBtnTitle;
    
    private List parameters = null;

    public MessageTargetHolder()
    {}
    
    public MessageTargetHolder(String uri, String btnTitle)
    {
        this.targetURI = uri;
        this.targetBtnTitle = btnTitle;
    }
    
    public void addRequestParam(String paramKey_, String paramValue_)
    {
        if (parameters == null) parameters = new ArrayList();
        
        RequestParameterHolder param_ = new RequestParameterHolder();
        param_.setParamKey(paramKey_);
        param_.setParamValue(paramValue_);
        
        this.parameters.add(param_);
    }
    
    public List getParameters()
    {
        return parameters;
    }

    public void setParameters(List parameters)
    {
        this.parameters = parameters;
    }
    
    public String getTargetURI()
    {
        return targetURI;
    }

    public void setTargetURI(String targetURI)
    {
        this.targetURI = targetURI;
    }

    public String getTargetBtnTitle()
    {
        return targetBtnTitle;
    }

    public void setTargetBtnTitle(String targetBtnTitle)
    {
        this.targetBtnTitle = targetBtnTitle;
    }
}
