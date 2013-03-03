package com.oyl.ffms.holder;

import java.math.BigDecimal;

import com.oyl.base.holder.BaseHolder;

public class RoleProfileHolder extends BaseHolder
{
    private BigDecimal roleOid;
    private String roleName;


    public BigDecimal getRoleOid()
    {
        return roleOid;
    }


    public void setRoleOid(BigDecimal roleOid)
    {
        this.roleOid = roleOid;
    }


    public String getRoleName()
    {
        return roleName;
    }


    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

}
