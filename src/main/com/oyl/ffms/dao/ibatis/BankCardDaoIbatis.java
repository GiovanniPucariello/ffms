package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.BankCardDao;
import com.oyl.ffms.holder.BankCardHolder;

public class BankCardDaoIbatis extends SqlMapClientDaoSupport implements
        BankCardDao
{

    public int delete(BankCardHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteBankCard", param);
    }


    public List<BankCardHolder> getBankCards(BankCardHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getBankCards", param);
    }


    public int getCountOfSummary(BankCardHolder param)
    {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("getCountOfBankCard", param);
    }


    public List<BankCardHolder> getListOfSummary(BankCardHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSummaryBankCard", param);
    }


    public int insert(BankCardHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertBankCard", param);
    }


    public int update(BankCardHolder param)
    {
        return this.getSqlMapClientTemplate().update("updateBankCard", param);
    }

}
