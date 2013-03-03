package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.ItemDao;
import com.oyl.ffms.holder.ItemHolder;

public class ItemDaoIbatis extends SqlMapClientDaoSupport implements ItemDao
{

    public int delete(ItemHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteItem", param);
    }


    public int getCountOfSummary(ItemHolder param)
    {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("getCountOfItem", param);
    }


    public List<ItemHolder> getItems(ItemHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getItems", param);
    }

    
    public List<ItemHolder> getListOfSummary(ItemHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSummaryItem", param);
    }


    public int insert(ItemHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertItem", param);
    }


    public int update(ItemHolder param)
    {
        return this.getSqlMapClientTemplate().update("updateItem", param);
    }

}
