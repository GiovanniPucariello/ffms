package com.oyl.ffms.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.base.holder.BaseHolder;
import com.oyl.base.holder.CommonParameterHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.ffms.dao.FamilyUserDao;
import com.oyl.ffms.dao.OidDao;
import com.oyl.ffms.dao.UserProfileDao;
import com.oyl.ffms.dao.UserRoleDao;
import com.oyl.ffms.holder.FamilyUserHolder;
import com.oyl.ffms.holder.UserProfileHolder;
import com.oyl.ffms.holder.UserRoleHolder;

public class UserProfileServiceImpl implements
        com.oyl.ffms.service.UserProfileService, DBActionService, BaseService
{
    private UserProfileDao dao_;
    private OidDao oidDao_;
    private FamilyUserDao familyUserDao_;
    private UserRoleDao userRoleDao_;
    
    
    public UserProfileHolder getUserProfileByKey(BigDecimal userOid)
            throws Exception
    {
        UserProfileHolder param = new UserProfileHolder();
        param.setUserOid(userOid);
        
        List<UserProfileHolder> list = this.getUserProfiles(param);
        if(list!=null && list.size()>0)
            return list.get(0);
        
        return null;
    }
    
    
    public UserProfileHolder getUserProfileByLoginId(String loginId)
            throws Exception
    {
        UserProfileHolder param = new UserProfileHolder();
        param.setLoginId(loginId);
        
        List<UserProfileHolder> list = this.getUserProfiles(param);
        if(list!=null && list.size()>0)
            return list.get(0);
        
        return null;
    }


    public List<UserProfileHolder> getUserProfiles(UserProfileHolder param)
            throws Exception
    {
        return dao_.getUserProfiles(param);
    }


    public void delete(CommonParameterHolder commPara_, BaseHolder oldObj_)
            throws Exception
    {
        UserProfileHolder user = (UserProfileHolder)oldObj_;
        
        FamilyUserHolder fu = new FamilyUserHolder();
        fu.setUserOid(user.getUserOid());
        
        UserRoleHolder ur = new UserRoleHolder();
        ur.setUserOid(user.getUserOid());
        
        familyUserDao_.delete(fu);
        userRoleDao_.delete(ur);
        dao_.delete(user);
    }


    public void insert(CommonParameterHolder commPara_, BaseHolder newObj_)
            throws Exception
    {
        UserProfileHolder user = (UserProfileHolder)newObj_;
        
        dao_.insert(user);
        
        FamilyUserHolder fu = new FamilyUserHolder();
        fu.setFamilyOid(user.getFamilyOid());
        fu.setUserOid(user.getUserOid());
        familyUserDao_.insert(fu);
        
        UserRoleHolder ur = new UserRoleHolder();
        ur.setUserOid(user.getUserOid());
        ur.setRoleOid(new BigDecimal(2));
        userRoleDao_.insert(ur);
        
    }


    public void update(CommonParameterHolder commPara_, BaseHolder newObj_,
            BaseHolder oldObj_) throws Exception
    {
        dao_.update((UserProfileHolder)newObj_);
    }


    public int getCountOfSummary(BaseHolder parameter_) throws Exception
    {
        return dao_.getCountOfSummary(((UserProfileHolder)parameter_));
    }


    public List getListOfSummary(BaseHolder parameter_) throws Exception
    {
        return dao_.getListOfSummary(((UserProfileHolder)parameter_));
    }


    public BigDecimal getOid() throws Exception
    {
        return oidDao_.getOid();
    }
    
    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setDao(UserProfileDao dao_)
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


    public void setUserRoleDao(UserRoleDao userRoleDao_)
    {
        this.userRoleDao_ = userRoleDao_;
    }

}
