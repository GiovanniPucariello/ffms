package com.oyl.ffms.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.base.holder.BaseHolder;
import com.oyl.base.holder.CommonParameterHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.ffms.dao.BankCardDao;
import com.oyl.ffms.dao.BankCardRecordDao;
import com.oyl.ffms.dao.OidDao;
import com.oyl.ffms.holder.BankCardHolder;
import com.oyl.ffms.holder.BankCardRecordHolder;
import com.oyl.ffms.service.BankCardRecordService;
import com.oyl.ffms.util.CommonConstants;

public class BankCardRecordServiceImpl implements BaseService, DBActionService,
        BankCardRecordService, CommonConstants
{
    private BankCardRecordDao dao;
    private BankCardDao bankCardDao;
    private OidDao oidDao;
    
    public int getCountOfSummary(BaseHolder parameter) throws Exception
    {
        return dao.getCountOfSummary((BankCardRecordHolder)parameter);
    }


    public List getListOfSummary(BaseHolder parameter) throws Exception
    {
        return dao.getListOfSummary((BankCardRecordHolder)parameter);
    }


    public void delete(CommonParameterHolder commPara, BaseHolder oldObj)
            throws Exception
    {
        dao.delete((BankCardRecordHolder)oldObj);
    }


    public void insert(CommonParameterHolder commPara, BaseHolder newObj)
            throws Exception
    {
        dao.insert((BankCardRecordHolder)newObj);
    }


    public void update(CommonParameterHolder commPara, BaseHolder newObj,
            BaseHolder oldObj) throws Exception
    {
        dao.update((BankCardRecordHolder)newObj);
    }


    public BankCardRecordHolder getBankCardRecordByKey(BigDecimal bcrOid)
            throws Exception
    {
        BankCardRecordHolder param = new BankCardRecordHolder();
        param.setBcrOid(bcrOid);
        
        List<BankCardRecordHolder> rlt = this.getBankCardRecords(param);
        
        if (rlt != null && !rlt.isEmpty())
        {
            return rlt.get(0);
        }
        
        return null;
    }
    
    
    public List<BankCardRecordHolder> getBankCardRecordsByBankCard(BigDecimal bcOid) throws Exception
    {
        BankCardRecordHolder param = new BankCardRecordHolder();
        param.setBcOid(bcOid);
        
        return this.getBankCardRecords(param);
    }


    public List<BankCardRecordHolder> getBankCardRecords(
            BankCardRecordHolder param) throws Exception
    {
        return dao.getBankCardRecords(param);
    }
    
    
    public void confirmBankCardRecord(BankCardRecordHolder record, BankCardHolder card) throws Exception
    {
        BigDecimal balance = card.getBalance();
        BigDecimal amount  = record.getAmount();
        
        BigDecimal finalBalance = null;
        
        if (VALUE_BANK_CARD_RECORD_BCRTYPE_WITHDRAW.equalsIgnoreCase(record.getBcrType()))
        {
            finalBalance = balance.subtract(amount);
        }
        else
        {
            finalBalance = balance.add(amount);
        }
        
        
        card.setBalance(finalBalance);
        record.setCtlStatus(VALUE_BANK_CARD_RECORD_CTLSTATUS_COMPLETE);
        
        dao.update(record);
        bankCardDao.update(card);
    }
    
    
    public void transfer(BankCardHolder source, BankCardHolder dest,
            BankCardRecordHolder sourceRecord, BankCardRecordHolder destRecord)
            throws Exception
    {
        dao.insert(sourceRecord);
        dao.insert(destRecord);
        
        bankCardDao.update(source);
        bankCardDao.update(dest);
    }


    public BigDecimal getOid() throws Exception
    {
        return oidDao.getOid();
    }

    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setDao(BankCardRecordDao dao)
    {
        this.dao = dao;
    }


    public void setOidDao(OidDao oidDao)
    {
        this.oidDao = oidDao;
    }


    public void setBankCardDao(BankCardDao bankCardDao)
    {
        this.bankCardDao = bankCardDao;
    }
    
}
