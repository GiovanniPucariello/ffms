package com.oyl.ffms.service;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.holder.CreditCardHolder;

public interface CreditCardService
{
    public List<CreditCardHolder> getCreditCards(CreditCardHolder param) throws Exception;
    
    
    public CreditCardHolder getCreditCardByKey(BigDecimal ccOid) throws Exception;
    
    
    public CreditCardHolder getCreditCardByCardNo(String cardNo) throws Exception;
    
    
    public List<CreditCardHolder> getCreditCarsByUser(BigDecimal userOid) throws Exception;
    
    
    public boolean isCreditCardUsedByAnyItem(BigDecimal ccOid) throws Exception;
    
    
    public BigDecimal getOid() throws Exception;
}
