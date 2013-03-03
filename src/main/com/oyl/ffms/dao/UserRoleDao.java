package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.UserRoleHolder;

public interface UserRoleDao
{
    public List<UserRoleHolder> getFamilyUsers(UserRoleHolder param);
    
    public int insert(UserRoleHolder param);
    
    public int delete(UserRoleHolder param);
}
