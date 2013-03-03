package com.oyl.ffms.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import com.oyl.base.util.ErrorMsgHelper;
import com.oyl.ffms.holder.BankCardHolder;
import com.oyl.ffms.holder.BankCardRecordHolder;
import com.oyl.ffms.holder.CategoryHolder;
import com.oyl.ffms.holder.CreditCardRecordHolder;
import com.oyl.ffms.holder.ItemHolder;
import com.oyl.ffms.holder.SalaryRecordHolder;
import com.oyl.ffms.service.BankCardRecordService;
import com.oyl.ffms.service.BankCardService;
import com.oyl.ffms.service.CategoryService;
import com.oyl.ffms.service.CreditCardRecordService;
import com.oyl.ffms.service.ItemService;
import com.oyl.ffms.service.SalaryRecordService;
import com.oyl.ffms.util.CommonConstants;

public class AjaxAction extends ProjectBaseAction implements CommonConstants
{
    private BigDecimal paramOid_;
    
    private CategoryService categoryService;
    private ItemService itemService;
    private BankCardRecordService bankCardRecordService;
    private BankCardService bankCardService;
    private SalaryRecordService salaryRecordService;
    private CreditCardRecordService creditCardRecordService;
    
    private InputStream resultObject_;
    
    
    public AjaxAction()
    {
        
    }
    
    
    public String checkSalaryRecord()
    {
        String rlt = null;
        
        try
        {
            SalaryRecordHolder oldObj_ = salaryRecordService.getSalaryRecordByKey(paramOid_);
            
            if(oldObj_.getCtlStatus().equals(VALUE_SALARY_RECORD_STATUS_COMPLETE))
            {
                rlt = "<result><status><![CDATA["+VALUE_NO+"]]></status></result>";
            }
            else
            {
                rlt = "<result><status><![CDATA["+VALUE_YES+"]]></status></result>";
            }
            
            resultObject_ = new ByteArrayInputStream(rlt.getBytes());
        }
        catch (Exception e)
        {
            log.error(ErrorMsgHelper.getStackTrace(e));
        }
        
        return SUCCESS;
    }

    
    public String requestCategoryType()
    {
        try
        {
            CategoryHolder obj = categoryService.getCategoryByKey(paramOid_);
            
            String rlt = "<result><categoryType><![CDATA["+obj.getCategoryType()+"]]></categoryType></result>";
            
            resultObject_ = new ByteArrayInputStream(rlt.getBytes());
        }
        catch(Exception e)
        {
            log.error(ErrorMsgHelper.getStackTrace(e));
        }
        
        return SUCCESS;
    }
    
    
    public String requestBankCards()
    {
        try
        {
            List<BankCardHolder> cards = bankCardService.getBankCarsByUser(paramOid_);
            
            StringBuffer rlt = new StringBuffer();
            
            rlt.append("<result>");
            for (BankCardHolder card : cards)
            {
                rlt.append("<card>" + card.getBcOid() + "---" + card.getCardNo() + "</card>");
            }
            rlt.append("</result>");
            
            resultObject_ = new ByteArrayInputStream(rlt.toString().getBytes());
        }
        catch(Exception e)
        {
            log.error(ErrorMsgHelper.getStackTrace(e));
        }
        
        return SUCCESS;
    }
    
    
    public String checkCategory()
    {
        String rlt = null;
        
        try
        {
            CategoryHolder oldObj_ = categoryService.getCategoryByKey(paramOid_);
            List list = itemService.getItemsByCategoryOid(paramOid_);
            
            if(list!=null && list.size()>0)
            {
                rlt = "<result><status><![CDATA["+VALUE_NO+"]]></status></result>";
            }
            else
            {
                rlt = "<result><status><![CDATA["+VALUE_YES+"]]></status></result>";
            }
            
            resultObject_ = new ByteArrayInputStream(rlt.getBytes());
        }
        catch(Exception e)
        {
            log.error(ErrorMsgHelper.getStackTrace(e));
        }
        
        return SUCCESS;
    }
    
    
    public String checkItem()
    {
        String rlt = null;
        
        try
        {
            ItemHolder oldObj_ = itemService.getItemByKey(paramOid_);
            
            if(oldObj_.getCtlStatus().equals(VALUE_ITEM_STATUS_COMPLETE))
            {
                rlt = "<result><status><![CDATA["+VALUE_NO+"]]></status></result>";
            }
            else
            {
                rlt = "<result><status><![CDATA["+VALUE_YES+"]]></status></result>";
            }
            
            resultObject_ = new ByteArrayInputStream(rlt.getBytes());
        }
        catch(Exception e)
        {
            log.error(ErrorMsgHelper.getStackTrace(e));
        }
        
        return SUCCESS;
    }
    
    
    public String checkBankCardRecord()
    {
        String rlt = null;
        
        try
        {
            BankCardRecordHolder oldObj_ = bankCardRecordService.getBankCardRecordByKey(paramOid_);
            
            if(oldObj_.getCtlStatus().equals(VALUE_BANK_CARD_RECORD_CTLSTATUS_COMPLETE))
            {
                rlt = "<result><status><![CDATA["+VALUE_NO+"]]></status></result>";
            }
            else
            {
                rlt = "<result><status><![CDATA["+VALUE_YES+"]]></status></result>";
            }
            
            resultObject_ = new ByteArrayInputStream(rlt.getBytes());
        }
        catch(Exception e)
        {
            log.error(ErrorMsgHelper.getStackTrace(e));
        }
        
        return SUCCESS;
    }
    
    
    public String checkCreditCardRecord()
    {
        String rlt = null;
        
        try
        {
            CreditCardRecordHolder oldObj_ = creditCardRecordService.getCreditCardRecordByKey(paramOid_);
            
            if(oldObj_.getCtlStatus().equals(VALUE_CREDIT_CARD_RECORD_CTLSTATUS_COMPLETE))
            {
                rlt = "<result><status><![CDATA["+VALUE_NO+"]]></status></result>";
            }
            else
            {
                rlt = "<result><status><![CDATA["+VALUE_YES+"]]></status></result>";
            }
            
            resultObject_ = new ByteArrayInputStream(rlt.getBytes());
        }
        catch(Exception e)
        {
            log.error(ErrorMsgHelper.getStackTrace(e));
        }
        
        return SUCCESS;
    }
    
    //*****************************************************
    // setter and getter
    //*****************************************************
    public InputStream getResultObject()
    {
        return resultObject_;
    }

    
    public void setCategoryService(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    
    public BigDecimal getParamOid()
    {
        return paramOid_;
    }

    
    public void setParamOid(BigDecimal paramOid_)
    {
        this.paramOid_ = paramOid_;
    }


    public void setItemService(ItemService itemService)
    {
        this.itemService = itemService;
    }


    public void setBankCardRecordService(BankCardRecordService bankCardRecordService)
    {
        this.bankCardRecordService = bankCardRecordService;
    }


    public void setBankCardService(BankCardService bankCardService)
    {
        this.bankCardService = bankCardService;
    }


    public void setSalaryRecordService(SalaryRecordService salaryRecordService)
    {
        this.salaryRecordService = salaryRecordService;
    }


    public void setCreditCardRecordService(
            CreditCardRecordService creditCardRecordService)
    {
        this.creditCardRecordService = creditCardRecordService;
    }
    
}
