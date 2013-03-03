package com.oyl.ffms.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.base.holder.BaseHolder;
import com.oyl.base.holder.CommonParameterHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.service.DBActionService;
import com.oyl.ffms.dao.BankCardDao;
import com.oyl.ffms.dao.BankCardRecordDao;
import com.oyl.ffms.dao.CategoryDao;
import com.oyl.ffms.dao.CreditCardDao;
import com.oyl.ffms.dao.CreditCardRecordDao;
import com.oyl.ffms.dao.FamilyDao;
import com.oyl.ffms.dao.FamilyUserDao;
import com.oyl.ffms.dao.ItemDao;
import com.oyl.ffms.dao.OidDao;
import com.oyl.ffms.holder.BankCardHolder;
import com.oyl.ffms.holder.BankCardRecordHolder;
import com.oyl.ffms.holder.CategoryHolder;
import com.oyl.ffms.holder.CreditCardHolder;
import com.oyl.ffms.holder.CreditCardRecordHolder;
import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.holder.FamilyUserHolder;
import com.oyl.ffms.holder.ItemHolder;
import com.oyl.ffms.service.ItemService;
import com.oyl.ffms.util.CommonConstants;

public class ItemServiceImpl implements ItemService, DBActionService,
        BaseService, CommonConstants
{
    private ItemDao dao_;
    private OidDao oidDao_;
    private FamilyDao familyDao;
    private FamilyUserDao familyUserDao;
    private CategoryDao categoryDao;
    private CreditCardDao creditCardDao;
    private CreditCardRecordDao creditCardRecordDao;
    private BankCardDao bankCardDao;
    private BankCardRecordDao bankCardRecordDao;
    
    
    private final String PROCEDURE_NAME = "spOidItem";
    
    public void confirmItem(ItemHolder param) throws Exception
    {
        param.setCtlStatus(VALUE_ITEM_STATUS_COMPLETE);
        
        if (null != param.getCcOid())
        {
            CreditCardHolder card = this.getCreditCardByKey(param.getCcOid());
            
            BigDecimal amount = param.getItemCost().multiply(param.getItemQuantity());
            
            card.setQuota(card.getQuota().subtract(amount));
            card.setDebt(card.getDebt().add(amount));
            card.setPoint(card.getPoint().add(amount.divide(card.getPointCondition(), 0, BigDecimal.ROUND_DOWN)));
            
            CreditCardRecordHolder record = new CreditCardRecordHolder();
            record.setCcrOid(oidDao_.getOid());
            record.setCcrType(VALUE_CREDIT_CARD_RECORD_CCRTYPE_CONSUMPTION);
            record.setAmount(amount);
            record.setCtlStatus(VALUE_CREDIT_CARD_RECORD_CTLSTATUS_COMPLETE);
            record.setDescription(param.getItemDesc());
            record.setCreateDate(param.getCreateDate());
            record.setCcOid(card.getCcOid());
            
            creditCardDao.update(card);
            creditCardRecordDao.insert(record);
        }
        
        if (null != param.getBcOid())
        {
            BankCardHolder card = this.getBankCardByKey(param.getBcOid());
            
            BigDecimal amount = param.getItemCost().multiply(param.getItemQuantity());
            
            card.setBalance(card.getBalance().subtract(amount));
            
            
            BankCardRecordHolder record = new BankCardRecordHolder();
            record.setBcrOid(oidDao_.getOid());
            record.setBcrType(VALUE_BANK_CARD_RECORD_BCRTYPE_WITHDRAW);
            record.setAmount(amount);
            record.setCtlStatus(VALUE_BANK_CARD_RECORD_CTLSTATUS_COMPLETE);
            record.setDescription(param.getItemDesc());
            record.setCreateDate(param.getCreateDate());
            record.setBcOid(card.getBcOid());
            
            bankCardDao.update(card);
            bankCardRecordDao.insert(record);
        }
        
        dao_.update(param);
    }
    
    private CreditCardHolder getCreditCardByKey(BigDecimal ccOid) throws Exception
    {
        CreditCardHolder card = new CreditCardHolder();
        card.setCcOid(ccOid);
        
        List<CreditCardHolder> rlt = creditCardDao.getCreditCards(card);
        
        if (rlt != null && !rlt.isEmpty())
        {
            return rlt.get(0);
        }
        
        return null;
    }
    
    private BankCardHolder getBankCardByKey(BigDecimal bcOid) throws Exception
    {
        BankCardHolder card = new BankCardHolder();
        card.setBcOid(bcOid);
        
        List<BankCardHolder> rlt = bankCardDao.getBankCards(card);
        
        if (rlt != null && !rlt.isEmpty())
        {
            return rlt.get(0);
        }
        
        return null;
    }
    
    public ItemHolder getItemByKey(BigDecimal itemOid) throws Exception
    {
        ItemHolder param = new ItemHolder();
        param.setItemOid(itemOid);
        
        List<ItemHolder> list = this.getItems(param);
        if(list!=null && list.size()>0)
            return list.get(0);
        
        return null;
    }


    public List<ItemHolder> getItems(ItemHolder param) throws Exception
    {
        return dao_.getItems(param);
    }


    public BigDecimal getOid() throws Exception
    {
        return oidDao_.getOid(PROCEDURE_NAME);
    }


    public void delete(CommonParameterHolder commPara_, BaseHolder oldObj_)
            throws Exception
    {
        dao_.delete((ItemHolder)oldObj_);
    }


    public void insert(CommonParameterHolder commPara_, BaseHolder newObj_)
            throws Exception
    {
        dao_.insert((ItemHolder)newObj_);
    }


    public void update(CommonParameterHolder commPara_, BaseHolder newObj_,
            BaseHolder oldObj_) throws Exception
    {
        dao_.update((ItemHolder)newObj_);
    }


    public int getCountOfSummary(BaseHolder parameter_) throws Exception
    {
        return dao_.getCountOfSummary((ItemHolder)parameter_);
    }


    public List getListOfSummary(BaseHolder parameter_) throws Exception
    {
        return dao_.getListOfSummary((ItemHolder)parameter_);
    }


    public List<ItemHolder> getItemsByUserOid(BigDecimal userOid) throws Exception
    {
        ItemHolder param = new ItemHolder();
        param.setUserOid(userOid);
        
        return this.getItems(param);
    }
    
    
    public List<ItemHolder> getItemsByCategoryOid(BigDecimal categoryOid) throws Exception
    {
        ItemHolder param = new ItemHolder();
        param.setCategoryOid(categoryOid);
        
        return this.getItems(param);
    }
    
    
    private FamilyHolder getFamilyByUser(BigDecimal userOid) throws Exception
    {
        FamilyUserHolder param = new FamilyUserHolder();
        param.setUserOid(userOid);
        
        List<FamilyUserHolder> list = familyUserDao.getFamilyUsers(param);
        if(list==null || list.size()<1)
            return null;
        
        BigDecimal familyOid = list.get(0).getFamilyOid();
        
        FamilyHolder fParam = new FamilyHolder();
        fParam.setFamilyOid(familyOid);
        
        List<FamilyHolder> fList = familyDao.getFamilies(fParam);
        
        if(fList!=null && fList.size()>0)
            return fList.get(0);
        
        return null;
    }
    
    
    private CategoryHolder getCategoryByKey(BigDecimal categoryOid) throws Exception
    {
        CategoryHolder param = new CategoryHolder();
        param.setCategoryOid(categoryOid);
        
        List<CategoryHolder> list = categoryDao.getCategories(param);
        if(list!=null && list.size()>0)
            return list.get(0);
        
        return null;
    }
    
    
    //*****************************************************
    // setter and getter
    //*****************************************************
    public void setDao(ItemDao dao_)
    {
        this.dao_ = dao_;
    }


    public void setOidDao(OidDao oidDao_)
    {
        this.oidDao_ = oidDao_;
    }


    public void setFamilyDao(FamilyDao familyDao)
    {
        this.familyDao = familyDao;
    }


    public void setFamilyUserDao(FamilyUserDao familyUserDao)
    {
        this.familyUserDao = familyUserDao;
    }


    public void setCategoryDao(CategoryDao categoryDao)
    {
        this.categoryDao = categoryDao;
    }


    public void setCreditCardDao(CreditCardDao creditCardDao)
    {
        this.creditCardDao = creditCardDao;
    }


    public void setCreditCardRecordDao(CreditCardRecordDao creditCardRecordDao)
    {
        this.creditCardRecordDao = creditCardRecordDao;
    }

    public void setBankCardDao(BankCardDao bankCardDao)
    {
        this.bankCardDao = bankCardDao;
    }

    public void setBankCardRecordDao(BankCardRecordDao bankCardRecordDao)
    {
        this.bankCardRecordDao = bankCardRecordDao;
    }
    
}
