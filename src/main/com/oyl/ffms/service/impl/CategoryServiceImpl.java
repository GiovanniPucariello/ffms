package com.oyl.ffms.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.base.holder.BaseHolder;
import com.oyl.base.holder.CommonParameterHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.ffms.dao.CategoryDao;
import com.oyl.ffms.dao.OidDao;
import com.oyl.ffms.holder.CategoryHolder;
import com.oyl.ffms.service.CategoryService;

public class CategoryServiceImpl implements CategoryService, DBActionService,
        BaseService
{
    private CategoryDao dao_;
    private OidDao oidDao_;
    
    
    public List<CategoryHolder> getCategories(CategoryHolder param)
            throws Exception
    {
        return dao_.getCategories(param);
    }


    public CategoryHolder getCategoryByKey(BigDecimal categoryOid)
            throws Exception
    {
        CategoryHolder param = new CategoryHolder();
        param.setCategoryOid(categoryOid);
        
        List<CategoryHolder> list = this.getCategories(param);
        if(list!=null && list.size()>0)
            return list.get(0);
        
        return null;
    }
    
    
    public List<CategoryHolder> getCategoriesByFamily(BigDecimal familyOid)
            throws Exception
    {
        CategoryHolder param = new CategoryHolder();
        param.setFamilyOid(familyOid);
        
        return this.getCategories(param);
    }


    public BigDecimal getOid() throws Exception
    {
        return oidDao_.getOid();
    }


    public boolean isCategoryExist(BigDecimal familyOid, String categoryName)
            throws Exception
    {
        CategoryHolder param = new CategoryHolder();
        param.setFamilyOid(familyOid);
        param.setCategoryName(categoryName);
        
        List<CategoryHolder> list = this.getCategories(param);
        if(list!=null && list.size()>0)
            return true;
        
        return false;
    }


    public void delete(CommonParameterHolder commPara_, BaseHolder oldObj_)
            throws Exception
    {
        dao_.delete((CategoryHolder)oldObj_);
    }


    public void insert(CommonParameterHolder commPara_, BaseHolder newObj_)
            throws Exception
    {
        dao_.insert((CategoryHolder)newObj_);
    }


    public void update(CommonParameterHolder commPara_, BaseHolder newObj_,
            BaseHolder oldObj_) throws Exception
    {
        dao_.update((CategoryHolder)newObj_);
    }


    public int getCountOfSummary(BaseHolder parameter_) throws Exception
    {
        return dao_.getCountOfSummary((CategoryHolder)parameter_);
    }


    public List getListOfSummary(BaseHolder parameter_) throws Exception
    {
        return dao_.getListOfSummary((CategoryHolder)parameter_);
    }

    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setDao(CategoryDao dao_)
    {
        this.dao_ = dao_;
    }


    public void setOidDao(OidDao oidDao_)
    {
        this.oidDao_ = oidDao_;
    }

}
