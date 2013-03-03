package com.oyl.ffms.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.base.holder.BaseHolder;
import com.oyl.base.holder.CommonParameterHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.ffms.dao.CreditCardDao;
import com.oyl.ffms.dao.ItemDao;
import com.oyl.ffms.dao.OidDao;
import com.oyl.ffms.holder.CreditCardHolder;
import com.oyl.ffms.holder.ItemHolder;
import com.oyl.ffms.service.CreditCardService;

public class CreditCardServiceImpl implements BaseService, DBActionService,
        CreditCardService
{
    private CreditCardDao dao;
    private OidDao oidDao;
    private ItemDao itemDao;
    
    public int getCountOfSummary(BaseHolder parameter) throws Exception
    {
        return dao.getCountOfSummary((CreditCardHolder)parameter);
    }


    public List getListOfSummary(BaseHolder parameter) throws Exception
    {
        return dao.getListOfSummary((CreditCardHolder)parameter);
    }


    public void delete(CommonParameterHolder commPara, BaseHolder oldObj)
            throws Exception
    {
        dao.delete((CreditCardHolder)oldObj);
    }


    public void insert(CommonParameterHolder commPara, BaseHolder newObj)
            throws Exception
    {
        dao.insert((CreditCardHolder)newObj);
    }


    public void update(CommonParameterHolder commPara, BaseHolder newObj,
            BaseHolder oldObj) throws Exception
    {
        dao.update((CreditCardHolder)newObj);
    }


    public CreditCardHolder getCreditCardByCardNo(String cardNo)
            throws Exception
    {
        CreditCardHolder param = new CreditCardHolder();
        param.setCardNo(cardNo);
        
        List<CreditCardHolder> rlt = this.getCreditCards(param);
        
        if (rlt != null && !rlt.isEmpty())
        {
            return rlt.get(0);
        }
        
        return null;
    }


    public CreditCardHolder getCreditCardByKey(BigDecimal ccOid)
            throws Exception
    {
        CreditCardHolder param = new CreditCardHolder();
        param.setCcOid(ccOid);
        
        List<CreditCardHolder> rlt = this.getCreditCards(param);
        
        if (rlt != null && !rlt.isEmpty())
        {
            return rlt.get(0);
        }
        
        return null;
    }


    public List<CreditCardHolder> getCreditCards(CreditCardHolder param)
            throws Exception
    {
        return dao.getCreditCards(param);
    }


    public List<CreditCardHolder> getCreditCarsByUser(BigDecimal userOid)
            throws Exception
    {
        CreditCardHolder param = new CreditCardHolder();
        param.setUserOid(userOid);
        
        return this.getCreditCards(param);
    }
    
    
    public boolean isCreditCardUsedByAnyItem(BigDecimal ccOid) throws Exception
    {
        ItemHolder param = new ItemHolder();
        param.setCcOid(ccOid);
        
        List<ItemHolder> rlt = itemDao.getItems(param);
        
        if (null != rlt && !rlt.isEmpty())
        {
            return true;
        }
        
        return false;
    }


    public BigDecimal getOid() throws Exception
    {
        return oidDao.getOid();
    }

    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public void setDao(CreditCardDao dao)
    {
        this.dao = dao;
    }


    public void setItemDao(ItemDao itemDao)
    {
        this.itemDao = itemDao;
    }


    public void setOidDao(OidDao oidDao)
    {
        this.oidDao = oidDao;
    }

}
