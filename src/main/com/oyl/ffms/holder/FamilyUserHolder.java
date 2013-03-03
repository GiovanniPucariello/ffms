package com.oyl.ffms.holder;

import java.math.BigDecimal;

import com.oyl.base.holder.BaseHolder;

public class FamilyUserHolder extends BaseHolder
{
    private BigDecimal familyOid;
    private BigDecimal userOid;


    public BigDecimal getFamilyOid()
    {
        return familyOid;
    }


    public void setFamilyOid(BigDecimal familyOid)
    {
        this.familyOid = familyOid;
    }


    public BigDecimal getUserOid()
    {
        return userOid;
    }


    public void setUserOid(BigDecimal userOid)
    {
        this.userOid = userOid;
    }

}
