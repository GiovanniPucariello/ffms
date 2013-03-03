package com.oyl.ffms.service;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.holder.BankCardHolder;

public interface BankCardService
{
    public List<BankCardHolder> getBankCards(BankCardHolder param) throws Exception;
    
    
    public BankCardHolder getBankCardByKey(BigDecimal bcOid) throws Exception;
    
    
    public BankCardHolder getBankCardByCardNo(String cardNo) throws Exception;
    
    
    public List<BankCardHolder> getBankCarsByUser(BigDecimal userOid) throws Exception;
    
    
    public boolean isBankCardUsedBySalaryRecord(BigDecimal bcOid) throws Exception;
    
    
    public BigDecimal getOid() throws Exception;
}
