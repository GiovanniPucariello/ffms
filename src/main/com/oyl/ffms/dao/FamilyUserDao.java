package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.FamilyUserHolder;

public interface FamilyUserDao
{
    public List<FamilyUserHolder> getFamilyUsers(FamilyUserHolder param);
    
    public int insert(FamilyUserHolder param);
    
    public int delete(FamilyUserHolder param);
    
}
