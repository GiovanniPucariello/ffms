package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.SalaryRecordDao;
import com.oyl.ffms.holder.SalaryRecordHolder;

public class SalaryRecordDaoIbatis extends SqlMapClientDaoSupport implements
        SalaryRecordDao
{

    public int delete(SalaryRecordHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteSalaryRecord", param);
    }


    public int getCountOfSummary(SalaryRecordHolder param)
    {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("getCountOfSalaryRecord", param);
    }


    public List<SalaryRecordHolder> getListOfSummary(SalaryRecordHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSummarySalaryRecord", param);
    }


    public List<SalaryRecordHolder> getSalaryRecords(SalaryRecordHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSalaryRecords", param);
    }


    public int insert(SalaryRecordHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertSalaryRecord", param);
    }


    public int update(SalaryRecordHolder param)
    {
        return this.getSqlMapClientTemplate().update("updateSalaryRecord", param);
    }

}
