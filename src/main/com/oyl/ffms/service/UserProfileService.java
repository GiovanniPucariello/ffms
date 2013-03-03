package com.oyl.ffms.service;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.holder.UserProfileHolder;

public interface UserProfileService
{
    public List<UserProfileHolder> getUserProfiles(UserProfileHolder param) throws Exception;
    
    public UserProfileHolder getUserProfileByKey(BigDecimal userOid) throws Exception;
    
    public UserProfileHolder getUserProfileByLoginId(String loginId) throws Exception;
    
    public BigDecimal getOid() throws Exception;
}
