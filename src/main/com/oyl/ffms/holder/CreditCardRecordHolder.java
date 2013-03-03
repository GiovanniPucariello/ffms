package com.oyl.ffms.holder;

import java.math.BigDecimal;
import java.util.Date;

import com.oyl.base.holder.BaseHolder;

public class CreditCardRecordHolder extends BaseHolder
{
    private BigDecimal ccrOid;
    private String ccrType;
    private BigDecimal amount;
    private String description;
    private String ctlStatus;
    private Date createDate;
    private BigDecimal ccOid;
    private BigDecimal bcOid;


    public BigDecimal getBcOid()
    {
        return bcOid;
    }


    public void setBcOid(BigDecimal bcOid)
    {
        this.bcOid = bcOid;
    }


    public BigDecimal getCcrOid()
    {
        return ccrOid;
    }


    public void setCcrOid(BigDecimal ccrOid)
    {
        this.ccrOid = ccrOid;
    }


    public String getCcrType()
    {
        return ccrType;
    }


    public void setCcrType(String ccrType)
    {
        this.ccrType = ccrType;
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


    public BigDecimal getCcOid()
    {
        return ccOid;
    }


    public void setCcOid(BigDecimal ccOid)
    {
        this.ccOid = ccOid;
    }

}
