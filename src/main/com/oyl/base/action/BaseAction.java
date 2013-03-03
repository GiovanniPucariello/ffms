package com.oyl.base.action;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.displaytag.pagination.PaginatedList;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.oyl.base.email.EmailEngine;
import com.oyl.base.holder.BaseHolder;
import com.oyl.base.holder.MessageHolder;
import com.oyl.base.pagination.PaginateHelper;
import com.oyl.base.pagination.PaginateParameter;
import com.oyl.base.pagination.PaginatedListHolder;
import com.oyl.base.service.BaseService;
import com.oyl.base.util.CommonConstants;
import com.oyl.base.util.DateUtil;
import com.oyl.base.util.NumberUtil;

public abstract class BaseAction extends ActionSupport implements
        PaginateParameter, CommonConstants
{
    protected final Log log = LogFactory.getLog(this.getClass());
    
    protected ResourceBundleMessageSource messageSource;
    protected ResourceBundleMessageSource sortingMapSource;
    
    // for error handle
    protected String ticket;
    protected MessageHolder msg;
 
    // process paginate and sorting
    protected int pageCount = 0;
    protected int currentPage = 1;
    protected String sortDir;
    protected String sortField;

    protected PaginatedList paginatedList;

    // pagination util
    protected PaginateHelper paginateHelper; 
    protected EmailEngine emailEngine;
    protected DateUtil dateUtil;
    protected NumberUtil numberUtil;

    protected void initMsg()
    {
        msg = new MessageHolder();
    }
    
    public ActionContext getActionContext()
    {
        return ActionContext.getContext();
    }
    
    public Map getSession()
    {
        return getActionContext().getSession();
    }
    
    public SessionMap getSessionMap()
    {   
        return (SessionMap) ActionContext.getContext().getSession();
    }
        
    public HttpServletRequest getRequest()
    {
        return ServletActionContext.getRequest();
    }
    
    public HttpServletResponse getResponse()
    {
        return ServletActionContext.getResponse();
    }
    
    public HttpSession getHttpSession()
    {
        return ServletActionContext.getRequest().getSession();
    }
    
    protected String getMessage(String key, Object[] args)
    {
        return messageSource.getMessage(key, args, Locale.US);
    }
    
    
    protected String getMessage(String key)
    {
        return getMessage(key, null);
    }
    
    protected String getMessage(String key, Object value1)
    {
        return getMessage(key, new Object[]{value1});
    }
    
    
    protected void initSorting(BaseHolder baseHolder, String prefixSorting)
    {
        if (log.isDebugEnabled())
        {
            log.debug("init sorting for super.");
        }
        if (sortField != null && sortField.trim().length() > 0
                && sortDir != null && sortDir.trim().length() > 0)
        {
            if (sortDir.toLowerCase().equals(SORTING_ASC) ||
                    sortDir.toLowerCase().equals(SORTING_DESC))
            {
                //obtain the sort column in database table  from sortingMap.properties base on property name
                String mappingField = sortingMapSource.getMessage(prefixSorting
                        + sortField, null, null);

                if (log.isDebugEnabled())
                {
                    log.debug("mappingField:" + mappingField);
                }

                baseHolder.setSortField(mappingField);
                baseHolder.setSortOrder(sortDir);
            }
        }
        else //set the default sort field and order
        {
            sortField = sortingMapSource.getMessage(prefixSorting + "default.field",null,null);  
            baseHolder.setSortField(sortingMapSource.getMessage(prefixSorting + "default.column",null,null));
            baseHolder.setSortOrder(sortingMapSource.getMessage(prefixSorting + "default.sortOrder",null,null));
        }
    }

    protected void processPaginationAndSorting(List recordList, int pageSize,
            int recordCount)
    {
        if (log.isDebugEnabled())
        {
            log.debug("process pagination and sorting for super.");
        }
        paginatedList = new PaginatedListHolder();
        ((PaginatedListHolder) paginatedList).setList(recordList);
        ((PaginatedListHolder) paginatedList).setFullListSize(recordCount);
        ((PaginatedListHolder) paginatedList).setPageNumber(currentPage);
        ((PaginatedListHolder) paginatedList).setObjectsPerPage(pageSize);

        if (sortField != null && sortField.trim().length() > 0
                && sortDir != null && sortDir.trim().length() > 0)
        {
            if (sortDir.toLowerCase().equals(SORTING_ASC) || 
                    sortDir.toLowerCase().equals(SORTING_DESC))
            {
                ((PaginatedListHolder) paginatedList).setSortCriterion(sortField);
                
                ((PaginatedListHolder) paginatedList)
                        .setSortDirection(paginateHelper
                                .getSortOrderEnum(sortDir));
            }
        }
    }
    
    protected void obtainListRecordsOfPagination(BaseService service_, 
            BaseHolder parameter_, String keyOfSortMapping_)
        throws Exception
    {
        int pageSize = this.retrievePageSize();
        this.setPageCount((currentPage-1)*pageSize);
        
        int recordCount = service_.getCountOfSummary(parameter_);
        
        paginateHelper.calculateRecordNum(currentPage, pageSize, recordCount, parameter_);
                
        this.initSorting(parameter_, keyOfSortMapping_);
        
        List recordList = service_.getListOfSummary(parameter_);
        
        this.processPaginationAndSorting(recordList, pageSize, recordCount);
    }
    
    protected void obtainListRecordsOfPagination(BaseService service_, 
            BaseHolder parameter_, String keyOfSortMapping_, int pageSize_)
        throws Exception
    {
        int pageSize = pageSize_;
        
        this.setPageCount((currentPage-1)*pageSize);
        
        int recordCount = service_.getCountOfSummary(parameter_);
        
        paginateHelper.calculateRecordNum(currentPage, pageSize, recordCount, parameter_);
                
        this.initSorting(parameter_, keyOfSortMapping_);
        
        List recordList = service_.getListOfSummary(parameter_);
        
        this.processPaginationAndSorting(recordList, pageSize, recordCount);
    }    

    protected boolean isKeepSearchParameter()
    {
        String keepSP = ServletActionContext.getRequest().getParameter(REQ_PARAMETER_KEEP_SEARCH_CONDITION);
        
        if (keepSP == null) return false;
        
        if (VALUE_YES.equals(keepSP.trim())) return true;
        
        return false;
    }
    
    protected void clearSearchParameter(String sessionKey_)
    {
        if (!isKeepSearchParameter())
        {
            getActionContext().getSession().remove(sessionKey_);
        }
    }
    
    protected int retrievePageSize()
    {
        return 5;
    }
    
    //*****************************************************
    // setter and getter
    //*****************************************************
    
    public int getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }

    public ResourceBundleMessageSource getMessageSource()
    {
        return messageSource;
    }

    public void setMessageSource(ResourceBundleMessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    public PaginatedList getPaginatedList()
    {
        return paginatedList;
    }

    public void setPaginatedList(PaginatedList paginatedList)
    {
        this.paginatedList = paginatedList;
    }

    public PaginateHelper getPaginateHelper()
    {
        return paginateHelper;
    }

    public void setPaginateHelper(PaginateHelper paginateHelper)
    {
        this.paginateHelper = paginateHelper;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public String getSortDir()
    {
        return sortDir;
    }

    public String getSortField()
    {
        return sortField;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

    public void setSortDir(String sortDir)
    {
        this.sortDir = sortDir;
    }

    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }

    public ResourceBundleMessageSource getSortingMapSource()
    {
        return sortingMapSource;
    }

    public void setSortingMapSource(ResourceBundleMessageSource sortingMapSource)
    {
        this.sortingMapSource = sortingMapSource;
    }

    public String getTicket()
    {
        return ticket;
    }

    public void setTicket(String ticket)
    {
        this.ticket = ticket;
    }

    public MessageHolder getMsg()
    {
        return msg;
    }

    public void setMsg(MessageHolder msg)
    {
        this.msg = msg;
    } 
    
    public EmailEngine getEmailEngine()
    {
        return emailEngine;
    }

    public void setEmailEngine(EmailEngine emailEngine)
    {
        this.emailEngine = emailEngine;
    }

    public DateUtil getDateUtil()
    {
        return dateUtil;
    }

    public void setDateUtil(DateUtil dateUtil)
    {
        this.dateUtil = dateUtil;
    }

    public NumberUtil getNumberUtil()
    {
        return numberUtil;
    }

    public void setNumberUtil(NumberUtil numberUtil)
    {
        this.numberUtil = numberUtil;
    }

    public String toString()
    {
        try
        {
            return BeanUtils.describe(this).toString();
        }
        catch (Exception exception)
        {
            return exception.getMessage();
        }
    }
}
