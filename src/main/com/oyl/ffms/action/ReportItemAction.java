package com.oyl.ffms.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.oyl.base.util.DateUtil;
import com.oyl.base.util.ErrorMsgHelper;
import com.oyl.ffms.holder.CategoryHolder;
import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.holder.UserProfileHolder;
import com.oyl.ffms.service.CategoryService;
import com.oyl.ffms.service.FamilyService;
import com.oyl.ffms.service.ReportService;
import com.oyl.ffms.util.CommonConstants;

public class ReportItemAction extends ProjectBaseAction implements
        CommonConstants
{
    private String reportType;
    private Date fromDate;
    private Date toDate;
    private BigDecimal selectedUserOid;
    private BigDecimal categoryOid;
    
    private List<UserProfileHolder> users;
    private List<CategoryHolder> categories;
    
    
    private CategoryService categoryService;
    private FamilyService familyService;
    private ReportService reportService;
    
    private InputStream resultObject_;
    
    public String init()
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        
        fromDate = c.getTime();
        toDate   = new Date();
        
        try
        {
            UserProfileHolder curUser = this.getLoginOfUser();
            FamilyHolder curFamily = familyService.getFamilyByUserOid(curUser.getUserOid());
            
            users      = familyService.getUsersByFamilyOid(curFamily.getFamilyOid());
            categories = categoryService.getCategoriesByFamily(curFamily.getFamilyOid());
        }
        catch(Exception e)
        {
            log.error("init(): ");
            log.error("Error occur on init", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return SUCCESS;
    }

    
    public String printPdf()
    {
        try
        {
            if ("N".equals(reportType))
            {
                List dataSource = this.getDataSourceForDetailReport();
                
                JasperReport jr = JasperCompileManager.compileReport(this.getClass().getClassLoader().getResourceAsStream("ITEM_DETAIL_REPORT.jrxml"));
                JasperPrint  jp = JasperFillManager.fillReport(jr, this.initPdfParamForDetailReport(), new JRBeanCollectionDataSource(dataSource));
                
                byte[] rlt = JasperExportManager.exportReportToPdf(jp);
                
                resultObject_ = new ByteArrayInputStream(rlt);
            }
            else if("D".equals(reportType))
            {
                List dataSource = this.getDataSourceForDailyReport();
                
                JasperReport jr = JasperCompileManager.compileReport(this.getClass().getClassLoader().getResourceAsStream("ITEM_DAILY_REPORT.jrxml"));
                JasperPrint  jp = JasperFillManager.fillReport(jr, this.initPdfParamForDailyReport(), new JRBeanCollectionDataSource(dataSource));
                
                byte[] rlt = JasperExportManager.exportReportToPdf(jp);
                
                resultObject_ = new ByteArrayInputStream(rlt);
            }
            else if("M".equals(reportType))
            {
                List dataSource = this.getDataSourceForMonthlyReport();
                
                JasperReport jr = JasperCompileManager.compileReport(this.getClass().getClassLoader().getResourceAsStream("ITEM_MONTHLY_REPORT.jrxml"));
                JasperPrint  jp = JasperFillManager.fillReport(jr, this.initPdfParamForMonthlyReport(), new JRBeanCollectionDataSource(dataSource));
                
                byte[] rlt = JasperExportManager.exportReportToPdf(jp);
                
                resultObject_ = new ByteArrayInputStream(rlt);
            }
        }
        catch(Exception e)
        {
            log.error("printStatistics(): ");
            log.error("Error occur on printStatistics", e);
            
            ErrorMsgHelper.logError(log, e, this, msg);

            return FORWARD_COMMON_MESSAGE;
        }
        
        return SUCCESS;
    }
    
    //*****************************************************
    // private methods
    //*****************************************************
    
    private Map initPdfParamForDetailReport() throws Exception
    {
        FamilyHolder family = familyService.getFamilyByUserOid(getLoginOfUser().getUserOid());
        
        Map param = new HashMap();
        param.put("familyName", family.getFamilyName());
        param.put("fromDate", fromDate);
        param.put("toDate", toDate);
        
        return param;
    }
    
    
    private Map initPdfParamForDailyReport() throws Exception
    {
        FamilyHolder family = familyService.getFamilyByUserOid(getLoginOfUser().getUserOid());
        
        Map param = new HashMap();
        param.put("familyName", family.getFamilyName());
        param.put("fromDate", fromDate);
        param.put("toDate", toDate);
        
        return param;
    }
    
    
    private Map initPdfParamForMonthlyReport() throws Exception
    {
        FamilyHolder family = familyService.getFamilyByUserOid(getLoginOfUser().getUserOid());
        
        Map param = new HashMap();
        param.put("familyName", family.getFamilyName());
        param.put("fromDate", this.getRequest().getParameter("sFromDate"));
        param.put("toDate", this.getRequest().getParameter("sToDate"));
        
        return param;
    }
    
    
    private List getDataSourceForDetailReport() throws Exception
    {
        Date paramFromDate = null;
        Date paramToDate = null;
        BigDecimal paramSelectedUserOid = new BigDecimal(-1).equals(selectedUserOid) ? null : selectedUserOid;
        BigDecimal paramCateOid = new BigDecimal(-1).equals(categoryOid) ? null : categoryOid;
        
        Calendar c = Calendar.getInstance();
        if(fromDate!=null)
        {
            c.setTime(fromDate);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            
            paramFromDate = c.getTime();
        }
        if(toDate!=null)
        {
            c.setTime(toDate);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
            
            paramToDate = c.getTime();
        }
        
        return reportService.getItemForDetailReport(this.getLoginOfUser()
                .getUserOid(), paramSelectedUserOid, paramCateOid,
                paramFromDate, paramToDate);
    }
    
    
    private List getDataSourceForDailyReport() throws Exception
    {
        Date paramFromDate = null;
        Date paramToDate = null;
        BigDecimal paramSelectedUserOid = new BigDecimal(-1).equals(selectedUserOid) ? null : selectedUserOid;
        
        Calendar c = Calendar.getInstance();
        if(fromDate!=null)
        {
            c.setTime(fromDate);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            
            paramFromDate = c.getTime();
        }
        if(toDate!=null)
        {
            c.setTime(toDate);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
            
            paramToDate = c.getTime();
        }
        
        return reportService
                .getItemForDailyReport(this.getLoginOfUser().getUserOid(),
                        paramSelectedUserOid, paramFromDate, paramToDate);
    }
    
    
    private List getDataSourceForMonthlyReport() throws Exception
    {
        Date paramFromDate = null;
        Date paramToDate = null;
        BigDecimal paramSelectedUserOid = new BigDecimal(-1).equals(selectedUserOid) ? null : selectedUserOid;
        
        Calendar c = Calendar.getInstance();
        
        
        
        if (this.getRequest().getParameter("sFromDate") != null)
        {
            String sFormDate = this.getRequest().getParameter("sFromDate")+"-01";
            
            c.setTime(DateUtil.convertStringToDate(sFormDate));
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            
            paramFromDate = c.getTime();
        }
        if (this.getRequest().getParameter("sToDate") != null)
        {
            String sToDate = this.getRequest().getParameter("sToDate")+"-01";
            
            c.setTime(DateUtil.convertStringToDate(sToDate));
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DAY_OF_YEAR, -1);
            
            paramToDate = c.getTime();
        }
        
        
        return reportService
                .getItemForMonthlyReport(this.getLoginOfUser().getUserOid(),
                        paramSelectedUserOid, paramFromDate, paramToDate);
    }
    
    
    //*****************************************************
    // setter and getter methods
    //*****************************************************
    
    public String getReportType()
    {
        return reportType;
    }


    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }


    public Date getFromDate()
    {
        return fromDate;
    }


    public void setFromDate(Date fromDate)
    {
        this.fromDate = fromDate;
    }


    public Date getToDate()
    {
        return toDate;
    }


    public void setToDate(Date toDate)
    {
        this.toDate = toDate;
    }


    public BigDecimal getSelectedUserOid()
    {
        return selectedUserOid;
    }


    public void setSelectedUserOid(BigDecimal selectedUserOid)
    {
        this.selectedUserOid = selectedUserOid;
    }


    public BigDecimal getCategoryOid()
    {
        return categoryOid;
    }


    public void setCategoryOid(BigDecimal categoryOid)
    {
        this.categoryOid = categoryOid;
    }


    public List<UserProfileHolder> getUsers()
    {
        return users;
    }


    public List<CategoryHolder> getCategories()
    {
        return categories;
    }


    public InputStream getResultObject()
    {
        return resultObject_;
    }


    public void setCategoryService(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }


    public void setFamilyService(FamilyService familyService)
    {
        this.familyService = familyService;
    }


    public void setReportService(ReportService reportService)
    {
        this.reportService = reportService;
    }
}
