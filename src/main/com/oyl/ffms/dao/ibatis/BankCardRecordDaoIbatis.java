package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.BankCardRecordDao;
import com.oyl.ffms.holder.BankCardRecordHolder;

public class BankCardRecordDaoIbatis extends SqlMapClientDaoSupport implements
        BankCardRecordDao
{

    public int delete(BankCardRecordHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteBankCardRecord", param);
    }


    public List<BankCardRecordHolder> getBankCardRecords(
            BankCardRecordHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getBankCardRecords", param);
    }


    public int getCountOfSummary(BankCardRecordHolder param)
    {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("getCountOfBankCardRecord", param);
    }


    public List<BankCardRecordHolder> getListOfSummary(
            BankCardRecordHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSummaryBankCardRecord", param);
    }


    public int insert(BankCardRecordHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertBankCardRecord", param);
    }


    public int update(BankCardRecordHolder param)
    {
        return this.getSqlMapClientTemplate().update("updateBankCardRecord", param);
    }

}
