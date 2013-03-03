package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.UserRoleDao;
import com.oyl.ffms.holder.UserRoleHolder;

public class UserRoleDaoIbatis extends SqlMapClientDaoSupport implements
        UserRoleDao
{

    public int delete(UserRoleHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteUserRole", param);
    }


    public List<UserRoleHolder> getFamilyUsers(UserRoleHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getUserRoles", param);
    }


    public int insert(UserRoleHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertUserRole", param);
    }

}
