package com.oyl.ffms.service;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.holder.CreditCardRecordHolder;

public interface CreditCardRecordService
{
    public List<CreditCardRecordHolder> getCreditCardRecords(CreditCardRecordHolder param) throws Exception;
    
    
    public CreditCardRecordHolder getCreditCardRecordByKey(BigDecimal ccrOid) throws Exception;
    
    
    public List<CreditCardRecordHolder> getCreditCardRecordsByCreditCard(BigDecimal ccOid) throws Exception;
    
    
    public void confirmCreditCardRecord(CreditCardRecordHolder param) throws Exception;
    
    
    public BigDecimal getOid() throws Exception;
}
