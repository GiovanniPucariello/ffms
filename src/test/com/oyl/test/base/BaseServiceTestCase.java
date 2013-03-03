//*****************************************************************************
//
// File Name       :  BaseServiceTestCase.java
// Date Created    :  5 Jan 2006
// Last Changed By :  $Author: cbchay $
// Last Changed On :  $Date: 2007-12-10 09:36:35 -0800 (Mon, 10 Dec 2007) $
// Revision        :  $Rev: 354 $
// Description     :  Base class for service layer Test cases. 
//
// PracBiz Pte Ltd.  Copyright (c) 2007.  All Rights Reserved.
//
//*****************************************************************************

package com.oyl.test.base;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

public abstract class BaseServiceTestCase extends TestCase
{
    protected static Log log = LogFactory.getLog(BaseServiceTestCase.class);

    protected ApplicationContext ctx = null;


    public BaseServiceTestCase()
    {
        ctx = TestEnvironment.getApplicationContext();
    }
}
