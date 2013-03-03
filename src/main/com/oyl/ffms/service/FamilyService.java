package com.oyl.ffms.service;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.holder.UserProfileHolder;

public interface FamilyService
{
    public List<FamilyHolder> getFamilies(FamilyHolder param) throws Exception;
    
    public FamilyHolder getFamilyByKey(BigDecimal familyOid) throws Exception;
    
    public FamilyHolder getFamilyByName(String familyName) throws Exception;
    
    public boolean isFamilyExist(String familyName) throws Exception;
    
    public BigDecimal getOid() throws Exception;
    
    public FamilyHolder getFamilyByUserOid(BigDecimal userOid) throws Exception;
    
    public List<UserProfileHolder> getUsersByFamilyOid(BigDecimal familyOid) throws Exception;
    
    public boolean isFamilyHasUsers(BigDecimal familyOid) throws Exception;
}
