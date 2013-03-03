package com.oyl.ffms.service;

import java.util.List;

import com.oyl.ffms.holder.CtlParamHolder;

public interface CtlParamService
{
    public List<CtlParamHolder> getCtlParams(CtlParamHolder param) throws Exception;
    
    public CtlParamHolder getCtlParamByKey(String ctlGrp, String ctlKey) throws Exception;
}
