package com.oyl.ffms.holder;

import java.math.BigDecimal;

import com.oyl.base.holder.BaseHolder;

public class UserProfileHolder extends BaseHolder
{
    private BigDecimal userOid;
    private String userName;
    private String userAlias;
    private String userType;
    private String phone;
    private String email;
    private String gender;
    private String loginId;
    private String loginPasswd;
    private String ctlStatus;
    private BigDecimal failedAttempt;
    
    //----- ex fields -----//
    private BigDecimal familyOid;
    private String familyName;
    private String confirmPasswd;
    
    
    public UserProfileHolder()
    {
        
    }
    
    
    public UserProfileHolder(UserProfileHolder user)
    {
        this.userOid        = user.getUserOid();
        this.userName       = user.getUserName();
        this.userAlias      = user.getUserAlias();
        this.userType       = user.getUserType();
        this.phone          = user.getPhone();
        this.email          = user.getEmail();
        this.gender         = user.getGender();
        this.loginId        = user.getLoginId();
        this.loginPasswd    = user.getLoginPasswd();
        this.ctlStatus      = user.getCtlStatus();
        this.failedAttempt  = user.getFailedAttempt();
        this.familyOid      = user.getFamilyOid();
        this.familyName     = user.getFamilyName();
        this.confirmPasswd  = user.getConfirmPasswd();
    }


    public String getUserAlias()
    {
        return userAlias;
    }


    public void setUserAlias(String userAlias)
    {
        this.userAlias = userAlias;
    }


    public BigDecimal getFamilyOid()
    {
        return familyOid;
    }


    public void setFamilyOid(BigDecimal familyOid)
    {
        this.familyOid = familyOid;
    }


    public String getFamilyName()
    {
        return familyName;
    }


    public void setFamilyName(String familyName)
    {
        this.familyName = familyName;
    }


    public BigDecimal getUserOid()
    {
        return userOid;
    }


    public void setUserOid(BigDecimal userOid)
    {
        this.userOid = userOid;
    }


    public String getPhone()
    {
        return phone;
    }


    public void setPhone(String phone)
    {
        this.phone = phone;
    }


    public String getEmail()
    {
        return email;
    }


    public void setEmail(String email)
    {
        this.email = email;
    }


    public String getGender()
    {
        return gender;
    }


    public void setGender(String gender)
    {
        this.gender = gender;
    }


    public String getLoginId()
    {
        return loginId;
    }


    public void setLoginId(String loginId)
    {
        this.loginId = loginId;
    }


    public String getLoginPasswd()
    {
        return loginPasswd;
    }


    public void setLoginPasswd(String loginPasswd)
    {
        this.loginPasswd = loginPasswd;
    }


    public String getCtlStatus()
    {
        return ctlStatus;
    }


    public void setCtlStatus(String ctlStatus)
    {
        this.ctlStatus = ctlStatus;
    }


    public BigDecimal getFailedAttempt()
    {
        return failedAttempt;
    }


    public void setFailedAttempt(BigDecimal failedAttempt)
    {
        this.failedAttempt = failedAttempt;
    }


    public String getUserName()
    {
        return userName;
    }


    public void setUserName(String userName)
    {
        this.userName = userName;
    }


    public String getUserType()
    {
        return userType;
    }


    public void setUserType(String userType)
    {
        this.userType = userType;
    }


    public String getConfirmPasswd()
    {
        return confirmPasswd;
    }


    public void setConfirmPasswd(String confirmPasswd)
    {
        this.confirmPasswd = confirmPasswd;
    }
}
