package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.CtlParamHolder;

public interface CtlParamDao
{
    public List<CtlParamHolder> getCtlParams(CtlParamHolder param);
    
    public int insert(CtlParamHolder param);
    
    public int delete(CtlParamHolder param);
    
    public int update(CtlParamHolder param);
}
