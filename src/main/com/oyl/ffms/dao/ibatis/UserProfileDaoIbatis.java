package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.UserProfileDao;
import com.oyl.ffms.holder.UserProfileHolder;

public class UserProfileDaoIbatis extends SqlMapClientDaoSupport implements
        UserProfileDao
{

    public int delete(UserProfileHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteUserProfile", param);
    }


    public List<UserProfileHolder> getUserProfiles(UserProfileHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getUserProfiles", param);
    }


    public int insert(UserProfileHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertUserProfile", param);
    }


    public int update(UserProfileHolder param)
    {
        return this.getSqlMapClientTemplate().update("updateUserProfile", param);
    }


    public int getCountOfSummary(UserProfileHolder param)
    {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("getCountOfUserProfile", param);
    }


    public List<UserProfileHolder> getListOfSummary(
            UserProfileHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSummaryUserProfiles", param);
    }

}
