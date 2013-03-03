package com.oyl.ffms.holder;

import java.math.BigDecimal;
import java.util.Date;

import com.oyl.base.holder.BaseHolder;

public class ItemHolder extends BaseHolder
{
    private BigDecimal itemOid;
    private BigDecimal categoryOid;
    private BigDecimal userOid;
    private String itemDesc;
    private BigDecimal itemCost;
    private BigDecimal itemQuantity;
    private String ctlStatus;
    private Date createDate;
    private BigDecimal ccOid;
    private BigDecimal bcOid;
    
    private String categoryName;
    private String userAlias;
    private BigDecimal itemTotalCost;
    private Date fromDate;
    private Date toDate;
    
    private BigDecimal selectedUserOid;


    public BigDecimal getBcOid()
    {
        return bcOid;
    }


    public void setBcOid(BigDecimal bcOid)
    {
        this.bcOid = bcOid;
    }


    public BigDecimal getSelectedUserOid()
    {
        return selectedUserOid;
    }


    public void setSelectedUserOid(BigDecimal selectedUserOid)
    {
        this.selectedUserOid = selectedUserOid;
    }


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


    public String getCtlStatus()
    {
        return ctlStatus;
    }


    public void setCtlStatus(String ctlStatus)
    {
        this.ctlStatus = ctlStatus;
    }


    public String getCategoryName()
    {
        return categoryName;
    }


    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }


    public BigDecimal getItemTotalCost()
    {
        return itemTotalCost;
    }


    public void setItemTotalCost(BigDecimal itemTotalCost)
    {
        this.itemTotalCost = itemTotalCost;
    }


    public BigDecimal getItemOid()
    {
        return itemOid;
    }


    public void setItemOid(BigDecimal itemOid)
    {
        this.itemOid = itemOid;
    }


    public BigDecimal getCategoryOid()
    {
        return categoryOid;
    }


    public void setCategoryOid(BigDecimal categoryOid)
    {
        this.categoryOid = categoryOid;
    }


    public BigDecimal getUserOid()
    {
        return userOid;
    }


    public void setUserOid(BigDecimal userOid)
    {
        this.userOid = userOid;
    }


    public String getItemDesc()
    {
        return itemDesc;
    }


    public void setItemDesc(String itemDesc)
    {
        this.itemDesc = itemDesc;
    }


    public BigDecimal getItemCost()
    {
        return itemCost;
    }


    public void setItemCost(BigDecimal itemCost)
    {
        this.itemCost = itemCost;
    }


    public Date getCreateDate()
    {
        return createDate;
    }


    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }


    public BigDecimal getItemQuantity()
    {
        return itemQuantity;
    }


    public void setItemQuantity(BigDecimal itemQuantity)
    {
        this.itemQuantity = itemQuantity;
    }


    public String getUserAlias()
    {
        return userAlias;
    }


    public void setUserAlias(String userAlias)
    {
        this.userAlias = userAlias;
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
