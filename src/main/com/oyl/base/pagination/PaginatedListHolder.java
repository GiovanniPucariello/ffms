package com.oyl.base.pagination;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

public class PaginatedListHolder implements PaginatedList
{
    private List list;
    private int pageNumber = 1;
    private int objectsPerPage = 10;
    private int fullListSize = 0;
    private String sortCriterion;
    private SortOrderEnum sortDirection;
    private String searchId;
    
    public int getFullListSize()
    {
        return fullListSize;
    }


    public List getList()
    {
        return list;
    }


    public int getObjectsPerPage()
    {
        return objectsPerPage;
    }


    public int getPageNumber()
    {
        return pageNumber;
    }


    public String getSearchId()
    {
        return searchId;
    }


    public String getSortCriterion()
    {
        return sortCriterion;
    }


    public SortOrderEnum getSortDirection()
    {
        return sortDirection;
    }


    public void setList(List list)
    {
        this.list = list;
    }


    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }


    public void setObjectsPerPage(int objectsPerPage)
    {
        this.objectsPerPage = objectsPerPage;
    }


    public void setFullListSize(int fullListSize)
    {
        this.fullListSize = fullListSize;
    }


    public void setSortCriterion(String sortCriterion)
    {
        this.sortCriterion = sortCriterion;
    }


    public void setSortDirection(SortOrderEnum sortDirection)
    {
        this.sortDirection = sortDirection;
    }


    public void setSearchId(String searchId)
    {
        this.searchId = searchId;
    }
}
