package com.oyl.ffms.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.base.holder.BaseHolder;
import com.oyl.base.holder.CommonParameterHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.ffms.dao.BankCardDao;
import com.oyl.ffms.dao.OidDao;
import com.oyl.ffms.dao.SalaryRecordDao;
import com.oyl.ffms.holder.BankCardHolder;
import com.oyl.ffms.holder.SalaryRecordHolder;
import com.oyl.ffms.service.BankCardService;

public class BankCardServiceImpl implements BankCardService, BaseService,
        DBActionService
{
    private BankCardDao dao;
    private SalaryRecordDao salaryRecordDao;
    private OidDao oidDao;
    
    public BigDecimal getOid() throws Exception
    {
        return oidDao.getOid();
    }
    

    public BankCardHolder getBankCardByCardNo(String cardNo) throws Exception
    {
        BankCardHolder param = new BankCardHolder();
        param.setCardNo(cardNo);
        
        List<BankCardHolder> rlt = this.getBankCards(param);
        
        if (rlt != null && !rlt.isEmpty())
        {
            return rlt.get(0);
        }
        
        return null;
    }


    public BankCardHolder getBankCardByKey(BigDecimal bcOid) throws Exception
    {
        BankCardHolder param = new BankCardHolder();
        param.setBcOid(bcOid);
        
        List<BankCardHolder> rlt = this.getBankCards(param);
        
        if (rlt != null && !rlt.isEmpty())
        {
            return rlt.get(0);
        }
        
        return null;
    }


    public List<BankCardHolder> getBankCards(BankCardHolder param)
            throws Exception
    {
        return dao.getBankCards(param);
    }


    public int getCountOfSummary(BaseHolder parameter) throws Exception
    {
        return dao.getCountOfSummary((BankCardHolder)parameter);
    }


    public List getListOfSummary(BaseHolder parameter) throws Exception
    {
        return dao.getListOfSummary((BankCardHolder)parameter);
    }


    public void delete(CommonParameterHolder commPara, BaseHolder oldObj)
            throws Exception
    {
        dao.delete((BankCardHolder)oldObj);
    }


    public void insert(CommonParameterHolder commPara, BaseHolder newObj)
            throws Exception
    {
        dao.insert((BankCardHolder)newObj);
    }


    public void update(CommonParameterHolder commPara, BaseHolder newObj,
            BaseHolder oldObj) throws Exception
    {
        dao.update((BankCardHolder)newObj);
    }
    
    
    public List<BankCardHolder> getBankCarsByUser(BigDecimal userOid) throws Exception
    {
        BankCardHolder param = new BankCardHolder();
        param.setUserOid(userOid);
        
        return this.getBankCards(param);
    }
    
    
    public boolean isBankCardUsedBySalaryRecord(BigDecimal bcOid) throws Exception
    {
        SalaryRecordHolder param = new SalaryRecordHolder();
        param.setBcOid(bcOid);
        
        List<SalaryRecordHolder> rlt = salaryRecordDao.getSalaryRecords(param);
        
        if (null != rlt && !rlt.isEmpty())
        {
            return true;
        }
        
        return false;
    }

    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setDao(BankCardDao dao)
    {
        this.dao = dao;
    }


    public void setSalaryRecordDao(SalaryRecordDao salaryRecordDao)
    {
        this.salaryRecordDao = salaryRecordDao;
    }


    public void setOidDao(OidDao oidDao)
    {
        this.oidDao = oidDao;
    }

}
