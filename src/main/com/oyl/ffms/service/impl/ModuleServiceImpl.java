package com.oyl.ffms.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.dao.ModuleDao;
import com.oyl.ffms.holder.ModuleHolder;
import com.oyl.ffms.service.ModuleService;

public class ModuleServiceImpl implements ModuleService
{
    private ModuleDao dao_;
    
    public List<ModuleHolder> getModules(ModuleHolder param)
    {
        return dao_.getModules(param);
    }


    public List<ModuleHolder> getUserModules(BigDecimal userOid)
    {
        return dao_.getUserModules(userOid);
    }


    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setDao(ModuleDao dao_)
    {
        this.dao_ = dao_;
    }
}
