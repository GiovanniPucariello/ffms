package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.FamilyUserDao;
import com.oyl.ffms.holder.FamilyUserHolder;

public class FamilyUserDaoIbatis extends SqlMapClientDaoSupport implements
        FamilyUserDao
{

    public int delete(FamilyUserHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteFamilyUser", param);
    }


    public List<FamilyUserHolder> getFamilyUsers(FamilyUserHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getFamilyUsers", param);
    }


    public int insert(FamilyUserHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertFamilyUser", param);
    }

}
