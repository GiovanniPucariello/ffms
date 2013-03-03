package com.oyl.ffms.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.base.holder.BaseHolder;
import com.oyl.base.holder.CommonParameterHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.ffms.dao.BankCardDao;
import com.oyl.ffms.dao.BankCardRecordDao;
import com.oyl.ffms.dao.CreditCardDao;
import com.oyl.ffms.dao.CreditCardRecordDao;
import com.oyl.ffms.dao.OidDao;
import com.oyl.ffms.holder.BankCardHolder;
import com.oyl.ffms.holder.BankCardRecordHolder;
import com.oyl.ffms.holder.CreditCardHolder;
import com.oyl.ffms.holder.CreditCardRecordHolder;
import com.oyl.ffms.service.CreditCardRecordService;
import com.oyl.ffms.util.CommonConstants;

public class CreditCardRecordServiceImpl implements DBActionService,
        BaseService, CreditCardRecordService, CommonConstants
{
    private CreditCardRecordDao dao;
    private CreditCardDao creditCardDao;
    private BankCardDao bankCardDao;
    private BankCardRecordDao bankCardRecordDao;
    private OidDao oidDao;
    
    public void delete(CommonParameterHolder commPara, BaseHolder oldObj)
            throws Exception
    {
        dao.delete((CreditCardRecordHolder)oldObj);
    }


    public void insert(CommonParameterHolder commPara, BaseHolder newObj)
            throws Exception
    {
        dao.insert((CreditCardRecordHolder)newObj);
    }


    public void update(CommonParameterHolder commPara, BaseHolder newObj,
            BaseHolder oldObj) throws Exception
    {
        dao.update((CreditCardRecordHolder)newObj);
    }


    public int getCountOfSummary(BaseHolder parameter) throws Exception
    {
        return dao.getCountOfSummary((CreditCardRecordHolder)parameter);
    }


    public List getListOfSummary(BaseHolder parameter) throws Exception
    {
        return dao.getListOfSummary((CreditCardRecordHolder)parameter);
    }


    public CreditCardRecordHolder getCreditCardRecordByKey(BigDecimal ccrOid)
            throws Exception
    {
        CreditCardRecordHolder param = new CreditCardRecordHolder();
        param.setCcrOid(ccrOid);
        
        List<CreditCardRecordHolder> rlt = this.getCreditCardRecords(param);
        
        if(rlt != null && !rlt.isEmpty())
        {
            return rlt.get(0);
        }
        
        return null;
    }


    public List<CreditCardRecordHolder> getCreditCardRecords(
            CreditCardRecordHolder param) throws Exception
    {
        return dao.getCreditCardRecords(param);
    }


    public List<CreditCardRecordHolder> getCreditCardRecordsByCreditCard(
            BigDecimal ccOid) throws Exception
    {
        CreditCardRecordHolder param = new CreditCardRecordHolder();
        param.setCcOid(ccOid);
        
        return this.getCreditCardRecords(param);
    }


    public BigDecimal getOid() throws Exception
    {
        return oidDao.getOid();
    }
    
    
    public void confirmCreditCardRecord(CreditCardRecordHolder param)
            throws Exception
    {
        param.setCtlStatus(VALUE_CREDIT_CARD_RECORD_CTLSTATUS_COMPLETE);
        
        CreditCardHolder cCard = this.getCreditCardByKey(param.getCcOid());
        
        if (VALUE_CREDIT_CARD_RECORD_CCRTYPE_CONSUMPTION.equals(param.getCcrType()))
        {
            cCard.setQuota(cCard.getQuota().subtract(param.getAmount()));
            cCard.setDebt(cCard.getDebt().add(param.getAmount()));
            cCard.setPoint(cCard.getPoint().add(param.getAmount().divide(cCard.getPointCondition(), 0, BigDecimal.ROUND_DOWN)));
            
        }
        else
        {
            cCard.setQuota(cCard.getQuota().add(param.getAmount()));
            cCard.setDebt(cCard.getDebt().subtract(param.getAmount()));
            
            if (null != param.getBcOid())
            {
                BankCardHolder bCard = this.getBankCardByKey(param.getBcOid());
                bCard.setBalance(bCard.getBalance().subtract(param.getAmount()));
                
                BankCardRecordHolder bankCardRecord = new BankCardRecordHolder();
                bankCardRecord.setBcrOid(this.getOid());
                bankCardRecord.setBcrType(VALUE_BANK_CARD_RECORD_BCRTYPE_WITHDRAW);
                bankCardRecord.setAmount(param.getAmount());
                bankCardRecord.setDescription("\u4fe1\u7528\u5361[" + cCard.getCardNo() + "]\u8fd8\u6b3e");
                bankCardRecord.setCtlStatus(VALUE_BANK_CARD_RECORD_CTLSTATUS_COMPLETE);
                bankCardRecord.setCreateDate(param.getCreateDate());
                bankCardRecord.setBcOid(bCard.getBcOid());
                
                bankCardDao.update(bCard);
                bankCardRecordDao.insert(bankCardRecord);
            }
            
        }
        
        dao.update(param);
        creditCardDao.update(cCard);
        
    }
    
    
    private BankCardHolder getBankCardByKey(BigDecimal bcOid) throws Exception
    {
        BankCardHolder param = new BankCardHolder();
        param.setBcOid(bcOid);
        
        List<BankCardHolder> rlt = bankCardDao.getBankCards(param);
        
        if (rlt != null && !rlt.isEmpty())
        {
            return rlt.get(0);
        }
        
        return null;
    }
    
    
    private CreditCardHolder getCreditCardByKey(BigDecimal ccOid) throws Exception
    {
        CreditCardHolder param = new CreditCardHolder();
        param.setCcOid(ccOid);
        
        List<CreditCardHolder> rlt = creditCardDao.getCreditCards(param);
        
        if (rlt != null && !rlt.isEmpty())
        {
            return rlt.get(0);
        }
        
        return null;
    }


    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setDao(CreditCardRecordDao dao)
    {
        this.dao = dao;
    }


    public void setOidDao(OidDao oidDao)
    {
        this.oidDao = oidDao;
    }


    public void setCreditCardDao(CreditCardDao creditCardDao)
    {
        this.creditCardDao = creditCardDao;
    }


    public void setBankCardDao(BankCardDao bankCardDao)
    {
        this.bankCardDao = bankCardDao;
    }


    public void setBankCardRecordDao(BankCardRecordDao bankCardRecordDao)
    {
        this.bankCardRecordDao = bankCardRecordDao;
    }

}
