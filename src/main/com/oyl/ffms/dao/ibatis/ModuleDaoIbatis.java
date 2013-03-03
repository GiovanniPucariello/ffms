package com.oyl.ffms.dao.ibatis;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.ModuleDao;
import com.oyl.ffms.holder.ModuleHolder;

public class ModuleDaoIbatis extends SqlMapClientDaoSupport
    implements ModuleDao
{

    public List<ModuleHolder> getModules(ModuleHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getModules", param);
    }

    public List<ModuleHolder> getUserModules(BigDecimal userOid)
    {
        Map param = new HashMap();
        param.put("userOid", userOid);
        
        return this.getSqlMapClientTemplate().queryForList("getUserModules", param);
    }
    
}
