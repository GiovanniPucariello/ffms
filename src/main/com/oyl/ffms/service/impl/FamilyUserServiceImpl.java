package com.oyl.ffms.service.impl;

import java.util.List;

import com.oyl.ffms.dao.FamilyUserDao;
import com.oyl.ffms.holder.FamilyUserHolder;
import com.oyl.ffms.service.FamilyUserService;

public class FamilyUserServiceImpl implements FamilyUserService
{
    private FamilyUserDao dao_;
    
    public int delete(FamilyUserHolder param) throws Exception
    {
        return dao_.delete(param);
    }


    public List<FamilyUserHolder> getFamilyUsers(FamilyUserHolder param) throws Exception
    {
        return dao_.getFamilyUsers(param);
    }


    public int insert(FamilyUserHolder param) throws Exception
    {
        return dao_.insert(param);
    }


    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setDao(FamilyUserDao dao_)
    {
        this.dao_ = dao_;
    }
}
