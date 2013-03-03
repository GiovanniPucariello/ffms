package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.UserProfileHolder;

public interface UserProfileDao
{
    public List<UserProfileHolder> getUserProfiles(UserProfileHolder param);
    
    public int insert(UserProfileHolder param);
    
    public int delete(UserProfileHolder param);
    
    public int update(UserProfileHolder param);
    
    public int getCountOfSummary(UserProfileHolder param);    
    
    public List<UserProfileHolder> getListOfSummary(UserProfileHolder param);
}
