package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.FamilyHolder;

public interface FamilyDao
{
    public List<FamilyHolder> getFamilies(FamilyHolder param);
    
    public int getCountOfSummary(FamilyHolder param);    
    
    public List<FamilyHolder> getListOfSummary(FamilyHolder param);
    
    public int insert(FamilyHolder param);
    
    public int delete(FamilyHolder param);
    
    public int update(FamilyHolder param);
    
}
