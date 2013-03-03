package com.oyl.ffms.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.ReportDao;

public class ReportDaoIbatis extends SqlMapClientDaoSupport implements
        ReportDao
{

    @Override
    public List getItemForDetailReport(Map param)
    {
        return this.getSqlMapClientTemplate().queryForList("getItemForDetailReport", param);
    }
    
    
    @Override
    public List getItemForDailyReport(Map param)
    {
        return this.getSqlMapClientTemplate().queryForList("getItemForDailyReport", param);
    }
    
    
    @Override
    public List getItemForMonthlyReport(Map param)
    {
        return this.getSqlMapClientTemplate().queryForList("getItemForMonthlyReport", param);
    }
    

    @Override
    public List getSalaryForDetailReport(Map param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSalaryForDetailReport", param);
    }
    
    
    @Override
    public List getSalaryForYearlyReport(Map param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSalaryForYearlyReport", param);
    }

}
