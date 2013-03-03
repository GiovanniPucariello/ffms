package com.oyl.ffms.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oyl.ffms.dao.ReportDao;
import com.oyl.ffms.service.ReportService;

public class ReportServiceImpl implements ReportService
{
    private ReportDao dao;
    
    @Override
    public List getItemForDetailReport(BigDecimal userOid,
            BigDecimal selectedUserOid, BigDecimal categoryOid, Date fromDate,
            Date toDate) throws Exception
    {
        Map param = new HashMap();
        
        if (null != userOid)
        {
            param.put("userOid", userOid);
        }
        if (null != selectedUserOid)
        {
            param.put("selectedUserOid", selectedUserOid);
        }
        if (null != categoryOid)
        {
            param.put("categoryOid", categoryOid);
        }
        if (null != fromDate)
        {
            param.put("fromDate", fromDate);
        }
        if (null != toDate)
        {
            param.put("toDate", toDate);
        }
        
        return dao.getItemForDetailReport(param);
    }
    
    
    @Override
    public List getItemForDailyReport(BigDecimal userOid,
            BigDecimal selectedUserOid, Date fromDate, Date toDate)
            throws Exception
    {
        Map param = new HashMap();
        
        if (null != userOid)
        {
            param.put("userOid", userOid);
        }
        if (null != selectedUserOid)
        {
            param.put("selectedUserOid", selectedUserOid);
        }
        if (null != fromDate)
        {
            param.put("fromDate", fromDate);
        }
        if (null != toDate)
        {
            param.put("toDate", toDate);
        }
        
        return dao.getItemForDailyReport(param);
    }
    
    
    @Override
    public List getItemForMonthlyReport(BigDecimal userOid,
            BigDecimal selectedUserOid, Date fromDate, Date toDate)
            throws Exception
    {
        Map param = new HashMap();
        
        if (null != userOid)
        {
            param.put("userOid", userOid);
        }
        if (null != selectedUserOid)
        {
            param.put("selectedUserOid", selectedUserOid);
        }
        if (null != fromDate)
        {
            param.put("fromDate", fromDate);
        }
        if (null != toDate)
        {
            param.put("toDate", toDate);
        }
        
        return dao.getItemForMonthlyReport(param);
    }
    
    
    @Override
    public List getSalaryForDetailReport(BigDecimal userOid,
            BigDecimal selectedUserOid, String srType, Date fromDate,
            Date toDate) throws Exception
    {
        Map param = new HashMap();
        
        if (null != userOid)
        {
            param.put("userOid", userOid);
        }
        if (null != selectedUserOid)
        {
            param.put("selectedUserOid", selectedUserOid);
        }
        if (null != srType)
        {
            param.put("srType", srType);
        }
        if (null != fromDate)
        {
            param.put("fromDate", fromDate);
        }
        if (null != toDate)
        {
            param.put("toDate", toDate);
        }
        
        return dao.getSalaryForDetailReport(param);
    }
    
    
    public List getSalaryForYearlyReport(BigDecimal userOid,
            BigDecimal selectedUserOid, Date fromDate, Date toDate)
            throws Exception
    {
        Map param = new HashMap();

        if (null != userOid)
        {
            param.put("userOid", userOid);
        }
        if (null != selectedUserOid)
        {
            param.put("selectedUserOid", selectedUserOid);
        }
        if (null != fromDate)
        {
            param.put("fromDate", fromDate);
        }
        if (null != toDate)
        {
            param.put("toDate", toDate);
        }

        return dao.getSalaryForYearlyReport(param);
    }

    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setDao(ReportDao dao)
    {
        this.dao = dao;
    }

}
