package com.oyl.ffms.holder;

import java.math.BigDecimal;
import java.util.Date;

import com.oyl.base.holder.BaseHolder;

public class SalaryRecordHolder extends BaseHolder
{
    private BigDecimal srOid;
    private String srType;
    private BigDecimal amount;
    private String description;
    private String ctlStatus;
    private Date createDate;
    private BigDecimal userOid;
    private BigDecimal bcOid;

    // ex
    private String userName;
    private BigDecimal curUserOid;
    private Date fromDate;
    private Date toDate;


    public Date getFromDate()
    {
        return fromDate;
    }


    public void setFromDate(Date fromDate)
    {
        this.fromDate = fromDate;
    }


    public Date getToDate()
    {
        return toDate;
    }


    public void setToDate(Date toDate)
    {
        this.toDate = toDate;
    }


    public BigDecimal getSrOid()
    {
        return srOid;
    }


    public void setSrOid(BigDecimal srOid)
    {
        this.srOid = srOid;
    }


    public String getSrType()
    {
        return srType;
    }


    public void setSrType(String srType)
    {
        this.srType = srType;
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


    public BigDecimal getUserOid()
    {
        return userOid;
    }


    public void setUserOid(BigDecimal userOid)
    {
        this.userOid = userOid;
    }


    public BigDecimal getBcOid()
    {
        return bcOid;
    }


    public void setBcOid(BigDecimal bcOid)
    {
        this.bcOid = bcOid;
    }


    public String getUserName()
    {
        return userName;
    }


    public void setUserName(String userName)
    {
        this.userName = userName;
    }


    public BigDecimal getCurUserOid()
    {
        return curUserOid;
    }


    public void setCurUserOid(BigDecimal curUserOid)
    {
        this.curUserOid = curUserOid;
    }

}
