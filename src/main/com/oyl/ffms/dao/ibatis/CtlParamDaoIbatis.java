package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.CtlParamDao;
import com.oyl.ffms.holder.CtlParamHolder;

public class CtlParamDaoIbatis extends SqlMapClientDaoSupport implements
        CtlParamDao
{

    public List<CtlParamHolder> getCtlParams(CtlParamHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getCtlParams", param);
    }

    public int delete(CtlParamHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteCtlParam", param);
    }

    public int insert(CtlParamHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertCtlParam", param);
    }

    public int update(CtlParamHolder param)
    {
        return this.getSqlMapClientTemplate().update("updateCtlParam", param);
    }

}
