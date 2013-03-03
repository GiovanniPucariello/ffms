package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.BankCardHolder;

public interface BankCardDao
{
    public List<BankCardHolder> getBankCards(BankCardHolder param);
    
    public int getCountOfSummary(BankCardHolder param);    
    
    public List<BankCardHolder> getListOfSummary(BankCardHolder param);
    
    public int insert(BankCardHolder param);
    
    public int delete(BankCardHolder param);
    
    public int update(BankCardHolder param);
}
