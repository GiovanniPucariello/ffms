package com.oyl.ffms.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReportService
{
    public List getItemForDetailReport(BigDecimal userOid,
            BigDecimal selectedUserOid, BigDecimal categoryOid, Date fromDate,
            Date toDate) throws Exception;
    
    
    public List getItemForDailyReport(BigDecimal userOid,
            BigDecimal selectedUserOid, Date fromDate, Date toDate)
            throws Exception;
    
    
    public List getItemForMonthlyReport(BigDecimal userOid,
            BigDecimal selectedUserOid, Date fromDate, Date toDate)
            throws Exception;
    
    
    public List getSalaryForDetailReport(BigDecimal userOid,
            BigDecimal selectedUserOid, String srType, Date fromDate,
            Date toDate) throws Exception;
    
    
    public List getSalaryForYearlyReport(BigDecimal userOid,
            BigDecimal selectedUserOid, Date fromDate, Date toDate)
            throws Exception;
}
