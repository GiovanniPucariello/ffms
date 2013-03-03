package com.oyl.ffms.holder;

import java.math.BigDecimal;

import com.oyl.base.holder.BaseHolder;

public class CreditCardHolder extends BaseHolder
{
    private BigDecimal ccOid;
    private String cardNo;
    private BigDecimal quota;
    private BigDecimal debt;
    private BigDecimal point;
    private BigDecimal pointCondition;
    private BigDecimal userOid;

    // ex
    private String userName;
    private BigDecimal curUserOid;


    public BigDecimal getCurUserOid()
    {
        return curUserOid;
    }


    public void setCurUserOid(BigDecimal curUserOid)
    {
        this.curUserOid = curUserOid;
    }


    public BigDecimal getCcOid()
    {
        return ccOid;
    }


    public void setCcOid(BigDecimal ccOid)
    {
        this.ccOid = ccOid;
    }


    public String getCardNo()
    {
        return cardNo;
    }


    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }


    public BigDecimal getQuota()
    {
        return quota;
    }


    public void setQuota(BigDecimal quota)
    {
        this.quota = quota;
    }


    public BigDecimal getDebt()
    {
        return debt;
    }


    public void setDebt(BigDecimal debt)
    {
        this.debt = debt;
    }


    public BigDecimal getPoint()
    {
        return point;
    }


    public void setPoint(BigDecimal point)
    {
        this.point = point;
    }


    public BigDecimal getPointCondition()
    {
        return pointCondition;
    }


    public void setPointCondition(BigDecimal pointCondition)
    {
        this.pointCondition = pointCondition;
    }


    public BigDecimal getUserOid()
    {
        return userOid;
    }


    public void setUserOid(BigDecimal userOid)
    {
        this.userOid = userOid;
    }


    public String getUserName()
    {
        return userName;
    }


    public void setUserName(String userName)
    {
        this.userName = userName;
    }

}
