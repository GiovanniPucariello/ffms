package com.oyl.base.holder;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;

public class BaseHolder implements Serializable
{
    private String key_;
    private String test_;
    private int topRowCount_;
    private int startRecordNum_;
    private int endRecordNum_;
    private String sortField_;
    private String sortOrder_;
    

    public String getKey()
    {
        return key_;
    }

    public void setKey(String key_)
    {
        this.key_ = key_;
    }

    public String getTest()
    {
        return test_;
    }

    public void setTest(String test_)
    {
        this.test_ = test_;
    }

    public int getStartRecordNum()
    {
        return startRecordNum_;
    }
    
    public int getStartRecordNumForMySql()
    {
        return startRecordNum_ - 1;
    }

    public void setStartRecordNum(int startRecordNum_)
    {
        this.startRecordNum_ = startRecordNum_;
    }
    
    public int getEndRecordNum()
    {
        return endRecordNum_;
    }

    public void setEndRecordNum(int endRecordNum_)
    {
        this.endRecordNum_ = endRecordNum_;
    }
    
    public int getPerPageSize()
    {
        return this.endRecordNum_ - this.startRecordNum_ + 1;
    }
    
    public String getSortField()
    {
        return sortField_;
    }

    public void setSortField(String sortField_)
    {
        this.sortField_ = sortField_;
    }

    public String getSortOrder()
    {
        return sortOrder_;
    }

    public void setSortOrder(String sortOrder_)
    {
        this.sortOrder_ = sortOrder_;
    }
    
    public String getOppositeSortOrder()
    {
        if (sortOrder_ == null || sortOrder_.trim().equals("") || sortOrder_.trim().equals("asc"))
            return "desc";
        else 
            return "asc";       
    }
    
    public int getTopRowCount()
    {
        return topRowCount_;
    }

    public void setTopRowCount(int topRowCount_)
    {
        this.topRowCount_ = topRowCount_;
    }

    public String toString()
    {
        try
        {
            return BeanUtils.describe(this).toString();
        }
        catch (Exception exception)
        {
            return exception.getMessage();
        }
    }
    
    public int hashCode()
    {
        return toString().hashCode();
    }
    
    public void trimAllString() throws Exception
    {
        Method[] methods = this.getClass().getMethods();
        if (methods != null && methods.length > 0)
        {
            for (int i = 0; i < methods.length; i++)
            {
                Method method = methods[i];
                if (method.getName().startsWith("get"))
                {
                    Object resultObj = method.invoke(this, new Object[] {});
                    if (resultObj != null && resultObj instanceof String)
                    {
                        String resultStr = (String) resultObj;
                        String setMethodName = "set"
                                + method.getName().substring(3,
                                        method.getName().length());
                        
                        try
                        {
                            Method setMethod = this.getClass()
                                    .getMethod(setMethodName,
                                            new Class[] { String.class });
                            setMethod.invoke(this, new Object[] { resultStr
                                    .trim() });
                        }
                        catch(NoSuchMethodException e)
                        {
                            // there is not setXXX method
                        }
                    }
                }
            }
        }
    }   
    
    public void setAllEmptyStringToNull() throws Exception
    {
        Method[] methods = this.getClass().getMethods();
        if (methods != null && methods.length > 0)
        {
            for (int i = 0; i < methods.length; i++)
            {
                Method method = methods[i];
                if (method.getName().startsWith("get"))
                {
                    Object resultObj = method.invoke(this, new Object[] {});
                    if(resultObj instanceof String)
                    {
                        String result = (String) resultObj;
                        if("".equals(result.trim()))
                        {
                            String setMethodName = "set"
                                + method.getName().substring(3,
                                        method.getName().length());
                            try
                            {
                                Method setMethod = this.getClass()
                                        .getMethod(setMethodName,
                                                new Class[] { String.class });
                                setMethod.invoke(this, new Object[] { null });
                            }
                            catch(NoSuchMethodException e)
                            {
                                // there is not setXXX method
                            }
                        }
                    }
                }
            }
        }
    }    
}
