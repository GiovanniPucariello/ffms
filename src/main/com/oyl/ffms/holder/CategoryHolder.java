package com.oyl.ffms.holder;

import java.math.BigDecimal;
import java.util.Date;

import com.oyl.base.holder.BaseHolder;

public class CategoryHolder extends BaseHolder
{
    private BigDecimal categoryOid;
    private String categoryType;
    private String categoryName;
    private BigDecimal familyOid;

    // ----- ex ----- //
    private BigDecimal userOid;
    private BigDecimal itemNum;
    private BigDecimal itemAmount;
    private Date itemFromDate;
    private Date itemToDate;


    public Date getItemFromDate()
    {
        return itemFromDate;
    }


    public void setItemFromDate(Date itemFromDate)
    {
        this.itemFromDate = itemFromDate;
    }


    public Date getItemToDate()
    {
        return itemToDate;
    }


    public void setItemToDate(Date itemToDate)
    {
        this.itemToDate = itemToDate;
    }


    public BigDecimal getItemAmount()
    {
        return itemAmount;
    }


    public void setItemAmount(BigDecimal itemAmount)
    {
        this.itemAmount = itemAmount;
    }


    public BigDecimal getUserOid()
    {
        return userOid;
    }


    public void setUserOid(BigDecimal userOid)
    {
        this.userOid = userOid;
    }


    public BigDecimal getFamilyOid()
    {
        return familyOid;
    }


    public void setFamilyOid(BigDecimal familyOid)
    {
        this.familyOid = familyOid;
    }


    public BigDecimal getCategoryOid()
    {
        return categoryOid;
    }


    public void setCategoryOid(BigDecimal categoryOid)
    {
        this.categoryOid = categoryOid;
    }


    public String getCategoryName()
    {
        return categoryName;
    }


    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }


    public String getCategoryType()
    {
        return categoryType;
    }


    public void setCategoryType(String categoryType)
    {
        this.categoryType = categoryType;
    }


    public BigDecimal getItemNum()
    {
        return itemNum;
    }


    public void setItemNum(BigDecimal itemNum)
    {
        this.itemNum = itemNum;
    }
}
