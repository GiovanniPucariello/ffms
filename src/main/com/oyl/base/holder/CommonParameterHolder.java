//*****************************************************************************
//
// File Name       :  CommonParameterHolder.java
// Date Created    :  17-ÈýÔÂ-08
// Last Changed By :  $Author: $
// Last Changed On :  $Date: $
// Revision        :  $Rev: $
// Description     :  TODO To fill in a brief description of the purpose of
//                    this class.
//
// PracBiz Pte Ltd.  Copyright (c) 2008.  All Rights Reserved.
//
//*****************************************************************************

package com.oyl.base.holder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class CommonParameterHolder implements Serializable
{
    private Map paramMap;
    
    public void setValue(String key_, Object value_)
    {
        if (paramMap == null) paramMap = new HashMap();
        
        paramMap.put(key_, value_);
    }
    
    public Object getValue(String key_)
    {
        if (paramMap == null) return null;
        
        return paramMap.get(key_);
    }
    
    public String toString()
    {
        try
        {
            return BeanUtils.describe(this).toString();
        }
        catch (Exception exception)
        {
            return exception.getMessage();
        }
    }
    
    public int hashCode()
    {
        return toString().hashCode();
    }
    
}
