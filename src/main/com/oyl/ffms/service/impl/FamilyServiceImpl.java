package com.oyl.ffms.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.oyl.base.holder.BaseHolder;
import com.oyl.base.holder.CommonParameterHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.ffms.dao.FamilyDao;
import com.oyl.ffms.dao.FamilyUserDao;
import com.oyl.ffms.dao.OidDao;
import com.oyl.ffms.dao.UserProfileDao;
import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.holder.FamilyUserHolder;
import com.oyl.ffms.holder.UserProfileHolder;
import com.oyl.ffms.service.FamilyService;

public class FamilyServiceImpl implements FamilyService, DBActionService,
        BaseService
{
    private FamilyDao dao_;
    private FamilyUserDao familyUserDao_;
    private OidDao oidDao_;
    private UserProfileDao userProfileDao_;
    

    public List<FamilyHolder> getFamilies(FamilyHolder param) throws Exception
    {
        return dao_.getFamilies(param);
    }


    public FamilyHolder getFamilyByKey(BigDecimal familyOid) throws Exception
    {
        FamilyHolder param = new FamilyHolder();
        param.setFamilyOid(familyOid);
        
        List<FamilyHolder> list = this.getFamilies(param);
        if(list!=null && list.size()>0)
            return list.get(0);
        
        return null;
    }


    public void delete(CommonParameterHolder commPara_, BaseHolder oldObj_)
            throws Exception
    {
        dao_.delete((FamilyHolder)oldObj_);
    }


    public void insert(CommonParameterHolder commPara_, BaseHolder newObj_)
            throws Exception
    {
        dao_.insert((FamilyHolder)newObj_);
    }


    public void update(CommonParameterHolder commPara_, BaseHolder newObj_,
            BaseHolder oldObj_) throws Exception
    {
        dao_.update((FamilyHolder)newObj_);
    }


    public int getCountOfSummary(BaseHolder parameter_) throws Exception
    {
        return dao_.getCountOfSummary(((FamilyHolder)parameter_));
    }


    public List getListOfSummary(BaseHolder parameter_) throws Exception
    {
        return dao_.getListOfSummary(((FamilyHolder)parameter_));
    }
    
    
    public FamilyHolder getFamilyByName(String familyName) throws Exception
    {
        FamilyHolder param = new FamilyHolder();
        param.setFamilyName(familyName);
        
        List<FamilyHolder> list = this.getFamilies(param);
        if(list!=null && list.size()>0)
            return list.get(0);
        
        return null;
    }


    public boolean isFamilyExist(String familyName) throws Exception
    {
        FamilyHolder obj_ = this.getFamilyByName(familyName);
        
        return obj_==null? false : true;
    }
    
    
    public BigDecimal getOid() throws Exception
    {
        return oidDao_.getOid();
    }
    
    
    public FamilyHolder getFamilyByUserOid(BigDecimal userOid) throws Exception
    {
        BigDecimal familyOid = null;
        
        FamilyUserHolder param = new FamilyUserHolder();
        param.setUserOid(userOid);
        
        List<FamilyUserHolder> list = familyUserDao_.getFamilyUsers(param);
        if(list!=null && list.size()>0)
            familyOid = list.get(0).getFamilyOid();
        else
            return null;
        
        return this.getFamilyByKey(familyOid);
        
    }
    
    
    public List<UserProfileHolder> getUsersByFamilyOid(BigDecimal familyOid)
            throws Exception
    {
        FamilyUserHolder fuParam = new FamilyUserHolder();
        fuParam.setFamilyOid(familyOid);
        
        List<FamilyUserHolder> list = familyUserDao_.getFamilyUsers(fuParam);
        
        if(list==null || list.size()==0)
            return null;
        
        List<UserProfileHolder> rlt = new ArrayList();
        for(FamilyUserHolder fu : list)
        {
            UserProfileHolder param = new UserProfileHolder();
            param.setUserOid(fu.getUserOid());
            
            List<UserProfileHolder> uList = userProfileDao_.getUserProfiles(param);
            if(uList!=null && uList.size()>0)
                rlt.add(uList.get(0));
        }
        
        return rlt;
    }

    
    public boolean isFamilyHasUsers(BigDecimal familyOid) throws Exception
    {
        List list = this.getUsersByFamilyOid(familyOid);
        
        if(list!=null && list.size()>0)
            return true;
        
        return false;
    }

    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setDao(FamilyDao dao_)
    {
        this.dao_ = dao_;
    }
    
    public void setOidDao(OidDao oidDao_)
    {
        this.oidDao_ = oidDao_;
    }


    public void setFamilyUserDao(FamilyUserDao familyUserDao_)
    {
        this.familyUserDao_ = familyUserDao_;
    }


    public void setUserProfileDao(UserProfileDao userProfileDao_)
    {
        this.userProfileDao_ = userProfileDao_;
    }

}
