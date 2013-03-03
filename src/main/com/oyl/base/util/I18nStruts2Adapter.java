//*****************************************************************************
//
// File Name       :  I18nStruts2Adapter.java
// Date Created    :  2008-2-4
// Last Changed By :  $Author: xuchengqing $
// Last Changed On :  $Date: 2008-02-04 14:41:59 +0800 (星期�?, 04 二月 2008) $
// Revision        :  $Rev: 326 $
// Description     :  TODO To fill in a brief description of the purpose of
//                    this class.
//
// PracBiz Pte Ltd.  Copyright (c) 2008.  All Rights Reserved.
//
//*****************************************************************************

package com.oyl.base.util;

import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.views.jsp.TagUtils;
import org.displaytag.Messages;
import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.ognl.OgnlValueStack;

/**
 * TODO To provide an overview of this class.
 * 
 * @author xuchengqing
 */
public class I18nStruts2Adapter implements LocaleResolver, I18nResourceProvider
{
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * prefix/suffix for missing entries.
     */
    public static final String UNDEFINED_KEY = "???"; //$NON-NLS-1$


    /**
     * @see LocaleResolver#resolveLocale(HttpServletRequest)
     */
    public Locale resolveLocale(HttpServletRequest request)
    {

        Locale result = null;
        OgnlValueStack stack = (OgnlValueStack) ActionContext.getContext()
                .getValueStack();

        Iterator iterator = stack.getRoot().iterator();
        while (iterator.hasNext())
        {
            Object o = iterator.next();

            if (o instanceof LocaleProvider)
            {
                LocaleProvider lp = (LocaleProvider) o;
                result = lp.getLocale();

                break;
            }
        }

        if (result == null)
        {
            log.debug("Missing LocalProvider actions, init locale to default");
            result = Locale.getDefault();
        }

        return result;
    }


    /**
     * @see I18nResourceProvider#getResource(String, String, Tag, PageContext)
     */
    public String getResource(String resourceKey, String defaultValue, Tag tag,
            PageContext pageContext)
    {
        // if resourceKey isn't defined either, use defaultValue
        String key = (resourceKey != null) ? resourceKey : defaultValue;

        String message = null;
        OgnlValueStack stack = (OgnlValueStack) TagUtils.getStack(pageContext);
        Iterator iterator = stack.getRoot().iterator();

        while (iterator.hasNext())
        {
            Object o = iterator.next();

            if (o instanceof TextProvider)
            {
                TextProvider tp = (TextProvider) o;
                tp.getText(key);
                message = tp.getText(key);

                break;
            }
        }

        // if user explicitely added a titleKey we guess this is an error
        if (message == null && resourceKey != null)
        {
            log.debug(Messages
                    .getString("Localization.missingkey", resourceKey)); //$NON-NLS-1$
            message = UNDEFINED_KEY + resourceKey + UNDEFINED_KEY;
        }

        return message;
    }
}
