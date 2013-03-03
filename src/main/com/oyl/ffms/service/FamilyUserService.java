package com.oyl.ffms.service;

import java.util.List;

import com.oyl.ffms.holder.FamilyUserHolder;

public interface FamilyUserService
{
    public List<FamilyUserHolder> getFamilyUsers(FamilyUserHolder param) throws Exception;
    
    public int insert(FamilyUserHolder param) throws Exception;
    
    public int delete(FamilyUserHolder param) throws Exception;
}
