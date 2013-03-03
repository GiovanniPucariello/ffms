package com.oyl.ffms.holder;

import java.math.BigDecimal;

import com.oyl.base.holder.BaseHolder;

public class RoleModuleHolder extends BaseHolder
{
    private BigDecimal roleOid;
    private String moduleId;


    public BigDecimal getRoleOid()
    {
        return roleOid;
    }


    public void setRoleOid(BigDecimal roleOid)
    {
        this.roleOid = roleOid;
    }


    public String getModuleId()
    {
        return moduleId;
    }


    public void setModuleId(String moduleId)
    {
        this.moduleId = moduleId;
    }
}
