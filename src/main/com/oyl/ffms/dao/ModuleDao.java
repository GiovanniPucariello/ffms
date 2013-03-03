package com.oyl.ffms.dao;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.holder.ModuleHolder;

public interface ModuleDao
{
    public List<ModuleHolder> getModules(ModuleHolder param);
    
    public List<ModuleHolder> getUserModules(BigDecimal userOid);
}
