package com.oyl.base.service;

import com.oyl.base.holder.BaseHolder;
import com.oyl.base.holder.CommonParameterHolder;

public interface DBActionService
{
    public void insert(CommonParameterHolder commPara_, BaseHolder newObj_)
            throws Exception;

    
    public void update(CommonParameterHolder commPara_, BaseHolder newObj_,
            BaseHolder oldObj_) throws Exception;

    
    public void delete(CommonParameterHolder commPara_, BaseHolder oldObj_)
            throws Exception;
}
