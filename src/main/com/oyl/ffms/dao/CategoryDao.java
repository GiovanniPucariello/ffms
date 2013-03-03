package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.CategoryHolder;

public interface CategoryDao
{
    public List<CategoryHolder> getCategories(CategoryHolder param);
    
    public int getCountOfSummary(CategoryHolder param);    
    
    public List<CategoryHolder> getListOfSummary(CategoryHolder param);
    
    public int insert(CategoryHolder param);
    
    public int delete(CategoryHolder param);
    
    public int update(CategoryHolder param);
}
