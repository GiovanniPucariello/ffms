package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.CreditCardRecordHolder;

public interface CreditCardRecordDao
{
    public List<CreditCardRecordHolder> getCreditCardRecords(CreditCardRecordHolder param);
    
    public int getCountOfSummary(CreditCardRecordHolder param);    
    
    public List<CreditCardRecordHolder> getListOfSummary(CreditCardRecordHolder param);
    
    public int insert(CreditCardRecordHolder param);
    
    public int delete(CreditCardRecordHolder param);
    
    public int update(CreditCardRecordHolder param);
}
