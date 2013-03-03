package com.oyl.base.service;

import java.util.List;

import com.oyl.base.holder.BaseHolder;

public interface BaseService
{
    public int getCountOfSummary(BaseHolder parameter_) throws Exception;
    
    public List getListOfSummary(BaseHolder parameter_) throws Exception;
}
