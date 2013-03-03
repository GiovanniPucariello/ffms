package com.oyl.base.holder;

import java.io.Serializable;

public class RequestParameterHolder implements Serializable
{
    private String paramKey_;
    private String paramValue_;
    
    public RequestParameterHolder()
    {}

    public String getParamKey()
    {
        return paramKey_;
    }

    public void setParamKey(String paramKey_)
    {
        this.paramKey_ = paramKey_;
    }

    public String getParamValue()
    {
        return paramValue_;
    }

    public void setParamValue(String paramValue_)
    {
        this.paramValue_ = paramValue_;
    }
}
