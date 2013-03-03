//*****************************************************************************
//
// File Name       :  PaginateParameter.java
// Date Created    :  20 Feb 2008
// Last Changed By :  $Author: huangfei $
// Last Changed On :  $Date: 20 Feb 2008 $
// Revision        :  $Rev: $
// Description     :  TODO To fill in a brief description of the purpose of
//                    this class.
//
// PracBiz Pte Ltd.  Copyright (c) 2008.  All Rights Reserved.
//
//*****************************************************************************

package com.oyl.base.pagination;

/**
 * TODO To provide an overview of this class.
 *
 * @author huangfei
 */
public interface PaginateParameter
{
    public void setCurrentPage(int page);

    public void setSortField(String sortField);

    public void setSortDir(String sortDir);
}
