package com.oyl.ffms.dao;

import java.util.List;
import java.util.Map;

public interface ReportDao
{
    public List getItemForDetailReport(Map param);
    
    public List getItemForDailyReport(Map param);
    
    public List getItemForMonthlyReport(Map param);
    
    public List getSalaryForDetailReport(Map param);
    
    public List getSalaryForYearlyReport(Map param);
}
