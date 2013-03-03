package com.oyl.ffms.holder;

import java.math.BigDecimal;

import com.oyl.base.holder.BaseHolder;

public class UserRoleHolder extends BaseHolder
{
    private BigDecimal userOid;
    private BigDecimal roleOid;


    public BigDecimal getUserOid()
    {
        return userOid;
    }


    public void setUserOid(BigDecimal userOid)
    {
        this.userOid = userOid;
    }


    public BigDecimal getRoleOid()
    {
        return roleOid;
    }


    public void setRoleOid(BigDecimal roleOid)
    {
        this.roleOid = roleOid;
    }
}
