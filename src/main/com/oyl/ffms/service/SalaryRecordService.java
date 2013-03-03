package com.oyl.ffms.service;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.holder.SalaryRecordHolder;

public interface SalaryRecordService
{
    public List<SalaryRecordHolder> getSalaryRecords(SalaryRecordHolder param) throws Exception;
    
    
    public SalaryRecordHolder getSalaryRecordByKey(BigDecimal srOid) throws Exception;
    
    
    public void confirmSalaryRecord(SalaryRecordHolder param) throws Exception;
    
    
    public BigDecimal getOid() throws Exception;
}
