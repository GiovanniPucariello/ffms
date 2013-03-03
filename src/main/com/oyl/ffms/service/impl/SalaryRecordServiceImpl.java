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
import com.oyl.ffms.dao.SalaryRecordDao;
import com.oyl.ffms.holder.BankCardHolder;
import com.oyl.ffms.holder.BankCardRecordHolder;
import com.oyl.ffms.holder.SalaryRecordHolder;
import com.oyl.ffms.service.SalaryRecordService;
import com.oyl.ffms.util.CommonConstants;

public class SalaryRecordServiceImpl implements DBActionService, BaseService,
        SalaryRecordService, CommonConstants
{
    private SalaryRecordDao dao;
    private BankCardDao bankCardDao;
    private BankCardRecordDao bankCardRecordDao;
    private OidDao oidDao;
    

    public void delete(CommonParameterHolder commPara, BaseHolder oldObj)
            throws Exception
    {
        dao.delete((SalaryRecordHolder) oldObj);
    }


    public void insert(CommonParameterHolder commPara, BaseHolder newObj)
            throws Exception
    {
        dao.insert((SalaryRecordHolder) newObj);
    }


    public void update(CommonParameterHolder commPara, BaseHolder newObj,
            BaseHolder oldObj) throws Exception
    {
        dao.update((SalaryRecordHolder) newObj);
    }


    public int getCountOfSummary(BaseHolder parameter) throws Exception
    {
        return dao.getCountOfSummary((SalaryRecordHolder) parameter);
    }


    public List getListOfSummary(BaseHolder parameter) throws Exception
    {
        return dao.getListOfSummary((SalaryRecordHolder) parameter);
    }


    public BigDecimal getOid() throws Exception
    {
        return oidDao.getOid();
    }


    public SalaryRecordHolder getSalaryRecordByKey(BigDecimal srOid)
            throws Exception
    {
        SalaryRecordHolder param = new SalaryRecordHolder();
        param.setSrOid(srOid);
        
        List<SalaryRecordHolder> rlt = this.getSalaryRecords(param);
        
        if (rlt != null && !rlt.isEmpty())
        {
            return rlt.get(0);
        }
        
        return null;
    }


    public List<SalaryRecordHolder> getSalaryRecords(SalaryRecordHolder param)
            throws Exception
    {
        return dao.getSalaryRecords(param);
    }
    
    
    public void confirmSalaryRecord(SalaryRecordHolder param) throws Exception
    {
        param.setCtlStatus(VALUE_SALARY_RECORD_STATUS_COMPLETE);
        
        dao.update(param);
        
        if (null != param.getBcOid())
        {
            BankCardHolder card = this.getBankCardByKey(param.getBcOid());
            
            if (null == card)
            {
                return;
            }
            
            card.setBalance(card.getBalance().add(param.getAmount()));
            bankCardDao.update(card);
            
            
            BankCardRecordHolder record = new BankCardRecordHolder();
            record.setBcrOid(this.getOid());
            record.setBcrType(VALUE_BANK_CARD_RECORD_BCRTYPE_DEPOSITE);
            record.setAmount(param.getAmount());
            
            if (VALUE_SALARY_RECORD_SRTYPE_SALARY.equals(param.getSrType()))
            {
                record.setDescription("\u5de5\u8d44");
            }
            else if (VALUE_SALARY_RECORD_SRTYPE_INCENTIVE.equals(param.getSrType()))
            {
                record.setDescription("\u5956\u91d1");
            }
            else 
            {
                record.setDescription(param.getDescription());
            }
            
            record.setCtlStatus(VALUE_BANK_CARD_RECORD_CTLSTATUS_COMPLETE);
            record.setCreateDate(param.getCreateDate());
            record.setBcOid(card.getBcOid());
            
            bankCardRecordDao.insert(record);
        }
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

    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setDao(SalaryRecordDao dao)
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


    public void setBankCardRecordDao(BankCardRecordDao bankCardRecordDao)
    {
        this.bankCardRecordDao = bankCardRecordDao;
    }

}
