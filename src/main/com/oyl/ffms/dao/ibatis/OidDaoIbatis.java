package com.oyl.ffms.dao.ibatis;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.OidDao;

public class OidDaoIbatis extends SqlMapClientDaoSupport implements OidDao
{

    private final String PROCEDURE_NAME = "spOidGeneric";
    private static byte[] tag = new byte[0];
    
    public BigDecimal getOid(String procedureName)
    {
        Map result = new HashMap();
        
        synchronized(tag)
        {
            result = (Map)getSqlMapClientTemplate().queryForObject(procedureName, new HashMap());
        }
        
        return (new BigDecimal(result.get("oid").toString()));
    }

    public BigDecimal getOid()
    {
        return getOid(PROCEDURE_NAME);
    }

}
