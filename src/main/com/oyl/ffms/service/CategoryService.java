package com.oyl.ffms.service;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.holder.CategoryHolder;

public interface CategoryService
{
    public List<CategoryHolder> getCategories(CategoryHolder param) throws Exception;
    
    public CategoryHolder getCategoryByKey(BigDecimal categoryOid) throws Exception;
    
    public List<CategoryHolder> getCategoriesByFamily(BigDecimal familyOid) throws Exception;
    
    public boolean isCategoryExist(BigDecimal familyOid, String categoryName) throws Exception;
    
    public BigDecimal getOid() throws Exception;
    
}
