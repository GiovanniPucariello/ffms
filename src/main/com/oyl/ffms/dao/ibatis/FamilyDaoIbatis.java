package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.FamilyDao;
import com.oyl.ffms.holder.FamilyHolder;

public class FamilyDaoIbatis extends SqlMapClientDaoSupport implements
        FamilyDao
{

    public int delete(FamilyHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteFamily", param);
    }


    public int getCountOfSummary(FamilyHolder param)
    {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("getCountOfFamily", param);
    }


    public List<FamilyHolder> getFamilies(FamilyHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getFamilies", param);
    }


    public List<FamilyHolder> getListOfSummary(FamilyHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSummaryFamily", param);
    }


    public int insert(FamilyHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertFamily", param);
    }


    public int update(FamilyHolder param)
    {
        return this.getSqlMapClientTemplate().update("updateFamily", param);
    }

}
