package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.CreditCardRecordDao;
import com.oyl.ffms.holder.CreditCardRecordHolder;

public class CreditCardRecordDaoIbatis extends SqlMapClientDaoSupport implements
        CreditCardRecordDao
{

    public int delete(CreditCardRecordHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteCreditCardRecord", param);
    }


    public int getCountOfSummary(CreditCardRecordHolder param)
    {
        return (Integer)this.getSqlMapClientTemplate().queryForObject("getCountOfCreditCardRecord", param);
    }


    public List<CreditCardRecordHolder> getCreditCardRecords(
            CreditCardRecordHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getCreditCardRecords", param);
    }


    public List<CreditCardRecordHolder> getListOfSummary(
            CreditCardRecordHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSummaryCreditCardRecord", param);
    }


    public int insert(CreditCardRecordHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertCreditCardRecord", param);
    }


    public int update(CreditCardRecordHolder param)
    {
        return this.getSqlMapClientTemplate().update("updateCreditCardRecord", param);
    }

}
