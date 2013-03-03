package com.oyl.ffms.service;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.holder.BankCardHolder;
import com.oyl.ffms.holder.BankCardRecordHolder;

public interface BankCardRecordService
{
    public List<BankCardRecordHolder> getBankCardRecords(BankCardRecordHolder param) throws Exception;
    
    
    public BankCardRecordHolder getBankCardRecordByKey(BigDecimal bcrOid) throws Exception;
    
    
    public List<BankCardRecordHolder> getBankCardRecordsByBankCard(BigDecimal bcOid) throws Exception;
    
    
    public void confirmBankCardRecord(BankCardRecordHolder record, BankCardHolder card) throws Exception;
    
    
    public void transfer(BankCardHolder source, BankCardHolder dest,
            BankCardRecordHolder sourceRecord, BankCardRecordHolder destRecord)
            throws Exception;
    
    
    public BigDecimal getOid() throws Exception;
}
