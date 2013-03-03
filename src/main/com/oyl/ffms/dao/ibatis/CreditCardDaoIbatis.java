package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.CreditCardDao;
import com.oyl.ffms.holder.CreditCardHolder;

public class CreditCardDaoIbatis extends SqlMapClientDaoSupport implements
        CreditCardDao
{

    public int delete(CreditCardHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteCreditCard", param);
    }


    public int getCountOfSummary(CreditCardHolder param)
    {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("getCountOfCreditCard", param);
    }


    public List<CreditCardHolder> getCreditCards(CreditCardHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getCreditCards", param);
    }


    public List<CreditCardHolder> getListOfSummary(CreditCardHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSummaryCreditCard", param);
    }


    public int insert(CreditCardHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertCreditCard", param);
    }


    public int update(CreditCardHolder param)
    {
        return this.getSqlMapClientTemplate().update("updateCreditCard", param);
    }

}
