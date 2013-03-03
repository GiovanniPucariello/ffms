package com.oyl.ffms.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.oyl.ffms.dao.CategoryDao;
import com.oyl.ffms.holder.CategoryHolder;

public class CategoryDaoIbatis extends SqlMapClientDaoSupport implements
        CategoryDao
{

    public int delete(CategoryHolder param)
    {
        return this.getSqlMapClientTemplate().update("deleteCategory", param);
    }


    public int getCountOfSummary(CategoryHolder param)
    {
        return (Integer) this.getSqlMapClientTemplate().queryForObject("getCountOfCategory", param);
    }


    public List<CategoryHolder> getCategories(CategoryHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getCategories", param);
    }


    public List<CategoryHolder> getListOfSummary(CategoryHolder param)
    {
        return this.getSqlMapClientTemplate().queryForList("getSummaryCategory", param);
    }


    public int insert(CategoryHolder param)
    {
        return this.getSqlMapClientTemplate().update("insertCategory", param);
    }


    public int update(CategoryHolder param)
    {
        return this.getSqlMapClientTemplate().update("updateCategory", param);
    }

}
