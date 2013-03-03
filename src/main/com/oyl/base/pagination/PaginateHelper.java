package com.oyl.base.pagination;

import org.displaytag.properties.SortOrderEnum;

import com.oyl.base.holder.BaseHolder;

public class PaginateHelper
{
    
    public void calculateRecordNum(int page, int pageSize, int recordCount,
            BaseHolder baseHolder)
    {
        int startNum = 0;
        int endNum = 0;
        int needCount = page * pageSize;
        if (needCount > recordCount)
        {
            int actualPage = recordCount / pageSize;
            int modPage = recordCount % pageSize;
            
            if (actualPage == 0)
            {
                startNum = 1;
            }
            else
            {
                if (modPage == 0)
                {
                    startNum = (actualPage - 1) * pageSize + 1;
                }
                else
                {
                    startNum = actualPage * pageSize + 1;
                }
            }

            endNum = recordCount;
        }
        else
        {
            startNum = (page - 1) * pageSize + 1;
            endNum = needCount;
        }
        
        baseHolder.setStartRecordNum(startNum);
        baseHolder.setEndRecordNum(endNum);
    }


    public SortOrderEnum getSortOrderEnum(String sortOrder)
    {
        if (sortOrder.toLowerCase().equals("asc"))
        {
            return SortOrderEnum.ASCENDING;
        }
        else if (sortOrder.toLowerCase().equals("desc"))
        {
            return SortOrderEnum.DESCENDING;
        }
        else
        {
            return null;
        }
    }
}
