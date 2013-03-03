package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.BankCardRecordHolder;

public interface BankCardRecordDao
{
    public List<BankCardRecordHolder> getBankCardRecords(BankCardRecordHolder param);
    
    public int getCountOfSummary(BankCardRecordHolder param);    
    
    public List<BankCardRecordHolder> getListOfSummary(BankCardRecordHolder param);
    
    public int insert(BankCardRecordHolder param);
    
    public int delete(BankCardRecordHolder param);
    
    public int update(BankCardRecordHolder param);
}
