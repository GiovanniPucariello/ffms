package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.SalaryRecordHolder;

public interface SalaryRecordDao
{
    public List<SalaryRecordHolder> getSalaryRecords(SalaryRecordHolder param);
    
    public int getCountOfSummary(SalaryRecordHolder param);    
    
    public List<SalaryRecordHolder> getListOfSummary(SalaryRecordHolder param);
    
    public int insert(SalaryRecordHolder param);
    
    public int delete(SalaryRecordHolder param);
    
    public int update(SalaryRecordHolder param);
}
