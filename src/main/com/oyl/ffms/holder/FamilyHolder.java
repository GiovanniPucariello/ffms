package com.oyl.ffms.holder;

import java.math.BigDecimal;

import com.oyl.base.holder.BaseHolder;

public class FamilyHolder extends BaseHolder
{
    private BigDecimal familyOid;
    private String familyName;
    private String address;
    private String phone;
    private BigDecimal numOfMember;


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


    public String getAddress()
    {
        return address;
    }


    public void setAddress(String address)
    {
        this.address = address;
    }


    public String getPhone()
    {
        return phone;
    }


    public void setPhone(String phone)
    {
        this.phone = phone;
    }


    public BigDecimal getNumOfMember()
    {
        return numOfMember;
    }


    public void setNumOfMember(BigDecimal numOfMember)
    {
        this.numOfMember = numOfMember;
    }

}
