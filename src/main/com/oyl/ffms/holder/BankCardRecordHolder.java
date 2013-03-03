package com.oyl.ffms.holder;

import java.math.BigDecimal;
import java.util.Date;

import com.oyl.base.holder.BaseHolder;

public class BankCardRecordHolder extends BaseHolder
{
    private BigDecimal bcrOid;
    private String bcrType;
    private BigDecimal amount;
    private String description;
    private String ctlStatus;
    private Date createDate;
    private BigDecimal bcOid;


    public BigDecimal getBcrOid()
    {
        return bcrOid;
    }


    public void setBcrOid(BigDecimal bcrOid)
    {
        this.bcrOid = bcrOid;
    }


    public String getBcrType()
    {
        return bcrType;
    }


    public void setBcrType(String bcrType)
    {
        this.bcrType = bcrType;
    }


    public BigDecimal getAmount()
    {
        return amount;
    }


    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public String getCtlStatus()
    {
        return ctlStatus;
    }


    public void setCtlStatus(String ctlStatus)
    {
        this.ctlStatus = ctlStatus;
    }


    public Date getCreateDate()
    {
        return createDate;
    }


    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }


    public BigDecimal getBcOid()
    {
        return bcOid;
    }


    public void setBcOid(BigDecimal bcOid)
    {
        this.bcOid = bcOid;
    }

}
