package com.oyl.ffms.service;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.holder.ModuleHolder;

public interface ModuleService
{
    public List<ModuleHolder> getModules(ModuleHolder param);
    
    public List<ModuleHolder> getUserModules(BigDecimal userOid);
}
