package com.oyl.ffms.holder;

import java.math.BigDecimal;

import com.oyl.base.holder.BaseHolder;

public class BankCardHolder extends BaseHolder
{
    private BigDecimal bcOid;
    private String cardNo;
    private BigDecimal balance;
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


    public String getUserName()
    {
        return userName;
    }


    public void setUserName(String userName)
    {
        this.userName = userName;
    }


    public BigDecimal getBcOid()
    {
        return bcOid;
    }


    public void setBcOid(BigDecimal bcOid)
    {
        this.bcOid = bcOid;
    }


    public String getCardNo()
    {
        return cardNo;
    }


    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }


    public BigDecimal getBalance()
    {
        return balance;
    }


    public void setBalance(BigDecimal balance)
    {
        this.balance = balance;
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
