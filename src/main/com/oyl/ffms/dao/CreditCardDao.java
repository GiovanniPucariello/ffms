package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.CreditCardHolder;

public interface CreditCardDao
{
    public List<CreditCardHolder> getCreditCards(CreditCardHolder param);
    
    public int getCountOfSummary(CreditCardHolder param);    
    
    public List<CreditCardHolder> getListOfSummary(CreditCardHolder param);
    
    public int insert(CreditCardHolder param);
    
    public int delete(CreditCardHolder param);
    
    public int update(CreditCardHolder param);
}
